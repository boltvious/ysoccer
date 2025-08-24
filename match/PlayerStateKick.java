/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ 
/*     */ class PlayerStateKick
/*     */   extends PlayerState {
/*     */   private Mode mode;
/*     */   private boolean startedShooting;
/*     */   
/*     */   enum Mode {
/*  11 */     UNKNOWN, PASSING, KICKING, SHOOTING;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   PlayerStateKick(PlayerFsm fsm) {
/*  17 */     super(PlayerFsm.Id.STATE_KICK, fsm);
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  22 */     super.entryActions();
/*     */     
/*  24 */     this.mode = Mode.UNKNOWN;
/*  25 */     this.startedShooting = false;
/*     */     
/*  27 */     this.ball.a = this.player.kickAngle;
/*     */   }
/*     */   
/*     */   void doActions() {
/*     */     float angle;
/*  32 */     super.doActions();
/*     */ 
/*     */     
/*  35 */     if (this.player.inputDevice.value) {
/*  36 */       angle = this.player.inputDevice.angle;
/*     */     } else {
/*  38 */       angle = this.player.kickAngle;
/*     */     } 
/*  40 */     float angle_diff = (angle - this.player.kickAngle + 540.0F) % 360.0F - 180.0F;
/*     */     
/*  42 */     switch (this.mode) {
/*     */       case UNKNOWN:
/*  44 */         if (this.timer > 76.8D) {
/*  45 */           if (this.player.inputDevice.fire11) {
/*  46 */             if (this.ball.isInsideDirectShotArea(-this.player.side) && this.player.seesTheGoal()) {
/*  47 */               this.mode = Mode.SHOOTING;
/*     */               
/*  49 */               Assets.Sounds.kick.play(Assets.Sounds.volume / 100.0F); break;
/*     */             } 
/*  51 */             this.mode = Mode.KICKING;
/*  52 */             this.ball.v = 250.0F;
/*     */             
/*  54 */             Assets.Sounds.kick.play(0.8F * Assets.Sounds.volume / 100.0F);
/*     */             
/*     */             break;
/*     */           } 
/*  58 */           this.mode = Mode.PASSING;
/*  59 */           this.ball.v = 240.0F;
/*     */ 
/*     */           
/*  62 */           if (angle_diff == 0.0F) {
/*  63 */             this.player.searchPassingMate();
/*  64 */             if (this.player.passingMate != null) {
/*  65 */               this.ball.a += this.player.passingMateAngleCorrection;
/*  66 */               this.ball.v += Const.PASSING_SPEED_FACTOR * this.player.passingMate.distanceFrom(this.player);
/*     */             } 
/*     */           } 
/*     */           
/*  70 */           Assets.Sounds.kick.play(0.6F * Assets.Sounds.volume / 100.0F);
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case PASSING:
/*  76 */         if (this.timer < 128.0D) {
/*     */           
/*  78 */           if (this.player.inputDevice.fire11) {
/*  79 */             this.ball.v += (960.0F + (2 * this.player.skills.shooting)) / 512.0F;
/*     */           }
/*     */ 
/*     */           
/*  83 */           float f = 1.0F;
/*  84 */           if (this.player.inputDevice.value)
/*     */           {
/*  86 */             if (Math.abs(angle_diff) < 67.5F) {
/*  87 */               f = 0.0F;
/*     */             
/*     */             }
/*  90 */             else if (Math.abs(angle_diff) > 112.5F) {
/*  91 */               f = 1.33F;
/*     */             } 
/*     */           }
/*  94 */           this.ball.vz = f * (120.0F + this.ball.v * (1.0F - 0.05F * this.player.skills.shooting) * this.timer / 512.0F);
/*     */ 
/*     */           
/*  97 */           if (this.player.inputDevice.value && 
/*  98 */             Math.abs(angle_diff) > 22.5F && Math.abs(angle_diff) < 157.5F) {
/*  99 */             this.ball.s += 0.25390625F * Math.signum(angle_diff);
/*     */           }
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case KICKING:
/* 106 */         if (this.timer < 128.0D) {
/*     */           
/* 108 */           if (this.player.inputDevice.fire11) {
/* 109 */             this.ball.v += (960.0F + (2 * this.player.skills.shooting)) / 512.0F;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 114 */           float base = 120.0F;
/* 115 */           float factor = 0.8F;
/* 116 */           if (this.player.inputDevice.value)
/*     */           {
/* 118 */             if (Math.abs(angle_diff) < 67.5F) {
/* 119 */               factor = 0.4F;
/*     */             
/*     */             }
/* 122 */             else if (Math.abs(angle_diff) > 112.5F) {
/* 123 */               factor = 1.2F;
/*     */             } 
/*     */           }
/* 126 */           this.ball.vz = factor * (base + this.ball.v * (1.0F - 0.05F * this.player.skills.shooting) * this.timer / 512.0F);
/*     */ 
/*     */           
/* 129 */           if (this.player.inputDevice.value && 
/* 130 */             Math.abs(angle_diff) > 22.5F && Math.abs(angle_diff) < 157.5F) {
/* 131 */             this.ball.s += 0.25390625F * Math.signum(angle_diff);
/*     */           }
/*     */         } 
/*     */         break;
/*     */ 
/*     */       
/*     */       case SHOOTING:
/* 138 */         if (this.timer > 102.4F && this.timer < 153.6F) {
/*     */           
/* 140 */           if (!this.startedShooting) {
/* 141 */             this.ball.v = 270.0F;
/* 142 */             this.startedShooting = true;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 150 */           if (this.player.inputDevice.fire11) {
/* 151 */             this.ball.v += (650.0F + (50 * this.player.skills.shooting)) / 512.0F;
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 156 */           float base = 110.0F;
/* 157 */           float factor = 0.8F;
/* 158 */           if (this.player.inputDevice.value)
/*     */           {
/* 160 */             if (Math.abs(angle_diff) < 67.5F) {
/* 161 */               base = 120.0F;
/* 162 */               factor = 0.35F;
/*     */             
/*     */             }
/* 165 */             else if (Math.abs(angle_diff) > 112.5F) {
/* 166 */               base = 100.0F;
/* 167 */               factor = 1.2F;
/*     */             } 
/*     */           }
/* 170 */           this.ball.vz = factor * (base + this.ball.v * (1.0F - 0.05F * this.player.skills.shooting) * this.timer / 512.0F);
/*     */ 
/*     */           
/* 173 */           if (this.player.inputDevice.value && 
/* 174 */             Math.abs(angle_diff) > 22.5F && Math.abs(angle_diff) < 157.5F) {
/* 175 */             this.ball.s += 0.25390625F * Math.signum(angle_diff);
/*     */           }
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 182 */     this.player.animationStandRun();
/*     */   }
/*     */ 
/*     */   
/*     */   State checkConditions() {
/* 187 */     if (this.timer > 179.2D) {
/* 188 */       return this.fsm.stateStandRun;
/*     */     }
/*     */     
/* 191 */     if (ballLost()) {
/* 192 */       return this.fsm.stateStandRun;
/*     */     }
/*     */     
/* 195 */     return null;
/*     */   }
/*     */   
/*     */   private boolean ballLost() {
/* 199 */     return (this.ball.owner != null && this.ball.owner.team.side != this.player.team.side);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKick.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */