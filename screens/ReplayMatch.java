/*    */ package com.ygames.ysoccer.screens;
/*    */ 
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.badlogic.gdx.graphics.Texture;
/*    */ import com.ygames.ysoccer.competitions.Competition;
/*    */ import com.ygames.ysoccer.competitions.Friendly;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.Font;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.framework.GLScreen;
/*    */ import com.ygames.ysoccer.gui.Button;
/*    */ import com.ygames.ysoccer.gui.Widget;
/*    */ import com.ygames.ysoccer.match.Match;
/*    */ import java.util.Objects;
/*    */ 
/*    */ class ReplayMatch
/*    */   extends GLScreen {
/*    */   private Match match;
/*    */   
/*    */   ReplayMatch(GLGame game, Match match) {
/* 21 */     super(game);
/* 22 */     this.match = match;
/*    */     
/* 24 */     this.background = new Texture("images/backgrounds/menu_match_presentation.jpg");
/*    */ 
/*    */ 
/*    */     
/* 28 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, match.competition.name, game.stateColor.body.intValue());
/* 29 */     this.widgets.add(titleBar);
/*    */     
/* 31 */     ReplayButton replayButton = new ReplayButton();
/* 32 */     this.widgets.add(replayButton);
/*    */     
/* 34 */     setSelectedWidget((Widget)replayButton);
/*    */     
/* 36 */     ExitButton exitButton = new ExitButton();
/* 37 */     this.widgets.add(exitButton);
/*    */   }
/*    */   
/*    */   private class ReplayButton
/*    */     extends Button {
/*    */     ReplayButton() {
/* 43 */       Objects.requireNonNull(ReplayMatch.this.game.gui); setGeometry((1280 - 240) / 2, 330, 240, 50);
/* 44 */       setColor(14417920);
/* 45 */       setText(Assets.strings.get("REPLAY"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 50 */       Friendly friendly = new Friendly();
/* 51 */       friendly.getMatch().setTeam(0, ReplayMatch.this.match.team[0]);
/* 52 */       friendly.getMatch().setTeam(1, ReplayMatch.this.match.team[1]);
/*    */       
/* 54 */       ReplayMatch.this.game.setScreen((Screen)new MatchLoading(ReplayMatch.this.game, ReplayMatch.this.match.getSettings(), (Competition)friendly));
/*    */     }
/*    */   }
/*    */   
/*    */   private class ExitButton
/*    */     extends Button {
/*    */     ExitButton() {
/* 61 */       setColor(13124096);
/* 62 */       Objects.requireNonNull(ReplayMatch.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 63 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font14);
/*    */     }
/*    */ 
/*    */     
/*    */     public void onFire1Down() {
/* 68 */       ReplayMatch.this.game.setScreen((Screen)new Main(ReplayMatch.this.game));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\ReplayMatch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */