/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStateIntro
/*    */   extends MatchState
/*    */ {
/* 15 */   private final int enterDelay = 4;
/*    */   private boolean stillCamera;
/*    */   
/*    */   MatchStateIntro(MatchFsm fsm) {
/* 19 */     super(fsm);
/*    */     
/* 21 */     this.displayRosters = true;
/*    */     
/* 23 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 28 */     super.entryActions();
/*    */     
/* 30 */     this.stillCamera = true;
/* 31 */     this.match.clock = 0.0F;
/* 32 */     (getFsm()).matchCompleted = false;
/* 33 */     this.match.setIntroPositions();
/* 34 */     this.match.resetData();
/*    */     
/* 36 */     Assets.Sounds.introId = Long.valueOf(Assets.Sounds.intro.play(Assets.Sounds.volume / 100.0F));
/* 37 */     Assets.Sounds.crowdId = Long.valueOf(Assets.Sounds.crowd.play(Assets.Sounds.volume / 100.0F));
/* 38 */     Assets.Sounds.crowd.setLooping(Assets.Sounds.crowdId.longValue(), true);
/*    */   }
/*    */ 
/*    */   
/*    */   void onResume() {
/* 43 */     super.onResume();
/*    */     
/* 45 */     setCameraMode();
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 50 */     super.doActions(deltaTime);
/*    */     
/* 52 */     this.match.enterPlayers(this.timer - 1, 4);
/*    */     
/* 54 */     float timeLeft = deltaTime;
/* 55 */     while (timeLeft >= 0.001953125F) {
/*    */       
/* 57 */       this.match.updatePlayers(false);
/* 58 */       this.match.playersPhoto();
/*    */       
/* 60 */       this.match.nextSubframe();
/*    */       
/* 62 */       this.sceneRenderer.save();
/*    */       
/* 64 */       if (this.stillCamera && this.timer > 64) {
/* 65 */         this.stillCamera = false;
/* 66 */         setCameraMode();
/*    */       } 
/* 68 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 70 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void setCameraMode() {
/* 75 */     this.sceneRenderer.actionCamera.setMode(this.stillCamera ? ActionCamera.Mode.STILL : ActionCamera.Mode.FOLLOW_BALL);
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 80 */     if (this.match.enterPlayersFinished(this.timer, 4) && (
/* 81 */       this.match.team[0].fire1Down() != null || this.match.team[1]
/* 82 */       .fire1Down() != null || this.timer >= 320))
/*    */     {
/* 84 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_STARTING_POSITIONS);
/*    */     }
/*    */ 
/*    */     
/* 88 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateIntro.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */