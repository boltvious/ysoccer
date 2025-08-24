/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.InputDevice;
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
/*    */ class MatchStateBenchEnter
/*    */   extends MatchState
/*    */ {
/*    */   MatchStateBenchEnter(MatchFsm fsm) {
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
/* 30 */     (getFsm()).benchStatus.oldTarget.set(this.sceneRenderer.actionCamera.getCurrentTarget());
/*    */     
/* 32 */     (getFsm()).benchStatus.selectedPosition = -1;
/* 33 */     (getFsm()).benchStatus.substPosition = -1;
/*    */     
/* 35 */     for (int t = 0; t <= 1; t++) {
/* 36 */       for (int i = 0; i < 11; i++) {
/* 37 */         Player player = (this.match.team[t]).lineup.get(i);
/* 38 */         if (this.match.team[t].usesAutomaticInputDevice()) {
/* 39 */           player.setInputDevice((InputDevice)player.ai);
/*    */         }
/* 41 */         player.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     this.sceneRenderer.actionCamera
/* 46 */       .setMode(ActionCamera.Mode.REACH_TARGET)
/* 47 */       .setTarget((getFsm()).benchStatus.targetX, (getFsm()).benchStatus.targetY)
/* 48 */       .setLimited(false, true)
/* 49 */       .setSpeed(ActionCamera.Speed.WARP);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 54 */     super.doActions(deltaTime);
/*    */     
/* 56 */     float timeLeft = deltaTime;
/* 57 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 59 */       this.match.updateBall();
/* 60 */       this.match.ball.inFieldKeep();
/*    */       
/* 62 */       this.match.updatePlayers(true);
/*    */       
/* 64 */       this.match.nextSubframe();
/*    */       
/* 66 */       this.sceneRenderer.save();
/*    */       
/* 68 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 70 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 77 */     if (this.sceneRenderer.actionCamera.getTargetDistance() < 1.0F) {
/* 78 */       Coach coach = (getFsm()).benchStatus.team.coach;
/* 79 */       coach.status = Coach.Status.STAND;
/* 80 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_BENCH_SUBSTITUTIONS);
/*    */     } 
/*    */     
/* 83 */     if ((getFsm()).benchStatus.inputDevice.xReleased()) {
/* 84 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_BENCH_EXIT);
/*    */     }
/*    */     
/* 87 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateBenchEnter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */