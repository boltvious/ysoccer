/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/*    */ 
/*    */ public class GLShapeRenderer
/*    */   extends ShapeRenderer {
/*    */   GLGraphics graphics;
/*    */   
/*    */   public GLShapeRenderer(GLGraphics graphics) {
/* 10 */     this.graphics = graphics;
/*    */   }
/*    */   
/*    */   public void setColor(int rgb, float alpha) {
/* 14 */     setColor((this.graphics.light * GLColor.red(rgb)) / 65025.0F, (this.graphics.light * GLColor.green(rgb)) / 65025.0F, (this.graphics.light * GLColor.blue(rgb)) / 65025.0F, alpha);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\GLShapeRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */