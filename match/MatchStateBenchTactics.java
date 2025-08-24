/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStateBenchTactics
/*    */   extends MatchState
/*    */ {
/*    */   MatchStateBenchTactics(MatchFsm fsm) {
/* 14 */     super(fsm);
/*    */     
/* 16 */     this.checkReplayKey = false;
/* 17 */     this.checkPauseKey = false;
/* 18 */     this.checkHelpKey = false;
/* 19 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 24 */     super.entryActions();
/* 25 */     this.displayTacticsSwitch = true;
/* 26 */     (getFsm()).benchStatus.selectedTactics = (getFsm()).benchStatus.team.tactics;
/*    */     
/* 28 */     this.sceneRenderer.actionCamera.setMode(ActionCamera.Mode.STILL);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 33 */     super.doActions(deltaTime);
/*    */     
/* 35 */     float timeLeft = deltaTime;
/* 36 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 38 */       this.match.updateBall();
/* 39 */       this.match.ball.inFieldKeep();
/*    */       
/* 41 */       this.match.updatePlayers(true);
/*    */       
/* 43 */       this.match.updateCoaches();
/*    */       
/* 45 */       this.match.nextSubframe();
/*    */       
/* 47 */       this.sceneRenderer.save();
/*    */       
/* 49 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 51 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */ 
/*    */     
/* 55 */     if ((getFsm()).benchStatus.inputDevice.yMoved()) {
/* 56 */       (getFsm()).benchStatus.selectedTactics = EMath.rotate((getFsm()).benchStatus.selectedTactics, 0, 17, (getFsm()).benchStatus.inputDevice.y1);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 64 */     if ((getFsm()).benchStatus.inputDevice.fire1Down() || 
/* 65 */       (getFsm()).benchStatus.inputDevice.xReleased()) {
/* 66 */       if ((getFsm()).benchStatus.selectedTactics != (getFsm()).benchStatus.team.tactics) {
/* 67 */         Coach coach = (getFsm()).benchStatus.team.coach;
/* 68 */         coach.status = Coach.Status.CALL;
/* 69 */         coach.timer = 500;
/* 70 */         (getFsm()).benchStatus.team.tactics = (getFsm()).benchStatus.selectedTactics;
/*    */       } 
/* 72 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_BENCH_SUBSTITUTIONS);
/*    */     } 
/*    */     
/* 75 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateBenchTactics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */