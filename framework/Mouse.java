/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.graphics.Camera;
/*    */ import com.badlogic.gdx.math.Vector3;
/*    */ 
/*    */ 
/*    */ class Mouse
/*    */ {
/* 10 */   final Vector3 position = new Vector3();
/*    */   boolean buttonLeft;
/*    */   boolean buttonRight;
/*    */   boolean enabled;
/*    */   
/*    */   Mouse() {
/* 16 */     enable();
/*    */   }
/*    */   
/*    */   void enable() {
/* 20 */     this.enabled = true;
/* 21 */     Gdx.graphics.setCursor(Assets.customCursor);
/*    */   }
/*    */   
/*    */   void disable() {
/* 25 */     this.enabled = false;
/* 26 */     Gdx.graphics.setCursor(Assets.hiddenCursor);
/*    */   }
/*    */   
/*    */   public void read(Camera camera) {
/* 30 */     this.position.x = Gdx.input.getX();
/* 31 */     this.position.y = Gdx.input.getY();
/* 32 */     this.position.z = 0.0F;
/* 33 */     camera.unproject(this.position);
/*    */     
/* 35 */     this.buttonLeft = Gdx.input.isButtonPressed(0);
/* 36 */     this.buttonRight = Gdx.input.isButtonPressed(1);
/*    */   }
/*    */   
/*    */   boolean isActioned() {
/* 40 */     return (Gdx.input.isButtonPressed(0) || Gdx.input
/* 41 */       .isButtonPressed(1) || Gdx.input
/* 42 */       .getDeltaX() != 0 || Gdx.input
/* 43 */       .getDeltaY() != 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\Mouse.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */