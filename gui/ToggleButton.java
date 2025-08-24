/*    */ package com.ygames.ysoccer.gui;
/*    */ 
/*    */ public abstract class ToggleButton
/*    */   extends Button
/*    */ {
/*    */   public void onFire1Down() {
/*  7 */     toggle();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onFire2Down() {
/* 12 */     toggle();
/*    */   }
/*    */   
/*    */   protected abstract void toggle();
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\ToggleButton.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */