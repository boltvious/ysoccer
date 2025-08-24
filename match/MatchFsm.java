/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MatchFsm
/*     */   extends SceneFsm
/*     */ {
/*     */   boolean matchCompleted;
/*     */   final BenchStatus benchStatus;
/*     */   final Vector2 throwInPosition;
/*     */   Team throwInTeam;
/*     */   Team cornerKickTeam;
/*     */   Team goalKickTeam;
/*     */   static int STATE_BENCH_ENTER;
/*     */   static int STATE_BENCH_EXIT;
/*     */   static int STATE_BENCH_FORMATION;
/*     */   static int STATE_BENCH_SUBSTITUTIONS;
/*     */   static int STATE_BENCH_TACTICS;
/*     */   static int STATE_CORNER_KICK;
/*     */   static int STATE_CORNER_STOP;
/*     */   static int STATE_END;
/*     */   static int STATE_END_POSITIONS;
/*     */   static int STATE_EXTRA_TIME_STOP;
/*     */   static int STATE_FREE_KICK;
/*     */   static int STATE_FREE_KICK_STOP;
/*     */   static int STATE_FULL_EXTRA_TIME_STOP;
/*     */   static int STATE_FULL_TIME_STOP;
/*     */   static int STATE_GOAL;
/*     */   static int STATE_GOAL_KICK;
/*     */   static int STATE_GOAL_KICK_STOP;
/*     */   static int STATE_HALF_EXTRA_TIME_STOP;
/*     */   static int STATE_HALF_TIME_ENTER;
/*     */   static int STATE_HALF_TIME_POSITIONS;
/*     */   static int STATE_HALF_TIME_STOP;
/*     */   static int STATE_HALF_TIME_WAIT;
/*     */   static int STATE_HELP;
/*     */   static int STATE_HIGHLIGHTS;
/*     */   private static int STATE_INTRO;
/*     */   static int STATE_KEEPER_STOP;
/*     */   static int STATE_KICK_OFF;
/*     */   static int STATE_MAIN;
/*     */   static int STATE_PAUSE;
/*     */   static int STATE_PENALTIES;
/*     */   static int STATE_PENALTIES_END;
/*     */   static int STATE_PENALTIES_KICK;
/*     */   static int STATE_PENALTIES_STOP;
/*     */   static int STATE_PENALTY_KICK;
/*     */   static int STATE_PENALTY_KICK_STOP;
/*     */   static int STATE_REPLAY;
/*     */   static int STATE_STARTING_POSITIONS;
/*     */   static int STATE_THROW_IN;
/*     */   static int STATE_THROW_IN_STOP;
/*     */   
/*     */   MatchFsm(Match match) {
/*  62 */     super(match);
/*     */     
/*  64 */     setHotKeys(new MatchHotKeys(match));
/*  65 */     setSceneRenderer(new MatchRenderer(match.game.glGraphics, match));
/*     */     
/*  67 */     this.benchStatus = new BenchStatus();
/*  68 */     this.benchStatus.targetX = -650.0F + (getSceneRenderer()).screenWidth / (2 * (getSceneRenderer()).zoom) / 100.0F;
/*  69 */     this.benchStatus.targetY = -20.0F;
/*  70 */     this.throwInPosition = new Vector2();
/*     */     
/*  72 */     STATE_BENCH_ENTER = addState(new MatchStateBenchEnter(this));
/*  73 */     STATE_BENCH_EXIT = addState(new MatchStateBenchExit(this));
/*  74 */     STATE_BENCH_FORMATION = addState(new MatchStateBenchFormation(this));
/*  75 */     STATE_BENCH_SUBSTITUTIONS = addState(new MatchStateBenchSubstitutions(this));
/*  76 */     STATE_BENCH_TACTICS = addState(new MatchStateBenchTactics(this));
/*  77 */     STATE_CORNER_KICK = addState(new MatchStateCornerKick(this));
/*  78 */     STATE_CORNER_STOP = addState(new MatchStateCornerStop(this));
/*  79 */     STATE_END = addState(new MatchStateEnd(this));
/*  80 */     STATE_END_POSITIONS = addState(new MatchStateEndPositions(this));
/*  81 */     STATE_EXTRA_TIME_STOP = addState(new MatchStateExtraTimeStop(this));
/*  82 */     STATE_FREE_KICK = addState(new MatchStateFreeKick(this));
/*  83 */     STATE_FREE_KICK_STOP = addState(new MatchStateFreeKickStop(this));
/*  84 */     STATE_FULL_EXTRA_TIME_STOP = addState(new MatchStateFullExtraTimeStop(this));
/*  85 */     STATE_FULL_TIME_STOP = addState(new MatchStateFullTimeStop(this));
/*  86 */     STATE_GOAL = addState(new MatchStateGoal(this));
/*  87 */     STATE_GOAL_KICK = addState(new MatchStateGoalKick(this));
/*  88 */     STATE_GOAL_KICK_STOP = addState(new MatchStateGoalKickStop(this));
/*  89 */     STATE_HALF_EXTRA_TIME_STOP = addState(new MatchStateHalfExtraTimeStop(this));
/*  90 */     STATE_HALF_TIME_ENTER = addState(new MatchStateHalfTimeEnter(this));
/*  91 */     STATE_HALF_TIME_POSITIONS = addState(new MatchStateHalfTimePositions(this));
/*  92 */     STATE_HALF_TIME_STOP = addState(new MatchStateHalfTimeStop(this));
/*  93 */     STATE_HALF_TIME_WAIT = addState(new MatchStateHalfTimeWait(this));
/*  94 */     STATE_HELP = addState(new MatchStateHelp(this));
/*  95 */     STATE_HIGHLIGHTS = addState(new MatchStateHighlights(this));
/*  96 */     STATE_INTRO = addState(new MatchStateIntro(this));
/*  97 */     STATE_KEEPER_STOP = addState(new MatchStateKeeperStop(this));
/*  98 */     STATE_KICK_OFF = addState(new MatchStateKickOff(this));
/*  99 */     STATE_MAIN = addState(new MatchStateMain(this));
/* 100 */     STATE_PAUSE = addState(new MatchStatePause(this));
/* 101 */     STATE_PENALTIES = addState(new MatchStatePenalties(this));
/* 102 */     STATE_PENALTIES_END = addState(new MatchStatePenaltiesEnd(this));
/* 103 */     STATE_PENALTIES_KICK = addState(new MatchStatePenaltiesKick(this));
/* 104 */     STATE_PENALTIES_STOP = addState(new MatchStatePenaltiesStop(this));
/* 105 */     STATE_PENALTY_KICK = addState(new MatchStatePenaltyKick(this));
/* 106 */     STATE_PENALTY_KICK_STOP = addState(new MatchStatePenaltyKickStop(this));
/* 107 */     STATE_REPLAY = addState(new MatchStateReplay(this));
/* 108 */     STATE_STARTING_POSITIONS = addState(new MatchStateStartingPositions(this));
/* 109 */     STATE_THROW_IN = addState(new MatchStateThrowIn(this));
/* 110 */     STATE_THROW_IN_STOP = addState(new MatchStateThrowInStop(this));
/*     */   }
/*     */ 
/*     */   
/*     */   public void start() {
/* 115 */     pushAction(SceneFsm.ActionType.NEW_FOREGROUND, STATE_INTRO);
/* 116 */     pushAction(SceneFsm.ActionType.FADE_IN);
/*     */   }
/*     */   
/*     */   public MatchState getState() {
/* 120 */     return (MatchState)super.getState();
/*     */   }
/*     */   
/*     */   MatchState getHoldState() {
/* 124 */     return (MatchState)super.getHoldState();
/*     */   }
/*     */   
/*     */   public Match getMatch() {
/* 128 */     return (Match)getScene();
/*     */   }
/*     */   
/*     */   class BenchStatus {
/*     */     Team team;
/*     */     InputDevice inputDevice;
/*     */     float targetX;
/*     */     float targetY;
/* 136 */     final Vector2 oldTarget = new Vector2();
/*     */     int selectedPosition;
/* 138 */     int substPosition = -1;
/* 139 */     int swapPosition = -1;
/*     */     int selectedTactics;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchFsm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */