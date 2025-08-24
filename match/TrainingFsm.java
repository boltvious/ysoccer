/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TrainingFsm
/*    */   extends SceneFsm
/*    */ {
/*    */   private static int STATE_FREE;
/*    */   static int STATE_REPLAY;
/*    */   
/*    */   TrainingFsm(Training training) {
/* 12 */     super(training);
/*    */     
/* 14 */     setHotKeys(new TrainingHotKeys(training));
/* 15 */     setSceneRenderer(new TrainingRenderer(training.game.glGraphics, training));
/*    */     
/* 17 */     STATE_FREE = addState(new TrainingStateFree(this));
/* 18 */     STATE_REPLAY = addState(new TrainingStateReplay(this));
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 23 */     pushAction(SceneFsm.ActionType.NEW_FOREGROUND, STATE_FREE);
/* 24 */     pushAction(SceneFsm.ActionType.FADE_IN);
/*    */   }
/*    */   
/*    */   public TrainingState getState() {
/* 28 */     return (TrainingState)super.getState();
/*    */   }
/*    */   
/*    */   public Training getTraining() {
/* 32 */     return (Training)getScene();
/*    */   }
/*    */   
/*    */   TrainingRenderer getRenderer() {
/* 36 */     return (TrainingRenderer)getSceneRenderer();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\TrainingFsm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */