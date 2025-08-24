/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStateOwnGoalScorer
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStateOwnGoalScorer(PlayerFsm fsm) {
/*  8 */     super(PlayerFsm.Id.STATE_OWN_GOAL_SCORER, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 13 */     super.entryActions();
/* 14 */     this.player.v = 0.0F;
/* 15 */     if (this.player.role == Player.Role.GOALKEEPER) {
/* 16 */       this.player.fmx = (2 + 2 * (1 - this.player.ball.ySide));
/* 17 */       this.player.fmy = 1.0F;
/*    */     } else {
/* 19 */       this.player.fmy = (14 + (1 - this.player.ball.ySide) / 2);
/* 20 */       this.player.fmx = 3.0F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateOwnGoalScorer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */