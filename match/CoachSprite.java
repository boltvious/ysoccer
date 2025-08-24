/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.GLGraphics;
/*    */ 
/*    */ class CoachSprite
/*    */   extends Sprite {
/*    */   private Coach coach;
/*    */   
/*    */   CoachSprite(GLGraphics glGraphics, Coach coach) {
/* 11 */     super(glGraphics);
/* 12 */     this.coach = coach;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(int subframe) {
/* 17 */     this.glGraphics.batch.draw(Assets.coach[this.coach.team.index][this.coach.fmx], this.coach.x - 7.0F, this.coach.y - 25.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\CoachSprite.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */