/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.GLGraphics;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class JumperSprite
/*    */   extends Sprite
/*    */ {
/*    */   JumperSprite(GLGraphics glGraphics, int xSide, int ySide) {
/* 14 */     super(glGraphics);
/* 15 */     this.textureRegion = new TextureRegion(Assets.jumper);
/* 16 */     this.textureRegion.flip(false, true);
/* 17 */     this.x = xSide * 92 - 1;
/* 18 */     this.y = ySide * 684 - 40 - 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getY(int subframe) {
/* 23 */     return this.y + 40;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\JumperSprite.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */