/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ class TrainingStateFree
/*     */   extends TrainingState
/*     */ {
/*     */   private Player lastTrained;
/*     */   private final Player[] keepers;
/*     */   
/*     */   TrainingStateFree(TrainingFsm fsm) {
/*  35 */     super(fsm);
/*     */     
/*  37 */     this.displayControlledPlayer = true;
/*     */     
/*  39 */     this.keepers = new Player[] { null, null };
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  44 */     super.entryActions();
/*     */     
/*  46 */     setIntroPositions();
/*  47 */     this.training.findNearest();
/*  48 */     this.training.resetData();
/*     */     
/*  50 */     this.lastTrained = (this.team[0]).lineup.get(0);
/*     */     
/*  52 */     this.sceneRenderer.actionCamera
/*  53 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/*  54 */       .setSpeed(ActionCamera.Speed.NORMAL)
/*  55 */       .setLimited(true, true);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  60 */     super.doActions(deltaTime);
/*     */     
/*  62 */     float timeLeft = deltaTime;
/*  63 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  65 */       if (this.training.subframe % 8 == 0) {
/*  66 */         this.training.updateFrameDistance();
/*     */       }
/*     */       
/*  69 */       this.training.updatePlayers(true);
/*  70 */       this.training.updateStates();
/*  71 */       this.training.findNearest();
/*  72 */       this.training.updateBall();
/*     */       
/*  74 */       this.ball.collisionFlagPosts();
/*  75 */       this.ball.collisionGoal();
/*  76 */       this.ball.collisionJumpers();
/*  77 */       this.ball.collisionNet();
/*  78 */       this.ball.collisionNetOut();
/*     */       
/*  80 */       if (this.ball.v == 0.0F && (Math.abs(this.ball.y) >= 644.0F || Math.abs(this.ball.x) > 514.0F)) {
/*  81 */         resetBall();
/*     */       }
/*     */       
/*  84 */       for (int t = 0; t <= 1; t++) {
/*  85 */         for (Player player : (this.team[t]).lineup) {
/*  86 */           if (player.inputDevice == player.ai) {
/*  87 */             if (player.checkState(PlayerFsm.Id.STATE_STAND_RUN)) {
/*  88 */               player.watchPosition(this.ball.x, this.ball.y);
/*     */             }
/*  90 */             if (player.checkState(PlayerFsm.Id.STATE_KEEPER_KICK_ANGLE) && 
/*  91 */               !Const.isInsideGoalArea(this.lastTrained.x, this.lastTrained.y, player.side) && 
/*  92 */               (player.getState()).timer > 512) {
/*  93 */               player.setState(PlayerFsm.Id.STATE_KEEPER_POSITIONING);
/*  94 */               this.ball.a = player.angleToPoint(this.lastTrained.x, this.lastTrained.y);
/*  95 */               this.ball.v = 180.0F;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 102 */       if (this.training.subframe % 64 == 0) {
/* 103 */         this.training.ball.updatePrediction();
/*     */       }
/*     */       
/* 106 */       this.training.nextSubframe();
/*     */       
/* 108 */       this.sceneRenderer.save();
/*     */       
/* 110 */       this.sceneRenderer.actionCamera.update();
/*     */       
/* 112 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */     
/* 115 */     if (this.ball.owner != null && this.ball.owner != this.lastTrained)
/*     */     {
/*     */       
/* 118 */       if (this.ball.owner.role == Player.Role.GOALKEEPER) {
/* 119 */         if (this.ball.owner != this.keepers[this.ball.owner.team.index]) {
/* 120 */           Team team = this.ball.owner.team;
/* 121 */           Player newKeeper = this.ball.owner;
/* 122 */           Player oldKeeper = this.keepers[team.index];
/*     */           
/* 124 */           newKeeper.setState(PlayerFsm.Id.STATE_KEEPER_POSITIONING);
/*     */           
/* 126 */           resetTargetPosition(oldKeeper);
/* 127 */           oldKeeper.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */           
/* 129 */           Collections.swap(team.lineup, oldKeeper.index, newKeeper.index);
/* 130 */           this.keepers[team.index] = newKeeper;
/*     */           
/* 132 */           this.ball.a = newKeeper.angleToPoint(this.lastTrained.x, this.lastTrained.y);
/* 133 */           this.ball.v = 180.0F;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 138 */       else if (this.ball.owner.inputDevice == this.ball.owner.ai && this.lastTrained.inputDevice != this.lastTrained.ai) {
/*     */ 
/*     */         
/* 141 */         this.ball.owner.setInputDevice(this.lastTrained.inputDevice);
/* 142 */         this.ball.owner.setState(PlayerFsm.Id.STATE_STAND_RUN);
/*     */         
/* 144 */         this.lastTrained.setInputDevice((InputDevice)this.lastTrained.ai);
/* 145 */         resetTargetPosition(this.lastTrained);
/* 146 */         this.lastTrained.setState(PlayerFsm.Id.STATE_REACH_TARGET);
/*     */       } 
/*     */     }
/*     */     
/* 150 */     if (this.ball.owner != null && this.ball.owner != this.keepers[0] && this.ball.owner != this.keepers[1] && this.ball.owner.inputDevice != this.ball.owner.ai)
/*     */     {
/*     */ 
/*     */       
/* 154 */       this.lastTrained = this.ball.owner;
/*     */     }
/*     */     
/* 157 */     if (this.lastTrained.inputDevice.fire2Down()) {
/* 158 */       resetBall();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 165 */     if (Gdx.input.isKeyPressed(131)) {
/* 166 */       quitTraining();
/* 167 */       return null;
/*     */     } 
/*     */     
/* 170 */     if (Gdx.input.isKeyPressed(46)) {
/* 171 */       return newFadedAction(SceneFsm.ActionType.HOLD_FOREGROUND, TrainingFsm.STATE_REPLAY);
/*     */     }
/*     */     
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   private void resetBall() {
/* 178 */     this.ball.setX(this.lastTrained.x + 4.0F * EMath.cos(this.lastTrained.a));
/* 179 */     this.ball.setY(this.lastTrained.y + 4.0F * EMath.sin(this.lastTrained.a));
/* 180 */     this.ball.setZ(0.0F);
/* 181 */     this.ball.vMax = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setIntroPositions() {
/* 186 */     this.ball.setPosition(0.0F, 0.0F, 0.0F);
/*     */     
/* 188 */     setIntroPositions(this.team[0]);
/* 189 */     setIntroPositions(this.team[1]);
/*     */     
/* 191 */     (this.team[0]).coach.x = -544.0F;
/* 192 */     (this.team[0]).coach.y = -166.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setIntroPositions(Team team) {
/* 197 */     int len = team.lineup.size();
/* 198 */     for (int i = 0; i < len; i++) {
/* 199 */       Player player = team.lineup.get(i);
/*     */       
/* 201 */       Vector2 target = getDefaultTarget(player);
/* 202 */       player.x = target.x;
/* 203 */       player.y = target.y;
/* 204 */       player.z = 0.0F;
/*     */       
/* 206 */       player.tx = target.x;
/* 207 */       player.ty = target.y;
/*     */ 
/*     */       
/* 210 */       if (player.role == Player.Role.GOALKEEPER && this.keepers[team.index] == null) {
/* 211 */         player.setState(PlayerFsm.Id.STATE_KEEPER_POSITIONING);
/* 212 */         this.keepers[team.index] = player;
/*     */       } else {
/* 214 */         player.setState(PlayerFsm.Id.STATE_STAND_RUN);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Vector2 getDefaultTarget(Player player) {
/* 220 */     return new Vector2((-280 + 16 * (
/* 221 */         -player.team.lineup.size() + 2 * player.index)) + 6.0F * EMath.cos((70 * player.number)), (-player.team.side * (150 + 5 * player.index % 2)) + 8.0F * 
/* 222 */         EMath.sin((70 * player.number)));
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetTargetPosition(Player player) {
/* 227 */     Vector2 target = getDefaultTarget(player);
/* 228 */     player.tx = target.x;
/* 229 */     player.ty = target.y;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\TrainingStateFree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */