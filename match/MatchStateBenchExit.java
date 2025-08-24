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
/*    */ class MatchStateBenchExit
/*    */   extends MatchState
/*    */ {
/*    */   MatchStateBenchExit(MatchFsm fsm) {
/* 16 */     super(fsm);
/*    */     
/* 18 */     this.checkReplayKey = false;
/* 19 */     this.checkPauseKey = false;
/* 20 */     this.checkHelpKey = false;
/* 21 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 26 */     super.entryActions();
/*    */     
/* 28 */     Coach coach = (getFsm()).benchStatus.team.coach;
/* 29 */     coach.status = Coach.Status.BENCH;
/*    */ 
/*    */     
/* 32 */     int substitutes = Math.min((this.match.getSettings()).benchSize, (getFsm()).benchStatus.team.lineup.size() - 11);
/* 33 */     for (int i = 0; i < substitutes; i++) {
/* 34 */       Player player = (getFsm()).benchStatus.team.lineup.get(11 + i);
/* 35 */       if (!player.getState().checkId(PlayerFsm.Id.STATE_OUTSIDE)) {
/* 36 */         player.setState(PlayerFsm.Id.STATE_BENCH_SITTING);
/*    */       }
/*    */     } 
/*    */     
/* 40 */     this.sceneRenderer.actionCamera
/* 41 */       .setMode(ActionCamera.Mode.REACH_TARGET)
/* 42 */       .setTarget((getFsm()).benchStatus.oldTarget)
/* 43 */       .setSpeed(ActionCamera.Speed.WARP);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 48 */     super.doActions(deltaTime);
/*    */     
/* 50 */     float timeLeft = deltaTime;
/* 51 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 53 */       this.match.updateBall();
/* 54 */       this.match.ball.inFieldKeep();
/*    */       
/* 56 */       this.match.updatePlayers(true);
/*    */       
/* 58 */       this.match.updateCoaches();
/*    */       
/* 60 */       this.match.nextSubframe();
/*    */       
/* 62 */       this.sceneRenderer.save();
/*    */       
/* 64 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 66 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 73 */     if (this.sceneRenderer.actionCamera.getTargetDistance() < 1.0F) {
/* 74 */       return newAction(SceneFsm.ActionType.RESTORE_FOREGROUND);
/*    */     }
/*    */     
/* 77 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateBenchExit.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */