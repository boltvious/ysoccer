/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.badlogic.gdx.controllers.Controller;
/*    */ 
/*    */ 
/*    */ class Joystick
/*    */   extends InputDevice
/*    */ {
/*    */   private Controller controller;
/*    */   private JoystickConfig config;
/*    */   
/*    */   Joystick(Controller controller, JoystickConfig config, int port) {
/* 13 */     super(InputDevice.Type.JOYSTICK, port);
/* 14 */     this.controller = controller;
/* 15 */     this.config = config;
/*    */   }
/*    */   
/*    */   protected void read() {
/* 19 */     this.x0 = Math.round(this.controller.getAxis(this.config.xAxis));
/* 20 */     this.y0 = Math.round(this.controller.getAxis(this.config.yAxis));
/* 21 */     this.fire10 = this.controller.getButton(this.config.button1);
/* 22 */     this.fire20 = this.controller.getButton(this.config.button2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\Joystick.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */