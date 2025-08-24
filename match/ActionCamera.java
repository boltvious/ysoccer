/*     */ package com.ygames.ysoccer.match;class ActionCamera { private Mode mode; private Speed speed; float x;
/*     */   float y;
/*     */   private float width;
/*     */   private float height;
/*     */   private float dx;
/*     */   private float dy;
/*     */   private float vx;
/*     */   private float vy;
/*     */   private float offsetX;
/*     */   private float offsetY;
/*     */   private boolean xLimited;
/*     */   private boolean yLimited;
/*     */   private final Vector2 target;
/*     */   private float targetDistance;
/*     */   private final Ball ball;
/*     */   
/*  17 */   enum Mode { STILL,
/*  18 */     FOLLOW_BALL,
/*  19 */     REACH_TARGET; }
/*     */ 
/*     */   
/*     */   enum Speed {
/*  23 */     NORMAL,
/*  24 */     FAST,
/*  25 */     WARP;
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
/*     */   ActionCamera(Ball ball) {
/*  55 */     this.ball = ball;
/*  56 */     this.speed = Speed.NORMAL;
/*  57 */     this.target = new Vector2();
/*     */   }
/*     */   
/*     */   Mode getMode() {
/*  61 */     return this.mode;
/*     */   }
/*     */   
/*     */   ActionCamera setMode(Mode mode) {
/*  65 */     this.mode = mode;
/*  66 */     return this;
/*     */   }
/*     */   
/*     */   Speed getSpeed() {
/*  70 */     return this.speed;
/*     */   }
/*     */   
/*     */   ActionCamera setSpeed(Speed speed) {
/*  74 */     this.speed = speed;
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   ActionCamera setOffset(float x, float y) {
/*  79 */     this.offsetX = x;
/*  80 */     this.offsetY = y;
/*  81 */     return this;
/*     */   }
/*     */   
/*     */   ActionCamera setLimited(boolean xLimited, boolean yLimited) {
/*  85 */     this.xLimited = xLimited;
/*  86 */     this.yLimited = yLimited;
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   ActionCamera setTarget(float x, float y) {
/*  91 */     this.target.set(x, y);
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   ActionCamera setTarget(Vector2 t) {
/*  96 */     this.target.set(t);
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   Vector2 getCurrentTarget() {
/* 101 */     Vector2 t = new Vector2();
/*     */     
/* 103 */     switch (this.mode) {
/*     */       case STILL:
/* 105 */         t.set(this.x - this.dx, this.y - this.dy);
/*     */         break;
/*     */       
/*     */       case FOLLOW_BALL:
/* 109 */         t.set(this.ball.x + this.offsetX, this.ball.y + this.offsetY);
/*     */         break;
/*     */       
/*     */       case REACH_TARGET:
/* 113 */         t.set(this.target);
/*     */         break;
/*     */     } 
/*     */     
/* 117 */     clampTarget(t);
/*     */     
/* 119 */     return t;
/*     */   }
/*     */   
/*     */   private void clampTarget(Vector2 t) {
/* 123 */     float txMin = -847.0F + this.width / 2.0F;
/* 124 */     float txMax = 853.0F - this.width / 2.0F;
/* 125 */     float tyMin = -919.0F + this.height / 2.0F;
/* 126 */     float tyMax = 881.0F - this.height / 2.0F;
/*     */     
/* 128 */     if (this.xLimited && this.width < 1600.0F) {
/* 129 */       txMin = -510.0F - this.width / 16.0F + this.width / 2.0F;
/* 130 */       txMax = 510.0F + this.width / 16.0F - this.width / 2.0F;
/*     */     } 
/* 132 */     if (this.yLimited) {
/* 133 */       tyMin = -640.0F - this.height / 4.0F + this.height / 2.0F;
/* 134 */       tyMax = 640.0F + this.height / 4.0F - this.height / 2.0F;
/*     */     } 
/*     */     
/* 137 */     t.x = EMath.clamp(t.x, txMin, txMax);
/* 138 */     t.y = EMath.clamp(t.y, tyMin, tyMax);
/*     */   }
/*     */   
/*     */   void setScreenParameters(int screenWidth, int screenHeight, int zoom) {
/* 142 */     this.x += this.width / 2.0F;
/* 143 */     this.y += this.height / 2.0F;
/* 144 */     this.width = screenWidth / zoom / 100.0F;
/* 145 */     this.height = screenHeight / zoom / 100.0F;
/* 146 */     this.x -= this.width / 2.0F;
/* 147 */     this.y -= this.height / 2.0F;
/* 148 */     this.dx = 847.0F - this.width / 2.0F;
/* 149 */     this.dy = 919.0F - this.height / 2.0F;
/*     */   }
/*     */   
/*     */   public float getTargetDistance() {
/* 153 */     return this.targetDistance;
/*     */   }
/*     */   
/*     */   void update() {
/*     */     float x0, y0, a;
/* 158 */     switch (this.mode) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case FOLLOW_BALL:
/* 166 */         if (Math.abs(this.ball.v * EMath.cos(this.ball.a)) > 1.0F) {
/* 167 */           this.offsetX = this.width / 20.0F * EMath.cos(this.ball.a);
/*     */         }
/* 169 */         if (Math.abs(this.ball.v * EMath.sin(this.ball.a)) > 1.0F) {
/* 170 */           this.offsetY = this.height / 20.0F * EMath.sin(this.ball.a);
/*     */         }
/*     */ 
/*     */         
/* 174 */         if (Math.abs(this.vx) > Math.abs(this.ball.v * EMath.cos(this.ball.a))) {
/*     */           
/* 176 */           this.vx = 0.9902344F * this.vx;
/*     */         } else {
/*     */           
/* 179 */           this.vx += 0.0048828125F * Math.signum(this.offsetX) * Math.abs(this.vx - this.ball.v * EMath.cos(this.ball.a));
/*     */         } 
/* 181 */         if (Math.abs(this.vy) > Math.abs(this.ball.v * EMath.sin(this.ball.a))) {
/*     */           
/* 183 */           this.vy = 0.9902344F * this.vy;
/*     */         } else {
/*     */           
/* 186 */           this.vy += 0.0048828125F * Math.signum(this.offsetY) * Math.abs(this.vy - this.ball.v * EMath.sin(this.ball.a));
/*     */         } 
/*     */         
/* 189 */         this.x += this.vx / 512.0F;
/* 190 */         this.y += this.vy / 512.0F;
/*     */ 
/*     */         
/* 193 */         if (Math.abs(this.ball.x + this.offsetX - this.x - this.dx) >= 10.0F) {
/* 194 */           float f = this.ball.x + this.offsetX - this.x - this.dx;
/* 195 */           this.x += 0.01953125F * (1 + this.speed.ordinal()) * Math.signum(f) * (float)Math.sqrt(Math.abs(f));
/*     */         } 
/* 197 */         if (Math.abs(this.ball.y + this.offsetY - this.y - this.dy) >= 10.0F) {
/* 198 */           float f = this.ball.y + this.offsetY - this.y - this.dy;
/* 199 */           this.y += 0.01953125F * (1 + this.speed.ordinal()) * Math.signum(f) * (float)Math.sqrt(Math.abs(f));
/*     */         } 
/*     */         break;
/*     */       
/*     */       case REACH_TARGET:
/* 204 */         x0 = this.target.x - this.x - this.dx;
/* 205 */         y0 = this.target.y - this.y - this.dy;
/* 206 */         a = EMath.aTan2(y0, x0);
/* 207 */         this.targetDistance = EMath.sqrt(Math.abs(x0) + Math.abs(y0));
/* 208 */         this.x += 0.01953125F * (1 + this.speed.ordinal()) * this.targetDistance * EMath.cos(a);
/* 209 */         this.y += 0.01953125F * (1 + this.speed.ordinal()) * this.targetDistance * EMath.sin(a);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 214 */     float xMin = 0.0F;
/* 215 */     float xMax = 1700.0F - this.width;
/* 216 */     float yMin = 0.0F;
/* 217 */     float yMax = 1800.0F - this.height;
/*     */     
/* 219 */     if (this.xLimited && this.width < 1600.0F) {
/* 220 */       xMin = 337.0F - this.width / 16.0F;
/* 221 */       xMax = 1357.0F + this.width / 16.0F - this.width;
/*     */     } 
/* 223 */     if (this.yLimited) {
/* 224 */       yMin = 279.0F - this.height / 4.0F;
/* 225 */       yMax = 1559.0F + this.height / 4.0F - this.height;
/*     */     } 
/*     */     
/* 228 */     if (this.x < xMin) {
/* 229 */       this.x = xMin;
/*     */     }
/*     */     
/* 232 */     if (this.x > xMax) {
/* 233 */       this.x = xMax;
/*     */     }
/*     */     
/* 236 */     if (this.y < yMin) {
/* 237 */       this.y = yMin;
/*     */     }
/*     */     
/* 240 */     if (this.y > yMax)
/* 241 */       this.y = yMax; 
/*     */   } }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\ActionCamera.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */