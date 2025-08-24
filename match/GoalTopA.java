/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.GLGraphics;
/*    */ 
/*    */ 
/*    */ class GoalTopA
/*    */   extends Sprite
/*    */ {
/*    */   GoalTopA(GLGraphics glGraphics) {
/* 12 */     super(glGraphics);
/* 13 */     this.textureRegion = new TextureRegion(Assets.goalTopA);
/* 14 */     this.textureRegion.flip(false, true);
/* 15 */     this.x = -73;
/* 16 */     this.y = -691;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getY(int subframe) {
/* 21 */     return -640;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\GoalTopA.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */