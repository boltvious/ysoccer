/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PlayerStateBenchSitting
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateBenchSitting(PlayerFsm fsm) {
/* 11 */     super(PlayerFsm.Id.STATE_BENCH_SITTING, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 16 */     super.doActions();
/*    */     
/* 18 */     if (this.player.targetDistance() > 1.0F) {
/* 19 */       this.player.v = 200.0F;
/* 20 */       this.player.a = this.player.targetAngle();
/*    */     } else {
/* 22 */       this.player.v = 0.0F;
/* 23 */       this.player.a = 0.0F;
/*    */     } 
/*    */     
/* 26 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 31 */     super.entryActions();
/*    */     
/* 33 */     this.player.tx = -544.0F;
/* 34 */     if (1 - 2 * this.player.team.index == (this.player.getMatch()).benchSide) {
/* 35 */       this.player.ty = (-198 + 14 * (this.player.index - 11) + 46);
/*    */     } else {
/* 37 */       this.player.ty = (38 + 14 * (this.player.index - 11) + 46);
/*    */     } 
/* 39 */     this.player.v = 0.0F;
/* 40 */     this.player.a = 0.0F;
/* 41 */     this.player.animationStandRun();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateBenchSitting.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */