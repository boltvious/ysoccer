/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.math.Vector3;
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ class AiStateDefending
/*    */   extends AiState
/*    */ {
/* 11 */   private static int MIN_UPDATE_INTERVAL = 15;
/* 12 */   private static int MAX_UPDATE_INTERVAL = 30;
/*    */   
/*    */   private int nextUpdate;
/*    */   
/*    */   AiStateDefending(AiFsm fsm) {
/* 17 */     super(AiFsm.Id.STATE_DEFENDING, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 22 */     super.entryActions();
/*    */     
/* 24 */     this.nextUpdate = EMath.rand(MIN_UPDATE_INTERVAL, MAX_UPDATE_INTERVAL);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 29 */     super.doActions();
/*    */     
/* 31 */     if (this.timer > this.nextUpdate) {
/* 32 */       int d = this.player.frameDistance;
/* 33 */       if (d < 128) {
/* 34 */         Vector3 b = this.player.ball.prediction[d];
/* 35 */         float a = EMath.aTan2(b.y - this.player.y, b.x - this.player.x);
/* 36 */         this.ai.x0 = Math.round(EMath.cos(a));
/* 37 */         this.ai.y0 = Math.round(EMath.sin(a));
/*    */       } 
/*    */       
/* 40 */       this.nextUpdate += EMath.rand(MIN_UPDATE_INTERVAL, MAX_UPDATE_INTERVAL);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 47 */     PlayerState playerState = this.player.getState();
/* 48 */     if (playerState != null && 
/* 49 */       !playerState.checkId(PlayerFsm.Id.STATE_STAND_RUN)) {
/* 50 */       return this.fsm.stateIdle;
/*    */     }
/*    */ 
/*    */     
/* 54 */     if (this.player.ball.owner != null) {
/*    */       
/* 56 */       if (this.player.ball.owner == this.player) {
/* 57 */         return this.fsm.stateAttacking;
/*    */       }
/*    */       
/* 60 */       if (this.player.ball.owner.team == this.player.team) {
/* 61 */         return this.fsm.statePositioning;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 66 */     if (this.player.team.bestDefender != this.player) {
/* 67 */       return this.fsm.statePositioning;
/*    */     }
/*    */     
/* 70 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateDefending.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */