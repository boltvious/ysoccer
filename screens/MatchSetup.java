/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.MatchSettings;
/*     */ import com.ygames.ysoccer.match.Pitch;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchSetup
/*     */   extends GLScreen
/*     */ {
/*     */   private MatchSettings matchSettings;
/*     */   private TimePicture timePicture;
/*     */   private PitchTypePicture pitchTypePicture;
/*     */   private WeatherButton weatherButton;
/*     */   private WeatherPicture weatherPicture;
/*  32 */   private KitPicture[] kitPictures = new KitPicture[2];
/*  33 */   private ArrayList<KitButton>[] kitButtons = (ArrayList<KitButton>[])new ArrayList[2];
/*     */   private Widget playMatchButton;
/*     */   
/*     */   MatchSetup(GLGame game) {
/*  37 */     super(game);
/*  38 */     this.playMenuMusic = false;
/*     */     
/*  40 */     Match match = navigation.competition.getMatch();
/*  41 */     Team homeTeam = match.team[0];
/*  42 */     Team awayTeam = match.team[1];
/*     */     
/*  44 */     Team.kitAutoSelection(homeTeam, awayTeam);
/*     */     
/*  46 */     this.matchSettings = new MatchSettings(navigation.competition, game.settings);
/*     */     
/*  48 */     Assets.Sounds.volume = game.settings.soundVolume;
/*     */     
/*  50 */     this.background = new Texture("images/backgrounds/menu_match_presentation.jpg");
/*     */ 
/*     */ 
/*     */     
/*  54 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, navigation.competition.name, game.stateColor.body.intValue());
/*  55 */     this.widgets.add(titleBar);
/*     */     
/*  57 */     TimeLabel timeLabel = new TimeLabel();
/*  58 */     this.widgets.add(timeLabel);
/*     */     
/*  60 */     this.timePicture = new TimePicture();
/*  61 */     this.widgets.add(this.timePicture);
/*     */     
/*  63 */     TimeButton timeButton = new TimeButton();
/*  64 */     this.widgets.add(timeButton);
/*     */     
/*  66 */     PitchTypeLabel pitchTypeLabel = new PitchTypeLabel();
/*  67 */     this.widgets.add(pitchTypeLabel);
/*     */     
/*  69 */     this.pitchTypePicture = new PitchTypePicture();
/*  70 */     this.widgets.add(this.pitchTypePicture);
/*     */     
/*  72 */     PitchTypeButton pitchTypeButton = new PitchTypeButton();
/*  73 */     this.widgets.add(pitchTypeButton);
/*     */     
/*  75 */     WeatherLabel weatherLabel = new WeatherLabel();
/*  76 */     this.widgets.add(weatherLabel);
/*     */     
/*  78 */     this.weatherPicture = new WeatherPicture();
/*  79 */     this.widgets.add(this.weatherPicture);
/*     */     
/*  81 */     this.weatherButton = new WeatherButton();
/*  82 */     this.widgets.add(this.weatherButton);
/*     */     
/*  84 */     for (int t = 0; t <= 1; t++) {
/*  85 */       Team team = (t == 0) ? homeTeam : awayTeam;
/*     */       
/*  87 */       TeamNameButton teamNameButton = new TeamNameButton(team, t);
/*  88 */       this.widgets.add(teamNameButton);
/*     */       
/*  90 */       this.kitPictures[t] = new KitPicture(team, t);
/*  91 */       this.widgets.add(this.kitPictures[t]);
/*     */       
/*  93 */       this.kitButtons[t] = new ArrayList<>();
/*  94 */       for (int i = 0; i < team.kits.size(); i++) {
/*  95 */         KitButton kitButton = new KitButton(team, t, i);
/*  96 */         this.kitButtons[t].add(kitButton);
/*  97 */         this.widgets.add(kitButton);
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     this.playMatchButton = (Widget)new PlayMatchButton();
/* 102 */     this.widgets.add(this.playMatchButton);
/*     */     
/* 104 */     setSelectedWidget(this.playMatchButton);
/*     */     
/* 106 */     ExitButton exitButton = new ExitButton();
/* 107 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private class TimeLabel
/*     */     extends Button {
/*     */     TimeLabel() {
/* 113 */       setColor(8388608);
/* 114 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 - 300 + 25, 110, 300, 40);
/* 115 */       setText(Assets.gettext("TIME"), Font.Align.CENTER, Assets.font14);
/* 116 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimePicture
/*     */     extends Button {
/*     */     TimePicture() {
/* 123 */       setColor(6710886);
/* 124 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 - 300 - 65, 105, 50, 50);
/* 125 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 130 */       this.textureRegion = Assets.lightIcons[MatchSetup.this.matchSettings.time.ordinal()];
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeButton
/*     */     extends Button {
/*     */     TimeButton() {
/* 137 */       if (MatchSetup.navigation.competition.type == Competition.Type.FRIENDLY || 
/* 138 */         MatchSetup.navigation.competition.type == Competition.Type.TEST_MATCH) {
/* 139 */         setColor(2039701);
/*     */       } else {
/* 141 */         setColor(6710886);
/* 142 */         setActive(false);
/*     */       } 
/* 144 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 + 65, 110, 300, 40);
/* 145 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 150 */       setText(Assets.gettext(MatchSettings.getTimeLabel(MatchSetup.this.matchSettings.time)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 155 */       rotateTime(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 160 */       rotateTime(-1);
/*     */     }
/*     */     
/*     */     private void rotateTime(int n) {
/* 164 */       MatchSetup.this.matchSettings.rotateTime(n);
/* 165 */       setDirty(true);
/* 166 */       MatchSetup.this.timePicture.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypeLabel
/*     */     extends Button {
/*     */     PitchTypeLabel() {
/* 173 */       setColor(8388608);
/* 174 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 - 300 + 25, 180, 300, 40);
/* 175 */       setText(Assets.gettext("PITCH TYPE"), Font.Align.CENTER, Assets.font14);
/* 176 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypePicture
/*     */     extends Button {
/*     */     PitchTypePicture() {
/* 183 */       setColor(6710886);
/* 184 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 - 300 - 65, 175, 50, 50);
/* 185 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 190 */       this.textureRegion = Assets.pitchIcons[MatchSetup.this.matchSettings.pitchType.ordinal()];
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypeButton
/*     */     extends Button {
/*     */     PitchTypeButton() {
/* 197 */       if (MatchSetup.navigation.competition.type == Competition.Type.FRIENDLY || 
/* 198 */         MatchSetup.navigation.competition.type == Competition.Type.TEST_MATCH) {
/* 199 */         setColor(2039701);
/*     */       } else {
/* 201 */         setColor(6710886);
/* 202 */         setActive(false);
/*     */       } 
/* 204 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 + 65, 180, 300, 40);
/* 205 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 210 */       setText(Assets.gettext(Pitch.names[MatchSetup.this.matchSettings.pitchType.ordinal()]));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 215 */       rotatePitchType(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 220 */       rotatePitchType(-1);
/*     */     }
/*     */     
/*     */     private void rotatePitchType(int n) {
/* 224 */       MatchSetup.this.matchSettings.rotatePitchType(n);
/* 225 */       setDirty(true);
/* 226 */       MatchSetup.this.pitchTypePicture.setDirty(true);
/* 227 */       MatchSetup.this.weatherPicture.setDirty(true);
/* 228 */       MatchSetup.this.weatherButton.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeatherLabel
/*     */     extends Button {
/*     */     WeatherLabel() {
/* 235 */       setColor(8388608);
/* 236 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 - 300 + 25, 250, 300, 40);
/* 237 */       setText(Assets.gettext("WEATHER"), Font.Align.CENTER, Assets.font14);
/* 238 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeatherPicture
/*     */     extends Button {
/*     */     WeatherPicture() {
/* 245 */       setColor(6710886);
/* 246 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 - 300 - 65, 245, 50, 50);
/* 247 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 252 */       this.textureRegion = Assets.weatherIcons[MatchSetup.this.matchSettings.weatherOffset()];
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeatherButton
/*     */     extends Button {
/*     */     WeatherButton() {
/* 259 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 260 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 + 65, 250, 300, 40);
/* 261 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 266 */       if (MatchSetup.navigation.competition.type == Competition.Type.FRIENDLY || 
/* 267 */         MatchSetup.navigation.competition.type == Competition.Type.TEST_MATCH) {
/* 268 */         setColor(2039701);
/* 269 */         setActive(true);
/*     */       } else {
/* 271 */         setColor(6710886);
/* 272 */         setActive(false);
/*     */       } 
/* 274 */       setText(Assets.gettext(MatchSetup.this.matchSettings.getWeatherLabel()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 279 */       MatchSetup.this.matchSettings.rotateWeather();
/* 280 */       setDirty(true);
/* 281 */       MatchSetup.this.weatherPicture.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamNameButton
/*     */     extends Button {
/*     */     TeamNameButton(Team team, int teamIndex) {
/* 288 */       int sign = (teamIndex == 0) ? -1 : 1;
/* 289 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry((1280 - 500) / 2 + 270 * sign, 326, 500, 42);
/* 290 */       setColor(2039701);
/* 291 */       setText(team.name, Font.Align.CENTER, Assets.font14);
/* 292 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class KitPicture
/*     */     extends Button {
/*     */     Team team;
/*     */     
/*     */     KitPicture(Team team, int teamIndex) {
/* 301 */       this.team = team;
/* 302 */       int sign = (teamIndex == 0) ? -1 : 1;
/* 303 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry(1280 / 2 - 83 + 270 * sign, 390, 167, 304);
/* 304 */       setActive(false);
/* 305 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 310 */       this.textureRegion = this.team.loadKit(this.team.kitIndex);
/*     */     }
/*     */   }
/*     */   
/*     */   private class KitButton
/*     */     extends Button {
/*     */     Team team;
/*     */     int teamIndex;
/*     */     int kitIndex;
/*     */     
/*     */     KitButton(Team team, int teamIndex, int kitIndex) {
/* 321 */       this.team = team;
/* 322 */       this.teamIndex = teamIndex;
/* 323 */       this.kitIndex = kitIndex;
/* 324 */       setSize(58, 104);
/* 325 */       setImageScale(0.33333334F, 0.33333334F);
/* 326 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 331 */       this.textureRegion = this.team.loadKit(this.kitIndex);
/* 332 */       if (this.team.kitIndex == this.kitIndex) {
/* 333 */         setVisible(false);
/*     */       } else {
/* 335 */         setVisible(true);
/* 336 */         int position = this.kitIndex - ((this.team.kitIndex < this.kitIndex) ? 1 : 0);
/* 337 */         int sign = (this.teamIndex == 0) ? -1 : 1;
/* 338 */         Objects.requireNonNull(MatchSetup.this.game.gui); int x = 1280 / 2 + sign * (340 + 68 * (1 + position / 2)) - 34;
/* 339 */         int y = 430 + 114 * position % 2;
/* 340 */         setPosition(x, y);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 346 */       this.team.kitIndex = this.kitIndex;
/* 347 */       for (KitButton kitButton : MatchSetup.this.kitButtons[this.teamIndex]) {
/* 348 */         kitButton.setDirty(true);
/*     */       }
/* 350 */       MatchSetup.this.kitPictures[this.teamIndex].setDirty(true);
/* 351 */       MatchSetup.this.setSelectedWidget(MatchSetup.this.playMatchButton);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayMatchButton
/*     */     extends Button {
/*     */     PlayMatchButton() {
/* 358 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry((1280 - 240) / 2, 510, 240, 50);
/* 359 */       setColors(Integer.valueOf(14417920), Integer.valueOf(16728385), Integer.valueOf(9175040));
/* 360 */       setText(Assets.gettext("PLAY MATCH"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 365 */       MatchSetup.this.game.setScreen((Screen)new MatchLoading(MatchSetup.this.game, MatchSetup.this.matchSettings, MatchSetup.navigation.competition));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 372 */       Objects.requireNonNull(MatchSetup.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 373 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 374 */       setText(Assets.gettext("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */     
/*     */     public void onFire1Down() {
/*     */       FileHandle[] teamFileHandles;
/* 379 */       switch (MatchSetup.navigation.competition.type) {
/*     */         case FRIENDLY:
/* 381 */           if (MatchSetup.navigation.folder.equals(Assets.favouritesFile)) {
/* 382 */             MatchSetup.this.game.setScreen((Screen)new SelectFavourites(MatchSetup.this.game)); break;
/*     */           } 
/* 384 */           teamFileHandles = MatchSetup.navigation.folder.list(Assets.teamFilenameFilter);
/* 385 */           if (teamFileHandles.length > 0) {
/* 386 */             MatchSetup.this.game.setScreen((Screen)new SelectTeams(MatchSetup.this.game)); break;
/*     */           } 
/* 388 */           MatchSetup.this.game.setScreen((Screen)new SelectFolder(MatchSetup.this.game));
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case LEAGUE:
/* 394 */           MatchSetup.this.game.setScreen((Screen)new PlayLeague(MatchSetup.this.game));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 398 */           MatchSetup.this.game.setScreen((Screen)new PlayCup(MatchSetup.this.game));
/*     */           break;
/*     */         
/*     */         case TOURNAMENT:
/* 402 */           MatchSetup.this.game.setScreen((Screen)new PlayTournament(MatchSetup.this.game));
/*     */           break;
/*     */         
/*     */         case TEST_MATCH:
/* 406 */           MatchSetup.this.game.setScreen((Screen)new DeveloperTools(MatchSetup.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\MatchSetup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */