/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AiStateKicking
/*     */   extends AiState
/*     */ {
/*     */   private float targetDistance;
/*     */   private float targetAngle;
/*     */   private float kickDuration;
/*     */   private float spinSign;
/*     */   
/*     */   AiStateKicking(AiFsm fsm) {
/*  22 */     super(AiFsm.Id.STATE_KICKING, fsm);
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  27 */     super.entryActions();
/*     */     
/*  29 */     this.ai.x0 = Math.round(EMath.cos(this.player.a));
/*  30 */     this.ai.y0 = Math.round(EMath.sin(this.player.a));
/*     */     
/*  32 */     setGoalTarget();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  37 */     this.kickDuration = EMath.rand(8, 11) + this.targetDistance / 60.0F;
/*     */     
/*  39 */     float signedAngleDiff = EMath.signedAngleDiff(this.targetAngle, this.player.a);
/*  40 */     if (Math.abs(signedAngleDiff) > 3.0F) {
/*  41 */       this.spinSign = EMath.sgn(signedAngleDiff);
/*     */     } else {
/*  43 */       this.spinSign = 0.0F;
/*     */     } 
/*     */     
/*  46 */     GLGame.debug(GLGame.LogType.AI_KICKING, this, this.player.numberName() + ", targetDistance: " + this.targetDistance + ", targetAngle: " + this.targetAngle + ", kickDuration: " + this.kickDuration + ", spinSign: " + this.spinSign + ", ai.x0: " + this.ai.x0 + ", ai.y0: " + this.ai.y0 + ", ai.angle: " + this.ai.angle + ", player.a: " + this.player.a);
/*     */   }
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
/*     */   void doActions() {
/*  60 */     super.doActions();
/*     */     
/*  62 */     this.ai.x0 = Math.round(EMath.cos(this.player.a));
/*  63 */     this.ai.y0 = Math.round(EMath.sin(this.player.a));
/*  64 */     if (this.spinSign != 0.0F && this.player.getState().checkId(PlayerFsm.Id.STATE_KICK)) {
/*  65 */       this.ai.x0 = Math.round(EMath.cos(this.player.a + this.spinSign * 90.0F));
/*  66 */       this.ai.y0 = Math.round(EMath.sin(this.player.a + this.spinSign * 90.0F));
/*     */     } 
/*  68 */     this.ai.fire10 = (this.timer < this.kickDuration);
/*     */   }
/*     */ 
/*     */   
/*     */   State checkConditions() {
/*  73 */     if (this.timer > this.kickDuration) {
/*  74 */       return this.fsm.stateIdle;
/*     */     }
/*  76 */     return null;
/*     */   }
/*     */   
/*     */   private void setGoalTarget() {
/*  80 */     float TARGET_X = 59.0F;
/*  81 */     float nearPostAngle = EMath.angle(this.player.ball.x, this.player.ball.y, this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/*  82 */     float farPostAngle = EMath.angle(this.player.ball.x, this.player.ball.y, -this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/*  83 */     float nearPostCorrection = Math.abs(nearPostAngle - this.player.a);
/*  84 */     float farPostCorrection = Math.abs(farPostAngle - this.player.a);
/*     */     
/*  86 */     GLGame.debug(GLGame.LogType.AI_KICKING, this, this.player.numberName() + ", nearPostAngle: " + nearPostAngle + ", farPostAngle: " + farPostAngle + ", nearPostCorrection: " + nearPostCorrection + ", farPostCorrection: " + farPostCorrection);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     if (nearPostCorrection < farPostCorrection) {
/*  94 */       this.targetDistance = EMath.dist(this.player.ball.x, this.player.ball.y, this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/*  95 */       this.targetAngle = EMath.angle(this.player.ball.x, this.player.ball.y, this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/*  96 */       GLGame.debug(GLGame.LogType.AI_KICKING, this.player.shirtName, "Near post angle: " + this.targetAngle);
/*     */     } else {
/*  98 */       this.targetDistance = EMath.dist(this.player.ball.x, this.player.ball.y, this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/*  99 */       this.targetAngle = EMath.angle(this.player.ball.x, this.player.ball.y, -this.player.ball.xSide * 59.0F, (-this.player.team.side * 640));
/* 100 */       GLGame.debug(GLGame.LogType.AI_KICKING, this.player.shirtName, "Far post angle: " + this.targetAngle);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateKicking.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */