/*    */ package com.ygames.ysoccer.framework;public abstract class InputDevice { public final Type type; public final int port; public boolean available; public int x0; public int y0; public boolean fire10; public boolean fire20; public int x1; public int y1;
/*    */   public boolean fire11;
/*    */   public boolean fire21;
/*    */   public boolean value;
/*    */   public int angle;
/*    */   
/*  7 */   public enum Type { COMPUTER, KEYBOARD, JOYSTICK; }
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   InputDevice(Type type, int port) {
/* 24 */     this.type = type;
/* 25 */     this.port = port;
/*    */   }
/*    */   
/*    */   public void setAvailable(boolean available) {
/* 29 */     this.available = available;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void update() {
/* 35 */     int x2 = this.x1;
/* 36 */     this.x1 = this.x0;
/* 37 */     int y2 = this.y1;
/* 38 */     this.y1 = this.y0;
/* 39 */     this.fire11 = this.fire10;
/* 40 */     this.fire21 = this.fire20;
/*    */ 
/*    */     
/* 43 */     read();
/*    */ 
/*    */     
/* 46 */     if (this.x1 != this.x0 && this.x1 != x2) {
/* 47 */       this.x1 = x2;
/*    */     }
/* 49 */     if (this.y1 != this.y0 && this.y1 != y2) {
/* 50 */       this.y1 = y2;
/*    */     }
/*    */     
/* 53 */     this.value = (this.x1 != 0 || this.y1 != 0);
/* 54 */     this.angle = Math.round(EMath.aTan2(this.y1, this.x1));
/*    */   }
/*    */   
/*    */   abstract void read();
/*    */   
/*    */   public boolean fire1Down() {
/* 60 */     return (this.fire10 && !this.fire11);
/*    */   }
/*    */   
/*    */   public boolean fire1Up() {
/* 64 */     return (!this.fire10 && this.fire11);
/*    */   }
/*    */   
/*    */   public boolean fire2Down() {
/* 68 */     return (this.fire20 && !this.fire21);
/*    */   }
/*    */   
/*    */   public boolean xReleased() {
/* 72 */     return (this.x1 == 0 && this.x0 != 0);
/*    */   }
/*    */   
/*    */   public boolean yMoved() {
/* 76 */     return (this.y1 != 0 && this.y0 == 0);
/*    */   }
/*    */   
/*    */   public static String keyDescription(int keyCode) {
/* 80 */     return Input.Keys.toString(keyCode).toUpperCase();
/*    */   } }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\InputDevice.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */