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
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Tactics;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ class LoadTactics
/*     */   extends GLScreen
/*     */ {
/*     */   LoadTactics(GLGame game) {
/*  27 */     super(game);
/*     */     
/*  29 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*     */ 
/*     */ 
/*     */     
/*  33 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("LOAD EDITED TACTICS"), 12227078);
/*  34 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  37 */     List<FileHandle> files = Arrays.asList(Assets.tacticsFolder.list(".TAC"));
/*  38 */     Collections.sort(files, Assets.fileComparatorByName);
/*     */     
/*  40 */     ArrayList<Widget> list = new ArrayList<>();
/*  41 */     for (FileHandle file : files) {
/*  42 */       TacticsButton tacticsButton = new TacticsButton(file.nameWithoutExtension());
/*  43 */       list.add(tacticsButton);
/*  44 */       this.widgets.add(tacticsButton);
/*     */     } 
/*     */     
/*  47 */     Objects.requireNonNull(game.gui); Widget.arrange(1280, 280, 42, 20, list);
/*     */     
/*  49 */     if (list.size() > 0) {
/*  50 */       setSelectedWidget(list.get(0));
/*     */     }
/*     */     
/*  53 */     for (Widget widget : list) {
/*  54 */       widget.w = 160;
/*  55 */       Button button = new Button();
/*  56 */       button.setSize(80, 36);
/*  57 */       button.setPosition(widget.x + 165, widget.y);
/*  58 */       button.setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/*  59 */       button.setText("TACT", Font.Align.CENTER, Assets.font14);
/*  60 */       button.setActive(false);
/*  61 */       this.widgets.add(button);
/*     */     } 
/*     */     
/*  64 */     AbortButton abortButton = new AbortButton();
/*  65 */     this.widgets.add(abortButton);
/*     */     
/*  67 */     if (getSelectedWidget() == null)
/*  68 */       setSelectedWidget((Widget)abortButton); 
/*     */   }
/*     */   
/*     */   private class TacticsButton
/*     */     extends Button
/*     */   {
/*     */     String name;
/*     */     
/*     */     TacticsButton(String name) {
/*  77 */       this.name = name;
/*  78 */       setSize(245, 36);
/*  79 */       setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/*  80 */       setText(name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/*  85 */       Tactics tactics = new Tactics();
/*  86 */       tactics.copyFrom(LoadTactics.this.game.editedTactics);
/*  87 */       LoadTactics.this.game.tacticsUndo.push(tactics);
/*     */       
/*  89 */       InputStream in = Assets.tacticsFolder.child(this.name + ".TAC").read();
/*     */       try {
/*  91 */         LoadTactics.this.game.editedTactics.loadFile(in);
/*  92 */       } catch (IOException e) {
/*  93 */         e.printStackTrace();
/*     */       } 
/*  95 */       Tactics.codes[LoadTactics.this.game.tacticsToEdit] = this.name;
/*     */       
/*  97 */       LoadTactics.this.game.setScreen((Screen)new EditTactics(LoadTactics.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 104 */       Objects.requireNonNull(LoadTactics.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 105 */       setColors(Integer.valueOf(14417920), Integer.valueOf(16728385), Integer.valueOf(9175040));
/* 106 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/* 111 */       LoadTactics.this.game.setScreen((Screen)new EditTactics(LoadTactics.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\LoadTactics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */