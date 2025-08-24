/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStateThrowInStop
/*    */   extends MatchState
/*    */ {
/*    */   private boolean move;
/*    */   
/*    */   MatchStateThrowInStop(MatchFsm fsm) {
/* 17 */     super(fsm);
/*    */     
/* 19 */     this.displayControlledPlayer = true;
/* 20 */     this.displayBallOwner = true;
/* 21 */     this.displayTime = true;
/* 22 */     this.displayWindVane = true;
/* 23 */     this.displayRadar = true;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 28 */     super.entryActions();
/*    */     
/* 30 */     Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*    */     
/* 32 */     (getFsm()).throwInPosition.set((this.match.ball.xSide * 510), this.match.ball.y);
/*    */     
/* 34 */     this.match.resetAutomaticInputDevices();
/* 35 */     this.match.setPlayersState(PlayerFsm.Id.STATE_REACH_TARGET, (Player)null);
/*    */   }
/*    */ 
/*    */   
/*    */   void onResume() {
/* 40 */     this.match.setPointOfInterest((getFsm()).throwInPosition);
/*    */     
/* 42 */     this.sceneRenderer.actionCamera
/* 43 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/* 44 */       .setSpeed(ActionCamera.Speed.NORMAL)
/* 45 */       .setLimited(true, true);
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
/* 59 */       this.match.updateBall();
/* 60 */       this.match.ball.inFieldKeep();
/*    */       
/* 62 */       this.move = this.match.updatePlayers(true);
/* 63 */       this.match.updateTeamTactics();
/*    */       
/* 65 */       this.match.nextSubframe();
/*    */       
/* 67 */       this.sceneRenderer.save();
/*    */       
/* 69 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 71 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 77 */     if (!this.move) {
/* 78 */       this.match.ball.setPosition((getFsm()).throwInPosition);
/* 79 */       this.match.ball.updatePrediction();
/*    */       
/* 81 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_THROW_IN);
/*    */     } 
/*    */     
/* 84 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateThrowInStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */