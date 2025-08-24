/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStatePenaltyKickAngle
/*    */   extends PlayerState
/*    */ {
/*    */   private float defaultAngle;
/*    */   
/*    */   PlayerStatePenaltyKickAngle(PlayerFsm fsm) {
/* 12 */     super(PlayerFsm.Id.STATE_PENALTY_KICK_ANGLE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 17 */     super.entryActions();
/*    */     
/* 19 */     this.scene.setBallOwner(this.player);
/* 20 */     this.defaultAngle = (90 * (2 - this.ball.ySide));
/* 21 */     this.player.a = this.defaultAngle;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 26 */     super.doActions();
/*    */     
/* 28 */     float turningSpeed = 0.5F;
/* 29 */     if (controlsAreTargetingTheGoal()) {
/* 30 */       moveToRequestedAngle(turningSpeed);
/*    */     } else {
/* 32 */       returnToDefaultAngle(turningSpeed);
/*    */     } 
/* 34 */     this.player.x = this.ball.x - 7.0F * EMath.cos(this.player.a);
/* 35 */     this.player.y = this.ball.y - 7.0F * EMath.sin(this.player.a);
/* 36 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 41 */     if (this.player.inputDevice.fire1Down()) {
/* 42 */       return this.fsm.statePenaltyKickSpeed;
/*    */     }
/* 44 */     return null;
/*    */   }
/*    */   
/*    */   private boolean controlsAreTargetingTheGoal() {
/* 48 */     return (this.player.inputDevice.y1 == this.ball.ySide);
/*    */   }
/*    */   
/*    */   private void moveToRequestedAngle(float turningSpeed) {
/* 52 */     int delta = 5;
/* 53 */     if (this.player.inputDevice.value) {
/* 54 */       int targetAngle = (this.player.inputDevice.angle + 360) % 360;
/* 55 */       switch (targetAngle) {
/*    */         case 45:
/* 57 */           this.player.a = Math.max(this.player.a - turningSpeed, 50.0F);
/*    */           break;
/*    */         case 135:
/* 60 */           this.player.a = Math.min(this.player.a + turningSpeed, 130.0F);
/*    */           break;
/*    */         case 225:
/* 63 */           this.player.a = Math.max(this.player.a - turningSpeed, 230.0F);
/*    */           break;
/*    */         case 315:
/* 66 */           this.player.a = Math.min(this.player.a + turningSpeed, 310.0F);
/*    */           break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void returnToDefaultAngle(float turningSpeed) {
/* 75 */     if (Math.abs(this.player.a - this.defaultAngle) > 1.0F)
/* 76 */       this.player.a -= turningSpeed * Math.signum(this.player.a - this.defaultAngle); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStatePenaltyKickAngle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */