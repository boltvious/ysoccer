/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.competitions.Cup;
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
/*     */ class PlayCup
/*     */   extends GLScreen
/*     */ {
/*     */   Cup cup;
/*     */   private ArrayList<Match> matches;
/*     */   private int offset;
/*     */   private ArrayList<Widget> resultWidgets;
/*     */   
/*     */   PlayCup(GLGame game) {
/*  36 */     super(game);
/*     */     
/*  38 */     this.cup = (Cup)game.competition;
/*     */     
/*  40 */     this.background = game.stateBackground;
/*     */     
/*  42 */     Font font10green = new Font(10, 13, 17, 12, 16, new RgbPair(16579836, 2220855));
/*  43 */     font10green.load();
/*     */ 
/*     */ 
/*     */     
/*  47 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, this.cup.getMenuTitle(), game.stateColor.body.intValue());
/*  48 */     this.widgets.add(titleBar);
/*     */     
/*  50 */     this.matches = this.cup.getMatches();
/*  51 */     this.offset = 0;
/*  52 */     if (this.matches.size() > 8 && this.cup.currentMatch > 4) {
/*  53 */       this.offset = Math.min(this.cup.currentMatch - 4, this.matches.size() - 8);
/*     */     }
/*     */     
/*  56 */     int dy = 100;
/*  57 */     if (this.matches.size() < 8) {
/*  58 */       dy += 64 * (8 - this.matches.size()) / 2;
/*     */     }
/*     */ 
/*     */     
/*  62 */     this.resultWidgets = new ArrayList<>();
/*  63 */     for (int m = 0; m < this.matches.size(); m++) {
/*  64 */       Match match1 = this.matches.get(m);
/*  65 */       int qualified = this.cup.getLeg().getQualifiedTeam(match1);
/*  66 */       int borderColor = 1973790;
/*  67 */       if (qualified == match1.teams[0]) borderColor = 2545466; 
/*  68 */       if (qualified == match1.teams[1]) borderColor = 13906750;
/*     */       
/*  70 */       TeamButton teamButton2 = new TeamButton(335, dy + 64 * m, this.cup.teams.get(match1.teams[0]), Font.Align.RIGHT, borderColor);
/*  71 */       this.resultWidgets.add(teamButton2);
/*  72 */       this.widgets.add(teamButton2);
/*     */ 
/*     */       
/*  75 */       Label label2 = new Label();
/*  76 */       label2.setGeometry(595, dy + 64 * m, 30, 26);
/*  77 */       label2.setText("", Font.Align.RIGHT, Assets.font10);
/*  78 */       if (match1.getResult() != null) {
/*  79 */         label2.setText(match1.getResult()[0]);
/*     */       }
/*  81 */       this.resultWidgets.add(label2);
/*  82 */       this.widgets.add(label2);
/*     */       
/*  84 */       label2 = new VersusLabel(dy + 64 * m, match1);
/*  85 */       this.resultWidgets.add(label2);
/*  86 */       this.widgets.add(label2);
/*     */ 
/*     */       
/*  89 */       label2 = new Label();
/*  90 */       label2.setGeometry(655, dy + 64 * m, 30, 26);
/*  91 */       label2.setText("", Font.Align.LEFT, Assets.font10);
/*  92 */       if (match1.isEnded()) {
/*  93 */         label2.setText(match1.getResult()[1]);
/*     */       }
/*  95 */       this.resultWidgets.add(label2);
/*  96 */       this.widgets.add(label2);
/*     */       
/*  98 */       borderColor = 1973790;
/*  99 */       if (qualified == match1.teams[1]) borderColor = 2545466; 
/* 100 */       if (qualified == match1.teams[0]) borderColor = 13906750; 
/* 101 */       TeamButton teamButton1 = new TeamButton(705, dy + 64 * m, this.cup.teams.get(match1.teams[1]), Font.Align.LEFT, borderColor);
/* 102 */       this.resultWidgets.add(teamButton1);
/* 103 */       this.widgets.add(teamButton1);
/*     */ 
/*     */       
/* 106 */       Label label1 = new Label();
/* 107 */       Objects.requireNonNull(game.gui); label1.setGeometry(1280 / 2 - 360, dy + 26 + 64 * m, 720, 26);
/* 108 */       label1.setText(this.cup.getMatchStatus(match1), Font.Align.CENTER, font10green);
/* 109 */       this.resultWidgets.add(label1);
/* 110 */       this.widgets.add(label1);
/*     */     } 
/* 112 */     updateResultWidgets();
/*     */ 
/*     */     
/* 115 */     Label label = new Label();
/* 116 */     label.setGeometry(240, 618, 322, 36);
/* 117 */     label.setText((this.cup.getTeam(0)).name, Font.Align.RIGHT, Assets.font14);
/* 118 */     this.widgets.add(label);
/*     */     
/* 120 */     Match match = this.cup.getMatch();
/*     */ 
/*     */     
/* 123 */     label = new Label();
/* 124 */     Objects.requireNonNull(game.gui); label.setGeometry(1280 / 2 - 60, 618, 40, 36);
/* 125 */     label.setText("", Font.Align.RIGHT, Assets.font14);
/* 126 */     if (match.isEnded()) {
/* 127 */       label.setText(match.getResult()[0]);
/*     */     }
/* 129 */     this.widgets.add(label);
/*     */ 
/*     */     
/* 132 */     label = new Label();
/* 133 */     Objects.requireNonNull(game.gui); label.setGeometry(1280 / 2 - 20, 618, 40, 36);
/* 134 */     label.setText("", Font.Align.CENTER, Assets.font14);
/* 135 */     if (match.isEnded()) {
/* 136 */       label.setText("-");
/*     */     } else {
/* 138 */       label.setText(Assets.gettext("ABBREVIATIONS.VERSUS"));
/*     */     } 
/* 140 */     this.widgets.add(label);
/*     */ 
/*     */     
/* 143 */     label = new Label();
/* 144 */     Objects.requireNonNull(game.gui); label.setGeometry(1280 / 2 + 20, 618, 40, 36);
/* 145 */     label.setText("", Font.Align.LEFT, Assets.font14);
/* 146 */     if (match.isEnded()) {
/* 147 */       label.setText(match.getResult()[1]);
/*     */     }
/* 149 */     this.widgets.add(label);
/*     */ 
/*     */     
/* 152 */     label = new Label();
/* 153 */     label.setGeometry(720, 618, 322, 36);
/* 154 */     label.setText((this.cup.getTeam(1)).name, Font.Align.LEFT, Assets.font14);
/* 155 */     this.widgets.add(label);
/*     */     
/* 157 */     ViewStatisticsButton viewStatisticsButton = new ViewStatisticsButton();
/* 158 */     this.widgets.add(viewStatisticsButton);
/*     */     
/* 160 */     ExitButton exitButton = new ExitButton();
/* 161 */     this.widgets.add(exitButton);
/*     */     
/* 163 */     if (this.cup.isEnded()) {
/*     */       
/* 165 */       setSelectedWidget((Widget)exitButton);
/*     */     }
/*     */     else {
/*     */       
/* 169 */       if (this.matches.size() > 8) {
/* 170 */         ScrollButton scrollButton = new ScrollButton(115, -1);
/* 171 */         this.widgets.add(scrollButton);
/*     */         
/* 173 */         scrollButton = new ScrollButton(564, 1);
/* 174 */         this.widgets.add(scrollButton);
/*     */       } 
/*     */       
/* 177 */       if (match.isEnded()) {
/* 178 */         NextMatchButton nextMatchButton = new NextMatchButton();
/* 179 */         this.widgets.add(nextMatchButton);
/* 180 */         setSelectedWidget((Widget)nextMatchButton);
/*     */       } else {
/* 182 */         PlayViewMatchButton playViewMatchButton = new PlayViewMatchButton();
/* 183 */         this.widgets.add(playViewMatchButton);
/*     */         
/* 185 */         ViewResultButton viewResultButton = new ViewResultButton();
/* 186 */         this.widgets.add(viewResultButton);
/*     */         
/* 188 */         if (this.cup.bothComputers() || this.cup.userPrefersResult) {
/* 189 */           setSelectedWidget((Widget)viewResultButton);
/*     */         } else {
/* 191 */           setSelectedWidget((Widget)playViewMatchButton);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private class TeamButton
/*     */     extends Button {
/*     */     TeamButton(int x, int y, Team team, Font.Align align, int borderColor) {
/* 200 */       setGeometry(x, y, 240, 26);
/* 201 */       int bodyColor = (team.controlMode == Team.ControlMode.COMPUTER) ? 9211020 : team.controlModeColor();
/* 202 */       setColors(Integer.valueOf(bodyColor), Integer.valueOf(borderColor), Integer.valueOf(borderColor));
/* 203 */       setText(team.name, align, Assets.font10);
/* 204 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class VersusLabel
/*     */     extends Label {
/*     */     VersusLabel(int y, Match match) {
/* 211 */       Objects.requireNonNull(PlayCup.this.game.gui); setGeometry((1280 - 30) / 2, y, 30, 26);
/*     */       
/* 213 */       setText(Assets.gettext("ABBREVIATIONS.VERSUS"), Font.Align.CENTER, Assets.font10);
/* 214 */       if (match.isEnded()) {
/* 215 */         setText("-");
/*     */       }
/* 217 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ScrollButton
/*     */     extends Button {
/*     */     int direction;
/*     */     
/*     */     ScrollButton(int y, int direction) {
/* 226 */       this.direction = direction;
/* 227 */       setGeometry(228, y, 20, 36);
/* 228 */       this.textureRegion = Assets.scroll[(direction == 1) ? 1 : 0];
/* 229 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 234 */       scroll(this.direction);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 239 */       scroll(this.direction);
/*     */     }
/*     */     
/*     */     private void scroll(int direction) {
/* 243 */       PlayCup.this.offset = EMath.slide(PlayCup.this.offset, 0, PlayCup.this.matches.size() - 8, direction);
/* 244 */       PlayCup.this.updateResultWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayViewMatchButton
/*     */     extends Button {
/*     */     PlayViewMatchButton() {
/* 251 */       Objects.requireNonNull(PlayCup.this.game.gui); setGeometry(1280 / 2 - 430, 660, 220, 36);
/* 252 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 253 */       setText("", Font.Align.CENTER, Assets.font14);
/* 254 */       if (PlayCup.this.cup.bothComputers()) {
/* 255 */         setText(Assets.gettext("VIEW MATCH"));
/*     */       } else {
/* 257 */         setText("- " + Assets.gettext("MATCH") + " -");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 263 */       PlayCup.this.cup.userPrefersResult = false;
/*     */       
/* 265 */       Team homeTeam = PlayCup.this.cup.getTeam(0);
/* 266 */       Team awayTeam = PlayCup.this.cup.getTeam(1);
/*     */       
/* 268 */       Match match = PlayCup.this.cup.getMatch();
/* 269 */       match.setTeam(0, homeTeam);
/* 270 */       match.setTeam(1, awayTeam);
/*     */ 
/*     */       
/* 273 */       PlayCup.this.game.inputDevices.setAvailability(true);
/* 274 */       homeTeam.setInputDevice(null);
/* 275 */       homeTeam.releaseNonAiInputDevices();
/* 276 */       awayTeam.setInputDevice(null);
/* 277 */       awayTeam.releaseNonAiInputDevices();
/*     */ 
/*     */       
/* 280 */       if (homeTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 281 */         if (PlayCup.this.lastFireInputDevice != null) {
/* 282 */           homeTeam.setInputDevice(PlayCup.this.lastFireInputDevice);
/*     */         }
/* 284 */         PlayCup.navigation.competition = (Competition)PlayCup.this.cup;
/* 285 */         PlayCup.navigation.team = homeTeam;
/* 286 */         PlayCup.this.game.setScreen((Screen)new SetTeam(PlayCup.this.game));
/* 287 */       } else if (awayTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 288 */         if (PlayCup.this.lastFireInputDevice != null) {
/* 289 */           awayTeam.setInputDevice(PlayCup.this.lastFireInputDevice);
/*     */         }
/* 291 */         PlayCup.navigation.competition = (Competition)PlayCup.this.cup;
/* 292 */         PlayCup.navigation.team = awayTeam;
/* 293 */         PlayCup.this.game.setScreen((Screen)new SetTeam(PlayCup.this.game));
/*     */       } else {
/* 295 */         PlayCup.navigation.competition = (Competition)PlayCup.this.cup;
/* 296 */         PlayCup.this.game.setScreen((Screen)new MatchSetup(PlayCup.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class NextMatchButton extends Button {
/*     */     private boolean onHold;
/*     */     
/*     */     NextMatchButton() {
/*     */       String label;
/* 306 */       Objects.requireNonNull(PlayCup.this.game.gui); setGeometry(1280 / 2 - 430, 660, 460, 36);
/* 307 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/*     */       
/* 309 */       if (PlayCup.this.cup.isLegEnded()) {
/* 310 */         if (PlayCup.this.cup.isRoundEnded()) {
/* 311 */           label = "NEXT ROUND";
/*     */         } else {
/* 313 */           switch (PlayCup.this.cup.currentLeg) {
/*     */             case 0:
/* 315 */               if ((PlayCup.this.cup.getRound()).numberOfLegs == 2) {
/* 316 */                 String str = "CUP.2ND LEG ROUND"; break;
/*     */               } 
/* 318 */               label = "CUP.PLAY REPLAYS";
/*     */               break;
/*     */             
/*     */             default:
/* 322 */               label = "CUP.PLAY REPLAYS";
/*     */               break;
/*     */           } 
/*     */         } 
/* 326 */         this.onHold = false;
/*     */       } else {
/* 328 */         label = "NEXT MATCH";
/* 329 */         this.onHold = true;
/*     */       } 
/* 331 */       setText(Assets.gettext(label), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 336 */       nextMatch();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 341 */       if (this.onHold) {
/* 342 */         nextMatch();
/*     */       }
/*     */     }
/*     */     
/*     */     private void nextMatch() {
/* 347 */       PlayCup.this.cup.nextMatch();
/* 348 */       PlayCup.this.game.setScreen((Screen)new PlayCup(PlayCup.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewResultButton
/*     */     extends Button {
/*     */     ViewResultButton() {
/* 355 */       Objects.requireNonNull(PlayCup.this.game.gui); setGeometry(1280 / 2 - 190, 660, 220, 36);
/* 356 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 357 */       setText("", Font.Align.CENTER, Assets.font14);
/* 358 */       if (PlayCup.this.cup.bothComputers()) {
/* 359 */         setText(Assets.gettext("VIEW RESULT"));
/*     */       } else {
/* 361 */         setText("- " + Assets.gettext("RESULT") + " -");
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 367 */       viewResult();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 372 */       if (PlayCup.this.cup.bothComputers()) {
/* 373 */         viewResult();
/*     */       }
/*     */     }
/*     */     
/*     */     private void viewResult() {
/* 378 */       if (!PlayCup.this.cup.bothComputers()) {
/* 379 */         PlayCup.this.cup.userPrefersResult = true;
/*     */       }
/*     */       
/* 382 */       PlayCup.this.cup.generateResult();
/*     */       
/* 384 */       PlayCup.this.game.setScreen((Screen)new PlayCup(PlayCup.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ViewStatisticsButton
/*     */     extends Button {
/*     */     ViewStatisticsButton() {
/* 391 */       Objects.requireNonNull(PlayCup.this.game.gui); setGeometry(1280 / 2 + 50, 660, 180, 36);
/* 392 */       setColors(Integer.valueOf(1280801), Integer.valueOf(1818927), Integer.valueOf(18452));
/* 393 */       setText(Assets.gettext("STATS"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 398 */       PlayCup.this.game.setScreen((Screen)new ViewStatistics(PlayCup.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 405 */       Objects.requireNonNull(PlayCup.this.game.gui); setGeometry(1280 / 2 + 250, 660, 180, 36);
/* 406 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 407 */       setText(Assets.gettext("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 412 */       PlayCup.this.game.setScreen((Screen)new Main(PlayCup.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateResultWidgets() {
/* 417 */     if (this.matches.size() > 8) {
/* 418 */       int m = 0;
/* 419 */       for (Widget w : this.resultWidgets) {
/* 420 */         if (m >= 6 * this.offset && m < 6 * (this.offset + 8)) {
/* 421 */           w.y = 120 + 64 * (m / 6 - this.offset) + ((m % 6 == 5) ? 26 : 0);
/* 422 */           w.setVisible(true);
/*     */         } else {
/* 424 */           w.setVisible(false);
/*     */         } 
/* 426 */         m++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\PlayCup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */