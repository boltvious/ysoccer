/*     */ package com.ygames.ysoccer.match;
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ abstract class SceneFsm {
/*     */   private final Scene scene;
/*     */   private final List<SceneState> states;
/*     */   private SceneState currentState;
/*     */   private SceneState holdState;
/*     */   private final ArrayDeque<Action> actions;
/*     */   private Action currentAction;
/*     */   private SceneRenderer sceneRenderer;
/*     */   private SceneHotKeys hotKeys;
/*     */   
/*     */   enum ActionType {
/*  18 */     NONE,
/*  19 */     NEW_FOREGROUND,
/*  20 */     FADE_IN,
/*  21 */     FADE_OUT,
/*  22 */     HOLD_FOREGROUND,
/*  23 */     RESTORE_FOREGROUND;
/*     */   }
/*     */   
/*     */   class Action {
/*     */     final SceneFsm.ActionType type;
/*     */     final int stateId;
/*     */     int timer;
/*     */     
/*     */     Action(SceneFsm.ActionType type, int stateId) {
/*  32 */       this.type = type;
/*  33 */       this.stateId = stateId;
/*     */     }
/*     */     
/*     */     Action(SceneFsm.ActionType type) {
/*  37 */       this(type, -1);
/*     */     }
/*     */     
/*     */     void update() {
/*  41 */       if (this.timer > 0) {
/*  42 */         this.timer--;
/*     */       }
/*     */     }
/*     */   }
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
/*     */   SceneFsm(Scene scene) {
/*  61 */     this.scene = scene;
/*  62 */     this.states = new ArrayList<>();
/*  63 */     this.actions = new ArrayDeque<>();
/*     */   }
/*     */   
/*     */   Scene getScene() {
/*  67 */     return this.scene;
/*     */   }
/*     */   
/*     */   SceneState getState() {
/*  71 */     return this.currentState;
/*     */   }
/*     */   
/*     */   int addState(SceneState state) {
/*  75 */     state.setId(this.states.size());
/*  76 */     this.states.add(state);
/*  77 */     return state.getId();
/*     */   }
/*     */   
/*     */   public abstract void start();
/*     */   
/*     */   SceneState getHoldState() {
/*  83 */     return this.holdState;
/*     */   }
/*     */   
/*     */   SceneHotKeys getHotKeys() {
/*  87 */     return this.hotKeys;
/*     */   }
/*     */   
/*     */   void setHotKeys(SceneHotKeys hotKeys) {
/*  91 */     this.hotKeys = hotKeys;
/*     */   }
/*     */   
/*     */   SceneRenderer getSceneRenderer() {
/*  95 */     return this.sceneRenderer;
/*     */   }
/*     */   
/*     */   void setSceneRenderer(SceneRenderer sceneRenderer) {
/*  99 */     this.sceneRenderer = sceneRenderer;
/*     */   }
/*     */   
/*     */   void think(float deltaTime) {
/* 103 */     boolean doUpdate = true;
/*     */ 
/*     */     
/* 106 */     if (this.currentAction != null && this.currentAction.type == ActionType.FADE_OUT) {
/* 107 */       this.sceneRenderer.light = 8 * (this.currentAction.timer - 1);
/* 108 */       doUpdate = false;
/*     */     } 
/* 110 */     if (this.currentAction != null && this.currentAction.type == ActionType.FADE_IN) {
/* 111 */       this.sceneRenderer.light = 255 - 8 * (this.currentAction.timer - 1);
/* 112 */       doUpdate = false;
/*     */     } 
/*     */ 
/*     */     
/* 116 */     if (this.currentState != null && doUpdate) {
/* 117 */       if (this.currentAction != null && (this.currentAction.type == ActionType.NEW_FOREGROUND || this.currentAction.type == ActionType.RESTORE_FOREGROUND))
/*     */       {
/* 119 */         this.currentState.onResume();
/*     */       }
/* 121 */       if (this.currentAction != null && this.currentAction.type == ActionType.HOLD_FOREGROUND)
/*     */       {
/* 123 */         this.holdState.onPause();
/*     */       }
/* 125 */       this.currentState.doActions(deltaTime);
/*     */       
/* 127 */       Action[] newActions = this.currentState.checkConditions();
/* 128 */       if (newActions != null) {
/* 129 */         for (Action action : newActions) {
/* 130 */           this.actions.offer(action);
/*     */         }
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 136 */     if (this.currentAction != null) {
/* 137 */       this.currentAction.update();
/* 138 */       if (this.currentAction.timer == 0) {
/* 139 */         this.currentAction = null;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 144 */     if (this.currentAction == null && 
/* 145 */       this.actions.size() > 0) {
/* 146 */       pollAction();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void pollAction() {
/* 153 */     this.currentAction = this.actions.pop();
/*     */     
/* 155 */     switch (this.currentAction.type) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case FADE_IN:
/* 161 */         this.currentAction.timer = 32;
/*     */         break;
/*     */       
/*     */       case FADE_OUT:
/* 165 */         this.currentAction.timer = 32;
/*     */         break;
/*     */       
/*     */       case NEW_FOREGROUND:
/* 169 */         if (this.currentState != null) {
/* 170 */           this.currentState.exitActions();
/*     */         }
/* 172 */         this.currentState = searchState(this.currentAction.stateId);
/* 173 */         Gdx.app.debug("NEW_FOREGROUND", (this.currentState == null) ? "null" : this.currentState.getClass().getSimpleName());
/*     */         break;
/*     */       
/*     */       case HOLD_FOREGROUND:
/* 177 */         this.holdState = this.currentState;
/* 178 */         this.currentState = searchState(this.currentAction.stateId);
/* 179 */         Gdx.app.debug("HOLD_FOREGROUND", (this.currentState == null) ? "null" : this.currentState.getClass().getSimpleName());
/*     */         break;
/*     */       
/*     */       case RESTORE_FOREGROUND:
/* 183 */         this.currentState.exitActions();
/* 184 */         this.currentState = this.holdState;
/* 185 */         this.holdState = null;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private SceneState searchState(int id) {
/* 192 */     for (int i = 0; i < this.states.size(); i++) {
/* 193 */       SceneState s = this.states.get(i);
/* 194 */       if (s.checkId(id)) {
/* 195 */         s.entryActions();
/* 196 */         return s;
/*     */       } 
/*     */     } 
/* 199 */     return null;
/*     */   }
/*     */   
/*     */   void pushAction(ActionType type) {
/* 203 */     pushAction(type, -1);
/*     */   }
/*     */   
/*     */   void pushAction(ActionType type, int stateId) {
/* 207 */     this.actions.offer(new Action(type, stateId));
/*     */   }
/*     */   
/*     */   Action[] newAction(ActionType type, int stateId) {
/* 211 */     return new Action[] { new Action(type, stateId) };
/*     */   }
/*     */   
/*     */   Action[] newFadedAction(ActionType type, int stateId) {
/* 215 */     return new Action[] { new Action(ActionType.FADE_OUT), new Action(type, stateId), new Action(ActionType.FADE_IN) };
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\SceneFsm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */