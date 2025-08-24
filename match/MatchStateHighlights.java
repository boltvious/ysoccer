/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLShapeRenderer;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchStateHighlights
/*     */   extends MatchState
/*     */ {
/*     */   private int subframe0;
/*     */   private boolean paused;
/*     */   private boolean showCurrentRecord;
/*     */   private boolean slowMotion;
/*     */   private boolean keySlow;
/*     */   private boolean keyPause;
/*     */   private int position;
/*     */   private InputDevice inputDevice;
/*     */   
/*     */   MatchStateHighlights(MatchFsm fsm) {
/*  32 */     super(fsm);
/*     */     
/*  34 */     this.displayWindVane = true;
/*     */     
/*  36 */     this.checkReplayKey = false;
/*  37 */     this.checkPauseKey = false;
/*  38 */     this.checkHelpKey = false;
/*  39 */     this.checkBenchCall = false;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  44 */     super.entryActions();
/*     */ 
/*     */     
/*  47 */     this.subframe0 = this.match.subframe;
/*     */     
/*  49 */     this.paused = false;
/*  50 */     this.showCurrentRecord = true;
/*  51 */     this.slowMotion = false;
/*     */ 
/*     */     
/*  54 */     this.keySlow = Gdx.input.isKeyPressed(46);
/*  55 */     this.keyPause = Gdx.input.isKeyPressed(44);
/*     */ 
/*     */     
/*  58 */     this.position = 0;
/*     */     
/*  60 */     this.inputDevice = null;
/*     */     
/*  62 */     this.match.recorder.loadHighlight(this.sceneRenderer);
/*     */   }
/*     */   
/*     */   void doActions(float deltaTime) {
/*     */     int speed;
/*  67 */     super.doActions(deltaTime);
/*     */ 
/*     */     
/*  70 */     if (Gdx.input.isKeyPressed(44) && !this.keyPause) {
/*  71 */       this.paused = !this.paused;
/*     */     }
/*  73 */     this.keyPause = Gdx.input.isKeyPressed(44);
/*     */ 
/*     */     
/*  76 */     if (Gdx.input.isKeyPressed(46) && !this.keySlow) {
/*  77 */       this.slowMotion = !this.slowMotion;
/*     */     }
/*  79 */     this.keySlow = Gdx.input.isKeyPressed(46);
/*     */ 
/*     */     
/*  82 */     if (this.inputDevice == null) {
/*  83 */       for (InputDevice d : this.match.game.inputDevices) {
/*  84 */         if (d.fire2Down()) {
/*  85 */           this.inputDevice = d;
/*     */         }
/*     */       }
/*     */     
/*  89 */     } else if (this.inputDevice.fire2Down()) {
/*  90 */       this.inputDevice = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (this.inputDevice != null) {
/*  97 */       speed = 12 * this.inputDevice.x1 - 4 * this.inputDevice.y1 + 8 * Math.abs(this.inputDevice.x1) * this.inputDevice.y1;
/*  98 */     } else if (this.slowMotion) {
/*  99 */       speed = 4;
/*     */     } else {
/* 101 */       speed = 8;
/*     */     } 
/*     */ 
/*     */     
/* 105 */     if (!this.paused) {
/* 106 */       this.position = EMath.slide(this.position, 4, 4096, speed);
/*     */       
/* 108 */       this.match.subframe = (this.subframe0 + this.position) % 4096;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 116 */     for (InputDevice d : this.match.game.inputDevices) {
/* 117 */       if (d.fire1Down()) {
/* 118 */         this.showCurrentRecord = false;
/* 119 */         return newFadedAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_END);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 124 */     if (this.position == 4096) {
/* 125 */       this.match.recorder.nextHighlight();
/* 126 */       this.showCurrentRecord = false;
/* 127 */       if (this.match.recorder.hasEnded()) {
/* 128 */         return newFadedAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_END);
/*     */       }
/* 130 */       return newFadedAction(SceneFsm.ActionType.NEW_FOREGROUND, MatchFsm.STATE_HIGHLIGHTS);
/*     */     } 
/*     */ 
/*     */     
/* 134 */     return checkCommonConditions();
/*     */   }
/*     */ 
/*     */   
/*     */   void render() {
/* 139 */     super.render();
/*     */     
/* 141 */     int f = Math.round(1.0F * this.match.subframe / 8.0F) % 32;
/* 142 */     if (this.showCurrentRecord && f < 16) {
/* 143 */       Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, (this.match.recorder.getCurrent() + 1) + "/" + this.match.recorder.getRecorded(), 30, 22, Font.Align.LEFT);
/*     */     }
/* 145 */     if (Settings.showDevelopmentInfo) {
/* 146 */       Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, "FRAME: " + (this.match.subframe / 8) + " / " + 'Ȁ', 30, 42, Font.Align.LEFT);
/* 147 */       Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, "SUBFRAME: " + this.match.subframe + " / " + 'က', 30, 62, Font.Align.LEFT);
/*     */     } 
/*     */     
/* 150 */     this.sceneRenderer.batch.end();
/* 151 */     float a = this.position * 360.0F / 4096.0F;
/* 152 */     GLShapeRenderer shapeRenderer = this.sceneRenderer.shapeRenderer;
/* 153 */     shapeRenderer.setProjectionMatrix(this.sceneRenderer.camera.combined);
/* 154 */     shapeRenderer.setAutoShapeType(true);
/* 155 */     shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/* 156 */     shapeRenderer.setColor(2368548, 0.9F);
/* 157 */     shapeRenderer.arc(20.0F, 32.0F, 6.0F, 270.0F + a, 360.0F - a);
/* 158 */     shapeRenderer.setColor(16711680, 0.9F);
/* 159 */     shapeRenderer.arc(18.0F, 30.0F, 6.0F, 270.0F + a, 360.0F - a);
/* 160 */     shapeRenderer.end();
/* 161 */     this.sceneRenderer.batch.begin();
/*     */     
/* 163 */     if (this.inputDevice != null) {
/* 164 */       int frameX = 1 + this.inputDevice.x1;
/* 165 */       int frameY = 1 + this.inputDevice.y1;
/* 166 */       Objects.requireNonNull(this.sceneRenderer); this.sceneRenderer.batch.draw(Assets.replaySpeed[frameX][frameY], (1280 - 50), (this.sceneRenderer.guiHeight - 50));
/*     */     } 
/*     */     
/* 169 */     if (this.paused) {
/* 170 */       Objects.requireNonNull(this.sceneRenderer); Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, Assets.gettext("PAUSE"), 1280 / 2, 22, Font.Align.CENTER);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateHighlights.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */