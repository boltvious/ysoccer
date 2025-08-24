/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchStateGoalKickStop
/*     */   extends MatchState
/*     */ {
/*  15 */   private final Vector2 goalKickPosition = new Vector2();
/*     */   
/*     */   MatchStateGoalKickStop(MatchFsm fsm) {
/*  18 */     super(fsm);
/*     */     
/*  20 */     this.displayTime = true;
/*  21 */     this.displayWindVane = true;
/*  22 */     this.displayRadar = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  27 */     super.entryActions();
/*     */     
/*  29 */     Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */     
/*  31 */     this.match.resetAutomaticInputDevices();
/*  32 */     this.match.setPlayersState(PlayerFsm.Id.STATE_REACH_TARGET, (Player)null);
/*     */     
/*  34 */     Team goalKickTeam = this.match.team[1 - this.match.ball.ownerLast.team.index];
/*  35 */     Player goalKickKeeper = goalKickTeam.lineup.get(0);
/*  36 */     goalKickKeeper.tx = this.match.ball.x / 4.0F;
/*  37 */     goalKickKeeper.ty = (goalKickTeam.side * 632);
/*     */     
/*  39 */     Team opponentTeam = this.match.team[1 - goalKickTeam.index];
/*  40 */     Player opponentKeeper = opponentTeam.lineup.get(0);
/*  41 */     opponentKeeper.tx = 0.0F;
/*  42 */     opponentKeeper.ty = (opponentTeam.side * 632);
/*     */     
/*  44 */     goalKickTeam.updateTactics(true);
/*  45 */     opponentTeam.updateTactics(true);
/*     */     
/*  47 */     this.goalKickPosition.set(126.0F * this.match.ball.xSide, (582 * this.match.ball.ySide));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void onResume() {
/*  55 */     this.sceneRenderer.actionCamera
/*  56 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  57 */       .setSpeed(ActionCamera.Speed.NORMAL)
/*  58 */       .setLimited(true, true);
/*     */     
/*  60 */     this.match.setPointOfInterest(this.goalKickPosition);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  65 */     super.doActions(deltaTime);
/*     */     
/*  67 */     float timeLeft = deltaTime;
/*  68 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  70 */       if (this.match.subframe % 8 == 0) {
/*  71 */         this.match.updateAi();
/*     */       }
/*     */       
/*  74 */       this.match.updateBall();
/*  75 */       this.match.ball.inFieldKeep();
/*  76 */       this.match.ball.collisionGoal();
/*  77 */       this.match.ball.collisionJumpers();
/*  78 */       this.match.ball.collisionNetOut();
/*     */       
/*  80 */       this.match.updatePlayers(true);
/*     */       
/*  82 */       this.match.nextSubframe();
/*     */       
/*  84 */       this.sceneRenderer.save();
/*     */       
/*  86 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  88 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/*  94 */     if (this.match.ball.v < 5.0F && this.match.ball.vz < 5.0F) {
/*  95 */       this.match.ball.setPosition(this.goalKickPosition.x, this.goalKickPosition.y, 0.0F);
/*  96 */       this.match.ball.updatePrediction();
/*     */       
/*  98 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_GOAL_KICK);
/*     */     } 
/*     */     
/* 101 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateGoalKickStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */