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
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class LoadCompetition extends GLScreen {
/*     */   LoadCompetition(GLGame game) {
/*  21 */     super(game);
/*     */     
/*  23 */     this.background = new Texture("images/backgrounds/menu_competition.jpg");
/*     */ 
/*     */ 
/*     */     
/*  27 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("LOAD OLD COMPETITION"), 2660551);
/*  28 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  31 */     List<Widget> competitionButtonsList = new ArrayList<>();
/*     */     
/*  33 */     ArrayList<FileHandle> folders = new ArrayList<>(Arrays.asList(Assets.savesFolder.list()));
/*  34 */     Collections.sort(folders, Assets.fileComparatorByName);
/*  35 */     for (FileHandle folder : folders) {
/*  36 */       if (folder.isDirectory()) {
/*  37 */         ArrayList<FileHandle> files = new ArrayList<>(Arrays.asList(folder.list()));
/*  38 */         Collections.sort(files, Assets.fileComparatorByName);
/*  39 */         for (FileHandle file : files) {
/*  40 */           if (!file.isDirectory() && file.extension().equals("json")) {
/*  41 */             Competition competition = Competition.load(file);
/*  42 */             competition.filename = file.nameWithoutExtension();
/*     */             
/*  44 */             CompetitionButton competitionButton = new CompetitionButton(file.nameWithoutExtension(), competition);
/*  45 */             competitionButtonsList.add(competitionButton);
/*  46 */             this.widgets.add(competitionButton);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  52 */     Objects.requireNonNull(game.gui); Widget.arrange(1280, 380, 34, 20, competitionButtonsList);
/*     */     
/*  54 */     AbortButton abortButton = new AbortButton();
/*  55 */     this.widgets.add(abortButton);
/*     */     
/*  57 */     if (getSelectedWidget() == null)
/*  58 */       setSelectedWidget((Widget)abortButton); 
/*     */   }
/*     */   
/*     */   private class CompetitionButton
/*     */     extends Button
/*     */   {
/*     */     private Competition competition;
/*     */     
/*     */     CompetitionButton(String filename, Competition competition) {
/*  67 */       this.competition = competition;
/*  68 */       setSize(540, 30);
/*  69 */       switch (competition.category) {
/*     */         case LEAGUE:
/*  71 */           setColor(3632687);
/*     */           break;
/*     */         case CUP:
/*  74 */           setColor(4281856);
/*     */           break;
/*     */       } 
/*  77 */       setText(filename, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  82 */       LoadCompetition.this.game.setCompetition(this.competition);
/*  83 */       switch (LoadCompetition.this.game.competition.category) {
/*     */         case LEAGUE:
/*     */         case CUP:
/*  86 */           switch (LoadCompetition.this.game.competition.type) {
/*     */             case LEAGUE:
/*  88 */               LoadCompetition.this.game.setScreen((Screen)new PlayLeague(LoadCompetition.this.game));
/*     */               break;
/*     */             
/*     */             case CUP:
/*  92 */               LoadCompetition.this.game.setScreen((Screen)new PlayCup(LoadCompetition.this.game));
/*     */               break;
/*     */             
/*     */             case TOURNAMENT:
/*  96 */               LoadCompetition.this.game.setScreen((Screen)new PlayTournament(LoadCompetition.this.game));
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 107 */       Objects.requireNonNull(LoadCompetition.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 108 */       setColors(Integer.valueOf(13107214), Integer.valueOf(16718121), Integer.valueOf(7603212));
/* 109 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 114 */       LoadCompetition.this.game.setScreen((Screen)new Main(LoadCompetition.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\LoadCompetition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */