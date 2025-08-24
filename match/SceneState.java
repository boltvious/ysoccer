/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ abstract class SceneState
/*    */ {
/*    */   private int id;
/*    */   final SceneFsm fsm;
/*    */   SceneRenderer sceneRenderer;
/*    */   int timer;
/*    */   
/*    */   SceneState(SceneFsm fsm) {
/* 11 */     this.fsm = fsm;
/* 12 */     this.sceneRenderer = fsm.getSceneRenderer();
/*    */   }
/*    */   
/*    */   public int getId() {
/* 16 */     return this.id;
/*    */   }
/*    */   
/*    */   public void setId(int id) {
/* 20 */     this.id = id;
/*    */   }
/*    */   
/*    */   void entryActions() {
/* 24 */     this.timer = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {}
/*    */   
/*    */   void doActions(float deltaTime) {
/* 31 */     this.timer++;
/* 32 */     this.fsm.getHotKeys().update();
/*    */   }
/*    */   
/*    */   SceneFsm.Action[] newAction(SceneFsm.ActionType type, int stateId) {
/* 36 */     return this.fsm.newAction(type, stateId);
/*    */   }
/*    */   
/*    */   SceneFsm.Action[] newAction(SceneFsm.ActionType type) {
/* 40 */     return newAction(type, -1);
/*    */   }
/*    */   
/*    */   SceneFsm.Action[] newFadedAction(SceneFsm.ActionType type, int stateId) {
/* 44 */     return this.fsm.newFadedAction(type, stateId);
/*    */   }
/*    */   
/*    */   SceneFsm.Action[] newFadedAction(SceneFsm.ActionType type) {
/* 48 */     return newFadedAction(type, -1);
/*    */   }
/*    */ 
/*    */   
/*    */   void onResume() {}
/*    */ 
/*    */   
/*    */   void onPause() {}
/*    */   
/*    */   abstract SceneFsm.Action[] checkConditions();
/*    */   
/*    */   boolean checkId(int id) {
/* 60 */     return (this.id == id);
/*    */   }
/*    */   
/*    */   void render() {}
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\SceneState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */