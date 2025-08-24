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
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchStatePenaltiesEnd
/*     */   extends MatchState
/*     */ {
/*     */   private boolean goalLineCrossed;
/*     */   private boolean isGoal;
/*     */   private Player keeper;
/*     */   
/*     */   MatchStatePenaltiesEnd(MatchFsm fsm) {
/*  26 */     super(fsm);
/*     */     
/*  28 */     this.displayWindVane = true;
/*  29 */     this.displayPenaltiesScore = true;
/*     */     
/*  31 */     this.checkBenchCall = false;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  36 */     super.entryActions();
/*     */     
/*  38 */     this.goalLineCrossed = false;
/*  39 */     this.isGoal = false;
/*  40 */     this.keeper = this.match.team[1 - this.match.penaltyKickingTeam].lineupAtPosition(0);
/*     */     
/*  42 */     this.match.resetAutomaticInputDevices();
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  47 */     super.onResume();
/*     */     
/*  49 */     this.sceneRenderer.actionCamera.setMode(ActionCamera.Mode.STILL);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  54 */     super.doActions(deltaTime);
/*     */     
/*  56 */     float timeLeft = deltaTime;
/*  57 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  59 */       if (this.match.subframe % 8 == 0) {
/*  60 */         this.match.updateAi();
/*  61 */         this.match.updateFrameDistance();
/*     */       } 
/*     */       
/*  64 */       this.match.updateBall();
/*  65 */       if (!this.goalLineCrossed && !this.isGoal && this.match.ball.y * this.match.ball.ySide >= 644.0F && 
/*     */         
/*  67 */         EMath.isIn(this.match.ball.x, -71.0F, 71.0F) && this.match.ball.z <= 33.0F) {
/*     */         
/*  69 */         this.isGoal = true;
/*  70 */         Assets.Sounds.homeGoal.play(Assets.Sounds.volume / 100.0F);
/*     */       } 
/*     */       
/*  73 */       if (this.match.ball.y * this.match.ball.ySide >= 644.0F) {
/*  74 */         this.goalLineCrossed = true;
/*     */       }
/*     */ 
/*     */       
/*  78 */       if ((this.goalLineCrossed || EMath.sin(this.match.ball.a) > 0.0F) && 
/*  79 */         this.keeper.checkState(PlayerFsm.Id.STATE_KEEPER_POSITIONING)) {
/*  80 */         this.keeper.setState(PlayerFsm.Id.STATE_IDLE);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*  85 */       if (this.match.ball.holder == this.keeper && 
/*  86 */         this.keeper.checkState(PlayerFsm.Id.STATE_KEEPER_KICK_ANGLE)) {
/*  87 */         this.keeper.setState(PlayerFsm.Id.STATE_IDLE);
/*     */       }
/*     */ 
/*     */       
/*  91 */       this.match.ball.collisionGoal();
/*  92 */       this.match.ball.collisionNet();
/*     */       
/*  94 */       this.match.updatePlayers(true);
/*     */       
/*  96 */       if (this.match.subframe % 64 == 0) {
/*  97 */         this.match.ball.updatePrediction();
/*     */       }
/*     */       
/* 100 */       this.match.nextSubframe();
/*     */       
/* 102 */       this.sceneRenderer.save();
/*     */       
/* 104 */       this.sceneRenderer.actionCamera.update();
/*     */       
/* 106 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 113 */     if (this.match.ball.v == 0.0F && this.match.ball.vz == 0.0F) {
/* 114 */       if (this.isGoal) {
/* 115 */         this.match.penalty.setState(Match.PenaltyState.SCORED);
/*     */       } else {
/* 117 */         this.match.penalty.setState(Match.PenaltyState.MISSED);
/*     */       } 
/*     */       
/* 120 */       if (this.timer > 192) {
/*     */         
/* 122 */         this.match.ball.setPosition(0.0F, -524.0F, 0.0F);
/* 123 */         this.match.ball.updatePrediction();
/*     */         
/* 125 */         if (haveWinner()) {
/* 126 */           this.match.setResult(this.match.penaltiesScore(0), this.match.penaltiesScore(1), Match.ResultType.AFTER_PENALTIES);
/* 127 */           (getFsm()).matchCompleted = true;
/* 128 */           return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_END_POSITIONS);
/*     */         } 
/* 130 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_PENALTIES);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 135 */     return checkCommonConditions();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean haveWinner() {
/* 140 */     if (this.match.penaltiesScore(0) > this.match.penaltiesPotentialScore(1)) return true;
/*     */ 
/*     */     
/* 143 */     if (this.match.penaltiesScore(1) > this.match.penaltiesPotentialScore(0)) return true;
/*     */ 
/*     */     
/* 146 */     return (this.match.penaltiesLeft(0) == 0 && this.match
/* 147 */       .penaltiesLeft(1) == 0 && this.match
/* 148 */       .penaltiesScore(0) != this.match.penaltiesScore(1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStatePenaltiesEnd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */