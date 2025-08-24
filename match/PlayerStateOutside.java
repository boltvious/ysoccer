/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateOutside
/*    */   extends PlayerState
/*    */ {
/*    */   private float v;
/*    */   
/*    */   PlayerStateOutside(PlayerFsm fsm) {
/* 12 */     super(PlayerFsm.Id.STATE_OUTSIDE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 17 */     super.entryActions();
/*    */     
/* 19 */     this.v = (180 + EMath.rand(0, 30));
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 24 */     super.doActions();
/* 25 */     if (this.player.isVisible && this.player.targetDistance() > 1.5F) {
/* 26 */       this.player.v = this.v;
/* 27 */       this.player.a = this.player.targetAngle();
/*    */     } else {
/* 29 */       this.player.v = 0.0F;
/* 30 */       this.player.isVisible = false;
/*    */     } 
/*    */ 
/*    */     
/* 34 */     this.player.fmx = (Math.round((this.player.a + 360.0F) % 360.0F / 45.0F) % 8);
/* 35 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {
/* 40 */     super.exitActions();
/* 41 */     this.player.isVisible = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateOutside.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */