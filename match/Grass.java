/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ public class Grass
/*    */ {
/*    */   public int lightShadow;
/*    */   public int darkShadow;
/*    */   float friction;
/*    */   float bounce;
/*    */   
/*    */   Grass() {}
/*    */   
/*    */   Grass(int lightShadow, int darkShadow, float friction, float bounce) {
/* 14 */     this.lightShadow = lightShadow;
/* 15 */     this.darkShadow = darkShadow;
/* 16 */     this.friction = friction;
/* 17 */     this.bounce = bounce;
/*    */   }
/*    */   
/*    */   void copy(Grass other) {
/* 21 */     this.lightShadow = other.lightShadow;
/* 22 */     this.darkShadow = other.darkShadow;
/* 23 */     this.friction = other.friction;
/* 24 */     this.bounce = other.bounce;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Grass.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */