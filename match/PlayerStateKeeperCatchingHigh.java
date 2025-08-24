/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStateKeeperCatchingHigh
/*    */   extends PlayerState
/*    */ {
/*    */   private KeeperFrame active;
/*  8 */   private KeeperFrame[] frames = new KeeperFrame[4];
/*    */   
/*    */   PlayerStateKeeperCatchingHigh(PlayerFsm fsm) {
/* 11 */     super(PlayerFsm.Id.STATE_KEEPER_CATCHING_HIGH, fsm);
/*    */     
/* 13 */     this.active = this.frames[0];
/*    */     
/* 15 */     this.frames[0] = new KeeperFrame();
/* 16 */     (this.frames[0]).fmx = new int[] { 2, 2, 6, 6 };
/* 17 */     (this.frames[0]).fmy = 1;
/* 18 */     (this.frames[0]).offx = new int[] { 0, 0, 0, 0 };
/* 19 */     (this.frames[0]).offz = 30;
/*    */     
/* 21 */     this.frames[1] = new KeeperFrame();
/* 22 */     (this.frames[1]).fmx = new int[] { 0, 0, 1, 1 };
/* 23 */     (this.frames[1]).fmy = 17;
/* 24 */     (this.frames[1]).offx = new int[] { 0, 0, 0, 0 };
/* 25 */     (this.frames[1]).offz = 30;
/*    */     
/* 27 */     this.frames[2] = new KeeperFrame();
/* 28 */     (this.frames[2]).fmx = new int[] { 0, 0, 1, 1 };
/* 29 */     (this.frames[2]).fmy = 18;
/* 30 */     (this.frames[2]).offx = new int[] { 0, 0, 0, 0 };
/* 31 */     (this.frames[2]).offz = 30;
/*    */     
/* 33 */     this.frames[3] = new KeeperFrame();
/* 34 */     (this.frames[3]).fmx = new int[] { 0, 0, 1, 1 };
/* 35 */     (this.frames[3]).fmy = 16;
/* 36 */     (this.frames[3]).offx = new int[] { 0, 0, 0, 0 };
/* 37 */     (this.frames[3]).offz = 15;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 42 */     super.doActions();
/*    */ 
/*    */     
/* 45 */     if (this.ball.holder != this.player) {
/* 46 */       this.player.keeperCollision();
/*    */     }
/*    */ 
/*    */     
/* 50 */     if (this.timer < 51.2F) {
/* 51 */       this.player.v = 0.0F;
/* 52 */       this.active = this.frames[0];
/* 53 */     } else if (this.timer < 102.4F) {
/* 54 */       this.player.vz = this.player.thrustZ;
/* 55 */       this.active = this.frames[1];
/* 56 */     } else if (this.timer < 307.2F) {
/* 57 */       this.player.vz = this.player.thrustZ;
/* 58 */       this.active = this.frames[2];
/* 59 */     } else if (this.timer < 460.8F && 
/* 60 */       this.ball.owner != this.player) {
/* 61 */       this.active = this.frames[3];
/*    */     } 
/*    */ 
/*    */     
/* 65 */     int p = ((this.player.a == 180.0F) ? 1 : 0) + ((this.player.y > 0.0F) ? 2 : 0);
/* 66 */     this.player.fmx = this.active.fmx[p];
/* 67 */     this.player.fmy = this.active.fmy;
/* 68 */     this.player.holdBall(this.active.offx[p], this.active.offz);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 73 */     if (this.timer >= 512.0F) {
/* 74 */       if (this.ball.holder == this.player) {
/* 75 */         return this.fsm.stateKeeperKickAngle;
/*    */       }
/* 77 */       return this.fsm.stateKeeperPositioning;
/*    */     } 
/*    */     
/* 80 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {
/* 85 */     this.ball.setHolder(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKeeperCatchingHigh.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */