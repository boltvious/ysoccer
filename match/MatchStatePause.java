/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.Font;
/*    */ import com.ygames.ysoccer.framework.InputDevice;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStatePause
/*    */   extends MatchState
/*    */ {
/*    */   private boolean keyPause;
/*    */   private boolean waitingNoPauseKey;
/*    */   private boolean resume;
/*    */   
/*    */   MatchStatePause(MatchFsm fsm) {
/* 21 */     super(fsm);
/*    */     
/* 23 */     this.checkReplayKey = false;
/* 24 */     this.checkPauseKey = false;
/* 25 */     this.checkHelpKey = false;
/* 26 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 31 */     super.entryActions();
/*    */     
/* 33 */     useHoldStateDisplayFlags();
/* 34 */     this.keyPause = Gdx.input.isKeyPressed(44);
/* 35 */     this.waitingNoPauseKey = true;
/* 36 */     this.resume = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 41 */     super.doActions(deltaTime);
/*    */ 
/*    */     
/* 44 */     if (this.waitingNoPauseKey) {
/* 45 */       if (!this.keyPause) {
/* 46 */         this.waitingNoPauseKey = false;
/*    */       }
/* 48 */     } else if (!Gdx.input.isKeyPressed(44) && this.keyPause) {
/* 49 */       this.resume = true;
/*    */     } 
/* 51 */     this.keyPause = Gdx.input.isKeyPressed(44);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 57 */     if (this.resume) {
/* 58 */       return newAction(SceneFsm.ActionType.RESTORE_FOREGROUND);
/*    */     }
/*    */ 
/*    */     
/* 62 */     for (InputDevice d : this.match.game.inputDevices) {
/* 63 */       if (d.fire1Down()) {
/* 64 */         return newAction(SceneFsm.ActionType.RESTORE_FOREGROUND);
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 69 */     if (Gdx.input.isKeyPressed(46)) {
/* 70 */       return newFadedAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_REPLAY);
/*    */     }
/*    */     
/* 73 */     return checkCommonConditions();
/*    */   }
/*    */ 
/*    */   
/*    */   void render() {
/* 78 */     super.render();
/* 79 */     Objects.requireNonNull(this.sceneRenderer); Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, Assets.gettext("PAUSE"), 1280 / 2, 22, Font.Align.CENTER);
/*    */   }
/*    */   
/*    */   private void useHoldStateDisplayFlags() {
/* 83 */     MatchState holdState = this.match.getFsm().getHoldState();
/*    */     
/* 85 */     this.displayControlledPlayer = holdState.displayControlledPlayer;
/* 86 */     this.displayBallOwner = holdState.displayBallOwner;
/* 87 */     this.displayGoalScorer = holdState.displayGoalScorer;
/* 88 */     this.displayTime = holdState.displayTime;
/* 89 */     this.displayWindVane = holdState.displayWindVane;
/* 90 */     this.displayRosters = holdState.displayRosters;
/* 91 */     this.displayScore = holdState.displayScore;
/* 92 */     this.displayPenaltiesScore = holdState.displayPenaltiesScore;
/* 93 */     this.displayStatistics = holdState.displayStatistics;
/* 94 */     this.displayRadar = holdState.displayRadar;
/* 95 */     this.displayBenchPlayers = holdState.displayBenchPlayers;
/* 96 */     this.displayBenchFormation = holdState.displayBenchFormation;
/* 97 */     this.displayTacticsSwitch = holdState.displayTacticsSwitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStatePause.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */