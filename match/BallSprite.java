/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.Font;
/*    */ import com.ygames.ysoccer.framework.GLGraphics;
/*    */ import com.ygames.ysoccer.framework.Settings;
/*    */ 
/*    */ class BallSprite extends Sprite {
/*    */   Ball ball;
/*    */   
/*    */   BallSprite(GLGraphics glGraphics, Ball ball) {
/* 13 */     super(glGraphics);
/* 14 */     this.ball = ball;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(int subframe) {
/* 19 */     Data d = this.ball.data[subframe];
/* 20 */     this.glGraphics.batch.draw(Assets.ball[d.fmx], (d.x - 4), (d.y - d.z - 2 - 4));
/*    */     
/* 22 */     if (Settings.showDevelopmentInfo) {
/* 23 */       Assets.font3.draw((SpriteBatch)this.glGraphics.batch, d.x + "," + d.y + "," + d.z, d.x, d.y + 22, Font.Align.CENTER);
/*    */     }
/*    */   }
/*    */   
/*    */   public int getY(int subframe) {
/* 28 */     return (this.ball.data[subframe]).y;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\BallSprite.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */