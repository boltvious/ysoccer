/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PlayerStateKeeperPositioning
/*     */   extends PlayerState
/*     */ {
/*     */   private Mode mode;
/*     */   private int dangerTime;
/*     */   private final float reactivity;
/*     */   
/*     */   enum Mode
/*     */   {
/*  19 */     DEFAULT, COVER_SHOOTING_ANGLE, RECOVER_BALL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PlayerStateKeeperPositioning(PlayerFsm fsm) {
/*  26 */     super(PlayerFsm.Id.STATE_KEEPER_POSITIONING, fsm);
/*     */     
/*  28 */     this.reactivity = (23 - 3 * this.player.getSkillKeeper()) / 100.0F * 512.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  33 */     super.entryActions();
/*     */     
/*  35 */     this.mode = Mode.DEFAULT;
/*  36 */     this.dangerTime = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions() {
/*  41 */     super.doActions();
/*     */     
/*  43 */     switch (this.mode) {
/*     */       case DEFAULT:
/*  45 */         if (this.timer % 100 == 0) {
/*  46 */           setDefaultTarget();
/*     */         }
/*     */         break;
/*     */       
/*     */       case RECOVER_BALL:
/*  51 */         if (this.player.frameDistance < 128) {
/*  52 */           this.player.tx = EMath.clamp((this.ball.prediction[this.player.frameDistance]).x, 0.0F, (this.ball.xSide * 572) / 2.0F);
/*  53 */           this.player.ty = EMath.clamp((this.ball.prediction[this.player.frameDistance]).y, (this.player.side * 466), (this.player.side * 640));
/*     */         } 
/*     */         break;
/*     */       
/*     */       case COVER_SHOOTING_ANGLE:
/*  58 */         if (this.timer % 40 == 0) {
/*  59 */           setCoveringTarget();
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/*  65 */     float dx = this.player.tx - this.player.x;
/*  66 */     float dy = this.player.ty - this.player.y;
/*     */     
/*  68 */     if (Math.abs(dx) > 1.0F || Math.abs(dy) > 1.0F) {
/*     */       
/*  70 */       this.player.v = (0.25F + 0.6F * ((EMath.hypo(dx, dy) > 4.0F) ? true : false)) * this.player.speed;
/*  71 */       this.player.a = EMath.angle(this.player.x, this.player.y, this.player.tx, this.player.ty);
/*     */     } else {
/*     */       
/*  74 */       dx = this.ball.x - this.player.x;
/*  75 */       dy = this.ball.y - this.player.y;
/*  76 */       this.player.v = 0.0F;
/*  77 */       if (this.timer > 256.0F) {
/*  78 */         this.player.a = EMath.aTan2(dy, dx);
/*     */       }
/*     */     } 
/*     */     
/*  82 */     this.player.animationStandRun();
/*     */   }
/*     */   
/*     */   State checkConditions() {
/*     */     PlayerState save;
/*  87 */     if (this.player.inputDevice != this.player.ai) {
/*  88 */       return this.fsm.stateStandRun;
/*     */     }
/*     */     
/*  91 */     Player nearestOfAll = this.player.scene.getNearestOfAll();
/*     */ 
/*     */     
/*  94 */     switch (this.mode) {
/*     */       case DEFAULT:
/*  96 */         if (this.ball.isInsidePenaltyArea(this.player.side) || this.ball.ySide * this.ball.y > 640.0F) {
/*  97 */           if (this.player == nearestOfAll) {
/*  98 */             this.mode = Mode.RECOVER_BALL; break;
/*  99 */           }  if (nearestOfAll != null && nearestOfAll.side != this.player.side && this.player == this.player.team.near1 && this.ball.owner != null)
/*     */           {
/*     */             
/* 102 */             this.mode = Mode.COVER_SHOOTING_ANGLE;
/*     */           }
/*     */         } 
/*     */         break;
/*     */       
/*     */       case RECOVER_BALL:
/* 108 */         if (nearestOfAll == this.player) {
/* 109 */           if (!this.ball.isInsidePenaltyArea(this.player.side))
/* 110 */             this.mode = Mode.DEFAULT; 
/*     */           break;
/*     */         } 
/* 113 */         if (this.ball.isInsidePenaltyArea(this.player.side) && nearestOfAll != null && nearestOfAll.team != this.player.team && this.player == this.player.team.near1) {
/*     */ 
/*     */ 
/*     */           
/* 117 */           this.mode = Mode.COVER_SHOOTING_ANGLE; break;
/*     */         } 
/* 119 */         this.mode = Mode.DEFAULT;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case COVER_SHOOTING_ANGLE:
/* 125 */         if (this.ball.isInsidePenaltyArea(this.player.side)) {
/* 126 */           if (this.ball.owner == null) {
/* 127 */             if (nearestOfAll == this.player)
/* 128 */               this.mode = Mode.RECOVER_BALL;  break;
/*     */           } 
/* 130 */           if (this.ball.owner.team == this.player.team)
/* 131 */             this.mode = Mode.DEFAULT; 
/*     */           break;
/*     */         } 
/* 134 */         this.mode = Mode.DEFAULT;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     switch (this.mode) {
/*     */       case DEFAULT:
/*     */       case RECOVER_BALL:
/*     */       case COVER_SHOOTING_ANGLE:
/* 145 */         save = getSaves();
/* 146 */         if (save != null) return save;
/*     */         
/*     */         break;
/*     */     } 
/*     */     
/* 151 */     if (this.player.ballDistance <= 8.0F && 
/* 152 */       EMath.dist(this.player.x0, this.player.y0, this.ball.x0, this.ball.y0) > 8.0F && this.ball.z < 22.0F) {
/*     */       
/* 154 */       this.ball.v = 0.0F;
/* 155 */       this.ball.vz = 0.0F;
/* 156 */       this.ball.s = 0.0F;
/*     */       
/* 158 */       this.scene.setBallOwner(this.player);
/* 159 */       this.ball.setHolder(this.player);
/*     */       
/* 161 */       return this.fsm.stateKeeperKickAngle;
/*     */     } 
/*     */     
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   void exitActions() {
/* 169 */     if (this.ball.holder == this.player) {
/* 170 */       this.ball.setHolder(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private PlayerState getSaves() {
/* 176 */     boolean danger = false;
/* 177 */     if (this.ball.isInsideDirectShotArea(this.player.side) && (this.ball.owner == null || this.mode == Mode.COVER_SHOOTING_ANGLE))
/*     */     {
/* 179 */       for (int frm = 0; frm < 128; frm++) {
/* 180 */         float x = (this.ball.prediction[frm]).x;
/* 181 */         float y = (this.ball.prediction[frm]).y;
/* 182 */         float z = (this.ball.prediction[frm]).z;
/* 183 */         if (Math.abs(x) < 126.0F && 
/* 184 */           Math.abs(z) < 66.0F && 
/* 185 */           Math.abs(y) > Math.abs(this.player.y) && Math.abs(y) < Math.abs(this.player.y) + 15.0F) {
/* 186 */           danger = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 192 */     if (danger) {
/* 193 */       this.dangerTime++;
/*     */     } else {
/* 195 */       this.dangerTime = 0;
/*     */     } 
/*     */     
/* 198 */     if (this.dangerTime > this.reactivity) {
/* 199 */       float predictionX = 0.0F;
/* 200 */       float predictionZ = 0.0F;
/*     */ 
/*     */ 
/*     */       
/* 204 */       boolean found2 = false; int frm;
/* 205 */       for (frm = 0; frm < 128; frm++) {
/* 206 */         float x = (this.ball.prediction[frm]).x;
/* 207 */         float y = (this.ball.prediction[frm]).y;
/* 208 */         float z = (this.ball.prediction[frm]).z;
/* 209 */         if (Math.abs(x - this.player.x) < 126.0F && Math.abs(z) < 66.0F && Math.abs(y) > Math.abs(this.player.y) && Math.abs(y) < Math.abs(this.player.y) + 15.0F) {
/* 210 */           predictionX = x;
/* 211 */           predictionZ = z;
/* 212 */           found2 = true;
/*     */         } 
/* 214 */         if (found2) {
/*     */           break;
/*     */         }
/*     */       } 
/* 218 */       float diffX = predictionX - this.player.x;
/*     */ 
/*     */       
/* 221 */       if (predictionZ < 66.0F) {
/* 222 */         float r = EMath.hypo(diffX, predictionZ);
/*     */         
/* 224 */         if (r < 88.0F) {
/* 225 */           if (Math.abs(diffX) < 4.0F) {
/* 226 */             if (predictionZ > 16.0F) {
/*     */               
/* 228 */               if ((frm * 8) < 307.2F) {
/* 229 */                 this.player.thrustZ = Math.min(predictionZ - 16.0F, 24.0F);
/* 230 */                 return this.fsm.stateKeeperCatchingHigh;
/*     */               }
/*     */             
/*     */             }
/* 234 */             else if ((frm * 8) < 307.2F) {
/* 235 */               return this.fsm.stateKeeperCatchingLow;
/*     */             }
/*     */           
/* 238 */           } else if (predictionZ < 7.0F) {
/* 239 */             if (Math.abs(diffX) > 71.0F) {
/*     */               
/* 241 */               if ((frm * 8) < 358.4F && (frm * 8) > 128.0F) {
/* 242 */                 this.player.thrustX = (Math.abs(diffX) - 71.0F) / 55.0F;
/* 243 */                 this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 244 */                 return this.fsm.stateKeeperDivingLowSingle;
/*     */               }
/*     */             
/*     */             }
/* 248 */             else if ((frm * 8) < 256.0F) {
/* 249 */               this.player.thrustX = (Math.abs(diffX) - 8.0F) / 63.0F;
/* 250 */               this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 251 */               return this.fsm.stateKeeperDivingLowDouble;
/*     */             }
/*     */           
/* 254 */           } else if (predictionZ < 21.0F) {
/*     */             
/* 256 */             if ((frm * 8) < 358.4F && (frm * 8) > 128.0F) {
/* 257 */               this.player.thrustX = (Math.abs(diffX) - 8.0F) / 63.0F;
/* 258 */               this.player.thrustZ = (predictionZ - 7.0F) / 14.0F;
/* 259 */               this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 260 */               return this.fsm.stateKeeperDivingMiddleTwo;
/*     */             } 
/* 262 */           } else if (predictionZ < 27.0F && Math.abs(diffX) < 87.0F) {
/*     */             
/* 264 */             if ((frm * 8) < 358.4F && (frm * 8) > 128.0F) {
/* 265 */               this.player.thrustX = (Math.abs(diffX) - 8.0F) / 79.0F;
/* 266 */               this.player.thrustZ = (predictionZ - 17.0F) / 6.0F;
/* 267 */               this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 268 */               return this.fsm.stateKeeperDivingMiddleOne;
/*     */             } 
/* 270 */           } else if (predictionZ < 44.0F && Math.abs(diffX) < 87.0F) {
/*     */             
/* 272 */             if ((frm * 8) < 358.4F && (frm * 8) > 128.0F) {
/* 273 */               this.player.thrustX = (Math.abs(diffX) - 8.0F) / 79.0F;
/* 274 */               this.player.thrustZ = (float)Math.min((predictionZ - 27.0F) / 8.0D, 1.0D);
/* 275 */               this.player.a = (diffX < 0.0F) ? 180.0F : 0.0F;
/* 276 */               return this.fsm.stateKeeperDivingHighOne;
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 283 */     return null;
/*     */   }
/*     */   
/*     */   private void setDefaultTarget() {
/* 287 */     float referenceY = (this.player.side * 662);
/* 288 */     float deltaX = this.ball.x * 30.0F / Math.abs(this.ball.y - referenceY);
/*     */     
/* 290 */     float tx = Math.signum(deltaX) * Math.min(Math.abs(deltaX), 50.0F);
/* 291 */     float ty = (this.player.side * 632);
/*     */     
/* 293 */     if (EMath.dist(tx, ty, this.player.tx, this.player.ty) > 1.5F) {
/* 294 */       this.player.tx = tx;
/* 295 */       this.player.ty = ty;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setCoveringTarget() {
/* 300 */     float referenceY = (this.player.side * 640);
/* 301 */     float deltaX = this.ball.x * 38.0F / Math.abs(this.ball.y - referenceY);
/*     */     
/* 303 */     float tx = Math.signum(deltaX) * Math.min(Math.abs(deltaX), 116.0F);
/* 304 */     float ty = this.player.side * EMath.clamp(Math.abs(this.ball.y), 602.0F, 640.0F);
/*     */     
/* 306 */     if (EMath.dist(tx, ty, this.player.tx, this.player.ty) > 1.5F) {
/* 307 */       this.player.tx = tx;
/* 308 */       this.player.ty = ty;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateKeeperPositioning.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */