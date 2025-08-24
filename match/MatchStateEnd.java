/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStateEnd
/*    */   extends MatchState
/*    */ {
/*    */   MatchStateEnd(MatchFsm fsm) {
/* 17 */     super(fsm);
/*    */     
/* 19 */     this.displayStatistics = true;
/*    */     
/* 21 */     this.checkReplayKey = false;
/* 22 */     this.checkPauseKey = false;
/* 23 */     this.checkHelpKey = false;
/* 24 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 29 */     super.entryActions();
/*    */     
/* 31 */     this.match.period = Match.Period.UNDEFINED;
/*    */     
/* 33 */     this.sceneRenderer.actionCamera
/* 34 */       .setMode(ActionCamera.Mode.REACH_TARGET)
/* 35 */       .setTarget(0.0F, 0.0F)
/* 36 */       .setSpeed(ActionCamera.Speed.NORMAL);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 41 */     super.doActions(deltaTime);
/*    */     
/* 43 */     float timeLeft = deltaTime;
/* 44 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 46 */       this.match.nextSubframe();
/*    */       
/* 48 */       this.sceneRenderer.save();
/*    */       
/* 50 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 52 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 58 */     if (Gdx.input.isKeyPressed(36) && this.match.recorder.hasHighlights()) {
/* 59 */       this.match.recorder.restart();
/* 60 */       return newFadedAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_HIGHLIGHTS);
/*    */     } 
/*    */     
/* 63 */     if (this.match.team[0].fire1Up() != null || this.match.team[1]
/* 64 */       .fire1Up() != null || this.timer > 1280) {
/*    */       
/* 66 */       quitMatch();
/* 67 */       return null;
/*    */     } 
/*    */     
/* 70 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateEnd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */