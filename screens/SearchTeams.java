/*     */ package com.ygames.ysoccer.screens;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class SearchTeams extends GLScreen {
/*     */   private final int MAX_RESULTS = 20;
/*     */   private State state;
/*     */   private Button searchInputButton;
/*     */   private int fileIndex;
/*     */   private ArrayList<String> teamFiles;
/*     */   private ArrayList<TeamButton> teamList;
/*     */   private boolean reachedMaxResults;
/*     */   
/*     */   private enum State {
/*  23 */     START, SEARCHING, FINISHED;
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
/*     */   SearchTeams(GLGame game) {
/*  38 */     super(game); boolean isDataRoot; this.MAX_RESULTS = 20; this.state = State.START;
/*     */     this.reachedMaxResults = false;
/*  40 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  44 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("SEARCH.SEARCH TEAMS"), game.stateColor.body.intValue());
/*  45 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  48 */     ArrayList<Widget> breadcrumb = new ArrayList<>();
/*  49 */     if (navigation.league != null) {
/*  50 */       BreadCrumbLeagueLabel breadCrumbLeagueLabel = new BreadCrumbLeagueLabel();
/*  51 */       breadcrumb.add(breadCrumbLeagueLabel);
/*     */     } 
/*  53 */     FileHandle fh = navigation.folder;
/*     */     
/*     */     do {
/*  56 */       isDataRoot = fh.equals(Assets.teamsRootFolder);
/*  57 */       boolean isCurrent = (navigation.league == null && fh == navigation.folder);
/*  58 */       BreadCrumbButton breadCrumbButton = new BreadCrumbButton(fh, isDataRoot, isCurrent);
/*  59 */       breadcrumb.add(breadCrumbButton);
/*  60 */       fh = fh.parent();
/*  61 */     } while (!isDataRoot);
/*     */     
/*  63 */     Collections.reverse(breadcrumb);
/*  64 */     Objects.requireNonNull(game.gui); int x = (1280 - 960) / 2;
/*  65 */     for (Widget b : breadcrumb) {
/*  66 */       b.setPosition(x, 72);
/*  67 */       x += b.w + 2;
/*     */     } 
/*  69 */     this.widgets.addAll(breadcrumb);
/*     */     
/*  71 */     this.teamFiles = new ArrayList<>();
/*  72 */     this.teamList = new ArrayList<>();
/*  73 */     recursiveTeamSearch(navigation.folder);
/*     */     
/*  75 */     InfoLabel infoLabel = new InfoLabel();
/*  76 */     this.widgets.add(infoLabel);
/*     */     
/*  78 */     this.searchInputButton = (Button)new SearchInputButton();
/*  79 */     this.widgets.add(this.searchInputButton);
/*  80 */     setSelectedWidget((Widget)infoLabel);
/*     */     
/*  82 */     ExitButton exitButton = new ExitButton();
/*  83 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private void recursiveTeamSearch(FileHandle folder) {
/*  87 */     FileHandle[] teamFileHandles = folder.list(Assets.teamFilenameFilter);
/*  88 */     if (teamFileHandles.length > 0) {
/*  89 */       for (FileHandle teamFileHandle : teamFileHandles) {
/*  90 */         Team team = (Team)Assets.json.fromJson(Team.class, teamFileHandle.readString("UTF-8"));
/*  91 */         team.path = Assets.getRelativeTeamPath(teamFileHandle);
/*  92 */         if (navigation.league == null || (team.type == Team.Type.CLUB && team.league.equals(navigation.league))) {
/*  93 */           this.teamFiles.add(Assets.getRelativeTeamPath(teamFileHandle));
/*     */         }
/*     */       } 
/*     */     } else {
/*  97 */       FileHandle[] fileHandles = folder.list();
/*  98 */       for (FileHandle teamFileHandle : fileHandles) {
/*  99 */         if (teamFileHandle.isDirectory())
/* 100 */           recursiveTeamSearch(teamFileHandle); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class BreadCrumbLeagueLabel
/*     */     extends Button
/*     */   {
/*     */     BreadCrumbLeagueLabel() {
/* 109 */       setSize(0, 32);
/* 110 */       setColors(SearchTeams.this.game.stateColor.darker());
/* 111 */       setActive(false);
/* 112 */       setText(SearchTeams.navigation.league, Font.Align.CENTER, Assets.font10);
/* 113 */       autoWidth();
/*     */     }
/*     */   }
/*     */   
/*     */   private class BreadCrumbButton
/*     */     extends Button {
/*     */     private FileHandle folder;
/*     */     
/*     */     BreadCrumbButton(FileHandle folder, boolean isDataRoot, boolean isCurrent) {
/* 122 */       this.folder = folder;
/* 123 */       setSize(0, 32);
/* 124 */       if (isCurrent) {
/* 125 */         setColors(SearchTeams.this.game.stateColor.darker());
/* 126 */         setActive(false);
/*     */       } else {
/* 128 */         setColors(SearchTeams.this.game.stateColor);
/* 129 */         if (isDataRoot) setActive(false); 
/*     */       } 
/* 131 */       setText(isDataRoot ? "\024" : folder.name().replace('_', ' '), Font.Align.CENTER, Assets.font10);
/* 132 */       autoWidth();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 137 */       SearchTeams.navigation.folder = this.folder;
/* 138 */       SearchTeams.navigation.league = null;
/* 139 */       SearchTeams.this.game.setScreen((Screen)new SearchTeams(SearchTeams.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class InfoLabel
/*     */     extends Label {
/*     */     InfoLabel() {
/* 146 */       Objects.requireNonNull(SearchTeams.this.game.gui); setGeometry((1280 - 400) / 2, 110, 400, 40);
/* 147 */       setText("", Font.Align.CENTER, Assets.font10);
/* 148 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 153 */       switch (SearchTeams.this.state) {
/*     */         case EDIT:
/* 155 */           setText("(" + SearchTeams.this.teamFiles.size() + " " + Assets.gettext("TEAMS") + ")");
/*     */           break;
/*     */         
/*     */         case TRAINING:
/* 159 */           setText((SearchTeams.this.fileIndex + 1) + " / " + SearchTeams.this.teamFiles.size());
/*     */           break;
/*     */         
/*     */         case null:
/* 163 */           if (SearchTeams.this.reachedMaxResults) {
/* 164 */             setText(Assets.gettext("SEARCH.FIRST %n TEAMS FOUND").replaceFirst("%n", "20")); break;
/*     */           } 
/* 166 */           setText(Assets.gettext("SEARCH.%n TEAMS FOUND").replaceFirst("%n", SearchTeams.this.teamList.size() + ""));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class SearchInputButton
/*     */     extends InputButton
/*     */   {
/*     */     SearchInputButton() {
/* 176 */       Objects.requireNonNull(SearchTeams.this.game.gui); setGeometry((1280 - 440) / 2, 150, 440, 36);
/* 177 */       setColors(Integer.valueOf(1534397), Integer.valueOf(3838184), Integer.valueOf(1066106));
/* 178 */       setText("", Font.Align.CENTER, Assets.font14);
/* 179 */       setEntryLimit(20);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 184 */       if (this.text.length() > 0) {
/* 185 */         SearchTeams.this.fileIndex = 0;
/* 186 */         SearchTeams.this.widgets.removeAll(SearchTeams.this.teamList);
/* 187 */         SearchTeams.this.teamList.clear();
/* 188 */         SearchTeams.this.reachedMaxResults = false;
/* 189 */         SearchTeams.this.state = SearchTeams.State.SEARCHING;
/*     */       } 
/* 191 */       SearchTeams.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 198 */       setColor(13124096);
/* 199 */       Objects.requireNonNull(SearchTeams.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 200 */       setText(Assets.gettext("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 205 */       FileHandle[] teamFileHandles = SearchTeams.navigation.folder.list(Assets.teamFilenameFilter);
/* 206 */       if (teamFileHandles.length > 0) {
/* 207 */         SearchTeams.this.game.setScreen((Screen)new SelectTeam(SearchTeams.this.game));
/*     */       } else {
/* 209 */         SearchTeams.this.game.setScreen((Screen)new SelectFolder(SearchTeams.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float deltaTime) {
/* 216 */     super.render(deltaTime);
/*     */     
/* 218 */     switch (this.state) {
/*     */ 
/*     */ 
/*     */       
/*     */       case TRAINING:
/* 223 */         if (this.reachedMaxResults || this.fileIndex == this.teamFiles.size()) {
/* 224 */           this.state = State.FINISHED;
/*     */         } else {
/* 226 */           searchTeam(this.teamFiles.get(this.fileIndex));
/* 227 */           this.fileIndex++;
/*     */         } 
/* 229 */         refreshAllWidgets();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void searchTeam(String teamFile) {
/* 238 */     FileHandle fh = Assets.teamsRootFolder.child(teamFile);
/* 239 */     Team team = (Team)Assets.json.fromJson(Team.class, fh.readString("UTF-8"));
/* 240 */     team.path = Assets.getRelativeTeamPath(fh);
/* 241 */     String searchTerm = this.searchInputButton.getText();
/* 242 */     if (accentInsensitiveContains(team.name, searchTerm)) {
/* 243 */       if (this.teamList.size() < 20) {
/* 244 */         TeamButton teamButton = new TeamButton(team, fh.parent());
/* 245 */         Objects.requireNonNull(this.game.gui); teamButton.setPosition((1280 - 604) / 2, 210 + 21 * this.teamList.size());
/* 246 */         this.teamList.add(teamButton);
/* 247 */         this.widgets.add(teamButton);
/*     */       } else {
/* 249 */         this.reachedMaxResults = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean accentInsensitiveContains(String string, String searchTerm) {
/* 255 */     return Normalizer.normalize(string, Normalizer.Form.NFD)
/* 256 */       .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
/* 257 */       .contains(searchTerm);
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     Team team;
/*     */     FileHandle folder;
/*     */     
/*     */     TeamButton(Team team, FileHandle folder) {
/* 266 */       this.team = team;
/* 267 */       this.folder = folder;
/* 268 */       setColor(3181627);
/* 269 */       setSize(604, 19);
/* 270 */       String t = "";
/* 271 */       switch (team.type) {
/*     */         case EDIT:
/*     */         case TRAINING:
/* 274 */           t = team.name + " (" + team.country + ")";
/*     */           break;
/*     */         case null:
/* 277 */           t = team.name + " (" + folder.name() + ")";
/*     */           break;
/*     */       } 
/* 280 */       setText(t, Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 285 */       SearchTeams.navigation.folder = this.folder;
/* 286 */       SearchTeams.navigation.league = this.team.league;
/*     */       
/* 288 */       switch (SearchTeams.this.game.getState()) {
/*     */         case EDIT:
/* 290 */           SearchTeams.this.game.setScreen((Screen)new EditTeam(SearchTeams.this.game, this.team, Boolean.valueOf(false)));
/*     */           break;
/*     */         
/*     */         case TRAINING:
/* 294 */           SearchTeams.navigation.team = this.team;
/* 295 */           SearchTeams.this.game.setScreen((Screen)new SetupTraining(SearchTeams.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SearchTeams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */