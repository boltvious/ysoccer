/*     */ package com.ygames.ysoccer.competitions.tournament.groups;
/*     */ 
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonValue;
/*     */ import com.ygames.ysoccer.competitions.TableRow;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Group
/*     */   implements Json.Serializable
/*     */ {
/*     */   private int currentRound;
/*     */   private int currentMatch;
/*     */   private Groups groups;
/*  26 */   public ArrayList<Match> calendar = new ArrayList<>();
/*  27 */   public List<TableRow> table = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public void setGroups(Groups groups) {
/*  31 */     this.groups = groups;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/*  36 */     this.currentRound = jsonData.getInt("currentRound", 0);
/*  37 */     this.currentMatch = jsonData.getInt("currentMatch", 0);
/*     */     
/*  39 */     Match[] calendarArray = (Match[])json.readValue("calendar", Match[].class, jsonData);
/*  40 */     Collections.addAll(this.calendar, calendarArray);
/*     */     
/*  42 */     TableRow[] tableArray = (TableRow[])json.readValue("table", TableRow[].class, jsonData);
/*  43 */     if (tableArray != null) {
/*  44 */       Collections.addAll(this.table, tableArray);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/*  50 */     json.writeValue("currentRound", Integer.valueOf(this.currentRound));
/*  51 */     json.writeValue("currentMatch", Integer.valueOf(this.currentMatch));
/*  52 */     json.writeValue("calendar", this.calendar, Match[].class, Match.class);
/*  53 */     json.writeValue("table", this.table, TableRow[].class, TableRow.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start(ArrayList<Integer> teams) {
/*  58 */     if (this.calendar.size() == 0) {
/*  59 */       generateCalendar(teams);
/*     */     }
/*     */     
/*  62 */     populateTable();
/*  63 */     sortTable();
/*     */   }
/*     */   
/*     */   void restart() {
/*  67 */     this.currentRound = 0;
/*  68 */     this.currentMatch = 0;
/*  69 */     resetCalendar();
/*  70 */     resetTable();
/*     */   }
/*     */   
/*     */   void clear() {
/*  74 */     this.currentRound = 0;
/*  75 */     this.currentMatch = 0;
/*  76 */     this.calendar.clear();
/*  77 */     this.table.clear();
/*     */   }
/*     */   
/*     */   public Match getMatch() {
/*  81 */     return this.calendar.get(this.currentMatch);
/*     */   }
/*     */   
/*     */   public boolean isEnded() {
/*  85 */     return (this.currentMatch == this.calendar.size() - 1 && getMatch().getResult() != null);
/*     */   }
/*     */   
/*     */   public void nextMatch() {
/*  89 */     this.currentMatch++;
/*  90 */     if (2 * this.currentMatch == (this.currentRound + 1) * this.groups.groupNumberOfTeams() * (this.groups.groupNumberOfTeams() - 1)) {
/*  91 */       nextRound();
/*     */     }
/*     */   }
/*     */   
/*     */   private void nextRound() {
/*  96 */     this.currentRound++;
/*     */   }
/*     */   
/*     */   private void generateCalendar(ArrayList<Integer> teams) {
/* 100 */     this.calendar.clear();
/* 101 */     while (this.currentRound < this.groups.rounds) {
/*     */ 
/*     */       
/* 104 */       int pos = 0;
/* 105 */       for (int i = 2; i < this.groups.groupNumberOfTeams(); i++) {
/* 106 */         pos += i * (i - 1);
/*     */       }
/* 108 */       pos = pos + 2 * this.currentMatch - this.currentRound * this.groups.groupNumberOfTeams() * (this.groups.groupNumberOfTeams() - 1);
/*     */ 
/*     */       
/* 111 */       Match match = new Match();
/* 112 */       if (this.currentRound % 2 == 0) {
/* 113 */         match.teams[0] = Assets.calendars[pos];
/* 114 */         match.teams[1] = Assets.calendars[pos + 1];
/*     */       } else {
/* 116 */         match.teams[0] = Assets.calendars[pos + 1];
/* 117 */         match.teams[1] = Assets.calendars[pos];
/*     */       } 
/* 119 */       this.calendar.add(match);
/*     */       
/* 121 */       nextMatch();
/*     */     } 
/*     */ 
/*     */     
/* 125 */     Collections.shuffle(teams);
/* 126 */     for (Match match : this.calendar) {
/* 127 */       match.teams[0] = ((Integer)teams.get(match.teams[0])).intValue();
/* 128 */       match.teams[1] = ((Integer)teams.get(match.teams[1])).intValue();
/*     */     } 
/*     */     
/* 131 */     this.currentMatch = 0;
/* 132 */     this.currentRound = 0;
/*     */   }
/*     */   
/*     */   private void resetCalendar() {
/* 136 */     for (Match match : this.calendar) {
/* 137 */       resetMatch(match);
/*     */     }
/*     */   }
/*     */   
/*     */   private void populateTable() {
/* 142 */     for (Match match : this.calendar) {
/* 143 */       for (int t = 0; t <= 1; t++) {
/* 144 */         int team = match.teams[t];
/* 145 */         if (!tableContains(team)) {
/* 146 */           this.table.add(new TableRow(team));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean tableContains(int team) {
/* 153 */     for (TableRow tableRow : this.table) {
/* 154 */       if (tableRow.team == team) {
/* 155 */         return true;
/*     */       }
/*     */     } 
/* 158 */     return false;
/*     */   }
/*     */   
/*     */   private void resetTable() {
/* 162 */     for (TableRow row : this.table) {
/* 163 */       row.reset();
/*     */     }
/* 165 */     sortTable();
/*     */   }
/*     */   
/*     */   private void sortTable() {
/* 169 */     Collections.sort(this.table, this.groups.tableRowComparator);
/*     */   }
/*     */   
/*     */   private void resetMatch(Match match) {
/* 173 */     match.resultAfter90 = null;
/*     */   }
/*     */   
/*     */   void generateResult() {
/* 177 */     Match match = getMatch();
/* 178 */     Team homeTeam = this.groups.tournament.getTeam(0);
/* 179 */     Team awayTeam = this.groups.tournament.getTeam(1);
/*     */     
/* 181 */     int homeGoals = Match.generateGoals(homeTeam, awayTeam, false);
/* 182 */     int awayGoals = Match.generateGoals(awayTeam, homeTeam, false);
/*     */     
/* 184 */     match.setResult(homeGoals, awayGoals, Match.ResultType.AFTER_90_MINUTES);
/* 185 */     addMatchToTable(match);
/*     */     
/* 187 */     this.groups.tournament.generateScorers(homeTeam, homeGoals);
/* 188 */     this.groups.tournament.generateScorers(awayTeam, awayGoals);
/*     */   }
/*     */   
/*     */   void addMatchToTable(Match match) {
/* 192 */     int[] result = match.getResult();
/* 193 */     for (TableRow row : this.table) {
/* 194 */       if (row.team == this.groups.tournament.getTeamIndex(0)) {
/* 195 */         row.update(result[0], result[1], this.groups.pointsForAWin);
/*     */       }
/* 197 */       if (row.team == this.groups.tournament.getTeamIndex(1)) {
/* 198 */         row.update(result[1], result[0], this.groups.pointsForAWin);
/*     */       }
/*     */     } 
/* 201 */     sortTable();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\tournament\groups\Group.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */