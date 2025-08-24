/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ public class Pitch
/*    */ {
/*    */   public enum Type {
/*  9 */     FROZEN,
/* 10 */     MUDDY,
/* 11 */     WET,
/* 12 */     SOFT,
/* 13 */     NORMAL,
/* 14 */     DRY,
/* 15 */     HARD,
/* 16 */     SNOWED,
/* 17 */     WHITE,
/* 18 */     RANDOM;
/*    */   }
/*    */   
/* 21 */   public static final String[] names = new String[] { "PITCH.FROZEN", "PITCH.MUDDY", "PITCH.WET", "PITCH.SOFT", "PITCH.NORMAL", "PITCH.DRY", "PITCH.HARD", "PITCH.SNOWED", "PITCH.WHITE", "RANDOM" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 34 */   static final Grass[] grasses = new Grass[] { new Grass(5789772, 3947572, 4.0F, 0.7F), new Grass(6043648, 4467712, 12.0F, 0.55F), new Grass(4746240, 3954688, 6.0F, 0.6F), new Grass(3954688, 2900992, 10.0F, 0.6F), new Grass(4746240, 3954688, 8.0F, 0.65F), new Grass(4746240, 3954688, 6.0F, 0.65F), new Grass(6835200, 4602880, 6.0F, 0.7F), new Grass(5789772, 3947572, 4.0F, 0.7F), new Grass(5789772, 3947572, 10.0F, 0.6F) };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 46 */   static final int[][][] startingPositions = new int[][][] { { { 0, -630 }, { -240, -180 }, { -120, -320 }, { 120, -320 }, { 240, -180 }, { -378, 0 }, { -100, -140 }, { 100, -140 }, { 212, 0 }, { -58, 0 }, { 10, -10 } }, { { 0, -630 }, { -240, -370 }, { -120, -480 }, { 120, -480 }, { 240, -370 }, { -320, -150 }, { -60, -240 }, { 60, -240 }, { 320, -150 }, { -100, -86 }, { 100, -86 } } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 64 */   public static int[][] probabilityByMonth = new int[][] { { 20, 20, 20, 5, 10, 0, 5, 10, 10, 50 }, { 20, 20, 20, 5, 10, 0, 5, 10, 10, 40 }, { 10, 20, 30, 10, 15, 0, 5, 5, 5, 30 }, { 0, 15, 35, 20, 25, 0, 5, 0, 0, 20 }, { 0, 10, 30, 25, 25, 5, 5, 0, 0, 10 }, { 0, 5, 20, 30, 35, 5, 5, 0, 0, 10 }, { 0, 0, 20, 30, 30, 10, 10, 0, 0, 10 }, { 0, 0, 10, 30, 30, 15, 15, 0, 0, 5 }, { 0, 5, 20, 25, 35, 5, 10, 0, 0, 10 }, { 5, 15, 20, 20, 30, 0, 5, 5, 0, 20 }, { 5, 20, 20, 15, 25, 0, 5, 5, 5, 30 }, { 10, 20, 20, 10, 15, 0, 5, 10, 10, 40 } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Type random() {
/* 82 */     int[] pitchProbabilities = probabilityByMonth[EMath.rand(0, 11)];
/*    */     
/* 84 */     float sum = 0.0F;
/* 85 */     float r = Assets.random.nextFloat();
/* 86 */     int i = -1;
/*    */     while (true) {
/* 88 */       i++;
/* 89 */       sum += pitchProbabilities[i] / 100.0F;
/* 90 */       if (sum >= r)
/* 91 */         return Type.values()[i]; 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Pitch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */