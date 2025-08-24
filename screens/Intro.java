/*    */ package com.ygames.ysoccer.screens;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.InputAdapter;
/*    */ import com.badlogic.gdx.InputProcessor;
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.badlogic.gdx.graphics.g2d.BitmapFont;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.framework.GLScreen;
/*    */ import com.ygames.ysoccer.gui.TextBox;
/*    */ import com.ygames.ysoccer.gui.Widget;
/*    */ import java.util.Arrays;
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class Intro extends GLScreen {
/*    */   public Intro(GLGame game) {
/* 17 */     super(game);
/*    */     
/* 19 */     this.usesMouse = false;
/*    */     
/* 21 */     game.disableMouse();
/* 22 */     Gdx.input.setInputProcessor((InputProcessor)new IntroInputProcessor());
/*    */ 
/*    */ 
/*    */     
/* 26 */     BitmapFont font = new BitmapFont(true);
/* 27 */     String[] lines = { "YSoccer 19, Copyright (C) 2019", "by Massimo Modica, Daniele Giannarini, Marco Modica", "", "YSoccer comes with ABSOLUTELY NO WARRANTY; for details press 'W'.", "This is free software, and you are welcome to redistribute it", "under certain conditions; press 'C' for details.", "", "", "Press any key or button to continue" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 38 */     Objects.requireNonNull(game.gui); TextBox textBox = new TextBox(font, Arrays.asList(lines), 1280 / 2, 270);
/* 39 */     this.widgets.add(textBox);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(float delta) {
/* 44 */     super.render(delta);
/*    */     
/* 46 */     if (this.widgetEvent == Widget.Event.FIRE1_UP || this.widgetEvent == Widget.Event.FIRE2_UP)
/* 47 */       setMainMenu(); 
/*    */   }
/*    */   
/*    */   private class IntroInputProcessor
/*    */     extends InputAdapter {
/*    */     private IntroInputProcessor() {}
/*    */     
/*    */     public boolean touchUp(int screenX, int screenY, int pointer, int button) {
/* 55 */       Intro.this.setMainMenu();
/* 56 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean keyUp(int keycode) {
/* 61 */       switch (keycode)
/*    */       { case 51:
/* 63 */           Intro.this.game.setScreen((Screen)new Warranty(Intro.this.game));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 74 */           return true;case 31: Intro.this.game.setScreen((Screen)new Copying(Intro.this.game)); return true; }  Intro.this.setMainMenu(); return true;
/*    */     }
/*    */   }
/*    */   
/*    */   private void setMainMenu() {
/* 79 */     Gdx.input.setInputProcessor(null);
/* 80 */     this.game.enableMouse();
/* 81 */     this.game.menuMusic.setMode(this.game.settings.musicMode);
/* 82 */     this.game.setScreen((Screen)new Main(this.game));
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\Intro.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */