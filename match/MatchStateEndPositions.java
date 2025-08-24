/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStateEndPositions
/*    */   extends MatchState
/*    */ {
/*    */   private boolean move;
/*    */   
/*    */   MatchStateEndPositions(MatchFsm fsm) {
/* 18 */     super(fsm);
/*    */     
/* 20 */     this.checkReplayKey = false;
/* 21 */     this.checkPauseKey = false;
/* 22 */     this.checkHelpKey = false;
/* 23 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 28 */     super.entryActions();
/*    */     
/* 30 */     this.displayScore = (this.match.period != Match.Period.PENALTIES);
/* 31 */     this.displayPenaltiesScore = (this.match.period == Match.Period.PENALTIES);
/*    */     
/* 33 */     this.match.period = Match.Period.UNDEFINED;
/*    */     
/* 35 */     this.match.ball.setPosition(0.0F, 0.0F, 0.0F);
/* 36 */     this.match.ball.updatePrediction();
/*    */     
/* 38 */     this.sceneRenderer.actionCamera
/* 39 */       .setMode(ActionCamera.Mode.REACH_TARGET)
/* 40 */       .setTarget(0.0F, 0.0F)
/* 41 */       .setOffset(0.0F, 0.0F)
/* 42 */       .setSpeed(ActionCamera.Speed.FAST);
/*    */     
/* 44 */     this.match.setLineupTarget(590.0F, 0.0F);
/* 45 */     this.match.setLineupState(PlayerFsm.Id.STATE_OUTSIDE);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 50 */     super.doActions(deltaTime);
/*    */     
/* 52 */     float timeLeft = deltaTime;
/* 53 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 55 */       if (this.match.subframe % 8 == 0) {
/* 56 */         this.match.updateAi();
/*    */       }
/*    */       
/* 59 */       this.move = this.match.updatePlayers(false);
/*    */       
/* 61 */       this.match.nextSubframe();
/*    */       
/* 63 */       this.sceneRenderer.save();
/*    */       
/* 65 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 67 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 73 */     if (!this.move) {
/* 74 */       if (this.match.recorder.hasHighlights()) {
/* 75 */         this.match.recorder.restart();
/* 76 */         return newFadedAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_HIGHLIGHTS);
/*    */       } 
/* 78 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_END);
/*    */     } 
/*    */ 
/*    */     
/* 82 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateEndPositions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */