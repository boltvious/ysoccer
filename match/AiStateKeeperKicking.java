/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AiStateKeeperKicking
/*    */   extends AiState
/*    */ {
/*    */   AiStateKeeperKicking(AiFsm fsm) {
/* 13 */     super(AiFsm.Id.STATE_KEEPER_KICKING, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 18 */     super.doActions();
/*    */     
/* 20 */     this.ai.x0 = 0;
/* 21 */     this.ai.y0 = ((this.timer > 32.0F) ? 1 : -1) * this.player.team.side;
/* 22 */     this.ai.fire10 = EMath.isIn(this.timer, 64.0F, 67.2F);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 27 */     PlayerState playerState = this.player.getState();
/* 28 */     if (playerState.checkId(PlayerFsm.Id.STATE_KEEPER_KICK_ANGLE)) {
/* 29 */       return null;
/*    */     }
/* 31 */     if (playerState.checkId(PlayerFsm.Id.STATE_KICK)) {
/* 32 */       return null;
/*    */     }
/* 34 */     return this.fsm.stateIdle;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateKeeperKicking.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */