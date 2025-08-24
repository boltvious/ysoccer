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
/*    */ class MatchStateHalfTimeEnter
/*    */   extends MatchState
/*    */ {
/*    */   private int enteringCounter;
/*    */   
/*    */   MatchStateHalfTimeEnter(MatchFsm fsm) {
/* 18 */     super(fsm);
/*    */     
/* 20 */     this.displayTime = true;
/* 21 */     this.displayWindVane = true;
/* 22 */     this.displayRadar = true;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 27 */     super.entryActions();
/*    */     
/* 29 */     this.match.setStartingPositions();
/*    */   }
/*    */ 
/*    */   
/*    */   void onResume() {
/* 34 */     super.onResume();
/*    */     
/* 36 */     this.sceneRenderer.actionCamera
/* 37 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/* 38 */       .setSpeed(ActionCamera.Speed.NORMAL);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 43 */     super.doActions(deltaTime);
/*    */     
/* 45 */     float timeLeft = deltaTime;
/* 46 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 48 */       if (this.match.subframe % 8 == 0) {
/* 49 */         this.match.updateAi();
/* 50 */         if (this.enteringCounter % 4 == 0 && this.enteringCounter / 4 < 11) {
/* 51 */           for (int t = 0; t <= 1; t++) {
/* 52 */             int i = this.enteringCounter / 4;
/* 53 */             Player player = (this.match.team[t]).lineup.get(i);
/* 54 */             player.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*    */           } 
/*    */         }
/* 57 */         this.enteringCounter++;
/*    */       } 
/*    */       
/* 60 */       this.match.updatePlayers(false);
/*    */       
/* 62 */       this.match.nextSubframe();
/*    */       
/* 64 */       this.sceneRenderer.save();
/*    */       
/* 66 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 68 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 74 */     if (this.enteringCounter / 4 == 11) {
/* 75 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_STARTING_POSITIONS);
/*    */     }
/*    */     
/* 78 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateHalfTimeEnter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */