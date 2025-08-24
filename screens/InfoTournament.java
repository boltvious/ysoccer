/*     */ package com.ygames.ysoccer.screens;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.tournament.Round;
/*     */ import com.ygames.ysoccer.competitions.tournament.Tournament;
/*     */ import com.ygames.ysoccer.competitions.tournament.groups.Groups;
/*     */ import com.ygames.ysoccer.competitions.tournament.knockout.Knockout;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Month;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Picture;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.MatchSettings;
/*     */ import com.ygames.ysoccer.match.Pitch;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class InfoTournament extends GLScreen {
/*     */   private Tournament tournament;
/*     */   
/*     */   InfoTournament(GLGame game) {
/*  25 */     super(game);
/*     */     
/*  27 */     this.background = game.stateBackground;
/*     */     
/*  29 */     this.tournament = (Tournament)game.competition;
/*     */ 
/*     */     
/*  32 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, this.tournament.name, game.stateColor.body.intValue());
/*  33 */     this.widgets.add(titleBar);
/*     */     
/*  35 */     WeatherButton weatherButton = new WeatherButton();
/*  36 */     this.widgets.add(weatherButton);
/*     */     
/*  38 */     SeasonStartButton seasonStartButton = new SeasonStartButton();
/*  39 */     this.widgets.add(seasonStartButton);
/*     */     
/*  41 */     SeasonSeparatorButton seasonSeparatorButton = new SeasonSeparatorButton();
/*  42 */     this.widgets.add(seasonSeparatorButton);
/*     */     
/*  44 */     SeasonEndButton seasonEndButton = new SeasonEndButton();
/*  45 */     this.widgets.add(seasonEndButton);
/*     */     
/*  47 */     PitchTypeButton pitchTypeButton = new PitchTypeButton();
/*  48 */     this.widgets.add(pitchTypeButton);
/*     */     
/*  50 */     TimeLabel timeLabel = new TimeLabel();
/*  51 */     this.widgets.add(timeLabel);
/*     */     
/*  53 */     TimeButton timeButton = new TimeButton();
/*  54 */     this.widgets.add(timeButton);
/*     */     
/*  56 */     SubstitutesLabel substitutesLabel = new SubstitutesLabel();
/*  57 */     this.widgets.add(substitutesLabel);
/*     */     
/*  59 */     SubstitutesButton substitutesButton = new SubstitutesButton();
/*  60 */     this.widgets.add(substitutesButton);
/*     */     
/*  62 */     BenchSizeLabel benchSizeLabel = new BenchSizeLabel();
/*  63 */     this.widgets.add(benchSizeLabel);
/*     */     
/*  65 */     BenchSizeButton benchSizeButton = new BenchSizeButton();
/*  66 */     this.widgets.add(benchSizeButton);
/*     */     
/*  68 */     AwayGoalsLabel awayGoalsLabel = new AwayGoalsLabel();
/*  69 */     this.widgets.add(awayGoalsLabel);
/*     */     
/*  71 */     AwayGoalsButton awayGoalsButton = new AwayGoalsButton();
/*  72 */     this.widgets.add(awayGoalsButton);
/*     */     
/*  74 */     TeamsLabel teamsLabel = new TeamsLabel();
/*  75 */     this.widgets.add(teamsLabel);
/*     */     
/*  77 */     SeededLabel seededLabel = new SeededLabel();
/*  78 */     this.widgets.add(seededLabel);
/*     */     
/*  80 */     DescriptionLabel descriptionLabel = new DescriptionLabel();
/*  81 */     this.widgets.add(descriptionLabel);
/*     */ 
/*     */     
/*  84 */     for (int i = 0; i < this.tournament.rounds.size(); i++) {
/*  85 */       RoundLegsButton roundLegsButton; RoundExtraTimeButton roundExtraTimeButton; RoundPenaltiesButton roundPenaltiesButton; RoundPointsForAWinButton roundPointsForAWinButton; RoundPlayEachTeamButton roundPlayEachTeamButton; Knockout knockout; Groups groups; RoundNumberLabel roundNumberLabel = new RoundNumberLabel(i);
/*  86 */       this.widgets.add(roundNumberLabel);
/*     */       
/*  88 */       RoundTeamsButton roundTeamsButton = new RoundTeamsButton(i);
/*  89 */       this.widgets.add(roundTeamsButton);
/*     */       
/*  91 */       RoundGroupsButton roundGroupsButton = new RoundGroupsButton(i);
/*  92 */       this.widgets.add(roundGroupsButton);
/*     */       
/*  94 */       RoundSeededButton roundSeededButton = new RoundSeededButton(i);
/*  95 */       this.widgets.add(roundSeededButton);
/*     */       
/*  97 */       Round round = this.tournament.rounds.get(i);
/*  98 */       switch (round.type) {
/*     */         case KNOCKOUT:
/* 100 */           knockout = (Knockout)round;
/*     */           
/* 102 */           roundLegsButton = new RoundLegsButton(i, knockout);
/* 103 */           this.widgets.add(roundLegsButton);
/*     */           
/* 105 */           roundExtraTimeButton = new RoundExtraTimeButton(i, knockout);
/* 106 */           this.widgets.add(roundExtraTimeButton);
/*     */           
/* 108 */           roundPenaltiesButton = new RoundPenaltiesButton(i, knockout);
/* 109 */           this.widgets.add(roundPenaltiesButton);
/*     */           break;
/*     */         
/*     */         case GROUPS:
/* 113 */           groups = (Groups)round;
/*     */           
/* 115 */           roundPointsForAWinButton = new RoundPointsForAWinButton(i, groups);
/* 116 */           this.widgets.add(roundPointsForAWinButton);
/*     */           
/* 118 */           roundPlayEachTeamButton = new RoundPlayEachTeamButton(i, groups);
/* 119 */           this.widgets.add(roundPlayEachTeamButton);
/*     */           break;
/*     */       } 
/* 122 */       if (i < this.tournament.rounds.size() - 1) {
/* 123 */         ShortArrowPicture shortArrowPicture = new ShortArrowPicture(i);
/* 124 */         this.widgets.add(shortArrowPicture);
/*     */       } 
/*     */       
/* 127 */       RoundQualificationLabel roundQualificationLabel = new RoundQualificationLabel(i);
/* 128 */       this.widgets.add(roundQualificationLabel);
/*     */     } 
/*     */     
/* 131 */     ExitButton exitButton = new ExitButton();
/* 132 */     this.widgets.add(exitButton);
/*     */     
/* 134 */     setSelectedWidget((Widget)exitButton);
/*     */   }
/*     */   
/*     */   private class WeatherButton
/*     */     extends Button {
/*     */     WeatherButton() {
/* 140 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 470, 145, 236, 36);
/* 141 */       setColor(6710886);
/* 142 */       setText(Assets.strings.get(InfoTournament.this.tournament.getWeatherLabel()), Font.Align.CENTER, Assets.font14);
/* 143 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonStartButton
/*     */     extends Button {
/*     */     SeasonStartButton() {
/* 150 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 232, 145, 176, 36);
/* 151 */       setColor(6710886);
/* 152 */       setText(Assets.strings.get(Month.getLabel(InfoTournament.this.tournament.seasonStart)), Font.Align.CENTER, Assets.font14);
/* 153 */       setVisible((InfoTournament.this.tournament.weather == Competition.Weather.BY_SEASON));
/* 154 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonSeparatorButton
/*     */     extends Button {
/*     */     SeasonSeparatorButton() {
/* 161 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 54, 145, 36, 36);
/* 162 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 163 */       setText("-", Font.Align.CENTER, Assets.font14);
/* 164 */       setVisible((InfoTournament.this.tournament.weather == Competition.Weather.BY_SEASON));
/* 165 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeasonEndButton
/*     */     extends Button {
/*     */     SeasonEndButton() {
/* 172 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 16, 145, 176, 36);
/* 173 */       setColor(6710886);
/* 174 */       setText(Assets.strings.get(Month.getLabel(InfoTournament.this.tournament.seasonEnd)), Font.Align.CENTER, Assets.font14);
/* 175 */       setVisible((InfoTournament.this.tournament.weather == Competition.Weather.BY_SEASON));
/* 176 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypeButton
/*     */     extends Button {
/*     */     PitchTypeButton() {
/* 183 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 232, 145, 392, 36);
/* 184 */       setColor(6710886);
/* 185 */       setText(Assets.strings.get(Pitch.names[InfoTournament.this.tournament.pitchType.ordinal()]), Font.Align.CENTER, Assets.font14);
/* 186 */       setVisible((InfoTournament.this.tournament.weather == Competition.Weather.BY_PITCH_TYPE));
/* 187 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeLabel
/*     */     extends Button {
/*     */     TimeLabel() {
/* 194 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 + 170, 145, 140, 36);
/* 195 */       setColor(6710886);
/* 196 */       setText(Assets.strings.get("TIME"), Font.Align.CENTER, Assets.font14);
/* 197 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeButton
/*     */     extends Button {
/*     */     TimeButton() {
/* 204 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 + 312, 145, 158, 36);
/* 205 */       setColor(6710886);
/* 206 */       setText(Assets.strings.get(MatchSettings.getTimeLabel(InfoTournament.this.tournament.time)), Font.Align.CENTER, Assets.font14);
/* 207 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesLabel
/*     */     extends Button {
/*     */     SubstitutesLabel() {
/* 214 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 470, 190, 244, 36);
/* 215 */       setColor(6710886);
/* 216 */       setText(Assets.strings.get("SUBSTITUTES"), Font.Align.CENTER, Assets.font14);
/* 217 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SubstitutesButton
/*     */     extends Button {
/*     */     SubstitutesButton() {
/* 224 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 224, 190, 52, 36);
/* 225 */       setColor(6710886);
/* 226 */       setText(InfoTournament.this.tournament.substitutions, Font.Align.CENTER, Assets.font14);
/* 227 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeLabel
/*     */     extends Button {
/*     */     BenchSizeLabel() {
/* 234 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 170, 190, 94, 36);
/* 235 */       setColor(6710886);
/* 236 */       setText(Assets.strings.get("SUBSTITUTES.FROM"), Font.Align.CENTER, Assets.font14);
/* 237 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeButton
/*     */     extends Button {
/*     */     BenchSizeButton() {
/* 244 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 74, 190, 52, 36);
/* 245 */       setColor(6710886);
/* 246 */       setText(InfoTournament.this.tournament.benchSize, Font.Align.CENTER, Assets.font14);
/* 247 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class AwayGoalsLabel
/*     */     extends Button {
/*     */     AwayGoalsLabel() {
/* 254 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 12, 190, 206, 36);
/* 255 */       setColor(6710886);
/* 256 */       setText(Assets.strings.get("AWAY GOALS"), Font.Align.CENTER, Assets.font14);
/* 257 */       setVisible(InfoTournament.this.tournament.hasTwoLegsRound());
/* 258 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class AwayGoalsButton
/*     */     extends Button {
/*     */     AwayGoalsButton() {
/* 265 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 + 196, 190, 274, 36);
/* 266 */       setColor(6710886);
/* 267 */       setText(Assets.strings.get(InfoTournament.this.tournament.getAwayGoalsLabel(InfoTournament.this.tournament.awayGoals)), Font.Align.CENTER, Assets.font14);
/* 268 */       setVisible(InfoTournament.this.tournament.hasTwoLegsRound());
/* 269 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamsLabel
/*     */     extends Label {
/*     */     TeamsLabel() {
/* 276 */       setText(Assets.strings.get("TEAMS"), Font.Align.CENTER, Assets.font14);
/* 277 */       Objects.requireNonNull(InfoTournament.this.game.gui); setPosition(1280 / 2 - 446, 260);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SeededLabel
/*     */     extends Label {
/*     */     SeededLabel() {
/* 284 */       setText(Assets.strings.get("SEEDED"), Font.Align.CENTER, Assets.font14);
/* 285 */       Objects.requireNonNull(InfoTournament.this.game.gui); setPosition(1280 / 2 - 149, 260);
/*     */     }
/*     */   }
/*     */   
/*     */   private class DescriptionLabel
/*     */     extends Label {
/*     */     DescriptionLabel() {
/* 292 */       setText(Assets.strings.get("DESCRIPTION"), Font.Align.CENTER, Assets.font14);
/* 293 */       Objects.requireNonNull(InfoTournament.this.game.gui); setPosition(1280 / 2 + 182, 260);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundNumberLabel
/*     */     extends Label {
/*     */     RoundNumberLabel(int round) {
/* 300 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 512, 280 + 62 * round, 40, 32);
/* 301 */       setText(round + 1, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundTeamsButton
/*     */     extends Button {
/*     */     RoundTeamsButton(int round) {
/* 308 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 470, 280 + 62 * round, 48, 32);
/* 309 */       if (round == InfoTournament.this.tournament.currentRound) {
/* 310 */         setColor(4473924);
/*     */       } else {
/* 312 */         setColor(6710886);
/*     */       } 
/* 314 */       setText(((Round)InfoTournament.this.tournament.rounds.get(round)).numberOfTeams, Font.Align.CENTER, Assets.font14);
/* 315 */       setActive(false);
/*     */     } }
/*     */   
/*     */   private class RoundGroupsButton extends Button {
/*     */     RoundGroupsButton(int round) {
/*     */       String key;
/*     */       int groups;
/* 322 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 420, 280 + 62 * round, 248, 32);
/* 323 */       if (round == InfoTournament.this.tournament.currentRound) {
/* 324 */         setColor(4473924);
/*     */       } else {
/* 326 */         setColor(6710886);
/*     */       } 
/* 328 */       setText("", Font.Align.CENTER, Assets.font14);
/* 329 */       int teams = ((Round)InfoTournament.this.tournament.rounds.get(round)).numberOfTeams;
/*     */       
/* 331 */       switch (((Round)InfoTournament.this.tournament.rounds.get(round)).type) {
/*     */         case KNOCKOUT:
/* 333 */           switch (teams) {
/*     */             case 2:
/* 335 */               key = "FINAL";
/*     */               break;
/*     */             case 4:
/* 338 */               key = "SEMI-FINAL";
/*     */               break;
/*     */             case 8:
/* 341 */               key = "QUARTER-FINAL";
/*     */               break;
/*     */             default:
/* 344 */               key = "KNOCKOUT"; break;
/*     */           } 
/* 346 */           setText(Assets.strings.get(key));
/*     */           break;
/*     */         
/*     */         case GROUPS:
/* 350 */           groups = ((Groups)InfoTournament.this.tournament.rounds.get(round)).groups.size();
/* 351 */           if (groups == 1) {
/* 352 */             key = "%n GROUP OF %m";
/*     */           } else {
/* 354 */             key = "%n GROUPS OF %m";
/*     */           } 
/* 356 */           setText(Assets.strings.get(key)
/* 357 */               .replaceFirst("%n", "" + groups)
/* 358 */               .replaceFirst("%m", "" + (teams / groups)));
/*     */           break;
/*     */       } 
/*     */       
/* 362 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundSeededButton
/*     */     extends Button {
/*     */     RoundSeededButton(int round) {
/* 369 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 170, 280 + 62 * round, 42, 32);
/* 370 */       if (round == InfoTournament.this.tournament.currentRound) {
/* 371 */         setColor(4473924);
/*     */       } else {
/* 373 */         setColor(6710886);
/*     */       } 
/* 375 */       setText(((Round)InfoTournament.this.tournament.rounds.get(round)).seeded ? "*" : "-", Font.Align.CENTER, Assets.font14);
/* 376 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundLegsButton
/*     */     extends Button {
/*     */     RoundLegsButton(int round, Knockout knockout) {
/* 383 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 126, 280 + 62 * round, 138, 32);
/* 384 */       if (round == InfoTournament.this.tournament.currentRound) {
/* 385 */         setColor(4473924);
/*     */       } else {
/* 387 */         setColor(6710886);
/*     */       } 
/* 389 */       setText(Assets.strings.get((knockout.numberOfLegs == 1) ? "ONE LEG" : "TWO LEGS"), Font.Align.CENTER, Assets.font14);
/* 390 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundExtraTimeButton
/*     */     extends Button {
/*     */     RoundExtraTimeButton(int round, Knockout knockout) {
/* 397 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 + 14, 280 + 62 * round, 240, 32);
/* 398 */       if (round == InfoTournament.this.tournament.currentRound) {
/* 399 */         setColor(4473924);
/*     */       } else {
/* 401 */         setColor(6710886);
/*     */       } 
/* 403 */       setText(Assets.strings.get(Round.getExtraTimeLabel(knockout.extraTime)), Font.Align.CENTER, Assets.font14);
/* 404 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundPenaltiesButton
/*     */     extends Button {
/*     */     RoundPenaltiesButton(int round, Knockout knockout) {
/* 411 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 + 256, 280 + 62 * round, 240, 32);
/* 412 */       if (round == InfoTournament.this.tournament.currentRound) {
/* 413 */         setColor(4473924);
/*     */       } else {
/* 415 */         setColor(6710886);
/*     */       } 
/* 417 */       setText(Assets.strings.get(Round.getPenaltiesLabel(knockout.penalties)), Font.Align.CENTER, Assets.font14);
/* 418 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundPointsForAWinButton
/*     */     extends Button {
/*     */     RoundPointsForAWinButton(int round, Groups groups) {
/* 425 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 - 126, 280 + 62 * round, 310, 32);
/* 426 */       if (round == InfoTournament.this.tournament.currentRound) {
/* 427 */         setColor(4473924);
/*     */       } else {
/* 429 */         setColor(6710886);
/*     */       } 
/* 431 */       setText(Assets.strings.get("%n POINTS FOR A WIN").replaceFirst("%n", "" + groups.pointsForAWin), Font.Align.CENTER, Assets.font14);
/* 432 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RoundPlayEachTeamButton
/*     */     extends Button {
/*     */     RoundPlayEachTeamButton(int round, Groups groups) {
/* 439 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry(1280 / 2 + 186, 280 + 62 * round, 310, 32);
/* 440 */       if (round == InfoTournament.this.tournament.currentRound) {
/* 441 */         setColor(4473924);
/*     */       } else {
/* 443 */         setColor(6710886);
/*     */       } 
/* 445 */       setText(Assets.strings.get("PLAY EACH TEAM ×%n").replaceFirst("%n", "" + groups.rounds), Font.Align.CENTER, Assets.font14);
/* 446 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ShortArrowPicture
/*     */     extends Picture {
/*     */     ShortArrowPicture(int round) {
/* 453 */       setTextureRegion(Assets.shortArrow);
/* 454 */       Objects.requireNonNull(InfoTournament.this.game.gui); setPosition(1280 / 2 - 446, 326 + 62 * round);
/*     */     } }
/*     */   
/*     */   private class RoundQualificationLabel extends Label {
/*     */     RoundQualificationLabel(int round) {
/*     */       Knockout knockout;
/*     */       int groups, nextRoundTeams, runnersUp;
/* 461 */       Objects.requireNonNull(InfoTournament.this.game.gui); setPosition(1280 / 2, 326 + 62 * round);
/* 462 */       int teams = ((Round)InfoTournament.this.tournament.rounds.get(round)).numberOfTeams;
/*     */       
/* 464 */       String label = "";
/* 465 */       switch (((Round)InfoTournament.this.tournament.rounds.get(round)).type) {
/*     */         case KNOCKOUT:
/* 467 */           knockout = InfoTournament.this.tournament.rounds.get(round);
/* 468 */           if (teams == 2) {
/* 469 */             if (knockout.numberOfLegs == 1) {
/* 470 */               label = Assets.strings.get("TOURNAMENT.MATCH WINNER WINS TOURNAMENT"); break;
/*     */             } 
/* 472 */             label = Assets.strings.get("TOURNAMENT.MATCH WINNER ON AGGREGATE WINS TOURNAMENT");
/*     */             break;
/*     */           } 
/* 475 */           if (knockout.numberOfLegs == 1) {
/* 476 */             label = Assets.strings.get("TOURNAMENT.MATCH WINNERS QUALIFY"); break;
/*     */           } 
/* 478 */           label = Assets.strings.get("TOURNAMENT.MATCH WINNERS ON AGGREGATE QUALIFY");
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case GROUPS:
/* 484 */           groups = ((Groups)InfoTournament.this.tournament.rounds.get(round)).groups.size();
/* 485 */           if (groups == 1) {
/* 486 */             if (round == InfoTournament.this.tournament.rounds.size() - 1) {
/* 487 */               label = Assets.strings.get("TOURNAMENT.GROUP WINNER WINS TOURNAMENT");
/*     */               break;
/*     */             } 
/* 490 */             label = Assets.strings.get("TOURNAMENT.TOP %n IN GROUP QUALIFY").replaceFirst("%n", "" + ((Round)InfoTournament.this.tournament.rounds.get(round + 1)).numberOfTeams);
/*     */             break;
/*     */           } 
/* 493 */           nextRoundTeams = ((Round)InfoTournament.this.tournament.rounds.get(round + 1)).numberOfTeams;
/* 494 */           runnersUp = nextRoundTeams % groups;
/* 495 */           switch (runnersUp) {
/*     */             case 0:
/* 497 */               if (nextRoundTeams / groups == 1) {
/* 498 */                 label = Assets.strings.get("TOURNAMENT.WINNERS OF EACH GROUP QUALIFY");
/*     */                 break;
/*     */               } 
/* 501 */               label = Assets.strings.get("TOURNAMENT.TOP %n IN EACH GROUP QUALIFY").replaceFirst("%n", "" + (nextRoundTeams / groups));
/*     */               break;
/*     */ 
/*     */             
/*     */             case 1:
/* 506 */               if (nextRoundTeams / groups == 1) {
/* 507 */                 label = Assets.strings.get("TOURNAMENT.WINNERS OF EACH GROUP AND BEST RUNNER-UP QUALIFIES");
/*     */                 break;
/*     */               } 
/* 510 */               label = Assets.strings.get("TOURNAMENT.TOP %n IN EACH GROUP AND BEST RUNNER-UP QUALIFY").replaceFirst("%n", "" + (nextRoundTeams / groups));
/*     */               break;
/*     */           } 
/*     */ 
/*     */           
/* 515 */           if (nextRoundTeams / groups == 1) {
/*     */             
/* 517 */             label = Assets.strings.get("TOURNAMENT.WINNERS OF EACH GROUP AND BEST %n RUNNERS-UP QUALIFY").replaceFirst("%n", "" + runnersUp);
/*     */             
/*     */             break;
/*     */           } 
/* 521 */           label = Assets.strings.get("TOURNAMENT.TOP %n IN EACH GROUP AND BEST %m RUNNERS-UP QUALIFY").replaceFirst("%n", "" + (nextRoundTeams / groups)).replaceFirst("%m", "" + runnersUp);
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 528 */       setText("(" + label + ")", Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 535 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 536 */       Objects.requireNonNull(InfoTournament.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 537 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 542 */       InfoTournament.this.game.setScreen((Screen)new ViewStatistics(InfoTournament.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\InfoTournament.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */