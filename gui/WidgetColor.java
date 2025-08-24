/*    */ package com.ygames.ysoccer.gui;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.GLColor;
/*    */ 
/*    */ 
/*    */ public class WidgetColor
/*    */ {
/*    */   public Integer body;
/*    */   Integer lightBorder;
/*    */   Integer darkBorder;
/*    */   
/*    */   public WidgetColor() {}
/*    */   
/*    */   public WidgetColor(int body, int lightBorder, int darkBorder) {
/* 15 */     this.body = Integer.valueOf(body);
/* 16 */     this.lightBorder = Integer.valueOf(lightBorder);
/* 17 */     this.darkBorder = Integer.valueOf(darkBorder);
/*    */   }
/*    */   
/*    */   public void set(Integer body, Integer lightBorder, Integer darkBorder) {
/* 21 */     this.body = body;
/* 22 */     this.lightBorder = lightBorder;
/* 23 */     this.darkBorder = darkBorder;
/*    */   }
/*    */   
/*    */   public WidgetColor brighter() {
/* 27 */     return new WidgetColor(GLColor.brighter(this.body.intValue()), GLColor.brighter(this.lightBorder.intValue()), GLColor.brighter(this.darkBorder.intValue()));
/*    */   }
/*    */   
/*    */   public WidgetColor darker() {
/* 31 */     return new WidgetColor(GLColor.darker(this.body.intValue()), GLColor.darker(this.lightBorder.intValue()), GLColor.darker(this.darkBorder.intValue()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\WidgetColor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */