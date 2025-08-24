/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.audio.Sound;
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import java.util.ArrayList;
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
/*     */ class MatchStatePenaltyKickStop
/*     */   extends MatchState
/*     */ {
/*     */   private boolean allPlayersReachingTarget;
/*     */   private final ArrayList<Player> playersReachingTarget;
/*     */   private boolean move;
/*     */   private final Vector2 penaltyKickPosition;
/*     */   
/*     */   MatchStatePenaltyKickStop(MatchFsm fsm) {
/*  30 */     super(fsm);
/*     */     
/*  32 */     this.displayControlledPlayer = true;
/*  33 */     this.displayBallOwner = true;
/*  34 */     this.displayTime = true;
/*  35 */     this.displayWindVane = true;
/*  36 */     this.displayRadar = true;
/*     */     
/*  38 */     this.playersReachingTarget = new ArrayList<>();
/*  39 */     this.penaltyKickPosition = new Vector2();
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  44 */     super.entryActions();
/*     */     
/*  46 */     Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */     
/*  48 */     if (this.match.settings.commentary) {
/*  49 */       int size = Assets.Commentary.penalty.size();
/*  50 */       if (size > 0) {
/*  51 */         ((Sound)Assets.Commentary.penalty.get(Assets.random.nextInt(size))).play(Assets.Sounds.volume / 100.0F);
/*     */       }
/*     */     } 
/*     */     
/*  55 */     Player penaltyKicker = this.match.foul.opponent.team.lastOfLineup();
/*  56 */     Player penaltyKeeper = this.match.foul.player.team.lineupAtPosition(0);
/*  57 */     this.match.foul = null;
/*  58 */     this.match.createPenalty(penaltyKicker, penaltyKeeper, this.match.ball.ySide);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     this.match.ball.updateZone(0.0F, (this.match.penalty.side * 524));
/*  64 */     this.match.updateTeamTactics();
/*  65 */     ((Player)(this.match.team[0]).lineup.get(0)).setTarget(0.0F, ((this.match.team[0]).side * 632));
/*  66 */     ((Player)(this.match.team[1]).lineup.get(0)).setTarget(0.0F, ((this.match.team[1]).side * 632));
/*  67 */     for (int t = 0; t <= 1; t++) {
/*  68 */       for (int i = 1; i < 11; i++) {
/*  69 */         Player player = (this.match.team[t]).lineup.get(i);
/*  70 */         player.tx = (player.tx >= 0.0F) ? Math.max(player.tx, 100.0F) : -Math.max(-player.tx, 100.0F);
/*  71 */         player.ty = Math.signum(player.ty) * Math.min(Math.abs(player.ty), 462.0F);
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     penaltyKicker.setTarget((-40 * this.match.ball.ySide), (this.match.penalty.side * 479));
/*  76 */     this.penaltyKickPosition.set(0.0F, (this.match.penalty.side * 524));
/*     */     
/*  78 */     this.match.resetAutomaticInputDevices();
/*     */     
/*  80 */     this.allPlayersReachingTarget = false;
/*  81 */     this.playersReachingTarget.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  86 */     this.sceneRenderer.actionCamera
/*  87 */       .setMode(ActionCamera.Mode.REACH_TARGET)
/*  88 */       .setTarget(this.penaltyKickPosition.x, this.penaltyKickPosition.y)
/*  89 */       .setSpeed(ActionCamera.Speed.NORMAL)
/*  90 */       .setLimited(true, true);
/*     */     
/*  92 */     this.match.setPointOfInterest(this.penaltyKickPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  97 */     super.doActions(deltaTime);
/*     */     
/*  99 */     this.allPlayersReachingTarget = true;
/* 100 */     for (int t = 0; t <= 1; t++) {
/* 101 */       for (int i = 0; i < 11; i++) {
/* 102 */         Player player = (this.match.team[t]).lineup.get(i);
/*     */ 
/*     */         
/* 105 */         if (player.checkState(PlayerFsm.Id.STATE_TACKLE) || player.checkState(PlayerFsm.Id.STATE_DOWN)) {
/* 106 */           this.allPlayersReachingTarget = false;
/* 107 */         } else if (!this.playersReachingTarget.contains(player)) {
/* 108 */           player.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/* 109 */           this.playersReachingTarget.add(player);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     float timeLeft = deltaTime;
/* 115 */     while (timeLeft >= 0.001953125F) {
/*     */       
/* 117 */       if (this.match.subframe % 8 == 0) {
/* 118 */         this.match.updateAi();
/*     */       }
/*     */       
/* 121 */       this.match.updateBall();
/* 122 */       this.match.ball.inFieldKeep();
/* 123 */       this.match.ball.collisionGoal();
/* 124 */       this.match.ball.collisionJumpers();
/* 125 */       this.match.ball.collisionNetOut();
/* 126 */       this.match.ball.collisionNet();
/*     */       
/* 128 */       this.move = this.match.updatePlayers(true);
/*     */       
/* 130 */       this.match.nextSubframe();
/*     */       
/* 132 */       this.sceneRenderer.save();
/*     */       
/* 134 */       this.sceneRenderer.actionCamera.update();
/*     */       
/* 136 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 142 */     if (this.allPlayersReachingTarget && !this.move) {
/* 143 */       this.match.penalty.keeper.setState(PlayerFsm.Id.STATE_KEEPER_PENALTY_POSITIONING);
/* 144 */       this.match.ball.setPosition(this.penaltyKickPosition.x, this.penaltyKickPosition.y, 0.0F);
/* 145 */       this.match.ball.updatePrediction();
/*     */       
/* 147 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_PENALTY_KICK);
/*     */     } 
/*     */     
/* 150 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStatePenaltyKickStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */