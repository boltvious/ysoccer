/*     */ package com.ygames.ysoccer.competitions;
/*     */ 
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonValue;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ 
/*     */ public abstract class Competition {
/*     */   public String name;
/*     */   public String filename;
/*     */   public final Type type;
/*     */   public Category category;
/*     */   public int numberOfTeams;
/*     */   public Files files;
/*     */   public SceneSettings.Time time;
/*     */   public Weather weather;
/*     */   public Month seasonStart;
/*     */   public Month seasonEnd;
/*     */   public Month currentMonth;
/*     */   public Pitch.Type pitchType;
/*     */   public int substitutions;
/*     */   public int benchSize;
/*     */   public boolean userPrefersResult;
/*     */   public int currentRound;
/*     */   public int currentMatch;
/*     */   public List<Team> teams;
/*     */   public ArrayList<Scorer> scorers;
/*     */   private Comparator<Scorer> scorerComparator;
/*     */   
/*  30 */   public enum Type { FRIENDLY, LEAGUE, CUP, TOURNAMENT, TEST_MATCH; }
/*     */   
/*  32 */   public enum Category { DIY_COMPETITION, PRESET_COMPETITION; }
/*     */   
/*  34 */   public enum Weather { BY_SEASON, BY_PITCH_TYPE; }
/*     */   
/*  36 */   public enum AwayGoals { OFF, AFTER_90_MINUTES, AFTER_EXTRA_TIME; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Competition(Type type) {
/*  62 */     this.type = type;
/*  63 */     this.filename = "";
/*  64 */     this.teams = new ArrayList<>();
/*  65 */     this.weather = Weather.BY_SEASON;
/*  66 */     this.seasonStart = Month.AUGUST;
/*  67 */     this.seasonEnd = Month.MAY;
/*  68 */     this.pitchType = Pitch.Type.RANDOM;
/*  69 */     this.substitutions = 3;
/*  70 */     this.benchSize = 5;
/*  71 */     this.time = SceneSettings.Time.DAY;
/*  72 */     this.scorers = new ArrayList<>();
/*  73 */     this.scorerComparator = new ScorerComparator();
/*     */   }
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/*  77 */     this.name = jsonData.getString("name");
/*  78 */     this.filename = jsonData.getString("filename", "");
/*  79 */     this.category = (Category)json.readValue("category", Category.class, jsonData);
/*  80 */     this.numberOfTeams = jsonData.getInt("numberOfTeams");
/*  81 */     this.files = (Files)json.readValue("files", Files.class, jsonData);
/*     */     
/*  83 */     this.time = (SceneSettings.Time)json.readValue("time", SceneSettings.Time.class, jsonData);
/*  84 */     this.weather = (Weather)json.readValue("weather", Weather.class, jsonData);
/*  85 */     if (this.weather == Weather.BY_SEASON) {
/*  86 */       this.seasonStart = (Month)json.readValue("seasonStart", Month.class, jsonData);
/*  87 */       this.seasonEnd = (Month)json.readValue("seasonEnd", Month.class, jsonData);
/*  88 */       this.currentMonth = (Month)json.readValue("currentMonth", Month.class, jsonData);
/*     */     } else {
/*  90 */       this.pitchType = (Pitch.Type)json.readValue("pitchType", Pitch.Type.class, jsonData);
/*     */     } 
/*  92 */     this.substitutions = jsonData.getInt("substitutions", 3);
/*  93 */     this.benchSize = jsonData.getInt("benchSize", 5);
/*  94 */     this.userPrefersResult = jsonData.getBoolean("userPrefersResult", false);
/*  95 */     this.currentRound = jsonData.getInt("currentRound", 0);
/*  96 */     this.currentMatch = jsonData.getInt("currentMatch", 0);
/*     */     
/*  98 */     Team[] teamsArray = (Team[])json.readValue("teams", Team[].class, jsonData);
/*  99 */     if (teamsArray != null) {
/* 100 */       Collections.addAll(this.teams, teamsArray);
/*     */     }
/*     */     
/* 103 */     JsonValue rawScorers = jsonData.get("scorers");
/* 104 */     if (rawScorers != null) {
/* 105 */       JsonValue rawScorer = rawScorers.child();
/* 106 */       while (rawScorer != null) {
/* 107 */         Team team = this.teams.get(rawScorer.getInt("team"));
/* 108 */         Player player = team.players.get(rawScorer.getInt("player"));
/* 109 */         this.scorers.add(new Scorer(player, rawScorer.getInt("goals")));
/* 110 */         rawScorer = rawScorer.next();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(Json json) {
/* 116 */     json.writeValue("name", this.name);
/* 117 */     if (this.filename.length() > 0) {
/* 118 */       json.writeValue("filename", this.filename);
/*     */     }
/* 120 */     json.writeValue("category", this.category);
/* 121 */     json.writeValue("numberOfTeams", Integer.valueOf(this.numberOfTeams));
/*     */     
/* 123 */     json.writeValue("time", this.time);
/* 124 */     json.writeValue("weather", this.weather);
/* 125 */     if (this.weather == Weather.BY_SEASON) {
/* 126 */       json.writeValue("seasonStart", this.seasonStart);
/* 127 */       json.writeValue("seasonEnd", this.seasonEnd);
/* 128 */       json.writeValue("currentMonth", this.currentMonth);
/*     */     } else {
/* 130 */       json.writeValue("pitchType", this.pitchType);
/*     */     } 
/* 132 */     json.writeValue("substitutions", Integer.valueOf(this.substitutions));
/* 133 */     json.writeValue("benchSize", Integer.valueOf(this.benchSize));
/* 134 */     json.writeValue("userPrefersResult", Boolean.valueOf(this.userPrefersResult));
/* 135 */     json.writeValue("currentRound", Integer.valueOf(this.currentRound));
/* 136 */     json.writeValue("currentMatch", Integer.valueOf(this.currentMatch));
/* 137 */     json.writeValue("teams", this.teams, Team[].class, Team.class);
/* 138 */     json.writeArrayStart("scorers");
/* 139 */     for (Scorer scorer : this.scorers) {
/* 140 */       json.writeObjectStart();
/* 141 */       json.writeValue("team", Integer.valueOf(this.teams.indexOf(scorer.player.team)));
/* 142 */       json.writeValue("player", Integer.valueOf(scorer.player.team.players.indexOf(scorer.player)));
/* 143 */       json.writeValue("goals", Integer.valueOf(scorer.goals));
/* 144 */       json.writeObjectEnd();
/*     */     } 
/* 146 */     json.writeArrayEnd();
/*     */   }
/*     */   
/*     */   public String getMenuTitle() {
/* 150 */     return this.name;
/*     */   }
/*     */   
/*     */   public void start(ArrayList<Team> teams) {
/* 154 */     this.teams = new ArrayList<>(teams);
/*     */   }
/*     */   
/*     */   public abstract Match getMatch();
/*     */   
/*     */   public Team getTeam(int side) {
/* 160 */     return this.teams.get(getTeamIndex(side));
/*     */   }
/*     */   
/*     */   public int getTeamIndex(int side) {
/* 164 */     return (getMatch()).teams[side];
/*     */   }
/*     */   
/*     */   public boolean bothComputers() {
/* 168 */     return ((getTeam(0)).controlMode == Team.ControlMode.COMPUTER && 
/* 169 */       (getTeam(1)).controlMode == Team.ControlMode.COMPUTER);
/*     */   }
/*     */   
/*     */   public boolean playExtraTime() {
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   public boolean playPenalties() {
/* 177 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isEnded() {
/* 181 */     return true;
/*     */   }
/*     */   
/*     */   public void restart() {
/* 185 */     this.userPrefersResult = false;
/* 186 */     this.scorers.clear();
/*     */   }
/*     */   
/*     */   public static String getCategoryLabel(Category category) {
/* 190 */     String label = "";
/* 191 */     switch (category) {
/*     */       case OFF:
/* 193 */         label = "DIY COMPETITION";
/*     */         break;
/*     */       
/*     */       case AFTER_90_MINUTES:
/* 197 */         label = "PRESET COMPETITION";
/*     */         break;
/*     */     } 
/* 200 */     return label;
/*     */   }
/*     */   
/*     */   public static String getWarningLabel(Category category) {
/* 204 */     String label = "";
/* 205 */     switch (category) {
/*     */       case OFF:
/* 207 */         label = "YOU ARE ABOUT TO LOSE CURRENT DIY COMPETITION";
/*     */         break;
/*     */       
/*     */       case AFTER_90_MINUTES:
/* 211 */         label = "YOU ARE ABOUT TO LOSE CURRENT PRESET COMPETITION";
/*     */         break;
/*     */     } 
/* 214 */     return label;
/*     */   }
/*     */   
/*     */   public String getWeatherLabel() {
/* 218 */     return (this.weather == Weather.BY_SEASON) ? "SEASON" : "PITCH TYPE";
/*     */   }
/*     */   
/*     */   public String getAwayGoalsLabel(AwayGoals awayGoals) {
/* 222 */     switch (awayGoals) {
/*     */       case OFF:
/* 224 */         return "OFF";
/*     */       case AFTER_90_MINUTES:
/* 226 */         return "AFTER 90 MINS";
/*     */       case AFTER_EXTRA_TIME:
/* 228 */         return "AFTER EXTRA TIME";
/*     */     } 
/* 230 */     throw new GdxRuntimeException("Unknown AwayGoals value");
/*     */   }
/*     */ 
/*     */   
/*     */   public SceneSettings.Time getTime() {
/* 235 */     return this.time;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Pitch.Type getPitchType() {
/* 241 */     if (this.weather == Weather.BY_SEASON) {
/* 242 */       int p = -1;
/* 243 */       int n = EMath.rand(0, 99);
/* 244 */       int tot = 0;
/*     */       do {
/* 246 */         p++;
/* 247 */         tot += Pitch.probabilityByMonth[this.currentMonth.ordinal()][p];
/* 248 */       } while (tot <= n);
/*     */       
/* 250 */       return Pitch.Type.values()[p];
/*     */     } 
/*     */ 
/*     */     
/* 254 */     if (this.pitchType == Pitch.Type.RANDOM) {
/* 255 */       return Pitch.random();
/*     */     }
/* 257 */     return this.pitchType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Competition load(FileHandle fileHandle) {
/* 263 */     return (Competition)Assets.json.fromJson(Competition.class, fileHandle);
/*     */   }
/*     */   
/*     */   public void saveAndSetFilename(String filename) {
/* 267 */     this.filename = "";
/* 268 */     save(Assets.savesFolder.child(getCategoryFolder()).child(filename + ".json"));
/* 269 */     this.filename = filename;
/*     */   }
/*     */   
/*     */   public void save(FileHandle fileHandle) {
/* 273 */     fileHandle.writeString(Assets.json.toJson(this, Competition.class), false, "UTF-8");
/*     */   }
/*     */   
/*     */   public String getNewFilename() {
/* 277 */     String newFilename = this.name;
/* 278 */     int i = 2;
/* 279 */     while (Assets.savesFolder.child(getCategoryFolder()).child(newFilename + ".json").exists()) {
/* 280 */       newFilename = this.name + " (" + i + ")";
/* 281 */       i++;
/*     */     } 
/* 283 */     return newFilename;
/*     */   }
/*     */   
/*     */   public String getCategoryFolder() {
/* 287 */     switch (this.category) {
/*     */       case OFF:
/* 289 */         return "DIY";
/*     */       
/*     */       case AFTER_90_MINUTES:
/* 292 */         return "PRESET";
/*     */     } 
/*     */     
/* 295 */     throw new GdxRuntimeException("Unknown category");
/*     */   }
/*     */   
/*     */   public static class Files
/*     */   {
/*     */     public String folder;
/*     */     public String league;
/*     */     public List<String> teams;
/*     */   }
/*     */   
/*     */   public static class Scorer {
/*     */     public Player player;
/*     */     public int goals;
/*     */     
/*     */     Scorer(Player player, int goals) {
/* 310 */       this.player = player;
/* 311 */       this.goals = goals;
/*     */     }
/*     */   }
/*     */   
/*     */   private class ScorerComparator
/*     */     implements Comparator<Scorer> {
/*     */     private ScorerComparator() {}
/*     */     
/*     */     public int compare(Competition.Scorer o1, Competition.Scorer o2) {
/* 320 */       if (o1.goals != o2.goals) {
/* 321 */         return o2.goals - o1.goals;
/*     */       }
/*     */ 
/*     */       
/* 325 */       if (!o1.player.shirtName.equals(o2.player.shirtName)) {
/* 326 */         return o1.player.shirtName.compareTo(o2.player.shirtName);
/*     */       }
/*     */ 
/*     */       
/* 330 */       return o1.player.team.name.compareTo(o2.player.team.name);
/*     */     }
/*     */   }
/*     */   
/*     */   private void sortScorerList() {
/* 335 */     Collections.sort(this.scorers, this.scorerComparator);
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateScorers(Team team, int goals) {
/* 340 */     int teamSize = Math.min(team.players.size(), 11 + this.benchSize);
/* 341 */     int teamWeight = 0;
/* 342 */     for (int playerIndex = 0; playerIndex < teamSize; playerIndex++) {
/* 343 */       Player player = team.players.get(playerIndex);
/* 344 */       teamWeight += player.getScoringWeight();
/*     */     } 
/*     */     
/* 347 */     for (int i = 0; i < goals; i++) {
/* 348 */       int target = 1 + EMath.floor(teamWeight * Math.random());
/* 349 */       int sum = teamWeight;
/* 350 */       for (int j = 0; j < teamSize; j++) {
/* 351 */         Player player = team.players.get(j);
/*     */         
/* 353 */         sum -= player.getScoringWeight();
/*     */         
/* 355 */         if (sum < target) {
/* 356 */           addGoal(player);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addGoal(Player player) {
/* 364 */     Scorer scorer = searchScorer(player);
/* 365 */     if (scorer == null) {
/* 366 */       this.scorers.add(new Scorer(player, 1));
/*     */     } else {
/* 368 */       scorer.goals++;
/*     */     } 
/* 370 */     sortScorerList();
/*     */   }
/*     */   
/*     */   private Scorer searchScorer(Player player) {
/* 374 */     for (Scorer scorer : this.scorers) {
/* 375 */       if (scorer.player == player) {
/* 376 */         return scorer;
/*     */       }
/*     */     } 
/* 379 */     return null;
/*     */   }
/*     */   
/*     */   public int getScorerGoals(Player player) {
/* 383 */     Scorer scorer = searchScorer(player);
/* 384 */     return (scorer == null) ? 0 : scorer.goals;
/*     */   }
/*     */ 
/*     */   
/*     */   public void matchInterrupted() {}
/*     */ 
/*     */   
/*     */   public void matchCompleted() {}
/*     */   
/*     */   public class ComparatorByPlayersValue
/*     */     implements Comparator<Integer>
/*     */   {
/*     */     public int compare(Integer teamIndex1, Integer teamIndex2) {
/* 397 */       Team team1 = Competition.this.teams.get(teamIndex1.intValue()), team2 = Competition.this.teams.get(teamIndex2.intValue());
/* 398 */       int v1 = 0, v2 = 0;
/* 399 */       for (int i = 0; i < 16; i++) {
/* 400 */         v1 += team1.playerAtPosition(i).getValue();
/* 401 */         v2 += team2.playerAtPosition(i).getValue();
/*     */       } 
/*     */ 
/*     */       
/* 405 */       if (v1 != v2) {
/* 406 */         return v2 - v1;
/*     */       }
/*     */ 
/*     */       
/* 410 */       return team1.name.compareTo(team2.name);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\Competition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */