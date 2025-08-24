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
/*    */ class SaveTacticsWarning
/*    */   extends GLScreen {
/*    */   SaveTacticsWarning(GLGame game) {
/* 16 */     super(game);
/*    */     
/* 18 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*    */ 
/*    */ 
/*    */     
/* 22 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("SAVE TACTICS"), 12227078);
/* 23 */     this.widgets.add(titleBar);
/*    */     
/* 25 */     WarningButton warningButton = new WarningButton();
/* 26 */     this.widgets.add(warningButton);
/*    */     
/* 28 */     SaveButton saveButton = new SaveButton();
/* 29 */     this.widgets.add(saveButton);
/*    */     
/* 31 */     setSelectedWidget((Widget)saveButton);
/*    */     
/* 33 */     ExitButton exitButton = new ExitButton();
/* 34 */     this.widgets.add(exitButton);
/*    */   }
/*    */   
/*    */   private class WarningButton
/*    */     extends Button {
/*    */     WarningButton() {
/* 40 */       Objects.requireNonNull(SaveTacticsWarning.this.game.gui); setGeometry((1280 - 620) / 2, 260, 620, 180);
/* 41 */       setColors(Integer.valueOf(14417920), Integer.valueOf(16728385), Integer.valueOf(9175040));
/* 42 */       setText(Assets.strings.get("TACTICS.TACTICS HAVE BEEN CHANGED"), Font.Align.CENTER, Assets.font14);
/* 43 */       setActive(false);
/*    */     }
/*    */   }
/*    */   
/*    */   private class SaveButton
/*    */     extends Button {
/*    */     SaveButton() {
/* 50 */       Objects.requireNonNull(SaveTacticsWarning.this.game.gui); setGeometry((1280 - 180) / 2, 590, 180, 36);
/* 51 */       setColors(Integer.valueOf(1089536), Integer.valueOf(1433600), Integer.valueOf(614400));
/* 52 */       setText(Assets.strings.get("SAVE"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Up() {
/* 57 */       SaveTacticsWarning.this.game.setScreen((Screen)new SaveTactics(SaveTacticsWarning.this.game));
/*    */     }
/*    */   }
/*    */   
/*    */   private class ExitButton
/*    */     extends Button {
/*    */     public ExitButton() {
/* 64 */       Objects.requireNonNull(SaveTacticsWarning.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 65 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 66 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 72 */       Assets.tactics[SaveTacticsWarning.this.game.tacticsToEdit].copyFrom(SaveTacticsWarning.this.game.editedTactics);
/*    */       
/* 74 */       if (SaveTacticsWarning.this.game.getState() == GLGame.State.NONE) {
/* 75 */         SaveTacticsWarning.this.game.setScreen((Screen)new Main(SaveTacticsWarning.this.game));
/*    */       } else {
/* 77 */         SaveTacticsWarning.this.game.setScreen((Screen)new SetTeam(SaveTacticsWarning.this.game));
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SaveTacticsWarning.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */