/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ 
/*    */ class AiStatePassing
/*    */   extends AiState
/*    */ {
/*    */   private int duration;
/*    */   
/*    */   AiStatePassing(AiFsm fsm) {
/* 11 */     super(AiFsm.Id.STATE_PASSING, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 16 */     super.entryActions();
/*    */     
/* 18 */     this.ai.fire10 = false;
/* 19 */     this.duration = 8;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 24 */     super.doActions();
/*    */     
/* 26 */     this.ai.x0 = this.ai.x1;
/* 27 */     this.ai.y0 = this.ai.y1;
/* 28 */     this.ai.fire10 = (this.timer <= this.duration);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 33 */     if (this.timer > 32) {
/* 34 */       return this.fsm.stateIdle;
/*    */     }
/*    */     
/* 37 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStatePassing.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */