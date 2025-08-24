/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SelectFolder
/*     */   extends GLScreen
/*     */ {
/*     */   SelectFolder(GLGame game) {
/*  27 */     super(game); SearchTeamButton searchTeamButton; ViewSelectedTeamsButton viewSelectedTeamsButton; SearchPlayerButton searchPlayerButton; PlayButton playButton;
/*     */     boolean isDataRoot;
/*  29 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  33 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, getTitle(), game.stateColor.body.intValue());
/*  34 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  37 */     ArrayList<Widget> breadcrumb = new ArrayList<>();
/*     */     
/*  39 */     FileHandle fh = navigation.folder;
/*     */     
/*     */     do {
/*  42 */       isDataRoot = fh.equals(Assets.teamsRootFolder);
/*  43 */       BreadCrumbButton breadCrumbButton = new BreadCrumbButton(fh, isDataRoot);
/*  44 */       breadcrumb.add(breadCrumbButton);
/*  45 */       fh = fh.parent();
/*  46 */     } while (!isDataRoot);
/*     */     
/*  48 */     Collections.reverse(breadcrumb);
/*  49 */     Objects.requireNonNull(game.gui); int x = (1280 - 960) / 2;
/*  50 */     for (Widget b : breadcrumb) {
/*  51 */       b.setPosition(x, 72);
/*  52 */       x += b.w + 2;
/*     */     } 
/*  54 */     this.widgets.addAll(breadcrumb);
/*     */ 
/*     */     
/*  57 */     ArrayList<Widget> list = new ArrayList<>();
/*     */     
/*  59 */     ArrayList<FileHandle> files = new ArrayList<>(Arrays.asList(navigation.folder.list()));
/*  60 */     Collections.sort(files, Assets.fileComparatorByName);
/*  61 */     for (FileHandle file : files) {
/*  62 */       if (file.isDirectory()) {
/*  63 */         FolderButton folderButton = new FolderButton(file);
/*  64 */         list.add(folderButton);
/*  65 */         this.widgets.add(folderButton);
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     if (navigation.folder.equals(Assets.teamsRootFolder) && Assets.favourites.size() > 0) {
/*  70 */       FavouritesButton favouritesButton = new FavouritesButton();
/*  71 */       list.add(favouritesButton);
/*  72 */       this.widgets.add(favouritesButton);
/*     */     } 
/*     */     
/*  75 */     if (list.size() > 0) {
/*  76 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, 380, 34, 20, list);
/*  77 */       setSelectedWidget(list.get(0));
/*     */     } 
/*     */     
/*  80 */     switch (game.getState()) {
/*     */       case FRIENDLY:
/*     */       case LEAGUE:
/*  83 */         searchTeamButton = new SearchTeamButton();
/*  84 */         this.widgets.add(searchTeamButton);
/*     */         break;
/*     */       
/*     */       case CUP:
/*     */       case TOURNAMENT:
/*  89 */         viewSelectedTeamsButton = new ViewSelectedTeamsButton();
/*  90 */         this.widgets.add(viewSelectedTeamsButton);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/*  95 */     AbortButton abortButton = new AbortButton();
/*  96 */     this.widgets.add(abortButton);
/*  97 */     if (getSelectedWidget() == null) {
/*  98 */       setSelectedWidget((Widget)abortButton);
/*     */     }
/*     */     
/* 101 */     switch (game.getState()) {
/*     */       case FRIENDLY:
/* 103 */         searchPlayerButton = new SearchPlayerButton();
/* 104 */         this.widgets.add(searchPlayerButton);
/*     */         break;
/*     */       
/*     */       case CUP:
/*     */       case TOURNAMENT:
/* 109 */         playButton = new PlayButton();
/* 110 */         this.widgets.add(playButton);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   private String getTitle() {
/*     */     int diff;
/* 116 */     String title = "";
/* 117 */     switch (this.game.getState()) {
/*     */       case CUP:
/*     */       case TOURNAMENT:
/* 120 */         diff = navigation.competition.numberOfTeams - this.game.teamList.numberOfTeams();
/* 121 */         title = Assets.gettext((diff == 0) ? "CHANGE TEAMS FOR" : "CHOOSE TEAMS FOR") + " " + navigation.competition.name;
/*     */         break;
/*     */ 
/*     */       
/*     */       case FRIENDLY:
/* 126 */         title = Assets.gettext("EDIT TEAMS");
/*     */         break;
/*     */       
/*     */       case LEAGUE:
/* 130 */         title = Assets.gettext("TRAINING");
/*     */         break;
/*     */     } 
/* 133 */     return title;
/*     */   }
/*     */   
/*     */   private class BreadCrumbButton
/*     */     extends Button {
/*     */     private FileHandle folder;
/*     */     
/*     */     BreadCrumbButton(FileHandle folder, boolean isDataRoot) {
/* 141 */       this.folder = folder;
/* 142 */       setSize(0, 32);
/* 143 */       if (folder == SelectFolder.navigation.folder) {
/* 144 */         setColors(SelectFolder.this.game.stateColor.darker());
/* 145 */         setActive(false);
/*     */       } else {
/* 147 */         setColors(SelectFolder.this.game.stateColor);
/*     */       } 
/* 149 */       setText(isDataRoot ? "\024" : folder.name().replace('_', ' '), Font.Align.CENTER, Assets.font10);
/* 150 */       autoWidth();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 155 */       SelectFolder.navigation.folder = this.folder;
/* 156 */       SelectFolder.navigation.league = null;
/* 157 */       SelectFolder.this.game.setScreen((Screen)new SelectFolder(SelectFolder.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class FolderButton
/*     */     extends Button {
/*     */     private FileHandle fileHandle;
/*     */     
/*     */     FolderButton(FileHandle fileHandle) {
/* 166 */       this.fileHandle = fileHandle;
/* 167 */       setSize(340, 32);
/* 168 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/* 169 */       setText(fileHandle.name().replace('_', ' '), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 174 */       FileHandle[] teamFileHandles = this.fileHandle.list(Assets.teamFilenameFilter);
/* 175 */       if (teamFileHandles.length > 0) {
/* 176 */         switch (SelectFolder.this.game.getState()) {
/*     */           case CUP:
/*     */           case TOURNAMENT:
/* 179 */             SelectFolder.navigation.folder = this.fileHandle;
/* 180 */             SelectFolder.navigation.league = null;
/* 181 */             SelectFolder.this.game.setScreen((Screen)new SelectTeams(SelectFolder.this.game));
/*     */             break;
/*     */           
/*     */           case FRIENDLY:
/*     */           case LEAGUE:
/* 186 */             SelectFolder.navigation.folder = this.fileHandle;
/* 187 */             SelectFolder.navigation.league = null;
/* 188 */             SelectFolder.this.game.setScreen((Screen)new SelectTeam(SelectFolder.this.game));
/*     */             break;
/*     */         } 
/*     */       } else {
/* 192 */         SelectFolder.navigation.folder = this.fileHandle;
/* 193 */         SelectFolder.this.game.setScreen((Screen)new SelectFolder(SelectFolder.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class FavouritesButton
/*     */     extends Button {
/*     */     FavouritesButton() {
/* 201 */       setSize(340, 32);
/* 202 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/* 203 */       setText(Assets.gettext("FAVOURITES"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 208 */       switch (SelectFolder.this.game.getState()) {
/*     */         case CUP:
/*     */         case TOURNAMENT:
/* 211 */           SelectFolder.navigation.folder = Assets.favouritesFile;
/* 212 */           SelectFolder.this.game.setScreen((Screen)new SelectFavourites(SelectFolder.this.game));
/*     */           break;
/*     */         
/*     */         case FRIENDLY:
/*     */         case LEAGUE:
/* 217 */           SelectFolder.navigation.folder = Assets.favouritesFile;
/* 218 */           SelectFolder.this.game.setScreen((Screen)new SelectFavourite(SelectFolder.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewSelectedTeamsButton
/*     */     extends Button {
/*     */     ViewSelectedTeamsButton() {
/* 227 */       Objects.requireNonNull(SelectFolder.this.game.gui); setGeometry((1280 - 180) / 2 - 360 - 20, 660, 360, 36);
/* 228 */       setColors(Integer.valueOf(10120348), Integer.valueOf(12229051), Integer.valueOf(5187919));
/* 229 */       setText(Assets.gettext("VIEW SELECTED TEAMS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 234 */       SelectFolder.this.game.setScreen((Screen)new AllSelectedTeams(SelectFolder.this.game));
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 239 */       setVisible((SelectFolder.this.game.teamList.numberOfTeams() > 0));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SearchTeamButton
/*     */     extends Button {
/*     */     SearchTeamButton() {
/* 246 */       setColor(4474026);
/* 247 */       setText(Assets.gettext("SEARCH.SEARCH TEAMS"), Font.Align.CENTER, Assets.font14);
/* 248 */       Objects.requireNonNull(SelectFolder.this.game.gui); setGeometry((1280 - 180) / 2 - 360 - 20, 660, 360, 36);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 253 */       setVisible(!SelectFolder.navigation.folder.equals(Assets.teamsRootFolder));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 258 */       SelectFolder.this.game.setScreen((Screen)new SearchTeams(SelectFolder.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 265 */       setColor(13107214);
/* 266 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/* 267 */       Objects.requireNonNull(SelectFolder.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 272 */       SelectFolder.this.game.setScreen((Screen)new Main(SelectFolder.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayButton
/*     */     extends Button {
/*     */     PlayButton() {
/* 279 */       Objects.requireNonNull(SelectFolder.this.game.gui); setGeometry(1280 / 2 + 110, 660, 360, 36);
/* 280 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 285 */       setVisible((SelectFolder.this.game.teamList.numberOfTeams() > 0));
/* 286 */       int diff = SelectFolder.navigation.competition.numberOfTeams - SelectFolder.this.game.teamList.numberOfTeams();
/* 287 */       if (diff == 0) {
/* 288 */         switch (SelectFolder.navigation.competition.type) {
/*     */           case FRIENDLY:
/* 290 */             setText(Assets.gettext("PLAY FRIENDLY"));
/*     */             break;
/*     */           
/*     */           case LEAGUE:
/* 294 */             setText(Assets.gettext("PLAY LEAGUE"));
/*     */             break;
/*     */           
/*     */           case CUP:
/* 298 */             setText(Assets.gettext("PLAY CUP"));
/*     */             break;
/*     */           
/*     */           case TOURNAMENT:
/* 302 */             setText(Assets.gettext("PLAY TOURNAMENT"));
/*     */             break;
/*     */         } 
/* 305 */         setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 306 */         setActive(true);
/*     */       } else {
/* 308 */         if (diff > 1) {
/* 309 */           setText(Assets.gettext("SELECT %n MORE TEAMS").replace("%n", "" + diff));
/* 310 */         } else if (diff == 1) {
/* 311 */           setText(Assets.gettext("SELECT 1 MORE TEAM"));
/* 312 */         } else if (diff == -1) {
/* 313 */           setText(Assets.gettext("SELECT 1 LESS TEAM"));
/*     */         } else {
/* 315 */           setText(Assets.gettext("SELECT %n LESS TEAMS").replace("%n", "" + -diff));
/*     */         } 
/* 317 */         setColors(null);
/* 318 */         setActive(false);
/*     */       } 
/*     */     }
/*     */     public void onFire1Down() {
/*     */       Team homeTeam, awayTeam;
/*     */       Match match;
/* 324 */       SelectFolder.this.game.teamList.removeNullValues();
/* 325 */       switch (SelectFolder.navigation.competition.type) {
/*     */         case FRIENDLY:
/* 327 */           homeTeam = (Team)SelectFolder.this.game.teamList.get(0);
/* 328 */           awayTeam = (Team)SelectFolder.this.game.teamList.get(1);
/*     */           
/* 330 */           match = SelectFolder.navigation.competition.getMatch();
/* 331 */           match.setTeam(0, homeTeam);
/* 332 */           match.setTeam(1, awayTeam);
/*     */ 
/*     */           
/* 335 */           SelectFolder.this.game.inputDevices.setAvailability(true);
/* 336 */           homeTeam.setInputDevice(null);
/* 337 */           homeTeam.releaseNonAiInputDevices();
/* 338 */           awayTeam.setInputDevice(null);
/* 339 */           awayTeam.releaseNonAiInputDevices();
/*     */ 
/*     */           
/* 342 */           if (homeTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 343 */             if (SelectFolder.this.lastFireInputDevice != null) {
/* 344 */               homeTeam.setInputDevice(SelectFolder.this.lastFireInputDevice);
/*     */             }
/* 346 */             SelectFolder.navigation.team = homeTeam;
/* 347 */             SelectFolder.this.game.setScreen((Screen)new SetTeam(SelectFolder.this.game)); break;
/* 348 */           }  if (awayTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 349 */             if (SelectFolder.this.lastFireInputDevice != null) {
/* 350 */               awayTeam.setInputDevice(SelectFolder.this.lastFireInputDevice);
/*     */             }
/* 352 */             SelectFolder.navigation.team = awayTeam;
/* 353 */             SelectFolder.this.game.setScreen((Screen)new SetTeam(SelectFolder.this.game)); break;
/*     */           } 
/* 355 */           SelectFolder.this.game.setScreen((Screen)new MatchSetup(SelectFolder.this.game));
/*     */           break;
/*     */ 
/*     */         
/*     */         case LEAGUE:
/* 360 */           SelectFolder.navigation.competition.start((ArrayList)SelectFolder.this.game.teamList);
/* 361 */           SelectFolder.this.game.setCompetition(SelectFolder.navigation.competition);
/* 362 */           SelectFolder.this.game.setScreen((Screen)new PlayLeague(SelectFolder.this.game));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 366 */           SelectFolder.navigation.competition.start((ArrayList)SelectFolder.this.game.teamList);
/* 367 */           SelectFolder.this.game.setCompetition(SelectFolder.navigation.competition);
/* 368 */           SelectFolder.this.game.setScreen((Screen)new PlayCup(SelectFolder.this.game));
/*     */           break;
/*     */         
/*     */         case TOURNAMENT:
/* 372 */           SelectFolder.navigation.competition.start((ArrayList)SelectFolder.this.game.teamList);
/* 373 */           SelectFolder.this.game.setCompetition(SelectFolder.navigation.competition);
/* 374 */           SelectFolder.this.game.setScreen((Screen)new PlayTournament(SelectFolder.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class SearchPlayerButton
/*     */     extends Button {
/*     */     SearchPlayerButton() {
/* 383 */       setColor(4474026);
/* 384 */       setText(Assets.gettext("SEARCH.SEARCH PLAYERS"), Font.Align.CENTER, Assets.font14);
/* 385 */       Objects.requireNonNull(SelectFolder.this.game.gui); setGeometry((1280 + 180) / 2 + 20, 660, 360, 36);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 390 */       setVisible(!SelectFolder.navigation.folder.equals(Assets.teamsRootFolder));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 395 */       SelectFolder.this.game.setScreen((Screen)new SearchPlayers(SelectFolder.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SelectFolder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */