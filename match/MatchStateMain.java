/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchStateMain
/*     */   extends MatchState
/*     */ {
/*     */   private Event event;
/*     */   
/*     */   private enum Event
/*     */   {
/*  33 */     KEEPER_STOP, GOAL, CORNER, GOAL_KICK, THROW_IN, FREE_KICK, PENALTY_KICK, NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MatchStateMain(MatchFsm fsm) {
/*  39 */     super(fsm);
/*     */     
/*  41 */     this.displayControlledPlayer = true;
/*  42 */     this.displayBallOwner = true;
/*  43 */     this.displayTime = true;
/*  44 */     this.displayWindVane = true;
/*  45 */     this.displayRadar = true;
/*     */     
/*  47 */     this.checkBenchCall = false;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  52 */     super.entryActions();
/*     */     
/*  54 */     this.event = Event.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  59 */     super.onResume();
/*     */     
/*  61 */     this.sceneRenderer.actionCamera
/*  62 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  63 */       .setSpeed(ActionCamera.Speed.NORMAL);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  68 */     super.doActions(deltaTime);
/*     */     
/*  70 */     float timeLeft = deltaTime;
/*  71 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  73 */       if (this.match.subframe % 8 == 0) {
/*  74 */         this.match.updateAi();
/*     */ 
/*     */         
/*  77 */         if (this.match.clock >= this.match.nextChant) {
/*  78 */           if (this.match.chantSwitch) {
/*  79 */             this.match.chantSwitch = false;
/*  80 */             this.match.nextChant = this.match.clock + ((6 + Assets.random.nextInt(6)) * 1000);
/*     */           } else {
/*  82 */             Assets.Sounds.chant.play((this.match.getSettings()).crowdChants ? (Assets.Sounds.volume / 100.0F) : 0.0F);
/*  83 */             this.match.chantSwitch = true;
/*  84 */             this.match.nextChant = this.match.clock + 8000.0F;
/*     */           } 
/*     */         }
/*     */         
/*  88 */         this.match.clock += 15.625F;
/*     */         
/*  90 */         this.match.updateFrameDistance();
/*     */         
/*  92 */         if (this.match.ball.owner != null) {
/*  93 */           (this.match.stats[this.match.ball.owner.team.index]).ballPossession++;
/*     */         }
/*     */       } 
/*     */       
/*  97 */       this.match.updateBall();
/*     */       
/*  99 */       int attackingTeam = this.match.attackingTeam();
/* 100 */       int defendingTeam = 1 - attackingTeam;
/*     */       
/* 102 */       if (this.match.ball.holder != null) {
/* 103 */         this.event = Event.KEEPER_STOP;
/*     */         
/*     */         return;
/*     */       } 
/* 107 */       this.match.ball.collisionFlagPosts();
/*     */       
/* 109 */       if (this.match.ball.collisionGoal()) {
/* 110 */         float elapsed = this.match.clock - this.match.lastGoalCollisionTime;
/* 111 */         if (elapsed > 100.0F) {
/* 112 */           (this.match.stats[attackingTeam]).overallShots++;
/* 113 */           (this.match.stats[attackingTeam]).centeredShots++;
/*     */         } 
/* 115 */         this.match.lastGoalCollisionTime = this.match.clock;
/*     */       } 
/*     */ 
/*     */       
/* 119 */       if (this.match.ball.y * this.match.ball.ySide >= 644.0F) {
/*     */         
/* 121 */         if (EMath.isIn(this.match.ball.x, -71.0F, 71.0F) && this.match.ball.z <= 33.0F) {
/*     */           
/* 123 */           (this.match.stats[attackingTeam]).goals++;
/* 124 */           (this.match.stats[attackingTeam]).overallShots++;
/* 125 */           (this.match.stats[attackingTeam]).centeredShots++;
/* 126 */           this.match.addGoal(attackingTeam);
/*     */           
/* 128 */           this.event = Event.GOAL;
/*     */           
/*     */           return;
/*     */         } 
/* 132 */         if (this.match.ball.ownerLast.team == this.match.team[defendingTeam]) {
/* 133 */           this.event = Event.CORNER;
/* 134 */           (getFsm()).cornerKickTeam = this.match.team[1 - this.match.ball.ownerLast.team.index];
/*     */           return;
/*     */         } 
/* 137 */         if (EMath.isIn(this.match.ball.x, -126.0F, 126.0F)) {
/* 138 */           (this.match.stats[attackingTeam]).overallShots++;
/*     */         }
/* 140 */         this.event = Event.GOAL_KICK;
/* 141 */         (getFsm()).goalKickTeam = this.match.team[1 - this.match.ball.ownerLast.team.index];
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/* 148 */       if (Math.abs(this.match.ball.x) > 514.0F) {
/* 149 */         this.event = Event.THROW_IN;
/* 150 */         (getFsm()).throwInTeam = this.match.team[1 - this.match.ball.ownerLast.team.index];
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 155 */       if (this.match.tackle == null) {
/*     */         
/* 157 */         for (int i = 0; i <= 1; i++) {
/*     */ 
/*     */           
/* 160 */           for (int j = 0; j < 11; j++) {
/* 161 */             Player player = (this.match.team[i]).lineup.get(j);
/* 162 */             if (player != null && player.checkState(PlayerFsm.Id.STATE_TACKLE) && player.v > 50.0F) {
/*     */ 
/*     */               
/* 165 */               Team opponentTeam = this.match.team[1 - player.team.index];
/* 166 */               Player opponent = opponentTeam.searchPlayerTackledBy(player);
/*     */               
/* 168 */               if (opponent != null && !opponent.checkState(PlayerFsm.Id.STATE_DOWN)) {
/* 169 */                 float strength = (4.0F + player.v / 260.0F) / 5.0F;
/* 170 */                 float angleDiff = EMath.angleDiff(player.a, opponent.a);
/* 171 */                 this.match.newTackle(player, opponent, strength, angleDiff);
/* 172 */                 Gdx.app.debug(player.shirtName, "tackles on " + opponent.shirtName + " at speed: " + player.v + " (strength = " + strength + ") and angle: " + angleDiff);
/*     */               }
/*     */             
/*     */             } 
/*     */           } 
/*     */         } 
/* 178 */       } else if (EMath.dist(this.match.tackle.player.x, this.match.tackle.player.y, this.match.tackle.opponent.x, this.match.tackle.opponent.y) >= 8.0F) {
/*     */         float downProbability, foulProbability;
/*     */         
/* 181 */         Player player = this.match.tackle.player;
/* 182 */         Player opponent = this.match.tackle.opponent;
/* 183 */         float angleDiff = this.match.tackle.angleDiff;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 188 */         if (angleDiff < 112.5F) {
/* 189 */           downProbability = this.match.tackle.strength * (0.7F + 0.01F * player.skills.tackling - 0.01F * opponent.skills.control);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 194 */           downProbability = this.match.tackle.strength * (0.9F + 0.01F * player.skills.tackling - 0.01F * opponent.skills.control);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 200 */         if (angleDiff < 67.5F) {
/* 201 */           foulProbability = (player.ballDistance < opponent.ballDistance) ? 0.8F : 0.9F;
/*     */ 
/*     */         
/*     */         }
/* 205 */         else if (angleDiff < 112.5F) {
/* 206 */           foulProbability = (player.ballDistance < opponent.ballDistance) ? 0.2F : 0.8F;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 211 */           foulProbability = (player.ballDistance < opponent.ballDistance) ? 0.3F : 0.9F;
/*     */         } 
/*     */         
/* 214 */         Gdx.app.debug(player.shirtName, "tackles on " + opponent.shirtName + " finished, down probability: " + downProbability + ", foul probability: " + foulProbability);
/*     */         
/* 216 */         if (Assets.random.nextFloat() < downProbability) {
/* 217 */           opponent.setState(PlayerFsm.Id.STATE_DOWN);
/*     */           
/* 219 */           if (Assets.random.nextFloat() < foulProbability) {
/* 220 */             this.match.newFoul(this.match.tackle.opponent.x, this.match.tackle.opponent.y);
/* 221 */             Gdx.app.debug(player.shirtName, "tackle on " + opponent.shirtName + " is a foul at: " + this.match.tackle.opponent.x + ", " + this.match.tackle.opponent.y);
/*     */           } else {
/* 223 */             Gdx.app.debug(player.shirtName, "tackles on " + opponent.shirtName + " is probably not a foul");
/*     */           } 
/*     */         } else {
/* 226 */           Gdx.app.debug(opponent.shirtName, "avoids the tackle from " + player.shirtName);
/*     */         } 
/* 228 */         this.match.tackle = null;
/*     */       } 
/*     */ 
/*     */       
/* 232 */       if (this.match.foul != null) {
/* 233 */         if (this.match.foul.isPenalty()) {
/* 234 */           this.event = Event.PENALTY_KICK;
/*     */         } else {
/* 236 */           this.event = Event.FREE_KICK;
/*     */         } 
/* 238 */         (this.match.stats[this.match.foul.player.team.index]).foulsConceded++;
/*     */         
/*     */         return;
/*     */       } 
/* 242 */       this.match.updatePlayers(true);
/* 243 */       this.match.findNearest();
/*     */       
/* 245 */       for (int t = 0; t <= 1; t++) {
/* 246 */         if (this.match.team[t].usesAutomaticInputDevice()) {
/* 247 */           this.match.team[t].automaticInputDeviceSelection();
/*     */         }
/*     */       } 
/*     */       
/* 251 */       this.match.updateBallZone();
/* 252 */       this.match.updateTeamTactics();
/*     */       
/* 254 */       if (this.match.subframe % 8 == 0) {
/* 255 */         this.match.ball.updatePrediction();
/*     */       }
/*     */       
/* 258 */       this.match.nextSubframe();
/*     */       
/* 260 */       this.sceneRenderer.save();
/*     */       
/* 262 */       this.sceneRenderer.actionCamera.update();
/*     */       
/* 264 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 270 */     switch (this.event) {
/*     */       case UNDEFINED:
/* 272 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_KEEPER_STOP);
/*     */       
/*     */       case FIRST_HALF:
/* 275 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_GOAL);
/*     */       
/*     */       case SECOND_HALF:
/* 278 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_CORNER_STOP);
/*     */       
/*     */       case FIRST_EXTRA_TIME:
/* 281 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_GOAL_KICK_STOP);
/*     */       
/*     */       case SECOND_EXTRA_TIME:
/* 284 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_THROW_IN_STOP);
/*     */       
/*     */       case null:
/* 287 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_FREE_KICK_STOP);
/*     */       
/*     */       case null:
/* 290 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_PENALTY_KICK_STOP);
/*     */     } 
/*     */     
/* 293 */     switch (this.match.period) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case FIRST_HALF:
/* 299 */         if (this.match.clock > (this.match.length * 45 / 90) && this.match.periodIsTerminable()) {
/* 300 */           return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_HALF_TIME_STOP);
/*     */         }
/*     */         break;
/*     */       
/*     */       case SECOND_HALF:
/* 305 */         if (this.match.clock > this.match.length && this.match.periodIsTerminable()) {
/*     */           
/* 307 */           this.match.setResult((this.match.stats[0]).goals, (this.match.stats[1]).goals, Match.ResultType.AFTER_90_MINUTES);
/*     */           
/* 309 */           if (this.match.competition.playExtraTime())
/* 310 */             return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_EXTRA_TIME_STOP); 
/* 311 */           if (this.match.competition.playPenalties()) {
/* 312 */             return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_PENALTIES_STOP);
/*     */           }
/* 314 */           return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_FULL_TIME_STOP);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case FIRST_EXTRA_TIME:
/* 320 */         if (this.match.clock > (this.match.length * 105 / 90) && this.match.periodIsTerminable()) {
/* 321 */           return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_HALF_EXTRA_TIME_STOP);
/*     */         }
/*     */         break;
/*     */       
/*     */       case SECOND_EXTRA_TIME:
/* 326 */         if (this.match.clock > (this.match.length * 120 / 90) && this.match.periodIsTerminable()) {
/*     */           
/* 328 */           this.match.setResult((this.match.stats[0]).goals, (this.match.stats[1]).goals, Match.ResultType.AFTER_EXTRA_TIME);
/*     */           
/* 330 */           if (this.match.competition.playPenalties()) {
/* 331 */             return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_PENALTIES_STOP);
/*     */           }
/* 333 */           return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_FULL_EXTRA_TIME_STOP);
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 339 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateMain.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */