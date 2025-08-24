/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.ygames.ysoccer.framework.InputDevice;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class MatchState
/*    */   extends SceneState
/*    */ {
/*    */   boolean displayControlledPlayer;
/*    */   boolean displayBallOwner;
/*    */   boolean displayGoalScorer;
/*    */   boolean displayTime;
/*    */   boolean displayWindVane;
/*    */   boolean displayRosters;
/*    */   boolean displayScore;
/*    */   boolean displayPenaltiesScore;
/*    */   boolean displayStatistics;
/*    */   boolean displayRadar;
/*    */   boolean displayBenchPlayers;
/*    */   boolean displayBenchFormation;
/*    */   boolean displayTacticsSwitch;
/*    */   boolean checkReplayKey = true;
/*    */   boolean checkPauseKey = true;
/*    */   boolean checkHelpKey = true;
/*    */   boolean checkBenchCall = true;
/*    */   final Match match;
/*    */   final Ball ball;
/*    */   
/*    */   MatchState(MatchFsm matchFsm) {
/* 43 */     super(matchFsm);
/*    */     
/* 45 */     this.match = matchFsm.getMatch();
/* 46 */     this.ball = this.match.ball;
/*    */   }
/*    */   
/*    */   MatchFsm getFsm() {
/* 50 */     return (MatchFsm)this.fsm;
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkCommonConditions() {
/* 55 */     if (this.checkReplayKey && Gdx.input.isKeyPressed(46)) {
/* 56 */       return newFadedAction(SceneFsm.ActionType.HOLD_FOREGROUND, MatchFsm.STATE_REPLAY);
/*    */     }
/*    */     
/* 59 */     if (this.checkPauseKey && Gdx.input.isKeyPressed(44)) {
/* 60 */       return newAction(SceneFsm.ActionType.HOLD_FOREGROUND, MatchFsm.STATE_PAUSE);
/*    */     }
/*    */     
/* 63 */     if (this.checkHelpKey && Gdx.input.isKeyPressed(244)) {
/* 64 */       return newAction(SceneFsm.ActionType.HOLD_FOREGROUND, MatchFsm.STATE_HELP);
/*    */     }
/*    */     
/* 67 */     if (this.checkBenchCall) {
/* 68 */       for (int t = 0; t <= 1; t++) {
/* 69 */         InputDevice inputDevice = this.match.team[t].fire2Down();
/* 70 */         if (inputDevice != null) {
/* 71 */           (getFsm()).benchStatus.team = this.match.team[t];
/* 72 */           (getFsm()).benchStatus.inputDevice = inputDevice;
/* 73 */           return newAction(SceneFsm.ActionType.HOLD_FOREGROUND, MatchFsm.STATE_BENCH_ENTER);
/*    */         } 
/*    */       } 
/*    */     }
/*    */     
/* 78 */     if (Gdx.input.isKeyPressed(131)) {
/* 79 */       quitMatch();
/* 80 */       return null;
/*    */     } 
/*    */     
/* 83 */     return null;
/*    */   }
/*    */   
/*    */   void quitMatch() {
/* 87 */     this.match.quit();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */