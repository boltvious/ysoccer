/*     */ package com.ygames.ysoccer.gui;
/*     */ 
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLShapeRenderer;
/*     */ import com.ygames.ysoccer.framework.GLSpriteBatch;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ 
/*     */ public class TacticsBoard
/*     */   extends Widget
/*     */ {
/*     */   private boolean viewOpponent;
/*     */   private boolean compareTactics;
/*     */   private Team teamA;
/*     */   private Team teamB;
/*  20 */   private static int[][][] positions = new int[][][] { { { 0, 0 }, { -2, 2 }, { -1, 1 }, { 1, 1 }, { 2, 2 }, { -2, 6 }, { 0, 4 }, { 0, 5 }, { 2, 6 }, { -1, 8 }, { 1, 8 } }, { { 0, 0 }, { -2, 3 }, { 0, 1 }, { 1, 2 }, { 2, 3 }, { -1, 5 }, { -1, 2 }, { 0, 4 }, { 1, 5 }, { 0, 6 }, { 0, 8 } }, { { 0, 0 }, { -2, 2 }, { -1, 1 }, { 1, 1 }, { 2, 2 }, { -2, 6 }, { 0, 4 }, { 1, 5 }, { 2, 6 }, { -1, 5 }, { 0, 8 } }, { { 0, 0 }, { -2, 3 }, { 0, 1 }, { 1, 2 }, { 2, 3 }, { -2, 5 }, { -1, 2 }, { 0, 6 }, { 2, 5 }, { -1, 8 }, { 1, 8 } }, { { 0, 0 }, { -1, 2 }, { 0, 1 }, { 1, 4 }, { 1, 2 }, { -2, 5 }, { -1, 4 }, { 0, 6 }, { 2, 5 }, { -1, 8 }, { 1, 8 } }, { { 0, 0 }, { -2, 2 }, { -1, 1 }, { 1, 1 }, { 2, 2 }, { -2, 5 }, { 0, 4 }, { 1, 7 }, { 2, 5 }, { -1, 7 }, { 0, 8 } }, { { 0, 0 }, { -2, 2 }, { -1, 1 }, { 1, 1 }, { 2, 2 }, { -2, 6 }, { -1, 4 }, { 1, 4 }, { 2, 6 }, { 0, 7 }, { 0, 8 } }, { { 0, 0 }, { -1, 2 }, { 0, 1 }, { 0, 4 }, { 1, 2 }, { -2, 5 }, { 0, 6 }, { 1, 7 }, { 2, 5 }, { -1, 7 }, { 0, 8 } }, { { 0, 0 }, { -2, 3 }, { 0, 1 }, { 0, 2 }, { 2, 3 }, { -2, 6 }, { 0, 4 }, { 0, 5 }, { 2, 6 }, { -1, 8 }, { 1, 8 } }, { { 0, 0 }, { -2, 3 }, { 0, 1 }, { 1, 2 }, { 2, 3 }, { -2, 7 }, { -1, 2 }, { 1, 5 }, { 2, 7 }, { -1, 5 }, { 0, 8 } }, { { 0, 0 }, { -1, 2 }, { 0, 1 }, { -1, 5 }, { 1, 2 }, { -2, 7 }, { 1, 5 }, { 0, 6 }, { 2, 7 }, { -1, 8 }, { 1, 8 } }, { { 0, 0 }, { -1, 2 }, { -1, 1 }, { 1, 1 }, { 1, 2 }, { -2, 3 }, { -1, 5 }, { 1, 5 }, { 2, 3 }, { 0, 6 }, { 0, 8 } } };
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
/*     */ 
/*     */   
/*     */   public TacticsBoard(Team teamA, Team teamB) {
/*  36 */     this.teamA = teamA;
/*  37 */     this.teamB = teamB;
/*     */     
/*  39 */     Texture texture = new Texture("images/board.png");
/*  40 */     this.textureRegion = new TextureRegion(texture);
/*  41 */     this.textureRegion.flip(false, true);
/*  42 */     this.w = this.textureRegion.getRegionWidth();
/*  43 */     this.h = this.textureRegion.getRegionHeight();
/*     */     
/*  45 */     this.font = Assets.font10;
/*     */   }
/*     */   
/*     */   public TacticsBoard(Team team) {
/*  49 */     this(team, (Team)null);
/*     */   }
/*     */   
/*     */   public void setViewOpponent(boolean viewOpponent) {
/*  53 */     this.viewOpponent = viewOpponent;
/*     */   }
/*     */   
/*     */   public void setCompareTactics(boolean compareTactics) {
/*  57 */     this.compareTactics = compareTactics;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GLSpriteBatch batch, GLShapeRenderer shapeRenderer) {
/*  62 */     if (!this.visible) {
/*     */       return;
/*     */     }
/*     */     
/*  66 */     batch.begin();
/*     */     
/*  68 */     batch.draw(this.textureRegion, this.x, this.y, this.textureRegion.getRegionWidth(), this.textureRegion.getRegionHeight());
/*     */     
/*  70 */     if (!this.compareTactics || this.viewOpponent) {
/*     */       
/*  72 */       Team team = this.viewOpponent ? this.teamB : this.teamA;
/*     */       
/*  74 */       int baseTactics = (Assets.tactics[team.tactics]).basedOn;
/*     */       
/*  76 */       for (int ply = 0; ply < 11; ply++)
/*     */       {
/*     */ 
/*     */         
/*  80 */         int ty, tx = 0;
/*  81 */         Font.Align align = Font.Align.CENTER;
/*  82 */         int xSide = positions[baseTactics][ply][0];
/*  83 */         switch (this.viewOpponent ? -xSide : xSide) {
/*     */           case -2:
/*  85 */             tx = this.x + 10;
/*  86 */             align = Font.Align.LEFT;
/*     */             break;
/*     */           
/*     */           case -1:
/*  90 */             tx = this.x + this.w / 2 - 7;
/*  91 */             align = Font.Align.RIGHT;
/*     */             break;
/*     */           
/*     */           case 0:
/*  95 */             tx = this.x + this.w / 2;
/*  96 */             align = Font.Align.CENTER;
/*     */             break;
/*     */           
/*     */           case 1:
/* 100 */             tx = this.x + this.w / 2 + 7;
/* 101 */             align = Font.Align.LEFT;
/*     */             break;
/*     */           
/*     */           case 2:
/* 105 */             tx = this.x + this.w - 10;
/* 106 */             align = Font.Align.RIGHT;
/*     */             break;
/*     */         } 
/*     */ 
/*     */         
/* 111 */         if (this.viewOpponent) {
/* 112 */           ty = this.y + this.h - 14 - 18 - 28 * positions[baseTactics][ply][1];
/*     */         } else {
/* 114 */           ty = this.y + 14 + 28 * positions[baseTactics][ply][1];
/*     */         } 
/*     */         
/* 117 */         Player player = team.players.get(ply);
/*     */ 
/*     */ 
/*     */         
/* 121 */         String name = (player.shirtName.length() > 10) ? (player.shirtName.substring(0, 8) + ".") : player.shirtName;
/*     */         
/* 123 */         this.font.draw((SpriteBatch)batch, name, tx, ty, align);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 128 */       for (int tm = 0; tm < 2; tm++) {
/*     */         Team teamToShow;
/*     */         
/* 131 */         if (tm == 0) {
/* 132 */           teamToShow = this.teamA;
/*     */         } else {
/* 134 */           teamToShow = this.teamB;
/*     */         } 
/*     */         
/* 137 */         int baseTactics = (Assets.tactics[teamToShow.tactics]).basedOn;
/*     */         
/* 139 */         for (int ply = 0; ply < 11; ply++) {
/* 140 */           int ty, tx = 0;
/* 141 */           int xSide = positions[baseTactics][ply][0];
/* 142 */           switch ((tm == (this.viewOpponent ? 0 : 1)) ? -xSide : xSide) {
/*     */             case -2:
/* 144 */               tx = this.x + 26;
/*     */               break;
/*     */             
/*     */             case -1:
/* 148 */               tx = this.x + this.w / 2 - 36 - 20;
/*     */               break;
/*     */             
/*     */             case 0:
/* 152 */               tx = this.x + this.w / 2 - 8;
/*     */               break;
/*     */             
/*     */             case 1:
/* 156 */               tx = this.x + this.w / 2 + 36;
/*     */               break;
/*     */             
/*     */             case 2:
/* 160 */               tx = this.x + this.w - 26 - 20;
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 166 */           if (tm == (this.viewOpponent ? 0 : 1)) {
/* 167 */             ty = this.y + this.h - 14 - 14 - 28 * positions[baseTactics][ply][1];
/*     */           } else {
/* 169 */             ty = this.y + 14 + 28 * positions[baseTactics][ply][1];
/*     */           } 
/* 171 */           batch.draw(Assets.pieces[tm][(ply > 0) ? 1 : 0], tx, ty);
/*     */ 
/*     */           
/* 174 */           Player player = teamToShow.players.get(ply);
/* 175 */           Assets.font10.draw((SpriteBatch)batch, "" + player.number, tx + 10, ty - 1, Font.Align.CENTER);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 180 */     batch.end();
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\TacticsBoard.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */