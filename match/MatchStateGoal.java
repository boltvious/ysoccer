/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchStateGoal
/*     */   extends MatchState
/*     */ {
/*     */   private Goal goal;
/*     */   private boolean replayDone;
/*     */   private boolean recordingDone;
/*     */   private boolean followBall;
/*     */   
/*     */   MatchStateGoal(MatchFsm fsm) {
/*  27 */     super(fsm);
/*     */     
/*  29 */     this.displayGoalScorer = true;
/*  30 */     this.displayTime = true;
/*  31 */     this.displayWindVane = true;
/*  32 */     this.displayRadar = true;
/*     */     
/*  34 */     this.checkReplayKey = false;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  39 */     super.entryActions();
/*     */     
/*  41 */     this.replayDone = false;
/*  42 */     this.recordingDone = false;
/*  43 */     this.followBall = true;
/*     */     
/*  45 */     Assets.Sounds.homeGoal.play(Assets.Sounds.volume / 100.0F);
/*     */     
/*  47 */     this.goal = this.match.goals.get(this.match.goals.size() - 1);
/*     */     
/*  49 */     if (this.match.settings.commentary) {
/*  50 */       if (this.goal.type == Goal.Type.OWN_GOAL) {
/*  51 */         int size = Assets.Commentary.ownGoal.size();
/*  52 */         if (size > 0) {
/*  53 */           ((Sound)Assets.Commentary.ownGoal.get(Assets.random.nextInt(size))).play(Assets.Sounds.volume / 100.0F);
/*     */         }
/*     */       } else {
/*  56 */         int size = Assets.Commentary.goal.size();
/*  57 */         if (size > 0) {
/*  58 */           ((Sound)Assets.Commentary.goal.get(Assets.random.nextInt(size))).play(Assets.Sounds.volume / 100.0F);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  63 */     if ((this.match.team[0]).side == this.match.ball.ySide) {
/*  64 */       this.match.kickOffTeam = 0;
/*  65 */     } else if ((this.match.team[1]).side == this.match.ball.ySide) {
/*  66 */       this.match.kickOffTeam = 1;
/*     */     } else {
/*  68 */       throw new RuntimeException("cannot decide kick_off_team!");
/*     */     } 
/*     */     
/*  71 */     this.match.resetAutomaticInputDevices();
/*     */     
/*  73 */     this.match.setPointOfInterest(this.match.ball.x, this.match.ball.y);
/*     */     
/*  75 */     this.sceneRenderer.actionCamera.setLimited(true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  80 */     super.onResume();
/*     */     
/*  82 */     setCameraMode();
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  87 */     super.doActions(deltaTime);
/*     */ 
/*     */     
/*  90 */     if (this.goal.type == Goal.Type.OWN_GOAL) {
/*  91 */       this.match.setStatesForOwnGoal(this.goal);
/*     */     } else {
/*  93 */       this.match.setStatesForGoal(this.goal);
/*     */     } 
/*     */     
/*  96 */     float timeLeft = deltaTime;
/*  97 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  99 */       if (this.match.subframe % 8 == 0) {
/* 100 */         this.match.updateAi();
/*     */       }
/*     */       
/* 103 */       this.match.updateBall();
/* 104 */       this.match.ball.collisionGoal();
/* 105 */       this.match.ball.collisionNet();
/*     */       
/* 107 */       this.match.updatePlayers(true);
/*     */       
/* 109 */       this.match.nextSubframe();
/*     */       
/* 111 */       this.sceneRenderer.save();
/*     */       
/* 113 */       if (this.followBall) {
/* 114 */         if (this.match.ball.v == 0.0F && this.match.ball.vz == 0.0F) {
/* 115 */           this.followBall = false;
/* 116 */           setCameraMode();
/*     */         } 
/*     */       } else {
/* 119 */         this.sceneRenderer.actionCamera.setTarget(this.goal.player.x, this.goal.player.y);
/*     */       } 
/* 121 */       this.sceneRenderer.actionCamera.update();
/* 122 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setCameraMode() {
/* 127 */     if (this.followBall) {
/* 128 */       this.sceneRenderer.actionCamera
/* 129 */         .setMode(ActionCamera.Mode.FOLLOW_BALL)
/* 130 */         .setSpeed(ActionCamera.Speed.NORMAL);
/*     */     } else {
/* 132 */       this.sceneRenderer.actionCamera
/* 133 */         .setMode(ActionCamera.Mode.REACH_TARGET)
/* 134 */         .setSpeed(ActionCamera.Speed.FAST);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 140 */     if (this.match.ball.v == 0.0F && this.match.ball.vz == 0.0F && this.timer > 192) {
/*     */ 
/*     */       
/* 143 */       if (!this.recordingDone) {
/* 144 */         this.match.recorder.saveHighlight(this.sceneRenderer);
/* 145 */         this.recordingDone = true;
/*     */       } 
/*     */       
/* 148 */       if ((this.match.getSettings()).autoReplays && !this.replayDone) {
/* 149 */         this.replayDone = true;
/* 150 */         return newFadedAction(SceneFsm.ActionType.HOLD_FOREGROUND, MatchFsm.STATE_REPLAY);
/*     */       } 
/* 152 */       this.match.ball.setPosition(0.0F, 0.0F, 0.0F);
/* 153 */       this.match.ball.updatePrediction();
/* 154 */       this.sceneRenderer.actionCamera.setOffset(0.0F, 0.0F);
/*     */       
/* 156 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_STARTING_POSITIONS);
/*     */     } 
/*     */ 
/*     */     
/* 160 */     if (Gdx.input.isKeyPressed(46)) {
/* 161 */       this.replayDone = true;
/* 162 */       return newFadedAction(SceneFsm.ActionType.HOLD_FOREGROUND, MatchFsm.STATE_REPLAY);
/*     */     } 
/*     */     
/* 165 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateGoal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */