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
/*     */ 
/*     */ 
/*     */ class MatchStateGoalKick
/*     */   extends MatchState
/*     */ {
/*     */   private Player goalKickPlayer;
/*     */   private boolean isKicking;
/*     */   
/*     */   MatchStateGoalKick(MatchFsm fsm) {
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
/*  36 */     this.sceneRenderer.actionCamera
/*  37 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  38 */       .setOffset((-30 * this.match.ball.xSide), (-30 * this.match.ball.ySide))
/*  39 */       .setSpeed(ActionCamera.Speed.FAST)
/*  40 */       .setLimited(true, true);
/*     */     
/*  42 */     this.isKicking = false;
/*     */     
/*  44 */     this.goalKickPlayer = (getFsm()).goalKickTeam.lineupAtPosition(0);
/*  45 */     this.goalKickPlayer.setTarget(this.match.ball.x, this.match.ball.y + (6 * this.match.ball.ySide));
/*  46 */     this.goalKickPlayer.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   void onPause() {
/*  51 */     super.onPause();
/*     */     
/*  53 */     this.goalKickPlayer.setTarget(this.match.ball.x / 4.0F, ((getFsm()).goalKickTeam.side * 632));
/*  54 */     this.match.team[0].updateTactics(true);
/*  55 */     this.match.team[1].updateTactics(true);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  60 */     super.doActions(deltaTime);
/*     */     
/*  62 */     boolean move = true;
/*  63 */     float timeLeft = deltaTime;
/*  64 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  66 */       if (this.match.subframe % 8 == 0) {
/*  67 */         this.match.updateAi();
/*     */       }
/*     */       
/*  70 */       this.match.updateBall();
/*  71 */       this.match.ball.inFieldKeep();
/*     */       
/*  73 */       move = this.match.updatePlayers(true);
/*     */       
/*  75 */       this.match.nextSubframe();
/*     */       
/*  77 */       this.sceneRenderer.save();
/*     */       
/*  79 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  81 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */     
/*  84 */     if (!move && !this.isKicking) {
/*  85 */       Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */       
/*  87 */       this.goalKickPlayer.setState(PlayerFsm.Id.STATE_GOAL_KICK);
/*  88 */       if (this.goalKickPlayer.team.usesAutomaticInputDevice()) {
/*  89 */         this.goalKickPlayer.inputDevice = this.goalKickPlayer.team.inputDevice;
/*     */       }
/*  91 */       this.isKicking = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/*  97 */     if (this.match.ball.v > 0.0F) {
/*  98 */       this.match.setPlayersState(PlayerFsm.Id.STATE_STAND_RUN, this.goalKickPlayer);
/*  99 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_MAIN);
/*     */     } 
/*     */     
/* 102 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateGoalKick.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */