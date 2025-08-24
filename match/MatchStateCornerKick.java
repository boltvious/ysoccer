/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.audio.Sound;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchStateCornerKick
/*     */   extends MatchState
/*     */ {
/*     */   private Player cornerKickPlayer;
/*     */   private boolean isKicking;
/*     */   
/*     */   MatchStateCornerKick(MatchFsm fsm) {
/*  20 */     super(fsm);
/*     */     
/*  22 */     this.displayControlledPlayer = true;
/*  23 */     this.displayBallOwner = true;
/*  24 */     this.displayTime = true;
/*  25 */     this.displayWindVane = true;
/*  26 */     this.displayScore = true;
/*  27 */     this.displayRadar = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  32 */     super.entryActions();
/*  33 */     if (this.match.settings.commentary) {
/*  34 */       int size = Assets.Commentary.cornerKick.size();
/*  35 */       if (size > 0) {
/*  36 */         ((Sound)Assets.Commentary.cornerKick.get(Assets.random.nextInt(size))).play(Assets.Sounds.volume / 100.0F);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  43 */     super.onResume();
/*     */     
/*  45 */     this.sceneRenderer.actionCamera
/*  46 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  47 */       .setOffset((-30 * this.match.ball.xSide), (-30 * this.match.ball.ySide))
/*  48 */       .setSpeed(ActionCamera.Speed.FAST)
/*  49 */       .setLimited(true, true);
/*     */     
/*  51 */     this.isKicking = false;
/*     */     
/*  53 */     (getFsm()).cornerKickTeam.updateFrameDistance();
/*  54 */     (getFsm()).cornerKickTeam.findNearest();
/*  55 */     this.cornerKickPlayer = (getFsm()).cornerKickTeam.near1;
/*     */     
/*  57 */     this.cornerKickPlayer.setTarget(this.match.ball.x + (7 * this.match.ball.xSide), this.match.ball.y);
/*  58 */     this.cornerKickPlayer.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */   }
/*     */ 
/*     */   
/*     */   void onPause() {
/*  63 */     super.onPause();
/*  64 */     this.match.updateTeamTactics();
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  69 */     super.doActions(deltaTime);
/*     */     
/*  71 */     boolean move = true;
/*  72 */     float timeLeft = deltaTime;
/*  73 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  75 */       if (this.match.subframe % 8 == 0) {
/*  76 */         this.match.updateAi();
/*     */       }
/*     */       
/*  79 */       this.match.updateBall();
/*  80 */       this.match.ball.inFieldKeep();
/*     */       
/*  82 */       move = this.match.updatePlayers(true);
/*     */       
/*  84 */       this.match.nextSubframe();
/*     */       
/*  86 */       this.sceneRenderer.save();
/*     */       
/*  88 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  90 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */     
/*  93 */     if (!move && !this.isKicking) {
/*  94 */       Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */       
/*  96 */       this.cornerKickPlayer.setState(PlayerFsm.Id.STATE_CORNER_KICK_ANGLE);
/*  97 */       if (this.cornerKickPlayer.team.usesAutomaticInputDevice()) {
/*  98 */         this.cornerKickPlayer.inputDevice = this.cornerKickPlayer.team.inputDevice;
/*     */       }
/* 100 */       this.isKicking = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 106 */     if (this.match.ball.v > 0.0F) {
/* 107 */       this.match.setPlayersState(PlayerFsm.Id.STATE_STAND_RUN, this.cornerKickPlayer);
/* 108 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_MAIN);
/*     */     } 
/*     */     
/* 111 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateCornerKick.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */