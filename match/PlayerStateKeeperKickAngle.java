/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateKeeperKickAngle
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateKeeperKickAngle(PlayerFsm fsm) {
/* 10 */     super(PlayerFsm.Id.STATE_KEEPER_KICK_ANGLE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 15 */     super.doActions();
/*    */ 
/*    */     
/* 18 */     if (this.player.inputDevice.y1 != this.ball.ySide && 
/* 19 */       this.player.inputDevice.value) {
/* 20 */       this.player.a = this.player.inputDevice.angle;
/*    */     }
/*    */ 
/*    */     
/* 24 */     this.ball.setPosition(this.player.x + 5.0F * EMath.cos(this.player.a), this.player.y + 5.0F * EMath.sin(this.player.a), 12.0F);
/*    */     
/* 26 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 31 */     if (this.player.inputDevice.fire1Down()) {
/* 32 */       this.player.kickAngle = this.player.a;
/* 33 */       return this.fsm.stateKick;
/*    */     } 
/* 35 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 40 */     super.entryActions();
/* 41 */     this.ball.setHolder(this.player);
/* 42 */     this.player.v = 0.0F;
/* 43 */     this.player.a = ((90 * this.ball.ySide + 180) % 360);
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {
/* 48 */     this.ball.setHolder(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKeeperKickAngle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */