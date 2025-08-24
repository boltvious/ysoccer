/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class AiStateKickingOff
/*    */   extends AiState
/*    */ {
/*    */   AiStateKickingOff(AiFsm fsm) {
/* 10 */     super(AiFsm.Id.STATE_KICKING_OFF, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 15 */     super.doActions();
/* 16 */     this.ai.x0 = this.player.team.side;
/* 17 */     this.ai.fire10 = EMath.isIn(this.timer, 50.0F, 56.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 22 */     if (this.timer > 70) {
/* 23 */       return this.fsm.stateIdle;
/*    */     }
/* 25 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateKickingOff.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */