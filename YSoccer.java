/*    */ package com.ygames.ysoccer;
/*    */ 
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.screens.Intro;
/*    */ import com.ygames.ysoccer.screens.Main;
/*    */ 
/*    */ public class YSoccer
/*    */   extends GLGame {
/*    */   public void create() {
/* 11 */     super.create();
/*    */     
/* 13 */     if (this.settings.showIntro) {
/* 14 */       setScreen((Screen)new Intro(this));
/*    */     } else {
/* 16 */       this.menuMusic.setMode(this.settings.musicMode);
/* 17 */       setScreen((Screen)new Main(this));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\YSoccer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */