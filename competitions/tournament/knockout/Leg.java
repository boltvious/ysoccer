/*     */ package com.ygames.ysoccer.competitions.tournament.knockout;
/*     */ 
/*     */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Leg
/*     */ {
/*     */   private Knockout knockout;
/*     */   public ArrayList<Match> matches;
/*     */   
/*     */   Leg(Knockout knockout) {
/*  18 */     this.knockout = knockout;
/*  19 */     this.matches = new ArrayList<>();
/*     */   }
/*     */   
/*     */   private int getIndex() {
/*  23 */     return this.knockout.legs.indexOf(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getQualifiedTeam(Match match) {
/*  28 */     if (match.getResult() == null) {
/*  29 */       return -1;
/*     */     }
/*     */     
/*  32 */     if (match.resultAfterPenalties != null) {
/*  33 */       if (match.resultAfterPenalties[0] > match.resultAfterPenalties[1])
/*  34 */         return match.teams[0]; 
/*  35 */       if (match.resultAfterPenalties[0] < match.resultAfterPenalties[1]) {
/*  36 */         return match.teams[1];
/*     */       }
/*  38 */       throw new GdxRuntimeException("Invalid state in cup");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  43 */     if (getIndex() == 0) {
/*  44 */       if (this.knockout.numberOfLegs == 1) {
/*  45 */         if (match.getResult()[0] > match.getResult()[1])
/*  46 */           return match.teams[0]; 
/*  47 */         if (match.getResult()[0] < match.getResult()[1]) {
/*  48 */           return match.teams[1];
/*     */         }
/*  50 */         return -1;
/*     */       } 
/*     */       
/*  53 */       return -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  58 */     if (getIndex() == 1 && this.knockout.numberOfLegs == 2) {
/*     */       
/*  60 */       int[] oldResult = ((Leg)this.knockout.legs.get(getIndex() - 1)).findResult(match.teams);
/*  61 */       int aggregate1 = match.getResult()[0] + oldResult[1];
/*  62 */       int aggregate2 = match.getResult()[1] + oldResult[0];
/*  63 */       if (aggregate1 > aggregate2)
/*  64 */         return match.teams[0]; 
/*  65 */       if (aggregate1 < aggregate2) {
/*  66 */         return match.teams[1];
/*     */       }
/*  68 */       if (this.knockout.tournament.awayGoals == Competition.AwayGoals.AFTER_90_MINUTES || (this.knockout.tournament.awayGoals == Competition.AwayGoals.AFTER_EXTRA_TIME && match.resultAfterExtraTime != null)) {
/*     */         
/*  70 */         if (oldResult[1] > match.getResult()[1])
/*  71 */           return match.teams[0]; 
/*  72 */         if (oldResult[1] < match.getResult()[1]) {
/*  73 */           return match.teams[1];
/*     */         }
/*  75 */         return -1;
/*     */       } 
/*     */       
/*  78 */       return -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     if (match.getResult()[0] > match.getResult()[1])
/*  86 */       return match.teams[0]; 
/*  87 */     if (match.getResult()[0] < match.getResult()[1]) {
/*  88 */       return match.teams[1];
/*     */     }
/*  90 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<Integer> getQualifiedTeams() {
/*  96 */     ArrayList<Integer> qualifiedTeams = new ArrayList<>();
/*  97 */     for (Match match : this.matches) {
/*  98 */       int qualifiedTeam = getQualifiedTeam(match);
/*  99 */       if (qualifiedTeam != -1) {
/* 100 */         qualifiedTeams.add(Integer.valueOf(qualifiedTeam));
/*     */       }
/*     */     } 
/* 103 */     return qualifiedTeams;
/*     */   }
/*     */   
/*     */   boolean hasReplays() {
/* 107 */     return (getQualifiedTeams().size() < this.matches.size());
/*     */   }
/*     */   
/*     */   protected int[] findResult(int[] teams) {
/* 111 */     for (Match match : this.matches) {
/* 112 */       if (match.teams[0] == teams[1] && match.teams[1] == teams[0]) {
/* 113 */         return match.getResult();
/*     */       }
/*     */     } 
/* 116 */     throw new RuntimeException("Cannot find result");
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\tournament\knockout\Leg.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */