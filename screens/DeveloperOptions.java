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
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.ToggleButton;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DeveloperOptions
/*     */   extends GLScreen
/*     */ {
/*     */   int x0;
/*     */   int y0;
/*     */   int labelWidth;
/*     */   
/*     */   DeveloperOptions(GLGame game) {
/*  31 */     super(game);
/*  32 */     this.background = new Texture("images/backgrounds/menu_game_options.jpg");
/*     */ 
/*     */ 
/*     */     
/*  36 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, "DEVELOPER OPTIONS", 1646512);
/*  37 */     this.widgets.add(titleBar);
/*     */     
/*  39 */     this.labelWidth = 180;
/*  40 */     Objects.requireNonNull(game.gui); this.x0 = 1280 / 2 - 185;
/*  41 */     this.y0 = 160;
/*     */     
/*  43 */     SectionLabel sectionLabel3 = new SectionLabel("GUI");
/*  44 */     this.widgets.add(sectionLabel3);
/*     */     
/*  46 */     this.y0 += 22;
/*  47 */     JavaHeapLabel javaHeapLabel = new JavaHeapLabel();
/*  48 */     this.widgets.add(javaHeapLabel);
/*  49 */     JavaHeapButton javaHeapButton = new JavaHeapButton();
/*  50 */     this.widgets.add(javaHeapButton);
/*  51 */     setSelectedWidget((Widget)javaHeapButton);
/*     */     
/*  53 */     this.y0 += 22;
/*  54 */     TeamValuesLabel teamValuesLabel = new TeamValuesLabel();
/*  55 */     this.widgets.add(teamValuesLabel);
/*  56 */     TeamValuesButton teamValuesButton = new TeamValuesButton();
/*  57 */     this.widgets.add(teamValuesButton);
/*     */     
/*  59 */     this.y0 += 44;
/*  60 */     SectionLabel sectionLabel2 = new SectionLabel("MATCH");
/*  61 */     this.widgets.add(sectionLabel2);
/*     */     
/*  63 */     this.y0 += 22;
/*  64 */     PlayerNumberLabel playerNumberLabel = new PlayerNumberLabel();
/*  65 */     this.widgets.add(playerNumberLabel);
/*  66 */     PlayerNumberButton playerNumberButton = new PlayerNumberButton();
/*  67 */     this.widgets.add(playerNumberButton);
/*     */     
/*  69 */     this.y0 += 22;
/*  70 */     BestDefenderLabel bestDefenderLabel = new BestDefenderLabel();
/*  71 */     this.widgets.add(bestDefenderLabel);
/*  72 */     BestDefenderButton bestDefenderButton = new BestDefenderButton();
/*  73 */     this.widgets.add(bestDefenderButton);
/*     */     
/*  75 */     this.y0 += 22;
/*  76 */     FrameDistanceLabel frameDistanceLabel = new FrameDistanceLabel();
/*  77 */     this.widgets.add(frameDistanceLabel);
/*  78 */     FrameDistanceButton frameDistanceButton = new FrameDistanceButton();
/*  79 */     this.widgets.add(frameDistanceButton);
/*     */     
/*  81 */     this.y0 += 22;
/*  82 */     PlayerStateLabel playerStateLabel = new PlayerStateLabel();
/*  83 */     this.widgets.add(playerStateLabel);
/*  84 */     PlayerStateButton playerStateButton = new PlayerStateButton();
/*  85 */     this.widgets.add(playerStateButton);
/*     */     
/*  87 */     this.y0 += 22;
/*  88 */     PlayerAiStateLabel playerAiStateLabel = new PlayerAiStateLabel();
/*  89 */     this.widgets.add(playerAiStateLabel);
/*  90 */     PlayerAiStateButton playerAiStateButton = new PlayerAiStateButton();
/*  91 */     this.widgets.add(playerAiStateButton);
/*     */     
/*  93 */     this.y0 += 22;
/*  94 */     BallZonesLabel ballZonesLabel = new BallZonesLabel();
/*  95 */     this.widgets.add(ballZonesLabel);
/*  96 */     BallZonesButton ballZonesButton = new BallZonesButton();
/*  97 */     this.widgets.add(ballZonesButton);
/*     */     
/*  99 */     this.y0 += 22;
/* 100 */     BallPredictionsLabel ballPredictionsLabel = new BallPredictionsLabel();
/* 101 */     this.widgets.add(ballPredictionsLabel);
/* 102 */     BallPredictionsButton ballPredictionsButton = new BallPredictionsButton();
/* 103 */     this.widgets.add(ballPredictionsButton);
/*     */     
/* 105 */     this.y0 += 44;
/* 106 */     SectionLabel sectionLabel1 = new SectionLabel("LOGS");
/* 107 */     this.widgets.add(sectionLabel1);
/*     */     
/* 109 */     this.y0 += 22;
/* 110 */     LogLevelLabel logLevelLabel = new LogLevelLabel();
/* 111 */     this.widgets.add(logLevelLabel);
/* 112 */     LogLevelButton logLevelButton = new LogLevelButton();
/* 113 */     this.widgets.add(logLevelButton);
/*     */     
/* 115 */     this.y0 += 22;
/* 116 */     LogFilterLabel logFilterLabel6 = new LogFilterLabel(GLGame.LogType.AI_ATTACKING);
/* 117 */     this.widgets.add(logFilterLabel6);
/* 118 */     LogFilterButton logFilterButton6 = new LogFilterButton(GLGame.LogType.AI_ATTACKING);
/* 119 */     this.widgets.add(logFilterButton6);
/*     */     
/* 121 */     this.y0 += 22;
/* 122 */     LogFilterLabel logFilterLabel5 = new LogFilterLabel(GLGame.LogType.AI_KICKING);
/* 123 */     this.widgets.add(logFilterLabel5);
/* 124 */     LogFilterButton logFilterButton5 = new LogFilterButton(GLGame.LogType.AI_KICKING);
/* 125 */     this.widgets.add(logFilterButton5);
/*     */     
/* 127 */     this.y0 += 22;
/* 128 */     LogFilterLabel logFilterLabel4 = new LogFilterLabel(GLGame.LogType.BALL);
/* 129 */     this.widgets.add(logFilterLabel4);
/* 130 */     LogFilterButton logFilterButton4 = new LogFilterButton(GLGame.LogType.BALL);
/* 131 */     this.widgets.add(logFilterButton4);
/*     */     
/* 133 */     this.y0 += 22;
/* 134 */     LogFilterLabel logFilterLabel3 = new LogFilterLabel(GLGame.LogType.PASSING);
/* 135 */     this.widgets.add(logFilterLabel3);
/* 136 */     LogFilterButton logFilterButton3 = new LogFilterButton(GLGame.LogType.PASSING);
/* 137 */     this.widgets.add(logFilterButton3);
/*     */     
/* 139 */     this.y0 += 22;
/* 140 */     LogFilterLabel logFilterLabel2 = new LogFilterLabel(GLGame.LogType.GUI);
/* 141 */     this.widgets.add(logFilterLabel2);
/* 142 */     LogFilterButton logFilterButton2 = new LogFilterButton(GLGame.LogType.GUI);
/* 143 */     this.widgets.add(logFilterButton2);
/*     */     
/* 145 */     this.y0 += 22;
/* 146 */     LogFilterLabel logFilterLabel1 = new LogFilterLabel(GLGame.LogType.PLAYER_SELECTION);
/* 147 */     this.widgets.add(logFilterLabel1);
/* 148 */     LogFilterButton logFilterButton1 = new LogFilterButton(GLGame.LogType.PLAYER_SELECTION);
/* 149 */     this.widgets.add(logFilterButton1);
/*     */     
/* 151 */     ExitButton exitButton = new ExitButton();
/* 152 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private class SectionLabel
/*     */     extends Button {
/*     */     SectionLabel(String text) {
/* 158 */       setColor(6298975);
/* 159 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 160 */       setText(text, Font.Align.CENTER, Assets.font6);
/* 161 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class JavaHeapLabel
/*     */     extends Button {
/*     */     JavaHeapLabel() {
/* 168 */       setColor(5066061);
/* 169 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 170 */       setText("JAVA HEAP", Font.Align.CENTER, Assets.font6);
/* 171 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class JavaHeapButton
/*     */     extends ToggleButton {
/*     */     JavaHeapButton() {
/* 178 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 179 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 184 */       setColor(Settings.showJavaHeap ? 1925201 : 5066061);
/* 185 */       setText(Settings.showJavaHeap ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 190 */       Settings.showJavaHeap = !Settings.showJavaHeap;
/* 191 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamValuesLabel
/*     */     extends Button {
/*     */     TeamValuesLabel() {
/* 198 */       setColor(5066061);
/* 199 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 200 */       setText("TEAM VALUES", Font.Align.CENTER, Assets.font6);
/* 201 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamValuesButton
/*     */     extends ToggleButton {
/*     */     TeamValuesButton() {
/* 208 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 209 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 214 */       setColor(Settings.showTeamValues ? 1925201 : 5066061);
/* 215 */       setText(Settings.showTeamValues ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 220 */       Settings.showTeamValues = !Settings.showTeamValues;
/* 221 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNumberLabel
/*     */     extends Button {
/*     */     PlayerNumberLabel() {
/* 228 */       setColor(5066061);
/* 229 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 230 */       setText("PLAYER NUMBER", Font.Align.CENTER, Assets.font6);
/* 231 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNumberButton
/*     */     extends ToggleButton {
/*     */     PlayerNumberButton() {
/* 238 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 239 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 244 */       setColor(Settings.showPlayerNumber ? 1925201 : 5066061);
/* 245 */       setText(Settings.showPlayerNumber ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 250 */       Settings.showPlayerNumber = !Settings.showPlayerNumber;
/* 251 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BestDefenderLabel
/*     */     extends Button {
/*     */     BestDefenderLabel() {
/* 258 */       setColor(5066061);
/* 259 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 260 */       setText("BEST DEFENDER", Font.Align.CENTER, Assets.font6);
/* 261 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BestDefenderButton
/*     */     extends ToggleButton {
/*     */     BestDefenderButton() {
/* 268 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 269 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 274 */       setColor(Settings.showBestDefender ? 1925201 : 5066061);
/* 275 */       setText(Settings.showBestDefender ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 280 */       Settings.showBestDefender = !Settings.showBestDefender;
/* 281 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FrameDistanceLabel
/*     */     extends Button {
/*     */     FrameDistanceLabel() {
/* 288 */       setColor(5066061);
/* 289 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 290 */       setText("FRAME DISTANCE", Font.Align.CENTER, Assets.font6);
/* 291 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FrameDistanceButton
/*     */     extends ToggleButton {
/*     */     FrameDistanceButton() {
/* 298 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 299 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 304 */       setColor(Settings.showFrameDistance ? 1925201 : 5066061);
/* 305 */       setText(Settings.showFrameDistance ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 310 */       Settings.showFrameDistance = !Settings.showFrameDistance;
/* 311 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerStateLabel
/*     */     extends Button {
/*     */     PlayerStateLabel() {
/* 318 */       setColor(5066061);
/* 319 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 320 */       setText("PLAYER STATE", Font.Align.CENTER, Assets.font6);
/* 321 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerStateButton
/*     */     extends ToggleButton {
/*     */     PlayerStateButton() {
/* 328 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 329 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 334 */       setColor(Settings.showPlayerState ? 1925201 : 5066061);
/* 335 */       setText(Settings.showPlayerState ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 340 */       Settings.showPlayerState = !Settings.showPlayerState;
/* 341 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerAiStateLabel
/*     */     extends Button {
/*     */     PlayerAiStateLabel() {
/* 348 */       setColor(5066061);
/* 349 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 350 */       setText("PLAYER AI STATE", Font.Align.CENTER, Assets.font6);
/* 351 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerAiStateButton
/*     */     extends ToggleButton {
/*     */     PlayerAiStateButton() {
/* 358 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 359 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 364 */       setColor(Settings.showPlayerAiState ? 1925201 : 5066061);
/* 365 */       setText(Settings.showPlayerAiState ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 370 */       Settings.showPlayerAiState = !Settings.showPlayerAiState;
/* 371 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BallZonesLabel
/*     */     extends Button {
/*     */     BallZonesLabel() {
/* 378 */       setColor(5066061);
/* 379 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 380 */       setText("BALL ZONES", Font.Align.CENTER, Assets.font6);
/* 381 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BallZonesButton
/*     */     extends ToggleButton {
/*     */     BallZonesButton() {
/* 388 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 389 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 394 */       setColor(Settings.showBallZones ? 1925201 : 5066061);
/* 395 */       setText(Settings.showBallZones ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 400 */       Settings.showBallZones = !Settings.showBallZones;
/* 401 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BallPredictionsLabel
/*     */     extends Button {
/*     */     BallPredictionsLabel() {
/* 408 */       setColor(5066061);
/* 409 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 410 */       setText("BALL PREDICTIONS", Font.Align.CENTER, Assets.font6);
/* 411 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BallPredictionsButton
/*     */     extends ToggleButton {
/*     */     BallPredictionsButton() {
/* 418 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 419 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 424 */       setColor(Settings.showBallPredictions ? 1925201 : 5066061);
/* 425 */       setText(Settings.showBallPredictions ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 430 */       Settings.showBallPredictions = !Settings.showBallPredictions;
/* 431 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class LogLevelLabel
/*     */     extends Button {
/*     */     LogLevelLabel() {
/* 438 */       setColor(5066061);
/* 439 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 440 */       setText("LOG LEVEL", Font.Align.CENTER, Assets.font6);
/* 441 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class LogLevelButton
/*     */     extends Button {
/*     */     LogLevelButton() {
/* 448 */       setColor(1925201);
/* 449 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 450 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 455 */       switch (Settings.logLevel) {
/*     */         case 0:
/* 457 */           setText("0: NONE");
/*     */           break;
/*     */         
/*     */         case 1:
/* 461 */           setText("1: ERRORS");
/*     */           break;
/*     */         
/*     */         case 2:
/* 465 */           setText("2: ERRORS + INFO");
/*     */           break;
/*     */         
/*     */         case 3:
/* 469 */           setText("3: ERRORS + INFO + DEBUG");
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 477 */       rotate(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 482 */       rotate(-1);
/*     */     }
/*     */     
/*     */     private void rotate(int n) {
/* 486 */       Settings.logLevel = EMath.rotate(Settings.logLevel, 0, 3, n);
/* 487 */       Gdx.app.setLogLevel(Settings.logLevel);
/* 488 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class LogFilterLabel
/*     */     extends Button
/*     */   {
/*     */     LogFilterLabel(GLGame.LogType logType) {
/* 496 */       setColor(5066061);
/* 497 */       setGeometry(DeveloperOptions.this.x0, DeveloperOptions.this.y0, DeveloperOptions.this.labelWidth, 22);
/* 498 */       setText(logType.toString().replace("_", "-"), Font.Align.CENTER, Assets.font6);
/* 499 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class LogFilterButton
/*     */     extends ToggleButton {
/*     */     private GLGame.LogType logType;
/*     */     
/*     */     LogFilterButton(GLGame.LogType logType) {
/* 508 */       this.logType = logType;
/* 509 */       setGeometry(DeveloperOptions.this.x0 + 10 + DeveloperOptions.this.labelWidth, DeveloperOptions.this.y0, 180, 22);
/* 510 */       setText("", Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 515 */       setColor(GLGame.isSetLogFilter(this.logType) ? 1925201 : 5066061);
/* 516 */       setText(GLGame.isSetLogFilter(this.logType) ? "ON" : "OFF");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void toggle() {
/* 521 */       GLGame.toggleLogFilter(this.logType);
/* 522 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 529 */       setColor(13124096);
/* 530 */       Objects.requireNonNull(DeveloperOptions.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 531 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 536 */       DeveloperOptions.this.game.settings.save();
/* 537 */       DeveloperOptions.this.game.setScreen((Screen)new DeveloperTools(DeveloperOptions.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DeveloperOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */