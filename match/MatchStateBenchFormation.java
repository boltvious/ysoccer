/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.audio.Sound;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchStateBenchFormation
/*     */   extends MatchState
/*     */ {
/*     */   MatchStateBenchFormation(MatchFsm fsm) {
/*  21 */     super(fsm);
/*     */     
/*  23 */     this.checkReplayKey = false;
/*  24 */     this.checkPauseKey = false;
/*  25 */     this.checkHelpKey = false;
/*  26 */     this.checkBenchCall = false;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  31 */     super.entryActions();
/*  32 */     this.displayBenchFormation = true;
/*  33 */     this.sceneRenderer.actionCamera.setMode(ActionCamera.Mode.STILL);
/*     */   }
/*     */ 
/*     */   
/*     */   void doActions(float deltaTime) {
/*  38 */     super.doActions(deltaTime);
/*     */     
/*  40 */     float timeLeft = deltaTime;
/*  41 */     while (timeLeft >= 0.001953125F) {
/*     */       
/*  43 */       this.match.updateBall();
/*  44 */       this.match.ball.inFieldKeep();
/*     */       
/*  46 */       this.match.updatePlayers(true);
/*     */       
/*  48 */       this.match.updateCoaches();
/*     */       
/*  50 */       this.match.nextSubframe();
/*     */       
/*  52 */       this.sceneRenderer.save();
/*     */       
/*  54 */       this.sceneRenderer.actionCamera.update();
/*     */       
/*  56 */       timeLeft -= 0.001953125F;
/*     */     } 
/*     */ 
/*     */     
/*  60 */     if ((getFsm()).benchStatus.inputDevice.yMoved()) {
/*  61 */       (getFsm()).benchStatus.selectedPosition = EMath.rotate((getFsm()).benchStatus.selectedPosition, -1, 10, (getFsm()).benchStatus.inputDevice.y1);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/*  68 */     if ((getFsm()).benchStatus.inputDevice.fire1Down()) {
/*     */ 
/*     */       
/*  71 */       if ((getFsm()).benchStatus.selectedPosition == -1) {
/*     */ 
/*     */         
/*  74 */         (getFsm()).benchStatus.swapPosition = -1;
/*  75 */         if ((getFsm()).benchStatus.substPosition != -1) {
/*  76 */           Player player = (getFsm()).benchStatus.team.lineup.get((getFsm()).benchStatus.substPosition);
/*  77 */           player.setState(PlayerFsm.Id.STATE_BENCH_STANDING);
/*     */           
/*  79 */           (getFsm()).benchStatus.selectedPosition = (getFsm()).benchStatus.substPosition - 11;
/*  80 */           (getFsm()).benchStatus.substPosition = -1;
/*     */         } 
/*     */         
/*  83 */         return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_BENCH_TACTICS);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       if ((getFsm()).benchStatus.swapPosition == (getFsm()).benchStatus.selectedPosition) {
/*  90 */         (getFsm()).benchStatus.swapPosition = -1;
/*     */ 
/*     */       
/*     */       }
/*  94 */       else if ((getFsm()).benchStatus.swapPosition == -1 && (getFsm()).benchStatus.substPosition == -1) {
/*  95 */         (getFsm()).benchStatus.swapPosition = (getFsm()).benchStatus.selectedPosition;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 100 */         int i2, i1 = (getFsm()).benchStatus.team.playerIndexAtPosition((getFsm()).benchStatus.selectedPosition);
/*     */ 
/*     */ 
/*     */         
/* 104 */         Coach coach = (getFsm()).benchStatus.team.coach;
/* 105 */         if ((getFsm()).benchStatus.substPosition != -1) {
/* 106 */           coach.status = Coach.Status.CALL;
/* 107 */           coach.timer = 500;
/*     */           
/* 109 */           (getFsm()).benchStatus.team.substitutionsCount++;
/*     */           
/* 111 */           i2 = (getFsm()).benchStatus.team.playerIndexAtPosition((getFsm()).benchStatus.substPosition);
/* 112 */           ((Player)(getFsm()).benchStatus.team.lineup.get(i1)).setState(PlayerFsm.Id.STATE_OUTSIDE);
/* 113 */           ((Player)(getFsm()).benchStatus.team.lineup.get(i2)).setState(PlayerFsm.Id.STATE_REACH_TARGET);
/* 114 */           (getFsm()).benchStatus.substPosition = -1;
/*     */           
/* 116 */           if (this.match.settings.commentary) {
/* 117 */             int size = Assets.Commentary.playerSubstitution.size();
/* 118 */             if (size > 0) {
/* 119 */               ((Sound)Assets.Commentary.playerSubstitution.get(Assets.random.nextInt(size))).play(Assets.Sounds.volume / 100.0F);
/*     */             }
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 126 */           coach.status = Coach.Status.SWAP;
/* 127 */           coach.timer = 500;
/*     */           
/* 129 */           i2 = (getFsm()).benchStatus.team.playerIndexAtPosition((getFsm()).benchStatus.swapPosition);
/* 130 */           ((Player)(getFsm()).benchStatus.team.lineup.get(i1)).setState(PlayerFsm.Id.STATE_REACH_TARGET);
/* 131 */           ((Player)(getFsm()).benchStatus.team.lineup.get(i2)).setState(PlayerFsm.Id.STATE_REACH_TARGET);
/* 132 */           (getFsm()).benchStatus.swapPosition = -1;
/*     */           
/* 134 */           if (this.match.settings.commentary) {
/* 135 */             int size = Assets.Commentary.playerSwap.size();
/* 136 */             if (size > 0) {
/* 137 */               ((Sound)Assets.Commentary.playerSwap.get(Assets.random.nextInt(size))).play(Assets.Sounds.volume / 100.0F);
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 143 */         Collections.swap((getFsm()).benchStatus.team.lineup, i1, i2);
/*     */ 
/*     */         
/* 146 */         Player ply1 = (getFsm()).benchStatus.team.lineup.get(i1);
/* 147 */         Player ply2 = (getFsm()).benchStatus.team.lineup.get(i2);
/*     */         
/* 149 */         float tx = ply1.tx;
/* 150 */         ply1.tx = ply2.tx;
/* 151 */         ply2.tx = tx;
/*     */         
/* 153 */         float ty = ply1.ty;
/* 154 */         ply1.ty = ply2.ty;
/* 155 */         ply2.ty = ty;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if ((getFsm()).benchStatus.inputDevice.xReleased()) {
/* 162 */       (getFsm()).benchStatus.selectedPosition = -1;
/*     */ 
/*     */       
/* 165 */       (getFsm()).benchStatus.swapPosition = -1;
/* 166 */       if ((getFsm()).benchStatus.substPosition != -1) {
/* 167 */         Player player = (getFsm()).benchStatus.team.lineup.get((getFsm()).benchStatus.substPosition);
/* 168 */         player.setState(PlayerFsm.Id.STATE_BENCH_STANDING);
/*     */         
/* 170 */         (getFsm()).benchStatus.selectedPosition = (getFsm()).benchStatus.substPosition - 11;
/* 171 */         (getFsm()).benchStatus.substPosition = -1;
/*     */       } 
/*     */       
/* 174 */       return newAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_BENCH_SUBSTITUTIONS);
/*     */     } 
/*     */     
/* 177 */     return checkCommonConditions();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateBenchFormation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */