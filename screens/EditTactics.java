/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.GLSpriteBatch;
/*     */ import com.ygames.ysoccer.framework.RgbPair;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Picture;
/*     */ import com.ygames.ysoccer.gui.Piece;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Data;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.PlayerSprite;
/*     */ import com.ygames.ysoccer.match.Tactics;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.Collections;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class EditTactics
/*     */   extends GLScreen
/*     */ {
/*     */   private boolean copyMode;
/*     */   private Team team;
/*     */   private int selectedForSwap;
/*     */   private int selectedForPair;
/*     */   private int[] ballZone;
/*     */   private int[] ballCopyZone;
/*     */   private Font font10yellow;
/*     */   private TextureRegion ballTextureRegion;
/*     */   private TextureRegion ballCopyTextureRegion;
/*     */   private Piece ball;
/*     */   private Piece ballCopy;
/*  45 */   private Piece[] players = new Piece[11];
/*  46 */   private PlayerSprite[] playerSprite = new PlayerSprite[26];
/*     */   
/*     */   private Widget copyButton;
/*     */   private Widget undoButton;
/*     */   private Widget saveButton;
/*     */   private Widget exitButton;
/*     */   
/*     */   EditTactics(GLGame game) {
/*  54 */     super(game);
/*     */     
/*  56 */     this.copyMode = false;
/*  57 */     this.team = game.tacticsTeam;
/*  58 */     this.selectedForSwap = -1;
/*  59 */     this.selectedForPair = -1;
/*  60 */     this.ballZone = new int[] { 0, 0 };
/*  61 */     this.ballCopyZone = new int[] { 0, 0 };
/*     */     
/*  63 */     this.background = new Texture("images/backgrounds/menu_set_team.jpg");
/*  64 */     this.ballTextureRegion = new TextureRegion(new Texture("images/ball.png"), 0, 0, 8, 8);
/*  65 */     this.ballTextureRegion.flip(false, true);
/*  66 */     this.ballCopyTextureRegion = new TextureRegion(new Texture("images/ballsnow.png"), 0, 0, 8, 8);
/*  67 */     this.ballCopyTextureRegion.flip(false, true);
/*     */     
/*  69 */     for (int ply = 0; ply < this.team.players.size(); ply++) {
/*  70 */       Player player = this.team.players.get(ply);
/*  71 */       player.data[0] = new Data();
/*  72 */       player.isVisible = true;
/*  73 */       if (player.role == Player.Role.GOALKEEPER) {
/*  74 */         Assets.loadKeeper(player);
/*     */       } else {
/*  76 */         Assets.loadPlayer(player, this.team.kits.get(this.team.kitIndex));
/*     */       } 
/*  78 */       Assets.loadHair(player);
/*  79 */       player.fmx = 2.0F;
/*  80 */       player.fmy = 1.0F;
/*  81 */       this.playerSprite[ply] = new PlayerSprite(game.glGraphics, player);
/*     */     } 
/*     */     
/*  84 */     this.font10yellow = new Font(10, 13, 17, 12, 16, new RgbPair(16579836, 16579584));
/*  85 */     this.font10yellow.load();
/*     */ 
/*     */ 
/*     */     
/*  89 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("EDIT TACTICS") + " (" + Tactics.codes[game.tacticsToEdit] + ")", 12227078);
/*  90 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  93 */     for (int pos = 0; pos < 26; pos++) {
/*     */       
/*  95 */       PlayerFaceButton playerFaceButton = new PlayerFaceButton(pos);
/*  96 */       this.widgets.add(playerFaceButton);
/*     */       
/*  98 */       PlayerNumberButton playerNumberButton = new PlayerNumberButton(pos);
/*  99 */       this.widgets.add(playerNumberButton);
/*     */       
/* 101 */       PlayerNameButton playerNameButton = new PlayerNameButton(pos);
/* 102 */       this.widgets.add(playerNameButton);
/*     */       
/* 104 */       int x = 404;
/* 105 */       if (this.team.type != Team.Type.NATIONAL) {
/* 106 */         if (game.settings.useFlags) {
/* 107 */           PlayerNationalityFlagButton playerNationalityFlagButton = new PlayerNationalityFlagButton(pos);
/* 108 */           this.widgets.add(playerNationalityFlagButton);
/* 109 */           x += 30;
/*     */         } else {
/* 111 */           PlayerNationalityCodeButton playerNationalityCodeButton = new PlayerNationalityCodeButton(pos);
/* 112 */           this.widgets.add(playerNationalityCodeButton);
/* 113 */           x += 58;
/*     */         } 
/*     */       }
/*     */       
/* 117 */       PlayerRoleButton playerRoleButton = new PlayerRoleButton(x, pos);
/* 118 */       this.widgets.add(playerRoleButton);
/* 119 */       x += 34;
/*     */       
/* 121 */       for (int skillIndex = 0; skillIndex < 3; skillIndex++) {
/* 122 */         PlayerSkillButton playerSkillButton = new PlayerSkillButton(pos, skillIndex, x);
/* 123 */         this.widgets.add(playerSkillButton);
/* 124 */         x += 13;
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     TacticsBoard tacticsBoard = new TacticsBoard();
/* 129 */     this.widgets.add(tacticsBoard);
/*     */     
/* 131 */     this.ball = new BallPiece();
/* 132 */     this.widgets.add(this.ball);
/*     */     
/* 134 */     this.ballCopy = new BallCopyPiece();
/* 135 */     this.widgets.add(this.ballCopy);
/*     */     
/* 137 */     for (int i = 0; i < 11; i++) {
/* 138 */       this.players[i] = new PlayerPiece(i);
/* 139 */       this.widgets.add(this.players[i]);
/*     */     } 
/*     */     
/* 142 */     this.copyButton = (Widget)new CopyButton();
/* 143 */     this.widgets.add(this.copyButton);
/*     */     
/* 145 */     FlipButton flipButton = new FlipButton();
/* 146 */     this.widgets.add(flipButton);
/*     */     
/* 148 */     this.undoButton = (Widget)new UndoButton();
/* 149 */     this.widgets.add(this.undoButton);
/*     */     
/* 151 */     ImportButton importButton = new ImportButton();
/* 152 */     this.widgets.add(importButton);
/*     */     
/* 154 */     this.saveButton = (Widget)new SaveExitButton();
/* 155 */     this.widgets.add(this.saveButton);
/*     */     
/* 157 */     setSelectedWidget((Widget)importButton);
/*     */     
/* 159 */     this.exitButton = (Widget)new AbortButton();
/* 160 */     this.widgets.add(this.exitButton);
/*     */   }
/*     */   
/*     */   private class TacticsBoard
/*     */     extends Picture {
/*     */     TacticsBoard() {
/* 166 */       Texture texture = new Texture("images/tactics_board.png");
/* 167 */       this.textureRegion = new TextureRegion(texture);
/* 168 */       this.textureRegion.flip(false, true);
/* 169 */       setGeometry(580, 110, 396, 576);
/* 170 */       this.hAlign = Picture.HAlign.LEFT;
/* 171 */       this.vAlign = Picture.VAlign.TOP;
/*     */     }
/*     */   }
/*     */   
/*     */   private class BallPiece
/*     */     extends Piece {
/*     */     BallPiece() {
/* 178 */       setSize(24, 14);
/* 179 */       this.textureRegion = EditTactics.this.ballTextureRegion;
/* 180 */       setImagePosition(6, -2);
/* 181 */       setRanges(0, 4, 0, 6);
/* 182 */       setGridGeometry(604, 155, 324, 472);
/* 183 */       setActive(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 188 */       setSquare(2 - EditTactics.this.ballZone[0], 3 - EditTactics.this.ballZone[1]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 193 */       EditTactics.this.ballZone[0] = 2 - this.square[0];
/* 194 */       EditTactics.this.ballZone[1] = 3 - this.square[1];
/*     */       
/* 196 */       EditTactics.this.updatePlayerPieces();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 201 */       toggleEntryMode();
/*     */     }
/*     */   }
/*     */   
/*     */   private class BallCopyPiece
/*     */     extends Piece {
/*     */     BallCopyPiece() {
/* 208 */       setSize(24, 14);
/* 209 */       this.textureRegion = EditTactics.this.ballCopyTextureRegion;
/* 210 */       setImagePosition(6, -2);
/* 211 */       setRanges(0, 4, 0, 6);
/* 212 */       setGridGeometry(604, 155, 324, 472);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 217 */       setVisible(EditTactics.this.copyMode);
/* 218 */       setSquare(2 - EditTactics.this.ballCopyZone[0], 3 - EditTactics.this.ballCopyZone[1]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 223 */       EditTactics.this.ballCopyZone[0] = 2 - this.square[0];
/* 224 */       EditTactics.this.ballCopyZone[1] = 3 - this.square[1];
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 229 */       ballCopy();
/*     */     }
/*     */ 
/*     */     
/*     */     private void ballCopy() {
/* 234 */       EditTactics.this.pushUndoStack();
/*     */       
/* 236 */       int toZone = 17 + EditTactics.this.ballCopyZone[0] + 5 * EditTactics.this.ballCopyZone[1];
/* 237 */       int fromZone = 17 + EditTactics.this.ballZone[0] + 5 * EditTactics.this.ballZone[1];
/* 238 */       for (int p1 = 1; p1 < 11; p1++) {
/*     */ 
/*     */         
/* 241 */         if (EditTactics.this.game.tacticsFlip && Math.signum((EditTactics.this.ballZone[0] * EditTactics.this.ballCopyZone[0])) == -1.0F) {
/*     */           
/* 243 */           if (EditTactics.this.game.editedTactics.isPaired(p1)) {
/* 244 */             int p2 = EditTactics.this.game.editedTactics.getPaired(p1);
/* 245 */             EditTactics.this.game.editedTactics.target[p1][toZone][0] = -EditTactics.this.game.editedTactics.target[p2][fromZone][0];
/* 246 */             EditTactics.this.game.editedTactics.target[p1][toZone][1] = EditTactics.this.game.editedTactics.target[p2][fromZone][1];
/*     */           } else {
/* 248 */             EditTactics.this.game.editedTactics.target[p1][toZone][0] = -EditTactics.this.game.editedTactics.target[p1][fromZone][0];
/* 249 */             EditTactics.this.game.editedTactics.target[p1][toZone][1] = EditTactics.this.game.editedTactics.target[p1][fromZone][1];
/*     */           } 
/*     */         } else {
/* 252 */           EditTactics.this.game.editedTactics.target[p1][toZone][0] = EditTactics.this.game.editedTactics.target[p1][fromZone][0];
/* 253 */           EditTactics.this.game.editedTactics.target[p1][toZone][1] = EditTactics.this.game.editedTactics.target[p1][fromZone][1];
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 258 */       if (EditTactics.this.game.tacticsFlip && Math.signum((EditTactics.this.ballZone[0] * EditTactics.this.ballCopyZone[0])) != 0.0F) {
/* 259 */         int flippedToZone = 17 - EditTactics.this.ballCopyZone[0] + 5 * EditTactics.this.ballCopyZone[1];
/* 260 */         for (int i = 1; i < 11; i++) {
/*     */ 
/*     */           
/* 263 */           if (EditTactics.this.game.editedTactics.isPaired(i)) {
/* 264 */             int p2 = EditTactics.this.game.editedTactics.getPaired(i);
/* 265 */             EditTactics.this.game.editedTactics.target[i][flippedToZone][0] = -EditTactics.this.game.editedTactics.target[p2][toZone][0];
/* 266 */             EditTactics.this.game.editedTactics.target[i][flippedToZone][1] = EditTactics.this.game.editedTactics.target[p2][toZone][1];
/*     */           } else {
/* 268 */             EditTactics.this.game.editedTactics.target[i][flippedToZone][0] = -EditTactics.this.game.editedTactics.target[i][toZone][0];
/* 269 */             EditTactics.this.game.editedTactics.target[i][flippedToZone][1] = EditTactics.this.game.editedTactics.target[i][toZone][1];
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 275 */       EditTactics.this.copyMode = false;
/* 276 */       EditTactics.this.setSelectedWidget((Widget)EditTactics.this.ball);
/* 277 */       setEntryMode(false);
/* 278 */       EditTactics.this.copyButton.setDirty(true);
/* 279 */       setDirty(true);
/*     */ 
/*     */       
/* 282 */       EditTactics.this.ballZone[0] = EditTactics.this.ballCopyZone[0];
/* 283 */       EditTactics.this.ballZone[1] = EditTactics.this.ballCopyZone[1];
/* 284 */       EditTactics.this.ball.setDirty(true);
/*     */       
/* 286 */       EditTactics.this.updatePlayerPieces();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerPiece
/*     */     extends Piece {
/*     */     int ply;
/*     */     Player player;
/*     */     
/*     */     PlayerPiece(int ply) {
/* 296 */       this.ply = ply;
/* 297 */       setSize(24, 14);
/* 298 */       setRanges(0, 14, 0, 15);
/* 299 */       if (ply == 0) {
/* 300 */         setGridGeometry(580, 110, 372, 562);
/* 301 */         setActive(false);
/*     */       } else {
/* 303 */         setGridGeometry(584, 130, 364, 522);
/* 304 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 310 */       this.player = EditTactics.this.team.players.get(this.ply);
/*     */ 
/*     */       
/* 313 */       if (this.ply == 0) {
/* 314 */         setSquare(7, 15);
/*     */       } else {
/* 316 */         int[] target = EditTactics.this.game.editedTactics.target[this.ply][17 + EditTactics.this.ballZone[0] + 5 * EditTactics.this.ballZone[1]];
/* 317 */         setSquare(14 - target[0] / 68 + 7, 15 - (target[1] / 40 + 15) / 2);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onChanged() {
/* 323 */       EditTactics.this.pushUndoStack();
/*     */       
/* 325 */       int[] target = new int[2];
/* 326 */       target[0] = (7 - this.square[0]) * 68;
/* 327 */       target[1] = (15 - 2 * this.square[1]) * 40;
/* 328 */       int currentZone = 17 + EditTactics.this.ballZone[0] + 5 * EditTactics.this.ballZone[1];
/* 329 */       EditTactics.this.game.editedTactics.target[this.ply][currentZone] = target;
/*     */ 
/*     */       
/* 332 */       if (EditTactics.this.game.tacticsFlip && EditTactics.this.ballZone[0] != 0) {
/* 333 */         int flippedZone = 17 - EditTactics.this.ballZone[0] + 5 * EditTactics.this.ballZone[1];
/* 334 */         for (int p1 = 1; p1 < 11; p1++) {
/*     */ 
/*     */           
/* 337 */           if (EditTactics.this.game.editedTactics.isPaired(p1)) {
/* 338 */             int p2 = EditTactics.this.game.editedTactics.getPaired(p1);
/* 339 */             EditTactics.this.game.editedTactics.target[p1][flippedZone][0] = -EditTactics.this.game.editedTactics.target[p2][currentZone][0];
/* 340 */             EditTactics.this.game.editedTactics.target[p1][flippedZone][1] = EditTactics.this.game.editedTactics.target[p2][currentZone][1];
/*     */           } else {
/* 342 */             EditTactics.this.game.editedTactics.target[p1][flippedZone][0] = -EditTactics.this.game.editedTactics.target[p1][currentZone][0];
/* 343 */             EditTactics.this.game.editedTactics.target[p1][flippedZone][1] = EditTactics.this.game.editedTactics.target[p1][currentZone][1];
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 351 */       toggleEntryMode();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void drawImage(GLSpriteBatch batch) {
/* 356 */       Player player = EditTactics.this.team.players.get(this.ply);
/* 357 */       if (player != null) {
/* 358 */         player.x = this.x + this.w / 2.0F - 1.0F;
/* 359 */         player.y = this.y + this.h / 2.0F;
/*     */         
/* 361 */         batch.begin();
/*     */         
/* 363 */         player.save(0);
/*     */         
/* 365 */         Data d = player.data[0];
/* 366 */         Integer[] origin = Assets.playerOrigins[d.fmy][d.fmx];
/* 367 */         float mX = 0.65F;
/* 368 */         float mY = 0.46F;
/* 369 */         batch.draw(Assets.playerShadow[d.fmx][d.fmy][0], (d.x - origin[0].intValue()) + mX * d.z, (d.y - origin[1].intValue() + 5) + mY * d.z);
/*     */         
/* 371 */         EditTactics.this.playerSprite[this.ply].draw(0);
/*     */ 
/*     */         
/* 374 */         int f0 = player.number % 10;
/* 375 */         int f1 = (player.number - f0) / 10 % 10;
/*     */         
/* 377 */         int dx = this.x + this.w / 2;
/* 378 */         int dy = this.y - 32;
/*     */         
/* 380 */         int w0 = 6 - ((f0 == 1) ? 2 : 0);
/* 381 */         int w1 = 6 - ((f1 == 1) ? 2 : 0);
/*     */         
/* 383 */         if (f1 > 0) {
/* 384 */           dx -= (w0 + 2 + w1) / 2;
/* 385 */           batch.draw(Assets.playerNumbers[f1][0], dx, dy);
/* 386 */           dx = dx + w1 + 2;
/* 387 */           batch.draw(Assets.playerNumbers[f0][0], dx, dy);
/*     */         } else {
/* 389 */           batch.draw(Assets.playerNumbers[f0][0], dx - w0 / 2.0F, dy);
/*     */         } 
/* 391 */         batch.end();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerFaceButton
/*     */     extends Button {
/*     */     int position;
/*     */     
/*     */     PlayerFaceButton(int position) {
/* 401 */       this.position = position;
/* 402 */       setGeometry(70, 114 + 22 * position, 24, 20);
/* 403 */       setImagePosition(2, -2);
/* 404 */       setActive(false);
/* 405 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 410 */       if (EditTactics.this.game.tacticsFlip) {
/* 411 */         setActive((this.position > 0 && this.position < 11));
/* 412 */         EditTactics.this.setPlayerFlipColor((Widget)this, this.position);
/*     */       } else {
/* 414 */         setActive(false);
/* 415 */         EditTactics.this.setPlayerWidgetColor((Widget)this, this.position);
/*     */       } 
/* 417 */       Player player = EditTactics.this.team.playerAtPosition(this.position);
/* 418 */       if (player == null) {
/* 419 */         this.textureRegion = null;
/*     */       } else {
/* 421 */         this.textureRegion = player.createFace();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 428 */       if (EditTactics.this.selectedForSwap != -1) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 433 */       if (EditTactics.this.selectedForPair == -1) {
/* 434 */         EditTactics.this.selectedForPair = this.position;
/*     */ 
/*     */       
/*     */       }
/* 438 */       else if (EditTactics.this.selectedForPair == this.position) {
/* 439 */         EditTactics.this.selectedForPair = -1;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 444 */         EditTactics.this.pushUndoStack();
/*     */         
/* 446 */         int ply1 = EditTactics.this.team.playerIndexAtPosition(EditTactics.this.selectedForPair, EditTactics.this.game.editedTactics);
/* 447 */         int ply2 = EditTactics.this.team.playerIndexAtPosition(this.position, EditTactics.this.game.editedTactics);
/*     */         
/* 449 */         EditTactics.this.game.editedTactics.addDeletePair(ply1, ply2);
/* 450 */         EditTactics.this.selectedForPair = -1;
/*     */       } 
/*     */       
/* 453 */       EditTactics.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNumberButton
/*     */     extends Button {
/*     */     int position;
/*     */     
/*     */     PlayerNumberButton(int position) {
/* 462 */       this.position = position;
/* 463 */       setGeometry(96, 114 + 22 * position, 30, 20);
/* 464 */       setText("", Font.Align.CENTER, Assets.font10);
/* 465 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 470 */       Player player = EditTactics.this.team.playerAtPosition(this.position);
/* 471 */       if (player == null) {
/* 472 */         setText("");
/*     */       } else {
/* 474 */         setText(player.number);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNameButton
/*     */     extends Button {
/*     */     int position;
/*     */     
/*     */     PlayerNameButton(int position) {
/* 484 */       this.position = position;
/* 485 */       setGeometry(128, 114 + 22 * position, 276, 20);
/* 486 */       setText("", Font.Align.LEFT, Assets.font10);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 491 */       Player player = EditTactics.this.team.playerAtPosition(this.position);
/* 492 */       if (player == null) {
/* 493 */         setText("");
/* 494 */         setActive(false);
/*     */       } else {
/* 496 */         setText(player.name);
/* 497 */         setActive(true);
/*     */       } 
/* 499 */       EditTactics.this.setPlayerWidgetColor((Widget)this, this.position);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/* 506 */       if (EditTactics.this.selectedForPair != -1) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 511 */       if (EditTactics.this.selectedForSwap == -1) {
/* 512 */         EditTactics.this.selectedForSwap = this.position;
/*     */ 
/*     */       
/*     */       }
/* 516 */       else if (EditTactics.this.selectedForSwap == this.position) {
/* 517 */         EditTactics.this.selectedForSwap = -1;
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 522 */         int ply1 = EditTactics.this.team.playerIndexAtPosition(EditTactics.this.selectedForSwap, EditTactics.this.game.editedTactics);
/* 523 */         int ply2 = EditTactics.this.team.playerIndexAtPosition(this.position, EditTactics.this.game.editedTactics);
/*     */         
/* 525 */         Collections.swap(EditTactics.this.team.players, ply1, ply2);
/*     */         
/* 527 */         EditTactics.this.selectedForSwap = -1;
/*     */       } 
/* 529 */       EditTactics.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNationalityFlagButton
/*     */     extends Button {
/*     */     int position;
/*     */     
/*     */     PlayerNationalityFlagButton(int position) {
/* 538 */       this.position = position;
/* 539 */       setGeometry(406, 114 + 22 * position, 30, 20);
/* 540 */       setImagePosition(2, 3);
/* 541 */       setActive(false);
/* 542 */       setAddShadow(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 547 */       Player player = EditTactics.this.team.playerAtPosition(this.position);
/* 548 */       if (player == null) {
/* 549 */         this.textureRegion = null;
/*     */       } else {
/* 551 */         this.textureRegion = Assets.getNationalityFlag(player.nationality);
/*     */       } 
/* 553 */       setVisible((EditTactics.this.team.type != Team.Type.NATIONAL));
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNationalityCodeButton
/*     */     extends Button {
/*     */     int position;
/*     */     
/*     */     PlayerNationalityCodeButton(int position) {
/* 562 */       this.position = position;
/* 563 */       setGeometry(406, 114 + 22 * position, 58, 20);
/* 564 */       setText("", Font.Align.CENTER, Assets.font10);
/* 565 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 570 */       Player player = EditTactics.this.team.playerAtPosition(this.position);
/* 571 */       if (player == null) {
/* 572 */         setText("");
/*     */       } else {
/* 574 */         setText("(" + player.nationality + ")");
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerRoleButton
/*     */     extends Button {
/*     */     int position;
/*     */     
/*     */     PlayerRoleButton(int x, int position) {
/* 584 */       this.position = position;
/* 585 */       setGeometry(x, 114 + 22 * position, 34, 20);
/* 586 */       setText("", Font.Align.CENTER, Assets.font10);
/* 587 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 592 */       Player player = EditTactics.this.team.playerAtPosition(this.position);
/* 593 */       if (player == null) {
/* 594 */         setText("");
/*     */       } else {
/* 596 */         setText(Assets.strings.get(player.getRoleLabel()));
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
/* 607 */       this.pos = pos;
/* 608 */       this.skillIndex = skillIndex;
/* 609 */       setGeometry(x, 114 + 22 * pos, 13, 20);
/* 610 */       setText("", Font.Align.CENTER, EditTactics.this.font10yellow);
/* 611 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 616 */       Player player = EditTactics.this.team.playerAtPosition(this.pos);
/* 617 */       if (player == null) {
/* 618 */         setText("");
/*     */       } else {
/* 620 */         Player.Skill[] skills = player.getOrderedSkills();
/* 621 */         if (skills == null) {
/* 622 */           setText("");
/*     */         } else {
/* 624 */           setText(Assets.strings.get(Player.getSkillLabel(skills[this.skillIndex])));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class CopyButton
/*     */     extends Button {
/*     */     CopyButton() {
/* 633 */       setGeometry(1035, 175, 170, 40);
/* 634 */       setText(Assets.strings.get("TACTICS.COPY"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 639 */       if (EditTactics.this.copyMode) {
/* 640 */         setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 641 */         setActive(false);
/*     */       } else {
/* 643 */         setColors(Integer.valueOf(1534397), Integer.valueOf(3838184), Integer.valueOf(1066106));
/* 644 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/* 650 */       EditTactics.this.copyMode = true;
/* 651 */       setActive(false);
/* 652 */       EditTactics.this.ballCopy.setVisible(true);
/* 653 */       EditTactics.this.ballCopy.setEntryMode(true);
/* 654 */       EditTactics.this.setSelectedWidget((Widget)EditTactics.this.ballCopy);
/* 655 */       EditTactics.this.ballCopyZone[0] = EditTactics.this.ballZone[0];
/* 656 */       EditTactics.this.ballCopyZone[1] = EditTactics.this.ballZone[1];
/* 657 */       setDirty(true);
/* 658 */       EditTactics.this.ballCopy.setDirty(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FlipButton
/*     */     extends Button {
/*     */     FlipButton() {
/* 665 */       setGeometry(1035, 240, 170, 40);
/* 666 */       setColors(Integer.valueOf(5467024), Integer.valueOf(7377090), Integer.valueOf(2502978));
/* 667 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 672 */       if (EditTactics.this.game.tacticsFlip) {
/* 673 */         setText(Assets.strings.get("TACTICS.FLIP ON"));
/*     */       } else {
/* 675 */         setText(Assets.strings.get("TACTICS.FLIP OFF"));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/* 681 */       EditTactics.this.game.tacticsFlip = !EditTactics.this.game.tacticsFlip;
/* 682 */       EditTactics.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class UndoButton
/*     */     extends Button {
/*     */     UndoButton() {
/* 689 */       setGeometry(1035, 305, 170, 40);
/* 690 */       setText(Assets.strings.get("TACTICS.UNDO"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 695 */       if (EditTactics.this.game.tacticsUndo.isEmpty()) {
/* 696 */         setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/* 697 */         setActive(false);
/*     */       } else {
/* 699 */         setColors(Integer.valueOf(12435247), Integer.valueOf(15790652), Integer.valueOf(9145379));
/* 700 */         setActive(true);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/* 706 */       if (EditTactics.this.game.tacticsUndo.isEmpty()) {
/*     */         return;
/*     */       }
/* 709 */       EditTactics.this.game.editedTactics = EditTactics.this.game.tacticsUndo.pop();
/* 710 */       EditTactics.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class ImportButton
/*     */     extends Button {
/*     */     ImportButton() {
/* 717 */       setGeometry(1035, 460, 170, 40);
/* 718 */       setColors(Integer.valueOf(11211917), Integer.valueOf(14555831), Integer.valueOf(7868003));
/* 719 */       setText(Assets.strings.get("TACTICS.IMPORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/* 724 */       EditTactics.this.game.setScreen((Screen)new ImportTactics(EditTactics.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class SaveExitButton
/*     */     extends Button {
/*     */     SaveExitButton() {
/* 731 */       setGeometry(1000, 525, 240, 40);
/* 732 */       setColors(Integer.valueOf(1089536), Integer.valueOf(1433600), Integer.valueOf(614400));
/* 733 */       setText(Assets.strings.get("SAVE") + "/" + Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Up() {
/* 738 */       if (EditTactics.this.game.tacticsUndo.isEmpty()) {
/*     */         
/* 740 */         if (EditTactics.this.game.getState() == GLGame.State.NONE) {
/* 741 */           EditTactics.this.game.setScreen((Screen)new Main(EditTactics.this.game));
/*     */         } else {
/* 743 */           EditTactics.this.game.setScreen((Screen)new SetTeam(EditTactics.this.game));
/*     */         } 
/*     */       } else {
/*     */         
/* 747 */         EditTactics.this.game.setScreen((Screen)new SaveTacticsWarning(EditTactics.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/* 755 */       setGeometry(1035, 590, 170, 40);
/* 756 */       setColor(13124096);
/* 757 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onFire1Down() {
/* 762 */       if (EditTactics.this.game.tacticsUndo.isEmpty()) {
/* 763 */         if (EditTactics.this.game.getState() == GLGame.State.NONE) {
/* 764 */           EditTactics.this.game.setScreen((Screen)new Main(EditTactics.this.game));
/*     */         } else {
/* 766 */           EditTactics.this.game.setScreen((Screen)new SetTeam(EditTactics.this.game));
/*     */         } 
/*     */       } else {
/* 769 */         EditTactics.this.game.setScreen((Screen)new TacticsAbortWarning(EditTactics.this.game));
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void pushUndoStack() {
/* 775 */     Tactics tactics = new Tactics();
/* 776 */     tactics.copyFrom(this.game.editedTactics);
/* 777 */     this.game.tacticsUndo.push(tactics);
/* 778 */     this.undoButton.setDirty(true);
/* 779 */     this.saveButton.setDirty(true);
/* 780 */     this.exitButton.setDirty(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPlayerWidgetColor(Widget w, int pos) {
/* 785 */     if (this.selectedForSwap == pos) {
/* 786 */       w.setColor(10040115);
/*     */     
/*     */     }
/* 789 */     else if (this.selectedForPair == pos) {
/* 790 */       w.setColor(3381657);
/*     */     
/*     */     }
/* 793 */     else if (pos == 0) {
/* 794 */       w.setColor(38110);
/*     */     
/*     */     }
/* 797 */     else if (pos < 11) {
/* 798 */       w.setColor(24030);
/*     */     
/*     */     }
/* 801 */     else if (pos < this.team.players.size()) {
/*     */       int benchSize;
/* 803 */       if (this.team.match != null && this.team.match.competition != null) {
/* 804 */         benchSize = this.team.match.competition.benchSize;
/*     */       } else {
/* 806 */         benchSize = this.game.settings.benchSize;
/*     */       } 
/*     */       
/* 809 */       if (pos < 11 + benchSize) {
/* 810 */         w.setColor(18086);
/*     */       }
/*     */       else {
/*     */         
/* 814 */         w.setColor(3158064);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 819 */       w.setColor(1052688);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setPlayerFlipColor(Widget w, int pos) {
/* 824 */     if (pos > 0 && pos < 11) {
/* 825 */       int baseTactics = this.game.editedTactics.basedOn;
/* 826 */       int ply = Tactics.order[baseTactics][pos];
/* 827 */       switch (this.game.editedTactics.pairs[ply]) {
/*     */         case 0:
/* 829 */           w.setColor(6275658);
/*     */           return;
/*     */         
/*     */         case 1:
/* 833 */           w.setColor(13385292);
/*     */           return;
/*     */         
/*     */         case 2:
/* 837 */           w.setColor(10328728);
/*     */           return;
/*     */         
/*     */         case 3:
/* 841 */           w.setColor(12485701);
/*     */           return;
/*     */         
/*     */         case 4:
/* 845 */           w.setColor(12406200);
/*     */           return;
/*     */         
/*     */         case 255:
/* 849 */           w.setColors(null);
/*     */           return;
/*     */       } 
/*     */       
/* 853 */       throw new GdxRuntimeException("invalid pair value");
/*     */     } 
/*     */     
/* 856 */     w.setColors(null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePlayerPieces() {
/* 861 */     for (Piece player : this.players)
/* 862 */       player.setDirty(true); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\EditTactics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */