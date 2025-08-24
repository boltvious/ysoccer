/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.Cup;
/*     */ import com.ygames.ysoccer.competitions.League;
/*     */ import com.ygames.ysoccer.competitions.tournament.Tournament;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class SelectCompetition
/*     */   extends GLScreen
/*     */ {
/*     */   private FileHandle currentFolder;
/*     */   
/*     */   SelectCompetition(GLGame game, FileHandle folder) {
/*  27 */     super(game); boolean isDataRoot;
/*  28 */     this.currentFolder = folder;
/*     */     
/*  30 */     this.background = game.stateBackground;
/*     */ 
/*     */ 
/*     */     
/*  34 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("CHOOSE PRESET COMPETITION"), game.stateColor.body.intValue());
/*  35 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  38 */     ArrayList<Widget> breadcrumb = new ArrayList<>();
/*     */     
/*  40 */     FileHandle fh = this.currentFolder;
/*     */     
/*     */     do {
/*  43 */       isDataRoot = fh.equals(Assets.competitionsRootFolder);
/*  44 */       BreadCrumbButton breadCrumbButton = new BreadCrumbButton(fh, isDataRoot);
/*  45 */       breadcrumb.add(breadCrumbButton);
/*  46 */       fh = fh.parent();
/*  47 */     } while (!isDataRoot);
/*     */     
/*  49 */     Collections.reverse(breadcrumb);
/*  50 */     Objects.requireNonNull(game.gui); int x = (1280 - 960) / 2;
/*  51 */     for (Widget b : breadcrumb) {
/*  52 */       b.setPosition(x, 72);
/*  53 */       x += b.w + 2;
/*     */     } 
/*  55 */     this.widgets.addAll(breadcrumb);
/*     */ 
/*     */     
/*  58 */     ArrayList<Widget> competitionsList = new ArrayList<>();
/*     */     
/*  60 */     FileHandle tournamentsFile = this.currentFolder.child("tournaments.json");
/*  61 */     if (tournamentsFile.exists()) {
/*  62 */       Tournament[] tournaments = (Tournament[])Assets.json.fromJson(Tournament[].class, tournamentsFile.readString("UTF-8"));
/*  63 */       for (Tournament tournament : tournaments) {
/*  64 */         tournament.category = Competition.Category.PRESET_COMPETITION;
/*  65 */         CompetitionButton competitionButton = new CompetitionButton((Competition)tournament);
/*  66 */         competitionsList.add(competitionButton);
/*  67 */         this.widgets.add(competitionButton);
/*     */       } 
/*     */     } 
/*     */     
/*  71 */     FileHandle leaguesFile = this.currentFolder.child("leagues.json");
/*  72 */     if (leaguesFile.exists()) {
/*  73 */       League[] leagues = (League[])Assets.json.fromJson(League[].class, leaguesFile.readString("UTF-8"));
/*  74 */       for (League league : leagues) {
/*  75 */         league.category = Competition.Category.PRESET_COMPETITION;
/*  76 */         CompetitionButton competitionButton = new CompetitionButton((Competition)league);
/*  77 */         competitionsList.add(competitionButton);
/*  78 */         this.widgets.add(competitionButton);
/*     */       } 
/*     */     } 
/*     */     
/*  82 */     FileHandle cupsFile = this.currentFolder.child("cups.json");
/*  83 */     if (cupsFile.exists()) {
/*  84 */       Cup[] cups = (Cup[])Assets.json.fromJson(Cup[].class, cupsFile.readString("UTF-8"));
/*  85 */       for (Cup cup : cups) {
/*  86 */         cup.category = Competition.Category.PRESET_COMPETITION;
/*  87 */         CompetitionButton competitionButton = new CompetitionButton((Competition)cup);
/*  88 */         competitionsList.add(competitionButton);
/*  89 */         this.widgets.add(competitionButton);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  94 */     ArrayList<Widget> foldersList = new ArrayList<>();
/*  95 */     ArrayList<FileHandle> files = new ArrayList<>(Arrays.asList(this.currentFolder.list()));
/*  96 */     Collections.sort(files, Assets.fileComparatorByName);
/*  97 */     for (FileHandle file : files) {
/*  98 */       if (file.isDirectory()) {
/*  99 */         FolderButton folderButton = new FolderButton(file);
/* 100 */         foldersList.add(folderButton);
/* 101 */         this.widgets.add(folderButton);
/*     */       } 
/*     */     } 
/*     */     
/* 105 */     int topY = 380 - 28 * (competitionsList.size() + Widget.getRows(foldersList)) / 2;
/* 106 */     int centerY = topY + 28 * competitionsList.size() / 2;
/* 107 */     if (competitionsList.size() > 0) {
/* 108 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, centerY, 28, 20, competitionsList);
/* 109 */       setSelectedWidget(competitionsList.get(0));
/*     */     } 
/* 111 */     centerY += 28 * (competitionsList.size() + Widget.getRows(foldersList)) / 2 + 6;
/* 112 */     if (foldersList.size() > 0) {
/* 113 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, centerY, 28, 20, foldersList);
/* 114 */       setSelectedWidget(foldersList.get(0));
/*     */     } 
/*     */     
/* 117 */     AbortButton abortButton = new AbortButton();
/* 118 */     this.widgets.add(abortButton);
/* 119 */     if (getSelectedWidget() == null)
/* 120 */       setSelectedWidget((Widget)abortButton); 
/*     */   }
/*     */   
/*     */   private class BreadCrumbButton
/*     */     extends Button
/*     */   {
/*     */     FileHandle folder;
/*     */     
/*     */     BreadCrumbButton(FileHandle folder, boolean isDataRoot) {
/* 129 */       this.folder = folder;
/* 130 */       setSize(0, 32);
/* 131 */       if (folder == SelectCompetition.this.currentFolder) {
/* 132 */         setColors(SelectCompetition.this.game.stateColor.darker());
/* 133 */         setActive(false);
/*     */       } else {
/* 135 */         setColors(SelectCompetition.this.game.stateColor);
/*     */       } 
/* 137 */       setText(isDataRoot ? "\024" : folder.name().replace('_', ' '), Font.Align.CENTER, Assets.font10);
/* 138 */       autoWidth();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 143 */       SelectCompetition.this.game.setScreen((Screen)new SelectCompetition(SelectCompetition.this.game, this.folder));
/*     */     }
/*     */   }
/*     */   
/*     */   private class FolderButton
/*     */     extends Button {
/*     */     FileHandle folder;
/*     */     
/*     */     FolderButton(FileHandle folder) {
/* 152 */       this.folder = folder;
/* 153 */       setSize(340, 28);
/* 154 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/* 155 */       setText(folder.name().replace('_', ' '), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 160 */       SelectCompetition.this.game.setScreen((Screen)new SelectCompetition(SelectCompetition.this.game, this.folder));
/*     */     }
/*     */   }
/*     */   
/*     */   private class CompetitionButton
/*     */     extends Button {
/*     */     Competition competition;
/*     */     
/*     */     CompetitionButton(Competition competition) {
/* 169 */       this.competition = competition;
/* 170 */       boolean dataFolderExists = Assets.teamsRootFolder.child(competition.files.folder).exists();
/* 171 */       setSize(480, 28);
/* 172 */       setColor(dataFolderExists ? 1789317 : 6710886);
/* 173 */       setText(competition.name, Font.Align.CENTER, Assets.font14);
/* 174 */       setActive(dataFolderExists);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 179 */       SelectCompetition.this.game.teamList = Team.loadTeamList(this.competition.files.teams);
/* 180 */       SelectCompetition.navigation.folder = Assets.teamsRootFolder.child(this.competition.files.folder);
/* 181 */       SelectCompetition.navigation.league = this.competition.files.league;
/* 182 */       SelectCompetition.navigation.competition = this.competition;
/* 183 */       SelectCompetition.this.game.setScreen((Screen)new AllSelectedTeams(SelectCompetition.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 190 */       setColor(13107214);
/* 191 */       setText(Assets.gettext("ABORT"), Font.Align.CENTER, Assets.font14);
/* 192 */       Objects.requireNonNull(SelectCompetition.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 197 */       SelectCompetition.this.game.setScreen((Screen)new Main(SelectCompetition.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SelectCompetition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */