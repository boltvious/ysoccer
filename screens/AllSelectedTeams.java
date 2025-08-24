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
/*     */ import java.util.List;
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
/*     */ class AllSelectedTeams
/*     */   extends GLScreen
/*     */ {
/*     */   private Widget playButton;
/*     */   private Widget changeTeamsButton;
/*     */   
/*     */   AllSelectedTeams(GLGame game) {
/*  35 */     super(game);
/*     */     
/*  37 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  41 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("ALL SELECTED TEAMS FOR") + " " + navigation.competition.name, game.stateColor.body.intValue());
/*  42 */     this.widgets.add(titleBar);
/*     */     
/*  44 */     ComputerButton computerButton = new ComputerButton();
/*  45 */     this.widgets.add(computerButton);
/*     */     
/*  47 */     ComputerLabel computerLabel = new ComputerLabel();
/*  48 */     this.widgets.add(computerLabel);
/*     */     
/*  50 */     PlayerCoachButton playerCoachButton = new PlayerCoachButton();
/*  51 */     this.widgets.add(playerCoachButton);
/*     */     
/*  53 */     PlayerCoachLabel playerCoachLabel = new PlayerCoachLabel();
/*  54 */     this.widgets.add(playerCoachLabel);
/*     */     
/*  56 */     CoachButton coachButton = new CoachButton();
/*  57 */     this.widgets.add(coachButton);
/*     */     
/*  59 */     CoachLabel coachLabel = new CoachLabel();
/*  60 */     this.widgets.add(coachLabel);
/*     */     
/*  62 */     List<Widget> list = new ArrayList<>();
/*  63 */     for (Team team : game.teamList) {
/*  64 */       if (team == null)
/*  65 */         continue;  TeamButton teamButton = new TeamButton(team);
/*  66 */       list.add(teamButton);
/*  67 */       this.widgets.add(teamButton);
/*     */     } 
/*  69 */     Collections.sort(list, Widget.widgetComparatorByText);
/*  70 */     Objects.requireNonNull(game.gui); Widget.arrange(1280, 392, 29, 20, list);
/*     */     
/*  72 */     ChangeTeamsButton changeTeamsButton = new ChangeTeamsButton();
/*  73 */     this.widgets.add(changeTeamsButton);
/*  74 */     this.changeTeamsButton = (Widget)changeTeamsButton;
/*  75 */     setSelectedWidget((Widget)changeTeamsButton);
/*     */     
/*  77 */     AbortButton abortButton = new AbortButton();
/*  78 */     this.widgets.add(abortButton);
/*     */     
/*  80 */     PlayButton playButton = new PlayButton();
/*  81 */     this.widgets.add(playButton);
/*  82 */     this.playButton = (Widget)playButton;
/*  83 */     int diff = navigation.competition.numberOfTeams - game.teamList.numberOfTeams();
/*  84 */     if (diff == 0)
/*  85 */       setSelectedWidget((Widget)playButton); 
/*     */   }
/*     */   
/*     */   private class ComputerButton
/*     */     extends Button
/*     */   {
/*     */     ComputerButton() {
/*  92 */       Objects.requireNonNull(AllSelectedTeams.this.game.gui); setGeometry((1280 - 780) / 2 - 20, 112, 60, 26);
/*  93 */       setColors(Integer.valueOf(9969182), Integer.valueOf(13052201), Integer.valueOf(6553600));
/*  94 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ComputerLabel
/*     */     extends Button {
/*     */     ComputerLabel() {
/* 101 */       Objects.requireNonNull(AllSelectedTeams.this.game.gui); setGeometry((1280 - 780) / 2 - 20 + 80, 112, 180, 26);
/* 102 */       setText(Assets.gettext("CONTROL MODE.COMPUTER"), Font.Align.LEFT, Assets.font10);
/* 103 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerCoachButton
/*     */     extends Button {
/*     */     PlayerCoachButton() {
/* 110 */       Objects.requireNonNull(AllSelectedTeams.this.game.gui); setGeometry((1280 - 260) / 2, 112, 60, 26);
/* 111 */       setColors(Integer.valueOf(200), Integer.valueOf(1645055), Integer.valueOf(120));
/* 112 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerCoachLabel
/*     */     extends Button {
/*     */     PlayerCoachLabel() {
/* 119 */       Objects.requireNonNull(AllSelectedTeams.this.game.gui); setGeometry((1280 - 260) / 2 + 80, 112, 180, 26);
/* 120 */       setText(Assets.gettext("CONTROL MODE.PLAYER-COACH"), Font.Align.LEFT, Assets.font10);
/* 121 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CoachButton
/*     */     extends Button {
/*     */     CoachButton() {
/* 128 */       Objects.requireNonNull(AllSelectedTeams.this.game.gui); setGeometry((1280 + 260) / 2 + 20, 112, 60, 26);
/* 129 */       setColors(Integer.valueOf(39900), Integer.valueOf(1686527), Integer.valueOf(29088));
/* 130 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CoachLabel
/*     */     extends Button {
/*     */     CoachLabel() {
/* 137 */       Objects.requireNonNull(AllSelectedTeams.this.game.gui); setGeometry((1280 + 260) / 2 + 20 + 80, 112, 180, 26);
/* 138 */       setText(Assets.gettext("CONTROL MODE.COACH"), Font.Align.LEFT, Assets.font10);
/* 139 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     private Team team;
/*     */     
/*     */     TeamButton(Team team) {
/* 148 */       this.team = team;
/* 149 */       setSize(300, 28);
/* 150 */       updateColors();
/* 151 */       setText(team.name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 156 */       if (AllSelectedTeams.this.game.teamList.contains(this.team)) {
/* 157 */         switch (this.team.controlMode) {
/*     */           case FRIENDLY:
/* 159 */             this.team.controlMode = Team.ControlMode.PLAYER;
/*     */             break;
/*     */           
/*     */           case LEAGUE:
/* 163 */             this.team.controlMode = Team.ControlMode.COACH;
/*     */             break;
/*     */           
/*     */           case CUP:
/* 167 */             this.team.controlMode = Team.ControlMode.UNDEFINED;
/* 168 */             AllSelectedTeams.this.game.teamList.removeTeam(this.team);
/*     */             break;
/*     */         } 
/*     */       } else {
/* 172 */         this.team.controlMode = Team.ControlMode.COMPUTER;
/* 173 */         AllSelectedTeams.this.game.teamList.addTeam(this.team);
/*     */       } 
/* 175 */       updateColors();
/* 176 */       AllSelectedTeams.this.playButton.setDirty(true);
/* 177 */       AllSelectedTeams.this.changeTeamsButton.setDirty(true);
/*     */     }
/*     */     
/*     */     private void updateColors() {
/* 181 */       switch (this.team.controlMode) {
/*     */         case TOURNAMENT:
/* 183 */           setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/*     */           break;
/*     */         
/*     */         case FRIENDLY:
/* 187 */           setColors(Integer.valueOf(9969182), Integer.valueOf(13052201), Integer.valueOf(6553600));
/*     */           break;
/*     */         
/*     */         case LEAGUE:
/* 191 */           setColors(Integer.valueOf(200), Integer.valueOf(1645055), Integer.valueOf(120));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 195 */           setColors(Integer.valueOf(39900), Integer.valueOf(1686527), Integer.valueOf(29088));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ChangeTeamsButton
/*     */     extends Button {
/*     */     ChangeTeamsButton() {
/* 204 */       Objects.requireNonNull(AllSelectedTeams.this.game.gui); setGeometry((1280 - 180) / 2 - 360 - 20, 660, 360, 36);
/* 205 */       setColors(Integer.valueOf(10120348), Integer.valueOf(12229051), Integer.valueOf(5187919));
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 210 */       int diff = AllSelectedTeams.navigation.competition.numberOfTeams - AllSelectedTeams.this.game.teamList.numberOfTeams();
/* 211 */       setText(Assets.gettext((diff == 0) ? "CHANGE TEAMS" : "CHOOSE TEAMS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 216 */       if (AllSelectedTeams.navigation.folder.equals(Assets.favouritesFile)) {
/* 217 */         AllSelectedTeams.this.game.setScreen((Screen)new SelectFavourites(AllSelectedTeams.this.game));
/*     */       } else {
/* 219 */         FileHandle[] teamFileHandles = AllSelectedTeams.navigation.folder.list(Assets.teamFilenameFilter);
/* 220 */         if (teamFileHandles.length > 0) {
/* 221 */           AllSelectedTeams.this.game.setScreen((Screen)new SelectTeams(AllSelectedTeams.this.game));
/*     */         } else {
/* 223 */           AllSelectedTeams.this.game.setScreen((Screen)new SelectFolder(AllSelectedTeams.this.game));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 232 */       Objects.requireNonNull(AllSelectedTeams.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 233 */       setColor(13107214);
/* 234 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 239 */       AllSelectedTeams.this.game.setScreen((Screen)new Main(AllSelectedTeams.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayButton
/*     */     extends Button {
/*     */     PlayButton() {
/* 246 */       Objects.requireNonNull(AllSelectedTeams.this.game.gui); setGeometry(1280 / 2 + 110, 660, 360, 36);
/* 247 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 252 */       int diff = AllSelectedTeams.navigation.competition.numberOfTeams - AllSelectedTeams.this.game.teamList.numberOfTeams();
/* 253 */       if (diff == 0) {
/* 254 */         switch (AllSelectedTeams.navigation.competition.type) {
/*     */           case FRIENDLY:
/* 256 */             setText(Assets.gettext("PLAY FRIENDLY"));
/*     */             break;
/*     */           
/*     */           case LEAGUE:
/* 260 */             setText(Assets.gettext("PLAY LEAGUE"));
/*     */             break;
/*     */           
/*     */           case CUP:
/* 264 */             setText(Assets.gettext("PLAY CUP"));
/*     */             break;
/*     */           
/*     */           case TOURNAMENT:
/* 268 */             setText(Assets.gettext("PLAY TOURNAMENT"));
/*     */             break;
/*     */         } 
/* 271 */         setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 272 */         setActive(true);
/*     */       } else {
/* 274 */         if (diff > 1) {
/* 275 */           setText(Assets.gettext("SELECT %n MORE TEAMS").replace("%n", "" + diff));
/* 276 */         } else if (diff == 1) {
/* 277 */           setText(Assets.gettext("SELECT 1 MORE TEAM"));
/* 278 */         } else if (diff == -1) {
/* 279 */           setText(Assets.gettext("SELECT 1 LESS TEAM"));
/*     */         } else {
/* 281 */           setText(Assets.gettext("SELECT %n LESS TEAMS").replace("%n", "" + -diff));
/*     */         } 
/* 283 */         setColors(null);
/* 284 */         setActive(false);
/*     */       } 
/*     */     }
/*     */     public void onFire1Down() {
/*     */       Team homeTeam, awayTeam;
/*     */       Match match;
/* 290 */       AllSelectedTeams.this.game.teamList.removeNullValues();
/* 291 */       switch (AllSelectedTeams.navigation.competition.type) {
/*     */         case FRIENDLY:
/* 293 */           homeTeam = (Team)AllSelectedTeams.this.game.teamList.get(0);
/* 294 */           awayTeam = (Team)AllSelectedTeams.this.game.teamList.get(1);
/*     */           
/* 296 */           match = AllSelectedTeams.navigation.competition.getMatch();
/* 297 */           match.setTeam(0, homeTeam);
/* 298 */           match.setTeam(1, awayTeam);
/*     */ 
/*     */           
/* 301 */           AllSelectedTeams.this.game.inputDevices.setAvailability(true);
/* 302 */           homeTeam.setInputDevice(null);
/* 303 */           homeTeam.releaseNonAiInputDevices();
/* 304 */           awayTeam.setInputDevice(null);
/* 305 */           awayTeam.releaseNonAiInputDevices();
/*     */ 
/*     */           
/* 308 */           if (homeTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 309 */             if (AllSelectedTeams.this.lastFireInputDevice != null) {
/* 310 */               homeTeam.setInputDevice(AllSelectedTeams.this.lastFireInputDevice);
/*     */             }
/* 312 */             AllSelectedTeams.navigation.team = homeTeam;
/* 313 */             AllSelectedTeams.this.game.setScreen((Screen)new SetTeam(AllSelectedTeams.this.game)); break;
/* 314 */           }  if (awayTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 315 */             if (AllSelectedTeams.this.lastFireInputDevice != null) {
/* 316 */               awayTeam.setInputDevice(AllSelectedTeams.this.lastFireInputDevice);
/*     */             }
/* 318 */             AllSelectedTeams.navigation.team = awayTeam;
/* 319 */             AllSelectedTeams.this.game.setScreen((Screen)new SetTeam(AllSelectedTeams.this.game)); break;
/*     */           } 
/* 321 */           AllSelectedTeams.this.game.setScreen((Screen)new MatchSetup(AllSelectedTeams.this.game));
/*     */           break;
/*     */ 
/*     */         
/*     */         case LEAGUE:
/* 326 */           AllSelectedTeams.navigation.competition.start((ArrayList)AllSelectedTeams.this.game.teamList);
/* 327 */           AllSelectedTeams.this.game.setCompetition(AllSelectedTeams.navigation.competition);
/* 328 */           AllSelectedTeams.this.game.setScreen((Screen)new PlayLeague(AllSelectedTeams.this.game));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 332 */           AllSelectedTeams.navigation.competition.start((ArrayList)AllSelectedTeams.this.game.teamList);
/* 333 */           AllSelectedTeams.this.game.setCompetition(AllSelectedTeams.navigation.competition);
/* 334 */           AllSelectedTeams.this.game.setScreen((Screen)new PlayCup(AllSelectedTeams.this.game));
/*     */           break;
/*     */         
/*     */         case TOURNAMENT:
/* 338 */           AllSelectedTeams.navigation.competition.start((ArrayList)AllSelectedTeams.this.game.teamList);
/* 339 */           AllSelectedTeams.this.game.setCompetition(AllSelectedTeams.navigation.competition);
/* 340 */           AllSelectedTeams.this.game.setScreen((Screen)new PlayTournament(AllSelectedTeams.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\AllSelectedTeams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */