/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.graphics.OrthographicCamera;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/*     */ import com.badlogic.gdx.math.Vector3;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLShapeRenderer;
/*     */ import com.ygames.ysoccer.framework.GLSpriteBatch;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ public abstract class SceneRenderer
/*     */ {
/*     */   private static final float VISIBLE_FIELD_WIDTH_MAX = 1.0F;
/*     */   private static final float VISIBLE_FIELD_WIDTH_OPT = 0.75F;
/*     */   private static final float VISIBLE_FIELD_WIDTH_MIN = 0.65F;
/*     */   static final float guiAlpha = 0.9F;
/*     */   final Scene scene;
/*     */   GLSpriteBatch batch;
/*     */   protected GLShapeRenderer shapeRenderer;
/*     */   OrthographicCamera camera;
/*     */   int screenWidth;
/*     */   int screenHeight;
/*     */   int zoom;
/*     */   int light;
/*     */   
/*     */   public static int zoomMin() {
/*  34 */     return 75;
/*     */   }
/*     */   
/*     */   public static int zoomMax() {
/*  38 */     return 115;
/*     */   }
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
/*  51 */   final int guiWidth = 1280;
/*     */   
/*     */   int guiHeight;
/*     */   public ActionCamera actionCamera;
/*  55 */   final int[] vCameraX = new int[4096];
/*  56 */   final int[] vCameraY = new int[4096];
/*     */   
/*     */   Ball ball;
/*     */   
/*  60 */   final List<Sprite> allSprites = new ArrayList<>();
/*  61 */   final Sprite.SpriteComparator spriteComparator = new Sprite.SpriteComparator();
/*     */   
/*     */   CornerFlagSprite[] cornerFlagSprites;
/*  64 */   private final int modW = 512;
/*  65 */   private final int modH = 1024;
/*  66 */   private final int modX = (int)Math.ceil(3.3203125D);
/*  67 */   private final int modY = (int)Math.ceil(1.7578125D);
/*     */   
/*     */   protected SceneRenderer(Scene scene) {
/*  70 */     this.scene = scene;
/*     */   }
/*     */   
/*     */   public abstract void render();
/*     */   
/*     */   void resize(int width, int height, int newZoom) {
/*  76 */     this.screenWidth = width;
/*  77 */     this.screenHeight = height;
/*  78 */     float zoomMin = width / 1020.0F;
/*  79 */     float zoomOpt = width / 765.0F;
/*  80 */     float zoomMax = width / 663.0F;
/*  81 */     this.zoom = 20 * (int)(5.0F * Math.min(Math.max(0.01F * newZoom * zoomOpt, zoomMin), zoomMax));
/*     */     
/*  83 */     this.actionCamera.setScreenParameters(this.screenWidth, this.screenHeight, this.zoom);
/*     */     
/*  85 */     this.guiHeight = 1280 * height / width;
/*     */   }
/*     */ 
/*     */   
/*     */   abstract void save();
/*     */   
/*     */   void renderSprites() {
/*  92 */     drawShadows();
/*     */     
/*  94 */     this.spriteComparator.setSubframe(this.scene.subframe);
/*  95 */     Collections.sort(this.allSprites, this.spriteComparator);
/*     */     
/*  97 */     for (Sprite sprite : this.allSprites) {
/*  98 */       sprite.draw(this.scene.subframe);
/*     */     }
/*     */   }
/*     */   
/*     */   abstract void drawShadows();
/*     */   
/*     */   void drawBallShadow(Ball ball, boolean redrawing) {
/* 105 */     Data d = ball.data[this.scene.subframe];
/* 106 */     for (int i = 0; i < ((this.scene.settings.time == SceneSettings.Time.NIGHT) ? 4 : 1); i++) {
/* 107 */       float oX = (i == 0 || i == 3) ? -1.0F : -5.0F;
/* 108 */       float mX = (i == 0 || i == 3) ? 0.65F : -0.65F;
/* 109 */       float oY = -3.0F;
/* 110 */       float mY = (i == 0 || i == 1) ? 0.46F : -0.46F;
/* 111 */       float x = d.x + oX + mX * d.z;
/* 112 */       float y = d.y + oY + mY * d.z;
/*     */       
/* 114 */       boolean overTheGoal = false;
/* 115 */       if (d.z > 33) {
/* 116 */         float x1 = d.x + oX + mX * (d.z - 33);
/* 117 */         float y1 = d.y + oY + mY * (d.z - 33);
/* 118 */         if (Const.isInsideGoal(x1 + 4.0F, y1 + 4.0F)) {
/* 119 */           overTheGoal = true;
/* 120 */           x = x1;
/* 121 */           y = y1 - 33.0F;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 127 */       if (((!overTheGoal ? 1 : 0) ^ redrawing) != 0) {
/* 128 */         this.batch.draw(Assets.ball[4], x, y);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void redrawBallShadowsOverGoals(Ball ball) {
/* 134 */     this.batch.setColor(16777215, this.scene.settings.shadowAlpha);
/* 135 */     drawBallShadow(ball, true);
/* 136 */     this.batch.setColor(16777215, 1.0F);
/*     */   }
/*     */   
/*     */   void drawRain() {
/* 140 */     this.batch.setColor(16777215, 0.6F);
/* 141 */     int subframe = this.scene.subframe;
/* 142 */     Assets.random.setSeed(1L);
/* 143 */     for (int i = 1; i <= 40 * this.scene.settings.weatherStrength; i++) {
/* 144 */       int x = Assets.random.nextInt(512);
/* 145 */       int y = Assets.random.nextInt(1024);
/* 146 */       int h = (Assets.random.nextInt(1024) + subframe) % 1024;
/* 147 */       if (h > 307.2F) {
/* 148 */         for (int fx = 0; fx <= this.modX; fx++) {
/* 149 */           for (int fy = 0; fy <= this.modY; fy++) {
/* 150 */             int px = (x + 512 - Math.round(subframe / 8.0F)) % 512 + 512 * (fx - 1);
/* 151 */             int py = (y + 4 * Math.round(1.0F * subframe / 8.0F)) % 1024 + 1024 * (fy - 1);
/* 152 */             int f = 3 * h / 1024;
/* 153 */             if (h > 921.6F) {
/* 154 */               f = 3;
/*     */             }
/* 156 */             this.batch.draw(Assets.rain[f], (-847 + px), (-919 + py));
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/* 161 */     Assets.random.setSeed(System.currentTimeMillis());
/* 162 */     this.batch.setColor(16777215, 1.0F);
/*     */   }
/*     */   
/*     */   void drawSnow() {
/* 166 */     this.batch.setColor(16777215, 0.7F);
/*     */     
/* 168 */     int subframe = this.scene.subframe;
/* 169 */     Assets.random.setSeed(1L);
/* 170 */     for (int i = 1; i <= 30 * this.scene.settings.weatherStrength; i++) {
/* 171 */       int x = Assets.random.nextInt(512);
/* 172 */       int y = Assets.random.nextInt(1024);
/* 173 */       int s = i % 3;
/* 174 */       int a = Assets.random.nextInt(360);
/* 175 */       for (int fx = 0; fx <= this.modX; fx++) {
/* 176 */         for (int fy = 0; fy <= this.modY; fy++) {
/* 177 */           int px = (int)(((x + 512) + 30.0F * EMath.sin((360 * subframe) / 4096.0F + a)) % 512.0F + (512 * (fx - 1)));
/* 178 */           int py = (y + 2 * Math.round(1.0F * subframe / 8.0F)) % 1024 + 1024 * (fy - 1);
/* 179 */           this.batch.draw(Assets.snow[s], (-847 + px), (-919 + py));
/*     */         } 
/*     */       } 
/*     */     } 
/* 183 */     Assets.random.setSeed(System.currentTimeMillis());
/* 184 */     this.batch.setColor(16777215, 1.0F);
/*     */   }
/*     */   
/*     */   void drawFog() {
/* 188 */     this.batch.setColor(16777215, 0.25F * this.scene.settings.weatherStrength);
/*     */     
/* 190 */     int subframe = this.scene.subframe;
/* 191 */     int TILE_WIDTH = 256;
/* 192 */     int fogX = -847 + this.vCameraX[subframe] - 2 * TILE_WIDTH + ((847 - this.vCameraX[subframe]) % TILE_WIDTH + 2 * TILE_WIDTH) % TILE_WIDTH;
/*     */     
/* 194 */     int fogY = -919 + this.vCameraY[subframe] - 2 * TILE_WIDTH + ((919 - this.vCameraY[subframe]) % TILE_WIDTH + 2 * TILE_WIDTH) % TILE_WIDTH;
/*     */     
/* 196 */     int x = fogX;
/* 197 */     while (x < fogX + this.screenWidth + 2 * TILE_WIDTH) {
/* 198 */       int y = fogY;
/* 199 */       while (y < fogY + this.screenHeight + 2 * TILE_WIDTH) {
/* 200 */         this.batch.draw(Assets.fog, x + 1.0F * subframe / 8.0F % TILE_WIDTH, y + 2.0F * subframe / 8.0F % TILE_WIDTH, 256.0F, 256.0F, 0, 0, 256, 256, false, true);
/* 201 */         y += TILE_WIDTH;
/*     */       } 
/* 203 */       x += TILE_WIDTH;
/*     */     } 
/* 205 */     this.batch.setColor(16777215, 1.0F);
/*     */   }
/*     */   
/*     */   void drawBallPredictions(Ball ball) {
/* 209 */     this.batch.end();
/* 210 */     this.shapeRenderer.setAutoShapeType(true);
/* 211 */     this.shapeRenderer.setProjectionMatrix(this.camera.combined);
/* 212 */     this.shapeRenderer.begin();
/*     */     
/* 214 */     this.shapeRenderer.setColor(255.0F, 255.0F, 255.0F, 255.0F); int frm;
/* 215 */     for (frm = 0; frm < 128; frm += 10) {
/* 216 */       Vector3 p = ball.predictionL[frm];
/* 217 */       this.shapeRenderer.circle(p.x, p.y, 1.0F);
/*     */     } 
/*     */     
/* 220 */     this.shapeRenderer.setColor(255.0F, 255.0F, 0.0F, 255.0F);
/* 221 */     for (frm = 0; frm < 128; frm += 10) {
/* 222 */       Vector3 p = ball.prediction[frm];
/* 223 */       this.shapeRenderer.circle(p.x, p.y, 1.0F);
/*     */     } 
/*     */     
/* 226 */     this.shapeRenderer.setColor(255.0F, 0.0F, 0.0F, 255.0F);
/* 227 */     for (frm = 0; frm < 128; frm += 10) {
/* 228 */       Vector3 p = ball.predictionR[frm];
/* 229 */       this.shapeRenderer.circle(p.x, p.y, 1.0F);
/*     */     } 
/*     */     
/* 232 */     this.shapeRenderer.end();
/* 233 */     this.batch.begin();
/*     */   }
/*     */   
/*     */   void fadeRect(int x0, int y0, int x1, int y1, float alpha, int color) {
/* 237 */     this.shapeRenderer.setColor(color, alpha);
/* 238 */     this.shapeRenderer.rect(x0, y0, (x1 - x0), (y1 - y0));
/*     */   }
/*     */   
/*     */   void drawFrame(int x, int y, int w, int h) {
/* 242 */     int r = x + w;
/* 243 */     int b = y + h;
/*     */ 
/*     */     
/* 246 */     this.shapeRenderer.rect((x + 5), y, (w - 8), 1.0F);
/* 247 */     this.shapeRenderer.rect((x + 3), (y + 1), (w - 4), 1.0F);
/*     */ 
/*     */     
/* 250 */     this.shapeRenderer.rect((x + 2), (y + 2), 4.0F, 1.0F);
/* 251 */     this.shapeRenderer.rect((x + 2), (y + 3), 1.0F, 3.0F);
/* 252 */     this.shapeRenderer.rect((x + 3), (y + 3), 1.0F, 1.0F);
/*     */ 
/*     */     
/* 255 */     this.shapeRenderer.rect((r - 4), (y + 2), 4.0F, 1.0F);
/* 256 */     this.shapeRenderer.rect((r - 1), (y + 3), 1.0F, 3.0F);
/* 257 */     this.shapeRenderer.rect((r - 2), (y + 3), 1.0F, 1.0F);
/*     */ 
/*     */     
/* 260 */     this.shapeRenderer.rect(x, (y + 5), 1.0F, (h - 8));
/* 261 */     this.shapeRenderer.rect((x + 1), (y + 3), 1.0F, (h - 4));
/*     */ 
/*     */     
/* 264 */     this.shapeRenderer.rect((r + 1), (y + 5), 1.0F, (h - 8));
/* 265 */     this.shapeRenderer.rect(r, (y + 3), 1.0F, (h - 4));
/*     */ 
/*     */     
/* 268 */     this.shapeRenderer.rect((x + 2), (b - 4), 1.0F, 3.0F);
/* 269 */     this.shapeRenderer.rect((x + 2), (b - 1), 4.0F, 1.0F);
/* 270 */     this.shapeRenderer.rect((x + 3), (b - 2), 1.0F, 1.0F);
/*     */ 
/*     */     
/* 273 */     this.shapeRenderer.rect((r - 1), (b - 4), 1.0F, 3.0F);
/* 274 */     this.shapeRenderer.rect((r - 4), (b - 1), 4.0F, 1.0F);
/* 275 */     this.shapeRenderer.rect((r - 2), (b - 2), 1.0F, 1.0F);
/*     */ 
/*     */     
/* 278 */     this.shapeRenderer.rect((x + 5), (b + 1), (w - 8), 1.0F);
/* 279 */     this.shapeRenderer.rect((x + 3), b, (w - 4), 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   void drawHelp(TreeMap<Integer, String[]> keyMap) {
/* 284 */     int rows = keyMap.size() + 1;
/* 285 */     int rowHeight = 52;
/*     */     
/* 287 */     int left = 271;
/* 288 */     int right = 1280 - left + 2;
/* 289 */     int width = right - left;
/* 290 */     int top = this.guiHeight / 2 - rowHeight * rows / 2 + 2;
/* 291 */     int bottom = top + rowHeight * rows;
/* 292 */     int halfWay = 640;
/*     */ 
/*     */     
/* 295 */     this.batch.end();
/* 296 */     Gdx.gl.glEnable(3042);
/* 297 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*     */ 
/*     */     
/* 300 */     fadeRect(left + 2, top + 2, right - 2, top + rowHeight + 1, 0.35F, 0);
/*     */ 
/*     */     
/* 303 */     int i = top + rowHeight + 2;
/* 304 */     for (int j = 1; j < rows - 1; j++) {
/* 305 */       fadeRect(left + 2, i + 1, right - 2, i + rowHeight - 1, 0.35F, 0);
/* 306 */       i += rowHeight;
/*     */     } 
/*     */ 
/*     */     
/* 310 */     fadeRect(left + 2, i + 1, right - 2, bottom - 2, 0.35F, 0);
/*     */ 
/*     */     
/* 313 */     this.shapeRenderer.setColor(2368548, 0.9F);
/* 314 */     drawFrame(left, top, right - left, bottom - top);
/*     */     
/* 316 */     left -= 2;
/* 317 */     right -= 2;
/* 318 */     top -= 2;
/* 319 */     bottom -= 2;
/*     */ 
/*     */     
/* 322 */     this.shapeRenderer.setColor(16777215, 0.9F);
/* 323 */     drawFrame(left, top, right - left, bottom - top);
/*     */     
/* 325 */     this.shapeRenderer.end();
/* 326 */     this.batch.begin();
/* 327 */     this.batch.setColor(16777215, 0.9F);
/*     */     
/* 329 */     int lc = left + 3 * width / 18;
/* 330 */     int rc = right - 12 * width / 18;
/* 331 */     i = top + rowHeight / 2 - 8;
/* 332 */     Assets.font14.draw((SpriteBatch)this.batch, Assets.gettext("HELP"), halfWay, i, Font.Align.CENTER);
/*     */     
/* 334 */     for (Integer key : keyMap.keySet()) {
/* 335 */       i += rowHeight;
/* 336 */       String[] info = keyMap.get(key);
/* 337 */       Assets.font14.draw((SpriteBatch)this.batch, info[0], lc, i, Font.Align.CENTER);
/* 338 */       Assets.font14.draw((SpriteBatch)this.batch, info[1], rc, i, Font.Align.LEFT);
/*     */     } 
/*     */     
/* 341 */     this.batch.setColor(16777215, 1.0F);
/*     */   }
/*     */   
/*     */   void redrawBallOverTopGoal(BallSprite ballSprite) {
/* 345 */     Data d = this.ball.data[this.scene.subframe];
/* 346 */     if (EMath.isIn(d.x, -71.0F, 71.0F) && d.y < -640 && d.z > 33.0F - (
/*     */       
/* 348 */       Math.abs(d.y) - 640) / 3.0F) {
/* 349 */       ballSprite.draw(this.scene.subframe);
/*     */     }
/*     */   }
/*     */   
/*     */   void redrawBallOverBottomGoal(BallSprite ballSprite) {
/* 354 */     Data d = this.ball.data[this.scene.subframe];
/* 355 */     if (EMath.isIn(d.x, -75.0F, 75.0F) && (d.y >= 661 || (d.y > 640 && d.z > 33.0F - (
/* 356 */       Math.abs(d.y) - 640) / 3.0F))) {
/* 357 */       ballSprite.draw(this.scene.subframe);
/*     */     }
/*     */   }
/*     */   
/*     */   void drawPlayerNumber(Player player) {
/* 362 */     Data d = player.data[this.scene.subframe];
/*     */     
/* 364 */     int f0 = player.number % 10;
/* 365 */     int f1 = (player.number - f0) / 10 % 10;
/*     */     
/* 367 */     int dx = Math.round(d.x) + 1;
/* 368 */     int dy = Math.round(d.y) - 40 - Math.round(d.z);
/*     */     
/* 370 */     int w0 = 6 - ((f0 == 1) ? 2 : 1);
/* 371 */     int w1 = 6 - ((f1 == 1) ? 2 : 1);
/*     */     
/* 373 */     int fy = (this.scene.settings.pitchType == Pitch.Type.WHITE) ? 1 : 0;
/* 374 */     if (f1 > 0) {
/* 375 */       dx -= (w0 + 2 + w1) / 2;
/* 376 */       this.batch.draw(Assets.playerNumbers[f1][fy], dx, dy, 6.0F, 10.0F);
/* 377 */       dx = dx + w1 + 2;
/* 378 */       this.batch.draw(Assets.playerNumbers[f0][fy], dx, dy, 6.0F, 10.0F);
/*     */     } else {
/* 380 */       this.batch.draw(Assets.playerNumbers[f0][fy], dx - w0 / 2.0F, dy, 6.0F, 10.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   void drawPlayerNumberAndName(Player player) {
/* 385 */     Assets.font10.draw((SpriteBatch)this.batch, player.number + " " + player.shirtName, 10, 2, Font.Align.LEFT);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\SceneRenderer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */