/*     */ package com.ygames.ysoccer.screens;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class ViewStatistics extends GLScreen {
/*     */   ViewStatistics(GLGame game) {
/*  13 */     super(game);
/*     */     
/*  15 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  19 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("STATISTICS"), game.stateColor.body.intValue());
/*  20 */     this.widgets.add(titleBar);
/*     */     
/*  22 */     HighestScorerButton highestScorerButton = new HighestScorerButton();
/*  23 */     this.widgets.add(highestScorerButton);
/*     */     
/*  25 */     setSelectedWidget((Widget)highestScorerButton);
/*     */     
/*  27 */     CompetitionInfoButton competitionInfoButton = new CompetitionInfoButton();
/*  28 */     this.widgets.add(competitionInfoButton);
/*     */     
/*  30 */     ViewSquadsButton viewSquadsButton = new ViewSquadsButton();
/*  31 */     this.widgets.add(viewSquadsButton);
/*     */     
/*  33 */     ExitButton exitButton = new ExitButton();
/*  34 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private class HighestScorerButton
/*     */     extends Button {
/*     */     HighestScorerButton() {
/*  40 */       Objects.requireNonNull(ViewStatistics.this.game.gui); setGeometry((1280 - 340) / 2, 270, 340, 44);
/*  41 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/*  42 */       setText(Assets.strings.get("HIGHEST SCORER LIST"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  47 */       ViewStatistics.this.game.setScreen((Screen)new TopScorers(ViewStatistics.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class CompetitionInfoButton
/*     */     extends Button {
/*     */     CompetitionInfoButton() {
/*  54 */       Objects.requireNonNull(ViewStatistics.this.game.gui); setGeometry((1280 - 340) / 2, 350, 340, 44);
/*  55 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/*  56 */       setText(Assets.strings.get("COMPETITION INFO"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  61 */       switch (ViewStatistics.this.game.competition.type) {
/*     */         case LEAGUE:
/*  63 */           ViewStatistics.this.game.setScreen((Screen)new InfoLeague(ViewStatistics.this.game));
/*     */           break;
/*     */         
/*     */         case CUP:
/*  67 */           ViewStatistics.this.game.setScreen((Screen)new InfoCup(ViewStatistics.this.game));
/*     */           break;
/*     */         
/*     */         case TOURNAMENT:
/*  71 */           ViewStatistics.this.game.setScreen((Screen)new InfoTournament(ViewStatistics.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewSquadsButton
/*     */     extends Button {
/*     */     ViewSquadsButton() {
/*  80 */       Objects.requireNonNull(ViewStatistics.this.game.gui); setGeometry((1280 - 340) / 2, 430, 340, 44);
/*  81 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/*  82 */       setText(Assets.strings.get("VIEW SQUADS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  87 */       ViewStatistics.this.game.setScreen((Screen)new CompetitionViewTeams(ViewStatistics.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/*  94 */       Objects.requireNonNull(ViewStatistics.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/*  95 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/*  96 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 101 */       switch (ViewStatistics.this.game.competition.type) {
/*     */         case LEAGUE:
/* 103 */           ViewStatistics.this.game.setScreen((Screen)new PlayLeague(ViewStatistics.this.game));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 107 */           ViewStatistics.this.game.setScreen((Screen)new PlayCup(ViewStatistics.this.game));
/*     */           break;
/*     */         
/*     */         case TOURNAMENT:
/* 111 */           ViewStatistics.this.game.setScreen((Screen)new PlayTournament(ViewStatistics.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\ViewStatistics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */