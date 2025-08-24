/*    */ package com.ygames.ysoccer.match;class Goal { Player player;
/*    */   int minute;
/*    */   Type type;
/*    */   
/*  5 */   enum Type { NORMAL, OWN_GOAL, PENALTY; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Goal(Player player, int minute, Type type) {
/* 12 */     this.player = player;
/* 13 */     this.minute = minute;
/* 14 */     this.type = type;
/*    */   } }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Goal.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */