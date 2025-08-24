/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ class PlayerState
/*    */   extends State {
/*    */   protected final PlayerFsm fsm;
/*    */   protected final Player player;
/*    */   protected final Scene scene;
/*    */   protected final Ball ball;
/*    */   
/*    */   public PlayerState(PlayerFsm.Id id, PlayerFsm fsm) {
/* 11 */     this.id = id.ordinal();
/* 12 */     this.fsm = fsm;
/* 13 */     this.player = fsm.player;
/* 14 */     this.scene = this.player.scene;
/* 15 */     this.ball = this.player.ball;
/*    */     
/* 17 */     fsm.addState(this);
/*    */   }
/*    */   
/*    */   boolean checkId(PlayerFsm.Id id) {
/* 21 */     return (this.id == id.ordinal());
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */