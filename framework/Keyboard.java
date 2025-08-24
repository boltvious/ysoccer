/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ 
/*    */ class Keyboard
/*    */   extends InputDevice {
/*    */   private KeyboardConfig config;
/*    */   
/*    */   Keyboard(int port, KeyboardConfig config) {
/* 10 */     super(InputDevice.Type.KEYBOARD, port);
/* 11 */     this.config = config;
/*    */   }
/*    */   
/*    */   protected void read() {
/* 15 */     this.x0 = (Gdx.input.isKeyPressed(this.config.keyLeft) ? -1 : 0) + (Gdx.input.isKeyPressed(this.config.keyRight) ? 1 : 0);
/* 16 */     this.y0 = (Gdx.input.isKeyPressed(this.config.keyUp) ? -1 : 0) + (Gdx.input.isKeyPressed(this.config.keyDown) ? 1 : 0);
/* 17 */     this.fire10 = Gdx.input.isKeyPressed(this.config.button1);
/* 18 */     this.fire20 = Gdx.input.isKeyPressed(this.config.button2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\Keyboard.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */