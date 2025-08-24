/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.math.Vector2;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ 
/*    */ 
/*    */ abstract class Scene
/*    */ {
/*    */   protected GLGame game;
/*    */   protected SceneFsm fsm;
/*    */   protected int subframe;
/*    */   protected SceneSettings settings;
/*    */   Vector2 pointOfInterest;
/*    */   
/*    */   SceneRenderer getRenderer() {
/* 16 */     return this.fsm.getSceneRenderer();
/*    */   }
/*    */   
/*    */   public void start() {
/* 20 */     this.fsm.start();
/*    */   }
/*    */   
/*    */   public void update(float deltaTime) {
/* 24 */     this.fsm.think(deltaTime);
/*    */   }
/*    */   
/*    */   public void nextSubframe() {
/* 28 */     this.subframe = (this.subframe + 1) % 4096;
/*    */   }
/*    */   
/*    */   public void render() {
/* 32 */     this.game.glGraphics.light = (this.fsm.getSceneRenderer()).light;
/* 33 */     this.fsm.getSceneRenderer().render();
/*    */   }
/*    */   
/*    */   public void resize(int width, int height) {
/* 37 */     this.fsm.getSceneRenderer().resize(width, height, this.settings.zoom);
/*    */   }
/*    */   
/*    */   void setPointOfInterest(float x, float y) {
/* 41 */     this.pointOfInterest.set(x, y);
/*    */   }
/*    */   
/*    */   void setPointOfInterest(Vector2 v) {
/* 45 */     this.pointOfInterest.set(v);
/*    */   }
/*    */   
/*    */   abstract Player getNearestOfAll();
/*    */   
/*    */   public void setBallOwner(Player player) {
/* 51 */     setBallOwner(player, true);
/*    */   }
/*    */   
/*    */   public abstract void setBallOwner(Player paramPlayer, boolean paramBoolean);
/*    */   
/*    */   abstract void quit();
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Scene.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */