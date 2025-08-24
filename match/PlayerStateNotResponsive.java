/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStateNotResponsive
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateNotResponsive(PlayerFsm fsm) {
/*  8 */     super(PlayerFsm.Id.STATE_NOT_RESPONSIVE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 13 */     super.entryActions();
/* 14 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 19 */     if (this.timer > 102.4F) {
/* 20 */       return this.fsm.stateStandRun;
/*    */     }
/* 22 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateNotResponsive.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */