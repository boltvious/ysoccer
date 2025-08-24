/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Scorers
/*    */ {
/* 14 */   ArrayList<String>[] rows = (ArrayList<String>[])new ArrayList[2];
/*    */   
/*    */   Scorers() {
/* 17 */     this.rows[0] = new ArrayList<>();
/* 18 */     this.rows[1] = new ArrayList<>();
/*    */   }
/*    */ 
/*    */   
/*    */   void build(List<Goal> goals) {
/* 23 */     this.rows[0].clear();
/* 24 */     this.rows[1].clear();
/*    */     
/* 26 */     for (Goal goal : goals) {
/* 27 */       int tm = goal.player.team.index;
/* 28 */       if (goal.type == Goal.Type.OWN_GOAL) {
/* 29 */         tm = 1 - tm;
/*    */       }
/*    */       
/* 32 */       if (!alreadyScored(goal, tm, goals)) {
/*    */         
/* 34 */         String s = goal.player.shirtName + " " + goal.minute;
/*    */         
/* 36 */         switch (goal.type) {
/*    */           case OWN_GOAL:
/* 38 */             s = s + "(" + Assets.strings.get("GOAL TYPE.OWN GOAL") + ")";
/*    */             break;
/*    */           
/*    */           case PENALTY:
/* 42 */             s = s + "(" + Assets.strings.get("GOAL TYPE.PENALTY") + ")";
/*    */             break;
/*    */         } 
/* 45 */         s = addOtherGoals(goal, tm, s, goals);
/*    */         
/* 47 */         this.rows[tm].add(s);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private boolean alreadyScored(Goal goal, int team, List<Goal> goals) {
/* 53 */     for (Goal g : goals) {
/* 54 */       if (goal == g) {
/* 55 */         return false;
/*    */       }
/* 57 */       int tm = goal.player.team.index;
/* 58 */       if (goal.type == Goal.Type.OWN_GOAL) {
/* 59 */         tm = 1 - tm;
/*    */       }
/* 61 */       if (tm == team && g.player == goal.player) {
/* 62 */         return true;
/*    */       }
/*    */     } 
/* 65 */     return false;
/*    */   }
/*    */   
/*    */   private String addOtherGoals(Goal g, int team, String s, List<Goal> goals) {
/* 69 */     for (Goal goal : goals) {
/* 70 */       if (goal != g) {
/* 71 */         int tm = goal.player.team.index;
/* 72 */         if (goal.type == Goal.Type.OWN_GOAL) {
/* 73 */           tm = 1 - tm;
/*    */         }
/* 75 */         if (tm == team && goal.player == g.player) {
/* 76 */           s = s + "," + goal.minute;
/* 77 */           switch (goal.type) {
/*    */             case OWN_GOAL:
/* 79 */               s = s + "(" + Assets.strings.get("GOAL TYPE.OWN GOAL") + ")";
/*    */ 
/*    */             
/*    */             case PENALTY:
/* 83 */               s = s + "(" + Assets.strings.get("GOAL TYPE.PENALTY") + ")";
/*    */           } 
/*    */         
/*    */         } 
/*    */       } 
/*    */     } 
/* 89 */     return s;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Scorers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */