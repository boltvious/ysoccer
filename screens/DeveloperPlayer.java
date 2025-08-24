/*      */ package com.ygames.ysoccer.screens;
/*      */ 
/*      */ import com.badlogic.gdx.Gdx;
/*      */ import com.badlogic.gdx.InputAdapter;
/*      */ import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/*      */ import com.ygames.ysoccer.framework.Assets;
/*      */ import com.ygames.ysoccer.framework.Color3;
/*      */ import com.ygames.ysoccer.framework.EMath;
/*      */ import com.ygames.ysoccer.framework.Font;
/*      */ import com.ygames.ysoccer.framework.GLColor;
/*      */ import com.ygames.ysoccer.framework.GLGame;
/*      */ import com.ygames.ysoccer.framework.GLScreen;
/*      */ import com.ygames.ysoccer.gui.Button;
/*      */ import com.ygames.ysoccer.gui.InputButton;
/*      */ import com.ygames.ysoccer.gui.Widget;
/*      */ import com.ygames.ysoccer.match.Data;
/*      */ import com.ygames.ysoccer.match.Hair;
/*      */ import com.ygames.ysoccer.match.Kit;
/*      */ import com.ygames.ysoccer.match.Player;
/*      */ import com.ygames.ysoccer.match.PlayerSprite;
/*      */ import com.ygames.ysoccer.match.Skin;
/*      */ import com.ygames.ysoccer.match.Team;
/*      */ 
/*      */ class DeveloperPlayer extends GLScreen {
/*      */   private Animation animation;
/*      */   private int animationLength;
/*      */   private int animationSpeed;
/*      */   private Shadows shadows;
/*      */   
/*   30 */   private enum Animation { ANIMATION_OFF, HORIZONTAL, VERTICAL; }
/*      */   
/*   32 */   private enum Shadows { NONE, DAY, NIGHT; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   40 */   private final int displayedRows = 7;
/*      */   
/*      */   private final Team team;
/*   43 */   private final int selectedKit = 0; private final Player player;
/*      */   private final PlayerSprite playerSprite;
/*      */   private int fmx;
/*      */   private int fmy;
/*   47 */   private int fmx2 = 0;
/*   48 */   private int fmy2 = 0;
/*      */   
/*      */   private int firstRow;
/*      */   
/*      */   private final Widget hairMapPosition;
/*      */   
/*      */   private final Widget hairFrameXButton;
/*      */   
/*      */   private final Widget hairFrameYButton;
/*      */   private final Widget saveHairMapButton;
/*      */   private final Widget resetHairMapButton;
/*      */   private final Widget originValue;
/*      */   private final Widget saveOriginsButton;
/*      */   private final Widget resetOriginsButton;
/*      */   private boolean hasExited = false;
/*      */   private String savedHairMap;
/*      */   private String savedOrigins;
/*      */   
/*      */   DeveloperPlayer(GLGame game) {
/*   67 */     super(game);
/*      */     
/*   69 */     Gdx.input.setInputProcessor((com.badlogic.gdx.InputProcessor)new InputProcessor());
/*      */     
/*   71 */     this.background = new Texture("images/backgrounds/menu_edit_team.jpg");
/*      */     
/*   73 */     this.animation = Animation.ANIMATION_OFF;
/*   74 */     this.animationLength = 8;
/*   75 */     this.animationSpeed = 3;
/*      */     
/*   77 */     this.shadows = Shadows.DAY;
/*      */ 
/*      */ 
/*      */     
/*   81 */     this.team = new Team();
/*   82 */     Kit kit = new Kit();
/*   83 */     kit.style = "PLAIN";
/*   84 */     kit.shirt1 = Kit.colors[2];
/*   85 */     kit.shirt2 = Kit.colors[5];
/*   86 */     kit.shirt3 = Kit.colors[1];
/*   87 */     this.team.kits.add(kit);
/*   88 */     this.player = new Player();
/*   89 */     this.fmx = 0;
/*   90 */     this.fmy = 0;
/*   91 */     this.firstRow = 0;
/*   92 */     this.player.team = this.team;
/*   93 */     this.player.isVisible = true;
/*   94 */     this.player.data[0] = new Data();
/*   95 */     this.player.save(0);
/*   96 */     this.player.skinColor = Skin.Color.PINK;
/*   97 */     this.player.hairColor = Hair.Color.BLACK;
/*   98 */     this.player.hairStyle = "SMOOTH_A";
/*   99 */     reloadPlayer();
/*  100 */     reloadHair();
/*  101 */     this.playerSprite = new PlayerSprite(game.glGraphics, this.player);
/*  102 */     this.savedHairMap = Assets.json.toJson(Assets.playerHairMap);
/*  103 */     this.savedOrigins = Assets.json.toJson(Assets.playerOrigins);
/*      */     
/*  105 */     int x = 10;
/*  106 */     int y = 20;
/*      */     
/*  108 */     StyleLabel styleLabel = new StyleLabel(x, y);
/*  109 */     this.widgets.add(styleLabel);
/*      */     
/*  111 */     y += 26;
/*  112 */     StyleButton styleButton = new StyleButton(x, y);
/*  113 */     this.widgets.add(styleButton);
/*      */     
/*  115 */     y += 28;
/*  116 */     KitFieldLabel kitFieldLabel = new KitFieldLabel("KITS.SHIRT", x, y, 76);
/*  117 */     this.widgets.add(kitFieldLabel);
/*      */     int f;
/*  119 */     for (f = 0; f < 3; f++) {
/*  120 */       HashButton hashButton = new HashButton(Kit.Field.values()[f], x, y);
/*  121 */       this.widgets.add(hashButton);
/*      */       
/*  123 */       KitColorButton kitColorButton = new KitColorButton(Kit.Field.values()[f], x, y);
/*  124 */       this.widgets.add(kitColorButton);
/*      */       
/*  126 */       y += 26;
/*      */     } 
/*      */     
/*  129 */     for (f = 3; f < 5; f++) {
/*  130 */       String label = "";
/*  131 */       switch (Kit.Field.values()[f]) {
/*      */         case ANIMATION_OFF:
/*  133 */           label = "KITS.SHORTS";
/*      */           break;
/*      */         case HORIZONTAL:
/*  136 */           label = "KITS.SOCKS";
/*      */           break;
/*      */       } 
/*  139 */       kitFieldLabel = new KitFieldLabel(label, x, y, 24);
/*  140 */       this.widgets.add(kitFieldLabel);
/*      */       
/*  142 */       HashButton hashButton = new HashButton(Kit.Field.values()[f], x, y);
/*  143 */       this.widgets.add(hashButton);
/*      */       
/*  145 */       KitColorButton kitColorButton = new KitColorButton(Kit.Field.values()[f], x, y);
/*  146 */       this.widgets.add(kitColorButton);
/*      */       
/*  148 */       y += 26;
/*      */     } 
/*      */     
/*  151 */     y += 16;
/*  152 */     SkinColorButton skinColorButton = new SkinColorButton(x, y);
/*  153 */     this.widgets.add(skinColorButton);
/*      */     
/*  155 */     HairColorButton hairColorButton = new HairColorButton(x + 30, y);
/*  156 */     this.widgets.add(hairColorButton);
/*      */     
/*  158 */     HairStyleButton hairStyleButton = new HairStyleButton(x + 60, y);
/*  159 */     this.widgets.add(hairStyleButton);
/*      */     
/*  161 */     y += 40;
/*  162 */     HairLabel hairLabel = new HairLabel(x, y);
/*  163 */     this.widgets.add(hairLabel);
/*      */     
/*  165 */     MoveHairButton moveHairButton = new MoveHairButton(x + 75, y, 0, -1);
/*  166 */     this.widgets.add(moveHairButton);
/*      */     
/*  168 */     this.hairFrameXButton = (Widget)new HairFrameButton(x + 118, y, 1, 0);
/*  169 */     this.widgets.add(this.hairFrameXButton);
/*      */     
/*  171 */     this.hairFrameYButton = (Widget)new HairFrameButton(x + 150, y, 0, 1);
/*  172 */     this.widgets.add(this.hairFrameYButton);
/*      */     
/*  174 */     y += 34;
/*  175 */     moveHairButton = new MoveHairButton(x + 20, y, -1, 0);
/*  176 */     this.widgets.add(moveHairButton);
/*      */     
/*  178 */     this.hairMapPosition = (Widget)new HairPosition(x + 54, y);
/*  179 */     this.widgets.add(this.hairMapPosition);
/*      */     
/*  181 */     moveHairButton = new MoveHairButton(x + 130, y, 1, 0);
/*  182 */     this.widgets.add(moveHairButton);
/*      */     
/*  184 */     y += 34;
/*  185 */     moveHairButton = new MoveHairButton(x + 75, y, 0, 1);
/*  186 */     this.widgets.add(moveHairButton);
/*      */     
/*  188 */     this.saveHairMapButton = (Widget)new SaveHairMapButton(x, y);
/*  189 */     this.widgets.add(this.saveHairMapButton);
/*      */     
/*  191 */     this.resetHairMapButton = (Widget)new ResetHairMapButton(x + 109, y);
/*  192 */     this.widgets.add(this.resetHairMapButton);
/*      */     
/*  194 */     y += 40;
/*  195 */     ColumnButton columnButton = new ColumnButton(x, y);
/*  196 */     this.widgets.add(columnButton);
/*      */     
/*  198 */     RowButton rowButton = new RowButton(x, y);
/*  199 */     this.widgets.add(rowButton);
/*      */     
/*  201 */     y += 40;
/*  202 */     AnimationTypeButton animationTypeButton = new AnimationTypeButton(x, y);
/*  203 */     this.widgets.add(animationTypeButton);
/*      */     
/*  205 */     y += 25;
/*  206 */     AnimationLengthButton animationLengthButton = new AnimationLengthButton(x, y);
/*  207 */     this.widgets.add(animationLengthButton);
/*      */     
/*  209 */     y += 25;
/*  210 */     AnimationSpeedButton animationSpeedButton = new AnimationSpeedButton(x, y);
/*  211 */     this.widgets.add(animationSpeedButton);
/*      */     
/*  213 */     y += 40;
/*  214 */     OriginLabel originLabel = new OriginLabel(x, y);
/*  215 */     this.widgets.add(originLabel);
/*      */     
/*  217 */     MoveOriginButton moveOriginButton = new MoveOriginButton(x + 75, y, 0, -1);
/*  218 */     this.widgets.add(moveOriginButton);
/*      */     
/*  220 */     y += 34;
/*  221 */     moveOriginButton = new MoveOriginButton(x + 20, y, -1, 0);
/*  222 */     this.widgets.add(moveOriginButton);
/*      */     
/*  224 */     this.originValue = (Widget)new OriginValue(x + 54, y);
/*  225 */     this.widgets.add(this.originValue);
/*      */     
/*  227 */     moveOriginButton = new MoveOriginButton(x + 130, y, 1, 0);
/*  228 */     this.widgets.add(moveOriginButton);
/*      */     
/*  230 */     y += 34;
/*  231 */     moveOriginButton = new MoveOriginButton(x + 75, y, 0, 1);
/*  232 */     this.widgets.add(moveOriginButton);
/*      */     
/*  234 */     this.saveOriginsButton = (Widget)new saveOriginsButton(x, y);
/*  235 */     this.widgets.add(this.saveOriginsButton);
/*      */     
/*  237 */     this.resetOriginsButton = (Widget)new ResetOriginsButton(x + 109, y);
/*  238 */     this.widgets.add(this.resetOriginsButton);
/*      */     
/*  240 */     y += 46;
/*  241 */     ShadowsButton shadowsButton = new ShadowsButton(x, y);
/*  242 */     this.widgets.add(shadowsButton);
/*      */     
/*  244 */     for (int c = 0; c < 8; c++) {
/*  245 */       for (int r = 0; r < 7; r++) {
/*  246 */         FrameButton frameButton = new FrameButton(c, r);
/*  247 */         this.widgets.add(frameButton);
/*      */       } 
/*      */     } 
/*      */     
/*  251 */     ExitButton exitButton = new ExitButton(x);
/*  252 */     this.widgets.add(exitButton);
/*      */     
/*  254 */     setSelectedWidget((Widget)exitButton);
/*      */   }
/*      */   
/*      */   public void render(float delta) {
/*      */     int i;
/*  259 */     super.render(delta);
/*      */ 
/*      */     
/*  262 */     if (this.hasExited)
/*      */       return; 
/*  264 */     this.camera.setToOrtho(true, this.game.gui.screenWidth / 2.0F, this.game.gui.screenHeight / 2.0F);
/*  265 */     this.camera.translate(-this.game.gui.originX / 2.0F, -this.game.gui.originY / 2.0F);
/*  266 */     this.camera.update();
/*      */     
/*  268 */     this.batch.setProjectionMatrix(this.camera.combined);
/*  269 */     this.batch.setColor(16777215, 1.0F);
/*      */     
/*  271 */     this.shapeRenderer.setProjectionMatrix(this.camera.combined);
/*  272 */     this.shapeRenderer.setColor(2795336, 1.0F);
/*  273 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*  274 */     this.shapeRenderer.rect(100.0F, 0.0F, 540.0F, 360.0F);
/*      */     
/*  276 */     int x0 = 120;
/*  277 */     int y0 = 5;
/*      */ 
/*      */     
/*  280 */     this.shapeRenderer.setColor(4473924, 1.0F);
/*  281 */     this.shapeRenderer.rect((x0 - 18), y0 - 350.0F * this.firstRow / getPlayerRows(), 4.0F, 2450.0F / getPlayerRows());
/*      */ 
/*      */     
/*  284 */     this.shapeRenderer.setColor(16579584, 1.0F);
/*  285 */     switch (this.animation) {
/*      */       case ANIMATION_OFF:
/*  287 */         this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow)));
/*  288 */         this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*  289 */         this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*  290 */         this.shapeRenderer.line((x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*      */         break;
/*      */       case HORIZONTAL:
/*  293 */         for (i = 0; i < this.animationLength; i++) {
/*  294 */           int x = (this.fmx + i) % 8;
/*  295 */           this.shapeRenderer.line((x0 - 1 + 50 * x), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * (x + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow)));
/*  296 */           this.shapeRenderer.line((x0 - 1 + 50 * x), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50), (x0 - 1 + 50 * (x + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*  297 */           this.shapeRenderer.line((x0 - 1 + 50 * x), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * x), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*  298 */           this.shapeRenderer.line((x0 - 1 + 50 * (x + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * (x + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*      */         } 
/*      */         break;
/*      */       case VERTICAL:
/*  302 */         for (i = 0; i < this.animationLength; i++) {
/*  303 */           int y = (this.fmy + this.firstRow + i) % getPlayerRows();
/*  304 */           this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * y), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * y));
/*  305 */           this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (y + 1)), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (y + 1)));
/*  306 */           this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * y), (x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (y + 1)));
/*  307 */           this.shapeRenderer.line((x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * y), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (y + 1)));
/*      */         } 
/*      */         break;
/*      */     } 
/*  311 */     this.shapeRenderer.end();
/*      */ 
/*      */     
/*  314 */     this.batch.begin();
/*  315 */     for (int j = 0; j < 7; j++) {
/*  316 */       for (int k = 0; k < 8; k++) {
/*  317 */         this.player.fmx = k;
/*  318 */         this.player.fmy = (j - this.firstRow);
/*  319 */         this.player.x = (x0 + 7 + 50 * k + Assets.playerOrigins[(int)Math.abs(Math.floor(this.player.fmy))][Math.round(this.player.fmx)][0].intValue());
/*  320 */         this.player.y = (y0 + 10 + 50 * j + Assets.playerOrigins[(int)Math.abs(Math.floor(this.player.fmy))][Math.round(this.player.fmx)][1].intValue());
/*  321 */         this.player.save(0);
/*  322 */         drawPlayerShadow();
/*  323 */         this.playerSprite.draw(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  328 */     switch (this.animation) {
/*      */       case ANIMATION_OFF:
/*  330 */         this.fmx2 = 0;
/*  331 */         this.fmy2 = 0;
/*      */         break;
/*      */       
/*      */       case HORIZONTAL:
/*  335 */         if (Gdx.graphics.getFrameId() % (6 - this.animationSpeed) == 0L) {
/*  336 */           this.fmx2 = EMath.rotate(this.fmx2, 0, 5 * this.animationLength - 1, 1);
/*      */         }
/*  338 */         this.fmy2 = 0;
/*      */         break;
/*      */       
/*      */       case VERTICAL:
/*  342 */         this.fmx2 = 0;
/*  343 */         if (Gdx.graphics.getFrameId() % (6 - this.animationSpeed) == 0L) {
/*  344 */           this.fmy2 = EMath.rotate(this.fmy2, 0, 5 * this.animationLength - 1, 1);
/*      */         }
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  350 */     this.player.x = 572.0F;
/*  351 */     this.player.y = 120.0F;
/*  352 */     this.player.fmx = ((this.fmx + EMath.floor((this.fmx2 / 5.0F))) % 8);
/*  353 */     this.player.fmy = ((this.fmy + EMath.floor((this.fmy2 / 5.0F))) % getPlayerRows());
/*  354 */     this.player.save(0);
/*  355 */     drawPlayerShadow();
/*  356 */     this.playerSprite.draw(0);
/*      */     
/*  358 */     this.batch.end();
/*      */ 
/*      */     
/*  361 */     this.camera.setToOrtho(true, this.game.gui.screenWidth / 4.0F, this.game.gui.screenHeight / 4.0F);
/*  362 */     this.camera.translate(-this.game.gui.originX / 4.0F, -this.game.gui.originY / 4.0F);
/*  363 */     this.camera.update();
/*      */     
/*  365 */     this.batch.setProjectionMatrix(this.camera.combined);
/*  366 */     this.batch.setColor(16777215, 1.0F);
/*      */     
/*  368 */     this.player.x = 286.0F;
/*  369 */     this.player.y = 130.0F;
/*  370 */     this.player.save(0);
/*  371 */     this.batch.begin();
/*  372 */     drawPlayerShadow();
/*  373 */     this.playerSprite.draw(0);
/*  374 */     this.batch.end();
/*      */ 
/*      */     
/*  377 */     Data d = this.player.data[0];
/*  378 */     this.shapeRenderer.setProjectionMatrix(this.camera.combined);
/*  379 */     this.shapeRenderer.setAutoShapeType(true);
/*  380 */     this.shapeRenderer.begin();
/*  381 */     this.shapeRenderer.line((d.x - 1), d.y, (d.x + 1), d.y);
/*  382 */     this.shapeRenderer.line(d.x, (d.y - 1), d.x, (d.y + 1));
/*  383 */     this.shapeRenderer.end();
/*      */   }
/*      */   
/*      */   private void drawPlayerShadow() {
/*  387 */     if (this.shadows != Shadows.NONE) {
/*  388 */       for (int s = 0; s < ((this.shadows == Shadows.NIGHT) ? 4 : 1); s++) {
/*  389 */         Data d = this.player.data[0];
/*  390 */         if (d.isVisible) {
/*  391 */           Integer[] origin = Assets.playerOrigins[d.fmy][d.fmx];
/*  392 */           float mX = (s == 0 || s == 3) ? 0.65F : -0.65F;
/*  393 */           float mY = (s == 0 || s == 1) ? 0.46F : -0.46F;
/*  394 */           this.batch.setColor(16777215, 0.5F);
/*  395 */           this.batch.draw(Assets.playerShadow[d.fmx][d.fmy][s], (d.x - origin[0].intValue()) + mX * d.z, (d.y - origin[1].intValue() + 5) + mY * d.z);
/*      */         } 
/*      */       } 
/*      */     }
/*  399 */     this.batch.setColor(16777215, 1.0F);
/*      */   }
/*      */   
/*      */   private class StyleLabel
/*      */     extends Button {
/*      */     StyleLabel(int x, int y) {
/*  405 */       setGeometry(x, y, 180, 24);
/*  406 */       setColor(6710886);
/*  407 */       setText(Assets.strings.get("KITS.STYLE"), Font.Align.CENTER, Assets.font10);
/*  408 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class StyleButton
/*      */     extends Button {
/*      */     int kitIndex;
/*      */     
/*      */     StyleButton(int x, int y) {
/*  417 */       this.kitIndex = Assets.kits.indexOf(((Kit)DeveloperPlayer.this.team.kits.get(0)).style);
/*  418 */       setGeometry(x, y, 180, 24);
/*  419 */       setColor(8919109);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  424 */       setText(((Kit)DeveloperPlayer.this.team.kits.get(0)).style.replace('_', ' '), Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  429 */       updateKitStyle(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  434 */       updateKitStyle(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  439 */       updateKitStyle(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  444 */       updateKitStyle(-1);
/*      */     }
/*      */     
/*      */     private void updateKitStyle(int n) {
/*  448 */       this.kitIndex = EMath.rotate(this.kitIndex, 0, Assets.kits.size() - 1, n);
/*  449 */       ((Kit)DeveloperPlayer.this.team.kits.get(0)).style = Assets.kits.get(this.kitIndex);
/*      */       
/*  451 */       DeveloperPlayer.this.reloadPlayer();
/*      */       
/*  453 */       DeveloperPlayer.this.refreshAllWidgets();
/*      */     }
/*      */   }
/*      */   
/*      */   private class KitFieldLabel
/*      */     extends Button {
/*      */     KitFieldLabel(String label, int x, int y, int h) {
/*  460 */       setGeometry(x, y, 80, h);
/*  461 */       setColor(6710886);
/*  462 */       setText(Assets.strings.get(label), Font.Align.CENTER, Assets.font10);
/*  463 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class HashButton
/*      */     extends Button {
/*      */     final Kit.Field field;
/*      */     int colorIndex;
/*      */     
/*      */     HashButton(Kit.Field field, int x, int y) {
/*  473 */       this.field = field;
/*  474 */       setGeometry(x + 82, y, 18, 24);
/*  475 */       setColors(Integer.valueOf(6710886), Integer.valueOf(9407885), Integer.valueOf(4210752));
/*  476 */       setText("#", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  481 */       int color = 0;
/*  482 */       switch (this.field) {
/*      */         case VERTICAL:
/*  484 */           color = ((Kit)DeveloperPlayer.this.team.kits.get(0)).shirt1;
/*      */           break;
/*      */         
/*      */         case null:
/*  488 */           color = ((Kit)DeveloperPlayer.this.team.kits.get(0)).shirt2;
/*      */           break;
/*      */         
/*      */         case null:
/*  492 */           color = ((Kit)DeveloperPlayer.this.team.kits.get(0)).shirt3;
/*      */           break;
/*      */         
/*      */         case ANIMATION_OFF:
/*  496 */           color = ((Kit)DeveloperPlayer.this.team.kits.get(0)).shorts;
/*      */           break;
/*      */         
/*      */         case HORIZONTAL:
/*  500 */           color = ((Kit)DeveloperPlayer.this.team.kits.get(0)).socks;
/*      */           break;
/*      */       } 
/*  503 */       setColor(color);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  508 */       updateColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  513 */       updateColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  518 */       updateColor(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  523 */       updateColor(-1);
/*      */     }
/*      */     
/*      */     private void updateColor(int n) {
/*  527 */       this.colorIndex = EMath.rotate(this.colorIndex, 0, Kit.colors.length - 1, n);
/*  528 */       int color = Kit.colors[this.colorIndex];
/*  529 */       switch (this.field) {
/*      */         case VERTICAL:
/*  531 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).shirt1 = color;
/*      */           break;
/*      */         
/*      */         case null:
/*  535 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).shirt2 = color;
/*      */           break;
/*      */         
/*      */         case null:
/*  539 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).shirt3 = color;
/*      */           break;
/*      */         
/*      */         case ANIMATION_OFF:
/*  543 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).shorts = color;
/*      */           break;
/*      */         
/*      */         case HORIZONTAL:
/*  547 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).socks = color;
/*      */           break;
/*      */       } 
/*  550 */       DeveloperPlayer.this.reloadPlayer();
/*  551 */       DeveloperPlayer.this.refreshAllWidgets();
/*      */     }
/*      */   }
/*      */   
/*      */   private class KitColorButton
/*      */     extends InputButton {
/*      */     final Kit.Field field;
/*      */     
/*      */     KitColorButton(Kit.Field field, int x, int y) {
/*  560 */       this.field = field;
/*  561 */       setGeometry(x + 102, y, 78, 24);
/*  562 */       setText("", Font.Align.CENTER, Assets.font10);
/*  563 */       setEntryLimit(6);
/*  564 */       setInputFilter("[A-F0-9]");
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  569 */       int color = getColor();
/*  570 */       setText(GLColor.toHexString(color).substring(1).toUpperCase());
/*  571 */       setColor(color);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onChanged() {
/*  576 */       int color = (this.text.length() == 0) ? 0 : GLColor.valueOf("#" + this.text);
/*  577 */       switch (this.field) {
/*      */         case VERTICAL:
/*  579 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).shirt1 = color;
/*      */           break;
/*      */         
/*      */         case null:
/*  583 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).shirt2 = color;
/*      */           break;
/*      */         
/*      */         case null:
/*  587 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).shirt3 = color;
/*      */           break;
/*      */         
/*      */         case ANIMATION_OFF:
/*  591 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).shorts = color;
/*      */           break;
/*      */         
/*      */         case HORIZONTAL:
/*  595 */           ((Kit)DeveloperPlayer.this.team.kits.get(0)).socks = color;
/*      */           break;
/*      */       } 
/*  598 */       DeveloperPlayer.this.reloadPlayer();
/*  599 */       DeveloperPlayer.this.refreshAllWidgets();
/*      */     }
/*      */     
/*      */     private int getColor() {
/*  603 */       Kit kit = DeveloperPlayer.this.team.kits.get(0);
/*  604 */       switch (this.field) {
/*      */         case VERTICAL:
/*  606 */           return kit.shirt1;
/*      */         
/*      */         case null:
/*  609 */           return kit.shirt2;
/*      */         
/*      */         case null:
/*  612 */           return kit.shirt3;
/*      */         
/*      */         case ANIMATION_OFF:
/*  615 */           return kit.shorts;
/*      */         
/*      */         case HORIZONTAL:
/*  618 */           return kit.socks;
/*      */       } 
/*      */       
/*  621 */       return 0;
/*      */     }
/*      */   }
/*      */   
/*      */   private class SkinColorButton
/*      */     extends Button
/*      */   {
/*      */     SkinColorButton(int x, int y) {
/*  629 */       setGeometry(x, y, 28, 24);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  634 */       Color3 skinColor = Skin.colors[DeveloperPlayer.this.player.skinColor.ordinal()];
/*  635 */       setColors(Integer.valueOf(skinColor.color2), Integer.valueOf(skinColor.color1), Integer.valueOf(skinColor.color3));
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  640 */       updateSkinColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  645 */       updateSkinColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  650 */       updateSkinColor(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  655 */       updateSkinColor(-1);
/*      */     }
/*      */     
/*      */     private void updateSkinColor(int n) {
/*  659 */       DeveloperPlayer.this.player.skinColor = Skin.Color.values()[EMath.rotate(DeveloperPlayer.this.player.skinColor.ordinal(), Skin.Color.PINK.ordinal(), Skin.Color.RED.ordinal(), n)];
/*      */       
/*  661 */       setDirty(true);
/*      */       
/*  663 */       DeveloperPlayer.this.reloadPlayer();
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairColorButton
/*      */     extends Button {
/*      */     HairColorButton(int x, int y) {
/*  670 */       setGeometry(x, y, 28, 24);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  675 */       Color3 hairColor = Hair.colors[DeveloperPlayer.this.player.hairColor.ordinal()];
/*  676 */       setColors(Integer.valueOf(hairColor.color2), Integer.valueOf(hairColor.color1), Integer.valueOf(hairColor.color3));
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  681 */       updateHairColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  686 */       updateHairColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  691 */       updateHairColor(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  696 */       updateHairColor(-1);
/*      */     }
/*      */     
/*      */     private void updateHairColor(int n) {
/*  700 */       int color = DeveloperPlayer.this.player.hairColor.ordinal();
/*  701 */       color = EMath.rotate(color, Hair.Color.BLACK.ordinal(), Hair.Color.PUNK_BLOND.ordinal(), n);
/*  702 */       DeveloperPlayer.this.player.hairColor = Hair.Color.values()[color];
/*      */       
/*  704 */       setDirty(true);
/*      */       
/*  706 */       DeveloperPlayer.this.reloadHair();
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairStyleButton
/*      */     extends Button {
/*      */     HairStyleButton(int x, int y) {
/*  713 */       setGeometry(x, y, 115, 24);
/*  714 */       setColors(Integer.valueOf(3181627), Integer.valueOf(4898904), Integer.valueOf(2054438));
/*  715 */       setText("", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  720 */       setText(DeveloperPlayer.this.player.hairStyle.replace('_', ' '));
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  725 */       updateHairStyle(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  730 */       updateHairStyle(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  735 */       updateHairStyle(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  740 */       updateHairStyle(-1);
/*      */     }
/*      */     
/*      */     private void updateHairStyle(int n) {
/*  744 */       int i = Assets.hairStyles.indexOf(DeveloperPlayer.this.player.hairStyle);
/*  745 */       if (i == -1) {
/*  746 */         i = 0;
/*      */       } else {
/*  748 */         i = EMath.rotate(i, 0, Assets.hairStyles.size() - 1, n);
/*      */       } 
/*  750 */       DeveloperPlayer.this.player.hairStyle = Assets.hairStyles.get(i);
/*      */       
/*  752 */       setDirty(true);
/*      */       
/*  754 */       DeveloperPlayer.this.reloadHair();
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairLabel
/*      */     extends Button {
/*      */     HairLabel(int x, int y) {
/*  761 */       setGeometry(x, y, 64, 24);
/*  762 */       setText("HAIR", Font.Align.CENTER, Assets.font10);
/*  763 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairFrameButton extends Button {
/*      */     final int xIncrement;
/*      */     final int yIncrement;
/*      */     
/*      */     HairFrameButton(int x, int y, int xIncrement, int yIncrement) {
/*  772 */       this.xIncrement = xIncrement;
/*  773 */       this.yIncrement = yIncrement;
/*  774 */       setGeometry(x, y, 30, 30);
/*  775 */       setColor(7688315);
/*  776 */       setText("", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  781 */       setText(((this.xIncrement != 0) ? Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][0] : Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][1]).intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  786 */       updateFrame(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  791 */       updateFrame(-1);
/*      */     }
/*      */     
/*      */     private void updateFrame(int sign) {
/*  795 */       Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][0] = Integer.valueOf(EMath.rotate(Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][0].intValue(), 0, 7, sign * this.xIncrement));
/*  796 */       Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][1] = Integer.valueOf(EMath.rotate(Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][1].intValue(), 0, 9, sign * this.yIncrement));
/*  797 */       setDirty(true);
/*  798 */       DeveloperPlayer.this.hairMapPosition.setDirty(true);
/*  799 */       DeveloperPlayer.this.saveHairMapButton.setDirty(true);
/*  800 */       DeveloperPlayer.this.resetHairMapButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class MoveHairButton extends Button {
/*      */     final int xDir;
/*      */     final int yDir;
/*      */     
/*      */     MoveHairButton(int x, int y, int xDir, int yDir) {
/*  809 */       this.xDir = xDir;
/*  810 */       this.yDir = yDir;
/*  811 */       setGeometry(x, y, 30, 30);
/*  812 */       setColor(2795336);
/*  813 */       int fx = ((yDir != 0) ? 2 : 0) + ((xDir < 0 || yDir < 0) ? 4 : 0);
/*  814 */       this.textureRegion = Assets.wind[fx][0];
/*  815 */       setImagePosition(-3, -3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  820 */       updateHair();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  825 */       updateHair();
/*      */     }
/*      */     
/*      */     private void updateHair() {
/*  829 */       Integer[] arrayOfInteger = Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx]; arrayOfInteger[2] = Integer.valueOf(arrayOfInteger[2].intValue() + this.xDir); Integer.valueOf(arrayOfInteger[2].intValue() + this.xDir);
/*  830 */       arrayOfInteger = Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx]; arrayOfInteger[3] = Integer.valueOf(arrayOfInteger[3].intValue() + this.yDir); Integer.valueOf(arrayOfInteger[3].intValue() + this.yDir);
/*  831 */       DeveloperPlayer.this.hairMapPosition.setDirty(true);
/*  832 */       DeveloperPlayer.this.saveHairMapButton.setDirty(true);
/*  833 */       DeveloperPlayer.this.resetHairMapButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairPosition
/*      */     extends Button {
/*      */     HairPosition(int x, int y) {
/*  840 */       setGeometry(x, y, 72, 30);
/*  841 */       setColor(6710886);
/*  842 */       setText("", Font.Align.CENTER, Assets.font10);
/*  843 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  848 */       setText(Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][2] + ", " + Assets.playerHairMap[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][3]);
/*      */     }
/*      */   }
/*      */   
/*      */   private class SaveHairMapButton
/*      */     extends Button {
/*      */     SaveHairMapButton(int x, int y) {
/*  855 */       setGeometry(x, y, 71, 30);
/*  856 */       setText("SAVE", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  861 */       String current = Assets.json.toJson(Assets.playerHairMap);
/*  862 */       boolean modified = !current.equals(DeveloperPlayer.this.savedHairMap);
/*  863 */       setColor(modified ? 13369344 : 6710886);
/*  864 */       setActive(modified);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  869 */       Assets.savePlayerHairMap();
/*  870 */       DeveloperPlayer.this.savedHairMap = Assets.json.toJson(Assets.playerHairMap);
/*  871 */       setDirty(true);
/*  872 */       DeveloperPlayer.this.resetHairMapButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ResetHairMapButton
/*      */     extends Button
/*      */   {
/*      */     ResetHairMapButton(int x, int y) {
/*  880 */       setGeometry(x, y, 71, 30);
/*  881 */       setText("RESET", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  886 */       String current = Assets.json.toJson(Assets.playerHairMap);
/*  887 */       boolean modified = !current.equals(DeveloperPlayer.this.savedHairMap);
/*  888 */       setColor(modified ? 13421568 : 6710886);
/*  889 */       setActive(modified);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  894 */       Assets.loadPlayerHairMap();
/*  895 */       setDirty(true);
/*  896 */       DeveloperPlayer.this.saveHairMapButton.setDirty(true);
/*  897 */       DeveloperPlayer.this.hairMapPosition.setDirty(true);
/*  898 */       DeveloperPlayer.this.hairFrameXButton.setDirty(true);
/*  899 */       DeveloperPlayer.this.hairFrameYButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ColumnButton
/*      */     extends Button
/*      */   {
/*      */     ColumnButton(int x, int y) {
/*  907 */       setGeometry(x, y, 88, 24);
/*  908 */       setColors(Integer.valueOf(3181627), Integer.valueOf(4898904), Integer.valueOf(2054438));
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  913 */       setText("COL: " + DeveloperPlayer.this.fmx, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  918 */       updateColumn(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  923 */       updateColumn(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  928 */       updateColumn(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  933 */       updateColumn(-1);
/*      */     }
/*      */     
/*      */     private void updateColumn(int n) {
/*  937 */       DeveloperPlayer.this.fmx = EMath.rotate(DeveloperPlayer.this.fmx, 0, 7, n);
/*  938 */       setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class RowButton
/*      */     extends Button {
/*      */     RowButton(int x, int y) {
/*  945 */       setGeometry(x + 4 + 88, y, 88, 24);
/*  946 */       setColors(Integer.valueOf(3181627), Integer.valueOf(4898904), Integer.valueOf(2054438));
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  951 */       setText("ROW: " + DeveloperPlayer.this.fmy, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  956 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  961 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  966 */       updateRow(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  971 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/*  975 */       DeveloperPlayer.this.fmy = EMath.slide(DeveloperPlayer.this.fmy, 0, DeveloperPlayer.this.getPlayerRows() - 1, n);
/*  976 */       DeveloperPlayer.this.refreshAllWidgets();
/*      */     }
/*      */   }
/*      */   
/*      */   private class AnimationTypeButton
/*      */     extends Button {
/*      */     AnimationTypeButton(int x, int y) {
/*  983 */       setGeometry(x, y, 180, 24);
/*  984 */       setColor(2837089);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  989 */       setText(DeveloperPlayer.this.animation.toString().replace('_', ' '), Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  994 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  999 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/* 1004 */       updateRow(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/* 1009 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/* 1013 */       DeveloperPlayer.this.animation = DeveloperPlayer.Animation.values()[EMath.rotate(DeveloperPlayer.this.animation, DeveloperPlayer.Animation.ANIMATION_OFF, DeveloperPlayer.Animation.VERTICAL, n)];
/* 1014 */       setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class AnimationLengthButton
/*      */     extends Button {
/*      */     AnimationLengthButton(int x, int y) {
/* 1021 */       setGeometry(x, y, 180, 24);
/* 1022 */       setColor(2837089);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1027 */       setText("LENGTH: " + DeveloperPlayer.this.animationLength, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1032 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/* 1037 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/* 1042 */       updateRow(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/* 1047 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/* 1051 */       DeveloperPlayer.this.animationLength = EMath.slide(DeveloperPlayer.this.animationLength, 2, 8, n);
/* 1052 */       setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class AnimationSpeedButton
/*      */     extends Button {
/*      */     AnimationSpeedButton(int x, int y) {
/* 1059 */       setGeometry(x, y, 180, 24);
/* 1060 */       setColor(2837089);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1065 */       setText("SPEED: " + DeveloperPlayer.this.animationSpeed, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1070 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/* 1075 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/* 1080 */       updateRow(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/* 1085 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/* 1089 */       DeveloperPlayer.this.animationSpeed = EMath.slide(DeveloperPlayer.this.animationSpeed, 1, 5, n);
/* 1090 */       setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ShadowsButton
/*      */     extends Button {
/*      */     ShadowsButton(int x, int y) {
/* 1097 */       setGeometry(x, y, 180, 24);
/* 1098 */       setColor(9394434);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1103 */       setText("SHADOWS: " + DeveloperPlayer.this.shadows, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1108 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/* 1113 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/* 1117 */       DeveloperPlayer.this.shadows = (DeveloperPlayer.Shadows)EMath.rotate(DeveloperPlayer.this.shadows, DeveloperPlayer.Shadows.class, n);
/* 1118 */       setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class OriginLabel
/*      */     extends Button {
/*      */     OriginLabel(int x, int y) {
/* 1125 */       setGeometry(x, y, 64, 24);
/* 1126 */       setText("ORIGIN", Font.Align.CENTER, Assets.font10);
/* 1127 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class MoveOriginButton extends Button {
/*      */     final int xDir;
/*      */     final int yDir;
/*      */     
/*      */     MoveOriginButton(int x, int y, int xDir, int yDir) {
/* 1136 */       this.xDir = xDir;
/* 1137 */       this.yDir = yDir;
/* 1138 */       setGeometry(x, y, 30, 30);
/* 1139 */       setColor(2795336);
/* 1140 */       int fx = ((yDir != 0) ? 2 : 0) + ((xDir < 0 || yDir < 0) ? 4 : 0);
/* 1141 */       this.textureRegion = Assets.wind[fx][0];
/* 1142 */       setImagePosition(-3, -3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1147 */       updateOrigin();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/* 1152 */       updateOrigin();
/*      */     }
/*      */     
/*      */     private void updateOrigin() {
/* 1156 */       Integer[] arrayOfInteger = Assets.playerOrigins[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx]; arrayOfInteger[0] = Integer.valueOf(arrayOfInteger[0].intValue() + this.xDir); Integer.valueOf(arrayOfInteger[0].intValue() + this.xDir);
/* 1157 */       arrayOfInteger = Assets.playerOrigins[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx]; arrayOfInteger[1] = Integer.valueOf(arrayOfInteger[1].intValue() + this.yDir); Integer.valueOf(arrayOfInteger[1].intValue() + this.yDir);
/* 1158 */       DeveloperPlayer.this.originValue.setDirty(true);
/* 1159 */       DeveloperPlayer.this.saveOriginsButton.setDirty(true);
/* 1160 */       DeveloperPlayer.this.resetOriginsButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class OriginValue
/*      */     extends Button {
/*      */     OriginValue(int x, int y) {
/* 1167 */       setGeometry(x, y, 72, 30);
/* 1168 */       setColor(6710886);
/* 1169 */       setText("", Font.Align.CENTER, Assets.font10);
/* 1170 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1175 */       setText(Assets.playerOrigins[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][0] + ", " + Assets.playerOrigins[DeveloperPlayer.this.fmy][DeveloperPlayer.this.fmx][1]);
/*      */     }
/*      */   }
/*      */   
/*      */   private class saveOriginsButton
/*      */     extends Button {
/*      */     saveOriginsButton(int x, int y) {
/* 1182 */       setGeometry(x, y, 71, 30);
/* 1183 */       setText("SAVE", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1188 */       String current = Assets.json.toJson(Assets.playerOrigins);
/* 1189 */       boolean modified = !current.equals(DeveloperPlayer.this.savedOrigins);
/* 1190 */       setColor(modified ? 13369344 : 6710886);
/* 1191 */       setActive(modified);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1196 */       Assets.savePlayerOrigins();
/* 1197 */       DeveloperPlayer.this.savedOrigins = Assets.json.toJson(Assets.playerOrigins);
/* 1198 */       setDirty(true);
/* 1199 */       DeveloperPlayer.this.resetOriginsButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ResetOriginsButton
/*      */     extends Button
/*      */   {
/*      */     ResetOriginsButton(int x, int y) {
/* 1207 */       setGeometry(x, y, 71, 30);
/* 1208 */       setText("RESET", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1213 */       String current = Assets.json.toJson(Assets.playerOrigins);
/* 1214 */       boolean modified = !current.equals(DeveloperPlayer.this.savedOrigins);
/* 1215 */       setColor(modified ? 13421568 : 6710886);
/* 1216 */       setActive(modified);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1221 */       Assets.loadPlayerOrigins();
/* 1222 */       setDirty(true);
/* 1223 */       DeveloperPlayer.this.saveOriginsButton.setDirty(true);
/* 1224 */       DeveloperPlayer.this.originValue.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class FrameButton
/*      */     extends Button {
/*      */     private final int column;
/*      */     private final int row;
/*      */     
/*      */     FrameButton(int column, int row) {
/* 1234 */       this.column = column;
/* 1235 */       this.row = row;
/* 1236 */       int x0 = 238;
/* 1237 */       int y0 = 8;
/* 1238 */       setGeometry(x0 + 100 * column, y0 + 100 * row, 100, 100);
/* 1239 */       setColors(Integer.valueOf(2795336), Integer.valueOf(2795336), Integer.valueOf(2795336));
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/* 1244 */       boolean selected = (this.row == DeveloperPlayer.this.fmy + DeveloperPlayer.this.firstRow && this.column == DeveloperPlayer.this.fmx);
/* 1245 */       setColors(null, selected ? Integer.valueOf(16711680) : null, selected ? Integer.valueOf(16711680) : null);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1250 */       updateSelected();
/*      */     }
/*      */     
/*      */     private void updateSelected() {
/* 1254 */       DeveloperPlayer.this.fmx = this.column;
/* 1255 */       DeveloperPlayer.this.fmy = this.row - DeveloperPlayer.this.firstRow;
/* 1256 */       DeveloperPlayer.this.refreshAllWidgets();
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExitButton
/*      */     extends Button {
/*      */     ExitButton(int x) {
/* 1263 */       setGeometry(x, 680, 180, 32);
/* 1264 */       setColor(13124096);
/* 1265 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/* 1270 */       DeveloperPlayer.this.hasExited = true;
/* 1271 */       Assets.unloadPlayer(DeveloperPlayer.this.player);
/* 1272 */       Gdx.input.setInputProcessor(null);
/* 1273 */       DeveloperPlayer.this.game.setScreen((Screen)new DeveloperTools(DeveloperPlayer.this.game));
/*      */     }
/*      */   }
/*      */   
/*      */   private void reloadPlayer() {
/* 1278 */     Assets.unloadPlayer(this.player);
/* 1279 */     Assets.loadPlayer(this.player, this.team.kits.get(this.team.kitIndex));
/*      */   }
/*      */   
/*      */   private void reloadHair() {
/* 1283 */     Assets.unloadHair(this.player);
/* 1284 */     Assets.loadHair(this.player);
/*      */   }
/*      */   
/*      */   private int getPlayerRows() {
/* 1288 */     return (this.player.role == Player.Role.GOALKEEPER) ? 19 : 16;
/*      */   }
/*      */   
/*      */   private class InputProcessor extends InputAdapter {
/*      */     private InputProcessor() {}
/*      */     
/*      */     public boolean scrolled(int n) {
/* 1295 */       DeveloperPlayer.this.firstRow = EMath.slide(DeveloperPlayer.this.firstRow, 7 - DeveloperPlayer.this.getPlayerRows(), 0, -n);
/* 1296 */       DeveloperPlayer.this.refreshAllWidgets();
/* 1297 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DeveloperPlayer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */