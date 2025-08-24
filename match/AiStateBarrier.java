/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AiStateBarrier
/*    */   extends AiState
/*    */ {
/*    */   private boolean jumped;
/*    */   
/*    */   AiStateBarrier(AiFsm fsm) {
/* 14 */     super(AiFsm.Id.AI_STATE_BARRIER, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 19 */     super.entryActions();
/*    */     
/* 21 */     this.jumped = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 26 */     super.doActions();
/*    */     
/* 28 */     this.ai.fire10 = false;
/*    */     
/* 30 */     if (!this.jumped && jumpingIsUseful()) {
/* 31 */       this.ai.fire10 = true;
/* 32 */       this.jumped = true;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 38 */     if (!this.player.getState().checkId(PlayerFsm.Id.STATE_BARRIER)) {
/* 39 */       return this.fsm.stateIdle;
/*    */     }
/*    */     
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   private boolean jumpingIsUseful() {
/* 46 */     return (this.player.ball.v > 0.0F && this.player.ball.z > 5.0F && 
/*    */       
/* 48 */       EMath.angleDiff(this.player.a, this.player.ballAngle) < 45.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateBarrier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */