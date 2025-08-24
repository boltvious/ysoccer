/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.GLGraphics;
/*    */ 
/*    */ class CornerFlagSprite
/*    */   extends Sprite
/*    */ {
/*    */   private SceneSettings sceneSettings;
/*    */   
/*    */   CornerFlagSprite(GLGraphics glGraphics, SceneSettings sceneSettings, int sideX, int sideY) {
/* 13 */     super(glGraphics);
/* 14 */     this.sceneSettings = sceneSettings;
/* 15 */     this.x = sideX * 510;
/* 16 */     this.y = sideY * 640;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(int subframe) {
/* 21 */     int frameX = 2;
/* 22 */     int frameY = 1;
/* 23 */     if (this.sceneSettings.wind.speed > 0) {
/* 24 */       frameX = 2 * (1 + this.sceneSettings.wind.dirX);
/* 25 */       frameX += (subframe / 8 >> 4 - this.sceneSettings.wind.speed) % 2;
/* 26 */       frameY = 1 + this.sceneSettings.wind.dirY;
/*    */     } 
/* 28 */     this.glGraphics.batch.draw(Assets.cornerFlags[frameX][frameY], (this.x - 22), (this.y - 35));
/*    */   }
/*    */   
/*    */   void drawShadow(int subframe, SpriteBatch batch) {
/* 32 */     int frameX = 2;
/* 33 */     int frameY = 1;
/* 34 */     if (this.sceneSettings.wind.speed > 0) {
/* 35 */       frameX = 2 * (1 + this.sceneSettings.wind.dirX);
/* 36 */       frameX += (subframe / 8 >> 4 - this.sceneSettings.wind.speed) % 2;
/* 37 */       frameY = 1 + this.sceneSettings.wind.dirY;
/*    */     } 
/* 39 */     batch.draw(Assets.cornerFlagsShadows[frameX][frameY][0], (this.x - 12), (this.y + 1));
/* 40 */     if (this.sceneSettings.time == SceneSettings.Time.NIGHT) {
/* 41 */       batch.draw(Assets.cornerFlagsShadows[frameX][frameY][1], (this.x - 28), (this.y + 1));
/* 42 */       batch.draw(Assets.cornerFlagsShadows[frameX][frameY][2], (this.x - 28), (this.y - 10));
/* 43 */       batch.draw(Assets.cornerFlagsShadows[frameX][frameY][3], (this.x - 12), (this.y - 10));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\CornerFlagSprite.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */