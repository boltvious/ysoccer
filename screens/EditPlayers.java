/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Color3;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.InputButton;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Hair;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Skin;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EditPlayers
/*     */   extends GLScreen
/*     */ {
/*     */   Team team;
/*     */   private int selectedPos;
/*     */   private boolean modified;
/*  36 */   private TextureRegion[][] imageSkill = new TextureRegion[8][2];
/*     */   
/*  38 */   private Widget[] selectButtons = new Widget[26];
/*  39 */   private Widget[] priceButtons = new Widget[26];
/*     */   
/*     */   private Widget resetButton;
/*     */   private Widget saveExitButton;
/*     */   private Widget clipboardPlayerButton;
/*     */   
/*     */   EditPlayers(GLGame game, Team team, Boolean modified) {
/*  46 */     super(game);
/*  47 */     this.team = team;
/*  48 */     this.selectedPos = -1;
/*  49 */     this.modified = modified.booleanValue();
/*     */     
/*  51 */     this.background = new Texture("images/backgrounds/menu_edit_players.jpg");
/*     */     
/*  53 */     Texture texture = new Texture("images/skill.png");
/*  54 */     for (int i = 0; i < 8; i++) {
/*  55 */       for (int k = 0; k < 2; k++) {
/*  56 */         this.imageSkill[i][k] = new TextureRegion(texture, 32 * i, 13 * k, 32, 13);
/*  57 */         this.imageSkill[i][k].flip(false, true);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     for (int p = 0; p < 26; p++) {
/*  65 */       HairColorButton hairColorButton = new HairColorButton(p);
/*  66 */       this.widgets.add(hairColorButton);
/*     */       
/*  68 */       HairStyleButton hairStyleButton = new HairStyleButton(p);
/*  69 */       this.widgets.add(hairStyleButton);
/*     */       
/*  71 */       SkinColorButton skinColorButton = new SkinColorButton(p);
/*  72 */       this.widgets.add(skinColorButton);
/*     */       
/*  74 */       PlayerSelectButton playerSelectButton = new PlayerSelectButton(p);
/*  75 */       this.selectButtons[p] = (Widget)playerSelectButton;
/*  76 */       this.widgets.add(playerSelectButton);
/*     */       
/*  78 */       PlayerNumberButton playerNumberButton = new PlayerNumberButton(p);
/*  79 */       this.widgets.add(playerNumberButton);
/*     */       
/*  81 */       PlayerNameButton playerNameButton = new PlayerNameButton(p);
/*  82 */       this.widgets.add(playerNameButton);
/*     */       
/*  84 */       PlayerShirtNameButton playerShirtNameButton = new PlayerShirtNameButton(p);
/*  85 */       this.widgets.add(playerShirtNameButton);
/*     */       
/*  87 */       PlayerNationalityButton playerNationalityButton = new PlayerNationalityButton(p);
/*  88 */       this.widgets.add(playerNationalityButton);
/*     */       
/*  90 */       PlayerRoleButton playerRoleButton = new PlayerRoleButton(p);
/*  91 */       this.widgets.add(playerRoleButton);
/*     */       
/*  93 */       for (int k = 0; k < 7; k++) {
/*  94 */         SkillButton skillButton = new SkillButton(p, Player.Skill.values()[k]);
/*  95 */         this.widgets.add(skillButton);
/*     */       } 
/*     */       
/*  98 */       PlayerPriceButton playerPriceButton = new PlayerPriceButton(p);
/*  99 */       this.priceButtons[p] = (Widget)playerPriceButton;
/* 100 */       this.widgets.add(playerPriceButton);
/*     */     } 
/*     */     
/* 103 */     for (int j = 0; j < 7; j++) {
/* 104 */       SkillLabel skillLabel = new SkillLabel(Player.Skill.values()[j]);
/* 105 */       this.widgets.add(skillLabel);
/*     */     } 
/*     */     
/* 108 */     ClipBoardPlayerButton clipBoardPlayerButton = new ClipBoardPlayerButton();
/* 109 */     this.clipboardPlayerButton = (Widget)clipBoardPlayerButton;
/* 110 */     this.widgets.add(clipBoardPlayerButton);
/*     */     
/* 112 */     TeamNameButton teamNameButton = new TeamNameButton();
/* 113 */     this.widgets.add(teamNameButton);
/*     */     
/* 115 */     EditTeamButton editTeamButton = new EditTeamButton();
/* 116 */     this.widgets.add(editTeamButton);
/*     */     
/* 118 */     setSelectedWidget((Widget)editTeamButton);
/*     */     
/* 120 */     NewPlayerButton newPlayerButton = new NewPlayerButton();
/* 121 */     this.widgets.add(newPlayerButton);
/*     */     
/* 123 */     DeletePlayerButton deletePlayerButton = new DeletePlayerButton();
/* 124 */     this.widgets.add(deletePlayerButton);
/*     */     
/* 126 */     ResetButton resetButton = new ResetButton();
/* 127 */     this.resetButton = (Widget)resetButton;
/* 128 */     this.widgets.add(resetButton);
/*     */     
/* 130 */     SaveExitButton saveExitButton = new SaveExitButton();
/* 131 */     this.saveExitButton = (Widget)saveExitButton;
/* 132 */     this.widgets.add(saveExitButton);
/*     */   }
/*     */   
/*     */   private void setModifiedFlag() {
/* 136 */     this.modified = true;
/* 137 */     this.resetButton.setDirty(true);
/* 138 */     this.saveExitButton.setDirty(true);
/* 139 */     if (this.selectedPos != -1) {
/* 140 */       navigation.setClipboardPlayer(this.team.playerAtPosition(this.selectedPos));
/* 141 */       this.clipboardPlayerButton.setDirty(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private class HairColorButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     HairColorButton(int pos) {
/* 150 */       this.pos = pos;
/* 151 */       setGeometry(309, 95 + 21 * pos, 17, 19);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 156 */       EditPlayers.this.setPlayerWidgetColor((Widget)this, this.pos);
/* 157 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 158 */       if (player == null) {
/* 159 */         setActive(false);
/*     */       } else {
/* 161 */         Color3 hairColor = Hair.colors[player.hairColor.ordinal()];
/* 162 */         setColors(Integer.valueOf(hairColor.color2), Integer.valueOf(hairColor.color1), Integer.valueOf(hairColor.color3));
/* 163 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 169 */       updateHairColor(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 174 */       updateHairColor(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 179 */       updateHairColor(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 184 */       updateHairColor(-1);
/*     */     }
/*     */     
/*     */     private void updateHairColor(int n) {
/* 188 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/*     */       
/* 190 */       int color = player.hairColor.ordinal();
/* 191 */       color = EMath.rotate(color, Hair.Color.BLACK.ordinal(), Hair.Color.PUNK_BLOND.ordinal(), n);
/* 192 */       player.hairColor = Hair.Color.values()[color];
/*     */       
/* 194 */       setDirty(true);
/* 195 */       EditPlayers.this.selectButtons[this.pos].setDirty(true);
/* 196 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class HairStyleButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     HairStyleButton(int pos) {
/* 205 */       this.pos = pos;
/* 206 */       setGeometry(328, 95 + 21 * pos, 112, 19);
/* 207 */       setText("", Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 212 */       EditPlayers.this.setPlayerWidgetColor((Widget)this, this.pos);
/* 213 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 214 */       if (player == null) {
/* 215 */         setText("");
/* 216 */         setActive(false);
/*     */       } else {
/* 218 */         setText(player.hairStyle.replace('_', ' '));
/* 219 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 225 */       updateHairStyle(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 230 */       updateHairStyle(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 235 */       updateHairStyle(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 240 */       updateHairStyle(-1);
/*     */     }
/*     */     
/*     */     private void updateHairStyle(int n) {
/* 244 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/*     */       
/* 246 */       int i = Assets.hairStyles.indexOf(player.hairStyle);
/* 247 */       if (i == -1) {
/* 248 */         i = 0;
/*     */       } else {
/* 250 */         i = EMath.rotate(i, 0, Assets.hairStyles.size() - 1, n);
/*     */       } 
/* 252 */       player.hairStyle = Assets.hairStyles.get(i);
/* 253 */       setDirty(true);
/* 254 */       EditPlayers.this.selectButtons[this.pos].setDirty(true);
/* 255 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class SkinColorButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     SkinColorButton(int pos) {
/* 264 */       this.pos = pos;
/* 265 */       setGeometry(290, 95 + 21 * pos, 17, 19);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 270 */       EditPlayers.this.setPlayerWidgetColor((Widget)this, this.pos);
/* 271 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 272 */       if (player == null) {
/* 273 */         setActive(false);
/*     */       } else {
/* 275 */         Color3 skinColor = Skin.colors[player.skinColor.ordinal()];
/* 276 */         setColors(Integer.valueOf(skinColor.color2), Integer.valueOf(skinColor.color1), Integer.valueOf(skinColor.color3));
/* 277 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 283 */       updateSkinColor(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 288 */       updateSkinColor(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 293 */       updateSkinColor(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 298 */       updateSkinColor(-1);
/*     */     }
/*     */     
/*     */     private void updateSkinColor(int n) {
/* 302 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 303 */       player.skinColor = Skin.Color.values()[EMath.rotate(player.skinColor.ordinal(), Skin.Color.PINK.ordinal(), Skin.Color.RED.ordinal(), n)];
/*     */       
/* 305 */       setDirty(true);
/* 306 */       EditPlayers.this.selectButtons[this.pos].setDirty(true);
/* 307 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerSelectButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerSelectButton(int pos) {
/* 316 */       this.pos = pos;
/* 317 */       setImagePosition(2, -3);
/* 318 */       setGeometry(264, 95 + 21 * pos, 24, 19);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 323 */       EditPlayers.this.setPlayerWidgetColor((Widget)this, this.pos);
/* 324 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 325 */       if (player == null) {
/* 326 */         this.textureRegion = null;
/* 327 */         setActive(false);
/*     */       } else {
/* 329 */         this.textureRegion = player.createFace();
/* 330 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 337 */       if (EditPlayers.this.selectedPos == -1) {
/* 338 */         EditPlayers.this.selectedPos = this.pos;
/* 339 */         EditPlayers.navigation.setClipboardPlayer(EditPlayers.this.team.playerAtPosition(EditPlayers.this.selectedPos));
/*     */ 
/*     */       
/*     */       }
/* 343 */       else if (EditPlayers.this.selectedPos == this.pos) {
/* 344 */         EditPlayers.this.selectedPos = -1;
/* 345 */         EditPlayers.navigation.setClipboardPlayer(null);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 350 */         int ply1 = EditPlayers.this.team.playerIndexAtPosition(EditPlayers.this.selectedPos);
/* 351 */         int ply2 = EditPlayers.this.team.playerIndexAtPosition(this.pos);
/*     */         
/* 353 */         Collections.swap(EditPlayers.this.team.players, ply1, ply2);
/*     */         
/* 355 */         EditPlayers.this.selectedPos = -1;
/* 356 */         EditPlayers.navigation.setClipboardPlayer(null);
/* 357 */         EditPlayers.this.setModifiedFlag();
/*     */       } 
/*     */       
/* 360 */       EditPlayers.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNumberButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerNumberButton(int pos) {
/* 369 */       this.pos = pos;
/* 370 */       setGeometry(32, 95 + 21 * pos, 34, 19);
/* 371 */       setText("", Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 376 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 377 */       if (player == null) {
/* 378 */         setText("");
/* 379 */         setActive(false);
/*     */       } else {
/* 381 */         setText(player.number);
/* 382 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 388 */       updateNumber(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 393 */       updateNumber(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 398 */       updateNumber(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 403 */       updateNumber(-1);
/*     */     }
/*     */     
/*     */     private void updateNumber(int n) {
/* 407 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 408 */       EditPlayers.this.team.rotatePlayerNumber(player, n);
/* 409 */       setDirty(true);
/* 410 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNameButton
/*     */     extends InputButton {
/*     */     int pos;
/*     */     
/*     */     PlayerNameButton(int pos) {
/* 419 */       this.pos = pos;
/* 420 */       setGeometry(442, 95 + 21 * pos, 364, 19);
/* 421 */       setText("", Font.Align.LEFT, Assets.font10);
/* 422 */       setEntryLimit(28);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 427 */       EditPlayers.this.setPlayerWidgetColor((Widget)this, this.pos);
/* 428 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 429 */       if (player == null) {
/* 430 */         setText("");
/* 431 */         setActive(false);
/*     */       } else {
/* 433 */         setText(player.name);
/* 434 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 440 */       (EditPlayers.this.team.playerAtPosition(this.pos)).name = this.text;
/* 441 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerShirtNameButton
/*     */     extends InputButton {
/*     */     int pos;
/*     */     
/*     */     PlayerShirtNameButton(int pos) {
/* 450 */       this.pos = pos;
/* 451 */       setGeometry(68, 95 + 21 * pos, 194, 19);
/* 452 */       setText("", Font.Align.LEFT, Assets.font10);
/* 453 */       setEntryLimit(14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 458 */       EditPlayers.this.setPlayerWidgetColor((Widget)this, this.pos);
/* 459 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 460 */       if (player == null) {
/* 461 */         setText("");
/* 462 */         setActive(false);
/*     */       } else {
/* 464 */         setText(player.shirtName);
/* 465 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 471 */       (EditPlayers.this.team.playerAtPosition(this.pos)).shirtName = this.text;
/* 472 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNationalityButton
/*     */     extends InputButton {
/*     */     private int pos;
/*     */     
/*     */     PlayerNationalityButton(int pos) {
/* 481 */       this.pos = pos;
/* 482 */       setGeometry(808, 95 + 21 * pos, 56, 19);
/* 483 */       setText("", Font.Align.CENTER, Assets.font10);
/* 484 */       setEntryLimit(3);
/* 485 */       setInputFilter("[A-Z]");
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 490 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 491 */       if (player == null) {
/* 492 */         setText("");
/* 493 */         setActive(false);
/*     */       } else {
/* 495 */         setText(player.nationality);
/* 496 */         setActive((EditPlayers.this.team.type == Team.Type.CLUB));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 502 */       (EditPlayers.this.team.playerAtPosition(this.pos)).nationality = this.text;
/* 503 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerRoleButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerRoleButton(int pos) {
/* 512 */       this.pos = pos;
/* 513 */       setGeometry(866, 95 + 21 * pos, 30, 19);
/* 514 */       setText("", Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 519 */       if (this.pos < EditPlayers.this.team.players.size()) {
/* 520 */         Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 521 */         setText(Assets.gettext(player.getRoleLabel()));
/*     */       } else {
/* 523 */         setText("");
/*     */       } 
/* 525 */       setActive((this.pos < EditPlayers.this.team.players.size()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 530 */       updateRole(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 535 */       updateRole(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 540 */       updateRole(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 545 */       updateRole(-1);
/*     */     }
/*     */     
/*     */     private void updateRole(int n) {
/* 549 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 550 */       player.role = Player.Role.values()[EMath.rotate(player.role.ordinal(), Player.Role.GOALKEEPER.ordinal(), Player.Role.ATTACKER.ordinal(), n)];
/* 551 */       EditPlayers.this.refreshAllWidgets();
/* 552 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class SkillLabel
/*     */     extends Label
/*     */   {
/*     */     SkillLabel(Player.Skill skill) {
/* 560 */       setGeometry(898 + 38 * skill.ordinal(), 74, 36, 19);
/* 561 */       setText(Assets.gettext(Player.getSkillLabel(skill)), Font.Align.CENTER, Assets.font10);
/*     */     }
/*     */   }
/*     */   
/*     */   private class SkillButton
/*     */     extends Button {
/*     */     int pos;
/*     */     Player.Skill skill;
/*     */     
/*     */     SkillButton(int pos, Player.Skill skill) {
/* 571 */       this.pos = pos;
/* 572 */       this.skill = skill;
/* 573 */       setGeometry(898 + 38 * skill.ordinal(), 95 + 21 * pos, 36, 19);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 578 */       EditPlayers.this.setPlayerWidgetColor((Widget)this, this.pos);
/* 579 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 580 */       if (player != null && player.role != Player.Role.GOALKEEPER) {
/* 581 */         boolean isBest = player.bestSkills.contains(this.skill);
/* 582 */         this.textureRegion = EditPlayers.this.imageSkill[player.getSkillValue(this.skill)][isBest ? 1 : 0];
/* 583 */         setActive(true);
/*     */       } else {
/* 585 */         this.textureRegion = null;
/* 586 */         setActive(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 592 */       updateSkill();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 597 */       updateSkill();
/*     */     }
/*     */     
/*     */     private void updateSkill() {
/* 601 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 602 */       int value = EMath.rotate(player.getSkillValue(this.skill), 0, 7, 1);
/* 603 */       player.setSkillValue(this.skill, value);
/* 604 */       setDirty(true);
/* 605 */       EditPlayers.this.priceButtons[this.pos].setDirty(true);
/* 606 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 611 */       if (EditPlayers.this.team.playerAtPosition(this.pos).toggleBestSkill(this.skill)) {
/* 612 */         setDirty(true);
/* 613 */         EditPlayers.this.setModifiedFlag();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerPriceButton
/*     */     extends Button {
/*     */     int pos;
/*     */     
/*     */     PlayerPriceButton(int pos) {
/* 623 */       this.pos = pos;
/* 624 */       setGeometry(1164, 95 + 21 * pos, 90, 19);
/* 625 */       setText("", Font.Align.LEFT, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 630 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 631 */       if (player != null) {
/* 632 */         setText(EditPlayers.this.game.settings.currency + " " + player.getPrice(EditPlayers.this.game.settings.maxPlayerValue));
/* 633 */         setActive((player.role == Player.Role.GOALKEEPER));
/*     */       } else {
/* 635 */         setText("");
/* 636 */         setActive(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 642 */       updatePrice(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Hold() {
/* 647 */       updatePrice(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Down() {
/* 652 */       updatePrice(-1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire2Hold() {
/* 657 */       updatePrice(-1);
/*     */     }
/*     */     
/*     */     private void updatePrice(int n) {
/* 661 */       Player player = EditPlayers.this.team.playerAtPosition(this.pos);
/* 662 */       player.value = EMath.slide(player.value, 0, 49, n);
/* 663 */       setDirty(true);
/* 664 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamNameButton
/*     */     extends InputButton {
/*     */     TeamNameButton() {
/* 671 */       Objects.requireNonNull(EditPlayers.this.game.gui); setGeometry((1280 - 450) / 2, 30, 450, 40);
/* 672 */       setColors(Integer.valueOf(10244650), Integer.valueOf(12278309), Integer.valueOf(6895645));
/* 673 */       setText(EditPlayers.this.team.name, Font.Align.CENTER, Assets.font14);
/* 674 */       setEntryLimit(16);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 679 */       EditPlayers.this.team.name = this.text;
/* 680 */       EditPlayers.this.setModifiedFlag();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ClipBoardPlayerButton
/*     */     extends Button {
/*     */     ClipBoardPlayerButton() {
/* 687 */       setGeometry(62, 30, 236, 40);
/* 688 */       setText("", Font.Align.LEFT, Assets.font10);
/* 689 */       setActive(false);
/* 690 */       setImageScale(2.0F, 2.0F);
/* 691 */       setImagePosition(this.w - 40, -1);
/* 692 */       setTextOffsetX(6);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 697 */       Player player = EditPlayers.navigation.getClipboardPlayer();
/* 698 */       if (player == null) {
/* 699 */         setColors(Integer.valueOf(7048885), Integer.valueOf(1066106), Integer.valueOf(1066106));
/* 700 */         setText("");
/* 701 */         this.textureRegion = null;
/*     */       } else {
/* 703 */         setColors(Integer.valueOf(1534397), Integer.valueOf(1066106), Integer.valueOf(1066106));
/* 704 */         setText(player.shirtName);
/* 705 */         this.textureRegion = player.createFace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class EditTeamButton
/*     */     extends Button {
/*     */     EditTeamButton() {
/* 713 */       setGeometry(80, 660, 206, 36);
/* 714 */       setColors(Integer.valueOf(33375), Integer.valueOf(49806), Integer.valueOf(16431));
/* 715 */       setText(Assets.gettext("TEAM"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 720 */       EditPlayers.this.game.setScreen((Screen)new EditTeam(EditPlayers.this.game, EditPlayers.this.team, Boolean.valueOf(EditPlayers.this.modified)));
/*     */     }
/*     */   }
/*     */   
/*     */   private class NewPlayerButton
/*     */     extends Button {
/*     */     NewPlayerButton() {
/* 727 */       setGeometry(310, 660, 226, 36);
/* 728 */       setText(Assets.gettext("NEW PLAYER"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 733 */       if (EditPlayers.this.team.players.size() < 26) {
/* 734 */         setColors(Integer.valueOf(1534397), Integer.valueOf(3838184), Integer.valueOf(1066106));
/* 735 */         setActive(true);
/*     */       } else {
/* 737 */         setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 738 */         setActive(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 744 */       Player player = EditPlayers.this.team.newPlayer();
/*     */       
/* 746 */       if (player != null) {
/* 747 */         if (EditPlayers.navigation.getClipboardPlayer() != null) {
/* 748 */           player.copyFrom(EditPlayers.navigation.getClipboardPlayer());
/* 749 */           EditPlayers.navigation.setClipboardPlayer(null);
/* 750 */           EditPlayers.this.selectedPos = -1;
/*     */         } 
/* 752 */         EditPlayers.this.refreshAllWidgets();
/* 753 */         EditPlayers.this.setModifiedFlag();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class DeletePlayerButton
/*     */     extends Button {
/*     */     DeletePlayerButton() {
/* 761 */       setGeometry(540, 660, 226, 36);
/* 762 */       setText(Assets.gettext("DELETE PLAYER"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 767 */       if (EditPlayers.this.selectedPos != -1 && EditPlayers.this.team.players.size() > 16) {
/* 768 */         setColors(Integer.valueOf(3282877), Integer.valueOf(5650919), Integer.valueOf(2232448));
/* 769 */         setActive(true);
/*     */       } else {
/* 771 */         setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 772 */         setActive(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 778 */       if (EditPlayers.this.selectedPos != -1 && EditPlayers.this.team.players.size() > 16) {
/*     */ 
/*     */         
/* 781 */         int ply1 = EditPlayers.this.team.playerIndexAtPosition(EditPlayers.this.selectedPos);
/* 782 */         int ply2 = EditPlayers.this.team.playerIndexAtPosition(EditPlayers.this.team.players.size() - 1);
/*     */         
/* 784 */         Collections.swap(EditPlayers.this.team.players, ply1, ply2);
/*     */         
/* 786 */         EditPlayers.this.selectedPos = -1;
/*     */         
/* 788 */         Player player = EditPlayers.this.team.playerAtPosition(EditPlayers.this.team.players.size() - 1);
/* 789 */         EditPlayers.this.team.deletePlayer(player);
/*     */         
/* 791 */         EditPlayers.this.refreshAllWidgets();
/* 792 */         EditPlayers.this.setModifiedFlag();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ResetButton
/*     */     extends Button {
/*     */     ResetButton() {
/* 800 */       setGeometry(790, 660, 196, 36);
/* 801 */       setText(Assets.gettext("EDIT.RESET"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 806 */       if (EditPlayers.this.modified) {
/* 807 */         setColor(12435247);
/* 808 */         setActive(true);
/*     */       } else {
/* 810 */         setColor(6710886);
/* 811 */         setActive(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 817 */       FileHandle file = Assets.teamsRootFolder.child(EditPlayers.this.team.path);
/* 818 */       if (file.exists()) {
/* 819 */         Team team = (Team)Assets.json.fromJson(Team.class, file.readString("UTF-8"));
/* 820 */         team.path = Assets.getRelativeTeamPath(file);
/* 821 */         EditPlayers.this.game.setScreen((Screen)new EditPlayers(EditPlayers.this.game, team, Boolean.valueOf(false)));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class SaveExitButton
/*     */     extends Button {
/*     */     SaveExitButton() {
/* 829 */       setGeometry(990, 660, 196, 36);
/* 830 */       setText(Assets.gettext(""), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 835 */       if (EditPlayers.this.modified) {
/* 836 */         setColor(14417920);
/* 837 */         setText(Assets.gettext("SAVE"));
/*     */       } else {
/* 839 */         setColor(13124096);
/* 840 */         setText(Assets.gettext("EXIT"));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 846 */       if (EditPlayers.this.modified) {
/* 847 */         EditPlayers.this.team.persist();
/*     */       }
/* 849 */       EditPlayers.this.game.setScreen((Screen)new SelectTeam(EditPlayers.this.game));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPlayerWidgetColor(Widget w, int pos) {
/* 855 */     if (pos == 0) {
/* 856 */       w.setColors(Integer.valueOf(4898904), Integer.valueOf(8508299), Integer.valueOf(3181627));
/*     */ 
/*     */     
/*     */     }
/* 860 */     else if (pos < 11) {
/* 861 */       w.setColors(Integer.valueOf(3181627), Integer.valueOf(4898904), Integer.valueOf(2054438));
/*     */ 
/*     */     
/*     */     }
/* 865 */     else if (pos < 11 + this.game.settings.benchSize) {
/* 866 */       w.setColors(Integer.valueOf(2912817), Integer.valueOf(4233290), Integer.valueOf(1655580));
/*     */ 
/*     */     
/*     */     }
/* 870 */     else if (pos < this.team.players.size()) {
/* 871 */       w.setColors(Integer.valueOf(4210752), Integer.valueOf(6316128), Integer.valueOf(2105376));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 876 */       w.setColors(Integer.valueOf(2105376), Integer.valueOf(4210752), Integer.valueOf(1052688));
/*     */     } 
/*     */ 
/*     */     
/* 880 */     if (this.selectedPos == pos)
/* 881 */       w.setColors(Integer.valueOf(10040115), Integer.valueOf(12730946), Integer.valueOf(5905950)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\EditPlayers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */