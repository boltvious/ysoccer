/*     */ package com.ygames.ysoccer.framework;
/*     */ 
/*     */ import com.badlogic.gdx.Application;
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.audio.Sound;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Cursor;
/*     */ import com.badlogic.gdx.graphics.Pixmap;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.badlogic.gdx.utils.I18NBundle;
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonWriter;
/*     */ import com.ygames.ysoccer.competitions.Cup;
/*     */ import com.ygames.ysoccer.competitions.League;
/*     */ import com.ygames.ysoccer.competitions.tournament.Tournament;
/*     */ import com.ygames.ysoccer.competitions.tournament.groups.Groups;
/*     */ import com.ygames.ysoccer.competitions.tournament.knockout.Knockout;
/*     */ import com.ygames.ysoccer.match.Const;
/*     */ import com.ygames.ysoccer.match.CrowdRenderer;
/*     */ import com.ygames.ysoccer.match.Hair;
/*     */ import com.ygames.ysoccer.match.Kit;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.SceneSettings;
/*     */ import com.ygames.ysoccer.match.Tactics;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.text.DecimalFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Assets
/*     */ {
/*     */   public static Random random;
/*     */   static Cursor customCursor;
/*     */   static Cursor hiddenCursor;
/*     */   public static I18NBundle strings;
/*     */   public static List<String> locales;
/*     */   public static Font font14;
/*     */   public static Font font10;
/*     */   public static Font font6;
/*     */   public static Font font3;
/*     */   public static FileHandle teamsRootFolder;
/*     */   public static FileHandle competitionsRootFolder;
/*     */   public static FileHandle tacticsFolder;
/*     */   public static FileHandle savesFolder;
/*     */   static FileHandle saveGame;
/*     */   public static Json json;
/*  63 */   public static final int[] calendars = new int[4600];
/*     */   public static List<String> associations;
/*     */   public static FileHandle favouritesFile;
/*     */   public static List<String> favourites;
/*  67 */   public static final Tactics[] tactics = new Tactics[18];
/*     */   public static List<String> kits;
/*     */   public static Integer[][][] playerOrigins;
/*     */   public static Integer[][][] keeperOrigins;
/*     */   public static List<String> hairStyles;
/*     */   public static Integer[][][] playerHairMap;
/*     */   public static Integer[][][] keeperHairMap;
/*     */   public static List<String> currencies;
/*  75 */   public static final TextureRegion[] scroll = new TextureRegion[2];
/*     */   public static TextureRegion shortArrow;
/*  77 */   public static final TextureRegion[] stars = new TextureRegion[10];
/*  78 */   public static final TextureRegion[][] controls = new TextureRegion[2][3];
/*  79 */   public static final TextureRegion[][] pieces = new TextureRegion[2][2];
/*  80 */   public static final TextureRegion[] lightIcons = new TextureRegion[3];
/*  81 */   public static final TextureRegion[] pitchIcons = new TextureRegion[10];
/*  82 */   public static final TextureRegion[] weatherIcons = new TextureRegion[11];
/*  83 */   public static final TextureRegion[][] stadium = new TextureRegion[4][4];
/*  84 */   public static final TextureRegion[] crowd = new TextureRegion[5];
/*     */   public static CrowdRenderer crowdRenderer;
/*  86 */   public static final TextureRegion[] ball = new TextureRegion[5];
/*  87 */   public static final TextureRegion[][] cornerFlags = new TextureRegion[6][3];
/*  88 */   public static final TextureRegion[][][] cornerFlagsShadows = new TextureRegion[6][3][4];
/*     */   public static Texture goalTopA;
/*     */   public static Texture goalTopB;
/*     */   public static Texture goalBottom;
/*     */   public static Texture jumper;
/*  93 */   public static final TextureRegion[][] coach = new TextureRegion[2][6];
/*  94 */   public static final TextureRegion[][][][] keeper = new TextureRegion[2][10][8][19];
/*  95 */   public static final TextureRegion[][][] keeperShadow = new TextureRegion[8][19][4];
/*  96 */   public static final TextureRegion[][][][] player = new TextureRegion[2][10][8][16];
/*  97 */   public static final Map<Hair, TextureRegion[][]> hairs = (Map)new HashMap<>();
/*  98 */   public static final TextureRegion[][][] playerShadow = new TextureRegion[8][16][4];
/*     */   public static Pixmap keeperCollisionDetection;
/* 100 */   public static final TextureRegion[][] playerNumbers = new TextureRegion[10][2];
/* 101 */   public static final TextureRegion[] tinyNumbers = new TextureRegion[10];
/* 102 */   public static final TextureRegion[] time = new TextureRegion[11];
/* 103 */   public static final TextureRegion[] score = new TextureRegion[11];
/* 104 */   public static final TextureRegion[][] replaySpeed = new TextureRegion[3][3];
/* 105 */   public static final TextureRegion[] rain = new TextureRegion[4];
/* 106 */   public static final TextureRegion[] snow = new TextureRegion[3];
/*     */   public static Texture fog;
/* 108 */   public static final TextureRegion[][] wind = new TextureRegion[8][2];
/* 109 */   public static final TextureRegion[] bench = new TextureRegion[2];
/*     */   
/*     */   public static class Sounds
/*     */   {
/*     */     public static Sound bounce;
/*     */     public static Sound chant;
/*     */     public static Sound crowd;
/*     */     public static Long crowdId;
/*     */     public static Sound deflect;
/*     */     public static Sound hold;
/*     */     public static Sound homeGoal;
/*     */     public static Sound intro;
/*     */     public static Long introId;
/*     */     public static Sound kick;
/*     */     public static Sound net;
/*     */     public static Sound post;
/*     */     public static Sound end;
/*     */     public static Sound whistle;
/*     */     public static int volume;
/*     */     
/*     */     static void load() {
/* 130 */       bounce = newSound("bounce.ogg");
/* 131 */       chant = newSound("chant.ogg");
/* 132 */       crowd = newSound("crowd.ogg");
/* 133 */       deflect = newSound("deflect.ogg");
/* 134 */       end = newSound("end.ogg");
/* 135 */       hold = newSound("hold.ogg");
/* 136 */       homeGoal = newSound("home_goal.ogg");
/* 137 */       intro = newSound("intro.ogg");
/* 138 */       kick = newSound("kick.ogg");
/* 139 */       net = newSound("net.ogg");
/* 140 */       post = newSound("post.ogg");
/* 141 */       whistle = newSound("whistle.ogg");
/*     */     }
/*     */     
/*     */     private static Sound newSound(String filename) {
/* 145 */       return Gdx.audio.newSound(Gdx.files.internal("sounds").child(filename));
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Commentary
/*     */   {
/* 151 */     public static final List<Sound> cornerKick = new ArrayList<>();
/* 152 */     public static final List<Sound> foul = new ArrayList<>();
/* 153 */     public static final List<Sound> goal = new ArrayList<>();
/* 154 */     public static final List<Sound> keeperSave = new ArrayList<>();
/* 155 */     public static final List<Sound> ownGoal = new ArrayList<>();
/* 156 */     public static final List<Sound> penalty = new ArrayList<>();
/* 157 */     public static final List<Sound> playerSubstitution = new ArrayList<>();
/* 158 */     public static final List<Sound> playerSwap = new ArrayList<>();
/*     */     
/*     */     static void load() {
/* 161 */       FileHandle commentaryFolder = Gdx.files.local("sounds/commentary");
/* 162 */       for (FileHandle fileHandle : commentaryFolder.list()) {
/* 163 */         List<String> extensions = Arrays.asList(new String[] { "ogg", "wav", "mp3" });
/* 164 */         if (extensions.contains(fileHandle.extension().toLowerCase())) {
/* 165 */           String name = fileHandle.nameWithoutExtension();
/* 166 */           if (name.startsWith("corner_kick")) {
/* 167 */             cornerKick.add(Gdx.audio.newSound(fileHandle));
/*     */           }
/* 169 */           if (name.startsWith("foul")) {
/* 170 */             foul.add(Gdx.audio.newSound(fileHandle));
/*     */           }
/* 172 */           if (name.startsWith("goal")) {
/* 173 */             goal.add(Gdx.audio.newSound(fileHandle));
/*     */           }
/* 175 */           if (name.startsWith("keeper_save")) {
/* 176 */             keeperSave.add(Gdx.audio.newSound(fileHandle));
/*     */           }
/* 178 */           if (name.startsWith("own_goal")) {
/* 179 */             ownGoal.add(Gdx.audio.newSound(fileHandle));
/*     */           }
/* 181 */           if (name.startsWith("penalty")) {
/* 182 */             penalty.add(Gdx.audio.newSound(fileHandle));
/*     */           }
/* 184 */           if (name.startsWith("player_substitution")) {
/* 185 */             playerSubstitution.add(Gdx.audio.newSound(fileHandle));
/*     */           }
/* 187 */           if (name.startsWith("player_swap")) {
/* 188 */             playerSwap.add(Gdx.audio.newSound(fileHandle));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public static void stop() {
/* 195 */       for (Sound s : cornerKick) s.stop(); 
/* 196 */       for (Sound s : foul) s.stop(); 
/* 197 */       for (Sound s : goal) s.stop(); 
/* 198 */       for (Sound s : keeperSave) s.stop(); 
/* 199 */       for (Sound s : ownGoal) s.stop(); 
/* 200 */       for (Sound s : penalty) s.stop(); 
/* 201 */       for (Sound s : playerSubstitution) s.stop(); 
/* 202 */       for (Sound s : playerSwap) s.stop(); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void load(Settings settings) {
/* 207 */     random = new Random(System.currentTimeMillis());
/* 208 */     customCursor = Gdx.graphics.newCursor(new Pixmap(Gdx.files.internal("images/arrow.png")), 0, 0);
/* 209 */     hiddenCursor = Gdx.graphics.newCursor(new Pixmap(1, 1, Pixmap.Format.RGBA8888), 0, 0);
/* 210 */     loadLocales();
/* 211 */     loadStrings(settings);
/* 212 */     font14 = new Font(14, 16, 23, 16, 22);
/* 213 */     font14.load();
/* 214 */     font10 = new Font(10, 13, 17, 12, 16);
/* 215 */     font10.load();
/* 216 */     font6 = new Font(6, 8, 14, 8, 14);
/* 217 */     font6.load();
/* 218 */     font3 = new Font(3, 4, 7, 4, 7);
/* 219 */     font3.load();
/*     */ 
/*     */     
/* 222 */     if (Gdx.app.getType() == Application.ApplicationType.Android) {
/* 223 */       FileHandle localDataFolder = Gdx.files.local("data");
/* 224 */       if ((localDataFolder.list()).length == 0) {
/* 225 */         Gdx.files.internal("data").copyTo(localDataFolder);
/*     */       }
/*     */     } 
/*     */     
/* 229 */     teamsRootFolder = Gdx.files.local("data/teams/");
/* 230 */     competitionsRootFolder = Gdx.files.local("data/competitions/");
/* 231 */     tacticsFolder = Gdx.files.local("data/tactics");
/* 232 */     savesFolder = Gdx.files.local("data/saves/competitions/");
/* 233 */     saveGame = savesFolder.child("savegame.json");
/* 234 */     json = new Json();
/* 235 */     json.addClassTag("CUP", Cup.class);
/* 236 */     json.addClassTag("LEAGUE", League.class);
/* 237 */     json.addClassTag("TOURNAMENT", Tournament.class);
/* 238 */     json.addClassTag("GROUPS", Groups.class);
/* 239 */     json.addClassTag("KNOCKOUT", Knockout.class);
/* 240 */     json.setOutputType(JsonWriter.OutputType.json);
/* 241 */     json.setUsePrototypes(false);
/* 242 */     loadCalendars();
/*     */     
/* 244 */     favouritesFile = teamsRootFolder.child("favourites.json");
/* 245 */     favourites = loadFavourites();
/*     */     
/* 247 */     associations = new ArrayList<>(Arrays.asList(Const.associations));
/* 248 */     loadTactics();
/* 249 */     loadKits();
/* 250 */     loadPlayerOrigins();
/* 251 */     loadKeeperOrigins();
/* 252 */     hairStyles = loadHairStyles();
/* 253 */     loadPlayerHairMap();
/* 254 */     loadKeeperHairMap();
/* 255 */     currencies = new ArrayList<>(Arrays.asList((Object[])loadJsonFile(String[].class, "configs/currencies.json")));
/* 256 */     loadScroll();
/* 257 */     shortArrow = loadTextureRegion("images/short_arrow.png");
/* 258 */     loadStars();
/* 259 */     loadControls();
/* 260 */     loadPieces();
/* 261 */     loadLightIcons();
/* 262 */     loadPitchIcons();
/* 263 */     loadWeatherIcons();
/* 264 */     crowdRenderer = new CrowdRenderer(Gdx.files.internal("images/stadium/crowd.json"));
/* 265 */     goalTopA = new Texture("images/stadium/goal_top_a.png");
/* 266 */     goalTopB = new Texture("images/stadium/goal_top_b.png");
/* 267 */     goalBottom = new Texture("images/stadium/goal_bottom.png");
/* 268 */     jumper = new Texture("images/stadium/jumper.png");
/* 269 */     loadKeeperShadow();
/* 270 */     loadPlayerShadow();
/* 271 */     keeperCollisionDetection = new Pixmap(Gdx.files.internal("images/keeper_cd.png"));
/* 272 */     loadPlayerNumbers();
/* 273 */     loadTinyNumbers();
/* 274 */     loadTime();
/* 275 */     loadScore();
/* 276 */     loadReplaySpeed();
/* 277 */     loadRain();
/* 278 */     loadSnow();
/* 279 */     fog = new Texture("images/fog.png");
/* 280 */     loadWind();
/* 281 */     loadBench();
/* 282 */     Sounds.load();
/* 283 */     Commentary.load();
/*     */   }
/*     */   
/*     */   private static void loadLocales() {
/* 287 */     locales = new ArrayList<>();
/* 288 */     FileHandle[] files = Gdx.files.local("i18n/").list(".properties");
/* 289 */     for (FileHandle file : files) {
/* 290 */       String[] parts = file.nameWithoutExtension().split("_");
/* 291 */       if (parts.length > 1) {
/* 292 */         locales.add(parts[1]);
/*     */       } else {
/* 294 */         locales.add("en");
/*     */       } 
/*     */     } 
/* 297 */     Collections.sort(locales);
/*     */   }
/*     */   
/*     */   public static void loadStrings(Settings settings) {
/* 301 */     strings = I18NBundle.createBundle(Gdx.files.internal("i18n/strings"), new Locale(settings.locale));
/*     */   }
/*     */   
/*     */   public static String gettext(String label) {
/*     */     try {
/* 306 */       return strings.get(label);
/* 307 */     } catch (MissingResourceException e) {
/* 308 */       return label;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadCalendars() {
/*     */     try {
/* 314 */       InputStream in = Gdx.files.internal("configs/calendars.bin").read();
/* 315 */       byte[] buffer = new byte[1];
/* 316 */       for (int i = 0; i < calendars.length; i++) {
/* 317 */         if (in.read(buffer) != -1) {
/* 318 */           calendars[i] = buffer[0] & 0xFF;
/*     */         }
/*     */       } 
/* 321 */     } catch (IOException e) {
/* 322 */       throw new RuntimeException("Error while reading calendars " + e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static List<String> loadFavourites() {
/* 327 */     if (favouritesFile.exists()) {
/* 328 */       return Arrays.asList((Object[])json.fromJson(String[].class, favouritesFile.readString("UTF-8")));
/*     */     }
/* 330 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveFavourites() {
/* 335 */     String json = Assets.json.toJson(favourites, ArrayList.class, String.class);
/* 336 */     favouritesFile.writeString(json, false, "UTF-8");
/*     */   }
/*     */   
/* 339 */   public static Comparator<FileHandle> fileComparatorByName = new CompareFileHandlesByName();
/*     */   
/*     */   private static class CompareFileHandlesByName implements Comparator<FileHandle> {
/*     */     private CompareFileHandlesByName() {}
/*     */     
/*     */     public int compare(FileHandle o1, FileHandle o2) {
/* 345 */       return o1.nameWithoutExtension().compareTo(o2.nameWithoutExtension());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadTactics() {
/* 350 */     for (int i = 0; i < tactics.length; i++) {
/*     */       try {
/* 352 */         tactics[i] = new Tactics();
/* 353 */         InputStream in = Gdx.files.internal("data/tactics/preset/" + Tactics.fileNames[i] + ".TAC").read();
/* 354 */         tactics[i].loadFile(in);
/* 355 */       } catch (IOException e) {
/* 356 */         throw new RuntimeException("Couldn't load tactics", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadKits() {
/* 362 */     kits = new ArrayList<>();
/* 363 */     FileHandle fileHandle = Gdx.files.internal("images/kit");
/* 364 */     for (FileHandle kitFile : fileHandle.list(".png")) {
/* 365 */       kits.add(kitFile.nameWithoutExtension());
/*     */     }
/* 367 */     Collections.sort(kits);
/*     */   }
/*     */   
/*     */   private static <T> T loadJsonFile(Class<T> type, String filename) {
/* 371 */     return (T)json.fromJson(type, Gdx.files.internal(filename).readString("UTF-8"));
/*     */   }
/*     */   
/*     */   private static List<String> loadHairStyles() {
/* 375 */     List<String> hairStyles = new ArrayList<>();
/* 376 */     FileHandle folder = Gdx.files.internal("images/player/hairstyles");
/* 377 */     for (FileHandle file : folder.list(".png")) {
/* 378 */       hairStyles.add(file.nameWithoutExtension());
/*     */     }
/* 380 */     Collections.sort(hairStyles);
/* 381 */     return hairStyles;
/*     */   }
/*     */   
/*     */   private static Integer[][][] loadIntegerArray3(String path) {
/* 385 */     return (Integer[][][])json.fromJson(Integer[][][].class, Gdx.files.local(path).readString("UTF-8"));
/*     */   }
/*     */   
/*     */   public static void loadPlayerOrigins() {
/* 389 */     playerOrigins = loadIntegerArray3("configs/player_origins.json");
/*     */   }
/*     */   
/*     */   public static void loadKeeperOrigins() {
/* 393 */     keeperOrigins = loadIntegerArray3("configs/keeper_origins.json");
/*     */   }
/*     */   
/*     */   public static void loadPlayerHairMap() {
/* 397 */     playerHairMap = loadIntegerArray3("configs/player_hair_map.json");
/*     */   }
/*     */   
/*     */   public static void loadKeeperHairMap() {
/* 401 */     keeperHairMap = loadIntegerArray3("configs/keeper_hair_map.json");
/*     */   }
/*     */   
/*     */   private static void saveIntegerArray3(Integer[][][] array3, String path) {
/* 405 */     Gdx.files.local(path).writeString(json.prettyPrint(array3), false, "UTF-8");
/*     */   }
/*     */   
/*     */   public static void savePlayerOrigins() {
/* 409 */     saveIntegerArray3(playerOrigins, "configs/player_origins.json");
/*     */   }
/*     */   
/*     */   public static void saveKeeperOrigins() {
/* 413 */     saveIntegerArray3(keeperOrigins, "configs/keeper_origins.json");
/*     */   }
/*     */   
/*     */   public static void savePlayerHairMap() {
/* 417 */     saveIntegerArray3(playerHairMap, "configs/player_hair_map.json");
/*     */   }
/*     */   
/*     */   public static void saveKeeperHairMap() {
/* 421 */     saveIntegerArray3(keeperHairMap, "configs/keeper_hair_map.json");
/*     */   }
/*     */   
/*     */   public static String moneyFormat(double p) {
/* 425 */     String suffix = "";
/* 426 */     int e3 = EMath.floor(Math.log10(p) / 3.0D);
/* 427 */     switch (e3) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 433 */         suffix = strings.get("MONEY.THOUSANDS");
/*     */         break;
/*     */       
/*     */       case 2:
/* 437 */         suffix = strings.get("MONEY.MILLIONS");
/*     */         break;
/*     */       
/*     */       case 3:
/* 441 */         suffix = strings.get("MONEY.BILLIONS");
/*     */         break;
/*     */     } 
/*     */     
/* 445 */     int e = EMath.floor(Math.log10(p));
/* 446 */     double div = Math.pow(10.0D, (e - 1));
/* 447 */     p = EMath.floor(p / div);
/*     */     
/* 449 */     int mul = e - 1 - 3 * e3;
/* 450 */     if (mul >= 0) {
/* 451 */       p *= (int)Math.pow(10.0D, mul);
/*     */     } else {
/* 453 */       p /= Math.pow(10.0D, -mul);
/*     */     } 
/*     */     
/* 456 */     DecimalFormat df = new DecimalFormat("#,###,###,##0.##");
/* 457 */     return df.format(p) + suffix;
/*     */   }
/*     */   
/*     */   public static TextureRegion getNationalityFlag(String nationality) {
/*     */     try {
/* 462 */       Texture texture = new Texture("images/flags/tiny/" + nationality + ".png");
/* 463 */       TextureRegion textureRegion = new TextureRegion(texture);
/* 464 */       textureRegion.flip(false, true);
/* 465 */       return textureRegion;
/* 466 */     } catch (Exception e) {
/* 467 */       Gdx.app.log("Warning", e.getMessage());
/* 468 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadScroll() {
/* 473 */     Texture texture = new Texture("images/scroll.png");
/* 474 */     for (int i = 0; i < 2; i++) {
/* 475 */       scroll[i] = new TextureRegion(texture, 16 * i, 0, 16, 32);
/* 476 */       scroll[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadStars() {
/* 481 */     Texture texture = new Texture("images/stars.png");
/* 482 */     for (int i = 0; i < 10; i++) {
/* 483 */       stars[i] = new TextureRegion(texture, 0, 16 * i, 64, 16);
/* 484 */       stars[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadControls() {
/* 489 */     Texture texture = new Texture("images/controls.png");
/* 490 */     for (int i = 0; i < 3; i++) {
/* 491 */       controls[0][i] = new TextureRegion(texture, 36 * i, 0, 36, 36);
/* 492 */       controls[0][i].flip(false, true);
/* 493 */       controls[1][i] = new TextureRegion(texture, 18 * i, 36, 18, 18);
/* 494 */       controls[1][i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadPieces() {
/* 499 */     Texture texture = new Texture("images/pieces.png");
/* 500 */     for (int i = 0; i < 2; i++) {
/* 501 */       for (int j = 0; j < 2; j++) {
/* 502 */         pieces[i][j] = new TextureRegion(texture, 20 * i, 14 * j, 20, 14);
/* 503 */         pieces[i][j].flip(false, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadLightIcons() {
/* 509 */     Texture texture = new Texture("images/light.png");
/* 510 */     for (int i = 0; i < 3; i++) {
/* 511 */       lightIcons[i] = new TextureRegion(texture, 47 * i, 0, 46, 46);
/* 512 */       lightIcons[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadPitchIcons() {
/* 517 */     Texture texture = new Texture("images/pitches.png");
/* 518 */     for (int i = 0; i < 10; i++) {
/* 519 */       pitchIcons[i] = new TextureRegion(texture, 47 * i, 0, 46, 46);
/* 520 */       pitchIcons[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadWeatherIcons() {
/* 525 */     Texture texture = new Texture("images/weather.png");
/* 526 */     for (int i = 0; i < 11; i++) {
/* 527 */       weatherIcons[i] = new TextureRegion(texture, 47 * i, 0, 46, 46);
/* 528 */       weatherIcons[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadTime() {
/* 533 */     Texture texture = new Texture("images/time.png");
/* 534 */     for (int i = 0; i < 11; i++) {
/* 535 */       time[i] = new TextureRegion(texture, 12 * i, 0, (i < 10) ? 12 : 48, 20);
/* 536 */       time[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadScore() {
/* 541 */     Texture texture = new Texture("images/score.png");
/* 542 */     for (int i = 0; i < 11; i++) {
/* 543 */       score[i] = new TextureRegion(texture, 24 * i, 0, 24, 38);
/* 544 */       score[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadReplaySpeed() {
/* 549 */     Texture texture = new Texture("images/replay_speed.png");
/* 550 */     for (int i = 0; i < 3; i++) {
/* 551 */       for (int j = 0; j < 3; j++) {
/* 552 */         replaySpeed[i][j] = new TextureRegion(texture, 42 * i, 42 * j, 41, 41);
/* 553 */         replaySpeed[i][j].flip(false, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadRain() {
/* 559 */     Texture texture = new Texture("images/rain.png");
/* 560 */     for (int i = 0; i < 4; i++) {
/* 561 */       rain[i] = new TextureRegion(texture, 30 * i, 0, 30, 30);
/* 562 */       rain[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadSnow() {
/* 567 */     Texture texture = new Texture("images/snow.png");
/* 568 */     for (int i = 0; i < 3; i++) {
/* 569 */       snow[i] = new TextureRegion(texture, 3 * i, 0, 3, 3);
/* 570 */       snow[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadWind() {
/* 575 */     Texture texture = new Texture("images/wind.png");
/* 576 */     for (int i = 0; i < 8; i++) {
/* 577 */       for (int j = 0; j < 2; j++) {
/* 578 */         wind[i][j] = new TextureRegion(texture, 30 * i, 30 * j, 30, 30);
/* 579 */         wind[i][j].flip(false, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadBench() {
/* 585 */     Texture texture = new Texture("images/bench.png");
/* 586 */     for (int i = 0; i < 2; i++) {
/* 587 */       bench[i] = new TextureRegion(texture, 82 * i, 0, 82, 66);
/* 588 */       bench[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadStadium(SceneSettings sceneSettings) {
/* 594 */     String paletteName = sceneSettings.pitchType.toString().toLowerCase();
/* 595 */     switch (sceneSettings.time) {
/*     */       case DAY:
/* 597 */         switch (sceneSettings.sky) {
/*     */           case 0:
/* 599 */             paletteName = paletteName + "_sunny.pal";
/*     */             break;
/*     */           
/*     */           case 1:
/* 603 */             paletteName = paletteName + "_cloudy.pal";
/*     */             break;
/*     */         } 
/*     */         
/*     */         break;
/*     */       case NIGHT:
/* 609 */         paletteName = paletteName + "_night.pal";
/*     */         break;
/*     */     } 
/*     */     
/* 613 */     for (int c = 0; c < 4; c++) {
/* 614 */       for (int r = 0; r < 4; r++) {
/* 615 */         stadium[r][c] = loadTextureRegion("images/stadium/generic_" + c + "" + r + ".png", "images/stadium/palettes/" + paletteName);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadCrowd(Team team) {
/* 621 */     List<RgbPair> rgbPairs = new ArrayList<>();
/* 622 */     ((Kit)team.kits.get(0)).addKitColors(rgbPairs);
/* 623 */     Texture texture = loadTexture("images/stadium/crowd.png", rgbPairs);
/* 624 */     crowd[0] = new TextureRegion(texture, 0, 0, 47, 35);
/* 625 */     crowd[0].flip(false, true);
/* 626 */     crowd[1] = new TextureRegion(texture, 70, 0, 47, 35);
/* 627 */     crowd[1].flip(false, true);
/* 628 */     crowd[2] = new TextureRegion(texture, 0, 38, 47, 26);
/* 629 */     crowd[2].flip(false, true);
/* 630 */     crowd[3] = new TextureRegion(texture, 49, 29, 23, 35);
/* 631 */     crowd[3].flip(false, true);
/* 632 */     crowd[4] = new TextureRegion(texture, 105, 29, 23, 35);
/* 633 */     crowd[4].flip(false, true);
/*     */   }
/*     */   
/*     */   public static void loadBall(SceneSettings sceneSettings) {
/* 637 */     List<RgbPair> rgbPairs = new ArrayList<>();
/* 638 */     switch (sceneSettings.time) {
/*     */       case DAY:
/* 640 */         rgbPairs.add(new RgbPair(20992, sceneSettings.grass.lightShadow));
/* 641 */         rgbPairs.add(new RgbPair(6144, sceneSettings.grass.darkShadow));
/*     */         break;
/*     */       
/*     */       case NIGHT:
/* 645 */         rgbPairs.add(new RgbPair(20992, sceneSettings.grass.lightShadow));
/* 646 */         rgbPairs.add(new RgbPair(6144, sceneSettings.grass.lightShadow));
/*     */         break;
/*     */     } 
/*     */     
/* 650 */     Texture ballTexture = loadTexture("images/" + (sceneSettings.useOrangeBall() ? "ballsnow.png" : "ball.png"), rgbPairs);
/* 651 */     for (int r = 0; r < 5; r++) {
/* 652 */       ball[r] = new TextureRegion(ballTexture, r * 8, 0, 8, 8);
/* 653 */       ball[r].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadCornerFlags(SceneSettings sceneSettings) {
/* 658 */     List<RgbPair> rgbPairs = new ArrayList<>();
/* 659 */     switch (sceneSettings.time) {
/*     */       case DAY:
/* 661 */         rgbPairs.add(new RgbPair(2691072, sceneSettings.grass.darkShadow));
/*     */         break;
/*     */       
/*     */       case NIGHT:
/* 665 */         rgbPairs.add(new RgbPair(2691072, sceneSettings.grass.lightShadow));
/*     */         break;
/*     */     } 
/*     */     
/* 669 */     Texture cornerFlags = loadTexture("images/corner_flags.png", rgbPairs);
/* 670 */     for (int frameX = 0; frameX < 6; frameX++) {
/* 671 */       for (int frameY = 0; frameY < 3; frameY++) {
/* 672 */         Assets.cornerFlags[frameX][frameY] = new TextureRegion(cornerFlags, 42 * frameX, 84 * frameY, 42, 36);
/* 673 */         Assets.cornerFlags[frameX][frameY].flip(false, true);
/* 674 */         for (int i = 0; i < 4; i++) {
/* 675 */           cornerFlagsShadows[frameX][frameY][i] = new TextureRegion(cornerFlags, 42 * frameX, 84 * frameY + 36 + 12 * i, 42, 12);
/* 676 */           cornerFlagsShadows[frameX][frameY][i].flip(false, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadKeeper(Player p) {
/* 683 */     if (Assets.keeper[p.team.index][p.skinColor.ordinal()][0][0] == null) {
/* 684 */       List<RgbPair> rgbPairs = new ArrayList<>();
/* 685 */       p.addSkinColors(rgbPairs);
/* 686 */       Texture keeper = loadTexture("images/player/keeper.png", rgbPairs);
/* 687 */       for (int frameX = 0; frameX < 8; frameX++) {
/* 688 */         for (int frameY = 0; frameY < 19; frameY++) {
/* 689 */           Assets.keeper[p.team.index][p.skinColor.ordinal()][frameX][frameY] = new TextureRegion(keeper, 50 * frameX, 50 * frameY, 50, 50);
/* 690 */           Assets.keeper[p.team.index][p.skinColor.ordinal()][frameX][frameY].flip(false, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void unloadKeeper(Player p) {
/* 697 */     if (keeper[p.team.index][p.skinColor.ordinal()][0][0] != null) {
/* 698 */       for (int frameX = 0; frameX < 8; frameX++) {
/* 699 */         for (int frameY = 0; frameY < 19; frameY++) {
/* 700 */           keeper[p.team.index][p.skinColor.ordinal()][frameX][frameY] = null;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void loadCoach(Team team) {
/* 707 */     List<RgbPair> rgbPairs = new ArrayList<>();
/* 708 */     ((Kit)team.kits.get(0)).addKitColors(rgbPairs);
/* 709 */     Texture texture = loadTexture("images/coach.png", rgbPairs);
/* 710 */     for (int i = 0; i < 6; i++) {
/* 711 */       coach[team.index][i] = new TextureRegion(texture, 29 * i, 0, 29, 29);
/* 712 */       coach[team.index][i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadKeeperShadow() {
/* 717 */     for (int i = 0; i < 4; i++) {
/* 718 */       Texture textureShadow = new Texture("images/player/shadows/keeper_" + i + ".png");
/* 719 */       for (int frameX = 0; frameX < 8; frameX++) {
/* 720 */         for (int frameY = 0; frameY < 19; frameY++) {
/* 721 */           keeperShadow[frameX][frameY][i] = new TextureRegion(textureShadow, 50 * frameX, 50 * frameY, 50, 50);
/* 722 */           keeperShadow[frameX][frameY][i].flip(false, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadPlayerShadow() {
/* 729 */     for (int i = 0; i < 4; i++) {
/* 730 */       Texture textureShadow = new Texture("images/player/shadows/player_" + i + ".png");
/* 731 */       for (int frameX = 0; frameX < 8; frameX++) {
/* 732 */         for (int frameY = 0; frameY < 16; frameY++) {
/* 733 */           playerShadow[frameX][frameY][i] = new TextureRegion(textureShadow, 32 * frameX, 32 * frameY, 32, 32);
/* 734 */           playerShadow[frameX][frameY][i].flip(false, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadPlayerNumbers() {
/* 741 */     Texture texture = new Texture("images/player/numbers.png");
/* 742 */     for (int i = 0; i < 10; i++) {
/* 743 */       for (int j = 0; j < 2; j++) {
/* 744 */         playerNumbers[i][j] = new TextureRegion(texture, 6 * i, 16 * j, 6, 10);
/* 745 */         playerNumbers[i][j].flip(false, true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadTinyNumbers() {
/* 751 */     Texture texture = new Texture("images/tiny_numbers.png");
/* 752 */     for (int i = 0; i < 10; i++) {
/* 753 */       tinyNumbers[i] = new TextureRegion(texture, 4 * i + ((i == 1) ? 2 : 0), 0, (i == 1) ? 2 : 4, 6);
/* 754 */       tinyNumbers[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void loadPlayer(Player p, Kit kit) {
/* 759 */     if (player[p.team.index][p.skinColor.ordinal()][0][0] == null) {
/* 760 */       List<RgbPair> rgbPairs = new ArrayList<>();
/* 761 */       kit.addKitColors(rgbPairs);
/* 762 */       p.addSkinColors(rgbPairs);
/* 763 */       Texture playerTexture = loadTexture("images/player/" + kit.style + ".png", rgbPairs);
/* 764 */       for (int frameX = 0; frameX < 8; frameX++) {
/* 765 */         for (int frameY = 0; frameY < 16; frameY++) {
/* 766 */           player[p.team.index][p.skinColor.ordinal()][frameX][frameY] = new TextureRegion(playerTexture, 32 * frameX, 32 * frameY, 32, 32);
/* 767 */           player[p.team.index][p.skinColor.ordinal()][frameX][frameY].flip(false, true);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void unloadPlayer(Player p) {
/* 774 */     if (player[p.team.index][p.skinColor.ordinal()][0][0] != null) {
/* 775 */       for (int frameX = 0; frameX < 8; frameX++) {
/* 776 */         for (int frameY = 0; frameY < 16; frameY++) {
/* 777 */           player[p.team.index][p.skinColor.ordinal()][frameX][frameY] = null;
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void loadHair(Player player) {
/* 784 */     player.hair = new Hair(player.hairColor, player.hairStyle);
/* 785 */     if (hairs.get(player.hair) == null) {
/* 786 */       List<RgbPair> rgbPairs = new ArrayList<>();
/* 787 */       player.addHairColors(rgbPairs);
/* 788 */       Texture texture = loadTexture("images/player/hairstyles/" + player.hairStyle + ".png", rgbPairs);
/* 789 */       TextureRegion[][] textureRegion = new TextureRegion[8][10];
/* 790 */       for (int i = 0; i < 8; i++) {
/* 791 */         for (int j = 0; j < 10; j++) {
/* 792 */           textureRegion[i][j] = new TextureRegion(texture, 21 * i, 21 * j, 20, 20);
/* 793 */           textureRegion[i][j].flip(false, true);
/*     */         } 
/*     */       } 
/* 796 */       hairs.put(player.hair, textureRegion);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void unloadHair(Player player) {
/* 801 */     player.hair = new Hair(player.hairColor, player.hairStyle);
/* 802 */     if (hairs.get(player.hair) != null) {
/* 803 */       hairs.remove(player.hair);
/*     */     }
/*     */   }
/*     */   
/*     */   public static TextureRegion loadTextureRegion(String internalPath) {
/* 808 */     Texture texture = new Texture(internalPath);
/* 809 */     TextureRegion textureRegion = new TextureRegion(texture);
/* 810 */     textureRegion.flip(false, true);
/* 811 */     return textureRegion;
/*     */   }
/*     */   
/*     */   public static TextureRegion loadTextureRegion(String internalPath, List<RgbPair> rgbPairs) {
/* 815 */     Texture texture = loadTexture(internalPath, rgbPairs);
/* 816 */     TextureRegion textureRegion = new TextureRegion(texture);
/* 817 */     textureRegion.flip(false, true);
/* 818 */     return textureRegion;
/*     */   }
/*     */   
/*     */   private static TextureRegion loadTextureRegion(String internalPath, String paletteFile) {
/* 822 */     Texture texture = loadTexture(internalPath, paletteFile);
/* 823 */     TextureRegion textureRegion = new TextureRegion(texture);
/* 824 */     textureRegion.flip(false, true);
/* 825 */     return textureRegion;
/*     */   }
/*     */   
/*     */   private static Texture loadTexture(String internalPath, List<RgbPair> rgbPairs) {
/* 829 */     InputStream in = null;
/*     */     try {
/* 831 */       in = Gdx.files.internal(internalPath).read();
/*     */       
/* 833 */       ByteArrayInputStream inputStream = PngEditor.editPalette(in, rgbPairs);
/*     */       
/* 835 */       byte[] bytes = FileUtils.inputStreamToBytes(inputStream);
/*     */       
/* 837 */       Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
/* 838 */       return new Texture(pixmap);
/* 839 */     } catch (IOException e) {
/* 840 */       throw new RuntimeException("Couldn't load texture", e);
/*     */     } finally {
/* 842 */       if (in != null)
/*     */         try {
/* 844 */           in.close();
/* 845 */         } catch (IOException e) {
/* 846 */           Gdx.app.error("loadTexture", e.toString());
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Texture loadTexture(String internalPath, String paletteFile) {
/* 852 */     InputStream in = null;
/*     */     try {
/* 854 */       in = Gdx.files.internal(internalPath).read();
/* 855 */       InputStream palette = Gdx.files.internal(paletteFile).read();
/*     */       
/* 857 */       ByteArrayInputStream inputStream = PngEditor.swapPalette(in, palette);
/*     */       
/* 859 */       byte[] bytes = FileUtils.inputStreamToBytes(inputStream);
/*     */       
/* 861 */       Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
/* 862 */       return new Texture(pixmap);
/* 863 */     } catch (IOException e) {
/* 864 */       throw new RuntimeException("Couldn't load texture", e);
/*     */     } finally {
/* 866 */       if (in != null)
/*     */         try {
/* 868 */           in.close();
/* 869 */         } catch (IOException e) {
/* 870 */           Gdx.app.error("loadTexture", e.toString());
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getRelativeTeamPath(FileHandle fileHandle) {
/* 876 */     return fileHandle.path().replaceFirst(teamsRootFolder.path(), "");
/*     */   }
/*     */   
/*     */   public static FileHandle getTeamFirstFolder(FileHandle fh) {
/* 880 */     while (!fh.parent().equals(teamsRootFolder)) {
/* 881 */       fh = fh.parent();
/*     */     }
/* 883 */     return fh;
/*     */   }
/*     */   
/* 886 */   public static FilenameFilter teamFilenameFilter = new FilenameFilter() {
/*     */       public boolean accept(File dir, String name) {
/* 888 */         return name.startsWith("team.");
/*     */       }
/*     */     };
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\Assets.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */