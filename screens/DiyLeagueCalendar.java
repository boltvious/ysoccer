/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.League;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DiyLeagueCalendar
/*     */   extends GLScreen
/*     */ {
/*     */   private League league;
/*     */   private int currentMatch;
/*     */   private int matchSide;
/*     */   private List<Widget> teamButtons;
/*     */   
/*     */   DiyLeagueCalendar(GLGame game, League league) {
/*  32 */     super(game);
/*  33 */     this.league = league;
/*  34 */     this.currentMatch = 0;
/*  35 */     this.matchSide = 0;
/*     */     
/*  37 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  41 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, "DIY LEAGUE CALENDAR", game.stateColor.body.intValue());
/*  42 */     this.widgets.add(titleBar);
/*     */     
/*  44 */     MatchesLabel matchesLabel = new MatchesLabel();
/*  45 */     this.widgets.add(matchesLabel);
/*     */     
/*  47 */     this.teamButtons = new ArrayList<>();
/*     */     
/*  49 */     int teamIndex = 0;
/*  50 */     for (Team team : game.teamList) {
/*  51 */       TeamButton teamButton = new TeamButton(team, teamIndex);
/*  52 */       this.teamButtons.add(teamButton);
/*  53 */       this.widgets.add(teamButton);
/*  54 */       teamIndex++;
/*     */     } 
/*     */     
/*  57 */     if (this.teamButtons.size() > 0) {
/*  58 */       Collections.sort(this.teamButtons, Widget.widgetComparatorByText);
/*  59 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, 392, 29, 20, this.teamButtons);
/*  60 */       setSelectedWidget(this.teamButtons.get(0));
/*     */     } 
/*     */     
/*  63 */     HomeTeamLabel homeTeamLabel = new HomeTeamLabel();
/*  64 */     this.widgets.add(homeTeamLabel);
/*     */     
/*  66 */     VersusLabel versusLabel = new VersusLabel();
/*  67 */     this.widgets.add(versusLabel);
/*     */     
/*  69 */     AwayTeamLabel awayTeamLabel = new AwayTeamLabel();
/*  70 */     this.widgets.add(awayTeamLabel);
/*     */     
/*  72 */     BackButton backButton = new BackButton();
/*  73 */     this.widgets.add(backButton);
/*     */     
/*  75 */     AbortButton abortButton = new AbortButton();
/*  76 */     this.widgets.add(abortButton);
/*  77 */     if (getSelectedWidget() == null) {
/*  78 */       setSelectedWidget((Widget)abortButton);
/*     */     }
/*     */     
/*  81 */     PlayButton playButton = new PlayButton();
/*  82 */     this.widgets.add(playButton);
/*     */   }
/*     */   
/*     */   private class MatchesLabel
/*     */     extends Button {
/*     */     int matches;
/*     */     
/*     */     MatchesLabel() {
/*  90 */       this.matches = DiyLeagueCalendar.this.league.numberOfTeams * (DiyLeagueCalendar.this.league.numberOfTeams - 1) / 2;
/*  91 */       Objects.requireNonNull(DiyLeagueCalendar.this.game.gui); setGeometry((1280 - 180) / 2, 80, 180, 36);
/*  92 */       setText("", Font.Align.CENTER, Assets.font14);
/*  93 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/*  98 */       setVisible((DiyLeagueCalendar.this.league.calendar.size() > 0));
/*  99 */       setText("MATCHES: " + DiyLeagueCalendar.this.league.calendar.size() + " / " + this.matches);
/*     */     }
/*     */   }
/*     */   
/*     */   private class HomeTeamLabel
/*     */     extends Button {
/*     */     HomeTeamLabel() {
/* 106 */       setGeometry(240, 618, 322, 36);
/* 107 */       setText("", Font.Align.RIGHT, Assets.font14);
/* 108 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 113 */       if (DiyLeagueCalendar.this.league.calendar.size() > 0) {
/* 114 */         Match match = DiyLeagueCalendar.this.league.calendar.get((DiyLeagueCalendar.this.matchSide == 0) ? (DiyLeagueCalendar.this.currentMatch - 1) : DiyLeagueCalendar.this.currentMatch);
/* 115 */         setText(((Team)DiyLeagueCalendar.this.game.teamList.get(match.teams[0])).name);
/*     */       } else {
/* 117 */         setText("");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class VersusLabel
/*     */     extends Button {
/*     */     VersusLabel() {
/* 125 */       Objects.requireNonNull(DiyLeagueCalendar.this.game.gui); setGeometry(1280 / 2 - 20, 618, 40, 36);
/* 126 */       setText(Assets.gettext("ABBREVIATIONS.VERSUS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 131 */       setVisible((DiyLeagueCalendar.this.league.calendar.size() > 0));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AwayTeamLabel
/*     */     extends Button {
/*     */     AwayTeamLabel() {
/* 138 */       setGeometry(720, 618, 322, 36);
/* 139 */       setText("", Font.Align.LEFT, Assets.font14);
/* 140 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 145 */       if (DiyLeagueCalendar.this.league.calendar.size() > 0 && DiyLeagueCalendar.this.matchSide == 0) {
/* 146 */         Match match = DiyLeagueCalendar.this.league.calendar.get(DiyLeagueCalendar.this.currentMatch - 1);
/* 147 */         setText(((Team)DiyLeagueCalendar.this.game.teamList.get(match.teams[1])).name);
/*     */       } else {
/* 149 */         setText("");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class BackButton
/*     */     extends Button {
/*     */     BackButton() {
/* 157 */       Objects.requireNonNull(DiyLeagueCalendar.this.game.gui); setGeometry((1280 - 180) / 2 - 360 - 20, 660, 360, 36);
/* 158 */       setColor(10120348);
/* 159 */       setText("BACK", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 164 */       setVisible((DiyLeagueCalendar.this.league.calendar.size() > 0));
/* 165 */       if (!this.visible) DiyLeagueCalendar.this.setSelectedWidget(DiyLeagueCalendar.this.teamButtons.get(0));
/*     */     
/*     */     }
/*     */     
/*     */     public void onFire1Down() {
/* 170 */       Match match = DiyLeagueCalendar.this.league.calendar.get((DiyLeagueCalendar.this.matchSide == 0) ? (DiyLeagueCalendar.this.currentMatch - 1) : DiyLeagueCalendar.this.currentMatch);
/*     */       
/* 172 */       if (DiyLeagueCalendar.this.matchSide == 0) {
/* 173 */         DiyLeagueCalendar.this.matchSide = 1;
/* 174 */         DiyLeagueCalendar.this.currentMatch--;
/*     */       } else {
/* 176 */         DiyLeagueCalendar.this.matchSide = 0;
/* 177 */         DiyLeagueCalendar.this.league.calendar.remove(match);
/*     */       } 
/* 179 */       int teamIndex = match.teams[DiyLeagueCalendar.this.matchSide];
/* 180 */       for (Widget w : DiyLeagueCalendar.this.teamButtons) {
/* 181 */         DiyLeagueCalendar.TeamButton teamButton = (DiyLeagueCalendar.TeamButton)w;
/* 182 */         if (teamButton.teamIndex == teamIndex) {
/* 183 */           teamButton.matches--;
/*     */           break;
/*     */         } 
/*     */       } 
/* 187 */       DiyLeagueCalendar.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     private Team team;
/*     */     int teamIndex;
/*     */     private int matches;
/*     */     
/*     */     TeamButton(Team team, int teamIndex) {
/* 198 */       this.team = team;
/* 199 */       this.teamIndex = teamIndex;
/* 200 */       setSize(300, 28);
/* 201 */       setText(team.name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 206 */       setText(this.team.name + " " + this.matches);
/* 207 */       switch (this.team.controlMode) {
/*     */         case UNDEFINED:
/* 209 */           setColor(9988382);
/*     */           break;
/*     */         
/*     */         case COMPUTER:
/* 213 */           setColor(9969182);
/*     */           break;
/*     */         
/*     */         case PLAYER:
/* 217 */           setColor(200);
/*     */           break;
/*     */         
/*     */         case COACH:
/* 221 */           setColor(39900);
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 228 */       if (DiyLeagueCalendar.this.matchSide == 0) {
/* 229 */         Match match = new Match();
/* 230 */         match.teams[0] = this.teamIndex;
/* 231 */         DiyLeagueCalendar.this.league.calendar.add(match);
/* 232 */         DiyLeagueCalendar.this.matchSide = 1;
/*     */       } else {
/* 234 */         Match match = DiyLeagueCalendar.this.league.calendar.get(DiyLeagueCalendar.this.currentMatch);
/* 235 */         match.teams[1] = this.teamIndex;
/* 236 */         DiyLeagueCalendar.this.matchSide = 0;
/* 237 */         DiyLeagueCalendar.this.currentMatch += 1;
/*     */       } 
/* 239 */       this.matches++;
/* 240 */       DiyLeagueCalendar.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 247 */       Objects.requireNonNull(DiyLeagueCalendar.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 248 */       setColor(13107214);
/* 249 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 254 */       DiyLeagueCalendar.this.game.setScreen((Screen)new Main(DiyLeagueCalendar.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayButton
/*     */     extends Button {
/*     */     PlayButton() {
/* 261 */       Objects.requireNonNull(DiyLeagueCalendar.this.game.gui); setGeometry(1280 / 2 + 110, 660, 360, 36);
/* 262 */       setText("PLAY LEAGUE", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 267 */       int diff = DiyLeagueCalendar.this.currentMatch - DiyLeagueCalendar.this.league.numberOfTeams * (DiyLeagueCalendar.this.league.numberOfTeams - 1) / 2;
/* 268 */       if (diff == 0) {
/* 269 */         setColor(1280801);
/* 270 */         setActive(true);
/*     */       } else {
/* 272 */         setColor(6710886);
/* 273 */         setActive(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 280 */       for (int r = 1; r < DiyLeagueCalendar.this.league.rounds; r++) {
/* 281 */         for (int i = 0; i < DiyLeagueCalendar.this.league.numberOfTeams * (DiyLeagueCalendar.this.league.numberOfTeams - 1) / 2; i++) {
/* 282 */           Match firstRoundMatch = DiyLeagueCalendar.this.league.calendar.get(i);
/* 283 */           Match match = new Match();
/* 284 */           if (r % 2 == 0) {
/* 285 */             match.teams[0] = firstRoundMatch.teams[0];
/* 286 */             match.teams[1] = firstRoundMatch.teams[1];
/*     */           } else {
/* 288 */             match.teams[0] = firstRoundMatch.teams[1];
/* 289 */             match.teams[1] = firstRoundMatch.teams[0];
/*     */           } 
/* 291 */           DiyLeagueCalendar.this.league.calendar.add(match);
/*     */         } 
/*     */       } 
/* 294 */       DiyLeagueCalendar.this.league.start((ArrayList)DiyLeagueCalendar.this.game.teamList);
/* 295 */       DiyLeagueCalendar.this.game.setCompetition((Competition)DiyLeagueCalendar.this.league);
/* 296 */       DiyLeagueCalendar.this.game.setScreen((Screen)new PlayLeague(DiyLeagueCalendar.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DiyLeagueCalendar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */