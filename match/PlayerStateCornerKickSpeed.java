/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ 
/*     */ 
/*     */ class PlayerStateCornerKickSpeed
/*     */   extends PlayerState
/*     */ {
/*     */   private float kickAngle;
/*     */   private float kickSpin;
/*     */   private boolean thrown;
/*     */   
/*     */   PlayerStateCornerKickSpeed(PlayerFsm fsm) {
/*  14 */     super(PlayerFsm.Id.STATE_CORNER_KICK_SPEED, fsm);
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  19 */     super.entryActions();
/*  20 */     this.kickAngle = 45.0F * this.player.fmx;
/*  21 */     this.kickSpin = 0.0F;
/*  22 */     this.thrown = false;
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions() {
/*  27 */     super.doActions();
/*     */     
/*  29 */     if (!this.thrown) {
/*     */       float angleDiff;
/*  31 */       if (this.timer > 76.8F && !this.player.inputDevice.fire11) {
/*  32 */         this.thrown = true;
/*     */       }
/*  34 */       if (this.timer > 153.6F) {
/*  35 */         this.thrown = true;
/*     */       }
/*     */ 
/*     */       
/*  39 */       if (this.player.inputDevice.value) {
/*  40 */         angleDiff = (this.player.inputDevice.angle - this.kickAngle + 540.0F) % 360.0F - 180.0F;
/*     */       } else {
/*  42 */         angleDiff = 90.0F;
/*     */       } 
/*     */ 
/*     */       
/*  46 */       if (this.player.inputDevice.value && 
/*  47 */         Math.abs(angleDiff) > 22.5F && Math.abs(angleDiff) < 157.5F) {
/*  48 */         this.kickSpin += 0.09765625F * EMath.sgn(angleDiff);
/*     */       }
/*     */ 
/*     */       
/*  52 */       if (this.thrown) {
/*     */         float f;
/*     */         
/*  55 */         if (Math.abs(angleDiff) < 67.5F) {
/*  56 */           f = 0.0F;
/*  57 */         } else if (Math.abs(angleDiff) < 112.5F) {
/*  58 */           f = 1.0F;
/*     */         } else {
/*  60 */           f = 1.3F;
/*     */         } 
/*  62 */         this.ball.v = 160.0F + 160.0F * f + (300 * this.timer / 512);
/*  63 */         this.ball.vz = f * (100 + 400 * this.timer / 512);
/*     */         
/*  65 */         boolean longRange = (this.timer > 102.4F);
/*     */         
/*  67 */         if (longRange) {
/*  68 */           this.player.searchFarPassingMate();
/*     */         } else {
/*  70 */           this.player.searchPassingMate();
/*     */         } 
/*     */         
/*  73 */         if (this.player.passingMate != null && angleDiff == 0.0F) {
/*  74 */           this.ball.a = 45.0F * this.player.fmx + this.player.passingMateAngleCorrection;
/*     */         } else {
/*  76 */           switch (Math.round(this.player.fmx)) {
/*     */             case 0:
/*  78 */               this.ball.a = (0 - 5 * this.ball.ySide);
/*     */               break;
/*     */             case 2:
/*  81 */               this.ball.a = (90 + 5 * this.ball.xSide);
/*     */               break;
/*     */             case 4:
/*  84 */               this.ball.a = (180 + 5 * this.ball.ySide);
/*     */               break;
/*     */             case 6:
/*  87 */               this.ball.a = (270 - 5 * this.ball.xSide);
/*     */               break;
/*     */             default:
/*  90 */               this.ball.a = 45.0F * this.player.fmx;
/*     */               break;
/*     */           } 
/*     */         } 
/*  94 */         this.ball.s = this.kickSpin;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   PlayerState checkConditions() {
/* 101 */     if (this.timer > 179.2F) {
/* 102 */       return this.fsm.stateStandRun;
/*     */     }
/* 104 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateCornerKickSpeed.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */