/*    */ package com.ygames.ysoccer.gui;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.ygames.ysoccer.framework.Font;
/*    */ import com.ygames.ysoccer.framework.GLShapeRenderer;
/*    */ import com.ygames.ysoccer.framework.GLSpriteBatch;
/*    */ 
/*    */ public class Label
/*    */   extends Widget {
/*    */   public void render(GLSpriteBatch batch, GLShapeRenderer shapeRenderer) {
/* 11 */     if (!this.visible) {
/*    */       return;
/*    */     }
/*    */     
/* 15 */     batch.setColor(16777215, 0.92F);
/* 16 */     batch.begin();
/*    */     
/* 18 */     drawText((SpriteBatch)batch);
/*    */     
/* 20 */     batch.end();
/* 21 */     batch.setColor(16777215, 1.0F);
/*    */   }
/*    */   
/*    */   private void drawText(SpriteBatch batch) {
/* 25 */     int tx = this.x;
/* 26 */     switch (this.align) {
/*    */       case RIGHT:
/* 28 */         tx += this.w - this.font.size;
/*    */         break;
/*    */       
/*    */       case CENTER:
/* 32 */         tx += this.w / 2;
/*    */         break;
/*    */       
/*    */       case LEFT:
/* 36 */         tx += this.font.size;
/*    */         break;
/*    */     } 
/* 39 */     this.font.draw(batch, getText(), tx + this.textOffsetX, this.y + (int)Math.ceil((0.5F * (this.h - 8 - this.font.size))), this.align);
/*    */   }
/*    */   
/*    */   public String getText() {
/* 43 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\Label.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */