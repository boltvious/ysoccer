/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateFreeKickSpeed
/*    */   extends PlayerState
/*    */ {
/*    */   private float kickAngle;
/*    */   private float kickSpin;
/*    */   private boolean thrown;
/*    */   
/*    */   PlayerStateFreeKickSpeed(PlayerFsm fsm) {
/* 14 */     super(PlayerFsm.Id.STATE_FREE_KICK_SPEED, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 19 */     super.entryActions();
/* 20 */     this.kickAngle = 45.0F * this.player.fmx;
/* 21 */     this.kickSpin = 0.0F;
/* 22 */     this.thrown = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 27 */     super.doActions();
/*    */     
/* 29 */     if (!this.thrown) {
/*    */       float angleDiff;
/* 31 */       if (this.timer > 76.8F && !this.player.inputDevice.fire11) {
/* 32 */         this.thrown = true;
/*    */       }
/* 34 */       if (this.timer > 153.6F) {
/* 35 */         this.thrown = true;
/*    */       }
/*    */ 
/*    */       
/* 39 */       if (this.player.inputDevice.value) {
/* 40 */         angleDiff = (this.player.inputDevice.angle - this.kickAngle + 540.0F) % 360.0F - 180.0F;
/*    */       } else {
/* 42 */         angleDiff = 90.0F;
/*    */       } 
/*    */ 
/*    */       
/* 46 */       if (this.player.inputDevice.value && 
/* 47 */         Math.abs(angleDiff) > 22.5F && Math.abs(angleDiff) < 157.5F) {
/* 48 */         this.kickSpin += 0.09765625F * EMath.sgn(angleDiff);
/*    */       }
/*    */ 
/*    */       
/* 52 */       if (this.thrown) {
/*    */         float factor;
/*    */ 
/*    */ 
/*    */         
/* 57 */         if (Math.abs(angleDiff) < 67.5F) {
/* 58 */           factor = 0.35F;
/*    */         
/*    */         }
/* 61 */         else if (Math.abs(angleDiff) < 112.5F) {
/* 62 */           factor = 1.0F;
/*    */         }
/*    */         else {
/*    */           
/* 66 */           factor = 1.3F;
/*    */         } 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 72 */         this.ball.v = 240.0F + 20.0F * factor + (250.0F + (10 * this.player.skills.shooting)) * this.timer / 512.0F;
/* 73 */         this.ball.vz = factor * (100.0F + 400.0F * this.timer / 512.0F);
/*    */         
/* 75 */         boolean longRange = (this.timer > 102.4F);
/*    */         
/* 77 */         if (longRange) {
/* 78 */           this.player.searchFarPassingMate();
/*    */         } else {
/* 80 */           this.player.searchPassingMate();
/*    */         } 
/*    */         
/* 83 */         if (this.player.passingMate != null && angleDiff == 0.0F) {
/* 84 */           this.ball.a = 45.0F * this.player.fmx + this.player.passingMateAngleCorrection;
/*    */         } else {
/* 86 */           this.ball.a = 45.0F * this.player.fmx;
/*    */         } 
/* 88 */         this.ball.s = this.kickSpin;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   PlayerState checkConditions() {
/* 95 */     if (this.timer > 179.2F) {
/* 96 */       return this.fsm.stateStandRun;
/*    */     }
/* 98 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateFreeKickSpeed.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */