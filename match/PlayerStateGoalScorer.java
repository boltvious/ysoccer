/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ 
/*     */ 
/*     */ class PlayerStateGoalScorer
/*     */   extends PlayerState
/*     */ {
/*     */   PlayerStateGoalScorer(PlayerFsm fsm) {
/*  11 */     super(PlayerFsm.Id.STATE_GOAL_SCORER, fsm);
/*     */   }
/*     */   
/*     */   void entryActions() {
/*     */     int celebrationType;
/*  16 */     super.entryActions();
/*  17 */     boolean[] disallowed = new boolean[8];
/*     */ 
/*     */     
/*  20 */     if (this.player.y + 640.0F < 375.0F) {
/*  21 */       disallowed[6] = true;
/*  22 */       if (this.player.x < 0.0F) {
/*  23 */         disallowed[7] = true;
/*     */       } else {
/*  25 */         disallowed[5] = true;
/*     */       } 
/*     */     } 
/*  28 */     if (this.player.y + 640.0F < 220.0F) {
/*  29 */       disallowed[5] = true;
/*  30 */       disallowed[6] = true;
/*  31 */       disallowed[7] = true;
/*  32 */       if (this.player.x < 0.0F) {
/*  33 */         disallowed[0] = true;
/*     */       } else {
/*  35 */         disallowed[4] = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  40 */     if (640.0F - this.player.y < 375.0F) {
/*  41 */       disallowed[2] = true;
/*  42 */       if (this.player.x < 0.0F) {
/*  43 */         disallowed[1] = true;
/*     */       } else {
/*  45 */         disallowed[3] = true;
/*     */       } 
/*     */     } 
/*  48 */     if (640.0F - this.player.y < 220.0F) {
/*  49 */       disallowed[1] = true;
/*  50 */       disallowed[2] = true;
/*  51 */       disallowed[3] = true;
/*  52 */       if (this.player.x < 0.0F) {
/*  53 */         disallowed[0] = true;
/*     */       } else {
/*  55 */         disallowed[4] = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  60 */     if (this.player.x + 510.0F < 340.0F) {
/*  61 */       disallowed[4] = true;
/*     */     }
/*  63 */     if (this.player.x + 510.0F < 200.0F) {
/*  64 */       disallowed[3] = true;
/*  65 */       disallowed[4] = true;
/*  66 */       disallowed[5] = true;
/*     */     } 
/*     */ 
/*     */     
/*  70 */     if (510.0F - this.player.x < 340.0F) {
/*  71 */       disallowed[0] = true;
/*     */     }
/*  73 */     if (510.0F - this.player.x < 200.0F) {
/*  74 */       disallowed[7] = true;
/*  75 */       disallowed[0] = true;
/*  76 */       disallowed[1] = true;
/*     */     } 
/*     */ 
/*     */     
/*     */     do {
/*  81 */       celebrationType = Assets.random.nextInt(8);
/*  82 */     } while (disallowed[celebrationType]);
/*     */     
/*  84 */     this.player.v = this.player.speed;
/*  85 */     this.player.a = (45 * celebrationType);
/*     */     
/*  87 */     float d = 500.0F;
/*     */ 
/*     */     
/*  90 */     if (Math.abs(EMath.cos(this.player.a)) > 0.002F) {
/*  91 */       float tx = this.player.x + d * EMath.cos(this.player.a);
/*  92 */       tx = EMath.sgn(tx) * Math.min(Math.abs(tx), (530 - 2 * this.player.number));
/*  93 */       d = Math.min(d, (tx - this.player.x) / EMath.cos(this.player.a));
/*     */     } 
/*     */ 
/*     */     
/*  97 */     if (Math.abs(EMath.sin(this.player.a)) > 0.002F) {
/*  98 */       float ty = this.player.y + d * EMath.sin(this.player.a);
/*  99 */       ty = EMath.sgn(ty) * Math.min(Math.abs(ty), (650 - this.player.number));
/* 100 */       d = Math.min(d, (ty - this.player.y) / EMath.sin(this.player.a));
/*     */     } 
/*     */     
/* 103 */     this.player.tx = this.player.x + d * EMath.cos(this.player.a);
/* 104 */     this.player.ty = this.player.y + d * EMath.sin(this.player.a);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions() {
/* 109 */     super.doActions();
/*     */ 
/*     */     
/* 112 */     if (this.player.targetDistance() < 1.5F) {
/* 113 */       this.player.v = 0.0F;
/*     */     }
/* 115 */     this.player.animationScorer();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateGoalScorer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */