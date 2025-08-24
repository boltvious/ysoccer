/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class InputDeviceList
/*    */   extends ArrayList<InputDevice>
/*    */ {
/*    */   public void setAvailability(boolean n) {
/* 10 */     for (InputDevice inputDevice : this) {
/* 11 */       inputDevice.available = n;
/*    */     }
/*    */   }
/*    */   
/*    */   public int getAvailabilityCount() {
/* 16 */     int n = 0;
/* 17 */     for (InputDevice inputDevice : this) {
/* 18 */       if (inputDevice.available) n++; 
/*    */     } 
/* 20 */     return n;
/*    */   }
/*    */   
/*    */   public InputDevice assignFirstAvailable() {
/* 24 */     for (InputDevice inputDevice : this) {
/* 25 */       if (inputDevice.available) {
/* 26 */         inputDevice.available = false;
/* 27 */         return inputDevice;
/*    */       } 
/*    */     } 
/* 30 */     return null;
/*    */   }
/*    */   
/*    */   public InputDevice assignNextAvailable(InputDevice current) {
/* 34 */     int start = indexOf(current);
/* 35 */     if (start == -1) {
/* 36 */       throw new GdxRuntimeException("item not found");
/*    */     }
/*    */     
/* 39 */     int len = size();
/* 40 */     for (int i = start + 1; i < len; i++) {
/* 41 */       InputDevice next = get(i);
/* 42 */       if (next.available) {
/* 43 */         current.setAvailable(true);
/* 44 */         next.setAvailable(false);
/* 45 */         return next;
/*    */       } 
/*    */     } 
/* 48 */     return null;
/*    */   }
/*    */   
/*    */   public InputDevice rotateAvailable(InputDevice inputDevice, int n) {
/* 52 */     int index = indexOf(inputDevice);
/* 53 */     if (index == -1) {
/* 54 */       throw new GdxRuntimeException("item not found");
/*    */     }
/* 56 */     inputDevice.available = true;
/*    */     do {
/* 58 */       index = EMath.rotate(index, 0, size() - 1, n);
/* 59 */       inputDevice = get(index);
/* 60 */     } while (!inputDevice.available);
/*    */     
/* 62 */     inputDevice.available = false;
/* 63 */     return inputDevice;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\InputDeviceList.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */