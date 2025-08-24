/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateGoalMate
/*    */   extends PlayerState
/*    */ {
/*    */   private Goal goal;
/*    */   
/*    */   PlayerStateGoalMate(PlayerFsm fsm) {
/* 12 */     super(PlayerFsm.Id.STATE_GOAL_MATE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 17 */     super.entryActions();
/* 18 */     this.goal = (this.player.getMatch()).goals.get((this.player.getMatch()).goals.size() - 1);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 23 */     super.doActions();
/*    */     
/* 25 */     this.player.tx = this.goal.player.x + (10 + this.player.number % 20) * EMath.cos((30 * this.player.number));
/* 26 */     this.player.ty = this.goal.player.y + (10 + this.player.number % 20) * EMath.sin((30 * this.player.number));
/*    */     
/* 28 */     if (this.player.targetDistance() < 1.5F) {
/* 29 */       this.player.v = 0.0F;
/*    */     } else {
/* 31 */       this.player.v = this.player.speed;
/* 32 */       this.player.a = this.player.targetAngle();
/*    */     } 
/* 34 */     this.player.animationStandRun();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateGoalMate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */