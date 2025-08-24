/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.RgbPair;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import com.ygames.ysoccer.match.Player;
/*     */ import com.ygames.ysoccer.match.Team;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class ViewTeam extends GLScreen {
/*     */   private Font font10yellow;
/*     */   
/*     */   ViewTeam(GLGame game, Team team, Competition competition) {
/*  21 */     super(game);
/*     */     
/*  23 */     this.background = game.stateBackground;
/*     */     
/*  25 */     this.font10yellow = new Font(10, 13, 17, 12, 16, new RgbPair(16579836, 16579584));
/*  26 */     this.font10yellow.load();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  31 */     int x = 0;
/*  32 */     for (int p = 0; p < 26; p++) {
/*  33 */       Player player = team.playerAtPosition(p);
/*     */       
/*  35 */       PlayerNumberLabel playerNumberLabel = new PlayerNumberLabel(p, player);
/*  36 */       this.widgets.add(playerNumberLabel);
/*     */       
/*  38 */       PlayerNameButton playerNameButton = new PlayerNameButton(p, player, team, competition);
/*  39 */       this.widgets.add(playerNameButton);
/*     */       
/*  41 */       x = 448;
/*  42 */       if (team.type == Team.Type.CLUB) {
/*  43 */         PlayerNationalityLabel playerNationalityLabel = new PlayerNationalityLabel(x, p, player);
/*  44 */         this.widgets.add(playerNationalityLabel);
/*  45 */         x += 58;
/*     */       } 
/*     */       
/*  48 */       PlayerRoleLabel playerRoleLabel = new PlayerRoleLabel(x, p, player);
/*  49 */       this.widgets.add(playerRoleLabel);
/*  50 */       x += 34;
/*     */       
/*  52 */       Player.Skill[] orderedSkills = null;
/*  53 */       if (player != null) {
/*  54 */         orderedSkills = player.getOrderedSkills();
/*     */       }
/*  56 */       for (int j = 0; j < 3; j++) {
/*  57 */         PlayerSkillLabel playerSkillLabel = new PlayerSkillLabel(orderedSkills, j, x, p, player);
/*  58 */         this.widgets.add(playerSkillLabel);
/*  59 */         x += 13;
/*     */       } 
/*  61 */       x += 31;
/*     */       
/*  63 */       PlayerGoalsLabel playerGoalsLabel = new PlayerGoalsLabel(x, p, player);
/*  64 */       this.widgets.add(playerGoalsLabel);
/*     */     } 
/*     */     
/*  67 */     Label label = new Label();
/*  68 */     label.setText(Assets.strings.get("GOALS"), Font.Align.CENTER, Assets.font10);
/*  69 */     label.setPosition(x + 15, 116);
/*  70 */     this.widgets.add(label);
/*     */     
/*  72 */     TitleBar titleBar = new TitleBar(team);
/*  73 */     this.widgets.add(titleBar);
/*     */     
/*  75 */     ExitButton exitButton = new ExitButton();
/*  76 */     this.widgets.add(exitButton);
/*     */     
/*  78 */     setSelectedWidget((Widget)exitButton);
/*     */   }
/*     */   
/*     */   private class PlayerNumberLabel
/*     */     extends Label {
/*     */     PlayerNumberLabel(int p, Player player) {
/*  84 */       setGeometry(54, 126 + 22 * p, 30, 20);
/*  85 */       setText("", Font.Align.CENTER, Assets.font10);
/*  86 */       if (player != null)
/*  87 */         setText(player.number); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNameButton
/*     */     extends Button
/*     */   {
/*     */     PlayerNameButton(int p, Player player, Team team, Competition competition) {
/*  95 */       setGeometry(84, 126 + 22 * p, 364, 20);
/*  96 */       setText("", Font.Align.LEFT, Assets.font10);
/*  97 */       ViewTeam.this.setPlayerWidgetColor((Widget)this, p, team, competition);
/*  98 */       if (player != null) {
/*  99 */         setText(player.name);
/*     */       }
/* 101 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerNationalityLabel
/*     */     extends Label {
/*     */     PlayerNationalityLabel(int x, int p, Player player) {
/* 108 */       setGeometry(x + 3, 126 + 22 * p, 58, 20);
/* 109 */       setText("", Font.Align.CENTER, Assets.font10);
/* 110 */       if (player != null)
/* 111 */         setText("(" + player.nationality + ")"); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerRoleLabel
/*     */     extends Label
/*     */   {
/*     */     PlayerRoleLabel(int x, int p, Player player) {
/* 119 */       setGeometry(x, 126 + 22 * p, 34, 20);
/* 120 */       setText("", Font.Align.CENTER, Assets.font10);
/* 121 */       if (player != null)
/* 122 */         setText(Assets.strings.get(player.getRoleLabel())); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerSkillLabel
/*     */     extends Label
/*     */   {
/*     */     PlayerSkillLabel(Player.Skill[] skills, int j, int x, int p, Player player) {
/* 130 */       setGeometry(x, 126 + 22 * p, 13, 20);
/* 131 */       setText("", Font.Align.CENTER, ViewTeam.this.font10yellow);
/* 132 */       if (player != null && skills != null)
/* 133 */         setText(Assets.strings.get(Player.getSkillLabel(skills[j]))); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class PlayerGoalsLabel
/*     */     extends Label
/*     */   {
/*     */     PlayerGoalsLabel(int x, int p, Player player) {
/* 141 */       setGeometry(x, 126 + 22 * p, 30, 20);
/* 142 */       setText("", Font.Align.CENTER, Assets.font10);
/* 143 */       if (player != null)
/* 144 */         setText(ViewTeam.this.game.competition.getScorerGoals(player)); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class TitleBar
/*     */     extends Button
/*     */   {
/*     */     TitleBar(Team team) {
/* 152 */       Objects.requireNonNull(ViewTeam.this.game.gui); setGeometry((1280 - 600) / 2, 45, 601, 41);
/* 153 */       setColor(24030);
/* 154 */       setText(team.name, Font.Align.CENTER, Assets.font14);
/* 155 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button {
/*     */     ExitButton() {
/* 162 */       Objects.requireNonNull(ViewTeam.this.game.gui); setGeometry(1280 - 185 - 30, 660, 145, 40);
/* 163 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 164 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 169 */       ViewTeam.this.game.setScreen((Screen)new CompetitionViewTeams(ViewTeam.this.game));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPlayerWidgetColor(Widget w, int pos, Team team, Competition competition) {
/* 175 */     if (pos == 0) {
/* 176 */       w.setColor(38110);
/*     */     
/*     */     }
/* 179 */     else if (pos < 11) {
/* 180 */       w.setColor(24030);
/*     */     
/*     */     }
/* 183 */     else if (pos < team.players.size()) {
/*     */       
/* 185 */       if (pos < 11 + competition.benchSize) {
/* 186 */         w.setColor(18086);
/*     */       }
/*     */       else {
/*     */         
/* 190 */         w.setColor(3158064);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 195 */       w.setColor(1052688);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\ViewTeam.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */