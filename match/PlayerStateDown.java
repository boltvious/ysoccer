/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStateDown
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateDown(PlayerFsm fsm) {
/*  8 */     super(PlayerFsm.Id.STATE_DOWN, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 13 */     super.entryActions();
/* 14 */     this.player.v = 0.0F;
/* 15 */     this.player.fmy = 7.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   PlayerState checkConditions() {
/* 20 */     if (this.timer > 768.0F) {
/* 21 */       return this.fsm.stateStandRun;
/*    */     }
/* 23 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateDown.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */