/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStateStartingPositions
/*    */   extends MatchState
/*    */ {
/*    */   private boolean move;
/*    */   
/*    */   MatchStateStartingPositions(MatchFsm fsm) {
/* 16 */     super(fsm);
/*    */     
/* 18 */     this.displayTime = true;
/* 19 */     this.displayWindVane = true;
/* 20 */     this.displayRadar = true;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 25 */     super.entryActions();
/*    */     
/* 27 */     this.match.setStartingPositions();
/* 28 */     this.match.setPlayersState(PlayerFsm.Id.STATE_REACH_TARGET, (Player)null);
/* 29 */     this.match.setPointOfInterest(this.match.ball.x, this.match.ball.y);
/*    */   }
/*    */ 
/*    */   
/*    */   void onResume() {
/* 34 */     this.sceneRenderer.actionCamera
/* 35 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/* 36 */       .setSpeed(ActionCamera.Speed.FAST)
/* 37 */       .setLimited(true, true);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 42 */     super.doActions(deltaTime);
/*    */     
/* 44 */     float timeLeft = deltaTime;
/* 45 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 47 */       this.move = this.match.updatePlayers(false);
/*    */       
/* 49 */       this.match.nextSubframe();
/*    */       
/* 51 */       this.sceneRenderer.save();
/*    */       
/* 53 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 55 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 61 */     if (!this.move) {
/* 62 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_KICK_OFF);
/*    */     }
/*    */     
/* 65 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateStartingPositions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */