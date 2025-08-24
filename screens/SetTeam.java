/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.framework.RgbPair;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Picture;
/*     */ import com.ygames.ysoccer.gui.TacticsBoard;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Match;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Tactics;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.ArrayList;
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
/*     */ class SetTeam
/*     */   extends GLScreen
/*     */ {
/*     */   private Team ownTeam;
/*     */   private Team opponentTeam;
/*     */   private Team shownTeam;
/*     */   private int reservedInputDevices;
/*     */   private int selectedPos;
/*     */   private boolean compareTactics;
/*     */   private Font font10yellow;
/*  44 */   private List<Widget> playerButtons = new ArrayList<>();
/*     */   private TacticsBoard tacticsBoard;
/*  46 */   private Widget[] tacticsButtons = new Widget[18];
/*     */   private Widget teamInputDeviceButton;
/*     */   
/*     */   SetTeam(GLGame game) {
/*  50 */     super(game);
/*  51 */     this.playMenuMusic = false;
/*     */     
/*  53 */     Match match = navigation.competition.getMatch();
/*     */     
/*  55 */     if (navigation.team.index == 0) {
/*  56 */       this.ownTeam = match.team[0];
/*  57 */       this.opponentTeam = match.team[1];
/*  58 */       this.reservedInputDevices = (this.ownTeam.controlMode != Team.ControlMode.COMPUTER && this.opponentTeam.controlMode != Team.ControlMode.COMPUTER) ? 1 : 0;
/*     */     } else {
/*  60 */       this.ownTeam = match.team[1];
/*  61 */       this.opponentTeam = match.team[0];
/*  62 */       this.reservedInputDevices = 0;
/*     */     } 
/*     */ 
/*     */     
/*  66 */     if (this.ownTeam.inputDevice == null && this.ownTeam.nonAiInputDevicesCount() == 0) {
/*  67 */       this.ownTeam.inputDevice = game.inputDevices.assignFirstAvailable();
/*     */     }
/*     */     
/*  70 */     this.shownTeam = this.ownTeam;
/*  71 */     this.selectedPos = -1;
/*  72 */     this.compareTactics = false;
/*     */     
/*  74 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*     */     
/*  76 */     this.font10yellow = new Font(10, 13, 17, 12, 16, new RgbPair(16579836, 16579584));
/*  77 */     this.font10yellow.load();
/*     */     
/*  79 */     this.ownTeam.loadImage();
/*  80 */     this.opponentTeam.loadImage();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  85 */     for (int pos = 0; pos < 26; pos++) {
/*     */       
/*  87 */       PlayerInputDeviceButton playerInputDeviceButton = new PlayerInputDeviceButton(pos);
/*  88 */       this.playerButtons.add(playerInputDeviceButton);
/*  89 */       this.widgets.add(playerInputDeviceButton);
/*     */       
/*  91 */       PlayerFaceButton playerFaceButton = new PlayerFaceButton(pos);
/*  92 */       this.playerButtons.add(playerFaceButton);
/*  93 */       this.widgets.add(playerFaceButton);
/*     */       
/*  95 */       PlayerNumberButton playerNumberButton = new PlayerNumberButton(pos);
/*  96 */       this.playerButtons.add(playerNumberButton);
/*  97 */       this.widgets.add(playerNumberButton);
/*     */       
/*  99 */       PlayerNameButton playerNameButton = new PlayerNameButton(pos);
/* 100 */       this.playerButtons.add(playerNameButton);
/* 101 */       this.widgets.add(playerNameButton);
/*     */       
/* 103 */       int x = 550;
/* 104 */       if (this.shownTeam.type != Team.Type.NATIONAL) {
/* 105 */         if (game.settings.useFlags) {
/* 106 */           PlayerNationalityFlagButton playerNationalityFlagButton = new PlayerNationalityFlagButton(pos);
/* 107 */           this.playerButtons.add(playerNationalityFlagButton);
/* 108 */           this.widgets.add(playerNationalityFlagButton);
/* 109 */           x += 26;
/*     */         } else {
/* 111 */           PlayerNationalityCodeButton playerNationalityCodeButton = new PlayerNationalityCodeButton(pos);
/* 112 */           this.playerButtons.add(playerNationalityCodeButton);
/* 113 */           this.widgets.add(playerNationalityCodeButton);
/* 114 */           x += 58;
/*     */         } 
/*     */       }
/*     */       
/* 118 */       PlayerRoleButton playerRoleButton = new PlayerRoleButton(x, pos);
/* 119 */       this.playerButtons.add(playerRoleButton);
/* 120 */       this.widgets.add(playerRoleButton);
/* 121 */       x += 34;
/*     */       
/* 123 */       for (int skillIndex = 0; skillIndex < 3; skillIndex++) {
/* 124 */         PlayerSkillButton playerSkillButton = new PlayerSkillButton(pos, skillIndex, x);
/* 125 */         this.playerButtons.add(playerSkillButton);
/* 126 */         this.widgets.add(playerSkillButton);
/* 127 */         x += 13;
/*     */       } 
/* 129 */       x += 4;
/*     */       
/* 131 */       PlayerStarsButton playerStarsButton = new PlayerStarsButton(pos, x);
/* 132 */       this.playerButtons.add(playerStarsButton);
/* 133 */       this.widgets.add(playerStarsButton);
/*     */     } 
/*     */     
/* 136 */     this.tacticsBoard = new TacticsBoard(this.ownTeam, this.opponentTeam);
/* 137 */     Objects.requireNonNull(game.gui); this.tacticsBoard.setPosition(1280 / 2 + 140, 120);
/* 138 */     this.widgets.add(this.tacticsBoard);
/*     */     
/* 140 */     for (int t = 0; t < 18; t++) {
/* 141 */       TacticsButton tacticsButton = new TacticsButton(t);
/* 142 */       this.tacticsButtons[t] = (Widget)tacticsButton;
/* 143 */       this.widgets.add(tacticsButton);
/*     */     } 
/*     */     
/* 146 */     TacticsComparisonButton tacticsComparisonButton = new TacticsComparisonButton();
/* 147 */     this.widgets.add(tacticsComparisonButton);
/*     */     
/* 149 */     OpponentTeamButton opponentTeamButton = new OpponentTeamButton();
/* 150 */     this.widgets.add(opponentTeamButton);
/*     */     
/* 152 */     TeamInputDeviceButton teamInputDeviceButton = new TeamInputDeviceButton();
/* 153 */     this.teamInputDeviceButton = (Widget)teamInputDeviceButton;
/* 154 */     this.widgets.add(teamInputDeviceButton);
/*     */     
/* 156 */     ControlModeButton controlModeButton = new ControlModeButton();
/* 157 */     this.widgets.add(controlModeButton);
/*     */     
/* 159 */     EditTacticsButton editTacticsButton = new EditTacticsButton();
/* 160 */     this.widgets.add(editTacticsButton);
/*     */     
/* 162 */     CoachNameLabel coachNameLabel = new CoachNameLabel();
/* 163 */     this.widgets.add(coachNameLabel);
/*     */     
/* 165 */     TeamPicture teamPicture = new TeamPicture();
/* 166 */     this.widgets.add(teamPicture);
/*     */     
/* 168 */     TeamNameButton teamNameButton = new TeamNameButton();
/* 169 */     this.widgets.add(teamNameButton);
/*     */     
/* 171 */     PlayMatchButton playMatchButton = new PlayMatchButton();
/* 172 */     this.widgets.add(playMatchButton);
/*     */     
/* 174 */     setSelectedWidget((Widget)playMatchButton);
/*     */     
/* 176 */     ExitButton exitButton = new ExitButton();
/* 177 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private class PlayerInputDeviceButton extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerInputDeviceButton(int pos) {
/* 184 */       this.pos = pos;
/* 185 */       setGeometry(80, 120 + 22 * pos, 42, 20);
/* 186 */       setText("", Font.Align.CENTER, Assets.font10);
/* 187 */       setImagePosition(1, -2);
/* 188 */       setAddShadow(true);
/* 189 */       this.textOffsetX = 11;
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 194 */       if (this.pos < Math.min(11 + SetTeam.navigation.competition.benchSize, SetTeam.this.shownTeam.players.size())) {
/* 195 */         Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 196 */         if (player.inputDevice.type == InputDevice.Type.COMPUTER) {
/* 197 */           setText("");
/*     */         } else {
/* 199 */           setText(player.inputDevice.port + 1);
/*     */         } 
/* 201 */         this.textureRegion = Assets.controls[1][player.inputDevice.type.ordinal()];
/* 202 */         setVisible(true);
/*     */       } else {
/* 204 */         setText("");
/* 205 */         this.textureRegion = null;
/* 206 */         setVisible(false);
/*     */       } 
/* 208 */       setActive((SetTeam.this.shownTeam == SetTeam.this.ownTeam && SetTeam.this.ownTeam.controlMode == Team.ControlMode.PLAYER));
/*     */     }
/*     */     
/*     */     public void onFire1Down() {
/*     */       InputDevice d;
/* 213 */       Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 214 */       switch (player.inputDevice.type) {
/*     */         
/*     */         case FRIENDLY:
/* 217 */           if (SetTeam.this.ownTeam.inputDevice != null) {
/* 218 */             player.setInputDevice(SetTeam.this.ownTeam.inputDevice);
/* 219 */             SetTeam.this.ownTeam.setInputDevice(null);
/*     */             break;
/*     */           } 
/* 222 */           if (SetTeam.this.game.inputDevices.getAvailabilityCount() > SetTeam.this.reservedInputDevices) {
/* 223 */             player.setInputDevice(SetTeam.this.game.inputDevices.assignFirstAvailable());
/*     */           }
/*     */           break;
/*     */         
/*     */         default:
/* 228 */           d = SetTeam.this.game.inputDevices.assignNextAvailable(player.inputDevice);
/* 229 */           if (d != null) {
/* 230 */             player.setInputDevice(d);
/*     */             break;
/*     */           } 
/* 233 */           player.inputDevice.setAvailable(true);
/* 234 */           player.setInputDevice((InputDevice)player.ai);
/* 235 */           if (SetTeam.this.ownTeam.nonAiInputDevicesCount() == 0) {
/* 236 */             SetTeam.this.ownTeam.setInputDevice(SetTeam.this.game.inputDevices.assignFirstAvailable());
/*     */           }
/*     */           break;
/*     */       } 
/*     */       
/* 241 */       SetTeam.this.updatePlayerButtons();
/* 242 */       SetTeam.this.teamInputDeviceButton.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerFaceButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerFaceButton(int pos) {
/* 251 */       this.pos = pos;
/* 252 */       setGeometry(122, 120 + 22 * pos, 24, 20);
/* 253 */       setImagePosition(2, -2);
/* 254 */       setActive(false);
/* 255 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 260 */       SetTeam.this.setPlayerWidgetColor((Widget)this, this.pos);
/* 261 */       Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 262 */       if (player == null) {
/* 263 */         this.textureRegion = null;
/*     */       } else {
/* 265 */         this.textureRegion = player.createFace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNumberButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerNumberButton(int pos) {
/* 275 */       this.pos = pos;
/* 276 */       setGeometry(148, 120 + 22 * pos, 34, 20);
/* 277 */       setText("", Font.Align.CENTER, Assets.font10);
/* 278 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 283 */       Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 284 */       if (player == null) {
/* 285 */         setText("");
/*     */       } else {
/* 287 */         setText(player.number);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNameButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerNameButton(int pos) {
/* 297 */       this.pos = pos;
/* 298 */       setGeometry(184, 120 + 22 * pos, 364, 20);
/* 299 */       setText("", Font.Align.LEFT, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 304 */       SetTeam.this.setPlayerWidgetColor((Widget)this, this.pos);
/* 305 */       Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 306 */       if (player == null) {
/* 307 */         setText("");
/* 308 */         setActive(false);
/*     */       } else {
/* 310 */         setText(player.name);
/* 311 */         setActive((SetTeam.this.shownTeam == SetTeam.this.ownTeam));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 318 */       if (SetTeam.this.selectedPos == -1) {
/* 319 */         SetTeam.this.selectedPos = this.pos;
/*     */       
/*     */       }
/* 322 */       else if (SetTeam.this.selectedPos == this.pos) {
/* 323 */         SetTeam.this.selectedPos = -1;
/*     */       }
/*     */       else {
/*     */         
/* 327 */         int ply1 = SetTeam.this.ownTeam.playerIndexAtPosition(SetTeam.this.selectedPos);
/* 328 */         int ply2 = SetTeam.this.ownTeam.playerIndexAtPosition(this.pos);
/*     */         
/* 330 */         Collections.swap(SetTeam.this.ownTeam.players, ply1, ply2);
/*     */         
/* 332 */         SetTeam.this.selectedPos = -1;
/*     */       } 
/* 334 */       SetTeam.this.updatePlayerButtons();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNationalityFlagButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerNationalityFlagButton(int pos) {
/* 343 */       this.pos = pos;
/* 344 */       setGeometry(550, 120 + 22 * pos, 26, 20);
/* 345 */       setImagePosition(1, 3);
/* 346 */       setActive(false);
/* 347 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 352 */       Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 353 */       if (player == null) {
/* 354 */         this.textureRegion = null;
/*     */       } else {
/* 356 */         this.textureRegion = Assets.getNationalityFlag(player.nationality);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNationalityCodeButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerNationalityCodeButton(int pos) {
/* 366 */       this.pos = pos;
/* 367 */       setGeometry(550, 120 + 22 * pos, 58, 20);
/* 368 */       setText("", Font.Align.CENTER, Assets.font10);
/* 369 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 374 */       Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 375 */       if (player == null) {
/* 376 */         setText("");
/*     */       } else {
/* 378 */         setText("(" + player.nationality + ")");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerRoleButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerRoleButton(int x, int pos) {
/* 388 */       this.pos = pos;
/* 389 */       setGeometry(x, 120 + 22 * pos, 34, 20);
/* 390 */       setText("", Font.Align.CENTER, Assets.font10);
/* 391 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 396 */       Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 397 */       if (player == null) {
/* 398 */         setText("");
/*     */       } else {
/* 400 */         setText(Assets.gettext(player.getRoleLabel()));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerSkillButton
/*     */     extends Button {
/*     */     int pos;
/*     */     int skillIndex;
/*     */     
/*     */     PlayerSkillButton(int pos, int skillIndex, int x) {
/* 411 */       this.pos = pos;
/* 412 */       this.skillIndex = skillIndex;
/* 413 */       setGeometry(x, 120 + 22 * pos, 13, 20);
/* 414 */       setText("", Font.Align.CENTER, SetTeam.this.font10yellow);
/* 415 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 420 */       Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 421 */       if (player == null) {
/* 422 */         setText("");
/*     */       } else {
/* 424 */         Player.Skill[] skills = player.getOrderedSkills();
/* 425 */         if (skills == null) {
/* 426 */           setText("");
/*     */         } else {
/* 428 */           setText(Assets.gettext(Player.getSkillLabel(skills[this.skillIndex])));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerStarsButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerStarsButton(int pos, int x) {
/* 439 */       this.pos = pos;
/* 440 */       setGeometry(x, 120 + 22 * pos, 64, 20);
/* 441 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 446 */       Player player = SetTeam.this.shownTeam.playerAtPosition(this.pos);
/* 447 */       if (player == null) {
/* 448 */         this.textureRegion = null;
/*     */       } else {
/* 450 */         this.textureRegion = Assets.stars[player.getStars()];
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class TacticsButton
/*     */     extends Button {
/*     */     int t;
/*     */     
/*     */     TacticsButton(int t) {
/* 460 */       this.t = t;
/* 461 */       Objects.requireNonNull(SetTeam.this.game.gui); setGeometry(1280 - 90 - 110, 120 + 23 * t, 110, 21);
/* 462 */       setText(Tactics.codes[t], Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 467 */       if (SetTeam.this.shownTeam.tactics == this.t) {
/* 468 */         setColors(Integer.valueOf(10320643), Integer.valueOf(14856196), Integer.valueOf(6770947));
/*     */       } else {
/* 470 */         setColors(Integer.valueOf(14856196), Integer.valueOf(16567856), Integer.valueOf(10320643));
/*     */       } 
/* 472 */       setActive((SetTeam.this.shownTeam == SetTeam.this.ownTeam));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 477 */       if (SetTeam.this.shownTeam.tactics != this.t) {
/* 478 */         SetTeam.this.shownTeam.tactics = this.t;
/* 479 */         SetTeam.this.updateTacticsButtons();
/* 480 */         SetTeam.this.updatePlayerButtons();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class TacticsComparisonButton
/*     */     extends Button {
/*     */     TacticsComparisonButton() {
/* 488 */       Objects.requireNonNull(SetTeam.this.game.gui); setGeometry(1280 / 2 + 140, 439, 264, 36);
/* 489 */       setColors(Integer.valueOf(8536576), Integer.valueOf(11823616), Integer.valueOf(4990464));
/* 490 */       setText("", Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 495 */       setVisible((SetTeam.this.shownTeam == SetTeam.this.ownTeam));
/* 496 */       if (SetTeam.this.compareTactics) {
/* 497 */         setText(Assets.gettext("TEAM TACTICS"));
/*     */       } else {
/* 499 */         setText(Assets.gettext("TACTICS COMPARISON"));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 505 */       SetTeam.this.compareTactics = !SetTeam.this.compareTactics;
/* 506 */       SetTeam.this.tacticsBoard.setCompareTactics(SetTeam.this.compareTactics);
/* 507 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class OpponentTeamButton
/*     */     extends Button {
/*     */     OpponentTeamButton() {
/* 514 */       Objects.requireNonNull(SetTeam.this.game.gui); setGeometry(1280 / 2 + 140, 483, 264, 36);
/* 515 */       setColors(Integer.valueOf(9118499), Integer.valueOf(12535089), Integer.valueOf(5707543));
/* 516 */       setText("", Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 521 */       setText(Assets.gettext((SetTeam.this.shownTeam == SetTeam.this.ownTeam) ? "VIEW OPPONENT" : "VIEW TEAM"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 526 */       if (SetTeam.this.shownTeam == SetTeam.this.ownTeam) {
/* 527 */         SetTeam.this.shownTeam = SetTeam.this.opponentTeam;
/* 528 */         SetTeam.this.tacticsBoard.setViewOpponent(true);
/*     */       } else {
/* 530 */         SetTeam.this.shownTeam = SetTeam.this.ownTeam;
/* 531 */         SetTeam.this.tacticsBoard.setViewOpponent(false);
/*     */       } 
/* 533 */       SetTeam.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamInputDeviceButton
/*     */     extends Button {
/*     */     TeamInputDeviceButton() {
/* 540 */       Objects.requireNonNull(SetTeam.this.game.gui); setGeometry(1280 / 2 + 140, 536, 202, 42);
/* 541 */       setText("", Font.Align.LEFT, Assets.font10);
/* 542 */       this.textOffsetX = 50;
/* 543 */       setImagePosition(8, 1);
/* 544 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 549 */       if (SetTeam.this.shownTeam.inputDevice != null) {
/* 550 */         setVisible((SetTeam.this.shownTeam == SetTeam.this.ownTeam));
/* 551 */         switch (SetTeam.this.shownTeam.inputDevice.type) {
/*     */           case FRIENDLY:
/* 553 */             setText("");
/*     */             break;
/*     */           
/*     */           case LEAGUE:
/* 557 */             setText(Assets.gettext("KEYBOARD") + " " + (SetTeam.this.shownTeam.inputDevice.port + 1));
/*     */             break;
/*     */           
/*     */           case CUP:
/* 561 */             setText(Assets.gettext("JOYSTICK") + " " + (SetTeam.this.shownTeam.inputDevice.port + 1));
/*     */             break;
/*     */         } 
/* 564 */         this.textureRegion = Assets.controls[0][SetTeam.this.shownTeam.inputDevice.type.ordinal()];
/*     */       } else {
/* 566 */         setVisible(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 572 */       updateTeamInputDevice(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 577 */       updateTeamInputDevice(-1);
/*     */     }
/*     */     
/*     */     private void updateTeamInputDevice(int n) {
/* 581 */       SetTeam.this.shownTeam.inputDevice = SetTeam.this.game.inputDevices.rotateAvailable(SetTeam.this.shownTeam.inputDevice, n);
/* 582 */       setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class EditTacticsButton
/*     */     extends Button {
/*     */     EditTacticsButton() {
/* 589 */       Objects.requireNonNull(SetTeam.this.game.gui); setGeometry(1280 - 90 - 110, 540, 110, 34);
/* 590 */       setColors(Integer.valueOf(12227078), Integer.valueOf(15316487), Integer.valueOf(6968068));
/* 591 */       setText(Assets.gettext("TACTICS.EDIT"), Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 596 */       setVisible((SetTeam.this.shownTeam == SetTeam.this.ownTeam));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 601 */       SetTeam.this.game.tacticsTeam = SetTeam.this.ownTeam;
/* 602 */       SetTeam.this.game.setScreen((Screen)new SelectTactics(SetTeam.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class ControlModeButton
/*     */     extends Button {
/*     */     ControlModeButton() {
/* 609 */       Objects.requireNonNull(SetTeam.this.game.gui); setGeometry(1280 / 2 + 140, 586, 155, 40);
/* 610 */       setText("", Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 615 */       switch (SetTeam.this.shownTeam.controlMode) {
/*     */         case FRIENDLY:
/* 617 */           setText(Assets.gettext("CONTROL MODE.COMPUTER") + ":");
/* 618 */           setColors(Integer.valueOf(9969182), Integer.valueOf(13052201), Integer.valueOf(6553600));
/*     */           break;
/*     */         
/*     */         case LEAGUE:
/* 622 */           setText(Assets.gettext("CONTROL MODE.PLAYER") + ":");
/* 623 */           setColors(Integer.valueOf(200), Integer.valueOf(1645055), Integer.valueOf(120));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 627 */           setText(Assets.gettext("CONTROL MODE.COACH") + ":");
/* 628 */           setColors(Integer.valueOf(39900), Integer.valueOf(1686527), Integer.valueOf(29088));
/*     */           break;
/*     */       } 
/* 631 */       setActive((SetTeam.this.shownTeam == SetTeam.this.ownTeam));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 636 */       switch (SetTeam.this.ownTeam.controlMode) {
/*     */         case LEAGUE:
/* 638 */           SetTeam.this.ownTeam.controlMode = Team.ControlMode.COACH;
/* 639 */           if (SetTeam.this.ownTeam.inputDevice == null) {
/* 640 */             SetTeam.this.ownTeam.releaseNonAiInputDevices();
/* 641 */             SetTeam.this.ownTeam.inputDevice = SetTeam.this.game.inputDevices.assignFirstAvailable();
/*     */           } 
/*     */           break;
/*     */         
/*     */         case CUP:
/* 646 */           SetTeam.this.ownTeam.controlMode = Team.ControlMode.PLAYER;
/*     */           break;
/*     */       } 
/* 649 */       setDirty(true);
/* 650 */       SetTeam.this.updatePlayerButtons();
/* 651 */       SetTeam.this.teamInputDeviceButton.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class CoachNameLabel
/*     */     extends Label {
/*     */     CoachNameLabel() {
/* 658 */       Objects.requireNonNull(SetTeam.this.game.gui); setPosition(1280 / 2 + 140 + 155 + 10, 606);
/* 659 */       setText("", Font.Align.LEFT, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 664 */       setText(SetTeam.this.shownTeam.coach.name);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamNameButton
/*     */     extends Button {
/*     */     TeamNameButton() {
/* 671 */       Objects.requireNonNull(SetTeam.this.game.gui); setGeometry((1280 - 780) / 2, 40, 780, 41);
/* 672 */       setText("", Font.Align.CENTER, Assets.font14);
/* 673 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 678 */       setText(SetTeam.this.shownTeam.name);
/* 679 */       if (SetTeam.this.shownTeam == SetTeam.this.ownTeam) {
/* 680 */         setColor(24030);
/*     */       } else {
/* 682 */         setColor(11278874);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamPicture
/*     */     extends Picture {
/*     */     TeamPicture() {
/* 690 */       setPosition(135, 60);
/* 691 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 696 */       setTextureRegion(SetTeam.this.shownTeam.image);
/* 697 */       limitToSize(100, 70);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayMatchButton
/*     */     extends Button {
/*     */     PlayMatchButton() {
/* 704 */       Objects.requireNonNull(SetTeam.this.game.gui); Objects.requireNonNull(SetTeam.this.game.gui); setGeometry(1280 / 2 + 140, 720 - 30 - 42, 240, 42);
/* 705 */       setColor(14417920);
/* 706 */       setText(Assets.gettext("PLAY MATCH"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 711 */       setVisible((SetTeam.this.shownTeam == SetTeam.this.ownTeam));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 716 */       if (SetTeam.navigation.team.index == 0 && SetTeam.this.opponentTeam.controlMode != Team.ControlMode.COMPUTER) {
/* 717 */         SetTeam.navigation.team = (SetTeam.navigation.competition.getMatch()).team[1];
/* 718 */         SetTeam.this.game.setScreen((Screen)new SetTeam(SetTeam.this.game));
/*     */       } else {
/* 720 */         SetTeam.this.game.setScreen((Screen)new MatchSetup(SetTeam.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 728 */       Objects.requireNonNull(SetTeam.this.game.gui); Objects.requireNonNull(SetTeam.this.game.gui); setGeometry(1280 - 150 - 90, 720 - 30 - 38 - 2, 150, 38);
/* 729 */       setColor(13124096);
/* 730 */       setText(Assets.gettext("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 735 */       setVisible((SetTeam.this.shownTeam == SetTeam.this.ownTeam));
/*     */     }
/*     */     
/*     */     public void onFire1Down() {
/*     */       FileHandle[] teamFileHandles;
/* 740 */       switch (SetTeam.navigation.competition.type) {
/*     */         case FRIENDLY:
/* 742 */           if (SetTeam.navigation.folder.equals(Assets.favouritesFile)) {
/* 743 */             SetTeam.this.game.setScreen((Screen)new SelectFavourites(SetTeam.this.game)); break;
/*     */           } 
/* 745 */           teamFileHandles = SetTeam.navigation.folder.list(Assets.teamFilenameFilter);
/* 746 */           if (teamFileHandles.length > 0) {
/* 747 */             SetTeam.this.game.setScreen((Screen)new SelectTeams(SetTeam.this.game)); break;
/*     */           } 
/* 749 */           SetTeam.this.game.setScreen((Screen)new SelectFolder(SetTeam.this.game));
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case LEAGUE:
/* 755 */           SetTeam.this.game.setScreen((Screen)new PlayLeague(SetTeam.this.game));
/*     */           break;
/*     */         
/*     */         case CUP:
/* 759 */           SetTeam.this.game.setScreen((Screen)new PlayCup(SetTeam.this.game));
/*     */           break;
/*     */         
/*     */         case TOURNAMENT:
/* 763 */           SetTeam.this.game.setScreen((Screen)new PlayTournament(SetTeam.this.game));
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void updatePlayerButtons() {
/* 770 */     for (Widget w : this.playerButtons) {
/* 771 */       w.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateTacticsButtons() {
/* 776 */     for (Widget w : this.tacticsButtons) {
/* 777 */       w.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setPlayerWidgetColor(Widget w, int pos) {
/* 782 */     if (this.shownTeam == this.ownTeam) {
/*     */       
/* 784 */       if (pos == 0) {
/* 785 */         w.setColor(38110);
/*     */       
/*     */       }
/* 788 */       else if (pos < 11) {
/* 789 */         w.setColor(24030);
/*     */       
/*     */       }
/* 792 */       else if (pos < this.shownTeam.players.size()) {
/*     */         
/* 794 */         if (pos < 11 + navigation.competition.benchSize) {
/* 795 */           w.setColor(18086);
/*     */         }
/*     */         else {
/*     */           
/* 799 */           w.setColor(3158064);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 804 */         w.setColor(1052688);
/*     */       } 
/*     */ 
/*     */       
/* 808 */       if (this.selectedPos == pos) {
/* 809 */         w.setColor(10040115);
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 815 */     else if (pos == 0) {
/* 816 */       w.setColor(15417665);
/*     */     
/*     */     }
/* 819 */     else if (pos < 11) {
/* 820 */       w.setColor(11278874);
/*     */     
/*     */     }
/* 823 */     else if (pos < this.shownTeam.players.size()) {
/*     */       
/* 825 */       if (pos < 11 + navigation.competition.benchSize) {
/* 826 */         w.setColor(8724255);
/*     */       }
/*     */       else {
/*     */         
/* 830 */         w.setColor(3158064);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 835 */       w.setColor(1052688);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SetTeam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */