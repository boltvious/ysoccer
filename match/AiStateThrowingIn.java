/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AiStateThrowingIn
/*    */   extends AiState
/*    */ {
/*    */   AiStateThrowingIn(AiFsm fsm) {
/* 13 */     super(AiFsm.Id.STATE_THROWING_IN, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 18 */     super.doActions();
/* 19 */     if (this.timer > 96.0F) {
/* 20 */       this.ai.x0 = this.player.team.side;
/*    */     }
/* 22 */     this.ai.fire10 = EMath.isIn(this.timer, 128.0F, 147.84F);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 27 */     PlayerState playerState = this.player.getState();
/* 28 */     if (playerState.checkId(PlayerFsm.Id.STATE_THROW_IN_ANGLE)) {
/* 29 */       return null;
/*    */     }
/* 31 */     if (playerState.checkId(PlayerFsm.Id.STATE_THROW_IN_SPEED)) {
/* 32 */       return null;
/*    */     }
/*    */     
/* 35 */     return this.fsm.stateIdle;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateThrowingIn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */