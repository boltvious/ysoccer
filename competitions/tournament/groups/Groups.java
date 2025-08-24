/*     */ package com.ygames.ysoccer.competitions.tournament.groups;
/*     */ 
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonValue;
/*     */ import com.ygames.ysoccer.competitions.TableRow;
/*     */ import com.ygames.ysoccer.competitions.tournament.Round;
/*     */ import com.ygames.ysoccer.framework.Assets;
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
/*     */ public class Groups
/*     */   extends Round
/*     */   implements Json.Serializable
/*     */ {
/*     */   public int rounds;
/*     */   public int pointsForAWin;
/*     */   public int currentGroup;
/*     */   public ArrayList<Group> groups;
/*     */   Comparator<TableRow> tableRowComparator;
/*     */   
/*     */   public Groups() {
/*  30 */     super(Round.Type.GROUPS);
/*  31 */     this.groups = new ArrayList<>();
/*  32 */     this.rounds = 1;
/*  33 */     this.pointsForAWin = 3;
/*  34 */     this.tableRowComparator = new TableRowComparator();
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/*  39 */     super.read(json, jsonData);
/*     */     
/*  41 */     this.rounds = jsonData.getInt("rounds");
/*  42 */     this.pointsForAWin = jsonData.getInt("pointsForAWin");
/*  43 */     this.currentGroup = jsonData.getInt("currentGroup", 0);
/*     */     
/*  45 */     Group[] groupsArray = (Group[])json.readValue("groups", Group[].class, jsonData);
/*  46 */     for (Group group : groupsArray) {
/*  47 */       group.setGroups(this);
/*  48 */       this.groups.add(group);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/*  54 */     super.write(json);
/*     */     
/*  56 */     json.writeValue("rounds", Integer.valueOf(this.rounds));
/*  57 */     json.writeValue("pointsForAWin", Integer.valueOf(this.pointsForAWin));
/*  58 */     json.writeValue("currentGroup", Integer.valueOf(this.currentGroup));
/*  59 */     json.writeValue("groups", this.groups, Group[].class, Group.class);
/*     */   }
/*     */   
/*     */   public void createGroups(int n) {
/*  63 */     for (int i = 0; i < n; i++) {
/*  64 */       Group group = new Group();
/*  65 */       group.setGroups(this);
/*  66 */       this.groups.add(group);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int groupNumberOfTeams() {
/*  71 */     return this.numberOfTeams / this.groups.size();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void start(ArrayList<Integer> qualifiedTeams) {
/*  76 */     if (isPreset()) {
/*  77 */       for (Group group : this.groups) {
/*  78 */         group.start(null);
/*     */       }
/*     */     } else {
/*  81 */       if (!this.seeded) {
/*  82 */         Collections.shuffle(qualifiedTeams);
/*     */       }
/*     */ 
/*     */       
/*  86 */       List<Integer> groupsIndexes = new ArrayList<>();
/*  87 */       for (int t = 0; t < this.groups.size(); t++) {
/*  88 */         groupsIndexes.add(Integer.valueOf(t));
/*     */       }
/*  90 */       List<Integer> teamsMapping = new ArrayList<>();
/*  91 */       for (int i = 0; i < groupNumberOfTeams(); i++) {
/*  92 */         Collections.shuffle(groupsIndexes);
/*  93 */         for (int j = 0; j < this.groups.size(); j++) {
/*  94 */           teamsMapping.add(Integer.valueOf(i * this.groups.size() + ((Integer)groupsIndexes.get(j)).intValue()));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  99 */       for (int g = 0; g < this.groups.size(); g++) {
/* 100 */         ArrayList<Integer> groupTeams = new ArrayList<>();
/* 101 */         for (int j = 0; j < groupNumberOfTeams(); j++) {
/* 102 */           groupTeams.add(qualifiedTeams.get(((Integer)teamsMapping.get(j * this.groups.size() + g)).intValue()));
/*     */         }
/* 104 */         ((Group)this.groups.get(g)).start(groupTeams);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void restart() {
/* 111 */     this.currentGroup = 0;
/* 112 */     for (Group group : this.groups) {
/* 113 */       group.restart();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 119 */     this.currentGroup = 0;
/* 120 */     for (Group group : this.groups) {
/* 121 */       group.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   private Group getGroup() {
/* 126 */     return this.groups.get(this.currentGroup);
/*     */   }
/*     */ 
/*     */   
/*     */   public Match getMatch() {
/* 131 */     return getGroup().getMatch();
/*     */   }
/*     */   
/*     */   public int numberOfTopTeams() {
/* 135 */     return this.tournament.numberOfNextRoundTeams() / this.groups.size();
/*     */   }
/*     */   
/*     */   public int numberOfRunnersUp() {
/* 139 */     return this.tournament.numberOfNextRoundTeams() % this.groups.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextMatch() {
/* 144 */     if (isEnded()) {
/* 145 */       ArrayList<Integer> qualifiedTeams = new ArrayList<>();
/*     */ 
/*     */       
/* 148 */       int topTeams = numberOfTopTeams();
/* 149 */       for (int team = 0; team < topTeams; team++) {
/* 150 */         for (Group group : this.groups) {
/* 151 */           qualifiedTeams.add(Integer.valueOf(((TableRow)group.table.get(team)).team));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 156 */       int runnersUp = numberOfRunnersUp();
/* 157 */       ArrayList<TableRow> runnersUpTable = new ArrayList<>();
/* 158 */       for (Group group : this.groups) {
/* 159 */         runnersUpTable.add(group.table.get(topTeams));
/*     */       }
/* 161 */       Collections.sort(runnersUpTable, this.tableRowComparator);
/* 162 */       for (int i = 0; i < runnersUp; i++) {
/* 163 */         qualifiedTeams.add(Integer.valueOf(((TableRow)runnersUpTable.get(i)).team));
/*     */       }
/*     */       
/* 166 */       this.tournament.nextRound(qualifiedTeams);
/*     */     } else {
/* 168 */       this.currentGroup++;
/*     */       
/* 170 */       if (this.currentGroup == this.groups.size()) {
/* 171 */         this.currentGroup = 0;
/* 172 */         for (Group group : this.groups) {
/* 173 */           group.nextMatch();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected String nextMatchLabel() {
/* 181 */     if (isEnded()) {
/* 182 */       return "NEXT ROUND";
/*     */     }
/* 184 */     return "NEXT MATCH";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean nextMatchOnHold() {
/* 190 */     return !isEnded();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnded() {
/* 195 */     return (this.currentGroup == this.groups.size() - 1 && getGroup().isEnded());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPreset() {
/* 200 */     return (((Group)this.groups.get(0)).calendar.size() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateResult() {
/* 205 */     getGroup().generateResult();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean playExtraTime() {
/* 210 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean playPenalties() {
/* 215 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getMenuTitle() {
/* 220 */     return this.name;
/*     */   }
/*     */   
/*     */   private class TableRowComparator
/*     */     implements Comparator<TableRow> {
/*     */     private TableRowComparator() {}
/*     */     
/*     */     public int compare(TableRow o1, TableRow o2) {
/* 228 */       if (o1.points != o2.points) {
/* 229 */         return o2.points - o1.points;
/*     */       }
/*     */ 
/*     */       
/* 233 */       int diff1 = o1.goalsFor - o1.goalsAgainst;
/* 234 */       int diff2 = o2.goalsFor - o2.goalsAgainst;
/* 235 */       if (diff1 != diff2) {
/* 236 */         return diff2 - diff1;
/*     */       }
/*     */ 
/*     */       
/* 240 */       if (o1.goalsFor != o2.goalsFor) {
/* 241 */         return o2.goalsFor - o1.goalsFor;
/*     */       }
/*     */ 
/*     */       
/* 245 */       return ((Team)Groups.this.tournament.teams.get(o1.team)).name.compareTo(((Team)Groups.this.tournament.teams.get(o2.team)).name);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void matchCompleted() {
/* 251 */     getGroup().addMatchToTable(getMatch());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void matchInterrupted() {
/* 256 */     Match match = getMatch();
/* 257 */     if ((match.team[0]).controlMode == Team.ControlMode.COMPUTER && (match.team[1]).controlMode != Team.ControlMode.COMPUTER) {
/* 258 */       int goals = 4 + Assets.random.nextInt(2);
/* 259 */       if (match.resultAfter90 != null) {
/* 260 */         goals += match.resultAfter90[1];
/* 261 */         match.resultAfter90[0] = match.resultAfter90[0] + goals;
/*     */       } else {
/* 263 */         match.setResult(goals, 0, Match.ResultType.AFTER_90_MINUTES);
/*     */       } 
/* 265 */       this.tournament.generateScorers(match.team[0], goals);
/* 266 */       matchCompleted();
/* 267 */     } else if ((match.team[0]).controlMode != Team.ControlMode.COMPUTER && (match.team[1]).controlMode == Team.ControlMode.COMPUTER) {
/* 268 */       int goals = 4 + Assets.random.nextInt(2);
/* 269 */       if (match.resultAfter90 != null) {
/* 270 */         goals += match.resultAfter90[1];
/* 271 */         match.resultAfter90[0] = match.resultAfter90[0] + goals;
/*     */       } else {
/* 273 */         match.setResult(0, goals, Match.ResultType.AFTER_90_MINUTES);
/*     */       } 
/* 275 */       this.tournament.generateScorers(match.team[1], goals);
/* 276 */       matchCompleted();
/*     */     } else {
/* 278 */       match.resultAfter90 = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\tournament\groups\Groups.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */