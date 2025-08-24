/*    */ package com.ygames.ysoccer.screens;
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.badlogic.gdx.graphics.Texture;
/*    */ import com.ygames.ysoccer.competitions.Competition;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.Font;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.framework.GLScreen;
/*    */ import com.ygames.ysoccer.gui.Button;
/*    */ import com.ygames.ysoccer.gui.Label;
/*    */ import com.ygames.ysoccer.gui.Widget;
/*    */ import java.util.Objects;
/*    */ 
/*    */ class LoadCompetitionWarning extends GLScreen {
/*    */   LoadCompetitionWarning(GLGame game) {
/* 16 */     super(game);
/*    */     
/* 18 */     this.background = new Texture("images/backgrounds/menu_competition.jpg");
/*    */ 
/*    */ 
/*    */     
/* 22 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("LOAD OLD COMPETITION"), 2660551);
/* 23 */     this.widgets.add(titleBar);
/*    */ 
/*    */     
/* 26 */     Button button = new Button();
/* 27 */     Objects.requireNonNull(game.gui); button.setGeometry((1280 - 580) / 2, 270, 580, 180);
/* 28 */     button.setColors(Integer.valueOf(14417920), Integer.valueOf(16728385), Integer.valueOf(9175040));
/* 29 */     button.setActive(false);
/* 30 */     this.widgets.add(button);
/*    */     
/* 32 */     String msg = Assets.strings.get(Competition.getWarningLabel(game.competition.category));
/* 33 */     int cut = msg.indexOf(" ", msg.length() / 2);
/*    */     
/* 35 */     Label label = new Label();
/* 36 */     label.setText(msg.substring(0, cut), Font.Align.CENTER, Assets.font14);
/* 37 */     Objects.requireNonNull(game.gui); label.setPosition(1280 / 2, 340);
/* 38 */     this.widgets.add(label);
/*    */     
/* 40 */     label = new Label();
/* 41 */     label.setText(msg.substring(cut + 1), Font.Align.CENTER, Assets.font14);
/* 42 */     Objects.requireNonNull(game.gui); label.setPosition(1280 / 2, 380);
/* 43 */     this.widgets.add(label);
/*    */     
/* 45 */     ContinueButton continueButton = new ContinueButton();
/* 46 */     this.widgets.add(continueButton);
/*    */     
/* 48 */     AbortButton abortButton = new AbortButton();
/* 49 */     this.widgets.add(abortButton);
/*    */     
/* 51 */     setSelectedWidget((Widget)abortButton);
/*    */   }
/*    */   
/*    */   private class ContinueButton
/*    */     extends Button {
/*    */     ContinueButton() {
/* 57 */       Objects.requireNonNull(LoadCompetitionWarning.this.game.gui); setGeometry((1280 - 180) / 2, 590, 180, 36);
/* 58 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/* 59 */       setText(Assets.strings.get("CONTINUE"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 64 */       LoadCompetitionWarning.this.game.clearCompetition();
/* 65 */       LoadCompetitionWarning.this.game.setScreen((Screen)new LoadCompetition(LoadCompetitionWarning.this.game));
/*    */     }
/*    */   }
/*    */   
/*    */   private class AbortButton
/*    */     extends Button {
/*    */     AbortButton() {
/* 72 */       Objects.requireNonNull(LoadCompetitionWarning.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 73 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 74 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 79 */       LoadCompetitionWarning.this.game.setScreen((Screen)new Main(LoadCompetitionWarning.this.game));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\LoadCompetitionWarning.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */