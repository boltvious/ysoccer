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
/*     */ 
/*     */ 
/*     */ class MatchStateCornerStop
/*     */   extends MatchState
/*     */ {
/*  17 */   private final Vector2 cornerPosition = new Vector2();
/*     */   
/*     */   MatchStateCornerStop(MatchFsm fsm) {
/*  20 */     super(fsm);
/*     */     
/*  22 */     this.displayControlledPlayer = true;
/*  23 */     this.displayTime = true;
/*  24 */     this.displayWindVane = true;
/*  25 */     this.displayRadar = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  30 */     super.entryActions();
/*     */     
/*  32 */     if ((this.match.team[0]).side == -this.match.ball.ySide) {
/*  33 */       (this.match.stats[0]).cornersWon++;
/*     */     } else {
/*  35 */       (this.match.stats[1]).cornersWon++;
/*     */     } 
/*     */     
/*  38 */     Assets.Sounds.whistle.play(Assets.Sounds.volume / 100.0F);
/*     */     
/*  40 */     this.cornerPosition.set((498 * this.match.ball.xSide), (628 * this.match.ball.ySide));
/*     */ 
/*     */ 
/*     */     
/*  44 */     this.match.ball.updateZone(this.cornerPosition.x, this.cornerPosition.y);
/*  45 */     this.match.updateTeamTactics();
/*  46 */     ((Player)(this.match.team[0]).lineup.get(0)).setTarget(0.0F, ((this.match.team[0]).side * 632));
/*  47 */     ((Player)(this.match.team[1]).lineup.get(0)).setTarget(0.0F, ((this.match.team[1]).side * 632));
/*     */     
/*  49 */     this.match.resetAutomaticInputDevices();
/*     */     
/*  51 */     this.match.setPlayersState(PlayerFsm.Id.STATE_REACH_TARGET, (Player)null);
/*     */   }
/*     */ 
/*     */   
/*     */   void onResume() {
/*  56 */     this.match.setPointOfInterest(this.cornerPosition);
/*     */     
/*  58 */     this.sceneRenderer.actionCamera
/*  59 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  60 */       .setSpeed(ActionCamera.Speed.NORMAL)
/*  61 */       .setLimited(true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  66 */     super.doActions(deltaTime);
/*     */     
/*  68 */     float timeLeft = deltaTime;
/*  69 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  71 */       if (this.match.subframe % 8 == 0) {
/*  72 */         this.match.updateAi();
/*     */       }
/*     */       
/*  75 */       this.match.updateBall();
/*  76 */       this.match.ball.inFieldKeep();
/*  77 */       this.match.ball.collisionGoal();
/*  78 */       this.match.ball.collisionJumpers();
/*  79 */       this.match.ball.collisionNetOut();
/*     */       
/*  81 */       this.match.updatePlayers(true);
/*     */       
/*  83 */       this.match.nextSubframe();
/*     */       
/*  85 */       this.sceneRenderer.save();
/*     */       
/*  87 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  89 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/*  95 */     if (this.match.ball.v < 5.0F && this.match.ball.vz < 5.0F) {
/*  96 */       this.match.ball.setPosition(this.cornerPosition);
/*  97 */       this.match.ball.updatePrediction();
/*     */       
/*  99 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_CORNER_KICK);
/*     */     } 
/*     */     
/* 102 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateCornerStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */