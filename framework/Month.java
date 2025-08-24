/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ public enum Month
/*    */ {
/*  5 */   JANUARY,
/*  6 */   FEBRUARY,
/*  7 */   MARCH,
/*  8 */   APRIL,
/*  9 */   MAY,
/* 10 */   JUNE,
/* 11 */   JULY,
/* 12 */   AUGUST,
/* 13 */   SEPTEMBER,
/* 14 */   OCTOBER,
/* 15 */   NOVEMBER,
/* 16 */   DECEMBER;
/*    */   static {
/* 18 */     labels = new String[] { "MONTHS.JANUARY", "MONTHS.FEBRUARY", "MONTHS.MARCH", "MONTHS.APRIL", "MONTHS.MAY", "MONTHS.JUNE", "MONTHS.JULY", "MONTHS.AUGUST", "MONTHS.SEPTEMBER", "MONTHS.OCTOBER", "MONTHS.NOVEMBER", "MONTHS.DECEMBER" };
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static final String[] labels;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getLabel(Month month) {
/* 34 */     return labels[month.ordinal()];
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\Month.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */