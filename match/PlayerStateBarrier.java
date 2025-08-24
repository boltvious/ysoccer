/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ class PlayerStateBarrier
/*    */   extends PlayerState
/*    */ {
/*    */   private int ballMovingTime;
/*    */   private float exitDelay;
/*    */   
/*    */   PlayerStateBarrier(PlayerFsm fsm) {
/* 14 */     super(PlayerFsm.Id.STATE_BARRIER, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 19 */     super.entryActions();
/*    */     
/* 21 */     this.ballMovingTime = 0;
/* 22 */     this.exitDelay = 0.01F * EMath.rand(45, 55) * 512.0F;
/*    */     
/* 24 */     this.player.v = 0.0F;
/* 25 */     this.player.a = EMath.angle(0.0F, (640 * this.player.team.side), this.ball.x, this.ball.y);
/* 26 */     this.player.fmx = (Math.round((this.player.a + 360.0F) % 360.0F / 45.0F) % 8);
/* 27 */     this.player.fmy = 10.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 32 */     super.doActions();
/*    */     
/* 34 */     if (this.ballMovingTime == 0) {
/* 35 */       if (this.ball.v > 0.0F) {
/* 36 */         this.ballMovingTime = this.timer;
/*    */       }
/* 38 */     } else if (this.player.z == 0.0F && this.player.inputDevice.fire11) {
/* 39 */       this.player.vz = (60 + 5 * this.player.skills.heading);
/*    */     } 
/*    */     
/* 42 */     this.player.ballCollision();
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 47 */     if (this.ballMovingTime > 0 && (this.timer - this.ballMovingTime) > this.exitDelay && this.player.z == 0.0F)
/*    */     {
/*    */       
/* 50 */       return this.fsm.stateStandRun;
/*    */     }
/* 52 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateBarrier.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */