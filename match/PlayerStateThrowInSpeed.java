/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ class PlayerStateThrowInSpeed
/*    */   extends PlayerState
/*    */ {
/*    */   private boolean thrown;
/*    */   
/*    */   PlayerStateThrowInSpeed(PlayerFsm fsm) {
/* 13 */     super(PlayerFsm.Id.STATE_THROW_IN_SPEED, fsm);
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 18 */     super.entryActions();
/* 19 */     this.thrown = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions() {
/* 24 */     super.doActions();
/*    */     
/* 26 */     if (!this.thrown) {
/* 27 */       this.ball.setX(this.player.x);
/* 28 */       this.ball.setY(this.player.y);
/* 29 */       this.ball.setZ(24.0F);
/* 30 */       this.player.fmy = 8.0F;
/*    */       
/* 32 */       if (this.timer > 76.8F && !this.player.inputDevice.fire11) {
/* 33 */         this.thrown = true;
/*    */       }
/* 35 */       if (this.timer > 153.6F) {
/* 36 */         this.thrown = true;
/*    */       }
/*    */       
/* 39 */       if (this.thrown) {
/* 40 */         this.ball.setX(this.player.x + 6.0F * EMath.cos(this.player.a));
/* 41 */         this.ball.setY(this.player.y + 6.0F * EMath.sin(this.player.a));
/* 42 */         this.ball.v = 30.0F + 1000.0F * this.timer / 512.0F;
/* 43 */         this.ball.vz = 500.0F * this.timer / 512.0F;
/* 44 */         switch (Math.round(this.player.fmx)) {
/*    */           case 2:
/* 46 */             this.ball.a = (90 + (10 + 5 * this.player.scene.settings.wind.speed) * this.ball.xSide);
/*    */             break;
/*    */           
/*    */           case 6:
/* 50 */             this.ball.a = (270 - (10 + 5 * this.player.scene.settings.wind.speed) * this.ball.xSide);
/*    */             break;
/*    */           
/*    */           default:
/* 54 */             this.ball.a = 45.0F * this.player.fmx;
/*    */             break;
/*    */         } 
/*    */         
/* 58 */         boolean longRange = (this.timer > 153.6F);
/*    */         
/* 60 */         if (longRange) {
/* 61 */           this.player.searchFarPassingMate();
/*    */         } else {
/* 63 */           this.player.searchPassingMate();
/*    */         } 
/*    */         
/* 66 */         if (this.player.passingMate != null) {
/* 67 */           this.ball.a += this.player.passingMateAngleCorrection;
/*    */         }
/*    */         
/* 70 */         this.player.fmy = 9.0F;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   State checkConditions() {
/* 77 */     if (this.timer > 179.2F) {
/* 78 */       return this.fsm.stateStandRun;
/*    */     }
/* 80 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerStateThrowInSpeed.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */