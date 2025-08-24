/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonWriter;
/*     */ import com.ygames.ysoccer.export.Config;
/*     */ import com.ygames.ysoccer.export.FileConfig;
/*     */ import com.ygames.ysoccer.export.TeamConfig;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLColor;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Hair;
/*     */ import com.ygames.ysoccer.match.Kit;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Skin;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.text.Normalizer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ExportTeams
/*     */   extends GLScreen
/*     */ {
/*     */   private enum State
/*     */   {
/*  44 */     NO_FOLDERS, READY, EXPORTING, FINISHED;
/*     */   }
/*  46 */   private State state = State.NO_FOLDERS;
/*     */   
/*     */   private Json json;
/*     */   
/*     */   private Config exportConfig;
/*     */   private KitStyle[] kitStyles;
/*     */   private int exportConfigIndex;
/*     */   private FileHandle exportFolder;
/*     */   private int exportedTeams;
/*     */   private int notFoundTeams;
/*     */   private Widget exitButton;
/*     */   
/*     */   ExportTeams(GLGame game) {
/*  59 */     super(game);
/*     */     
/*  61 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*     */     
/*  63 */     this.json = new Json();
/*  64 */     this.json.setOutputType(JsonWriter.OutputType.json);
/*     */     
/*  66 */     this.exportFolder = Gdx.files.local("data/export");
/*  67 */     this.kitStyles = (KitStyle[])this.json.fromJson(KitStyle[].class, Gdx.files.local("data/config/kit_styles.json").readString("UTF-8"));
/*     */ 
/*     */ 
/*     */     
/*  71 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("EXPORT"), 7744398);
/*  72 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  75 */     List<Widget> list = new ArrayList<>();
/*  76 */     ArrayList<FileHandle> files = new ArrayList<>(Arrays.asList(Assets.teamsRootFolder.list()));
/*  77 */     Collections.sort(files, Assets.fileComparatorByName);
/*  78 */     for (FileHandle file : files) {
/*  79 */       if (file.isDirectory()) {
/*  80 */         FolderButton folderButton = new FolderButton(file);
/*  81 */         list.add(folderButton);
/*  82 */         this.widgets.add(folderButton);
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     if (list.size() > 0) {
/*  87 */       this.state = State.READY;
/*  88 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, 380, 34, 20, list);
/*  89 */       setSelectedWidget(list.get(0));
/*     */     } 
/*  91 */     InfoLabel infoLabel = new InfoLabel();
/*  92 */     this.widgets.add(infoLabel);
/*     */     
/*  94 */     this.exitButton = (Widget)new ExitButton();
/*  95 */     this.widgets.add(this.exitButton);
/*     */     
/*  97 */     if (getSelectedWidget() == null)
/*  98 */       setSelectedWidget(this.exitButton); 
/*     */   }
/*     */   
/*     */   private class FolderButton
/*     */     extends Button
/*     */   {
/*     */     private FileHandle configFile;
/*     */     
/*     */     FolderButton(FileHandle folder) {
/* 107 */       this.configFile = Gdx.files.local("data/config/export_" + folder.name() + ".json");
/* 108 */       setSize(340, 32);
/* 109 */       if (this.configFile.exists()) {
/* 110 */         setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/*     */       } else {
/* 112 */         setColor(6710886);
/* 113 */         setActive(false);
/*     */       } 
/* 115 */       setText(folder.name().replace('_', ' '), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 120 */       setVisible((ExportTeams.this.state == ExportTeams.State.READY));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 125 */       ExportTeams.this.exportConfig = (Config)ExportTeams.this.json.fromJson(Config.class, this.configFile.readString("UTF-8"));
/* 126 */       ExportTeams.this.state = ExportTeams.State.EXPORTING;
/* 127 */       ExportTeams.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class InfoLabel
/*     */     extends Label {
/*     */     InfoLabel() {
/* 134 */       Objects.requireNonNull(ExportTeams.this.game.gui); setGeometry((1280 - 400) / 2, 300, 400, 40);
/* 135 */       setText("", Font.Align.CENTER, Assets.font14);
/* 136 */       setActive(false);
/*     */     }
/*     */     
/*     */     public void refresh() {
/*     */       String message;
/* 141 */       switch (ExportTeams.this.state) {
/*     */         case PINK:
/* 143 */           setText("NO FOLDERS");
/*     */           break;
/*     */ 
/*     */         
/*     */         case PALE:
/* 148 */           setText("EXPORTING " + (ExportTeams.this.exportConfigIndex + 1) + "/" + ExportTeams.this.exportConfig.files.size());
/*     */           break;
/*     */         case ASIATIC:
/* 151 */           message = ExportTeams.this.exportedTeams + " TEAMS EXPORTED";
/* 152 */           if (ExportTeams.this.notFoundTeams > 0) message = message + " - " + ExportTeams.this.notFoundTeams + " TEAMS NOT FOUND"; 
/* 153 */           setText(message);
/*     */           break;
/*     */       } 
/* 156 */       setVisible((ExportTeams.this.state != ExportTeams.State.READY));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 163 */       Objects.requireNonNull(ExportTeams.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 164 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 169 */       switch (ExportTeams.this.state) {
/*     */         case PINK:
/*     */         case ASIATIC:
/* 172 */           setColor(13124096);
/* 173 */           setText(Assets.gettext("EXIT"));
/*     */           break;
/*     */         case BLACK:
/* 176 */           setText(Assets.gettext("ABORT"));
/* 177 */           setColor(13107214);
/*     */           break;
/*     */       } 
/* 180 */       setVisible((ExportTeams.this.state != ExportTeams.State.EXPORTING));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 185 */       ExportTeams.this.game.setScreen((Screen)new Main(ExportTeams.this.game));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float deltaTime) {
/* 191 */     super.render(deltaTime);
/*     */     
/* 193 */     switch (this.state) {
/*     */       case PALE:
/* 195 */         if (this.exportConfigIndex < this.exportConfig.files.size()) {
/* 196 */           FileConfig exportFileConfig = this.exportConfig.files.get(this.exportConfigIndex++);
/* 197 */           exportFile(exportFileConfig);
/*     */         } else {
/* 199 */           this.state = State.FINISHED;
/*     */         } 
/* 201 */         refreshAllWidgets();
/*     */         break;
/*     */       case ASIATIC:
/* 204 */         setSelectedWidget(this.exitButton);
/* 205 */         refreshAllWidgets();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean exportFile(FileConfig exportConfig) {
/* 212 */     FileHandle file = this.exportFolder.child(exportConfig.filename);
/*     */ 
/*     */     
/* 215 */     int numberOfTeams = 0;
/* 216 */     for (TeamConfig teamConfig : exportConfig.teams) {
/* 217 */       FileHandle sourceFile = Assets.teamsRootFolder.child(teamConfig.path);
/* 218 */       if (sourceFile.exists()) {
/* 219 */         teamConfig.sourceFile = sourceFile;
/* 220 */         numberOfTeams++; continue;
/*     */       } 
/* 222 */       Gdx.app.log("File not found", sourceFile.path());
/* 223 */       this.notFoundTeams++;
/*     */     } 
/*     */     
/* 226 */     file.writeBytes(getHalfWord(numberOfTeams), false);
/*     */     
/* 228 */     int teamIndex = 0;
/* 229 */     for (TeamConfig teamConfig : exportConfig.teams) {
/* 230 */       if (teamConfig.sourceFile == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 234 */       Team team = (Team)this.json.fromJson(Team.class, teamConfig.sourceFile);
/*     */       
/* 236 */       file.writeBytes(getByte(teamConfig.country), true);
/*     */       
/* 238 */       file.writeBytes(getByte(teamIndex), true);
/*     */       
/* 240 */       file.writeBytes(getHalfWord(teamConfig.gtn), true);
/*     */ 
/*     */       
/* 243 */       file.writeBytes(getByte(0), true);
/*     */ 
/*     */       
/* 246 */       writePaddedString(file, decompose(team.name), 16);
/*     */ 
/*     */       
/* 249 */       file.writeBytes(getByte(0), true);
/* 250 */       file.writeBytes(getByte(0), true);
/* 251 */       file.writeBytes(getByte(0), true);
/*     */       
/* 253 */       file.writeBytes(getByte(team.tactics), true);
/*     */       
/* 255 */       file.writeBytes(getByte(teamConfig.division), true);
/*     */       
/*     */       int i;
/* 258 */       for (i = 0; i < 2; i++) {
/* 259 */         Kit kit = team.kits.get(i);
/* 260 */         file.writeBytes(getByte(getKitStyleIndex(kit.style)), true);
/* 261 */         file.writeBytes(getByte(getKitColorIndex(kit.shirt1)), true);
/* 262 */         file.writeBytes(getByte(getKitColorIndex(kit.shirt2)), true);
/* 263 */         file.writeBytes(getByte(getKitColorIndex(kit.shorts)), true);
/* 264 */         file.writeBytes(getByte(getKitColorIndex(kit.socks)), true);
/*     */       } 
/*     */ 
/*     */       
/* 268 */       writePaddedString(file, decompose(team.coach.name), 24);
/*     */ 
/*     */       
/* 271 */       for (i = 0; i < 16; i++) {
/* 272 */         file.writeBytes(getByte(i), true);
/*     */       }
/*     */ 
/*     */       
/* 276 */       for (i = 0; i < 16; i++) {
/* 277 */         int hairColor; Player player = team.players.get(i);
/*     */         
/* 279 */         file.writeBytes(getByte(getPlayerNationalityIndex(player.nationality)), true);
/*     */ 
/*     */         
/* 282 */         file.writeBytes(getByte(0), true);
/*     */ 
/*     */         
/* 285 */         file.writeBytes(getByte(player.number), true);
/*     */ 
/*     */         
/* 288 */         writePaddedString(file, decompose(player.name), 22);
/*     */ 
/*     */         
/* 291 */         file.writeBytes(getByte(0), true);
/*     */ 
/*     */         
/* 294 */         int role = player.role.ordinal();
/* 295 */         int skinColor = getSkinColorIndex(player.skinColor);
/*     */         
/* 297 */         if (skinColor == 0) {
/* 298 */           hairColor = getHairColorIndex(player.hairColor);
/*     */         } else {
/* 300 */           hairColor = 0;
/*     */         } 
/* 302 */         file.writeBytes(getByte(role << 5 | skinColor << 4 | hairColor << 3), true);
/*     */ 
/*     */         
/* 305 */         file.writeBytes(getByte(0), true);
/*     */         
/* 307 */         Player.Skills skills = player.skills;
/* 308 */         int passing = skills.passing;
/* 309 */         if (player.role != Player.Role.GOALKEEPER && player.bestSkills.contains(Player.Skill.PASSING)) {
/* 310 */           passing |= 0x8;
/*     */         }
/* 312 */         file.writeBytes(getByte(passing), true);
/*     */         
/* 314 */         int shooting = skills.shooting;
/* 315 */         if (player.role != Player.Role.GOALKEEPER && player.bestSkills.contains(Player.Skill.SHOOTING)) {
/* 316 */           shooting |= 0x8;
/*     */         }
/* 318 */         int heading = skills.heading;
/* 319 */         if (player.role != Player.Role.GOALKEEPER && player.bestSkills.contains(Player.Skill.HEADING)) {
/* 320 */           heading |= 0x8;
/*     */         }
/* 322 */         file.writeBytes(getByte(shooting << 4 | heading), true);
/*     */         
/* 324 */         int tackling = skills.tackling;
/* 325 */         if (player.role != Player.Role.GOALKEEPER && player.bestSkills.contains(Player.Skill.TACKLING)) {
/* 326 */           tackling |= 0x8;
/*     */         }
/* 328 */         int control = skills.control;
/* 329 */         if (player.role != Player.Role.GOALKEEPER && player.bestSkills.contains(Player.Skill.CONTROL)) {
/* 330 */           control |= 0x8;
/*     */         }
/* 332 */         file.writeBytes(getByte(tackling << 4 | control), true);
/*     */         
/* 334 */         int speed = skills.speed;
/* 335 */         if (player.role != Player.Role.GOALKEEPER && player.bestSkills.contains(Player.Skill.SPEED)) {
/* 336 */           speed |= 0x8;
/*     */         }
/* 338 */         int finishing = skills.finishing;
/* 339 */         if (player.role != Player.Role.GOALKEEPER && player.bestSkills.contains(Player.Skill.FINISHING)) {
/* 340 */           finishing |= 0x8;
/*     */         }
/* 342 */         file.writeBytes(getByte(speed << 4 | finishing), true);
/*     */ 
/*     */         
/* 345 */         file.writeBytes(getByte(player.getValue()), true);
/*     */ 
/*     */         
/* 348 */         file.writeBytes(getByte(0), true);
/*     */ 
/*     */         
/* 351 */         file.writeBytes(getByte(0), true);
/*     */ 
/*     */         
/* 354 */         file.writeBytes(getByte(0), true);
/* 355 */         file.writeBytes(getByte(0), true);
/* 356 */         file.writeBytes(getByte(0), true);
/*     */       } 
/*     */       
/* 359 */       this.exportedTeams++;
/*     */       
/* 361 */       teamIndex++;
/*     */     } 
/*     */     
/* 364 */     return true;
/*     */   }
/*     */   
/*     */   private byte[] getByte(int i) {
/* 368 */     return new byte[] { (byte)i };
/*     */   }
/*     */   
/*     */   private byte[] getHalfWord(int i) {
/* 372 */     return new byte[] { (byte)(i >>> 8), (byte)(i & 0xFF) };
/*     */   }
/*     */   
/*     */   private void writePaddedString(FileHandle file, String string, int totalLength) {
/* 376 */     if (string.length() > totalLength) {
/* 377 */       file.writeString(string.substring(0, totalLength), true);
/*     */     } else {
/* 379 */       file.writeString(string, true);
/* 380 */       padWithZeroes(file, string.length(), totalLength);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void padWithZeroes(FileHandle file, int stringLength, int totaLength) {
/* 385 */     if (stringLength < totaLength) {
/* 386 */       for (int i = stringLength; i < totaLength; i++) {
/* 387 */         file.writeBytes(getByte(0), true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private int getKitStyleIndex(String style) {
/* 393 */     for (KitStyle kitStyle : this.kitStyles) {
/* 394 */       if (kitStyle.name.equals(style)) {
/* 395 */         return kitStyle.index;
/*     */       }
/*     */     } 
/* 398 */     return 0;
/*     */   }
/*     */   
/*     */   private int getKitColorIndex(int color) {
/* 402 */     int index = 0;
/* 403 */     float difference = GLColor.difference(color, Kit.colors[0]);
/* 404 */     for (int i = 1; i < 10; i++) {
/* 405 */       if (GLColor.difference(color, Kit.colors[i]) < difference) {
/* 406 */         difference = GLColor.difference(color, Kit.colors[i]);
/* 407 */         index = i;
/*     */       } 
/*     */     } 
/* 410 */     return index;
/*     */   }
/*     */   
/*     */   private int getPlayerNationalityIndex(String nationality) {
/* 414 */     for (int i = 0; i < ImportTeams.playerCountryCodes.length; i++) {
/* 415 */       if (ImportTeams.playerCountryCodes[i].equals(nationality)) return i; 
/*     */     } 
/* 417 */     return 0;
/*     */   }
/*     */   
/*     */   private int getHairColorIndex(Hair.Color color) {
/* 421 */     switch (color) {
/*     */       case PINK:
/* 423 */         return 0;
/*     */       case BLACK:
/* 425 */         return 1;
/*     */       case PALE:
/* 427 */         return 0;
/*     */       case ASIATIC:
/* 429 */         return 0;
/*     */       case ARAB:
/* 431 */         return 1;
/*     */       case MULATTO:
/* 433 */         return 1;
/*     */       case RED:
/* 435 */         return 0;
/*     */       case null:
/* 437 */         return 1;
/*     */       case null:
/* 439 */         return 1;
/*     */     } 
/* 441 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getSkinColorIndex(Skin.Color color) {
/* 446 */     switch (color) {
/*     */       case PINK:
/* 448 */         return 0;
/*     */       case BLACK:
/* 450 */         return 1;
/*     */       case PALE:
/* 452 */         return 0;
/*     */       case ASIATIC:
/* 454 */         return 0;
/*     */       case ARAB:
/* 456 */         return 0;
/*     */       case MULATTO:
/* 458 */         return 1;
/*     */       case RED:
/* 460 */         return 0;
/*     */     } 
/* 462 */     return 0;
/*     */   }
/*     */   
/*     */   private static class KitStyle
/*     */   {
/*     */     String name;
/*     */     int index;
/*     */   }
/*     */   
/*     */   private static String decompose(String s) {
/* 472 */     return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\ExportTeams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */