/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGraphics;
/*     */ import com.ygames.ysoccer.framework.Settings;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TrainingRenderer
/*     */   extends SceneRenderer
/*     */ {
/*     */   private TrainingState trainingState;
/*     */   private final BallSprite ballSprite;
/*     */   
/*     */   TrainingRenderer(GLGraphics glGraphics, Training training) {
/*  20 */     super(training);
/*  21 */     this.batch = glGraphics.batch;
/*  22 */     this.shapeRenderer = glGraphics.shapeRenderer;
/*  23 */     this.camera = glGraphics.camera;
/*  24 */     this.ball = training.ball;
/*  25 */     this.actionCamera = new ActionCamera(this.ball);
/*     */     
/*  27 */     resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.scene.settings.zoom);
/*     */     
/*  29 */     this.actionCamera.x = 0.5F * (1700.0F - this.screenWidth / this.zoom / 100.0F);
/*  30 */     this.actionCamera.y = 0.5F * (1800.0F - this.screenHeight / this.zoom / 100.0F);
/*  31 */     for (int i = 0; i < 4096; i++) {
/*  32 */       this.vCameraX[i] = Math.round(this.actionCamera.x);
/*  33 */       this.vCameraY[i] = Math.round(this.actionCamera.y);
/*     */     } 
/*     */     
/*  36 */     this.ballSprite = new BallSprite(glGraphics, training.ball);
/*  37 */     this.allSprites.add(this.ballSprite);
/*  38 */     CoachSprite coachSprite = new CoachSprite(glGraphics, (training.team[0]).coach);
/*  39 */     this.allSprites.add(coachSprite);
/*     */     
/*  41 */     for (int t = 0; t <= 1; t++) {
/*  42 */       int len = (training.team[t]).lineup.size();
/*  43 */       for (int k = 0; k < len; k++) {
/*  44 */         PlayerSprite playerSprite = new PlayerSprite(glGraphics, (training.team[t]).lineup.get(k));
/*  45 */         this.allSprites.add(playerSprite);
/*     */       } 
/*     */     } 
/*     */     
/*  49 */     for (int xSide = -1; xSide <= 1; xSide += 2) {
/*  50 */       for (int ySide = -1; ySide <= 1; ySide += 2) {
/*  51 */         this.allSprites.add(new JumperSprite(glGraphics, xSide, ySide));
/*     */       }
/*     */     } 
/*     */     
/*  55 */     this.cornerFlagSprites = new CornerFlagSprite[4];
/*  56 */     for (int j = 0; j < 4; j++) {
/*  57 */       this.cornerFlagSprites[j] = new CornerFlagSprite(glGraphics, this.scene.settings, j / 2 * 2 - 1, j % 2 * 2 - 1);
/*  58 */       this.allSprites.add(this.cornerFlagSprites[j]);
/*     */     } 
/*  60 */     this.allSprites.add(new GoalTopA(glGraphics));
/*  61 */     this.allSprites.add(new GoalTopB(glGraphics));
/*     */   }
/*     */   
/*     */   private Training getTraining() {
/*  65 */     return (Training)this.scene;
/*     */   }
/*     */   
/*     */   public void render() {
/*  69 */     this.trainingState = getTraining().getFsm().getState();
/*     */     
/*  71 */     Gdx.gl.glEnable(3042);
/*  72 */     Gdx.gl.glClear(16384);
/*  73 */     this.camera.setToOrtho(true, Gdx.graphics.getWidth() * 100.0F / this.zoom, Gdx.graphics.getHeight() * 100.0F / this.zoom);
/*  74 */     this.camera.translate((-847 + this.vCameraX[this.scene.subframe]), (-919 + this.vCameraY[this.scene.subframe]), 0.0F);
/*  75 */     this.camera.update();
/*  76 */     this.batch.setProjectionMatrix(this.camera.combined);
/*  77 */     this.batch.begin();
/*     */     
/*  79 */     renderBackground();
/*     */     
/*  81 */     if (Settings.showDevelopmentInfo && Settings.showBallPredictions) {
/*  82 */       drawBallPredictions(this.ball);
/*     */     }
/*     */     
/*  85 */     renderSprites();
/*     */     
/*  87 */     redrawBallShadowsOverGoals((getTraining()).ball);
/*  88 */     redrawBallOverTopGoal(this.ballSprite);
/*     */ 
/*     */     
/*  91 */     this.batch.draw(Assets.goalBottom, -73.0F, 603.0F, 146.0F, 56.0F, 0, 0, 146, 56, false, true);
/*     */     
/*  93 */     redrawBallShadowsOverGoals((getTraining()).ball);
/*  94 */     redrawBallOverBottomGoal(this.ballSprite);
/*     */     
/*  96 */     if (this.scene.settings.weatherStrength != 0) {
/*  97 */       switch (this.scene.settings.weatherEffect) {
/*     */         case 1:
/*  99 */           drawRain();
/*     */           break;
/*     */         
/*     */         case 2:
/* 103 */           drawSnow();
/*     */           break;
/*     */         
/*     */         case 3:
/* 107 */           drawFog();
/*     */           break;
/*     */       } 
/*     */     
/*     */     }
/* 112 */     if (this.trainingState.displayControlledPlayer) {
/* 113 */       drawControlledPlayersNumbers();
/*     */     }
/*     */     
/* 116 */     this.batch.end();
/*     */     
/* 118 */     renderGui();
/*     */   }
/*     */   
/*     */   private void renderGui() {
/* 122 */     this.camera.setToOrtho(true, 1280.0F, this.guiHeight);
/* 123 */     this.camera.update();
/* 124 */     this.batch.setProjectionMatrix(this.camera.combined);
/* 125 */     this.shapeRenderer.setProjectionMatrix(this.camera.combined);
/* 126 */     this.batch.begin();
/* 127 */     this.batch.setColor(16777215, 0.9F);
/*     */ 
/*     */     
/* 130 */     if ((getTraining()).ball.owner != null) {
/* 131 */       drawPlayerNumberAndName((getTraining()).ball.owner);
/*     */     }
/*     */ 
/*     */     
/* 135 */     if (this.scene.settings.wind.speed > 0) {
/* 136 */       this.batch.draw(Assets.wind[this.scene.settings.wind.direction][this.scene.settings.wind.speed - 1], 1230.0F, 20.0F);
/*     */     }
/*     */ 
/*     */     
/* 140 */     if (((getTraining()).fsm.getHotKeys()).messageTimer > 0L) {
/* 141 */       this.batch.setColor(16777215, 0.9F);
/* 142 */       Assets.font10.draw((SpriteBatch)this.batch, ((getTraining()).fsm.getHotKeys()).message, 640, 1, Font.Align.CENTER);
/*     */     } 
/*     */ 
/*     */     
/* 146 */     TrainingState trainingState = getTraining().getFsm().getState();
/* 147 */     if (trainingState != null) {
/* 148 */       trainingState.render();
/*     */     }
/*     */     
/* 151 */     this.batch.end();
/*     */   }
/*     */   
/*     */   private void renderBackground() {
/* 155 */     this.batch.disableBlending();
/* 156 */     for (int c = 0; c < 4; c++) {
/* 157 */       for (int r = 0; r < 4; r++) {
/* 158 */         this.batch.draw(Assets.stadium[r][c], (-847 + 512 * c), (-919 + 512 * r));
/*     */       }
/*     */     } 
/* 161 */     this.batch.enableBlending();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawShadows() {
/* 166 */     this.batch.setColor(16777215, this.scene.settings.shadowAlpha);
/*     */     
/* 168 */     drawBallShadow((getTraining()).ball, false);
/*     */     
/* 170 */     for (int j = 0; j < 4; j++) {
/* 171 */       this.cornerFlagSprites[j].drawShadow(this.scene.subframe, (SpriteBatch)this.batch);
/*     */     }
/*     */ 
/*     */     
/* 175 */     for (int t = 0; t <= 1; t++) {
/* 176 */       for (Player player : ((getTraining()).team[t]).lineup) {
/* 177 */         if (player.role == Player.Role.GOALKEEPER) {
/* 178 */           Data d = player.data[this.scene.subframe];
/* 179 */           if (d.isVisible) {
/* 180 */             Integer[] origin = Assets.keeperOrigins[d.fmy][d.fmx];
/* 181 */             this.batch.draw(Assets.keeperShadow[d.fmx][d.fmy][0], (d.x - origin[0].intValue()) + 0.65F * d.z, (d.y - origin[1].intValue()) + 0.46F * d.z);
/* 182 */             if (this.scene.settings.time == SceneSettings.Time.NIGHT);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     for (int i = 0; i < ((this.scene.settings.time == SceneSettings.Time.NIGHT) ? 4 : 1); i++) {
/* 195 */       for (int k = 0; k <= 1; k++) {
/* 196 */         for (Player player : ((getTraining()).team[k]).lineup) {
/* 197 */           if (player.role != Player.Role.GOALKEEPER) {
/* 198 */             Data d = player.data[this.scene.subframe];
/* 199 */             if (d.isVisible) {
/* 200 */               Integer[] origin = Assets.playerOrigins[d.fmy][d.fmx];
/* 201 */               float mX = (i == 0 || i == 3) ? 0.65F : -0.65F;
/* 202 */               float mY = (i == 0 || i == 1) ? 0.46F : -0.46F;
/* 203 */               this.batch.draw(Assets.playerShadow[d.fmx][d.fmy][i], (d.x - origin[0].intValue()) + mX * d.z, (d.y - origin[1].intValue() + 5) + mY * d.z);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     this.batch.setColor(16777215, 1.0F);
/*     */   }
/*     */   
/*     */   private void drawControlledPlayersNumbers() {
/* 214 */     for (int t = 0; t <= 1; t++) {
/* 215 */       if ((getTraining()).team[t] != null) {
/* 216 */         int len = ((getTraining()).team[t]).lineup.size();
/* 217 */         for (int i = 0; i < len; i++) {
/* 218 */           Player player = ((getTraining()).team[t]).lineup.get(i);
/* 219 */           if ((player.inputDevice != player.ai && player.isVisible) || (Settings.development && Settings.showPlayerNumber))
/*     */           {
/* 221 */             drawPlayerNumber(player);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void save() {
/* 230 */     this.ball.save(this.scene.subframe);
/* 231 */     (getTraining()).team[0].save(this.scene.subframe);
/* 232 */     (getTraining()).team[1].save(this.scene.subframe);
/* 233 */     this.vCameraX[this.scene.subframe] = Math.round(this.actionCamera.x);
/* 234 */     this.vCameraY[this.scene.subframe] = Math.round(this.actionCamera.y);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\TrainingRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */