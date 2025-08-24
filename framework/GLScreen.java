/*     */ package com.ygames.ysoccer.framework;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Camera;
/*     */ import com.badlogic.gdx.graphics.OrthographicCamera;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ 
/*     */ public abstract class GLScreen
/*     */   implements Screen
/*     */ {
/*     */   protected final GLGame game;
/*     */   protected final OrthographicCamera camera;
/*     */   protected final GLSpriteBatch batch;
/*     */   protected final GLShapeRenderer shapeRenderer;
/*     */   protected Texture background;
/*     */   protected final List<Widget> widgets;
/*     */   private Widget selectedWidget;
/*     */   protected Widget.Event widgetEvent;
/*     */   protected boolean usesMouse;
/*     */   protected boolean playMenuMusic;
/*     */   protected InputDevice lastFireInputDevice;
/*     */   
/*     */   protected static class Navigation {
/*     */     public FileHandle folder;
/*     */     public String league;
/*     */     public Team team;
/*     */     public Competition competition;
/*     */     private Player clipboardPlayer;
/*     */     
/*     */     public Player getClipboardPlayer() {
/*  42 */       return this.clipboardPlayer;
/*     */     }
/*     */     
/*     */     public void setClipboardPlayer(Player player) {
/*  46 */       if (player != null) {
/*  47 */         if (this.clipboardPlayer == null) {
/*  48 */           this.clipboardPlayer = new Player();
/*  49 */           this.clipboardPlayer.skills = new Player.Skills();
/*     */         } 
/*  51 */         this.clipboardPlayer.copyFrom(player);
/*     */       } else {
/*  53 */         this.clipboardPlayer = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*  58 */   protected static final Navigation navigation = new Navigation();
/*     */   
/*     */   public GLScreen(GLGame game) {
/*  61 */     this.game = game;
/*  62 */     this.camera = game.glGraphics.camera;
/*  63 */     this.batch = game.glGraphics.batch;
/*  64 */     this.shapeRenderer = game.glGraphics.shapeRenderer;
/*     */     
/*  66 */     this.widgets = new ArrayList<>();
/*  67 */     this.usesMouse = true;
/*  68 */     this.playMenuMusic = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(float delta) {
/*  74 */     this.game.menuMusic.update(this.playMenuMusic ? this.game.settings.musicVolume : 0);
/*     */     
/*  76 */     this.camera.setToOrtho(true, this.game.gui.screenWidth, this.game.gui.screenHeight);
/*  77 */     this.camera.translate(-this.game.gui.originX, -this.game.gui.originY);
/*  78 */     this.camera.update();
/*     */     
/*  80 */     if (this.game.mouse.enabled) {
/*  81 */       this.game.mouse.read((Camera)this.camera);
/*     */       
/*  83 */       int j = this.widgets.size();
/*  84 */       for (int k = 0; k < j; k++) {
/*  85 */         Widget widget = this.widgets.get(k);
/*  86 */         if (widget.contains(this.game.mouse.position.x, this.game.mouse.position.y)) {
/*  87 */           setSelectedWidget(widget);
/*     */         }
/*     */       } 
/*  90 */     } else if (this.usesMouse && this.game.mouse.isActioned()) {
/*  91 */       this.game.mouse.enable();
/*     */     } 
/*     */     
/*  94 */     for (InputDevice inputDevice : this.game.inputDevices) {
/*  95 */       inputDevice.update();
/*     */     }
/*     */     
/*  98 */     this.game.menuInput.read(this);
/*     */     
/* 100 */     int len = this.widgets.size(); int i;
/* 101 */     for (i = 0; i < len; i++) {
/* 102 */       Widget widget = this.widgets.get(i);
/* 103 */       if (widget.getDirty()) {
/* 104 */         widget.refresh();
/*     */       }
/* 106 */       widget.setDirty(false);
/*     */     } 
/*     */     
/* 109 */     this.widgetEvent = this.game.menuInput.getWidgetEvent();
/*     */     
/* 111 */     if (this.widgetEvent != null && this.selectedWidget != null) {
/* 112 */       this.selectedWidget.fireEvent(this.widgetEvent);
/*     */     }
/*     */     
/* 115 */     this.batch.setProjectionMatrix(this.camera.combined);
/* 116 */     this.batch.setColor(16777215, 1.0F);
/*     */     
/* 118 */     this.shapeRenderer.setProjectionMatrix(this.camera.combined);
/* 119 */     this.shapeRenderer.setColor(16777215, 1.0F);
/*     */     
/* 121 */     Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
/* 122 */     Gdx.gl.glClear(16384);
/*     */ 
/*     */     
/* 125 */     if (this.background != null) {
/* 126 */       this.batch.disableBlending();
/* 127 */       this.batch.begin();
/* 128 */       Objects.requireNonNull(this.game.gui); Objects.requireNonNull(this.game.gui); this.batch.draw(this.background, 0.0F, 0.0F, 1280.0F, 720.0F, 0, 0, this.background.getWidth(), this.background.getHeight(), false, true);
/* 129 */       this.batch.end();
/* 130 */       this.batch.enableBlending();
/*     */     } 
/*     */     
/* 133 */     len = this.widgets.size();
/* 134 */     for (i = 0; i < len; i++) {
/* 135 */       ((Widget)this.widgets.get(i)).render(this.game.glGraphics.batch, this.game.glGraphics.shapeRenderer);
/*     */     }
/*     */     
/* 138 */     if (this.game.menuMusic.isPlaying()) {
/* 139 */       this.batch.begin();
/* 140 */       String s = "" + (char)(16 + (int)(Gdx.graphics.getFrameId() % 24L) / 6);
/* 141 */       if (this.game.menuMusic.playList.size() > 1) {
/* 142 */         s = s + " " + this.game.menuMusic.getCurrentTrackName();
/*     */       }
/* 144 */       Objects.requireNonNull(this.game.gui); Assets.font10.draw(this.batch, s, 8, 720 - 20, Font.Align.LEFT);
/* 145 */       this.batch.end();
/*     */     } 
/*     */     
/* 148 */     if (Settings.development && Settings.showJavaHeap) {
/* 149 */       this.batch.begin();
/* 150 */       Objects.requireNonNull(this.game.gui); Assets.font10.draw(this.batch, String.format(Locale.getDefault(), "%,d", new Object[] { Long.valueOf(Gdx.app.getJavaHeap()) }), 1280 - 120, 10, Font.Align.LEFT);
/* 151 */       this.batch.end();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Widget getSelectedWidget() {
/* 156 */     return this.selectedWidget;
/*     */   }
/*     */   
/*     */   public boolean setSelectedWidget(Widget widget) {
/* 160 */     if (widget == null || widget == this.selectedWidget || !widget.visible || !widget.active) {
/* 161 */       return false;
/*     */     }
/* 163 */     if (this.selectedWidget != null) {
/* 164 */       if (this.selectedWidget.entryMode) {
/* 165 */         return false;
/*     */       }
/* 167 */       this.selectedWidget.setSelected(false);
/*     */     } 
/* 169 */     this.selectedWidget = widget;
/* 170 */     this.selectedWidget.setSelected(true);
/* 171 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {}
/*     */ 
/*     */   
/*     */   public void resize(int width, int height) {
/* 180 */     this.game.gui.resize(width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pause() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void resume() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void hide() {}
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 197 */     if (this.background != null) {
/* 198 */       this.background.dispose();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void refreshAllWidgets() {
/* 203 */     for (Widget widget : this.widgets)
/* 204 */       widget.setDirty(true); 
/*     */   }
/*     */   
/*     */   protected class TitleBar
/*     */     extends Button
/*     */   {
/*     */     public TitleBar(String text, int color) {
/* 211 */       Objects.requireNonNull(GLScreen.this.game.gui); setGeometry((1280 - 960) / 2, 30, 960, 40);
/* 212 */       setColor(color);
/* 213 */       setText(text, Font.Align.CENTER, Assets.font14);
/* 214 */       setActive(false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\GLScreen.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */