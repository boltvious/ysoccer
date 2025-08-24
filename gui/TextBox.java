/*    */ package com.ygames.ysoccer.gui;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.Batch;
/*    */ import com.badlogic.gdx.graphics.g2d.BitmapFont;
/*    */ import com.ygames.ysoccer.framework.GLShapeRenderer;
/*    */ import com.ygames.ysoccer.framework.GLSpriteBatch;
/*    */ import java.util.List;
/*    */ 
/*    */ public class TextBox
/*    */   extends Widget
/*    */ {
/*    */   private BitmapFont font;
/*    */   public List<String> lines;
/*    */   private int top;
/*    */   private int centerX;
/*    */   
/*    */   public TextBox(BitmapFont font, List<String> lines, int centerX, int top) {
/* 18 */     this.font = font;
/* 19 */     this.lines = lines;
/* 20 */     this.top = top;
/* 21 */     this.centerX = centerX;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GLSpriteBatch batch, GLShapeRenderer shapeRenderer) {
/* 26 */     batch.begin();
/* 27 */     int y = this.top;
/* 28 */     for (String line : this.lines) {
/* 29 */       this.font.draw((Batch)batch, line, this.centerX, y, 0.0F, 1, true);
/* 30 */       y += 22;
/*    */     } 
/* 32 */     batch.end();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\TextBox.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */