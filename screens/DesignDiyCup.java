/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.Cup;
/*     */ import com.ygames.ysoccer.competitions.Round;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Month;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.InputButton;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.MatchSettings;
/*     */ import com.ygames.ysoccer.match.Pitch;
/*     */ import com.ygames.ysoccer.match.SceneSettings;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class DesignDiyCup
/*     */   extends GLScreen
/*     */ {
/*     */   Cup cup;
/*     */   private Widget seasonStartButton;
/*     */   private Widget seasonSeparatorButton;
/*  28 */   private Widget[] roundNameLabels = new Widget[6]; private Widget seasonEndButton; private Widget pitchTypeButton; private Widget substitutesButton; private Widget awayGoalsButton;
/*  29 */   private Widget[] roundTeamsLabels = new Widget[6];
/*  30 */   private Widget[] roundLegsButtons = new Widget[6];
/*  31 */   private Widget[] roundExtraTimeButtons = new Widget[6];
/*  32 */   private Widget[] roundPenaltiesButtons = new Widget[6];
/*     */   
/*     */   DesignDiyCup(GLGame game) {
/*  35 */     super(game);
/*     */     
/*  37 */     this.background = game.stateBackground;
/*     */     
/*  39 */     this.cup = new Cup();
/*  40 */     this.cup.name = Assets.strings.get("DIY CUP");
/*  41 */     this.cup.category = Competition.Category.DIY_COMPETITION;
/*  42 */     this.cup.addRound();
/*  43 */     this.cup.addRound();
/*  44 */     this.cup.addRound();
/*  45 */     this.cup.addRound();
/*     */ 
/*     */ 
/*     */     
/*  49 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("DESIGN DIY CUP"), game.stateColor.body.intValue());
/*  50 */     this.widgets.add(titleBar);
/*     */     
/*  52 */     CupNameButton cupNameButton = new CupNameButton();
/*  53 */     this.widgets.add(cupNameButton);
/*     */     
/*  55 */     WeatherButton weatherButton = new WeatherButton();
/*  56 */     this.widgets.add(weatherButton);
/*     */     
/*  58 */     SeasonStartButton seasonStartButton = new SeasonStartButton();
/*  59 */     this.widgets.add(seasonStartButton);
/*  60 */     this.seasonStartButton = (Widget)seasonStartButton;
/*     */     
/*  62 */     SeasonSeparatorButton seasonSeparatorButton = new SeasonSeparatorButton();
/*  63 */     this.widgets.add(seasonSeparatorButton);
/*  64 */     this.seasonSeparatorButton = (Widget)seasonSeparatorButton;
/*     */     
/*  66 */     SeasonEndButton seasonEndButton = new SeasonEndButton();
/*  67 */     this.widgets.add(seasonEndButton);
/*  68 */     this.seasonEndButton = (Widget)seasonEndButton;
/*     */     
/*  70 */     PitchTypeButton pitchTypeButton = new PitchTypeButton();
/*  71 */     this.widgets.add(pitchTypeButton);
/*  72 */     this.pitchTypeButton = (Widget)pitchTypeButton;
/*     */     
/*  74 */     TimeLabel timeLabel = new TimeLabel();
/*  75 */     this.widgets.add(timeLabel);
/*     */     
/*  77 */     TimeButton timeButton = new TimeButton();
/*  78 */     this.widgets.add(timeButton);
/*     */     
/*  80 */     SubstitutesLabel substitutesLabel = new SubstitutesLabel();
/*  81 */     this.widgets.add(substitutesLabel);
/*     */     
/*  83 */     SubstitutesButton substitutesButton = new SubstitutesButton();
/*  84 */     this.widgets.add(substitutesButton);
/*  85 */     this.substitutesButton = (Widget)substitutesButton;
/*     */     
/*  87 */     BenchSizeLabel benchSizeLabel = new BenchSizeLabel();
/*  88 */     this.widgets.add(benchSizeLabel);
/*     */     
/*  90 */     BenchSizeButton benchSizeButton = new BenchSizeButton();
/*  91 */     this.widgets.add(benchSizeButton);
/*     */     
/*  93 */     RoundsLabel roundsLabel = new RoundsLabel();
/*  94 */     this.widgets.add(roundsLabel);
/*     */     
/*  96 */     RoundsButton roundsButton = new RoundsButton();
/*  97 */     this.widgets.add(roundsButton);
/*  98 */     setSelectedWidget((Widget)roundsButton);
/*     */     
/* 100 */     AwayGoalsLabel awayGoalsLabel = new AwayGoalsLabel();
/* 101 */     this.widgets.add(awayGoalsLabel);
/*     */     
/* 103 */     AwayGoalsButton awayGoalsButton = new AwayGoalsButton();
/* 104 */     this.widgets.add(awayGoalsButton);
/* 105 */     this.awayGoalsButton = (Widget)awayGoalsButton;
/*     */     
/* 107 */     TeamsLabel teamsLabel = new TeamsLabel();
/* 108 */     this.widgets.add(teamsLabel);
/*     */     
/* 110 */     DescriptionLabel descriptionLabel = new DescriptionLabel();
/* 111 */     this.widgets.add(descriptionLabel);
/*     */ 
/*     */     
/* 114 */     for (int i = 0; i < 6; i++) {
/* 115 */       RoundNameLabel roundNameLabel = new RoundNameLabel(i);
/* 116 */       this.widgets.add(roundNameLabel);
/* 117 */       this.roundNameLabels[i] = (Widget)roundNameLabel;
/*     */       
/* 119 */       RoundTeamsLabel roundTeamsLabel = new RoundTeamsLabel(i);
/* 120 */       this.widgets.add(roundTeamsLabel);
/* 121 */       this.roundTeamsLabels[i] = (Widget)roundTeamsLabel;
/*     */       
/* 123 */       RoundLegsButton roundLegsButton = new RoundLegsButton(i);
/* 124 */       this.widgets.add(roundLegsButton);
/* 125 */       this.roundLegsButtons[i] = (Widget)roundLegsButton;
/*     */       
/* 127 */       RoundExtraTimeButton roundExtraTimeButton = new RoundExtraTimeButton(i);
/* 128 */       this.widgets.add(roundExtraTimeButton);
/* 129 */       this.roundExtraTimeButtons[i] = (Widget)roundExtraTimeButton;
/*     */       
/* 131 */       RoundPenaltiesButton roundPenaltiesButton = new RoundPenaltiesButton(i);
/* 132 */       this.widgets.add(roundPenaltiesButton);
/* 133 */       this.roundPenaltiesButtons[i] = (Widget)roundPenaltiesButton;
/*     */     } 
/*     */     
/* 136 */     OkButton okButton = new OkButton();
/* 137 */     this.widgets.add(okButton);
/*     */     
/* 139 */     AbortButton abortButton = new AbortButton();
/* 140 */     this.widgets.add(abortButton);
/*     */   }
/*     */   
/*     */   private class CupNameButton
/*     */     extends InputButton {
/*     */     CupNameButton() {
/* 146 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry((1280 - 940) / 2, 120, 940, 36);
/* 147 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 148 */       setText(DesignDiyCup.this.cup.name, Font.Align.CENTER, Assets.font14);
/* 149 */       setEntryLimit(24);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 154 */       DesignDiyCup.this.cup.name = this.text;
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeatherButton
/*     */     extends Button {
/*     */     WeatherButton() {
/* 161 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 470, 165, 236, 36);
/* 162 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 163 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 168 */       DesignDiyCup.this.cup.weather = Competition.Weather.values()[EMath.rotate((Enum)DesignDiyCup.this.cup.weather, (Enum)Competition.Weather.BY_SEASON, (Enum)Competition.Weather.BY_PITCH_TYPE, 1)];
/* 169 */       setDirty(true);
/* 170 */       DesignDiyCup.this.seasonStartButton.setDirty(true);
/* 171 */       DesignDiyCup.this.seasonSeparatorButton.setDirty(true);
/* 172 */       DesignDiyCup.this.seasonEndButton.setDirty(true);
/* 173 */       DesignDiyCup.this.pitchTypeButton.setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 178 */       setText(Assets.strings.get(DesignDiyCup.this.cup.getWeatherLabel()));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonStartButton
/*     */     extends Button {
/*     */     SeasonStartButton() {
/* 185 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 232, 165, 176, 36);
/* 186 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 187 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 192 */       updateSeasonStart(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 197 */       updateSeasonStart(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 202 */       updateSeasonStart(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 207 */       updateSeasonStart(-1);
/*     */     }
/*     */     
/*     */     private void updateSeasonStart(int n) {
/* 211 */       DesignDiyCup.this.cup.seasonStart = Month.values()[EMath.rotate((Enum)DesignDiyCup.this.cup.seasonStart, (Enum)Month.JANUARY, (Enum)Month.DECEMBER, n)];
/* 212 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 217 */       setText(Assets.strings.get(Month.getLabel(DesignDiyCup.this.cup.seasonStart)));
/* 218 */       setVisible((DesignDiyCup.this.cup.weather == Competition.Weather.BY_SEASON));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonSeparatorButton
/*     */     extends Button {
/*     */     SeasonSeparatorButton() {
/* 225 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 54, 165, 36, 36);
/* 226 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 227 */       setText("-", Font.Align.CENTER, Assets.font14);
/* 228 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 233 */       setVisible((DesignDiyCup.this.cup.weather == Competition.Weather.BY_SEASON));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonEndButton
/*     */     extends Button {
/*     */     SeasonEndButton() {
/* 240 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 16, 165, 176, 36);
/* 241 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 242 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 247 */       updateSeasonEnd(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 252 */       updateSeasonEnd(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 257 */       updateSeasonEnd(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 262 */       updateSeasonEnd(-1);
/*     */     }
/*     */     
/*     */     private void updateSeasonEnd(int n) {
/* 266 */       DesignDiyCup.this.cup.seasonEnd = Month.values()[EMath.rotate((Enum)DesignDiyCup.this.cup.seasonEnd, (Enum)Month.JANUARY, (Enum)Month.DECEMBER, n)];
/* 267 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 272 */       setText(Assets.strings.get(Month.getLabel(DesignDiyCup.this.cup.seasonEnd)));
/* 273 */       setVisible((DesignDiyCup.this.cup.weather == Competition.Weather.BY_SEASON));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypeButton
/*     */     extends Button {
/*     */     PitchTypeButton() {
/* 280 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 232, 165, 392, 36);
/* 281 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 282 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 287 */       updatePitchType(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 292 */       updatePitchType(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 297 */       updatePitchType(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 302 */       updatePitchType(-1);
/*     */     }
/*     */     
/*     */     private void updatePitchType(int n) {
/* 306 */       DesignDiyCup.this.cup.pitchType = Pitch.Type.values()[EMath.rotate((Enum)DesignDiyCup.this.cup.pitchType, (Enum)Pitch.Type.FROZEN, (Enum)Pitch.Type.RANDOM, n)];
/* 307 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 312 */       setText(Assets.strings.get(Pitch.names[DesignDiyCup.this.cup.pitchType.ordinal()]));
/* 313 */       setVisible((DesignDiyCup.this.cup.weather == Competition.Weather.BY_PITCH_TYPE));
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeLabel
/*     */     extends Button {
/*     */     TimeLabel() {
/* 320 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 + 170, 165, 140, 36);
/* 321 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 322 */       setText(Assets.strings.get("TIME"), Font.Align.CENTER, Assets.font14);
/* 323 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeButton
/*     */     extends Button {
/*     */     TimeButton() {
/* 330 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 + 312, 165, 158, 36);
/* 331 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 332 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 337 */       updateTime(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 342 */       updateTime(-1);
/*     */     }
/*     */     
/*     */     private void updateTime(int n) {
/* 346 */       DesignDiyCup.this.cup.time = SceneSettings.Time.values()[EMath.rotate((Enum)DesignDiyCup.this.cup.time, (Enum)SceneSettings.Time.DAY, (Enum)SceneSettings.Time.NIGHT, n)];
/* 347 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 352 */       setText(Assets.strings.get(MatchSettings.getTimeLabel(DesignDiyCup.this.cup.time)));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesLabel
/*     */     extends Button {
/*     */     SubstitutesLabel() {
/* 359 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 470, 210, 305, 36);
/* 360 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 361 */       setText(Assets.strings.get("SUBSTITUTES"), Font.Align.CENTER, Assets.font14);
/* 362 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesButton
/*     */     extends Button {
/*     */     SubstitutesButton() {
/* 369 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 163, 210, 158, 36);
/* 370 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 371 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 376 */       updateSubstitutes(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 381 */       updateSubstitutes(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 386 */       updateSubstitutes(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 391 */       updateSubstitutes(-1);
/*     */     }
/*     */     
/*     */     private void updateSubstitutes(int n) {
/* 395 */       DesignDiyCup.this.cup.substitutions = EMath.slide(DesignDiyCup.this.cup.substitutions, 2, DesignDiyCup.this.cup.benchSize, n);
/* 396 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 401 */       setText(DesignDiyCup.this.cup.substitutions);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeLabel
/*     */     extends Button {
/*     */     BenchSizeLabel() {
/* 408 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 + 5, 210, 305, 36);
/* 409 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 410 */       setText(Assets.strings.get("BENCH SIZE"), Font.Align.CENTER, Assets.font14);
/* 411 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeButton
/*     */     extends Button {
/*     */     BenchSizeButton() {
/* 418 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 + 312, 210, 158, 36);
/* 419 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 420 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 425 */       updateBenchSize(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 430 */       updateBenchSize(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 435 */       updateBenchSize(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 440 */       updateBenchSize(-1);
/*     */     }
/*     */     
/*     */     private void updateBenchSize(int n) {
/* 444 */       DesignDiyCup.this.cup.benchSize = EMath.slide(DesignDiyCup.this.cup.benchSize, 2, 12, n);
/* 445 */       DesignDiyCup.this.cup.substitutions = Math.min(DesignDiyCup.this.cup.substitutions, DesignDiyCup.this.cup.benchSize);
/* 446 */       setDirty(true);
/* 447 */       DesignDiyCup.this.substitutesButton.setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 452 */       setText(DesignDiyCup.this.cup.benchSize);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundsLabel
/*     */     extends Button {
/*     */     RoundsLabel() {
/* 459 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 470, 255, 236, 36);
/* 460 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 461 */       setText(Assets.strings.get("ROUNDS"), Font.Align.CENTER, Assets.font14);
/* 462 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundsButton
/*     */     extends Button {
/*     */     RoundsButton() {
/* 469 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 232, 255, 158, 36);
/* 470 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 471 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 476 */       updateRounds(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 481 */       updateRounds(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 486 */       updateRounds(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 491 */       updateRounds(-1);
/*     */     }
/*     */     
/*     */     private void updateRounds(int n) {
/* 495 */       if (n == 1) {
/* 496 */         DesignDiyCup.this.cup.addRound();
/*     */       } else {
/* 498 */         DesignDiyCup.this.cup.removeRound();
/*     */       } 
/* 500 */       setDirty(true);
/* 501 */       for (int i = 0; i < 6; i++) {
/* 502 */         DesignDiyCup.this.roundNameLabels[i].setDirty(true);
/* 503 */         DesignDiyCup.this.roundTeamsLabels[i].setDirty(true);
/* 504 */         DesignDiyCup.this.roundLegsButtons[i].setDirty(true);
/* 505 */         DesignDiyCup.this.roundExtraTimeButtons[i].setDirty(true);
/* 506 */         DesignDiyCup.this.roundPenaltiesButtons[i].setDirty(true);
/*     */       } 
/* 508 */       DesignDiyCup.this.awayGoalsButton.setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 513 */       setText(DesignDiyCup.this.cup.rounds.size());
/*     */     }
/*     */   }
/*     */   
/*     */   private class AwayGoalsLabel
/*     */     extends Button {
/*     */     AwayGoalsLabel() {
/* 520 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 64, 255, 228, 36);
/* 521 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 522 */       setText(Assets.strings.get("AWAY GOALS"), Font.Align.CENTER, Assets.font14);
/* 523 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class AwayGoalsButton
/*     */     extends Button {
/*     */     AwayGoalsButton() {
/* 530 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 + 166, 255, 304, 36);
/* 531 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 532 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 537 */       updateAwayGoals(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 542 */       updateAwayGoals(-1);
/*     */     }
/*     */     
/*     */     private void updateAwayGoals(int n) {
/* 546 */       DesignDiyCup.this.cup.awayGoals = Competition.AwayGoals.values()[EMath.rotate(DesignDiyCup.this.cup.awayGoals.ordinal(), 0, 2, n)];
/* 547 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 552 */       setText(Assets.strings.get(DesignDiyCup.this.cup.getAwayGoalsLabel(DesignDiyCup.this.cup.awayGoals)));
/* 553 */       setVisible(DesignDiyCup.this.cup.hasTwoLegsRound());
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamsLabel
/*     */     extends Label {
/*     */     TeamsLabel() {
/* 560 */       setText(Assets.strings.get("TEAMS"), Font.Align.CENTER, Assets.font14);
/* 561 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setPosition(1280 / 2 - 186, 325);
/*     */     }
/*     */   }
/*     */   
/*     */   private class DescriptionLabel
/*     */     extends Label {
/*     */     DescriptionLabel() {
/* 568 */       setText(Assets.strings.get("DESCRIPTION"), Font.Align.CENTER, Assets.font14);
/* 569 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setPosition(1280 / 2 + 115, 325);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundNameLabel
/*     */     extends Button {
/*     */     private int round;
/*     */     
/*     */     RoundNameLabel(int round) {
/* 578 */       this.round = round;
/* 579 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 470, 344 + 34 * round, 248, 32);
/* 580 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 581 */       setText("", Font.Align.LEFT, Assets.font14);
/* 582 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 587 */       setVisible((this.round < DesignDiyCup.this.cup.rounds.size()));
/* 588 */       if (this.visible)
/* 589 */         setText(Assets.strings.get(((Round)DesignDiyCup.this.cup.rounds.get(this.round)).name)); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundTeamsLabel
/*     */     extends Button
/*     */   {
/*     */     private int round;
/*     */     
/*     */     RoundTeamsLabel(int round) {
/* 599 */       this.round = round;
/* 600 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 212, 344 + 34 * round, 50, 32);
/* 601 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 602 */       setText("", Font.Align.CENTER, Assets.font14);
/* 603 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 608 */       setText(DesignDiyCup.this.cup.getRoundTeams(this.round));
/* 609 */       setVisible((this.round < DesignDiyCup.this.cup.rounds.size()));
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundLegsButton
/*     */     extends Button {
/*     */     private int round;
/*     */     
/*     */     RoundLegsButton(int round) {
/* 618 */       this.round = round;
/* 619 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 152, 344 + 34 * round, 138, 32);
/* 620 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 621 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 626 */       updateLegs(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 631 */       updateLegs(-1);
/*     */     }
/*     */     
/*     */     private void updateLegs(int n) {
/* 635 */       ((Round)DesignDiyCup.this.cup.rounds.get(this.round)).numberOfLegs = EMath.rotate(((Round)DesignDiyCup.this.cup.rounds.get(this.round)).numberOfLegs, 1, 2, n);
/* 636 */       setDirty(true);
/* 637 */       DesignDiyCup.this.awayGoalsButton.setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 642 */       setVisible((this.round < DesignDiyCup.this.cup.rounds.size()));
/* 643 */       if (this.visible)
/* 644 */         setText(Assets.strings.get(((Round)DesignDiyCup.this.cup.rounds.get(this.round)).getLegsLabel())); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundExtraTimeButton
/*     */     extends Button
/*     */   {
/*     */     private int round;
/*     */     
/*     */     RoundExtraTimeButton(int round) {
/* 654 */       this.round = round;
/* 655 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 - 12, 344 + 34 * round, 240, 32);
/* 656 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 657 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 662 */       updateExtraTime(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 667 */       updateExtraTime(-1);
/*     */     }
/*     */     
/*     */     private void updateExtraTime(int n) {
/* 671 */       ((Round)DesignDiyCup.this.cup.rounds.get(this.round)).extraTime = Round.ExtraTime.values()[EMath.rotate(((Round)DesignDiyCup.this.cup.rounds.get(this.round)).extraTime.ordinal(), 0, 2, n)];
/* 672 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 677 */       setVisible((this.round < DesignDiyCup.this.cup.rounds.size()));
/* 678 */       if (this.visible)
/* 679 */         setText(Assets.strings.get(((Round)DesignDiyCup.this.cup.rounds.get(this.round)).getExtraTimeLabel())); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundPenaltiesButton
/*     */     extends Button
/*     */   {
/*     */     private int round;
/*     */     
/*     */     RoundPenaltiesButton(int round) {
/* 689 */       this.round = round;
/* 690 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry(1280 / 2 + 230, 344 + 34 * round, 240, 32);
/* 691 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 692 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 697 */       updatePenalties(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 702 */       updatePenalties(-1);
/*     */     }
/*     */     
/*     */     private void updatePenalties(int n) {
/* 706 */       ((Round)DesignDiyCup.this.cup.rounds.get(this.round)).penalties = Round.Penalties.values()[EMath.rotate(((Round)DesignDiyCup.this.cup.rounds.get(this.round)).penalties.ordinal(), 0, 2, n)];
/* 707 */       setDirty(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 712 */       setVisible((this.round < DesignDiyCup.this.cup.rounds.size()));
/* 713 */       if (this.visible)
/* 714 */         setText(Assets.strings.get(((Round)DesignDiyCup.this.cup.rounds.get(this.round)).getPenaltiesLabel())); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class OkButton
/*     */     extends Button
/*     */   {
/*     */     OkButton() {
/* 722 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 723 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry((1280 - 180) / 2, 605, 180, 38);
/* 724 */       setText(Assets.strings.get("OK"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 729 */       DesignDiyCup.navigation.folder = Assets.teamsRootFolder;
/* 730 */       DesignDiyCup.navigation.competition = (Competition)DesignDiyCup.this.cup;
/* 731 */       DesignDiyCup.this.game.setScreen((Screen)new SelectFolder(DesignDiyCup.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 738 */       setColor(13107214);
/* 739 */       Objects.requireNonNull(DesignDiyCup.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 740 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 745 */       DesignDiyCup.this.game.setScreen((Screen)new Main(DesignDiyCup.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DesignDiyCup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */