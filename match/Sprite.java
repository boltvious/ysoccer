/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.ygames.ysoccer.framework.GLGraphics;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ class Sprite
/*    */ {
/*    */   GLGraphics glGraphics;
/*    */   TextureRegion textureRegion;
/*    */   int x;
/*    */   int y;
/*    */   int z;
/*    */   
/*    */   Sprite(GLGraphics glGraphics) {
/* 18 */     this.glGraphics = glGraphics;
/*    */   }
/*    */   
/*    */   public void draw(int subframe) {
/* 22 */     this.glGraphics.batch.draw(this.textureRegion, this.x, (this.y - this.z));
/*    */   }
/*    */   
/*    */   public int getY(int subframe) {
/* 26 */     return this.y;
/*    */   }
/*    */   
/*    */   static class SpriteComparator
/*    */     implements Comparator<Sprite> {
/*    */     private int subframe;
/*    */     
/*    */     public void setSubframe(int subframe) {
/* 34 */       this.subframe = subframe;
/*    */     }
/*    */ 
/*    */     
/*    */     public int compare(Sprite sprite1, Sprite sprite2) {
/* 39 */       return sprite1.getY(this.subframe) - sprite2.getY(this.subframe);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Sprite.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */