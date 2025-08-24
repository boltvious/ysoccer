/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.Cup;
/*     */ import com.ygames.ysoccer.competitions.Leg;
/*     */ import com.ygames.ysoccer.competitions.Round;
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
/*     */ class DiyCupCalendar
/*     */   extends GLScreen
/*     */ {
/*     */   private Cup cup;
/*     */   private ArrayList<Match> matches;
/*     */   private int currentMatch;
/*     */   private int matchSide;
/*     */   private List<Widget> teamButtons;
/*     */   
/*     */   DiyCupCalendar(GLGame game, Cup cup) {
/*  34 */     super(game);
/*  35 */     this.cup = cup;
/*  36 */     this.currentMatch = 0;
/*  37 */     this.matchSide = 0;
/*  38 */     this.matches = new ArrayList<>();
/*     */     
/*  40 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  44 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, "DIY CUP CALENDAR", game.stateColor.body.intValue());
/*  45 */     this.widgets.add(titleBar);
/*     */     
/*  47 */     MatchesLabel matchesLabel = new MatchesLabel();
/*  48 */     this.widgets.add(matchesLabel);
/*     */     
/*  50 */     this.teamButtons = new ArrayList<>();
/*     */     
/*  52 */     int teamIndex = 0;
/*  53 */     for (Team team : game.teamList) {
/*  54 */       TeamButton teamButton = new TeamButton(team, teamIndex);
/*  55 */       this.teamButtons.add(teamButton);
/*  56 */       this.widgets.add(teamButton);
/*  57 */       teamIndex++;
/*     */     } 
/*     */     
/*  60 */     if (this.teamButtons.size() > 0) {
/*  61 */       Collections.sort(this.teamButtons, Widget.widgetComparatorByText);
/*  62 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, 392, 29, 20, this.teamButtons);
/*  63 */       setSelectedWidget(this.teamButtons.get(0));
/*     */     } 
/*     */     
/*  66 */     HomeTeamLabel homeTeamLabel = new HomeTeamLabel();
/*  67 */     this.widgets.add(homeTeamLabel);
/*     */     
/*  69 */     VersusLabel versusLabel = new VersusLabel();
/*  70 */     this.widgets.add(versusLabel);
/*     */     
/*  72 */     AwayTeamLabel awayTeamLabel = new AwayTeamLabel();
/*  73 */     this.widgets.add(awayTeamLabel);
/*     */     
/*  75 */     BackButton backButton = new BackButton();
/*  76 */     this.widgets.add(backButton);
/*     */     
/*  78 */     AbortButton abortButton = new AbortButton();
/*  79 */     this.widgets.add(abortButton);
/*  80 */     if (getSelectedWidget() == null) {
/*  81 */       setSelectedWidget((Widget)abortButton);
/*     */     }
/*     */     
/*  84 */     PlayButton playButton = new PlayButton();
/*  85 */     this.widgets.add(playButton);
/*     */   }
/*     */   
/*     */   private class MatchesLabel
/*     */     extends Button {
/*     */     MatchesLabel() {
/*  91 */       Objects.requireNonNull(DiyCupCalendar.this.game.gui); setGeometry((1280 - 180) / 2, 80, 180, 36);
/*  92 */       setText("", Font.Align.CENTER, Assets.font14);
/*  93 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/*  98 */       setText("MATCHES: " + DiyCupCalendar.this.currentMatch + " / " + (DiyCupCalendar.this.cup.numberOfTeams / 2));
/*     */     }
/*     */   }
/*     */   
/*     */   private class HomeTeamLabel
/*     */     extends Button {
/*     */     HomeTeamLabel() {
/* 105 */       setGeometry(240, 618, 322, 36);
/* 106 */       setText("", Font.Align.RIGHT, Assets.font14);
/* 107 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 112 */       if (DiyCupCalendar.this.matches.size() > 0) {
/* 113 */         Match match = DiyCupCalendar.this.matches.get((DiyCupCalendar.this.matchSide == 0) ? (DiyCupCalendar.this.currentMatch - 1) : DiyCupCalendar.this.currentMatch);
/* 114 */         setText(((Team)DiyCupCalendar.this.game.teamList.get(match.teams[0])).name);
/*     */       } else {
/* 116 */         setText("");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class VersusLabel
/*     */     extends Button {
/*     */     VersusLabel() {
/* 124 */       Objects.requireNonNull(DiyCupCalendar.this.game.gui); setGeometry(1280 / 2 - 20, 618, 40, 36);
/* 125 */       setText(Assets.gettext("ABBREVIATIONS.VERSUS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 130 */       setVisible((DiyCupCalendar.this.matches.size() > 0));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AwayTeamLabel
/*     */     extends Button {
/*     */     AwayTeamLabel() {
/* 137 */       setGeometry(720, 618, 322, 36);
/* 138 */       setText("", Font.Align.LEFT, Assets.font14);
/* 139 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 144 */       if (DiyCupCalendar.this.matches.size() > 0 && DiyCupCalendar.this.matchSide == 0) {
/* 145 */         Match match = DiyCupCalendar.this.matches.get(DiyCupCalendar.this.currentMatch - 1);
/* 146 */         setText(((Team)DiyCupCalendar.this.game.teamList.get(match.teams[1])).name);
/*     */       } else {
/* 148 */         setText("");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class BackButton
/*     */     extends Button {
/*     */     BackButton() {
/* 156 */       Objects.requireNonNull(DiyCupCalendar.this.game.gui); setGeometry((1280 - 180) / 2 - 360 - 20, 660, 360, 36);
/* 157 */       setColor(10120348);
/* 158 */       setText("BACK", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 163 */       setVisible((DiyCupCalendar.this.matches.size() > 0));
/* 164 */       if (!this.visible) DiyCupCalendar.this.setSelectedWidget(DiyCupCalendar.this.teamButtons.get(0));
/*     */     
/*     */     }
/*     */     
/*     */     public void onFire1Down() {
/* 169 */       Match match = DiyCupCalendar.this.matches.get((DiyCupCalendar.this.matchSide == 0) ? (DiyCupCalendar.this.currentMatch - 1) : DiyCupCalendar.this.currentMatch);
/*     */       
/* 171 */       if (DiyCupCalendar.this.matchSide == 0) {
/* 172 */         DiyCupCalendar.this.matchSide = 1;
/* 173 */         DiyCupCalendar.this.currentMatch--;
/*     */       } else {
/* 175 */         DiyCupCalendar.this.matchSide = 0;
/* 176 */         DiyCupCalendar.this.matches.remove(match);
/*     */       } 
/* 178 */       int teamIndex = match.teams[DiyCupCalendar.this.matchSide];
/* 179 */       for (Widget w : DiyCupCalendar.this.teamButtons) {
/* 180 */         DiyCupCalendar.TeamButton teamButton = (DiyCupCalendar.TeamButton)w;
/* 181 */         if (teamButton.teamIndex == teamIndex) {
/* 182 */           teamButton.done = false;
/*     */           break;
/*     */         } 
/*     */       } 
/* 186 */       DiyCupCalendar.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     private Team team;
/*     */     int teamIndex;
/*     */     boolean done;
/*     */     
/*     */     TeamButton(Team team, int teamIndex) {
/* 197 */       this.team = team;
/* 198 */       this.teamIndex = teamIndex;
/* 199 */       setSize(300, 28);
/* 200 */       setText(team.name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 205 */       if (this.done) {
/* 206 */         setColor(6710886);
/* 207 */         setActive(false);
/*     */       } else {
/* 209 */         switch (this.team.controlMode) {
/*     */           case UNDEFINED:
/* 211 */             setColor(9988382);
/*     */             break;
/*     */           
/*     */           case COMPUTER:
/* 215 */             setColor(9969182);
/*     */             break;
/*     */           
/*     */           case PLAYER:
/* 219 */             setColor(200);
/*     */             break;
/*     */           
/*     */           case COACH:
/* 223 */             setColor(39900);
/*     */             break;
/*     */         } 
/* 226 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 232 */       if (DiyCupCalendar.this.matchSide == 0) {
/* 233 */         Match match = new Match();
/* 234 */         match.teams[0] = this.teamIndex;
/* 235 */         DiyCupCalendar.this.matches.add(match);
/* 236 */         DiyCupCalendar.this.matchSide = 1;
/*     */       } else {
/* 238 */         Match match = DiyCupCalendar.this.matches.get(DiyCupCalendar.this.currentMatch);
/* 239 */         match.teams[1] = this.teamIndex;
/* 240 */         DiyCupCalendar.this.matchSide = 0;
/* 241 */         DiyCupCalendar.this.currentMatch += 1;
/*     */       } 
/* 243 */       this.done = true;
/* 244 */       DiyCupCalendar.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 251 */       Objects.requireNonNull(DiyCupCalendar.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 252 */       setColor(13107214);
/* 253 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 258 */       DiyCupCalendar.this.game.setScreen((Screen)new Main(DiyCupCalendar.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayButton
/*     */     extends Button {
/*     */     PlayButton() {
/* 265 */       Objects.requireNonNull(DiyCupCalendar.this.game.gui); setGeometry(1280 / 2 + 110, 660, 360, 36);
/* 266 */       setText("PLAY CUP", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 271 */       int teams = 0;
/* 272 */       for (Match match : DiyCupCalendar.this.matches) {
/* 273 */         if (match.teams[0] != -1) teams++; 
/* 274 */         if (match.teams[1] != -1) teams++; 
/*     */       } 
/* 276 */       if (teams == DiyCupCalendar.this.cup.numberOfTeams) {
/* 277 */         setColor(1280801);
/* 278 */         setActive(true);
/*     */       } else {
/* 280 */         setColor(6710886);
/* 281 */         setActive(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 287 */       Round round = DiyCupCalendar.this.cup.rounds.get(0);
/* 288 */       round.newLeg();
/* 289 */       ((Leg)round.legs.get(0)).matches = DiyCupCalendar.this.matches;
/*     */       
/* 291 */       DiyCupCalendar.this.cup.start((ArrayList)DiyCupCalendar.this.game.teamList);
/* 292 */       DiyCupCalendar.this.game.setCompetition((Competition)DiyCupCalendar.this.cup);
/* 293 */       DiyCupCalendar.this.game.setScreen((Screen)new PlayCup(DiyCupCalendar.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DiyCupCalendar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */