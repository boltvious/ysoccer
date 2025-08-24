/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ class AiStateGoalKicking
/*    */   extends AiState
/*    */ {
/*    */   AiStateGoalKicking(AiFsm fsm) {
/* 11 */     super(AiFsm.Id.STATE_GOAL_KICKING, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 16 */     super.doActions();
/*    */     
/* 18 */     this.ai.x0 = 0;
/* 19 */     this.ai.y0 = 0;
/* 20 */     this.ai.fire10 = EMath.isIn(this.timer, 64.0F, 67.2F);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 25 */     if (this.timer > 96.0F) {
/* 26 */       return this.fsm.stateIdle;
/*    */     }
/* 28 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateGoalKicking.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */