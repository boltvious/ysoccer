/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.TableRow;
/*     */ import com.ygames.ysoccer.competitions.tournament.Round;
/*     */ import com.ygames.ysoccer.competitions.tournament.Tournament;
/*     */ import com.ygames.ysoccer.competitions.tournament.groups.Group;
/*     */ import com.ygames.ysoccer.competitions.tournament.groups.Groups;
/*     */ import com.ygames.ysoccer.competitions.tournament.knockout.Knockout;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.framework.RgbPair;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PlayTournament
/*     */   extends GLScreen
/*     */ {
/*     */   private Tournament tournament;
/*     */   private ArrayList<Match> matches;
/*     */   private int offset;
/*     */   private int maxOffset;
/*     */   private ArrayList<Widget> resultWidgets;
/*     */   
/*     */   PlayTournament(GLGame game) {
/*  42 */     super(game); Groups groups; int tableHeight, visibleGroups, topTeams, runnersUp, g; Knockout knockout;
/*     */     int dy, m;
/*  44 */     this.tournament = (Tournament)game.competition;
/*     */     
/*  46 */     this.background = game.stateBackground;
/*     */     
/*  48 */     Font font10green = new Font(10, 13, 17, 12, 16, new RgbPair(16579836, 2220855));
/*  49 */     font10green.load();
/*     */ 
/*     */ 
/*     */     
/*  53 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, this.tournament.getMenuTitle(), game.stateColor.body.intValue());
/*  54 */     this.widgets.add(titleBar);
/*     */     
/*  56 */     this.resultWidgets = new ArrayList<>();
/*  57 */     switch ((this.tournament.getRound()).type) {
/*     */       case GROUPS:
/*  59 */         groups = (Groups)this.tournament.getRound();
/*  60 */         tableHeight = 21 * (groups.groupNumberOfTeams() + 1) + 23;
/*  61 */         visibleGroups = Math.min(groups.groups.size(), 548 / tableHeight);
/*  62 */         topTeams = groups.numberOfTopTeams();
/*  63 */         runnersUp = groups.numberOfRunnersUp();
/*     */ 
/*     */         
/*  66 */         this.resultWidgets = new ArrayList<>();
/*  67 */         for (g = 0; g < groups.groups.size(); g++) {
/*  68 */           Group group = groups.groups.get(g);
/*     */ 
/*     */           
/*  71 */           int dx = 250;
/*  72 */           Label label1 = new Label();
/*  73 */           label1.setGeometry(dx, 0, 322, 21);
/*  74 */           label1.setText((groups.groups.size() == 1) ? "" : (Assets.gettext("GROUP") + " " + (char)(65 + g)), Font.Align.CENTER, Assets.font10);
/*  75 */           this.resultWidgets.add(label1);
/*  76 */           this.widgets.add(label1);
/*  77 */           dx += 320;
/*     */           
/*  79 */           String[] headers = { "TABLE HEADER.PLAYED MATCHES", "TABLE HEADER.WON MATCHES", "TABLE HEADER.DRAWN MATCHES", "TABLE HEADER.LOST MATCHES", "TABLE HEADER.GOALS FOR", "TABLE HEADER.GOALS AGAINST", "TABLE HEADER.POINTS" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  88 */           for (String header : headers) {
/*  89 */             label1 = new Label();
/*  90 */             label1.setGeometry(dx, 0, 72, 21);
/*  91 */             label1.setText(Assets.gettext(header), Font.Align.CENTER, Assets.font10);
/*  92 */             this.resultWidgets.add(label1);
/*  93 */             this.widgets.add(label1);
/*  94 */             dx += 70;
/*     */           } 
/*     */ 
/*     */           
/*  98 */           int tm = 0;
/*  99 */           dx = 570;
/* 100 */           for (int row = 0; row < group.table.size(); row++) {
/* 101 */             int bodyColor; TableRow tableRow = group.table.get(row);
/* 102 */             Team team = this.tournament.teams.get(tableRow.team);
/* 103 */             Button button = new Button();
/* 104 */             button.setGeometry(210, 0, 36, 23);
/* 105 */             button.setText(tm + 1, Font.Align.CENTER, Assets.font10);
/* 106 */             button.setActive(false);
/* 107 */             this.resultWidgets.add(button);
/* 108 */             this.widgets.add(button);
/*     */             
/* 110 */             button = new Button();
/* 111 */             button.setGeometry(250, 0, 322, 23);
/*     */             
/* 113 */             if (team.controlMode != Team.ControlMode.COMPUTER) {
/* 114 */               bodyColor = team.controlModeColor();
/* 115 */             } else if (runnersUp == 0) {
/* 116 */               if (row < topTeams) {
/* 117 */                 bodyColor = 9211020;
/*     */               } else {
/* 119 */                 bodyColor = 13906750;
/*     */               }
/*     */             
/* 122 */             } else if (row < topTeams) {
/* 123 */               bodyColor = 9211020;
/* 124 */             } else if (row == topTeams) {
/* 125 */               bodyColor = 14069025;
/*     */             } else {
/* 127 */               bodyColor = 13906750;
/*     */             } 
/*     */             
/* 130 */             button.setColors(Integer.valueOf(bodyColor), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 131 */             button.setText(team.name, Font.Align.LEFT, Assets.font10);
/* 132 */             button.setActive(false);
/* 133 */             this.resultWidgets.add(button);
/* 134 */             this.widgets.add(button);
/*     */ 
/*     */             
/* 137 */             button = new Button();
/* 138 */             button.setGeometry(dx, 0, 72, 23);
/* 139 */             button.setColors(Integer.valueOf(bodyColor), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 140 */             button.setText(tableRow.won + tableRow.drawn + tableRow.lost, Font.Align.CENTER, Assets.font10);
/* 141 */             button.setActive(false);
/* 142 */             this.resultWidgets.add(button);
/* 143 */             this.widgets.add(button);
/*     */ 
/*     */             
/* 146 */             button = new Button();
/* 147 */             button.setGeometry(dx + 70, 0, 72, 23);
/* 148 */             button.setColors(Integer.valueOf(bodyColor), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 149 */             button.setText(tableRow.won, Font.Align.CENTER, Assets.font10);
/* 150 */             button.setActive(false);
/* 151 */             this.resultWidgets.add(button);
/* 152 */             this.widgets.add(button);
/*     */ 
/*     */             
/* 155 */             button = new Button();
/* 156 */             button.setGeometry(dx + 140, 0, 72, 23);
/* 157 */             button.setColors(Integer.valueOf(bodyColor), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 158 */             button.setText(tableRow.drawn, Font.Align.CENTER, Assets.font10);
/* 159 */             button.setActive(false);
/* 160 */             this.resultWidgets.add(button);
/* 161 */             this.widgets.add(button);
/*     */ 
/*     */             
/* 164 */             button = new Button();
/* 165 */             button.setGeometry(dx + 210, 0, 72, 23);
/* 166 */             button.setColors(Integer.valueOf(bodyColor), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 167 */             button.setText(tableRow.lost, Font.Align.CENTER, Assets.font10);
/* 168 */             button.setActive(false);
/* 169 */             this.resultWidgets.add(button);
/* 170 */             this.widgets.add(button);
/*     */ 
/*     */             
/* 173 */             button = new Button();
/* 174 */             button.setGeometry(dx + 280, 0, 72, 23);
/* 175 */             button.setColors(Integer.valueOf(bodyColor), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 176 */             button.setText(tableRow.goalsFor, Font.Align.CENTER, Assets.font10);
/* 177 */             button.setActive(false);
/* 178 */             this.resultWidgets.add(button);
/* 179 */             this.widgets.add(button);
/*     */ 
/*     */             
/* 182 */             button = new Button();
/* 183 */             button.setGeometry(dx + 350, 0, 72, 23);
/* 184 */             button.setColors(Integer.valueOf(bodyColor), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 185 */             button.setText(tableRow.goalsAgainst, Font.Align.CENTER, Assets.font10);
/* 186 */             button.setActive(false);
/* 187 */             this.resultWidgets.add(button);
/* 188 */             this.widgets.add(button);
/*     */ 
/*     */             
/* 191 */             button = new Button();
/* 192 */             button.setGeometry(dx + 420, 0, 72, 23);
/* 193 */             button.setColors(Integer.valueOf(bodyColor), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 194 */             button.setText(tableRow.points, Font.Align.CENTER, Assets.font10);
/* 195 */             button.setActive(false);
/* 196 */             this.resultWidgets.add(button);
/* 197 */             this.widgets.add(button);
/*     */             
/* 199 */             tm++;
/*     */           } 
/*     */         } 
/* 202 */         this.offset = 0;
/* 203 */         if (groups.currentGroup >= visibleGroups) {
/* 204 */           this.offset = groups.currentGroup - visibleGroups + 1;
/*     */         }
/* 206 */         updateResultWidgets();
/*     */         
/* 208 */         if (groups.groups.size() > visibleGroups) {
/* 209 */           int topScrollY = 98 + 10 * (24 - visibleGroups * (groups.groupNumberOfTeams() + 2)) + 21;
/* 210 */           int bottomScrollY = topScrollY + visibleGroups * tableHeight - 42 - 36;
/*     */           
/* 212 */           this.maxOffset = groups.groups.size() - visibleGroups;
/*     */           
/* 214 */           ScrollButton scrollButton = new ScrollButton(180, topScrollY, -1);
/* 215 */           this.widgets.add(scrollButton);
/*     */           
/* 217 */           scrollButton = new ScrollButton(180, bottomScrollY, 1);
/* 218 */           this.widgets.add(scrollButton);
/*     */         } 
/*     */         break;
/*     */       
/*     */       case KNOCKOUT:
/* 223 */         knockout = (Knockout)this.tournament.getRound();
/* 224 */         this.matches = knockout.getMatches();
/*     */         
/* 226 */         this.offset = 0;
/* 227 */         if (this.matches.size() > 8 && this.tournament.currentMatch > 4) {
/* 228 */           this.offset = Math.min(this.tournament.currentMatch - 4, this.matches.size() - 8);
/*     */         }
/*     */         
/* 231 */         dy = 100;
/* 232 */         if (this.matches.size() < 8) {
/* 233 */           dy += 64 * (8 - this.matches.size()) / 2;
/*     */         }
/*     */ 
/*     */         
/* 237 */         for (m = 0; m < this.matches.size(); m++) {
/* 238 */           Match match1 = this.matches.get(m);
/* 239 */           int qualified = knockout.getLeg().getQualifiedTeam(match1);
/* 240 */           int borderColor = 1973790;
/* 241 */           if (qualified == match1.teams[0]) borderColor = 2545466; 
/* 242 */           if (qualified == match1.teams[1]) borderColor = 13906750;
/*     */           
/* 244 */           TeamButton teamButton2 = new TeamButton(335, dy + 64 * m, this.tournament.teams.get(match1.teams[0]), Font.Align.RIGHT, borderColor);
/* 245 */           this.resultWidgets.add(teamButton2);
/* 246 */           this.widgets.add(teamButton2);
/*     */ 
/*     */           
/* 249 */           Label label2 = new Label();
/* 250 */           label2.setGeometry(595, dy + 64 * m, 30, 26);
/* 251 */           label2.setText("", Font.Align.RIGHT, Assets.font10);
/* 252 */           if (match1.getResult() != null) {
/* 253 */             label2.setText(match1.getResult()[0]);
/*     */           }
/* 255 */           this.resultWidgets.add(label2);
/* 256 */           this.widgets.add(label2);
/*     */           
/* 258 */           label2 = new VersusLabel(dy + 64 * m, match1);
/* 259 */           this.resultWidgets.add(label2);
/* 260 */           this.widgets.add(label2);
/*     */ 
/*     */           
/* 263 */           label2 = new Label();
/* 264 */           label2.setGeometry(655, dy + 64 * m, 30, 26);
/* 265 */           label2.setText("", Font.Align.LEFT, Assets.font10);
/* 266 */           if (match1.isEnded()) {
/* 267 */             label2.setText(match1.getResult()[1]);
/*     */           }
/* 269 */           this.resultWidgets.add(label2);
/* 270 */           this.widgets.add(label2);
/*     */           
/* 272 */           borderColor = 1973790;
/* 273 */           if (qualified == match1.teams[1]) borderColor = 2545466; 
/* 274 */           if (qualified == match1.teams[0]) borderColor = 13906750; 
/* 275 */           TeamButton teamButton1 = new TeamButton(705, dy + 64 * m, this.tournament.teams.get(match1.teams[1]), Font.Align.LEFT, borderColor);
/* 276 */           this.resultWidgets.add(teamButton1);
/* 277 */           this.widgets.add(teamButton1);
/*     */ 
/*     */           
/* 280 */           Label label1 = new Label();
/* 281 */           Objects.requireNonNull(game.gui); label1.setGeometry(1280 / 2 - 360, dy + 26 + 64 * m, 720, 26);
/* 282 */           label1.setText(knockout.getMatchStatus(match1), Font.Align.CENTER, font10green);
/* 283 */           this.resultWidgets.add(label1);
/* 284 */           this.widgets.add(label1);
/*     */         } 
/* 286 */         updateResultWidgets();
/*     */         
/* 288 */         if (!this.tournament.isEnded())
/*     */         {
/* 290 */           if (this.matches.size() > 8) {
/* 291 */             this.maxOffset = this.matches.size() - 8;
/*     */             
/* 293 */             ScrollButton scrollButton = new ScrollButton(228, 115, -1);
/* 294 */             this.widgets.add(scrollButton);
/*     */             
/* 296 */             scrollButton = new ScrollButton(228, 564, 1);
/* 297 */             this.widgets.add(scrollButton);
/*     */           } 
/*     */         }
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 304 */     Label label = new Label();
/* 305 */     label.setGeometry(240, 618, 322, 36);
/* 306 */     label.setText((this.tournament.getTeam(0)).name, Font.Align.RIGHT, Assets.font14);
/* 307 */     this.widgets.add(label);
/*     */     
/* 309 */     Match match = this.tournament.getMatch();
/*     */ 
/*     */     
/* 312 */     label = new Label();
/* 313 */     Objects.requireNonNull(game.gui); label.setGeometry(1280 / 2 - 60, 618, 40, 36);
/* 314 */     label.setText("", Font.Align.RIGHT, Assets.font14);
/* 315 */     if (match.isEnded()) {
/* 316 */       label.setText(match.getResult()[0]);
/*     */     }
/* 318 */     this.widgets.add(label);
/*     */ 
/*     */     
/* 321 */     label = new Label();
/* 322 */     Objects.requireNonNull(game.gui); label.setGeometry(1280 / 2 - 20, 618, 40, 36);
/* 323 */     label.setText("", Font.Align.CENTER, Assets.font14);
/* 324 */     if (match.isEnded()) {
/* 325 */       label.setText("-");
/*     */     } else {
/* 327 */       label.setText(Assets.gettext("ABBREVIATIONS.VERSUS"));
/*     */     } 
/* 329 */     this.widgets.add(label);
/*     */ 
/*     */     
/* 332 */     label = new Label();
/* 333 */     Objects.requireNonNull(game.gui); label.setGeometry(1280 / 2 + 20, 618, 40, 36);
/* 334 */     label.setText("", Font.Align.LEFT, Assets.font14);
/* 335 */     if (match.isEnded()) {
/* 336 */       label.setText(match.getResult()[1]);
/*     */     }
/* 338 */     this.widgets.add(label);
/*     */ 
/*     */     
/* 341 */     label = new Label();
/* 342 */     label.setGeometry(720, 618, 322, 36);
/* 343 */     label.setText((this.tournament.getTeam(1)).name, Font.Align.LEFT, Assets.font14);
/* 344 */     this.widgets.add(label);
/*     */     
/* 346 */     ViewStatisticsButton viewStatisticsButton = new ViewStatisticsButton();
/* 347 */     this.widgets.add(viewStatisticsButton);
/*     */     
/* 349 */     ExitButton exitButton = new ExitButton();
/* 350 */     this.widgets.add(exitButton);
/*     */     
/* 352 */     if (this.tournament.isEnded()) {
/*     */       
/* 354 */       setSelectedWidget((Widget)exitButton);
/*     */ 
/*     */     
/*     */     }
/* 358 */     else if (match.isEnded()) {
/* 359 */       NextMatchButton nextMatchButton = new NextMatchButton();
/* 360 */       this.widgets.add(nextMatchButton);
/* 361 */       setSelectedWidget((Widget)nextMatchButton);
/*     */     } else {
/* 363 */       PlayViewMatchButton playViewMatchButton = new PlayViewMatchButton();
/* 364 */       this.widgets.add(playViewMatchButton);
/*     */       
/* 366 */       ViewResultButton viewResultButton = new ViewResultButton();
/* 367 */       this.widgets.add(viewResultButton);
/*     */       
/* 369 */       if (this.tournament.bothComputers() || this.tournament.userPrefersResult) {
/* 370 */         setSelectedWidget((Widget)viewResultButton);
/*     */       } else {
/* 372 */         setSelectedWidget((Widget)playViewMatchButton);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button
/*     */   {
/*     */     TeamButton(int x, int y, Team team, Font.Align align, int borderColor) {
/* 381 */       setGeometry(x, y, 240, 26);
/* 382 */       int bodyColor = (team.controlMode == Team.ControlMode.COMPUTER) ? 9211020 : team.controlModeColor();
/* 383 */       setColors(Integer.valueOf(bodyColor), Integer.valueOf(borderColor), Integer.valueOf(borderColor));
/* 384 */       setText(team.name, align, Assets.font10);
/* 385 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class VersusLabel
/*     */     extends Label {
/*     */     VersusLabel(int y, Match match) {
/* 392 */       Objects.requireNonNull(PlayTournament.this.game.gui); setGeometry((1280 - 30) / 2, y, 30, 26);
/*     */       
/* 394 */       setText(Assets.gettext("ABBREVIATIONS.VERSUS"), Font.Align.CENTER, Assets.font10);
/* 395 */       if (match.isEnded()) {
/* 396 */         setText("-");
/*     */       }
/* 398 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ScrollButton
/*     */     extends Button {
/*     */     int direction;
/*     */     
/*     */     ScrollButton(int x, int y, int direction) {
/* 407 */       this.direction = direction;
/* 408 */       setGeometry(x, y, 20, 36);
/* 409 */       this.textureRegion = Assets.scroll[(direction == 1) ? 1 : 0];
/* 410 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 415 */       scroll(this.direction);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 420 */       scroll(this.direction);
/*     */     }
/*     */     
/*     */     private void scroll(int direction) {
/* 424 */       PlayTournament.this.offset = EMath.slide(PlayTournament.this.offset, 0, PlayTournament.this.maxOffset, direction);
/* 425 */       PlayTournament.this.updateResultWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayViewMatchButton
/*     */     extends Button {
/*     */     PlayViewMatchButton() {
/* 432 */       Objects.requireNonNull(PlayTournament.this.game.gui); setGeometry(1280 / 2 - 430, 660, 220, 36);
/* 433 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 434 */       setText("", Font.Align.CENTER, Assets.font14);
/* 435 */       if (PlayTournament.this.tournament.bothComputers()) {
/* 436 */         setText(Assets.gettext("VIEW MATCH"));
/*     */       } else {
/* 438 */         setText("- " + Assets.gettext("MATCH") + " -");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 444 */       PlayTournament.this.tournament.userPrefersResult = false;
/*     */       
/* 446 */       Team homeTeam = PlayTournament.this.tournament.getTeam(0);
/* 447 */       Team awayTeam = PlayTournament.this.tournament.getTeam(1);
/*     */       
/* 449 */       Match match = PlayTournament.this.tournament.getMatch();
/* 450 */       match.setTeam(0, homeTeam);
/* 451 */       match.setTeam(1, awayTeam);
/*     */ 
/*     */       
/* 454 */       PlayTournament.this.game.inputDevices.setAvailability(true);
/* 455 */       homeTeam.setInputDevice(null);
/* 456 */       homeTeam.releaseNonAiInputDevices();
/* 457 */       awayTeam.setInputDevice(null);
/* 458 */       awayTeam.releaseNonAiInputDevices();
/*     */ 
/*     */       
/* 461 */       if (homeTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 462 */         if (PlayTournament.this.lastFireInputDevice != null) {
/* 463 */           homeTeam.setInputDevice(PlayTournament.this.lastFireInputDevice);
/*     */         }
/* 465 */         PlayTournament.navigation.competition = (Competition)PlayTournament.this.tournament;
/* 466 */         PlayTournament.navigation.team = homeTeam;
/* 467 */         PlayTournament.this.game.setScreen((Screen)new SetTeam(PlayTournament.this.game));
/* 468 */       } else if (awayTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 469 */         if (PlayTournament.this.lastFireInputDevice != null) {
/* 470 */           awayTeam.setInputDevice(PlayTournament.this.lastFireInputDevice);
/*     */         }
/* 472 */         PlayTournament.navigation.competition = (Competition)PlayTournament.this.tournament;
/* 473 */         PlayTournament.navigation.team = awayTeam;
/* 474 */         PlayTournament.this.game.setScreen((Screen)new SetTeam(PlayTournament.this.game));
/*     */       } else {
/* 476 */         PlayTournament.navigation.competition = (Competition)PlayTournament.this.tournament;
/* 477 */         PlayTournament.this.game.setScreen((Screen)new MatchSetup(PlayTournament.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class NextMatchButton
/*     */     extends Button {
/*     */     NextMatchButton() {
/* 485 */       Objects.requireNonNull(PlayTournament.this.game.gui); setGeometry(1280 / 2 - 430, 660, 460, 36);
/* 486 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 487 */       setText(Assets.gettext(PlayTournament.this.tournament.nextMatchLabel()), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 492 */       nextMatch();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 497 */       if (PlayTournament.this.tournament.nextMatchOnHold()) {
/* 498 */         nextMatch();
/*     */       }
/*     */     }
/*     */     
/*     */     private void nextMatch() {
/* 503 */       PlayTournament.this.tournament.nextMatch();
/* 504 */       PlayTournament.this.game.setScreen((Screen)new PlayTournament(PlayTournament.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewResultButton
/*     */     extends Button {
/*     */     ViewResultButton() {
/* 511 */       Objects.requireNonNull(PlayTournament.this.game.gui); setGeometry(1280 / 2 - 190, 660, 220, 36);
/* 512 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 513 */       setText("", Font.Align.CENTER, Assets.font14);
/* 514 */       if (PlayTournament.this.tournament.bothComputers()) {
/* 515 */         setText(Assets.gettext("VIEW RESULT"));
/*     */       } else {
/* 517 */         setText("- " + Assets.gettext("RESULT") + " -");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 523 */       viewResult();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 528 */       if (PlayTournament.this.tournament.bothComputers()) {
/* 529 */         viewResult();
/*     */       }
/*     */     }
/*     */     
/*     */     private void viewResult() {
/* 534 */       if (!PlayTournament.this.tournament.bothComputers()) {
/* 535 */         PlayTournament.this.tournament.userPrefersResult = true;
/*     */       }
/*     */       
/* 538 */       PlayTournament.this.tournament.generateResult();
/*     */       
/* 540 */       PlayTournament.this.game.setScreen((Screen)new PlayTournament(PlayTournament.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewStatisticsButton
/*     */     extends Button {
/*     */     ViewStatisticsButton() {
/* 547 */       Objects.requireNonNull(PlayTournament.this.game.gui); setGeometry(1280 / 2 + 50, 660, 180, 36);
/* 548 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 549 */       setText(Assets.gettext("STATS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 554 */       PlayTournament.this.game.setScreen((Screen)new ViewStatistics(PlayTournament.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 561 */       Objects.requireNonNull(PlayTournament.this.game.gui); setGeometry(1280 / 2 + 250, 660, 180, 36);
/* 562 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 563 */       setText(Assets.gettext("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 568 */       PlayTournament.this.game.setScreen((Screen)new Main(PlayTournament.this.game));
/*     */     } }
/*     */   
/*     */   private void updateResultWidgets() {
/*     */     Groups groups;
/* 573 */     int widgetsPerTable, tableHeight, visibleGroups, dy, m = 0;
/* 574 */     switch ((this.tournament.getRound()).type) {
/*     */       case GROUPS:
/* 576 */         groups = (Groups)this.tournament.getRound();
/* 577 */         widgetsPerTable = 8 + 9 * groups.groupNumberOfTeams();
/* 578 */         tableHeight = 21 * (groups.groupNumberOfTeams() + 1) + 23;
/* 579 */         visibleGroups = Math.min(groups.groups.size(), 548 / tableHeight);
/* 580 */         dy = 104 - this.offset * tableHeight + 10 * (24 - visibleGroups * (groups.groupNumberOfTeams() + 2));
/* 581 */         for (Widget w : this.resultWidgets) {
/* 582 */           if (m >= widgetsPerTable * this.offset && m < widgetsPerTable * (this.offset + visibleGroups)) {
/* 583 */             int i = m % widgetsPerTable;
/* 584 */             int row = (i < 8) ? 0 : ((i - 8) / 9 + 1);
/* 585 */             w.y = dy + 21 * row;
/* 586 */             w.setVisible(true);
/*     */           } else {
/* 588 */             w.setVisible(false);
/*     */           } 
/* 590 */           m++;
/* 591 */           if (m % widgetsPerTable == 0) dy += tableHeight;
/*     */         
/*     */         } 
/*     */         break;
/*     */       case KNOCKOUT:
/* 596 */         if (this.matches.size() > 8)
/* 597 */           for (Widget w : this.resultWidgets) {
/* 598 */             if (m >= 6 * this.offset && m < 6 * (this.offset + 8)) {
/* 599 */               w.y = 120 + 64 * (m / 6 - this.offset) + ((m % 6 == 5) ? 26 : 0);
/* 600 */               w.setVisible(true);
/*     */             } else {
/* 602 */               w.setVisible(false);
/*     */             } 
/* 604 */             m++;
/*     */           }  
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\PlayTournament.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */