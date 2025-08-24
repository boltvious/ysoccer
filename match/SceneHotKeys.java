/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ import java.util.TreeMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class SceneHotKeys
/*     */ {
/*     */   final Scene scene;
/*     */   String message;
/*     */   long messageTimer;
/*     */   private boolean keySoundDown;
/*     */   private boolean keySoundUp;
/*     */   private boolean keyScreenMode;
/*     */   private boolean keyZoomIn;
/*     */   private boolean keyZoomOut;
/*     */   private boolean keyDevelopmentInfo;
/*  35 */   final TreeMap<Integer, String[]> keyMap = (TreeMap)new TreeMap<>();
/*     */   
/*     */   SceneHotKeys(Scene scene) {
/*  38 */     this.scene = scene;
/*     */     
/*  40 */     String[] keySoundInfo = { InputDevice.keyDescription(245) + "-" + InputDevice.keyDescription(246), Assets.gettext("HELP.SOUND FX VOLUME") };
/*  41 */     this.keyMap.put(Integer.valueOf(2), keySoundInfo);
/*     */     
/*  43 */     String[] keyScreenModeInfo = { InputDevice.keyDescription(249), Assets.gettext("HELP.WINDOW / FULL SCREEN") };
/*  44 */     this.keyMap.put(Integer.valueOf(7), keyScreenModeInfo);
/*     */     
/*  46 */     String[] keyZoomInfo = { InputDevice.keyDescription(250) + "-" + InputDevice.keyDescription(251), Assets.gettext("HELP.ZOOM IN / OUT") };
/*  47 */     this.keyMap.put(Integer.valueOf(8), keyZoomInfo);
/*     */     
/*  49 */     String[] replay = { InputDevice.keyDescription(46), Assets.gettext("ACTION REPLAY") };
/*  50 */     this.keyMap.put(Integer.valueOf(15), replay);
/*     */     
/*  52 */     String[] escape = { InputDevice.keyDescription(131), Assets.gettext("HELP.QUIT") };
/*  53 */     this.keyMap.put(Integer.valueOf(16), escape);
/*     */   }
/*     */   
/*     */   public void update() {
/*  57 */     if (this.messageTimer > 0L) this.messageTimer--;
/*     */     
/*  59 */     if (Gdx.input.isKeyPressed(245) && !this.keySoundDown) {
/*  60 */       Assets.Sounds.volume = Math.max(0, Assets.Sounds.volume - 10);
/*     */       
/*  62 */       onChangeVolume();
/*     */       
/*  64 */       setMessageSoundEffects();
/*  65 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/*  68 */     if (Gdx.input.isKeyPressed(246) && !this.keySoundUp) {
/*  69 */       Assets.Sounds.volume = Math.min(100, Assets.Sounds.volume + 10);
/*     */       
/*  71 */       onChangeVolume();
/*     */       
/*  73 */       setMessageSoundEffects();
/*  74 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/*  77 */     if (Gdx.input.isKeyPressed(249) && !this.keyScreenMode) {
/*  78 */       this.scene.settings.fullScreen = !this.scene.settings.fullScreen;
/*  79 */       this.scene.game.setScreenMode(this.scene.settings.fullScreen);
/*     */       
/*  81 */       if (this.scene.settings.fullScreen) {
/*  82 */         this.message = Assets.gettext("SCREEN MODE.FULL SCREEN");
/*     */       } else {
/*  84 */         this.message = Assets.gettext("SCREEN MODE.WINDOW");
/*     */       } 
/*     */       
/*  87 */       this.messageTimer = 120L;
/*     */     } 
/*     */     
/*  90 */     if (Gdx.input.isKeyPressed(250) && !this.keyZoomIn) {
/*  91 */       this.scene.settings.zoom = EMath.slide(this.scene.settings.zoom, SceneRenderer.zoomMin(), SceneRenderer.zoomMax(), 5);
/*  92 */       this.scene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
/*     */       
/*  94 */       this.message = Assets.gettext("ZOOM") + " " + this.scene.settings.zoom + "%";
/*     */       
/*  96 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/*  99 */     if (Gdx.input.isKeyPressed(251) && !this.keyZoomOut) {
/* 100 */       this.scene.settings.zoom = EMath.slide(this.scene.settings.zoom, SceneRenderer.zoomMin(), SceneRenderer.zoomMax(), -5);
/* 101 */       this.scene.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
/*     */       
/* 103 */       this.message = Assets.gettext("ZOOM") + " " + this.scene.settings.zoom + "%";
/*     */       
/* 105 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/* 108 */     if (Settings.development && Gdx.input.isKeyPressed(255) && !this.keyDevelopmentInfo) {
/* 109 */       Settings.showDevelopmentInfo = !Settings.showDevelopmentInfo;
/*     */       
/* 111 */       this.message = "DEVELOPMENT INFO " + (Settings.showDevelopmentInfo ? "ON" : "OFF");
/*     */       
/* 113 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/* 116 */     this.keySoundDown = Gdx.input.isKeyPressed(245);
/* 117 */     this.keySoundUp = Gdx.input.isKeyPressed(246);
/* 118 */     this.keyScreenMode = Gdx.input.isKeyPressed(249);
/* 119 */     this.keyZoomIn = Gdx.input.isKeyPressed(250);
/* 120 */     this.keyZoomOut = Gdx.input.isKeyPressed(251);
/* 121 */     this.keyDevelopmentInfo = Gdx.input.isKeyPressed(255);
/*     */   }
/*     */ 
/*     */   
/*     */   void onChangeVolume() {}
/*     */   
/*     */   private void setMessageSoundEffects() {
/* 128 */     StringBuilder sb = new StringBuilder(Assets.gettext("MATCH OPTIONS.SOUND VOLUME"));
/* 129 */     sb.append(" <");
/* 130 */     for (int i = 10; i <= 100; i += 10) {
/* 131 */       sb.append((i <= Assets.Sounds.volume) ? "|" : " ");
/*     */     }
/* 133 */     sb.append(">");
/* 134 */     this.message = sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\SceneHotKeys.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */