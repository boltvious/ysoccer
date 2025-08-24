/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class MatchStateKeeperStop
/*    */   extends MatchState
/*    */ {
/*    */   private Player keeper;
/*    */   private Team keeperTeam;
/*    */   private Team opponentTeam;
/*    */   
/*    */   MatchStateKeeperStop(MatchFsm fsm) {
/* 20 */     super(fsm);
/*    */     
/* 22 */     this.displayControlledPlayer = true;
/* 23 */     this.displayBallOwner = true;
/* 24 */     this.displayTime = true;
/* 25 */     this.displayWindVane = true;
/* 26 */     this.displayRadar = true;
/*    */     
/* 28 */     this.checkBenchCall = false;
/*    */   }
/*    */ 
/*    */   
/*    */   void entryActions() {
/* 33 */     super.entryActions();
/*    */     
/* 35 */     this.keeper = this.match.ball.holder;
/* 36 */     this.keeperTeam = this.match.team[this.keeper.team.index];
/* 37 */     this.opponentTeam = this.match.team[1 - this.keeper.team.index];
/*    */     
/* 39 */     (this.match.stats[this.opponentTeam.index]).overallShots++;
/* 40 */     (this.match.stats[this.opponentTeam.index]).centeredShots++;
/*    */     
/* 42 */     this.keeperTeam.assignAutomaticInputDevices(this.keeper);
/* 43 */     this.opponentTeam.assignAutomaticInputDevices(null);
/*    */     
/* 45 */     this.keeperTeam.setPlayersState(PlayerFsm.Id.STATE_REACH_TARGET, this.keeper);
/* 46 */     this.opponentTeam.setPlayersState(PlayerFsm.Id.STATE_REACH_TARGET, null);
/*    */     
/* 48 */     this.keeperTeam.updateTactics(true);
/* 49 */     this.opponentTeam.updateTactics(true);
/*    */     
/* 51 */     this.match.setPointOfInterest(this.keeper.x, this.keeper.y);
/*    */   }
/*    */ 
/*    */   
/*    */   void onResume() {
/* 56 */     super.onResume();
/*    */     
/* 58 */     this.sceneRenderer.actionCamera
/* 59 */       .setMode(ActionCamera.Mode.FOLLOW_BALL)
/* 60 */       .setSpeed(ActionCamera.Speed.NORMAL);
/*    */   }
/*    */ 
/*    */   
/*    */   void doActions(float deltaTime) {
/* 65 */     super.doActions(deltaTime);
/*    */     
/* 67 */     float timeLeft = deltaTime;
/*    */     
/* 69 */     while (timeLeft > 0.001953125F) {
/*    */       
/* 71 */       if (this.match.subframe % 8 == 0) {
/* 72 */         this.match.ball.updatePrediction();
/* 73 */         this.match.updateFrameDistance();
/* 74 */         this.match.updateAi();
/*    */       } 
/*    */       
/* 77 */       this.match.updateBall();
/* 78 */       this.match.updatePlayers(true);
/* 79 */       this.match.findNearest();
/*    */       
/* 81 */       this.match.nextSubframe();
/*    */       
/* 83 */       this.sceneRenderer.save();
/*    */       
/* 85 */       this.sceneRenderer.actionCamera.update();
/*    */       
/* 87 */       timeLeft -= 0.001953125F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   SceneFsm.Action[] checkConditions() {
/* 93 */     if (this.keeper.checkState(PlayerFsm.Id.STATE_STAND_RUN) || this.keeper.checkState(PlayerFsm.Id.STATE_KEEPER_POSITIONING)) {
/* 94 */       this.keeperTeam.setPlayersState(PlayerFsm.Id.STATE_STAND_RUN, this.keeper);
/* 95 */       this.opponentTeam.setPlayersState(PlayerFsm.Id.STATE_STAND_RUN, null);
/* 96 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_MAIN);
/*    */     } 
/*    */     
/* 99 */     return checkCommonConditions();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateKeeperStop.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */