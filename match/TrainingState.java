/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ abstract class TrainingState
/*    */   extends SceneState
/*    */ {
/*    */   boolean displayControlledPlayer;
/*    */   final Training training;
/*    */   final Team[] team;
/*    */   final Ball ball;
/*    */   
/*    */   TrainingState(TrainingFsm trainingFsm) {
/* 13 */     super(trainingFsm);
/*    */     
/* 15 */     this.training = trainingFsm.getTraining();
/* 16 */     this.team = this.training.team;
/* 17 */     this.ball = this.training.ball;
/*    */   }
/*    */   
/*    */   SceneFsm.Action[] newFadedAction(SceneFsm.ActionType type, int stateId) {
/* 21 */     return this.fsm.newFadedAction(type, stateId);
/*    */   }
/*    */   
/*    */   void quitTraining() {
/* 25 */     this.training.quit();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\TrainingState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */