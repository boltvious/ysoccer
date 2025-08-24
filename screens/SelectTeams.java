/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.Cup;
/*     */ import com.ygames.ysoccer.competitions.League;
/*     */ import com.ygames.ysoccer.competitions.tournament.Tournament;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ class SelectTeams
/*     */   extends GLScreen
/*     */ {
/*     */   private Widget titleButton;
/*     */   private Widget viewSelectedTeamsButton;
/*     */   private Widget playButton;
/*     */   private Widget calendarButton;
/*     */   
/*     */   SelectTeams(GLGame game) {
/*  34 */     super(game);
/*     */     boolean isDataRoot;
/*  36 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  40 */     TitleBar titleBar = new TitleBar();
/*  41 */     this.widgets.add(titleBar);
/*  42 */     this.titleButton = (Widget)titleBar;
/*     */     
/*  44 */     List<Widget> list = new ArrayList<>();
/*     */     
/*  46 */     List<String> leagues = new ArrayList<>();
/*  47 */     boolean singleLeague = false;
/*  48 */     FileHandle leaguesFile = navigation.folder.child("leagues.json");
/*  49 */     if (leaguesFile.exists()) {
/*  50 */       leagues = (List<String>)Assets.json.fromJson(ArrayList.class, String.class, leaguesFile.readString("UTF-8"));
/*  51 */       if (leagues.size() == 1) {
/*  52 */         singleLeague = true;
/*     */       }
/*  54 */       if (navigation.league == null) {
/*  55 */         if (singleLeague) {
/*  56 */           navigation.league = leagues.get(0);
/*     */         }
/*     */       } else {
/*  59 */         leagues.clear();
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     ArrayList<Team> teamList = new ArrayList<>();
/*  64 */     FileHandle[] teamFileHandles = navigation.folder.list(Assets.teamFilenameFilter);
/*  65 */     for (FileHandle teamFileHandle : teamFileHandles) {
/*  66 */       Team team = (Team)Assets.json.fromJson(Team.class, teamFileHandle.readString("UTF-8"));
/*  67 */       team.path = Assets.getRelativeTeamPath(teamFileHandle);
/*  68 */       if (navigation.league == null || (team.type == Team.Type.CLUB && team.league.equals(navigation.league))) {
/*  69 */         teamList.add(team);
/*  70 */         if (team.type == Team.Type.CLUB && !leagues.contains(team.league)) {
/*  71 */           leagues.add(team.league);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  77 */     if (leagues.size() > 1) {
/*  78 */       for (String name : leagues) {
/*  79 */         LeagueButton leagueButton = new LeagueButton(name);
/*  80 */         list.add(leagueButton);
/*  81 */         this.widgets.add(leagueButton);
/*     */       } 
/*  83 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, 392, 34, 20, list);
/*  84 */       setSelectedWidget(list.get(0));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  89 */       ComputerButton computerButton = new ComputerButton();
/*  90 */       this.widgets.add(computerButton);
/*     */       
/*  92 */       ComputerLabel computerLabel = new ComputerLabel();
/*  93 */       this.widgets.add(computerLabel);
/*     */       
/*  95 */       PlayerCoachButton playerCoachButton = new PlayerCoachButton();
/*  96 */       this.widgets.add(playerCoachButton);
/*     */       
/*  98 */       PlayerCoachLabel playerCoachLabel = new PlayerCoachLabel();
/*  99 */       this.widgets.add(playerCoachLabel);
/*     */       
/* 101 */       CoachButton coachButton = new CoachButton();
/* 102 */       this.widgets.add(coachButton);
/*     */       
/* 104 */       CoachLabel coachLabel = new CoachLabel();
/* 105 */       this.widgets.add(coachLabel);
/*     */       
/* 107 */       for (Team team : teamList) {
/* 108 */         TeamButton teamButton; if (game.teamList.contains(team)) {
/* 109 */           teamButton = new TeamButton((Team)game.teamList.get(game.teamList.indexOf(team)));
/*     */         } else {
/* 111 */           teamButton = new TeamButton(team);
/*     */         } 
/* 113 */         list.add(teamButton);
/* 114 */         this.widgets.add(teamButton);
/*     */       } 
/*     */       
/* 117 */       if (list.size() > 0) {
/* 118 */         Collections.sort(list, Widget.widgetComparatorByText);
/* 119 */         Objects.requireNonNull(game.gui); Widget.arrange(1280, 392, 29, 20, list);
/* 120 */         setSelectedWidget(list.get(0));
/*     */       } 
/*     */       
/* 123 */       CalendarButton calendarButton = new CalendarButton();
/* 124 */       this.widgets.add(calendarButton);
/* 125 */       this.calendarButton = (Widget)calendarButton;
/*     */     } 
/*     */ 
/*     */     
/* 129 */     List<Widget> breadcrumb = new ArrayList<>();
/* 130 */     if (navigation.league != null) {
/* 131 */       BreadCrumbLeagueLabel breadCrumbLeagueLabel = new BreadCrumbLeagueLabel();
/* 132 */       breadcrumb.add(breadCrumbLeagueLabel);
/*     */     } 
/* 134 */     FileHandle fh = navigation.folder;
/*     */     
/*     */     do {
/* 137 */       isDataRoot = fh.equals(Assets.teamsRootFolder);
/* 138 */       boolean disabled = (fh == navigation.folder && (navigation.league == null || singleLeague));
/* 139 */       BreadCrumbButton breadCrumbButton = new BreadCrumbButton(fh, isDataRoot, disabled);
/* 140 */       breadcrumb.add(breadCrumbButton);
/* 141 */       fh = fh.parent();
/* 142 */     } while (!isDataRoot);
/*     */     
/* 144 */     Collections.reverse(breadcrumb);
/* 145 */     Objects.requireNonNull(game.gui); int x = (1280 - 960) / 2;
/* 146 */     for (Widget b : breadcrumb) {
/* 147 */       b.setPosition(x, 72);
/* 148 */       x += b.w + 2;
/*     */     } 
/* 150 */     this.widgets.addAll(breadcrumb);
/*     */     
/* 152 */     ViewSelectedTeamsButton viewSelectedTeamsButton = new ViewSelectedTeamsButton();
/* 153 */     this.widgets.add(viewSelectedTeamsButton);
/* 154 */     this.viewSelectedTeamsButton = (Widget)viewSelectedTeamsButton;
/*     */     
/* 156 */     AbortButton abortButton = new AbortButton();
/* 157 */     this.widgets.add(abortButton);
/* 158 */     if (getSelectedWidget() == null) {
/* 159 */       setSelectedWidget((Widget)abortButton);
/*     */     }
/*     */     
/* 162 */     PlayButton playButton = new PlayButton();
/* 163 */     this.widgets.add(playButton);
/* 164 */     this.playButton = (Widget)playButton;
/*     */   }
/*     */   
/*     */   private class TitleBar
/*     */     extends Button {
/*     */     TitleBar() {
/* 170 */       setColors(SelectTeams.this.game.stateColor);
/* 171 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 176 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry((1280 - 960) / 2, 30, 960, 40);
/* 177 */       setColors(SelectTeams.this.game.stateColor);
/* 178 */       int diff = SelectTeams.navigation.competition.numberOfTeams - SelectTeams.this.game.teamList.numberOfTeams();
/* 179 */       String title = Assets.strings.get((diff == 0) ? "CHANGE TEAMS FOR" : "CHOOSE TEAMS FOR") + " " + SelectTeams.navigation.competition.name;
/* 180 */       setText(title, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BreadCrumbLeagueLabel
/*     */     extends Button {
/*     */     BreadCrumbLeagueLabel() {
/* 187 */       setSize(0, 32);
/* 188 */       setColors(SelectTeams.this.game.stateColor.darker());
/* 189 */       setActive(false);
/* 190 */       setText(SelectTeams.navigation.league, Font.Align.CENTER, Assets.font10);
/* 191 */       autoWidth();
/*     */     }
/*     */   }
/*     */   
/*     */   private class BreadCrumbButton
/*     */     extends Button {
/*     */     private FileHandle fh;
/*     */     
/*     */     BreadCrumbButton(FileHandle folder, boolean isDataRoot, boolean disabled) {
/* 200 */       this.fh = folder;
/* 201 */       setSize(0, 32);
/* 202 */       if (disabled) {
/* 203 */         setColors(SelectTeams.this.game.stateColor.darker());
/* 204 */         setActive(false);
/*     */       } else {
/* 206 */         setColors(SelectTeams.this.game.stateColor);
/*     */       } 
/* 208 */       setText(isDataRoot ? "\024" : this.fh.name().replace('_', ' '), Font.Align.CENTER, Assets.font10);
/* 209 */       autoWidth();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 214 */       if (this.fh == SelectTeams.navigation.folder && SelectTeams.navigation.league != null) {
/* 215 */         SelectTeams.navigation.folder = this.fh;
/* 216 */         SelectTeams.navigation.league = null;
/* 217 */         SelectTeams.this.game.setScreen((Screen)new SelectTeams(SelectTeams.this.game));
/*     */       } else {
/* 219 */         SelectTeams.navigation.folder = this.fh;
/* 220 */         SelectTeams.navigation.league = null;
/* 221 */         SelectTeams.this.game.setScreen((Screen)new SelectFolder(SelectTeams.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class LeagueButton
/*     */     extends Button {
/*     */     LeagueButton(String name) {
/* 229 */       setSize(300, 32);
/* 230 */       setColor(1789317);
/* 231 */       setText(name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 236 */       SelectTeams.navigation.league = this.text;
/* 237 */       SelectTeams.this.game.setScreen((Screen)new SelectTeams(SelectTeams.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ComputerButton
/*     */     extends Button {
/*     */     ComputerButton() {
/* 244 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry((1280 - 780) / 2 - 20, 112, 60, 26);
/* 245 */       setColors(Integer.valueOf(9969182), Integer.valueOf(13052201), Integer.valueOf(6553600));
/* 246 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ComputerLabel
/*     */     extends Button {
/*     */     ComputerLabel() {
/* 253 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry((1280 - 780) / 2 - 20 + 80, 112, 180, 26);
/* 254 */       setText(Assets.strings.get("CONTROL MODE.COMPUTER"), Font.Align.LEFT, Assets.font10);
/* 255 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerCoachButton
/*     */     extends Button {
/*     */     PlayerCoachButton() {
/* 262 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry((1280 - 260) / 2, 112, 60, 26);
/* 263 */       setColors(Integer.valueOf(200), Integer.valueOf(1645055), Integer.valueOf(120));
/* 264 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerCoachLabel
/*     */     extends Button {
/*     */     PlayerCoachLabel() {
/* 271 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry((1280 - 260) / 2 + 80, 112, 180, 26);
/* 272 */       setText(Assets.strings.get("CONTROL MODE.PLAYER-COACH"), Font.Align.LEFT, Assets.font10);
/* 273 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CoachButton
/*     */     extends Button {
/*     */     CoachButton() {
/* 280 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry((1280 + 260) / 2 + 20, 112, 60, 26);
/* 281 */       setColors(Integer.valueOf(39900), Integer.valueOf(1686527), Integer.valueOf(29088));
/* 282 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CoachLabel
/*     */     extends Button {
/*     */     CoachLabel() {
/* 289 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry((1280 + 260) / 2 + 20 + 80, 112, 180, 26);
/* 290 */       setText(Assets.strings.get("CONTROL MODE.COACH"), Font.Align.LEFT, Assets.font10);
/* 291 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     private Team team;
/*     */     
/*     */     TeamButton(Team team) {
/* 300 */       this.team = team;
/* 301 */       setSize(300, 28);
/* 302 */       updateColors();
/* 303 */       setText(team.name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 308 */       if (SelectTeams.this.game.teamList.contains(this.team)) {
/* 309 */         switch (this.team.controlMode) {
/*     */           case FRIENDLY:
/* 311 */             this.team.controlMode = Team.ControlMode.PLAYER;
/*     */             break;
/*     */           
/*     */           case LEAGUE:
/* 315 */             this.team.controlMode = Team.ControlMode.COACH;
/*     */             break;
/*     */           
/*     */           case CUP:
/* 319 */             this.team.controlMode = Team.ControlMode.UNDEFINED;
/* 320 */             SelectTeams.this.game.teamList.removeTeam(this.team);
/*     */             break;
/*     */         } 
/*     */       } else {
/* 324 */         this.team.controlMode = Team.ControlMode.COMPUTER;
/* 325 */         SelectTeams.this.game.teamList.addTeam(this.team);
/*     */       } 
/* 327 */       updateColors();
/* 328 */       SelectTeams.this.viewSelectedTeamsButton.setDirty(true);
/* 329 */       SelectTeams.this.playButton.setDirty(true);
/* 330 */       SelectTeams.this.calendarButton.setDirty(true);
/* 331 */       SelectTeams.this.titleButton.setDirty(true);
/*     */     }
/*     */     
/*     */     private void updateColors() {
/* 335 */       switch (this.team.controlMode) {
/*     */         case TOURNAMENT:
/* 337 */           setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/*     */           break;
/*     */         
/*     */         case FRIENDLY:
/* 341 */           setColors(Integer.valueOf(9969182), Integer.valueOf(13052201), Integer.valueOf(6553600));
/*     */           break;
/*     */         
/*     */         case LEAGUE:
/* 345 */           setColors(Integer.valueOf(200), Integer.valueOf(1645055), Integer.valueOf(120));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 349 */           setColors(Integer.valueOf(39900), Integer.valueOf(1686527), Integer.valueOf(29088));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewSelectedTeamsButton
/*     */     extends Button {
/*     */     ViewSelectedTeamsButton() {
/* 358 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry((1280 - 180) / 2 - 360 - 20, 660, 360, 36);
/* 359 */       setColors(Integer.valueOf(10120348), Integer.valueOf(12229051), Integer.valueOf(5187919));
/* 360 */       setText(Assets.strings.get("VIEW SELECTED TEAMS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 365 */       SelectTeams.this.game.setScreen((Screen)new AllSelectedTeams(SelectTeams.this.game));
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 370 */       setVisible((SelectTeams.this.game.teamList.numberOfTeams() > 0));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 377 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 378 */       setColor(13107214);
/* 379 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 384 */       SelectTeams.this.game.setScreen((Screen)new Main(SelectTeams.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayButton
/*     */     extends Button {
/*     */     PlayButton() {
/* 391 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry(1280 / 2 + 110, 660, 360, 36);
/* 392 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 397 */       setVisible((SelectTeams.this.game.teamList.numberOfTeams() > 0));
/* 398 */       int diff = SelectTeams.navigation.competition.numberOfTeams - SelectTeams.this.game.teamList.numberOfTeams();
/* 399 */       if (diff == 0) {
/* 400 */         switch (SelectTeams.navigation.competition.type) {
/*     */           case FRIENDLY:
/* 402 */             setText(Assets.strings.get("PLAY FRIENDLY"));
/*     */             break;
/*     */           
/*     */           case LEAGUE:
/* 406 */             setText(Assets.strings.get("PLAY LEAGUE"));
/*     */             break;
/*     */           
/*     */           case CUP:
/* 410 */             setText(Assets.strings.get("PLAY CUP"));
/*     */             break;
/*     */           
/*     */           case TOURNAMENT:
/* 414 */             setText(Assets.strings.get("PLAY TOURNAMENT"));
/*     */             break;
/*     */         } 
/* 417 */         setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 418 */         setActive(true);
/*     */       } else {
/* 420 */         if (diff > 1) {
/* 421 */           setText(Assets.strings.get("SELECT %n MORE TEAMS").replace("%n", "" + diff));
/* 422 */         } else if (diff == 1) {
/* 423 */           setText(Assets.strings.get("SELECT 1 MORE TEAM"));
/* 424 */         } else if (diff == -1) {
/* 425 */           setText(Assets.strings.get("SELECT 1 LESS TEAM"));
/*     */         } else {
/* 427 */           setText(Assets.strings.get("SELECT %n LESS TEAMS").replace("%n", "" + -diff));
/*     */         } 
/* 429 */         setColors(null);
/* 430 */         setActive(false);
/*     */       } 
/*     */     }
/*     */     public void onFire1Down() {
/*     */       Team homeTeam, awayTeam;
/*     */       Match match;
/* 436 */       SelectTeams.this.game.teamList.removeNullValues();
/* 437 */       switch (SelectTeams.navigation.competition.type) {
/*     */         case FRIENDLY:
/* 439 */           homeTeam = (Team)SelectTeams.this.game.teamList.get(0);
/* 440 */           awayTeam = (Team)SelectTeams.this.game.teamList.get(1);
/*     */           
/* 442 */           match = SelectTeams.navigation.competition.getMatch();
/* 443 */           match.setTeam(0, homeTeam);
/* 444 */           match.setTeam(1, awayTeam);
/*     */ 
/*     */           
/* 447 */           SelectTeams.this.game.inputDevices.setAvailability(true);
/* 448 */           homeTeam.setInputDevice(null);
/* 449 */           homeTeam.releaseNonAiInputDevices();
/* 450 */           awayTeam.setInputDevice(null);
/* 451 */           awayTeam.releaseNonAiInputDevices();
/*     */ 
/*     */           
/* 454 */           if (homeTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 455 */             if (SelectTeams.this.lastFireInputDevice != null) {
/* 456 */               homeTeam.setInputDevice(SelectTeams.this.lastFireInputDevice);
/*     */             }
/* 458 */             SelectTeams.navigation.team = homeTeam;
/* 459 */             SelectTeams.this.game.setScreen((Screen)new SetTeam(SelectTeams.this.game)); break;
/* 460 */           }  if (awayTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 461 */             if (SelectTeams.this.lastFireInputDevice != null) {
/* 462 */               awayTeam.setInputDevice(SelectTeams.this.lastFireInputDevice);
/*     */             }
/* 464 */             SelectTeams.navigation.team = awayTeam;
/* 465 */             SelectTeams.this.game.setScreen((Screen)new SetTeam(SelectTeams.this.game)); break;
/*     */           } 
/* 467 */           SelectTeams.this.game.setScreen((Screen)new MatchSetup(SelectTeams.this.game));
/*     */           break;
/*     */ 
/*     */         
/*     */         case LEAGUE:
/* 472 */           SelectTeams.navigation.competition.start((ArrayList)SelectTeams.this.game.teamList);
/* 473 */           SelectTeams.this.game.setCompetition(SelectTeams.navigation.competition);
/* 474 */           SelectTeams.this.game.setScreen((Screen)new PlayLeague(SelectTeams.this.game));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 478 */           SelectTeams.navigation.competition.start((ArrayList)SelectTeams.this.game.teamList);
/* 479 */           SelectTeams.this.game.setCompetition(SelectTeams.navigation.competition);
/* 480 */           SelectTeams.this.game.setScreen((Screen)new PlayCup(SelectTeams.this.game));
/*     */           break;
/*     */         
/*     */         case TOURNAMENT:
/* 484 */           SelectTeams.navigation.competition.start((ArrayList)SelectTeams.this.game.teamList);
/* 485 */           SelectTeams.this.game.setCompetition(SelectTeams.navigation.competition);
/* 486 */           SelectTeams.this.game.setScreen((Screen)new PlayTournament(SelectTeams.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class CalendarButton
/*     */     extends Button {
/*     */     CalendarButton() {
/* 495 */       Objects.requireNonNull(SelectTeams.this.game.gui); setGeometry(1280 / 2 + 490, 660, 40, 36);
/* 496 */       setText("\025", Font.Align.CENTER, Assets.font14);
/* 497 */       setColor(1280801);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 502 */       setVisible((Settings.development && 
/* 503 */           SelectTeams.navigation.competition.type != Competition.Type.FRIENDLY && 
/* 504 */           SelectTeams.navigation.competition.category == Competition.Category.DIY_COMPETITION && 
/* 505 */           SelectTeams.navigation.competition.numberOfTeams == SelectTeams.this.game.teamList.numberOfTeams()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 510 */       SelectTeams.this.game.teamList.removeNullValues();
/* 511 */       switch (SelectTeams.navigation.competition.type) {
/*     */         case CUP:
/* 513 */           SelectTeams.this.game.setScreen((Screen)new DiyCupCalendar(SelectTeams.this.game, (Cup)SelectTeams.navigation.competition));
/*     */           break;
/*     */         case LEAGUE:
/* 516 */           SelectTeams.this.game.setScreen((Screen)new DiyLeagueCalendar(SelectTeams.this.game, (League)SelectTeams.navigation.competition));
/*     */           break;
/*     */         case TOURNAMENT:
/* 519 */           SelectTeams.this.game.setScreen((Screen)new DiyTournamentCalendar(SelectTeams.this.game, (Tournament)SelectTeams.navigation.competition));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SelectTeams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */