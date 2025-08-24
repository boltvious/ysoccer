/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStateKeeperDivingMiddleOne
/*    */   extends PlayerState
/*    */ {
/*    */   private KeeperFrame active;
/*  8 */   private KeeperFrame[] frames = new KeeperFrame[5];
/*    */   
/*    */   PlayerStateKeeperDivingMiddleOne(PlayerFsm fsm) {
/* 11 */     super(PlayerFsm.Id.STATE_KEEPER_DIVING_MIDDLE_ONE, fsm);
/*    */     
/* 13 */     this.active = this.frames[0];
/*    */     
/* 15 */     this.frames[0] = new KeeperFrame();
/* 16 */     (this.frames[0]).fmx = new int[] { 0, 1, 2, 3 };
/* 17 */     (this.frames[0]).fmy = 8;
/* 18 */     (this.frames[0]).offx = new int[] { 10, -8, 13, -9 };
/* 19 */     (this.frames[0]).offz = 24;
/*    */     
/* 21 */     this.frames[1] = new KeeperFrame();
/* 22 */     (this.frames[1]).fmx = new int[] { 0, 1, 2, 3 };
/* 23 */     (this.frames[1]).fmy = 9;
/* 24 */     (this.frames[1]).offx = new int[] { 10, -8, 13, -9 };
/* 25 */     (this.frames[1]).offz = 24;
/*    */     
/* 27 */     this.frames[2] = new KeeperFrame();
/* 28 */     (this.frames[2]).fmx = new int[] { 0, 1, 2, 3 };
/* 29 */     (this.frames[2]).fmy = 10;
/* 30 */     (this.frames[2]).offx = new int[] { 10, -8, 13, -9 };
/* 31 */     (this.frames[2]).offz = 24;
/*    */     
/* 33 */     this.frames[3] = new KeeperFrame();
/* 34 */     (this.frames[3]).fmx = new int[] { 0, 1, 2, 3 };
/* 35 */     (this.frames[3]).fmy = 11;
/* 36 */     (this.frames[3]).offx = new int[] { 8, -5, 10, -8 };
/* 37 */     (this.frames[3]).offz = 10;
/*    */     
/* 39 */     this.frames[4] = new KeeperFrame();
/* 40 */     (this.frames[4]).fmx = new int[] { 0, 1, 2, 3 };
/* 41 */     (this.frames[4]).fmy = 12;
/* 42 */     (this.frames[4]).offx = new int[] { 8, -5, 15, -11 };
/* 43 */     (this.frames[4]).offz = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 48 */     super.doActions();
/*    */ 
/*    */     
/* 51 */     if (this.ball.holder != this.player) {
/* 52 */       this.player.keeperCollision();
/*    */     }
/*    */ 
/*    */     
/* 56 */     if (this.timer < 51.2F) {
/* 57 */       this.player.v = 20.0F;
/* 58 */       this.active = this.frames[0];
/* 59 */     } else if (this.timer < 102.4F) {
/* 60 */       this.player.v = 120.0F * this.player.thrustX;
/* 61 */       this.active = this.frames[1];
/* 62 */     } else if (this.timer < 384.0F) {
/* 63 */       this.player.v = (130.0F + 0.5F * this.player.getSkillKeeper()) * this.player.thrustX;
/* 64 */       this.active = this.frames[2];
/* 65 */     } else if (this.timer < 460.8F) {
/* 66 */       this.player.v = 40.0F * this.player.thrustX;
/* 67 */       this.active = this.frames[3];
/* 68 */     } else if (this.timer < 512.0F) {
/* 69 */       this.player.v *= 0.90234375F;
/* 70 */       this.active = this.frames[4];
/*    */     } else {
/* 72 */       this.player.v *= 0.90234375F;
/*    */     } 
/*    */     
/* 75 */     int p = ((this.player.a == 180.0F) ? 1 : 0) + ((this.player.y > 0.0F) ? 2 : 0);
/* 76 */     this.player.fmx = this.active.fmx[p];
/* 77 */     this.player.fmy = this.active.fmy;
/* 78 */     this.player.holdBall(this.active.offx[p], this.active.offz);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 83 */     if (this.timer >= 614.4F) {
/* 84 */       if (this.ball.holder == this.player) {
/* 85 */         return this.fsm.stateKeeperKickAngle;
/*    */       }
/* 87 */       return this.fsm.stateKeeperPositioning;
/*    */     } 
/*    */     
/* 90 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {
/* 95 */     this.ball.setHolder(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKeeperDivingMiddleOne.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */