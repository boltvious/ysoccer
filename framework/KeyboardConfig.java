/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ public class KeyboardConfig
/*    */   extends InputDeviceConfig
/*    */ {
/*    */   public int keyLeft;
/*    */   public int keyRight;
/*    */   public int keyUp;
/*    */   public int keyDown;
/*    */   public int button1;
/*    */   public int button2;
/*    */   
/*    */   public KeyboardConfig() {
/* 14 */     super(InputDevice.Type.KEYBOARD);
/*    */   }
/*    */   
/*    */   KeyboardConfig(int keyLeft, int keyRight, int keyUp, int keyDown, int button1, int button2) {
/* 18 */     super(InputDevice.Type.KEYBOARD);
/* 19 */     this.keyLeft = keyLeft;
/* 20 */     this.keyRight = keyRight;
/* 21 */     this.keyUp = keyUp;
/* 22 */     this.keyDown = keyDown;
/* 23 */     this.button1 = button1;
/* 24 */     this.button2 = button2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\KeyboardConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */