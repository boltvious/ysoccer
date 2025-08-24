/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStateHalfTimePositions
/*    */   extends MatchState
/*    */ {
/*    */   private boolean move;
/*    */   
/*    */   MatchStateHalfTimePositions(MatchFsm fsm) {
/* 16 */     super(fsm);
/*    */     
/* 18 */     this.displayTime = true;
/* 19 */     this.displayWindVane = true;
/* 20 */     this.displayStatistics = true;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 25 */     super.entryActions();
/*    */     
/* 27 */     this.match.ball.setPosition(0.0F, 0.0F, 0.0F);
/* 28 */     this.match.ball.updatePrediction();
/*    */     
/* 30 */     this.sceneRenderer.actionCamera
/* 31 */       .setMode(ActionCamera.Mode.REACH_TARGET)
/* 32 */       .setTarget(0.0F, 0.0F)
/* 33 */       .setOffset(0.0F, 0.0F)
/* 34 */       .setSpeed(ActionCamera.Speed.FAST);
/*    */     
/* 36 */     this.match.period = Match.Period.UNDEFINED;
/* 37 */     this.match.clock = this.match.length * 45.0F / 90.0F;
/*    */     
/* 39 */     this.match.setPlayersTarget(590.0F, 0.0F);
/* 40 */     this.match.setPlayersState(PlayerFsm.Id.STATE_OUTSIDE, (Player)null);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 45 */     super.doActions(deltaTime);
/*    */     
/* 47 */     float timeLeft = deltaTime;
/* 48 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 50 */       if (this.match.subframe % 8 == 0) {
/* 51 */         this.match.updateAi();
/*    */       }
/*    */       
/* 54 */       this.move = this.match.updatePlayers(false);
/*    */       
/* 56 */       this.match.nextSubframe();
/*    */       
/* 58 */       this.sceneRenderer.save();
/*    */       
/* 60 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 62 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 68 */     if (!this.move) {
/* 69 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_HALF_TIME_WAIT);
/*    */     }
/*    */     
/* 72 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateHalfTimePositions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */