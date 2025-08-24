/*     */ package com.ygames.ysoccer.competitions.tournament.knockout;
/*     */ 
/*     */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonValue;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.tournament.Round;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
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
/*     */ 
/*     */ public class Knockout
/*     */   extends Round
/*     */   implements Json.Serializable
/*     */ {
/*     */   public int numberOfLegs;
/*     */   public Round.ExtraTime extraTime;
/*     */   public Round.Penalties penalties;
/*     */   public int currentLeg;
/*     */   public ArrayList<Leg> legs;
/*     */   
/*     */   public Knockout() {
/*  33 */     super(Round.Type.KNOCKOUT);
/*  34 */     this.numberOfLegs = 1;
/*  35 */     this.extraTime = Round.ExtraTime.ON;
/*  36 */     this.penalties = Round.Penalties.ON;
/*  37 */     this.currentLeg = 0;
/*  38 */     this.legs = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/*  43 */     super.read(json, jsonData);
/*  44 */     this.numberOfLegs = jsonData.getInt("numberOfLegs");
/*  45 */     this.extraTime = (Round.ExtraTime)json.readValue("extraTime", Round.ExtraTime.class, jsonData);
/*  46 */     this.penalties = (Round.Penalties)json.readValue("penalties", Round.Penalties.class, jsonData);
/*  47 */     this.currentLeg = jsonData.getInt("currentLeg", 0);
/*     */     
/*  49 */     Match[][] legsArray = (Match[][])json.readValue("legs", Match[][].class, jsonData);
/*  50 */     if (legsArray != null) {
/*  51 */       for (Match[] matchesArray : legsArray) {
/*  52 */         Leg leg = new Leg(this);
/*  53 */         Collections.addAll(leg.matches, matchesArray);
/*  54 */         this.legs.add(leg);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/*  61 */     super.write(json);
/*  62 */     json.writeValue("numberOfLegs", Integer.valueOf(this.numberOfLegs));
/*  63 */     json.writeValue("extraTime", this.extraTime);
/*  64 */     json.writeValue("penalties", this.penalties);
/*  65 */     json.writeValue("currentLeg", Integer.valueOf(this.currentLeg));
/*     */     
/*  67 */     json.writeArrayStart("legs");
/*  68 */     for (Leg leg : this.legs) {
/*  69 */       json.writeArrayStart();
/*  70 */       for (Match match : leg.matches) {
/*  71 */         json.writeValue(match, Match.class);
/*     */       }
/*  73 */       json.writeArrayEnd();
/*     */     } 
/*  75 */     json.writeArrayEnd();
/*     */   }
/*     */   
/*     */   public ArrayList<Match> getMatches() {
/*  79 */     return (getLeg()).matches;
/*     */   }
/*     */   
/*     */   public Leg getLeg() {
/*  83 */     return this.legs.get(this.currentLeg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void start(ArrayList<Integer> qualifiedTeams) {
/*  88 */     this.currentLeg = 0;
/*     */ 
/*     */     
/*  91 */     if (this.legs.size() == 0) {
/*  92 */       newLeg();
/*  93 */       generateCalendar(qualifiedTeams);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void restart() {
/*  99 */     this.currentLeg = 0;
/*     */ 
/*     */     
/* 102 */     List<Match> firstLegMatches = new ArrayList<>();
/* 103 */     for (Match m : ((Leg)this.legs.get(0)).matches) {
/* 104 */       Match match = new Match();
/* 105 */       match.teams[0] = m.teams[0];
/* 106 */       match.teams[1] = m.teams[1];
/* 107 */       firstLegMatches.add(match);
/*     */     } 
/*     */     
/* 110 */     this.legs.clear();
/*     */ 
/*     */     
/* 113 */     newLeg();
/* 114 */     ((Leg)this.legs.get(0)).matches.addAll(firstLegMatches);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 119 */     this.currentLeg = 0;
/* 120 */     this.legs.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Match getMatch() {
/* 125 */     return (getLeg()).matches.get(this.tournament.currentMatch);
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextMatch() {
/* 130 */     this.tournament.currentMatch++;
/* 131 */     if (this.tournament.currentMatch == (getLeg()).matches.size()) {
/* 132 */       nextLeg();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected String nextMatchLabel() {
/* 138 */     String label = "NEXT MATCH";
/* 139 */     if (isLegEnded())
/* 140 */       if (isEnded())
/* 141 */       { label = "NEXT ROUND"; }
/*     */       else
/* 143 */       { switch (this.currentLeg)
/*     */         { case 0:
/* 145 */             if (this.numberOfLegs == 2) {
/* 146 */               label = "CUP.2ND LEG ROUND";
/*     */             } else {
/* 148 */               label = "CUP.PLAY REPLAYS";
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 157 */             return label; }  label = "CUP.PLAY REPLAYS"; }   return label;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean nextMatchOnHold() {
/* 162 */     return !isLegEnded();
/*     */   }
/*     */   
/*     */   private void nextLeg() {
/* 166 */     this.currentLeg++;
/* 167 */     this.tournament.currentMatch = 0;
/* 168 */     newLeg();
/* 169 */     generateNextLegCalendar();
/* 170 */     if ((getLeg()).matches.size() == 0) {
/* 171 */       ArrayList<Integer> qualifiedTeams = new ArrayList<>();
/* 172 */       for (Leg leg : this.legs) {
/* 173 */         qualifiedTeams.addAll(leg.getQualifiedTeams());
/*     */       }
/*     */ 
/*     */       
/* 177 */       Collections.sort(qualifiedTeams, (Comparator<? super Integer>)this.tournament.teamComparatorByPlayersValue);
/*     */       
/* 179 */       this.tournament.nextRound(qualifiedTeams);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnded() {
/* 185 */     return (this.currentLeg == this.legs.size() - 1 && !getLeg().hasReplays());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPreset() {
/* 190 */     return (this.legs.size() > 0);
/*     */   }
/*     */   
/*     */   private boolean isLegEnded() {
/* 194 */     return (this.tournament.currentMatch == (getLeg()).matches.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateCalendar(ArrayList<Integer> qualifiedTeams) {
/* 199 */     if (!this.seeded) {
/* 200 */       Collections.shuffle(qualifiedTeams);
/*     */     }
/*     */ 
/*     */     
/* 204 */     List<Integer> groupsIndexes = new ArrayList<>();
/* 205 */     for (int t = 0; t < qualifiedTeams.size() / 2; t++) {
/* 206 */       groupsIndexes.add(Integer.valueOf(t));
/*     */     }
/* 208 */     List<Integer> teamsMapping = new ArrayList<>();
/* 209 */     for (int j = 0; j < 2; j++) {
/* 210 */       Collections.shuffle(groupsIndexes);
/* 211 */       for (int g = 0; g < qualifiedTeams.size() / 2; g++) {
/* 212 */         teamsMapping.add(Integer.valueOf(j * qualifiedTeams.size() / 2 + ((Integer)groupsIndexes.get(g)).intValue()));
/*     */       }
/*     */     } 
/*     */     
/* 216 */     for (int i = 0; i < qualifiedTeams.size() / 2; i++) {
/* 217 */       Match match = new Match();
/* 218 */       int shuffle = EMath.rand(0, 1);
/* 219 */       match.teams[0] = ((Integer)qualifiedTeams.get(((Integer)teamsMapping.get(i + shuffle * qualifiedTeams.size() / 2)).intValue())).intValue();
/* 220 */       match.teams[1] = ((Integer)qualifiedTeams.get(((Integer)teamsMapping.get(i + (1 - shuffle) * qualifiedTeams.size() / 2)).intValue())).intValue();
/* 221 */       (getLeg()).matches.add(match);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateNextLegCalendar() {
/* 227 */     if (this.currentLeg == 1 && this.numberOfLegs == 2) {
/* 228 */       for (Match oldMatch : ((Leg)this.legs.get(0)).matches) {
/* 229 */         Match match = new Match();
/* 230 */         match.teams[0] = oldMatch.teams[1];
/* 231 */         match.teams[1] = oldMatch.teams[0];
/* 232 */         (getLeg()).matches.add(match);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 238 */       Leg previousLeg = this.legs.get(this.currentLeg - 1);
/* 239 */       for (Match oldMatch : previousLeg.matches) {
/* 240 */         if (previousLeg.getQualifiedTeam(oldMatch) == -1) {
/* 241 */           Match match = new Match();
/* 242 */           match.teams[0] = oldMatch.teams[1];
/* 243 */           match.teams[1] = oldMatch.teams[0];
/* 244 */           (getLeg()).matches.add(match);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void generateResult() {
/* 251 */     Match match = getMatch();
/* 252 */     Team homeTeam = this.tournament.getTeam(0);
/* 253 */     Team awayTeam = this.tournament.getTeam(1);
/*     */     
/* 255 */     int homeGoals = Match.generateGoals(homeTeam, awayTeam, false);
/* 256 */     int awayGoals = Match.generateGoals(awayTeam, homeTeam, false);
/* 257 */     match.setResult(homeGoals, awayGoals, Match.ResultType.AFTER_90_MINUTES);
/*     */     
/* 259 */     if (playExtraTime()) {
/* 260 */       homeGoals += Match.generateGoals(homeTeam, awayTeam, true);
/* 261 */       awayGoals += Match.generateGoals(awayTeam, homeTeam, true);
/* 262 */       match.setResult(homeGoals, awayGoals, Match.ResultType.AFTER_EXTRA_TIME);
/*     */     } 
/*     */     
/* 265 */     this.tournament.generateScorers(homeTeam, homeGoals);
/* 266 */     this.tournament.generateScorers(awayTeam, awayGoals);
/*     */     
/* 268 */     if (playPenalties())
/*     */       while (true) {
/* 270 */         homeGoals = EMath.floor(6.0D * Math.random());
/* 271 */         awayGoals = EMath.floor(6.0D * Math.random());
/* 272 */         if (homeGoals != awayGoals) {
/* 273 */           match.setResult(homeGoals, awayGoals, Match.ResultType.AFTER_PENALTIES);
/*     */           break;
/*     */         } 
/*     */       }  
/*     */   }
/*     */   
/*     */   protected boolean playExtraTime() {
/* 280 */     Match match = getMatch();
/*     */ 
/*     */     
/* 283 */     if (this.currentLeg == 0) {
/*     */ 
/*     */       
/* 286 */       if (this.numberOfLegs == 2) {
/* 287 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 291 */       if (match.getResult()[0] != match.getResult()[1]) {
/* 292 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 296 */       switch (this.extraTime) {
/*     */         case OFF:
/* 298 */           return false;
/*     */         
/*     */         case ON:
/* 301 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 304 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/* 309 */     } else if (this.currentLeg == 1 && this.numberOfLegs == 2) {
/*     */ 
/*     */       
/* 312 */       int[] oldResult = ((Leg)this.legs.get(this.currentLeg - 1)).findResult(match.teams);
/* 313 */       int aggregate1 = match.getResult()[0] + oldResult[1];
/* 314 */       int aggregate2 = match.getResult()[1] + oldResult[0];
/* 315 */       if (aggregate1 != aggregate2) {
/* 316 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 320 */       if (oldResult[1] != match.getResult()[1] && this.tournament.awayGoals == Competition.AwayGoals.AFTER_90_MINUTES) {
/* 321 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 325 */       switch (this.extraTime) {
/*     */         case OFF:
/* 327 */           return false;
/*     */         
/*     */         case ON:
/* 330 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 333 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } else {
/* 341 */       if (match.getResult()[0] != match.getResult()[1]) {
/* 342 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 346 */       switch (this.extraTime) {
/*     */         case OFF:
/* 348 */           return false;
/*     */         
/*     */         case ON:
/* 351 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 354 */           return true;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 359 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean playPenalties() {
/* 365 */     Match match = getMatch();
/*     */ 
/*     */     
/* 368 */     if (this.currentLeg == 0) {
/*     */ 
/*     */       
/* 371 */       if (this.numberOfLegs == 2) {
/* 372 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 376 */       if (match.getResult()[0] != match.getResult()[1]) {
/* 377 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 381 */       switch (this.penalties) {
/*     */         case OFF:
/* 383 */           return false;
/*     */         
/*     */         case ON:
/* 386 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 389 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/* 394 */     } else if (this.currentLeg == 1 && this.numberOfLegs == 2) {
/*     */ 
/*     */       
/* 397 */       int[] oldResult = ((Leg)this.legs.get(0)).findResult(match.teams);
/* 398 */       int aggregate1 = match.getResult()[0] + oldResult[1];
/* 399 */       int aggregate2 = match.getResult()[1] + oldResult[0];
/* 400 */       if (aggregate1 != aggregate2) {
/* 401 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 405 */       if (oldResult[1] != match.getResult()[1] && this.tournament.awayGoals != Competition.AwayGoals.OFF) {
/* 406 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 410 */       switch (this.penalties) {
/*     */         case OFF:
/* 412 */           return false;
/*     */         
/*     */         case ON:
/* 415 */           return true;
/*     */         
/*     */         case IF_REPLAY:
/* 418 */           return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } else {
/* 425 */       if (match.getResult()[0] != match.getResult()[1]) {
/* 426 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 430 */       switch (this.penalties) {
/*     */         case OFF:
/* 432 */           return false;
/*     */         
/*     */         case ON:
/* 435 */           throw new GdxRuntimeException("Invalid state in cup");
/*     */         case IF_REPLAY:
/* 437 */           return true;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 442 */     return false;
/*     */   }
/*     */   
/*     */   public String getMatchStatus(Match match) {
/* 446 */     String s = "";
/*     */     
/* 448 */     int qualified = getLeg().getQualifiedTeam(match);
/*     */ 
/*     */     
/* 451 */     if (this.currentLeg == 0) {
/* 452 */       if (qualified != -1) {
/* 453 */         if (match.resultAfterPenalties != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 459 */           s = ((Team)this.tournament.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Math.max(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + "-" + Math.min(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + " " + Assets.strings.get("MATCH STATUS.ON PENALTIES");
/* 460 */           if (match.resultAfterExtraTime != null) {
/* 461 */             s = s + " " + Assets.strings.get("AFTER EXTRA TIME");
/* 462 */             if (match.getResult()[0] != match.resultAfter90[0] || match
/* 463 */               .getResult()[1] != match.resultAfter90[1]) {
/* 464 */               s = s + " " + Assets.strings.get("MATCH STATUS.90 MINUTES") + " " + match.resultAfter90[0] + "-" + match.resultAfter90[1];
/*     */             }
/*     */           }
/*     */         
/* 468 */         } else if (match.resultAfterExtraTime != null) {
/*     */           
/* 470 */           s = Assets.strings.get("AFTER EXTRA TIME") + " " + Assets.strings.get("MATCH STATUS.90 MINUTES") + " " + match.resultAfter90[0] + "-" + match.resultAfter90[1];
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */     }
/* 477 */     else if (this.currentLeg == 1 && this.numberOfLegs == 2) {
/* 478 */       if (qualified != -1) {
/*     */         
/* 480 */         if (match.resultAfterPenalties != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 485 */           s = ((Team)this.tournament.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Math.max(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + "-" + Math.min(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + " " + Assets.strings.get("MATCH STATUS.ON PENALTIES");
/* 486 */           if (match.resultAfterExtraTime != null) {
/* 487 */             s = s + " " + Assets.strings.get("AFTER EXTRA TIME");
/* 488 */             if (match.getResult()[0] != match.resultAfter90[0] || match
/* 489 */               .getResult()[1] != match.resultAfter90[1]) {
/* 490 */               s = s + " " + Assets.strings.get("MATCH STATUS.90 MINUTES") + " " + match.resultAfter90[0] + "-" + match.resultAfter90[1];
/*     */             }
/*     */           } 
/*     */         } else {
/*     */           
/* 495 */           int[] oldResult = ((Leg)this.legs.get(this.currentLeg - 1)).findResult(match.teams);
/* 496 */           int agg_score_a = match.getResult()[0] + oldResult[1];
/* 497 */           int agg_score_b = match.getResult()[1] + oldResult[0];
/*     */ 
/*     */           
/* 500 */           if (agg_score_a == agg_score_b) {
/*     */             
/* 502 */             s = s + agg_score_a + "-" + agg_score_b + " " + Assets.strings.get("MATCH STATUS.ON AGGREGATE") + " " + ((Team)this.tournament.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Assets.strings.get("MATCH STATUS.ON AWAY GOALS");
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */             
/* 510 */             s = ((Team)this.tournament.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Math.max(agg_score_a, agg_score_b) + "-" + Math.min(agg_score_a, agg_score_b) + " " + Assets.strings.get("MATCH STATUS.ON AGGREGATE");
/*     */           } 
/* 512 */           if (match.resultAfterExtraTime != null) {
/* 513 */             s = s + " " + Assets.strings.get("AFTER EXTRA TIME");
/*     */           }
/*     */         } 
/*     */       } else {
/* 517 */         int[] oldResult = ((Leg)this.legs.get(this.currentLeg - 1)).findResult(match.teams);
/* 518 */         s = Assets.strings.get("MATCH STATUS.1ST LEG") + " " + oldResult[1] + "-" + oldResult[0];
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 524 */     else if (qualified != -1) {
/* 525 */       if (match.resultAfterPenalties != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 530 */         s = ((Team)this.tournament.teams.get(qualified)).name + " " + Assets.strings.get("MATCH STATUS.WIN") + " " + Math.max(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + "-" + Math.min(match.resultAfterPenalties[0], match.resultAfterPenalties[1]) + " " + Assets.strings.get("MATCH STATUS.ON PENALTIES");
/* 531 */         if (match.resultAfterExtraTime != null) {
/* 532 */           s = s + " " + Assets.strings.get("AFTER EXTRA TIME");
/*     */         }
/* 534 */       } else if (match.resultAfterExtraTime != null) {
/* 535 */         s = Assets.strings.get("AFTER EXTRA TIME") + " " + Assets.strings.get("MATCH STATUS.90 MINUTES") + " " + match.resultAfter90[0] + "-" + match.resultAfter90[1];
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 541 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getMenuTitle() {
/* 546 */     String title = Assets.gettext(this.name);
/* 547 */     int matches = (getLeg()).matches.size();
/* 548 */     switch (this.numberOfLegs) {
/*     */       case 1:
/* 550 */         switch (this.currentLeg) {
/*     */           case 0:
/*     */             break;
/*     */           case 1:
/* 554 */             if (matches == 1) {
/* 555 */               title = title + " " + Assets.strings.get("MATCH STATUS.REPLAY"); break;
/*     */             } 
/* 557 */             title = title + " " + Assets.strings.get("MATCH STATUS.REPLAYS");
/*     */             break;
/*     */           
/*     */           case 2:
/* 561 */             if (matches == 1) {
/* 562 */               title = title + " " + Assets.strings.get("MATCH STATUS.2ND REPLAY"); break;
/*     */             } 
/* 564 */             title = title + " " + Assets.strings.get("MATCH STATUS.2ND REPLAYS");
/*     */             break;
/*     */           
/*     */           case 3:
/* 568 */             if (matches == 1) {
/* 569 */               title = title + " " + Assets.strings.get("MATCH STATUS.3RD REPLAY"); break;
/*     */             } 
/* 571 */             title = title + " " + Assets.strings.get("MATCH STATUS.3RD REPLAYS");
/*     */             break;
/*     */         } 
/*     */         
/* 575 */         if (matches == 1) {
/* 576 */           title = title + " " + Assets.strings.get("MATCH STATUS.REPLAY"); break;
/*     */         } 
/* 578 */         title = title + " " + Assets.strings.get("MATCH STATUS.REPLAYS");
/*     */         break;
/*     */ 
/*     */       
/*     */       case 2:
/* 583 */         switch (this.currentLeg) {
/*     */           case 0:
/* 585 */             title = title + " " + Assets.strings.get("MATCH STATUS.1ST LEG");
/*     */             break;
/*     */           case 1:
/* 588 */             title = title + " " + Assets.strings.get("MATCH STATUS.2ND LEG");
/*     */             break;
/*     */         } 
/* 591 */         if (matches == 1) {
/* 592 */           title = title + " " + Assets.strings.get("MATCH STATUS.REPLAY"); break;
/*     */         } 
/* 594 */         title = title + " " + Assets.strings.get("MATCH STATUS.REPLAYS");
/*     */         break;
/*     */     } 
/*     */     
/* 598 */     return title;
/*     */   }
/*     */   
/*     */   public void newLeg() {
/* 602 */     Leg leg = new Leg(this);
/* 603 */     this.legs.add(leg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void matchCompleted() {}
/*     */ 
/*     */   
/*     */   protected void matchInterrupted() {
/* 612 */     Match match = getMatch();
/* 613 */     if ((match.team[0]).controlMode == Team.ControlMode.COMPUTER && (match.team[1]).controlMode != Team.ControlMode.COMPUTER) {
/* 614 */       int goals = 4 + Assets.random.nextInt(2);
/* 615 */       if (match.resultAfterPenalties != null) {
/* 616 */         goals += match.resultAfterPenalties[1];
/* 617 */         match.resultAfterPenalties[0] = match.resultAfterPenalties[0] + goals;
/* 618 */       } else if (match.resultAfterExtraTime != null) {
/* 619 */         goals += match.resultAfterExtraTime[1];
/* 620 */         match.resultAfterExtraTime[0] = match.resultAfterExtraTime[0] + goals;
/* 621 */         this.tournament.generateScorers(match.team[0], goals);
/* 622 */       } else if (match.resultAfter90 != null) {
/* 623 */         goals += match.resultAfter90[1];
/* 624 */         match.resultAfter90[0] = match.resultAfter90[0] + goals;
/* 625 */         this.tournament.generateScorers(match.team[0], goals);
/*     */       } else {
/* 627 */         match.setResult(goals, 0, Match.ResultType.AFTER_90_MINUTES);
/* 628 */         this.tournament.generateScorers(match.team[0], goals);
/*     */       } 
/* 630 */       matchCompleted();
/* 631 */     } else if ((match.team[0]).controlMode != Team.ControlMode.COMPUTER && (match.team[1]).controlMode == Team.ControlMode.COMPUTER) {
/* 632 */       int goals = 4 + Assets.random.nextInt(2);
/* 633 */       if (match.resultAfterPenalties != null) {
/* 634 */         goals += match.resultAfterPenalties[0];
/* 635 */         match.resultAfterPenalties[1] = match.resultAfterPenalties[1] + goals;
/* 636 */       } else if (match.resultAfterExtraTime != null) {
/* 637 */         goals += match.resultAfterExtraTime[0];
/* 638 */         match.resultAfterExtraTime[1] = match.resultAfterExtraTime[1] + goals;
/* 639 */         this.tournament.generateScorers(match.team[0], goals);
/* 640 */       } else if (match.resultAfter90 != null) {
/* 641 */         goals += match.resultAfter90[0];
/* 642 */         match.resultAfter90[1] = match.resultAfter90[1] + goals;
/* 643 */         this.tournament.generateScorers(match.team[0], goals);
/*     */       } else {
/* 645 */         match.setResult(0, 6, Match.ResultType.AFTER_90_MINUTES);
/* 646 */         this.tournament.generateScorers(match.team[0], goals);
/*     */       } 
/* 648 */       matchCompleted();
/*     */     } else {
/* 650 */       match.resultAfter90 = null;
/* 651 */       match.resultAfterExtraTime = null;
/* 652 */       match.resultAfterPenalties = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\tournament\knockout\Knockout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */