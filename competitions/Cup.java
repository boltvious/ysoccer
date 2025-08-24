/*     */ package com.ygames.ysoccer.competitions;
/*     */ 
/*     */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonValue;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Month;
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
/*     */ public class Cup
/*     */   extends Competition
/*     */   implements Json.Serializable
/*     */ {
/*     */   public ArrayList<Round> rounds;
/*     */   public int currentLeg;
/*     */   public Competition.AwayGoals awayGoals;
/*     */   
/*     */   public Cup() {
/*  30 */     super(Competition.Type.CUP);
/*  31 */     this.rounds = new ArrayList<>();
/*  32 */     this.awayGoals = Competition.AwayGoals.OFF;
/*  33 */     this.currentLeg = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/*  38 */     super.read(json, jsonData);
/*  39 */     Round[] roundsArray = (Round[])json.readValue("rounds", Round[].class, jsonData);
/*  40 */     if (roundsArray != null) {
/*  41 */       for (Round round : roundsArray) {
/*  42 */         round.setCup(this);
/*  43 */         this.rounds.add(round);
/*     */       } 
/*     */     }
/*  46 */     this.currentLeg = jsonData.getInt("currentLeg", 0);
/*  47 */     if (hasTwoLegsRound()) {
/*  48 */       this.awayGoals = (Competition.AwayGoals)json.readValue("awayGoals", Competition.AwayGoals.class, Competition.AwayGoals.AFTER_90_MINUTES, jsonData);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/*  54 */     super.write(json);
/*     */     
/*  56 */     json.writeValue("rounds", this.rounds, Round[].class, Round.class);
/*  57 */     json.writeValue("currentLeg", Integer.valueOf(this.currentLeg));
/*  58 */     if (hasTwoLegsRound()) {
/*  59 */       json.writeValue("awayGoals", this.awayGoals);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void start(ArrayList<Team> teams) {
/*  65 */     super.start(teams);
/*     */ 
/*     */     
/*  68 */     if ((getRound()).legs.size() == 0) {
/*  69 */       getRound().newLeg();
/*  70 */       generateMatches();
/*     */     } 
/*  72 */     updateMonth();
/*     */   }
/*     */ 
/*     */   
/*     */   public void restart() {
/*  77 */     super.restart();
/*  78 */     this.currentRound = 0;
/*  79 */     this.currentMatch = 0;
/*  80 */     this.currentLeg = 0;
/*     */ 
/*     */     
/*  83 */     List<Match> firstLegMatches = new ArrayList<>();
/*  84 */     for (Match m : ((Leg)((Round)this.rounds.get(0)).legs.get(0)).matches) {
/*  85 */       Match match = new Match();
/*  86 */       match.teams[0] = m.teams[0];
/*  87 */       match.teams[1] = m.teams[1];
/*  88 */       firstLegMatches.add(match);
/*     */     } 
/*     */ 
/*     */     
/*  92 */     for (int r = 0; r < this.rounds.size(); r++) {
/*  93 */       Round round = this.rounds.get(r);
/*  94 */       round.legs.clear();
/*     */       
/*  96 */       if (r == 0) {
/*  97 */         round.newLeg();
/*  98 */         ((Leg)round.legs.get(0)).matches.addAll(firstLegMatches);
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     updateMonth();
/*     */   }
/*     */   
/*     */   public Round getRound() {
/* 106 */     return this.rounds.get(this.currentRound);
/*     */   }
/*     */   
/*     */   public Leg getLeg() {
/* 110 */     return (getRound()).legs.get(this.currentLeg);
/*     */   }
/*     */ 
/*     */   
/*     */   public Match getMatch() {
/* 115 */     return (getLeg()).matches.get(this.currentMatch);
/*     */   }
/*     */   
/*     */   public ArrayList<Match> getMatches() {
/* 119 */     return (getLeg()).matches;
/*     */   }
/*     */   
/*     */   public Team getTeam(int t) {
/* 123 */     return this.teams.get((getMatch()).teams[t]);
/*     */   }
/*     */   
/*     */   public boolean isLegEnded() {
/* 127 */     return (this.currentMatch == (getLeg()).matches.size() - 1);
/*     */   }
/*     */   
/*     */   public boolean isRoundEnded() {
/* 131 */     return (this.currentLeg == (getRound()).legs.size() - 1 && !getLeg().hasReplays());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnded() {
/* 136 */     return (this.currentRound == this.rounds.size() - 1 && getLeg().getQualifiedTeam(getMatch()) != -1);
/*     */   }
/*     */   
/*     */   public void nextMatch() {
/* 140 */     this.currentMatch++;
/* 141 */     if (this.currentMatch == (getLeg()).matches.size()) {
/* 142 */       nextLeg();
/*     */     }
/*     */   }
/*     */   
/*     */   private void nextLeg() {
/* 147 */     this.currentLeg++;
/* 148 */     this.currentMatch = 0;
/* 149 */     getRound().newLeg();
/* 150 */     generateMatches();
/* 151 */     if ((getLeg()).matches.size() == 0) {
/* 152 */       nextRound();
/*     */     }
/* 154 */     updateMonth();
/*     */   }
/*     */   
/*     */   private void nextRound() {
/* 158 */     this.currentRound++;
/* 159 */     this.currentLeg = 0;
/* 160 */     this.currentMatch = 0;
/* 161 */     getRound().newLeg();
/* 162 */     generateMatches();
/*     */   }
/*     */   
/*     */   private void updateMonth() {
/* 166 */     if (this.weather == Competition.Weather.BY_SEASON) {
/* 167 */       int seasonLength = (this.seasonEnd.ordinal() - this.seasonStart.ordinal() + 12) % 12;
/* 168 */       this.currentMonth = Month.values()[(this.seasonStart.ordinal() + seasonLength * this.currentRound / this.rounds.size()) % 12];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateMatches() {
/* 175 */     if (this.currentLeg == 0) {
/* 176 */       ArrayList<Integer> qualifiedTeams = new ArrayList<>();
/* 177 */       if (this.currentRound == 0) {
/* 178 */         for (int j = 0; j < this.numberOfTeams; j++) {
/* 179 */           qualifiedTeams.add(Integer.valueOf(j));
/*     */         }
/*     */       } else {
/* 182 */         for (Leg leg : ((Round)this.rounds.get(this.currentRound - 1)).legs) {
/* 183 */           qualifiedTeams.addAll(leg.getQualifiedTeams());
/*     */         }
/*     */       } 
/*     */       
/* 187 */       Collections.shuffle(qualifiedTeams);
/*     */       
/* 189 */       for (int i = 0; i < qualifiedTeams.size() / 2; i++) {
/* 190 */         Match match = new Match();
/* 191 */         match.teams[0] = ((Integer)qualifiedTeams.get(2 * i)).intValue();
/* 192 */         match.teams[1] = ((Integer)qualifiedTeams.get(2 * i + 1)).intValue();
/* 193 */         (getLeg()).matches.add(match);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 198 */     else if (this.currentLeg == 1 && (getRound()).numberOfLegs == 2) {
/* 199 */       for (Match oldMatch : ((Leg)(getRound()).legs.get(0)).matches) {
/* 200 */         Match match = new Match();
/* 201 */         match.teams[0] = oldMatch.teams[1];
/* 202 */         match.teams[1] = oldMatch.teams[0];
/* 203 */         (getLeg()).matches.add(match);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 209 */       Leg previousLeg = (getRound()).legs.get(this.currentLeg - 1);
/* 210 */       for (Match oldMatch : previousLeg.matches) {
/* 211 */         if (previousLeg.getQualifiedTeam(oldMatch) == -1) {
/* 212 */           Match match = new Match();
/* 213 */           match.teams[0] = oldMatch.teams[1];
/* 214 */           match.teams[1] = oldMatch.teams[0];
/* 215 */           (getLeg()).matches.add(match);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void generateResult() {
/* 222 */     Match match = getMatch();
/* 223 */     Team homeTeam = getTeam(0);
/* 224 */     Team awayTeam = getTeam(1);
/*     */     
/* 226 */     int homeGoals = Match.generateGoals(homeTeam, awayTeam, false);
/* 227 */     int awayGoals = Match.generateGoals(awayTeam, homeTeam, false);
/* 228 */     match.setResult(homeGoals, awayGoals, Match.ResultType.AFTER_90_MINUTES);
/*     */     
/* 230 */     if (playExtraTime()) {
/* 231 */       homeGoals += Match.generateGoals(homeTeam, awayTeam, true);
/* 232 */       awayGoals += Match.generateGoals(awayTeam, homeTeam, true);
/* 233 */       match.setResult(homeGoals, awayGoals, Match.ResultType.AFTER_EXTRA_TIME);
/*     */     } 
/*     */     
/* 236 */     generateScorers(homeTeam, homeGoals);
/* 237 */     generateScorers(awayTeam, awayGoals);
/*     */     
/* 239 */     if (playPenalties())
/*     */       while (true) {
/* 241 */         homeGoals = EMath.floor(6.0D * Math.random());
/* 242 */         awayGoals = EMath.floor(6.0D * Math.random());
/* 243 */         if (homeGoals != awayGoals) {
/* 244 */           match.setResult(homeGoals, awayGoals, Match.ResultType.AFTER_PENALTIES);
/*     */           break;
/*     */         } 
/*     */       }  
/*     */   }
/*     */   public boolean playExtraTime() {
/* 250 */     Match match = getMatch();
/* 251 */     Round round = this.rounds.get(this.currentRound);
/*     */ 
/*     */     
/* 254 */     if (this.currentLeg == 0) {
/*     */ 
/*     */       
/* 257 */       if (round.numberOfLegs == 2) {
/* 258 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 262 */       if (match.getResult()[0] != match.getResult()[1]) {
/* 263 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 267 */       switch (round.extraTime) {
/*     */         case OFF:
/* 269 */           return false;
/*     */         
/*     */         case ON:
/* 272 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 275 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/* 280 */     } else if (this.currentLeg == 1 && round.numberOfLegs == 2) {
/*     */ 
/*     */       
/* 283 */       int[] oldResult = ((Leg)(getRound()).legs.get(this.currentLeg - 1)).findResult(match.teams);
/* 284 */       int aggregate1 = match.getResult()[0] + oldResult[1];
/* 285 */       int aggregate2 = match.getResult()[1] + oldResult[0];
/*     */       
/* 287 */       if (aggregate1 != aggregate2) {
/* 288 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 292 */       if (oldResult[1] != match.getResult()[1] && this.awayGoals == Competition.AwayGoals.AFTER_90_MINUTES) {
/* 293 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 297 */       switch (round.extraTime) {
/*     */         case OFF:
/* 299 */           return false;
/*     */         
/*     */         case ON:
/* 302 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 305 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } else {
/* 313 */       if (match.getResult()[0] != match.getResult()[1]) {
/* 314 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 318 */       switch (round.extraTime) {
/*     */         case OFF:
/* 320 */           return false;
/*     */         
/*     */         case ON:
/* 323 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 326 */           return true;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 331 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean playPenalties() {
/* 336 */     Match match = getMatch();
/* 337 */     Round round = this.rounds.get(this.currentRound);
/*     */ 
/*     */     
/* 340 */     if (this.currentLeg == 0) {
/*     */ 
/*     */       
/* 343 */       if (round.numberOfLegs == 2) {
/* 344 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 348 */       if (match.getResult()[0] != match.getResult()[1]) {
/* 349 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 353 */       switch (round.penalties) {
/*     */         case OFF:
/* 355 */           return false;
/*     */         
/*     */         case ON:
/* 358 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 361 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/* 366 */     } else if (this.currentLeg == 1 && round.numberOfLegs == 2) {
/*     */ 
/*     */       
/* 369 */       int[] oldResult = ((Leg)(getRound()).legs.get(this.currentLeg - 1)).findResult(match.teams);
/* 370 */       int aggregate1 = match.getResult()[0] + oldResult[1];
/* 371 */       int aggregate2 = match.getResult()[1] + oldResult[0];
/* 372 */       if (aggregate1 != aggregate2) {
/* 373 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 377 */       if (oldResult[1] != match.getResult()[1] && this.awayGoals != Competition.AwayGoals.OFF) {
/* 378 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 382 */       switch (round.penalties) {
/*     */         case OFF:
/* 384 */           return false;
/*     */         
/*     */         case ON:
/* 387 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 390 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } else {
/* 397 */       if (match.getResult()[0] != match.getResult()[1]) {
/* 398 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 402 */       switch (round.penalties) {
/*     */         case OFF:
/* 404 */           return false;
/*     */         
/*     */         case ON:
/* 407 */           throw new GdxRuntimeException("Invalid state in cup");
/*     */         case IF_REPLAY:
/* 409 */           return true;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 414 */     return false;
/*     */   }
/*     */   
/*     */   public String getMatchStatus(Match match) {
/* 418 */     String s = "";
/*     */     
/* 420 */     int qualified = getLeg().getQualifiedTeam(match);
/*     */ 
/*     */     
/* 423 */     if (this.currentLeg == 0) {
/* 424 */       if (qualified != -1) {
/* 425 */         if (match.resultAfterPenalties != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 431 */           s = ((Team)this.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Math.max(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + "-" + Math.min(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + " " + Assets.strings.get("MATCH STATUS.ON PENALTIES");
/* 432 */           if (match.resultAfterExtraTime != null) {
/* 433 */             s = s + " " + Assets.strings.get("AFTER EXTRA TIME");
/* 434 */             if (match.getResult()[0] != match.resultAfter90[0] || match
/* 435 */               .getResult()[1] != match.resultAfter90[1]) {
/* 436 */               s = s + " " + Assets.strings.get("MATCH STATUS.90 MINUTES") + " " + match.resultAfter90[0] + "-" + match.resultAfter90[1];
/*     */             }
/*     */           }
/*     */         
/* 440 */         } else if (match.resultAfterExtraTime != null) {
/*     */           
/* 442 */           s = Assets.strings.get("AFTER EXTRA TIME") + " " + Assets.strings.get("MATCH STATUS.90 MINUTES") + " " + match.resultAfter90[0] + "-" + match.resultAfter90[1];
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */     }
/* 449 */     else if (this.currentLeg == 1 && ((Round)this.rounds.get(this.currentRound)).numberOfLegs == 2) {
/* 450 */       if (qualified != -1) {
/*     */         
/* 452 */         if (match.resultAfterPenalties != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 457 */           s = ((Team)this.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Math.max(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + "-" + Math.min(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + " " + Assets.strings.get("MATCH STATUS.ON PENALTIES");
/* 458 */           if (match.resultAfterExtraTime != null) {
/* 459 */             s = s + " " + Assets.strings.get("AFTER EXTRA TIME");
/* 460 */             if (match.getResult()[0] != match.resultAfter90[0] || match
/* 461 */               .getResult()[1] != match.resultAfter90[1]) {
/* 462 */               s = s + " " + Assets.strings.get("MATCH STATUS.90 MINUTES") + " " + match.resultAfter90[0] + "-" + match.resultAfter90[1];
/*     */             }
/*     */           } 
/*     */         } else {
/*     */           
/* 467 */           int[] oldResult = ((Leg)(getRound()).legs.get(this.currentLeg - 1)).findResult(match.teams);
/* 468 */           int agg_score_a = match.getResult()[0] + oldResult[1];
/* 469 */           int agg_score_b = match.getResult()[1] + oldResult[0];
/*     */ 
/*     */           
/* 472 */           if (agg_score_a == agg_score_b) {
/*     */             
/* 474 */             s = s + agg_score_a + "-" + agg_score_b + " " + Assets.strings.get("MATCH STATUS.ON AGGREGATE") + " " + ((Team)this.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Assets.strings.get("MATCH STATUS.ON AWAY GOALS");
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 482 */             s = ((Team)this.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Math.max(agg_score_a, agg_score_b) + "-" + Math.min(agg_score_a, agg_score_b) + " " + Assets.strings.get("MATCH STATUS.ON AGGREGATE");
/*     */           } 
/* 484 */           if (match.resultAfterExtraTime != null) {
/* 485 */             s = s + " " + Assets.strings.get("AFTER EXTRA TIME");
/*     */           }
/*     */         } 
/*     */       } else {
/* 489 */         int[] oldResult = ((Leg)(getRound()).legs.get(this.currentLeg - 1)).findResult(match.teams);
/* 490 */         s = Assets.strings.get("MATCH STATUS.1ST LEG") + " " + oldResult[1] + "-" + oldResult[0];
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 496 */     else if (qualified != -1) {
/* 497 */       if (match.resultAfterPenalties != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 502 */         s = ((Team)this.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Math.max(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + "-" + Math.min(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + " " + Assets.strings.get("MATCH STATUS.ON PENALTIES");
/* 503 */         if (match.resultAfterExtraTime != null) {
/* 504 */           s = s + " " + Assets.strings.get("AFTER EXTRA TIME");
/*     */         }
/* 506 */       } else if (match.resultAfterExtraTime != null) {
/* 507 */         s = Assets.strings.get("AFTER EXTRA TIME") + " " + Assets.strings.get("MATCH STATUS.90 MINUTES") + " " + match.resultAfter90[0] + "-" + match.resultAfter90[1];
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 513 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMenuTitle() {
/* 519 */     String title = this.name + " " + Assets.gettext((getRound()).name);
/* 520 */     int matches = (getLeg()).matches.size();
/* 521 */     switch (((Round)this.rounds.get(this.currentRound)).numberOfLegs) {
/*     */       case 1:
/* 523 */         switch (this.currentLeg) {
/*     */           case 0:
/*     */             break;
/*     */           case 1:
/* 527 */             if (matches == 1) {
/* 528 */               title = title + " " + Assets.strings.get("MATCH STATUS.REPLAY"); break;
/*     */             } 
/* 530 */             title = title + " " + Assets.strings.get("MATCH STATUS.REPLAYS");
/*     */             break;
/*     */           
/*     */           case 2:
/* 534 */             if (matches == 1) {
/* 535 */               title = title + " " + Assets.strings.get("MATCH STATUS.2ND REPLAY"); break;
/*     */             } 
/* 537 */             title = title + " " + Assets.strings.get("MATCH STATUS.2ND REPLAYS");
/*     */             break;
/*     */           
/*     */           case 3:
/* 541 */             if (matches == 1) {
/* 542 */               title = title + " " + Assets.strings.get("MATCH STATUS.3RD REPLAY"); break;
/*     */             } 
/* 544 */             title = title + " " + Assets.strings.get("MATCH STATUS.3RD REPLAYS");
/*     */             break;
/*     */         } 
/*     */         
/* 548 */         if (matches == 1) {
/* 549 */           title = title + " " + Assets.strings.get("MATCH STATUS.REPLAY"); break;
/*     */         } 
/* 551 */         title = title + " " + Assets.strings.get("MATCH STATUS.REPLAYS");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 556 */         switch (this.currentLeg) {
/*     */           case 0:
/* 558 */             title = title + " " + Assets.strings.get("MATCH STATUS.1ST LEG");
/*     */             break;
/*     */           case 1:
/* 561 */             title = title + " " + Assets.strings.get("MATCH STATUS.2ND LEG");
/*     */             break;
/*     */         } 
/* 564 */         if (matches == 1) {
/* 565 */           title = title + " " + Assets.strings.get("MATCH STATUS.REPLAY"); break;
/*     */         } 
/* 567 */         title = title + " " + Assets.strings.get("MATCH STATUS.REPLAYS");
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 572 */     return title;
/*     */   }
/*     */   
/*     */   public void addRound() {
/* 576 */     if (this.rounds.size() < 6) {
/* 577 */       Round round = new Round();
/* 578 */       round.setCup(this);
/* 579 */       this.rounds.add(round);
/*     */     } 
/* 581 */     this.numberOfTeams = getRoundTeams(0);
/* 582 */     updateRoundNames();
/*     */   }
/*     */   
/*     */   public void removeRound() {
/* 586 */     if (this.rounds.size() > 1) {
/* 587 */       this.rounds.remove(this.rounds.size() - 1);
/*     */     }
/* 589 */     this.numberOfTeams = getRoundTeams(0);
/* 590 */     updateRoundNames();
/*     */   }
/*     */   
/*     */   private void updateRoundNames() {
/* 594 */     for (int i = 0; i < this.rounds.size(); i++) {
/* 595 */       ((Round)this.rounds.get(i)).name = getRoundLabel(i);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getRoundLabel(int round) {
/* 600 */     if (round == this.rounds.size() - 1)
/* 601 */       return "FINAL"; 
/* 602 */     if (round == this.rounds.size() - 2)
/* 603 */       return "SEMI-FINAL"; 
/* 604 */     if (round == this.rounds.size() - 3)
/* 605 */       return "QUARTER-FINAL"; 
/* 606 */     if (round == 0)
/* 607 */       return "FIRST ROUND"; 
/* 608 */     if (round == 1) {
/* 609 */       return "SECOND ROUND";
/*     */     }
/* 611 */     return "THIRD ROUND";
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRoundTeams(int round) {
/* 616 */     return (int)Math.pow(2.0D, (this.rounds.size() - round));
/*     */   }
/*     */   
/*     */   public boolean hasTwoLegsRound() {
/* 620 */     for (Round round : this.rounds) {
/* 621 */       if (round.numberOfLegs == 2) {
/* 622 */         return true;
/*     */       }
/*     */     } 
/* 625 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void matchInterrupted() {
/* 630 */     Match match = getMatch();
/* 631 */     if ((match.team[0]).controlMode == Team.ControlMode.COMPUTER && (match.team[1]).controlMode != Team.ControlMode.COMPUTER) {
/* 632 */       int goals = 4 + Assets.random.nextInt(2);
/* 633 */       if (match.resultAfterPenalties != null) {
/* 634 */         goals += match.resultAfterPenalties[1];
/* 635 */         match.resultAfterPenalties[0] = match.resultAfterPenalties[0] + goals;
/* 636 */       } else if (match.resultAfterExtraTime != null) {
/* 637 */         goals += match.resultAfterExtraTime[1];
/* 638 */         match.resultAfterExtraTime[0] = match.resultAfterExtraTime[0] + goals;
/* 639 */         generateScorers(match.team[0], goals);
/* 640 */       } else if (match.resultAfter90 != null) {
/* 641 */         goals += match.resultAfter90[1];
/* 642 */         match.resultAfter90[0] = match.resultAfter90[0] + goals;
/* 643 */         generateScorers(match.team[0], goals);
/*     */       } else {
/* 645 */         match.setResult(goals, 0, Match.ResultType.AFTER_90_MINUTES);
/* 646 */         generateScorers(match.team[0], goals);
/*     */       } 
/* 648 */       matchCompleted();
/* 649 */     } else if ((match.team[0]).controlMode != Team.ControlMode.COMPUTER && (match.team[1]).controlMode == Team.ControlMode.COMPUTER) {
/* 650 */       int goals = 4 + Assets.random.nextInt(2);
/* 651 */       if (match.resultAfterPenalties != null) {
/* 652 */         goals += match.resultAfterPenalties[0];
/* 653 */         match.resultAfterPenalties[1] = match.resultAfterPenalties[1] + goals;
/* 654 */       } else if (match.resultAfterExtraTime != null) {
/* 655 */         goals += match.resultAfterExtraTime[0];
/* 656 */         match.resultAfterExtraTime[1] = match.resultAfterExtraTime[1] + goals;
/* 657 */         generateScorers(match.team[0], goals);
/* 658 */       } else if (match.resultAfter90 != null) {
/* 659 */         goals += match.resultAfter90[0];
/* 660 */         match.resultAfter90[1] = match.resultAfter90[1] + goals;
/* 661 */         generateScorers(match.team[0], goals);
/*     */       } else {
/* 663 */         match.setResult(0, 6, Match.ResultType.AFTER_90_MINUTES);
/* 664 */         generateScorers(match.team[0], goals);
/*     */       } 
/* 666 */       matchCompleted();
/*     */     } else {
/* 668 */       match.resultAfter90 = null;
/* 669 */       match.resultAfterExtraTime = null;
/* 670 */       match.resultAfterPenalties = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\Cup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */