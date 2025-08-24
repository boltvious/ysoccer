/*     */ package com.ygames.ysoccer.screens;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.Cup;
/*     */ import com.ygames.ysoccer.competitions.Round;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Month;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.match.MatchSettings;
/*     */ import com.ygames.ysoccer.match.Pitch;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class InfoCup extends GLScreen {
/*     */   private Cup cup;
/*     */   
/*     */   InfoCup(GLGame game) {
/*  21 */     super(game);
/*     */     
/*  23 */     this.background = game.stateBackground;
/*     */     
/*  25 */     this.cup = (Cup)game.competition;
/*     */ 
/*     */ 
/*     */     
/*  29 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, this.cup.name, game.stateColor.body.intValue());
/*  30 */     this.widgets.add(titleBar);
/*     */     
/*  32 */     WeatherButton weatherButton = new WeatherButton();
/*  33 */     this.widgets.add(weatherButton);
/*     */     
/*  35 */     SeasonStartButton seasonStartButton = new SeasonStartButton();
/*  36 */     this.widgets.add(seasonStartButton);
/*     */     
/*  38 */     SeasonSeparatorButton seasonSeparatorButton = new SeasonSeparatorButton();
/*  39 */     this.widgets.add(seasonSeparatorButton);
/*     */     
/*  41 */     SeasonEndButton seasonEndButton = new SeasonEndButton();
/*  42 */     this.widgets.add(seasonEndButton);
/*     */     
/*  44 */     PitchTypeButton pitchTypeButton = new PitchTypeButton();
/*  45 */     this.widgets.add(pitchTypeButton);
/*     */     
/*  47 */     TimeLabel timeLabel = new TimeLabel();
/*  48 */     this.widgets.add(timeLabel);
/*     */     
/*  50 */     TimeButton timeButton = new TimeButton();
/*  51 */     this.widgets.add(timeButton);
/*     */     
/*  53 */     SubstitutesLabel substitutesLabel = new SubstitutesLabel();
/*  54 */     this.widgets.add(substitutesLabel);
/*     */     
/*  56 */     SubstitutesButton substitutesButton = new SubstitutesButton();
/*  57 */     this.widgets.add(substitutesButton);
/*     */     
/*  59 */     BenchSizeLabel benchSizeLabel = new BenchSizeLabel();
/*  60 */     this.widgets.add(benchSizeLabel);
/*     */     
/*  62 */     BenchSizeButton benchSizeButton = new BenchSizeButton();
/*  63 */     this.widgets.add(benchSizeButton);
/*     */     
/*  65 */     RoundsLabel roundsLabel = new RoundsLabel();
/*  66 */     this.widgets.add(roundsLabel);
/*     */     
/*  68 */     RoundsButton roundsButton = new RoundsButton();
/*  69 */     this.widgets.add(roundsButton);
/*     */     
/*  71 */     AwayGoalsLabel awayGoalsLabel = new AwayGoalsLabel();
/*  72 */     this.widgets.add(awayGoalsLabel);
/*     */     
/*  74 */     AwayGoalsButton awayGoalsButton = new AwayGoalsButton();
/*  75 */     this.widgets.add(awayGoalsButton);
/*     */     
/*  77 */     TeamsLabel teamsLabel = new TeamsLabel();
/*  78 */     this.widgets.add(teamsLabel);
/*     */     
/*  80 */     DescriptionLabel descriptionLabel = new DescriptionLabel();
/*  81 */     this.widgets.add(descriptionLabel);
/*     */ 
/*     */     
/*  84 */     for (int i = 0; i < 6; i++) {
/*  85 */       RoundNameLabel roundNameLabel = new RoundNameLabel(i);
/*  86 */       this.widgets.add(roundNameLabel);
/*     */       
/*  88 */       RoundTeamsLabel roundTeamsLabel = new RoundTeamsLabel(i);
/*  89 */       this.widgets.add(roundTeamsLabel);
/*     */       
/*  91 */       RoundLegsButton roundLegsButton = new RoundLegsButton(i);
/*  92 */       this.widgets.add(roundLegsButton);
/*     */       
/*  94 */       RoundExtraTimeButton roundExtraTimeButton = new RoundExtraTimeButton(i);
/*  95 */       this.widgets.add(roundExtraTimeButton);
/*     */       
/*  97 */       RoundPenaltiesButton roundPenaltiesButton = new RoundPenaltiesButton(i);
/*  98 */       this.widgets.add(roundPenaltiesButton);
/*     */     } 
/*     */     
/* 101 */     ExitButton exitButton = new ExitButton();
/* 102 */     this.widgets.add(exitButton);
/*     */     
/* 104 */     setSelectedWidget((Widget)exitButton);
/*     */   }
/*     */   
/*     */   private class WeatherButton
/*     */     extends Button {
/*     */     WeatherButton() {
/* 110 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 470, 165, 236, 36);
/* 111 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 112 */       setText(Assets.strings.get(InfoCup.this.cup.getWeatherLabel()), Font.Align.CENTER, Assets.font14);
/* 113 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonStartButton
/*     */     extends Button {
/*     */     SeasonStartButton() {
/* 120 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 232, 165, 176, 36);
/* 121 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 122 */       setText(Assets.strings.get(Month.getLabel(InfoCup.this.cup.seasonStart)), Font.Align.CENTER, Assets.font14);
/* 123 */       setVisible((InfoCup.this.cup.weather == Competition.Weather.BY_SEASON));
/* 124 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonSeparatorButton
/*     */     extends Button {
/*     */     SeasonSeparatorButton() {
/* 131 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 54, 165, 36, 36);
/* 132 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 133 */       setText("-", Font.Align.CENTER, Assets.font14);
/* 134 */       setVisible((InfoCup.this.cup.weather == Competition.Weather.BY_SEASON));
/* 135 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonEndButton
/*     */     extends Button {
/*     */     SeasonEndButton() {
/* 142 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 16, 165, 176, 36);
/* 143 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 144 */       setText(Assets.strings.get(Month.getLabel(InfoCup.this.cup.seasonEnd)), Font.Align.CENTER, Assets.font14);
/* 145 */       setVisible((InfoCup.this.cup.weather == Competition.Weather.BY_SEASON));
/* 146 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypeButton
/*     */     extends Button {
/*     */     PitchTypeButton() {
/* 153 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 232, 165, 392, 36);
/* 154 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 155 */       setText(Assets.strings.get(Pitch.names[InfoCup.this.cup.pitchType.ordinal()]), Font.Align.CENTER, Assets.font14);
/* 156 */       setVisible((InfoCup.this.cup.weather == Competition.Weather.BY_PITCH_TYPE));
/* 157 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeLabel
/*     */     extends Button {
/*     */     TimeLabel() {
/* 164 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 + 170, 165, 140, 36);
/* 165 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 166 */       setText(Assets.strings.get("TIME"), Font.Align.CENTER, Assets.font14);
/* 167 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeButton
/*     */     extends Button {
/*     */     TimeButton() {
/* 174 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 + 312, 165, 158, 36);
/* 175 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 176 */       setText(Assets.strings.get(MatchSettings.getTimeLabel(InfoCup.this.cup.time)), Font.Align.CENTER, Assets.font14);
/* 177 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesLabel
/*     */     extends Button {
/*     */     SubstitutesLabel() {
/* 184 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 470, 210, 305, 36);
/* 185 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 186 */       setText(Assets.strings.get("SUBSTITUTES"), Font.Align.CENTER, Assets.font14);
/* 187 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesButton
/*     */     extends Button {
/*     */     SubstitutesButton() {
/* 194 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 163, 210, 158, 36);
/* 195 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 196 */       setText(InfoCup.this.cup.substitutions, Font.Align.CENTER, Assets.font14);
/* 197 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeLabel
/*     */     extends Button {
/*     */     BenchSizeLabel() {
/* 204 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 + 5, 210, 305, 36);
/* 205 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 206 */       setText(Assets.strings.get("BENCH SIZE"), Font.Align.CENTER, Assets.font14);
/* 207 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeButton
/*     */     extends Button {
/*     */     BenchSizeButton() {
/* 214 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 + 312, 210, 158, 36);
/* 215 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 216 */       setText(InfoCup.this.cup.benchSize, Font.Align.CENTER, Assets.font14);
/* 217 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundsLabel
/*     */     extends Button {
/*     */     RoundsLabel() {
/* 224 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 470, 255, 236, 36);
/* 225 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 226 */       setText(Assets.strings.get("ROUNDS"), Font.Align.CENTER, Assets.font14);
/* 227 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundsButton
/*     */     extends Button {
/*     */     RoundsButton() {
/* 234 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 232, 255, 158, 36);
/* 235 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 236 */       setText(InfoCup.this.cup.rounds.size(), Font.Align.CENTER, Assets.font14);
/* 237 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class AwayGoalsLabel
/*     */     extends Button {
/*     */     AwayGoalsLabel() {
/* 244 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 64, 255, 228, 36);
/* 245 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 246 */       setText(Assets.strings.get("AWAY GOALS"), Font.Align.CENTER, Assets.font14);
/* 247 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class AwayGoalsButton
/*     */     extends Button {
/*     */     AwayGoalsButton() {
/* 254 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 + 166, 255, 304, 36);
/* 255 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 256 */       setText(Assets.strings.get(InfoCup.this.cup.getAwayGoalsLabel(InfoCup.this.cup.awayGoals)), Font.Align.CENTER, Assets.font14);
/* 257 */       setVisible(InfoCup.this.cup.hasTwoLegsRound());
/* 258 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamsLabel
/*     */     extends Label {
/*     */     TeamsLabel() {
/* 265 */       setText(Assets.strings.get("TEAMS"), Font.Align.CENTER, Assets.font14);
/* 266 */       Objects.requireNonNull(InfoCup.this.game.gui); setPosition(1280 / 2 - 186, 325);
/*     */     }
/*     */   }
/*     */   
/*     */   private class DescriptionLabel
/*     */     extends Label {
/*     */     DescriptionLabel() {
/* 273 */       setText(Assets.strings.get("DESCRIPTION"), Font.Align.CENTER, Assets.font14);
/* 274 */       Objects.requireNonNull(InfoCup.this.game.gui); setPosition(1280 / 2 + 115, 325);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundNameLabel
/*     */     extends Button {
/*     */     RoundNameLabel(int round) {
/* 281 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 470, 344 + 34 * round, 248, 32);
/* 282 */       if (round == InfoCup.this.cup.currentRound) {
/* 283 */         setColor(4473924);
/*     */       } else {
/* 285 */         setColor(6710886);
/*     */       } 
/* 287 */       setText("", Font.Align.LEFT, Assets.font14);
/* 288 */       setActive(false);
/* 289 */       setVisible((round < InfoCup.this.cup.rounds.size()));
/* 290 */       if (this.visible)
/* 291 */         setText(Assets.gettext(((Round)InfoCup.this.cup.rounds.get(round)).name)); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundTeamsLabel
/*     */     extends Button
/*     */   {
/*     */     RoundTeamsLabel(int round) {
/* 299 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 212, 344 + 34 * round, 50, 32);
/* 300 */       if (round == InfoCup.this.cup.currentRound) {
/* 301 */         setColor(4473924);
/*     */       } else {
/* 303 */         setColor(6710886);
/*     */       } 
/* 305 */       setText(InfoCup.this.cup.getRoundTeams(round), Font.Align.CENTER, Assets.font14);
/* 306 */       setActive(false);
/* 307 */       setVisible((round < InfoCup.this.cup.rounds.size()));
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundLegsButton
/*     */     extends Button {
/*     */     RoundLegsButton(int round) {
/* 314 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 152, 344 + 34 * round, 138, 32);
/* 315 */       if (round == InfoCup.this.cup.currentRound) {
/* 316 */         setColor(4473924);
/*     */       } else {
/* 318 */         setColor(6710886);
/*     */       } 
/* 320 */       setText("", Font.Align.CENTER, Assets.font14);
/* 321 */       setVisible((round < InfoCup.this.cup.rounds.size()));
/* 322 */       if (this.visible) {
/* 323 */         setText(Assets.strings.get(((Round)InfoCup.this.cup.rounds.get(round)).getLegsLabel()));
/*     */       }
/* 325 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundExtraTimeButton
/*     */     extends Button {
/*     */     RoundExtraTimeButton(int round) {
/* 332 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 - 12, 344 + 34 * round, 240, 32);
/* 333 */       if (round == InfoCup.this.cup.currentRound) {
/* 334 */         setColor(4473924);
/*     */       } else {
/* 336 */         setColor(6710886);
/*     */       } 
/* 338 */       setText("", Font.Align.CENTER, Assets.font14);
/* 339 */       setVisible((round < InfoCup.this.cup.rounds.size()));
/* 340 */       if (this.visible) {
/* 341 */         setText(Assets.strings.get(((Round)InfoCup.this.cup.rounds.get(round)).getExtraTimeLabel()));
/*     */       }
/* 343 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundPenaltiesButton
/*     */     extends Button {
/*     */     RoundPenaltiesButton(int round) {
/* 350 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry(1280 / 2 + 230, 344 + 34 * round, 240, 32);
/* 351 */       if (round == InfoCup.this.cup.currentRound) {
/* 352 */         setColor(4473924);
/*     */       } else {
/* 354 */         setColor(6710886);
/*     */       } 
/* 356 */       setText("", Font.Align.CENTER, Assets.font14);
/* 357 */       setVisible((round < InfoCup.this.cup.rounds.size()));
/* 358 */       if (this.visible) {
/* 359 */         setText(Assets.strings.get(((Round)InfoCup.this.cup.rounds.get(round)).getPenaltiesLabel()));
/*     */       }
/* 361 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 368 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 369 */       Objects.requireNonNull(InfoCup.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 370 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 375 */       InfoCup.this.game.setScreen((Screen)new ViewStatistics(InfoCup.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\InfoCup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */