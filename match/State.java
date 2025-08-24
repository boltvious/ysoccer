/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ class State
/*    */ {
/*    */   int timer;
/*    */   int id;
/*    */   
/*    */   void doActions() {
/*  9 */     this.timer++;
/*    */   }
/*    */   
/*    */   State checkConditions() {
/* 13 */     return null;
/*    */   }
/*    */   
/*    */   void entryActions() {
/* 17 */     this.timer = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   void exitActions() {}
/*    */   
/*    */   boolean checkId(int id) {
/* 24 */     return (this.id == id);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 29 */     return this.id + ":" + this.timer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\State.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */