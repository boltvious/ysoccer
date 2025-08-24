/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.ygames.ysoccer.match.AiFsm;
/*    */ import com.ygames.ysoccer.match.Player;
/*    */ 
/*    */ public class Ai
/*    */   extends InputDevice {
/*    */   public Player player;
/*    */   public AiFsm fsm;
/*    */   
/*    */   public Ai(Player player) {
/* 12 */     super(InputDevice.Type.COMPUTER, 0);
/* 13 */     this.player = player;
/*    */     
/* 15 */     this.fsm = new AiFsm(this);
/*    */   }
/*    */   
/*    */   public void read() {
/* 19 */     this.fsm.think();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\Ai.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */