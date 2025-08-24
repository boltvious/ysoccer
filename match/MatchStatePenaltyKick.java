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
/*     */ class MatchStatePenaltyKick
/*     */   extends MatchState
/*     */ {
/*     */   private boolean isKicking;
/*     */   
/*     */   MatchStatePenaltyKick(MatchFsm fsm) {
/*  19 */     super(fsm);
/*     */     
/*  21 */     this.displayControlledPlayer = true;
/*  22 */     this.displayBallOwner = true;
/*  23 */     this.displayWindVane = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  28 */     super.entryActions();
/*     */     
/*  30 */     this.displayTime = true;
/*  31 */     this.displayScore = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  36 */     super.onResume();
/*     */     
/*  38 */     this.isKicking = false;
/*     */     
/*  40 */     this.match.penalty.kicker.setTarget(0.0F, (this.match.penalty.side * 517));
/*  41 */     this.match.penalty.kicker.setState(PlayerFsm.Id.STATE_REACH_TARGET);
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
/*  53 */     this.match.penalty.kicker.setTarget((-40 * this.match.ball.ySide), (this.match.penalty.side * 479));
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  58 */     super.doActions(deltaTime);
/*     */     
/*  60 */     boolean move = true;
/*  61 */     float timeLeft = deltaTime;
/*  62 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  64 */       if (this.match.subframe % 8 == 0) {
/*  65 */         this.match.updateAi();
/*     */       }
/*     */       
/*  68 */       this.match.updateBall();
/*  69 */       this.match.ball.inFieldKeep();
/*     */       
/*  71 */       move = this.match.updatePlayers(true);
/*     */       
/*  73 */       this.match.nextSubframe();
/*     */       
/*  75 */       this.sceneRenderer.save();
/*     */       
/*  77 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  79 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */     
/*  82 */     if (!move && !this.isKicking) {
/*  83 */       Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */       
/*  85 */       this.match.penalty.kicker.setState(PlayerFsm.Id.STATE_PENALTY_KICK_ANGLE);
/*  86 */       if (this.match.penalty.kicker.team.usesAutomaticInputDevice()) {
/*  87 */         this.match.penalty.kicker.inputDevice = this.match.penalty.kicker.team.inputDevice;
/*     */       }
/*     */       
/*  90 */       this.isKicking = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/*  96 */     if (this.match.ball.v > 0.0F) {
/*  97 */       this.match.setPlayersState(PlayerFsm.Id.STATE_STAND_RUN, this.match.penalty.kicker);
/*  98 */       this.match.penaltyScorer = this.match.penalty.kicker;
/*  99 */       this.match.penalty = null;
/* 100 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_MAIN);
/*     */     } 
/*     */     
/* 103 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStatePenaltyKick.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */