/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ class SelectTeam
/*     */   extends GLScreen
/*     */ {
/*     */   SelectTeam(GLGame game) {
/*  31 */     super(game);
/*     */     boolean isDataRoot;
/*  33 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  37 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, getTitle(), game.stateColor.body.intValue());
/*  38 */     this.widgets.add(titleBar);
/*     */     
/*  40 */     List<Widget> list = new ArrayList<>();
/*     */     
/*  42 */     ArrayList<String> leagues = new ArrayList<>();
/*  43 */     boolean singleLeague = false;
/*  44 */     FileHandle leaguesFile = navigation.folder.child("leagues.json");
/*  45 */     if (leaguesFile.exists()) {
/*  46 */       Collections.addAll((Collection)leagues, (Object[])Assets.json.fromJson(String[].class, leaguesFile.readString("UTF-8")));
/*  47 */       if (leagues.size() == 1) {
/*  48 */         singleLeague = true;
/*     */       }
/*  50 */       if (navigation.league == null) {
/*  51 */         if (singleLeague) {
/*  52 */           navigation.league = leagues.get(0);
/*     */         }
/*     */       } else {
/*  55 */         leagues.clear();
/*     */       } 
/*     */     } 
/*     */     
/*  59 */     ArrayList<Team> teamList = new ArrayList<>();
/*  60 */     FileHandle[] teamFileHandles = navigation.folder.list(Assets.teamFilenameFilter);
/*  61 */     for (FileHandle teamFileHandle : teamFileHandles) {
/*  62 */       Team team = (Team)Assets.json.fromJson(Team.class, teamFileHandle.readString("UTF-8"));
/*  63 */       team.path = Assets.getRelativeTeamPath(teamFileHandle);
/*  64 */       if (navigation.league == null || (team.type == Team.Type.CLUB && team.league.equals(navigation.league))) {
/*  65 */         teamList.add(team);
/*  66 */         if (team.type == Team.Type.CLUB && !leagues.contains(team.league)) {
/*  67 */           leagues.add(team.league);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  73 */     if (leagues.size() > 1) {
/*  74 */       for (String name : leagues) {
/*  75 */         LeagueButton leagueButton = new LeagueButton(name);
/*  76 */         list.add(leagueButton);
/*  77 */         this.widgets.add(leagueButton);
/*     */       } 
/*  79 */       if (list.size() > 0) {
/*  80 */         Objects.requireNonNull(game.gui); Widget.arrange(1280, 380, 34, 20, list);
/*  81 */         setSelectedWidget(list.get(0));
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  87 */       for (Team team : teamList) {
/*  88 */         TeamButton teamButton = new TeamButton(team);
/*  89 */         list.add(teamButton);
/*  90 */         this.widgets.add(teamButton);
/*     */       } 
/*  92 */       if (list.size() > 0) {
/*  93 */         Collections.sort(list, Widget.widgetComparatorByText);
/*  94 */         Objects.requireNonNull(game.gui); Widget.arrange(1280, 380, 30, (game.getState() == GLGame.State.EDIT) ? 40 : 20, list);
/*  95 */         setSelectedWidget(list.get(0));
/*     */         
/*  97 */         for (Widget teamButton : list) {
/*  98 */           if (game.getState() == GLGame.State.EDIT) {
/*  99 */             FavouriteToggleButton favouriteToggleButton = new FavouriteToggleButton(teamButton);
/* 100 */             this.widgets.add(favouriteToggleButton);
/* 101 */             if (Settings.development && Settings.showTeamValues) {
/* 102 */               PriceLabel priceLabel = new PriceLabel(teamButton);
/* 103 */               this.widgets.add(priceLabel);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 113 */     List<Widget> breadcrumb = new ArrayList<>();
/* 114 */     if (navigation.league != null) {
/* 115 */       BreadCrumbLeagueLabel breadCrumbLeagueLabel = new BreadCrumbLeagueLabel();
/* 116 */       breadcrumb.add(breadCrumbLeagueLabel);
/*     */     } 
/* 118 */     FileHandle fh = navigation.folder;
/*     */     
/*     */     do {
/* 121 */       isDataRoot = fh.equals(Assets.teamsRootFolder);
/* 122 */       boolean disabled = (fh == navigation.folder && (navigation.league == null || singleLeague));
/*     */       
/* 124 */       BreadCrumbButton breadCrumbButton = new BreadCrumbButton(fh, isDataRoot, disabled);
/* 125 */       breadcrumb.add(breadCrumbButton);
/* 126 */       fh = fh.parent();
/* 127 */     } while (!isDataRoot);
/*     */     
/* 129 */     Collections.reverse(breadcrumb);
/* 130 */     Objects.requireNonNull(game.gui); int x = (1280 - 960) / 2;
/* 131 */     for (Widget b : breadcrumb) {
/* 132 */       b.setPosition(x, 72);
/* 133 */       x += b.w + 2;
/*     */     } 
/* 135 */     this.widgets.addAll(breadcrumb);
/*     */     
/* 137 */     if (leagues.size() > 1 && game.getState() == GLGame.State.EDIT) {
/* 138 */       SearchTeamButton searchTeamButton = new SearchTeamButton();
/* 139 */       this.widgets.add(searchTeamButton);
/*     */     } 
/*     */     
/* 142 */     AbortButton abortButton = new AbortButton();
/* 143 */     this.widgets.add(abortButton);
/* 144 */     if (getSelectedWidget() == null) {
/* 145 */       setSelectedWidget((Widget)abortButton);
/*     */     }
/*     */     
/* 148 */     if (game.getState() == GLGame.State.EDIT && 
/* 149 */       !navigation.folder.equals(Assets.teamsRootFolder)) {
/* 150 */       SearchPlayerButton searchPlayerButton = new SearchPlayerButton();
/* 151 */       this.widgets.add(searchPlayerButton);
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getTitle() {
/* 156 */     String title = "";
/* 157 */     switch (this.game.getState()) {
/*     */       case EDIT:
/* 159 */         title = Assets.gettext("EDIT TEAMS");
/*     */         break;
/*     */       
/*     */       case TRAINING:
/* 163 */         title = Assets.gettext("TRAINING");
/*     */         break;
/*     */     } 
/* 166 */     return title;
/*     */   }
/*     */   
/*     */   private class BreadCrumbLeagueLabel
/*     */     extends Button {
/*     */     BreadCrumbLeagueLabel() {
/* 172 */       setSize(0, 32);
/* 173 */       setColors(SelectTeam.this.game.stateColor.darker());
/* 174 */       setActive(false);
/* 175 */       setText(SelectTeam.navigation.league, Font.Align.CENTER, Assets.font10);
/* 176 */       autoWidth();
/*     */     }
/*     */   }
/*     */   
/*     */   private class BreadCrumbButton
/*     */     extends Button {
/*     */     private final FileHandle folder;
/*     */     
/*     */     BreadCrumbButton(FileHandle folder, boolean isDataRoot, boolean disabled) {
/* 185 */       this.folder = folder;
/* 186 */       setSize(0, 32);
/* 187 */       if (disabled) {
/* 188 */         setColors(SelectTeam.this.game.stateColor.darker());
/* 189 */         setActive(false);
/*     */       } else {
/* 191 */         setColors(SelectTeam.this.game.stateColor);
/*     */       } 
/* 193 */       setText(isDataRoot ? "\024" : folder.name().replace('_', ' '), Font.Align.CENTER, Assets.font10);
/* 194 */       autoWidth();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 199 */       if (this.folder == SelectTeam.navigation.folder && SelectTeam.navigation.league != null) {
/* 200 */         SelectTeam.navigation.league = null;
/* 201 */         SelectTeam.this.game.setScreen((Screen)new SelectTeam(SelectTeam.this.game));
/*     */       } else {
/* 203 */         SelectTeam.navigation.folder = this.folder;
/* 204 */         SelectTeam.navigation.league = null;
/* 205 */         SelectTeam.this.game.setScreen((Screen)new SelectFolder(SelectTeam.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class LeagueButton
/*     */     extends Button {
/*     */     LeagueButton(String name) {
/* 213 */       setSize(300, 32);
/* 214 */       setColor(1789317);
/* 215 */       setText(name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 220 */       SelectTeam.navigation.league = this.text;
/* 221 */       SelectTeam.this.game.setScreen((Screen)new SelectTeam(SelectTeam.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     private final Team team;
/*     */     
/*     */     TeamButton(Team team) {
/* 230 */       this.team = team;
/* 231 */       setSize(270, 28);
/* 232 */       setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/* 233 */       setText(team.name, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 238 */       switch (SelectTeam.this.game.getState()) {
/*     */         case EDIT:
/* 240 */           SelectTeam.this.game.setScreen((Screen)new EditPlayers(SelectTeam.this.game, this.team, Boolean.valueOf(false)));
/*     */           break;
/*     */         
/*     */         case TRAINING:
/* 244 */           SelectTeam.navigation.team = this.team;
/* 245 */           SelectTeam.this.game.setScreen((Screen)new SetupTraining(SelectTeam.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class FavouriteToggleButton
/*     */     extends Button {
/*     */     private final String teamPath;
/*     */     private boolean isFavourite;
/*     */     
/*     */     FavouriteToggleButton(Widget teamButton) {
/* 257 */       this.teamPath = ((SelectTeam.TeamButton)teamButton).team.path;
/* 258 */       setGeometry(teamButton.x + teamButton.w, teamButton.y, 26, 28);
/* 259 */       setText("", Font.Align.CENTER, Assets.font14);
/* 260 */       this.isFavourite = Assets.favourites.contains(this.teamPath);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 265 */       setText(this.isFavourite ? "\026" : "\027");
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 270 */       if (Assets.favourites.contains(this.teamPath)) {
/* 271 */         Assets.favourites.remove(this.teamPath);
/* 272 */         this.isFavourite = false;
/*     */       } else {
/* 274 */         Assets.favourites.add(this.teamPath);
/* 275 */         this.isFavourite = true;
/*     */       } 
/* 277 */       Assets.saveFavourites();
/* 278 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PriceLabel
/*     */     extends Label {
/*     */     PriceLabel(Widget teamButton) {
/* 285 */       Team team = ((SelectTeam.TeamButton)teamButton).team;
/* 286 */       int v = 0;
/* 287 */       for (int i = 0; i < 11; i++) {
/* 288 */         v += team.playerAtPosition(i).getValue();
/*     */       }
/* 290 */       setGeometry(teamButton.x, teamButton.y, 60, 19);
/* 291 */       setText("" + v, Font.Align.LEFT, Assets.font6);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SearchTeamButton
/*     */     extends Button {
/*     */     SearchTeamButton() {
/* 298 */       setColor(4474026);
/* 299 */       setText(Assets.gettext("SEARCH.SEARCH TEAMS"), Font.Align.CENTER, Assets.font14);
/* 300 */       Objects.requireNonNull(SelectTeam.this.game.gui); setGeometry((1280 - 180) / 2 - 360 - 20, 660, 360, 36);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 305 */       SelectTeam.this.game.setScreen((Screen)new SearchTeams(SelectTeam.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 312 */       setColor(13107214);
/* 313 */       Objects.requireNonNull(SelectTeam.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 314 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 319 */       SelectTeam.this.game.setScreen((Screen)new Main(SelectTeam.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SearchPlayerButton
/*     */     extends Button {
/*     */     SearchPlayerButton() {
/* 326 */       setColor(4474026);
/* 327 */       setText(Assets.gettext("SEARCH.SEARCH PLAYERS"), Font.Align.CENTER, Assets.font14);
/* 328 */       Objects.requireNonNull(SelectTeam.this.game.gui); setGeometry((1280 + 180) / 2 + 20, 660, 360, 36);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 333 */       SelectTeam.this.game.setScreen((Screen)new SearchPlayers(SelectTeam.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SelectTeam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */