/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Picture;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */   extends GLScreen
/*     */ {
/*     */   public Main(GLGame game) {
/*  32 */     super(game);
/*  33 */     this.background = new Texture("images/backgrounds/menu_main.jpg");
/*  34 */     TextureRegion logo = Assets.loadTextureRegion("images/logo.png");
/*     */     
/*  36 */     game.teamList.clear();
/*  37 */     game.setState(GLGame.State.NONE, null);
/*  38 */     navigation.folder = Assets.teamsRootFolder;
/*  39 */     navigation.league = null;
/*  40 */     navigation.team = null;
/*     */ 
/*     */ 
/*     */     
/*  44 */     Picture picture = new Picture(logo);
/*  45 */     Objects.requireNonNull(game.gui); picture.setPosition(1280 / 2, 164);
/*  46 */     this.widgets.add(picture);
/*     */     
/*  48 */     GameOptionsButton gameOptionsButton = new GameOptionsButton();
/*  49 */     this.widgets.add(gameOptionsButton);
/*  50 */     setSelectedWidget((Widget)gameOptionsButton);
/*     */     
/*  52 */     MatchOptionsButton matchOptionsButton = new MatchOptionsButton();
/*  53 */     this.widgets.add(matchOptionsButton);
/*     */     
/*  55 */     ControlsButton controlsButton = new ControlsButton();
/*  56 */     this.widgets.add(controlsButton);
/*     */     
/*  58 */     EditTacticsButton editTacticsButton = new EditTacticsButton();
/*  59 */     this.widgets.add(editTacticsButton);
/*     */     
/*  61 */     EditTeamsButton editTeamsButton = new EditTeamsButton();
/*  62 */     this.widgets.add(editTeamsButton);
/*     */     
/*  64 */     FriendlyButton friendlyButton = new FriendlyButton();
/*  65 */     this.widgets.add(friendlyButton);
/*     */     
/*  67 */     DiyCompetitionButton diyCompetitionButton = new DiyCompetitionButton();
/*  68 */     this.widgets.add(diyCompetitionButton);
/*     */     
/*  70 */     PresetCompetitionButton presetCompetitionButton = new PresetCompetitionButton();
/*  71 */     this.widgets.add(presetCompetitionButton);
/*     */     
/*  73 */     TrainingButton trainingButton = new TrainingButton();
/*  74 */     this.widgets.add(trainingButton);
/*     */     
/*  76 */     int y = 510;
/*  77 */     if (game.hasCompetition()) {
/*  78 */       ReplayContinueCompetitionButton replayContinueCompetitionButton = new ReplayContinueCompetitionButton(y);
/*  79 */       this.widgets.add(replayContinueCompetitionButton);
/*     */       
/*  81 */       SaveCompetitionButton saveCompetitionButton = new SaveCompetitionButton(y);
/*  82 */       this.widgets.add(saveCompetitionButton);
/*     */       
/*  84 */       y += 80;
/*     */     } 
/*     */     
/*  87 */     LoadOldCompetitionButton loadOldCompetitionButton = new LoadOldCompetitionButton(y);
/*  88 */     this.widgets.add(loadOldCompetitionButton);
/*     */ 
/*     */     
/*  91 */     Label label = new Label();
/*  92 */     label.setText("", Font.Align.LEFT, Assets.font10);
/*  93 */     Objects.requireNonNull(game.gui); label.setPosition(20, 720 - 29);
/*  94 */     this.widgets.add(label);
/*     */     
/*  96 */     HomePageButton homePageButton = new HomePageButton();
/*  97 */     this.widgets.add(homePageButton);
/*     */     
/*  99 */     if (Settings.development) {
/* 100 */       DeveloperToolsButton developerToolsButton = new DeveloperToolsButton();
/* 101 */       this.widgets.add(developerToolsButton);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class GameOptionsButton
/*     */     extends Button {
/*     */     GameOptionsButton() {
/* 108 */       Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 / 2 - 45 - 350, 270, 350, 36);
/* 109 */       setColors(Integer.valueOf(5467024), Integer.valueOf(7377090), Integer.valueOf(2502978));
/* 110 */       setText(Assets.gettext("GAME OPTIONS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 115 */       Main.this.game.setScreen((Screen)new GameOptions(Main.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class MatchOptionsButton
/*     */     extends Button {
/*     */     MatchOptionsButton() {
/* 122 */       setColor(3688355);
/* 123 */       Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 / 2 - 45 - 350, 315, 350, 36);
/* 124 */       setText(Assets.gettext("MATCH OPTIONS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 129 */       Main.this.game.setScreen((Screen)new MatchOptions(Main.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ControlsButton
/*     */     extends Button {
/*     */     ControlsButton() {
/* 136 */       setColor(8587164);
/* 137 */       Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 / 2 - 45 - 350, 360, 350, 36);
/* 138 */       setText(Assets.gettext("CONTROLS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 143 */       Main.this.game.setScreen((Screen)new SetupControls(Main.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class EditTacticsButton
/*     */     extends Button {
/*     */     EditTacticsButton() {
/* 150 */       setColor(12227078);
/* 151 */       Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 / 2 - 45 - 350, 405, 350, 36);
/* 152 */       setText(Assets.gettext("EDIT TACTICS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 157 */       FileHandle teamFileHandle = Assets.teamsRootFolder.child("CUSTOM/team.electronics.json");
/* 158 */       Main.this.game.tacticsTeam = (Team)Assets.json.fromJson(Team.class, teamFileHandle.readString("UTF-8"));
/* 159 */       Main.this.game.setScreen((Screen)new SelectTactics(Main.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class EditTeamsButton
/*     */     extends Button {
/*     */     EditTeamsButton() {
/* 166 */       setColors(Integer.valueOf(8995355), Integer.valueOf(12278309), Integer.valueOf(4005389));
/* 167 */       Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 / 2 - 45 - 350, 450, 350, 36);
/* 168 */       setText(Assets.gettext("EDIT TEAMS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 173 */       Main.this.game.setState(GLGame.State.EDIT, null);
/* 174 */       Main.navigation.competition = null;
/* 175 */       Main.this.game.setScreen((Screen)new SelectFolder(Main.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class FriendlyButton
/*     */     extends Button {
/*     */     FriendlyButton() {
/* 182 */       Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 / 2 + 45, 270, 350, 36);
/* 183 */       setColors(Integer.valueOf(2983261), Integer.valueOf(4043645), Integer.valueOf(1986599));
/* 184 */       setText(Assets.gettext("FRIENDLY"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 189 */       Main.this.game.setScreen((Screen)new DesignFriendly(Main.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class DiyCompetitionButton
/*     */     extends Button {
/*     */     DiyCompetitionButton() {
/* 196 */       setColors(Integer.valueOf(3632687), Integer.valueOf(5150783), Integer.valueOf(2179092));
/* 197 */       Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 / 2 + 45, 315, 350, 36);
/* 198 */       setText(Assets.gettext("DIY COMPETITION"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 203 */       if (Main.this.game.hasCompetition()) {
/* 204 */         Main.this.game.setScreen((Screen)new CreateCompetitionWarning(Main.this.game, Competition.Category.DIY_COMPETITION));
/*     */       } else {
/* 206 */         Main.this.game.setScreen((Screen)new DiyCompetition(Main.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PresetCompetitionButton
/*     */     extends Button {
/*     */     PresetCompetitionButton() {
/* 214 */       setColors(Integer.valueOf(4281856), Integer.valueOf(6192384), Integer.valueOf(2371584));
/* 215 */       Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 / 2 + 45, 360, 350, 36);
/* 216 */       setText(Assets.gettext("PRESET COMPETITION"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 221 */       if (Main.this.game.hasCompetition()) {
/* 222 */         Main.this.game.setScreen((Screen)new CreateCompetitionWarning(Main.this.game, Competition.Category.PRESET_COMPETITION));
/*     */       } else {
/* 224 */         Main.this.game.setState(GLGame.State.COMPETITION, Competition.Category.PRESET_COMPETITION);
/* 225 */         Main.this.game.setScreen((Screen)new SelectCompetition(Main.this.game, Assets.competitionsRootFolder));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class TrainingButton
/*     */     extends Button {
/*     */     TrainingButton() {
/* 233 */       setColors(Integer.valueOf(1804927), Integer.valueOf(2473390), Integer.valueOf(1136464));
/* 234 */       Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 / 2 + 45, 405, 350, 36);
/* 235 */       setText(Assets.gettext("TRAINING"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/* 240 */       Main.this.game.setState(GLGame.State.TRAINING, null);
/* 241 */       Main.navigation.competition = null;
/* 242 */       Main.this.game.setScreen((Screen)new SelectFolder(Main.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ReplayContinueCompetitionButton
/*     */     extends Button {
/*     */     ReplayContinueCompetitionButton(int y) {
/* 249 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/* 250 */       Objects.requireNonNull(Main.this.game.gui); setGeometry((1280 - 600) / 2, y, 600, 32);
/* 251 */       String s = Assets.gettext(Main.this.game.competition.isEnded() ? "REPLAY %s" : "CONTINUE %s");
/* 252 */       setText(s.replace("%s", Main.this.game.competition.name), Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Up() {
/* 257 */       if (Main.this.game.competition.isEnded()) {
/* 258 */         Main.this.game.competition.restart();
/*     */       }
/*     */       
/* 261 */       switch (Main.this.game.competition.category) {
/*     */         case DIY_COMPETITION:
/*     */         case PRESET_COMPETITION:
/* 264 */           Main.this.game.setState(GLGame.State.COMPETITION, Main.this.game.competition.category);
/* 265 */           switch (Main.this.game.competition.type) {
/*     */             case DIY_COMPETITION:
/* 267 */               Main.this.game.setScreen((Screen)new PlayLeague(Main.this.game));
/*     */               break;
/*     */             
/*     */             case PRESET_COMPETITION:
/* 271 */               Main.this.game.setScreen((Screen)new PlayCup(Main.this.game));
/*     */               break;
/*     */             
/*     */             case null:
/* 275 */               Main.this.game.setScreen((Screen)new PlayTournament(Main.this.game));
/*     */               break;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class SaveCompetitionButton
/*     */     extends Button {
/*     */     SaveCompetitionButton(int y) {
/* 286 */       setColors(Integer.valueOf(13107214), Integer.valueOf(16718121), Integer.valueOf(7603212));
/* 287 */       Objects.requireNonNull(Main.this.game.gui); setGeometry((1280 - 600) / 2, y + 40, 600, 32);
/* 288 */       String s = Assets.gettext("SAVE %s");
/* 289 */       setText(s.replace("%s", Main.this.game.competition.name), Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Up() {
/* 294 */       Main.this.game.setScreen((Screen)new SaveCompetition(Main.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class LoadOldCompetitionButton
/*     */     extends Button {
/*     */     LoadOldCompetitionButton(int y) {
/* 301 */       setColor(2660551);
/* 302 */       Objects.requireNonNull(Main.this.game.gui); setGeometry((1280 - 600) / 2, y, 600, 32);
/* 303 */       setText(Assets.gettext("LOAD OLD COMPETITION"), Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Up() {
/* 308 */       if (Main.this.game.hasCompetition()) {
/* 309 */         Main.this.game.setScreen((Screen)new LoadCompetitionWarning(Main.this.game));
/*     */       } else {
/* 311 */         Main.this.game.setScreen((Screen)new LoadCompetition(Main.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class HomePageButton
/*     */     extends Button {
/*     */     HomePageButton() {
/* 319 */       Objects.requireNonNull(Main.this.game.gui); Objects.requireNonNull(Main.this.game.gui); setGeometry(1280 - 172, 720 - 20, 172, 20);
/* 320 */       setText("YSOCCER.SF.NET", Font.Align.LEFT, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 325 */       Gdx.net.openURI("http://ysoccer.sf.net");
/*     */     }
/*     */   }
/*     */   
/*     */   private class DeveloperToolsButton
/*     */     extends Button {
/*     */     DeveloperToolsButton() {
/* 332 */       setColor(1646512);
/* 333 */       Objects.requireNonNull(Main.this.game.gui); setGeometry((1280 - 300) / 2, 675, 300, 32);
/* 334 */       setText("DEVELOPER TOOLS", Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 339 */       Main.this.game.setScreen((Screen)new DeveloperTools(Main.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\Main.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */