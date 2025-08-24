/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ public class Weather
/*    */ {
/*    */   public static final int WIND = 0;
/*    */   public static final int RAIN = 1;
/*    */   public static final int SNOW = 2;
/*    */   public static final int FOG = 3;
/*    */   
/*    */   public static class Strength
/*    */   {
/*    */     public static final int NONE = 0;
/*    */     public static final int LIGHT = 1;
/*    */     public static final int STRONG = 2;
/* 16 */     public static final String[] names = new String[] { "WEATHER.EFFECTS.OFF", "WEATHER.EFFECTS.LIGHT", "WEATHER.EFFECTS.STRONG" };
/*    */   }
/*    */   
/* 19 */   public static int[][] cap = new int[][] { { 2, 0, 1, 0 }, { 2, 2, 0, 2 }, { 2, 2, 0, 2 }, { 2, 1, 0, 2 }, { 2, 0, 0, 2 }, { 2, 0, 0, 0 }, { 2, 0, 0, 2 }, { 2, 0, 2, 2 }, { 2, 0, 2, 2 } };
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Weather.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */