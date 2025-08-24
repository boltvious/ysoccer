/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.strongjoshua.console.annotation.HiddenCommand;
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ 
/*    */ public class TrainingConsoleCommandExecutor
/*    */   extends ConsoleCommandExecutor {
/*    */   private Training training;
/*    */   
/*    */   public TrainingConsoleCommandExecutor(Training training) {
/* 11 */     this.training = training;
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void ballPosition() {
/* 16 */     this.console.log("(" + this.training.ball.x + ", " + this.training.ball.y + ", " + this.training.ball.z + ")");
/*    */   }
/*    */   
/*    */   public void ballPosition(float x, float y, float z) {
/* 20 */     this.training.ball.setPosition(x, y, z);
/*    */   }
/*    */   
/*    */   @HiddenCommand
/*    */   public void ballSpeed() {
/* 25 */     this.console.log("(" + (this.training.ball.v * EMath.cos(this.training.ball.a)) + ", " + (this.training.ball.v * EMath.sin(this.training.ball.a)) + ", " + this.training.ball.vz + ")");
/*    */   }
/*    */   
/*    */   public void ballSpeed(float vx, float vy, float vz) {
/* 29 */     this.training.ball.v = EMath.hypo(vx, vy);
/* 30 */     this.training.ball.a = EMath.aTan2(vy, vx);
/* 31 */     this.training.ball.vz = vz;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\TrainingConsoleCommandExecutor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */