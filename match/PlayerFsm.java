/*     */ package com.ygames.ysoccer.match;class PlayerFsm extends Fsm { protected Player player; PlayerState stateIdle; PlayerState stateOutSide; PlayerState stateBenchSitting; PlayerState stateBenchStanding; PlayerState stateBenchOut; PlayerState statePhoto; PlayerState stateStandRun; PlayerState stateKick; PlayerState stateHead; PlayerState stateTackle; PlayerState stateDown; PlayerState stateReachTarget; PlayerState stateKickOff; PlayerState stateNotResponsive; PlayerState stateGoalKick; PlayerState stateThrowInAngle; PlayerState stateThrowInSpeed; PlayerState stateCornerKickAngle; PlayerState stateCornerKickSpeed; PlayerState stateFreeKickAngle; PlayerState stateFreeKickSpeed; PlayerState stateBarrier; PlayerState statePenaltyKickAngle; PlayerState statePenaltyKickSpeed; PlayerState stateGoalScorer; PlayerState stateGoalMate; PlayerState stateOwnGoalScorer; PlayerState stateKeeperKickAngle; PlayerState stateKeeperPositioning; PlayerState stateKeeperPenaltyPositioning; PlayerState stateKeeperDivingLowSingle; PlayerState stateKeeperDivingLowDouble; PlayerState stateKeeperDivingMiddleOne; PlayerState stateKeeperDivingMiddleTwo;
/*     */   PlayerState stateKeeperDivingHighOne;
/*     */   PlayerState stateKeeperCatchingHigh;
/*     */   PlayerState stateKeeperCatchingLow;
/*     */   
/*   6 */   enum Id { STATE_IDLE,
/*   7 */     STATE_OUTSIDE,
/*   8 */     STATE_BENCH_SITTING,
/*   9 */     STATE_BENCH_STANDING,
/*  10 */     STATE_BENCH_OUT,
/*     */     
/*  12 */     STATE_PHOTO,
/*  13 */     STATE_STAND_RUN,
/*  14 */     STATE_KICK,
/*  15 */     STATE_HEAD,
/*  16 */     STATE_TACKLE,
/*  17 */     STATE_DOWN,
/*  18 */     STATE_REACH_TARGET,
/*  19 */     STATE_KICK_OFF,
/*  20 */     STATE_NOT_RESPONSIVE,
/*  21 */     STATE_GOAL_KICK,
/*  22 */     STATE_THROW_IN_ANGLE,
/*  23 */     STATE_THROW_IN_SPEED,
/*  24 */     STATE_CORNER_KICK_ANGLE,
/*  25 */     STATE_CORNER_KICK_SPEED,
/*  26 */     STATE_FREE_KICK_ANGLE,
/*  27 */     STATE_FREE_KICK_SPEED,
/*  28 */     STATE_BARRIER,
/*  29 */     STATE_PENALTY_KICK_ANGLE,
/*  30 */     STATE_PENALTY_KICK_SPEED,
/*  31 */     STATE_GOAL_SCORER,
/*  32 */     STATE_GOAL_MATE,
/*  33 */     STATE_OWN_GOAL_SCORER,
/*     */     
/*  35 */     STATE_KEEPER_POSITIONING,
/*  36 */     STATE_KEEPER_PENALTY_POSITIONING,
/*  37 */     STATE_KEEPER_DIVING_LOW_SINGLE,
/*  38 */     STATE_KEEPER_DIVING_LOW_DOUBLE,
/*  39 */     STATE_KEEPER_DIVING_MIDDLE_ONE,
/*  40 */     STATE_KEEPER_DIVING_MIDDLE_TWO,
/*  41 */     STATE_KEEPER_DIVING_HIGH_ONE,
/*  42 */     STATE_KEEPER_CATCHING_HIGH,
/*  43 */     STATE_KEEPER_CATCHING_LOW,
/*  44 */     STATE_KEEPER_KICK_ANGLE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerFsm(Player player) {
/*  90 */     this.player = player;
/*  91 */     this.stateIdle = new PlayerStateIdle(this);
/*  92 */     this.stateOutSide = new PlayerStateOutside(this);
/*  93 */     this.stateBenchSitting = new PlayerStateBenchSitting(this);
/*  94 */     this.stateBenchStanding = new PlayerStateBenchStanding(this);
/*  95 */     this.stateBenchOut = new PlayerStateBenchOut(this);
/*     */     
/*  97 */     this.statePhoto = new PlayerStatePhoto(this);
/*  98 */     this.stateStandRun = new PlayerStateStandRun(this);
/*  99 */     this.stateKick = new PlayerStateKick(this);
/* 100 */     this.stateHead = new PlayerStateHead(this);
/* 101 */     this.stateTackle = new PlayerStateTackle(this);
/* 102 */     this.stateDown = new PlayerStateDown(this);
/* 103 */     this.stateReachTarget = new PlayerStateReachTarget(this);
/* 104 */     this.stateKickOff = new PlayerStateKickOff(this);
/* 105 */     this.stateNotResponsive = new PlayerStateNotResponsive(this);
/* 106 */     this.stateGoalKick = new PlayerStateGoalKick(this);
/* 107 */     this.stateThrowInAngle = new PlayerStateThrowInAngle(this);
/* 108 */     this.stateThrowInSpeed = new PlayerStateThrowInSpeed(this);
/* 109 */     this.stateCornerKickAngle = new PlayerStateCornerKickAngle(this);
/* 110 */     this.stateCornerKickSpeed = new PlayerStateCornerKickSpeed(this);
/* 111 */     this.stateFreeKickAngle = new PlayerStateFreeKickAngle(this);
/* 112 */     this.stateFreeKickSpeed = new PlayerStateFreeKickSpeed(this);
/* 113 */     this.stateBarrier = new PlayerStateBarrier(this);
/* 114 */     this.statePenaltyKickAngle = new PlayerStatePenaltyKickAngle(this);
/* 115 */     this.statePenaltyKickSpeed = new PlayerStatePenaltyKickSpeed(this);
/* 116 */     this.stateGoalScorer = new PlayerStateGoalScorer(this);
/* 117 */     this.stateGoalMate = new PlayerStateGoalMate(this);
/* 118 */     this.stateOwnGoalScorer = new PlayerStateOwnGoalScorer(this);
/*     */     
/* 120 */     this.stateKeeperPositioning = new PlayerStateKeeperPositioning(this);
/* 121 */     this.stateKeeperPenaltyPositioning = new PlayerStateKeeperPenaltyPositioning(this);
/* 122 */     this.stateKeeperDivingLowSingle = new PlayerStateKeeperDivingLowSingle(this);
/* 123 */     this.stateKeeperDivingLowDouble = new PlayerStateKeeperDivingLowDouble(this);
/* 124 */     this.stateKeeperDivingMiddleOne = new PlayerStateKeeperDivingMiddleOne(this);
/* 125 */     this.stateKeeperDivingMiddleTwo = new PlayerStateKeeperDivingMiddleTwo(this);
/* 126 */     this.stateKeeperDivingHighOne = new PlayerStateKeeperDivingHighOne(this);
/* 127 */     this.stateKeeperCatchingHigh = new PlayerStateKeeperCatchingHigh(this);
/* 128 */     this.stateKeeperCatchingLow = new PlayerStateKeeperCatchingLow(this);
/* 129 */     this.stateKeeperKickAngle = new PlayerStateKeeperKickAngle(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerState getState() {
/* 134 */     return (PlayerState)this.state;
/*     */   }
/*     */   
/*     */   void setState(Id id) {
/* 138 */     setState(id.ordinal());
/*     */   } }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerFsm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */