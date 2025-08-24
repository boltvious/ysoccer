/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateFreeKickAngle
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateFreeKickAngle(PlayerFsm fsm) {
/* 10 */     super(PlayerFsm.Id.STATE_FREE_KICK_ANGLE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 15 */     super.entryActions();
/*    */     
/* 17 */     this.scene.setBallOwner(this.player);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 22 */     super.doActions();
/*    */     
/* 24 */     if (this.player.inputDevice.value) {
/* 25 */       this.player.a = this.player.inputDevice.angle;
/*    */     }
/* 27 */     this.player.x = this.ball.x - 7.0F * EMath.cos(this.player.a);
/* 28 */     this.player.y = this.ball.y - 7.0F * EMath.sin(this.player.a);
/* 29 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 34 */     if (this.player.inputDevice.fire1Down()) {
/* 35 */       return this.fsm.stateFreeKickSpeed;
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateFreeKickAngle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */