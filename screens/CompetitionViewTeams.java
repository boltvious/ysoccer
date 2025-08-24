/*    */ package com.ygames.ysoccer.screens;
/*    */ 
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.Font;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.framework.GLScreen;
/*    */ import com.ygames.ysoccer.gui.Button;
/*    */ import com.ygames.ysoccer.gui.Widget;
/*    */ import com.ygames.ysoccer.match.Team;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ class CompetitionViewTeams
/*    */   extends GLScreen
/*    */ {
/*    */   CompetitionViewTeams(GLGame game) {
/* 21 */     super(game);
/*    */     
/* 23 */     this.background = game.stateBackground;
/*    */ 
/*    */ 
/*    */     
/* 27 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("SELECT SQUAD TO VIEW"), game.stateColor.body.intValue());
/* 28 */     this.widgets.add(titleBar);
/*    */     
/* 30 */     List<Widget> list = new ArrayList<>();
/* 31 */     for (Team team : game.competition.teams) {
/* 32 */       TeamButton teamButton = new TeamButton(team);
/* 33 */       list.add(teamButton);
/* 34 */       this.widgets.add(teamButton);
/*    */     } 
/*    */ 
/*    */     
/* 38 */     if (list.size() > 0) {
/* 39 */       Collections.sort(list, Widget.widgetComparatorByText);
/* 40 */       Objects.requireNonNull(game.gui); Widget.arrange(1280, 350, 32, 20, list);
/* 41 */       setSelectedWidget(list.get(0));
/*    */     } 
/*    */     
/* 44 */     ExitButton exitButton = new ExitButton();
/* 45 */     this.widgets.add(exitButton);
/*    */   }
/*    */   
/*    */   private class TeamButton
/*    */     extends Button {
/*    */     Team team;
/*    */     
/*    */     TeamButton(Team team) {
/* 53 */       this.team = team;
/* 54 */       setSize(270, 30);
/* 55 */       setColors(Integer.valueOf(9988382), Integer.valueOf(13142824), Integer.valueOf(4072960));
/* 56 */       setText(team.name, Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 61 */       CompetitionViewTeams.this.game.setScreen((Screen)new ViewTeam(CompetitionViewTeams.this.game, this.team, CompetitionViewTeams.this.game.competition));
/*    */     }
/*    */   }
/*    */   
/*    */   private class ExitButton
/*    */     extends Button {
/*    */     ExitButton() {
/* 68 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 69 */       Objects.requireNonNull(CompetitionViewTeams.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 70 */       setText(Assets.gettext("EXIT"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 75 */       CompetitionViewTeams.this.game.setScreen((Screen)new ViewStatistics(CompetitionViewTeams.this.game));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\CompetitionViewTeams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */