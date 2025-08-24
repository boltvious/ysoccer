/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchStateThrowIn
/*     */   extends MatchState
/*     */ {
/*     */   private Player throwInPlayer;
/*     */   private boolean isThrowingIn;
/*     */   
/*     */   MatchStateThrowIn(MatchFsm fsm) {
/*  20 */     super(fsm);
/*     */     
/*  22 */     this.displayControlledPlayer = true;
/*  23 */     this.displayBallOwner = true;
/*  24 */     this.displayTime = true;
/*  25 */     this.displayWindVane = true;
/*  26 */     this.displayScore = true;
/*  27 */     this.displayRadar = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  32 */     super.onResume();
/*     */     
/*  34 */     this.isThrowingIn = false;
/*     */     
/*  36 */     (getFsm()).throwInTeam.updateFrameDistance();
/*  37 */     (getFsm()).throwInTeam.findNearest();
/*  38 */     this.throwInPlayer = (getFsm()).throwInTeam.near1;
/*     */     
/*  40 */     this.throwInPlayer.setTarget(this.match.ball.x, this.match.ball.y);
/*  41 */     this.throwInPlayer.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */     
/*  43 */     this.sceneRenderer.actionCamera
/*  44 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  45 */       .setSpeed(ActionCamera.Speed.FAST)
/*  46 */       .setLimited(true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   void onPause() {
/*  51 */     super.onPause();
/*     */     
/*  53 */     this.match.updateTeamTactics();
/*     */     
/*  55 */     this.match.ball.setPosition((getFsm()).throwInPosition);
/*  56 */     this.match.ball.updatePrediction();
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  61 */     super.doActions(deltaTime);
/*     */     
/*  63 */     boolean move = true;
/*  64 */     float timeLeft = deltaTime;
/*  65 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  67 */       if (this.match.subframe % 8 == 0) {
/*  68 */         this.match.updateAi();
/*     */       }
/*     */       
/*  71 */       this.match.updateBall();
/*  72 */       this.match.ball.inFieldKeep();
/*     */       
/*  74 */       move = this.match.updatePlayers(true);
/*     */       
/*  76 */       this.match.nextSubframe();
/*     */       
/*  78 */       this.sceneRenderer.save();
/*     */       
/*  80 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  82 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */     
/*  85 */     if (!move && !this.isThrowingIn) {
/*     */       
/*  87 */       Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */       
/*  89 */       this.throwInPlayer.setState(PlayerFsm.Id.STATE_THROW_IN_ANGLE);
/*  90 */       if (this.throwInPlayer.team.usesAutomaticInputDevice()) {
/*  91 */         this.throwInPlayer.inputDevice = this.throwInPlayer.team.inputDevice;
/*     */       }
/*  93 */       this.isThrowingIn = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/*  99 */     if (Math.abs(this.match.ball.x) < 510.0F) {
/* 100 */       this.match.setPlayersState(PlayerFsm.Id.STATE_STAND_RUN, this.throwInPlayer);
/* 101 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_MAIN);
/*     */     } 
/*     */     
/* 104 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateThrowIn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */