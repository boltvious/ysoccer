/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class SelectFavourite
/*     */   extends GLScreen
/*     */ {
/*     */   SelectFavourite(GLGame game) {
/*  23 */     super(game);
/*     */     
/*  25 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  29 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, getTitle(), game.stateColor.body.intValue());
/*  30 */     this.widgets.add(titleBar);
/*     */     
/*  32 */     ArrayList<Widget> teamButtons = new ArrayList<>();
/*     */     
/*  34 */     for (String teamPath : Assets.favourites) {
/*  35 */       FileHandle file = Assets.teamsRootFolder.child(teamPath);
/*  36 */       if (file.exists()) {
/*  37 */         Team team = (Team)Assets.json.fromJson(Team.class, file.readString("UTF-8"));
/*  38 */         team.path = Assets.getRelativeTeamPath(file);
/*  39 */         TeamButton teamButton = new TeamButton(team, file.parent());
/*  40 */         teamButtons.add(teamButton);
/*  41 */         this.widgets.add(teamButton);
/*     */       } 
/*     */     } 
/*     */     
/*  45 */     if (teamButtons.size() > 0) {
/*  46 */       Collections.sort(teamButtons, Widget.widgetComparatorByText);
/*  47 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, 380, 30, (game.getState() == GLGame.State.EDIT) ? 40 : 20, teamButtons);
/*  48 */       setSelectedWidget(teamButtons.get(0));
/*     */       
/*  50 */       for (Widget teamButton : teamButtons) {
/*  51 */         FavouriteFolderButton favouriteFolderButton = new FavouriteFolderButton(teamButton);
/*  52 */         this.widgets.add(favouriteFolderButton);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  57 */     ArrayList<Widget> breadcrumb = new ArrayList<>();
/*     */     
/*  59 */     BreadCrumbRootButton breadCrumbRootButton = new BreadCrumbRootButton();
/*  60 */     breadcrumb.add(breadCrumbRootButton);
/*     */     
/*  62 */     BreadCrumbFavouritesLabel breadCrumbFavouritesLabel = new BreadCrumbFavouritesLabel();
/*  63 */     breadcrumb.add(breadCrumbFavouritesLabel);
/*     */     
/*  65 */     Objects.requireNonNull(game.gui); int x = (1280 - 960) / 2;
/*  66 */     for (Widget b : breadcrumb) {
/*  67 */       b.setPosition(x, 72);
/*  68 */       x += b.w + 2;
/*     */     } 
/*  70 */     this.widgets.addAll(breadcrumb);
/*     */     
/*  72 */     AbortButton abortButton = new AbortButton();
/*  73 */     this.widgets.add(abortButton);
/*  74 */     if (getSelectedWidget() == null) {
/*  75 */       setSelectedWidget((Widget)abortButton);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getTitle() {
/*  80 */     String title = "";
/*  81 */     switch (this.game.getState()) {
/*     */       case EDIT:
/*  83 */         title = Assets.gettext("EDIT TEAMS");
/*     */         break;
/*     */       
/*     */       case TRAINING:
/*  87 */         title = Assets.gettext("TRAINING");
/*     */         break;
/*     */     } 
/*  90 */     return title;
/*     */   }
/*     */   
/*     */   private class BreadCrumbRootButton
/*     */     extends Button {
/*     */     BreadCrumbRootButton() {
/*  96 */       setSize(0, 32);
/*  97 */       setColors(SelectFavourite.this.game.stateColor);
/*  98 */       setText("\024", Font.Align.CENTER, Assets.font10);
/*  99 */       autoWidth();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 104 */       SelectFavourite.navigation.folder = Assets.teamsRootFolder;
/* 105 */       SelectFavourite.navigation.league = null;
/* 106 */       SelectFavourite.this.game.setScreen((Screen)new SelectFolder(SelectFavourite.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class BreadCrumbFavouritesLabel
/*     */     extends Button {
/*     */     BreadCrumbFavouritesLabel() {
/* 113 */       setSize(0, 32);
/* 114 */       setColors(SelectFavourite.this.game.stateColor.darker());
/* 115 */       setActive(false);
/* 116 */       setText(Assets.gettext("FAVOURITES"), Font.Align.CENTER, Assets.font10);
/* 117 */       autoWidth();
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     private Team team;
/*     */     FileHandle folder;
/*     */     
/*     */     TeamButton(Team team, FileHandle folder) {
/* 127 */       this.team = team;
/* 128 */       this.folder = folder;
/* 129 */       setSize(382, 28);
/* 130 */       setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/* 131 */       String mainFolder = Assets.getTeamFirstFolder(folder).name();
/* 132 */       setText(team.name + ", " + mainFolder, Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 137 */       switch (SelectFavourite.this.game.getState()) {
/*     */         case EDIT:
/* 139 */           SelectFavourite.navigation.folder = this.folder;
/* 140 */           SelectFavourite.navigation.league = this.team.league;
/* 141 */           SelectFavourite.this.game.setScreen((Screen)new EditPlayers(SelectFavourite.this.game, this.team, Boolean.valueOf(false)));
/*     */           break;
/*     */         
/*     */         case TRAINING:
/* 145 */           SelectFavourite.navigation.team = this.team;
/* 146 */           SelectFavourite.this.game.setScreen((Screen)new SetupTraining(SelectFavourite.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class FavouriteFolderButton
/*     */     extends Button {
/*     */     private SelectFavourite.TeamButton teamButton;
/*     */     
/*     */     FavouriteFolderButton(Widget teamButton) {
/* 157 */       this.teamButton = (SelectFavourite.TeamButton)teamButton;
/* 158 */       setGeometry(teamButton.x + teamButton.w, teamButton.y, 26, 28);
/* 159 */       setText("\024", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 164 */       SelectFavourite.navigation.folder = Assets.teamsRootFolder.child(this.teamButton.team.path).parent();
/* 165 */       SelectFavourite.navigation.league = this.teamButton.team.league;
/* 166 */       SelectFavourite.this.game.setScreen((Screen)new SelectTeam(SelectFavourite.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 173 */       setColor(13107214);
/* 174 */       Objects.requireNonNull(SelectFavourite.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 175 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 180 */       SelectFavourite.this.game.setScreen((Screen)new Main(SelectFavourite.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SelectFavourite.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */