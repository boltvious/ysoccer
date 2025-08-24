/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.strongjoshua.console.CommandExecutor;
/*    */ import com.strongjoshua.console.annotation.HiddenCommand;
/*    */ 
/*    */ public class ConsoleCommandExecutor
/*    */   extends CommandExecutor {
/*    */   @HiddenCommand
/*    */   public void gravity() {
/* 10 */     this.console.log("gravity " + Const.GRAVITY);
/*    */   }
/*    */   
/*    */   public void gravity(float f) {
/* 14 */     Const.GRAVITY = f;
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void airFriction() {
/* 19 */     this.console.log("airFriction " + Const.AIR_FRICTION);
/*    */   }
/*    */   
/*    */   public void airFriction(float f) {
/* 23 */     Const.AIR_FRICTION = f;
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void spinFactor() {
/* 28 */     this.console.log("spinFactor " + Const.SPIN_FACTOR);
/*    */   }
/*    */   
/*    */   public void spinFactor(float f) {
/* 32 */     Const.SPIN_FACTOR = f;
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void spinDampening() {
/* 37 */     this.console.log("spinDampening " + Const.SPIN_DAMPENING);
/*    */   }
/*    */   
/*    */   public void spinDampening(float f) {
/* 41 */     Const.SPIN_DAMPENING = f;
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void bounce() {
/* 46 */     this.console.log("bounce " + Const.BOUNCE);
/*    */   }
/*    */   
/*    */   public void bounce(float f) {
/* 50 */     Const.BOUNCE = f;
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void passingThreshold() {
/* 55 */     this.console.log("passingThreshold " + Const.PASSING_THRESHOLD);
/*    */   }
/*    */   
/*    */   public void passingThreshold(float f) {
/* 59 */     Const.PASSING_THRESHOLD = f;
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void passingSpeedFactor() {
/* 64 */     this.console.log("passingSpeedFactor " + Const.PASSING_SPEED_FACTOR);
/*    */   }
/*    */   
/*    */   public void passingSpeedFactor(float f) {
/* 68 */     Const.PASSING_SPEED_FACTOR = f;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\ConsoleCommandExecutor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */