/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PlayerStateBenchStanding
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateBenchStanding(PlayerFsm fsm) {
/* 12 */     super(PlayerFsm.Id.STATE_BENCH_STANDING, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 17 */     super.doActions();
/*    */     
/* 19 */     if (this.player.targetDistance() > 1.0F) {
/* 20 */       this.player.v = 200.0F;
/* 21 */       this.player.a = this.player.targetAngle();
/*    */     } else {
/* 23 */       this.player.v = 0.0F;
/* 24 */       this.player.a = 0.0F;
/*    */     } 
/*    */     
/* 27 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 32 */     super.entryActions();
/*    */     
/* 34 */     this.player.tx = -532.0F;
/* 35 */     if (1 - 2 * this.player.team.index == (this.player.getMatch()).benchSide) {
/* 36 */       this.player.ty = (-198 + 14 * (this.player.index - 11) + 46);
/*    */     } else {
/* 38 */       this.player.ty = (38 + 14 * (this.player.index - 11) + 46);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateBenchStanding.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */