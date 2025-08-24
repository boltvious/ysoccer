/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ 
/*     */ 
/*     */ class AiStatePenaltyKicking
/*     */   extends AiState
/*     */ {
/*     */   private float targetAngle;
/*     */   private float controlsAngle;
/*     */   private float kickDuration;
/*     */   private Step step;
/*     */   private KickType kickType;
/*     */   private KickAngle kickAngle;
/*     */   
/*     */   enum Step
/*     */   {
/*  19 */     TURNING,
/*  20 */     KICKING;
/*     */   }
/*     */ 
/*     */   
/*     */   enum KickType
/*     */   {
/*  26 */     LOW,
/*  27 */     MEDIUM,
/*  28 */     HIGH;
/*     */   }
/*     */ 
/*     */   
/*     */   enum KickAngle
/*     */   {
/*  34 */     LEFT,
/*  35 */     CENTER,
/*  36 */     RIGHT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   AiStatePenaltyKicking(AiFsm fsm) {
/*  42 */     super(AiFsm.Id.STATE_PENALTY_KICKING, fsm);
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  47 */     this.step = Step.TURNING;
/*     */     
/*  49 */     this.kickType = (KickType)EMath.randomPick(KickType.class);
/*  50 */     KickAngle[] kickAngles = { KickAngle.LEFT, KickAngle.RIGHT };
/*  51 */     switch (this.kickType) {
/*     */       case TURNING:
/*  53 */         this.kickDuration = EMath.rand(100, 200) / 640.0F * 64.0F;
/*  54 */         this.kickAngle = (KickAngle)EMath.randomPick((Object[])kickAngles);
/*     */         break;
/*     */       
/*     */       case KICKING:
/*  58 */         this.kickDuration = EMath.rand(50, 200) / 640.0F * 64.0F;
/*  59 */         this.kickAngle = (KickAngle)EMath.randomPick((Object[])kickAngles);
/*     */         break;
/*     */       
/*     */       case null:
/*  63 */         this.kickDuration = EMath.rand(10, 200) / 640.0F * 64.0F;
/*  64 */         this.kickAngle = (KickAngle)EMath.randomPick((Object[])kickAngles);
/*     */         break;
/*     */     } 
/*     */     
/*  68 */     switch (this.kickAngle) {
/*     */       case TURNING:
/*  70 */         this.controlsAngle = (90 * this.player.ball.ySide - 45);
/*  71 */         this.targetAngle = (90 * this.player.ball.ySide - EMath.rand(10, 30));
/*     */         break;
/*     */       
/*     */       case KICKING:
/*  75 */         this.controlsAngle = (90 * this.player.ball.ySide);
/*  76 */         this.targetAngle = (90 * this.player.ball.ySide);
/*     */         break;
/*     */       
/*     */       case null:
/*  80 */         this.controlsAngle = (90 * this.player.ball.ySide + 45);
/*  81 */         this.targetAngle = (90 * this.player.ball.ySide + EMath.rand(10, 30));
/*     */         break;
/*     */     } 
/*  84 */     Gdx.app.debug(this.player.shirtName, "Kick type: " + this.kickType + ", kick angle: " + this.kickAngle + ", targetDuration: " + this.kickDuration + ", kickDuration: " + this.kickDuration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void doActions() {
/*  93 */     super.doActions();
/*     */     
/*  95 */     switch (this.step) {
/*     */       case TURNING:
/*  97 */         if (this.timer > 10) {
/*  98 */           this.ai.x0 = Math.round(EMath.cos(this.controlsAngle));
/*  99 */           this.ai.y0 = Math.round(EMath.sin(this.controlsAngle));
/* 100 */           Gdx.app.debug(this.player.shirtName, "angle: " + this.player.a + ", controlsAngle: " + ((this.controlsAngle + 360.0F) % 360.0F) + ", targetAngle: " + ((this.targetAngle + 360.0F) % 360.0F));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 106 */           if (EMath.angleDiff(this.player.a, this.targetAngle) < 3.0F) {
/* 107 */             this.step = Step.KICKING;
/* 108 */             this.timer = 0;
/*     */           } 
/*     */         } 
/*     */         break;
/*     */       
/*     */       case KICKING:
/* 114 */         switch (this.kickType) {
/*     */           case TURNING:
/* 116 */             this.ai.x0 = Math.round(EMath.cos(this.controlsAngle));
/* 117 */             this.ai.y0 = Math.round(EMath.sin(this.controlsAngle));
/*     */             break;
/*     */           
/*     */           case KICKING:
/* 121 */             this.ai.x0 = 0;
/* 122 */             this.ai.y0 = 0;
/*     */             break;
/*     */           
/*     */           case null:
/* 126 */             this.ai.x0 = Math.round(EMath.cos(this.controlsAngle + 180.0F));
/* 127 */             this.ai.y0 = Math.round(EMath.sin(this.controlsAngle + 180.0F)); break;
/*     */         } 
/* 129 */         this.ai.fire10 = (this.timer < this.kickDuration);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   State checkConditions() {
/* 136 */     PlayerState playerState = this.player.getState();
/* 137 */     if (playerState.checkId(PlayerFsm.Id.STATE_PENALTY_KICK_ANGLE)) {
/* 138 */       return null;
/*     */     }
/* 140 */     if (playerState.checkId(PlayerFsm.Id.STATE_PENALTY_KICK_SPEED)) {
/* 141 */       return null;
/*     */     }
/* 143 */     return this.fsm.stateIdle;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStatePenaltyKicking.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */