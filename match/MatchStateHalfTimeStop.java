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
/*    */ class MatchStateHalfTimeStop
/*    */   extends MatchState
/*    */ {
/*    */   MatchStateHalfTimeStop(MatchFsm fsm) {
/* 15 */     super(fsm);
/*    */     
/* 17 */     this.displayTime = true;
/* 18 */     this.displayWindVane = true;
/* 19 */     this.displayRadar = true;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 24 */     super.entryActions();
/*    */     
/* 26 */     Assets.Sounds.end.play(Assets.Sounds.volume / 100.0F);
/*    */     
/* 28 */     this.match.resetAutomaticInputDevices();
/* 29 */     this.match.setPlayersState(PlayerFsm.Id.STATE_IDLE, (Player)null);
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
/*    */       }
/*    */       
/* 52 */       this.match.updateBall();
/* 53 */       this.match.ball.inFieldKeep();
/*    */       
/* 55 */       this.match.updatePlayers(true);
/*    */       
/* 57 */       this.match.nextSubframe();
/*    */       
/* 59 */       this.sceneRenderer.save();
/*    */       
/* 61 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 63 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 69 */     if (this.timer > 192) {
/* 70 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_HALF_TIME_POSITIONS);
/*    */     }
/*    */     
/* 73 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateHalfTimeStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */