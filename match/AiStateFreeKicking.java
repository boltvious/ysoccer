/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AiStateFreeKicking
/*     */   extends AiState
/*     */ {
/*     */   private float targetDistance;
/*     */   private float targetAngle;
/*     */   private int preparingTime;
/*     */   private float kickDuration;
/*     */   private float kickSpin;
/*     */   private Step step;
/*     */   private KickType kickType;
/*     */   
/*     */   enum Step
/*     */   {
/*  24 */     TURNING,
/*  25 */     PREPARING,
/*  26 */     KICKING;
/*     */   }
/*     */ 
/*     */   
/*     */   enum KickType
/*     */   {
/*  32 */     PASS,
/*  33 */     MEDIUM,
/*  34 */     LONG;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   AiStateFreeKicking(AiFsm fsm) {
/*  40 */     super(AiFsm.Id.STATE_FREE_KICKING, fsm);
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  45 */     this.step = Step.TURNING;
/*  46 */     boolean playerHasShootingConfidence = (Assets.random.nextFloat() < 0.13F * this.player.skills.shooting);
/*     */ 
/*     */     
/*  49 */     if ((this.player.getMatch()).foul.isNearOwnGoal()) {
/*  50 */       this.targetDistance = 500.0F;
/*  51 */       this.targetAngle = (-90 * this.player.team.side);
/*  52 */       Gdx.app.debug(this.player.shirtName, "Cleaning area");
/*     */ 
/*     */     
/*     */     }
/*  56 */     else if ((this.player.getMatch()).foul.isDirectShot() && playerHasShootingConfidence) {
/*     */       
/*  58 */       setGoalTarget();
/*  59 */       Gdx.app.debug(this.player.shirtName, "Kicking to goal");
/*     */     } else {
/*  61 */       Player nearPlayer = this.player.searchNearPlayer();
/*  62 */       float nearPlayerDistance = (nearPlayer == null) ? 0.0F : this.player.distanceFrom(nearPlayer);
/*  63 */       Gdx.app.debug(this.player.shirtName, "Near player: " + ((nearPlayer == null) ? " none" : (" at distance: " + this.player.distanceFrom(nearPlayer))));
/*     */ 
/*     */       
/*  66 */       if (nearPlayer != null && nearPlayerDistance < 350.0F) {
/*  67 */         this.targetDistance = 0.7F * nearPlayerDistance;
/*  68 */         this.targetAngle = EMath.angle(this.player.x, this.player.y, nearPlayer.x, nearPlayer.y);
/*  69 */         Gdx.app.debug(this.player.shirtName, "Passing to mate");
/*     */       
/*     */       }
/*     */       else {
/*     */         
/*  74 */         this.targetDistance = 500.0F;
/*  75 */         this.targetAngle = (-90 * this.player.team.side);
/*  76 */         Gdx.app.debug(this.player.shirtName, "Launching to the opponent side");
/*     */       } 
/*     */     } 
/*     */     
/*  80 */     if (this.targetDistance <= 150.0F) {
/*  81 */       this.kickType = KickType.PASS;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  86 */       this.kickDuration = 3.0F + this.targetDistance / 150.0F;
/*  87 */       this.preparingTime = 20;
/*  88 */     } else if (this.targetDistance < 300.0F) {
/*  89 */       this.kickType = KickType.MEDIUM;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  94 */       this.kickDuration = EMath.rand(8, 11) + this.targetDistance / 60.0F;
/*  95 */       this.preparingTime = 30;
/*     */     } else {
/*  97 */       this.kickType = KickType.LONG;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 102 */       this.kickDuration = EMath.rand(11, 13) + this.targetDistance / 120.0F;
/* 103 */       this.preparingTime = 40;
/*     */     } 
/* 105 */     Gdx.app.debug(this.player.shirtName, "Kicking type: " + this.kickType + ", targetDistance: " + this.targetDistance + ", kickDuration: " + this.kickDuration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void doActions() {
/* 113 */     super.doActions();
/*     */     
/* 115 */     switch (this.step) {
/*     */       case TURNING:
/* 117 */         if (this.timer > 10) {
/* 118 */           this.ai.x0 = Math.round(EMath.cos(this.targetAngle));
/* 119 */           this.ai.y0 = Math.round(EMath.sin(this.targetAngle));
/* 120 */           this.step = Step.PREPARING;
/* 121 */           this.timer = 0;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case PREPARING:
/* 126 */         if (this.timer > this.preparingTime) {
/* 127 */           float signedAngleDiff = EMath.signedAngleDiff(this.targetAngle, this.player.a);
/* 128 */           if (Math.abs(signedAngleDiff) > 3.0F) {
/* 129 */             this.kickSpin = Math.signum(signedAngleDiff);
/*     */           } else {
/* 131 */             this.kickSpin = 0.0F;
/*     */           } 
/* 133 */           Gdx.app.debug(this.player.shirtName, "angle: " + this.player.a + ", targetAngle: " + this.targetAngle + " signedAngleDiff: " + signedAngleDiff + " kickSpin: " + this.kickSpin);
/* 134 */           this.step = Step.KICKING;
/* 135 */           this.timer = 0;
/*     */         } 
/*     */         break;
/*     */       
/*     */       case KICKING:
/* 140 */         switch (this.kickType) {
/*     */           case TURNING:
/* 142 */             this.ai.x0 = Math.round(EMath.cos(this.player.a));
/* 143 */             this.ai.y0 = Math.round(EMath.sin(this.player.a));
/* 144 */             if (this.kickSpin != 0.0F && this.player.getState().checkId(PlayerFsm.Id.STATE_FREE_KICK_SPEED)) {
/* 145 */               this.ai.x0 = Math.round(EMath.cos(this.player.a + this.kickSpin * 45.0F));
/* 146 */               this.ai.y0 = Math.round(EMath.sin(this.player.a + this.kickSpin * 45.0F));
/*     */             } 
/*     */             break;
/*     */           
/*     */           case PREPARING:
/* 151 */             this.ai.x0 = 0;
/* 152 */             this.ai.y0 = 0;
/* 153 */             if (this.kickSpin != 0.0F && this.player.getState().checkId(PlayerFsm.Id.STATE_FREE_KICK_SPEED)) {
/* 154 */               this.ai.x0 = Math.round(EMath.cos(this.player.a + this.kickSpin * 90.0F));
/* 155 */               this.ai.y0 = Math.round(EMath.sin(this.player.a + this.kickSpin * 90.0F));
/*     */             } 
/*     */             break;
/*     */           
/*     */           case KICKING:
/* 160 */             this.ai.x0 = Math.round(EMath.cos(this.player.a + 180.0F));
/* 161 */             this.ai.y0 = Math.round(EMath.sin(this.player.a + 180.0F));
/* 162 */             if (this.kickSpin != 0.0F && this.player.getState().checkId(PlayerFsm.Id.STATE_FREE_KICK_SPEED)) {
/* 163 */               this.ai.x0 = Math.round(EMath.cos(this.player.a + 180.0F - this.kickSpin * 45.0F));
/* 164 */               this.ai.y0 = Math.round(EMath.sin(this.player.a + 180.0F - this.kickSpin * 45.0F));
/*     */             }  break;
/*     */         } 
/* 167 */         this.ai.fire10 = (this.timer < this.kickDuration);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   State checkConditions() {
/* 174 */     PlayerState playerState = this.player.getState();
/* 175 */     if (playerState.checkId(PlayerFsm.Id.STATE_FREE_KICK_ANGLE)) {
/* 176 */       return null;
/*     */     }
/* 178 */     if (playerState.checkId(PlayerFsm.Id.STATE_FREE_KICK_SPEED)) {
/* 179 */       return null;
/*     */     }
/* 181 */     return this.fsm.stateIdle;
/*     */   }
/*     */   
/*     */   private void setGoalTarget() {
/* 185 */     float TARGET_X = 59.0F;
/* 186 */     float nearPostAngle = EMath.angle(this.player.ball.x, this.player.ball.y, this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/* 187 */     float farPostAngle = EMath.angle(this.player.ball.x, this.player.ball.y, -this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/* 188 */     float nearPostCorrection = Math.abs(nearPostAngle - EMath.roundBy(nearPostAngle, 45.0F));
/* 189 */     float farPostCorrection = Math.abs(farPostAngle - EMath.roundBy(farPostAngle, 45.0F));
/* 190 */     Gdx.app.debug(this.player.shirtName, "nearPostAngle: " + nearPostAngle + ", farPostAngle: " + farPostAngle + ", nearPostCorrection: " + nearPostCorrection + ", farPostCorrection: " + farPostCorrection);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 197 */     if (nearPostCorrection > 12.0F && farPostCorrection > 12.0F) {
/* 198 */       this.targetDistance = EMath.dist(this.player.ball.x, this.player.ball.y, 0.0F, (-this.player.team.side * 524));
/* 199 */       this.targetAngle = EMath.angle(this.player.ball.x, this.player.ball.y, 0.0F, (-this.player.team.side * 524));
/* 200 */       Gdx.app.debug(this.player.shirtName, "Penalty spot post distance: " + this.targetDistance + ", angle: " + this.targetAngle);
/*     */ 
/*     */     
/*     */     }
/* 204 */     else if (nearPostCorrection < farPostCorrection) {
/* 205 */       this.targetDistance = EMath.dist(this.player.ball.x, this.player.ball.y, this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/* 206 */       this.targetAngle = EMath.angle(this.player.ball.x, this.player.ball.y, this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/* 207 */       Gdx.app.debug(this.player.shirtName, "Near post distance: " + this.targetDistance + ", angle: " + this.targetAngle);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 212 */       this.targetDistance = EMath.dist(this.player.ball.x, this.player.ball.y, this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/* 213 */       this.targetAngle = EMath.angle(this.player.ball.x, this.player.ball.y, -this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/* 214 */       Gdx.app.debug(this.player.shirtName, "Far post distance: " + this.targetDistance + ", angle: " + this.targetAngle);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateFreeKicking.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */