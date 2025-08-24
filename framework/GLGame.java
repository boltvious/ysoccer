/*     */ package com.ygames.ysoccer.framework;
/*     */ import com.badlogic.gdx.Game;
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.controllers.Controller;
/*     */ import com.badlogic.gdx.controllers.Controllers;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.gui.Gui;
/*     */ import com.ygames.ysoccer.gui.WidgetColor;
/*     */ import com.ygames.ysoccer.match.Tactics;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ 
/*     */ public class GLGame extends Game {
/*     */   public static final int SUBFRAMES = 8;
/*     */   public static final int VIRTUAL_REFRESH_RATE = 64;
/*     */   public static final int SUBFRAMES_PER_SECOND = 512;
/*     */   public static final float SUBFRAME_DURATION = 0.001953125F;
/*     */   public Settings settings;
/*     */   public GLGraphics glGraphics;
/*     */   public Gui gui;
/*     */   private float deltaTime;
/*     */   public InputDeviceList inputDevices;
/*     */   Mouse mouse;
/*     */   MenuInput menuInput;
/*     */   public int tacticsToEdit;
/*     */   public Tactics editedTactics;
/*     */   public Stack<Tactics> tacticsUndo;
/*     */   public boolean tacticsFlip = true;
/*     */   public Team tacticsTeam;
/*     */   private State state;
/*     */   public Texture stateBackground;
/*     */   public WidgetColor stateColor;
/*     */   public TeamList teamList;
/*     */   public Competition competition;
/*     */   public MenuMusic menuMusic;
/*     */   
/*     */   public enum State {
/*  41 */     NONE, FRIENDLY, COMPETITION, EDIT, TRAINING;
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
/*     */   public void create() {
/*  55 */     this.settings = new Settings();
/*  56 */     Gdx.app.setLogLevel(Settings.logLevel);
/*  57 */     this.glGraphics = new GLGraphics();
/*  58 */     this.gui = new Gui();
/*  59 */     setScreenMode(this.settings.fullScreen);
/*  60 */     Assets.load(this.settings);
/*     */     
/*  62 */     this.inputDevices = new InputDeviceList();
/*  63 */     reloadInputDevices();
/*     */     
/*  65 */     this.menuInput = new MenuInput(this);
/*     */     
/*  67 */     this.mouse = new Mouse();
/*     */     
/*  69 */     this.state = State.NONE;
/*  70 */     this.stateColor = new WidgetColor();
/*     */     
/*  72 */     this.teamList = new TeamList();
/*     */     
/*  74 */     this.menuMusic = new MenuMusic("music");
/*     */     
/*  76 */     restoreSaveGame();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScreen(Screen screen) {
/*  81 */     debug(LogType.GUI, "setScreen", screen.getClass().getSimpleName());
/*  82 */     super.setScreen(screen);
/*     */   }
/*     */   
/*     */   private void createSaveGame() {
/*  86 */     if (hasCompetition()) {
/*  87 */       this.competition.save(Assets.saveGame);
/*  88 */     } else if (Assets.saveGame.exists()) {
/*  89 */       Assets.saveGame.delete();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void restoreSaveGame() {
/*  94 */     if (Assets.saveGame.exists()) {
/*  95 */       Competition competition = Competition.load(Assets.saveGame);
/*  96 */       setCompetition(competition);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setScreenMode(boolean fullScreen) {
/* 101 */     if (fullScreen) {
/* 102 */       if (!Gdx.graphics.isFullscreen()) {
/* 103 */         Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
/*     */       }
/* 105 */     } else if (Gdx.graphics.isFullscreen()) {
/* 106 */       Objects.requireNonNull(this.gui); Objects.requireNonNull(this.gui); Gdx.graphics.setWindowedMode(1280, 720);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void enableMouse() {
/* 111 */     this.mouse.enable();
/*     */   }
/*     */   
/*     */   public void disableMouse() {
/* 115 */     this.mouse.disable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render() {
/*     */     try {
/* 121 */       this.deltaTime += Gdx.graphics.getDeltaTime();
/*     */       
/* 123 */       int subFrames = (int)(this.deltaTime / 0.001953125F);
/*     */       
/* 125 */       if (this.screen != null) {
/* 126 */         this.screen.render(subFrames * 0.001953125F);
/*     */       }
/*     */       
/* 129 */       this.deltaTime -= subFrames * 0.001953125F;
/*     */     }
/* 131 */     catch (Exception e) {
/* 132 */       e.printStackTrace();
/*     */       
/* 134 */       FileHandle file = Gdx.files.local("error.log");
/* 135 */       file.writeString("Exception in thread \"" + Thread.currentThread().getName() + "\" " + e.toString() + "\n", false);
/* 136 */       for (StackTraceElement s : e.getStackTrace()) {
/* 137 */         file.writeString("\tat " + s.toString() + "\n", true);
/*     */       }
/* 139 */       file.writeString("\n\n", true);
/*     */       
/* 141 */       Gdx.app.exit();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void pause() {
/* 147 */     super.pause();
/* 148 */     createSaveGame();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dispose() {
/* 153 */     super.dispose();
/* 154 */     this.glGraphics.dispose();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setState(State state, Competition.Category category) {
/* 159 */     this.state = state;
/*     */     
/* 161 */     switch (state) {
/*     */       case COMPETITION:
/* 163 */         this.stateBackground = new Texture("images/backgrounds/menu_competition.jpg");
/* 164 */         switch (category) {
/*     */           case COMPETITION:
/* 166 */             this.stateColor.set(Integer.valueOf(3632687), Integer.valueOf(5150783), Integer.valueOf(2179092));
/*     */             break;
/*     */           case FRIENDLY:
/* 169 */             this.stateColor.set(Integer.valueOf(4281856), Integer.valueOf(6192384), Integer.valueOf(2371584));
/*     */             break;
/*     */         } 
/*     */         
/*     */         break;
/*     */       case FRIENDLY:
/* 175 */         this.stateBackground = new Texture("images/backgrounds/menu_friendly.jpg");
/* 176 */         this.stateColor.set(Integer.valueOf(2983261), Integer.valueOf(4043645), Integer.valueOf(1986599));
/*     */         break;
/*     */       
/*     */       case EDIT:
/* 180 */         this.stateBackground = new Texture("images/backgrounds/menu_edit.jpg");
/* 181 */         this.stateColor.set(Integer.valueOf(8995355), Integer.valueOf(12278309), Integer.valueOf(4005389));
/*     */         break;
/*     */       
/*     */       case TRAINING:
/* 185 */         this.stateBackground = new Texture("images/backgrounds/menu_training.jpg");
/* 186 */         this.stateColor.set(Integer.valueOf(1804927), Integer.valueOf(2473390), Integer.valueOf(1136464));
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public State getState() {
/* 192 */     return this.state;
/*     */   }
/*     */   
/*     */   public boolean hasCompetition() {
/* 196 */     return (this.competition != null);
/*     */   }
/*     */   
/*     */   public void setCompetition(Competition competition) {
/* 200 */     this.competition = competition;
/* 201 */     setState(State.COMPETITION, competition.category);
/*     */   }
/*     */   
/*     */   public void clearCompetition() {
/* 205 */     this.competition = null;
/*     */   }
/*     */   
/*     */   public void reloadInputDevices() {
/* 209 */     this.inputDevices.clear();
/*     */ 
/*     */     
/* 212 */     ArrayList<KeyboardConfig> keyboardConfigs = this.settings.getKeyboardConfigs();
/* 213 */     this.inputDevices.add(new Keyboard(0, keyboardConfigs.get(0)));
/* 214 */     this.inputDevices.add(new Keyboard(1, keyboardConfigs.get(1)));
/*     */ 
/*     */     
/* 217 */     int port = 0;
/* 218 */     for (Controller controller : Controllers.getControllers()) {
/* 219 */       JoystickConfig joystickConfig = this.settings.getJoystickConfigByName(controller.getName());
/* 220 */       if (joystickConfig != null) {
/* 221 */         this.inputDevices.add(new Joystick(controller, joystickConfig, port));
/* 222 */         port++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum LogType {
/* 228 */     AI_ATTACKING,
/* 229 */     AI_KICKING,
/* 230 */     BALL,
/* 231 */     GUI,
/* 232 */     PASSING,
/* 233 */     PLAYER_SELECTION;
/*     */   }
/*     */   
/*     */   public static void debug(LogType type, Object object, String message) {
/* 237 */     debug(type, object.getClass().getSimpleName(), message);
/*     */   }
/*     */   
/*     */   public static void debug(LogType type, String tag, String message) {
/* 241 */     if (Settings.development && isSetLogFilter(type)) {
/* 242 */       Gdx.app.debug(tag, message);
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isSetLogFilter(LogType type) {
/* 247 */     return (type == null || (Settings.logFilter & 1 << type.ordinal()) != 0);
/*     */   }
/*     */   
/*     */   public static void toggleLogFilter(LogType type) {
/* 251 */     Settings.logFilter ^= 1 << type.ordinal();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\GLGame.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */