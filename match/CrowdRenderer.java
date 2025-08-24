/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.files.FileHandle;
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ 
/*    */ public class CrowdRenderer
/*    */ {
/*    */   private int maxRank;
/*    */   private Position[] positions;
/*    */   
/*    */   private static class Position {
/*    */     int x;
/*    */     int y;
/*    */     int type;
/*    */     int rank;
/*    */   }
/*    */   
/*    */   public CrowdRenderer(FileHandle fileHandle) {
/* 20 */     this.positions = (Position[])Assets.json.fromJson(Position[].class, fileHandle);
/* 21 */     for (Position position : this.positions) {
/* 22 */       position.x -= 847;
/* 23 */       position.y -= 919;
/*    */     } 
/*    */   }
/*    */   
/*    */   void setMaxRank(int l) {
/* 28 */     this.maxRank = Math.max(0, l);
/*    */   }
/*    */   
/*    */   void draw(SpriteBatch batch) {
/* 32 */     for (Position position : this.positions) {
/* 33 */       if (position.rank <= this.maxRank)
/* 34 */         batch.draw(Assets.crowd[position.type], position.x, position.y); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\CrowdRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */