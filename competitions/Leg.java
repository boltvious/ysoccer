/*     */ package com.ygames.ysoccer.competitions;
/*     */ 
/*     */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Leg
/*     */ {
/*     */   private Round round;
/*     */   public ArrayList<Match> matches;
/*     */   
/*     */   Leg(Round round) {
/*  17 */     this.round = round;
/*  18 */     this.matches = new ArrayList<>();
/*     */   }
/*     */   
/*     */   private int getIndex() {
/*  22 */     return this.round.legs.indexOf(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getQualifiedTeam(Match match) {
/*  27 */     if (match.getResult() == null) {
/*  28 */       return -1;
/*     */     }
/*     */     
/*  31 */     if (match.resultAfterPenalties != null) {
/*  32 */       if (match.resultAfterPenalties[0] > match.resultAfterPenalties[1])
/*  33 */         return match.teams[0]; 
/*  34 */       if (match.resultAfterPenalties[0] < match.resultAfterPenalties[1]) {
/*  35 */         return match.teams[1];
/*     */       }
/*  37 */       throw new GdxRuntimeException("Invalid state in cup");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  42 */     if (getIndex() == 0) {
/*  43 */       if (this.round.numberOfLegs == 1) {
/*  44 */         if (match.getResult()[0] > match.getResult()[1])
/*  45 */           return match.teams[0]; 
/*  46 */         if (match.getResult()[0] < match.getResult()[1]) {
/*  47 */           return match.teams[1];
/*     */         }
/*  49 */         return -1;
/*     */       } 
/*     */       
/*  52 */       return -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  57 */     if (getIndex() == 1 && this.round.numberOfLegs == 2) {
/*     */       
/*  59 */       int[] oldResult = ((Leg)this.round.legs.get(getIndex() - 1)).findResult(match.teams);
/*  60 */       int aggregate1 = match.getResult()[0] + oldResult[1];
/*  61 */       int aggregate2 = match.getResult()[1] + oldResult[0];
/*  62 */       if (aggregate1 > aggregate2)
/*  63 */         return match.teams[0]; 
/*  64 */       if (aggregate1 < aggregate2) {
/*  65 */         return match.teams[1];
/*     */       }
/*  67 */       if (this.round.cup.awayGoals == Competition.AwayGoals.AFTER_90_MINUTES || (this.round.cup.awayGoals == Competition.AwayGoals.AFTER_EXTRA_TIME && match.resultAfterExtraTime != null)) {
/*     */         
/*  69 */         if (oldResult[1] > match.getResult()[1])
/*  70 */           return match.teams[0]; 
/*  71 */         if (oldResult[1] < match.getResult()[1]) {
/*  72 */           return match.teams[1];
/*     */         }
/*  74 */         return -1;
/*     */       } 
/*     */       
/*  77 */       return -1;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     if (match.getResult()[0] > match.getResult()[1])
/*  85 */       return match.teams[0]; 
/*  86 */     if (match.getResult()[0] < match.getResult()[1]) {
/*  87 */       return match.teams[1];
/*     */     }
/*  89 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   ArrayList<Integer> getQualifiedTeams() {
/*  95 */     ArrayList<Integer> qualifiedTeams = new ArrayList<>();
/*  96 */     for (Match match : this.matches) {
/*  97 */       int qualifiedTeam = getQualifiedTeam(match);
/*  98 */       if (qualifiedTeam != -1) {
/*  99 */         qualifiedTeams.add(Integer.valueOf(qualifiedTeam));
/*     */       }
/*     */     } 
/* 102 */     return qualifiedTeams;
/*     */   }
/*     */   
/*     */   boolean hasReplays() {
/* 106 */     return (getQualifiedTeams().size() < this.matches.size());
/*     */   }
/*     */   
/*     */   protected int[] findResult(int[] teams) {
/* 110 */     for (Match match : this.matches) {
/* 111 */       if (match.teams[0] == teams[1] && match.teams[1] == teams[0]) {
/* 112 */         return match.getResult();
/*     */       }
/*     */     } 
/* 115 */     throw new RuntimeException("Cannot find result");
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\Leg.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */