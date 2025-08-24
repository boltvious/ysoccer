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
/*     */ class MatchStateReplay
/*     */   extends MatchState
/*     */ {
/*     */   private int subframe0;
/*     */   private boolean paused;
/*     */   private boolean slowMotion;
/*     */   private boolean keySlow;
/*     */   private boolean keyPause;
/*     */   private int position;
/*     */   private InputDevice inputDevice;
/*     */   
/*     */   MatchStateReplay(MatchFsm fsm) {
/*  29 */     super(fsm);
/*     */     
/*  31 */     this.displayWindVane = true;
/*  32 */     this.displayControlledPlayer = Settings.development;
/*     */     
/*  34 */     this.checkReplayKey = false;
/*  35 */     this.checkPauseKey = false;
/*  36 */     this.checkHelpKey = false;
/*  37 */     this.checkBenchCall = false;
/*     */   }
/*     */ 
/*     */   
/*     */   void entryActions() {
/*  42 */     super.entryActions();
/*     */     
/*  44 */     this.subframe0 = this.match.subframe;
/*     */     
/*  46 */     this.paused = false;
/*  47 */     this.slowMotion = false;
/*     */ 
/*     */     
/*  50 */     this.keySlow = Gdx.input.isKeyPressed(46);
/*  51 */     this.keyPause = Gdx.input.isKeyPressed(44);
/*     */ 
/*     */     
/*  54 */     this.position = 0;
/*     */     
/*  56 */     this.inputDevice = null;
/*     */   }
/*     */ 
/*     */   
/*     */   void exitActions() {
/*  61 */     this.match.subframe = this.subframe0;
/*     */   }
/*     */   
/*     */   void doActions(float deltaTime) {
/*     */     int speed;
/*  66 */     super.doActions(deltaTime);
/*     */ 
/*     */     
/*  69 */     if (this.inputDevice == null && Gdx.input.isKeyPressed(44) && !this.keyPause) {
/*  70 */       this.paused = !this.paused;
/*     */     }
/*  72 */     this.keyPause = Gdx.input.isKeyPressed(44);
/*     */ 
/*     */     
/*  75 */     if (Gdx.input.isKeyPressed(46) && !this.keySlow) {
/*  76 */       this.slowMotion = !this.slowMotion;
/*     */     }
/*  78 */     this.keySlow = Gdx.input.isKeyPressed(46);
/*     */ 
/*     */     
/*  81 */     if (this.inputDevice == null) {
/*  82 */       for (InputDevice d : this.match.game.inputDevices) {
/*  83 */         if (d.fire2Down()) {
/*  84 */           this.inputDevice = d;
/*  85 */           this.paused = false;
/*     */         }
/*     */       
/*     */       } 
/*  89 */     } else if (this.inputDevice.fire2Down()) {
/*  90 */       this.inputDevice = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  96 */     if (this.inputDevice != null) {
/*  97 */       speed = 12 * this.inputDevice.x1 - 2 * this.inputDevice.y1 + 8 * Math.abs(this.inputDevice.x1) * this.inputDevice.y1;
/*  98 */     } else if (this.slowMotion) {
/*  99 */       speed = 4;
/*     */     } else {
/* 101 */       speed = 8;
/*     */     } 
/*     */ 
/*     */     
/* 105 */     if (!this.paused) {
/* 106 */       this.position = EMath.slide(this.position, 1, 4096, speed);
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
/* 118 */         return quitAction();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 123 */     if (this.position == 4096 && this.inputDevice == null) {
/* 124 */       return quitAction();
/*     */     }
/*     */     
/* 127 */     return checkCommonConditions();
/*     */   }
/*     */ 
/*     */   
/*     */   private SceneFsm.Action[] quitAction() {
/* 132 */     if (this.position != 4096) {
/* 133 */       return newFadedAction(SceneFsm.ActionType.RESTORE_FOREGROUND);
/*     */     }
/* 135 */     return newAction(SceneFsm.ActionType.RESTORE_FOREGROUND);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void render() {
/* 141 */     super.render();
/*     */     
/* 143 */     int f = Math.round(1.0F * this.match.subframe / 8.0F) % 32;
/* 144 */     if (f < 16) {
/* 145 */       Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, Assets.gettext("ACTION REPLAY"), 30, 22, Font.Align.LEFT);
/*     */     }
/* 147 */     if (Settings.showDevelopmentInfo) {
/* 148 */       Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, "FRAME: " + (this.match.subframe / 8) + " / " + 'Ȁ', 30, 42, Font.Align.LEFT);
/* 149 */       Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, "SUBFRAME: " + this.match.subframe + " / " + 'က', 30, 62, Font.Align.LEFT);
/*     */     } 
/*     */     
/* 152 */     float a = this.position * 360.0F / 4096.0F;
/*     */     
/* 154 */     this.sceneRenderer.batch.end();
/* 155 */     GLShapeRenderer shapeRenderer = this.sceneRenderer.shapeRenderer;
/* 156 */     shapeRenderer.setProjectionMatrix(this.sceneRenderer.camera.combined);
/* 157 */     shapeRenderer.setAutoShapeType(true);
/* 158 */     shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/* 159 */     shapeRenderer.setColor(2368548, 0.9F);
/* 160 */     shapeRenderer.arc(20.0F, 32.0F, 6.0F, 270.0F + a, 360.0F - a);
/* 161 */     shapeRenderer.setColor(16711680, 0.9F);
/* 162 */     shapeRenderer.arc(18.0F, 30.0F, 6.0F, 270.0F + a, 360.0F - a);
/* 163 */     shapeRenderer.end();
/* 164 */     this.sceneRenderer.batch.begin();
/*     */     
/* 166 */     if (this.inputDevice != null) {
/* 167 */       int frameX = 1 + this.inputDevice.x1;
/* 168 */       int frameY = 1 + this.inputDevice.y1;
/* 169 */       Objects.requireNonNull(this.sceneRenderer); this.sceneRenderer.batch.draw(Assets.replaySpeed[frameX][frameY], (1280 - 50), (this.sceneRenderer.guiHeight - 50));
/*     */     } 
/*     */     
/* 172 */     if (this.paused) {
/* 173 */       Objects.requireNonNull(this.sceneRenderer); Assets.font10.draw((SpriteBatch)this.sceneRenderer.batch, Assets.gettext("PAUSE"), 1280 / 2, 22, Font.Align.CENTER);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchStateReplay.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */