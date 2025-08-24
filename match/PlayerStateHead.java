/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ class PlayerStateHead
/*    */   extends PlayerState
/*    */ {
/*    */   private boolean hit;
/*    */   private boolean jumped;
/*    */   private float v;
/*    */   
/*    */   PlayerStateHead(PlayerFsm fsm) {
/* 15 */     super(PlayerFsm.Id.STATE_HEAD, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 20 */     super.doActions();
/*    */     
/* 22 */     if (!this.hit && 
/* 23 */       EMath.isIn(this.ball.z, this.player.z + 22.0F - 4.0F, this.player.z + 22.0F + 4.0F))
/*    */     {
/* 25 */       if (this.player.ballDistance < 8.0F) {
/*    */         
/* 27 */         this.scene.setBallOwner(this.player);
/* 28 */         this.ball.v = Math.max((170 + 7 * this.player.skills.heading), 1.5F * this.player.v);
/* 29 */         this.ball.vz = (100 - 8 * this.player.skills.heading) + this.player.vz;
/* 30 */         this.hit = true;
/*    */         
/* 32 */         Assets.Sounds.kick.play(0.1F * (1.0F + 0.03F * this.timer) * Assets.Sounds.volume / 100.0F);
/*    */         
/* 34 */         if (this.player.inputDevice.value) {
/* 35 */           this.ball.a = this.player.inputDevice.angle;
/*    */         } else {
/* 37 */           this.ball.a = this.player.a;
/*    */         } 
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/* 43 */     if (!this.jumped && this.timer > 25.6F) {
/* 44 */       if (this.v > 0.0F) {
/* 45 */         this.player.v = 0.5F * this.player.speed * (1.0F + 0.1F * this.player.skills.heading);
/*    */       }
/* 47 */       this.player.vz = (90 + 5 * this.player.skills.heading);
/* 48 */       this.jumped = true;
/*    */     } 
/*    */ 
/*    */     
/* 52 */     this.player.fmx = (Math.round((this.player.a + 360.0F) % 360.0F / 45.0F) % 8);
/*    */     
/* 54 */     if (this.jumped) {
/* 55 */       if (this.player.z > 0.0F) {
/* 56 */         if (this.player.vz > 40.0F) {
/* 57 */           this.player.fmy = 5.0F;
/* 58 */         } else if (this.player.vz > -30.0F) {
/* 59 */           this.player.fmy = 6.0F;
/*    */         } else {
/* 61 */           this.player.fmy = 5.0F;
/*    */         } 
/*    */       } else {
/* 64 */         this.player.fmy = 1.0F;
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 71 */     if (this.jumped && this.player.vz == 0.0F) {
/* 72 */       return this.fsm.stateStandRun;
/*    */     }
/* 74 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 79 */     super.entryActions();
/*    */     
/* 81 */     this.hit = false;
/* 82 */     this.jumped = false;
/* 83 */     this.v = this.player.v;
/* 84 */     this.player.v = 0.0F;
/*    */     
/* 86 */     this.player.fmy = 3.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateHead.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */