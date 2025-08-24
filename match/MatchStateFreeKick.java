/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
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
/*     */ class MatchStateFreeKick
/*     */   extends MatchState
/*     */ {
/*     */   private Player freeKickPlayer;
/*     */   private Team freeKickTeam;
/*     */   private Team defendingTeam;
/*     */   private boolean isKicking;
/*     */   
/*     */   MatchStateFreeKick(MatchFsm fsm) {
/*  26 */     super(fsm);
/*     */     
/*  28 */     this.displayControlledPlayer = true;
/*  29 */     this.displayBallOwner = true;
/*  30 */     this.displayTime = true;
/*  31 */     this.displayWindVane = true;
/*  32 */     this.displayScore = true;
/*  33 */     this.displayRadar = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  38 */     super.onResume();
/*     */     
/*  40 */     this.freeKickTeam = this.match.foul.opponent.team;
/*  41 */     this.defendingTeam = this.match.foul.player.team;
/*     */     
/*  43 */     this.sceneRenderer.actionCamera
/*  44 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  45 */       .setSpeed(ActionCamera.Speed.FAST)
/*  46 */       .setOffset((-30 * this.match.ball.xSide), (-80 * this.freeKickTeam.side))
/*  47 */       .setLimited(true, true);
/*     */     
/*  49 */     this.isKicking = false;
/*     */     
/*  51 */     this.freeKickTeam.updateFrameDistance();
/*  52 */     this.freeKickTeam.findNearest();
/*  53 */     this.freeKickPlayer = this.freeKickTeam.near1;
/*     */     
/*  55 */     float ballToGoal = EMath.roundBy(EMath.angle(this.match.ball.x, this.match.ball.y, 0.0F, (this.defendingTeam.side * 640)), 45.0F);
/*  56 */     this.freeKickPlayer.tx = this.match.ball.x - 7.0F * EMath.cos(ballToGoal);
/*  57 */     this.freeKickPlayer.ty = this.match.ball.y - 7.0F * EMath.sin(ballToGoal);
/*  58 */     this.freeKickPlayer.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  63 */     super.doActions(deltaTime);
/*     */     
/*  65 */     boolean move = true;
/*  66 */     float timeLeft = deltaTime;
/*  67 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  69 */       if (this.match.subframe % 8 == 0) {
/*  70 */         this.match.updateAi();
/*     */       }
/*     */       
/*  73 */       this.match.updateBall();
/*  74 */       move = this.match.updatePlayers(true);
/*     */       
/*  76 */       this.match.nextSubframe();
/*     */       
/*  78 */       this.sceneRenderer.save();
/*     */       
/*  80 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  82 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */     
/*  85 */     if (!move && !this.isKicking) {
/*  86 */       for (Player ply : this.defendingTeam.lineup) {
/*  87 */         if (this.defendingTeam.barrier.contains(ply)) {
/*  88 */           ply.setState(PlayerFsm.Id.STATE_BARRIER);
/*     */         }
/*     */       } 
/*     */       
/*  92 */       Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */       
/*  94 */       this.freeKickPlayer.setState(PlayerFsm.Id.STATE_FREE_KICK_ANGLE);
/*  95 */       if (this.freeKickPlayer.team.usesAutomaticInputDevice()) {
/*  96 */         this.freeKickPlayer.inputDevice = this.freeKickPlayer.team.inputDevice;
/*     */       }
/*  98 */       this.isKicking = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 104 */     if (this.match.ball.v > 0.0F) {
/* 105 */       this.freeKickTeam.setPlayersState(PlayerFsm.Id.STATE_STAND_RUN, this.freeKickPlayer);
/* 106 */       for (int i = 0; i < 11; i++) {
/* 107 */         Player player = this.defendingTeam.lineup.get(i);
/* 108 */         if (!this.defendingTeam.barrier.contains(player)) {
/* 109 */           player.setState(PlayerFsm.Id.STATE_STAND_RUN);
/*     */         }
/*     */       } 
/*     */       
/* 113 */       this.match.foul = null;
/* 114 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_MAIN);
/*     */     } 
/*     */     
/* 117 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateFreeKick.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */