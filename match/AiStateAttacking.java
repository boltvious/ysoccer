/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.math.Vector3;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AiStateAttacking
/*     */   extends AiState
/*     */ {
/*     */   private float controlsAngle;
/*     */   private float targetAngle;
/*     */   
/*     */   AiStateAttacking(AiFsm fsm) {
/*  21 */     super(AiFsm.Id.STATE_ATTACKING, fsm);
/*     */   }
/*     */   
/*     */   static class Parameters {
/*  25 */     static int URGENT_TARGET_UPDATE_INTERVAL = 10;
/*  26 */     static int TARGET_UPDATE_INTERVAL = 30;
/*  27 */     static int CONTROLS_UPDATE_INTERVAL = 5;
/*  28 */     static int MATE_SEARCH_INTERVAL = 8;
/*  29 */     static float PASSING_PROBABILITY = 0.3F;
/*     */     
/*  31 */     static int PLAYER_DETECTION_RADIUS = 300;
/*  32 */     static int OUTSIDE_DETECTION_RADIUS = 60;
/*     */     
/*  34 */     static float MATE_FACTOR = 1.0F;
/*  35 */     static float OPPONENT_FACTOR = 1.5F;
/*  36 */     static float OWN_GOAL_FACTOR = 3.5F;
/*  37 */     static float GOAL_FACTOR = 2.5F;
/*  38 */     static float GOAL_FACTOR_INSIDE_PENALTY_AREA = 3.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  46 */     super.entryActions();
/*     */     
/*  48 */     this.controlsAngle = this.player.a;
/*  49 */     this.ai.x0 = Math.round(EMath.cos(this.controlsAngle));
/*  50 */     this.ai.y0 = Math.round(EMath.sin(this.controlsAngle));
/*     */     
/*  52 */     this.targetAngle = this.controlsAngle;
/*     */     
/*  54 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Enters attacking state, player.a: " + this.player.a + ", controlsAngle: " + this.controlsAngle + ", targetAngle: " + this.targetAngle);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions() {
/*  59 */     super.doActions();
/*     */     
/*  61 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "[TIMER: " + this.timer + "] controlsAngle: " + this.controlsAngle + ", player.a: " + this.player.a);
/*     */     
/*  63 */     if (isUrgentTargetUpdateTime()) {
/*  64 */       this.targetAngle += getUrgentAngleCorrection();
/*  65 */       GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Urgent target update time, targetAngle: " + this.targetAngle);
/*     */     } 
/*     */     
/*  68 */     if (isTargetUpdateTime()) {
/*  69 */       this.targetAngle += getAngleCorrection();
/*  70 */       GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Target update, targetAngle: " + this.targetAngle);
/*     */     } 
/*     */     
/*  73 */     if (isMateSearchingTime()) {
/*  74 */       this.player.searchPassingMate();
/*  75 */       GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Mate searching, passingMate: " + ((this.player.passingMate == null) ? "null" : this.player.passingMate.numberName()) + " with passingMateAngleCorrection: " + this.player.passingMateAngleCorrection + ", updating targetAngle: " + this.targetAngle + ", timer: " + this.timer);
/*     */     } 
/*     */     
/*  78 */     if (isTurningTime()) {
/*  79 */       float signedAngleDiff = EMath.signedAngleDiff(this.targetAngle, this.player.a);
/*  80 */       if (Math.abs(signedAngleDiff) > 22.5F) {
/*  81 */         this.controlsAngle += 45.0F * Math.signum(signedAngleDiff);
/*  82 */         GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Turning, signedAngleDiff: " + signedAngleDiff + ", correction: " + (45.0F * Math.signum(signedAngleDiff)) + ", new controlsAngle: " + this.controlsAngle);
/*     */       } else {
/*  84 */         GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "No turning needed");
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     this.ai.x0 = Math.round(EMath.cos(this.controlsAngle));
/*  89 */     this.ai.y0 = Math.round(EMath.sin(this.controlsAngle));
/*     */   }
/*     */   
/*     */   private boolean isUrgentTargetUpdateTime() {
/*  93 */     return ((this.timer - 1) % Parameters.URGENT_TARGET_UPDATE_INTERVAL == 0);
/*     */   }
/*     */   
/*     */   private boolean isTargetUpdateTime() {
/*  97 */     return ((this.timer - 1) % Parameters.TARGET_UPDATE_INTERVAL == 0);
/*     */   }
/*     */   
/*     */   private boolean isMateSearchingTime() {
/* 101 */     return ((this.timer - 1) % Parameters.MATE_SEARCH_INTERVAL == 0);
/*     */   }
/*     */   
/*     */   private boolean isTurningTime() {
/* 105 */     return ((this.timer - 1) % Parameters.CONTROLS_UPDATE_INTERVAL == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   State checkConditions() {
/* 112 */     if (isMateSearchingTime() && 
/* 113 */       this.player.passingMate != null && Assets.random.nextFloat() < Parameters.PASSING_PROBABILITY) {
/* 114 */       return this.fsm.statePassing;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 123 */     if (this.player.ball.isInsideDirectShotArea(-this.player.team.side) && this.player.seesTheGoal()) {
/* 124 */       float visualWidth = goalVisualWidth(this.player.x, this.player.y);
/* 125 */       float probabilityByVisualWidth = probabilityByVisualWidth(visualWidth, 180.0F);
/*     */       
/* 127 */       float distance = EMath.dist(this.player.x, this.player.y, (71 * EMath.sgn(this.player.x)), (640 * EMath.sgn(this.player.y)));
/* 128 */       float probabilityByDistance = probabilityByDistance(distance, 0.1F, 310.0F);
/*     */       
/* 130 */       float probability = (probabilityByVisualWidth + probabilityByDistance * probabilityByDistance) / 2.0F;
/* 131 */       GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Inside direct shot area, visualWidth: " + visualWidth + ", probabilityByVisualWidth: " + probabilityByVisualWidth + ", distance: " + distance + ", probabilityByDistance: " + probabilityByDistance + ", probability: " + probability);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 136 */       if (Assets.random.nextFloat() < probability) {
/* 137 */         return this.fsm.stateKicking;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 142 */     PlayerState playerState = this.player.getState();
/* 143 */     if (playerState != null && 
/* 144 */       !playerState.checkId(PlayerFsm.Id.STATE_STAND_RUN)) {
/* 145 */       return this.fsm.stateIdle;
/*     */     }
/*     */ 
/*     */     
/* 149 */     if (this.player.ball.owner != this.player) {
/* 150 */       return this.fsm.stateSeeking;
/*     */     }
/*     */     
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getUrgentAngleCorrection() {
/* 158 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "player.x: " + this.player.x + ", player.y: " + this.player.y + ", controlsAngle: " + this.controlsAngle);
/*     */ 
/*     */     
/* 161 */     Vector3 totalWeights = new Vector3(1.0F, 1.0F, 1.0F);
/*     */ 
/*     */     
/* 164 */     Vector3 inFieldMap = getInFieldMap();
/* 165 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "In field map: " + inFieldMap);
/*     */ 
/*     */     
/* 168 */     totalWeights.scl(inFieldMap);
/*     */     
/* 170 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "TotalWeights: " + totalWeights);
/*     */ 
/*     */ 
/*     */     
/* 174 */     if (totalWeights.isZero()) {
/* 175 */       return emergencyAngle();
/*     */     }
/*     */ 
/*     */     
/* 179 */     if (Math.abs(this.player.x) < 81.0F && 
/* 180 */       EMath.isIn(this.player.y, (-this.player.side * 572), (-this.player.side * 640)) && 
/* 181 */       !this.player.seesTheGoal()) {
/* 182 */       float signedAngleDiff = this.player.goalSignedAngleDiff();
/* 183 */       if (signedAngleDiff < -Const.SHOOTING_ANGLE_TOLERANCE) {
/* 184 */         GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Forcing left turn to see the goal");
/* 185 */         totalWeights.scl(2.0F, 0.0F, 0.0F);
/*     */       } 
/* 187 */       if (signedAngleDiff > Const.SHOOTING_ANGLE_TOLERANCE) {
/* 188 */         GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Forcing right turn to see the goal");
/* 189 */         totalWeights.scl(0.0F, 0.0F, 2.0F);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 194 */     if (totalWeights.x > Math.max(totalWeights.y, totalWeights.z)) {
/* 195 */       GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Turning left: -45");
/* 196 */       return -45.0F;
/* 197 */     }  if (totalWeights.y >= totalWeights.z) {
/* 198 */       GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "No turning: 0");
/* 199 */       return 0.0F;
/*     */     } 
/* 201 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Turning right: +45");
/* 202 */     return 45.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getAngleCorrection() {
/*     */     Vector3 goalWeights;
/*     */     float GOAL_FACTOR;
/* 209 */     Vector3 totalWeights = new Vector3();
/*     */ 
/*     */     
/* 212 */     Vector3 mateWeights = getMateWeights();
/* 213 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Mates weights: " + mateWeights);
/* 214 */     totalWeights.add(mateWeights.scl(Parameters.MATE_FACTOR));
/*     */ 
/*     */     
/* 217 */     Vector3 opponentWeights = getOpponentWeights();
/* 218 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Opponents weights: " + opponentWeights);
/* 219 */     totalWeights.add(opponentWeights.scl(Parameters.OPPONENT_FACTOR));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     if (this.player.ball.isInsidePenaltyArea(this.player.team.side)) {
/* 225 */       goalWeights = getOwnGoalWeights();
/* 226 */       GOAL_FACTOR = Parameters.OWN_GOAL_FACTOR;
/*     */     } else {
/* 228 */       goalWeights = getGoalWeights();
/* 229 */       if (this.player.ball.isInsidePenaltyArea(-this.player.side)) {
/* 230 */         GOAL_FACTOR = Parameters.GOAL_FACTOR_INSIDE_PENALTY_AREA;
/*     */       } else {
/* 232 */         GOAL_FACTOR = Parameters.GOAL_FACTOR;
/*     */       } 
/*     */     } 
/* 235 */     totalWeights.add(goalWeights.scl(GOAL_FACTOR));
/* 236 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Goal weights: " + goalWeights);
/*     */     
/* 238 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "TotalWeights: " + totalWeights);
/*     */ 
/*     */     
/* 241 */     if (totalWeights.x > Math.max(totalWeights.y, totalWeights.z)) {
/* 242 */       GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Turning left: -45");
/* 243 */       return -45.0F;
/* 244 */     }  if (totalWeights.y >= totalWeights.z) {
/* 245 */       GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "No turning: 0");
/* 246 */       return 0.0F;
/*     */     } 
/* 248 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Turning right: +45");
/* 249 */     return 45.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private Vector3 getMateWeights() {
/* 254 */     Vector3 weights = new Vector3();
/* 255 */     int count = 0;
/* 256 */     for (int i = 0; i < 11; i++) {
/* 257 */       Player ply = this.player.team.lineup.get(i);
/* 258 */       if (ply != this.player) {
/*     */         
/* 260 */         float dist = this.player.distanceFrom(ply);
/* 261 */         if (dist <= Parameters.PLAYER_DETECTION_RADIUS)
/*     */         
/* 263 */         { float minFrameDistance = EMath.min(new int[] { ply.frameDistanceL, ply.frameDistance, ply.frameDistanceR });
/*     */ 
/*     */           
/* 266 */           float w = weightByDistance(dist);
/* 267 */           if (ply.frameDistanceL == minFrameDistance) {
/* 268 */             weights.add(w, 0.0F, 0.0F);
/*     */           }
/* 270 */           if (ply.frameDistance == minFrameDistance) {
/* 271 */             weights.add(0.0F, w, 0.0F);
/*     */           }
/* 273 */           if (ply.frameDistanceR == minFrameDistance) {
/* 274 */             weights.add(0.0F, 0.0F, w);
/*     */           }
/*     */           
/* 277 */           count++; } 
/*     */       } 
/* 279 */     }  return (count == 0) ? weights.setZero() : weights.scl(1.0F / count);
/*     */   }
/*     */   
/*     */   private Vector3 getOpponentWeights() {
/* 283 */     Vector3 weights = new Vector3();
/* 284 */     Team opponentTeam = this.player.team.match.team[1 - this.player.team.index];
/* 285 */     int count = 0;
/* 286 */     for (int i = 0; i < 11; i++) {
/* 287 */       Player ply = opponentTeam.lineup.get(i);
/*     */       
/* 289 */       float dist = this.player.distanceFrom(ply);
/* 290 */       if (dist <= Parameters.PLAYER_DETECTION_RADIUS) {
/*     */         
/* 292 */         float maxFrameDistance = EMath.max(new float[] { ply.frameDistanceL, ply.frameDistance, ply.frameDistanceR });
/*     */ 
/*     */         
/* 295 */         float w = weightByDistance(dist);
/* 296 */         if (ply.frameDistanceL == maxFrameDistance) {
/* 297 */           weights.add(w, 0.0F, 0.0F);
/*     */         }
/* 299 */         if (ply.frameDistance == maxFrameDistance) {
/* 300 */           weights.add(0.0F, w, 0.0F);
/*     */         }
/* 302 */         if (ply.frameDistanceR == maxFrameDistance) {
/* 303 */           weights.add(0.0F, 0.0F, w);
/*     */         }
/*     */         
/* 306 */         count++;
/*     */       } 
/* 308 */     }  return (count == 0) ? weights.setZero() : weights.scl(1.0F / count);
/*     */   }
/*     */   
/*     */   private Vector3 getOwnGoalWeights() {
/* 312 */     Vector3 weights = new Vector3();
/* 313 */     float ownGoalToPlayerAngle = EMath.aTan2(this.player.y - (640 * this.player.team.side), this.player.x);
/* 314 */     weights.set(1.0F - 
/* 315 */         EMath.angleDiff(ownGoalToPlayerAngle, this.controlsAngle - 45.0F) / 360.0F, 1.0F - 
/* 316 */         EMath.angleDiff(ownGoalToPlayerAngle, this.controlsAngle) / 360.0F, 1.0F - 
/* 317 */         EMath.angleDiff(ownGoalToPlayerAngle, this.controlsAngle + 45.0F) / 360.0F);
/*     */     
/* 319 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "player.x: " + this.player.x + ", player.y: " + this.player.y + ", controlsAngle: " + this.controlsAngle + ", ownGoalToPlayerAngle: " + ownGoalToPlayerAngle);
/* 320 */     return weights;
/*     */   }
/*     */   
/*     */   private Vector3 getGoalWeights() {
/* 324 */     Vector3 weights = new Vector3();
/* 325 */     float playerToGoalAngle = EMath.aTan2((-582 * this.player.team.side) - this.player.y, -this.player.x);
/* 326 */     weights.set(1.0F - 
/* 327 */         EMath.angleDiff(playerToGoalAngle, this.controlsAngle - 45.0F) / 360.0F, 1.0F - 
/* 328 */         EMath.angleDiff(playerToGoalAngle, this.controlsAngle) / 360.0F, 1.0F - 
/* 329 */         EMath.angleDiff(playerToGoalAngle, this.controlsAngle + 45.0F) / 360.0F);
/*     */     
/* 331 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "player.x: " + this.player.x + ", player.y: " + this.player.y + ", controlsAngle: " + this.controlsAngle + ", playerToGoalAngle: " + playerToGoalAngle);
/* 332 */     return weights;
/*     */   }
/*     */ 
/*     */   
/*     */   private Vector3 getInFieldMap() {
/* 337 */     boolean includeGoal = Const.isInsideGoalArea(this.player.x, this.player.y, -this.player.side);
/*     */     
/* 339 */     float leftX = this.player.x + Parameters.OUTSIDE_DETECTION_RADIUS * EMath.cos(this.controlsAngle - 45.0F);
/* 340 */     float leftY = this.player.y + Parameters.OUTSIDE_DETECTION_RADIUS * EMath.sin(this.controlsAngle - 45.0F);
/* 341 */     boolean left = insideField(leftX, leftY, includeGoal);
/*     */     
/* 343 */     float centerX = this.player.x + Parameters.OUTSIDE_DETECTION_RADIUS * EMath.cos(this.controlsAngle);
/* 344 */     float centerY = this.player.y + Parameters.OUTSIDE_DETECTION_RADIUS * EMath.sin(this.controlsAngle);
/* 345 */     boolean center = insideField(centerX, centerY, includeGoal);
/*     */     
/* 347 */     float rightX = this.player.x + Parameters.OUTSIDE_DETECTION_RADIUS * EMath.cos(this.controlsAngle + 45.0F);
/* 348 */     float rightY = this.player.y + Parameters.OUTSIDE_DETECTION_RADIUS * EMath.sin(this.controlsAngle + 45.0F);
/* 349 */     boolean right = insideField(rightX, rightY, includeGoal);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 354 */     Vector3 map = new Vector3(left ? 1.0F : 0.0F, (center && left && right) ? 1.0F : 0.0F, right ? 1.0F : 0.0F);
/*     */ 
/*     */ 
/*     */     
/* 358 */     if (map.len2() == 2.0F && Const.isInsideGoalArea(this.player.x, this.player.y, this.player.side)) {
/* 359 */       if (EMath.dist(0.0F, (this.player.side * 640), leftX, leftY) < EMath.dist(0.0F, (this.player.side * 640), rightX, rightY)) {
/* 360 */         map.x = 0.0F;
/*     */       } else {
/* 362 */         map.z = 0.0F;
/*     */       } 
/*     */     }
/*     */     
/* 366 */     return map;
/*     */   }
/*     */   
/*     */   private boolean insideField(float x, float y, boolean includeGoal) {
/* 370 */     if (Math.abs(x) < 510.0F && Math.abs(y) < 640.0F) {
/* 371 */       return true;
/*     */     }
/*     */     
/* 374 */     return (includeGoal && Math.abs(x) < 71.0F && Math.abs(y) < (640 + Parameters.OUTSIDE_DETECTION_RADIUS));
/*     */   }
/*     */   
/*     */   private float weightByDistance(float dist) {
/* 378 */     return EMath.pow(dist / Parameters.PLAYER_DETECTION_RADIUS, 2.0F) - 2.0F * dist / Parameters.PLAYER_DETECTION_RADIUS + 1.0F;
/*     */   }
/*     */   
/*     */   private float emergencyAngle() {
/* 382 */     float playerToCenterAngle = this.player.angleToPoint(0.0F, 0.0F);
/* 383 */     float angle = 90.0F * Math.signum(EMath.signedAngleDiff(this.controlsAngle, playerToCenterAngle));
/*     */     
/* 385 */     GLGame.debug(GLGame.LogType.AI_ATTACKING, this.player.numberName(), "Emergency turn by: " + angle);
/* 386 */     return angle;
/*     */   }
/*     */   
/*     */   private float probabilityByVisualWidth(float value, float maxValue) {
/* 390 */     return -value * value / maxValue * maxValue + 2.0F * value / maxValue;
/*     */   }
/*     */   
/*     */   private float probabilityByDistance(float value, float minProb, float maxValue) {
/* 394 */     return (minProb - 1.0F) * value * value / maxValue * maxValue + 1.0F;
/*     */   }
/*     */   
/*     */   private float goalVisualWidth(float x, float y) {
/* 398 */     int side = EMath.sgn(y);
/* 399 */     float angle1 = EMath.angle(x, y, -71.0F, (side * 640));
/* 400 */     float angle2 = EMath.angle(x, y, 71.0F, (side * 640));
/* 401 */     return EMath.angleDiff(angle1, angle2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateAttacking.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */