/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.tournament.Round;
/*     */ import com.ygames.ysoccer.competitions.tournament.Tournament;
/*     */ import com.ygames.ysoccer.competitions.tournament.groups.Group;
/*     */ import com.ygames.ysoccer.competitions.tournament.groups.Groups;
/*     */ import com.ygames.ysoccer.competitions.tournament.knockout.Knockout;
/*     */ import com.ygames.ysoccer.competitions.tournament.knockout.Leg;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class DiyTournamentCalendar
/*     */   extends GLScreen
/*     */ {
/*     */   private Tournament tournament;
/*     */   private Groups groups;
/*     */   private Knockout knockout;
/*     */   private int currentGroup;
/*     */   private int currentMatch;
/*     */   private int matchSide;
/*     */   private List<Integer> groupsTeams;
/*     */   private List<Widget> teamButtons;
/*     */   private ArrayList<Match> matches;
/*     */   private Mode mode;
/*     */   
/*     */   private enum Mode {
/*  39 */     GROUPS_DISTRIBUTION, GROUP_MATCHES, KNOCKOUT_CALENDAR;
/*     */   }
/*     */ 
/*     */   
/*     */   DiyTournamentCalendar(GLGame game, Tournament tournament) {
/*  44 */     super(game);
/*  45 */     this.tournament = tournament;
/*  46 */     this.groupsTeams = new ArrayList<>();
/*  47 */     this.matches = new ArrayList<>();
/*     */     
/*  49 */     this.background = game.stateBackground;
/*     */ 
/*     */     
/*  52 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, "DIY TOURNAMENT CALENDAR", game.stateColor.body.intValue());
/*  53 */     this.widgets.add(titleBar);
/*     */     
/*  55 */     StatusLabel statusLabel = new StatusLabel();
/*  56 */     this.widgets.add(statusLabel);
/*     */     
/*  58 */     switch ((tournament.getRound()).type) {
/*     */       case UNDEFINED:
/*  60 */         this.groups = (Groups)tournament.getRound();
/*  61 */         this.mode = Mode.GROUPS_DISTRIBUTION;
/*     */         break;
/*     */       
/*     */       case COMPUTER:
/*  65 */         this.knockout = (Knockout)tournament.getRound();
/*  66 */         this.mode = Mode.KNOCKOUT_CALENDAR;
/*     */         break;
/*     */     } 
/*     */     
/*  70 */     this.teamButtons = new ArrayList<>();
/*     */     
/*  72 */     int teamIndex = 0;
/*  73 */     for (Team team : game.teamList) {
/*  74 */       TeamButton teamButton = new TeamButton(team, teamIndex);
/*  75 */       this.teamButtons.add(teamButton);
/*  76 */       this.widgets.add(teamButton);
/*  77 */       teamIndex++;
/*     */     } 
/*     */     
/*  80 */     if (this.teamButtons.size() > 0) {
/*  81 */       Collections.sort(this.teamButtons, Widget.widgetComparatorByText);
/*  82 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, 392, 29, 20, this.teamButtons);
/*  83 */       setSelectedWidget(this.teamButtons.get(0));
/*     */     } 
/*     */     
/*  86 */     HomeTeamLabel homeTeamLabel = new HomeTeamLabel();
/*  87 */     this.widgets.add(homeTeamLabel);
/*     */     
/*  89 */     VersusLabel versusLabel = new VersusLabel();
/*  90 */     this.widgets.add(versusLabel);
/*     */     
/*  92 */     AwayTeamLabel awayTeamLabel = new AwayTeamLabel();
/*  93 */     this.widgets.add(awayTeamLabel);
/*     */     
/*  95 */     BackButton backButton = new BackButton();
/*  96 */     this.widgets.add(backButton);
/*     */     
/*  98 */     AbortButton abortButton = new AbortButton();
/*  99 */     this.widgets.add(abortButton);
/* 100 */     if (getSelectedWidget() == null) {
/* 101 */       setSelectedWidget((Widget)abortButton);
/*     */     }
/*     */     
/* 104 */     PlayButton playButton = new PlayButton();
/* 105 */     this.widgets.add(playButton);
/*     */   }
/*     */   
/*     */   private class StatusLabel
/*     */     extends Button {
/*     */     StatusLabel() {
/* 111 */       Objects.requireNonNull(DiyTournamentCalendar.this.game.gui); setGeometry((1280 - 180) / 2, 80, 180, 36);
/* 112 */       setText("", Font.Align.CENTER, Assets.font14);
/* 113 */       setActive(false);
/*     */     } public void refresh() {
/*     */       int groupIndex;
/*     */       int matches;
/*     */       int match;
/* 118 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case UNDEFINED:
/* 120 */           groupIndex = DiyTournamentCalendar.this.groupsTeams.size() / DiyTournamentCalendar.this.groups.groupNumberOfTeams();
/* 121 */           setText("CREATE GROUP " + (char)(65 + groupIndex));
/*     */           break;
/*     */         
/*     */         case COMPUTER:
/* 125 */           if (DiyTournamentCalendar.this.currentGroup == DiyTournamentCalendar.this.groups.groups.size()) {
/* 126 */             setVisible(false); break;
/*     */           } 
/* 128 */           matches = DiyTournamentCalendar.this.groups.groupNumberOfTeams() * (DiyTournamentCalendar.this.groups.groupNumberOfTeams() - 1) / 2;
/* 129 */           match = ((Group)DiyTournamentCalendar.this.groups.groups.get(DiyTournamentCalendar.this.currentGroup)).calendar.size();
/* 130 */           setText("GROUP " + (char)(65 + DiyTournamentCalendar.this.currentGroup) + " MATCHES: " + match + " / " + matches);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case PLAYER:
/* 136 */           setText("MATCHES: " + DiyTournamentCalendar.this.currentMatch + " / " + (DiyTournamentCalendar.this.knockout.numberOfTeams / 2));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class HomeTeamLabel
/*     */     extends Button {
/*     */     HomeTeamLabel() {
/* 145 */       setGeometry(240, 618, 322, 36);
/* 146 */       setText("", Font.Align.RIGHT, Assets.font14);
/* 147 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 152 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case COMPUTER:
/* 154 */           if (DiyTournamentCalendar.this.currentGroup < DiyTournamentCalendar.this.groups.groups.size() && ((Group)DiyTournamentCalendar.this.groups.groups.get(DiyTournamentCalendar.this.currentGroup)).calendar.size() > 0) {
/* 155 */             Match match = ((Group)DiyTournamentCalendar.this.groups.groups.get(DiyTournamentCalendar.this.currentGroup)).calendar.get((DiyTournamentCalendar.this.matchSide == 0) ? (DiyTournamentCalendar.this.currentMatch - 1) : DiyTournamentCalendar.this.currentMatch);
/* 156 */             setText(((Team)DiyTournamentCalendar.this.game.teamList.get(match.teams[0])).name); break;
/*     */           } 
/* 158 */           setText("");
/*     */           break;
/*     */ 
/*     */         
/*     */         case PLAYER:
/* 163 */           if (DiyTournamentCalendar.this.matches.size() > 0) {
/* 164 */             Match match = DiyTournamentCalendar.this.matches.get((DiyTournamentCalendar.this.matchSide == 0) ? (DiyTournamentCalendar.this.currentMatch - 1) : DiyTournamentCalendar.this.currentMatch);
/* 165 */             setText(((Team)DiyTournamentCalendar.this.game.teamList.get(match.teams[0])).name); break;
/*     */           } 
/* 167 */           setText("");
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class VersusLabel
/*     */     extends Button
/*     */   {
/*     */     VersusLabel() {
/* 177 */       Objects.requireNonNull(DiyTournamentCalendar.this.game.gui); setGeometry(1280 / 2 - 20, 618, 40, 36);
/* 178 */       setText(Assets.gettext("ABBREVIATIONS.VERSUS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 183 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case COMPUTER:
/* 185 */           setVisible((DiyTournamentCalendar.this.currentGroup < DiyTournamentCalendar.this.groups.groups.size() && ((Group)DiyTournamentCalendar.this.groups.groups.get(DiyTournamentCalendar.this.currentGroup)).calendar.size() > 0));
/*     */           break;
/*     */         
/*     */         case PLAYER:
/* 189 */           setVisible((DiyTournamentCalendar.this.matches.size() > 0));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class AwayTeamLabel
/*     */     extends Button {
/*     */     AwayTeamLabel() {
/* 198 */       setGeometry(720, 618, 322, 36);
/* 199 */       setText("", Font.Align.LEFT, Assets.font14);
/* 200 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 205 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case COMPUTER:
/* 207 */           if (DiyTournamentCalendar.this.currentGroup < DiyTournamentCalendar.this.groups.groups.size() && ((Group)DiyTournamentCalendar.this.groups.groups.get(DiyTournamentCalendar.this.currentGroup)).calendar.size() > 0 && DiyTournamentCalendar.this.matchSide == 0) {
/* 208 */             Match match = ((Group)DiyTournamentCalendar.this.groups.groups.get(DiyTournamentCalendar.this.currentGroup)).calendar.get(DiyTournamentCalendar.this.currentMatch - 1);
/* 209 */             setText(((Team)DiyTournamentCalendar.this.game.teamList.get(match.teams[1])).name); break;
/*     */           } 
/* 211 */           setText("");
/*     */           break;
/*     */ 
/*     */         
/*     */         case PLAYER:
/* 216 */           if (DiyTournamentCalendar.this.matches.size() > 0 && DiyTournamentCalendar.this.matchSide == 0) {
/* 217 */             Match match = DiyTournamentCalendar.this.matches.get(DiyTournamentCalendar.this.currentMatch - 1);
/* 218 */             setText(((Team)DiyTournamentCalendar.this.game.teamList.get(match.teams[1])).name); break;
/*     */           } 
/* 220 */           setText("");
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class BackButton
/*     */     extends Button
/*     */   {
/*     */     BackButton() {
/* 230 */       Objects.requireNonNull(DiyTournamentCalendar.this.game.gui); setGeometry((1280 - 180) / 2 - 360 - 20, 660, 360, 36);
/* 231 */       setColor(10120348);
/* 232 */       setText("BACK", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/*     */       boolean b;
/* 238 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case UNDEFINED:
/* 240 */           b = (DiyTournamentCalendar.this.groupsTeams.size() > 0);
/* 241 */           setVisible(b);
/* 242 */           setActive(b);
/*     */           break;
/*     */         
/*     */         case COMPUTER:
/* 246 */           b = (DiyTournamentCalendar.this.currentGroup < DiyTournamentCalendar.this.groups.groups.size() && ((Group)DiyTournamentCalendar.this.groups.groups.get(DiyTournamentCalendar.this.currentGroup)).calendar.size() > 0);
/* 247 */           setVisible(b);
/* 248 */           setActive(b);
/*     */           break;
/*     */         
/*     */         case PLAYER:
/* 252 */           b = (DiyTournamentCalendar.this.matches.size() > 0);
/* 253 */           setVisible(b);
/* 254 */           setActive(b);
/*     */           break;
/*     */       } 
/*     */     }
/*     */     public void onFire1Down() {
/*     */       Match match;
/*     */       int team;
/*     */       Integer teamIndex;
/*     */       Group group;
/* 263 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case UNDEFINED:
/* 265 */           teamIndex = DiyTournamentCalendar.this.groupsTeams.get(DiyTournamentCalendar.this.groupsTeams.size() - 1);
/* 266 */           DiyTournamentCalendar.this.groupsTeams.remove(teamIndex);
/* 267 */           for (Widget w : DiyTournamentCalendar.this.teamButtons) {
/* 268 */             DiyTournamentCalendar.TeamButton teamButton = (DiyTournamentCalendar.TeamButton)w;
/* 269 */             if (teamButton.teamIndex == teamIndex.intValue()) {
/* 270 */               teamButton.done = false;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         
/*     */         case COMPUTER:
/* 277 */           group = DiyTournamentCalendar.this.groups.groups.get(DiyTournamentCalendar.this.currentGroup);
/* 278 */           match = group.calendar.get((DiyTournamentCalendar.this.matchSide == 0) ? (DiyTournamentCalendar.this.currentMatch - 1) : DiyTournamentCalendar.this.currentMatch);
/*     */           
/* 280 */           if (DiyTournamentCalendar.this.matchSide == 0) {
/* 281 */             DiyTournamentCalendar.this.matchSide = 1;
/* 282 */             DiyTournamentCalendar.this.currentMatch--;
/*     */           } else {
/* 284 */             DiyTournamentCalendar.this.matchSide = 0;
/* 285 */             group.calendar.remove(match);
/*     */           } 
/* 287 */           team = match.teams[DiyTournamentCalendar.this.matchSide];
/* 288 */           for (Widget w : DiyTournamentCalendar.this.teamButtons) {
/* 289 */             DiyTournamentCalendar.TeamButton teamButton = (DiyTournamentCalendar.TeamButton)w;
/* 290 */             if (teamButton.teamIndex == team) {
/* 291 */               teamButton.teamMatches--;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */         
/*     */         case PLAYER:
/* 298 */           match = DiyTournamentCalendar.this.matches.get((DiyTournamentCalendar.this.matchSide == 0) ? (DiyTournamentCalendar.this.currentMatch - 1) : DiyTournamentCalendar.this.currentMatch);
/* 299 */           if (DiyTournamentCalendar.this.matchSide == 0) {
/* 300 */             DiyTournamentCalendar.this.matchSide = 1;
/* 301 */             DiyTournamentCalendar.this.currentMatch--;
/*     */           } else {
/* 303 */             DiyTournamentCalendar.this.matchSide = 0;
/* 304 */             DiyTournamentCalendar.this.matches.remove(match);
/*     */           } 
/* 306 */           team = match.teams[DiyTournamentCalendar.this.matchSide];
/* 307 */           for (Widget w : DiyTournamentCalendar.this.teamButtons) {
/* 308 */             DiyTournamentCalendar.TeamButton teamButton = (DiyTournamentCalendar.TeamButton)w;
/* 309 */             if (teamButton.teamIndex == team) {
/* 310 */               teamButton.done = false;
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           break;
/*     */       } 
/* 316 */       DiyTournamentCalendar.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     private Team team;
/*     */     int teamIndex;
/*     */     private int groupIndex;
/*     */     private int teamMatches;
/*     */     boolean done;
/*     */     
/*     */     TeamButton(Team team, int teamIndex) {
/* 329 */       this.team = team;
/* 330 */       this.teamIndex = teamIndex;
/* 331 */       setSize(300, 28);
/* 332 */       setText(team.name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 337 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case UNDEFINED:
/* 339 */           if (this.done) {
/* 340 */             setText(this.team.name + " (" + (char)(65 + this.groupIndex) + ")");
/* 341 */             setActive(false); break;
/*     */           } 
/* 343 */           setText(this.team.name);
/* 344 */           setActive(true);
/*     */           break;
/*     */ 
/*     */         
/*     */         case COMPUTER:
/* 349 */           setText(this.team.name + " " + this.teamMatches);
/* 350 */           setVisible((this.groupIndex == DiyTournamentCalendar.this.currentGroup));
/* 351 */           setActive((this.groupIndex == DiyTournamentCalendar.this.currentGroup));
/*     */           break;
/*     */       } 
/*     */       
/* 355 */       if (this.done) {
/* 356 */         setColor(6710886);
/*     */       } else {
/* 358 */         switch (this.team.controlMode) {
/*     */           case UNDEFINED:
/* 360 */             setColor(9988382);
/*     */             break;
/*     */           
/*     */           case COMPUTER:
/* 364 */             setColor(9969182);
/*     */             break;
/*     */           
/*     */           case PLAYER:
/* 368 */             setColor(200);
/*     */             break;
/*     */           
/*     */           case COACH:
/* 372 */             setColor(39900);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 380 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case UNDEFINED:
/* 382 */           this.groupIndex = DiyTournamentCalendar.this.groupsTeams.size() / DiyTournamentCalendar.this.groups.groupNumberOfTeams();
/* 383 */           DiyTournamentCalendar.this.groupsTeams.add(Integer.valueOf(this.teamIndex));
/* 384 */           this.done = true;
/*     */ 
/*     */           
/* 387 */           if (DiyTournamentCalendar.this.groupsTeams.size() == DiyTournamentCalendar.this.game.teamList.size()) {
/* 388 */             for (Widget w : DiyTournamentCalendar.this.teamButtons) {
/* 389 */               ((TeamButton)w).done = false;
/*     */             }
/* 391 */             DiyTournamentCalendar.this.mode = DiyTournamentCalendar.Mode.GROUP_MATCHES;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case COMPUTER:
/* 396 */           if (DiyTournamentCalendar.this.matchSide == 0) {
/* 397 */             Match match = new Match();
/* 398 */             match.teams[0] = this.teamIndex;
/* 399 */             ((Group)DiyTournamentCalendar.this.groups.groups.get(this.groupIndex)).calendar.add(match);
/* 400 */             DiyTournamentCalendar.this.matchSide = 1;
/*     */           } else {
/* 402 */             Match match = ((Group)DiyTournamentCalendar.this.groups.groups.get(this.groupIndex)).calendar.get(DiyTournamentCalendar.this.currentMatch);
/* 403 */             match.teams[1] = this.teamIndex;
/* 404 */             DiyTournamentCalendar.this.matchSide = 0;
/* 405 */             DiyTournamentCalendar.this.currentMatch += 1;
/*     */           } 
/* 407 */           this.teamMatches++;
/*     */ 
/*     */           
/* 410 */           if (DiyTournamentCalendar.this.currentMatch == DiyTournamentCalendar.this.groups.groupNumberOfTeams() * (DiyTournamentCalendar.this.groups.groupNumberOfTeams() - 1) / 2) {
/* 411 */             DiyTournamentCalendar.this.currentGroup++;
/* 412 */             DiyTournamentCalendar.this.currentMatch = 0;
/*     */           } 
/*     */           break;
/*     */         
/*     */         case PLAYER:
/* 417 */           if (DiyTournamentCalendar.this.matchSide == 0) {
/* 418 */             Match match = new Match();
/* 419 */             match.teams[0] = this.teamIndex;
/* 420 */             DiyTournamentCalendar.this.matches.add(match);
/* 421 */             DiyTournamentCalendar.this.matchSide = 1;
/*     */           } else {
/* 423 */             Match match = DiyTournamentCalendar.this.matches.get(DiyTournamentCalendar.this.currentMatch);
/* 424 */             match.teams[1] = this.teamIndex;
/* 425 */             DiyTournamentCalendar.this.matchSide = 0;
/* 426 */             DiyTournamentCalendar.this.currentMatch += 1;
/*     */           } 
/* 428 */           this.done = true;
/*     */           break;
/*     */       } 
/* 431 */       DiyTournamentCalendar.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 438 */       Objects.requireNonNull(DiyTournamentCalendar.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 439 */       setColor(13107214);
/* 440 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 445 */       DiyTournamentCalendar.this.game.setScreen((Screen)new Main(DiyTournamentCalendar.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayButton
/*     */     extends Button {
/*     */     PlayButton() {
/* 452 */       Objects.requireNonNull(DiyTournamentCalendar.this.game.gui); setGeometry(1280 / 2 + 110, 660, 360, 36);
/* 453 */       setText("PLAY TOURNAMENT", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */     
/*     */     public void refresh() {
/*     */       int teams;
/* 458 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case UNDEFINED:
/* 460 */           setColor(6710886);
/* 461 */           setActive(false);
/*     */           break;
/*     */         
/*     */         case COMPUTER:
/* 465 */           if (DiyTournamentCalendar.this.currentGroup == DiyTournamentCalendar.this.groups.groups.size() && DiyTournamentCalendar.this.currentMatch == 0) {
/* 466 */             setColor(1280801);
/* 467 */             setActive(true); break;
/*     */           } 
/* 469 */           setColor(6710886);
/* 470 */           setActive(false);
/*     */           break;
/*     */ 
/*     */         
/*     */         case PLAYER:
/* 475 */           teams = 0;
/* 476 */           for (Match match : DiyTournamentCalendar.this.matches) {
/* 477 */             if (match.teams[0] != -1) teams++; 
/* 478 */             if (match.teams[1] != -1) teams++; 
/*     */           } 
/* 480 */           if (teams == DiyTournamentCalendar.this.knockout.numberOfTeams) {
/* 481 */             setColor(1280801);
/* 482 */             setActive(true); break;
/*     */           } 
/* 484 */           setColor(6710886);
/* 485 */           setActive(false);
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*     */       int g;
/* 493 */       switch (DiyTournamentCalendar.this.mode) {
/*     */         case COMPUTER:
/* 495 */           for (g = 0; g < DiyTournamentCalendar.this.groups.groups.size(); g++) {
/* 496 */             Group group = DiyTournamentCalendar.this.groups.groups.get(g);
/*     */             
/* 498 */             for (int r = 1; r < DiyTournamentCalendar.this.groups.rounds; r++) {
/* 499 */               for (int i = 0; i < DiyTournamentCalendar.this.groups.groupNumberOfTeams() * (DiyTournamentCalendar.this.groups.groupNumberOfTeams() - 1) / 2; i++) {
/* 500 */                 Match firstRoundMatch = group.calendar.get(i);
/* 501 */                 Match match = new Match();
/* 502 */                 if (r % 2 == 0) {
/* 503 */                   match.teams[0] = firstRoundMatch.teams[0];
/* 504 */                   match.teams[1] = firstRoundMatch.teams[1];
/*     */                 } else {
/* 506 */                   match.teams[0] = firstRoundMatch.teams[1];
/* 507 */                   match.teams[1] = firstRoundMatch.teams[0];
/*     */                 } 
/* 509 */                 group.calendar.add(match);
/*     */               } 
/*     */             } 
/*     */           } 
/* 513 */           DiyTournamentCalendar.this.tournament.start((ArrayList)DiyTournamentCalendar.this.game.teamList);
/* 514 */           DiyTournamentCalendar.this.game.setCompetition((Competition)DiyTournamentCalendar.this.tournament);
/* 515 */           DiyTournamentCalendar.this.game.setScreen((Screen)new PlayTournament(DiyTournamentCalendar.this.game));
/*     */           break;
/*     */         
/*     */         case PLAYER:
/* 519 */           DiyTournamentCalendar.this.knockout.newLeg();
/* 520 */           ((Leg)DiyTournamentCalendar.this.knockout.legs.get(0)).matches = DiyTournamentCalendar.this.matches;
/*     */           
/* 522 */           DiyTournamentCalendar.this.tournament.start((ArrayList)DiyTournamentCalendar.this.game.teamList);
/* 523 */           DiyTournamentCalendar.this.game.setCompetition((Competition)DiyTournamentCalendar.this.tournament);
/* 524 */           DiyTournamentCalendar.this.game.setScreen((Screen)new PlayTournament(DiyTournamentCalendar.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DiyTournamentCalendar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */