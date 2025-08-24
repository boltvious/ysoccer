/*      */ package com.ygames.ysoccer.match;
/*      */ 
/*      */ import com.badlogic.gdx.Gdx;
/*      */ import com.badlogic.gdx.audio.Sound;
/*      */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*      */ import com.badlogic.gdx.math.Vector2;
/*      */ import com.badlogic.gdx.utils.Json;
/*      */ import com.badlogic.gdx.utils.JsonValue;
/*      */ import com.ygames.ysoccer.framework.Ai;
/*      */ import com.ygames.ysoccer.framework.Assets;
/*      */ import com.ygames.ysoccer.framework.Color2;
/*      */ import com.ygames.ysoccer.framework.Color3;
/*      */ import com.ygames.ysoccer.framework.EMath;
/*      */ import com.ygames.ysoccer.framework.GLColor;
/*      */ import com.ygames.ysoccer.framework.GLGame;
/*      */ import com.ygames.ysoccer.framework.InputDevice;
/*      */ import com.ygames.ysoccer.framework.RgbPair;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Player
/*      */   implements Json.Serializable
/*      */ {
/*      */   public enum Role
/*      */   {
/*   33 */     GOALKEEPER, RIGHT_BACK, LEFT_BACK, DEFENDER, RIGHT_WINGER, LEFT_WINGER, MIDFIELDER, ATTACKER; }
/*      */   
/*   35 */   public enum Skill { PASSING, SHOOTING, HEADING, TACKLING, CONTROL, SPEED, FINISHING; }
/*      */   
/*   37 */   private static final String[] roleLabels = new String[] { "ROLES.GOALKEEPER", "ROLES.RIGHT_BACK", "ROLES.LEFT_BACK", "ROLES.DEFENDER", "ROLES.RIGHT_WINGER", "ROLES.LEFT_WINGER", "ROLES.MIDFIELDER", "ROLES.ATTACKER" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   48 */   private static final String[] skillLabels = new String[] { "SKILLS.PASSING", "SKILLS.SHOOTING", "SKILLS.HEADING", "SKILLS.TACKLING", "SKILLS.CONTROL", "SKILLS.SPEED", "SKILLS.FINISHING" };
/*      */   
/*      */   public String name;
/*      */   
/*      */   public String shirtName;
/*      */   
/*      */   public Team team;
/*      */   public String nationality;
/*      */   int index;
/*      */   public Role role;
/*   58 */   private static final int[] stars = new int[] { 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9 };
/*      */   public int number;
/*      */   public Skin.Color skinColor;
/*      */   public Hair.Color hairColor;
/*      */   public String hairStyle;
/*      */   public Hair hair;
/*      */   public Skills skills;
/*      */   
/*   66 */   enum KeeperCollision { NONE, REBOUND, CATCH, DEFLECT; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   82 */   public final List<Skill> bestSkills = new ArrayList<>();
/*      */   
/*      */   public int value;
/*      */   
/*      */   public InputDevice inputDevice;
/*      */   
/*      */   public Ai ai;
/*      */   
/*      */   int side;
/*      */   
/*      */   float kickAngle;
/*      */   
/*      */   Player passingMate;
/*      */   
/*      */   float passingMateAngleCorrection;
/*      */   Scene scene;
/*      */   Ball ball;
/*      */   public PlayerFsm fsm;
/*      */   float speed;
/*      */   public boolean isVisible;
/*  102 */   public final Data[] data = new Data[4096];
/*      */   
/*      */   public float x;
/*      */   
/*      */   public float y;
/*      */   
/*      */   float z;
/*      */   
/*      */   float x0;
/*      */   
/*      */   float y0;
/*      */   
/*      */   float z0;
/*      */   
/*      */   float v;
/*      */   
/*      */   float vz;
/*      */   public float a;
/*      */   float thrustX;
/*      */   float thrustZ;
/*      */   float tx;
/*      */   float ty;
/*      */   public float fmx;
/*      */   public float fmy;
/*      */   private float fmySweep;
/*      */   float ballDistance;
/*      */   float ballAngle;
/*      */   int frameDistanceL;
/*      */   int frameDistance;
/*      */   int frameDistanceR;
/*      */   
/*      */   public Player() {
/*  134 */     this.skills = new Skills();
/*  135 */     setAi(new Ai(this));
/*  136 */     setInputDevice((InputDevice)this.ai);
/*      */   }
/*      */ 
/*      */   
/*      */   public void read(Json json, JsonValue jsonData) {
/*  141 */     json.readFields(this, jsonData);
/*      */   }
/*      */ 
/*      */   
/*      */   public void write(Json json) {
/*  146 */     json.writeValue("name", this.name);
/*  147 */     json.writeValue("shirtName", this.shirtName);
/*  148 */     json.writeValue("nationality", this.nationality);
/*  149 */     json.writeValue("role", this.role);
/*  150 */     json.writeValue("number", Integer.valueOf(this.number));
/*  151 */     json.writeValue("skinColor", this.skinColor);
/*  152 */     json.writeValue("hairColor", this.hairColor);
/*  153 */     json.writeValue("hairStyle", this.hairStyle);
/*  154 */     if (this.role == Role.GOALKEEPER) {
/*  155 */       json.writeValue("value", Integer.valueOf(this.value));
/*      */     } else {
/*  157 */       json.writeValue("skills", this.skills);
/*  158 */       json.writeValue("bestSkills", this.bestSkills, Skill[].class, Skill.class);
/*      */     } 
/*      */   }
/*      */   
/*      */   public Match getMatch() {
/*  163 */     return (this.scene.getClass() == Match.class) ? (Match)this.scene : null;
/*      */   }
/*      */   
/*      */   void beforeMatch(Match match) {
/*  167 */     this.scene = match;
/*  168 */     this.ball = match.ball;
/*  169 */     this.fsm = new PlayerFsm(this);
/*  170 */     this.isVisible = true;
/*  171 */     for (int i = 0; i < this.data.length; i++) {
/*  172 */       this.data[i] = new Data();
/*      */     }
/*      */   }
/*      */   
/*      */   void beforeTraining(Training training) {
/*  177 */     this.scene = training;
/*  178 */     this.ball = training.ball;
/*  179 */     this.fsm = new PlayerFsm(this);
/*  180 */     this.isVisible = true;
/*  181 */     for (int i = 0; i < this.data.length; i++) {
/*  182 */       this.data[i] = new Data();
/*      */     }
/*      */   }
/*      */   
/*      */   void setTarget(float tx, float ty) {
/*  187 */     this.tx = tx;
/*  188 */     this.ty = ty;
/*      */   }
/*      */   
/*      */   public PlayerState getState() {
/*  192 */     return this.fsm.getState();
/*      */   }
/*      */   
/*      */   public void setState(PlayerFsm.Id id) {
/*  196 */     this.fsm.setState(id);
/*      */   }
/*      */   
/*      */   boolean checkState(PlayerFsm.Id id) {
/*  200 */     return (this.fsm.state != null && this.fsm.getState().checkId(id));
/*      */   }
/*      */   
/*      */   boolean checkStates(PlayerFsm.Id... ids) {
/*  204 */     if (this.fsm.state == null) return false;
/*      */     
/*  206 */     PlayerState state = this.fsm.getState();
/*  207 */     for (PlayerFsm.Id id : ids) {
/*  208 */       if (state.checkId(id)) return true; 
/*      */     } 
/*  210 */     return false;
/*      */   }
/*      */   
/*      */   void think() {
/*  214 */     this.fsm.think();
/*      */   }
/*      */   
/*      */   public void setInputDevice(InputDevice inputDevice) {
/*  218 */     this.inputDevice = inputDevice;
/*      */   }
/*      */   
/*      */   boolean isAiControlled() {
/*  222 */     return (this.inputDevice == this.ai);
/*      */   }
/*      */   
/*      */   void setAi(Ai ai) {
/*  226 */     this.ai = ai;
/*      */   }
/*      */   
/*      */   void updateAi() {
/*  230 */     this.ai.update();
/*      */   }
/*      */   
/*      */   void animationStandRun() {
/*  234 */     this.fmx = (Math.round((this.a + 360.0F) % 360.0F / 45.0F) % 8);
/*  235 */     if (this.v > 0.0F) {
/*  236 */       this.fmySweep = (this.fmySweep + Const.PLAYER_RUN_ANIMATION * this.v / 1000.0F) % 4.0F;
/*  237 */       if (this.fmySweep > 3.0F) {
/*  238 */         this.fmy = this.fmySweep - 2.0F;
/*      */       } else {
/*  240 */         this.fmy = this.fmySweep;
/*      */       } 
/*      */     } else {
/*  243 */       this.fmy = 1.0F;
/*      */     } 
/*      */   }
/*      */   
/*      */   void animationScorer() {
/*  248 */     this.fmx = (Math.round((this.a + 360.0F) % 360.0F / 45.0F) % 8);
/*  249 */     if (this.v > 0.0F) {
/*  250 */       this.fmySweep = (this.fmySweep + Const.PLAYER_RUN_ANIMATION * this.v / 1000.0F) % 4.0F;
/*  251 */       if (this.fmySweep > 3.0F) {
/*  252 */         this.fmy = 12.0F;
/*      */       } else {
/*  254 */         this.fmy = 11.0F + this.fmySweep;
/*      */       } 
/*      */     } else {
/*  257 */       this.fmy = 1.0F;
/*      */     } 
/*      */   }
/*      */   
/*      */   void updateFrameDistance() {
/*  262 */     this.frameDistanceL = 128;
/*  263 */     this.frameDistance = 128;
/*  264 */     this.frameDistanceR = 128;
/*      */ 
/*      */     
/*  267 */     for (int f = 127; f >= 0; f--) {
/*  268 */       float dist = EMath.dist(this.x, this.y, (this.ball.predictionL[f]).x, (this.ball.predictionL[f]).y);
/*  269 */       if (dist < this.speed * f / 64.0F) {
/*  270 */         this.frameDistanceL = f;
/*      */       }
/*      */       
/*  273 */       dist = EMath.dist(this.x, this.y, (this.ball.prediction[f]).x, (this.ball.prediction[f]).y);
/*  274 */       if (dist < this.speed * f / 64.0F) {
/*  275 */         this.frameDistance = f;
/*      */       }
/*      */       
/*  278 */       dist = EMath.dist(this.x, this.y, (this.ball.predictionR[f]).x, (this.ball.predictionR[f]).y);
/*  279 */       if (dist < this.speed * f / 64.0F) {
/*  280 */         this.frameDistanceR = f;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void getPossession() {
/*  288 */     float focalPoint1x = this.x + 6.5F * EMath.cos(this.a + 75.0F);
/*  289 */     float focalPoint1y = this.y + 6.5F * EMath.sin(this.a + 75.0F);
/*  290 */     float focalPoint2x = this.x + 6.5F * EMath.cos(this.a - 75.0F);
/*  291 */     float focalPoint2y = this.y + 6.5F * EMath.sin(this.a - 75.0F);
/*      */     
/*  293 */     boolean inEllipse = (EMath.dist(this.ball.x, this.ball.y, focalPoint1x, focalPoint1y) + EMath.dist(this.ball.x, this.ball.y, focalPoint2x, focalPoint2y) < 14.0F);
/*      */     
/*  295 */     if (inEllipse && this.ball.z < 22.0F) {
/*      */       
/*  297 */       float smoothedBallV = this.ball.v * 0.5F;
/*  298 */       Vector2 ballVec = new Vector2(smoothedBallV, 0.0F);
/*  299 */       ballVec.setAngle(this.ball.a);
/*  300 */       Vector2 playerVec = new Vector2(this.v, 0.0F);
/*  301 */       playerVec.setAngle(this.a);
/*      */       
/*  303 */       Vector2 differenceVec = playerVec.sub(ballVec);
/*      */       
/*  305 */       if (differenceVec.len() < (320 + 10 * this.skills.control)) {
/*      */ 
/*      */         
/*  308 */         if (this.ball.owner != null && this.ball.owner != this)
/*      */         {
/*  310 */           float sum = (this.skills.tackling + this.ball.owner.skills.tackling + 2);
/*  311 */           float r = Assets.random.nextFloat();
/*      */ 
/*      */           
/*  314 */           if (r < (this.skills.tackling + 1) / sum) {
/*  315 */             this.scene.setBallOwner(this);
/*  316 */             this.ball.v = this.v;
/*  317 */             this.ball.a = this.a;
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  322 */             setState(PlayerFsm.Id.STATE_NOT_RESPONSIVE);
/*      */           }
/*      */         
/*      */         }
/*      */         else
/*      */         {
/*  328 */           this.scene.setBallOwner(this);
/*  329 */           this.ball.v = this.v;
/*  330 */           this.ball.a = this.a;
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  337 */         this.scene.setBallOwner(this);
/*  338 */         this.scene.setBallOwner(null);
/*  339 */         this.ball.collisionPlayer(this);
/*      */       } 
/*      */       
/*  342 */       this.ball.vz /= (2 + this.skills.control);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   void ballCollision() {
/*  349 */     float focalPoint1x = this.x + 5.0F * EMath.cos(this.a + 90.0F);
/*  350 */     float focalPoint1y = this.y + 5.0F * EMath.sin(this.a + 90.0F);
/*  351 */     float focalPoint2x = this.x + 5.0F * EMath.cos(this.a - 90.0F);
/*  352 */     float focalPoint2y = this.y + 5.0F * EMath.sin(this.a - 90.0F);
/*      */     
/*  354 */     boolean inEllipse = (EMath.dist(this.ball.x, this.ball.y, focalPoint1x, focalPoint1y) + EMath.dist(this.ball.x, this.ball.y, focalPoint2x, focalPoint2y) < 14.0F);
/*      */     
/*  356 */     if (inEllipse && this.ball.z < 22.0F) {
/*  357 */       this.scene.setBallOwner(this);
/*  358 */       this.scene.setBallOwner(null);
/*  359 */       this.ball.collisionPlayer(this);
/*      */     } 
/*      */   }
/*      */   
/*      */   boolean ballIsApproaching() {
/*  364 */     return (EMath.dist(this.x0, this.y0, this.ball.x0, this.ball.y0) > this.ballDistance);
/*      */   }
/*      */   
/*      */   boolean ballIsInFront() {
/*  368 */     return (EMath.angleDiff(this.ballAngle, this.a) < 90.0F);
/*      */   }
/*      */   
/*      */   boolean keeperCollision() {
/*  372 */     KeeperCollision collisionType = KeeperCollision.NONE;
/*      */     
/*  374 */     if (Math.abs(this.ball.y0 - this.y) >= 1.0F && Math.abs(this.ball.y - this.y) < 1.0F) {
/*      */       float ballAxy, ballVx, ballVy;
/*      */ 
/*      */ 
/*      */       
/*  379 */       int fmx = Math.round(this.fmx);
/*  380 */       int fmy = Math.abs((int)Math.floor(this.fmy));
/*      */ 
/*      */       
/*  383 */       int originX = Assets.keeperOrigins[fmy][fmx][0].intValue() + Math.round(this.ball.x - this.x);
/*  384 */       int originY = Assets.keeperOrigins[fmy][fmx][1].intValue() + Math.round(-this.ball.z - 4.0F + this.z);
/*      */ 
/*      */       
/*  387 */       if (originX < 0 || originX >= 50) {
/*  388 */         return false;
/*      */       }
/*  390 */       if (originY < 0 || originY >= 50) {
/*  391 */         return false;
/*      */       }
/*      */       
/*  394 */       int det_x = Math.round((50 * fmx % 24 + originX));
/*  395 */       int det_y = Math.round((50 * fmy % 24 + originY));
/*      */       
/*  397 */       int rgb = Assets.keeperCollisionDetection.getPixel(det_x, det_y) >>> 8;
/*      */       
/*  399 */       switch (rgb) {
/*      */         case 12632064:
/*  401 */           collisionType = KeeperCollision.REBOUND;
/*      */           break;
/*      */         
/*      */         case 12582912:
/*  405 */           collisionType = KeeperCollision.CATCH;
/*      */           break;
/*      */         
/*      */         case 192:
/*  409 */           collisionType = KeeperCollision.DEFLECT;
/*      */           break;
/*      */       } 
/*      */       
/*  413 */       Gdx.app.debug("Keeper collision", collisionType + " (" + GLColor.toHexString(rgb) + ") at " + det_x + ", " + det_y);
/*      */       
/*  415 */       switch (collisionType) {
/*      */         case PASSING:
/*  417 */           if (this.ball.v > 180.0F) {
/*  418 */             Assets.Sounds.deflect.play(0.5F * Assets.Sounds.volume / 100.0F);
/*      */           }
/*      */           
/*  421 */           ballAxy = EMath.aTan2(this.ball.y - this.ball.y0, this.ball.x - this.ball.x0);
/*      */           
/*  423 */           ballVx = this.ball.v * EMath.cos(ballAxy);
/*  424 */           ballVy = this.ball.v * EMath.sin(ballAxy);
/*      */ 
/*      */ 
/*      */           
/*  428 */           ballVx = Math.signum(EMath.cos(this.a)) * (0.2F * Math.abs(ballVx) + 0.55F * Math.abs(ballVy)) + Math.min(100.0F, this.v) * EMath.cos(this.a);
/*  429 */           ballVy = -EMath.rand(5, 20) * 0.01F * ballVy;
/*      */           
/*  431 */           this.ball.v = (float)Math.sqrt((ballVx * ballVx + ballVy * ballVy));
/*  432 */           this.ball.vz = 2.0F * this.vz;
/*  433 */           this.ball.a = EMath.aTan2(ballVy, ballVx);
/*  434 */           this.ball.s = -this.ball.s;
/*      */           
/*  436 */           this.scene.setBallOwner(this, false);
/*  437 */           this.scene.setBallOwner(null);
/*      */           break;
/*      */         
/*      */         case SHOOTING:
/*  441 */           if (this.ball.v > 180.0F) {
/*  442 */             Assets.Sounds.hold.play(0.5F * Assets.Sounds.volume / 100.0F);
/*      */           }
/*  444 */           this.ball.v = 0.0F;
/*  445 */           this.ball.vz = 0.0F;
/*  446 */           this.ball.s = 0.0F;
/*      */           
/*  448 */           this.scene.setBallOwner(this);
/*  449 */           this.ball.setHolder(this);
/*      */           break;
/*      */         
/*      */         case HEADING:
/*  453 */           if (this.ball.v > 180.0F) {
/*  454 */             Assets.Sounds.deflect.play(0.5F * Assets.Sounds.volume / 100.0F);
/*      */           }
/*      */           
/*  457 */           ballAxy = EMath.aTan2(this.ball.y - this.ball.y0, this.ball.x - this.ball.x0);
/*      */           
/*  459 */           ballVx = this.ball.v * EMath.cos(ballAxy);
/*  460 */           ballVy = this.ball.v * EMath.sin(ballAxy);
/*      */ 
/*      */ 
/*      */           
/*  464 */           ballVx = Math.signum(EMath.cos(this.a)) * (0.5F * Math.abs(ballVx) + 0.25F * Math.abs(ballVy)) + Math.min(100.0F, this.v) * EMath.cos(this.a);
/*  465 */           ballVy = 0.7F * ballVy;
/*      */           
/*  467 */           this.ball.v = (float)Math.sqrt((ballVx * ballVx + ballVy * ballVy));
/*  468 */           this.ball.vz = 1.5F * this.vz;
/*  469 */           this.ball.a = EMath.aTan2(ballVy, ballVx);
/*      */           
/*  471 */           this.scene.setBallOwner(this, false);
/*  472 */           this.scene.setBallOwner(null);
/*      */           break;
/*      */       } 
/*      */     
/*      */     } 
/*  477 */     if (collisionType != KeeperCollision.NONE && getMatch() != null) {
/*  478 */       ((getMatch()).stats[1 - this.team.index]).overallShots++;
/*  479 */       ((getMatch()).stats[1 - this.team.index]).centeredShots++;
/*      */     } 
/*      */     
/*  482 */     if (collisionType == KeeperCollision.CATCH && 
/*  483 */       this.scene.settings.commentary) {
/*  484 */       int size = Assets.Commentary.keeperSave.size();
/*  485 */       if (size > 0) {
/*  486 */         ((Sound)Assets.Commentary.keeperSave.get(Assets.random.nextInt(size))).play(Assets.Sounds.volume / 100.0F);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  491 */     return (collisionType == KeeperCollision.CATCH);
/*      */   }
/*      */   
/*      */   void holdBall(int offX, int offZ) {
/*  495 */     if (this.ball.holder == this) {
/*  496 */       this.ball.setX(this.x + offX);
/*  497 */       this.ball.setY(this.y);
/*  498 */       this.ball.setZ(this.z + offZ);
/*  499 */       this.ball.v = this.v;
/*  500 */       this.ball.vz = this.vz;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void save(int subframe) {
/*  505 */     (this.data[subframe]).x = Math.round(this.x);
/*  506 */     (this.data[subframe]).y = Math.round(this.y);
/*  507 */     (this.data[subframe]).z = Math.round(this.z);
/*  508 */     (this.data[subframe]).fmx = Math.round(this.fmx);
/*  509 */     (this.data[subframe]).fmy = (int)Math.abs(Math.floor(this.fmy));
/*  510 */     (this.data[subframe]).isVisible = this.isVisible;
/*  511 */     (this.data[subframe]).isHumanControlled = (this.inputDevice != this.ai);
/*  512 */     (this.data[subframe]).isBestDefender = (this.team.bestDefender == this);
/*  513 */     (this.data[subframe]).frameDistance = this.frameDistance;
/*  514 */     (this.data[subframe]).playerState = (this.fsm == null) ? -1 : this.fsm.state.id;
/*  515 */     (this.data[subframe]).playerAiState = (this.inputDevice == this.ai) ? this.ai.fsm.state.id : -1;
/*      */   }
/*      */   
/*      */   float targetDistance() {
/*  519 */     return EMath.dist(this.tx, this.ty, this.x, this.y);
/*      */   }
/*      */   
/*      */   float targetAngle() {
/*  523 */     return EMath.aTan2(this.ty - this.y, this.tx - this.x);
/*      */   }
/*      */   
/*      */   void watchPosition(Vector2 pos) {
/*  527 */     this.a = EMath.roundBy(EMath.aTan2(this.y - pos.y, this.x - pos.x) + 180.0F, 45.0F);
/*      */   }
/*      */   
/*      */   void watchPosition(float x0, float y0) {
/*  531 */     this.a = EMath.roundBy(EMath.aTan2(this.y - y0, this.x - x0) + 180.0F, 45.0F);
/*      */   }
/*      */   
/*      */   public String getRoleLabel() {
/*  535 */     return roleLabels[this.role.ordinal()];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Skill[] getOrderedSkills() {
/*  541 */     if (this.role == Role.GOALKEEPER) {
/*  542 */       return null;
/*      */     }
/*      */     
/*  545 */     Skill[] orderedSkills = new Skill[7];
/*      */ 
/*      */     
/*  548 */     int[] skill = new int[7];
/*  549 */     if (this.role == Role.RIGHT_BACK || this.role == Role.LEFT_BACK) {
/*  550 */       skill[0] = this.skills.tackling;
/*  551 */       skill[1] = this.skills.speed;
/*  552 */       skill[2] = this.skills.passing;
/*  553 */       skill[3] = this.skills.shooting;
/*  554 */       skill[4] = this.skills.heading;
/*  555 */       skill[5] = this.skills.control;
/*  556 */       skill[6] = this.skills.finishing;
/*  557 */       orderedSkills[0] = Skill.TACKLING;
/*  558 */       orderedSkills[1] = Skill.SPEED;
/*  559 */       orderedSkills[2] = Skill.PASSING;
/*  560 */       orderedSkills[3] = Skill.SHOOTING;
/*  561 */       orderedSkills[4] = Skill.HEADING;
/*  562 */       orderedSkills[5] = Skill.CONTROL;
/*  563 */       orderedSkills[6] = Skill.FINISHING;
/*      */     }
/*  565 */     else if (this.role == Role.DEFENDER) {
/*  566 */       skill[0] = this.skills.tackling;
/*  567 */       skill[1] = this.skills.heading;
/*  568 */       skill[2] = this.skills.passing;
/*  569 */       skill[3] = this.skills.speed;
/*  570 */       skill[4] = this.skills.shooting;
/*  571 */       skill[5] = this.skills.control;
/*  572 */       skill[6] = this.skills.finishing;
/*  573 */       orderedSkills[0] = Skill.TACKLING;
/*  574 */       orderedSkills[1] = Skill.HEADING;
/*  575 */       orderedSkills[2] = Skill.PASSING;
/*  576 */       orderedSkills[3] = Skill.SPEED;
/*  577 */       orderedSkills[4] = Skill.SHOOTING;
/*  578 */       orderedSkills[5] = Skill.CONTROL;
/*  579 */       orderedSkills[6] = Skill.FINISHING;
/*      */     }
/*  581 */     else if (this.role == Role.RIGHT_WINGER || this.role == Role.LEFT_WINGER) {
/*  582 */       skill[0] = this.skills.control;
/*  583 */       skill[1] = this.skills.speed;
/*  584 */       skill[2] = this.skills.passing;
/*  585 */       skill[3] = this.skills.tackling;
/*  586 */       skill[4] = this.skills.heading;
/*  587 */       skill[5] = this.skills.finishing;
/*  588 */       skill[6] = this.skills.shooting;
/*  589 */       orderedSkills[0] = Skill.CONTROL;
/*  590 */       orderedSkills[1] = Skill.SPEED;
/*  591 */       orderedSkills[2] = Skill.PASSING;
/*  592 */       orderedSkills[3] = Skill.TACKLING;
/*  593 */       orderedSkills[4] = Skill.HEADING;
/*  594 */       orderedSkills[5] = Skill.FINISHING;
/*  595 */       orderedSkills[6] = Skill.SHOOTING;
/*      */     }
/*  597 */     else if (this.role == Role.MIDFIELDER) {
/*  598 */       skill[0] = this.skills.passing;
/*  599 */       skill[1] = this.skills.tackling;
/*  600 */       skill[2] = this.skills.control;
/*  601 */       skill[3] = this.skills.heading;
/*  602 */       skill[4] = this.skills.shooting;
/*  603 */       skill[5] = this.skills.speed;
/*  604 */       skill[6] = this.skills.finishing;
/*  605 */       orderedSkills[0] = Skill.PASSING;
/*  606 */       orderedSkills[1] = Skill.TACKLING;
/*  607 */       orderedSkills[2] = Skill.CONTROL;
/*  608 */       orderedSkills[3] = Skill.HEADING;
/*  609 */       orderedSkills[4] = Skill.SHOOTING;
/*  610 */       orderedSkills[5] = Skill.SPEED;
/*  611 */       orderedSkills[6] = Skill.FINISHING;
/*      */     }
/*  613 */     else if (this.role == Role.ATTACKER) {
/*  614 */       skill[0] = this.skills.heading;
/*  615 */       skill[1] = this.skills.finishing;
/*  616 */       skill[2] = this.skills.speed;
/*  617 */       skill[3] = this.skills.shooting;
/*  618 */       skill[4] = this.skills.control;
/*  619 */       skill[5] = this.skills.passing;
/*  620 */       skill[6] = this.skills.tackling;
/*  621 */       orderedSkills[0] = Skill.HEADING;
/*  622 */       orderedSkills[1] = Skill.FINISHING;
/*  623 */       orderedSkills[2] = Skill.SPEED;
/*  624 */       orderedSkills[3] = Skill.SHOOTING;
/*  625 */       orderedSkills[4] = Skill.CONTROL;
/*  626 */       orderedSkills[5] = Skill.PASSING;
/*  627 */       orderedSkills[6] = Skill.TACKLING;
/*      */     } 
/*      */     
/*  630 */     boolean ordered = false;
/*  631 */     while (!ordered) {
/*  632 */       ordered = true;
/*  633 */       for (int i = 0; i < 6; i++) {
/*  634 */         if (skill[i] < skill[i + 1]) {
/*  635 */           int tmp = skill[i];
/*  636 */           skill[i] = skill[i + 1];
/*  637 */           skill[i + 1] = tmp;
/*      */           
/*  639 */           Skill s = orderedSkills[i];
/*  640 */           orderedSkills[i] = orderedSkills[i + 1];
/*  641 */           orderedSkills[i + 1] = s;
/*      */           
/*  643 */           ordered = false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  648 */     return orderedSkills;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean update(boolean limit) {
/*  656 */     this.speed = (130 + 4 * this.skills.speed);
/*      */ 
/*      */     
/*  659 */     this.x0 = this.x;
/*  660 */     this.y0 = this.y;
/*  661 */     this.z0 = this.z;
/*      */ 
/*      */     
/*  664 */     this.x += this.v / 512.0F * EMath.cos(this.a);
/*  665 */     this.y += this.v / 512.0F * EMath.sin(this.a);
/*  666 */     this.z += this.vz / 512.0F;
/*      */ 
/*      */     
/*  669 */     if (this.z > 0.0F) {
/*  670 */       this.vz -= Const.GRAVITY;
/*      */     }
/*      */ 
/*      */     
/*  674 */     if (this.z < 0.0F) {
/*  675 */       this.z = 0.0F;
/*  676 */       this.vz = 0.0F;
/*      */     } 
/*      */     
/*  679 */     if (limit) {
/*  680 */       limitInsideField();
/*      */     }
/*      */     
/*  683 */     this.ballDistance = EMath.dist(this.x, this.y, this.ball.x, this.ball.y);
/*  684 */     this.ballAngle = EMath.aTan2(this.ball.y - this.y, this.ball.x - this.x);
/*      */     
/*  686 */     return (this.v > 0.0F || this.vz != 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   private void limitInsideField() {
/*  691 */     this.x = Math.max(this.x, -560.0F);
/*      */     
/*  693 */     this.x = Math.min(this.x, 560.0F);
/*      */     
/*  695 */     if (Math.abs(this.x) > 81.0F) {
/*      */       
/*  697 */       this.y = Math.max(this.y, -690.0F);
/*      */       
/*  699 */       this.y = Math.min(this.y, 690.0F);
/*      */     } else {
/*      */       
/*  702 */       this.y = Math.max(this.y, -640.0F);
/*      */       
/*  704 */       this.y = Math.min(this.y, 640.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   int getDefenseRating() {
/*  709 */     switch (this.role) {
/*      */       case PASSING:
/*      */       case SHOOTING:
/*      */       case HEADING:
/*      */       case TACKLING:
/*  714 */         return this.skills.tackling + this.skills.heading + this.skills.passing + this.skills.speed + this.skills.control;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  721 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   int getOffenseRating() {
/*  726 */     switch (this.role) {
/*      */       case TACKLING:
/*      */       case CONTROL:
/*      */       case SPEED:
/*      */       case FINISHING:
/*  731 */         return this.skills.heading + this.skills.finishing + this.skills.speed + this.skills.shooting + this.skills.control;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  737 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void copyFrom(Player player) {
/*  742 */     this.name = player.name;
/*  743 */     this.shirtName = player.shirtName;
/*  744 */     this.nationality = player.nationality;
/*  745 */     this.role = player.role;
/*  746 */     this.value = player.value;
/*      */     
/*  748 */     this.hairColor = player.hairColor;
/*  749 */     this.hairStyle = player.hairStyle;
/*  750 */     this.skinColor = player.skinColor;
/*      */     
/*  752 */     this.skills.passing = player.skills.passing;
/*  753 */     this.skills.shooting = player.skills.shooting;
/*  754 */     this.skills.heading = player.skills.heading;
/*  755 */     this.skills.tackling = player.skills.tackling;
/*  756 */     this.skills.control = player.skills.control;
/*  757 */     this.skills.speed = player.skills.speed;
/*  758 */     this.skills.finishing = player.skills.finishing;
/*      */     
/*  760 */     this.bestSkills.clear();
/*  761 */     this.bestSkills.addAll(player.bestSkills);
/*      */   }
/*      */   
/*      */   public int getSkillValue(Skill skill) {
/*  765 */     int value = 0;
/*  766 */     switch (skill) {
/*      */       case PASSING:
/*  768 */         value = this.skills.passing;
/*      */         break;
/*      */       
/*      */       case SHOOTING:
/*  772 */         value = this.skills.shooting;
/*      */         break;
/*      */       
/*      */       case HEADING:
/*  776 */         value = this.skills.heading;
/*      */         break;
/*      */       
/*      */       case TACKLING:
/*  780 */         value = this.skills.tackling;
/*      */         break;
/*      */       
/*      */       case CONTROL:
/*  784 */         value = this.skills.control;
/*      */         break;
/*      */       
/*      */       case SPEED:
/*  788 */         value = this.skills.speed;
/*      */         break;
/*      */       
/*      */       case FINISHING:
/*  792 */         value = this.skills.finishing;
/*      */         break;
/*      */     } 
/*  795 */     return value;
/*      */   }
/*      */   
/*      */   public void setSkillValue(Skill skill, int value) {
/*  799 */     switch (skill) {
/*      */       case PASSING:
/*  801 */         this.skills.passing = value;
/*      */         break;
/*      */       
/*      */       case SHOOTING:
/*  805 */         this.skills.shooting = value;
/*      */         break;
/*      */       
/*      */       case HEADING:
/*  809 */         this.skills.heading = value;
/*      */         break;
/*      */       
/*      */       case TACKLING:
/*  813 */         this.skills.tackling = value;
/*      */         break;
/*      */       
/*      */       case CONTROL:
/*  817 */         this.skills.control = value;
/*      */         break;
/*      */       
/*      */       case SPEED:
/*  821 */         this.skills.speed = value;
/*      */         break;
/*      */       
/*      */       case FINISHING:
/*  825 */         this.skills.finishing = value;
/*      */         break;
/*      */     } 
/*      */     
/*  829 */     if (this.bestSkills.contains(skill)) {
/*      */       
/*  831 */       for (int i = Skill.PASSING.ordinal(); i <= Skill.FINISHING.ordinal(); i++) {
/*  832 */         Skill s = Skill.values()[i];
/*  833 */         if (s != skill && !this.bestSkills.contains(s) && getSkillValue(s) > value) {
/*  834 */           removeBestSkill(skill);
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/*  839 */       for (int i = Skill.PASSING.ordinal(); i <= Skill.FINISHING.ordinal(); i++) {
/*  840 */         Skill s = Skill.values()[i];
/*  841 */         if (s != skill && this.bestSkills.contains(s) && getSkillValue(s) < value) {
/*  842 */           addBestSkill(skill);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean addBestSkill(Skill skill) {
/*  849 */     if (this.bestSkills.contains(skill)) {
/*  850 */       return false;
/*      */     }
/*      */     
/*  853 */     for (int i = Skill.PASSING.ordinal(); i <= Skill.FINISHING.ordinal(); i++) {
/*  854 */       Skill s = Skill.values()[i];
/*  855 */       if (s != skill && !this.bestSkills.contains(s) && getSkillValue(s) > getSkillValue(skill)) {
/*  856 */         return false;
/*      */       }
/*      */     } 
/*  859 */     return this.bestSkills.add(skill);
/*      */   }
/*      */   
/*      */   private boolean removeBestSkill(Skill skill) {
/*  863 */     if (!this.bestSkills.contains(skill)) {
/*  864 */       return false;
/*      */     }
/*      */     
/*  867 */     for (int i = Skill.PASSING.ordinal(); i <= Skill.FINISHING.ordinal(); i++) {
/*  868 */       Skill s = Skill.values()[i];
/*  869 */       if (s != skill && this.bestSkills.contains(s) && getSkillValue(s) < getSkillValue(skill)) {
/*  870 */         return false;
/*      */       }
/*      */     } 
/*  873 */     return this.bestSkills.remove(skill);
/*      */   }
/*      */   
/*      */   public boolean toggleBestSkill(Skill skill) {
/*  877 */     return this.bestSkills.contains(skill) ? removeBestSkill(skill) : addBestSkill(skill);
/*      */   }
/*      */   
/*      */   int getSkillKeeper() {
/*  881 */     return (int)((this.value + 0.5F) / 7.0F);
/*      */   }
/*      */   
/*      */   public int getValue() {
/*  885 */     if (this.role == Role.GOALKEEPER) {
/*  886 */       return this.value;
/*      */     }
/*  888 */     return this.skills.passing + this.skills.shooting + this.skills.heading + this.skills.tackling + this.skills.control + this.skills.speed + this.skills.finishing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStars() {
/*  899 */     return stars[getValue()];
/*      */   }
/*      */   
/*      */   public String getPrice(double maxPlayerPrice) {
/*  903 */     if (getValue() == 49) {
/*  904 */       return Assets.moneyFormat(maxPlayerPrice) + "+";
/*      */     }
/*  906 */     return Assets.moneyFormat(maxPlayerPrice / (float)Math.pow(1.2D, (48 - getValue())));
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getSkillLabel(Skill skill) {
/*  911 */     return skillLabels[skill.ordinal()];
/*      */   }
/*      */   
/*      */   public static class Skills {
/*      */     public int passing;
/*      */     public int shooting;
/*      */     public int heading;
/*      */     public int tackling;
/*      */     public int control;
/*      */     public int speed;
/*      */     public int finishing;
/*      */   }
/*      */   
/*      */   public int getIndex() {
/*  925 */     return this.team.players.indexOf(this);
/*      */   }
/*      */   
/*      */   public int getScoringWeight() {
/*  929 */     int value = this.skills.heading + this.skills.shooting + this.skills.finishing;
/*      */     
/*  931 */     if (getIndex() < 11) {
/*  932 */       value *= 4;
/*      */     }
/*      */     
/*  935 */     switch (this.role) {
/*      */       case CONTROL:
/*      */       case SPEED:
/*  938 */         value *= 5;
/*      */         break;
/*      */       
/*      */       case TACKLING:
/*  942 */         value *= 3;
/*      */         break;
/*      */       
/*      */       case FINISHING:
/*  946 */         value *= 10;
/*      */         break;
/*      */     } 
/*  949 */     return value;
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureRegion createFace() {
/*  954 */     List<RgbPair> rgbPairs = new ArrayList<>();
/*      */     
/*  956 */     addSkinColors(rgbPairs);
/*  957 */     addHairColors(rgbPairs);
/*      */     
/*  959 */     String filename = "images/player/menu/" + this.hairStyle + ".png";
/*  960 */     return Assets.loadTextureRegion(filename, rgbPairs);
/*      */   }
/*      */   
/*      */   public void addSkinColors(List<RgbPair> rgbPairs) {
/*  964 */     Color3 sc = Skin.colors[this.skinColor.ordinal()];
/*  965 */     rgbPairs.add(new RgbPair(16737024, sc.color1));
/*  966 */     rgbPairs.add(new RgbPair(11878912, sc.color2));
/*  967 */     rgbPairs.add(new RgbPair(6494208, sc.color3));
/*      */   }
/*      */   
/*      */   public void addHairColors(List<RgbPair> rgbPairs) {
/*  971 */     if (this.hairStyle.equals("SHAVED")) {
/*  972 */       Color2 shavedColor = Hair.shavedColors[this.skinColor.ordinal()][this.hairColor.ordinal()];
/*  973 */       if (shavedColor != null) {
/*  974 */         rgbPairs.add(new RgbPair(9466160, shavedColor.color1));
/*  975 */         rgbPairs.add(new RgbPair(7428400, shavedColor.color2));
/*      */       } else {
/*  977 */         Color3 sc = Skin.colors[this.skinColor.ordinal()];
/*  978 */         rgbPairs.add(new RgbPair(9466160, sc.color1));
/*  979 */         rgbPairs.add(new RgbPair(7428400, sc.color2));
/*      */       } 
/*      */     } else {
/*  982 */       Color3 hc = Hair.colors[this.hairColor.ordinal()];
/*  983 */       rgbPairs.add(new RgbPair(9466160, hc.color1));
/*  984 */       rgbPairs.add(new RgbPair(7428400, hc.color2));
/*  985 */       rgbPairs.add(new RgbPair(5324848, hc.color3));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   boolean searchFarPassingMate() {
/*  991 */     float minDistance = 255.0F;
/*  992 */     float maxDistance = 510.0F;
/*      */     
/*  994 */     float maxAngle = 22.5F;
/*  995 */     float passingDelta = maxDistance * EMath.sin(maxAngle);
/*      */     
/*  997 */     this.passingMate = null;
/*  998 */     this.passingMateAngleCorrection = 0.0F;
/*      */     
/* 1000 */     for (int i = 0; i < 11; i++) {
/* 1001 */       Player ply = this.team.lineup.get(i);
/* 1002 */       if (ply != this) {
/*      */ 
/*      */ 
/*      */         
/* 1006 */         float plyDistance = EMath.dist(this.x, this.y, ply.x, ply.y);
/* 1007 */         float plyAngle = (EMath.aTan2(ply.y + 5.0F * EMath.sin(ply.a) - this.y, ply.x + 5.0F * 
/* 1008 */             EMath.cos(ply.a) - this.x) - this.a + 540.0F) % 360.0F - 180.0F;
/* 1009 */         float plyDelta = plyDistance * EMath.sin(plyAngle);
/*      */         
/* 1011 */         if (Math.abs(plyAngle) < maxAngle && plyDistance > minDistance && plyDistance < maxDistance && 
/*      */           
/* 1013 */           Math.abs(plyDelta) < Math.abs(passingDelta)) {
/* 1014 */           this.passingMate = ply;
/* 1015 */           this.passingMateAngleCorrection = plyAngle;
/* 1016 */           passingDelta = plyDelta;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1020 */     return (this.passingMate != null);
/*      */   }
/*      */ 
/*      */   
/*      */   Player searchNearPlayer() {
/* 1025 */     Player nearPlayer = null;
/* 1026 */     float minDistance = 30.0F;
/* 1027 */     float nearPlayerDistance = 350.0F;
/*      */     
/* 1029 */     for (Player ply : this.team.lineup) {
/* 1030 */       if (ply.checkState(PlayerFsm.Id.STATE_OUTSIDE)) {
/*      */         continue;
/*      */       }
/*      */       
/* 1034 */       float distance = distanceFrom(ply);
/* 1035 */       if (distance < nearPlayerDistance && distance > minDistance) {
/* 1036 */         nearPlayer = ply;
/* 1037 */         nearPlayerDistance = distance;
/*      */       } 
/*      */     } 
/*      */     
/* 1041 */     return nearPlayer;
/*      */   }
/*      */   
/*      */   float distanceFrom(Player player) {
/* 1045 */     return EMath.dist(this.x, this.y, player.x, player.y);
/*      */   }
/*      */   
/*      */   float angleToPoint(float x0, float y0) {
/* 1049 */     return EMath.aTan2(y0 - this.y, x0 - this.x);
/*      */   }
/*      */   
/*      */   boolean seesTheGoal() {
/* 1053 */     return Const.seesTheGoal(this.x, this.y, this.a);
/*      */   }
/*      */   
/*      */   float goalSignedAngleDiff() {
/* 1057 */     float playerToGoal = angleToPoint(0.0F, Math.signum(this.y) * 640.0F);
/* 1058 */     return EMath.signedAngleDiff(playerToGoal, this.a);
/*      */   }
/*      */ 
/*      */   
/*      */   Player searchPassingMate() {
/* 1063 */     float maxCorrectionAngle = 22.5F;
/* 1064 */     float maxSearchAngle = maxCorrectionAngle + 5.0F;
/*      */     
/* 1066 */     this.passingMate = null;
/* 1067 */     float minFrameDistance = 128.0F;
/*      */     
/* 1069 */     int count = Math.min(11, this.team.lineup.size());
/* 1070 */     for (int i = 0; i < count; i++) {
/* 1071 */       Player ply = this.team.lineup.get(i);
/* 1072 */       if (ply != this)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1077 */         if (ply.frameDistance == 128) {
/* 1078 */           GLGame.debug(GLGame.LogType.PASSING, numberName(), "skipped because too far");
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1083 */         else if (!ply.checkStates(new PlayerFsm.Id[] { PlayerFsm.Id.STATE_STAND_RUN, PlayerFsm.Id.STATE_REACH_TARGET, PlayerFsm.Id.STATE_IDLE })) {
/* 1084 */           GLGame.debug(GLGame.LogType.PASSING, numberName(), "skipped after state check");
/*      */ 
/*      */         
/*      */         }
/* 1088 */         else if (ply.frameDistance < minFrameDistance) {
/*      */           
/* 1090 */           float targetPointX = ply.x + 5.0F * EMath.cos(ply.a);
/* 1091 */           float targetPointY = ply.y + 5.0F * EMath.sin(ply.a);
/* 1092 */           float plyAngleCorrection = EMath.signedAngleDiff(EMath.aTan2(targetPointY - this.ball.y, targetPointX - this.ball.x), this.a);
/* 1093 */           if (Math.abs(plyAngleCorrection) < maxSearchAngle) {
/* 1094 */             this.passingMate = ply;
/* 1095 */             minFrameDistance = ply.frameDistance;
/* 1096 */             this.passingMateAngleCorrection = Math.signum(plyAngleCorrection) * Math.min(Math.abs(plyAngleCorrection), maxCorrectionAngle);
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 1101 */     if (this.passingMate == null) {
/* 1102 */       GLGame.debug(GLGame.LogType.PASSING, numberName(), "has not found a passing mate");
/*      */     } else {
/* 1104 */       GLGame.debug(GLGame.LogType.PASSING, numberName(), "has found " + this.passingMate.numberName() + " as passing mate with angleCorrection: " + this.passingMateAngleCorrection);
/*      */     } 
/*      */     
/* 1107 */     return this.passingMate;
/*      */   }
/*      */   
/*      */   String numberName() {
/* 1111 */     return this.number + "_" + this.shirtName + " (" + this.fsm.getState().getClass().getSimpleName() + ", " + ((this.inputDevice == this.ai) ? this.ai.fsm.state.getClass().getSimpleName() : "Controlled") + ")";
/*      */   }
/*      */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Player.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */