/*    */ package com.ygames.ysoccer.screens;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
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
/*    */ class DeveloperInfo
/*    */   extends GLScreen
/*    */ {
/*    */   DeveloperInfo(GLGame game) {
/* 18 */     super(game);
/* 19 */     this.background = new Texture("images/backgrounds/menu_game_options.jpg");
/*    */ 
/*    */ 
/*    */     
/* 23 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, "DEVELOPER INFO", 1646512);
/* 24 */     this.widgets.add(titleBar);
/*    */     
/* 26 */     int labelWidth = 230;
/* 27 */     Objects.requireNonNull(game.gui); int x0 = 1280 / 2 - 460;
/* 28 */     int y0 = 160;
/*    */     
/* 30 */     Button button = new Button();
/* 31 */     button.setColor(6298975);
/* 32 */     button.setGeometry(x0, y0, labelWidth, 22);
/* 33 */     button.setText("LOCAL STORAGE PATH", Font.Align.LEFT, Assets.font6);
/* 34 */     button.setActive(false);
/* 35 */     this.widgets.add(button);
/*    */     
/* 37 */     button = new Button();
/* 38 */     button.setColor(5066061);
/* 39 */     button.setGeometry(x0 + 10 + labelWidth, y0, 680, 22);
/* 40 */     button.setText(Gdx.files.getLocalStoragePath().toUpperCase(), Font.Align.LEFT, Assets.font6);
/* 41 */     button.setActive(false);
/* 42 */     this.widgets.add(button);
/*    */     
/* 44 */     y0 += 22;
/* 45 */     button = new Button();
/* 46 */     button.setColor(6298975);
/* 47 */     button.setGeometry(x0, y0, labelWidth, 22);
/* 48 */     button.setText("LOCAL STORAGE AVAILABLE", Font.Align.LEFT, Assets.font6);
/* 49 */     button.setActive(false);
/* 50 */     this.widgets.add(button);
/*    */     
/* 52 */     button = new Button();
/* 53 */     button.setColor(5066061);
/* 54 */     button.setGeometry(x0 + 10 + labelWidth, y0, 100, 22);
/* 55 */     button.setText(Gdx.files.isLocalStorageAvailable() ? "YES" : "NO", Font.Align.LEFT, Assets.font6);
/* 56 */     button.setActive(false);
/* 57 */     this.widgets.add(button);
/*    */     
/* 59 */     button = new ExitButton();
/* 60 */     this.widgets.add(button);
/* 61 */     setSelectedWidget((Widget)button);
/*    */   }
/*    */   
/*    */   private class ExitButton
/*    */     extends Button {
/*    */     ExitButton() {
/* 67 */       setColor(13124096);
/* 68 */       Objects.requireNonNull(DeveloperInfo.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 69 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 74 */       DeveloperInfo.this.game.settings.save();
/* 75 */       DeveloperInfo.this.game.setScreen((Screen)new DeveloperTools(DeveloperInfo.this.game));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DeveloperInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */