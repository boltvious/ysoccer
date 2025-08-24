/*     */ package com.ygames.ysoccer.gui;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.InputAdapter;
/*     */ import com.badlogic.gdx.InputProcessor;
/*     */ import com.ygames.ysoccer.framework.GLColor;
/*     */ import com.ygames.ysoccer.framework.GLShapeRenderer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Piece
/*     */   extends Button
/*     */ {
/*  18 */   private final int GL_MIN = 36;
/*  19 */   private final int GL_MAX = 200;
/*     */   
/*     */   protected int[] square;
/*     */   
/*     */   private int gridX;
/*     */   
/*     */   private int gridY;
/*     */   private int gridW;
/*     */   private int gridH;
/*     */   private int xMin;
/*     */   private int xMax;
/*     */   private int yMin;
/*     */   private int yMax;
/*     */   private InputProcessor inputProcessor;
/*     */   
/*     */   protected Piece() {
/*  35 */     this.square = new int[2];
/*  36 */     this.inputProcessor = (InputProcessor)new PieceInputProcessor();
/*     */   }
/*     */   
/*     */   protected void toggleEntryMode() {
/*  40 */     setEntryMode(!this.entryMode);
/*     */   }
/*     */   
/*     */   public void setEntryMode(boolean entryMode) {
/*  44 */     if (!this.entryMode && entryMode) {
/*  45 */       this.entryMode = true;
/*  46 */       Gdx.input.setInputProcessor(this.inputProcessor);
/*     */     } 
/*  48 */     if (this.entryMode && !entryMode) {
/*  49 */       this.entryMode = false;
/*  50 */       Gdx.input.setInputProcessor(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void setSquare(int x, int y) {
/*  55 */     this.square[0] = x;
/*  56 */     this.square[1] = y;
/*  57 */     updatePosition();
/*     */   }
/*     */   
/*     */   protected void setGridGeometry(int x, int y, int w, int h) {
/*  61 */     this.gridX = x;
/*  62 */     this.gridY = y;
/*  63 */     this.gridW = w;
/*  64 */     this.gridH = h;
/*  65 */     updatePosition();
/*     */   }
/*     */   
/*     */   protected void setRanges(int xMin, int xMax, int yMin, int yMax) {
/*  69 */     this.xMin = xMin;
/*  70 */     this.xMax = xMax;
/*  71 */     this.yMin = yMin;
/*  72 */     this.yMax = yMax;
/*  73 */     updatePosition();
/*     */   }
/*     */   
/*     */   private void updatePosition() {
/*  77 */     this.x = this.gridX + this.square[0] * this.gridW / (this.xMax - this.xMin);
/*  78 */     this.y = this.gridY + this.square[1] * this.gridH / (this.yMax - this.yMin);
/*     */   }
/*     */   
/*     */   private class PieceInputProcessor
/*     */     extends InputAdapter {
/*     */     public boolean keyDown(int keycode) {
/*  84 */       switch (keycode) {
/*     */         case 21:
/*  86 */           Piece.this.square[0] = Math.max(Piece.this.square[0] - 1, Piece.this.xMin);
/*  87 */           Piece.this.updatePosition();
/*  88 */           Piece.this.onChanged();
/*  89 */           Piece.this.setDirty(true);
/*     */           break;
/*     */         
/*     */         case 22:
/*  93 */           Piece.this.square[0] = Math.min(Piece.this.square[0] + 1, Piece.this.xMax);
/*  94 */           Piece.this.updatePosition();
/*  95 */           Piece.this.onChanged();
/*  96 */           Piece.this.setDirty(true);
/*     */           break;
/*     */         
/*     */         case 19:
/* 100 */           Piece.this.square[1] = Math.max(Piece.this.square[1] - 1, Piece.this.yMin);
/* 101 */           Piece.this.updatePosition();
/* 102 */           Piece.this.onChanged();
/* 103 */           Piece.this.setDirty(true);
/*     */           break;
/*     */         
/*     */         case 20:
/* 107 */           Piece.this.square[1] = Math.min(Piece.this.square[1] + 1, Piece.this.yMax);
/* 108 */           Piece.this.updatePosition();
/* 109 */           Piece.this.onChanged();
/* 110 */           Piece.this.setDirty(true);
/*     */           break;
/*     */       } 
/* 113 */       return true;
/*     */     }
/*     */     
/*     */     private PieceInputProcessor() {}
/*     */   }
/*     */   
/*     */   public void onChanged() {}
/*     */   
/*     */   protected void drawBorder(GLShapeRenderer shapeRenderer, int bx, int by, int bw, int bh, int topLeftColor, int bottomRightColor) {
/* 122 */     int color = GLColor.rgb(200, 200, 0);
/* 123 */     shapeRenderer.setColor(color, 0.92F);
/* 124 */     shapeRenderer.ellipse(this.x, this.y, this.w, this.h);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawAnimatedBorder(GLShapeRenderer shapeRenderer) {
/* 130 */     int gl = (int)Math.abs(0.8D * Math.abs(System.currentTimeMillis()) % 328.0D - 164.0D) + 36;
/* 131 */     int color = GLColor.rgb(gl, gl, gl);
/* 132 */     shapeRenderer.setColor(color, 0.92F);
/* 133 */     shapeRenderer.ellipse(this.x, this.y, this.w, this.h);
/* 134 */     shapeRenderer.setColor(16777215, 0.92F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\Piece.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */