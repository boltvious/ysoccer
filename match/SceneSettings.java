/*     */ package com.ygames.ysoccer.match;public class SceneSettings { public Time time; public Grass grass; public Pitch.Type pitchType; public Wind wind; public int sky;
/*     */   int weatherEffect;
/*     */   int weatherStrength;
/*     */   int weatherMaxStrength;
/*     */   public boolean commentary;
/*     */   boolean fullScreen;
/*     */   public int zoom;
/*     */   float shadowAlpha;
/*     */   
/*  10 */   public enum Time { DAY, NIGHT; }
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
/*     */   public SceneSettings(Settings gameSettings) {
/*  29 */     this.zoom = gameSettings.zoom;
/*  30 */     this.fullScreen = gameSettings.fullScreen;
/*     */     
/*  32 */     this.time = randomTime();
/*  33 */     this.grass = new Grass();
/*  34 */     this.pitchType = Pitch.random();
/*  35 */     this.weatherMaxStrength = gameSettings.weatherMaxStrength;
/*  36 */     for (int i = EMath.rand(0, 2 + 4 * this.weatherMaxStrength); i >= 0; i--) {
/*  37 */       rotateWeather();
/*     */     }
/*  39 */     this.wind = new Wind();
/*     */   }
/*     */   
/*     */   public void rotateTime(int direction) {
/*  43 */     this.time = Time.values()[EMath.rotate(this.time, Time.DAY, Time.NIGHT, direction)];
/*     */   }
/*     */   
/*     */   public void rotatePitchType(int direction) {
/*  47 */     this.pitchType = Pitch.Type.values()[EMath.rotate(this.pitchType.ordinal(), Pitch.Type.FROZEN.ordinal(), Pitch.Type.WHITE.ordinal(), direction)];
/*  48 */     this.weatherEffect = 0;
/*  49 */     this.weatherStrength = 0;
/*  50 */     this.sky = 0;
/*     */   }
/*     */   
/*     */   public void rotateWeather() {
/*  54 */     if (this.weatherStrength == 0 && this.sky == 0) {
/*  55 */       this.sky = 1;
/*     */     } else {
/*     */       boolean found;
/*     */       do {
/*  59 */         found = true;
/*  60 */         if (this.weatherStrength < 2) {
/*  61 */           this.weatherStrength++;
/*     */         } else {
/*  63 */           this.weatherEffect = EMath.rotate(this.weatherEffect, 0, 3, 1);
/*  64 */           if (this.weatherEffect == 0) {
/*  65 */             this.weatherStrength = 0;
/*     */           } else {
/*  67 */             this.weatherStrength = 1;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/*  72 */         if (this.weatherStrength > Weather.cap[this.pitchType.ordinal()][this.weatherEffect]) {
/*  73 */           found = false;
/*     */         }
/*  75 */         if (this.weatherStrength > this.weatherMaxStrength) {
/*  76 */           found = false;
/*     */         }
/*     */ 
/*     */         
/*  80 */         this.sky = 1;
/*  81 */         if (this.weatherStrength == 0) {
/*  82 */           this.sky = 0;
/*  83 */         } else if (this.weatherEffect == 0) {
/*  84 */           this.sky = 0;
/*     */         } 
/*  86 */       } while (!found);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean useOrangeBall() {
/*  91 */     return (this.pitchType == Pitch.Type.SNOWED || this.pitchType == Pitch.Type.WHITE || (this.pitchType == Pitch.Type.FROZEN && this.weatherEffect == 2 && this.weatherStrength > 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getTimeLabel(Time time) {
/*  99 */     switch (time) {
/*     */       case DAY:
/* 101 */         return "TIME.DAY";
/*     */       case NIGHT:
/* 103 */         return "TIME.NIGHT";
/*     */     } 
/* 105 */     throw new GdxRuntimeException("Unknown time");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getWeatherLabel() {
/* 110 */     String s = "";
/* 111 */     switch (this.weatherStrength) {
/*     */       case 0:
/* 113 */         if (this.sky == 0) {
/* 114 */           s = "SKY.CLEAR"; break;
/*     */         } 
/* 116 */         s = "SKY.CLOUDY";
/*     */         break;
/*     */ 
/*     */       
/*     */       case 1:
/* 121 */         switch (this.weatherEffect) {
/*     */           case 0:
/* 123 */             s = "WIND.LIGHT";
/*     */             break;
/*     */           
/*     */           case 1:
/* 127 */             s = "RAIN.LIGHT";
/*     */             break;
/*     */           
/*     */           case 2:
/* 131 */             s = "SNOW.LIGHT";
/*     */             break;
/*     */           
/*     */           case 3:
/* 135 */             s = "FOG.THIN";
/*     */             break;
/*     */         } 
/*     */         
/*     */         break;
/*     */       case 2:
/* 141 */         switch (this.weatherEffect) {
/*     */           case 0:
/* 143 */             s = "WIND.STRONG";
/*     */             break;
/*     */           
/*     */           case 1:
/* 147 */             s = "RAIN.STRONG";
/*     */             break;
/*     */           
/*     */           case 2:
/* 151 */             s = "SNOW.STRONG";
/*     */             break;
/*     */           
/*     */           case 3:
/* 155 */             s = "FOG.THICK";
/*     */             break;
/*     */         } 
/*     */         break;
/*     */     } 
/* 160 */     return s;
/*     */   }
/*     */   
/*     */   public int weatherOffset() {
/* 164 */     if (this.weatherStrength == 0) {
/* 165 */       return this.sky;
/*     */     }
/* 167 */     return 2 * this.weatherEffect + this.weatherStrength + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setup() {
/* 172 */     initTime();
/* 173 */     initGrass();
/* 174 */     initWind();
/* 175 */     adjustGrassFriction();
/* 176 */     adjustGrassBounce();
/*     */   }
/*     */   
/*     */   private void initTime() {
/* 180 */     if (this.time == Time.NIGHT) {
/* 181 */       this.shadowAlpha = 0.4F;
/*     */     } else {
/* 183 */       this.shadowAlpha = 0.75F;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initGrass() {
/* 188 */     this.grass.copy(Pitch.grasses[this.pitchType.ordinal()]);
/*     */   }
/*     */   
/*     */   private void initWind() {
/* 192 */     if (this.weatherEffect == 0) {
/* 193 */       this.wind.init(this.weatherStrength, Assets.random);
/*     */     }
/*     */   }
/*     */   
/*     */   private void adjustGrassFriction() {
/* 198 */     if (this.weatherStrength != 0) {
/* 199 */       switch (this.weatherEffect) {
/*     */         case 1:
/* 201 */           this.grass.friction -= this.weatherStrength;
/*     */           break;
/*     */         
/*     */         case 2:
/* 205 */           this.grass.friction += this.weatherStrength;
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void adjustGrassBounce() {
/* 212 */     if (this.weatherStrength != 0) {
/* 213 */       switch (this.weatherEffect) {
/*     */         case 1:
/* 215 */           this.grass.bounce -= 0.03F * this.weatherStrength;
/*     */           break;
/*     */         
/*     */         case 2:
/* 219 */           this.grass.bounce -= 0.02F * this.weatherStrength;
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static Time randomTime() {
/* 226 */     float dayProbability = 0.7F;
/* 227 */     return (Assets.random.nextFloat() < dayProbability) ? Time.DAY : Time.NIGHT;
/*     */   }
/*     */   
/*     */   public int getZoom() {
/* 231 */     return this.zoom;
/*     */   } }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\SceneSettings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */