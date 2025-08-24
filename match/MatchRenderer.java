/*      */ package com.ygames.ysoccer.match;
/*      */ 
/*      */ import com.badlogic.gdx.Gdx;
/*      */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*      */ import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/*      */ import com.ygames.ysoccer.framework.Assets;
/*      */ import com.ygames.ysoccer.framework.Font;
/*      */ import com.ygames.ysoccer.framework.GLColor;
/*      */ import com.ygames.ysoccer.framework.GLGraphics;
/*      */ import com.ygames.ysoccer.framework.Settings;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MatchRenderer
/*      */   extends SceneRenderer
/*      */ {
/*      */   private MatchState matchState;
/*      */   private final BallSprite ballSprite;
/*      */   
/*      */   MatchRenderer(GLGraphics glGraphics, Match match) {
/*   29 */     super(match);
/*   30 */     this.batch = glGraphics.batch;
/*   31 */     this.shapeRenderer = glGraphics.shapeRenderer;
/*   32 */     this.camera = glGraphics.camera;
/*   33 */     this.ball = match.ball;
/*   34 */     this.actionCamera = new ActionCamera(this.ball);
/*      */     
/*   36 */     resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.scene.settings.zoom);
/*      */     
/*   38 */     this.actionCamera.x = 0.5F * (1700.0F - this.screenWidth / this.zoom / 100.0F);
/*   39 */     this.actionCamera.y = 0.0F;
/*   40 */     for (int j = 0; j < 4096; j++) {
/*   41 */       this.vCameraX[j] = Math.round(this.actionCamera.x);
/*   42 */       this.vCameraY[j] = Math.round(this.actionCamera.y);
/*      */     } 
/*      */     
/*   45 */     this.ballSprite = new BallSprite(glGraphics, (getMatch()).ball);
/*   46 */     this.allSprites.add(this.ballSprite);
/*   47 */     for (int t = 0; t <= 1; t++) {
/*   48 */       CoachSprite coachSprite = new CoachSprite(glGraphics, ((getMatch()).team[t]).coach);
/*   49 */       this.allSprites.add(coachSprite);
/*   50 */       int len = ((getMatch()).team[t]).lineup.size();
/*   51 */       for (int k = 0; k < len; k++) {
/*   52 */         PlayerSprite playerSprite = new PlayerSprite(glGraphics, ((getMatch()).team[t]).lineup.get(k));
/*   53 */         this.allSprites.add(playerSprite);
/*      */       } 
/*      */     } 
/*      */     
/*   57 */     for (int xSide = -1; xSide <= 1; xSide += 2) {
/*   58 */       for (int ySide = -1; ySide <= 1; ySide += 2) {
/*   59 */         this.allSprites.add(new JumperSprite(glGraphics, xSide, ySide));
/*      */       }
/*      */     } 
/*      */     
/*   63 */     this.cornerFlagSprites = new CornerFlagSprite[4];
/*   64 */     for (int i = 0; i < 4; i++) {
/*   65 */       this.cornerFlagSprites[i] = new CornerFlagSprite(glGraphics, this.scene.settings, i / 2 * 2 - 1, i % 2 * 2 - 1);
/*   66 */       this.allSprites.add(this.cornerFlagSprites[i]);
/*      */     } 
/*   68 */     this.allSprites.add(new GoalTopA(glGraphics));
/*   69 */     this.allSprites.add(new GoalTopB(glGraphics));
/*      */     
/*   71 */     Assets.crowdRenderer.setMaxRank(getMatch().getRank());
/*      */   }
/*      */   
/*      */   private Match getMatch() {
/*   75 */     return (Match)this.scene;
/*      */   }
/*      */   
/*      */   public void render() {
/*   79 */     this.matchState = getMatch().getFsm().getState();
/*      */     
/*   81 */     Gdx.gl.glEnable(3042);
/*   82 */     Gdx.gl.glClear(16384);
/*   83 */     this.camera.setToOrtho(true, Gdx.graphics.getWidth() * 100.0F / this.zoom, Gdx.graphics.getHeight() * 100.0F / this.zoom);
/*   84 */     this.camera.translate((-847 + this.vCameraX[this.scene.subframe]), (-919 + this.vCameraY[this.scene.subframe]), 0.0F);
/*   85 */     this.camera.update();
/*   86 */     this.batch.setProjectionMatrix(this.camera.combined);
/*   87 */     this.batch.begin();
/*      */     
/*   89 */     renderBackground();
/*      */     
/*   91 */     if (Settings.showDevelopmentInfo && Settings.showBallZones) {
/*   92 */       drawBallZones();
/*      */     }
/*      */     
/*   95 */     if (Settings.showDevelopmentInfo && Settings.showBallPredictions) {
/*   96 */       drawBallPredictions(this.ball);
/*      */     }
/*      */     
/*   99 */     Assets.crowdRenderer.draw((SpriteBatch)this.batch);
/*      */     
/*  101 */     renderSprites();
/*      */     
/*  103 */     redrawBallShadowsOverGoals((getMatch()).ball);
/*  104 */     redrawBallOverTopGoal(this.ballSprite);
/*      */ 
/*      */     
/*  107 */     this.batch.draw(Assets.goalBottom, -73.0F, 603.0F, 146.0F, 56.0F, 0, 0, 146, 56, false, true);
/*      */     
/*  109 */     redrawBallShadowsOverGoals((getMatch()).ball);
/*  110 */     redrawBallOverBottomGoal(this.ballSprite);
/*      */     
/*  112 */     if (this.scene.settings.weatherStrength != 0) {
/*  113 */       switch (this.scene.settings.weatherEffect) {
/*      */         case 1:
/*  115 */           drawRain();
/*      */           break;
/*      */         
/*      */         case 2:
/*  119 */           drawSnow();
/*      */           break;
/*      */         
/*      */         case 3:
/*  123 */           drawFog();
/*      */           break;
/*      */       } 
/*      */     
/*      */     }
/*  128 */     if (this.matchState.displayControlledPlayer) {
/*  129 */       drawControlledPlayersNumbers();
/*      */     }
/*      */     
/*  132 */     this.batch.end();
/*      */     
/*  134 */     renderGui();
/*      */   }
/*      */   
/*      */   private void renderGui() {
/*  138 */     this.camera.setToOrtho(true, 1280.0F, this.guiHeight);
/*  139 */     this.camera.update();
/*  140 */     this.batch.setProjectionMatrix(this.camera.combined);
/*  141 */     this.shapeRenderer.setProjectionMatrix(this.camera.combined);
/*  142 */     this.batch.begin();
/*  143 */     this.batch.setColor(16777215, 0.9F);
/*      */ 
/*      */     
/*  146 */     if (this.matchState.displayBallOwner && (getMatch()).ball.owner != null) {
/*  147 */       drawPlayerNumberAndName((getMatch()).ball.owner);
/*      */     }
/*      */     
/*  150 */     if (Settings.showDevelopmentInfo) {
/*  151 */       Assets.font10.draw((SpriteBatch)this.batch, "CAMERA MODE: " + this.actionCamera.getMode() + ", SPEED: " + this.actionCamera.getSpeed(), 640, 22, Font.Align.CENTER);
/*      */     }
/*      */ 
/*      */     
/*  155 */     if (this.matchState.displayTime) {
/*  156 */       drawTime();
/*      */     }
/*      */ 
/*      */     
/*  160 */     if (this.matchState.displayRadar && (getMatch().getSettings()).radar) {
/*  161 */       drawRadar();
/*      */     }
/*      */ 
/*      */     
/*  165 */     if (this.matchState.displayWindVane && this.scene.settings.wind.speed > 0) {
/*  166 */       this.batch.draw(Assets.wind[this.scene.settings.wind.direction][this.scene.settings.wind.speed - 1], 1230.0F, 20.0F);
/*      */     }
/*      */ 
/*      */     
/*  170 */     if (this.matchState.displayRosters) {
/*  171 */       drawRosters();
/*      */     }
/*      */ 
/*      */     
/*  175 */     if (this.matchState.displayScore) {
/*  176 */       drawScore();
/*      */     }
/*      */ 
/*      */     
/*  180 */     if (this.matchState.displayPenaltiesScore) {
/*  181 */       drawPenaltiesScore();
/*      */     }
/*      */ 
/*      */     
/*  185 */     if (((getMatch()).fsm.getHotKeys()).messageTimer > 0L) {
/*  186 */       this.batch.setColor(16777215, 0.9F);
/*  187 */       Assets.font10.draw((SpriteBatch)this.batch, ((getMatch()).fsm.getHotKeys()).message, 640, 1, Font.Align.CENTER);
/*      */     } 
/*      */ 
/*      */     
/*  191 */     if (this.matchState.displayStatistics) {
/*  192 */       drawStatistics();
/*      */     }
/*      */ 
/*      */     
/*  196 */     if (this.matchState.displayGoalScorer && this.scene.subframe % 160 > 80) {
/*  197 */       drawPlayerNumberAndName((getMatch()).ball.goalOwner);
/*      */     }
/*      */     
/*  200 */     if (this.matchState.displayBenchPlayers) {
/*  201 */       drawBenchPlayers();
/*      */     }
/*      */     
/*  204 */     if (this.matchState.displayBenchFormation) {
/*  205 */       drawBenchFormation();
/*      */     }
/*      */     
/*  208 */     if (this.matchState.displayTacticsSwitch) {
/*  209 */       drawTacticsSwitch();
/*      */     }
/*      */ 
/*      */     
/*  213 */     MatchState matchState = getMatch().getFsm().getState();
/*  214 */     if (matchState != null) {
/*  215 */       matchState.render();
/*      */     }
/*  217 */     this.batch.end();
/*      */   }
/*      */   
/*      */   private void renderBackground() {
/*  221 */     this.batch.disableBlending();
/*  222 */     for (int c = 0; c < 4; c++) {
/*  223 */       for (int r = 0; r < 4; r++) {
/*  224 */         this.batch.draw(Assets.stadium[r][c], (-847 + 512 * c), (-919 + 512 * r));
/*      */       }
/*      */     } 
/*  227 */     this.batch.enableBlending();
/*      */   }
/*      */   
/*      */   private void drawBallZones() {
/*  231 */     this.batch.end();
/*  232 */     this.shapeRenderer.setAutoShapeType(true);
/*  233 */     this.shapeRenderer.setProjectionMatrix(this.camera.combined);
/*  234 */     this.shapeRenderer.begin();
/*  235 */     this.shapeRenderer.setColor(11184640, 0.4F);
/*  236 */     for (int x = 0; x < 6; x++) {
/*  237 */       this.shapeRenderer.line((-2.5F + x) * 206.0F, -644.0F, (-2.5F + x) * 206.0F, 644.0F);
/*      */     }
/*  239 */     for (int y = 0; y < 8; y++) {
/*  240 */       this.shapeRenderer.line(-515.0F, (-3.5F + y) * 184.0F, 515.0F, (-3.5F + y) * 184.0F);
/*      */     }
/*  242 */     this.shapeRenderer.end();
/*  243 */     this.batch.begin();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void drawShadows() {
/*  248 */     this.batch.setColor(16777215, this.scene.settings.shadowAlpha);
/*      */     
/*  250 */     drawBallShadow((getMatch()).ball, false);
/*      */     
/*  252 */     for (int j = 0; j < 4; j++) {
/*  253 */       this.cornerFlagSprites[j].drawShadow(this.scene.subframe, (SpriteBatch)this.batch);
/*      */     }
/*      */ 
/*      */     
/*  257 */     for (int t = 0; t <= 1; t++) {
/*  258 */       for (Player player : ((getMatch()).team[t]).lineup) {
/*  259 */         if (player.role == Player.Role.GOALKEEPER) {
/*  260 */           Data d = player.data[this.scene.subframe];
/*  261 */           if (d.isVisible) {
/*  262 */             Integer[] origin = Assets.keeperOrigins[d.fmy][d.fmx];
/*  263 */             this.batch.draw(Assets.keeperShadow[d.fmx][d.fmy][0], (d.x - origin[0].intValue()) + 0.65F * d.z, (d.y - origin[1].intValue()) + 0.46F * d.z);
/*  264 */             if (this.scene.settings.time == SceneSettings.Time.NIGHT);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  276 */     for (int i = 0; i < ((this.scene.settings.time == SceneSettings.Time.NIGHT) ? 4 : 1); i++) {
/*  277 */       for (int k = 0; k <= 1; k++) {
/*  278 */         for (Player player : ((getMatch()).team[k]).lineup) {
/*  279 */           if (player.role != Player.Role.GOALKEEPER) {
/*  280 */             Data d = player.data[this.scene.subframe];
/*  281 */             if (d.isVisible) {
/*  282 */               Integer[] origin = Assets.playerOrigins[d.fmy][d.fmx];
/*  283 */               float mX = (i == 0 || i == 3) ? 0.65F : -0.65F;
/*  284 */               float mY = (i == 0 || i == 1) ? 0.46F : -0.46F;
/*  285 */               this.batch.draw(Assets.playerShadow[d.fmx][d.fmy][i], (d.x - origin[0].intValue()) + mX * d.z, (d.y - origin[1].intValue() + 5) + mY * d.z);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  292 */     this.batch.setColor(16777215, 1.0F);
/*      */   }
/*      */   
/*      */   private void drawControlledPlayersNumbers() {
/*  296 */     for (int t = 0; t <= 1; t++) {
/*  297 */       if ((getMatch()).team[t] != null) {
/*  298 */         int len = ((getMatch()).team[t]).lineup.size();
/*  299 */         for (int i = 0; i < len; i++) {
/*  300 */           Player player = ((getMatch()).team[t]).lineup.get(i);
/*  301 */           Data d = player.data[this.scene.subframe];
/*  302 */           if (d.isVisible) {
/*  303 */             if (d.isHumanControlled) {
/*  304 */               drawPlayerNumber(player);
/*  305 */             } else if (Settings.showDevelopmentInfo && Settings.showPlayerNumber) {
/*  306 */               Assets.font6.draw((SpriteBatch)this.batch, player.number, d.x, d.y - 40 - d.z, Font.Align.CENTER);
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawRosters() {
/*  316 */     int l = 199;
/*  317 */     int r = 1280 - l + 2;
/*  318 */     int w = r - l;
/*  319 */     int t = this.guiHeight / 2 - 270 + 2;
/*  320 */     int b = this.guiHeight / 2 + 270 + 2;
/*  321 */     int h = b - t;
/*  322 */     int m1 = t + h / 8 + 2;
/*  323 */     int m2 = t + h / 3 + 2;
/*  324 */     int hw = 642;
/*      */ 
/*      */     
/*  327 */     this.batch.end();
/*  328 */     Gdx.gl.glEnable(3042);
/*  329 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*      */     
/*  331 */     fadeRect(l + 2, t + 2, r - 2, b - 2, 0.35F, 0);
/*      */ 
/*      */     
/*  334 */     this.shapeRenderer.setColor(2368548, 0.9F);
/*  335 */     drawRosterLines(l, r, w, t, b, m1, m2, hw);
/*      */     
/*  337 */     l -= 2;
/*  338 */     r -= 2;
/*  339 */     t -= 2;
/*  340 */     b -= 2;
/*  341 */     m1 -= 2;
/*  342 */     m2 -= 2;
/*  343 */     hw -= 2;
/*      */ 
/*      */     
/*  346 */     this.shapeRenderer.setColor(16777215, 0.9F);
/*  347 */     drawRosterLines(l, r, w, t, b, m1, m2, hw);
/*      */     
/*  349 */     this.shapeRenderer.end();
/*  350 */     this.batch.begin();
/*  351 */     this.batch.setColor(16777215, 0.9F);
/*      */ 
/*      */     
/*  354 */     int y = t + h / 23;
/*  355 */     Assets.font14.draw((SpriteBatch)this.batch, (getMatch()).competition.name, 640, y, Font.Align.CENTER);
/*      */ 
/*      */     
/*  358 */     if (((getMatch()).team[0]).city.length() > 0 && ((getMatch()).team[0]).stadium.length() > 0) {
/*  359 */       y = t + h / 6;
/*  360 */       Assets.font14.draw((SpriteBatch)this.batch, ((getMatch()).team[0]).stadium + ", " + ((getMatch()).team[0]).city, 640, y, Font.Align.CENTER);
/*      */     } 
/*      */ 
/*      */     
/*  364 */     y = t + 13 * h / 100;
/*      */     
/*  366 */     if (((getMatch()).team[0]).image != null) {
/*  367 */       int w0 = ((getMatch()).team[0]).image.getRegionWidth();
/*  368 */       int h0 = ((getMatch()).team[0]).image.getRegionHeight();
/*  369 */       float imageScale0 = (h0 > 70) ? (70.0F / h0) : 1.0F;
/*  370 */       int x0 = l + w / 23;
/*  371 */       int y0 = y - (int)(imageScale0 * h0) / 2;
/*  372 */       this.batch.setColor(2368548, 0.9F);
/*  373 */       this.batch.draw(((getMatch()).team[0]).image, (x0 + 2), (y0 + 2), 0.0F, 0.0F, w0, h0, imageScale0, imageScale0, 0.0F);
/*  374 */       this.batch.setColor(16777215, 0.9F);
/*  375 */       this.batch.draw(((getMatch()).team[0]).image, x0, y0, 0.0F, 0.0F, w0, h0, imageScale0, imageScale0, 0.0F);
/*      */     } 
/*  377 */     if (((getMatch()).team[1]).image != null) {
/*  378 */       int w1 = ((getMatch()).team[1]).image.getRegionWidth();
/*  379 */       int h1 = ((getMatch()).team[1]).image.getRegionHeight();
/*  380 */       float imageScale1 = (h1 > 70) ? (70.0F / h1) : 1.0F;
/*  381 */       int x1 = r - w / 23 - (int)(imageScale1 * w1);
/*  382 */       int y1 = y - (int)(imageScale1 * h1) / 2;
/*  383 */       this.batch.setColor(2368548, 0.9F);
/*  384 */       this.batch.draw(((getMatch()).team[1]).image, (x1 + 2), (y1 + 2), 0.0F, 0.0F, w1, h1, imageScale1, imageScale1, 0.0F);
/*  385 */       this.batch.setColor(16777215, 0.9F);
/*  386 */       this.batch.draw(((getMatch()).team[1]).image, x1, y1, 0.0F, 0.0F, w1, h1, imageScale1, imageScale1, 0.0F);
/*      */     } 
/*      */ 
/*      */     
/*  390 */     y = t + h / 4;
/*  391 */     Assets.font14.draw((SpriteBatch)this.batch, ((getMatch()).team[0]).name, l + w / 4, y, Font.Align.CENTER);
/*  392 */     Assets.font14.draw((SpriteBatch)this.batch, ((getMatch()).team[1]).name, l + 3 * w / 4, y, Font.Align.CENTER);
/*      */ 
/*      */     
/*  395 */     for (int tm = 0; tm <= 1; tm++) {
/*  396 */       y = t + 16 * h / 42;
/*  397 */       for (int pos = 0; pos < 11; pos++) {
/*  398 */         Player player = (getMatch()).team[tm].playerAtPosition(pos);
/*  399 */         Assets.font10.draw((SpriteBatch)this.batch, player.number, l + tm * w / 2 + w / 10, y, Font.Align.CENTER);
/*  400 */         Assets.font10.draw((SpriteBatch)this.batch, player.shirtName, l + tm * w / 2 + w / 7, y, Font.Align.LEFT);
/*  401 */         y += h / 23;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  406 */     y = t + 7 * h / 8;
/*  407 */     Assets.font10.draw((SpriteBatch)this.batch, Assets.strings.get("COACH") + ":", l + 2 * w / 25, y, Font.Align.LEFT);
/*  408 */     Assets.font10.draw((SpriteBatch)this.batch, Assets.strings.get("COACH") + ":", l + 5 * w / 9, y, Font.Align.LEFT);
/*      */     
/*  410 */     y = t + 37 * h / 40;
/*  411 */     Assets.font10.draw((SpriteBatch)this.batch, ((getMatch()).team[0]).coach.name, l + w / 4, y, Font.Align.CENTER);
/*  412 */     Assets.font10.draw((SpriteBatch)this.batch, ((getMatch()).team[1]).coach.name, l + 3 * w / 4, y, Font.Align.CENTER);
/*      */   }
/*      */   
/*      */   private void drawRosterLines(int l, int r, int w, int t, int b, int m1, int m2, int hw) {
/*  416 */     drawFrame(l, t, r - l, b - t);
/*      */ 
/*      */     
/*  419 */     this.shapeRenderer.rect(hw - 0.2F * w, m1, 0.4F * w, 1.0F);
/*  420 */     this.shapeRenderer.rect(hw - 0.2F * w, (m1 + 1), 0.4F * w, 1.0F);
/*      */ 
/*      */     
/*  423 */     this.shapeRenderer.rect(l + 0.05F * w, m2, (hw - l) - 0.1F * w, 1.0F);
/*  424 */     this.shapeRenderer.rect(l + 0.05F * w, (m2 + 1), (hw - l) - 0.1F * w, 1.0F);
/*      */ 
/*      */     
/*  427 */     this.shapeRenderer.rect(hw + 0.05F * w, m2, (r - hw) - 0.1F * w, 1.0F);
/*  428 */     this.shapeRenderer.rect(hw + 0.05F * w, (m2 + 1), (r - hw) - 0.1F * w, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawTime() {
/*  433 */     int minute = getMatch().getMinute();
/*      */ 
/*      */     
/*  436 */     this.batch.draw(Assets.time[10], 46.0F, 22.0F);
/*      */ 
/*      */     
/*  439 */     int digit = minute % 10;
/*  440 */     this.batch.draw(Assets.time[digit], 34.0F, 22.0F);
/*      */ 
/*      */     
/*  443 */     minute = (minute - digit) / 10;
/*  444 */     digit = minute % 10;
/*  445 */     if (minute > 0) {
/*  446 */       this.batch.draw(Assets.time[digit], 22.0F, 22.0F);
/*      */     }
/*      */ 
/*      */     
/*  450 */     minute = (minute - digit) / 10;
/*  451 */     digit = minute % 10;
/*  452 */     if (digit > 0) {
/*  453 */       this.batch.draw(Assets.time[digit], 10.0F, 22.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawRadar() {
/*  459 */     int RX = 10;
/*  460 */     int RY = 60;
/*  461 */     int RW = 132;
/*  462 */     int RH = 166;
/*      */     
/*  464 */     this.batch.end();
/*  465 */     Gdx.gl.glEnable(3042);
/*  466 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*      */     
/*  468 */     fadeRect(10, 60, 142, 226, 0.6F, this.scene.settings.grass.darkShadow);
/*      */     
/*  470 */     this.shapeRenderer.setColor(0, 1.0F);
/*  471 */     this.shapeRenderer.rect(10.0F, 60.0F, 1.0F, 166.0F);
/*  472 */     this.shapeRenderer.rect(11.0F, 60.0F, 130.0F, 1.0F);
/*  473 */     this.shapeRenderer.rect(11.0F, 143.0F, 130.0F, 1.0F);
/*  474 */     this.shapeRenderer.rect(11.0F, 225.0F, 130.0F, 1.0F);
/*  475 */     this.shapeRenderer.rect(141.0F, 60.0F, 1.0F, 166.0F);
/*      */ 
/*      */     
/*  478 */     this.spriteComparator.setSubframe(this.scene.subframe);
/*      */ 
/*      */     
/*  481 */     int[] shirt1 = new int[2];
/*  482 */     int[] shirt2 = new int[2];
/*  483 */     for (int t = 0; t <= 1; t++) {
/*  484 */       Kit kit = (getMatch()).team[t].getKit();
/*  485 */       shirt1[t] = kit.shirt1;
/*  486 */       shirt2[t] = kit.shirt2;
/*      */     } 
/*      */ 
/*      */     
/*  490 */     for (Sprite sprite : this.allSprites) {
/*  491 */       if (sprite.getClass() == PlayerSprite.class) {
/*  492 */         Player player = ((PlayerSprite)sprite).player;
/*  493 */         if (player.checkState(PlayerFsm.Id.STATE_BENCH_SITTING) || player.checkState(PlayerFsm.Id.STATE_OUTSIDE)) {
/*      */           continue;
/*      */         }
/*  496 */         Data d = player.data[this.scene.subframe];
/*  497 */         if (d.isVisible) {
/*  498 */           int dx = 76 + d.x / 8;
/*  499 */           int dy = 143 + d.y / 8;
/*      */           
/*  501 */           this.shapeRenderer.setColor(2368548, 1.0F);
/*  502 */           this.shapeRenderer.rect((dx - 3), (dy - 3), 6.0F, 1.0F);
/*  503 */           this.shapeRenderer.rect((dx - 4), (dy - 2), 1.0F, 4.0F);
/*  504 */           this.shapeRenderer.rect((dx - 3), (dy + 2), 6.0F, 1.0F);
/*  505 */           this.shapeRenderer.rect((dx + 3), (dy - 2), 1.0F, 4.0F);
/*      */           
/*  507 */           this.shapeRenderer.setColor(shirt1[player.team.index], 1.0F);
/*  508 */           this.shapeRenderer.rect((dx - 3), (dy - 2), 3.0F, 4.0F);
/*      */           
/*  510 */           this.shapeRenderer.setColor(shirt2[player.team.index], 1.0F);
/*  511 */           this.shapeRenderer.rect(dx, (dy - 2), 3.0F, 4.0F);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  516 */     this.shapeRenderer.end();
/*  517 */     this.batch.begin();
/*  518 */     this.batch.setColor(16777215, 0.9F);
/*      */ 
/*      */     
/*  521 */     if (this.matchState.displayControlledPlayer) {
/*  522 */       for (Sprite sprite : this.allSprites) {
/*  523 */         if (sprite.getClass() == PlayerSprite.class) {
/*  524 */           Player player = ((PlayerSprite)sprite).player;
/*  525 */           if (player.checkState(PlayerFsm.Id.STATE_BENCH_SITTING) || player.checkState(PlayerFsm.Id.STATE_OUTSIDE)) {
/*      */             continue;
/*      */           }
/*  528 */           Data d = player.data[this.scene.subframe];
/*  529 */           if (d.isVisible && player.inputDevice != player.ai) {
/*  530 */             int dx = 76 + d.x / 8 + 1;
/*  531 */             int dy = 143 + d.y / 8 - 10;
/*      */             
/*  533 */             int f0 = player.number % 10;
/*  534 */             int f1 = (player.number - f0) / 10 % 10;
/*      */ 
/*      */             
/*  537 */             if (f1 > 0) {
/*  538 */               int i = 4 - ((f0 == 1) ? 2 : 0);
/*  539 */               int w1 = 4 - ((f1 == 1) ? 2 : 0);
/*  540 */               dx -= (i + w1) / 2;
/*  541 */               this.batch.draw(Assets.tinyNumbers[f1], dx, dy);
/*  542 */               dx += w1;
/*  543 */               this.batch.draw(Assets.tinyNumbers[f0], dx, dy); continue;
/*      */             } 
/*  545 */             int w0 = 4 - ((f0 == 1) ? 2 : 0);
/*  546 */             dx -= w0 / 2;
/*  547 */             this.batch.draw(Assets.tinyNumbers[f0], dx, dy);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void drawScore() {
/*  557 */     int h0 = 0;
/*  558 */     int w0 = 0;
/*  559 */     int w1 = 0;
/*  560 */     int h1 = 0;
/*  561 */     float imageScale0 = 1.0F;
/*  562 */     float imageScale1 = 1.0F;
/*      */ 
/*      */     
/*  565 */     int rows = Math.max((getMatch()).scorers.rows[0].size(), (getMatch()).scorers.rows[1].size());
/*      */ 
/*      */     
/*  568 */     if (((getMatch()).team[0]).image != null) {
/*  569 */       w0 = ((getMatch()).team[0]).image.getRegionWidth();
/*  570 */       h0 = ((getMatch()).team[0]).image.getRegionHeight();
/*  571 */       if (h0 > 70) {
/*  572 */         imageScale0 = 70.0F / h0;
/*      */       }
/*      */     } 
/*  575 */     if (((getMatch()).team[1]).image != null) {
/*  576 */       w1 = ((getMatch()).team[1]).image.getRegionWidth();
/*  577 */       h1 = ((getMatch()).team[1]).image.getRegionHeight();
/*  578 */       if (h1 > 70) {
/*  579 */         imageScale1 = 70.0F / h1;
/*      */       }
/*      */     } 
/*      */     
/*  583 */     int hMax = Math.max((int)(imageScale0 * h0), (int)(imageScale1 * h1));
/*  584 */     int y0 = this.guiHeight - 16 - Math.max(hMax, 14 * rows);
/*      */ 
/*      */     
/*  587 */     if (((getMatch()).team[0]).image != null) {
/*  588 */       int x = 12;
/*  589 */       int y = y0 + 8 + (hMax - (int)(imageScale0 * h0)) / 2;
/*  590 */       this.batch.setColor(2368548, 0.9F);
/*  591 */       this.batch.draw(((getMatch()).team[0]).image, (x + 2), (y + 2), 0.0F, 0.0F, w0, h0, imageScale0, imageScale0, 0.0F);
/*  592 */       this.batch.setColor(16777215, 0.9F);
/*  593 */       this.batch.draw(((getMatch()).team[0]).image, x, y, 0.0F, 0.0F, w0, h0, imageScale0, imageScale0, 0.0F);
/*      */     } 
/*  595 */     if (((getMatch()).team[1]).image != null) {
/*  596 */       int x = 1280 - (int)(imageScale1 * w1) - 12;
/*  597 */       int y = y0 + 8 + (hMax - (int)(imageScale1 * h1)) / 2;
/*  598 */       this.batch.setColor(2368548, 0.9F);
/*  599 */       this.batch.draw(((getMatch()).team[1]).image, (x + 2), (y + 2), 0.0F, 0.0F, w1, h1, imageScale1, imageScale1, 0.0F);
/*  600 */       this.batch.setColor(16777215, 0.9F);
/*  601 */       this.batch.draw(((getMatch()).team[1]).image, x, y, 0.0F, 0.0F, w1, h1, imageScale1, imageScale1, 0.0F);
/*      */     } 
/*      */ 
/*      */     
/*  605 */     Assets.font14.draw((SpriteBatch)this.batch, ((getMatch()).team[0]).name, 12, y0 - 22, Font.Align.LEFT);
/*  606 */     Assets.font14.draw((SpriteBatch)this.batch, ((getMatch()).team[1]).name, 1270, y0 - 22, Font.Align.RIGHT);
/*      */ 
/*      */     
/*  609 */     this.batch.end();
/*  610 */     Gdx.gl.glEnable(3042);
/*  611 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*  612 */     this.shapeRenderer.setColor(16777215, 0.9F);
/*      */     
/*  614 */     this.shapeRenderer.rect(10.0F, y0, 618.0F, 2.0F);
/*  615 */     this.shapeRenderer.rect(652.0F, y0, 618.0F, 2.0F);
/*      */     
/*  617 */     this.shapeRenderer.setColor(2368548, 0.9F);
/*  618 */     this.shapeRenderer.rect(12.0F, (y0 + 2), 618.0F, 2.0F);
/*  619 */     this.shapeRenderer.rect(654.0F, (y0 + 2), 618.0F, 2.0F);
/*      */     
/*  621 */     this.shapeRenderer.end();
/*  622 */     this.batch.begin();
/*  623 */     this.batch.setColor(16777215, 0.9F);
/*      */ 
/*      */     
/*  626 */     int f0 = ((getMatch()).stats[0]).goals % 10;
/*  627 */     int f1 = (((getMatch()).stats[0]).goals - f0) / 10 % 10;
/*      */     
/*  629 */     if (f1 > 0) {
/*  630 */       this.batch.draw(Assets.score[f1], 577.0F, (y0 - 40));
/*      */     }
/*  632 */     this.batch.draw(Assets.score[f0], 601.0F, (y0 - 40));
/*      */ 
/*      */     
/*  635 */     this.batch.draw(Assets.score[10], 631.0F, (y0 - 40));
/*      */ 
/*      */     
/*  638 */     f0 = ((getMatch()).stats[1]).goals % 10;
/*  639 */     f1 = (((getMatch()).stats[1]).goals - f0) / 10 % 10;
/*      */     
/*  641 */     if (f1 > 0) {
/*  642 */       this.batch.draw(Assets.score[f1], 657.0F, (y0 - 40));
/*  643 */       this.batch.draw(Assets.score[f0], 681.0F, (y0 - 40));
/*      */     } else {
/*  645 */       this.batch.draw(Assets.score[f0], 657.0F, (y0 - 40));
/*      */     } 
/*      */ 
/*      */     
/*  649 */     for (int t = 0; t <= 1; t++) {
/*  650 */       int y = y0 + 4;
/*  651 */       for (String row : (getMatch()).scorers.rows[t]) {
/*  652 */         int x = 640 + ((t == 0) ? -12 : 14);
/*  653 */         Font.Align align = (t == 0) ? Font.Align.RIGHT : Font.Align.LEFT;
/*  654 */         Assets.font10.draw((SpriteBatch)this.batch, row, x, y, align);
/*  655 */         y += 14;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawPenaltiesScore() {
/*  662 */     int h0 = 0;
/*  663 */     int w0 = 0;
/*  664 */     int w1 = 0;
/*  665 */     int h1 = 0;
/*  666 */     float imageScale0 = 1.0F;
/*  667 */     float imageScale1 = 1.0F;
/*      */ 
/*      */     
/*  670 */     int rows = Math.max((getMatch()).penalties[0].size(), (getMatch()).penalties[1].size());
/*      */ 
/*      */     
/*  673 */     if (((getMatch()).team[0]).image != null) {
/*  674 */       w0 = ((getMatch()).team[0]).image.getRegionWidth();
/*  675 */       h0 = ((getMatch()).team[0]).image.getRegionHeight();
/*  676 */       if (h0 > 70) {
/*  677 */         imageScale0 = 70.0F / h0;
/*      */       }
/*      */     } 
/*  680 */     if (((getMatch()).team[1]).image != null) {
/*  681 */       w1 = ((getMatch()).team[1]).image.getRegionWidth();
/*  682 */       h1 = ((getMatch()).team[1]).image.getRegionHeight();
/*  683 */       if (h1 > 70) {
/*  684 */         imageScale1 = 70.0F / h1;
/*      */       }
/*      */     } 
/*      */     
/*  688 */     int hMax = Math.max((int)(imageScale0 * h0), (int)(imageScale1 * h1));
/*  689 */     int y0 = this.guiHeight - 16 - Math.max(hMax, 14 * rows);
/*      */ 
/*      */     
/*  692 */     if (((getMatch()).team[0]).image != null) {
/*  693 */       int x = 12;
/*  694 */       int y = y0 + 8 + (hMax - (int)(imageScale0 * h0)) / 2;
/*  695 */       this.batch.setColor(2368548, 0.9F);
/*  696 */       this.batch.draw(((getMatch()).team[0]).image, (x + 2), (y + 2), 0.0F, 0.0F, w0, h0, imageScale0, imageScale0, 0.0F);
/*  697 */       this.batch.setColor(16777215, 0.9F);
/*  698 */       this.batch.draw(((getMatch()).team[0]).image, x, y, 0.0F, 0.0F, w0, h0, imageScale0, imageScale0, 0.0F);
/*      */     } 
/*  700 */     if (((getMatch()).team[1]).image != null) {
/*  701 */       int x = 1280 - (int)(imageScale1 * w1) - 12;
/*  702 */       int y = y0 + 8 + (hMax - (int)(imageScale1 * h1)) / 2;
/*  703 */       this.batch.setColor(2368548, 0.9F);
/*  704 */       this.batch.draw(((getMatch()).team[1]).image, (x + 2), (y + 2), 0.0F, 0.0F, w1, h1, imageScale1, imageScale1, 0.0F);
/*  705 */       this.batch.setColor(16777215, 0.9F);
/*  706 */       this.batch.draw(((getMatch()).team[1]).image, x, y, 0.0F, 0.0F, w1, h1, imageScale1, imageScale1, 0.0F);
/*      */     } 
/*      */ 
/*      */     
/*  710 */     Assets.font14.draw((SpriteBatch)this.batch, ((getMatch()).team[0]).name, 12, y0 - 22, Font.Align.LEFT);
/*  711 */     Assets.font14.draw((SpriteBatch)this.batch, ((getMatch()).team[1]).name, 1270, y0 - 22, Font.Align.RIGHT);
/*      */ 
/*      */     
/*  714 */     this.batch.end();
/*  715 */     Gdx.gl.glEnable(3042);
/*  716 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*  717 */     this.shapeRenderer.setColor(16777215, 0.9F);
/*      */     
/*  719 */     this.shapeRenderer.rect(10.0F, y0, 618.0F, 2.0F);
/*  720 */     this.shapeRenderer.rect(652.0F, y0, 618.0F, 2.0F);
/*      */     
/*  722 */     this.shapeRenderer.setColor(2368548, 0.9F);
/*  723 */     this.shapeRenderer.rect(12.0F, (y0 + 2), 618.0F, 2.0F);
/*  724 */     this.shapeRenderer.rect(654.0F, (y0 + 2), 618.0F, 2.0F);
/*      */     
/*  726 */     this.shapeRenderer.end();
/*  727 */     this.batch.begin();
/*  728 */     this.batch.setColor(16777215, 0.9F);
/*      */ 
/*      */     
/*  731 */     int homeScore = getMatch().penaltyGoals(0);
/*  732 */     int f0 = homeScore % 10;
/*  733 */     int f1 = (homeScore - f0) / 10 % 10;
/*      */     
/*  735 */     if (f1 > 0) {
/*  736 */       this.batch.draw(Assets.score[f1], 577.0F, (y0 - 40));
/*      */     }
/*  738 */     this.batch.draw(Assets.score[f0], 601.0F, (y0 - 40));
/*      */ 
/*      */     
/*  741 */     this.batch.draw(Assets.score[10], 631.0F, (y0 - 40));
/*      */ 
/*      */     
/*  744 */     int awayScore = getMatch().penaltyGoals(1);
/*  745 */     f0 = awayScore % 10;
/*  746 */     f1 = (awayScore - f0) / 10 % 10;
/*      */     
/*  748 */     if (f1 > 0) {
/*  749 */       this.batch.draw(Assets.score[f1], 657.0F, (y0 - 40));
/*  750 */       this.batch.draw(Assets.score[f0], 681.0F, (y0 - 40));
/*      */     } else {
/*  752 */       this.batch.draw(Assets.score[f0], 657.0F, (y0 - 40));
/*      */     } 
/*      */ 
/*      */     
/*  756 */     for (int t = 0; t <= 1; t++) {
/*  757 */       int y = y0 + 4;
/*  758 */       for (Match.Penalty penalty : (getMatch()).penalties[t]) {
/*  759 */         int x = 640 + ((t == 0) ? -12 : 14);
/*  760 */         String text = "";
/*  761 */         switch (penalty.state) {
/*      */           case TO_KICK:
/*  763 */             text = " ";
/*      */             break;
/*      */           case SCORED:
/*  766 */             text = (t == 0) ? (penalty.kicker.shirtName + " " + '\031') : ("\031 " + penalty.kicker.shirtName);
/*      */             break;
/*      */           case MISSED:
/*  769 */             text = (t == 0) ? (penalty.kicker.shirtName + " " + '\032') : ("\032 " + penalty.kicker.shirtName);
/*      */             break;
/*      */         } 
/*      */         
/*  773 */         Font.Align align = (t == 0) ? Font.Align.RIGHT : Font.Align.LEFT;
/*  774 */         Assets.font10.draw((SpriteBatch)this.batch, text, x, y, align);
/*  775 */         y += 14;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawStatistics() {
/*  782 */     int l = 199;
/*  783 */     int r = 1280 - l + 2;
/*  784 */     int w = r - l;
/*  785 */     int t = this.guiHeight / 2 - 270 + 2;
/*  786 */     int b = this.guiHeight / 2 + 270 + 2;
/*  787 */     int h = b - t;
/*  788 */     int hw = 640;
/*      */ 
/*      */     
/*  791 */     this.batch.end();
/*  792 */     Gdx.gl.glEnable(3042);
/*  793 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*      */ 
/*      */     
/*  796 */     fadeRect(l + 2, t + 2, r - 2, t + h / 10 + 1, 0.35F, 0);
/*      */ 
/*      */     
/*  799 */     int i = t + h / 10 + 2;
/*  800 */     for (int j = 1; j < 9; j++) {
/*  801 */       fadeRect(l + 2, i + 1, r - 2, i + h / 10 - 1, 0.35F, 0);
/*  802 */       i += h / 10;
/*      */     } 
/*      */ 
/*      */     
/*  806 */     fadeRect(l + 2, i + 1, r - 2, b - 2, 0.35F, 0);
/*      */ 
/*      */     
/*  809 */     this.shapeRenderer.setColor(2368548, 0.9F);
/*  810 */     drawFrame(l, t, r - l, b - t);
/*      */     
/*  812 */     l -= 2;
/*  813 */     r -= 2;
/*  814 */     t -= 2;
/*  815 */     b -= 2;
/*      */ 
/*      */     
/*  818 */     this.shapeRenderer.setColor(16777215, 0.9F);
/*  819 */     drawFrame(l, t, r - l, b - t);
/*      */     
/*  821 */     this.shapeRenderer.end();
/*  822 */     this.batch.begin();
/*  823 */     this.batch.setColor(16777215, 0.9F);
/*      */     
/*  825 */     MatchStats homeStats = (getMatch()).stats[0];
/*  826 */     MatchStats awayStats = (getMatch()).stats[1];
/*      */     
/*  828 */     int possHome = Math.round(100.0F * (1.0F + ((getMatch()).stats[0]).ballPossession) / (2.0F + homeStats.ballPossession + awayStats.ballPossession));
/*  829 */     int possAway = 100 - possHome;
/*      */ 
/*      */     
/*  832 */     int lc = l + w / 5;
/*  833 */     int rc = r - w / 5;
/*  834 */     i = t + h / 20 - 8;
/*  835 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.strings.get("MATCH STATISTICS"), hw, i, Font.Align.CENTER);
/*      */     
/*  837 */     i += h / 10;
/*  838 */     Assets.font14.draw((SpriteBatch)this.batch, ((getMatch()).team[0]).name, lc, i, Font.Align.CENTER);
/*  839 */     Assets.font14.draw((SpriteBatch)this.batch, ((getMatch()).team[1]).name, rc, i, Font.Align.CENTER);
/*      */     
/*  841 */     i += h / 10;
/*  842 */     int homeGoals = ((getMatch()).penalties[0].size() > 0) ? getMatch().penaltiesScore(0) : homeStats.goals;
/*  843 */     int awayGoals = ((getMatch()).penalties[1].size() > 0) ? getMatch().penaltiesScore(1) : awayStats.goals;
/*  844 */     Assets.font14.draw((SpriteBatch)this.batch, homeGoals, lc, i, Font.Align.CENTER);
/*  845 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.strings.get("MATCH STATISTICS.GOALS"), hw, i, Font.Align.CENTER);
/*  846 */     Assets.font14.draw((SpriteBatch)this.batch, awayGoals, rc, i, Font.Align.CENTER);
/*      */     
/*  848 */     i += h / 10;
/*  849 */     Assets.font14.draw((SpriteBatch)this.batch, possHome, lc, i, Font.Align.CENTER);
/*  850 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.strings.get("MATCH STATISTICS.POSSESSION"), hw, i, Font.Align.CENTER);
/*  851 */     Assets.font14.draw((SpriteBatch)this.batch, possAway, rc, i, Font.Align.CENTER);
/*      */     
/*  853 */     i += h / 10;
/*  854 */     Assets.font14.draw((SpriteBatch)this.batch, homeStats.overallShots, lc, i, Font.Align.CENTER);
/*  855 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.strings.get("MATCH STATISTICS.GOAL ATTEMPTS"), hw, i, Font.Align.CENTER);
/*  856 */     Assets.font14.draw((SpriteBatch)this.batch, awayStats.overallShots, rc, i, Font.Align.CENTER);
/*      */     
/*  858 */     i += h / 10;
/*  859 */     Assets.font14.draw((SpriteBatch)this.batch, homeStats.centeredShots, lc, i, Font.Align.CENTER);
/*  860 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.strings.get("MATCH STATISTICS.ON TARGET"), hw, i, Font.Align.CENTER);
/*  861 */     Assets.font14.draw((SpriteBatch)this.batch, awayStats.centeredShots, rc, i, Font.Align.CENTER);
/*      */     
/*  863 */     i += h / 10;
/*  864 */     Assets.font14.draw((SpriteBatch)this.batch, homeStats.cornersWon, lc, i, Font.Align.CENTER);
/*  865 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.strings.get("MATCH STATISTICS.CORNERS WON"), hw, i, Font.Align.CENTER);
/*  866 */     Assets.font14.draw((SpriteBatch)this.batch, awayStats.cornersWon, rc, i, Font.Align.CENTER);
/*      */     
/*  868 */     i += h / 10;
/*  869 */     Assets.font14.draw((SpriteBatch)this.batch, homeStats.foulsConceded, lc, i, Font.Align.CENTER);
/*  870 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.strings.get("MATCH STATISTICS.FOULS CONCEDED"), hw, i, Font.Align.CENTER);
/*  871 */     Assets.font14.draw((SpriteBatch)this.batch, awayStats.foulsConceded, rc, i, Font.Align.CENTER);
/*      */     
/*  873 */     i += h / 10;
/*  874 */     Assets.font14.draw((SpriteBatch)this.batch, homeStats.yellowCards, lc, i, Font.Align.CENTER);
/*  875 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.strings.get("MATCH STATISTICS.BOOKINGS"), hw, i, Font.Align.CENTER);
/*  876 */     Assets.font14.draw((SpriteBatch)this.batch, awayStats.yellowCards, rc, i, Font.Align.CENTER);
/*      */     
/*  878 */     i += h / 10;
/*  879 */     Assets.font14.draw((SpriteBatch)this.batch, homeStats.redCards, lc, i, Font.Align.CENTER);
/*  880 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.strings.get("MATCH STATISTICS.SENDINGS OFF"), hw, i, Font.Align.CENTER);
/*  881 */     Assets.font14.draw((SpriteBatch)this.batch, awayStats.redCards, rc, i, Font.Align.CENTER);
/*      */     
/*  883 */     this.batch.setColor(16777215, 1.0F);
/*      */   }
/*      */   
/*      */   private void drawBenchPlayers() {
/*  887 */     int w = 250;
/*  888 */     int h = 18;
/*      */     
/*  890 */     int x = 428;
/*  891 */     int y = this.guiHeight / 2 - 100 + 2;
/*      */ 
/*      */     
/*  894 */     this.batch.end();
/*  895 */     Gdx.gl.glEnable(3042);
/*  896 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*  897 */     this.shapeRenderer.setColor(2368548, 0.9F);
/*      */ 
/*      */     
/*  900 */     drawFrame(x, y, w, h + 2);
/*      */ 
/*      */     
/*  903 */     this.shapeRenderer.rect(x + w / 2.0F - 41.0F, (y + 41), 82.0F, 66.0F);
/*      */ 
/*      */     
/*  906 */     drawFrame(x, y + 125, w, (getMatch().getSettings()).benchSize * h + 6);
/*      */ 
/*      */     
/*  909 */     int color = 2368548;
/*      */     
/*  911 */     if ((getMatch().getFsm()).benchStatus.selectedPosition == -1) {
/*  912 */       color = GLColor.sweepColor(color, 16768307);
/*      */     }
/*      */     
/*  915 */     fadeRect(x, y + 2, x + w - 2, y + h, 0.6F, color);
/*      */     
/*  917 */     for (int pos = 0; pos < (getMatch().getSettings()).benchSize; pos++) {
/*  918 */       color = 2368548;
/*      */       
/*  920 */       if ((getMatch().getFsm()).benchStatus.selectedPosition == pos) {
/*  921 */         color = GLColor.sweepColor(color, 16768307);
/*      */       }
/*  923 */       fadeRect(x, y + 125 + 4 + pos * h, x + w - 2, y + 125 + 2 + (pos + 1) * h, 0.6F, color);
/*      */     } 
/*      */     
/*  926 */     x -= 2;
/*  927 */     y -= 2;
/*      */ 
/*      */     
/*  930 */     this.shapeRenderer.setColor(16777215, 0.9F);
/*      */ 
/*      */     
/*  933 */     drawFrame(x, y, w, h + 2);
/*      */ 
/*      */     
/*  936 */     drawFrame(x, y + 125, w, (getMatch().getSettings()).benchSize * h + 6);
/*      */     
/*  938 */     this.shapeRenderer.end();
/*  939 */     this.batch.begin();
/*  940 */     this.batch.setColor(16777215, 0.9F);
/*      */ 
/*      */     
/*  943 */     this.batch.draw(Assets.bench[0], x + w / 2.0F - 41.0F, (y + 41));
/*      */     
/*  945 */     Assets.font10.draw((SpriteBatch)this.batch, Assets.strings.get("BENCH"), x + w / 2, y + 3, Font.Align.CENTER);
/*      */     
/*  947 */     int benchSize = Math.min((getMatch().getSettings()).benchSize, (getMatch().getFsm()).benchStatus.team.lineup.size() - 11);
/*  948 */     for (int i = 0; i < benchSize; i++) {
/*  949 */       Player player = (getMatch().getFsm()).benchStatus.team.lineupAtPosition(11 + i);
/*      */       
/*  951 */       if (!player.getState().checkId(PlayerFsm.Id.STATE_OUTSIDE)) {
/*  952 */         Assets.font10.draw((SpriteBatch)this.batch, player.number, x + 25, y + 5 + 125 + i * h, Font.Align.CENTER);
/*  953 */         Assets.font10.draw((SpriteBatch)this.batch, player.shirtName, x + 45, y + 5 + 125 + i * h, Font.Align.LEFT);
/*  954 */         Assets.font10.draw((SpriteBatch)this.batch, Assets.strings.get(player.getRoleLabel()), x + w - 20, y + 5 + 125 + i * h, Font.Align.CENTER);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void drawBenchFormation() {
/*  960 */     int w = 250;
/*  961 */     int h = 18;
/*      */     
/*  963 */     int x = 428;
/*  964 */     int y = this.guiHeight / 2 - 150 + 2;
/*      */ 
/*      */     
/*  967 */     this.batch.end();
/*  968 */     Gdx.gl.glEnable(3042);
/*  969 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*  970 */     this.shapeRenderer.setColor(2368548, 0.9F);
/*      */ 
/*      */     
/*  973 */     drawFrame(x, y, w, h + 2);
/*      */ 
/*      */     
/*  976 */     this.shapeRenderer.rect(x + w / 2.0F - 41.0F, (y + 41), 82.0F, 66.0F);
/*      */ 
/*      */     
/*  979 */     drawFrame(x, y + 125, w, 11 * h + 6);
/*      */ 
/*      */     
/*  982 */     int color = 2368548;
/*  983 */     if ((getMatch().getFsm()).benchStatus.selectedPosition == -1)
/*      */     {
/*  985 */       if ((getMatch().getFsm()).benchStatus.substPosition != -1) {
/*  986 */         color = GLColor.sweepColor(color, 16777011);
/*      */       }
/*      */       else {
/*      */         
/*  990 */         color = GLColor.sweepColor(color, 3399167);
/*      */       } 
/*      */     }
/*  993 */     fadeRect(x, y + 2, x + w - 2, y + h, 0.6F, color);
/*      */     int pos;
/*  995 */     for (pos = 0; pos < 11; pos++) {
/*  996 */       color = 2368548;
/*  997 */       if (pos == (getMatch().getFsm()).benchStatus.swapPosition) {
/*  998 */         color = 3399167;
/*      */       }
/*      */       
/* 1001 */       if (pos == (getMatch().getFsm()).benchStatus.selectedPosition)
/*      */       {
/* 1003 */         if ((getMatch().getFsm()).benchStatus.substPosition != -1) {
/* 1004 */           color = GLColor.sweepColor(2368548, 16777011);
/*      */         }
/*      */         else {
/*      */           
/* 1008 */           color = GLColor.sweepColor(color, 3399167);
/*      */         } 
/*      */       }
/* 1011 */       fadeRect(x, y + 125 + 4 + pos * h, x + w - 2, y + 125 + 2 + (pos + 1) * h, 0.6F, color);
/*      */     } 
/*      */     
/* 1014 */     x -= 2;
/* 1015 */     y -= 2;
/*      */ 
/*      */     
/* 1018 */     this.shapeRenderer.setColor(16777215, 0.9F);
/*      */ 
/*      */     
/* 1021 */     drawFrame(x, y, w, h + 2);
/*      */ 
/*      */     
/* 1024 */     drawFrame(x, y + 125, w, 11 * h + 6);
/*      */     
/* 1026 */     this.shapeRenderer.end();
/* 1027 */     this.batch.begin();
/* 1028 */     this.batch.setColor(16777215, 0.9F);
/*      */ 
/*      */     
/* 1031 */     this.batch.draw(Assets.bench[1], x + w / 2.0F - 41.0F, (y + 41));
/*      */     
/* 1033 */     Assets.font10.draw((SpriteBatch)this.batch, Assets.strings.get("FORMATION"), x + w / 2, y + 3, Font.Align.CENTER);
/*      */     
/* 1035 */     for (pos = 0; pos < 11; pos++) {
/*      */       
/* 1037 */       Player ply = (getMatch().getFsm()).benchStatus.team.lineupAtPosition(pos);
/*      */       
/* 1039 */       Assets.font10.draw((SpriteBatch)this.batch, ply.number, x + 25, y + 5 + 125 + pos * h, Font.Align.CENTER);
/* 1040 */       Assets.font10.draw((SpriteBatch)this.batch, ply.shirtName, x + 45, y + 5 + 125 + pos * h, Font.Align.LEFT);
/* 1041 */       Assets.font10.draw((SpriteBatch)this.batch, Assets.strings.get(ply.getRoleLabel()), x + w - 20, y + 5 + 125 + pos * h, Font.Align.CENTER);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void drawTacticsSwitch() {
/* 1046 */     int w = 180;
/* 1047 */     int h = 18;
/*      */     
/* 1049 */     int x = 463;
/* 1050 */     int y = this.guiHeight / 2 - 186 + 2;
/*      */ 
/*      */     
/* 1053 */     this.batch.end();
/* 1054 */     Gdx.gl.glEnable(3042);
/* 1055 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/* 1056 */     this.shapeRenderer.setColor(2368548, 0.9F);
/*      */ 
/*      */     
/* 1059 */     this.shapeRenderer.rect(x + w / 2.0F - 41.0F, y, 82.0F, 66.0F);
/*      */ 
/*      */     
/* 1062 */     drawFrame(x, y + 80, w, 18 * h + 6);
/*      */     
/*      */     int i;
/* 1065 */     for (i = 0; i < 18; i++) {
/* 1066 */       int color = 2368548;
/* 1067 */       if (i == (getMatch().getFsm()).benchStatus.selectedTactics) {
/* 1068 */         color = GLColor.sweepColor(color, 16755251);
/*      */       }
/* 1070 */       fadeRect(x, y + 84 + h * i, x + w - 2, y + 82 + h * (i + 1), 0.6F, color);
/*      */     } 
/*      */     
/* 1073 */     x -= 2;
/* 1074 */     y -= 2;
/*      */ 
/*      */     
/* 1077 */     this.shapeRenderer.setColor(16777215, 0.9F);
/*      */     
/* 1079 */     drawFrame(x, y + 80, w, 18 * h + 6);
/*      */     
/* 1081 */     this.shapeRenderer.end();
/* 1082 */     this.batch.begin();
/* 1083 */     this.batch.setColor(16777215, 0.9F);
/*      */     
/* 1085 */     this.batch.draw(Assets.bench[1], x + w / 2.0F - 41.0F, y);
/*      */     
/* 1087 */     for (i = 0; i < 18; i++) {
/* 1088 */       Assets.font10.draw((SpriteBatch)this.batch, Tactics.codes[i], x + w / 2, y + 85 + h * i, Font.Align.CENTER);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   void save() {
/* 1094 */     this.ball.save(this.scene.subframe);
/* 1095 */     (getMatch()).team[0].save(this.scene.subframe);
/* 1096 */     (getMatch()).team[1].save(this.scene.subframe);
/* 1097 */     this.vCameraX[this.scene.subframe] = Math.round(this.actionCamera.x);
/* 1098 */     this.vCameraY[this.scene.subframe] = Math.round(this.actionCamera.y);
/*      */   }
/*      */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */