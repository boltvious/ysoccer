/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ class PlayerStateThrowInAngle
/*    */   extends PlayerState
/*    */ {
/*    */   private int animationCountdown;
/*    */   
/*    */   PlayerStateThrowInAngle(PlayerFsm fsm) {
/* 13 */     super(PlayerFsm.Id.STATE_THROW_IN_ANGLE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 18 */     super.entryActions();
/*    */     
/* 20 */     this.player.a = (45 * (2 * this.ball.xSide + 2));
/* 21 */     this.player.x = (this.ball.xSide * 516);
/* 22 */     this.player.fmx = (2 * this.ball.xSide + 2);
/* 23 */     this.player.fmy = 8.0F;
/* 24 */     this.scene.setBallOwner(this.player);
/* 25 */     this.animationCountdown = 512;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 30 */     super.doActions();
/* 31 */     if (this.animationCountdown > 0) {
/* 32 */       this.animationCountdown--;
/*    */     }
/*    */     
/* 35 */     int x = this.player.inputDevice.x1;
/* 36 */     int y = this.player.inputDevice.y1;
/*    */ 
/*    */     
/* 39 */     if (x != this.ball.xSide) {
/* 40 */       boolean value = (x != 0 || y != 0);
/* 41 */       int angle = Math.round(EMath.aTan2(y, x));
/*    */       
/* 43 */       if (value) {
/*    */         
/* 45 */         if (angle != this.player.a) {
/* 46 */           this.animationCountdown = 512;
/*    */         }
/* 48 */         this.player.a = angle;
/* 49 */         this.player.fmx = ((Math.round(angle / 45.0F) + 8) % 8);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 54 */     int a = 0;
/* 55 */     if (this.animationCountdown == 0) {
/* 56 */       a = (this.timer % 200 > 100) ? 1 : 0;
/*    */     }
/*    */     
/* 59 */     this.player.fmy = (8 + a);
/* 60 */     this.ball.setX(this.player.x + 6.0F * EMath.cos(this.player.a) * a);
/* 61 */     this.ball.setY(this.player.y + 6.0F * EMath.sin(this.player.a) * a);
/* 62 */     this.ball.setZ(24.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 67 */     if (this.player.inputDevice.fire1Down()) {
/* 68 */       return this.fsm.stateThrowInSpeed;
/*    */     }
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateThrowInAngle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */