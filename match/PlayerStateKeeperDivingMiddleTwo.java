/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ 
/*     */ class PlayerStateKeeperDivingMiddleTwo
/*     */   extends PlayerState
/*     */ {
/*     */   private KeeperFrame active;
/*   8 */   private KeeperFrame[] frames = new KeeperFrame[6];
/*     */   
/*     */   PlayerStateKeeperDivingMiddleTwo(PlayerFsm fsm) {
/*  11 */     super(PlayerFsm.Id.STATE_KEEPER_DIVING_MIDDLE_TWO, fsm);
/*     */     
/*  13 */     this.active = this.frames[0];
/*     */     
/*  15 */     this.frames[0] = new KeeperFrame();
/*  16 */     (this.frames[0]).fmx = new int[] { 4, 5, 6, 7 };
/*  17 */     (this.frames[0]).fmy = 8;
/*  18 */     (this.frames[0]).offx = new int[] { 16, -13, 16, -12 };
/*  19 */     (this.frames[0]).offz = 18;
/*     */     
/*  21 */     this.frames[1] = new KeeperFrame();
/*  22 */     (this.frames[1]).fmx = new int[] { 4, 5, 6, 7 };
/*  23 */     (this.frames[1]).fmy = 9;
/*  24 */     (this.frames[1]).offx = new int[] { 16, -13, 16, -12 };
/*  25 */     (this.frames[1]).offz = 18;
/*     */     
/*  27 */     this.frames[2] = new KeeperFrame();
/*  28 */     (this.frames[2]).fmx = new int[] { 4, 5, 6, 7 };
/*  29 */     (this.frames[2]).fmy = 10;
/*  30 */     (this.frames[2]).offx = new int[] { 16, -13, 16, -12 };
/*  31 */     (this.frames[2]).offz = 18;
/*     */     
/*  33 */     this.frames[3] = new KeeperFrame();
/*  34 */     (this.frames[3]).fmx = new int[] { 4, 5, 6, 7 };
/*  35 */     (this.frames[3]).fmy = 11;
/*  36 */     (this.frames[3]).offx = new int[] { 16, -13, 18, -14 };
/*  37 */     (this.frames[3]).offz = 15;
/*     */     
/*  39 */     this.frames[4] = new KeeperFrame();
/*  40 */     (this.frames[4]).fmx = new int[] { 4, 5, 6, 7 };
/*  41 */     (this.frames[4]).fmy = 12;
/*  42 */     (this.frames[4]).offx = new int[] { 13, -10, 17, -13 };
/*  43 */     (this.frames[4]).offz = 0;
/*     */     
/*  45 */     this.frames[5] = new KeeperFrame();
/*  46 */     (this.frames[5]).fmx = new int[] { 4, 5, 6, 7 };
/*  47 */     (this.frames[5]).fmy = 13;
/*  48 */     (this.frames[5]).offx = new int[] { 12, -9, 17, -13 };
/*  49 */     (this.frames[5]).offz = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions() {
/*  54 */     super.doActions();
/*     */ 
/*     */     
/*  57 */     if (this.ball.holder != this.player) {
/*  58 */       this.player.keeperCollision();
/*     */     }
/*     */ 
/*     */     
/*  62 */     if (this.timer < 25.6F) {
/*  63 */       this.player.v = 40.0F * this.player.thrustX;
/*  64 */       this.active = this.frames[0];
/*  65 */     } else if (this.timer < 51.2F) {
/*  66 */       this.player.v = 40.0F * this.player.thrustX;
/*  67 */       this.player.vz = 80.0F + 35.0F * this.player.thrustZ;
/*  68 */       this.active = this.frames[1];
/*  69 */     } else if (this.timer < 281.6F) {
/*  70 */       this.player.v = (120.0F + 0.5F * this.player.getSkillKeeper()) * this.player.thrustX;
/*  71 */       this.active = this.frames[2];
/*  72 */     } else if (this.timer < 358.4F) {
/*  73 */       this.player.v = 50.0F * this.player.thrustX;
/*  74 */       this.active = this.frames[3];
/*  75 */     } else if (this.timer < 409.6F) {
/*  76 */       this.player.v = 20.0F * this.player.thrustX;
/*  77 */       this.active = this.frames[4];
/*  78 */     } else if (this.timer < 460.8F) {
/*  79 */       this.player.v *= 0.90234375F;
/*  80 */       this.active = this.frames[5];
/*     */     } else {
/*  82 */       this.player.v *= 0.90234375F;
/*     */     } 
/*     */     
/*  85 */     int p = ((this.player.a == 180.0F) ? 1 : 0) + ((this.player.y > 0.0F) ? 2 : 0);
/*  86 */     this.player.fmx = this.active.fmx[p];
/*  87 */     this.player.fmy = this.active.fmy;
/*  88 */     this.player.holdBall(this.active.offx[p], this.active.offz);
/*     */   }
/*     */ 
/*     */   
/*     */   State checkConditions() {
/*  93 */     if (this.timer >= 563.2F) {
/*  94 */       if (this.ball.holder == this.player) {
/*  95 */         return this.fsm.stateKeeperKickAngle;
/*     */       }
/*  97 */       return this.fsm.stateKeeperPositioning;
/*     */     } 
/*     */     
/* 100 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   void exitActions() {
/* 105 */     this.ball.setHolder(null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKeeperDivingMiddleTwo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */