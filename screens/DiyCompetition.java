/*    */ package com.ygames.ysoccer.screens;
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.ygames.ysoccer.competitions.Competition;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.Font;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.framework.GLScreen;
/*    */ import com.ygames.ysoccer.gui.Button;
/*    */ import com.ygames.ysoccer.gui.Widget;
/*    */ import java.util.Objects;
/*    */ 
/*    */ class DiyCompetition extends GLScreen {
/*    */   DiyCompetition(GLGame game) {
/* 14 */     super(game);
/*    */     
/* 16 */     game.setState(GLGame.State.COMPETITION, Competition.Category.DIY_COMPETITION);
/*    */     
/* 18 */     this.background = game.stateBackground;
/*    */ 
/*    */ 
/*    */     
/* 22 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get("DIY COMPETITION"), 3632687);
/* 23 */     this.widgets.add(titleBar);
/*    */     
/* 25 */     LeagueButton leagueButton = new LeagueButton();
/* 26 */     this.widgets.add(leagueButton);
/* 27 */     setSelectedWidget((Widget)leagueButton);
/*    */     
/* 29 */     CupButton cupButton = new CupButton();
/* 30 */     this.widgets.add(cupButton);
/*    */     
/* 32 */     TournamentButton tournamentButton = new TournamentButton();
/* 33 */     this.widgets.add(tournamentButton);
/*    */     
/* 35 */     ExitButton exitButton = new ExitButton();
/* 36 */     this.widgets.add(exitButton);
/*    */   }
/*    */   
/*    */   private class LeagueButton
/*    */     extends Button {
/*    */     LeagueButton() {
/* 42 */       Objects.requireNonNull(DiyCompetition.this.game.gui); setGeometry((1280 - 340) / 2, 280, 340, 44);
/* 43 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/* 44 */       setText(Assets.strings.get("LEAGUE"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 49 */       DiyCompetition.this.game.setScreen((Screen)new DesignDiyLeague(DiyCompetition.this.game));
/*    */     }
/*    */   }
/*    */   
/*    */   private class CupButton
/*    */     extends Button {
/*    */     CupButton() {
/* 56 */       Objects.requireNonNull(DiyCompetition.this.game.gui); setGeometry((1280 - 340) / 2, 350, 340, 44);
/* 57 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/* 58 */       setText(Assets.strings.get("CUP"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 63 */       DiyCompetition.this.game.setScreen((Screen)new DesignDiyCup(DiyCompetition.this.game));
/*    */     }
/*    */   }
/*    */   
/*    */   private class TournamentButton
/*    */     extends Button {
/*    */     TournamentButton() {
/* 70 */       Objects.requireNonNull(DiyCompetition.this.game.gui); setGeometry((1280 - 340) / 2, 420, 340, 44);
/* 71 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/* 72 */       setText(Assets.strings.get("TOURNAMENT"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 77 */       DiyCompetition.this.game.setScreen((Screen)new DesignDiyTournament(DiyCompetition.this.game));
/*    */     }
/*    */   }
/*    */   
/*    */   private class ExitButton
/*    */     extends Button {
/*    */     ExitButton() {
/* 84 */       Objects.requireNonNull(DiyCompetition.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 85 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/* 86 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 91 */       DiyCompetition.this.game.setScreen((Screen)new Main(DiyCompetition.this.game));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DiyCompetition.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */