/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.League;
/*     */ import com.ygames.ysoccer.competitions.TableRow;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class PlayLeague extends GLScreen {
/*     */   private League league;
/*     */   
/*     */   PlayLeague(GLGame game) {
/*  23 */     super(game);
/*     */     
/*  25 */     this.league = (League)game.competition;
/*     */     
/*  27 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  31 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, this.league.getMenuTitle(), game.stateColor.body.intValue());
/*  32 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  35 */     int dx = 570;
/*  36 */     int dy = 86 + 10 * (24 - this.league.numberOfTeams);
/*  37 */     String[] headers = { "TABLE HEADER.PLAYED MATCHES", "TABLE HEADER.WON MATCHES", "TABLE HEADER.DRAWN MATCHES", "TABLE HEADER.LOST MATCHES", "TABLE HEADER.GOALS FOR", "TABLE HEADER.GOALS AGAINST", "TABLE HEADER.POINTS" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  46 */     for (String header : headers) {
/*  47 */       Label label = new Label();
/*  48 */       label.setGeometry(dx, dy, 72, 21);
/*  49 */       label.setText(Assets.strings.get(header), Font.Align.CENTER, Assets.font10);
/*  50 */       this.widgets.add(label);
/*  51 */       dx += 70;
/*     */     } 
/*     */ 
/*     */     
/*  55 */     int tm = 0;
/*  56 */     dx = 570;
/*  57 */     for (TableRow row : this.league.table) {
/*  58 */       Button button = new Button();
/*  59 */       button.setGeometry(210, dy + 20 + 21 * tm, 36, 23);
/*  60 */       button.setText(tm + 1, Font.Align.CENTER, Assets.font10);
/*  61 */       button.setActive(false);
/*  62 */       this.widgets.add(button);
/*     */       
/*  64 */       Team team = this.league.teams.get(row.team);
/*  65 */       button = new Button();
/*  66 */       button.setGeometry(250, dy + 20 + 21 * tm, 322, 23);
/*  67 */       switch (team.controlMode) {
/*     */         case COMPUTER:
/*  69 */           button.setColors(Integer.valueOf(9969182), Integer.valueOf(1973790), Integer.valueOf(1973790));
/*     */           break;
/*     */         
/*     */         case PLAYER:
/*  73 */           button.setColors(Integer.valueOf(200), Integer.valueOf(1973790), Integer.valueOf(1973790));
/*     */           break;
/*     */         
/*     */         case COACH:
/*  77 */           button.setColors(Integer.valueOf(39900), Integer.valueOf(1973790), Integer.valueOf(1973790));
/*     */           break;
/*     */       } 
/*  80 */       button.setText(team.name, Font.Align.LEFT, Assets.font10);
/*  81 */       button.setActive(false);
/*  82 */       this.widgets.add(button);
/*     */ 
/*     */       
/*  85 */       button = new Button();
/*  86 */       button.setGeometry(dx, dy + 20 + 21 * tm, 72, 23);
/*  87 */       button.setColors(Integer.valueOf(8421504), Integer.valueOf(1973790), Integer.valueOf(1973790));
/*  88 */       button.setText(row.won + row.drawn + row.lost, Font.Align.CENTER, Assets.font10);
/*  89 */       button.setActive(false);
/*  90 */       this.widgets.add(button);
/*     */ 
/*     */       
/*  93 */       button = new Button();
/*  94 */       button.setGeometry(dx + 70, dy + 20 + 21 * tm, 72, 23);
/*  95 */       button.setColors(Integer.valueOf(8421504), Integer.valueOf(1973790), Integer.valueOf(1973790));
/*  96 */       button.setText(row.won, Font.Align.CENTER, Assets.font10);
/*  97 */       button.setActive(false);
/*  98 */       this.widgets.add(button);
/*     */ 
/*     */       
/* 101 */       button = new Button();
/* 102 */       button.setGeometry(dx + 140, dy + 20 + 21 * tm, 72, 23);
/* 103 */       button.setColors(Integer.valueOf(8421504), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 104 */       button.setText(row.drawn, Font.Align.CENTER, Assets.font10);
/* 105 */       button.setActive(false);
/* 106 */       this.widgets.add(button);
/*     */ 
/*     */       
/* 109 */       button = new Button();
/* 110 */       button.setGeometry(dx + 210, dy + 20 + 21 * tm, 72, 23);
/* 111 */       button.setColors(Integer.valueOf(8421504), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 112 */       button.setText(row.lost, Font.Align.CENTER, Assets.font10);
/* 113 */       button.setActive(false);
/* 114 */       this.widgets.add(button);
/*     */ 
/*     */       
/* 117 */       button = new Button();
/* 118 */       button.setGeometry(dx + 280, dy + 20 + 21 * tm, 72, 23);
/* 119 */       button.setColors(Integer.valueOf(8421504), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 120 */       button.setText(row.goalsFor, Font.Align.CENTER, Assets.font10);
/* 121 */       button.setActive(false);
/* 122 */       this.widgets.add(button);
/*     */ 
/*     */       
/* 125 */       button = new Button();
/* 126 */       button.setGeometry(dx + 350, dy + 20 + 21 * tm, 72, 23);
/* 127 */       button.setColors(Integer.valueOf(8421504), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 128 */       button.setText(row.goalsAgainst, Font.Align.CENTER, Assets.font10);
/* 129 */       button.setActive(false);
/* 130 */       this.widgets.add(button);
/*     */ 
/*     */       
/* 133 */       button = new Button();
/* 134 */       button.setGeometry(dx + 420, dy + 20 + 21 * tm, 72, 23);
/* 135 */       button.setColors(Integer.valueOf(8421504), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 136 */       button.setText(row.points, Font.Align.CENTER, Assets.font10);
/* 137 */       button.setActive(false);
/* 138 */       this.widgets.add(button);
/*     */       
/* 140 */       tm++;
/*     */     } 
/*     */     
/* 143 */     ViewStatisticsButton viewStatisticsButton = new ViewStatisticsButton();
/* 144 */     this.widgets.add(viewStatisticsButton);
/*     */     
/* 146 */     ExitButton exitButton = new ExitButton();
/* 147 */     this.widgets.add(exitButton);
/*     */     
/* 149 */     if (this.league.isEnded()) {
/*     */       
/* 151 */       setSelectedWidget((Widget)exitButton);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 156 */       Label label = new Label();
/* 157 */       label.setGeometry(240, 618, 322, 36);
/* 158 */       label.setText((this.league.getTeam(0)).name, Font.Align.RIGHT, Assets.font14);
/* 159 */       this.widgets.add(label);
/*     */ 
/*     */       
/* 162 */       label = new Label();
/* 163 */       label.setGeometry(720, 618, 322, 36);
/* 164 */       label.setText((this.league.getTeam(1)).name, Font.Align.LEFT, Assets.font14);
/* 165 */       this.widgets.add(label);
/*     */       
/* 167 */       Match match = this.league.getMatch();
/*     */ 
/*     */       
/* 170 */       label = new Label();
/* 171 */       Objects.requireNonNull(game.gui); label.setGeometry(1280 / 2 - 60, 618, 40, 36);
/* 172 */       label.setText("", Font.Align.RIGHT, Assets.font14);
/* 173 */       if (match.isEnded()) {
/* 174 */         label.setText(match.getResult()[0]);
/*     */       }
/* 176 */       this.widgets.add(label);
/*     */ 
/*     */       
/* 179 */       label = new Label();
/* 180 */       Objects.requireNonNull(game.gui); label.setGeometry(1280 / 2 - 20, 618, 40, 36);
/* 181 */       label.setText("", Font.Align.CENTER, Assets.font14);
/* 182 */       if (match.isEnded()) {
/* 183 */         label.setText("-");
/*     */       } else {
/* 185 */         label.setText(Assets.strings.get("ABBREVIATIONS.VERSUS"));
/*     */       } 
/* 187 */       this.widgets.add(label);
/*     */ 
/*     */       
/* 190 */       label = new Label();
/* 191 */       Objects.requireNonNull(game.gui); label.setGeometry(1280 / 2 + 20, 618, 40, 36);
/* 192 */       label.setText("", Font.Align.LEFT, Assets.font14);
/* 193 */       if (match.isEnded()) {
/* 194 */         label.setText(match.getResult()[1]);
/*     */       }
/* 196 */       this.widgets.add(label);
/*     */       
/* 198 */       if (match.isEnded()) {
/* 199 */         NextMatchButton nextMatchButton = new NextMatchButton();
/* 200 */         this.widgets.add(nextMatchButton);
/* 201 */         setSelectedWidget((Widget)nextMatchButton);
/*     */       } else {
/* 203 */         PlayViewMatchButton playViewMatchButton = new PlayViewMatchButton();
/* 204 */         this.widgets.add(playViewMatchButton);
/*     */         
/* 206 */         ViewResultButton viewResultButton = new ViewResultButton();
/* 207 */         this.widgets.add(viewResultButton);
/*     */         
/* 209 */         if (this.league.bothComputers() || this.league.userPrefersResult) {
/* 210 */           setSelectedWidget((Widget)viewResultButton);
/*     */         } else {
/* 212 */           setSelectedWidget((Widget)playViewMatchButton);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class PlayViewMatchButton
/*     */     extends Button {
/*     */     PlayViewMatchButton() {
/* 221 */       Objects.requireNonNull(PlayLeague.this.game.gui); setGeometry(1280 / 2 - 430, 660, 220, 36);
/* 222 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 223 */       setText("", Font.Align.CENTER, Assets.font14);
/* 224 */       if (PlayLeague.this.league.bothComputers()) {
/* 225 */         setText(Assets.strings.get("VIEW MATCH"));
/*     */       } else {
/* 227 */         setText("- " + Assets.strings.get("MATCH") + " -");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 233 */       PlayLeague.this.league.userPrefersResult = false;
/*     */       
/* 235 */       Team homeTeam = PlayLeague.this.league.getTeam(0);
/* 236 */       Team awayTeam = PlayLeague.this.league.getTeam(1);
/*     */       
/* 238 */       Match match = PlayLeague.this.league.getMatch();
/* 239 */       match.setTeam(0, homeTeam);
/* 240 */       match.setTeam(1, awayTeam);
/*     */ 
/*     */       
/* 243 */       PlayLeague.this.game.inputDevices.setAvailability(true);
/* 244 */       homeTeam.setInputDevice(null);
/* 245 */       homeTeam.releaseNonAiInputDevices();
/* 246 */       awayTeam.setInputDevice(null);
/* 247 */       awayTeam.releaseNonAiInputDevices();
/*     */ 
/*     */       
/* 250 */       if (homeTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 251 */         if (PlayLeague.this.lastFireInputDevice != null) {
/* 252 */           homeTeam.setInputDevice(PlayLeague.this.lastFireInputDevice);
/*     */         }
/* 254 */         PlayLeague.navigation.competition = (Competition)PlayLeague.this.league;
/* 255 */         PlayLeague.navigation.team = homeTeam;
/* 256 */         PlayLeague.this.game.setScreen((Screen)new SetTeam(PlayLeague.this.game));
/* 257 */       } else if (awayTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 258 */         if (PlayLeague.this.lastFireInputDevice != null) {
/* 259 */           awayTeam.setInputDevice(PlayLeague.this.lastFireInputDevice);
/*     */         }
/* 261 */         PlayLeague.navigation.competition = (Competition)PlayLeague.this.league;
/* 262 */         PlayLeague.navigation.team = awayTeam;
/* 263 */         PlayLeague.this.game.setScreen((Screen)new SetTeam(PlayLeague.this.game));
/*     */       } else {
/* 265 */         PlayLeague.navigation.competition = (Competition)PlayLeague.this.league;
/* 266 */         PlayLeague.this.game.setScreen((Screen)new MatchSetup(PlayLeague.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class NextMatchButton
/*     */     extends Button {
/*     */     NextMatchButton() {
/* 274 */       Objects.requireNonNull(PlayLeague.this.game.gui); setGeometry(1280 / 2 - 430, 660, 460, 36);
/* 275 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 276 */       setText(Assets.strings.get("NEXT MATCH"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 281 */       nextMatch();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 286 */       nextMatch();
/*     */     }
/*     */     
/*     */     private void nextMatch() {
/* 290 */       PlayLeague.this.league.nextMatch();
/* 291 */       PlayLeague.this.game.setScreen((Screen)new PlayLeague(PlayLeague.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewResultButton
/*     */     extends Button {
/*     */     ViewResultButton() {
/* 298 */       Objects.requireNonNull(PlayLeague.this.game.gui); setGeometry(1280 / 2 - 190, 660, 220, 36);
/* 299 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 300 */       setText("", Font.Align.CENTER, Assets.font14);
/* 301 */       if (PlayLeague.this.league.bothComputers()) {
/* 302 */         setText(Assets.strings.get("VIEW RESULT"));
/*     */       } else {
/* 304 */         setText("- " + Assets.strings.get("RESULT") + " -");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 310 */       viewResult();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 315 */       if (PlayLeague.this.league.bothComputers()) {
/* 316 */         viewResult();
/*     */       }
/*     */     }
/*     */     
/*     */     private void viewResult() {
/* 321 */       if (!PlayLeague.this.league.bothComputers()) {
/* 322 */         PlayLeague.this.league.userPrefersResult = true;
/*     */       }
/*     */       
/* 325 */       PlayLeague.this.league.generateResult();
/*     */       
/* 327 */       PlayLeague.this.game.setScreen((Screen)new PlayLeague(PlayLeague.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewStatisticsButton
/*     */     extends Button {
/*     */     ViewStatisticsButton() {
/* 334 */       Objects.requireNonNull(PlayLeague.this.game.gui); setGeometry(1280 / 2 + 50, 660, 180, 36);
/* 335 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 336 */       setText(Assets.strings.get("STATS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 341 */       PlayLeague.this.game.setScreen((Screen)new ViewStatistics(PlayLeague.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 348 */       Objects.requireNonNull(PlayLeague.this.game.gui); setGeometry(1280 / 2 + 250, 660, 180, 36);
/* 349 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 350 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 355 */       PlayLeague.this.game.setScreen((Screen)new Main(PlayLeague.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\PlayLeague.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */