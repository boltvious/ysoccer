/*     */ package com.ygames.ysoccer.competitions;
/*     */ 
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonValue;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Month;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class League
/*     */   extends Competition
/*     */   implements Json.Serializable
/*     */ {
/*     */   public int rounds;
/*     */   public int pointsForAWin;
/*     */   public ArrayList<Match> calendar;
/*     */   public List<TableRow> table;
/*     */   private Comparator<TableRow> tableRowComparator;
/*     */   
/*     */   public League() {
/*  29 */     super(Competition.Type.LEAGUE);
/*  30 */     this.numberOfTeams = 2;
/*  31 */     this.rounds = 1;
/*  32 */     this.pointsForAWin = 3;
/*  33 */     this.calendar = new ArrayList<>();
/*  34 */     this.table = new ArrayList<>();
/*  35 */     this.tableRowComparator = new TableRowComparator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/*  40 */     super.read(json, jsonData);
/*  41 */     this.rounds = jsonData.getInt("rounds");
/*  42 */     this.pointsForAWin = jsonData.getInt("pointsForAWin");
/*     */     
/*  44 */     Match[] calendarArray = (Match[])json.readValue("calendar", Match[].class, jsonData);
/*  45 */     if (calendarArray != null) {
/*  46 */       Collections.addAll(this.calendar, calendarArray);
/*     */     }
/*     */     
/*  49 */     TableRow[] tableArray = (TableRow[])json.readValue("table", TableRow[].class, jsonData);
/*  50 */     if (tableArray != null) {
/*  51 */       Collections.addAll(this.table, tableArray);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/*  57 */     super.write(json);
/*     */     
/*  59 */     json.writeValue("rounds", Integer.valueOf(this.rounds));
/*  60 */     json.writeValue("pointsForAWin", Integer.valueOf(this.pointsForAWin));
/*  61 */     json.writeValue("calendar", this.calendar, Match[].class, Match.class);
/*  62 */     json.writeValue("table", this.table, TableRow[].class, TableRow.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start(ArrayList<Team> teams) {
/*  67 */     super.start(teams);
/*     */ 
/*     */     
/*  70 */     if (this.calendar.size() == 0) {
/*  71 */       generateCalendar();
/*     */     }
/*     */     
/*  74 */     updateMonth();
/*  75 */     populateTable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void restart() {
/*  80 */     super.restart();
/*  81 */     resetCalendar();
/*  82 */     resetTable();
/*     */   }
/*     */ 
/*     */   
/*     */   public Match getMatch() {
/*  87 */     return this.calendar.get(this.currentMatch);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnded() {
/*  92 */     return (this.currentRound == this.rounds);
/*     */   }
/*     */   
/*     */   public void nextMatch() {
/*  96 */     this.currentMatch++;
/*  97 */     if (2 * this.currentMatch == (this.currentRound + 1) * this.numberOfTeams * (this.numberOfTeams - 1)) {
/*  98 */       nextRound();
/*     */     }
/*     */   }
/*     */   
/*     */   private void nextRound() {
/* 103 */     this.currentRound++;
/* 104 */     updateMonth();
/*     */   }
/*     */   
/*     */   private void updateMonth() {
/* 108 */     if (this.weather == Competition.Weather.BY_SEASON) {
/* 109 */       int seasonLength = (this.seasonEnd.ordinal() - this.seasonStart.ordinal() + 12) % 12;
/* 110 */       this.currentMonth = Month.values()[(this.seasonStart.ordinal() + seasonLength * this.currentRound / this.rounds) % 12];
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateCalendar() {
/* 115 */     this.calendar.clear();
/* 116 */     while (this.currentRound < this.rounds) {
/*     */ 
/*     */       
/* 119 */       int pos = 0;
/* 120 */       for (int j = 2; j < this.numberOfTeams; j++) {
/* 121 */         pos += j * (j - 1);
/*     */       }
/* 123 */       pos = pos + 2 * this.currentMatch - this.currentRound * this.numberOfTeams * (this.numberOfTeams - 1);
/*     */ 
/*     */       
/* 126 */       Match match = new Match();
/* 127 */       if (this.currentRound % 2 == 0) {
/* 128 */         match.teams[0] = Assets.calendars[pos];
/* 129 */         match.teams[1] = Assets.calendars[pos + 1];
/*     */       } else {
/* 131 */         match.teams[0] = Assets.calendars[pos + 1];
/* 132 */         match.teams[1] = Assets.calendars[pos];
/*     */       } 
/* 134 */       this.calendar.add(match);
/*     */       
/* 136 */       nextMatch();
/*     */     } 
/*     */ 
/*     */     
/* 140 */     List<Integer> randomIndexes = new ArrayList<>();
/* 141 */     for (int i = 0; i < this.numberOfTeams; ) { randomIndexes.add(Integer.valueOf(i)); i++; }
/* 142 */      Collections.shuffle(randomIndexes);
/* 143 */     for (Match match : this.calendar) {
/* 144 */       match.teams[0] = ((Integer)randomIndexes.get(match.teams[0])).intValue();
/* 145 */       match.teams[1] = ((Integer)randomIndexes.get(match.teams[1])).intValue();
/*     */     } 
/*     */     
/* 148 */     this.currentMatch = 0;
/* 149 */     this.currentRound = 0;
/*     */   }
/*     */   
/*     */   private void resetCalendar() {
/* 153 */     for (Match match : this.calendar) {
/* 154 */       resetMatch(match);
/*     */     }
/*     */     
/* 157 */     this.currentRound = 0;
/* 158 */     this.currentMatch = 0;
/* 159 */     updateMonth();
/*     */   }
/*     */   
/*     */   private void populateTable() {
/* 163 */     for (int i = 0; i < this.numberOfTeams; i++) {
/* 164 */       this.table.add(new TableRow(i));
/*     */     }
/* 166 */     sortTable();
/*     */   }
/*     */   
/*     */   private void resetTable() {
/* 170 */     for (TableRow row : this.table) {
/* 171 */       row.reset();
/*     */     }
/* 173 */     sortTable();
/*     */   }
/*     */   
/*     */   private void sortTable() {
/* 177 */     Collections.sort(this.table, this.tableRowComparator);
/*     */   }
/*     */   
/*     */   private void resetMatch(Match match) {
/* 181 */     match.resultAfter90 = null;
/*     */   }
/*     */   
/*     */   public void generateResult() {
/* 185 */     Match match = getMatch();
/* 186 */     Team homeTeam = getTeam(0);
/* 187 */     Team awayTeam = getTeam(1);
/*     */     
/* 189 */     int homeGoals = Match.generateGoals(homeTeam, awayTeam, false);
/* 190 */     int awayGoals = Match.generateGoals(awayTeam, homeTeam, false);
/*     */     
/* 192 */     match.setResult(homeGoals, awayGoals, Match.ResultType.AFTER_90_MINUTES);
/* 193 */     addMatchToTable(match);
/*     */     
/* 195 */     generateScorers(homeTeam, homeGoals);
/* 196 */     generateScorers(awayTeam, awayGoals);
/*     */   }
/*     */   
/*     */   private void addMatchToTable(Match match) {
/* 200 */     int[] result = match.getResult();
/* 201 */     for (TableRow row : this.table) {
/* 202 */       if (row.team == getTeamIndex(0)) {
/* 203 */         row.update(result[0], result[1], this.pointsForAWin);
/*     */       }
/* 205 */       if (row.team == getTeamIndex(1)) {
/* 206 */         row.update(result[1], result[0], this.pointsForAWin);
/*     */       }
/*     */     } 
/* 209 */     sortTable();
/*     */   }
/*     */   
/*     */   private class TableRowComparator
/*     */     implements Comparator<TableRow> {
/*     */     private TableRowComparator() {}
/*     */     
/*     */     public int compare(TableRow o1, TableRow o2) {
/* 217 */       if (o1.points != o2.points) {
/* 218 */         return o2.points - o1.points;
/*     */       }
/*     */ 
/*     */       
/* 222 */       int diff1 = o1.goalsFor - o1.goalsAgainst;
/* 223 */       int diff2 = o2.goalsFor - o2.goalsAgainst;
/* 224 */       if (diff1 != diff2) {
/* 225 */         return diff2 - diff1;
/*     */       }
/*     */ 
/*     */       
/* 229 */       if (o1.goalsFor != o2.goalsFor) {
/* 230 */         return o2.goalsFor - o1.goalsFor;
/*     */       }
/*     */ 
/*     */       
/* 234 */       return ((Team)League.this.teams.get(o1.team)).name.compareTo(((Team)League.this.teams.get(o2.team)).name);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void matchInterrupted() {
/* 240 */     Match match = getMatch();
/* 241 */     if ((match.team[0]).controlMode == Team.ControlMode.COMPUTER && (match.team[1]).controlMode != Team.ControlMode.COMPUTER) {
/* 242 */       int goals = 4 + Assets.random.nextInt(2);
/* 243 */       if (match.resultAfter90 != null) {
/* 244 */         goals += match.resultAfter90[1];
/* 245 */         match.resultAfter90[0] = match.resultAfter90[0] + goals;
/*     */       } else {
/* 247 */         match.setResult(goals, 0, Match.ResultType.AFTER_90_MINUTES);
/*     */       } 
/* 249 */       generateScorers(match.team[0], goals);
/* 250 */       matchCompleted();
/* 251 */     } else if ((match.team[0]).controlMode != Team.ControlMode.COMPUTER && (match.team[1]).controlMode == Team.ControlMode.COMPUTER) {
/* 252 */       int goals = 4 + Assets.random.nextInt(2);
/* 253 */       if (match.resultAfter90 != null) {
/* 254 */         goals += match.resultAfter90[1];
/* 255 */         match.resultAfter90[0] = match.resultAfter90[0] + goals;
/*     */       } else {
/* 257 */         match.setResult(0, goals, Match.ResultType.AFTER_90_MINUTES);
/*     */       } 
/* 259 */       generateScorers(match.team[1], goals);
/* 260 */       matchCompleted();
/*     */     } else {
/* 262 */       match.resultAfter90 = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void matchCompleted() {
/* 268 */     addMatchToTable(getMatch());
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\League.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */