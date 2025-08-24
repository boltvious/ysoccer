/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ public class JoystickConfig
/*    */   extends InputDeviceConfig {
/*    */   public String name;
/*  6 */   public int xAxis = -1;
/*  7 */   public int yAxis = -1;
/*  8 */   public int button1 = -1;
/*  9 */   public int button2 = -1;
/*    */   
/*    */   public JoystickConfig() {
/* 12 */     super(InputDevice.Type.JOYSTICK);
/*    */   }
/*    */   
/*    */   public JoystickConfig(String name) {
/* 16 */     super(InputDevice.Type.JOYSTICK);
/* 17 */     this.name = name;
/*    */   }
/*    */   
/*    */   public boolean isConfigured() {
/* 21 */     return (this.xAxis != -1 && this.yAxis != -1 && this.button1 != -1 && this.button2 != -1);
/*    */   }
/*    */   
/*    */   public void reset() {
/* 25 */     this.xAxis = -1;
/* 26 */     this.yAxis = -1;
/* 27 */     this.button1 = -1;
/* 28 */     this.button2 = -1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\JoystickConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */