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
/*    */ class MatchStateHalfExtraTimeStop
/*    */   extends MatchState
/*    */ {
/*    */   MatchStateHalfExtraTimeStop(MatchFsm fsm) {
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
/* 70 */       this.match.ball.setPosition(0.0F, 0.0F, 0.0F);
/* 71 */       this.match.ball.updatePrediction();
/*    */       
/* 73 */       this.sceneRenderer.actionCamera.setOffset(0.0F, 0.0F);
/*    */       
/* 75 */       this.match.swapTeamSides();
/*    */       
/* 77 */       this.match.kickOffTeam = 1 - this.match.coinToss;
/*    */       
/* 79 */       this.match.period = Match.Period.SECOND_EXTRA_TIME;
/* 80 */       this.match.clock = this.match.length * 105.0F / 90.0F;
/*    */       
/* 82 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_STARTING_POSITIONS);
/*    */     } 
/*    */     
/* 85 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateHalfExtraTimeStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */