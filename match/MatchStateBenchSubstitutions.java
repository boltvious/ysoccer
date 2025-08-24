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
/*     */ class MatchStateBenchSubstitutions
/*     */   extends MatchState
/*     */ {
/*     */   MatchStateBenchSubstitutions(MatchFsm fsm) {
/*  21 */     super(fsm);
/*     */     
/*  23 */     this.checkReplayKey = false;
/*  24 */     this.checkPauseKey = false;
/*  25 */     this.checkHelpKey = false;
/*  26 */     this.checkBenchCall = false;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  31 */     super.entryActions();
/*  32 */     this.displayBenchPlayers = true;
/*     */     
/*  34 */     this.sceneRenderer.actionCamera.setMode(ActionCamera.Mode.STILL);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  39 */     super.doActions(deltaTime);
/*     */     
/*  41 */     float timeLeft = deltaTime;
/*  42 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  44 */       this.match.updateBall();
/*  45 */       this.match.ball.inFieldKeep();
/*     */       
/*  47 */       this.match.updatePlayers(true);
/*     */       
/*  49 */       this.match.updateCoaches();
/*     */       
/*  51 */       this.match.nextSubframe();
/*     */       
/*  53 */       this.sceneRenderer.save();
/*     */       
/*  55 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  57 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */ 
/*     */     
/*  61 */     if ((getFsm()).benchStatus.inputDevice.yMoved()) {
/*  62 */       int substitutes = Math.min((this.match.getSettings()).benchSize, (getFsm()).benchStatus.team.lineup.size() - 11);
/*     */ 
/*     */       
/*  65 */       if ((getFsm()).benchStatus.team.substitutionsCount < (this.match.getSettings()).substitutions) {
/*  66 */         (getFsm()).benchStatus.selectedPosition = EMath.rotate((getFsm()).benchStatus.selectedPosition, -1, substitutes - 1, (getFsm()).benchStatus.inputDevice.y1);
/*     */       }
/*     */ 
/*     */       
/*  70 */       for (int i = 0; i < substitutes; i++) {
/*  71 */         Player player = (getFsm()).benchStatus.team.lineup.get(11 + i);
/*  72 */         if (!player.getState().checkId(PlayerFsm.Id.STATE_OUTSIDE)) {
/*  73 */           player.setState(PlayerFsm.Id.STATE_BENCH_SITTING);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  78 */       if ((getFsm()).benchStatus.selectedPosition != -1) {
/*  79 */         Player player = (getFsm()).benchStatus.team.lineup.get(11 + (getFsm()).benchStatus.selectedPosition);
/*  80 */         if (!player.getState().checkId(PlayerFsm.Id.STATE_OUTSIDE)) {
/*     */           
/*  82 */           Coach coach = (getFsm()).benchStatus.team.coach;
/*  83 */           coach.status = Coach.Status.LOOK_BENCH;
/*  84 */           coach.timer = 250;
/*     */           
/*  86 */           player.setState(PlayerFsm.Id.STATE_BENCH_STANDING);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/*  95 */     if ((getFsm()).benchStatus.inputDevice.fire1Down()) {
/*  96 */       if ((getFsm()).benchStatus.selectedPosition == -1) {
/*  97 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_BENCH_FORMATION);
/*     */       }
/*     */       
/* 100 */       if ((getFsm()).benchStatus.substPosition == -1) {
/*     */ 
/*     */         
/* 103 */         Player player = (getFsm()).benchStatus.team.lineup.get(11 + (getFsm()).benchStatus.selectedPosition);
/*     */         
/* 105 */         if (!player.getState().checkId(PlayerFsm.Id.STATE_OUTSIDE)) {
/*     */           
/* 107 */           player.setState(PlayerFsm.Id.STATE_BENCH_OUT);
/*     */           
/* 109 */           (getFsm()).benchStatus.substPosition = 11 + (getFsm()).benchStatus.selectedPosition;
/* 110 */           (getFsm()).benchStatus.selectedPosition = (getFsm()).benchStatus.team.nearestBenchPlayerByRole(player.role);
/*     */           
/* 112 */           return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_BENCH_FORMATION);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 118 */     if ((getFsm()).benchStatus.inputDevice.xReleased()) {
/* 119 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_BENCH_EXIT);
/*     */     }
/*     */     
/* 122 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateBenchSubstitutions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */