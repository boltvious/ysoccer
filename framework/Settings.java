/*     */ package com.ygames.ysoccer.framework;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Preferences;
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Settings
/*     */ {
/*     */   private Preferences preferences;
/*     */   private Json json;
/*  18 */   public final String APP_NAME = "YSoccer";
/*  19 */   public final String VERSION = "19";
/*     */   
/*     */   public String locale;
/*     */   
/*     */   public boolean fullScreen;
/*     */   
/*     */   public boolean showIntro;
/*     */   
/*     */   public int musicMode;
/*     */   public int musicVolume;
/*  29 */   public static Integer[] matchLengths = new Integer[] { Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(7), Integer.valueOf(10) };
/*     */   
/*     */   public int matchLength;
/*     */   
/*     */   public int benchSize;
/*     */   
/*     */   public boolean useFlags;
/*     */   
/*     */   public double maxPlayerValue;
/*     */   
/*     */   public String currency;
/*     */   
/*     */   public int weatherMaxStrength;
/*     */   
/*     */   public int zoom;
/*     */   public boolean radar;
/*     */   public boolean autoReplays;
/*     */   public int soundVolume;
/*     */   public boolean commentary;
/*     */   private String keyboardConfigs;
/*     */   private String joystickConfigs;
/*     */   public static boolean development;
/*     */   public static int logLevel;
/*     */   static int logFilter;
/*     */   public static boolean showJavaHeap;
/*     */   public static boolean showTeamValues;
/*     */   public static boolean showDevelopmentInfo;
/*     */   public static boolean showBallZones;
/*     */   public static boolean showBallPredictions;
/*     */   public static boolean showPlayerNumber;
/*     */   public static boolean showBestDefender;
/*     */   public static boolean showFrameDistance;
/*     */   public static boolean showPlayerState;
/*     */   public static boolean showPlayerAiState;
/*     */   
/*     */   Settings() {
/*  65 */     this.preferences = Gdx.app.getPreferences("YSoccer19");
/*     */     
/*  67 */     this.json = new Json();
/*  68 */     this.json.addClassTag("KeyboardConfig", KeyboardConfig.class);
/*  69 */     this.json.addClassTag("JoystickConfig", JoystickConfig.class);
/*     */ 
/*     */     
/*  72 */     this.locale = this.preferences.getString("locale", "en");
/*  73 */     this.fullScreen = this.preferences.getBoolean("fullScreen", false);
/*  74 */     this.showIntro = this.preferences.getBoolean("showIntro", true);
/*  75 */     this.musicMode = this.preferences.getInteger("musicMode", -2);
/*  76 */     this.musicVolume = this.preferences.getInteger("musicVolume", 40);
/*  77 */     this.useFlags = this.preferences.getBoolean("useFlags", true);
/*  78 */     this
/*  79 */       .maxPlayerValue = this.preferences.getInteger("maxPlayerValueM", 2) * Math.pow(10.0D, this.preferences.getInteger("maxPlayerValueE", 8));
/*  80 */     this.currency = this.preferences.getString("currency", "€");
/*     */ 
/*     */     
/*  83 */     this.matchLength = this.preferences.getInteger("matchLength", matchLengths[0].intValue());
/*  84 */     this.benchSize = this.preferences.getInteger("benchSize", 5);
/*  85 */     this.weatherMaxStrength = this.preferences.getInteger("weatherMaxStrength", 1);
/*  86 */     this.zoom = this.preferences.getInteger("zoom", 100);
/*  87 */     this.radar = this.preferences.getBoolean("radar", true);
/*  88 */     this.autoReplays = this.preferences.getBoolean("autoReplays", true);
/*  89 */     this.soundVolume = this.preferences.getInteger("soundVolume", 40);
/*  90 */     this.commentary = this.preferences.getBoolean("commentary", true);
/*     */ 
/*     */     
/*  93 */     this.keyboardConfigs = this.preferences.getString("keyboardConfigs", defaultKeyboardConfigs());
/*  94 */     this.joystickConfigs = this.preferences.getString("joystickConfigs", "[]");
/*     */ 
/*     */     
/*  97 */     development = this.preferences.getBoolean("development", false);
/*     */ 
/*     */     
/* 100 */     logLevel = this.preferences.getInteger("logLevel", 2);
/* 101 */     logFilter = this.preferences.getInteger("logFilter", 0);
/*     */ 
/*     */     
/* 104 */     showJavaHeap = this.preferences.getBoolean("showJavaHeap", false);
/* 105 */     showTeamValues = this.preferences.getBoolean("showTeamValues", false);
/*     */ 
/*     */     
/* 108 */     showBallZones = this.preferences.getBoolean("showBallZones", false);
/* 109 */     showBallPredictions = this.preferences.getBoolean("showBallPredictions", false);
/* 110 */     showPlayerNumber = this.preferences.getBoolean("showPlayerNumber", false);
/* 111 */     showBestDefender = this.preferences.getBoolean("showBestDefender", false);
/* 112 */     showFrameDistance = this.preferences.getBoolean("showFrameDistance", false);
/* 113 */     showPlayerState = this.preferences.getBoolean("showPlayerState", false);
/* 114 */     showPlayerAiState = this.preferences.getBoolean("showPlayerAiState", false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void save() {
/* 119 */     this.preferences.putString("locale", this.locale);
/* 120 */     this.preferences.putBoolean("fullScreen", this.fullScreen);
/* 121 */     this.preferences.putBoolean("showIntro", this.showIntro);
/* 122 */     this.preferences.putInteger("musicMode", this.musicMode);
/* 123 */     this.preferences.putInteger("musicVolume", this.musicVolume);
/* 124 */     this.preferences.putBoolean("useFlags", this.useFlags);
/* 125 */     int e = (int)Math.log10(this.maxPlayerValue);
/* 126 */     int m = (int)(this.maxPlayerValue / Math.pow(10.0D, e));
/* 127 */     this.preferences.putInteger("maxPlayerValueM", m);
/* 128 */     this.preferences.putInteger("maxPlayerValueE", e);
/* 129 */     this.preferences.putString("currency", this.currency);
/*     */ 
/*     */     
/* 132 */     this.preferences.putInteger("matchLength", this.matchLength);
/* 133 */     this.preferences.putInteger("benchSize", this.benchSize);
/* 134 */     this.preferences.putInteger("weatherMaxStrength", this.weatherMaxStrength);
/* 135 */     this.preferences.putInteger("zoom", this.zoom);
/* 136 */     this.preferences.putBoolean("radar", this.radar);
/* 137 */     this.preferences.putBoolean("autoReplays", this.autoReplays);
/* 138 */     this.preferences.putInteger("soundVolume", this.soundVolume);
/* 139 */     this.preferences.putBoolean("commentary", this.commentary);
/*     */ 
/*     */     
/* 142 */     this.preferences.putString("keyboardConfigs", this.keyboardConfigs);
/* 143 */     this.preferences.putString("joystickConfigs", this.joystickConfigs);
/*     */ 
/*     */     
/* 146 */     this.preferences.putBoolean("development", development);
/*     */ 
/*     */     
/* 149 */     this.preferences.putInteger("logLevel", logLevel);
/* 150 */     this.preferences.putInteger("logFilter", logFilter);
/*     */ 
/*     */     
/* 153 */     this.preferences.putBoolean("showJavaHeap", showJavaHeap);
/* 154 */     this.preferences.putBoolean("showTeamValues", showTeamValues);
/*     */ 
/*     */     
/* 157 */     this.preferences.putBoolean("showBallZones", showBallZones);
/* 158 */     this.preferences.putBoolean("showBallPredictions", showBallPredictions);
/* 159 */     this.preferences.putBoolean("showPlayerNumber", showPlayerNumber);
/* 160 */     this.preferences.putBoolean("showBestDefender", showBestDefender);
/* 161 */     this.preferences.putBoolean("showFrameDistance", showFrameDistance);
/* 162 */     this.preferences.putBoolean("showPlayerState", showPlayerState);
/* 163 */     this.preferences.putBoolean("showPlayerAiState", showPlayerAiState);
/*     */     
/* 165 */     this.preferences.flush();
/*     */   }
/*     */   
/*     */   private String defaultKeyboardConfigs() {
/* 169 */     ArrayList<KeyboardConfig> keyboardConfigs = new ArrayList<>();
/* 170 */     keyboardConfigs.add(new KeyboardConfig(21, 22, 19, 20, 41, 42));
/* 171 */     keyboardConfigs.add(new KeyboardConfig(29, 32, 51, 47, 50, 30));
/* 172 */     return this.json.toJson(keyboardConfigs);
/*     */   }
/*     */   
/*     */   public ArrayList<KeyboardConfig> getKeyboardConfigs() {
/* 176 */     return new ArrayList<>(Arrays.asList((Object[])this.json.fromJson(KeyboardConfig[].class, this.keyboardConfigs)));
/*     */   }
/*     */   
/*     */   public void setKeyboardConfigs(ArrayList<KeyboardConfig> keyboardConfigs) {
/* 180 */     this.keyboardConfigs = this.json.toJson(keyboardConfigs);
/*     */   }
/*     */   
/*     */   private ArrayList<JoystickConfig> getJoystickConfigs() {
/* 184 */     return new ArrayList<>(Arrays.asList((Object[])this.json.fromJson(JoystickConfig[].class, this.joystickConfigs)));
/*     */   }
/*     */   
/*     */   public JoystickConfig getJoystickConfigByName(String name) {
/* 188 */     for (JoystickConfig joystickConfig : getJoystickConfigs()) {
/* 189 */       if (joystickConfig.name.equals(name)) {
/* 190 */         return joystickConfig;
/*     */       }
/*     */     } 
/* 193 */     return null;
/*     */   }
/*     */   
/*     */   public void setJoystickConfigs(ArrayList<JoystickConfig> joystickConfigs) {
/* 197 */     this.joystickConfigs = this.json.toJson(joystickConfigs);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\Settings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */