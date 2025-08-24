/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStateKeeperDivingHighOne
/*    */   extends PlayerState
/*    */ {
/*    */   private KeeperFrame active;
/*  8 */   private KeeperFrame[] frames = new KeeperFrame[5];
/*    */   
/*    */   PlayerStateKeeperDivingHighOne(PlayerFsm fsm) {
/* 11 */     super(PlayerFsm.Id.STATE_KEEPER_DIVING_HIGH_ONE, fsm);
/*    */     
/* 13 */     this.active = this.frames[0];
/*    */     
/* 15 */     this.frames[0] = new KeeperFrame();
/* 16 */     (this.frames[0]).fmx = new int[] { 4, 5, 6, 7 };
/* 17 */     (this.frames[0]).fmy = 8;
/* 18 */     (this.frames[0]).offx = new int[] { 16, -13, 16, -12 };
/* 19 */     (this.frames[0]).offz = 18;
/*    */     
/* 21 */     this.frames[1] = new KeeperFrame();
/* 22 */     (this.frames[1]).fmx = new int[] { 0, 1, 2, 3 };
/* 23 */     (this.frames[1]).fmy = 13;
/* 24 */     (this.frames[1]).offx = new int[] { 13, -11, 10, -10 };
/* 25 */     (this.frames[1]).offz = 26;
/*    */     
/* 27 */     this.frames[2] = new KeeperFrame();
/* 28 */     (this.frames[2]).fmx = new int[] { 0, 1, 2, 3 };
/* 29 */     (this.frames[2]).fmy = 14;
/* 30 */     (this.frames[2]).offx = new int[] { 13, -11, 10, -10 };
/* 31 */     (this.frames[2]).offz = 26;
/*    */     
/* 33 */     this.frames[3] = new KeeperFrame();
/* 34 */     (this.frames[3]).fmx = new int[] { 0, 1, 2, 3 };
/* 35 */     (this.frames[3]).fmy = 15;
/* 36 */     (this.frames[3]).offx = new int[] { 14, -12, 10, -10 };
/* 37 */     (this.frames[3]).offz = 20;
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
/* 56 */     if (this.timer < 25.6F) {
/* 57 */       this.player.v = 10.0F;
/* 58 */       this.active = this.frames[0];
/* 59 */     } else if (this.timer < 102.4F) {
/* 60 */       this.player.v = 40.0F * this.player.thrustX;
/* 61 */       this.active = this.frames[1];
/* 62 */     } else if (this.timer < 358.4F) {
/* 63 */       this.player.v = (140.0F + 0.5F * this.player.getSkillKeeper()) * this.player.thrustX;
/* 64 */       this.player.vz = 15.0F + 0.5F * this.player.getSkillKeeper() + 10.0F * this.player.thrustZ;
/* 65 */       this.active = this.frames[2];
/* 66 */     } else if (this.timer < 563.2F) {
/* 67 */       this.player.v = (50.0F + 0.5F * this.player.getSkillKeeper()) * this.player.thrustX;
/* 68 */       this.active = this.frames[3];
/* 69 */     } else if (this.timer < 691.2F) {
/* 70 */       this.player.v *= 0.90234375F;
/* 71 */       this.active = this.frames[4];
/*    */     } else {
/* 73 */       this.player.v *= 0.90234375F;
/*    */     } 
/*    */     
/* 76 */     int p = ((this.player.a == 180.0F) ? 1 : 0) + ((this.player.y > 0.0F) ? 2 : 0);
/* 77 */     this.player.fmx = this.active.fmx[p];
/* 78 */     this.player.fmy = this.active.fmy;
/* 79 */     this.player.holdBall(this.active.offx[p], this.active.offz);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 84 */     if (this.timer >= 793.6F) {
/* 85 */       if (this.ball.holder == this.player) {
/* 86 */         return this.fsm.stateKeeperKickAngle;
/*    */       }
/* 88 */       return this.fsm.stateKeeperPositioning;
/*    */     } 
/*    */     
/* 91 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {
/* 96 */     this.ball.setHolder(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKeeperDivingHighOne.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */