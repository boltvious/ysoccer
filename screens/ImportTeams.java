/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonWriter;
/*     */ import com.ygames.ysoccer.database.ImportConfig;
/*     */ import com.ygames.ysoccer.database.ImportFileConfig;
/*     */ import com.ygames.ysoccer.export.Config;
/*     */ import com.ygames.ysoccer.export.FileConfig;
/*     */ import com.ygames.ysoccer.export.TeamConfig;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Coach;
/*     */ import com.ygames.ysoccer.match.Hair;
/*     */ import com.ygames.ysoccer.match.Kit;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Skin;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class ImportTeams
/*     */   extends GLScreen
/*     */ {
/*     */   private enum State {
/*  41 */     NO_FILES, READY, IMPORTING, FINISHED;
/*     */   }
/*  43 */   private State state = State.NO_FILES;
/*     */   
/*  45 */   private int year = 2019;
/*     */   
/*  47 */   private final String[] divisions = new String[] { "PREMIER DIVISION", "DIVISION ONE", "DIVISION TWO", "DIVISION THREE", "NO LEAGUE" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   static String[] playerCountryCodes = new String[] { "ALB", "AUT", "BEL", "BUL", "CRO", "CYP", "CZE", "DEN", "ENG", "EST", "FRO", "FIN", "FRA", "GER", "GRE", "HUN", "ISL", "ISR", "ITA", "LVA", "LTU", "LUX", "MLT", "NED", "NIR", "NOR", "POL", "POR", "ROM", "RUS", "SMR", "SCO", "SVN", "SWE", "TUR", "UKR", "WAL", "SBM", "BLR", "SVK", "ESP", "ARM", "BIH", "AZE", "GEO", "SUI", "IRL", "MKD", "TKM", "LIE", "MDA", "CRC", "SLV", "GUA", "HON", "BAH", "MEX", "PAN", "USA", "BHR", "NCA", "BER", "JAM", "TRI", "CAN", "BRB", "SLV", "VIN", "ARG", "BOL", "BRA", "CHI", "COL", "ECU", "PAR", "SUR", "URU", "VEN", "GUY", "PER", "ALG", "RSA", "BOT", "BFA", "BDI", "LES", "COD", "ZAM", "GHA", "SEN", "CIV", "TUN", "MLI", "MAD", "CMR", "CHA", "UGA", "LBR", "MOZ", "KEN", "SUD", "SWZ", "ANG", "TOG", "ZIM", "EGY", "TAN", "NGA", "ETH", "GAB", "SLE", "BEN", "CGO", "GUI", "SRI", "MAR", "GAM", "MWI", "JPN", "TPE", "IND", "BAN", "BRU", "IRQ", "JOR", "SRI", "SYR", "KOR", "IRN", "VIE", "MAS", "KSA", "YEM", "KUW", "LAO", "PRK", "OMA", "PAK", "PHI", "CHN", "SIN", "MRI", "MYA", "PNG", "THA", "UZB", "QAT", "UAE", "AUS", "NZL", "FIJ", "SOL", "CUS" };
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
/*     */ 
/*     */ 
/*     */   
/*  74 */   private String[] kitNames = new String[] { "PLAIN", "SLEEVES", "VERTICAL", "HORIZONTAL" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  81 */   private List<Integer> position = new ArrayList<>(); FileHandle[] files;
/*     */   private int fileIndex;
/*     */   private int importedTeams;
/*     */   private int failedTeams;
/*     */   private int skippedFiles;
/*     */   private Widget warningLabel;
/*     */   private Widget exitButton;
/*     */   private Json json;
/*     */   private FileHandle configFile;
/*     */   private Config exportConfigs;
/*  91 */   private Map<String, List<String>> leagues = new HashMap<>();
/*     */   
/*     */   ImportTeams(GLGame game) {
/*  94 */     super(game);
/*     */     
/*  96 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*     */     
/*  98 */     FileHandle importFolder = Gdx.files.local("data/import");
/*     */     
/* 100 */     this.json = new Json();
/* 101 */     this.json.setOutputType(JsonWriter.OutputType.json);
/*     */     
/* 103 */     updateConfigFile();
/*     */     
/* 105 */     this.exportConfigs = new Config();
/*     */     
/* 107 */     for (int i = 0; i < 16; i++) {
/* 108 */       this.position.add(Integer.valueOf(i));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 113 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("IMPORT"), 7744398);
/* 114 */     this.widgets.add(titleBar);
/*     */     
/* 116 */     InfoLabel infoLabel = new InfoLabel();
/* 117 */     this.widgets.add(infoLabel);
/*     */     
/* 119 */     FilenameFilter filter = new FilenameFilter() {
/*     */         public boolean accept(File dir, String name) {
/* 121 */           return name.toUpperCase().startsWith("TEAM.");
/*     */         }
/*     */       };
/* 124 */     this.files = importFolder.list(filter);
/*     */     
/* 126 */     if (this.files.length > 0) {
/* 127 */       this.state = State.READY;
/*     */       
/* 129 */       this.warningLabel = (Widget)new WarningLabel();
/* 130 */       this.widgets.add(this.warningLabel);
/*     */       
/* 132 */       StartButton startButton = new StartButton();
/* 133 */       this.widgets.add(startButton);
/*     */       
/* 135 */       FolderLabel folderLabel = new FolderLabel();
/* 136 */       this.widgets.add(folderLabel);
/*     */       
/* 138 */       FolderButton folderButton = new FolderButton();
/* 139 */       this.widgets.add(folderButton);
/*     */     } 
/*     */     
/* 142 */     this.exitButton = (Widget)new ExitButton();
/* 143 */     this.widgets.add(this.exitButton);
/*     */     
/* 145 */     if (getSelectedWidget() == null) {
/* 146 */       setSelectedWidget(this.exitButton);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float deltaTime) {
/* 152 */     super.render(deltaTime);
/*     */     
/* 154 */     switch (this.state) {
/*     */       case CLUB:
/* 156 */         if (this.fileIndex == this.files.length) {
/* 157 */           this.state = State.FINISHED;
/* 158 */           FileHandle fh = Assets.teamsRootFolder.child(getYearFolder() + "/export_" + getYearFolder() + ".json");
/* 159 */           fh.writeString(Assets.json.prettyPrint(this.exportConfigs), false, "UTF-8");
/*     */         } else {
/* 161 */           ImportConfig importConfig = (ImportConfig)this.json.fromJson(ImportConfig.class, this.configFile.readString("UTF-8"));
/* 162 */           FileHandle fileHandle = this.files[this.fileIndex++];
/* 163 */           for (ImportFileConfig importFileConfig : importConfig.files) {
/* 164 */             if (fileHandle.name().equals(importFileConfig.filename)) {
/* 165 */               if (!importFile(fileHandle, importFileConfig)) {
/* 166 */                 this.skippedFiles++;
/*     */               }
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/* 172 */         for (String folder : this.leagues.keySet()) {
/* 173 */           FileHandle fileHandle = Assets.teamsRootFolder.child(folder).child("leagues.json");
/* 174 */           List<String> names = new ArrayList<>(this.leagues.get(folder));
/* 175 */           fileHandle.writeString(Assets.json.toJson(names, String[].class, String.class), false, "UTF-8");
/*     */         } 
/* 177 */         refreshAllWidgets();
/*     */         break;
/*     */       case NATIONAL:
/* 180 */         refreshAllWidgets();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class InfoLabel
/*     */     extends Label {
/*     */     InfoLabel() {
/* 188 */       Objects.requireNonNull(ImportTeams.this.game.gui); setGeometry((1280 - 400) / 2, 300, 400, 40);
/* 189 */       setText("", Font.Align.CENTER, Assets.font14);
/* 190 */       setActive(false);
/*     */     }
/*     */     
/*     */     public void refresh() {
/*     */       String message;
/* 195 */       switch (ImportTeams.this.state) {
/*     */         case null:
/* 197 */           setText(Assets.gettext("IMPORT.NO FILES"));
/*     */           break;
/*     */         case null:
/* 200 */           setText(Assets.gettext("IMPORT.%n FILES FOUND").replaceFirst("%n", "" + ImportTeams.this.files.length));
/*     */           break;
/*     */         case CLUB:
/* 203 */           setText(Assets.gettext("IMPORT.IMPORTING") + " " + (ImportTeams.this.fileIndex + 1) + "/" + ImportTeams.this.files.length);
/*     */           break;
/*     */         case NATIONAL:
/* 206 */           message = Assets.gettext("IMPORT.%n TEAMS IMPORTED").replaceFirst("%n", "" + ImportTeams.this.importedTeams);
/* 207 */           if (ImportTeams.this.failedTeams > 0) {
/* 208 */             message = message + " - " + Assets.gettext("IMPORT.%n TEAMS NOT IMPORTED").replaceFirst("%n", "" + ImportTeams.this.failedTeams);
/*     */           }
/* 210 */           if (ImportTeams.this.skippedFiles > 0) {
/* 211 */             message = message + " - " + Assets.gettext("IMPORT.%n FILES IGNORED").replaceFirst("5n", "" + ImportTeams.this.skippedFiles);
/*     */           }
/* 213 */           setText(message);
/* 214 */           ImportTeams.this.setSelectedWidget(ImportTeams.this.exitButton);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class FolderLabel
/*     */     extends Button {
/*     */     FolderLabel() {
/* 223 */       setColor(11372032);
/* 224 */       Objects.requireNonNull(ImportTeams.this.game.gui); setGeometry(1280 / 2 - 280, 360, 420, 36);
/* 225 */       setText(Assets.gettext("IMPORT.DESTINATION FOLDER"), Font.Align.CENTER, Assets.font14);
/* 226 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 231 */       setVisible((ImportTeams.this.state == ImportTeams.State.READY));
/*     */     }
/*     */   }
/*     */   
/*     */   private class FolderButton
/*     */     extends Button {
/*     */     FolderButton() {
/* 238 */       setColor(5669376);
/* 239 */       Objects.requireNonNull(ImportTeams.this.game.gui); setGeometry(1280 / 2 + 150, 360, 160, 36);
/* 240 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 245 */       setText(ImportTeams.this.getYearFolder());
/* 246 */       setVisible((ImportTeams.this.state == ImportTeams.State.READY));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 251 */       updateYear(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 256 */       updateYear(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 261 */       updateYear(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 266 */       updateYear(-1);
/*     */     }
/*     */     
/*     */     private void updateYear(int n) {
/* 270 */       ImportTeams.this.year = EMath.slide(ImportTeams.this.year, 1863, 2100, n);
/* 271 */       ImportTeams.this.updateConfigFile();
/* 272 */       setDirty(true);
/* 273 */       ImportTeams.this.warningLabel.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getYearFolder() {
/* 278 */     return this.year + "-" + ("" + (this.year + 1)).substring(2);
/*     */   }
/*     */   
/*     */   private void updateConfigFile() {
/* 282 */     this.configFile = Gdx.files.local("data/config/import_" + getYearFolder() + ".json");
/* 283 */     if (!this.configFile.exists()) {
/* 284 */       this.configFile = Gdx.files.local("data/config/import.json");
/*     */     }
/*     */   }
/*     */   
/*     */   private String getTeamTypeFolder(Team team) {
/* 289 */     switch (team.type) {
/*     */       case CLUB:
/* 291 */         return "CLUB_TEAMS";
/*     */       case NATIONAL:
/* 293 */         return "NATIONAL_TEAMS";
/*     */     } 
/* 295 */     return "";
/*     */   }
/*     */   
/*     */   private class WarningLabel
/*     */     extends Button {
/*     */     WarningLabel() {
/* 301 */       setColor(14417920);
/* 302 */       Objects.requireNonNull(ImportTeams.this.game.gui); setGeometry((1280 - 1020) / 2, 430, 1020, 80);
/* 303 */       setText(Assets.gettext("IMPORT.EXISTING FILES IN THE DESTINATION FOLDER WILL BE OVERWRITTEN"), Font.Align.CENTER, Assets.font14);
/* 304 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 309 */       FileHandle fh = Assets.teamsRootFolder.child(ImportTeams.this.getYearFolder());
/* 310 */       setVisible((fh.isDirectory() && ImportTeams.this.state == ImportTeams.State.READY));
/*     */     }
/*     */   }
/*     */   
/*     */   private class StartButton
/*     */     extends Button {
/*     */     StartButton() {
/* 317 */       setColor(1280801);
/* 318 */       Objects.requireNonNull(ImportTeams.this.game.gui); setGeometry((1280 - 220) / 2, 538, 220, 50);
/* 319 */       setText(Assets.gettext("IMPORT.START"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 324 */       setVisible((ImportTeams.this.state == ImportTeams.State.READY));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 329 */       ImportTeams.this.state = ImportTeams.State.IMPORTING;
/* 330 */       for (Widget widget : ImportTeams.this.widgets)
/* 331 */         widget.setDirty(true); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button
/*     */   {
/*     */     ExitButton() {
/* 339 */       Objects.requireNonNull(ImportTeams.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 340 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 345 */       switch (ImportTeams.this.state) {
/*     */         case NATIONAL:
/*     */         case null:
/* 348 */           setColor(13124096);
/* 349 */           setText(Assets.gettext("EXIT"));
/*     */           break;
/*     */         case null:
/* 352 */           setText(Assets.gettext("ABORT"));
/* 353 */           setColor(13107214);
/*     */           break;
/*     */       } 
/* 356 */       setVisible((ImportTeams.this.state != ImportTeams.State.IMPORTING));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 361 */       ImportTeams.this.game.setScreen((Screen)new Main(ImportTeams.this.game));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean importFile(FileHandle fileHandle, ImportFileConfig importConfig) {
/* 368 */     FileConfig exportConfig = new FileConfig(fileHandle.name());
/*     */     
/* 370 */     byte[] bytes = fileHandle.readBytes();
/*     */ 
/*     */     
/* 373 */     int pos = 1;
/*     */     
/* 375 */     int teams = bytes[pos++] & 0xFF;
/*     */ 
/*     */     
/* 378 */     for (int tm = 0; tm < teams; tm++) {
/* 379 */       pos = importTeam(fileHandle, importConfig, bytes, pos, exportConfig);
/*     */     }
/*     */     
/* 382 */     this.exportConfigs.files.add(exportConfig);
/*     */     
/* 384 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private int importTeam(FileHandle fileHandle, ImportFileConfig importConfig, byte[] bytes, int pos, FileConfig exportConfig) {
/* 389 */     int startingPosition = pos;
/*     */     
/* 391 */     Team team = new Team();
/*     */     
/* 393 */     team.type = importConfig.type;
/*     */     
/* 395 */     int countryIndex = bytes[pos++] & 0xFF;
/*     */     
/* 397 */     String continent = "";
/* 398 */     switch (team.type) {
/*     */       case CLUB:
/* 400 */         team.country = importConfig.country;
/* 401 */         continent = importConfig.continent;
/*     */         break;
/*     */       
/*     */       case NATIONAL:
/* 405 */         continent = importConfig.continent;
/*     */         break;
/*     */     } 
/* 408 */     team.city = "";
/* 409 */     team.stadium = "";
/*     */ 
/*     */     
/* 412 */     pos++;
/*     */ 
/*     */     
/* 415 */     int gtn = (bytes[pos++] & 0xFF) << 8 | bytes[pos++] & 0xFF;
/*     */ 
/*     */     
/* 418 */     pos++;
/*     */ 
/*     */     
/* 421 */     StringBuilder teamNameBuilder = new StringBuilder();
/* 422 */     for (int i = 0; i < 19; i++) {
/* 423 */       int b = bytes[pos++] & 0xFF;
/* 424 */       if (b > 0) {
/* 425 */         teamNameBuilder.append((char)b);
/*     */       }
/*     */     } 
/* 428 */     team.name = teamNameBuilder.toString();
/*     */ 
/*     */     
/* 431 */     team.tactics = bytes[pos++] & 0xFF;
/*     */ 
/*     */     
/* 434 */     int division = bytes[pos++] & 0xFF;
/*     */     
/* 436 */     if (team.type == Team.Type.CLUB) {
/* 437 */       if (importConfig.leagues != null && importConfig.leagues.length > division) {
/* 438 */         team.league = importConfig.leagues[division];
/*     */       } else {
/* 440 */         team.league = this.divisions[division];
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 445 */     team.kits = new ArrayList();
/* 446 */     for (int j = 0; j < 2; j++) {
/* 447 */       Kit kit1 = new Kit();
/* 448 */       int styleIndex = bytes[pos++] & 0xFF;
/* 449 */       kit1.style = this.kitNames[styleIndex];
/* 450 */       kit1.shirt1 = Kit.colors[bytes[pos++] & 0xFF];
/* 451 */       kit1.shirt2 = Kit.colors[bytes[pos++] & 0xFF];
/* 452 */       switch (styleIndex) {
/*     */         case 0:
/*     */         case 1:
/* 455 */           kit1.shirt3 = kit1.shirt1;
/*     */           break;
/*     */         case 2:
/*     */         case 3:
/* 459 */           kit1.shirt3 = kit1.shirt2;
/*     */           break;
/*     */       } 
/* 462 */       kit1.shorts = Kit.colors[bytes[pos++] & 0xFF];
/* 463 */       kit1.socks = Kit.colors[bytes[pos++] & 0xFF];
/* 464 */       team.kits.add(kit1);
/*     */     } 
/*     */ 
/*     */     
/* 468 */     Kit kit = new Kit();
/* 469 */     kit.style = ((Kit)team.kits.get(0)).style;
/* 470 */     kit.shirt1 = ((Kit)team.kits.get(0)).shirt1;
/* 471 */     kit.shirt2 = ((Kit)team.kits.get(0)).shirt2;
/* 472 */     kit.shorts = ((Kit)team.kits.get(1)).shorts;
/* 473 */     kit.socks = ((Kit)team.kits.get(1)).socks;
/* 474 */     team.kits.add(kit);
/*     */ 
/*     */     
/* 477 */     team.coach = new Coach();
/* 478 */     StringBuilder coachNameBuilder = new StringBuilder(); int k;
/* 479 */     for (k = 0; k < 24; k++) {
/* 480 */       int b = bytes[pos++] & 0xFF;
/* 481 */       if (b > 0) {
/* 482 */         coachNameBuilder.append((char)b);
/*     */       }
/*     */     } 
/* 485 */     team.coach.name = coachNameBuilder.toString();
/* 486 */     team.coach.nationality = "";
/*     */ 
/*     */ 
/*     */     
/* 490 */     for (k = 0; k < 16; k++) {
/* 491 */       this.position.set(k, Integer.valueOf(-1));
/*     */     }
/* 493 */     for (k = 0; k < 16; k++) {
/* 494 */       int p = bytes[pos++] & 0xFF;
/* 495 */       if (this.position.contains(Integer.valueOf(p))) {
/* 496 */         Gdx.app.log("Error", "duplicate position: " + p + " in file: " + fileHandle.name() + ", team: " + team.name);
/* 497 */         this.failedTeams++;
/* 498 */         return startingPosition + 684;
/*     */       } 
/* 500 */       this.position.set(k, Integer.valueOf(p));
/*     */     } 
/*     */ 
/*     */     
/* 504 */     team.players = new ArrayList();
/* 505 */     for (k = 0; k < 16; k++) {
/* 506 */       team.players.add(new Player());
/*     */     }
/*     */     
/* 509 */     for (k = 0; k < 16; k++) {
/*     */ 
/*     */       
/* 512 */       Player player = team.players.get(this.position.indexOf(Integer.valueOf(k)));
/*     */ 
/*     */       
/* 515 */       player.nationality = "";
/* 516 */       int nationalityIndex = bytes[pos++] & 0xFF;
/* 517 */       if (nationalityIndex < playerCountryCodes.length) {
/* 518 */         player.nationality = playerCountryCodes[nationalityIndex];
/*     */       }
/*     */ 
/*     */       
/* 522 */       pos++;
/*     */ 
/*     */       
/* 525 */       player.number = bytes[pos++] & 0xFF;
/*     */ 
/*     */       
/* 528 */       boolean surnameFound = false;
/* 529 */       StringBuilder surnameBuilder = new StringBuilder();
/* 530 */       StringBuilder nameBuilder = new StringBuilder();
/* 531 */       for (int m = 1; m <= 22; m++) {
/* 532 */         int n = bytes[pos++] & 0xFF;
/* 533 */         if (n == 32 && !surnameFound) {
/* 534 */           surnameFound = true;
/*     */         }
/* 536 */         else if (n > 0) {
/* 537 */           if (surnameFound) {
/* 538 */             surnameBuilder.append((char)n);
/*     */           } else {
/* 540 */             nameBuilder.append((char)n);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 545 */       if (surnameBuilder.length() == 0) {
/* 546 */         player.shirtName = nameBuilder.toString();
/* 547 */         player.name = nameBuilder.toString();
/*     */       } else {
/* 549 */         player.shirtName = surnameBuilder.toString();
/* 550 */         nameBuilder.append(" ").append(surnameBuilder);
/* 551 */         player.name = nameBuilder.toString();
/*     */       } 
/*     */ 
/*     */       
/* 555 */       pos++;
/*     */ 
/*     */       
/* 558 */       int b = bytes[pos++] & 0xFF;
/* 559 */       player.role = Player.Role.values()[b >> 5];
/* 560 */       int headSkin = b >> 3 & 0x3;
/* 561 */       switch (headSkin) {
/*     */         case 0:
/* 563 */           player.hairColor = Hair.Color.BLACK;
/* 564 */           player.skinColor = Skin.Color.values()[0];
/*     */           break;
/*     */         case 1:
/* 567 */           player.hairColor = Hair.Color.BLOND;
/* 568 */           player.skinColor = Skin.Color.values()[0];
/*     */           break;
/*     */         case 2:
/* 571 */           player.hairColor = Hair.Color.BLACK;
/* 572 */           player.skinColor = Skin.Color.values()[1];
/*     */           break;
/*     */       } 
/* 575 */       player.hairStyle = "SMOOTH_A";
/*     */ 
/*     */       
/* 578 */       pos++;
/*     */ 
/*     */       
/* 581 */       player.skills = new Player.Skills();
/* 582 */       int passing = bytes[pos++] & 0xF;
/* 583 */       player.skills.passing = passing & 0x7;
/* 584 */       if (passing >>> 3 == 1) {
/* 585 */         player.bestSkills.add(Player.Skill.PASSING);
/*     */       }
/*     */       
/* 588 */       b = bytes[pos++] & 0xFF;
/* 589 */       int shooting = b >>> 4;
/* 590 */       player.skills.shooting = shooting & 0x7;
/* 591 */       if (shooting >>> 3 == 1) {
/* 592 */         player.bestSkills.add(Player.Skill.SHOOTING);
/*     */       }
/* 594 */       int heading = b & 0xF;
/* 595 */       player.skills.heading = heading & 0x7;
/* 596 */       if (heading >>> 3 == 1) {
/* 597 */         player.bestSkills.add(Player.Skill.HEADING);
/*     */       }
/*     */       
/* 600 */       b = bytes[pos++] & 0xFF;
/* 601 */       int tackling = b >>> 4;
/* 602 */       player.skills.tackling = tackling & 0x7;
/* 603 */       if (tackling >>> 3 == 1) {
/* 604 */         player.bestSkills.add(Player.Skill.TACKLING);
/*     */       }
/* 606 */       int control = b & 0xF;
/* 607 */       player.skills.control = control & 0x7;
/* 608 */       if (control >>> 3 == 1) {
/* 609 */         player.bestSkills.add(Player.Skill.CONTROL);
/*     */       }
/*     */       
/* 612 */       b = bytes[pos++] & 0xFF;
/* 613 */       int speed = b >>> 4;
/* 614 */       player.skills.speed = speed & 0x7;
/* 615 */       if (speed >>> 3 == 1) {
/* 616 */         player.bestSkills.add(Player.Skill.SPEED);
/*     */       }
/* 618 */       int finishing = b & 0xF;
/* 619 */       player.skills.finishing = finishing & 0x7;
/* 620 */       if (finishing >>> 3 == 1) {
/* 621 */         player.bestSkills.add(Player.Skill.FINISHING);
/*     */       }
/*     */ 
/*     */       
/* 625 */       b = bytes[pos++] & 0xFF;
/* 626 */       player.value = b;
/*     */ 
/*     */       
/* 629 */       pos += 5;
/*     */     } 
/*     */     
/* 632 */     String folder = getYearFolder() + "/" + getTeamTypeFolder(team) + "/";
/* 633 */     switch (team.type) {
/*     */       case CLUB:
/* 635 */         folder = folder + continent + "/" + team.country + "/";
/*     */         break;
/*     */       case NATIONAL:
/* 638 */         folder = folder + continent + "/";
/*     */         break;
/*     */     } 
/*     */     
/* 642 */     String cleanName = team.name.toLowerCase().replace(" ", "_").replace("/", "_").replace(".", "");
/* 643 */     team.path = folder + "team." + cleanName + ".json";
/*     */     
/* 645 */     team.persist();
/*     */     
/* 647 */     exportConfig.teams.add(new TeamConfig(team.path, countryIndex, gtn, division));
/*     */     
/* 649 */     if (team.type == Team.Type.CLUB) {
/* 650 */       if (importConfig.leagues != null) {
/* 651 */         this.leagues.put(folder, Arrays.asList(importConfig.leagues));
/* 652 */       } else if (this.leagues.get(folder) == null) {
/* 653 */         List<String> list = new ArrayList<>();
/* 654 */         list.add(team.league);
/* 655 */         this.leagues.put(folder, list);
/*     */       } else {
/* 657 */         List<String> list = this.leagues.get(folder);
/* 658 */         if (!list.contains(team.league)) {
/* 659 */           list.add(team.league);
/*     */         }
/* 661 */         this.leagues.put(folder, list);
/*     */       } 
/*     */     }
/*     */     
/* 665 */     this.importedTeams++;
/*     */     
/* 667 */     return pos;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\ImportTeams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */