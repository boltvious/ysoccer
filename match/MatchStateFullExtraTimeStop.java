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
/*    */ class MatchStateFullExtraTimeStop
/*    */   extends MatchState
/*    */ {
/*    */   MatchStateFullExtraTimeStop(MatchFsm fsm) {
/* 15 */     super(fsm);
/*    */     
/* 17 */     this.displayTime = true;
/* 18 */     this.displayWindVane = true;
/* 19 */     this.displayRadar = true;
/*    */     
/* 21 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 26 */     super.entryActions();
/*    */     
/* 28 */     this.match.clock = this.match.length * 120.0F / 90.0F;
/* 29 */     (getFsm()).matchCompleted = true;
/*    */     
/* 31 */     Assets.Sounds.end.play(Assets.Sounds.volume / 100.0F);
/*    */     
/* 33 */     this.match.resetAutomaticInputDevices();
/* 34 */     this.match.setPlayersState(PlayerFsm.Id.STATE_IDLE, (Player)null);
/*    */   }
/*    */ 
/*    */   
/*    */   void onResume() {
/* 39 */     super.onResume();
/*    */     
/* 41 */     this.sceneRenderer.actionCamera
/* 42 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/* 43 */       .setSpeed(ActionCamera.Speed.NORMAL);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 48 */     super.doActions(deltaTime);
/*    */     
/* 50 */     float timeLeft = deltaTime;
/* 51 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 53 */       if (this.match.subframe % 8 == 0) {
/* 54 */         this.match.updateAi();
/*    */       }
/*    */       
/* 57 */       this.match.updateBall();
/* 58 */       this.match.ball.inFieldKeep();
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
/* 74 */     if (this.timer > 192) {
/* 75 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_END_POSITIONS);
/*    */     }
/*    */     
/* 78 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateFullExtraTimeStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */