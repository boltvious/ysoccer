/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.strongjoshua.console.CommandExecutor;
/*     */ import com.strongjoshua.console.Console;
/*     */ import com.strongjoshua.console.GUIConsole;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.MatchConsoleCommandExecutor;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class MatchScreen
/*     */   extends GLScreen {
/*     */   private final Match match;
/*     */   private boolean matchStarted;
/*     */   private boolean matchPaused;
/*     */   private boolean matchEnded;
/*     */   private Console console;
/*     */   
/*     */   MatchScreen(GLGame game, Match match) {
/*  30 */     super(game);
/*  31 */     this.match = match;
/*     */     
/*  33 */     this.playMenuMusic = false;
/*  34 */     this.usesMouse = false;
/*     */     
/*  36 */     this.matchStarted = false;
/*  37 */     this.matchPaused = false;
/*  38 */     this.matchEnded = false;
/*  39 */     game.glGraphics.light = 0;
/*     */     
/*  41 */     match.listener = new Match.MatchListener() {
/*     */         public void quitMatch(boolean matchCompleted) {
/*  43 */           MatchScreen.this.quit(matchCompleted);
/*     */         }
/*     */       };
/*     */     
/*  47 */     if (Settings.development) {
/*  48 */       this.console = (Console)new GUIConsole();
/*  49 */       this.console.setSizePercent(25.0F, 100.0F);
/*  50 */       this.console.setPositionPercent(0.0F, 0.0F);
/*  51 */       this.console.setHoverAlpha(0.9F);
/*  52 */       this.console.setNoHoverAlpha(0.9F);
/*  53 */       this.console.setCommandExecutor((CommandExecutor)new MatchConsoleCommandExecutor(match));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float deltaTime) {
/*  59 */     super.render(deltaTime);
/*     */     
/*  61 */     if (!this.matchStarted) {
/*  62 */       this.match.start();
/*  63 */       this.matchStarted = true;
/*     */     } 
/*     */     
/*  66 */     if (!this.matchPaused) {
/*  67 */       this.match.update(deltaTime);
/*     */     }
/*     */     
/*  70 */     if (!this.matchEnded) {
/*  71 */       this.match.render();
/*     */     }
/*     */     
/*  74 */     if (!this.matchEnded && Settings.development) {
/*  75 */       this.console.draw();
/*  76 */       this.matchPaused = this.console.isVisible();
/*     */     } 
/*     */     
/*  79 */     if (Settings.development && Settings.showJavaHeap) {
/*  80 */       this.batch.begin();
/*  81 */       Objects.requireNonNull(this.game.gui); Assets.font10.draw((SpriteBatch)this.batch, String.format(Locale.getDefault(), "%,d", new Object[] { Long.valueOf(Gdx.app.getJavaHeap()) }), 1280 - 120, 10, Font.Align.LEFT);
/*  82 */       this.batch.end();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(int width, int height) {
/*  88 */     super.resize(width, height);
/*     */     
/*  90 */     this.match.resize(width, height);
/*     */     
/*  92 */     if (Settings.development) {
/*  93 */       this.console.refresh();
/*     */     }
/*     */   }
/*     */   
/*     */   private void quit(boolean matchCompleted) {
/*  98 */     this.matchEnded = true;
/*     */     
/* 100 */     for (int t = 0; t <= 1; t++) {
/* 101 */       int len = (this.match.team[t]).lineup.size();
/* 102 */       for (int i = 0; i < len; i++) {
/* 103 */         Player player = (this.match.team[t]).lineup.get(i);
/* 104 */         if (player.role == Player.Role.GOALKEEPER) {
/* 105 */           Assets.unloadKeeper(player);
/*     */         } else {
/* 107 */           Assets.unloadPlayer(player);
/*     */         } 
/* 109 */         Assets.unloadHair(player);
/*     */       } 
/* 111 */       (this.match.team[t]).lineup.clear();
/*     */     } 
/*     */     
/* 114 */     if (matchCompleted) {
/* 115 */       this.match.competition.matchCompleted();
/* 116 */     } else if (this.match.clock > 0.0F) {
/* 117 */       this.match.competition.matchInterrupted();
/*     */     } 
/*     */     
/* 120 */     this.game.enableMouse();
/* 121 */     switch (this.match.competition.type) {
/*     */       case FRIENDLY:
/* 123 */         this.game.setScreen((Screen)new ReplayMatch(this.game, this.match));
/*     */         break;
/*     */       
/*     */       case LEAGUE:
/* 127 */         this.game.setScreen((Screen)new PlayLeague(this.game));
/*     */         break;
/*     */       
/*     */       case CUP:
/* 131 */         this.game.setScreen((Screen)new PlayCup(this.game));
/*     */         break;
/*     */       
/*     */       case TOURNAMENT:
/* 135 */         this.game.setScreen((Screen)new PlayTournament(this.game));
/*     */         break;
/*     */       
/*     */       case TEST_MATCH:
/* 139 */         this.game.setScreen((Screen)new DeveloperTools(this.game));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\MatchScreen.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */