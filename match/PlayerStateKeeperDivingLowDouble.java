/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStateKeeperDivingLowDouble
/*    */   extends PlayerState
/*    */ {
/*    */   private KeeperFrame active;
/*  8 */   private KeeperFrame[] frames = new KeeperFrame[3];
/*    */   
/*    */   PlayerStateKeeperDivingLowDouble(PlayerFsm fsm) {
/* 11 */     super(PlayerFsm.Id.STATE_KEEPER_DIVING_LOW_DOUBLE, fsm);
/*    */     
/* 13 */     this.active = this.frames[0];
/*    */     
/* 15 */     this.frames[0] = new KeeperFrame();
/* 16 */     (this.frames[0]).fmx = new int[] { 0, 1, 2, 3 };
/* 17 */     (this.frames[0]).fmy = 8;
/* 18 */     (this.frames[0]).offx = new int[] { 13, -10, 17, -13 };
/* 19 */     (this.frames[0]).offz = 0;
/*    */     
/* 21 */     this.frames[1] = new KeeperFrame();
/* 22 */     (this.frames[1]).fmx = new int[] { 4, 5, 6, 7 };
/* 23 */     (this.frames[1]).fmy = 12;
/* 24 */     (this.frames[1]).offx = new int[] { 13, -10, 17, -13 };
/* 25 */     (this.frames[1]).offz = 0;
/*    */     
/* 27 */     this.frames[2] = new KeeperFrame();
/* 28 */     (this.frames[2]).fmx = new int[] { 4, 5, 6, 7 };
/* 29 */     (this.frames[2]).fmy = 13;
/* 30 */     (this.frames[2]).offx = new int[] { 12, -9, 17, -13 };
/* 31 */     (this.frames[2]).offz = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 36 */     super.doActions();
/*    */ 
/*    */     
/* 39 */     if (this.ball.holder != this.player) {
/* 40 */       this.player.keeperCollision();
/*    */     }
/*    */ 
/*    */     
/* 44 */     if (this.timer < 51.2F) {
/* 45 */       this.player.v = 10.0F;
/* 46 */       this.active = this.frames[0];
/* 47 */     } else if (this.timer < 256.0F) {
/* 48 */       this.player.v = 20.0F + (145.0F + 0.5F * this.player.getSkillKeeper()) * this.player.thrustX;
/* 49 */       this.active = this.frames[1];
/* 50 */     } else if (this.timer < 281.6F) {
/* 51 */       this.player.v *= 0.90234375F;
/* 52 */       this.active = this.frames[2];
/*    */     } else {
/* 54 */       this.player.v *= 0.90234375F;
/*    */     } 
/*    */     
/* 57 */     int p = ((this.player.a == 180.0F) ? 1 : 0) + ((this.player.y > 0.0F) ? 2 : 0);
/* 58 */     this.player.fmx = this.active.fmx[p];
/* 59 */     this.player.fmy = this.active.fmy;
/* 60 */     this.player.holdBall(this.active.offx[p], this.active.offz);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 65 */     if (this.timer >= 384.0F) {
/* 66 */       if (this.ball.holder == this.player) {
/* 67 */         return this.fsm.stateKeeperKickAngle;
/*    */       }
/* 69 */       return this.fsm.stateKeeperPositioning;
/*    */     } 
/*    */     
/* 72 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {
/* 77 */     this.ball.setHolder(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKeeperDivingLowDouble.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */