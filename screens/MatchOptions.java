/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.SceneRenderer;
/*     */ import com.ygames.ysoccer.match.Weather;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class MatchOptions extends GLScreen {
/*     */   MatchOptions(GLGame game) {
/*  21 */     super(game);
/*     */     
/*  23 */     this.background = new Texture("images/backgrounds/menu_match_options.jpg");
/*     */ 
/*     */ 
/*     */     
/*  27 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("MATCH OPTIONS"), 3688355);
/*  28 */     this.widgets.add(titleBar);
/*     */     
/*  30 */     MatchLengthLabel matchLengthLabel = new MatchLengthLabel();
/*  31 */     this.widgets.add(matchLengthLabel);
/*     */     
/*  33 */     MatchLengthButton matchLengthButton = new MatchLengthButton();
/*  34 */     this.widgets.add(matchLengthButton);
/*     */     
/*  36 */     setSelectedWidget((Widget)matchLengthButton);
/*     */     
/*  38 */     WeatherEffectsLabel weatherEffectsLabel = new WeatherEffectsLabel();
/*  39 */     this.widgets.add(weatherEffectsLabel);
/*     */     
/*  41 */     WeatherEffectsButton weatherEffectsButton = new WeatherEffectsButton();
/*  42 */     this.widgets.add(weatherEffectsButton);
/*     */     
/*  44 */     RadarLabel radarLabel = new RadarLabel();
/*  45 */     this.widgets.add(radarLabel);
/*     */     
/*  47 */     RadarButton radarButton = new RadarButton();
/*  48 */     this.widgets.add(radarButton);
/*     */     
/*  50 */     AutoReplaysLabel autoReplaysLabel = new AutoReplaysLabel();
/*  51 */     this.widgets.add(autoReplaysLabel);
/*     */     
/*  53 */     AutoReplaysButton autoReplaysButton = new AutoReplaysButton();
/*  54 */     this.widgets.add(autoReplaysButton);
/*     */     
/*  56 */     ZoomLabel zoomLabel = new ZoomLabel();
/*  57 */     this.widgets.add(zoomLabel);
/*     */     
/*  59 */     ZoomButton zoomButton = new ZoomButton();
/*  60 */     this.widgets.add(zoomButton);
/*     */     
/*  62 */     SfxVolumeLabel sfxVolumeLabel = new SfxVolumeLabel();
/*  63 */     this.widgets.add(sfxVolumeLabel);
/*     */     
/*  65 */     SfxVolumeButton sfxVolumeButton = new SfxVolumeButton();
/*  66 */     this.widgets.add(sfxVolumeButton);
/*     */     
/*  68 */     CommentaryLabel commentaryLabel = new CommentaryLabel();
/*  69 */     this.widgets.add(commentaryLabel);
/*     */     
/*  71 */     CommentaryButton commentaryButton = new CommentaryButton();
/*  72 */     this.widgets.add(commentaryButton);
/*     */     
/*  74 */     ExitButton exitButton = new ExitButton();
/*  75 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private class MatchLengthLabel
/*     */     extends Button {
/*     */     MatchLengthLabel() {
/*  81 */       setColor(7759932);
/*  82 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 190, 440, 40);
/*  83 */       setText(Assets.strings.get("FRIENDLY/DIY GAME LENGTH"), Font.Align.CENTER, Assets.font14);
/*  84 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class MatchLengthButton
/*     */     extends Button {
/*     */     List<Integer> matchLengths;
/*     */     
/*     */     MatchLengthButton() {
/*  93 */       this.matchLengths = Arrays.asList(Settings.matchLengths);
/*  94 */       setColor(2837089);
/*  95 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 + 10, 190, 440, 40);
/*  96 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 101 */       setText(Assets.strings.get("%n MINUTES").replaceFirst("%n", "" + MatchOptions.this.game.settings.matchLength));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 106 */       updateMatchLength(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 111 */       updateMatchLength(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 116 */       updateMatchLength(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 121 */       updateMatchLength(-1);
/*     */     }
/*     */     
/*     */     private void updateMatchLength(int n) {
/* 125 */       int index = this.matchLengths.indexOf(Integer.valueOf(MatchOptions.this.game.settings.matchLength));
/* 126 */       MatchOptions.this.game.settings.matchLength = ((Integer)this.matchLengths.get(EMath.slide(index, 0, this.matchLengths.size() - 1, n))).intValue();
/* 127 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeatherEffectsLabel
/*     */     extends Button {
/*     */     WeatherEffectsLabel() {
/* 134 */       setColor(7759932);
/* 135 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 240, 440, 40);
/* 136 */       setText(Assets.strings.get("WEATHER.EFFECTS"), Font.Align.CENTER, Assets.font14);
/* 137 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class WeatherEffectsButton
/*     */     extends Button {
/*     */     WeatherEffectsButton() {
/* 144 */       setColor(2837089);
/* 145 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 + 10, 240, 440, 40);
/* 146 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 151 */       setText(Assets.strings.get(Weather.Strength.names[MatchOptions.this.game.settings.weatherMaxStrength]));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 156 */       updateWeatherMaxStrength(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 161 */       updateWeatherMaxStrength(-1);
/*     */     }
/*     */     
/*     */     private void updateWeatherMaxStrength(int n) {
/* 165 */       MatchOptions.this.game.settings.weatherMaxStrength = EMath.rotate(MatchOptions.this.game.settings.weatherMaxStrength, 0, 2, n);
/* 166 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RadarLabel
/*     */     extends Button {
/*     */     RadarLabel() {
/* 173 */       setColor(7759932);
/* 174 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 290, 440, 40);
/* 175 */       setText(Assets.strings.get("RADAR"), Font.Align.CENTER, Assets.font14);
/* 176 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RadarButton
/*     */     extends Button {
/*     */     RadarButton() {
/* 183 */       setColor(2837089);
/* 184 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 + 10, 290, 440, 40);
/* 185 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 190 */       setText(Assets.strings.get(MatchOptions.this.game.settings.radar ? "RADAR.ON" : "RADAR.OFF"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 195 */       toggleRadar();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 200 */       toggleRadar();
/*     */     }
/*     */     
/*     */     private void toggleRadar() {
/* 204 */       MatchOptions.this.game.settings.radar = !MatchOptions.this.game.settings.radar;
/* 205 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class AutoReplaysLabel
/*     */     extends Button {
/*     */     AutoReplaysLabel() {
/* 212 */       setColor(7759932);
/* 213 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 340, 440, 40);
/* 214 */       setText(Assets.strings.get("AUTO REPLAYS"), Font.Align.CENTER, Assets.font14);
/* 215 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class AutoReplaysButton
/*     */     extends Button {
/*     */     AutoReplaysButton() {
/* 222 */       setColor(2837089);
/* 223 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 + 10, 340, 440, 40);
/* 224 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 229 */       setText(Assets.strings.get(MatchOptions.this.game.settings.autoReplays ? "AUTO REPLAYS.ON" : "AUTO REPLAYS.OFF"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 234 */       toggleAutoReplays();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 239 */       toggleAutoReplays();
/*     */     }
/*     */     
/*     */     private void toggleAutoReplays() {
/* 243 */       MatchOptions.this.game.settings.autoReplays = !MatchOptions.this.game.settings.autoReplays;
/* 244 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ZoomLabel
/*     */     extends Button {
/*     */     ZoomLabel() {
/* 251 */       setColor(7759932);
/* 252 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 390, 440, 40);
/* 253 */       setText(Assets.strings.get("ZOOM"), Font.Align.CENTER, Assets.font14);
/* 254 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ZoomButton
/*     */     extends Button {
/*     */     ZoomButton() {
/* 261 */       setColor(2837089);
/* 262 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 + 10, 390, 440, 40);
/* 263 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 268 */       setText(MatchOptions.this.game.settings.zoom + "%");
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 273 */       updateZoom(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 278 */       updateZoom(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 283 */       updateZoom(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 288 */       updateZoom(-1);
/*     */     }
/*     */     
/*     */     private void updateZoom(int n) {
/* 292 */       MatchOptions.this.game.settings.zoom = EMath.slide(MatchOptions.this.game.settings.zoom, SceneRenderer.zoomMin(), SceneRenderer.zoomMax(), 5 * n);
/* 293 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SfxVolumeLabel
/*     */     extends Button {
/*     */     SfxVolumeLabel() {
/* 300 */       setColor(7759932);
/* 301 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 440, 440, 40);
/* 302 */       setText(Assets.strings.get("MATCH OPTIONS.SOUND VOLUME"), Font.Align.CENTER, Assets.font14);
/* 303 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SfxVolumeButton
/*     */     extends Button {
/*     */     SfxVolumeButton() {
/* 310 */       setColor(2837089);
/* 311 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 + 10, 440, 440, 40);
/* 312 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 317 */       if (MatchOptions.this.game.settings.soundVolume == 0) {
/* 318 */         setText(Assets.strings.get("MATCH OPTIONS.SOUND VOLUME.OFF"));
/*     */       } else {
/* 320 */         setText(MatchOptions.this.game.settings.soundVolume / 10);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 326 */       updateSfxVolume(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 331 */       updateSfxVolume(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 336 */       updateSfxVolume(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 341 */       updateSfxVolume(-1);
/*     */     }
/*     */     
/*     */     private void updateSfxVolume(int n) {
/* 345 */       MatchOptions.this.game.settings.soundVolume = EMath.slide(MatchOptions.this.game.settings.soundVolume, 0, 100, 10 * n);
/* 346 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CommentaryLabel
/*     */     extends Button {
/*     */     CommentaryLabel() {
/* 353 */       setColor(7759932);
/* 354 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 490, 440, 40);
/* 355 */       setText(Assets.strings.get("MATCH OPTIONS.COMMENTARY"), Font.Align.CENTER, Assets.font14);
/* 356 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CommentaryButton
/*     */     extends Button {
/*     */     CommentaryButton() {
/* 363 */       setColor(2837089);
/* 364 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry(1280 / 2 + 10, 490, 440, 40);
/* 365 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 370 */       setText(Assets.strings.get(MatchOptions.this.game.settings.commentary ? "MATCH OPTIONS.COMMENTARY.ON" : "MATCH OPTIONS.COMMENTARY.OFF"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 375 */       toggleCommentary();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 380 */       toggleCommentary();
/*     */     }
/*     */     
/*     */     private void toggleCommentary() {
/* 384 */       MatchOptions.this.game.settings.commentary = !MatchOptions.this.game.settings.commentary;
/* 385 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 392 */       setColor(13124096);
/* 393 */       Objects.requireNonNull(MatchOptions.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 394 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 399 */       MatchOptions.this.game.settings.save();
/* 400 */       MatchOptions.this.game.setScreen((Screen)new Main(MatchOptions.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\MatchOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */