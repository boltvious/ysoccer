/*     */ package com.ygames.ysoccer.match;
/*     */ 
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
/*     */ 
/*     */ class MatchStatePenalties
/*     */   extends MatchState
/*     */ {
/*     */   private boolean move;
/*     */   
/*     */   MatchStatePenalties(MatchFsm fsm) {
/*  23 */     super(fsm);
/*     */     
/*  25 */     this.displayWindVane = true;
/*  26 */     this.displayPenaltiesScore = true;
/*     */     
/*  28 */     this.checkBenchCall = false;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  33 */     super.entryActions();
/*     */ 
/*     */     
/*  36 */     this.match.penaltyKickingTeam = 1 - this.match.penaltyKickingTeam;
/*  37 */     this.match.team[this.match.penaltyKickingTeam].setSide(1);
/*  38 */     this.match.team[1 - this.match.penaltyKickingTeam].setSide(-1);
/*     */ 
/*     */     
/*  41 */     if (this.match.penaltiesLeft(0) == 0 && this.match.penaltiesLeft(1) == 0) {
/*  42 */       this.match.addPenalties(1);
/*     */     }
/*     */     
/*  45 */     this.match.nextPenalty();
/*     */     
/*  47 */     this.match.setPointOfInterest(0.0F, (this.match.penalty.side * 524));
/*     */     
/*  49 */     setPlayersTargetPositions();
/*  50 */     this.match.penalty.kicker.setTarget((-40 * this.match.penalty.side), (this.match.penalty.side * 479));
/*  51 */     this.match.penalty.keeper.setTarget(0.0F, (this.match.penalty.side * 636));
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  56 */     super.onResume();
/*     */     
/*  58 */     this.sceneRenderer.actionCamera
/*  59 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  60 */       .setSpeed(ActionCamera.Speed.NORMAL);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  65 */     super.doActions(deltaTime);
/*     */     
/*  67 */     float timeLeft = deltaTime;
/*  68 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  70 */       this.move = this.match.updatePlayers(false);
/*     */       
/*  72 */       this.match.nextSubframe();
/*     */       
/*  74 */       this.sceneRenderer.save();
/*     */       
/*  76 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  78 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/*  84 */     if (!this.move) {
/*  85 */       this.match.penalty.keeper.setState(PlayerFsm.Id.STATE_KEEPER_PENALTY_POSITIONING);
/*  86 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_PENALTIES_KICK);
/*     */     } 
/*     */     
/*  89 */     return checkCommonConditions();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPlayersTargetPositions() {
/*  94 */     for (int t = 0; t <= 1; t++) {
/*  95 */       Team team = this.match.team[t];
/*  96 */       int len = team.lineup.size();
/*  97 */       for (int i = 0; i < len; i++) {
/*  98 */         Player player = team.lineupAtPosition(i);
/*  99 */         if (!player.checkState(PlayerFsm.Id.STATE_OUTSIDE)) {
/* 100 */           int side = 2 * t - 1;
/* 101 */           player.tx = (18 * (-team.lineup.size() + 2 * i)) + 8.0F * EMath.cos((70 * player.number));
/* 102 */           player.ty = (-((i == 0) ? 300 : 100) + side * (15 + 5 * i % 2)) + 8.0F * EMath.sin((70 * player.number));
/* 103 */           player.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStatePenalties.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */