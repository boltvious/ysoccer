/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.GLGame;
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
/*     */ public class Training
/*     */   extends Scene
/*     */ {
/*     */   Ball ball;
/*  28 */   public Team[] team = new Team[2];
/*     */   public Training(Team trainingTeam) {
/*  30 */     for (int t = 0; t <= 1; t++) {
/*  31 */       this.team[t] = new Team();
/*     */     }
/*  33 */     (this.team[0]).coach = trainingTeam.coach;
/*     */ 
/*     */     
/*  36 */     int keepers = 0;
/*  37 */     int players = 0;
/*  38 */     for (int i = 0; i < trainingTeam.players.size(); i++) {
/*  39 */       Player ply = trainingTeam.players.get(i);
/*  40 */       if (ply.role == Player.Role.GOALKEEPER) {
/*  41 */         ply.team = this.team[keepers % 2];
/*  42 */         (this.team[keepers % 2]).players.add(ply);
/*  43 */         keepers++;
/*     */       } else {
/*  45 */         ply.team = this.team[players % 2];
/*  46 */         (this.team[players % 2]).players.add(ply);
/*  47 */         players++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public TrainingListener listener;
/*     */   public void init(GLGame game, SceneSettings sceneSettings) {
/*  53 */     this.game = game;
/*  54 */     this.settings = sceneSettings;
/*     */     
/*  56 */     this.ball = new Ball(sceneSettings);
/*     */     
/*  58 */     for (int t = 0; t <= 1; t++) {
/*  59 */       (this.team[t]).index = t;
/*  60 */       this.team[t].beforeTraining(this);
/*  61 */       this.team[t].setSide(1 - 2 * t);
/*     */     } 
/*     */     
/*  64 */     this.fsm = new TrainingFsm(this);
/*  65 */     this.pointOfInterest = new Vector2();
/*     */   }
/*     */   
/*     */   public TrainingFsm getFsm() {
/*  69 */     return (TrainingFsm)this.fsm;
/*     */   }
/*     */   
/*     */   void updateBall() {
/*  73 */     this.ball.update();
/*  74 */     updateBallOwner();
/*  75 */     this.ball.inFieldKeep();
/*     */   }
/*     */   
/*     */   private void updateBallOwner() {
/*  79 */     if (this.ball.owner != null && (
/*  80 */       this.ball.owner.ballDistance > 11.0F || this.ball.z > 26.0F)) {
/*  81 */       setBallOwner((Player)null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBallOwner(Player player, boolean updateGoalOwner) {
/*  87 */     this.ball.owner = player;
/*  88 */     if (player != null) {
/*  89 */       this.ball.ownerLast = player;
/*  90 */       if (updateGoalOwner) {
/*  91 */         this.ball.goalOwner = player;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void updatePlayers(boolean limit) {
/*  97 */     this.team[0].updateLineup(limit);
/*  98 */     this.team[1].updateLineup(limit);
/*     */   }
/*     */   
/*     */   void updateStates() {
/* 102 */     for (int t = 0; t <= 1; t++) {
/* 103 */       for (Player player : (this.team[t]).lineup) {
/* 104 */         if (player.checkState(PlayerFsm.Id.STATE_IDLE)) {
/* 105 */           player.setState(PlayerFsm.Id.STATE_STAND_RUN);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void resetData() {
/* 112 */     updatePlayers(true);
/* 113 */     for (int f = 0; f < 4096; f++) {
/* 114 */       this.ball.save(f);
/* 115 */       this.team[0].save(f);
/* 116 */       this.team[1].save(f);
/*     */     } 
/*     */   }
/*     */   
/*     */   void findNearest() {
/* 121 */     this.team[0].findNearest((this.team[0]).lineup.size());
/* 122 */     this.team[1].findNearest((this.team[1]).lineup.size());
/*     */   }
/*     */   
/*     */   Player getNearestOfAll() {
/* 126 */     Player player0 = (this.team[0]).near1;
/* 127 */     Player player1 = (this.team[1]).near1;
/*     */     
/* 129 */     int distance0 = EMath.min(new int[] { player0.frameDistanceL, player0.frameDistance, player0.frameDistanceR });
/* 130 */     int distance1 = EMath.min(new int[] { player1.frameDistanceL, player1.frameDistance, player1.frameDistanceR });
/*     */     
/* 132 */     if (distance0 == 128 && distance1 == 128) return null;
/*     */     
/* 134 */     return (distance0 < distance1) ? player0 : player1;
/*     */   }
/*     */   
/*     */   void updateFrameDistance() {
/* 138 */     this.team[0].updateFrameDistance((this.team[0]).lineup.size());
/* 139 */     this.team[1].updateFrameDistance((this.team[1]).lineup.size());
/*     */   }
/*     */ 
/*     */   
/*     */   void quit() {
/* 144 */     Assets.Sounds.chant.stop();
/*     */     
/* 146 */     this.listener.quitTraining();
/*     */   }
/*     */   
/*     */   public static interface TrainingListener {
/*     */     void quitTraining();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Training.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */