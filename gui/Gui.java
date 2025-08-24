/*    */ package com.ygames.ysoccer.gui;
/*    */ 
/*    */ public class Gui
/*    */ {
/*  5 */   public final int WIDTH = 1280;
/*  6 */   public final int HEIGHT = 720;
/*    */   public int screenWidth;
/*    */   public int screenHeight;
/*    */   public int originX;
/*    */   public int originY;
/*    */   
/*    */   public void resize(int width, int height) {
/* 13 */     float wZoom = width / 1280.0F;
/* 14 */     float hZoom = height / 720.0F;
/* 15 */     float zoom = Math.min(wZoom, hZoom);
/* 16 */     this.screenWidth = (int)(width / zoom);
/* 17 */     this.screenHeight = (int)(height / zoom);
/* 18 */     this.originX = (this.screenWidth - 1280) / 2;
/* 19 */     this.originY = (this.screenHeight - 720) / 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\Gui.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */