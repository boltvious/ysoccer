/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.ygames.ysoccer.match.Team;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class TeamList
/*    */   extends ArrayList<Team>
/*    */ {
/*    */   public void addTeam(Team team) {
/* 10 */     if (indexOf(null) == -1) {
/* 11 */       add(team);
/*    */     } else {
/* 13 */       set(indexOf(null), team);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void removeTeam(Team team) {
/* 18 */     set(indexOf(team), null);
/*    */   }
/*    */   
/*    */   public int numberOfTeams() {
/* 22 */     int count = 0;
/* 23 */     for (Team team : this) {
/* 24 */       if (team != null) count++; 
/*    */     } 
/* 26 */     return count;
/*    */   }
/*    */   
/*    */   public void removeNullValues() {
/* 30 */     while (indexOf(null) != -1) {
/* 31 */       if (indexOf(null) == size() - 1) {
/* 32 */         remove(indexOf(null)); continue;
/*    */       } 
/* 34 */       set(indexOf(null), get(size() - 1));
/* 35 */       remove(size() - 1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\TeamList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */