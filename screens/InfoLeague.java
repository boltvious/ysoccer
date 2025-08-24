/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.League;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Month;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.MatchSettings;
/*     */ import com.ygames.ysoccer.match.Pitch;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class InfoLeague
/*     */   extends GLScreen {
/*     */   InfoLeague(GLGame game) {
/*  20 */     super(game);
/*     */     
/*  22 */     this.background = game.stateBackground;
/*     */     
/*  24 */     this.league = (League)game.competition;
/*     */ 
/*     */ 
/*     */     
/*  28 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, this.league.name, game.stateColor.body.intValue());
/*  29 */     this.widgets.add(titleBar);
/*     */     
/*  31 */     WeatherButton weatherButton = new WeatherButton();
/*  32 */     this.widgets.add(weatherButton);
/*     */     
/*  34 */     SeasonStartButton seasonStartButton = new SeasonStartButton();
/*  35 */     this.widgets.add(seasonStartButton);
/*     */     
/*  37 */     SeasonSeparatorButton seasonSeparatorButton = new SeasonSeparatorButton();
/*  38 */     this.widgets.add(seasonSeparatorButton);
/*     */     
/*  40 */     SeasonEndButton seasonEndButton = new SeasonEndButton();
/*  41 */     this.widgets.add(seasonEndButton);
/*     */     
/*  43 */     PitchTypeButton pitchTypeButton = new PitchTypeButton();
/*  44 */     this.widgets.add(pitchTypeButton);
/*     */     
/*  46 */     TimeLabel timeLabel = new TimeLabel();
/*  47 */     this.widgets.add(timeLabel);
/*     */     
/*  49 */     TimeButton timeButton = new TimeButton();
/*  50 */     this.widgets.add(timeButton);
/*     */     
/*  52 */     NumberOfTeamsLabel numberOfTeamsLabel = new NumberOfTeamsLabel();
/*  53 */     this.widgets.add(numberOfTeamsLabel);
/*     */     
/*  55 */     NumberOfTeamsButton numberOfTeamsButton = new NumberOfTeamsButton();
/*  56 */     this.widgets.add(numberOfTeamsButton);
/*     */     
/*  58 */     PlayEachTeamLabel playEachTeamLabel = new PlayEachTeamLabel();
/*  59 */     this.widgets.add(playEachTeamLabel);
/*     */     
/*  61 */     PlayEachTeamButton playEachTeamButton = new PlayEachTeamButton();
/*  62 */     this.widgets.add(playEachTeamButton);
/*     */     
/*  64 */     PointsForAWinLabel pointsForAWinLabel = new PointsForAWinLabel();
/*  65 */     this.widgets.add(pointsForAWinLabel);
/*     */     
/*  67 */     PointsForAWinButton pointsForAWinButton = new PointsForAWinButton();
/*  68 */     this.widgets.add(pointsForAWinButton);
/*     */     
/*  70 */     SubstitutesLabel substitutesLabel = new SubstitutesLabel();
/*  71 */     this.widgets.add(substitutesLabel);
/*     */     
/*  73 */     SubstitutesButton substitutesButton = new SubstitutesButton();
/*  74 */     this.widgets.add(substitutesButton);
/*     */     
/*  76 */     BenchSizeLabel benchSizeLabel = new BenchSizeLabel();
/*  77 */     this.widgets.add(benchSizeLabel);
/*     */     
/*  79 */     BenchSizeButton benchSizeButton = new BenchSizeButton();
/*  80 */     this.widgets.add(benchSizeButton);
/*     */     
/*  82 */     ExitButton exitButton = new ExitButton();
/*  83 */     this.widgets.add(exitButton);
/*     */     
/*  85 */     setSelectedWidget((Widget)exitButton);
/*     */   }
/*     */   
/*     */   private League league;
/*     */   
/*     */   private class WeatherButton extends Button { WeatherButton() {
/*  91 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 - 350, 175, 290, 38);
/*  92 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/*  93 */       setText(Assets.strings.get(InfoLeague.this.league.getWeatherLabel()), Font.Align.CENTER, Assets.font14);
/*  94 */       setActive(false);
/*     */     } }
/*     */ 
/*     */   
/*     */   private class SeasonStartButton
/*     */     extends Button {
/*     */     SeasonStartButton() {
/* 101 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 - 50, 175, 180, 38);
/* 102 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 103 */       setText(Assets.strings.get(Month.getLabel(InfoLeague.this.league.seasonStart)), Font.Align.CENTER, Assets.font14);
/* 104 */       setVisible((InfoLeague.this.league.weather == Competition.Weather.BY_SEASON));
/* 105 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonSeparatorButton
/*     */     extends Button {
/*     */     SeasonSeparatorButton() {
/* 112 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 + 132, 175, 36, 38);
/* 113 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 114 */       setText("-", Font.Align.CENTER, Assets.font14);
/* 115 */       setActive(false);
/* 116 */       setVisible((InfoLeague.this.league.weather == Competition.Weather.BY_SEASON));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonEndButton
/*     */     extends Button {
/*     */     SeasonEndButton() {
/* 123 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 + 170, 175, 180, 38);
/* 124 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 125 */       setText(Assets.strings.get(Month.getLabel(InfoLeague.this.league.seasonEnd)), Font.Align.CENTER, Assets.font14);
/* 126 */       setVisible((InfoLeague.this.league.weather == Competition.Weather.BY_SEASON));
/* 127 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypeButton
/*     */     extends Button {
/*     */     PitchTypeButton() {
/* 134 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 - 50, 175, 400, 38);
/* 135 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 136 */       setText(Assets.strings.get(Pitch.names[InfoLeague.this.league.pitchType.ordinal()]), Font.Align.CENTER, Assets.font14);
/* 137 */       setVisible((InfoLeague.this.league.weather == Competition.Weather.BY_PITCH_TYPE));
/* 138 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeLabel
/*     */     extends Button {
/*     */     private TimeLabel() {
/* 145 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 - 350, 230, 440, 38);
/* 146 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 147 */       setText(Assets.strings.get("TIME"), Font.Align.CENTER, Assets.font14);
/* 148 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeButton
/*     */     extends Button {
/*     */     TimeButton() {
/* 155 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 + 110, 230, 240, 38);
/* 156 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 157 */       setText(Assets.strings.get(MatchSettings.getTimeLabel(InfoLeague.this.league.time)), Font.Align.CENTER, Assets.font14);
/* 158 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class NumberOfTeamsLabel
/*     */     extends Button {
/*     */     NumberOfTeamsLabel() {
/* 165 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 - 350, 285, 440, 38);
/* 166 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 167 */       setText(Assets.strings.get("NUMBER OF TEAMS"), Font.Align.CENTER, Assets.font14);
/* 168 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class NumberOfTeamsButton
/*     */     extends Button {
/*     */     NumberOfTeamsButton() {
/* 175 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 + 110, 285, 240, 38);
/* 176 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 177 */       setText(InfoLeague.this.league.numberOfTeams, Font.Align.CENTER, Assets.font14);
/* 178 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayEachTeamLabel
/*     */     extends Button {
/*     */     PlayEachTeamLabel() {
/* 185 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 - 350, 340, 440, 38);
/* 186 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 187 */       setText(Assets.strings.get("PLAY EACH TEAM"), Font.Align.CENTER, Assets.font14);
/* 188 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayEachTeamButton
/*     */     extends Button {
/*     */     PlayEachTeamButton() {
/* 195 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 + 110, 340, 240, 38);
/* 196 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 197 */       setText("×" + InfoLeague.this.league.rounds, Font.Align.CENTER, Assets.font14);
/* 198 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PointsForAWinLabel
/*     */     extends Button {
/*     */     PointsForAWinLabel() {
/* 205 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 - 350, 395, 440, 38);
/* 206 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 207 */       setText(Assets.strings.get("POINTS FOR A WIN"), Font.Align.CENTER, Assets.font14);
/* 208 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PointsForAWinButton
/*     */     extends Button {
/*     */     PointsForAWinButton() {
/* 215 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 + 110, 395, 240, 38);
/* 216 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 217 */       setText(InfoLeague.this.league.pointsForAWin, Font.Align.CENTER, Assets.font14);
/* 218 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesLabel
/*     */     extends Button {
/*     */     SubstitutesLabel() {
/* 225 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 - 350, 450, 440, 38);
/* 226 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 227 */       setText(Assets.strings.get("SUBSTITUTES"), Font.Align.CENTER, Assets.font14);
/* 228 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesButton
/*     */     extends Button {
/*     */     SubstitutesButton() {
/* 235 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 + 110, 450, 240, 38);
/* 236 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 237 */       setText(InfoLeague.this.league.substitutions, Font.Align.CENTER, Assets.font14);
/* 238 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeLabel
/*     */     extends Button {
/*     */     BenchSizeLabel() {
/* 245 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 - 350, 505, 440, 38);
/* 246 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 247 */       setText(Assets.strings.get("BENCH SIZE"), Font.Align.CENTER, Assets.font14);
/* 248 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeButton
/*     */     extends Button {
/*     */     BenchSizeButton() {
/* 255 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry(1280 / 2 + 110, 505, 240, 38);
/* 256 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 257 */       setText(InfoLeague.this.league.benchSize, Font.Align.CENTER, Assets.font14);
/* 258 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 265 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 266 */       Objects.requireNonNull(InfoLeague.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 267 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 272 */       InfoLeague.this.game.setScreen((Screen)new ViewStatistics(InfoLeague.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\InfoLeague.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */