/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PlayerStateKeeperPenaltyPositioning
/*     */   extends PlayerState
/*     */ {
/*     */   private int dangerTime;
/*     */   private float reactivity;
/*     */   
/*     */   enum Mode
/*     */   {
/*  16 */     DEFAULT, COVER_SHOOTING_ANGLE, RECOVER_BALL;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   PlayerStateKeeperPenaltyPositioning(PlayerFsm fsm) {
/*  22 */     super(PlayerFsm.Id.STATE_KEEPER_PENALTY_POSITIONING, fsm);
/*     */     
/*  24 */     this.reactivity = (23 - 3 * this.player.getSkillKeeper()) / 100.0F * 512.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  29 */     super.entryActions();
/*     */     
/*  31 */     this.dangerTime = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions() {
/*  36 */     super.doActions();
/*     */     
/*  38 */     setDefaultTarget();
/*     */ 
/*     */     
/*  41 */     float dx = this.player.tx - this.player.x;
/*  42 */     float dy = this.player.ty - this.player.y;
/*     */     
/*  44 */     if (Math.abs(dx) > 1.0F || Math.abs(dy) > 1.0F) {
/*     */       
/*  46 */       this.player.v = (0.25F + 0.6F * ((EMath.hypo(dx, dy) > 4.0F) ? true : false)) * this.player.speed;
/*  47 */       this.player.a = EMath.angle(this.player.x, this.player.y, this.player.tx, this.player.ty);
/*     */     } else {
/*     */       
/*  50 */       dx = this.ball.x - this.player.x;
/*  51 */       dy = this.ball.y - this.player.y;
/*  52 */       this.player.v = 0.0F;
/*  53 */       if (this.timer > 256.0F) {
/*  54 */         this.player.a = EMath.aTan2(dy, dx);
/*     */       }
/*     */     } 
/*     */     
/*  58 */     this.player.animationStandRun();
/*     */   }
/*     */ 
/*     */   
/*     */   State checkConditions() {
/*  63 */     if (this.player.inputDevice != this.player.ai) {
/*  64 */       return this.fsm.stateStandRun;
/*     */     }
/*     */     
/*  67 */     PlayerState save = getSaves();
/*  68 */     if (save != null) return save;
/*     */     
/*  70 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private PlayerState getSaves() {
/*  75 */     boolean danger = false;
/*  76 */     for (int frm = 0; frm < 128; frm++) {
/*  77 */       float x = (this.ball.prediction[frm]).x;
/*  78 */       float y = (this.ball.prediction[frm]).y;
/*  79 */       float z = (this.ball.prediction[frm]).z;
/*  80 */       if (Math.abs(x) < 126.0F && 
/*  81 */         Math.abs(z) < 66.0F && 
/*  82 */         Math.abs(y) > Math.abs(this.player.y) && Math.abs(y) < Math.abs(this.player.y) + 15.0F) {
/*  83 */         danger = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  88 */     if (danger) {
/*  89 */       this.dangerTime++;
/*     */     } else {
/*  91 */       this.dangerTime = 0;
/*     */     } 
/*     */     
/*  94 */     if (this.dangerTime > this.reactivity) {
/*  95 */       float predX = 0.0F;
/*  96 */       float predZ = 0.0F;
/*     */ 
/*     */ 
/*     */       
/* 100 */       boolean found2 = false; int i;
/* 101 */       for (i = 0; i < 128; i++) {
/* 102 */         float x = (this.ball.prediction[i]).x;
/* 103 */         float y = (this.ball.prediction[i]).y;
/* 104 */         float z = (this.ball.prediction[i]).z;
/* 105 */         if (Math.abs(x - this.player.x) < 126.0F && Math.abs(z) < 66.0F && Math.abs(y) > Math.abs(this.player.y) && Math.abs(y) < Math.abs(this.player.y) + 15.0F) {
/* 106 */           predX = x;
/* 107 */           predZ = z;
/* 108 */           found2 = true;
/*     */         } 
/* 110 */         if (found2) {
/*     */           break;
/*     */         }
/*     */       } 
/* 114 */       float diffX = predX - this.player.x;
/*     */ 
/*     */       
/* 117 */       if (predZ < 66.0F) {
/* 118 */         float r = EMath.hypo(diffX, predZ);
/*     */         
/* 120 */         if (r < 88.0F) {
/* 121 */           if (Math.abs(diffX) < 4.0F) {
/* 122 */             if (predZ > 30.0F) {
/*     */               
/* 124 */               if ((i * 8) < 307.2F) {
/* 125 */                 return this.fsm.stateKeeperCatchingHigh;
/*     */               
/*     */               }
/*     */             }
/* 129 */             else if ((i * 8) < 307.2F) {
/* 130 */               return this.fsm.stateKeeperCatchingLow;
/*     */             }
/*     */           
/* 133 */           } else if (predZ < 7.0F) {
/* 134 */             if (Math.abs(diffX) > 71.0F) {
/*     */               
/* 136 */               if ((i * 8) < 358.4F && (i * 8) > 128.0F) {
/* 137 */                 this.player.thrustX = (Math.abs(diffX) - 71.0F) / 55.0F;
/* 138 */                 this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 139 */                 return this.fsm.stateKeeperDivingLowSingle;
/*     */               }
/*     */             
/*     */             }
/* 143 */             else if ((i * 8) < 256.0F) {
/* 144 */               this.player.thrustX = (Math.abs(diffX) - 8.0F) / 63.0F;
/* 145 */               this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 146 */               return this.fsm.stateKeeperDivingLowDouble;
/*     */             }
/*     */           
/* 149 */           } else if (predZ < 21.0F) {
/*     */             
/* 151 */             if ((i * 8) < 358.4F && (i * 8) > 128.0F) {
/* 152 */               this.player.thrustX = (Math.abs(diffX) - 8.0F) / 63.0F;
/* 153 */               this.player.thrustZ = (predZ - 7.0F) / 14.0F;
/* 154 */               this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 155 */               return this.fsm.stateKeeperDivingMiddleTwo;
/*     */             } 
/* 157 */           } else if (predZ < 27.0F && Math.abs(diffX) < 87.0F) {
/*     */             
/* 159 */             if ((i * 8) < 358.4F && (i * 8) > 128.0F) {
/* 160 */               this.player.thrustX = (Math.abs(diffX) - 8.0F) / 79.0F;
/* 161 */               this.player.thrustZ = (predZ - 17.0F) / 6.0F;
/* 162 */               this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 163 */               return this.fsm.stateKeeperDivingMiddleOne;
/*     */             } 
/* 165 */           } else if (predZ < 44.0F && Math.abs(diffX) < 87.0F) {
/*     */             
/* 167 */             if ((i * 8) < 358.4F && (i * 8) > 128.0F) {
/* 168 */               this.player.thrustX = (Math.abs(diffX) - 8.0F) / 79.0F;
/* 169 */               this.player.thrustZ = (float)Math.min((predZ - 27.0F) / 8.0D, 1.0D);
/* 170 */               this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 171 */               return this.fsm.stateKeeperDivingHighOne;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 178 */     return null;
/*     */   }
/*     */   
/*     */   private void setDefaultTarget() {
/* 182 */     float tx = 0.0F;
/* 183 */     float ty = (this.player.side * 636);
/*     */     
/* 185 */     if (EMath.dist(tx, ty, this.player.tx, this.player.ty) > 1.5F) {
/* 186 */       this.player.tx = tx;
/* 187 */       this.player.ty = ty;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKeeperPenaltyPositioning.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */