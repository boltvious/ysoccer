/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AiStateCornerKicking
/*    */   extends AiState
/*    */ {
/*    */   AiStateCornerKicking(AiFsm fsm) {
/* 13 */     super(AiFsm.Id.STATE_CORNER_KICKING, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 18 */     super.doActions();
/*    */     
/* 20 */     this.ai.x0 = EMath.isIn(this.timer, 32.0F, 35.2F) ? this.player.team.side : 0;
/* 21 */     this.ai.y0 = 0;
/* 22 */     this.ai.fire10 = EMath.isIn(this.timer, 64.0F, 67.2F);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 27 */     PlayerState playerState = this.player.getState();
/* 28 */     if (playerState.checkId(PlayerFsm.Id.STATE_CORNER_KICK_ANGLE)) {
/* 29 */       return null;
/*    */     }
/* 31 */     if (playerState.checkId(PlayerFsm.Id.STATE_CORNER_KICK_SPEED)) {
/* 32 */       return null;
/*    */     }
/* 34 */     return this.fsm.stateIdle;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateCornerKicking.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */