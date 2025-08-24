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
/*    */ class MatchStatePenaltiesStop
/*    */   extends MatchState
/*    */ {
/*    */   MatchStatePenaltiesStop(MatchFsm fsm) {
/* 15 */     super(fsm);
/*    */     
/* 17 */     this.displayWindVane = true;
/*    */     
/* 19 */     this.checkBenchCall = false;
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
/* 55 */       this.match.updatePlayers(false);
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
/* 70 */       this.match.ball.setPosition(0.0F, -524.0F, 0.0F);
/* 71 */       this.match.ball.updatePrediction();
/*    */       
/* 73 */       this.sceneRenderer.actionCamera.setOffset(0.0F, 0.0F);
/*    */       
/* 75 */       this.match.penaltyKickingTeam = Assets.random.nextInt(2);
/*    */       
/* 77 */       this.match.period = Match.Period.PENALTIES;
/*    */       
/* 79 */       this.match.addPenalties(5);
/*    */       
/* 81 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_PENALTIES);
/*    */     } 
/*    */     
/* 84 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStatePenaltiesStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */