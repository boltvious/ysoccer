/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStateKeeperCatchingLow
/*    */   extends PlayerState
/*    */ {
/*    */   private KeeperFrame active;
/*  8 */   private KeeperFrame[] frames = new KeeperFrame[1];
/*    */   
/*    */   PlayerStateKeeperCatchingLow(PlayerFsm fsm) {
/* 11 */     super(PlayerFsm.Id.STATE_KEEPER_CATCHING_LOW, fsm);
/*    */     
/* 13 */     this.active = this.frames[0];
/*    */     
/* 15 */     this.frames[0] = new KeeperFrame();
/* 16 */     (this.frames[0]).fmx = new int[] { 2, 2, 6, 6 };
/* 17 */     (this.frames[0]).fmy = 1;
/* 18 */     (this.frames[0]).offx = new int[] { 0, 0, 0, 0 };
/* 19 */     (this.frames[0]).offz = 10;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 24 */     super.doActions();
/*    */ 
/*    */     
/* 27 */     if (this.ball.holder != this.player) {
/* 28 */       this.player.keeperCollision();
/*    */     }
/*    */ 
/*    */     
/* 32 */     this.player.v = 0.0F;
/* 33 */     this.active = this.frames[0];
/*    */     
/* 35 */     int p = ((this.player.a == 180.0F) ? 1 : 0) + ((this.player.y > 0.0F) ? 2 : 0);
/* 36 */     this.player.fmx = this.active.fmx[p];
/* 37 */     this.player.fmy = this.active.fmy;
/* 38 */     this.player.holdBall(this.active.offx[p], this.active.offz);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 43 */     if (this.timer >= 512.0F) {
/* 44 */       if (this.ball.holder == this.player) {
/* 45 */         return this.fsm.stateKeeperKickAngle;
/*    */       }
/* 47 */       return this.fsm.stateKeeperPositioning;
/*    */     } 
/*    */     
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {
/* 55 */     this.ball.setHolder(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKeeperCatchingLow.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */