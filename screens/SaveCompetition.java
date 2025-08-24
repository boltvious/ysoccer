/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.InputButton;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class SaveCompetition extends GLScreen {
/*     */   private Button fileNameButton;
/*     */   
/*     */   SaveCompetition(GLGame game) {
/*  24 */     super(game);
/*     */     
/*  26 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  30 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("SAVE %s").replace("%s", game.competition.name), game.stateColor.body.intValue());
/*  31 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  34 */     List<Widget> competitionButtonsList = new ArrayList<>();
/*  35 */     List<Widget> categoryLabelsList = new ArrayList<>();
/*     */     
/*  37 */     FileHandle folder = Assets.savesFolder.child(game.competition.getCategoryFolder());
/*  38 */     ArrayList<FileHandle> files = new ArrayList<>(Arrays.asList(folder.list()));
/*  39 */     Collections.sort(files, Assets.fileComparatorByName);
/*  40 */     for (FileHandle file : files) {
/*  41 */       if (!file.isDirectory() && file.extension().equals("json")) {
/*  42 */         CompetitionButton competitionButton = new CompetitionButton(file.nameWithoutExtension());
/*  43 */         competitionButtonsList.add(competitionButton);
/*  44 */         this.widgets.add(competitionButton);
/*     */         
/*  46 */         CategoryLabel categoryLabel = new CategoryLabel(game.competition);
/*  47 */         categoryLabelsList.add(categoryLabel);
/*  48 */         this.widgets.add(categoryLabel);
/*     */       } 
/*     */     } 
/*     */     
/*  52 */     if (competitionButtonsList.size() > 0) {
/*  53 */       int len = competitionButtonsList.size();
/*  54 */       for (int i = 0; i < len; i++) {
/*  55 */         Widget b = competitionButtonsList.get(i);
/*  56 */         Widget l = categoryLabelsList.get(i);
/*  57 */         Objects.requireNonNull(game.gui); b.x = (1280 - b.w - l.w - 4) / 2;
/*  58 */         b.y = 300 + 34 * (i - len / 2);
/*  59 */         Objects.requireNonNull(game.gui); l.x = (1280 + b.w - l.w + 4) / 2;
/*  60 */         l.y = 300 + 34 * (i - len / 2);
/*     */       } 
/*     */     } 
/*     */     
/*  64 */     FilenameLabel filenameLabel = new FilenameLabel();
/*  65 */     this.widgets.add(filenameLabel);
/*     */     
/*  67 */     this.fileNameButton = (Button)new FilenameButton();
/*  68 */     this.widgets.add(this.fileNameButton);
/*     */     
/*  70 */     SaveButton saveButton = new SaveButton();
/*  71 */     this.widgets.add(saveButton);
/*  72 */     setSelectedWidget((Widget)saveButton);
/*     */     
/*  74 */     AbortButton abortButton = new AbortButton();
/*  75 */     this.widgets.add(abortButton);
/*     */   }
/*     */   
/*     */   private class CompetitionButton
/*     */     extends Button {
/*     */     private String filename;
/*     */     
/*     */     CompetitionButton(String filename) {
/*  83 */       this.filename = filename;
/*  84 */       setSize(540, 30);
/*  85 */       setColors(Integer.valueOf(1789317), Integer.valueOf(2452151), Integer.valueOf(7486));
/*  86 */       setText(filename, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  91 */       SaveCompetition.this.game.competition.saveAndSetFilename(this.filename);
/*  92 */       SaveCompetition.this.game.setScreen((Screen)new Main(SaveCompetition.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class CategoryLabel
/*     */     extends Button {
/*     */     CategoryLabel(Competition competition) {
/*  99 */       setSize(180, 30);
/* 100 */       setText(competition.getCategoryFolder(), Font.Align.CENTER, Assets.font14);
/* 101 */       switch (competition.category) {
/*     */         case DIY_COMPETITION:
/* 103 */           setColor(3632687);
/*     */           break;
/*     */         case PRESET_COMPETITION:
/* 106 */           setColor(4281856);
/*     */           break;
/*     */       } 
/* 109 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FilenameLabel
/*     */     extends Label {
/*     */     FilenameLabel() {
/* 116 */       Objects.requireNonNull(SaveCompetition.this.game.gui); setGeometry(1280 / 2 - 360 - 2, 550, 180, 36);
/* 117 */       setColors(Integer.valueOf(10244650), Integer.valueOf(12278309), Integer.valueOf(6895645));
/* 118 */       setText(Assets.strings.get("FILENAME") + ":", Font.Align.RIGHT, Assets.font14);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FilenameButton
/*     */     extends InputButton {
/*     */     FilenameButton() {
/* 125 */       String filename = SaveCompetition.this.game.competition.filename;
/* 126 */       if (filename.length() == 0) {
/* 127 */         filename = SaveCompetition.this.game.competition.getNewFilename();
/*     */       }
/* 129 */       Objects.requireNonNull(SaveCompetition.this.game.gui); setGeometry(1280 / 2 - 180 + 2, 550, 540, 36);
/* 130 */       setColors(Integer.valueOf(1534397), Integer.valueOf(3838184), Integer.valueOf(1066106));
/* 131 */       setText(filename, Font.Align.CENTER, Assets.font14);
/* 132 */       setEntryLimit(30);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 137 */       SaveCompetition.this.game.competition.saveAndSetFilename(this.text);
/* 138 */       SaveCompetition.this.game.setScreen((Screen)new Main(SaveCompetition.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SaveButton
/*     */     extends Button {
/*     */     SaveButton() {
/* 145 */       Objects.requireNonNull(SaveCompetition.this.game.gui); setGeometry((1280 - 180) / 2, 605, 180, 36);
/* 146 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 147 */       setText(Assets.strings.get("SAVE"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Up() {
/* 152 */       SaveCompetition.this.game.competition.saveAndSetFilename(SaveCompetition.this.fileNameButton.getText());
/* 153 */       SaveCompetition.this.game.setScreen((Screen)new Main(SaveCompetition.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 160 */       Objects.requireNonNull(SaveCompetition.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 161 */       setColors(Integer.valueOf(13107214), Integer.valueOf(16718121), Integer.valueOf(7603212));
/* 162 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 167 */       SaveCompetition.this.game.setScreen((Screen)new Main(SaveCompetition.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SaveCompetition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */