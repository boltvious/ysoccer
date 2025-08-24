/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ class PlayerStatePhoto
/*    */   extends PlayerState
/*    */ {
/*    */   PlayerStatePhoto(PlayerFsm fsm) {
/*  8 */     super(PlayerFsm.Id.STATE_PHOTO, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 13 */     super.entryActions();
/*    */     
/* 15 */     if (this.player.role == Player.Role.GOALKEEPER) {
/* 16 */       this.player.fmx = 2.0F;
/* 17 */       this.player.fmy = 16.0F;
/*    */     } else {
/* 19 */       if (this.player.index == 9) {
/* 20 */         this.player.fmx = 2.0F;
/*    */       } else {
/* 22 */         this.player.fmx = (this.player.index % 2);
/*    */       } 
/* 24 */       this.player.fmy = 14.0F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStatePhoto.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */