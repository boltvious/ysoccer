/*    */ package com.ygames.ysoccer.screens;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.framework.GLScreen;
/*    */ import com.ygames.ysoccer.framework.InputDevice;
/*    */ import com.ygames.ysoccer.match.Kit;
/*    */ import com.ygames.ysoccer.match.Player;
/*    */ import com.ygames.ysoccer.match.SceneSettings;
/*    */ import com.ygames.ysoccer.match.Team;
/*    */ import com.ygames.ysoccer.match.Training;
/*    */ 
/*    */ 
/*    */ 
/*    */ class TrainingLoading
/*    */   extends GLScreen
/*    */ {
/*    */   private final Training training;
/*    */   
/*    */   TrainingLoading(GLGame game, SceneSettings sceneSettings) {
/* 23 */     super(game);
/* 24 */     this.playMenuMusic = false;
/* 25 */     this.usesMouse = false;
/*    */     
/* 27 */     Team trainingTeam = navigation.team;
/*    */     
/* 29 */     sceneSettings.setup();
/*    */     
/* 31 */     this.training = new Training(trainingTeam);
/* 32 */     this.training.init(game, sceneSettings);
/* 33 */     assignInputDevices();
/*    */     
/* 35 */     game.disableMouse();
/*    */     
/* 37 */     Assets.loadStadium(sceneSettings);
/* 38 */     Assets.loadCrowd(trainingTeam);
/* 39 */     Assets.loadBall(sceneSettings);
/* 40 */     Assets.loadCornerFlags(sceneSettings);
/* 41 */     Assets.loadCoach(trainingTeam);
/* 42 */     Kit[] trainingKits = { new Kit("PLAIN", 12578156, 2507411, 2507411, 2507411, 12578156), new Kit("PLAIN", 15563613, 15658732, 15658732, 15658732, 15563613) };
/*    */ 
/*    */ 
/*    */     
/* 46 */     for (int t = 0; t <= 1; t++) {
/* 47 */       Team team = this.training.team[t];
/* 48 */       for (Player player : team.lineup) {
/* 49 */         if (player.role == Player.Role.GOALKEEPER) {
/* 50 */           Assets.loadKeeper(player);
/*    */         } else {
/* 52 */           Assets.loadPlayer(player, trainingKits[t]);
/*    */         } 
/* 54 */         Assets.loadHair(player);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(float deltaTime) {
/* 61 */     super.render(deltaTime);
/*    */     
/* 63 */     Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
/* 64 */     Gdx.gl.glClear(16640);
/*    */     
/* 66 */     this.game.setScreen((Screen)new TrainingScreen(this.game, this.training));
/*    */   }
/*    */   
/*    */   private void assignInputDevices() {
/* 70 */     int assigned_devices = 0;
/* 71 */     int[] i = { (this.training.team[0]).lineup.size() - 1, (this.training.team[1]).lineup.size() - 1 };
/* 72 */     int t = 0;
/* 73 */     while (i[0] >= 0 || i[1] >= 0) {
/* 74 */       if (i[t] >= 0) {
/* 75 */         Player ply = (this.training.team[t]).lineup.get(i[t]);
/* 76 */         if (assigned_devices < this.game.inputDevices.size()) {
/* 77 */           if (ply.role != Player.Role.GOALKEEPER) {
/* 78 */             ply.setInputDevice((InputDevice)this.game.inputDevices.get(assigned_devices));
/* 79 */             assigned_devices++;
/*    */           } 
/*    */         } else {
/* 82 */           ply.setInputDevice((InputDevice)ply.ai);
/*    */         } 
/* 84 */         i[t] = i[t] - 1;
/*    */       } 
/* 86 */       t = 1 - t;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\TrainingLoading.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */