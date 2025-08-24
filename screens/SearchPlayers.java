/*     */ package com.ygames.ysoccer.screens;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class SearchPlayers extends GLScreen {
/*     */   private final int MAX_RESULTS = 20;
/*     */   private State state;
/*     */   private Button searchInputButton;
/*     */   private int fileIndex;
/*     */   private ArrayList<String> teamFiles;
/*     */   private ArrayList<PlayerButton> playerList;
/*     */   private boolean reachedMaxResults;
/*     */   
/*     */   private enum State {
/*  24 */     START, SEARCHING, FINISHED;
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
/*     */   SearchPlayers(GLGame game) {
/*  39 */     super(game); boolean isDataRoot; this.MAX_RESULTS = 20; this.state = State.START;
/*     */     this.reachedMaxResults = false;
/*  41 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  45 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("SEARCH.SEARCH PLAYERS"), game.stateColor.body.intValue());
/*  46 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  49 */     ArrayList<Widget> breadcrumb = new ArrayList<>();
/*  50 */     if (navigation.league != null) {
/*  51 */       BreadCrumbLeagueLabel breadCrumbLeagueLabel = new BreadCrumbLeagueLabel();
/*  52 */       breadcrumb.add(breadCrumbLeagueLabel);
/*     */     } 
/*  54 */     FileHandle fh = navigation.folder;
/*     */     
/*     */     do {
/*  57 */       isDataRoot = fh.equals(Assets.teamsRootFolder);
/*  58 */       boolean isCurrent = (navigation.league == null && fh == navigation.folder);
/*  59 */       BreadCrumbButton breadCrumbButton = new BreadCrumbButton(fh, isDataRoot, isCurrent);
/*  60 */       breadcrumb.add(breadCrumbButton);
/*  61 */       fh = fh.parent();
/*  62 */     } while (!isDataRoot);
/*     */     
/*  64 */     Collections.reverse(breadcrumb);
/*  65 */     Objects.requireNonNull(game.gui); int x = (1280 - 960) / 2;
/*  66 */     for (Widget b : breadcrumb) {
/*  67 */       b.setPosition(x, 72);
/*  68 */       x += b.w + 2;
/*     */     } 
/*  70 */     this.widgets.addAll(breadcrumb);
/*     */     
/*  72 */     this.teamFiles = new ArrayList<>();
/*  73 */     this.playerList = new ArrayList<>();
/*  74 */     recursiveTeamSearch(navigation.folder);
/*     */     
/*  76 */     InfoLabel infoLabel = new InfoLabel();
/*  77 */     this.widgets.add(infoLabel);
/*     */     
/*  79 */     this.searchInputButton = (Button)new SearchInputButton();
/*  80 */     this.widgets.add(this.searchInputButton);
/*  81 */     setSelectedWidget((Widget)infoLabel);
/*     */     
/*  83 */     ExitButton exitButton = new ExitButton();
/*  84 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private void recursiveTeamSearch(FileHandle folder) {
/*  88 */     FileHandle[] teamFileHandles = folder.list(Assets.teamFilenameFilter);
/*  89 */     if (teamFileHandles.length > 0) {
/*  90 */       for (FileHandle teamFileHandle : teamFileHandles) {
/*  91 */         Team team = (Team)Assets.json.fromJson(Team.class, teamFileHandle.readString("UTF-8"));
/*  92 */         team.path = Assets.getRelativeTeamPath(teamFileHandle);
/*  93 */         if (navigation.league == null || (team.type == Team.Type.CLUB && team.league.equals(navigation.league))) {
/*  94 */           this.teamFiles.add(Assets.getRelativeTeamPath(teamFileHandle));
/*     */         }
/*     */       } 
/*     */     } else {
/*  98 */       FileHandle[] fileHandles = folder.list();
/*  99 */       for (FileHandle teamFileHandle : fileHandles) {
/* 100 */         if (teamFileHandle.isDirectory())
/* 101 */           recursiveTeamSearch(teamFileHandle); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class BreadCrumbLeagueLabel
/*     */     extends Button
/*     */   {
/*     */     BreadCrumbLeagueLabel() {
/* 110 */       setSize(0, 32);
/* 111 */       setColors(SearchPlayers.this.game.stateColor.darker());
/* 112 */       setActive(false);
/* 113 */       setText(SearchPlayers.navigation.league, Font.Align.CENTER, Assets.font10);
/* 114 */       autoWidth();
/*     */     }
/*     */   }
/*     */   
/*     */   private class BreadCrumbButton
/*     */     extends Button {
/*     */     private FileHandle folder;
/*     */     
/*     */     BreadCrumbButton(FileHandle folder, boolean isDataRoot, boolean isCurrent) {
/* 123 */       this.folder = folder;
/* 124 */       setSize(0, 32);
/* 125 */       if (isCurrent) {
/* 126 */         setColors(SearchPlayers.this.game.stateColor.darker());
/* 127 */         setActive(false);
/*     */       } else {
/* 129 */         setColors(SearchPlayers.this.game.stateColor);
/* 130 */         if (isDataRoot) setActive(false); 
/*     */       } 
/* 132 */       setText(isDataRoot ? "\024" : folder.name().replace('_', ' '), Font.Align.CENTER, Assets.font10);
/* 133 */       autoWidth();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 138 */       SearchPlayers.navigation.folder = this.folder;
/* 139 */       SearchPlayers.navigation.league = null;
/* 140 */       SearchPlayers.this.game.setScreen((Screen)new SearchPlayers(SearchPlayers.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class InfoLabel
/*     */     extends Label {
/*     */     InfoLabel() {
/* 147 */       Objects.requireNonNull(SearchPlayers.this.game.gui); setGeometry((1280 - 400) / 2, 110, 400, 40);
/* 148 */       setText("", Font.Align.CENTER, Assets.font10);
/* 149 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 154 */       switch (SearchPlayers.this.state) {
/*     */         case START:
/* 156 */           setText("(" + SearchPlayers.this.teamFiles.size() + " " + Assets.gettext("TEAMS") + ")");
/*     */           break;
/*     */         
/*     */         case SEARCHING:
/* 160 */           setText((SearchPlayers.this.fileIndex + 1) + " / " + SearchPlayers.this.teamFiles.size());
/*     */           break;
/*     */         
/*     */         case FINISHED:
/* 164 */           if (SearchPlayers.this.reachedMaxResults) {
/* 165 */             setText(Assets.gettext("SEARCH.FIRST %n PLAYERS FOUND").replaceFirst("%n", "20")); break;
/*     */           } 
/* 167 */           setText(Assets.gettext("SEARCH.%n PLAYERS FOUND").replaceFirst("%n", SearchPlayers.this.playerList.size() + ""));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class SearchInputButton
/*     */     extends InputButton
/*     */   {
/*     */     SearchInputButton() {
/* 177 */       Objects.requireNonNull(SearchPlayers.this.game.gui); setGeometry((1280 - 440) / 2, 150, 440, 36);
/* 178 */       setColors(Integer.valueOf(1534397), Integer.valueOf(3838184), Integer.valueOf(1066106));
/* 179 */       setText("", Font.Align.CENTER, Assets.font14);
/* 180 */       setEntryLimit(20);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 185 */       if (this.text.length() > 0) {
/* 186 */         SearchPlayers.this.fileIndex = 0;
/* 187 */         SearchPlayers.this.widgets.removeAll(SearchPlayers.this.playerList);
/* 188 */         SearchPlayers.this.playerList.clear();
/* 189 */         SearchPlayers.this.reachedMaxResults = false;
/* 190 */         SearchPlayers.this.state = SearchPlayers.State.SEARCHING;
/*     */       } 
/* 192 */       SearchPlayers.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 199 */       setColor(13124096);
/* 200 */       Objects.requireNonNull(SearchPlayers.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 201 */       setText(Assets.gettext("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 206 */       FileHandle[] teamFileHandles = SearchPlayers.navigation.folder.list(Assets.teamFilenameFilter);
/* 207 */       if (teamFileHandles.length > 0) {
/* 208 */         SearchPlayers.this.game.setScreen((Screen)new SelectTeam(SearchPlayers.this.game));
/*     */       } else {
/* 210 */         SearchPlayers.this.game.setScreen((Screen)new SelectFolder(SearchPlayers.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float deltaTime) {
/* 217 */     super.render(deltaTime);
/*     */     
/* 219 */     switch (this.state) {
/*     */ 
/*     */ 
/*     */       
/*     */       case SEARCHING:
/* 224 */         if (this.reachedMaxResults || this.fileIndex == this.teamFiles.size()) {
/* 225 */           this.state = State.FINISHED;
/*     */         } else {
/* 227 */           searchPlayer(this.teamFiles.get(this.fileIndex));
/* 228 */           this.fileIndex++;
/*     */         } 
/* 230 */         refreshAllWidgets();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void searchPlayer(String teamFile) {
/* 239 */     FileHandle fh = Assets.teamsRootFolder.child(teamFile);
/* 240 */     Team team = (Team)Assets.json.fromJson(Team.class, fh.readString("UTF-8"));
/* 241 */     team.path = Assets.getRelativeTeamPath(fh);
/* 242 */     String searchTerm = this.searchInputButton.getText();
/* 243 */     for (Player player : team.players) {
/* 244 */       if (accentInsensitiveContains(player.name, searchTerm) || 
/* 245 */         accentInsensitiveContains(player.shirtName, searchTerm)) {
/* 246 */         if (this.playerList.size() < 20) {
/* 247 */           PlayerButton playerButton = new PlayerButton(player, fh.parent());
/* 248 */           Objects.requireNonNull(this.game.gui); playerButton.setPosition((1280 - 604) / 2, 210 + 21 * this.playerList.size());
/* 249 */           this.playerList.add(playerButton);
/* 250 */           this.widgets.add(playerButton); continue;
/*     */         } 
/* 252 */         this.reachedMaxResults = true;
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean accentInsensitiveContains(String string, String searchTerm) {
/* 260 */     return Normalizer.normalize(string, Normalizer.Form.NFD)
/* 261 */       .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
/* 262 */       .contains(searchTerm);
/*     */   }
/*     */   
/*     */   private class PlayerButton
/*     */     extends Button {
/*     */     Player player;
/*     */     FileHandle folder;
/*     */     
/*     */     PlayerButton(Player player, FileHandle folder) {
/* 271 */       this.player = player;
/* 272 */       this.folder = folder;
/* 273 */       setColor(3181627);
/* 274 */       setSize(604, 19);
/* 275 */       setText(player.name + " (" + player.team.name + ")", Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 280 */       SearchPlayers.navigation.folder = this.folder;
/* 281 */       SearchPlayers.navigation.league = this.player.team.league;
/* 282 */       SearchPlayers.this.game.setScreen((Screen)new EditPlayers(SearchPlayers.this.game, this.player.team, Boolean.valueOf(false)));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SearchPlayers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */