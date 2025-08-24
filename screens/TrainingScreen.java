/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.strongjoshua.console.CommandExecutor;
/*     */ import com.strongjoshua.console.Console;
/*     */ import com.strongjoshua.console.GUIConsole;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Training;
/*     */ import com.ygames.ysoccer.match.TrainingConsoleCommandExecutor;
/*     */ 
/*     */ 
/*     */ class TrainingScreen
/*     */   extends GLScreen
/*     */ {
/*     */   private final Training training;
/*     */   private boolean started;
/*     */   private boolean paused;
/*     */   private boolean ended;
/*     */   private Console console;
/*     */   
/*     */   TrainingScreen(GLGame game, Training training) {
/*  26 */     super(game);
/*     */     
/*  28 */     this.training = training;
/*  29 */     this.usesMouse = false;
/*     */     
/*  31 */     this.started = false;
/*  32 */     this.paused = false;
/*  33 */     this.ended = false;
/*  34 */     game.glGraphics.light = 0;
/*     */     
/*  36 */     training.listener = new Training.TrainingListener() {
/*     */         public void quitTraining() {
/*  38 */           TrainingScreen.this.quit();
/*     */         }
/*     */       };
/*     */     
/*  42 */     if (Settings.development) {
/*  43 */       this.console = (Console)new GUIConsole();
/*  44 */       this.console.setSizePercent(25.0F, 100.0F);
/*  45 */       this.console.setPositionPercent(0.0F, 0.0F);
/*  46 */       this.console.setHoverAlpha(0.9F);
/*  47 */       this.console.setNoHoverAlpha(0.9F);
/*  48 */       this.console.setCommandExecutor((CommandExecutor)new TrainingConsoleCommandExecutor(training));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float deltaTime) {
/*  54 */     super.render(deltaTime);
/*     */     
/*  56 */     if (!this.started) {
/*  57 */       this.training.start();
/*  58 */       this.started = true;
/*     */     } 
/*     */     
/*  61 */     if (!this.paused) {
/*  62 */       this.training.update(deltaTime);
/*     */     }
/*     */     
/*  65 */     if (!this.ended) {
/*  66 */       this.training.render();
/*     */     }
/*     */     
/*  69 */     if (Settings.development) {
/*  70 */       this.console.draw();
/*  71 */       this.paused = this.console.isVisible();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(int width, int height) {
/*  77 */     super.resize(width, height);
/*     */     
/*  79 */     this.training.resize(width, height);
/*     */     
/*  81 */     if (Settings.development) {
/*  82 */       this.console.refresh();
/*     */     }
/*     */   }
/*     */   
/*     */   private void quit() {
/*  87 */     this.ended = true;
/*  88 */     this.game.enableMouse();
/*     */     
/*  90 */     for (int t = 0; t <= 1; t++) {
/*  91 */       int len = (this.training.team[t]).lineup.size();
/*  92 */       for (int i = 0; i < len; i++) {
/*  93 */         Player player = (this.training.team[t]).lineup.get(i);
/*  94 */         if (player.role == Player.Role.GOALKEEPER) {
/*  95 */           Assets.unloadKeeper(player);
/*     */         } else {
/*  97 */           Assets.unloadPlayer(player);
/*     */         } 
/*  99 */         Assets.unloadHair(player);
/*     */       } 
/* 101 */       (this.training.team[t]).lineup.clear();
/*     */     } 
/*     */     
/* 104 */     this.game.setScreen((Screen)new SetupTraining(this.game));
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\TrainingScreen.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */