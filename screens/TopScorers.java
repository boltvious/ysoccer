/*     */ package com.ygames.ysoccer.screens;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class TopScorers extends GLScreen {
/*     */   TopScorers(GLGame game) {
/*  16 */     super(game);
/*     */     
/*  18 */     this.background = game.stateBackground;
/*     */ 
/*     */     
/*  21 */     int row = 0;
/*  22 */     int goals = 10000;
/*  23 */     int position = 0;
/*  24 */     for (Competition.Scorer scorer : game.competition.scorers) {
/*     */       int color;
/*  26 */       if (scorer.goals < goals) {
/*  27 */         goals = scorer.goals;
/*  28 */         position++;
/*     */       } 
/*     */       
/*  31 */       row++;
/*     */       
/*  33 */       PositionLabel positionLabel = new PositionLabel(21 * row, position);
/*  34 */       this.widgets.add(positionLabel);
/*     */       
/*  36 */       if (game.settings.useFlags) {
/*  37 */         ScorerNationalityFlagButton scorerNationalityFlagButton = new ScorerNationalityFlagButton(21 * row, scorer.player.nationality);
/*  38 */         this.widgets.add(scorerNationalityFlagButton);
/*     */       } else {
/*  40 */         ScorerNationalityCodeButton scorerNationalityCodeButton = new ScorerNationalityCodeButton(21 * row, scorer.player.nationality);
/*  41 */         this.widgets.add(scorerNationalityCodeButton);
/*     */       } 
/*     */ 
/*     */       
/*  45 */       switch (position) {
/*     */         case 1:
/*  47 */           color = 12234017;
/*     */           break;
/*     */         case 2:
/*  50 */           color = 11382189;
/*     */           break;
/*     */         default:
/*  53 */           color = (position % 2 == 0) ? 9863749 : 8219705;
/*     */           break;
/*     */       } 
/*     */       
/*  57 */       ScorerNameButton scorerNameButton = new ScorerNameButton(21 * row, scorer.player, color);
/*  58 */       this.widgets.add(scorerNameButton);
/*     */       
/*  60 */       TeamNameLabel teamNameLabel = new TeamNameLabel(21 * row, scorer.player.team, color);
/*  61 */       this.widgets.add(teamNameLabel);
/*     */       
/*  63 */       GoalsLabel goalsLabel = new GoalsLabel(21 * row, scorer.goals, color);
/*  64 */       this.widgets.add(goalsLabel);
/*     */       
/*  66 */       if (row > 21) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  72 */     int y0 = 360 - 11 * row;
/*  73 */     for (Widget widget : this.widgets) {
/*  74 */       widget.y += y0;
/*     */     }
/*     */     
/*  77 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("HIGHEST SCORER LIST"), game.stateColor.body.intValue());
/*  78 */     this.widgets.add(titleBar);
/*     */     
/*  80 */     ExitButton exitButton = new ExitButton();
/*  81 */     this.widgets.add(exitButton);
/*     */     
/*  83 */     setSelectedWidget((Widget)exitButton);
/*     */   }
/*     */   
/*     */   private class PositionLabel
/*     */     extends Button {
/*     */     PositionLabel(int y, int position) {
/*  89 */       Objects.requireNonNull(TopScorers.this.game.gui); setGeometry(1280 / 2 - 240 + 3 - 30 + 2 - (TopScorers.this.game.settings.useFlags ? 32 : 58), y, 30, 23);
/*  90 */       setText(position, Font.Align.CENTER, Assets.font10);
/*  91 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ScorerNationalityFlagButton
/*     */     extends Button {
/*     */     ScorerNationalityFlagButton(int y, String nationality) {
/*  98 */       Objects.requireNonNull(TopScorers.this.game.gui); setGeometry(1280 / 2 - 240 + 3 - 32, y, 32, 23);
/*  99 */       this.textureRegion = Assets.getNationalityFlag(nationality);
/* 100 */       setImagePosition(2, 4);
/* 101 */       setActive(false);
/* 102 */       setAddShadow(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ScorerNationalityCodeButton
/*     */     extends Button {
/*     */     ScorerNationalityCodeButton(int y, String nationality) {
/* 109 */       Objects.requireNonNull(TopScorers.this.game.gui); setGeometry(1280 / 2 - 240 + 3 - 58, y, 58, 23);
/* 110 */       setText("(" + nationality + ")", Font.Align.CENTER, Assets.font10);
/* 111 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ScorerNameButton
/*     */     extends Button {
/*     */     ScorerNameButton(int y, Player player, int color) {
/* 118 */       Objects.requireNonNull(TopScorers.this.game.gui); setGeometry(1280 / 2 - 240 + 1, y, 240, 23);
/* 119 */       setColors(Integer.valueOf(color), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 120 */       setText(player.shirtName, Font.Align.LEFT, Assets.font10);
/* 121 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class TeamNameLabel
/*     */     extends Button {
/*     */     TeamNameLabel(int y, Team team, int color) {
/* 128 */       Objects.requireNonNull(TopScorers.this.game.gui); setGeometry(1280 / 2 - 1, y, 240, 23);
/* 129 */       setColors(Integer.valueOf(color), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 130 */       setText(team.name, Font.Align.LEFT, Assets.font10);
/* 131 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class GoalsLabel
/*     */     extends Button {
/*     */     GoalsLabel(int y, int goals, int color) {
/* 138 */       setColors(Integer.valueOf(color), Integer.valueOf(1973790), Integer.valueOf(1973790));
/* 139 */       Objects.requireNonNull(TopScorers.this.game.gui); setGeometry(1280 / 2 + 240 - 3, y, 40, 23);
/* 140 */       setText(goals, Font.Align.CENTER, Assets.font10);
/* 141 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 148 */       Objects.requireNonNull(TopScorers.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 149 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 150 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 155 */       TopScorers.this.game.setScreen((Screen)new ViewStatistics(TopScorers.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\TopScorers.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */