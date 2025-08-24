/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.Ai;
/*    */ 
/*    */ class AiState
/*    */   extends State {
/*    */   protected final AiFsm fsm;
/*    */   protected final Ai ai;
/*    */   protected final Player player;
/*    */   
/*    */   AiState(AiFsm.Id id, AiFsm fsm) {
/* 12 */     this.id = id.ordinal();
/* 13 */     this.fsm = fsm;
/* 14 */     this.ai = fsm.ai;
/* 15 */     this.player = this.ai.player;
/*    */     
/* 17 */     fsm.addState(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\AiState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */