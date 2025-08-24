/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.OrthographicCamera;
/*    */ 
/*    */ public class GLGraphics
/*    */ {
/*    */   public GLSpriteBatch batch;
/*    */   public GLShapeRenderer shapeRenderer;
/*    */   public OrthographicCamera camera;
/* 10 */   public int light = 255;
/*    */   
/*    */   public GLGraphics() {
/* 13 */     this.batch = new GLSpriteBatch(this);
/* 14 */     this.shapeRenderer = new GLShapeRenderer(this);
/* 15 */     this.camera = new OrthographicCamera();
/*    */   }
/*    */   
/*    */   public void dispose() {
/* 19 */     this.batch.dispose();
/* 20 */     this.shapeRenderer.dispose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\GLGraphics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */