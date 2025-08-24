/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Screen;
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
/*     */ 
/*     */ class ImportTactics
/*     */   extends GLScreen {
/*     */   public ImportTactics(GLGame game) {
/*  19 */     super(game);
/*     */     
/*  21 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*     */ 
/*     */ 
/*     */     
/*  25 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("IMPORT TACTICS"), 12227078);
/*  26 */     this.widgets.add(titleBar);
/*     */     
/*  28 */     for (int i = 0; i < 18; i++) {
/*  29 */       TacticsButton tacticsButton = new TacticsButton(i);
/*  30 */       this.widgets.add(tacticsButton);
/*  31 */       if (i == 1) {
/*  32 */         setSelectedWidget((Widget)tacticsButton);
/*     */       }
/*     */     } 
/*     */     
/*  36 */     LoadTacticsButton loadTacticsButton = new LoadTacticsButton();
/*  37 */     this.widgets.add(loadTacticsButton);
/*     */     
/*  39 */     ExitButton exitButton = new ExitButton();
/*  40 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private class TacticsButton
/*     */     extends Button
/*     */   {
/*     */     int tacticsIndex;
/*     */     
/*     */     TacticsButton(int tacticsIndex) {
/*  49 */       this.tacticsIndex = tacticsIndex;
/*  50 */       if (tacticsIndex < 12) {
/*  51 */         int dx = -186 + tacticsIndex % 3 * 126;
/*  52 */         int dy = tacticsIndex / 3 * 42;
/*  53 */         setGeometry(640 + dx, 220 + dy, 120, 36);
/*     */       } else {
/*  55 */         int dx = -186 + (tacticsIndex - 12) % 2 * 189;
/*  56 */         int dy = (tacticsIndex - 12) / 2 * 42;
/*  57 */         setGeometry(640 + dx, 388 + dy, 183, 36);
/*     */       } 
/*  59 */       setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/*  60 */       setText(Tactics.codes[tacticsIndex], Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/*  65 */       Tactics tactics = new Tactics();
/*  66 */       tactics.copyFrom(ImportTactics.this.game.editedTactics);
/*  67 */       ImportTactics.this.game.tacticsUndo.push(tactics);
/*  68 */       InputStream in = Gdx.files.internal("data/tactics/preset/" + Tactics.fileNames[this.tacticsIndex] + ".TAC").read();
/*     */       try {
/*  70 */         ImportTactics.this.game.editedTactics.loadFile(in);
/*  71 */       } catch (IOException e) {
/*  72 */         e.printStackTrace();
/*     */       } 
/*     */       
/*  75 */       ImportTactics.this.game.setScreen((Screen)new EditTactics(ImportTactics.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class LoadTacticsButton
/*     */     extends Button {
/*     */     LoadTacticsButton() {
/*  82 */       setGeometry(454, 514, 372, 36);
/*  83 */       setColors(Integer.valueOf(11211917), Integer.valueOf(14555831), Integer.valueOf(7868003));
/*  84 */       setText(Assets.strings.get("TACTICS.LOAD"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/*  89 */       ImportTactics.this.game.setScreen((Screen)new LoadTactics(ImportTactics.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     public ExitButton() {
/*  96 */       setGeometry(550, 660, 180, 36);
/*  97 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/*  98 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/* 103 */       ImportTactics.this.game.setScreen((Screen)new EditTactics(ImportTactics.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\ImportTactics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */