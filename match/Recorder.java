/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Recorder
/*     */ {
/*     */   private static final int MAX_RECORDS = 12;
/*     */   private final Match match;
/*  15 */   private final ArrayList<short[]> highlights = (ArrayList)new ArrayList<>();
/*     */   private int current;
/*     */   private int recorded;
/*     */   
/*     */   Recorder(Match match) {
/*  20 */     this.match = match;
/*     */   }
/*     */   
/*     */   int getCurrent() {
/*  24 */     return this.current;
/*     */   }
/*     */   
/*     */   int getRecorded() {
/*  28 */     return Math.min(this.recorded, 12);
/*     */   }
/*     */ 
/*     */   
/*     */   void saveHighlight(SceneRenderer sceneRenderer) {
/*  33 */     int recordSize = (4 + 10 * ((this.match.team[0]).lineup.size() + (this.match.team[1]).lineup.size()) + 2) * 2 * 4096;
/*     */     
/*  35 */     short[] record = new short[recordSize];
/*     */     
/*  37 */     int index = 0;
/*     */     
/*  39 */     for (int i = 1; i <= 8192; i++) {
/*     */ 
/*     */       
/*  42 */       Data ballData = this.match.ball.data[this.match.subframe];
/*  43 */       record[index++] = (short)ballData.x;
/*  44 */       record[index++] = (short)ballData.y;
/*  45 */       record[index++] = (short)ballData.z;
/*  46 */       record[index++] = (short)ballData.fmx;
/*     */ 
/*     */       
/*  49 */       for (int t = 0; t <= 1; t++) {
/*  50 */         for (Player player : (this.match.team[t]).lineup) {
/*  51 */           Data playerData = player.data[this.match.subframe];
/*  52 */           record[index++] = (short)playerData.x;
/*  53 */           record[index++] = (short)playerData.y;
/*  54 */           record[index++] = (short)playerData.fmx;
/*  55 */           record[index++] = (short)playerData.fmy;
/*  56 */           record[index++] = (short)(playerData.isVisible ? 1 : 0);
/*  57 */           record[index++] = (short)(playerData.isHumanControlled ? 1 : 0);
/*  58 */           record[index++] = (short)(playerData.isBestDefender ? 1 : 0);
/*  59 */           record[index++] = (short)playerData.frameDistance;
/*  60 */           record[index++] = (short)playerData.playerState;
/*  61 */           record[index++] = (short)playerData.playerAiState;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  66 */       record[index++] = (short)sceneRenderer.vCameraX[this.match.subframe];
/*  67 */       record[index++] = (short)sceneRenderer.vCameraY[this.match.subframe];
/*     */       
/*  69 */       this.match.subframe = (this.match.subframe + 4) % 4096;
/*     */     } 
/*     */ 
/*     */     
/*  73 */     if (this.recorded < 12) {
/*  74 */       this.highlights.add(record);
/*     */     } else {
/*  76 */       this.highlights.set(this.recorded % 12, record);
/*     */     } 
/*     */     
/*  79 */     this.recorded++;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void loadHighlight(SceneRenderer sceneRenderer) {
/*  85 */     int index = this.current;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (this.recorded > 12) {
/*  91 */       index = (this.recorded + this.current) % 12;
/*     */     }
/*     */ 
/*     */     
/*  95 */     if (index == 12) {
/*  96 */       index = 0;
/*     */     }
/*     */     
/*  99 */     short[] record = this.highlights.get(index);
/*     */     
/* 101 */     int offset = 0;
/*     */     
/* 103 */     for (int j = 1; j <= 8192; j++) {
/*     */ 
/*     */       
/* 106 */       Data ballData = this.match.ball.data[this.match.subframe];
/* 107 */       ballData.x = record[offset++];
/* 108 */       ballData.y = record[offset++];
/* 109 */       ballData.z = record[offset++];
/* 110 */       ballData.fmx = record[offset++];
/*     */ 
/*     */       
/* 113 */       for (int t = 0; t <= 1; t++) {
/* 114 */         for (Player player : (this.match.team[t]).lineup) {
/* 115 */           Data playerData = player.data[this.match.subframe];
/* 116 */           playerData.x = record[offset++];
/* 117 */           playerData.y = record[offset++];
/* 118 */           playerData.fmx = record[offset++];
/* 119 */           playerData.fmy = record[offset++];
/* 120 */           playerData.isVisible = (record[offset++] == 1);
/* 121 */           playerData.isHumanControlled = (record[offset++] == 1);
/* 122 */           playerData.isBestDefender = (record[offset++] == 1);
/* 123 */           playerData.frameDistance = record[offset++];
/* 124 */           playerData.playerState = record[offset++];
/* 125 */           playerData.playerAiState = record[offset++];
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 130 */       sceneRenderer.vCameraX[this.match.subframe] = record[offset++];
/* 131 */       sceneRenderer.vCameraY[this.match.subframe] = record[offset++];
/*     */       
/* 133 */       this.match.subframe = (this.match.subframe + 4) % 4096;
/*     */     } 
/*     */   }
/*     */   
/*     */   void nextHighlight() {
/* 138 */     this.current++;
/*     */   }
/*     */   
/*     */   boolean hasEnded() {
/* 142 */     return (this.current == Math.min(this.recorded, 12));
/*     */   }
/*     */   
/*     */   void restart() {
/* 146 */     this.current = 0;
/*     */   }
/*     */   
/*     */   boolean hasHighlights() {
/* 150 */     return (this.recorded > 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Recorder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */