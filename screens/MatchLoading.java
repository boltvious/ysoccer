/*    */ package com.ygames.ysoccer.screens;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.ygames.ysoccer.competitions.Competition;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.framework.GLScreen;
/*    */ import com.ygames.ysoccer.match.Match;
/*    */ import com.ygames.ysoccer.match.MatchSettings;
/*    */ import com.ygames.ysoccer.match.Player;
/*    */ import com.ygames.ysoccer.match.SceneSettings;
/*    */ 
/*    */ 
/*    */ class MatchLoading
/*    */   extends GLScreen
/*    */ {
/*    */   private final Match match;
/*    */   
/*    */   MatchLoading(GLGame game, MatchSettings matchSettings, Competition competition) {
/* 21 */     super(game);
/*    */     
/* 23 */     this.playMenuMusic = false;
/* 24 */     this.usesMouse = false;
/*    */     
/* 26 */     matchSettings.setup();
/*    */     
/* 28 */     this.match = competition.getMatch();
/* 29 */     this.match.init(game, matchSettings, competition);
/*    */     
/* 31 */     game.disableMouse();
/*    */     
/* 33 */     Assets.loadStadium((SceneSettings)matchSettings);
/* 34 */     Assets.loadCrowd(this.match.team[0]);
/* 35 */     Assets.loadBall((SceneSettings)matchSettings);
/* 36 */     Assets.loadCornerFlags((SceneSettings)matchSettings);
/* 37 */     for (int t = 0; t <= 1; t++) {
/* 38 */       this.match.team[t].loadImage();
/* 39 */       Assets.loadCoach(this.match.team[t]);
/* 40 */       int len = (this.match.team[t]).lineup.size();
/* 41 */       for (int i = 0; i < len; i++) {
/* 42 */         Player player = (this.match.team[t]).lineup.get(i);
/* 43 */         if (player.role == Player.Role.GOALKEEPER) {
/* 44 */           Assets.loadKeeper(player);
/*    */         } else {
/* 46 */           Assets.loadPlayer(player, (this.match.team[t]).kits.get((this.match.team[t]).kitIndex));
/*    */         } 
/* 48 */         Assets.loadHair(player);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(float deltaTime) {
/* 55 */     super.render(deltaTime);
/*    */     
/* 57 */     Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
/* 58 */     Gdx.gl.glClear(16640);
/*    */     
/* 60 */     this.game.setScreen((Screen)new MatchScreen(this.game, this.match));
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\MatchLoading.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */