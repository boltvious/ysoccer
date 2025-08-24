/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.math.Vector3;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ 
/*     */ class PlayerStateStandRun
/*     */   extends PlayerState
/*     */ {
/*     */   PlayerStateStandRun(PlayerFsm fsm) {
/*  11 */     super(PlayerFsm.Id.STATE_STAND_RUN, fsm);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions() {
/*  16 */     super.doActions();
/*     */     
/*  18 */     if (this.ball.owner != this.player) {
/*  19 */       this.player.getPossession();
/*     */     }
/*     */ 
/*     */     
/*  23 */     if (this.ball.owner == this.player && this.ball.z < 22.0F) {
/*     */ 
/*     */       
/*  26 */       float signedAngleDiff = EMath.signedAngleDiff(this.player.ballAngle, this.player.a);
/*     */       
/*  28 */       if (this.player.ballDistance < 9.0F) {
/*     */ 
/*     */ 
/*     */         
/*  32 */         float slice = (70 + 3 * this.player.skills.control);
/*     */ 
/*     */         
/*  35 */         if (Math.abs(signedAngleDiff) <= slice && this.player.ballDistance > 3.5F) {
/*     */ 
/*     */ 
/*     */           
/*  39 */           float m = 1.0F + 0.06F - 0.004F * this.player.skills.control;
/*  40 */           this.ball.v = Math.max(this.ball.v, m * ((this.player.ballDistance < 9.0F) ? true : false) * this.player.v);
/*     */ 
/*     */           
/*  43 */           if (Math.abs(signedAngleDiff) > 22.5D) {
/*  44 */             this.ball.a = this.player.a - signedAngleDiff / 2.0F;
/*     */           } else {
/*  46 */             this.ball.a = this.player.a;
/*     */           
/*     */           }
/*     */ 
/*     */         
/*     */         }
/*  52 */         else if (this.player.v > 0.0F) {
/*  53 */           this.ball.v = Math.max(this.ball.v, 1.2F * this.player.v);
/*  54 */           this.ball.a = this.player.a;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  61 */     if (this.player.inputDevice.value) {
/*  62 */       this.player.v = this.player.speed * (1.0F - 0.1F * ((this.player == this.ball.owner) ? true : false));
/*  63 */       this.player.a = this.player.inputDevice.angle;
/*     */     } else {
/*  65 */       this.player.v = 0.0F;
/*     */     } 
/*     */     
/*  68 */     this.player.animationStandRun();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   State checkConditions() {
/*  74 */     if (this.player.role == Player.Role.GOALKEEPER && this.player == this.player.team.lineup
/*  75 */       .get(0) && this.player.team.near1 != this.player && this.player.inputDevice == this.player.ai)
/*     */     {
/*     */       
/*  78 */       return this.fsm.stateKeeperPositioning;
/*     */     }
/*     */ 
/*     */     
/*  82 */     if (this.player.inputDevice.fire1Down())
/*     */     {
/*     */       
/*  85 */       if (this.ball.owner == this.player) {
/*  86 */         if (this.player.v > 0.0F && this.ball.z < 8.0F) {
/*  87 */           this.player.kickAngle = this.player.a;
/*  88 */           return this.fsm.stateKick;
/*     */         
/*     */         }
/*     */       
/*     */       }
/*  93 */       else if (this.player.ballDistance < 120.0F && this.player.ballIsApproaching()) {
/*     */         
/*  95 */         Vector3 ballPrediction = this.ball.prediction[Math.min(this.player.frameDistance, 127)];
/*     */         
/*  97 */         if (ballPrediction.z > 18.0F)
/*  98 */           return this.fsm.stateHead; 
/*  99 */         if (this.player.v > 0.0F && this.player
/* 100 */           .ballIsInFront() && this.player.ballDistance > 12.0F)
/*     */         {
/* 102 */           return this.fsm.stateTackle;
/*     */         
/*     */         }
/*     */       }
/* 106 */       else if (this.player.team.usesAutomaticInputDevice()) {
/* 107 */         this.player.inputDevice = (InputDevice)this.player.ai;
/*     */       } 
/*     */     }
/*     */     
/* 111 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateStandRun.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */