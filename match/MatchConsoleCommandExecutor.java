/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.strongjoshua.console.annotation.HiddenCommand;
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MatchConsoleCommandExecutor
/*    */   extends ConsoleCommandExecutor
/*    */ {
/*    */   private Match match;
/*    */   
/*    */   public MatchConsoleCommandExecutor(Match match) {
/* 19 */     this.match = match;
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void ballPosition() {
/* 24 */     this.console.log("(" + this.match.ball.x + ", " + this.match.ball.y + ", " + this.match.ball.z + ")");
/*    */   }
/*    */   
/*    */   public void ballPosition(float x, float y, float z) {
/* 28 */     this.match.ball.setPosition(x, y, z);
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void ballSpeed() {
/* 33 */     this.console.log("(" + (this.match.ball.v * EMath.cos(this.match.ball.a)) + ", " + (this.match.ball.v * EMath.sin(this.match.ball.a)) + ", " + this.match.ball.vz + ")");
/*    */   }
/*    */   
/*    */   public void ballSpeed(float vx, float vy, float vz) {
/* 37 */     this.match.ball.v = EMath.hypo(vx, vy);
/* 38 */     this.match.ball.a = EMath.aTan2(vy, vx);
/* 39 */     this.match.ball.vz = vz;
/*    */   }
/*    */   
/*    */   public void homePenalty() {
/* 43 */     this.match.newTackle((this.match.team[1]).lineup.get(0), (this.match.team[0]).lineup.get(0), 0.0F, 0.0F);
/* 44 */     this.match.newFoul(0.0F, ((this.match.team[1]).side * 524));
/*    */   }
/*    */   
/*    */   public void awayPenalty() {
/* 48 */     this.match.newTackle((this.match.team[0]).lineup.get(0), (this.match.team[1]).lineup.get(0), 0.0F, 0.0F);
/* 49 */     this.match.newFoul(0.0F, ((this.match.team[0]).side * 524));
/*    */   }
/*    */   
/*    */   public void homeFreeKick() {
/* 53 */     this.match.newTackle((this.match.team[1]).lineup.get(0), (this.match.team[0]).lineup.get(0), 0.0F, 0.0F);
/* 54 */     newFoul((this.match.team[1]).side);
/*    */   }
/*    */   
/*    */   public void awayFreeKick() {
/* 58 */     this.match.newTackle((this.match.team[0]).lineup.get(0), (this.match.team[1]).lineup.get(0), 0.0F, 0.0F);
/* 59 */     newFoul((this.match.team[0]).side);
/*    */   }
/*    */ 
/*    */   
/*    */   private void newFoul(int side) {
/*    */     while (true) {
/* 65 */       float a = (EMath.rand(-90, 90) - 90 * side);
/* 66 */       float d = EMath.rand(174, (int)EMath.hypo(284.0F, 467.0F));
/* 67 */       float x = d * EMath.cos(a);
/* 68 */       float y = (side * 640) + d * EMath.sin(a);
/* 69 */       if (!Const.isInsidePenaltyArea(x, y, side) && Const.isInsideDirectShotArea(x, y, side)) {
/* 70 */         this.match.newFoul(x, y);
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchConsoleCommandExecutor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */