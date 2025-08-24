/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class Wind
/*    */ {
/*    */   public int speed;
/*    */   public int direction;
/*    */   public int angle;
/*    */   public int dirX;
/*    */   public int dirY;
/*    */   
/*    */   public void init(int weatherStrenght, Random rand) {
/* 16 */     this.speed = weatherStrenght;
/* 17 */     this.direction = rand.nextInt(8);
/* 18 */     this.angle = 45 * this.direction;
/* 19 */     this.dirX = Math.round(EMath.cos(this.angle));
/* 20 */     this.dirY = Math.round(EMath.sin(this.angle));
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Wind.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */