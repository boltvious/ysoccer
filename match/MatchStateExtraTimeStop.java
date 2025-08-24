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
/*    */ 
/*    */ 
/*    */ class MatchStateExtraTimeStop
/*    */   extends MatchState
/*    */ {
/*    */   MatchStateExtraTimeStop(MatchFsm fsm) {
/* 17 */     super(fsm);
/*    */     
/* 19 */     this.displayTime = true;
/* 20 */     this.displayWindVane = true;
/* 21 */     this.displayRadar = true;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 26 */     super.entryActions();
/*    */     
/* 28 */     Assets.Sounds.end.play(Assets.Sounds.volume / 100.0F);
/*    */     
/* 30 */     this.match.resetAutomaticInputDevices();
/* 31 */     this.match.setPlayersState(PlayerFsm.Id.STATE_IDLE, (Player)null);
/*    */   }
/*    */ 
/*    */   
/*    */   void onResume() {
/* 36 */     super.onResume();
/*    */     
/* 38 */     this.sceneRenderer.actionCamera
/* 39 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/* 40 */       .setSpeed(ActionCamera.Speed.NORMAL);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 45 */     super.doActions(deltaTime);
/*    */     
/* 47 */     float timeLeft = deltaTime;
/* 48 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 50 */       if (this.match.subframe % 8 == 0) {
/* 51 */         this.match.updateAi();
/*    */       }
/*    */       
/* 54 */       this.match.updateBall();
/* 55 */       this.match.ball.inFieldKeep();
/*    */       
/* 57 */       this.match.updatePlayers(true);
/*    */       
/* 59 */       this.match.nextSubframe();
/*    */       
/* 61 */       this.sceneRenderer.save();
/*    */       
/* 63 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 65 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 71 */     if (this.timer > 192) {
/* 72 */       this.match.ball.setPosition(0.0F, 0.0F, 0.0F);
/* 73 */       this.match.ball.updatePrediction();
/*    */       
/* 75 */       this.sceneRenderer.actionCamera.setOffset(0.0F, 0.0F);
/*    */ 
/*    */       
/* 78 */       this.match.coinToss = Assets.random.nextInt(2);
/* 79 */       this.match.kickOffTeam = this.match.coinToss;
/*    */ 
/*    */       
/* 82 */       this.match.team[0].setSide(1 - 2 * Assets.random.nextInt(2));
/* 83 */       this.match.team[1].setSide(-(this.match.team[0]).side);
/*    */       
/* 85 */       this.match.period = Match.Period.FIRST_EXTRA_TIME;
/* 86 */       this.match.clock = this.match.length;
/*    */       
/* 88 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_STARTING_POSITIONS);
/*    */     } 
/*    */     
/* 91 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateExtraTimeStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */