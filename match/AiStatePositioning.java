/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ class AiStatePositioning
/*    */   extends AiState
/*    */ {
/*    */   AiStatePositioning(AiFsm fsm) {
/* 11 */     super(AiFsm.Id.STATE_POSITIONING, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 16 */     super.doActions();
/* 17 */     if (this.player.targetDistance() > 20.0F) {
/* 18 */       float a = this.player.targetAngle();
/* 19 */       this.ai.x0 = Math.round(EMath.cos(a));
/* 20 */       this.ai.y0 = Math.round(EMath.sin(a));
/*    */     } else {
/* 22 */       this.ai.x0 = 0;
/* 23 */       this.ai.y0 = 0;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 29 */     PlayerState playerState = this.player.getState();
/* 30 */     if (playerState != null && 
/* 31 */       !playerState.checkId(PlayerFsm.Id.STATE_STAND_RUN)) {
/* 32 */       return this.fsm.stateIdle;
/*    */     }
/*    */     
/* 35 */     if (this.player == this.player.team.near1) {
/* 36 */       if (this.player.ball.owner == null)
/* 37 */         return this.fsm.stateSeeking; 
/* 38 */       if (this.player.ball.owner == this.player) {
/* 39 */         return this.fsm.stateAttacking;
/*    */       }
/*    */     } 
/*    */     
/* 43 */     if (this.player.team.bestDefender == this.player) {
/* 44 */       return this.fsm.stateDefending;
/*    */     }
/*    */     
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiStatePositioning.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */