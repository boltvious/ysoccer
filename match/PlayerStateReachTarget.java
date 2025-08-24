/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateReachTarget
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateReachTarget(PlayerFsm fsm) {
/* 10 */     super(PlayerFsm.Id.STATE_REACH_TARGET, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 15 */     super.entryActions();
/*    */     
/* 17 */     this.player.v = (180 + EMath.rand(0, 30));
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 22 */     super.doActions();
/*    */     
/* 24 */     this.player.a = this.player.targetAngle();
/* 25 */     this.player.animationStandRun();
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 30 */     if (this.player.targetDistance() < 1.0F) {
/* 31 */       return this.fsm.stateIdle;
/*    */     }
/* 33 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {
/* 38 */     this.player.v = 0.0F;
/* 39 */     this.player.watchPosition(this.player.scene.pointOfInterest);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateReachTarget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */