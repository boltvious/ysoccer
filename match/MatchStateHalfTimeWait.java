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
/*    */ class MatchStateHalfTimeWait
/*    */   extends MatchState
/*    */ {
/*    */   MatchStateHalfTimeWait(MatchFsm fsm) {
/* 15 */     super(fsm);
/*    */     
/* 17 */     this.displayTime = true;
/* 18 */     this.displayWindVane = true;
/* 19 */     this.displayStatistics = true;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 24 */     super.entryActions();
/*    */     
/* 26 */     this.match.swapTeamSides();
/*    */     
/* 28 */     this.match.kickOffTeam = 1 - this.match.coinToss;
/*    */     
/* 30 */     this.sceneRenderer.actionCamera
/* 31 */       .setMode(ActionCamera.Mode.REACH_TARGET)
/* 32 */       .setSpeed(ActionCamera.Speed.FAST)
/* 33 */       .setTarget(0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 38 */     super.doActions(deltaTime);
/*    */     
/* 40 */     float timeLeft = deltaTime;
/* 41 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 43 */       this.match.nextSubframe();
/*    */       
/* 45 */       this.sceneRenderer.save();
/*    */       
/* 47 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 49 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 55 */     if (this.match.team[0].fire1Down() != null || this.match.team[1]
/* 56 */       .fire1Down() != null || this.timer > 192) {
/*    */       
/* 58 */       this.match.period = Match.Period.SECOND_HALF;
/* 59 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_HALF_TIME_ENTER);
/*    */     } 
/*    */     
/* 62 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateHalfTimeWait.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */