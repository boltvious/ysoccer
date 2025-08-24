/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonValue;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class Match
/*     */   extends Scene
/*     */   implements Json.Serializable
/*     */ {
/*     */   public static final int HOME = 0;
/*     */   public static final int AWAY = 1;
/*     */   
/*     */   public enum ResultType
/*     */   {
/*  40 */     AFTER_90_MINUTES, AFTER_EXTRA_TIME, AFTER_PENALTIES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   public final int[] teams = new int[] { -1, -1 }; public Scorers scorers; public int[] resultAfter90; public int[] resultAfterExtraTime; public int[] resultAfterPenalties; Ball ball; float lastGoalCollisionTime; public final Team[] team; int benchSide; public MatchListener listener; public Competition competition;
/*  49 */   public MatchStats[] stats = new MatchStats[] { new MatchStats(), new MatchStats() };
/*     */   
/*     */   public float clock;
/*     */   
/*     */   int length;
/*     */   Period period;
/*     */   int coinToss;
/*     */   int kickOffTeam;
/*     */   int penaltyKickingTeam;
/*     */   Tackle tackle;
/*     */   Foul foul;
/*     */   List<Goal> goals;
/*     */   
/*     */   enum Period
/*     */   {
/*  64 */     UNDEFINED, FIRST_HALF, SECOND_HALF, FIRST_EXTRA_TIME, SECOND_EXTRA_TIME, PENALTIES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   enum PenaltyState
/*     */   {
/*  77 */     TO_KICK, SCORED, MISSED;
/*     */   }
/*  79 */   final ArrayList<Penalty>[] penalties = (ArrayList<Penalty>[])new ArrayList[2]; Penalty penalty; Player penaltyScorer;
/*     */   boolean chantSwitch;
/*     */   float nextChant;
/*     */   Recorder recorder;
/*     */   
/*     */   class Penalty { Match.PenaltyState state;
/*     */     final Player kicker;
/*     */     final Player keeper;
/*     */     final int side;
/*     */     
/*     */     Penalty(Player kicker, Player keeper, int side) {
/*  90 */       this.state = Match.PenaltyState.TO_KICK;
/*  91 */       this.kicker = kicker;
/*  92 */       this.keeper = keeper;
/*  93 */       this.side = side;
/*     */     }
/*     */     
/*     */     void setState(Match.PenaltyState state) {
/*  97 */       this.state = state;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Match() {
/* 107 */     this.team = new Team[2];
/*     */   }
/*     */   
/*     */   public MatchFsm getFsm() {
/* 111 */     return (MatchFsm)this.fsm;
/*     */   }
/*     */   
/*     */   public MatchSettings getSettings() {
/* 115 */     return (MatchSettings)this.settings;
/*     */   }
/*     */   
/*     */   public void setTeam(int side, Team team) {
/* 119 */     this.team[side] = team;
/* 120 */     (this.team[side]).index = side;
/*     */   }
/*     */   
/*     */   public void init(GLGame game, MatchSettings matchSettings, Competition competition) {
/* 124 */     this.game = game;
/* 125 */     this.settings = matchSettings;
/* 126 */     this.competition = competition;
/*     */     
/* 128 */     this.scorers = new Scorers();
/* 129 */     this.ball = new Ball(matchSettings);
/*     */     
/* 131 */     for (int t = 0; t <= 1; t++) {
/* 132 */       (this.team[t]).index = t;
/* 133 */       this.team[t].beforeMatch(this);
/*     */     } 
/*     */     
/* 136 */     this.fsm = new MatchFsm(this);
/*     */     
/* 138 */     this.team[0].setSide(1 - 2 * Assets.random.nextInt(2));
/* 139 */     this.team[1].setSide(-(this.team[0]).side);
/*     */     
/* 141 */     this.benchSide = 1 - 2 * Assets.random.nextInt(2);
/*     */     
/* 143 */     this.period = Period.FIRST_HALF;
/* 144 */     this.length = matchSettings.matchLength * 60 * 1000;
/* 145 */     this.coinToss = Assets.random.nextInt(2);
/* 146 */     this.kickOffTeam = this.coinToss;
/*     */     
/* 148 */     this.goals = new ArrayList<>();
/* 149 */     this.penalties[0] = new ArrayList<>();
/* 150 */     this.penalties[1] = new ArrayList<>();
/*     */     
/* 152 */     this.recorder = new Recorder(this);
/* 153 */     this.pointOfInterest = new Vector2();
/*     */   }
/*     */   
/*     */   void createPenalty(Player player, Player keeper, int side) {
/* 157 */     this.penalty = new Penalty(player, keeper, side);
/*     */   }
/*     */   
/*     */   void addPenalties(int n) {
/* 161 */     for (int t = 0; t <= 1; t++) {
/* 162 */       int newSize = this.penalties[t].size() + n;
/* 163 */       while (this.penalties[t].size() < newSize) {
/* 164 */         Player kicker = this.team[t].lineupAtPosition((this.team[t]).kickerIndex);
/* 165 */         Player keeper = this.team[1 - t].lineupAtPosition(0);
/* 166 */         if (!kicker.checkState(PlayerFsm.Id.STATE_OUTSIDE)) {
/* 167 */           this.penalties[t].add(new Penalty(kicker, keeper, -1));
/*     */         }
/* 169 */         (this.team[t]).kickerIndex = EMath.rotate((this.team[t]).kickerIndex, 0, (this.team[t]).lineup.size() - 1, -1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void nextPenalty() {
/* 175 */     this.penalty = null;
/* 176 */     for (Penalty penalty : this.penalties[this.penaltyKickingTeam]) {
/* 177 */       if (penalty.state == PenaltyState.TO_KICK) {
/* 178 */         this.penalty = penalty;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   int penaltyGoals(int t) {
/* 185 */     int goals = 0;
/* 186 */     for (Penalty penalty : this.penalties[t]) {
/* 187 */       if (penalty.state == PenaltyState.SCORED) goals++; 
/*     */     } 
/* 189 */     return goals;
/*     */   }
/*     */   
/*     */   int penaltiesScore(int t) {
/* 193 */     int s = 0;
/* 194 */     for (Penalty penalty : this.penalties[t]) {
/* 195 */       if (penalty.state == PenaltyState.SCORED) {
/* 196 */         s++;
/*     */       }
/*     */     } 
/* 199 */     return s;
/*     */   }
/*     */   
/*     */   int penaltiesPotentialScore(int t) {
/* 203 */     int s = 0;
/* 204 */     for (Penalty penalty : this.penalties[t]) {
/* 205 */       if (penalty.state == PenaltyState.SCORED || penalty.state == PenaltyState.TO_KICK) {
/* 206 */         s++;
/*     */       }
/*     */     } 
/* 209 */     return s;
/*     */   }
/*     */   
/*     */   int penaltiesLeft(int t) {
/* 213 */     int s = 0;
/* 214 */     for (Penalty penalty : this.penalties[t]) {
/* 215 */       if (penalty.state == PenaltyState.TO_KICK) {
/* 216 */         s++;
/*     */       }
/*     */     } 
/* 219 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/* 224 */     json.readFields(this, jsonData);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/* 229 */     json.writeValue("teams", this.teams);
/* 230 */     if (this.resultAfter90 != null) {
/* 231 */       json.writeValue("resultAfter90", this.resultAfter90);
/*     */     }
/* 233 */     if (this.resultAfterExtraTime != null) {
/* 234 */       json.writeValue("resultAfterExtraTime", this.resultAfterExtraTime);
/*     */     }
/* 236 */     if (this.resultAfterPenalties != null) {
/* 237 */       json.writeValue("resultAfterPenalties", this.resultAfterPenalties);
/*     */     }
/*     */   }
/*     */   
/*     */   public int[] getResult() {
/* 242 */     return (this.resultAfterExtraTime != null) ? this.resultAfterExtraTime : this.resultAfter90;
/*     */   }
/*     */   
/*     */   public void setResult(int homeGoals, int awayGoals, ResultType resultType) {
/* 246 */     switch (resultType) {
/*     */       case FIRST_HALF:
/* 248 */         this.resultAfter90 = new int[] { homeGoals, awayGoals };
/*     */         break;
/*     */       case SECOND_HALF:
/* 251 */         this.resultAfterExtraTime = new int[] { homeGoals, awayGoals };
/*     */         break;
/*     */       case FIRST_EXTRA_TIME:
/* 254 */         this.resultAfterPenalties = new int[] { homeGoals, awayGoals };
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEnded() {
/* 260 */     return (getResult() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int generateGoals(Team teamFor, Team teamAgainst, boolean extraTimeResult) {
/* 265 */     double factor = (teamFor.offenseRating() - teamAgainst.defenseRating() + 300) / 60.0D;
/*     */ 
/*     */     
/* 268 */     int[] goalsProbability = new int[7]; int goals;
/* 269 */     for (goals = 0; goals < 7; goals++) {
/* 270 */       int a = Const.goalsProbability[(int)Math.floor(factor)][goals];
/* 271 */       int b = Const.goalsProbability[(int)Math.ceil(factor)][goals];
/* 272 */       goalsProbability[goals] = (int)Math.round(a + (b - a) * (factor - Math.floor(factor)));
/*     */     } 
/*     */     
/* 275 */     goalsProbability[6] = 1000;
/* 276 */     for (goals = 0; goals <= 5; goals++) {
/* 277 */       goalsProbability[6] = goalsProbability[6] - goalsProbability[goals];
/*     */     }
/*     */     
/* 280 */     int r = (int)Math.ceil(1000.0D * Math.random());
/* 281 */     int sum = 0;
/* 282 */     int i = -1;
/* 283 */     while (sum < r) {
/* 284 */       i++;
/* 285 */       sum += goalsProbability[i];
/*     */     } 
/*     */     
/* 288 */     if (extraTimeResult) {
/* 289 */       return (int)Math.floor((i / 3.0F));
/*     */     }
/* 291 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   void updateAi() {
/* 296 */     for (int t = 0; t <= 1; t++) {
/* 297 */       if (this.team[t] != null) {
/* 298 */         this.team[t].updateLineupAi();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void updateBall() {
/* 304 */     this.ball.update();
/* 305 */     updateBallOwner();
/* 306 */     this.ball.inFieldKeep();
/*     */   }
/*     */   
/*     */   private void updateBallOwner() {
/* 310 */     if (this.ball.owner != null && (
/* 311 */       this.ball.owner.ballDistance > 11.0F || this.ball.z > 26.0F)) {
/* 312 */       setBallOwner((Player)null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBallOwner(Player player, boolean updateGoalOwner) {
/* 318 */     if (this.penaltyScorer != null && player != null && player != this.penaltyScorer) {
/* 319 */       this.penaltyScorer = null;
/*     */     }
/* 321 */     this.ball.owner = player;
/* 322 */     if (player != null) {
/* 323 */       this.ball.ownerLast = player;
/* 324 */       if (updateGoalOwner) {
/* 325 */         this.ball.goalOwner = player;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean updatePlayers(boolean limit) {
/* 331 */     boolean move = false;
/* 332 */     if (this.team[0].updatePlayers(limit)) {
/* 333 */       move = true;
/*     */     }
/* 335 */     if (this.team[1].updatePlayers(limit)) {
/* 336 */       move = true;
/*     */     }
/* 338 */     return move;
/*     */   }
/*     */   
/*     */   void updateCoaches() {
/* 342 */     (this.team[0]).coach.update();
/* 343 */     (this.team[1]).coach.update();
/*     */   }
/*     */   
/*     */   void resetData() {
/* 347 */     updatePlayers(false);
/* 348 */     for (int f = 0; f < 4096; f++) {
/* 349 */       this.ball.save(f);
/* 350 */       this.team[0].save(f);
/* 351 */       this.team[1].save(f);
/*     */     } 
/*     */   }
/*     */   
/*     */   void setIntroPositions() {
/* 356 */     setIntroPositions(this.team[0]);
/* 357 */     setIntroPositions(this.team[1]);
/*     */   }
/*     */   
/*     */   private void setIntroPositions(Team team) {
/* 361 */     int len = team.lineup.size();
/* 362 */     for (int i = 0; i < len; i++) {
/* 363 */       Player player = team.lineup.get(i);
/* 364 */       if (i < 11) {
/* 365 */         player.setState(PlayerFsm.Id.STATE_OUTSIDE);
/*     */         
/* 367 */         player.x = 590.0F;
/* 368 */         player.y = (10 * team.side + 20);
/* 369 */         player.z = 0.0F;
/*     */         
/* 371 */         player.tx = player.x;
/* 372 */         player.ty = player.y;
/*     */       } else {
/* 374 */         player.x = -544.0F;
/* 375 */         if (1 - 2 * team.index == this.benchSide) {
/* 376 */           player.y = (-198 + 14 * (i - 11) + 46);
/*     */         } else {
/* 378 */           player.y = (38 + 14 * (i - 11) + 46);
/*     */         } 
/* 380 */         player.setState(PlayerFsm.Id.STATE_BENCH_SITTING);
/*     */       } 
/*     */     } 
/*     */     
/* 384 */     team.coach.x = -544.0F;
/* 385 */     if (1 - 2 * team.index == team.match.benchSide) {
/* 386 */       team.coach.y = -166.0F;
/*     */     } else {
/* 388 */       team.coach.y = 70.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   void enterPlayers(int timer, int enterDelay) {
/* 393 */     if (timer % enterDelay == 0 && timer / enterDelay <= 11) {
/* 394 */       for (int t = 0; t <= 1; t++) {
/* 395 */         for (int i = 0; i < 11; i++) {
/* 396 */           if (i < timer / enterDelay) {
/* 397 */             Player player = (this.team[t]).lineup.get(i);
/* 398 */             if (player.getState().checkId(PlayerFsm.Id.STATE_OUTSIDE)) {
/* 399 */               player.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/* 400 */               player.tx = (210 + 7 * i);
/* 401 */               player.ty = (60 * (this.team[t]).side - ((i % 2 == 0) ? 20 : 0));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   boolean enterPlayersFinished(int timer, int enterDelay) {
/* 410 */     return (timer / enterDelay > 11);
/*     */   }
/*     */   
/*     */   void playersPhoto() {
/* 414 */     for (int t = 0; t <= 1; t++) {
/* 415 */       for (int i = 0; i < 11; i++) {
/* 416 */         Player player = (this.team[t]).lineup.get(i);
/* 417 */         if (player.getState().checkId(PlayerFsm.Id.STATE_IDLE)) {
/* 418 */           player.setState(PlayerFsm.Id.STATE_PHOTO);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void setStartingPositions() {
/* 425 */     int t = this.kickOffTeam;
/* 426 */     for (int k = 0; k < 2; k++) {
/* 427 */       for (int i = 0; i < 11; i++) {
/* 428 */         Player player = (this.team[t]).lineup.get(i);
/* 429 */         player.tx = (Pitch.startingPositions[k][i][0] * -(this.team[t]).side);
/* 430 */         player.ty = (Pitch.startingPositions[k][i][1] * -(this.team[t]).side);
/*     */       } 
/* 432 */       t = 1 - t;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPlayersState(PlayerFsm.Id stateId, Player excluded) {
/* 437 */     for (int t = 0; t <= 1; t++) {
/* 438 */       this.team[t].setPlayersState(stateId, excluded);
/*     */     }
/*     */   }
/*     */   
/*     */   void setLineupState(PlayerFsm.Id stateId) {
/* 443 */     for (int t = 0; t <= 1; t++) {
/* 444 */       for (Player player : (this.team[t]).lineup) {
/* 445 */         player.setState(stateId);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void setStatesForGoal(Goal goal) {
/* 451 */     for (int t = 0; t <= 1; t++) {
/* 452 */       for (int i = 0; i < 11; i++) {
/* 453 */         Player player = (this.team[t]).lineup.get(i);
/* 454 */         PlayerState playerState = player.getState();
/* 455 */         if (playerState.checkId(PlayerFsm.Id.STATE_STAND_RUN) || playerState.checkId(PlayerFsm.Id.STATE_KEEPER_POSITIONING)) {
/* 456 */           player.tx = player.x;
/* 457 */           player.ty = player.y;
/* 458 */           if (t == goal.player.team.index && player == goal.player) {
/* 459 */             player.setState(PlayerFsm.Id.STATE_GOAL_SCORER);
/* 460 */           } else if (t == goal.player.team.index && EMath.dist(player.x, player.y, goal.player.x, goal.player.y) < (150 * Assets.random.nextInt(4))) {
/* 461 */             player.setState(PlayerFsm.Id.STATE_GOAL_MATE);
/*     */           } else {
/* 463 */             player.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void setStatesForOwnGoal(Goal goal) {
/* 471 */     for (int t = 0; t <= 1; t++) {
/* 472 */       for (int i = 0; i < 11; i++) {
/* 473 */         Player player = (this.team[t]).lineup.get(i);
/* 474 */         PlayerState playerState = player.getState();
/* 475 */         if (playerState.checkId(PlayerFsm.Id.STATE_STAND_RUN) || playerState.checkId(PlayerFsm.Id.STATE_KEEPER_POSITIONING)) {
/* 476 */           player.tx = player.x;
/* 477 */           player.ty = player.y;
/* 478 */           if (player == goal.player) {
/* 479 */             player.setState(PlayerFsm.Id.STATE_OWN_GOAL_SCORER);
/*     */           } else {
/* 481 */             player.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void setPlayersTarget(float tx, float ty) {
/* 489 */     for (int t = 0; t <= 1; t++) {
/* 490 */       for (int i = 0; i < 11; i++) {
/* 491 */         ((Player)(this.team[t]).lineup.get(i)).setTarget(tx, ty);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void setLineupTarget(float tx, float ty) {
/* 497 */     for (int t = 0; t <= 1; t++) {
/* 498 */       for (Player player : (this.team[t]).lineup) {
/* 499 */         if (!player.checkState(PlayerFsm.Id.STATE_OUTSIDE)) {
/* 500 */           player.setTarget(tx, ty);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void resetAutomaticInputDevices() {
/* 507 */     for (int t = 0; t <= 1; t++) {
/* 508 */       this.team[t].assignAutomaticInputDevices(null);
/*     */     }
/*     */   }
/*     */   
/*     */   void findNearest() {
/* 513 */     this.team[0].findNearest();
/* 514 */     this.team[1].findNearest();
/*     */   }
/*     */   
/*     */   Player getNearestOfAll() {
/* 518 */     Player player0 = (this.team[0]).near1;
/* 519 */     Player player1 = (this.team[1]).near1;
/*     */     
/* 521 */     int distance0 = EMath.min(new int[] { player0.frameDistanceL, player0.frameDistance, player0.frameDistanceR });
/* 522 */     int distance1 = EMath.min(new int[] { player1.frameDistanceL, player1.frameDistance, player1.frameDistanceR });
/*     */     
/* 524 */     if (distance0 == 128 && distance1 == 128) return null;
/*     */     
/* 526 */     return (distance0 < distance1) ? player0 : player1;
/*     */   }
/*     */   
/*     */   void updateFrameDistance() {
/* 530 */     this.team[0].updateFrameDistance();
/* 531 */     this.team[1].updateFrameDistance();
/*     */   }
/*     */   
/*     */   void updateBallZone() {
/* 535 */     this.ball.updateZone(this.ball.x, this.ball.y);
/*     */   }
/*     */   
/*     */   void updateTeamTactics() {
/* 539 */     this.team[0].updateTactics(false);
/* 540 */     this.team[1].updateTactics(false);
/*     */   }
/*     */   
/*     */   int attackingTeam() {
/* 544 */     return ((this.team[0]).side == -this.ball.ySide) ? 0 : 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int getMinute() {
/* 550 */     int minute = (int)(this.clock * 90.0F / this.length);
/*     */     
/* 552 */     switch (this.period) {
/*     */       case FIRST_HALF:
/* 554 */         minute = Math.min(minute, 45);
/*     */         break;
/*     */       
/*     */       case SECOND_HALF:
/* 558 */         minute = Math.min(minute, 90);
/*     */         break;
/*     */       
/*     */       case FIRST_EXTRA_TIME:
/* 562 */         minute = Math.min(minute, 105);
/*     */         break;
/*     */       
/*     */       case SECOND_EXTRA_TIME:
/* 566 */         minute = Math.min(minute, 120);
/*     */         break;
/*     */     } 
/*     */     
/* 570 */     return minute;
/*     */   }
/*     */   
/*     */   void addGoal(int attackingTeam) {
/*     */     Goal goal;
/* 575 */     if (this.ball.goalOwner == this.penaltyScorer) {
/* 576 */       this.competition.addGoal(this.ball.goalOwner);
/* 577 */       goal = new Goal(this.ball.goalOwner, getMinute(), Goal.Type.PENALTY);
/* 578 */     } else if (this.team[attackingTeam] == this.ball.goalOwner.team) {
/* 579 */       this.competition.addGoal(this.ball.goalOwner);
/* 580 */       goal = new Goal(this.ball.goalOwner, getMinute(), Goal.Type.NORMAL);
/*     */     } else {
/* 582 */       goal = new Goal(this.ball.goalOwner, getMinute(), Goal.Type.OWN_GOAL);
/*     */     } 
/*     */     
/* 585 */     this.goals.add(goal);
/*     */     
/* 587 */     this.scorers.build(this.goals);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean periodIsTerminable() {
/* 592 */     if (Math.abs(this.ball.zoneY) == 3) {
/* 593 */       return false;
/*     */     }
/*     */     
/* 596 */     if (Math.abs(this.ball.y) > 160.0F && 
/* 597 */       Math.abs(this.ball.y) > Math.abs(this.ball.y0)) {
/* 598 */       return false;
/*     */     }
/* 600 */     return true;
/*     */   }
/*     */   
/*     */   void swapTeamSides() {
/* 604 */     this.team[0].setSide(-(this.team[0]).side);
/* 605 */     this.team[1].setSide(-(this.team[1]).side);
/*     */   }
/*     */ 
/*     */   
/*     */   void quit() {
/* 610 */     Assets.Sounds.chant.stop();
/* 611 */     Assets.Sounds.crowd.stop();
/* 612 */     Assets.Sounds.end.stop();
/* 613 */     Assets.Sounds.homeGoal.stop();
/* 614 */     Assets.Sounds.intro.stop();
/* 615 */     Assets.Commentary.stop();
/*     */     
/* 617 */     this.listener.quitMatch((getFsm()).matchCompleted);
/*     */   }
/*     */ 
/*     */   
/*     */   int getRank() {
/* 622 */     int matchRank = (int)((this.team[0].getRank() + 2.0F * this.team[1].getRank()) / 3.0F);
/* 623 */     return (this.competition.type == Competition.Type.FRIENDLY) ? matchRank : (matchRank + 1);
/*     */   }
/*     */   
/*     */   void newTackle(Player player, Player opponent, float strength, float angleDiff) {
/* 627 */     this.tackle = new Tackle();
/* 628 */     this.tackle.time = this.clock;
/* 629 */     this.tackle.position = new Vector2(player.x, player.y);
/* 630 */     this.tackle.player = player;
/* 631 */     this.tackle.opponent = opponent;
/* 632 */     this.tackle.strength = strength;
/* 633 */     this.tackle.angleDiff = angleDiff;
/*     */   }
/*     */   
/*     */   void newFoul(float x, float y) {
/* 637 */     this.foul = new Foul();
/* 638 */     this.foul.time = this.tackle.time;
/* 639 */     this.foul.position = new Vector2(x, y);
/* 640 */     this.foul.player = this.tackle.player;
/* 641 */     this.foul.opponent = this.tackle.opponent;
/*     */   }
/*     */   
/*     */   class Foul {
/*     */     float time;
/*     */     public Vector2 position;
/*     */     public Player player;
/*     */     public Player opponent;
/*     */     
/*     */     public boolean isPenalty() {
/* 651 */       return (Math.abs(this.position.x) < 286.0F && 
/*     */         
/* 653 */         EMath.isIn(this.position.y, (this.player.team.side * 466), (this.player.team.side * 640)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isDirectShot() {
/* 661 */       return Const.isInsideDirectShotArea(this.position.x, this.position.y, this.player.team.side);
/*     */     }
/*     */     
/*     */     boolean isNearOwnGoal() {
/* 665 */       return (Math.abs(Match.this.ball.x) < 176.0F && 
/* 666 */         EMath.isIn(Match.this.ball.y, (-this.player.team.side * 532), (-this.player.team.side * 640)));
/*     */     }
/*     */   }
/*     */   
/*     */   class Tackle {
/*     */     float time;
/*     */     Vector2 position;
/*     */     public Player player;
/*     */     public Player opponent;
/*     */     float strength;
/*     */     float angleDiff;
/*     */   }
/*     */   
/*     */   public static interface MatchListener {
/*     */     void quitMatch(boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Match.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */