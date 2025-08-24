/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Pitch;
/*     */ import com.ygames.ysoccer.match.SceneSettings;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class SetupTraining
/*     */   extends GLScreen
/*     */ {
/*     */   private SceneSettings sceneSettings;
/*     */   private TimePicture timePicture;
/*     */   private PitchTypePicture pitchTypePicture;
/*     */   private WeatherButton weatherButton;
/*     */   private WeatherPicture weatherPicture;
/*     */   
/*     */   SetupTraining(GLGame game) {
/*  25 */     super(game);
/*     */     
/*  27 */     this.sceneSettings = new SceneSettings(game.settings);
/*     */     
/*  29 */     Assets.Sounds.volume = game.settings.soundVolume;
/*     */     
/*  31 */     this.background = new Texture("images/backgrounds/menu_training.jpg");
/*     */ 
/*     */ 
/*     */     
/*  35 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("TRAINING") + " - " + navigation.team.name, game.stateColor.body.intValue());
/*  36 */     this.widgets.add(titleBar);
/*     */     
/*  38 */     TimeLabel timeLabel = new TimeLabel();
/*  39 */     this.widgets.add(timeLabel);
/*     */     
/*  41 */     this.timePicture = new TimePicture();
/*  42 */     this.widgets.add(this.timePicture);
/*     */     
/*  44 */     TimeButton timeButton = new TimeButton();
/*  45 */     this.widgets.add(timeButton);
/*     */     
/*  47 */     PitchTypeLabel pitchTypeLabel = new PitchTypeLabel();
/*  48 */     this.widgets.add(pitchTypeLabel);
/*     */     
/*  50 */     this.pitchTypePicture = new PitchTypePicture();
/*  51 */     this.widgets.add(this.pitchTypePicture);
/*     */     
/*  53 */     PitchTypeButton pitchTypeButton = new PitchTypeButton();
/*  54 */     this.widgets.add(pitchTypeButton);
/*     */     
/*  56 */     WeatherLabel weatherLabel = new WeatherLabel();
/*  57 */     this.widgets.add(weatherLabel);
/*     */     
/*  59 */     this.weatherPicture = new WeatherPicture();
/*  60 */     this.widgets.add(this.weatherPicture);
/*     */     
/*  62 */     this.weatherButton = new WeatherButton();
/*  63 */     this.widgets.add(this.weatherButton);
/*     */     
/*  65 */     FreeTrainingButton freeTrainingButton = new FreeTrainingButton();
/*  66 */     this.widgets.add(freeTrainingButton);
/*     */     
/*  68 */     setSelectedWidget((Widget)freeTrainingButton);
/*     */     
/*  70 */     FreeKicksButton freeKicksButton = new FreeKicksButton();
/*  71 */     this.widgets.add(freeKicksButton);
/*     */     
/*  73 */     PenaltyKicksButton penaltyKicksButton = new PenaltyKicksButton();
/*  74 */     this.widgets.add(penaltyKicksButton);
/*     */     
/*  76 */     ExitButton exitButton = new ExitButton();
/*  77 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private class TimeLabel
/*     */     extends Button {
/*     */     TimeLabel() {
/*  83 */       setColor(8388608);
/*  84 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry(1280 / 2 - 300 + 25, 110, 300, 40);
/*  85 */       setText(Assets.strings.get("TIME"), Font.Align.CENTER, Assets.font14);
/*  86 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimePicture
/*     */     extends Button {
/*     */     TimePicture() {
/*  93 */       setColor(6710886);
/*  94 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry(1280 / 2 - 300 - 65, 105, 50, 50);
/*  95 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 100 */       this.textureRegion = Assets.lightIcons[SetupTraining.this.sceneSettings.time.ordinal()];
/*     */     }
/*     */   }
/*     */   
/*     */   private class TimeButton
/*     */     extends Button {
/*     */     TimeButton() {
/* 107 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry(1280 / 2 + 65, 110, 300, 40);
/* 108 */       setColor(2039701);
/* 109 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 114 */       setText(Assets.strings.get(SceneSettings.getTimeLabel(SetupTraining.this.sceneSettings.time)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 119 */       rotateTime(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 124 */       rotateTime(-1);
/*     */     }
/*     */     
/*     */     private void rotateTime(int n) {
/* 128 */       SetupTraining.this.sceneSettings.rotateTime(n);
/* 129 */       setDirty(true);
/* 130 */       SetupTraining.this.timePicture.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypeLabel
/*     */     extends Button {
/*     */     PitchTypeLabel() {
/* 137 */       setColor(8388608);
/* 138 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry(1280 / 2 - 300 + 25, 180, 300, 40);
/* 139 */       setText(Assets.strings.get("PITCH TYPE"), Font.Align.CENTER, Assets.font14);
/* 140 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypePicture
/*     */     extends Button {
/*     */     PitchTypePicture() {
/* 147 */       setColor(6710886);
/* 148 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry(1280 / 2 - 300 - 65, 175, 50, 50);
/* 149 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 154 */       this.textureRegion = Assets.pitchIcons[SetupTraining.this.sceneSettings.pitchType.ordinal()];
/*     */     }
/*     */   }
/*     */   
/*     */   private class PitchTypeButton
/*     */     extends Button {
/*     */     PitchTypeButton() {
/* 161 */       setColor(2039701);
/* 162 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry(1280 / 2 + 65, 180, 300, 40);
/* 163 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 168 */       setText(Assets.strings.get(Pitch.names[SetupTraining.this.sceneSettings.pitchType.ordinal()]));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 173 */       rotatePitchType(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 178 */       rotatePitchType(-1);
/*     */     }
/*     */     
/*     */     private void rotatePitchType(int n) {
/* 182 */       SetupTraining.this.sceneSettings.rotatePitchType(n);
/* 183 */       setDirty(true);
/* 184 */       SetupTraining.this.pitchTypePicture.setDirty(true);
/* 185 */       SetupTraining.this.weatherPicture.setDirty(true);
/* 186 */       SetupTraining.this.weatherButton.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeatherLabel
/*     */     extends Button {
/*     */     WeatherLabel() {
/* 193 */       setColor(8388608);
/* 194 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry(1280 / 2 - 300 + 25, 250, 300, 40);
/* 195 */       setText(Assets.strings.get("WEATHER"), Font.Align.CENTER, Assets.font14);
/* 196 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeatherPicture
/*     */     extends Button {
/*     */     WeatherPicture() {
/* 203 */       setColor(6710886);
/* 204 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry(1280 / 2 - 300 - 65, 245, 50, 50);
/* 205 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 210 */       this.textureRegion = Assets.weatherIcons[SetupTraining.this.sceneSettings.weatherOffset()];
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeatherButton
/*     */     extends Button {
/*     */     WeatherButton() {
/* 217 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 218 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry(1280 / 2 + 65, 250, 300, 40);
/* 219 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 224 */       setColor(2039701);
/* 225 */       setActive(true);
/* 226 */       setText(Assets.strings.get(SetupTraining.this.sceneSettings.getWeatherLabel()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 231 */       SetupTraining.this.sceneSettings.rotateWeather();
/* 232 */       setDirty(true);
/* 233 */       SetupTraining.this.weatherPicture.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FreeTrainingButton
/*     */     extends Button {
/*     */     FreeTrainingButton() {
/* 240 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry((1280 - 340) / 2, 400, 340, 40);
/* 241 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/* 242 */       setText(Assets.strings.get("TRAINING.FREE TRAINING"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 247 */       SetupTraining.this.game.setScreen((Screen)new TrainingLoading(SetupTraining.this.game, SetupTraining.this.sceneSettings));
/*     */     }
/*     */   }
/*     */   
/*     */   private class FreeKicksButton
/*     */     extends Button {
/*     */     FreeKicksButton() {
/* 254 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry((1280 - 340) / 2, 460, 340, 40);
/* 255 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 256 */       setText(Assets.strings.get("TRAINING.FREE KICKS"), Font.Align.CENTER, Assets.font14);
/* 257 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PenaltyKicksButton
/*     */     extends Button {
/*     */     PenaltyKicksButton() {
/* 264 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry((1280 - 340) / 2, 520, 340, 40);
/* 265 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 266 */       setText(Assets.strings.get("TRAINING.PENALTY KICKS"), Font.Align.CENTER, Assets.font14);
/* 267 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 274 */       Objects.requireNonNull(SetupTraining.this.game.gui); setGeometry((1280 - 196) / 2, 660, 196, 36);
/* 275 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 276 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 281 */       if (SetupTraining.navigation.folder.equals(Assets.favouritesFile)) {
/* 282 */         SetupTraining.this.game.setScreen((Screen)new SelectFavourite(SetupTraining.this.game));
/*     */       } else {
/* 284 */         SetupTraining.this.game.setScreen((Screen)new SelectTeam(SetupTraining.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SetupTraining.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */