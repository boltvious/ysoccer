/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateCornerKickAngle
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateCornerKickAngle(PlayerFsm fsm) {
/* 10 */     super(PlayerFsm.Id.STATE_CORNER_KICK_ANGLE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 15 */     super.entryActions();
/*    */     
/* 17 */     this.scene.setBallOwner(this.player);
/* 18 */     this.player.a = (90 * (1 + this.ball.xSide));
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 23 */     super.doActions();
/*    */     
/* 25 */     int x = this.player.inputDevice.x1;
/* 26 */     int y = this.player.inputDevice.y1;
/*    */ 
/*    */     
/* 29 */     if (x != this.ball.xSide && y != this.ball.ySide) {
/* 30 */       boolean value = (x != 0 || y != 0);
/* 31 */       float angle = EMath.aTan2(y, x);
/*    */       
/* 33 */       if (value) {
/* 34 */         this.player.a = angle;
/*    */       }
/*    */     } 
/* 37 */     this.player.x = this.ball.x - 7.0F * EMath.cos(this.player.a);
/* 38 */     this.player.y = this.ball.y - 7.0F * EMath.sin(this.player.a);
/* 39 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 44 */     if (this.player.inputDevice.fire1Down()) {
/* 45 */       return this.fsm.stateCornerKickSpeed;
/*    */     }
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateCornerKickAngle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */