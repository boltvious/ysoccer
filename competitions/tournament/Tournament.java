/*     */ package com.ygames.ysoccer.competitions.tournament;
/*     */ 
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonValue;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.tournament.knockout.Knockout;
/*     */ import com.ygames.ysoccer.framework.Month;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class Tournament
/*     */   extends Competition implements Json.Serializable {
/*     */   public ArrayList<Round> rounds;
/*     */   public Competition.AwayGoals awayGoals;
/*     */   public Competition.ComparatorByPlayersValue teamComparatorByPlayersValue;
/*     */   
/*     */   public Tournament() {
/*  21 */     super(Competition.Type.TOURNAMENT);
/*  22 */     this.rounds = new ArrayList<>();
/*  23 */     this.awayGoals = Competition.AwayGoals.OFF;
/*  24 */     this.teamComparatorByPlayersValue = new Competition.ComparatorByPlayersValue(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/*  29 */     super.read(json, jsonData);
/*     */     
/*  31 */     Round[] roundsArray = (Round[])json.readValue("rounds", Round[].class, jsonData);
/*  32 */     if (roundsArray != null) {
/*  33 */       for (Round round : roundsArray) {
/*  34 */         round.setTournament(this);
/*  35 */         this.rounds.add(round);
/*     */       } 
/*     */     }
/*  38 */     if (hasTwoLegsRound()) {
/*  39 */       this.awayGoals = (Competition.AwayGoals)json.readValue("awayGoals", Competition.AwayGoals.class, Competition.AwayGoals.AFTER_90_MINUTES, jsonData);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/*  45 */     super.write(json);
/*     */     
/*  47 */     json.writeValue("rounds", this.rounds, Round[].class, Round.class);
/*  48 */     if (hasTwoLegsRound()) {
/*  49 */       json.writeValue("awayGoals", this.awayGoals);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void start(ArrayList<Team> teams) {
/*  55 */     super.start(teams);
/*     */     
/*  57 */     ArrayList<Integer> qualifiedTeams = new ArrayList<>();
/*  58 */     for (int i = 0; i < this.numberOfTeams; i++) {
/*  59 */       qualifiedTeams.add(Integer.valueOf(i));
/*     */     }
/*     */     
/*  62 */     if (!getRound().isPreset())
/*     */     {
/*  64 */       Collections.sort(qualifiedTeams, (Comparator<? super Integer>)this.teamComparatorByPlayersValue);
/*     */     }
/*     */     
/*  67 */     getRound().start(qualifiedTeams);
/*  68 */     updateMonth();
/*     */   }
/*     */ 
/*     */   
/*     */   public void restart() {
/*  73 */     super.restart();
/*  74 */     this.currentRound = 0;
/*  75 */     this.currentMatch = 0;
/*     */     
/*  77 */     ((Round)this.rounds.get(0)).restart();
/*  78 */     for (int i = 1; i < this.rounds.size(); i++) {
/*  79 */       ((Round)this.rounds.get(i)).clear();
/*     */     }
/*  81 */     updateMonth();
/*     */   }
/*     */   
/*     */   public Round getRound() {
/*  85 */     return this.rounds.get(this.currentRound);
/*     */   }
/*     */   
/*     */   public void addRound(Round round) {
/*  89 */     round.setTournament(this);
/*  90 */     this.rounds.add(round);
/*  91 */     this.numberOfTeams = ((Round)this.rounds.get(0)).numberOfTeams;
/*  92 */     updateRoundNames();
/*     */   }
/*     */   
/*     */   public void nextMatch() {
/*  96 */     getRound().nextMatch();
/*     */   }
/*     */   
/*     */   public String nextMatchLabel() {
/* 100 */     return getRound().nextMatchLabel();
/*     */   }
/*     */   
/*     */   public void nextRound(ArrayList<Integer> qualifiedTeams) {
/* 104 */     this.currentRound++;
/* 105 */     this.currentMatch = 0;
/* 106 */     updateMonth();
/*     */     
/* 108 */     getRound().start(qualifiedTeams);
/*     */   }
/*     */   
/*     */   private void updateMonth() {
/* 112 */     if (this.weather == Competition.Weather.BY_SEASON) {
/* 113 */       int seasonLength = (this.seasonEnd.ordinal() - this.seasonStart.ordinal() + 12) % 12;
/* 114 */       this.currentMonth = Month.values()[(this.seasonStart.ordinal() + seasonLength * this.currentRound / this.rounds.size()) % 12];
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateRoundNames() {
/* 119 */     for (int i = 0; i < this.rounds.size(); i++) {
/* 120 */       switch (((Round)this.rounds.get(i)).type) {
/*     */         case KNOCKOUT:
/* 122 */           ((Round)this.rounds.get(i)).name = getKnockoutLabel(i);
/*     */           break;
/*     */         
/*     */         case GROUPS:
/* 126 */           ((Round)this.rounds.get(i)).name = getGroupsLabel(i);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMenuTitle() {
/* 134 */     return this.name + " " + getRound().getMenuTitle();
/*     */   }
/*     */   
/*     */   private String getKnockoutLabel(int round) {
/* 138 */     if (round == this.rounds.size() - 1)
/* 139 */       return "FINAL"; 
/* 140 */     if (round == this.rounds.size() - 2)
/* 141 */       return "SEMI-FINAL"; 
/* 142 */     if (round == this.rounds.size() - 3)
/* 143 */       return "QUARTER-FINAL"; 
/* 144 */     if (round == 0)
/* 145 */       return "FIRST ROUND"; 
/* 146 */     if (round == 1) {
/* 147 */       return "SECOND ROUND";
/*     */     }
/* 149 */     return "THIRD ROUND";
/*     */   }
/*     */ 
/*     */   
/*     */   private String getGroupsLabel(int round) {
/* 154 */     if (round == this.rounds.size() - 1)
/* 155 */       return "FINAL ROUND"; 
/* 156 */     if (round == 0)
/* 157 */       return "FIRST ROUND"; 
/* 158 */     if (round == 1)
/* 159 */       return "SECOND ROUND"; 
/* 160 */     if (round == 2)
/* 161 */       return "THIRD ROUND"; 
/* 162 */     if (round == 3) {
/* 163 */       return "FOURTH ROUND";
/*     */     }
/* 165 */     return "FIFTH ROUND";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Match getMatch() {
/* 171 */     return getRound().getMatch();
/*     */   }
/*     */   
/*     */   public void generateResult() {
/* 175 */     getRound().generateResult();
/*     */   }
/*     */   
/*     */   public boolean playExtraTime() {
/* 179 */     return getRound().playExtraTime();
/*     */   }
/*     */   
/*     */   public boolean playPenalties() {
/* 183 */     return getRound().playPenalties();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnded() {
/* 188 */     return (this.currentRound == this.rounds.size() - 1 && getRound().isEnded());
/*     */   }
/*     */   
/*     */   public boolean nextMatchOnHold() {
/* 192 */     return getRound().nextMatchOnHold();
/*     */   }
/*     */   
/*     */   public boolean hasTwoLegsRound() {
/* 196 */     for (Round round : this.rounds) {
/* 197 */       if (round.type == Round.Type.KNOCKOUT && 
/* 198 */         ((Knockout)round).numberOfLegs == 2) {
/* 199 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 203 */     return false;
/*     */   }
/*     */   
/*     */   public int numberOfNextRoundTeams() {
/* 207 */     if (this.currentRound == this.rounds.size() - 1) {
/* 208 */       return 1;
/*     */     }
/* 210 */     return ((Round)this.rounds.get(this.currentRound + 1)).numberOfTeams;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void matchCompleted() {
/* 216 */     getRound().matchCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public void matchInterrupted() {
/* 221 */     getRound().matchInterrupted();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\tournament\Tournament.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */