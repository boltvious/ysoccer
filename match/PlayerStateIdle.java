/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStateIdle
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateIdle(PlayerFsm fsm) {
/*  8 */     super(PlayerFsm.Id.STATE_IDLE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 13 */     super.entryActions();
/* 14 */     this.player.v = 0.0F;
/* 15 */     this.player.animationStandRun();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateIdle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */