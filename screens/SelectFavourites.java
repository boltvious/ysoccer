/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
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
/*     */ class SelectFavourites
/*     */   extends GLScreen
/*     */ {
/*     */   private Widget titleButton;
/*     */   private Widget viewSelectedTeamsButton;
/*     */   private Widget playButton;
/*     */   
/*     */   SelectFavourites(GLGame game) {
/*  41 */     super(game);
/*     */     
/*  43 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  47 */     TitleBar titleBar = new TitleBar();
/*  48 */     this.widgets.add(titleBar);
/*  49 */     this.titleButton = (Widget)titleBar;
/*     */     
/*  51 */     ArrayList<Widget> teamButtons = new ArrayList<>();
/*     */     
/*  53 */     ArrayList<Team> teamList = new ArrayList<>();
/*  54 */     for (String teamPath : Assets.favourites) {
/*  55 */       FileHandle file = Assets.teamsRootFolder.child(teamPath);
/*  56 */       if (file.exists()) {
/*  57 */         Team team = (Team)Assets.json.fromJson(Team.class, file.readString("UTF-8"));
/*  58 */         team.path = Assets.getRelativeTeamPath(file);
/*  59 */         teamList.add(team);
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     ComputerButton computerButton = new ComputerButton();
/*  64 */     this.widgets.add(computerButton);
/*     */     
/*  66 */     ComputerLabel computerLabel = new ComputerLabel();
/*  67 */     this.widgets.add(computerLabel);
/*     */     
/*  69 */     PlayerCoachButton playerCoachButton = new PlayerCoachButton();
/*  70 */     this.widgets.add(playerCoachButton);
/*     */     
/*  72 */     PlayerCoachLabel playerCoachLabel = new PlayerCoachLabel();
/*  73 */     this.widgets.add(playerCoachLabel);
/*     */     
/*  75 */     CoachButton coachButton = new CoachButton();
/*  76 */     this.widgets.add(coachButton);
/*     */     
/*  78 */     CoachLabel coachLabel = new CoachLabel();
/*  79 */     this.widgets.add(coachLabel);
/*     */     
/*  81 */     for (Team team : teamList) {
/*  82 */       TeamButton teamButton; if (game.teamList.contains(team)) {
/*  83 */         teamButton = new TeamButton((Team)game.teamList.get(game.teamList.indexOf(team)));
/*     */       } else {
/*  85 */         teamButton = new TeamButton(team);
/*     */       } 
/*  87 */       teamButtons.add(teamButton);
/*  88 */       this.widgets.add(teamButton);
/*     */     } 
/*     */     
/*  91 */     if (teamButtons.size() > 0) {
/*  92 */       Collections.sort(teamButtons, Widget.widgetComparatorByText);
/*  93 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, 392, 29, 20, teamButtons);
/*  94 */       setSelectedWidget(teamButtons.get(0));
/*     */       
/*  96 */       for (Widget teamButton : teamButtons) {
/*  97 */         FavouriteFolderButton favouriteFolderButton = new FavouriteFolderButton(teamButton);
/*  98 */         this.widgets.add(favouriteFolderButton);
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     PlayButton playButton = new PlayButton();
/* 103 */     this.widgets.add(playButton);
/* 104 */     this.playButton = (Widget)playButton;
/*     */ 
/*     */     
/* 107 */     ArrayList<Widget> breadcrumb = new ArrayList<>();
/*     */     
/* 109 */     BreadCrumbRootButton breadCrumbRootButton = new BreadCrumbRootButton();
/* 110 */     breadcrumb.add(breadCrumbRootButton);
/*     */     
/* 112 */     BreadCrumbFavouritesLabel breadCrumbFavouritesLabel = new BreadCrumbFavouritesLabel();
/* 113 */     breadcrumb.add(breadCrumbFavouritesLabel);
/*     */     
/* 115 */     Objects.requireNonNull(game.gui); int x = (1280 - 960) / 2;
/* 116 */     for (Widget b : breadcrumb) {
/* 117 */       b.setPosition(x, 72);
/* 118 */       x += b.w + 2;
/*     */     } 
/* 120 */     this.widgets.addAll(breadcrumb);
/*     */     
/* 122 */     ViewSelectedTeamsButton viewSelectedTeamsButton = new ViewSelectedTeamsButton();
/* 123 */     this.widgets.add(viewSelectedTeamsButton);
/* 124 */     this.viewSelectedTeamsButton = (Widget)viewSelectedTeamsButton;
/*     */     
/* 126 */     AbortButton abortButton = new AbortButton();
/* 127 */     this.widgets.add(abortButton);
/* 128 */     if (getSelectedWidget() == null)
/* 129 */       setSelectedWidget((Widget)abortButton); 
/*     */   }
/*     */   
/*     */   private class TitleBar
/*     */     extends Button
/*     */   {
/*     */     TitleBar() {
/* 136 */       setColors(SelectFavourites.this.game.stateColor);
/* 137 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 142 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry((1280 - 960) / 2, 30, 960, 40);
/* 143 */       setColors(SelectFavourites.this.game.stateColor);
/* 144 */       int diff = SelectFavourites.navigation.competition.numberOfTeams - SelectFavourites.this.game.teamList.numberOfTeams();
/* 145 */       String title = Assets.gettext((diff == 0) ? "CHANGE TEAMS FOR" : "CHOOSE TEAMS FOR") + " " + SelectFavourites.navigation.competition.name;
/* 146 */       setText(title, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BreadCrumbRootButton
/*     */     extends Button {
/*     */     BreadCrumbRootButton() {
/* 153 */       setSize(0, 32);
/* 154 */       setColors(SelectFavourites.this.game.stateColor);
/* 155 */       setText("\024", Font.Align.CENTER, Assets.font10);
/* 156 */       autoWidth();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 161 */       SelectFavourites.navigation.folder = Assets.teamsRootFolder;
/* 162 */       SelectFavourites.navigation.league = null;
/* 163 */       SelectFavourites.this.game.setScreen((Screen)new SelectFolder(SelectFavourites.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class BreadCrumbFavouritesLabel
/*     */     extends Button {
/*     */     BreadCrumbFavouritesLabel() {
/* 170 */       setSize(0, 32);
/* 171 */       setColors(SelectFavourites.this.game.stateColor.darker());
/* 172 */       setActive(false);
/* 173 */       setText(Assets.gettext("FAVOURITES"), Font.Align.CENTER, Assets.font10);
/* 174 */       autoWidth();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ComputerButton
/*     */     extends Button {
/*     */     ComputerButton() {
/* 181 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry((1280 - 780) / 2 - 20, 112, 60, 26);
/* 182 */       setColors(Integer.valueOf(9969182), Integer.valueOf(13052201), Integer.valueOf(6553600));
/* 183 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ComputerLabel
/*     */     extends Button {
/*     */     ComputerLabel() {
/* 190 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry((1280 - 780) / 2 - 20 + 80, 112, 180, 26);
/* 191 */       setText(Assets.gettext("CONTROL MODE.COMPUTER"), Font.Align.LEFT, Assets.font10);
/* 192 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerCoachButton
/*     */     extends Button {
/*     */     PlayerCoachButton() {
/* 199 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry((1280 - 260) / 2, 112, 60, 26);
/* 200 */       setColors(Integer.valueOf(200), Integer.valueOf(1645055), Integer.valueOf(120));
/* 201 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerCoachLabel
/*     */     extends Button {
/*     */     PlayerCoachLabel() {
/* 208 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry((1280 - 260) / 2 + 80, 112, 180, 26);
/* 209 */       setText(Assets.gettext("CONTROL MODE.PLAYER-COACH"), Font.Align.LEFT, Assets.font10);
/* 210 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CoachButton
/*     */     extends Button {
/*     */     CoachButton() {
/* 217 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry((1280 + 260) / 2 + 20, 112, 60, 26);
/* 218 */       setColors(Integer.valueOf(39900), Integer.valueOf(1686527), Integer.valueOf(29088));
/* 219 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CoachLabel
/*     */     extends Button {
/*     */     CoachLabel() {
/* 226 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry((1280 + 260) / 2 + 20 + 80, 112, 180, 26);
/* 227 */       setText(Assets.gettext("CONTROL MODE.COACH"), Font.Align.LEFT, Assets.font10);
/* 228 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     private Team team;
/*     */     FileHandle folder;
/*     */     
/*     */     TeamButton(Team team) {
/* 238 */       this.team = team;
/* 239 */       setSize(382, 28);
/* 240 */       updateColors();
/* 241 */       FileHandle teamFolder = Assets.teamsRootFolder.child(team.path);
/* 242 */       String mainFolder = Assets.getTeamFirstFolder(teamFolder).name();
/* 243 */       setText(team.name + ", " + mainFolder, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 248 */       if (SelectFavourites.this.game.teamList.contains(this.team)) {
/* 249 */         switch (this.team.controlMode) {
/*     */           case FRIENDLY:
/* 251 */             this.team.controlMode = Team.ControlMode.PLAYER;
/*     */             break;
/*     */           
/*     */           case LEAGUE:
/* 255 */             this.team.controlMode = Team.ControlMode.COACH;
/*     */             break;
/*     */           
/*     */           case CUP:
/* 259 */             this.team.controlMode = Team.ControlMode.UNDEFINED;
/* 260 */             SelectFavourites.this.game.teamList.removeTeam(this.team);
/*     */             break;
/*     */         } 
/*     */       } else {
/* 264 */         this.team.controlMode = Team.ControlMode.COMPUTER;
/* 265 */         SelectFavourites.this.game.teamList.addTeam(this.team);
/*     */       } 
/* 267 */       updateColors();
/* 268 */       SelectFavourites.this.viewSelectedTeamsButton.setDirty(true);
/* 269 */       SelectFavourites.this.playButton.setDirty(true);
/* 270 */       SelectFavourites.this.titleButton.setDirty(true);
/*     */     }
/*     */     
/*     */     private void updateColors() {
/* 274 */       switch (this.team.controlMode) {
/*     */         case TOURNAMENT:
/* 276 */           setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/*     */           break;
/*     */         
/*     */         case FRIENDLY:
/* 280 */           setColors(Integer.valueOf(9969182), Integer.valueOf(13052201), Integer.valueOf(6553600));
/*     */           break;
/*     */         
/*     */         case LEAGUE:
/* 284 */           setColors(Integer.valueOf(200), Integer.valueOf(1645055), Integer.valueOf(120));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 288 */           setColors(Integer.valueOf(39900), Integer.valueOf(1686527), Integer.valueOf(29088));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class FavouriteFolderButton
/*     */     extends Button {
/*     */     private SelectFavourites.TeamButton teamButton;
/*     */     
/*     */     FavouriteFolderButton(Widget teamButton) {
/* 299 */       this.teamButton = (SelectFavourites.TeamButton)teamButton;
/* 300 */       setGeometry(teamButton.x + teamButton.w, teamButton.y, 26, 28);
/* 301 */       setText("\024", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 306 */       SelectFavourites.navigation.folder = Assets.teamsRootFolder.child(this.teamButton.team.path).parent();
/* 307 */       SelectFavourites.navigation.league = this.teamButton.team.league;
/* 308 */       SelectFavourites.this.game.setScreen((Screen)new SelectTeams(SelectFavourites.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewSelectedTeamsButton
/*     */     extends Button {
/*     */     ViewSelectedTeamsButton() {
/* 315 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry((1280 - 180) / 2 - 360 - 20, 660, 360, 36);
/* 316 */       setColors(Integer.valueOf(10120348), Integer.valueOf(12229051), Integer.valueOf(5187919));
/* 317 */       setText(Assets.gettext("VIEW SELECTED TEAMS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 322 */       SelectFavourites.this.game.setScreen((Screen)new AllSelectedTeams(SelectFavourites.this.game));
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 327 */       setVisible((SelectFavourites.this.game.teamList.numberOfTeams() > 0));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 334 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 335 */       setColor(13107214);
/* 336 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 341 */       SelectFavourites.this.game.setScreen((Screen)new Main(SelectFavourites.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayButton
/*     */     extends Button {
/*     */     PlayButton() {
/* 348 */       Objects.requireNonNull(SelectFavourites.this.game.gui); setGeometry(1280 / 2 + 110, 660, 360, 36);
/* 349 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 354 */       int diff = SelectFavourites.navigation.competition.numberOfTeams - SelectFavourites.this.game.teamList.numberOfTeams();
/* 355 */       if (diff == 0) {
/* 356 */         switch (SelectFavourites.navigation.competition.type) {
/*     */           case FRIENDLY:
/* 358 */             setText(Assets.gettext("PLAY FRIENDLY"));
/*     */             break;
/*     */           
/*     */           case LEAGUE:
/* 362 */             setText(Assets.gettext("PLAY LEAGUE"));
/*     */             break;
/*     */           
/*     */           case CUP:
/* 366 */             setText(Assets.gettext("PLAY CUP"));
/*     */             break;
/*     */           
/*     */           case TOURNAMENT:
/* 370 */             setText(Assets.gettext("PLAY TOURNAMENT"));
/*     */             break;
/*     */         } 
/* 373 */         setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 374 */         setActive(true);
/*     */       } else {
/* 376 */         if (diff > 1) {
/* 377 */           setText(Assets.gettext("SELECT %n MORE TEAMS").replace("%n", "" + diff));
/* 378 */         } else if (diff == 1) {
/* 379 */           setText(Assets.gettext("SELECT 1 MORE TEAM"));
/* 380 */         } else if (diff == -1) {
/* 381 */           setText(Assets.gettext("SELECT 1 LESS TEAM"));
/*     */         } else {
/* 383 */           setText(Assets.gettext("SELECT %n LESS TEAMS").replace("%n", "" + -diff));
/*     */         } 
/* 385 */         setColors(null);
/* 386 */         setActive(false);
/*     */       } 
/*     */     }
/*     */     public void onFire1Down() {
/*     */       Team homeTeam, awayTeam;
/*     */       Match match;
/* 392 */       SelectFavourites.this.game.teamList.removeNullValues();
/* 393 */       switch (SelectFavourites.navigation.competition.type) {
/*     */         case FRIENDLY:
/* 395 */           homeTeam = (Team)SelectFavourites.this.game.teamList.get(0);
/* 396 */           awayTeam = (Team)SelectFavourites.this.game.teamList.get(1);
/*     */           
/* 398 */           match = SelectFavourites.navigation.competition.getMatch();
/* 399 */           match.setTeam(0, homeTeam);
/* 400 */           match.setTeam(1, awayTeam);
/*     */ 
/*     */           
/* 403 */           SelectFavourites.this.game.inputDevices.setAvailability(true);
/* 404 */           homeTeam.setInputDevice(null);
/* 405 */           homeTeam.releaseNonAiInputDevices();
/* 406 */           awayTeam.setInputDevice(null);
/* 407 */           awayTeam.releaseNonAiInputDevices();
/*     */ 
/*     */           
/* 410 */           if (homeTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 411 */             if (SelectFavourites.this.lastFireInputDevice != null) {
/* 412 */               homeTeam.setInputDevice(SelectFavourites.this.lastFireInputDevice);
/*     */             }
/* 414 */             SelectFavourites.navigation.team = homeTeam;
/* 415 */             SelectFavourites.this.game.setScreen((Screen)new SetTeam(SelectFavourites.this.game)); break;
/* 416 */           }  if (awayTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 417 */             if (SelectFavourites.this.lastFireInputDevice != null) {
/* 418 */               awayTeam.setInputDevice(SelectFavourites.this.lastFireInputDevice);
/*     */             }
/* 420 */             SelectFavourites.navigation.team = awayTeam;
/* 421 */             SelectFavourites.this.game.setScreen((Screen)new SetTeam(SelectFavourites.this.game)); break;
/*     */           } 
/* 423 */           SelectFavourites.this.game.setScreen((Screen)new MatchSetup(SelectFavourites.this.game));
/*     */           break;
/*     */ 
/*     */         
/*     */         case LEAGUE:
/* 428 */           SelectFavourites.navigation.competition.start((ArrayList)SelectFavourites.this.game.teamList);
/* 429 */           SelectFavourites.this.game.setCompetition(SelectFavourites.navigation.competition);
/* 430 */           SelectFavourites.this.game.setScreen((Screen)new PlayLeague(SelectFavourites.this.game));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 434 */           SelectFavourites.navigation.competition.start((ArrayList)SelectFavourites.this.game.teamList);
/* 435 */           SelectFavourites.this.game.setCompetition(SelectFavourites.navigation.competition);
/* 436 */           SelectFavourites.this.game.setScreen((Screen)new PlayCup(SelectFavourites.this.game));
/*     */           break;
/*     */         
/*     */         case TOURNAMENT:
/* 440 */           SelectFavourites.navigation.competition.start((ArrayList)SelectFavourites.this.game.teamList);
/* 441 */           SelectFavourites.this.game.setCompetition(SelectFavourites.navigation.competition);
/* 442 */           SelectFavourites.this.game.setScreen((Screen)new PlayTournament(SelectFavourites.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SelectFavourites.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */