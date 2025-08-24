/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.audio.Sound;
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
/*     */ class MatchStateFreeKickStop
/*     */   extends MatchState
/*     */ {
/*     */   private boolean allPlayersReachingTarget;
/*     */   private final ArrayList<Player> playersReachingTarget;
/*     */   
/*     */   MatchStateFreeKickStop(MatchFsm fsm) {
/*  24 */     super(fsm);
/*     */     
/*  26 */     this.displayTime = true;
/*  27 */     this.displayWindVane = true;
/*  28 */     this.displayRadar = true;
/*     */     
/*  30 */     this.playersReachingTarget = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  35 */     super.entryActions();
/*     */     
/*  37 */     Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */     
/*  39 */     if (this.match.settings.commentary) {
/*  40 */       int size = Assets.Commentary.foul.size();
/*  41 */       if (size > 0) {
/*  42 */         ((Sound)Assets.Commentary.foul.get(Assets.random.nextInt(size))).play(Assets.Sounds.volume / 100.0F);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  48 */     this.ball.updateZone(this.match.foul.position.x, this.match.foul.position.y);
/*  49 */     this.match.updateTeamTactics();
/*  50 */     this.match.foul.player.team.keepTargetDistanceFrom(this.match.foul.position);
/*  51 */     if (this.match.foul.isDirectShot()) {
/*  52 */       this.match.foul.player.team.setFreeKickBarrier();
/*     */     }
/*  54 */     ((Player)(this.match.team[0]).lineup.get(0)).setTarget(0.0F, ((this.match.team[0]).side * 632));
/*  55 */     ((Player)(this.match.team[1]).lineup.get(0)).setTarget(0.0F, ((this.match.team[1]).side * 632));
/*     */     
/*  57 */     this.match.resetAutomaticInputDevices();
/*     */     
/*  59 */     this.allPlayersReachingTarget = false;
/*  60 */     this.playersReachingTarget.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  65 */     this.match.setPointOfInterest(this.match.foul.position);
/*     */     
/*  67 */     this.sceneRenderer.actionCamera.setMode(ActionCamera.Mode.STILL);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  72 */     super.doActions(deltaTime);
/*     */     
/*  74 */     float timeLeft = deltaTime;
/*  75 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  77 */       if (this.match.subframe % 8 == 0) {
/*  78 */         this.match.updateAi();
/*     */         
/*  80 */         this.allPlayersReachingTarget = true;
/*  81 */         for (int t = 0; t <= 1; t++) {
/*  82 */           for (int i = 0; i < 11; i++) {
/*  83 */             Player player = (this.match.team[t]).lineup.get(i);
/*     */ 
/*     */             
/*  86 */             if (player.checkState(PlayerFsm.Id.STATE_TACKLE) || player.checkState(PlayerFsm.Id.STATE_DOWN)) {
/*  87 */               this.allPlayersReachingTarget = false;
/*  88 */             } else if (!this.playersReachingTarget.contains(player)) {
/*  89 */               player.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*  90 */               this.playersReachingTarget.add(player);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  96 */       this.match.updateBall();
/*  97 */       this.ball.inFieldKeep();
/*  98 */       this.ball.collisionFlagPosts();
/*  99 */       this.ball.collisionGoal();
/* 100 */       this.ball.collisionJumpers();
/* 101 */       this.ball.collisionNet();
/* 102 */       this.ball.collisionNetOut();
/*     */       
/* 104 */       this.match.updatePlayers(true);
/*     */       
/* 106 */       this.match.nextSubframe();
/*     */       
/* 108 */       this.sceneRenderer.save();
/*     */       
/* 110 */       this.sceneRenderer.actionCamera.update();
/*     */       
/* 112 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 118 */     if (this.allPlayersReachingTarget) {
/* 119 */       this.ball.setPosition(this.match.foul.position.x, this.match.foul.position.y, 0.0F);
/* 120 */       this.ball.updatePrediction();
/*     */       
/* 122 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_FREE_KICK);
/*     */     } 
/*     */     
/* 125 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateFreeKickStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */