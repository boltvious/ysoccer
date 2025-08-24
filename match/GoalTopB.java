/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.GLGraphics;
/*    */ 
/*    */ 
/*    */ 
/*    */ class GoalTopB
/*    */   extends Sprite
/*    */ {
/*    */   GoalTopB(GLGraphics glGraphics) {
/* 13 */     super(glGraphics);
/* 14 */     this.textureRegion = new TextureRegion(Assets.goalTopB);
/* 15 */     this.textureRegion.flip(false, true);
/* 16 */     this.x = -69;
/* 17 */     this.y = -673;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getY(int subframe) {
/* 22 */     return -659;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\GoalTopB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */