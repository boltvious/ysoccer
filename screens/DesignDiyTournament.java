/*      */ package com.ygames.ysoccer.screens;
/*      */ 
/*      */ import com.badlogic.gdx.Screen;
/*      */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*      */ import com.ygames.ysoccer.competitions.Competition;
/*      */ import com.ygames.ysoccer.competitions.tournament.Round;
/*      */ import com.ygames.ysoccer.competitions.tournament.Tournament;
/*      */ import com.ygames.ysoccer.competitions.tournament.groups.Groups;
/*      */ import com.ygames.ysoccer.competitions.tournament.knockout.Knockout;
/*      */ import com.ygames.ysoccer.framework.Assets;
/*      */ import com.ygames.ysoccer.framework.EMath;
/*      */ import com.ygames.ysoccer.framework.Font;
/*      */ import com.ygames.ysoccer.framework.GLGame;
/*      */ import com.ygames.ysoccer.framework.GLScreen;
/*      */ import com.ygames.ysoccer.framework.Month;
/*      */ import com.ygames.ysoccer.gui.Button;
/*      */ import com.ygames.ysoccer.gui.InputButton;
/*      */ import com.ygames.ysoccer.gui.Label;
/*      */ import com.ygames.ysoccer.gui.Picture;
/*      */ import com.ygames.ysoccer.gui.Widget;
/*      */ import com.ygames.ysoccer.match.MatchSettings;
/*      */ import com.ygames.ysoccer.match.Pitch;
/*      */ import com.ygames.ysoccer.match.SceneSettings;
/*      */ import java.util.Objects;
/*      */ 
/*      */ class DesignDiyTournament
/*      */   extends GLScreen {
/*      */   Tournament tournament;
/*      */   private Widget seasonStartButton;
/*      */   private Widget seasonSeparatorButton;
/*      */   private Widget seasonEndButton;
/*      */   private Widget pitchTypeButton;
/*      */   private Widget substitutesButton;
/*      */   private Widget awayGoalsLabel;
/*      */   private Widget awayGoalsButton;
/*   36 */   private int[] roundTeams = new int[] { 24, 16, 8, 4, 2, 1, 0 };
/*   37 */   private int[] roundGroups = new int[] { 6, 0, 0, 0, 0, 0 };
/*   38 */   private boolean[] roundSeeded = new boolean[] { false, false, false, false, false, false };
/*      */   
/*   40 */   private Knockout[] knockouts = new Knockout[] { new Knockout(), new Knockout(), new Knockout(), new Knockout(), new Knockout(), new Knockout() };
/*   41 */   private Groups[] groups = new Groups[] { new Groups(), new Groups(), new Groups(), new Groups(), new Groups(), new Groups() };
/*      */   
/*   43 */   private Widget[] roundNumberLabels = new Widget[6];
/*   44 */   private Widget[] roundTeamsButtons = new Widget[6];
/*   45 */   private Widget[] roundGroupsButtons = new Widget[6];
/*   46 */   private Widget[] roundSeededButtons = new Widget[6];
/*   47 */   private Widget[] roundLegsButtons = new Widget[6];
/*   48 */   private Widget[] roundExtraTimeButtons = new Widget[6];
/*   49 */   private Widget[] roundPenaltiesButtons = new Widget[6];
/*   50 */   private Widget[] roundPointsFawButtons = new Widget[6];
/*   51 */   private Widget[] roundPlayEachTeamButtons = new Widget[6];
/*   52 */   private Widget[] roundShortArrowPictures = new Widget[5];
/*   53 */   private Widget[] roundQualificationLabels = new Widget[6];
/*      */   
/*      */   DesignDiyTournament(GLGame game) {
/*   56 */     super(game);
/*      */     
/*   58 */     this.background = game.stateBackground;
/*      */     
/*   60 */     this.tournament = new Tournament();
/*   61 */     this.tournament.name = Assets.strings.get("DIY TOURNAMENT");
/*   62 */     this.tournament.category = Competition.Category.DIY_COMPETITION;
/*      */ 
/*      */ 
/*      */     
/*   66 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("DESIGN DIY TOURNAMENT"), game.stateColor.body.intValue());
/*   67 */     this.widgets.add(titleBar);
/*      */     
/*   69 */     TournamentNameButton tournamentNameButton = new TournamentNameButton();
/*   70 */     this.widgets.add(tournamentNameButton);
/*      */     
/*   72 */     WeatherButton weatherButton = new WeatherButton();
/*   73 */     this.widgets.add(weatherButton);
/*      */     
/*   75 */     SeasonStartButton seasonStartButton = new SeasonStartButton();
/*   76 */     this.widgets.add(seasonStartButton);
/*   77 */     this.seasonStartButton = (Widget)seasonStartButton;
/*      */     
/*   79 */     SeasonSeparatorButton seasonSeparatorButton = new SeasonSeparatorButton();
/*   80 */     this.widgets.add(seasonSeparatorButton);
/*   81 */     this.seasonSeparatorButton = (Widget)seasonSeparatorButton;
/*      */     
/*   83 */     SeasonEndButton seasonEndButton = new SeasonEndButton();
/*   84 */     this.widgets.add(seasonEndButton);
/*   85 */     this.seasonEndButton = (Widget)seasonEndButton;
/*      */     
/*   87 */     PitchTypeButton pitchTypeButton = new PitchTypeButton();
/*   88 */     this.widgets.add(pitchTypeButton);
/*   89 */     this.pitchTypeButton = (Widget)pitchTypeButton;
/*      */     
/*   91 */     TimeLabel timeLabel = new TimeLabel();
/*   92 */     this.widgets.add(timeLabel);
/*      */     
/*   94 */     TimeButton timeButton = new TimeButton();
/*   95 */     this.widgets.add(timeButton);
/*      */     
/*   97 */     SubstitutesLabel substitutesLabel = new SubstitutesLabel();
/*   98 */     this.widgets.add(substitutesLabel);
/*      */     
/*  100 */     SubstitutesButton substitutesButton = new SubstitutesButton();
/*  101 */     this.widgets.add(substitutesButton);
/*  102 */     this.substitutesButton = (Widget)substitutesButton;
/*      */     
/*  104 */     BenchSizeLabel benchSizeLabel = new BenchSizeLabel();
/*  105 */     this.widgets.add(benchSizeLabel);
/*      */     
/*  107 */     BenchSizeButton benchSizeButton = new BenchSizeButton();
/*  108 */     this.widgets.add(benchSizeButton);
/*      */     
/*  110 */     AwayGoalsLabel awayGoalsLabel = new AwayGoalsLabel();
/*  111 */     this.widgets.add(awayGoalsLabel);
/*  112 */     this.awayGoalsLabel = (Widget)awayGoalsLabel;
/*      */     
/*  114 */     AwayGoalsButton awayGoalsButton = new AwayGoalsButton();
/*  115 */     this.widgets.add(awayGoalsButton);
/*  116 */     this.awayGoalsButton = (Widget)awayGoalsButton;
/*      */     
/*  118 */     TeamsLabel teamsLabel = new TeamsLabel();
/*  119 */     this.widgets.add(teamsLabel);
/*      */     
/*  121 */     SeededLabel seededLabel = new SeededLabel();
/*  122 */     this.widgets.add(seededLabel);
/*      */     
/*  124 */     DescriptionLabel descriptionLabel = new DescriptionLabel();
/*  125 */     this.widgets.add(descriptionLabel);
/*      */ 
/*      */     
/*  128 */     for (int i = 0; i < 6; i++) {
/*  129 */       RoundNumberLabel roundNumberLabel = new RoundNumberLabel(i);
/*  130 */       this.widgets.add(roundNumberLabel);
/*  131 */       this.roundNumberLabels[i] = (Widget)roundNumberLabel;
/*      */       
/*  133 */       RoundTeamsButton roundTeamsButton = new RoundTeamsButton(i);
/*  134 */       this.widgets.add(roundTeamsButton);
/*  135 */       this.roundTeamsButtons[i] = (Widget)roundTeamsButton;
/*  136 */       if (i == 0) setSelectedWidget((Widget)roundTeamsButton);
/*      */       
/*  138 */       RoundGroupsButton roundGroupsButton = new RoundGroupsButton(i);
/*  139 */       this.widgets.add(roundGroupsButton);
/*  140 */       this.roundGroupsButtons[i] = (Widget)roundGroupsButton;
/*      */       
/*  142 */       RoundSeededButton roundSeededButton = new RoundSeededButton(i);
/*  143 */       this.widgets.add(roundSeededButton);
/*  144 */       this.roundSeededButtons[i] = (Widget)roundSeededButton;
/*      */       
/*  146 */       RoundLegsButton roundLegsButton = new RoundLegsButton(i);
/*  147 */       this.widgets.add(roundLegsButton);
/*  148 */       this.roundLegsButtons[i] = (Widget)roundLegsButton;
/*      */       
/*  150 */       RoundExtraTimeButton roundExtraTimeButton = new RoundExtraTimeButton(i);
/*  151 */       this.widgets.add(roundExtraTimeButton);
/*  152 */       this.roundExtraTimeButtons[i] = (Widget)roundExtraTimeButton;
/*      */       
/*  154 */       RoundPenaltiesButton roundPenaltiesButton = new RoundPenaltiesButton(i);
/*  155 */       this.widgets.add(roundPenaltiesButton);
/*  156 */       this.roundPenaltiesButtons[i] = (Widget)roundPenaltiesButton;
/*      */       
/*  158 */       RoundPointsForAWinButton roundPointsForAWinButton = new RoundPointsForAWinButton(i);
/*  159 */       this.widgets.add(roundPointsForAWinButton);
/*  160 */       this.roundPointsFawButtons[i] = (Widget)roundPointsForAWinButton;
/*      */       
/*  162 */       RoundPlayEachTeamButton roundPlayEachTeamButton = new RoundPlayEachTeamButton(i);
/*  163 */       this.widgets.add(roundPlayEachTeamButton);
/*  164 */       this.roundPlayEachTeamButtons[i] = (Widget)roundPlayEachTeamButton;
/*      */       
/*  166 */       if (i < 5) {
/*  167 */         ShortArrowPicture shortArrowPicture = new ShortArrowPicture(i);
/*  168 */         this.widgets.add(shortArrowPicture);
/*  169 */         this.roundShortArrowPictures[i] = (Widget)shortArrowPicture;
/*      */       } 
/*      */       
/*  172 */       RoundQualificationLabel roundQualificationLabel = new RoundQualificationLabel(i);
/*  173 */       this.widgets.add(roundQualificationLabel);
/*  174 */       this.roundQualificationLabels[i] = (Widget)roundQualificationLabel;
/*      */     } 
/*      */     
/*  177 */     OkButton okButton = new OkButton();
/*  178 */     this.widgets.add(okButton);
/*      */     
/*  180 */     AbortButton abortButton = new AbortButton();
/*  181 */     this.widgets.add(abortButton);
/*      */   }
/*      */   
/*      */   private class TournamentNameButton
/*      */     extends InputButton {
/*      */     TournamentNameButton() {
/*  187 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry((1280 - 940) / 2, 100, 940, 36);
/*  188 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  189 */       setText(DesignDiyTournament.this.tournament.name, Font.Align.CENTER, Assets.font14);
/*  190 */       setEntryLimit(24);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onChanged() {
/*  195 */       DesignDiyTournament.this.tournament.name = this.text;
/*      */     }
/*      */   }
/*      */   
/*      */   private class WeatherButton
/*      */     extends Button {
/*      */     WeatherButton() {
/*  202 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 470, 145, 236, 36);
/*  203 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  204 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  209 */       DesignDiyTournament.this.tournament.weather = Competition.Weather.values()[EMath.rotate((Enum)DesignDiyTournament.this.tournament.weather, (Enum)Competition.Weather.BY_SEASON, (Enum)Competition.Weather.BY_PITCH_TYPE, 1)];
/*  210 */       setDirty(true);
/*  211 */       DesignDiyTournament.this.seasonStartButton.setDirty(true);
/*  212 */       DesignDiyTournament.this.seasonSeparatorButton.setDirty(true);
/*  213 */       DesignDiyTournament.this.seasonEndButton.setDirty(true);
/*  214 */       DesignDiyTournament.this.pitchTypeButton.setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  219 */       setText(Assets.strings.get(DesignDiyTournament.this.tournament.getWeatherLabel()));
/*      */     }
/*      */   }
/*      */   
/*      */   private class SeasonStartButton
/*      */     extends Button {
/*      */     SeasonStartButton() {
/*  226 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 232, 145, 176, 36);
/*  227 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  228 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  233 */       updateSeasonStart(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  238 */       updateSeasonStart(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  243 */       updateSeasonStart(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  248 */       updateSeasonStart(-1);
/*      */     }
/*      */     
/*      */     private void updateSeasonStart(int n) {
/*  252 */       DesignDiyTournament.this.tournament.seasonStart = Month.values()[EMath.rotate((Enum)DesignDiyTournament.this.tournament.seasonStart, (Enum)Month.JANUARY, (Enum)Month.DECEMBER, n)];
/*  253 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  258 */       setText(Assets.strings.get(Month.getLabel(DesignDiyTournament.this.tournament.seasonStart)));
/*  259 */       setVisible((DesignDiyTournament.this.tournament.weather == Competition.Weather.BY_SEASON));
/*      */     }
/*      */   }
/*      */   
/*      */   private class SeasonSeparatorButton
/*      */     extends Button {
/*      */     SeasonSeparatorButton() {
/*  266 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 54, 145, 36, 36);
/*  267 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/*  268 */       setText("-", Font.Align.CENTER, Assets.font14);
/*  269 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  274 */       setVisible((DesignDiyTournament.this.tournament.weather == Competition.Weather.BY_SEASON));
/*      */     }
/*      */   }
/*      */   
/*      */   private class SeasonEndButton
/*      */     extends Button {
/*      */     SeasonEndButton() {
/*  281 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 16, 145, 176, 36);
/*  282 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  283 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  288 */       updateSeasonEnd(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  293 */       updateSeasonEnd(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  298 */       updateSeasonEnd(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  303 */       updateSeasonEnd(-1);
/*      */     }
/*      */     
/*      */     private void updateSeasonEnd(int n) {
/*  307 */       DesignDiyTournament.this.tournament.seasonEnd = Month.values()[EMath.rotate((Enum)DesignDiyTournament.this.tournament.seasonEnd, (Enum)Month.JANUARY, (Enum)Month.DECEMBER, n)];
/*  308 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  313 */       setText(Assets.strings.get(Month.getLabel(DesignDiyTournament.this.tournament.seasonEnd)));
/*  314 */       setVisible((DesignDiyTournament.this.tournament.weather == Competition.Weather.BY_SEASON));
/*      */     }
/*      */   }
/*      */   
/*      */   private class PitchTypeButton
/*      */     extends Button {
/*      */     PitchTypeButton() {
/*  321 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 232, 145, 392, 36);
/*  322 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  323 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  328 */       updatePitchType(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  333 */       updatePitchType(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  338 */       updatePitchType(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  343 */       updatePitchType(-1);
/*      */     }
/*      */     
/*      */     private void updatePitchType(int n) {
/*  347 */       DesignDiyTournament.this.tournament.pitchType = Pitch.Type.values()[EMath.rotate((Enum)DesignDiyTournament.this.tournament.pitchType, (Enum)Pitch.Type.FROZEN, (Enum)Pitch.Type.RANDOM, n)];
/*  348 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  353 */       setText(Assets.strings.get(Pitch.names[DesignDiyTournament.this.tournament.pitchType.ordinal()]));
/*  354 */       setVisible((DesignDiyTournament.this.tournament.weather == Competition.Weather.BY_PITCH_TYPE));
/*      */     }
/*      */   }
/*      */   
/*      */   private class TimeLabel
/*      */     extends Button {
/*      */     TimeLabel() {
/*  361 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 + 170, 145, 140, 36);
/*  362 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/*  363 */       setText(Assets.strings.get("TIME"), Font.Align.CENTER, Assets.font14);
/*  364 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class TimeButton
/*      */     extends Button {
/*      */     TimeButton() {
/*  371 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 + 312, 145, 158, 36);
/*  372 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  373 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  378 */       updateTime(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  383 */       updateTime(-1);
/*      */     }
/*      */     
/*      */     private void updateTime(int n) {
/*  387 */       DesignDiyTournament.this.tournament.time = SceneSettings.Time.values()[EMath.rotate((Enum)DesignDiyTournament.this.tournament.time, (Enum)SceneSettings.Time.DAY, (Enum)SceneSettings.Time.NIGHT, n)];
/*  388 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  393 */       setText(Assets.strings.get(MatchSettings.getTimeLabel(DesignDiyTournament.this.tournament.time)));
/*      */     }
/*      */   }
/*      */   
/*      */   private class SubstitutesLabel
/*      */     extends Button {
/*      */     SubstitutesLabel() {
/*  400 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 470, 190, 244, 36);
/*  401 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/*  402 */       setText(Assets.strings.get("SUBSTITUTES"), Font.Align.CENTER, Assets.font14);
/*  403 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class SubstitutesButton
/*      */     extends Button {
/*      */     SubstitutesButton() {
/*  410 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 224, 190, 52, 36);
/*  411 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  412 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  417 */       updateSubstitutes(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  422 */       updateSubstitutes(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  427 */       updateSubstitutes(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  432 */       updateSubstitutes(-1);
/*      */     }
/*      */     
/*      */     private void updateSubstitutes(int n) {
/*  436 */       DesignDiyTournament.this.tournament.substitutions = EMath.slide(DesignDiyTournament.this.tournament.substitutions, 2, DesignDiyTournament.this.tournament.benchSize, n);
/*  437 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  442 */       setText(DesignDiyTournament.this.tournament.substitutions);
/*      */     }
/*      */   }
/*      */   
/*      */   private class BenchSizeLabel
/*      */     extends Button {
/*      */     BenchSizeLabel() {
/*  449 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 170, 190, 94, 36);
/*  450 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/*  451 */       setText(Assets.strings.get("SUBSTITUTES.FROM"), Font.Align.CENTER, Assets.font14);
/*  452 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class BenchSizeButton
/*      */     extends Button {
/*      */     BenchSizeButton() {
/*  459 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 74, 190, 52, 36);
/*  460 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  461 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  466 */       updateBenchSize(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  471 */       updateBenchSize(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  476 */       updateBenchSize(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  481 */       updateBenchSize(-1);
/*      */     }
/*      */     
/*      */     private void updateBenchSize(int n) {
/*  485 */       DesignDiyTournament.this.tournament.benchSize = EMath.slide(DesignDiyTournament.this.tournament.benchSize, 2, 12, n);
/*  486 */       DesignDiyTournament.this.tournament.substitutions = Math.min(DesignDiyTournament.this.tournament.substitutions, DesignDiyTournament.this.tournament.benchSize);
/*  487 */       setDirty(true);
/*  488 */       DesignDiyTournament.this.substitutesButton.setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  493 */       setText(DesignDiyTournament.this.tournament.benchSize);
/*      */     }
/*      */   }
/*      */   
/*      */   private class AwayGoalsLabel
/*      */     extends Button {
/*      */     AwayGoalsLabel() {
/*  500 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 12, 190, 206, 36);
/*  501 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/*  502 */       setText(Assets.strings.get("AWAY GOALS"), Font.Align.CENTER, Assets.font14);
/*  503 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  508 */       setVisible(false);
/*  509 */       for (int i = 0; i < 6; i++) {
/*  510 */         if (DesignDiyTournament.this.roundGroups[i] == 0 && (DesignDiyTournament.this.knockouts[i]).numberOfLegs == 2) {
/*  511 */           setVisible(true);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class AwayGoalsButton
/*      */     extends Button {
/*      */     AwayGoalsButton() {
/*  521 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 + 196, 190, 274, 36);
/*  522 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  523 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  528 */       updateAwayGoals(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  533 */       updateAwayGoals(-1);
/*      */     }
/*      */     
/*      */     private void updateAwayGoals(int n) {
/*  537 */       DesignDiyTournament.this.tournament.awayGoals = Competition.AwayGoals.values()[EMath.rotate(DesignDiyTournament.this.tournament.awayGoals.ordinal(), 0, 2, n)];
/*  538 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  543 */       setText(Assets.strings.get(DesignDiyTournament.this.tournament.getAwayGoalsLabel(DesignDiyTournament.this.tournament.awayGoals)));
/*  544 */       setVisible(false);
/*  545 */       for (int i = 0; i < 6; i++) {
/*  546 */         if (DesignDiyTournament.this.roundGroups[i] == 0 && (DesignDiyTournament.this.knockouts[i]).numberOfLegs == 2) {
/*  547 */           setVisible(true);
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class TeamsLabel
/*      */     extends Label {
/*      */     TeamsLabel() {
/*  557 */       setText(Assets.strings.get("TEAMS"), Font.Align.CENTER, Assets.font14);
/*  558 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setPosition(1280 / 2 - 446, 260);
/*      */     }
/*      */   }
/*      */   
/*      */   private class SeededLabel
/*      */     extends Label {
/*      */     SeededLabel() {
/*  565 */       setText(Assets.strings.get("SEEDED"), Font.Align.CENTER, Assets.font14);
/*  566 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setPosition(1280 / 2 - 149, 260);
/*      */     }
/*      */   }
/*      */   
/*      */   private class DescriptionLabel
/*      */     extends Label {
/*      */     DescriptionLabel() {
/*  573 */       setText(Assets.strings.get("DESCRIPTION"), Font.Align.CENTER, Assets.font14);
/*  574 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setPosition(1280 / 2 + 182, 260);
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundNumberLabel
/*      */     extends Label {
/*      */     private int round;
/*      */     
/*      */     RoundNumberLabel(int round) {
/*  583 */       this.round = round;
/*  584 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 512, 280 + 62 * round, 40, 32);
/*  585 */       setText(round + 1, Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  590 */       setVisible((DesignDiyTournament.this.roundTeams[this.round] > 1));
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundTeamsButton
/*      */     extends Button {
/*      */     private int round;
/*      */     
/*      */     RoundTeamsButton(int round) {
/*  599 */       this.round = round;
/*  600 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 470, 280 + 62 * round, 48, 32);
/*  601 */       setColor(9445754);
/*  602 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  607 */       incrementTeams();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  612 */       incrementTeams();
/*      */     }
/*      */     
/*      */     private void incrementTeams() {
/*  616 */       int teams = DesignDiyTournament.this.roundTeams[this.round];
/*  617 */       boolean found = false;
/*  618 */       while (!found) {
/*  619 */         teams++;
/*      */         
/*  621 */         int maxTeams = 64;
/*  622 */         if (this.round == 5) {
/*  623 */           maxTeams = 24;
/*      */         }
/*  625 */         else if (DesignDiyTournament.this.roundTeams[this.round + 1] > 0) {
/*  626 */           maxTeams = Math.min(maxTeams, 24 * DesignDiyTournament.this.roundTeams[this.round + 1]);
/*      */         } 
/*      */         
/*  629 */         if (teams > maxTeams) {
/*      */           return;
/*      */         }
/*  632 */         if (this.round > 0 && teams >= DesignDiyTournament.this.roundTeams[this.round - 1]) {
/*      */           return;
/*      */         }
/*  635 */         int maxGroups = 8;
/*  636 */         if (DesignDiyTournament.this.roundTeams[this.round + 1] > 0) {
/*  637 */           maxGroups = Math.min(maxGroups, DesignDiyTournament.this.roundTeams[this.round + 1]);
/*      */         }
/*  639 */         for (int d = 1; d <= maxGroups; d++) {
/*  640 */           if (teams % d == 0 && teams / d <= 24) {
/*  641 */             found = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  648 */       DesignDiyTournament.this.roundTeams[this.round] = teams;
/*      */ 
/*      */       
/*  651 */       if (teams > 1 && DesignDiyTournament.this.roundTeams[this.round + 1] == 0) {
/*  652 */         DesignDiyTournament.this.roundTeams[this.round + 1] = 1;
/*      */       }
/*      */       
/*  655 */       DesignDiyTournament.this.resetRoundGroups(this.round);
/*      */ 
/*      */       
/*  658 */       if (DesignDiyTournament.this.invalidKnockout(this.round)) {
/*  659 */         DesignDiyTournament.this.resetRoundGroups(this.round - 1);
/*      */       }
/*      */       
/*  662 */       DesignDiyTournament.this.setRoundButtonsDirty(this.round);
/*  663 */       if (this.round > 0) {
/*  664 */         DesignDiyTournament.this.setRoundButtonsDirty(this.round - 1);
/*      */       }
/*  666 */       if (this.round < 5) {
/*  667 */         DesignDiyTournament.this.setRoundButtonsDirty(this.round + 1);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  673 */       decrementTeams();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  678 */       decrementTeams();
/*      */     }
/*      */     
/*      */     private void decrementTeams() {
/*  682 */       int teams = DesignDiyTournament.this.roundTeams[this.round];
/*  683 */       boolean found = false;
/*  684 */       while (!found) {
/*  685 */         teams--;
/*      */         
/*  687 */         if (this.round == 0 && teams < 2) {
/*      */           return;
/*      */         }
/*  690 */         if (teams == 0) {
/*      */           return;
/*      */         }
/*  693 */         if (teams > 1 && teams <= DesignDiyTournament.this.roundTeams[this.round + 1]) {
/*      */           return;
/*      */         }
/*  696 */         if (this.round > 0 && teams < DesignDiyTournament.this.roundGroups[this.round - 1]) {
/*      */           return;
/*      */         }
/*  699 */         int maxGroups = 8;
/*  700 */         if (DesignDiyTournament.this.roundTeams[this.round + 1] > 0) {
/*  701 */           maxGroups = Math.min(maxGroups, DesignDiyTournament.this.roundTeams[this.round + 1]);
/*      */         }
/*  703 */         for (int d = 1; d <= maxGroups; d++) {
/*  704 */           if (teams % d == 0 && teams / d <= 24) {
/*  705 */             found = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  712 */       DesignDiyTournament.this.roundTeams[this.round] = teams;
/*      */ 
/*      */       
/*  715 */       if (DesignDiyTournament.this.invalidKnockout(this.round)) {
/*  716 */         DesignDiyTournament.this.resetRoundGroups(this.round - 1);
/*      */       }
/*      */       
/*  719 */       DesignDiyTournament.this.resetRoundGroups(this.round);
/*      */ 
/*      */       
/*  722 */       if (teams == 1 && DesignDiyTournament.this.roundTeams[this.round + 1] == 1) {
/*  723 */         DesignDiyTournament.this.roundTeams[this.round + 1] = 0;
/*      */       }
/*      */       
/*  726 */       DesignDiyTournament.this.setRoundButtonsDirty(this.round);
/*  727 */       if (this.round > 0) {
/*  728 */         DesignDiyTournament.this.setRoundButtonsDirty(this.round - 1);
/*      */       }
/*  730 */       if (this.round < 5) {
/*  731 */         DesignDiyTournament.this.setRoundButtonsDirty(this.round + 1);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  737 */       setText(DesignDiyTournament.this.roundTeams[this.round]);
/*  738 */       setVisible((this.round == 0 || DesignDiyTournament.this.roundTeams[this.round - 1] > 2));
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundGroupsButton
/*      */     extends Button {
/*      */     private int round;
/*      */     
/*      */     RoundGroupsButton(int round) {
/*  747 */       this.round = round;
/*  748 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 420, 280 + 62 * round, 248, 32);
/*  749 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  750 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  755 */       rotateGroups();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  760 */       rotateGroups();
/*      */     }
/*      */     
/*      */     private void rotateGroups() {
/*  764 */       int groups = DesignDiyTournament.this.roundGroups[this.round];
/*  765 */       int maxGroups = Math.min(DesignDiyTournament.this.roundTeams[this.round + 1], 8);
/*      */ 
/*      */       
/*  768 */       boolean found = false;
/*      */       do {
/*  770 */         groups = EMath.rotate(groups, 0, maxGroups, 1);
/*      */         
/*  772 */         if (groups == 0) {
/*      */           
/*  774 */           if (DesignDiyTournament.this.roundTeams[this.round] == 2 * DesignDiyTournament.this.roundTeams[this.round + 1]) {
/*  775 */             found = true;
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*  780 */         } else if (DesignDiyTournament.this.roundTeams[this.round] % groups == 0 && 
/*  781 */           EMath.isIn((DesignDiyTournament.this.roundTeams[this.round] / groups), 2.0F, 24.0F)) {
/*  782 */           found = true;
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*  787 */       } while (groups != DesignDiyTournament.this.roundGroups[this.round]);
/*      */       
/*  789 */       if (!found) {
/*      */         return;
/*      */       }
/*  792 */       DesignDiyTournament.this.roundGroups[this.round] = groups;
/*  793 */       DesignDiyTournament.this.setRoundButtonsDirty(this.round);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*      */       String key;
/*  799 */       if (DesignDiyTournament.this.roundGroups[this.round] == 0) {
/*  800 */         switch (DesignDiyTournament.this.roundTeams[this.round]) {
/*      */           case 2:
/*  802 */             key = "FINAL";
/*      */             break;
/*      */           case 4:
/*  805 */             key = "SEMI-FINAL";
/*      */             break;
/*      */           case 8:
/*  808 */             key = "QUARTER-FINAL";
/*      */             break;
/*      */           default:
/*  811 */             key = "KNOCKOUT"; break;
/*      */         } 
/*  813 */       } else if (DesignDiyTournament.this.roundGroups[this.round] == 1) {
/*  814 */         key = "%n GROUP OF %m";
/*      */       } else {
/*  816 */         key = "%n GROUPS OF %m";
/*      */       } 
/*  818 */       String label = Assets.strings.get(key);
/*  819 */       if (DesignDiyTournament.this.roundGroups[this.round] == 0) {
/*  820 */         setText(label);
/*      */       } else {
/*  822 */         setText(label
/*  823 */             .replaceFirst("%n", "" + DesignDiyTournament.this.roundGroups[this.round])
/*  824 */             .replaceFirst("%m", "" + (DesignDiyTournament.this.roundTeams[this.round] / DesignDiyTournament.this.roundGroups[this.round])));
/*      */       } 
/*      */       
/*  827 */       setVisible((DesignDiyTournament.this.roundTeams[this.round] > 1));
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundSeededButton
/*      */     extends Button {
/*      */     private int round;
/*      */     
/*      */     RoundSeededButton(int round) {
/*  836 */       this.round = round;
/*  837 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 170, 280 + 62 * round, 42, 32);
/*  838 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  839 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  844 */       toggleSeeded();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  849 */       toggleSeeded();
/*      */     }
/*      */     
/*      */     private void toggleSeeded() {
/*  853 */       DesignDiyTournament.this.roundSeeded[this.round] = !DesignDiyTournament.this.roundSeeded[this.round];
/*  854 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  859 */       setText(DesignDiyTournament.this.roundSeeded[this.round] ? "*" : "-");
/*  860 */       setVisible((DesignDiyTournament.this.roundTeams[this.round] > 1));
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundLegsButton
/*      */     extends Button {
/*      */     private int round;
/*      */     
/*      */     RoundLegsButton(int round) {
/*  869 */       this.round = round;
/*  870 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 126, 280 + 62 * round, 138, 32);
/*  871 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  872 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  877 */       rotateLegs();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  882 */       rotateLegs();
/*      */     }
/*      */     
/*      */     private void rotateLegs() {
/*  886 */       (DesignDiyTournament.this.knockouts[this.round]).numberOfLegs = EMath.rotate((DesignDiyTournament.this.knockouts[this.round]).numberOfLegs, 1, 2, 1);
/*  887 */       setDirty(true);
/*  888 */       DesignDiyTournament.this.awayGoalsLabel.setDirty(true);
/*  889 */       DesignDiyTournament.this.awayGoalsButton.setDirty(true);
/*  890 */       DesignDiyTournament.this.roundQualificationLabels[this.round].setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  895 */       setVisible((DesignDiyTournament.this.roundTeams[this.round] > 1 && DesignDiyTournament.this.roundGroups[this.round] == 0));
/*  896 */       if (this.visible)
/*  897 */         setText(Assets.strings.get(((DesignDiyTournament.this.knockouts[this.round]).numberOfLegs == 1) ? "ONE LEG" : "TWO LEGS")); 
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundExtraTimeButton
/*      */     extends Button
/*      */   {
/*      */     private int round;
/*      */     
/*      */     RoundExtraTimeButton(int round) {
/*  907 */       this.round = round;
/*  908 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 + 14, 280 + 62 * round, 240, 32);
/*  909 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  910 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  915 */       rotateExtraTime();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  920 */       rotateExtraTime();
/*      */     }
/*      */     
/*      */     private void rotateExtraTime() {
/*  924 */       (DesignDiyTournament.this.knockouts[this.round]).extraTime = Round.ExtraTime.values()[EMath.rotate((DesignDiyTournament.this.knockouts[this.round]).extraTime.ordinal(), 0, 2, 1)];
/*  925 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  930 */       setVisible((DesignDiyTournament.this.roundTeams[this.round] > 1 && DesignDiyTournament.this.roundGroups[this.round] == 0));
/*  931 */       if (this.visible)
/*  932 */         setText(Assets.strings.get(Round.getExtraTimeLabel((DesignDiyTournament.this.knockouts[this.round]).extraTime))); 
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundPenaltiesButton
/*      */     extends Button
/*      */   {
/*      */     private int round;
/*      */     
/*      */     RoundPenaltiesButton(int round) {
/*  942 */       this.round = round;
/*  943 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 + 256, 280 + 62 * round, 240, 32);
/*  944 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  945 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  950 */       rotatePenalties();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  955 */       rotatePenalties();
/*      */     }
/*      */     
/*      */     private void rotatePenalties() {
/*  959 */       (DesignDiyTournament.this.knockouts[this.round]).penalties = Round.Penalties.values()[EMath.rotate((DesignDiyTournament.this.knockouts[this.round]).penalties.ordinal(), 0, 2, 1)];
/*  960 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  965 */       setVisible((DesignDiyTournament.this.roundTeams[this.round] > 1 && DesignDiyTournament.this.roundGroups[this.round] == 0));
/*  966 */       if (this.visible)
/*  967 */         setText(Assets.strings.get(Round.getPenaltiesLabel((DesignDiyTournament.this.knockouts[this.round]).penalties))); 
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundPointsForAWinButton
/*      */     extends Button
/*      */   {
/*      */     private int round;
/*      */     
/*      */     RoundPointsForAWinButton(int round) {
/*  977 */       this.round = round;
/*  978 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 126, 280 + 62 * round, 310, 32);
/*  979 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  980 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  985 */       rotatePointsForAWin();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  990 */       rotatePointsForAWin();
/*      */     }
/*      */     
/*      */     private void rotatePointsForAWin() {
/*  994 */       (DesignDiyTournament.this.groups[this.round]).pointsForAWin = EMath.rotate((DesignDiyTournament.this.groups[this.round]).pointsForAWin, 2, 3, 1);
/*  995 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1000 */       setVisible((DesignDiyTournament.this.roundTeams[this.round] > 1 && DesignDiyTournament.this.roundGroups[this.round] > 0));
/* 1001 */       if (this.visible)
/* 1002 */         setText(Assets.strings.get("%n POINTS FOR A WIN").replaceFirst("%n", "" + (DesignDiyTournament.this.groups[this.round]).pointsForAWin)); 
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundPlayEachTeamButton
/*      */     extends Button
/*      */   {
/*      */     private int round;
/*      */     
/*      */     RoundPlayEachTeamButton(int round) {
/* 1012 */       this.round = round;
/* 1013 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 + 186, 280 + 62 * round, 310, 32);
/* 1014 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 1015 */       setText("", Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1020 */       rotatePlayEachTeam();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/* 1025 */       rotatePlayEachTeam();
/*      */     }
/*      */     
/*      */     private void rotatePlayEachTeam() {
/* 1029 */       (DesignDiyTournament.this.groups[this.round]).rounds = EMath.rotate((DesignDiyTournament.this.groups[this.round]).rounds, 1, 4, 1);
/* 1030 */       setDirty(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1035 */       setVisible((DesignDiyTournament.this.roundTeams[this.round] > 1 && DesignDiyTournament.this.roundGroups[this.round] > 0));
/* 1036 */       if (this.visible)
/* 1037 */         setText(Assets.strings.get("PLAY EACH TEAM ×%n").replaceFirst("%n", "" + (DesignDiyTournament.this.groups[this.round]).rounds)); 
/*      */     }
/*      */   }
/*      */   
/*      */   private class ShortArrowPicture
/*      */     extends Picture
/*      */   {
/*      */     private int round;
/*      */     
/*      */     ShortArrowPicture(int round) {
/* 1047 */       this.round = round;
/* 1048 */       setTextureRegion(Assets.shortArrow);
/* 1049 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setPosition(1280 / 2 - 446, 326 + 62 * round);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1054 */       setVisible((DesignDiyTournament.this.roundTeams[this.round] > 2));
/*      */     }
/*      */   }
/*      */   
/*      */   private class RoundQualificationLabel
/*      */     extends Label {
/*      */     private int round;
/*      */     
/*      */     RoundQualificationLabel(int round) {
/* 1063 */       this.round = round;
/* 1064 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setPosition(1280 / 2, 326 + 62 * round);
/* 1065 */       setText("", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */     
/*      */     public void refresh() {
/*      */       String label;
/* 1070 */       int nextRoundTeams, runnersUp, teams = DesignDiyTournament.this.roundTeams[this.round];
/* 1071 */       setVisible((teams > 1));
/*      */ 
/*      */       
/* 1074 */       int groups = DesignDiyTournament.this.roundGroups[this.round];
/* 1075 */       switch (groups) {
/*      */         case 0:
/* 1077 */           if (teams == 2) {
/* 1078 */             if ((DesignDiyTournament.this.knockouts[this.round]).numberOfLegs == 1) {
/* 1079 */               String str1 = Assets.strings.get("TOURNAMENT.MATCH WINNER WINS TOURNAMENT"); break;
/*      */             } 
/* 1081 */             String str = Assets.strings.get("TOURNAMENT.MATCH WINNER ON AGGREGATE WINS TOURNAMENT");
/*      */             break;
/*      */           } 
/* 1084 */           if ((DesignDiyTournament.this.knockouts[this.round]).numberOfLegs == 1) {
/* 1085 */             String str = Assets.strings.get("TOURNAMENT.MATCH WINNERS QUALIFY"); break;
/*      */           } 
/* 1087 */           label = Assets.strings.get("TOURNAMENT.MATCH WINNERS ON AGGREGATE QUALIFY");
/*      */           break;
/*      */ 
/*      */ 
/*      */         
/*      */         case 1:
/* 1093 */           if (DesignDiyTournament.this.roundTeams[this.round + 1] == 1) {
/* 1094 */             label = Assets.strings.get("TOURNAMENT.GROUP WINNER WINS TOURNAMENT");
/*      */             break;
/*      */           } 
/* 1097 */           label = Assets.strings.get("TOURNAMENT.TOP %n IN GROUP QUALIFY").replaceFirst("%n", "" + DesignDiyTournament.this.roundTeams[this.round + 1]);
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/* 1102 */           nextRoundTeams = DesignDiyTournament.this.roundTeams[this.round + 1];
/* 1103 */           runnersUp = nextRoundTeams % groups;
/* 1104 */           switch (runnersUp) {
/*      */             case 0:
/* 1106 */               if (nextRoundTeams / groups == 1) {
/* 1107 */                 label = Assets.strings.get("TOURNAMENT.WINNERS OF EACH GROUP QUALIFY");
/*      */                 break;
/*      */               } 
/* 1110 */               label = Assets.strings.get("TOURNAMENT.TOP %n IN EACH GROUP QUALIFY").replaceFirst("%n", "" + (nextRoundTeams / groups));
/*      */               break;
/*      */ 
/*      */             
/*      */             case 1:
/* 1115 */               if (nextRoundTeams / groups == 1) {
/* 1116 */                 label = Assets.strings.get("TOURNAMENT.WINNERS OF EACH GROUP AND BEST RUNNER-UP QUALIFIES");
/*      */                 break;
/*      */               } 
/* 1119 */               label = Assets.strings.get("TOURNAMENT.TOP %n IN EACH GROUP AND BEST RUNNER-UP QUALIFY").replaceFirst("%n", "" + (nextRoundTeams / groups));
/*      */               break;
/*      */           } 
/*      */ 
/*      */           
/* 1124 */           if (nextRoundTeams / groups == 1) {
/*      */             
/* 1126 */             label = Assets.strings.get("TOURNAMENT.WINNERS OF EACH GROUP AND BEST %n RUNNERS-UP QUALIFY").replaceFirst("%n", "" + runnersUp);
/*      */             
/*      */             break;
/*      */           } 
/* 1130 */           label = Assets.strings.get("TOURNAMENT.TOP %n IN EACH GROUP AND BEST %m RUNNERS-UP QUALIFY").replaceFirst("%n", "" + (nextRoundTeams / groups)).replaceFirst("%m", "" + runnersUp);
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1136 */       setText("(" + label + ")");
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean invalidKnockout(int round) {
/* 1141 */     return (round > 0 && this.roundGroups[round - 1] == 0 && 2 * this.roundTeams[round] != this.roundTeams[round - 1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetRoundGroups(int round) {
/* 1149 */     if (this.roundTeams[round] == 2 * this.roundTeams[round + 1]) {
/* 1150 */       this.roundGroups[round] = 0;
/*      */       
/*      */       return;
/*      */     } 
/* 1154 */     for (int groups = 1; groups <= 8; groups++) {
/*      */       
/* 1156 */       if (this.roundTeams[round] % groups == 0 && this.roundTeams[round] / groups <= 24) {
/* 1157 */         this.roundGroups[round] = groups;
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1162 */     throw new GdxRuntimeException("Failed to reset groups");
/*      */   }
/*      */   
/*      */   private void setRoundButtonsDirty(int round) {
/* 1166 */     this.roundNumberLabels[round].setDirty(true);
/* 1167 */     this.roundTeamsButtons[round].setDirty(true);
/* 1168 */     this.roundGroupsButtons[round].setDirty(true);
/* 1169 */     this.roundSeededButtons[round].setDirty(true);
/* 1170 */     this.roundLegsButtons[round].setDirty(true);
/* 1171 */     this.roundExtraTimeButtons[round].setDirty(true);
/* 1172 */     this.roundPenaltiesButtons[round].setDirty(true);
/* 1173 */     this.roundPointsFawButtons[round].setDirty(true);
/* 1174 */     this.roundPlayEachTeamButtons[round].setDirty(true);
/* 1175 */     if (round < 5) {
/* 1176 */       this.roundShortArrowPictures[round].setDirty(true);
/*      */     }
/* 1178 */     this.roundQualificationLabels[round].setDirty(true);
/* 1179 */     this.awayGoalsLabel.setDirty(true);
/* 1180 */     this.awayGoalsButton.setDirty(true);
/*      */   }
/*      */   
/*      */   private class OkButton
/*      */     extends Button {
/*      */     OkButton() {
/* 1186 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 1187 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 - 180 - 2, 660, 180, 38);
/* 1188 */       setText(Assets.strings.get("OK"), Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1193 */       int round = 0;
/* 1194 */       while (DesignDiyTournament.this.roundTeams[round] > 1) {
/* 1195 */         if (DesignDiyTournament.this.roundGroups[round] == 0) {
/* 1196 */           (DesignDiyTournament.this.knockouts[round]).numberOfTeams = DesignDiyTournament.this.roundTeams[round];
/* 1197 */           (DesignDiyTournament.this.knockouts[round]).seeded = DesignDiyTournament.this.roundSeeded[round];
/* 1198 */           DesignDiyTournament.this.tournament.addRound((Round)DesignDiyTournament.this.knockouts[round]);
/*      */         } else {
/* 1200 */           (DesignDiyTournament.this.groups[round]).numberOfTeams = DesignDiyTournament.this.roundTeams[round];
/* 1201 */           (DesignDiyTournament.this.groups[round]).seeded = DesignDiyTournament.this.roundSeeded[round];
/* 1202 */           DesignDiyTournament.this.groups[round].createGroups(DesignDiyTournament.this.roundGroups[round]);
/* 1203 */           DesignDiyTournament.this.tournament.addRound((Round)DesignDiyTournament.this.groups[round]);
/*      */         } 
/* 1205 */         round++;
/*      */       } 
/* 1207 */       DesignDiyTournament.navigation.folder = Assets.teamsRootFolder;
/* 1208 */       DesignDiyTournament.navigation.competition = (Competition)DesignDiyTournament.this.tournament;
/* 1209 */       DesignDiyTournament.this.game.setScreen((Screen)new SelectFolder(DesignDiyTournament.this.game));
/*      */     }
/*      */   }
/*      */   
/*      */   private class AbortButton
/*      */     extends Button {
/*      */     AbortButton() {
/* 1216 */       setColor(13107214);
/* 1217 */       Objects.requireNonNull(DesignDiyTournament.this.game.gui); setGeometry(1280 / 2 + 2, 660, 180, 36);
/* 1218 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1223 */       DesignDiyTournament.this.game.setScreen((Screen)new Main(DesignDiyTournament.this.game));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DesignDiyTournament.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */