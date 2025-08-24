/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ class PlayerStatePenaltyKickSpeed
/*    */   extends PlayerState
/*    */ {
/*    */   private float kickAngle;
/*    */   private float kickSpin;
/*    */   private boolean endOfAfterEffect;
/*    */   
/*    */   PlayerStatePenaltyKickSpeed(PlayerFsm fsm) {
/* 15 */     super(PlayerFsm.Id.STATE_PENALTY_KICK_SPEED, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 20 */     super.entryActions();
/* 21 */     this.kickAngle = this.player.a;
/* 22 */     this.kickSpin = 0.0F;
/* 23 */     this.endOfAfterEffect = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 28 */     super.doActions();
/*    */     
/* 30 */     if (!this.endOfAfterEffect) {
/*    */       
/* 32 */       if (this.timer > 76.8F && !this.player.inputDevice.fire11) {
/* 33 */         this.endOfAfterEffect = true;
/*    */       }
/* 35 */       if (this.timer > 153.6F) {
/* 36 */         this.endOfAfterEffect = true;
/*    */       }
/*    */       
/* 39 */       float factor = 0.8F;
/* 40 */       if (this.player.inputDevice.value) {
/* 41 */         float angleDiff = (this.player.inputDevice.angle - this.kickAngle + 540.0F) % 360.0F - 180.0F;
/*    */         
/* 43 */         if (Math.abs(angleDiff) < 67.5F) {
/* 44 */           factor = 0.35F;
/*    */         
/*    */         }
/* 47 */         else if (Math.abs(angleDiff) > 112.5F) {
/* 48 */           factor = 1.0F;
/*    */         } 
/*    */ 
/*    */         
/* 52 */         if (Math.abs(angleDiff) > 22.5F && Math.abs(angleDiff) < 157.5F) {
/* 53 */           this.kickSpin += 0.09765625F * EMath.sgn(angleDiff);
/*    */         }
/*    */       } 
/*    */       
/* 57 */       if (this.endOfAfterEffect) {
/* 58 */         this.ball.v = 160.0F + 120.0F * factor + 300.0F * this.timer / 512.0F;
/* 59 */         this.ball.vz = factor * (100.0F + 350.0F * this.timer / 512.0F);
/*    */         
/* 61 */         this.ball.a = this.player.a;
/* 62 */         this.ball.s = this.kickSpin;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   PlayerState checkConditions() {
/* 69 */     if (this.timer > 179.2F) {
/* 70 */       return this.fsm.stateStandRun;
/*    */     }
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStatePenaltyKickSpeed.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */