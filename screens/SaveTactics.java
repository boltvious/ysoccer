/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.InputButton;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Tactics;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ class SaveTactics
/*     */   extends GLScreen
/*     */ {
/*     */   private String filename;
/*     */   
/*     */   SaveTactics(GLGame game) {
/*  28 */     super(game);
/*     */     
/*  30 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*     */     
/*  32 */     this.filename = Tactics.codes[game.tacticsToEdit];
/*     */ 
/*     */ 
/*     */     
/*  36 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("SAVE TACTICS"), 12227078);
/*  37 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  40 */     List<FileHandle> files = Arrays.asList(Assets.tacticsFolder.list(".TAC"));
/*  41 */     Collections.sort(files, Assets.fileComparatorByName);
/*     */     
/*  43 */     List<Widget> list = new ArrayList<>();
/*  44 */     for (FileHandle file : files) {
/*  45 */       TacticsButton tacticsButton = new TacticsButton(file.nameWithoutExtension());
/*  46 */       list.add(tacticsButton);
/*  47 */       this.widgets.add(tacticsButton);
/*     */     } 
/*     */     
/*  50 */     Objects.requireNonNull(game.gui); Widget.arrange(1280, 280, 42, 20, list);
/*     */     
/*  52 */     for (Widget widget : list) {
/*  53 */       widget.w = 160;
/*  54 */       Button button = new Button();
/*  55 */       button.setSize(80, 36);
/*  56 */       button.setPosition(widget.x + 165, widget.y);
/*  57 */       button.setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/*  58 */       button.setText("TACT", Font.Align.CENTER, Assets.font14);
/*  59 */       button.setActive(false);
/*  60 */       this.widgets.add(button);
/*     */     } 
/*     */     
/*  63 */     FilenameLabel filenameLabel = new FilenameLabel();
/*  64 */     this.widgets.add(filenameLabel);
/*     */     
/*  66 */     FilenameButton filenameButton = new FilenameButton();
/*  67 */     this.widgets.add(filenameButton);
/*     */     
/*  69 */     SaveButton saveButton = new SaveButton();
/*  70 */     this.widgets.add(saveButton);
/*     */     
/*  72 */     setSelectedWidget((Widget)saveButton);
/*     */     
/*  74 */     AbortButton abortButton = new AbortButton();
/*  75 */     this.widgets.add(abortButton);
/*     */   }
/*     */   
/*     */   private class TacticsButton
/*     */     extends Button {
/*     */     String name;
/*     */     
/*     */     TacticsButton(String name) {
/*  83 */       this.name = name;
/*  84 */       setSize(245, 36);
/*  85 */       setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/*  86 */       setText(name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/*  91 */       SaveTactics.this.save(this.name);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FilenameLabel
/*     */     extends Label {
/*     */     FilenameLabel() {
/*  98 */       Objects.requireNonNull(SaveTactics.this.game.gui); setGeometry(1280 / 2 - 180, 500, 180, 36);
/*  99 */       setText(Assets.gettext("FILENAME") + ":", Font.Align.RIGHT, Assets.font14);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FilenameButton
/*     */     extends InputButton {
/*     */     FilenameButton() {
/* 106 */       Objects.requireNonNull(SaveTactics.this.game.gui); setGeometry(1280 / 2, 500, 160, 36);
/* 107 */       setColors(Integer.valueOf(1534397), Integer.valueOf(3838184), Integer.valueOf(1066106));
/* 108 */       setText(SaveTactics.this.filename, Font.Align.CENTER, Assets.font14);
/* 109 */       setEntryLimit(8);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 114 */       SaveTactics.this.filename = this.text;
/*     */     }
/*     */   }
/*     */   
/*     */   private class SaveButton
/*     */     extends Button {
/*     */     SaveButton() {
/* 121 */       Objects.requireNonNull(SaveTactics.this.game.gui); setGeometry((1280 - 180) / 2, 590, 180, 36);
/* 122 */       setColors(Integer.valueOf(1089536), Integer.valueOf(1433600), Integer.valueOf(614400));
/* 123 */       setText(Assets.gettext("SAVE"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Up() {
/* 128 */       SaveTactics.this.save((String)null);
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 135 */       Objects.requireNonNull(SaveTactics.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 136 */       setColors(Integer.valueOf(14417920), Integer.valueOf(16728385), Integer.valueOf(9175040));
/* 137 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Up() {
/* 142 */       SaveTactics.this.game.setScreen((Screen)new EditTactics(SaveTactics.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private void save(String name) {
/* 147 */     if (name == null) {
/* 148 */       name = this.filename;
/*     */     }
/*     */ 
/*     */     
/* 152 */     this.game.editedTactics.name = name;
/*     */ 
/*     */     
/* 155 */     this.game.editedTactics.saveFile(name + ".TAC");
/*     */ 
/*     */     
/* 158 */     Assets.tactics[this.game.tacticsToEdit].copyFrom(this.game.editedTactics);
/* 159 */     Tactics.codes[this.game.tacticsToEdit] = this.game.editedTactics.name;
/*     */     
/* 161 */     if (this.game.getState() == GLGame.State.NONE) {
/* 162 */       this.game.setScreen((Screen)new Main(this.game));
/*     */     } else {
/* 164 */       this.game.setScreen((Screen)new SetTeam(this.game));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SaveTactics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */