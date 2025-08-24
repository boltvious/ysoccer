/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class AiStateIdle
/*    */   extends AiState
/*    */ {
/*    */   AiStateIdle(AiFsm fsm) {
/*  8 */     super(AiFsm.Id.STATE_IDLE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 13 */     super.doActions();
/* 14 */     this.ai.x0 = 0;
/* 15 */     this.ai.y0 = 0;
/* 16 */     this.ai.fire10 = false;
/* 17 */     this.ai.fire20 = false;
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 22 */     State playerState = this.player.getState();
/* 23 */     if (playerState != null) {
/* 24 */       switch (PlayerFsm.Id.values()[playerState.id]) {
/*    */         case STATE_KICK_OFF:
/* 26 */           return this.fsm.stateKickingOff;
/*    */         
/*    */         case STATE_STAND_RUN:
/* 29 */           return this.fsm.statePositioning;
/*    */         
/*    */         case STATE_GOAL_KICK:
/* 32 */           return this.fsm.stateGoalKicking;
/*    */         
/*    */         case STATE_THROW_IN_ANGLE:
/* 35 */           return this.fsm.stateThrowingIn;
/*    */         
/*    */         case STATE_CORNER_KICK_ANGLE:
/* 38 */           return this.fsm.stateCornerKicking;
/*    */         
/*    */         case STATE_FREE_KICK_ANGLE:
/* 41 */           return this.fsm.stateFreeKicking;
/*    */         
/*    */         case STATE_PENALTY_KICK_ANGLE:
/* 44 */           return this.fsm.statePenaltyKicking;
/*    */         
/*    */         case STATE_KEEPER_KICK_ANGLE:
/* 47 */           return this.fsm.stateKeeperKicking;
/*    */         
/*    */         case STATE_BARRIER:
/* 50 */           return this.fsm.stateBarrier;
/*    */       } 
/*    */     }
/* 53 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateIdle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */