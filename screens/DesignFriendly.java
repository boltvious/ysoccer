/*     */ package com.ygames.ysoccer.screens;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.Friendly;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class DesignFriendly extends GLScreen {
/*     */   private Friendly friendly;
/*     */   
/*     */   DesignFriendly(GLGame game) {
/*  18 */     super(game);
/*     */     
/*  20 */     this.friendly = new Friendly();
/*     */     
/*  22 */     game.setState(GLGame.State.FRIENDLY, null);
/*     */     
/*  24 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  28 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("FRIENDLY"), 2983261);
/*  29 */     this.widgets.add(titleBar);
/*     */     
/*  31 */     SubstitutesLabel substitutesLabel = new SubstitutesLabel();
/*  32 */     this.widgets.add(substitutesLabel);
/*     */     
/*  34 */     SubstitutesButton substitutesButton = new SubstitutesButton();
/*  35 */     this.widgets.add(substitutesButton);
/*  36 */     this.substitutesButton = (Widget)substitutesButton;
/*     */     
/*  38 */     BenchSizeLabel benchSizeLabel = new BenchSizeLabel();
/*  39 */     this.widgets.add(benchSizeLabel);
/*     */     
/*  41 */     BenchSizeButton benchSizeButton = new BenchSizeButton();
/*  42 */     this.widgets.add(benchSizeButton);
/*     */     
/*  44 */     OkButton okButton = new OkButton();
/*  45 */     this.widgets.add(okButton);
/*  46 */     setSelectedWidget((Widget)okButton);
/*     */     
/*  48 */     ExitButton exitButton = new ExitButton();
/*  49 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private Widget substitutesButton;
/*     */   
/*     */   private class SubstitutesLabel extends Button { SubstitutesLabel() {
/*  55 */       Objects.requireNonNull(DesignFriendly.this.game.gui); setGeometry(1280 / 2 - 350, 280, 440, 40);
/*  56 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/*  57 */       setText(Assets.strings.get("SUBSTITUTES"), Font.Align.CENTER, Assets.font14);
/*  58 */       setActive(false);
/*     */     } }
/*     */ 
/*     */   
/*     */   private class SubstitutesButton
/*     */     extends Button {
/*     */     SubstitutesButton() {
/*  65 */       Objects.requireNonNull(DesignFriendly.this.game.gui); setGeometry(1280 / 2 + 110, 280, 240, 40);
/*  66 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/*  67 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/*  72 */       setText(DesignFriendly.this.friendly.substitutions);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  77 */       updateSubstitutes(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/*  82 */       updateSubstitutes(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/*  87 */       updateSubstitutes(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/*  92 */       updateSubstitutes(-1);
/*     */     }
/*     */     
/*     */     private void updateSubstitutes(int n) {
/*  96 */       DesignFriendly.this.friendly.substitutions = EMath.slide(DesignFriendly.this.friendly.substitutions, 2, DesignFriendly.this.friendly.benchSize, n);
/*  97 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeLabel
/*     */     extends Button {
/*     */     BenchSizeLabel() {
/* 104 */       Objects.requireNonNull(DesignFriendly.this.game.gui); setGeometry(1280 / 2 - 350, 335, 440, 40);
/* 105 */       setColors(Integer.valueOf(8388608), Integer.valueOf(11796480), Integer.valueOf(4194304));
/* 106 */       setText(Assets.strings.get("BENCH SIZE"), Font.Align.CENTER, Assets.font14);
/* 107 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class BenchSizeButton
/*     */     extends Button {
/*     */     BenchSizeButton() {
/* 114 */       Objects.requireNonNull(DesignFriendly.this.game.gui); setGeometry(1280 / 2 + 110, 335, 240, 40);
/* 115 */       setColors(Integer.valueOf(2039701), Integer.valueOf(3158228), Integer.valueOf(1381731));
/* 116 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 121 */       setText(DesignFriendly.this.friendly.benchSize);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 126 */       updateBenchSize(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 131 */       updateBenchSize(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 136 */       updateBenchSize(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 141 */       updateBenchSize(-1);
/*     */     }
/*     */     
/*     */     private void updateBenchSize(int n) {
/* 145 */       DesignFriendly.this.friendly.benchSize = EMath.slide(DesignFriendly.this.friendly.benchSize, 2, 12, n);
/* 146 */       DesignFriendly.this.friendly.substitutions = Math.min(DesignFriendly.this.friendly.substitutions, DesignFriendly.this.friendly.benchSize);
/* 147 */       setDirty(true);
/* 148 */       DesignFriendly.this.substitutesButton.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class OkButton
/*     */     extends Button {
/*     */     OkButton() {
/* 155 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 156 */       Objects.requireNonNull(DesignFriendly.this.game.gui); setGeometry((1280 - 180) / 2, 605, 180, 36);
/* 157 */       setText(Assets.strings.get("OK"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 162 */       DesignFriendly.navigation.folder = Assets.teamsRootFolder;
/* 163 */       DesignFriendly.navigation.competition = (Competition)DesignFriendly.this.friendly;
/* 164 */       DesignFriendly.this.game.setScreen((Screen)new SelectFolder(DesignFriendly.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 171 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 172 */       Objects.requireNonNull(DesignFriendly.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 173 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 178 */       DesignFriendly.this.game.setScreen((Screen)new Main(DesignFriendly.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DesignFriendly.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */