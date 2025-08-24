/*     */ package com.ygames.ysoccer.gui;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLColor;
/*     */ import com.ygames.ysoccer.framework.GLShapeRenderer;
/*     */ import com.ygames.ysoccer.framework.GLSpriteBatch;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Button
/*     */   extends Widget
/*     */ {
/*     */   private static final double sweepSpeed = 0.4D;
/*     */   private int imageX;
/*     */   private int imageY;
/*     */   
/*     */   public void render(GLSpriteBatch batch, GLShapeRenderer shapeRenderer) {
/*  25 */     if (!this.visible) {
/*     */       return;
/*     */     }
/*     */     
/*  29 */     Gdx.gl.glEnable(3042);
/*  30 */     shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*     */ 
/*     */     
/*  33 */     if (this.color.body != null) {
/*  34 */       shapeRenderer.setColor(this.color.body.intValue(), 0.92F);
/*  35 */       shapeRenderer.rect((this.x + 2), (this.y + 2), (this.w - 4), (this.h - 4));
/*     */     } 
/*     */ 
/*     */     
/*  39 */     if (this.color.lightBorder != null) {
/*  40 */       drawBorder(shapeRenderer, this.x, this.y, this.w, this.h, this.color.lightBorder.intValue(), this.color.darkBorder.intValue());
/*     */     }
/*     */     
/*  43 */     if (this.selected && this.active) {
/*  44 */       if (this.entryMode) {
/*  45 */         drawBorder(shapeRenderer, this.x, this.y, this.w, this.h, 15658634, 12566383);
/*     */       } else {
/*  47 */         drawAnimatedBorder(shapeRenderer);
/*     */       } 
/*     */     }
/*     */     
/*  51 */     shapeRenderer.end();
/*     */     
/*  53 */     batch.setColor(16777215, 0.92F);
/*     */     
/*  55 */     drawImage(batch);
/*     */     
/*  57 */     if (this.font != null) {
/*  58 */       batch.begin();
/*  59 */       drawText((SpriteBatch)batch);
/*  60 */       batch.end();
/*     */     } 
/*     */     
/*  63 */     batch.setColor(16777215, 1.0F);
/*     */   }
/*     */   
/*     */   protected void setImagePosition(int imageX, int imageY) {
/*  67 */     this.imageX = imageX;
/*  68 */     this.imageY = imageY;
/*     */   }
/*     */   
/*     */   protected void drawImage(GLSpriteBatch batch) {
/*  72 */     if (this.textureRegion == null) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     if (this.addShadow) {
/*  77 */       batch.setColor(2368548, 0.92F);
/*  78 */       batch.begin();
/*  79 */       batch.draw(this.textureRegion, (this.x + 4 + this.imageX), (this.y + 4 + this.imageY), 0.0F, 0.0F, this.textureRegion.getRegionWidth(), this.textureRegion.getRegionHeight(), this.imageScaleX, this.imageScaleY, 0.0F);
/*  80 */       batch.end();
/*  81 */       batch.setColor(16777215, 0.92F);
/*     */     } 
/*  83 */     batch.begin();
/*  84 */     batch.draw(this.textureRegion, (this.x + 2 + this.imageX), (this.y + 2 + this.imageY), 0.0F, 0.0F, this.textureRegion.getRegionWidth(), this.textureRegion.getRegionHeight(), this.imageScaleX, this.imageScaleY, 0.0F);
/*  85 */     batch.end();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawBorder(GLShapeRenderer shapeRenderer, int bx, int by, int bw, int bh, int topLeftColor, int bottomRightColor) {
/*  92 */     shapeRenderer.setColor(topLeftColor, 0.92F);
/*  93 */     shapeRenderer.triangle((bx + 1), by, (bx + bw - 1), by, (bx + bw - 1), (by + 2));
/*  94 */     shapeRenderer.triangle((bx + bw - 1), (by + 2), (bx + 1), (by + 2), (bx + 1), by);
/*  95 */     shapeRenderer.triangle(bx, (by + 1), bx, (by + bh - 1), (bx + 2), (by + bh - 1));
/*  96 */     shapeRenderer.triangle((bx + 2), (by + bh - 1), (bx + 2), (by + 2), bx, (by + 1));
/*     */ 
/*     */     
/*  99 */     shapeRenderer.setColor(bottomRightColor, 0.92F);
/* 100 */     shapeRenderer.triangle((bx + bw - 2), (by + 2), (bx + bw - 2), (by + bh - 1), (bx + bw), (by + bh - 1));
/* 101 */     shapeRenderer.triangle((bx + bw), (by + bh), (bx + bw), (by + 1), (bx + bw - 2), (by + 2));
/* 102 */     shapeRenderer.triangle((bx + 2), (by + bh - 2), (bx + bw - 2), (by + bh - 2), (bx + bw - 1), (by + bh));
/* 103 */     shapeRenderer.triangle((bx + bw - 1), (by + bh), (bx + 1), (by + bh), (bx + 2), (by + bh - 2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawAnimatedBorder(GLShapeRenderer shapeRenderer) {
/* 108 */     int gl = (int)Math.abs(0.4D * Math.abs(System.currentTimeMillis()) % 200.0D - 100.0D) + 100;
/*     */ 
/*     */     
/* 111 */     int bdr1 = GLColor.rgb(gl, gl, gl);
/*     */ 
/*     */     
/* 114 */     gl -= 50;
/* 115 */     int bdr2 = GLColor.rgb(gl, gl, gl);
/*     */     
/* 117 */     drawBorder(shapeRenderer, this.x, this.y, this.w, this.h, bdr1, bdr2);
/*     */   }
/*     */   
/*     */   private void drawText(SpriteBatch batch) {
/* 121 */     int tx = this.x;
/* 122 */     switch (this.align) {
/*     */       case RIGHT:
/* 124 */         tx += this.w - this.font.size;
/*     */         break;
/*     */       
/*     */       case CENTER:
/* 128 */         tx += this.w / 2;
/*     */         break;
/*     */       
/*     */       case LEFT:
/* 132 */         tx += this.font.size;
/*     */         break;
/*     */     } 
/*     */     
/* 136 */     this.font.draw(batch, getText(), tx + this.textOffsetX, this.y + EMath.ceil(0.5F * (this.h - this.font.regionHeight)), this.align);
/*     */   }
/*     */   
/*     */   public String getText() {
/* 140 */     return this.text;
/*     */   }
/*     */   
/*     */   protected void autoWidth() {
/* 144 */     this.w = this.font.textWidth(this.text) + 3 * this.font.size;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\Button.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */