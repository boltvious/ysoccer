/*    */ package com.ygames.ysoccer.gui;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.ygames.ysoccer.framework.GLSpriteBatch;
/*    */ 
/*    */ public class Picture extends Widget {
/*    */   protected HAlign hAlign;
/*    */   protected VAlign vAlign;
/*    */   
/*    */   protected enum HAlign {
/* 10 */     RIGHT, CENTER, LEFT;
/*    */   }
/*    */   
/*    */   protected enum VAlign {
/* 14 */     TOP, CENTER, BOTTOM;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Picture() {
/* 21 */     this.hAlign = HAlign.CENTER;
/* 22 */     this.vAlign = VAlign.CENTER;
/*    */   }
/*    */   
/*    */   public Picture(TextureRegion textureRegion) {
/* 26 */     this();
/* 27 */     this.textureRegion = textureRegion;
/* 28 */     this.w = textureRegion.getRegionWidth();
/* 29 */     this.h = textureRegion.getRegionHeight();
/*    */   }
/*    */   
/*    */   public void setTextureRegion(TextureRegion textureRegion) {
/* 33 */     if (textureRegion != null) {
/* 34 */       this.textureRegion = textureRegion;
/* 35 */       this.w = textureRegion.getRegionWidth();
/* 36 */       this.h = textureRegion.getRegionHeight();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void limitToSize(int wMax, int hMax) {
/* 41 */     if (this.textureRegion != null) {
/* 42 */       float w = this.textureRegion.getRegionWidth();
/* 43 */       float h = this.textureRegion.getRegionHeight();
/* 44 */       this.imageScaleX = 1.0F;
/* 45 */       this.imageScaleY = 1.0F;
/* 46 */       if (w > wMax || h > hMax) {
/* 47 */         this.imageScaleX = Math.min(wMax / w, hMax / h);
/* 48 */         this.imageScaleY = Math.min(wMax / w, hMax / h);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GLSpriteBatch batch, GLShapeRenderer shapeRenderer) {
/* 55 */     if (!this.visible) {
/*    */       return;
/*    */     }
/*    */     
/* 59 */     if (this.textureRegion != null) {
/* 60 */       int posX = this.x;
/* 61 */       if (this.hAlign == HAlign.CENTER) {
/* 62 */         posX -= (int)(this.w * this.imageScaleX / 2.0F);
/* 63 */       } else if (this.hAlign == HAlign.RIGHT) {
/* 64 */         posX -= (int)(this.w * this.imageScaleX);
/*    */       } 
/*    */       
/* 67 */       int posY = this.y;
/* 68 */       if (this.vAlign == VAlign.CENTER) {
/* 69 */         posY -= (int)(this.h * this.imageScaleY / 2.0F);
/* 70 */       } else if (this.vAlign == VAlign.BOTTOM) {
/* 71 */         posY -= (int)(this.h * this.imageScaleY);
/*    */       } 
/* 73 */       if (this.addShadow) {
/* 74 */         batch.setColor(2368548, 0.92F);
/* 75 */         batch.begin();
/* 76 */         batch.draw(this.textureRegion, (posX + 2), (posY + 2), 0.0F, 0.0F, this.w, this.h, this.imageScaleX, this.imageScaleY, 0.0F);
/* 77 */         batch.end();
/*    */       } 
/* 79 */       batch.setColor(16777215, 0.92F);
/* 80 */       batch.begin();
/* 81 */       batch.draw(this.textureRegion, posX, posY, 0.0F, 0.0F, this.w, this.h, this.imageScaleX, this.imageScaleY, 0.0F);
/* 82 */       batch.end();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\Picture.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */