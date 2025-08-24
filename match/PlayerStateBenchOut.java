/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ 
/*    */ class PlayerStateBenchOut
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateBenchOut(PlayerFsm fsm) {
/*  9 */     super(PlayerFsm.Id.STATE_BENCH_OUT, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 14 */     super.doActions();
/*    */     
/* 16 */     if (this.player.targetDistance() > 1.0F) {
/* 17 */       this.player.v = 200.0F;
/* 18 */       this.player.a = this.player.targetAngle();
/*    */     } else {
/* 20 */       this.player.v = 0.0F;
/* 21 */       this.player.a = 0.0F;
/*    */     } 
/*    */     
/* 24 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 29 */     super.entryActions();
/*    */     
/* 31 */     this.player.setTarget(-528.0F, (12 * (2 * this.player.team.index - 1) * (this.player.getMatch()).benchSide));
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateBenchOut.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */