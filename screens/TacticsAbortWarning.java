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
/*    */ import java.util.Objects;
/*    */ 
/*    */ class TacticsAbortWarning
/*    */   extends GLScreen {
/*    */   TacticsAbortWarning(GLGame game) {
/* 16 */     super(game);
/*    */     
/* 18 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*    */ 
/*    */ 
/*    */     
/* 22 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("EDIT TACTICS"), 12227078);
/* 23 */     this.widgets.add(titleBar);
/*    */     
/* 25 */     WarningButton warningButton = new WarningButton();
/* 26 */     this.widgets.add(warningButton);
/*    */     
/* 28 */     ContinueButton continueButton = new ContinueButton();
/* 29 */     this.widgets.add(continueButton);
/*    */     
/* 31 */     setSelectedWidget((Widget)continueButton);
/*    */     
/* 33 */     AbortButton abortButton = new AbortButton();
/* 34 */     this.widgets.add(abortButton);
/*    */   }
/*    */   
/*    */   private class WarningButton
/*    */     extends Button {
/*    */     WarningButton() {
/* 40 */       setGeometry(330, 260, 620, 180);
/* 41 */       setColors(Integer.valueOf(14417920), Integer.valueOf(16728385), Integer.valueOf(9175040));
/* 42 */       setText(Assets.strings.get("TACTICS.EDITED TACTICS WILL BE LOST"), Font.Align.CENTER, Assets.font14);
/* 43 */       setActive(false);
/*    */     }
/*    */   }
/*    */   
/*    */   private class ContinueButton
/*    */     extends Button {
/*    */     ContinueButton() {
/* 50 */       Objects.requireNonNull(TacticsAbortWarning.this.game.gui); setGeometry((1280 - 180) / 2, 590, 180, 36);
/* 51 */       setColors(Integer.valueOf(1089536), Integer.valueOf(1433600), Integer.valueOf(614400));
/* 52 */       setText(Assets.strings.get("CONTINUE"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     protected void onFire1Down() {
/* 57 */       if (TacticsAbortWarning.this.game.getState() == GLGame.State.NONE) {
/* 58 */         TacticsAbortWarning.this.game.setScreen((Screen)new Main(TacticsAbortWarning.this.game));
/*    */       } else {
/* 60 */         TacticsAbortWarning.this.game.setScreen((Screen)new SetTeam(TacticsAbortWarning.this.game));
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   private class AbortButton
/*    */     extends Button {
/*    */     public AbortButton() {
/* 68 */       Objects.requireNonNull(TacticsAbortWarning.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 69 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 70 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     protected void onFire1Down() {
/* 75 */       TacticsAbortWarning.this.game.setScreen((Screen)new EditTactics(TacticsAbortWarning.this.game));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\TacticsAbortWarning.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */