/*     */ package com.ygames.ysoccer.framework;
/*     */ 
/*     */ public class EMath
/*     */ {
/*     */   private static final float TO_RADIANS = 0.017453292F;
/*     */   private static final float TO_DEGREES = 57.295776F;
/*     */   
/*     */   public static boolean isIn(float v, float a, float b) {
/*   9 */     return (b > a) ? ((a <= v && v <= b)) : ((b <= v && v <= a));
/*     */   }
/*     */   
/*     */   public static int bound(int value, int valueMin, int valueMax) {
/*  13 */     return Math.min(Math.max(value, valueMin), valueMax);
/*     */   }
/*     */   
/*     */   public static float aTan2(float y, float x) {
/*  17 */     return (float)(Math.atan2(y, x) * 57.2957763671875D);
/*     */   }
/*     */   
/*     */   public static float cos(float a) {
/*  21 */     return (float)Math.cos((a * 0.017453292F));
/*     */   }
/*     */   
/*     */   public static float sin(float a) {
/*  25 */     return (float)Math.sin((a * 0.017453292F));
/*     */   }
/*     */   
/*     */   public static float tan(float a) {
/*  29 */     return (float)Math.tan((a * 0.017453292F));
/*     */   }
/*     */   
/*     */   public static float hypo(float diffX, float diffY) {
/*  33 */     return (float)Math.sqrt((diffX * diffX + diffY * diffY));
/*     */   }
/*     */   
/*     */   public static float dist(float ax, float ay, float bx, float by) {
/*  37 */     return hypo(bx - ax, by - ay);
/*     */   }
/*     */   
/*     */   public static float sqrt(float v) {
/*  41 */     return (float)Math.sqrt(v);
/*     */   }
/*     */   
/*     */   public static float pow(float v, float v1) {
/*  45 */     return (float)Math.pow(v, v1);
/*     */   }
/*     */   
/*     */   public static float angle(float x1, float y1, float x2, float y2) {
/*  49 */     return aTan2(y2 - y1, x2 - x1);
/*     */   }
/*     */   
/*     */   public static float angleDiff(float a1, float a2) {
/*  53 */     return Math.abs((a1 - a2 + 540.0F) % 360.0F - 180.0F);
/*     */   }
/*     */   
/*     */   public static float signedAngleDiff(float a1, float a2) {
/*  57 */     float an1 = (a1 + 360.0F) % 360.0F;
/*  58 */     float an2 = (a2 + 360.0F) % 360.0F;
/*  59 */     int sign = ((an1 - an2 >= 0.0F && an1 - an2 <= 180.0F) || (an1 - an2 <= -180.0F && an1 - an2 >= -360.0F)) ? 1 : -1;
/*  60 */     return sign * angleDiff(an1, an2);
/*     */   }
/*     */   
/*     */   public static int rotate(int value, int low, int high, int direction) {
/*  64 */     return (value - low + high - low + 1 + direction) % (high - low + 1) + low;
/*     */   }
/*     */   
/*     */   public static <T extends Enum<T>> int rotate(Enum<T> value, Enum<T> low, Enum<T> high, int direction) {
/*  68 */     return (value.ordinal() - low.ordinal() + high.ordinal() - low.ordinal() + 1 + direction) % (high.ordinal() - low.ordinal() + 1) + low.ordinal();
/*     */   }
/*     */   
/*     */   public static <T extends Enum<T>> T rotate(Enum<T> value, Class<T> c, int direction) {
/*  72 */     int values = ((Enum[])c.getEnumConstants()).length;
/*  73 */     return (T)((Enum[])c.getEnumConstants())[(value.ordinal() + values + direction) % values];
/*     */   }
/*     */   
/*     */   public static int slide(int value, int low, int high, int step) {
/*  77 */     return Math.min(Math.max(value + step, low), high);
/*     */   }
/*     */   
/*     */   public static int sgn(float value) {
/*  81 */     return (int)Math.signum(value);
/*     */   }
/*     */   
/*     */   public static int floor(double value) {
/*  85 */     return (int)Math.floor(value);
/*     */   }
/*     */   
/*     */   public static int ceil(float value) {
/*  89 */     return (int)Math.ceil(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int rand(int min, int max) {
/*  94 */     return min + Assets.random.nextInt(max + 1 - min);
/*     */   }
/*     */   
/*     */   public static <T> T randomPick(T[] elements) {
/*  98 */     return elements[Assets.random.nextInt(elements.length)];
/*     */   }
/*     */   
/*     */   public static <T extends Enum<T>> T randomPick(Class<T> c) {
/* 102 */     int values = ((Enum[])c.getEnumConstants()).length;
/* 103 */     return (T)((Enum[])c.getEnumConstants())[Assets.random.nextInt(values)];
/*     */   }
/*     */   
/*     */   public static float roundBy(float value, float step) {
/* 107 */     return step * Math.round(value / step);
/*     */   }
/*     */   
/*     */   public static int min(int... values) {
/* 111 */     int min = values[0];
/* 112 */     for (int v : values) {
/* 113 */       if (v < min) min = v; 
/*     */     } 
/* 115 */     return min;
/*     */   }
/*     */   
/*     */   public static float max(float... values) {
/* 119 */     float max = values[0];
/* 120 */     for (float v : values) {
/* 121 */       if (v > max) max = v; 
/*     */     } 
/* 123 */     return max;
/*     */   }
/*     */   
/*     */   public static float clamp(float value, float limit1, float limit2) {
/* 127 */     float diff1 = value - limit1;
/* 128 */     float diff2 = value - limit2;
/* 129 */     return (Math.signum(diff1) != Math.signum(diff2)) ? value : ((Math.abs(diff1) < Math.abs(diff2)) ? limit1 : limit2);
/*     */   }
/*     */   
/*     */   public static <T> boolean isAmong(T value, T[] elements) {
/* 133 */     for (T element : elements) {
/* 134 */       if (value == element) return true; 
/*     */     } 
/* 136 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\EMath.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */