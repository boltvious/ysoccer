/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateTackle
/*    */   extends PlayerState
/*    */ {
/*    */   private boolean hit;
/*    */   
/*    */   PlayerStateTackle(PlayerFsm fsm) {
/* 13 */     super(PlayerFsm.Id.STATE_TACKLE, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 18 */     super.entryActions();
/*    */     
/* 20 */     this.hit = false;
/*    */     
/* 22 */     this.player.v = 1.4F * this.player.speed * (1.0F + 0.02F * this.player.skills.tackling);
/* 23 */     this.player.x += 10.0F * EMath.cos(this.player.a);
/* 24 */     this.player.y += 10.0F * EMath.sin(this.player.a);
/* 25 */     this.player.fmx = (Math.round((this.player.a + 360.0F) % 360.0F / 45.0F) % 8);
/* 26 */     this.player.fmy = 4.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 31 */     super.doActions();
/*    */ 
/*    */     
/* 34 */     if (!this.hit && 
/* 35 */       this.ball.z < 5.0F && this.player.ballDistance < 7.0F) {
/* 36 */       float angle = EMath.aTan2(this.ball.y - this.player.y, this.ball.x - this.player.x);
/* 37 */       float angleDiff = EMath.angleDiff(angle, this.player.a);
/* 38 */       if (angleDiff <= 90.0F && this.player.ballDistance * 
/* 39 */         EMath.sin(angleDiff) <= 8.0F + 0.3F * this.player.skills.tackling) {
/*    */         
/* 41 */         this.scene.setBallOwner(this.player);
/* 42 */         this.ball.v = this.player.v * (1.0F + 0.02F * this.player.skills.tackling);
/* 43 */         this.hit = true;
/*    */         
/* 45 */         if (this.player.inputDevice.value && 
/* 46 */           Math.abs((this.player.a - this.player.inputDevice.angle + 540.0F) % 360.0F - 180.0F) < 67.5D) {
/* 47 */           this.ball.a = this.player.inputDevice.angle;
/*    */         } else {
/* 49 */           this.ball.a = this.player.a;
/*    */         } 
/*    */         
/* 52 */         Assets.Sounds.kick.play(0.1F * (1.0F + 0.03F * this.timer) * Assets.Sounds.volume / 100.0F);
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 58 */     if (!this.hit) {
/* 59 */       float bodyX = this.player.x - 9.0F * EMath.cos(this.player.a);
/* 60 */       float bodyY = this.player.y - 9.0F * EMath.sin(this.player.a);
/* 61 */       if (this.ball.z < 7.0F && EMath.dist(this.ball.x, this.ball.y, bodyX, bodyY) < 9.0F) {
/* 62 */         float angle = EMath.aTan2(this.ball.y - bodyY, this.ball.x - bodyX);
/* 63 */         float angleDiff = EMath.angleDiff(angle, this.player.a);
/* 64 */         this.ball.v = this.player.v * Math.abs(EMath.cos(angleDiff));
/* 65 */         this.ball.a = angle;
/* 66 */         this.hit = true;
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     this.player.v = (float)(this.player.v - ((20.0F + this.player.scene.settings.grass.friction) / 512.0F) * Math.sqrt(Math.abs(this.player.v)));
/*    */ 
/*    */     
/* 73 */     this.player.fmx = (Math.round((this.player.a + 360.0F) % 360.0F / 45.0F) % 8);
/* 74 */     this.player.fmy = 4.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 79 */     if (this.player.v < 30.0F) {
/* 80 */       return this.fsm.stateStandRun;
/*    */     }
/* 82 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateTackle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */