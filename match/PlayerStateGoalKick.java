/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateGoalKick
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateGoalKick(PlayerFsm fsm) {
/* 10 */     super(PlayerFsm.Id.STATE_GOAL_KICK, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 15 */     super.doActions();
/*    */     
/* 17 */     this.scene.setBallOwner(this.player);
/*    */ 
/*    */     
/* 20 */     if (this.player.inputDevice.y1 != this.ball.ySide && 
/* 21 */       this.player.inputDevice.value) {
/* 22 */       this.player.a = this.player.inputDevice.angle;
/* 23 */       this.player.x = this.ball.x - 7.0F * EMath.cos(this.player.a) + 1.0F;
/* 24 */       this.player.y = this.ball.y - 7.0F * EMath.sin(this.player.a) + 1.0F;
/*    */     } 
/*    */ 
/*    */     
/* 28 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 33 */     if (this.player.inputDevice.fire1Down()) {
/* 34 */       this.player.kickAngle = this.player.a;
/* 35 */       return this.fsm.stateKick;
/*    */     } 
/* 37 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateGoalKick.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */