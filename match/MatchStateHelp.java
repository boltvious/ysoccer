/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.ygames.ysoccer.framework.InputDevice;
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStateHelp
/*    */   extends MatchState
/*    */ {
/*    */   private boolean keyHelp;
/*    */   private boolean waitingNoHelpKey;
/*    */   private boolean resume;
/*    */   
/*    */   MatchStateHelp(MatchFsm fsm) {
/* 16 */     super(fsm);
/*    */     
/* 18 */     this.checkReplayKey = false;
/* 19 */     this.checkPauseKey = false;
/* 20 */     this.checkHelpKey = false;
/* 21 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 26 */     super.entryActions();
/*    */     
/* 28 */     useHoldStateDisplayFlags();
/* 29 */     this.keyHelp = Gdx.input.isKeyPressed(244);
/* 30 */     this.waitingNoHelpKey = true;
/* 31 */     this.resume = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 36 */     super.doActions(deltaTime);
/*    */ 
/*    */     
/* 39 */     if (this.waitingNoHelpKey) {
/* 40 */       if (!this.keyHelp) {
/* 41 */         this.waitingNoHelpKey = false;
/*    */       }
/* 43 */     } else if (!Gdx.input.isKeyPressed(244) && this.keyHelp) {
/* 44 */       this.resume = true;
/*    */     } 
/* 46 */     this.keyHelp = Gdx.input.isKeyPressed(244);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 52 */     if (this.resume) {
/* 53 */       return newAction(SceneFsm.ActionType.RESTORE_FOREGROUND);
/*    */     }
/*    */ 
/*    */     
/* 57 */     for (InputDevice d : this.match.game.inputDevices) {
/* 58 */       if (d.fire1Down()) {
/* 59 */         return newAction(SceneFsm.ActionType.RESTORE_FOREGROUND);
/*    */       }
/*    */     } 
/*    */     
/* 63 */     return checkCommonConditions();
/*    */   }
/*    */ 
/*    */   
/*    */   void render() {
/* 68 */     super.render();
/* 69 */     this.sceneRenderer.drawHelp((this.match.getFsm().getHotKeys()).keyMap);
/*    */   }
/*    */   
/*    */   private void useHoldStateDisplayFlags() {
/* 73 */     MatchState holdState = this.match.getFsm().getHoldState();
/*    */     
/* 75 */     this.displayTime = holdState.displayTime;
/* 76 */     this.displayWindVane = holdState.displayWindVane;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateHelp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */