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
/*     */ class TrainingStateReplay
/*     */   extends TrainingState
/*     */ {
/*     */   private int subframe0;
/*     */   private boolean paused;
/*     */   private boolean slowMotion;
/*     */   private boolean keySlow;
/*     */   private boolean keyPause;
/*     */   private int position;
/*     */   private InputDevice inputDevice;
/*     */   
/*     */   TrainingStateReplay(TrainingFsm fsm) {
/*  29 */     super(fsm);
/*     */     
/*  31 */     this.displayControlledPlayer = Settings.showPlayerNumber;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  36 */     super.entryActions();
/*     */     
/*  38 */     this.subframe0 = this.training.subframe;
/*     */     
/*  40 */     this.paused = false;
/*  41 */     this.slowMotion = false;
/*     */ 
/*     */     
/*  44 */     this.keySlow = Gdx.input.isKeyPressed(46);
/*  45 */     this.keyPause = Gdx.input.isKeyPressed(44);
/*     */ 
/*     */     
/*  48 */     this.position = 0;
/*     */     
/*  50 */     this.inputDevice = null;
/*     */   }
/*     */ 
/*     */   
/*     */   void exitActions() {
/*  55 */     this.training.subframe = this.subframe0;
/*     */   }
/*     */   
/*     */   void doActions(float deltaTime) {
/*     */     int speed;
/*  60 */     super.doActions(deltaTime);
/*     */ 
/*     */     
/*  63 */     if (Gdx.input.isKeyPressed(44) && !this.keyPause) {
/*  64 */       this.paused = !this.paused;
/*     */     }
/*  66 */     this.keyPause = Gdx.input.isKeyPressed(44);
/*     */ 
/*     */     
/*  69 */     if (Gdx.input.isKeyPressed(46) && !this.keySlow) {
/*  70 */       this.slowMotion = !this.slowMotion;
/*     */     }
/*  72 */     this.keySlow = Gdx.input.isKeyPressed(46);
/*     */ 
/*     */     
/*  75 */     if (this.inputDevice == null) {
/*  76 */       for (InputDevice d : this.training.game.inputDevices) {
/*  77 */         if (d.fire2Down()) {
/*  78 */           this.inputDevice = d;
/*     */         }
/*     */       }
/*     */     
/*  82 */     } else if (this.inputDevice.fire2Down()) {
/*  83 */       this.inputDevice = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     if (this.inputDevice != null) {
/*  90 */       speed = 12 * this.inputDevice.x1 - 2 * this.inputDevice.y1 + 8 * Math.abs(this.inputDevice.x1) * this.inputDevice.y1;
/*  91 */     } else if (this.slowMotion) {
/*  92 */       speed = 4;
/*     */     } else {
/*  94 */       speed = 8;
/*     */     } 
/*     */ 
/*     */     
/*  98 */     if (!this.paused) {
/*  99 */       this.position = EMath.slide(this.position, 1, 4096, speed);
/*     */       
/* 101 */       this.training.subframe = (this.subframe0 + this.position) % 4096;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SceneFsm.Action[] checkConditions() {
/* 109 */     if (Gdx.input.isKeyPressed(131)) {
/* 110 */       return quitAction();
/*     */     }
/*     */ 
/*     */     
/* 114 */     for (InputDevice d : this.training.game.inputDevices) {
/* 115 */       if (d.fire1Down()) {
/* 116 */         return quitAction();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 121 */     if (this.position == 4096 && this.inputDevice == null) {
/* 122 */       return quitAction();
/*     */     }
/*     */     
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private SceneFsm.Action[] quitAction() {
/* 130 */     if (this.position != 4096) {
/* 131 */       return newFadedAction(SceneFsm.ActionType.RESTORE_FOREGROUND);
/*     */     }
/* 133 */     return newAction(SceneFsm.ActionType.RESTORE_FOREGROUND);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void render() {
/* 139 */     super.render();
/*     */     
/* 141 */     int f = Math.round(1.0F * this.training.subframe / 8.0F) % 32;
/* 142 */     if (f < 16) {
/* 143 */       Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, Assets.gettext("ACTION REPLAY"), 30, 22, Font.Align.LEFT);
/*     */     }
/* 145 */     if (Settings.showDevelopmentInfo) {
/* 146 */       Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, "FRAME: " + (this.training.subframe / 8) + " / " + 'Ȁ', 30, 42, Font.Align.LEFT);
/* 147 */       Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, "SUBFRAME: " + this.training.subframe + " / " + 'က', 30, 62, Font.Align.LEFT);
/*     */     } 
/*     */     
/* 150 */     float a = this.position * 360.0F / 4096.0F;
/*     */     
/* 152 */     this.sceneRenderer.batch.end();
/* 153 */     GLShapeRenderer shapeRenderer = this.sceneRenderer.shapeRenderer;
/* 154 */     shapeRenderer.setProjectionMatrix(this.sceneRenderer.camera.combined);
/* 155 */     shapeRenderer.setAutoShapeType(true);
/* 156 */     shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/* 157 */     shapeRenderer.setColor(2368548, 0.9F);
/* 158 */     shapeRenderer.arc(20.0F, 32.0F, 6.0F, 270.0F + a, 360.0F - a);
/* 159 */     shapeRenderer.setColor(16711680, 0.9F);
/* 160 */     shapeRenderer.arc(18.0F, 30.0F, 6.0F, 270.0F + a, 360.0F - a);
/* 161 */     shapeRenderer.end();
/* 162 */     this.sceneRenderer.batch.begin();
/*     */     
/* 164 */     if (this.inputDevice != null) {
/* 165 */       int frameX = 1 + this.inputDevice.x1;
/* 166 */       int frameY = 1 + this.inputDevice.y1;
/* 167 */       Objects.requireNonNull(this.sceneRenderer); this.sceneRenderer.batch.draw(Assets.replaySpeed[frameX][frameY], (1280 - 50), (this.sceneRenderer.guiHeight - 50));
/*     */     } 
/*     */     
/* 170 */     if (this.paused) {
/* 171 */       Objects.requireNonNull(this.sceneRenderer); Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, Assets.gettext("PAUSE"), 1280 / 2, 22, Font.Align.CENTER);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\TrainingStateReplay.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */