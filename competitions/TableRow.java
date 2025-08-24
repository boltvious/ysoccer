/*    */ package com.ygames.ysoccer.competitions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TableRow
/*    */ {
/*    */   public int team;
/*    */   public int won;
/*    */   public int drawn;
/*    */   public int lost;
/*    */   public int goalsFor;
/*    */   public int goalsAgainst;
/*    */   public int points;
/*    */   
/*    */   TableRow() {}
/*    */   
/*    */   public TableRow(int team) {
/* 20 */     this.team = team;
/*    */   }
/*    */   
/*    */   public void update(int goalsFor, int goalsAgainst, int pointsForAWin) {
/* 24 */     this.goalsFor += goalsFor;
/* 25 */     this.goalsAgainst += goalsAgainst;
/* 26 */     if (goalsFor > goalsAgainst) {
/* 27 */       this.won++;
/* 28 */       this.points += pointsForAWin;
/* 29 */     } else if (goalsFor == goalsAgainst) {
/* 30 */       this.drawn++;
/* 31 */       this.points++;
/*    */     } else {
/* 33 */       this.lost++;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void reset() {
/* 38 */     this.won = 0;
/* 39 */     this.drawn = 0;
/* 40 */     this.lost = 0;
/*    */     
/* 42 */     this.goalsFor = 0;
/* 43 */     this.goalsAgainst = 0;
/* 44 */     this.points = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\TableRow.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */