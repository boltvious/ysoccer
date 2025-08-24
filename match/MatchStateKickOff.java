/*     */ package com.ygames.ysoccer.match;
/*     */ 
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
/*     */ class MatchStateKickOff
/*     */   extends MatchState
/*     */ {
/*     */   private Player kickOffPlayer;
/*     */   private boolean isKickingOff;
/*     */   
/*     */   MatchStateKickOff(MatchFsm fsm) {
/*  22 */     super(fsm);
/*     */     
/*  24 */     this.displayControlledPlayer = true;
/*  25 */     this.displayBallOwner = true;
/*  26 */     this.displayTime = true;
/*  27 */     this.displayWindVane = true;
/*  28 */     this.displayScore = true;
/*  29 */     this.displayRadar = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  34 */     super.onResume();
/*     */     
/*  36 */     this.isKickingOff = false;
/*     */     
/*  38 */     Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */     
/*  40 */     Team kickOffTeam = this.match.team[this.match.kickOffTeam];
/*  41 */     kickOffTeam.updateFrameDistance();
/*  42 */     kickOffTeam.findNearest();
/*  43 */     this.kickOffPlayer = kickOffTeam.near1;
/*  44 */     this.kickOffPlayer.tx = this.match.ball.x - (7 * this.kickOffPlayer.team.side) + 1.0F;
/*  45 */     this.kickOffPlayer.ty = this.match.ball.y + 1.0F;
/*     */     
/*  47 */     if (kickOffTeam.usesAutomaticInputDevice()) {
/*  48 */       this.kickOffPlayer.inputDevice = kickOffTeam.inputDevice;
/*     */     }
/*     */     
/*  51 */     this.sceneRenderer.actionCamera
/*  52 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  53 */       .setSpeed(ActionCamera.Speed.FAST);
/*     */   }
/*     */ 
/*     */   
/*     */   void onPause() {
/*  58 */     super.onPause();
/*  59 */     this.match.setStartingPositions();
/*  60 */     this.kickOffPlayer.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  65 */     super.doActions(deltaTime);
/*     */     
/*  67 */     boolean move = true;
/*  68 */     float timeLeft = deltaTime;
/*  69 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  71 */       if (this.match.subframe % 8 == 0) {
/*  72 */         this.match.ball.updatePrediction();
/*  73 */         this.match.updateFrameDistance();
/*  74 */         this.match.updateAi();
/*     */       } 
/*     */       
/*  77 */       this.match.updateBall();
/*  78 */       move = this.match.updatePlayers(true);
/*  79 */       this.match.findNearest();
/*     */       
/*  81 */       this.match.nextSubframe();
/*     */       
/*  83 */       this.sceneRenderer.save();
/*     */       
/*  85 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  87 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */     
/*  90 */     if (!move && !this.isKickingOff) {
/*  91 */       this.kickOffPlayer.setState(PlayerFsm.Id.STATE_KICK_OFF);
/*  92 */       this.isKickingOff = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/*  98 */     if (EMath.dist(this.match.ball.x, this.match.ball.y, 0.0F, 0.0F) > 10.0F) {
/*  99 */       for (int t = 0; t <= 1; t++) {
/* 100 */         for (int i = 0; i < 11; i++) {
/* 101 */           Player player = (this.match.team[t]).lineup.get(i);
/* 102 */           if (player != this.kickOffPlayer) {
/* 103 */             player.setState(PlayerFsm.Id.STATE_STAND_RUN);
/*     */           }
/*     */         } 
/*     */       } 
/* 107 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_MAIN);
/*     */     } 
/*     */     
/* 110 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateKickOff.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */