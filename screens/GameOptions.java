/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.ToggleButton;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ class GameOptions
/*     */   extends GLScreen
/*     */ {
/*     */   GameOptions(GLGame game) {
/*  21 */     super(game);
/*  22 */     this.background = new Texture("images/backgrounds/menu_game_options.jpg");
/*     */ 
/*     */ 
/*     */     
/*  26 */     TitleBar titleBar = new TitleBar();
/*  27 */     this.widgets.add(titleBar);
/*     */     
/*  29 */     LanguageLabel languageLabel = new LanguageLabel();
/*  30 */     this.widgets.add(languageLabel);
/*     */     
/*  32 */     LanguageButton languageButton = new LanguageButton();
/*  33 */     this.widgets.add(languageButton);
/*  34 */     setSelectedWidget((Widget)languageButton);
/*     */     
/*  36 */     ScreenModeLabel screenModeLabel = new ScreenModeLabel();
/*  37 */     this.widgets.add(screenModeLabel);
/*     */     
/*  39 */     ScreenModeButton screenModeButton = new ScreenModeButton();
/*  40 */     this.widgets.add(screenModeButton);
/*     */     
/*  42 */     ShowIntroLabel showIntroLabel = new ShowIntroLabel();
/*  43 */     this.widgets.add(showIntroLabel);
/*     */     
/*  45 */     ShowIntroButton showIntroButton = new ShowIntroButton();
/*  46 */     this.widgets.add(showIntroButton);
/*     */     
/*  48 */     MusicModeLabel musicModeLabel = new MusicModeLabel();
/*  49 */     this.widgets.add(musicModeLabel);
/*     */     
/*  51 */     MusicModeButton musicModeButton = new MusicModeButton();
/*  52 */     this.widgets.add(musicModeButton);
/*     */     
/*  54 */     MusicVolumeLabel musicVolumeLabel = new MusicVolumeLabel();
/*  55 */     this.widgets.add(musicVolumeLabel);
/*     */     
/*  57 */     MusicVolumeButton musicVolumeButton = new MusicVolumeButton();
/*  58 */     this.widgets.add(musicVolumeButton);
/*     */     
/*  60 */     PlayerCountryLabel playerCountryLabel = new PlayerCountryLabel();
/*  61 */     this.widgets.add(playerCountryLabel);
/*     */     
/*  63 */     PlayerCountryButton playerCountryButton = new PlayerCountryButton();
/*  64 */     this.widgets.add(playerCountryButton);
/*     */     
/*  66 */     MaxPlayerValueLabel maxPlayerValueLabel = new MaxPlayerValueLabel();
/*  67 */     this.widgets.add(maxPlayerValueLabel);
/*     */     
/*  69 */     MaxPlayerValueButton maxPlayerValueButton = new MaxPlayerValueButton();
/*  70 */     this.widgets.add(maxPlayerValueButton);
/*     */     
/*  72 */     CurrencyButton currencyButton = new CurrencyButton();
/*  73 */     this.widgets.add(currencyButton);
/*     */     
/*  75 */     ImportButton importButton = new ImportButton();
/*  76 */     this.widgets.add(importButton);
/*     */     
/*  78 */     ExportButton exportButton = new ExportButton();
/*  79 */     this.widgets.add(exportButton);
/*     */     
/*  81 */     QuitToOsButton quitToOsButton = new QuitToOsButton();
/*  82 */     this.widgets.add(quitToOsButton);
/*     */     
/*  84 */     ExitButton exitButton = new ExitButton();
/*  85 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private class TitleBar
/*     */     extends Button {
/*     */     TitleBar() {
/*  91 */       setColor(5467024);
/*  92 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry((1280 - 960) / 2, 20, 960, 40);
/*  93 */       setText("", Font.Align.CENTER, Assets.font14);
/*  94 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/*  99 */       setText(Assets.gettext("GAME OPTIONS"));
/*     */     }
/*     */   }
/*     */   
/*     */   private class LanguageLabel
/*     */     extends Button {
/*     */     LanguageLabel() {
/* 106 */       setColor(8388608);
/* 107 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 150, 440, 40);
/* 108 */       setText("", Font.Align.CENTER, Assets.font14);
/* 109 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 114 */       setText(Assets.gettext("LANGUAGE"));
/*     */     }
/*     */   }
/*     */   
/*     */   private class LanguageButton
/*     */     extends Button {
/*     */     LanguageButton() {
/* 121 */       setColor(2039701);
/* 122 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 + 10, 150, 440, 40);
/* 123 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 128 */       setText(Assets.gettext("// THIS LANGUAGE NAME //"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 133 */       updateLanguage(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 138 */       updateLanguage(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 143 */       updateLanguage(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 148 */       updateLanguage(-1);
/*     */     }
/*     */     
/*     */     private void updateLanguage(int direction) {
/* 152 */       int index = Assets.locales.indexOf(GameOptions.this.game.settings.locale);
/* 153 */       GameOptions.this.game.settings.locale = Assets.locales.get(EMath.rotate(index, 0, Assets.locales.size() - 1, direction));
/* 154 */       Assets.loadStrings(GameOptions.this.game.settings);
/* 155 */       GameOptions.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ScreenModeLabel
/*     */     extends Button {
/*     */     ScreenModeLabel() {
/* 162 */       setColor(8388608);
/* 163 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 200, 440, 40);
/* 164 */       setText("", Font.Align.CENTER, Assets.font14);
/* 165 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 170 */       setText(Assets.gettext("SCREEN MODE"));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ScreenModeButton
/*     */     extends Button {
/*     */     ScreenModeButton() {
/* 177 */       setColor(2039701);
/* 178 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 + 10, 200, 440, 40);
/* 179 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 184 */       setText(Assets.gettext(GameOptions.this.game.settings.fullScreen ? "SCREEN MODE.FULL SCREEN" : "SCREEN MODE.WINDOW"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 189 */       toggleFullScreen();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 194 */       toggleFullScreen();
/*     */     }
/*     */     
/*     */     private void toggleFullScreen() {
/* 198 */       GameOptions.this.game.settings.fullScreen = !GameOptions.this.game.settings.fullScreen;
/* 199 */       GameOptions.this.game.setScreenMode(GameOptions.this.game.settings.fullScreen);
/* 200 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ShowIntroLabel
/*     */     extends Button {
/*     */     ShowIntroLabel() {
/* 207 */       setColor(8388608);
/* 208 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 250, 440, 40);
/* 209 */       setText("", Font.Align.CENTER, Assets.font14);
/* 210 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 215 */       setText(Assets.gettext("SHOW INTRO"));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ShowIntroButton
/*     */     extends ToggleButton {
/*     */     ShowIntroButton() {
/* 222 */       setColor(2039701);
/* 223 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 + 10, 250, 440, 40);
/* 224 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 229 */       setText(Assets.gettext(GameOptions.this.game.settings.showIntro ? "SHOW INTRO.ON" : "SHOW INTRO.OFF"));
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 234 */       GameOptions.this.game.settings.showIntro = !GameOptions.this.game.settings.showIntro;
/* 235 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class MusicModeLabel
/*     */     extends Button {
/*     */     MusicModeLabel() {
/* 242 */       setColor(8388608);
/* 243 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 300, 440, 40);
/* 244 */       setText("", Font.Align.CENTER, Assets.font14);
/* 245 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 250 */       setText(Assets.gettext("MUSIC.MODE"));
/*     */     }
/*     */   }
/*     */   
/*     */   private class MusicModeButton
/*     */     extends Button {
/*     */     MusicModeButton() {
/* 257 */       setColor(2039701);
/* 258 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 + 10, 300, 440, 40);
/* 259 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 264 */       switch (GameOptions.this.game.settings.musicMode) {
/*     */         
/*     */         case -2:
/* 267 */           setText(Assets.gettext("MUSIC.ALL"));
/*     */           return;
/*     */         
/*     */         case -1:
/* 271 */           setText(Assets.gettext("MUSIC.SHUFFLE"));
/*     */           return;
/*     */       } 
/*     */       
/* 275 */       String trackName = GameOptions.this.game.menuMusic.getCurrentTrackName();
/* 276 */       setText((trackName.length() > 28) ? (trackName.substring(0, 28) + "...") : trackName);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 283 */       updateMusicMode(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 288 */       updateMusicMode(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 293 */       updateMusicMode(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 298 */       updateMusicMode(-1);
/*     */     }
/*     */     
/*     */     private void updateMusicMode(int n) {
/* 302 */       GameOptions.this.game.settings.musicMode = EMath.rotate(GameOptions.this.game.settings.musicMode, GameOptions.this.game.menuMusic.getModeMin(), GameOptions.this.game.menuMusic.getModeMax(), n);
/* 303 */       GameOptions.this.game.menuMusic.setMode(GameOptions.this.game.settings.musicMode);
/* 304 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class MusicVolumeLabel
/*     */     extends Button {
/*     */     MusicVolumeLabel() {
/* 311 */       setColor(8388608);
/* 312 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 350, 440, 40);
/* 313 */       setText("", Font.Align.CENTER, Assets.font14);
/* 314 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 319 */       setText(Assets.gettext("MUSIC.VOLUME"));
/*     */     }
/*     */   }
/*     */   
/*     */   private class MusicVolumeButton
/*     */     extends Button {
/*     */     MusicVolumeButton() {
/* 326 */       setColor(2039701);
/* 327 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 + 10, 350, 440, 40);
/* 328 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 333 */       if (GameOptions.this.game.settings.musicVolume == 0) {
/* 334 */         setText(Assets.gettext("MUSIC.OFF"));
/*     */       } else {
/* 336 */         setText(GameOptions.this.game.settings.musicVolume / 10);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 342 */       updateMusicVolume(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 347 */       updateMusicVolume(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 352 */       updateMusicVolume(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 357 */       updateMusicVolume(-1);
/*     */     }
/*     */     
/*     */     private void updateMusicVolume(int n) {
/* 361 */       GameOptions.this.game.settings.musicVolume = EMath.slide(GameOptions.this.game.settings.musicVolume, 0, 100, 10 * n);
/* 362 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerCountryLabel
/*     */     extends Button {
/*     */     PlayerCountryLabel() {
/* 369 */       setColor(8388608);
/* 370 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 400, 440, 40);
/* 371 */       setText("", Font.Align.CENTER, Assets.font14);
/* 372 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 377 */       setText(Assets.gettext("PLAYER'S COUNTRY"));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerCountryButton
/*     */     extends Button {
/*     */     PlayerCountryButton() {
/* 384 */       setColor(2039701);
/* 385 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 + 10, 400, 440, 40);
/* 386 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 391 */       setText(Assets.gettext(GameOptions.this.game.settings.useFlags ? "FLAG" : "CODE"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 396 */       updatePlayerCountry();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 401 */       updatePlayerCountry();
/*     */     }
/*     */     
/*     */     private void updatePlayerCountry() {
/* 405 */       GameOptions.this.game.settings.useFlags = !GameOptions.this.game.settings.useFlags;
/* 406 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class MaxPlayerValueLabel
/*     */     extends Button {
/*     */     MaxPlayerValueLabel() {
/* 413 */       setColor(8388608);
/* 414 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 - 10 - 440, 450, 440, 40);
/* 415 */       setText("", Font.Align.CENTER, Assets.font14);
/* 416 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 421 */       setText(Assets.gettext("MAX PLAYER VALUE"));
/*     */     }
/*     */   }
/*     */   
/*     */   private class MaxPlayerValueButton
/*     */     extends Button {
/*     */     MaxPlayerValueButton() {
/* 428 */       setColor(2039701);
/* 429 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 + 10, 450, 300, 40);
/* 430 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 435 */       setText(Assets.moneyFormat(GameOptions.this.game.settings.maxPlayerValue));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 440 */       updateMaxPlayerValue(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 445 */       updateMaxPlayerValue(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 450 */       updateMaxPlayerValue(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 455 */       updateMaxPlayerValue(-1);
/*     */     }
/*     */     
/*     */     private void updateMaxPlayerValue(int direction) {
/* 459 */       int e = (int)Math.log10(GameOptions.this.game.settings.maxPlayerValue);
/* 460 */       int m = (int)(GameOptions.this.game.settings.maxPlayerValue / Math.pow(10.0D, e));
/* 461 */       if (direction == 1) {
/* 462 */         if (e < 11 || m < 5) {
/* 463 */           switch (m) {
/*     */             case 1:
/* 465 */               m = 2;
/*     */               break;
/*     */             
/*     */             case 2:
/* 469 */               m = 5;
/*     */               break;
/*     */             
/*     */             case 5:
/* 473 */               m = 1;
/* 474 */               e++;
/*     */               break;
/*     */           } 
/*     */         }
/* 478 */       } else if (direction == -1 && (
/* 479 */         e > 4 || m > 1)) {
/* 480 */         switch (m) {
/*     */           case 5:
/* 482 */             m = 2;
/*     */             break;
/*     */           
/*     */           case 2:
/* 486 */             m = 1;
/*     */             break;
/*     */           
/*     */           case 1:
/* 490 */             m = 5;
/* 491 */             e--;
/*     */             break;
/*     */         } 
/*     */       
/*     */       } 
/* 496 */       GameOptions.this.game.settings.maxPlayerValue = m * Math.pow(10.0D, e);
/* 497 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CurrencyButton
/*     */     extends Button {
/*     */     CurrencyButton() {
/* 504 */       setColor(2039701);
/* 505 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 + 10 + 300 + 10, 450, 130, 40);
/* 506 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 511 */       setText(GameOptions.this.game.settings.currency);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 516 */       updateCurrency(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 521 */       updateCurrency(-1);
/*     */     }
/*     */     
/*     */     private void updateCurrency(int direction) {
/* 525 */       int i = Assets.currencies.indexOf(GameOptions.this.game.settings.currency);
/* 526 */       if (i == -1) {
/* 527 */         i = 0;
/*     */       } else {
/* 529 */         i = EMath.rotate(i, 0, Assets.currencies.size() - 1, direction);
/*     */       } 
/* 531 */       GameOptions.this.game.settings.currency = Assets.currencies.get(i);
/* 532 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ImportButton
/*     */     extends Button {
/*     */     ImportButton() {
/* 539 */       setColor(7744398);
/* 540 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 - 260 - 10, 530, 260, 36);
/* 541 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 546 */       setText(Assets.gettext("IMPORT"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 551 */       GameOptions.this.game.setScreen((Screen)new ImportTeams(GameOptions.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExportButton
/*     */     extends Button {
/*     */     ExportButton() {
/* 558 */       setColor(7744398);
/* 559 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry(1280 / 2 + 10, 530, 260, 36);
/* 560 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 565 */       setText(Assets.gettext("EXPORT"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 570 */       GameOptions.this.game.setScreen((Screen)new ExportTeams(GameOptions.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class QuitToOsButton
/*     */     extends Button {
/*     */     QuitToOsButton() {
/* 577 */       setColor(32896);
/* 578 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry((1280 - 300) / 2, 580, 300, 36);
/* 579 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 584 */       setText(Assets.gettext("QUIT TO OS"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 589 */       Gdx.app.exit();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 596 */       setColor(13124096);
/* 597 */       Objects.requireNonNull(GameOptions.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 598 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 603 */       setText(Assets.gettext("EXIT"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 608 */       GameOptions.this.game.settings.save();
/* 609 */       GameOptions.this.game.setScreen((Screen)new Main(GameOptions.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\GameOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */