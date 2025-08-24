/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.math.Vector3;
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ class AiStateSeeking
/*    */   extends AiState
/*    */ {
/*    */   AiStateSeeking(AiFsm fsm) {
/* 12 */     super(AiFsm.Id.STATE_SEEKING, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 17 */     super.doActions();
/*    */     
/* 19 */     if (this.player.ballDistance > 4.0F) {
/* 20 */       int d = this.player.frameDistance;
/* 21 */       if (d < 128) {
/* 22 */         Vector3 b = this.player.ball.prediction[d];
/* 23 */         float a = EMath.aTan2(b.y - this.player.y, b.x - this.player.x);
/* 24 */         this.ai.x0 = Math.round(EMath.cos(a));
/* 25 */         this.ai.y0 = Math.round(EMath.sin(a));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 33 */     PlayerState playerState = this.player.getState();
/* 34 */     if (playerState != null && 
/* 35 */       !playerState.checkId(PlayerFsm.Id.STATE_STAND_RUN)) {
/* 36 */       return this.fsm.stateIdle;
/*    */     }
/*    */ 
/*    */     
/* 40 */     if (this.player.ball.owner != null) {
/*    */       
/* 42 */       if (this.player.ball.owner == this.player) {
/* 43 */         return this.fsm.stateAttacking;
/*    */       }
/*    */       
/* 46 */       if (this.player.ball.owner.team == this.player.team) {
/* 47 */         return this.fsm.statePositioning;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 52 */     if (this.player.team.near1 != this.player) {
/* 53 */       return this.fsm.statePositioning;
/*    */     }
/*    */     
/* 56 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStateSeeking.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */