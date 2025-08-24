/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.League;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Month;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.InputButton;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.MatchSettings;
/*     */ import com.ygames.ysoccer.match.Pitch;
/*     */ import com.ygames.ysoccer.match.SceneSettings;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class DesignDiyLeague
/*     */   extends GLScreen {
/*     */   League league;
/*     */   private Widget seasonStartButton;
/*     */   private Widget seasonSeparatorButton;
/*     */   
/*     */   DesignDiyLeague(GLGame game) {
/*  27 */     super(game);
/*     */     
/*  29 */     this.background = game.stateBackground;
/*     */     
/*  31 */     this.league = new League();
/*  32 */     this.league.name = Assets.strings.get("DIY LEAGUE");
/*  33 */     this.league.category = Competition.Category.DIY_COMPETITION;
/*     */ 
/*     */ 
/*     */     
/*  37 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("DESIGN DIY LEAGUE"), game.stateColor.body.intValue());
/*  38 */     this.widgets.add(titleBar);
/*     */     
/*  40 */     LeagueNameButton leagueNameButton = new LeagueNameButton();
/*  41 */     this.widgets.add(leagueNameButton);
/*     */     
/*  43 */     WeatherButton weatherButton = new WeatherButton();
/*  44 */     this.widgets.add(weatherButton);
/*     */     
/*  46 */     SeasonStartButton seasonStartButton = new SeasonStartButton();
/*  47 */     this.widgets.add(seasonStartButton);
/*  48 */     this.seasonStartButton = (Widget)seasonStartButton;
/*     */     
/*  50 */     SeasonSeparatorButton seasonSeparatorButton = new SeasonSeparatorButton();
/*  51 */     this.widgets.add(seasonSeparatorButton);
/*  52 */     this.seasonSeparatorButton = (Widget)seasonSeparatorButton;
/*     */     
/*  54 */     SeasonEndButton seasonEndButton = new SeasonEndButton();
/*  55 */     this.widgets.add(seasonEndButton);
/*  56 */     this.seasonEndButton = (Widget)seasonEndButton;
/*     */     
/*  58 */     PitchTypeButton pitchTypeButton = new PitchTypeButton();
/*  59 */     this.widgets.add(pitchTypeButton);
/*  60 */     this.pitchTypeButton = (Widget)pitchTypeButton;
/*     */     
/*  62 */     TimeLabel timeLabel = new TimeLabel();
/*  63 */     this.widgets.add(timeLabel);
/*     */     
/*  65 */     TimeButton timeButton = new TimeButton();
/*  66 */     this.widgets.add(timeButton);
/*     */     
/*  68 */     setSelectedWidget((Widget)timeButton);
/*     */     
/*  70 */     NumberOfTeamsLabel numberOfTeamsLabel = new NumberOfTeamsLabel();
/*  71 */     this.widgets.add(numberOfTeamsLabel);
/*     */     
/*  73 */     NumberOfTeamsButton numberOfTeamsButton = new NumberOfTeamsButton();
/*  74 */     this.widgets.add(numberOfTeamsButton);
/*     */     
/*  76 */     PlayEachTeamLabel playEachTeamLabel = new PlayEachTeamLabel();
/*  77 */     this.widgets.add(playEachTeamLabel);
/*     */     
/*  79 */     PlayEachTeamButton playEachTeamButton = new PlayEachTeamButton();
/*  80 */     this.widgets.add(playEachTeamButton);
/*     */     
/*  82 */     PointsForAWinLabel pointsForAWinLabel = new PointsForAWinLabel();
/*  83 */     this.widgets.add(pointsForAWinLabel);
/*     */     
/*  85 */     PointsForAWinButton pointsForAWinButton = new PointsForAWinButton();
/*  86 */     this.widgets.add(pointsForAWinButton);
/*     */     
/*  88 */     SubstitutesLabel substitutesLabel = new SubstitutesLabel();
/*  89 */     this.widgets.add(substitutesLabel);
/*     */     
/*  91 */     SubstitutesButton substitutesButton = new SubstitutesButton();
/*  92 */     this.widgets.add(substitutesButton);
/*  93 */     this.substitutesButton = (Widget)substitutesButton;
/*     */     
/*  95 */     BenchSizeLabel benchSizeLabel = new BenchSizeLabel();
/*  96 */     this.widgets.add(benchSizeLabel);
/*     */     
/*  98 */     BenchSizeButton benchSizeButton = new BenchSizeButton();
/*  99 */     this.widgets.add(benchSizeButton);
/*     */     
/* 101 */     OkButton okButton = new OkButton();
/* 102 */     this.widgets.add(okButton);
/*     */     
/* 104 */     AbortButton abortButton = new AbortButton();
/* 105 */     this.widgets.add(abortButton);
/*     */   }
/*     */   private Widget seasonEndButton; private Widget pitchTypeButton;
/*     */   private Widget substitutesButton;
/*     */   
/*     */   private class LeagueNameButton extends InputButton { LeagueNameButton() {
/* 111 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry((1280 - 700) / 2, 120, 700, 38);
/* 112 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 113 */       setText(DesignDiyLeague.this.league.name, Font.Align.CENTER, Assets.font14);
/* 114 */       setEntryLimit(24);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 119 */       DesignDiyLeague.this.league.name = this.text;
/*     */     } }
/*     */ 
/*     */   
/*     */   private class WeatherButton
/*     */     extends Button {
/*     */     WeatherButton() {
/* 126 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 - 350, 175, 290, 38);
/* 127 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 128 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 133 */       DesignDiyLeague.this.league.weather = Competition.Weather.values()[EMath.rotate((Enum)DesignDiyLeague.this.league.weather, (Enum)Competition.Weather.BY_SEASON, (Enum)Competition.Weather.BY_PITCH_TYPE, 1)];
/* 134 */       setDirty(true);
/* 135 */       DesignDiyLeague.this.seasonStartButton.setDirty(true);
/* 136 */       DesignDiyLeague.this.seasonSeparatorButton.setDirty(true);
/* 137 */       DesignDiyLeague.this.seasonEndButton.setDirty(true);
/* 138 */       DesignDiyLeague.this.pitchTypeButton.setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 143 */       setText(Assets.strings.get(DesignDiyLeague.this.league.getWeatherLabel()));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonStartButton
/*     */     extends Button {
/*     */     SeasonStartButton() {
/* 150 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 - 50, 175, 180, 38);
/* 151 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 152 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 157 */       updateSeasonStart(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 162 */       updateSeasonStart(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 167 */       updateSeasonStart(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 172 */       updateSeasonStart(-1);
/*     */     }
/*     */     
/*     */     private void updateSeasonStart(int n) {
/* 176 */       DesignDiyLeague.this.league.seasonStart = Month.values()[EMath.rotate((Enum)DesignDiyLeague.this.league.seasonStart, (Enum)Month.JANUARY, (Enum)Month.DECEMBER, n)];
/* 177 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 182 */       setText(Assets.strings.get(Month.getLabel(DesignDiyLeague.this.league.seasonStart)));
/* 183 */       setVisible((DesignDiyLeague.this.league.weather == Competition.Weather.BY_SEASON));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonSeparatorButton
/*     */     extends Button {
/*     */     SeasonSeparatorButton() {
/* 190 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 + 132, 175, 36, 38);
/* 191 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 192 */       setText("-", Font.Align.CENTER, Assets.font14);
/* 193 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 198 */       setVisible((DesignDiyLeague.this.league.weather == Competition.Weather.BY_SEASON));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonEndButton
/*     */     extends Button {
/*     */     SeasonEndButton() {
/* 205 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 + 170, 175, 180, 38);
/* 206 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 207 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 212 */       updateSeasonEnd(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 217 */       updateSeasonEnd(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 222 */       updateSeasonEnd(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 227 */       updateSeasonEnd(-1);
/*     */     }
/*     */     
/*     */     private void updateSeasonEnd(int n) {
/* 231 */       DesignDiyLeague.this.league.seasonEnd = Month.values()[EMath.rotate((Enum)DesignDiyLeague.this.league.seasonEnd, (Enum)Month.JANUARY, (Enum)Month.DECEMBER, n)];
/* 232 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 237 */       setText(Assets.strings.get(Month.getLabel(DesignDiyLeague.this.league.seasonEnd)));
/* 238 */       setVisible((DesignDiyLeague.this.league.weather == Competition.Weather.BY_SEASON));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypeButton
/*     */     extends Button {
/*     */     PitchTypeButton() {
/* 245 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 - 50, 175, 400, 38);
/* 246 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 247 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 252 */       updatePitchType(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 257 */       updatePitchType(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 262 */       updatePitchType(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 267 */       updatePitchType(-1);
/*     */     }
/*     */     
/*     */     private void updatePitchType(int n) {
/* 271 */       DesignDiyLeague.this.league.pitchType = Pitch.Type.values()[EMath.rotate((Enum)DesignDiyLeague.this.league.pitchType, (Enum)Pitch.Type.FROZEN, (Enum)Pitch.Type.RANDOM, n)];
/* 272 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 277 */       setText(Assets.strings.get(Pitch.names[DesignDiyLeague.this.league.pitchType.ordinal()]));
/* 278 */       setVisible((DesignDiyLeague.this.league.weather == Competition.Weather.BY_PITCH_TYPE));
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeLabel
/*     */     extends Button {
/*     */     TimeLabel() {
/* 285 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 - 350, 230, 440, 38);
/* 286 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 287 */       setText(Assets.strings.get("TIME"), Font.Align.CENTER, Assets.font14);
/* 288 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeButton
/*     */     extends Button {
/*     */     TimeButton() {
/* 295 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 + 110, 230, 240, 38);
/* 296 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 297 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 302 */       updateTime(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 307 */       updateTime(-1);
/*     */     }
/*     */     
/*     */     private void updateTime(int n) {
/* 311 */       DesignDiyLeague.this.league.time = SceneSettings.Time.values()[EMath.rotate((Enum)DesignDiyLeague.this.league.time, (Enum)SceneSettings.Time.DAY, (Enum)SceneSettings.Time.NIGHT, n)];
/* 312 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 317 */       setText(Assets.strings.get(MatchSettings.getTimeLabel(DesignDiyLeague.this.league.time)));
/*     */     }
/*     */   }
/*     */   
/*     */   private class NumberOfTeamsLabel
/*     */     extends Button {
/*     */     NumberOfTeamsLabel() {
/* 324 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 - 350, 285, 440, 38);
/* 325 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 326 */       setText(Assets.strings.get("NUMBER OF TEAMS"), Font.Align.CENTER, Assets.font14);
/* 327 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class NumberOfTeamsButton
/*     */     extends Button {
/*     */     NumberOfTeamsButton() {
/* 334 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 + 110, 285, 240, 38);
/* 335 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 336 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 341 */       updateNumberOfTeams(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 346 */       updateNumberOfTeams(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 351 */       updateNumberOfTeams(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 356 */       updateNumberOfTeams(-1);
/*     */     }
/*     */     
/*     */     private void updateNumberOfTeams(int n) {
/* 360 */       DesignDiyLeague.this.league.numberOfTeams = EMath.slide(DesignDiyLeague.this.league.numberOfTeams, 2, 24, n);
/* 361 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 366 */       setText(DesignDiyLeague.this.league.numberOfTeams);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayEachTeamLabel
/*     */     extends Button {
/*     */     PlayEachTeamLabel() {
/* 373 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 - 350, 340, 440, 38);
/* 374 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 375 */       setText(Assets.strings.get("PLAY EACH TEAM"), Font.Align.CENTER, Assets.font14);
/* 376 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayEachTeamButton
/*     */     extends Button {
/*     */     PlayEachTeamButton() {
/* 383 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 + 110, 340, 240, 38);
/* 384 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 385 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 390 */       updatePlayEachTeam(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 395 */       updatePlayEachTeam(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 400 */       updatePlayEachTeam(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 405 */       updatePlayEachTeam(-1);
/*     */     }
/*     */     
/*     */     private void updatePlayEachTeam(int n) {
/* 409 */       DesignDiyLeague.this.league.rounds = EMath.slide(DesignDiyLeague.this.league.rounds, 1, 10, n);
/* 410 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 415 */       setText("×" + DesignDiyLeague.this.league.rounds);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PointsForAWinLabel
/*     */     extends Button {
/*     */     PointsForAWinLabel() {
/* 422 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 - 350, 395, 440, 38);
/* 423 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 424 */       setText(Assets.strings.get("POINTS FOR A WIN"), Font.Align.CENTER, Assets.font14);
/* 425 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PointsForAWinButton
/*     */     extends Button {
/*     */     PointsForAWinButton() {
/* 432 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 + 110, 395, 240, 38);
/* 433 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 434 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 439 */       updatePointsForAWin(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 444 */       updatePointsForAWin(-1);
/*     */     }
/*     */     
/*     */     private void updatePointsForAWin(int n) {
/* 448 */       DesignDiyLeague.this.league.pointsForAWin = EMath.rotate(DesignDiyLeague.this.league.pointsForAWin, 2, 3, n);
/* 449 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 454 */       setText(DesignDiyLeague.this.league.pointsForAWin);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesLabel
/*     */     extends Button {
/*     */     SubstitutesLabel() {
/* 461 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 - 350, 450, 440, 38);
/* 462 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 463 */       setText(Assets.strings.get("SUBSTITUTES"), Font.Align.CENTER, Assets.font14);
/* 464 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesButton
/*     */     extends Button {
/*     */     SubstitutesButton() {
/* 471 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 + 110, 450, 240, 38);
/* 472 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 473 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 478 */       updateSubstitutes(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 483 */       updateSubstitutes(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 488 */       updateSubstitutes(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 493 */       updateSubstitutes(-1);
/*     */     }
/*     */     
/*     */     private void updateSubstitutes(int n) {
/* 497 */       DesignDiyLeague.this.league.substitutions = EMath.slide(DesignDiyLeague.this.league.substitutions, 2, DesignDiyLeague.this.league.benchSize, n);
/* 498 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 503 */       setText(DesignDiyLeague.this.league.substitutions);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeLabel
/*     */     extends Button {
/*     */     BenchSizeLabel() {
/* 510 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 - 350, 505, 440, 38);
/* 511 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 512 */       setText(Assets.strings.get("BENCH SIZE"), Font.Align.CENTER, Assets.font14);
/* 513 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeButton
/*     */     extends Button {
/*     */     BenchSizeButton() {
/* 520 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry(1280 / 2 + 110, 505, 240, 38);
/* 521 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 522 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 527 */       updateBenchSize(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 532 */       updateBenchSize(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 537 */       updateBenchSize(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 542 */       updateBenchSize(-1);
/*     */     }
/*     */     
/*     */     private void updateBenchSize(int n) {
/* 546 */       DesignDiyLeague.this.league.benchSize = EMath.slide(DesignDiyLeague.this.league.benchSize, 2, 12, n);
/* 547 */       DesignDiyLeague.this.league.substitutions = Math.min(DesignDiyLeague.this.league.substitutions, DesignDiyLeague.this.league.benchSize);
/* 548 */       setDirty(true);
/* 549 */       DesignDiyLeague.this.substitutesButton.setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 554 */       setText(DesignDiyLeague.this.league.benchSize);
/*     */     }
/*     */   }
/*     */   
/*     */   private class OkButton
/*     */     extends Button {
/*     */     OkButton() {
/* 561 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 562 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry((1280 - 180) / 2, 605, 180, 38);
/* 563 */       setText(Assets.strings.get("OK"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 568 */       DesignDiyLeague.navigation.folder = Assets.teamsRootFolder;
/* 569 */       DesignDiyLeague.navigation.competition = (Competition)DesignDiyLeague.this.league;
/* 570 */       DesignDiyLeague.this.game.setScreen((Screen)new SelectFolder(DesignDiyLeague.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 577 */       setColor(13107214);
/* 578 */       Objects.requireNonNull(DesignDiyLeague.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 579 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 584 */       DesignDiyLeague.this.game.setScreen((Screen)new Main(DesignDiyLeague.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DesignDiyLeague.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */