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
/*    */ class MatchStatePenaltiesKick
/*    */   extends MatchState
/*    */ {
/*    */   private boolean isKicking;
/*    */   
/*    */   MatchStatePenaltiesKick(MatchFsm fsm) {
/* 18 */     super(fsm);
/*    */     
/* 20 */     this.displayControlledPlayer = true;
/* 21 */     this.displayBallOwner = true;
/* 22 */     this.displayWindVane = true;
/*    */     
/* 24 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 29 */     super.entryActions();
/*    */     
/* 31 */     this.displayPenaltiesScore = true;
/*    */   }
/*    */ 
/*    */   
/*    */   void onResume() {
/* 36 */     super.onResume();
/*    */     
/* 38 */     this.isKicking = false;
/*    */     
/* 40 */     this.match.penalty.kicker.setTarget(0.0F, (this.match.penalty.side * 517));
/* 41 */     this.match.penalty.kicker.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*    */     
/* 43 */     this.sceneRenderer.actionCamera.setMode(ActionCamera.Mode.STILL);
/*    */   }
/*    */ 
/*    */   
/*    */   void onPause() {
/* 48 */     super.onPause();
/*    */     
/* 50 */     this.match.penalty.kicker.setTarget((-40 * this.match.ball.ySide), (this.match.penalty.side * 479));
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 55 */     super.doActions(deltaTime);
/*    */     
/* 57 */     boolean move = true;
/* 58 */     float timeLeft = deltaTime;
/* 59 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 61 */       if (this.match.subframe % 8 == 0) {
/* 62 */         this.match.updateAi();
/*    */       }
/*    */       
/* 65 */       this.match.updateBall();
/* 66 */       this.match.ball.inFieldKeep();
/*    */       
/* 68 */       move = this.match.updatePlayers(true);
/*    */       
/* 70 */       this.match.nextSubframe();
/*    */       
/* 72 */       this.sceneRenderer.save();
/*    */       
/* 74 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 76 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */     
/* 79 */     if (!move && !this.isKicking) {
/* 80 */       Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*    */       
/* 82 */       this.match.penalty.kicker.setState(PlayerFsm.Id.STATE_PENALTY_KICK_ANGLE);
/* 83 */       if (this.match.penalty.kicker.team.usesAutomaticInputDevice()) {
/* 84 */         this.match.penalty.kicker.inputDevice = this.match.penalty.kicker.team.inputDevice;
/*    */       }
/*    */       
/* 87 */       this.isKicking = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 93 */     if (this.match.ball.v > 0.0F) {
/* 94 */       this.match.penalty.kicker.setState(PlayerFsm.Id.STATE_IDLE);
/* 95 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_PENALTIES_END);
/*    */     } 
/*    */     
/* 98 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStatePenaltiesKick.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */