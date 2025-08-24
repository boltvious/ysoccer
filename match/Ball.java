/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.badlogic.gdx.math.Vector3;
/*     */ import com.ygames.ysoccer.framework.Assets;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Ball
/*     */ {
/*     */   float x;
/*     */   float y;
/*     */   float z;
/*     */   private float zMax;
/*     */   float x0;
/*     */   float y0;
/*     */   private float z0;
/*     */   float v;
/*     */   float vMax;
/*     */   float vz;
/*     */   private float vzMax;
/*     */   private float bouncing_speed;
/*     */   float a;
/*     */   float s;
/*     */   private float f;
/*     */   int xSide;
/*     */   int ySide;
/*  44 */   final Vector3[] predictionL = new Vector3[128];
/*  45 */   final Vector3[] prediction = new Vector3[128];
/*  46 */   final Vector3[] predictionR = new Vector3[128];
/*  47 */   final Data[] data = new Data[4096];
/*     */   
/*     */   int zoneX;
/*     */   
/*     */   int zoneY;
/*     */   
/*     */   float mx;
/*     */   
/*     */   float my;
/*     */   Player owner;
/*     */   Player ownerLast;
/*     */   Player goalOwner;
/*     */   Player holder;
/*     */   private final SceneSettings sceneSettings;
/*     */   
/*     */   Ball(SceneSettings sceneSettings) {
/*  63 */     this.sceneSettings = sceneSettings;
/*     */     
/*  65 */     for (int frm = 0; frm < 128; frm++) {
/*  66 */       this.predictionL[frm] = new Vector3();
/*  67 */       this.prediction[frm] = new Vector3();
/*  68 */       this.predictionR[frm] = new Vector3();
/*     */     } 
/*     */     
/*  71 */     for (int i = 0; i < this.data.length; i++) {
/*  72 */       this.data[i] = new Data();
/*     */     }
/*     */   }
/*     */   
/*     */   void setX(float x) {
/*  77 */     this.x = x;
/*  78 */     this.xSide = EMath.sgn(x);
/*     */   }
/*     */   
/*     */   void setY(float y) {
/*  82 */     this.y = y;
/*  83 */     this.ySide = EMath.sgn(y);
/*     */   }
/*     */   
/*     */   void setZ(float z) {
/*  87 */     this.z = z;
/*     */   }
/*     */   
/*     */   public void setPosition(float x, float y, float z) {
/*  91 */     setX(x);
/*  92 */     setY(y);
/*  93 */     setZ(z);
/*  94 */     this.v = 0.0F;
/*  95 */     this.vz = 0.0F;
/*  96 */     this.s = 0.0F;
/*     */   }
/*     */   
/*     */   public void setPosition(Vector2 position) {
/* 100 */     setPosition(position.x, position.y, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/* 105 */     this.x0 = this.x;
/* 106 */     this.y0 = this.y;
/* 107 */     this.z0 = this.z;
/*     */     
/* 109 */     updatePhysics(true);
/*     */     
/* 111 */     if (this.bouncing_speed > 0.0F) {
/* 112 */       Assets.Sounds.bounce.play(Math.min(this.bouncing_speed / 250.0F, 1.0F) * Assets.Sounds.volume / 100.0F);
/* 113 */       this.bouncing_speed = 0.0F;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     this.f = (this.f + 4.0F + this.v / 2500.0F) % 4.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePhysics(boolean saveBouncingSpeed) {
/* 122 */     this.a += Const.SPIN_FACTOR / 512.0F * this.s;
/* 123 */     this.s *= 1.0F - Const.SPIN_DAMPENING / 512.0F;
/*     */ 
/*     */     
/* 126 */     setX(this.x + this.v / 512.0F * EMath.cos(this.a));
/* 127 */     setY(this.y + this.v / 512.0F * EMath.sin(this.a));
/*     */     
/* 129 */     if (this.z < 1.0F) {
/*     */       
/* 131 */       this.v = (float)(this.v - (this.sceneSettings.grass.friction / 512.0F) * Math.sqrt(Math.abs(this.v)));
/*     */     } else {
/*     */       
/* 134 */       this.v *= 1.0F - Const.AIR_FRICTION / 512.0F;
/*     */     } 
/*     */ 
/*     */     
/* 138 */     if (this.v > 0.0F && this.z > 0.0F) {
/* 139 */       float vx = this.v * EMath.cos(this.a);
/* 140 */       float vy = this.v * EMath.sin(this.a);
/*     */       
/* 142 */       float windV = 0.025F * (float)Math.log10((1.0F + this.z)) * this.sceneSettings.wind.speed;
/* 143 */       float windA = 45.0F * this.sceneSettings.wind.direction;
/*     */       
/* 145 */       float windVx = windV * EMath.cos(windA);
/* 146 */       float windVy = windV * EMath.sin(windA);
/*     */       
/* 148 */       vx += windVx;
/* 149 */       vy += windVy;
/*     */       
/* 151 */       this.v = EMath.hypo(vx, vy);
/* 152 */       this.a = EMath.aTan2(vy, vx);
/*     */     } 
/*     */ 
/*     */     
/* 156 */     if (this.v <= 0.0F) {
/* 157 */       this.v = 0.0F;
/* 158 */       this.s = 0.0F;
/*     */     } 
/*     */     
/* 161 */     if (this.v > this.vMax) {
/* 162 */       this.vMax = this.v;
/*     */     }
/*     */ 
/*     */     
/* 166 */     setZ(this.z + this.vz / 512.0F);
/*     */ 
/*     */     
/* 169 */     if (this.z > 0.0F) {
/* 170 */       this.vz -= Const.GRAVITY;
/*     */ 
/*     */       
/* 173 */       this.vz *= 1.0F - Const.AIR_FRICTION / 512.0F;
/*     */     } 
/*     */ 
/*     */     
/* 177 */     if (this.z < 0.0F && this.vz < 0.0F) {
/* 178 */       setZ(0.0F);
/* 179 */       if (this.vz > -40.0F) {
/* 180 */         this.vz = 0.0F;
/*     */       } else {
/*     */         
/* 183 */         this.v *= 1.0F + this.vz / 1400.0F;
/* 184 */         this.vz *= -Const.BOUNCE * this.sceneSettings.grass.bounce;
/* 185 */         if (saveBouncingSpeed) {
/* 186 */           this.bouncing_speed = this.vz;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     if (this.z > this.zMax) {
/* 192 */       this.zMax = this.z;
/*     */     }
/*     */     
/* 195 */     if (this.vz > this.vzMax) {
/* 196 */       this.vzMax = this.vz;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePrediction() {
/* 202 */     PhysicsSet.saveValues(this);
/* 203 */     this.a -= 45.0F;
/*     */     int frm;
/* 205 */     for (frm = 0; frm < 128; frm++) {
/* 206 */       (this.predictionL[frm]).x = Math.round(this.x);
/* 207 */       (this.predictionL[frm]).y = Math.round(this.y);
/* 208 */       (this.predictionL[frm]).z = Math.round(this.z);
/* 209 */       for (int subframe = 0; subframe < 8; subframe++) {
/* 210 */         updatePhysics(false);
/*     */       }
/*     */     } 
/*     */     
/* 214 */     PhysicsSet.restoreValues(this);
/*     */     
/* 216 */     for (frm = 0; frm < 128; frm++) {
/* 217 */       (this.prediction[frm]).x = Math.round(this.x);
/* 218 */       (this.prediction[frm]).y = Math.round(this.y);
/* 219 */       (this.prediction[frm]).z = Math.round(this.z);
/* 220 */       for (int subframe = 0; subframe < 8; subframe++) {
/* 221 */         updatePhysics(false);
/*     */       }
/*     */     } 
/*     */     
/* 225 */     PhysicsSet.restoreValues(this);
/* 226 */     this.a += 45.0F;
/*     */     
/* 228 */     for (frm = 0; frm < 128; frm++) {
/* 229 */       (this.predictionR[frm]).x = Math.round(this.x);
/* 230 */       (this.predictionR[frm]).y = Math.round(this.y);
/* 231 */       (this.predictionR[frm]).z = Math.round(this.z);
/* 232 */       for (int subframe = 0; subframe < 8; subframe++) {
/* 233 */         updatePhysics(false);
/*     */       }
/*     */     } 
/*     */     
/* 237 */     PhysicsSet.restoreValues(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void updateZone(float x, float y) {
/* 243 */     this.zoneX = Math.round(x / 206.0F);
/* 244 */     this.zoneX = EMath.bound(this.zoneX, -2, 2);
/*     */     
/* 246 */     this.zoneY = Math.round(y / 184.0F);
/* 247 */     this.zoneY = EMath.bound(this.zoneY, -3, 3);
/*     */ 
/*     */     
/* 250 */     this.mx = (x % 206.0F / 206.0F + 1.5F) % 1.0F - 0.5F;
/* 251 */     this.my = (y % 184.0F / 184.0F + 1.5F) % 1.0F - 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void inFieldKeep() {
/* 257 */     if (this.x <= -565.0F) {
/* 258 */       setX(-565.0F);
/* 259 */       this.v = 0.5F * this.v;
/* 260 */       this.a = (180.0F - this.a) % 360.0F;
/* 261 */       this.s = -this.s;
/*     */     } 
/*     */ 
/*     */     
/* 265 */     if (this.x >= 572.0F) {
/* 266 */       setX(572.0F);
/* 267 */       this.v = 0.5F * this.v;
/* 268 */       this.a = (180.0F - this.a) % 360.0F;
/* 269 */       this.s = -this.s;
/*     */     } 
/*     */ 
/*     */     
/* 273 */     if (this.y <= -700.0F) {
/* 274 */       setY(-700.0F);
/* 275 */       this.v = 0.5F * this.v;
/* 276 */       this.a = -this.a % 360.0F;
/* 277 */       this.s = -this.s;
/*     */     } 
/*     */ 
/*     */     
/* 281 */     if (this.y >= 698.0F) {
/* 282 */       setY(698.0F);
/* 283 */       this.v = 0.5F * this.v;
/* 284 */       this.a = -this.a % 360.0F;
/* 285 */       this.s = -this.s;
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean isInsidePenaltyArea(int ySide) {
/* 290 */     return Const.isInsidePenaltyArea(this.x, this.y, ySide);
/*     */   }
/*     */ 
/*     */   
/*     */   boolean collisionGoal() {
/* 295 */     if (this.ySide == 0) {
/* 296 */       return false;
/*     */     }
/*     */     
/* 299 */     boolean hit = false;
/*     */ 
/*     */     
/* 302 */     if (this.ySide * this.y < 640.0F && 
/* 303 */       EMath.dist(this.y0, this.z0, (this.ySide * 640), 33.0F) > 6.0F && 
/* 304 */       EMath.dist(this.y, this.z, (this.ySide * 640), 33.0F) <= 6.0F && (
/* 305 */       EMath.dist(this.x, this.y, -71.0F, (this.ySide * 641)) <= 6.0F || 
/* 306 */       EMath.dist(this.x, this.y, 71.0F, (this.ySide * 641)) <= 6.0F)) {
/*     */ 
/*     */       
/* 309 */       float ballAxy = EMath.aTan2(this.y - this.y0, this.x - this.x0);
/*     */       
/* 311 */       this.v = 0.5F * this.v;
/* 312 */       this.a = (-ballAxy + 360.0F) % 360.0F;
/* 313 */       this.s = 0.0F;
/* 314 */       setX(this.x0);
/* 315 */       setY(this.y0);
/* 316 */       setZ(this.z0);
/*     */       
/* 318 */       hit = true;
/* 319 */       this.bouncing_speed = this.v;
/*     */       
/* 321 */       GLGame.debug(GLGame.LogType.BALL, this, "Goal top corner collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */ 
/*     */     
/*     */     }
/* 325 */     else if (EMath.dist(this.y0, this.z0, (this.ySide * 640), 33.0F) > 5.0F && 
/* 326 */       EMath.dist(this.y, this.z, (this.ySide * 640), 33.0F) <= 5.0F && -73.0F < this.x && this.x < 73.0F) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 331 */       float ballAxy = EMath.aTan2(this.y - this.y0, this.x - this.x0);
/*     */       
/* 333 */       float ballVx = this.v * EMath.cos(ballAxy);
/* 334 */       float ballVy = this.v * EMath.sin(ballAxy);
/*     */ 
/*     */       
/* 337 */       float angle = EMath.aTan2(this.z - 33.0F, this.y - (this.ySide * 640));
/*     */ 
/*     */       
/* 340 */       float ballVyz = (float)Math.sqrt((ballVy * ballVy + this.vz * this.vz));
/* 341 */       float ballAyz = EMath.aTan2(this.vz, ballVy);
/*     */ 
/*     */       
/* 344 */       ballAyz = 2.0F * angle - ballAyz + 180.0F;
/*     */ 
/*     */       
/* 347 */       ballVy = 0.5F * ballVyz * EMath.cos(ballAyz);
/* 348 */       this.vz = 0.5F * ballVyz * EMath.sin(ballAyz);
/*     */ 
/*     */       
/* 351 */       this.v = (float)Math.sqrt((ballVx * ballVx + ballVy * ballVy));
/* 352 */       this.a = EMath.aTan2(ballVy, ballVx);
/* 353 */       this.s = 0.0F;
/* 354 */       setX(this.x0);
/* 355 */       setY(this.y0);
/* 356 */       setZ(this.z0);
/*     */       
/* 358 */       hit = true;
/* 359 */       this.bouncing_speed = 0.5F * ballVyz;
/*     */       
/* 361 */       GLGame.debug(GLGame.LogType.BALL, this, "Goal crossbar collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */ 
/*     */     
/*     */     }
/* 365 */     else if (EMath.dist(this.x0, this.y0, (this.xSide * 71), (this.ySide * 641)) > 5.0F && 
/* 366 */       EMath.dist(this.x, this.y, (this.xSide * 71), (this.ySide * 641)) <= 5.0F && this.z <= 35.0F) {
/*     */ 
/*     */ 
/*     */       
/* 370 */       float ballAxy = EMath.aTan2(this.y - this.y0, this.x - this.x0);
/*     */       
/* 372 */       float angle = EMath.aTan2(this.y - (this.ySide * 640), this.x - (this.xSide * 71));
/*     */ 
/*     */       
/* 375 */       this.v = 0.5F * this.v;
/* 376 */       this.a = (2.0F * angle - ballAxy + 180.0F) % 360.0F;
/* 377 */       this.s = 0.0F;
/* 378 */       setX(this.x0);
/* 379 */       setY(this.y0);
/* 380 */       setZ(this.z0);
/*     */       
/* 382 */       hit = true;
/* 383 */       this.bouncing_speed = this.v;
/*     */       
/* 385 */       GLGame.debug(GLGame.LogType.BALL, this, "Goal post collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */     } 
/*     */ 
/*     */     
/* 389 */     if (hit) {
/* 390 */       float volume = Math.min(EMath.floor((this.bouncing_speed / 10.0F)) / 10.0F, 1.0F);
/* 391 */       Assets.Sounds.post.play(volume * Assets.Sounds.volume / 100.0F);
/* 392 */       this.bouncing_speed = 0.0F;
/*     */     } 
/*     */     
/* 395 */     return hit;
/*     */   }
/*     */ 
/*     */   
/*     */   void collisionFlagPosts() {
/* 400 */     if (EMath.dist(Math.abs(this.x), Math.abs(this.y), 510.0F, 640.0F) <= 5.0F && 
/* 401 */       EMath.dist(Math.abs(this.x0), Math.abs(this.y0), 510.0F, 640.0F) > 5.0F && this.z <= 21.0F) {
/*     */ 
/*     */ 
/*     */       
/* 405 */       float ballAxy = EMath.aTan2(this.y - this.y0, this.x - this.x0);
/*     */       
/* 407 */       float angle = EMath.aTan2(this.y - Math.signum(this.y) * 640.0F, this.x - Math.signum(this.x) * 510.0F);
/* 408 */       this.v = 0.3F * this.v;
/* 409 */       this.a = (2.0F * angle - ballAxy + 180.0F) % 360.0F;
/* 410 */       this.s = 0.0F;
/*     */       
/* 412 */       setX(this.x0);
/* 413 */       setY(this.y0);
/* 414 */       setZ(this.z0);
/*     */       
/* 416 */       float volume = Math.min(EMath.floor((this.v / 10.0F)) / 20.0F, 1.0F);
/* 417 */       Assets.Sounds.post.play(volume * Assets.Sounds.volume / 100.0F);
/*     */       
/* 419 */       GLGame.debug(GLGame.LogType.BALL, this, "Flag post collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void collisionNet() {
/* 425 */     boolean xTest = (Math.abs(this.x0) < 69.0F);
/* 426 */     if (!xTest)
/* 427 */       return;  boolean yTest = (Math.abs(this.y0) > 640.0F && Math.abs(this.y0) < 657.0F);
/* 428 */     if (!yTest)
/* 429 */       return;  boolean zTest = (this.z0 < 31.0F - (Math.abs(this.y0) - 640.0F) / 3.0F);
/* 430 */     if (!zTest)
/*     */       return; 
/* 432 */     boolean collision = false;
/*     */ 
/*     */     
/* 435 */     if (Math.abs(this.y) >= 657.0F) {
/*     */       
/* 437 */       float ballVx = this.v * EMath.cos(this.a);
/* 438 */       float ballVy = this.v * EMath.sin(this.a);
/*     */       
/* 440 */       ballVx = 0.5F * ballVx;
/* 441 */       ballVy = -0.1F * ballVy;
/*     */       
/* 443 */       this.v = EMath.hypo(ballVx, ballVy);
/* 444 */       this.a = EMath.aTan2(ballVy, ballVx);
/*     */       
/* 446 */       collision = true;
/*     */       
/* 448 */       GLGame.debug(GLGame.LogType.BALL, this, "Net internal back collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */     } 
/*     */ 
/*     */     
/* 452 */     if (Math.abs(this.x) >= 69.0F) {
/*     */       
/* 454 */       float ballVx = this.v * EMath.cos(this.a);
/* 455 */       float ballVy = this.v * EMath.sin(this.a);
/*     */       
/* 457 */       ballVx = -0.1F * ballVx;
/* 458 */       ballVy = 0.5F * ballVy;
/*     */       
/* 460 */       this.v = EMath.hypo(ballVx, ballVy);
/* 461 */       this.a = EMath.aTan2(ballVy, ballVx);
/*     */       
/* 463 */       collision = true;
/*     */       
/* 465 */       GLGame.debug(GLGame.LogType.BALL, this, "Net internal side collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */     } 
/*     */ 
/*     */     
/* 469 */     if (this.z >= 31.0F - (Math.abs(this.y) - 640.0F) / 3.0F) {
/*     */       
/* 471 */       float ballVx = this.v * EMath.cos(this.a);
/* 472 */       float ballVy = this.v * EMath.sin(this.a);
/*     */       
/* 474 */       float ballVyz = 0.25F * EMath.sqrt(ballVy * ballVy + this.vz * this.vz);
/* 475 */       float ballAyz = EMath.aTan2(0.0F, this.y - Math.signum(this.y) * 640.0F);
/*     */       
/* 477 */       ballVy = ballVyz * EMath.cos(ballAyz);
/* 478 */       ballVy = Math.signum(ballVy) * Math.abs(ballVy);
/*     */       
/* 480 */       this.vz = -0.25F * this.vz;
/* 481 */       this.v = EMath.hypo(ballVx, ballVy);
/* 482 */       this.a = EMath.aTan2(ballVy, ballVx);
/*     */       
/* 484 */       collision = true;
/*     */       
/* 486 */       GLGame.debug(GLGame.LogType.BALL, this, "Net internal top collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */     } 
/*     */     
/* 489 */     if (collision) {
/* 490 */       setX(this.x0);
/* 491 */       setY(this.y0);
/* 492 */       setZ(this.z0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void collisionNetOut() {
/* 498 */     boolean xTest = (Math.abs(this.x) <= 73.0F);
/* 499 */     if (!xTest)
/* 500 */       return;  boolean yTest = EMath.isIn(Math.abs(this.y), 640.0F, 661.0F);
/* 501 */     if (!yTest)
/* 502 */       return;  boolean zTest = (this.z <= 37.0F - (Math.abs(this.y) - 640.0F) / 3.0F);
/* 503 */     if (!zTest)
/*     */       return; 
/* 505 */     boolean collision = false;
/*     */ 
/*     */     
/* 508 */     if (this.z0 > 37.0F - (Math.abs(this.y0) - 640.0F) / 3.0F) {
/*     */       
/* 510 */       float ballVx = this.v * EMath.cos(this.a);
/* 511 */       float ballVy = this.v * EMath.sin(this.a);
/*     */       
/* 513 */       float ballVyz = 0.25F * EMath.sqrt(ballVy * ballVy + this.vz * this.vz);
/* 514 */       float ballAyz = EMath.aTan2(0.0F, this.y - Math.signum(this.y) * 640.0F);
/*     */       
/* 516 */       ballVy = ballVyz * EMath.cos(ballAyz);
/* 517 */       ballVy = Math.signum(ballVy) * Math.max(30.0F, Math.abs(ballVy));
/*     */       
/* 519 */       this.vz = -0.25F * this.vz;
/* 520 */       this.v = EMath.hypo(ballVx, ballVy);
/* 521 */       this.a = EMath.aTan2(ballVy, ballVx);
/*     */       
/* 523 */       collision = true;
/*     */       
/* 525 */       GLGame.debug(GLGame.LogType.BALL, this, "Net external top collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */     } 
/*     */ 
/*     */     
/* 529 */     if (Math.abs(this.y0) > 661.0F) {
/*     */       
/* 531 */       float ballVx = this.v * EMath.cos(this.a);
/* 532 */       float ballVy = this.v * EMath.sin(this.a);
/*     */       
/* 534 */       ballVx = 0.5F * ballVx;
/* 535 */       ballVy = -0.15F * ballVy;
/*     */       
/* 537 */       this.v = EMath.hypo(ballVx, ballVy);
/* 538 */       this.a = EMath.aTan2(ballVy, ballVx);
/*     */       
/* 540 */       collision = true;
/*     */       
/* 542 */       GLGame.debug(GLGame.LogType.BALL, this, "Net external back collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */     } 
/*     */ 
/*     */     
/* 546 */     if (Math.abs(this.x0) > 73.0F) {
/*     */       
/* 548 */       float ballVx = this.v * EMath.cos(this.a);
/* 549 */       float ballVy = this.v * EMath.sin(this.a);
/*     */       
/* 551 */       ballVx = -0.15F * ballVx;
/* 552 */       ballVy = 0.5F * ballVy;
/*     */       
/* 554 */       this.v = EMath.hypo(ballVx, ballVy);
/* 555 */       this.a = EMath.aTan2(ballVy, ballVx);
/*     */       
/* 557 */       collision = true;
/*     */       
/* 559 */       GLGame.debug(GLGame.LogType.BALL, this, "Net external side collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */     } 
/*     */     
/* 562 */     if (collision) {
/* 563 */       setX(this.x0);
/* 564 */       setY(this.y0);
/* 565 */       setZ(this.z0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void collisionJumpers() {
/* 571 */     if (EMath.dist(Math.abs(this.x), Math.abs(this.y), 92.0F, 684.0F) <= 5.0F && 
/* 572 */       EMath.dist(Math.abs(this.x0), Math.abs(this.y0), 92.0F, 684.0F) > 5.0F && this.z < 40.0F) {
/*     */ 
/*     */ 
/*     */       
/* 576 */       float ballAxy = EMath.aTan2(this.y - this.y0, this.x - this.x0);
/*     */       
/* 578 */       float angle = EMath.aTan2(this.y - Math.signum(this.y) * 684.0F, this.x - Math.signum(this.x) * 92.0F);
/* 579 */       this.v = 0.3F * this.v;
/* 580 */       this.a = (2.0F * angle - ballAxy + 180.0F) % 360.0F;
/* 581 */       this.s = 0.0F;
/*     */       
/* 583 */       setX(this.x0);
/* 584 */       setY(this.y0);
/* 585 */       setZ(this.z0);
/*     */       
/* 587 */       float volume = Math.min(EMath.floor((this.v / 10.0F)) / 20.0F, 1.0F);
/* 588 */       Assets.Sounds.post.play(volume * Assets.Sounds.volume / 100.0F);
/*     */       
/* 590 */       GLGame.debug(GLGame.LogType.BALL, this, "Jumper collision, v: " + this.v + ", a: " + this.a + ", vz: " + this.vz);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void collisionPlayer(Player player) {
/* 596 */     float ballAxy = EMath.aTan2(this.y - this.y0, this.x - this.x0);
/* 597 */     float angle = EMath.aTan2(this.y - player.y, this.x - player.x);
/* 598 */     this.v = 0.25F * this.v;
/* 599 */     this.a = (2.0F * angle - ballAxy + 180.0F) % 360.0F;
/* 600 */     this.s = 0.0F;
/*     */     
/* 602 */     setX(this.x0);
/* 603 */     setY(this.y0);
/* 604 */     setZ(this.z0);
/*     */   }
/*     */   
/*     */   public void setHolder(Player player) {
/* 608 */     this.holder = player;
/*     */   }
/*     */   
/*     */   public void save(int subframe) {
/* 612 */     (this.data[subframe]).x = Math.round(this.x);
/* 613 */     (this.data[subframe]).y = Math.round(this.y);
/* 614 */     (this.data[subframe]).z = Math.round(this.z);
/* 615 */     (this.data[subframe]).fmx = (int)Math.floor(this.f);
/*     */   }
/*     */   
/*     */   boolean isInsideDirectShotArea(int ySide) {
/* 619 */     return Const.isInsideDirectShotArea(this.x, this.y, ySide);
/*     */   }
/*     */   
/*     */   static class PhysicsSet {
/*     */     static float x;
/*     */     static float y;
/*     */     static float z;
/*     */     static float v;
/*     */     static float vz;
/*     */     static float a;
/*     */     static float s;
/*     */     
/*     */     static void saveValues(Ball ball) {
/* 632 */       x = ball.x;
/* 633 */       y = ball.y;
/* 634 */       z = ball.z;
/* 635 */       v = ball.v;
/* 636 */       vz = ball.vz;
/* 637 */       a = ball.a;
/* 638 */       s = ball.s;
/*     */     }
/*     */     
/*     */     static void restoreValues(Ball ball) {
/* 642 */       ball.setX(x);
/* 643 */       ball.setY(y);
/* 644 */       ball.setZ(z);
/* 645 */       ball.v = v;
/* 646 */       ball.vz = vz;
/* 647 */       ball.a = a;
/* 648 */       ball.s = s;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Ball.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */