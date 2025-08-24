/*      */ package com.ygames.ysoccer.screens;
/*      */ 
/*      */ import com.badlogic.gdx.Screen;
/*      */ import com.badlogic.gdx.files.FileHandle;
/*      */ import com.badlogic.gdx.graphics.Texture;
/*      */ import com.ygames.ysoccer.framework.Assets;
/*      */ import com.ygames.ysoccer.framework.EMath;
/*      */ import com.ygames.ysoccer.framework.Font;
/*      */ import com.ygames.ysoccer.framework.GLColor;
/*      */ import com.ygames.ysoccer.framework.GLGame;
/*      */ import com.ygames.ysoccer.framework.GLScreen;
/*      */ import com.ygames.ysoccer.gui.Button;
/*      */ import com.ygames.ysoccer.gui.InputButton;
/*      */ import com.ygames.ysoccer.gui.Picture;
/*      */ import com.ygames.ysoccer.gui.TacticsBoard;
/*      */ import com.ygames.ysoccer.gui.Widget;
/*      */ import com.ygames.ysoccer.match.Kit;
/*      */ import com.ygames.ysoccer.match.Player;
/*      */ import com.ygames.ysoccer.match.Tactics;
/*      */ import com.ygames.ysoccer.match.Team;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class EditTeam
/*      */   extends GLScreen
/*      */ {
/*      */   Team team;
/*      */   private int selectedKit;
/*      */   private int selectedPos;
/*      */   private boolean modified;
/*   39 */   private Widget[] tacticsButtons = new Widget[18];
/*      */   
/*   41 */   private Widget[] kitSelectionButtons = new Widget[5];
/*   42 */   private Widget[] kitEditButtons = new Widget[11];
/*      */   
/*   44 */   private Widget[] numberButtons = new Widget[11];
/*   45 */   private Widget[] faceButtons = new Widget[11];
/*   46 */   private Widget[] nameButtons = new Widget[11];
/*   47 */   private Widget[] roleButtons = new Widget[11];
/*      */   
/*      */   private Picture teamImage;
/*      */   private Widget kitWidget;
/*      */   private Widget newKitButton;
/*      */   private Widget deleteKitButton;
/*      */   private Widget resetButton;
/*      */   private Widget saveExitButton;
/*      */   
/*      */   EditTeam(GLGame game, Team team, Boolean modified) {
/*   57 */     super(game);
/*   58 */     this.team = team;
/*   59 */     this.modified = modified.booleanValue();
/*      */     
/*   61 */     this.selectedKit = 0;
/*   62 */     this.selectedPos = -1;
/*      */     
/*   64 */     this.background = new Texture("images/backgrounds/menu_edit_team.jpg");
/*      */ 
/*      */ 
/*      */     
/*   68 */     for (int t = 0; t < 18; t++) {
/*   69 */       TacticsButton tacticsButton = new TacticsButton(t);
/*   70 */       this.tacticsButtons[t] = (Widget)tacticsButton;
/*   71 */       this.widgets.add(tacticsButton);
/*      */     } 
/*      */     
/*   74 */     TeamNameButton teamNameButton = new TeamNameButton();
/*   75 */     this.widgets.add(teamNameButton);
/*      */     
/*   77 */     this.teamImage = new TeamPicture();
/*   78 */     this.widgets.add(this.teamImage);
/*      */     
/*   80 */     CoachLabel coachLabel = new CoachLabel();
/*   81 */     this.widgets.add(coachLabel);
/*      */     
/*   83 */     CoachButton coachButton = new CoachButton();
/*   84 */     this.widgets.add(coachButton);
/*      */     
/*   86 */     CityLabel cityLabel = new CityLabel();
/*   87 */     this.widgets.add(cityLabel);
/*      */     
/*   89 */     CoachNationalityButton coachNationalityButton = new CoachNationalityButton();
/*   90 */     this.widgets.add(coachNationalityButton);
/*      */     
/*   92 */     CityButton cityButton = new CityButton();
/*   93 */     this.widgets.add(cityButton);
/*      */     
/*   95 */     StadiumLabel stadiumLabel = new StadiumLabel();
/*   96 */     this.widgets.add(stadiumLabel);
/*      */     
/*   98 */     StadiumButton stadiumButton = new StadiumButton();
/*   99 */     this.widgets.add(stadiumButton);
/*      */     
/*  101 */     if (team.type == Team.Type.CLUB) {
/*  102 */       CountryLabel countryLabel = new CountryLabel();
/*  103 */       this.widgets.add(countryLabel);
/*      */       
/*  105 */       CountryButton countryButton = new CountryButton();
/*  106 */       this.widgets.add(countryButton);
/*      */       
/*  108 */       LeagueLabel leagueLabel = new LeagueLabel();
/*  109 */       this.widgets.add(leagueLabel);
/*      */       
/*  111 */       LeagueButton leagueButton = new LeagueButton();
/*  112 */       this.widgets.add(leagueButton);
/*      */     } 
/*      */     
/*  115 */     TacticsBoard tacticsBoard = new TacticsBoard(team);
/*  116 */     tacticsBoard.setPosition(915, 40);
/*  117 */     this.widgets.add(tacticsBoard);
/*      */     
/*  119 */     for (int i = 0; i < 5; i++) {
/*  120 */       SelectKitButton selectKitButton = new SelectKitButton(i);
/*  121 */       this.kitSelectionButtons[i] = (Widget)selectKitButton;
/*  122 */       this.widgets.add(selectKitButton);
/*      */     } 
/*      */     
/*  125 */     TeamKit teamKit = new TeamKit(team);
/*  126 */     teamKit.setPosition(321, 343);
/*  127 */     this.kitWidget = (Widget)teamKit;
/*  128 */     this.widgets.add(teamKit);
/*      */     
/*  130 */     StyleLabel styleLabel = new StyleLabel();
/*  131 */     this.widgets.add(styleLabel);
/*      */     
/*  133 */     StyleButton styleButton = new StyleButton();
/*  134 */     this.kitEditButtons[0] = (Widget)styleButton;
/*  135 */     this.widgets.add(styleButton);
/*      */     
/*  137 */     int y = 418;
/*  138 */     KitFieldLabel kitFieldLabel = new KitFieldLabel("KITS.SHIRT", 528, y);
/*  139 */     this.widgets.add(kitFieldLabel);
/*      */     
/*  141 */     y += 25; int f;
/*  142 */     for (f = 0; f < 3; f++) {
/*  143 */       HashButton hashButton = new HashButton(Kit.Field.values()[f], 528, y);
/*  144 */       this.widgets.add(hashButton);
/*  145 */       this.kitEditButtons[1 + 2 * f] = (Widget)hashButton;
/*      */       
/*  147 */       KitColorButton kitColorButton = new KitColorButton(Kit.Field.values()[f], 570, y);
/*  148 */       this.widgets.add(kitColorButton);
/*  149 */       this.kitEditButtons[1 + 2 * f + 1] = (Widget)kitColorButton;
/*      */       
/*  151 */       y += 26;
/*      */     } 
/*      */     
/*  154 */     y += 3;
/*  155 */     for (f = 3; f < 5; f++) {
/*  156 */       String label = "";
/*  157 */       switch (Kit.Field.values()[f]) {
/*      */         case SHORTS:
/*  159 */           label = "KITS.SHORTS";
/*      */           break;
/*      */         case SOCKS:
/*  162 */           label = "KITS.SOCKS";
/*      */           break;
/*      */       } 
/*  165 */       kitFieldLabel = new KitFieldLabel(label, 528, y);
/*  166 */       this.widgets.add(kitFieldLabel);
/*      */       
/*  168 */       y += 25;
/*      */       
/*  170 */       HashButton hashButton = new HashButton(Kit.Field.values()[f], 528, y);
/*  171 */       this.widgets.add(hashButton);
/*  172 */       this.kitEditButtons[1 + 2 * f] = (Widget)hashButton;
/*      */       
/*  174 */       KitColorButton kitColorButton = new KitColorButton(Kit.Field.values()[f], 570, y);
/*  175 */       this.widgets.add(kitColorButton);
/*  176 */       this.kitEditButtons[1 + 2 * f + 1] = (Widget)kitColorButton;
/*      */       
/*  178 */       y += 29;
/*      */     } 
/*      */     
/*  181 */     for (int pos = 0; pos < 11; pos++) {
/*  182 */       PlayerNumberButton playerNumberButton = new PlayerNumberButton(pos);
/*  183 */       this.numberButtons[pos] = (Widget)playerNumberButton;
/*  184 */       this.widgets.add(playerNumberButton);
/*      */       
/*  186 */       PlayerFaceButton playerFaceButton = new PlayerFaceButton(pos);
/*  187 */       this.faceButtons[pos] = (Widget)playerFaceButton;
/*  188 */       this.widgets.add(playerFaceButton);
/*      */       
/*  190 */       PlayerNameButton playerNameButton = new PlayerNameButton(pos);
/*  191 */       this.nameButtons[pos] = (Widget)playerNameButton;
/*  192 */       this.widgets.add(playerNameButton);
/*      */       
/*  194 */       PlayerRoleButton playerRoleButton = new PlayerRoleButton(pos);
/*  195 */       this.roleButtons[pos] = (Widget)playerRoleButton;
/*  196 */       this.widgets.add(playerRoleButton);
/*      */       
/*  198 */       PlayerSelectButton playerSelectButton = new PlayerSelectButton(pos);
/*  199 */       this.widgets.add(playerSelectButton);
/*      */     } 
/*      */     
/*  202 */     EditPlayersButton editPlayersButton = new EditPlayersButton();
/*  203 */     this.widgets.add(editPlayersButton);
/*      */     
/*  205 */     setSelectedWidget((Widget)editPlayersButton);
/*      */     
/*  207 */     NewKitButton newKitButton = new NewKitButton();
/*  208 */     this.newKitButton = (Widget)newKitButton;
/*  209 */     this.widgets.add(newKitButton);
/*      */     
/*  211 */     DeleteKitButton deleteKitButton = new DeleteKitButton();
/*  212 */     this.deleteKitButton = (Widget)deleteKitButton;
/*  213 */     this.widgets.add(deleteKitButton);
/*      */     
/*  215 */     ResetButton resetButton = new ResetButton();
/*  216 */     this.resetButton = (Widget)resetButton;
/*  217 */     this.widgets.add(resetButton);
/*      */     
/*  219 */     SaveExitButton saveExitButton = new SaveExitButton();
/*  220 */     this.saveExitButton = (Widget)saveExitButton;
/*  221 */     this.widgets.add(saveExitButton);
/*      */   }
/*      */   
/*      */   private void setModifiedFlag() {
/*  225 */     this.modified = true;
/*  226 */     this.resetButton.setDirty(true);
/*  227 */     this.saveExitButton.setDirty(true);
/*      */   }
/*      */   
/*      */   private class TacticsButton
/*      */     extends Button {
/*      */     int t;
/*      */     
/*      */     TacticsButton(int t) {
/*  235 */       this.t = t;
/*  236 */       setGeometry(692 + 94 * t % 2, 44 + 25 * (int)Math.floor((t / 2.0F)), 90, 22);
/*  237 */       setText(Tactics.codes[t], Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  242 */       if (EditTeam.this.team.tactics == this.t) {
/*  243 */         setColors(Integer.valueOf(10320643), Integer.valueOf(14856196), Integer.valueOf(6770947));
/*      */       } else {
/*  245 */         setColors(Integer.valueOf(14856196), Integer.valueOf(16567856), Integer.valueOf(10320643));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  251 */       if (EditTeam.this.team.tactics != this.t) {
/*  252 */         EditTeam.this.team.tactics = this.t;
/*  253 */         for (Widget w : EditTeam.this.tacticsButtons) {
/*  254 */           w.setDirty(true);
/*      */         }
/*  256 */         EditTeam.this.setModifiedFlag();
/*  257 */         for (int pos = 0; pos < 11; pos++)
/*  258 */           EditTeam.this.updatePlayerButtons(pos); 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class TeamNameButton
/*      */     extends InputButton
/*      */   {
/*      */     TeamNameButton() {
/*  267 */       setGeometry(194, 30, 450, 40);
/*  268 */       setColors(Integer.valueOf(10244650), Integer.valueOf(12278309), Integer.valueOf(6895645));
/*  269 */       setText(EditTeam.this.team.name, Font.Align.CENTER, Assets.font14);
/*  270 */       setEntryLimit(16);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onChanged() {
/*  275 */       EditTeam.this.team.name = this.text;
/*  276 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */   }
/*      */   
/*      */   private class TeamPicture
/*      */     extends Picture {
/*      */     TeamPicture() {
/*  283 */       setAddShadow(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  288 */       if (EditTeam.this.team.image == null) {
/*  289 */         EditTeam.this.team.loadImage();
/*  290 */       } else if (EditTeam.this.selectedKit == 0 && EditTeam.this.team.imageIsDefaultLogo) {
/*  291 */         EditTeam.this.team.image = null;
/*  292 */         EditTeam.this.team.loadImage();
/*      */       } 
/*  294 */       setTextureRegion(EditTeam.this.team.image);
/*  295 */       limitToSize(100, 70);
/*  296 */       setPosition(125, 52);
/*      */     }
/*      */   }
/*      */   
/*      */   private class CoachLabel
/*      */     extends Button {
/*      */     CoachLabel() {
/*  303 */       setGeometry(90, 290, 182, 32);
/*  304 */       setColors(Integer.valueOf(8421504), Integer.valueOf(12632256), Integer.valueOf(4210752));
/*  305 */       setText(Assets.gettext("COACH"), Font.Align.CENTER, Assets.font10);
/*  306 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class CoachButton
/*      */     extends InputButton {
/*      */     CoachButton() {
/*  313 */       setGeometry(280, 290, 364, 32);
/*  314 */       setColors(Integer.valueOf(1089536), Integer.valueOf(1433600), Integer.valueOf(614400));
/*  315 */       setText(EditTeam.this.team.coach.name, Font.Align.CENTER, Assets.font10);
/*  316 */       setEntryLimit(28);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onChanged() {
/*  321 */       EditTeam.this.team.coach.name = this.text;
/*  322 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */   }
/*      */   
/*      */   private class CoachNationalityButton
/*      */     extends InputButton {
/*      */     CoachNationalityButton() {
/*  329 */       setGeometry(652, 290, 60, 32);
/*  330 */       setColors(Integer.valueOf(1089536), Integer.valueOf(1433600), Integer.valueOf(614400));
/*  331 */       setText(EditTeam.this.team.coach.nationality, Font.Align.CENTER, Assets.font10);
/*  332 */       setEntryLimit(3);
/*  333 */       setInputFilter("[A-Z]");
/*      */     }
/*      */ 
/*      */     
/*      */     public void onChanged() {
/*  338 */       EditTeam.this.team.coach.nationality = this.text;
/*  339 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */   }
/*      */   
/*      */   private class CityLabel
/*      */     extends Button {
/*      */     CityLabel() {
/*  346 */       setGeometry(90, 200, 182, 32);
/*  347 */       setColors(Integer.valueOf(8421504), Integer.valueOf(12632256), Integer.valueOf(4210752));
/*  348 */       setText(Assets.gettext("CITY"), Font.Align.CENTER, Assets.font10);
/*  349 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class CityButton
/*      */     extends InputButton {
/*      */     CityButton() {
/*  356 */       setGeometry(280, 200, 364, 32);
/*  357 */       setColors(Integer.valueOf(1089536), Integer.valueOf(1433600), Integer.valueOf(614400));
/*  358 */       setText(EditTeam.this.team.city, Font.Align.CENTER, Assets.font10);
/*  359 */       setEntryLimit(28);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onChanged() {
/*  364 */       EditTeam.this.team.city = this.text;
/*  365 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */   }
/*      */   
/*      */   private class StadiumLabel
/*      */     extends Button {
/*      */     StadiumLabel() {
/*  372 */       setGeometry(90, 245, 182, 32);
/*  373 */       setColors(Integer.valueOf(8421504), Integer.valueOf(12632256), Integer.valueOf(4210752));
/*  374 */       setText(Assets.gettext("STADIUM"), Font.Align.CENTER, Assets.font10);
/*  375 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class StadiumButton
/*      */     extends InputButton {
/*      */     private StadiumButton() {
/*  382 */       setGeometry(280, 245, 364, 32);
/*  383 */       setColors(Integer.valueOf(1089536), Integer.valueOf(1433600), Integer.valueOf(614400));
/*  384 */       setText(EditTeam.this.team.stadium, Font.Align.CENTER, Assets.font10);
/*  385 */       setEntryLimit(28);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onChanged() {
/*  390 */       EditTeam.this.team.stadium = this.text;
/*  391 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */   }
/*      */   
/*      */   private class CountryLabel
/*      */     extends Button {
/*      */     CountryLabel() {
/*  398 */       setGeometry(90, 110, 182, 32);
/*  399 */       setColors(Integer.valueOf(8421504), Integer.valueOf(12632256), Integer.valueOf(4210752));
/*  400 */       setText(Assets.gettext("COUNTRY"), Font.Align.CENTER, Assets.font10);
/*  401 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class CountryButton
/*      */     extends Button {
/*      */     private CountryButton() {
/*  408 */       setGeometry(280, 110, 364, 32);
/*  409 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/*  410 */       setText(EditTeam.this.team.country, Font.Align.CENTER, Assets.font10);
/*  411 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class LeagueLabel
/*      */     extends Button {
/*      */     LeagueLabel() {
/*  418 */       setGeometry(90, 155, 182, 32);
/*  419 */       setColors(Integer.valueOf(8421504), Integer.valueOf(12632256), Integer.valueOf(4210752));
/*  420 */       setText(Assets.gettext("LEAGUE"), Font.Align.CENTER, Assets.font10);
/*  421 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class LeagueButton
/*      */     extends Button {
/*      */     List<String> leagues;
/*      */     
/*      */     LeagueButton() {
/*  430 */       FileHandle file = EditTeam.navigation.folder.child("leagues.json");
/*  431 */       this.leagues = Arrays.asList((Object[])Assets.json.fromJson(String[].class, file.readString("UTF-8")));
/*  432 */       setGeometry(280, 155, 364, 32);
/*  433 */       setColor(1089536);
/*  434 */       setText("", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  439 */       setText(EditTeam.this.team.league);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  444 */       changeLeague(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  449 */       changeLeague(-1);
/*      */     }
/*      */     
/*      */     private void changeLeague(int n) {
/*  453 */       int i = this.leagues.indexOf(EditTeam.this.team.league);
/*  454 */       if (i == -1) {
/*  455 */         i = 0;
/*      */       } else {
/*  457 */         i = EMath.rotate(i, 0, this.leagues.size() - 1, n);
/*      */       } 
/*  459 */       EditTeam.this.team.league = this.leagues.get(i);
/*  460 */       setDirty(true);
/*  461 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */   }
/*      */   
/*      */   private class SelectKitButton
/*      */     extends Button {
/*      */     int kitIndex;
/*      */     
/*      */     SelectKitButton(int kitIndex) {
/*  470 */       this.kitIndex = kitIndex;
/*  471 */       setGeometry(90, 364 + 56 * kitIndex, 190, 39);
/*  472 */       String label = "";
/*  473 */       switch (kitIndex) {
/*      */         case 0:
/*  475 */           label = "KITS.HOME";
/*      */           break;
/*      */         
/*      */         case 1:
/*  479 */           label = "KITS.AWAY";
/*      */           break;
/*      */         
/*      */         case 2:
/*  483 */           label = "KITS.THIRD";
/*      */           break;
/*      */         
/*      */         case 3:
/*  487 */           label = "KITS.1ST CHANGE";
/*      */           break;
/*      */         
/*      */         case 4:
/*  491 */           label = "KITS.2ND CHANGE";
/*      */           break;
/*      */       } 
/*  494 */       setText(Assets.gettext(label), Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  499 */       if (this.kitIndex >= EditTeam.this.team.kits.size()) {
/*  500 */         setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/*  501 */         setActive(false);
/*      */       } else {
/*  503 */         if (this.kitIndex == EditTeam.this.selectedKit) {
/*  504 */           setColors(Integer.valueOf(8919109), Integer.valueOf(14427246), Integer.valueOf(5312297));
/*      */         } else {
/*  506 */           setColors(Integer.valueOf(14297712), Integer.valueOf(14965906), Integer.valueOf(10755154));
/*      */         } 
/*  508 */         setActive(true);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  514 */       EditTeam.this.selectedKit = this.kitIndex;
/*  515 */       EditTeam.this.kitWidget.setDirty(true);
/*  516 */       EditTeam.this.updateKitSelectionButtons();
/*  517 */       EditTeam.this.updateKitEditButtons();
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateKitSelectionButtons() {
/*  522 */     for (Widget w : this.kitSelectionButtons) {
/*  523 */       w.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateKitEditButtons() {
/*  528 */     for (Widget w : this.kitEditButtons)
/*  529 */       w.setDirty(true); 
/*      */   }
/*      */   
/*      */   private class TeamKit
/*      */     extends Button {
/*      */     Team team;
/*      */     
/*      */     TeamKit(Team team) {
/*  537 */       this.team = team;
/*  538 */       setSize(167, 304);
/*  539 */       setActive(false);
/*  540 */       setAddShadow(true);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  545 */       this.textureRegion = this.team.loadKit(EditTeam.this.selectedKit);
/*      */     }
/*      */   }
/*      */   
/*      */   private class StyleLabel
/*      */     extends Button {
/*      */     StyleLabel() {
/*  552 */       setGeometry(528, 364, 175, 23);
/*  553 */       setColors(Integer.valueOf(8421504), Integer.valueOf(12632256), Integer.valueOf(4210752));
/*  554 */       setText(Assets.gettext("KITS.STYLE"), Font.Align.CENTER, Assets.font10);
/*  555 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class StyleButton
/*      */     extends Button {
/*      */     int kitIndex;
/*      */     
/*      */     StyleButton() {
/*  564 */       this.kitIndex = Assets.kits.indexOf(((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).style);
/*  565 */       setGeometry(528, 389, 175, 24);
/*  566 */       setColor(8919109);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  571 */       setText(((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).style.replace('_', ' '), Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  576 */       updateKitStyle(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  581 */       updateKitStyle(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  586 */       updateKitStyle(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  591 */       updateKitStyle(-1);
/*      */     }
/*      */     
/*      */     private void updateKitStyle(int n) {
/*  595 */       this.kitIndex = EMath.rotate(this.kitIndex, 0, Assets.kits.size() - 1, n);
/*  596 */       ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).style = Assets.kits.get(this.kitIndex);
/*  597 */       setDirty(true);
/*  598 */       EditTeam.this.kitWidget.setDirty(true);
/*  599 */       EditTeam.this.teamImage.setDirty(true);
/*  600 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */   }
/*      */   
/*      */   private class KitFieldLabel
/*      */     extends Button {
/*      */     KitFieldLabel(String label, int x, int y) {
/*  607 */       setGeometry(x, y, 175, 23);
/*  608 */       setColors(Integer.valueOf(8421504), Integer.valueOf(12632256), Integer.valueOf(4210752));
/*  609 */       setText(Assets.gettext(label), Font.Align.CENTER, Assets.font10);
/*  610 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class HashButton
/*      */     extends Button {
/*      */     Kit.Field field;
/*      */     int colorIndex;
/*      */     
/*      */     HashButton(Kit.Field field, int x, int y) {
/*  620 */       this.field = field;
/*  621 */       setGeometry(x, y, 40, 24);
/*  622 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/*  623 */       setText("#", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  628 */       updateColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  633 */       updateColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  638 */       updateColor(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  643 */       updateColor(-1);
/*      */     }
/*      */     
/*      */     private void updateColor(int n) {
/*  647 */       this.colorIndex = EMath.rotate(this.colorIndex, 0, Kit.colors.length - 1, n);
/*  648 */       int color = Kit.colors[this.colorIndex];
/*  649 */       switch (this.field) {
/*      */         case SHIRT1:
/*  651 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt1 = color;
/*      */           break;
/*      */         
/*      */         case SHIRT2:
/*  655 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt2 = color;
/*  656 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt3 = color;
/*      */           break;
/*      */         
/*      */         case SHIRT3:
/*  660 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt3 = color;
/*      */           break;
/*      */         
/*      */         case SHORTS:
/*  664 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shorts = color;
/*      */           break;
/*      */         
/*      */         case SOCKS:
/*  668 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).socks = color;
/*      */           break;
/*      */       } 
/*  671 */       EditTeam.this.updateKitEditButtons();
/*  672 */       EditTeam.this.kitWidget.setDirty(true);
/*  673 */       EditTeam.this.teamImage.setDirty(true);
/*  674 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  679 */       int color = 0;
/*  680 */       switch (this.field) {
/*      */         case SHIRT1:
/*  682 */           color = ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt1;
/*      */           break;
/*      */         
/*      */         case SHIRT2:
/*  686 */           color = ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt2;
/*      */           break;
/*      */         
/*      */         case SHIRT3:
/*  690 */           color = ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt3;
/*      */           break;
/*      */         
/*      */         case SHORTS:
/*  694 */           color = ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shorts;
/*      */           break;
/*      */         
/*      */         case SOCKS:
/*  698 */           color = ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).socks;
/*      */           break;
/*      */       } 
/*  701 */       setColor(color);
/*      */     }
/*      */   }
/*      */   
/*      */   private class KitColorButton
/*      */     extends InputButton {
/*      */     Kit.Field field;
/*      */     
/*      */     KitColorButton(Kit.Field field, int x, int y) {
/*  710 */       this.field = field;
/*  711 */       setGeometry(x, y, 133, 24);
/*  712 */       setText("", Font.Align.CENTER, Assets.font10);
/*  713 */       setEntryLimit(6);
/*  714 */       setInputFilter("[A-F0-9]");
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  719 */       int color = getColor();
/*  720 */       setText(GLColor.toHexString(color).substring(1).toUpperCase());
/*  721 */       setColor(color);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onChanged() {
/*  726 */       int color = (this.text.length() == 0) ? 0 : GLColor.valueOf("#" + this.text);
/*  727 */       switch (this.field) {
/*      */         case SHIRT1:
/*  729 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt1 = color;
/*      */           break;
/*      */         
/*      */         case SHIRT2:
/*  733 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt2 = color;
/*  734 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt3 = color;
/*      */           break;
/*      */         
/*      */         case SHIRT3:
/*  738 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shirt3 = color;
/*      */           break;
/*      */         
/*      */         case SHORTS:
/*  742 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).shorts = color;
/*      */           break;
/*      */         
/*      */         case SOCKS:
/*  746 */           ((Kit)EditTeam.this.team.kits.get(EditTeam.this.selectedKit)).socks = color;
/*      */           break;
/*      */       } 
/*  749 */       EditTeam.this.updateKitEditButtons();
/*  750 */       EditTeam.this.kitWidget.setDirty(true);
/*  751 */       EditTeam.this.teamImage.setDirty(true);
/*  752 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */     
/*      */     private int getColor() {
/*  756 */       Kit kit = EditTeam.this.team.kits.get(EditTeam.this.selectedKit);
/*  757 */       switch (this.field) {
/*      */         case SHIRT1:
/*  759 */           return kit.shirt1;
/*      */         
/*      */         case SHIRT2:
/*  762 */           return kit.shirt2;
/*      */         
/*      */         case SHIRT3:
/*  765 */           return kit.shirt3;
/*      */         
/*      */         case SHORTS:
/*  768 */           return kit.shorts;
/*      */         
/*      */         case SOCKS:
/*  771 */           return kit.socks;
/*      */       } 
/*      */       
/*  774 */       return 0;
/*      */     }
/*      */   }
/*      */   
/*      */   private class PlayerNumberButton
/*      */     extends Button
/*      */   {
/*      */     int pos;
/*      */     
/*      */     PlayerNumberButton(int pos) {
/*  784 */       this.pos = pos;
/*  785 */       setGeometry(734, 374 + 24 * pos, 34, 21);
/*  786 */       setText("", Font.Align.CENTER, Assets.font10);
/*  787 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  792 */       setText((EditTeam.this.team.playerAtPosition(this.pos)).number);
/*      */     }
/*      */   }
/*      */   
/*      */   private class PlayerFaceButton
/*      */     extends Button {
/*      */     int pos;
/*      */     
/*      */     PlayerFaceButton(int pos) {
/*  801 */       this.pos = pos;
/*  802 */       setGeometry(768, 374 + 24 * pos, 24, 21);
/*  803 */       setImagePosition(2, 0);
/*  804 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  809 */       EditTeam.this.setPlayerWidgetColor((Widget)this, this.pos);
/*  810 */       this.textureRegion = EditTeam.this.team.playerAtPosition(this.pos).createFace();
/*      */     }
/*      */   }
/*      */   
/*      */   private class PlayerNameButton
/*      */     extends Button {
/*      */     int pos;
/*      */     
/*      */     PlayerNameButton(int pos) {
/*  819 */       this.pos = pos;
/*  820 */       setGeometry(796, 374 + 24 * pos, 364, 21);
/*  821 */       setText("", Font.Align.LEFT, Assets.font10);
/*  822 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  827 */       setText((EditTeam.this.team.playerAtPosition(this.pos)).name);
/*  828 */       EditTeam.this.setPlayerWidgetColor((Widget)this, this.pos);
/*      */     }
/*      */   }
/*      */   
/*      */   private class PlayerRoleButton
/*      */     extends Button {
/*      */     int pos;
/*      */     
/*      */     PlayerRoleButton(int pos) {
/*  837 */       this.pos = pos;
/*  838 */       setGeometry(1160, 374 + 24 * pos, 36, 21);
/*  839 */       setText("", Font.Align.CENTER, Assets.font10);
/*  840 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  845 */       Player player = EditTeam.this.team.playerAtPosition(this.pos);
/*  846 */       setText(Assets.gettext(player.getRoleLabel()));
/*      */     }
/*      */   }
/*      */   
/*      */   private class PlayerSelectButton
/*      */     extends Button {
/*      */     int pos;
/*      */     
/*      */     PlayerSelectButton(int pos) {
/*  855 */       this.pos = pos;
/*  856 */       setGeometry(734, 374 + 24 * this.pos, 462, 21);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  862 */       if (EditTeam.this.selectedPos == -1) {
/*  863 */         EditTeam.this.selectedPos = this.pos;
/*      */       
/*      */       }
/*  866 */       else if (EditTeam.this.selectedPos == this.pos) {
/*  867 */         EditTeam.this.selectedPos = -1;
/*      */       }
/*      */       else {
/*      */         
/*  871 */         int ply1 = EditTeam.this.team.playerIndexAtPosition(EditTeam.this.selectedPos);
/*  872 */         int ply2 = EditTeam.this.team.playerIndexAtPosition(this.pos);
/*      */         
/*  874 */         Collections.swap(EditTeam.this.team.players, ply1, ply2);
/*      */         
/*  876 */         int oldSelected = EditTeam.this.selectedPos;
/*  877 */         EditTeam.this.selectedPos = -1;
/*      */         
/*  879 */         EditTeam.this.updatePlayerButtons(oldSelected);
/*  880 */         EditTeam.this.setModifiedFlag();
/*      */       } 
/*      */       
/*  883 */       EditTeam.this.updatePlayerButtons(this.pos);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void setPlayerWidgetColor(Widget b, int pos) {
/*  889 */     if (this.selectedPos == pos) {
/*  890 */       b.setColors(Integer.valueOf(10040115), Integer.valueOf(12730946), Integer.valueOf(5905950));
/*      */     
/*      */     }
/*  893 */     else if (pos == 0) {
/*  894 */       b.setColors(Integer.valueOf(4898904), Integer.valueOf(8508299), Integer.valueOf(3181627));
/*      */     }
/*      */     else {
/*      */       
/*  898 */       b.setColors(Integer.valueOf(3181627), Integer.valueOf(4898904), Integer.valueOf(2054438));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updatePlayerButtons(int pos) {
/*  903 */     this.numberButtons[pos].setDirty(true);
/*  904 */     this.faceButtons[pos].setDirty(true);
/*  905 */     this.nameButtons[pos].setDirty(true);
/*  906 */     this.roleButtons[pos].setDirty(true);
/*      */   }
/*      */   
/*      */   private class EditPlayersButton
/*      */     extends Button {
/*      */     EditPlayersButton() {
/*  912 */       setGeometry(80, 660, 206, 36);
/*  913 */       setColors(Integer.valueOf(33375), Integer.valueOf(49806), Integer.valueOf(16431));
/*  914 */       setText(Assets.gettext("PLAYERS"), Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  919 */       EditTeam.this.game.setScreen((Screen)new EditPlayers(EditTeam.this.game, EditTeam.this.team, Boolean.valueOf(EditTeam.this.modified)));
/*      */     }
/*      */   }
/*      */   
/*      */   private class NewKitButton
/*      */     extends Button {
/*      */     NewKitButton() {
/*  926 */       setGeometry(310, 660, 226, 36);
/*  927 */       setText(Assets.gettext("NEW KIT"), Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  932 */       if (EditTeam.this.team.kits.size() < 5) {
/*  933 */         setColors(Integer.valueOf(1534397), Integer.valueOf(3838184), Integer.valueOf(1066106));
/*  934 */         setActive(true);
/*      */       } else {
/*  936 */         setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/*  937 */         setActive(false);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  944 */       Kit kit = EditTeam.this.team.newKit();
/*      */       
/*  946 */       if (kit == null) {
/*      */         return;
/*      */       }
/*  949 */       Kit other = EditTeam.this.team.kits.get(EditTeam.this.team.kits.size() - 4);
/*  950 */       kit.style = other.style;
/*  951 */       kit.shirt1 = other.shirt1;
/*  952 */       kit.shirt2 = other.shirt2;
/*  953 */       kit.shirt3 = other.shirt3;
/*      */ 
/*      */       
/*  956 */       other = EditTeam.this.team.kits.get(5 - EditTeam.this.team.kits.size());
/*  957 */       kit.shorts = other.shorts;
/*  958 */       kit.socks = other.socks;
/*      */       
/*  960 */       EditTeam.this.selectedKit = EditTeam.this.team.kits.size() - 1;
/*  961 */       EditTeam.this.kitWidget.setDirty(true);
/*  962 */       EditTeam.this.updateKitSelectionButtons();
/*  963 */       EditTeam.this.updateKitEditButtons();
/*  964 */       setDirty(true);
/*  965 */       EditTeam.this.deleteKitButton.setDirty(true);
/*      */       
/*  967 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */   }
/*      */   
/*      */   private class DeleteKitButton
/*      */     extends Button {
/*      */     DeleteKitButton() {
/*  974 */       setGeometry(540, 660, 226, 36);
/*  975 */       setText(Assets.gettext("DELETE KIT"), Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  980 */       if (EditTeam.this.team.kits.size() > 3) {
/*  981 */         setColors(Integer.valueOf(3282877), Integer.valueOf(5650919), Integer.valueOf(2232448));
/*  982 */         setActive(true);
/*      */       } else {
/*  984 */         setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/*  985 */         setActive(false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  991 */       boolean deleted = EditTeam.this.team.deleteKit();
/*      */       
/*  993 */       if (!deleted)
/*      */         return; 
/*  995 */       if (EditTeam.this.selectedKit >= EditTeam.this.team.kits.size() - 1) {
/*  996 */         EditTeam.this.selectedKit = EditTeam.this.team.kits.size() - 1;
/*  997 */         EditTeam.this.kitWidget.setDirty(true);
/*      */       } 
/*  999 */       EditTeam.this.updateKitSelectionButtons();
/* 1000 */       EditTeam.this.updateKitEditButtons();
/* 1001 */       EditTeam.this.newKitButton.setDirty(true);
/* 1002 */       setDirty(true);
/*      */       
/* 1004 */       EditTeam.this.setModifiedFlag();
/*      */     }
/*      */   }
/*      */   
/*      */   private class ResetButton
/*      */     extends Button {
/*      */     ResetButton() {
/* 1011 */       setGeometry(790, 660, 196, 36);
/* 1012 */       setText(Assets.gettext("EDIT.RESET"), Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1017 */       if (EditTeam.this.modified) {
/* 1018 */         setColor(12435247);
/* 1019 */         setActive(true);
/*      */       } else {
/* 1021 */         setColor(6710886);
/* 1022 */         setActive(false);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1028 */       FileHandle file = Assets.teamsRootFolder.child(EditTeam.this.team.path);
/* 1029 */       if (file.exists()) {
/* 1030 */         Team team = (Team)Assets.json.fromJson(Team.class, file.readString("UTF-8"));
/* 1031 */         team.path = Assets.getRelativeTeamPath(file);
/* 1032 */         EditTeam.this.game.setScreen((Screen)new EditTeam(EditTeam.this.game, team, Boolean.valueOf(false)));
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private class SaveExitButton
/*      */     extends Button {
/*      */     SaveExitButton() {
/* 1040 */       setGeometry(990, 660, 196, 36);
/* 1041 */       setText(Assets.gettext(""), Font.Align.CENTER, Assets.font14);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1046 */       if (EditTeam.this.modified) {
/* 1047 */         setColor(14417920);
/* 1048 */         setText(Assets.gettext("SAVE"));
/*      */       } else {
/* 1050 */         setColor(13124096);
/* 1051 */         setText(Assets.gettext("EXIT"));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1057 */       if (EditTeam.this.modified) {
/* 1058 */         EditTeam.this.team.persist();
/*      */       }
/* 1060 */       EditTeam.this.game.setScreen((Screen)new SelectTeam(EditTeam.this.game));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\EditTeam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */