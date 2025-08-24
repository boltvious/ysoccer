/*    */ package com.ygames.ysoccer.screens;
/*    */ 
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.badlogic.gdx.graphics.Texture;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.Font;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.framework.GLScreen;
/*    */ import com.ygames.ysoccer.gui.Button;
/*    */ import com.ygames.ysoccer.gui.Widget;
/*    */ import com.ygames.ysoccer.match.Tactics;
/*    */ import java.util.Objects;
/*    */ import java.util.Stack;
/*    */ 
/*    */ class SelectTactics extends GLScreen {
/*    */   SelectTactics(GLGame game) {
/* 17 */     super(game);
/*    */     
/* 19 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*    */ 
/*    */ 
/*    */     
/* 23 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("EDIT TACTICS"), 12227078);
/* 24 */     this.widgets.add(titleBar);
/*    */     
/* 26 */     for (int t = 12; t < 18; t++) {
/* 27 */       TacticsButton tacticsButton = new TacticsButton(t);
/* 28 */       this.widgets.add(tacticsButton);
/* 29 */       if (getSelectedWidget() == null) {
/* 30 */         setSelectedWidget((Widget)tacticsButton);
/*    */       }
/*    */     } 
/*    */     
/* 34 */     ExitButton exitButton = new ExitButton();
/* 35 */     this.widgets.add(exitButton);
/*    */   }
/*    */   
/*    */   private class TacticsButton
/*    */     extends Button {
/*    */     private int tacticsIndex;
/*    */     
/*    */     TacticsButton(int tacticsIndex) {
/* 43 */       this.tacticsIndex = tacticsIndex;
/* 44 */       Objects.requireNonNull(SelectTactics.this.game.gui); setGeometry((1280 - 340) / 2, 150 + 75 * (tacticsIndex - 12), 340, 44);
/* 45 */       setColor(5669376);
/* 46 */       setText(Tactics.codes[tacticsIndex], Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 51 */       SelectTactics.this.game.tacticsToEdit = this.tacticsIndex;
/* 52 */       SelectTactics.this.game.editedTactics = new Tactics();
/* 53 */       SelectTactics.this.game.editedTactics.copyFrom(Assets.tactics[SelectTactics.this.game.tacticsToEdit]);
/* 54 */       SelectTactics.this.game.tacticsUndo = new Stack();
/*    */       
/* 56 */       SelectTactics.this.game.setScreen((Screen)new EditTactics(SelectTactics.this.game));
/*    */     }
/*    */   }
/*    */   
/*    */   private class ExitButton
/*    */     extends Button {
/*    */     ExitButton() {
/* 63 */       setColor(13124096);
/* 64 */       Objects.requireNonNull(SelectTactics.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 65 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 70 */       SelectTactics.this.game.setScreen((Screen)new Main(SelectTactics.this.game));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SelectTactics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */