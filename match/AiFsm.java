/*    */ package com.ygames.ysoccer.match;public class AiFsm extends Fsm { Ai ai; AiState stateBarrier; AiState stateIdle; AiState stateKickingOff; AiState statePositioning; AiState stateSeeking; AiState stateDefending; AiState stateAttacking; AiState statePassing; AiState stateKicking; AiState stateGoalKicking;
/*    */   AiState stateThrowingIn;
/*    */   AiState stateCornerKicking;
/*    */   AiState stateFreeKicking;
/*    */   AiState statePenaltyKicking;
/*    */   AiState stateKeeperKicking;
/*    */   
/*  8 */   enum Id { AI_STATE_BARRIER,
/*  9 */     STATE_IDLE,
/* 10 */     STATE_KICKING_OFF,
/* 11 */     STATE_POSITIONING,
/* 12 */     STATE_SEEKING,
/* 13 */     STATE_DEFENDING,
/* 14 */     STATE_ATTACKING,
/* 15 */     STATE_PASSING,
/* 16 */     STATE_KICKING,
/* 17 */     STATE_GOAL_KICKING,
/* 18 */     STATE_THROWING_IN,
/* 19 */     STATE_CORNER_KICKING,
/* 20 */     STATE_FREE_KICKING,
/* 21 */     STATE_PENALTY_KICKING,
/* 22 */     STATE_KEEPER_KICKING; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AiFsm(Ai ai) {
/* 44 */     this.ai = ai;
/*    */     
/* 46 */     this.stateBarrier = new AiStateBarrier(this);
/* 47 */     this.stateIdle = new AiStateIdle(this);
/* 48 */     this.stateKickingOff = new AiStateKickingOff(this);
/* 49 */     this.statePositioning = new AiStatePositioning(this);
/* 50 */     this.stateSeeking = new AiStateSeeking(this);
/* 51 */     this.stateDefending = new AiStateDefending(this);
/* 52 */     this.stateAttacking = new AiStateAttacking(this);
/* 53 */     this.statePassing = new AiStatePassing(this);
/* 54 */     this.stateKicking = new AiStateKicking(this);
/* 55 */     this.stateGoalKicking = new AiStateGoalKicking(this);
/* 56 */     this.stateThrowingIn = new AiStateThrowingIn(this);
/* 57 */     this.stateCornerKicking = new AiStateCornerKicking(this);
/* 58 */     this.stateFreeKicking = new AiStateFreeKicking(this);
/* 59 */     this.statePenaltyKicking = new AiStatePenaltyKicking(this);
/* 60 */     this.stateKeeperKicking = new AiStateKeeperKicking(this);
/*    */     
/* 62 */     setState(Id.STATE_IDLE);
/*    */   }
/*    */   
/*    */   void setState(Id id) {
/* 66 */     super.setState(id.ordinal());
/*    */   } }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiFsm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */