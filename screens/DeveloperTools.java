/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import ar.com.hjg.pngj.IImageLine;
/*     */ import ar.com.hjg.pngj.ImageLineInt;
/*     */ import ar.com.hjg.pngj.PngReader;
/*     */ import ar.com.hjg.pngj.PngWriter;
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.TestMatch;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ class DeveloperTools
/*     */   extends GLScreen
/*     */ {
/*     */   DeveloperTools(GLGame game) {
/*  29 */     super(game);
/*  30 */     this.background = new Texture("images/backgrounds/menu_game_options.jpg");
/*     */ 
/*     */ 
/*     */     
/*  34 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, "DEVELOPER TOOLS", 1646512);
/*  35 */     this.widgets.add(titleBar);
/*     */     
/*  37 */     PlayerButton playerButton = new PlayerButton();
/*  38 */     this.widgets.add(playerButton);
/*     */     
/*  40 */     setSelectedWidget((Widget)playerButton);
/*     */     
/*  42 */     KeeperButton keeperButton = new KeeperButton();
/*  43 */     this.widgets.add(keeperButton);
/*     */     
/*  45 */     MatchTestButton matchTestButton = new MatchTestButton();
/*  46 */     this.widgets.add(matchTestButton);
/*     */     
/*  48 */     OptionsButton optionsButton = new OptionsButton();
/*  49 */     this.widgets.add(optionsButton);
/*     */     
/*  51 */     InfoButton infoButton = new InfoButton();
/*  52 */     this.widgets.add(infoButton);
/*     */     
/*  54 */     ExitButton exitButton = new ExitButton();
/*  55 */     this.widgets.add(exitButton);
/*     */   }
/*     */ 
/*     */   
/*     */   private class PlayerButton
/*     */     extends Button
/*     */   {
/*     */     PlayerButton() {
/*  63 */       setColor(8209057);
/*  64 */       Objects.requireNonNull(DeveloperTools.this.game.gui); setGeometry((1280 - 260) / 2, 250, 260, 36);
/*  65 */       setText("PLAYER", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  70 */       DeveloperTools.this.game.setScreen((Screen)new DeveloperPlayer(DeveloperTools.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class KeeperButton
/*     */     extends Button {
/*     */     KeeperButton() {
/*  77 */       setColor(8209057);
/*  78 */       Objects.requireNonNull(DeveloperTools.this.game.gui); setGeometry((1280 - 260) / 2, 300, 260, 36);
/*  79 */       setText("KEEPER", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  84 */       DeveloperTools.this.game.setScreen((Screen)new DeveloperKeeper(DeveloperTools.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class MatchTestButton
/*     */     extends Button {
/*     */     MatchTestButton() {
/*  91 */       setColor(4356769);
/*  92 */       Objects.requireNonNull(DeveloperTools.this.game.gui); setGeometry((1280 - 260) / 2, 350, 260, 36);
/*  93 */       setText("QUICK MATCH", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  98 */       DeveloperTools.this.game.setState(GLGame.State.FRIENDLY, null);
/*     */       
/* 100 */       FileHandle homeTeamFile = Gdx.files.local("/data/teams/1964-65/CLUB_TEAMS/EUROPE/ITALY/team.inter_milan.json");
/* 101 */       Team homeTeam = (Team)Assets.json.fromJson(Team.class, homeTeamFile.readString("UTF-8"));
/* 102 */       homeTeam.path = Assets.getRelativeTeamPath(homeTeamFile);
/* 103 */       homeTeam.controlMode = Team.ControlMode.PLAYER;
/*     */       
/* 105 */       FileHandle awayTeamFile = Gdx.files.local("/data/teams/1964-65/CLUB_TEAMS/EUROPE/ITALY/team.ac_milan.json");
/* 106 */       Team awayTeam = (Team)Assets.json.fromJson(Team.class, awayTeamFile.readString("UTF-8"));
/* 107 */       awayTeam.path = Assets.getRelativeTeamPath(awayTeamFile);
/* 108 */       awayTeam.controlMode = Team.ControlMode.COMPUTER;
/*     */ 
/*     */       
/* 111 */       DeveloperTools.this.game.inputDevices.setAvailability(true);
/* 112 */       homeTeam.setInputDevice(null);
/* 113 */       homeTeam.releaseNonAiInputDevices();
/* 114 */       awayTeam.setInputDevice(null);
/* 115 */       awayTeam.releaseNonAiInputDevices();
/* 116 */       for (InputDevice id : DeveloperTools.this.game.inputDevices) {
/* 117 */         if (id.type == InputDevice.Type.JOYSTICK) {
/* 118 */           homeTeam.inputDevice = id;
/*     */           break;
/*     */         } 
/*     */       } 
/* 122 */       if (homeTeam.inputDevice == null) {
/* 123 */         homeTeam.inputDevice = DeveloperTools.this.game.inputDevices.assignFirstAvailable();
/*     */       }
/*     */       
/* 126 */       TestMatch testMatch = new TestMatch();
/* 127 */       testMatch.getMatch().setTeam(0, homeTeam);
/* 128 */       testMatch.getMatch().setTeam(1, awayTeam);
/*     */       
/* 130 */       DeveloperTools.navigation.competition = (Competition)testMatch;
/* 131 */       DeveloperTools.this.game.setScreen((Screen)new MatchSetup(DeveloperTools.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class OptionsButton
/*     */     extends Button {
/*     */     OptionsButton() {
/* 138 */       setColor(9085250);
/* 139 */       Objects.requireNonNull(DeveloperTools.this.game.gui); setGeometry((1280 - 260) / 2, 400, 260, 36);
/* 140 */       setText("OPTIONS", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 145 */       DeveloperTools.this.game.setScreen((Screen)new DeveloperOptions(DeveloperTools.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class InfoButton
/*     */     extends Button {
/*     */     InfoButton() {
/* 152 */       setColor(9085250);
/* 153 */       Objects.requireNonNull(DeveloperTools.this.game.gui); setGeometry((1280 - 260) / 2, 450, 260, 36);
/* 154 */       setText("INFO", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 159 */       DeveloperTools.this.game.setScreen((Screen)new DeveloperInfo(DeveloperTools.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 166 */       setColor(13124096);
/* 167 */       Objects.requireNonNull(DeveloperTools.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 168 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 173 */       DeveloperTools.this.game.setScreen((Screen)new Main(DeveloperTools.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   static void convert(String origFilename, String destFilename) {
/* 178 */     FileHandle inputFileHandle = Gdx.files.internal("images/" + origFilename);
/* 179 */     PngReader pngr = new PngReader(inputFileHandle.read());
/* 180 */     System.out.println(pngr.toString());
/* 181 */     int channels = pngr.imgInfo.channels;
/*     */ 
/*     */ 
/*     */     
/* 185 */     FileHandle outputFileHandle = Gdx.files.local("images/" + destFilename);
/* 186 */     PngWriter pngw = new PngWriter(outputFileHandle.write(false), pngr.imgInfo);
/* 187 */     pngw.copyChunksFrom(pngr.getChunksList(), 8);
/*     */     
/* 189 */     for (int row = 0; row < pngr.imgInfo.rows; row++) {
/* 190 */       IImageLine l1 = pngr.readRow();
/* 191 */       int[] scanLine = ((ImageLineInt)l1).getScanline();
/* 192 */       for (int j = 0; j < scanLine.length; j++) {
/* 193 */         scanLine[j] = Assets.random.nextInt(255);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 199 */       pngw.writeRow(l1);
/*     */     } 
/* 201 */     pngr.end();
/* 202 */     pngw.end();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DeveloperTools.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */