/*      */ package com.ygames.ysoccer.screens;
/*      */ 
/*      */ import com.badlogic.gdx.Gdx;
/*      */ import com.badlogic.gdx.InputAdapter;
/*      */ import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
/*      */ import com.ygames.ysoccer.framework.Assets;
/*      */ import com.ygames.ysoccer.framework.Color3;
/*      */ import com.ygames.ysoccer.framework.EMath;
/*      */ import com.ygames.ysoccer.framework.Font;
/*      */ import com.ygames.ysoccer.framework.GLGame;
/*      */ import com.ygames.ysoccer.gui.Button;
/*      */ import com.ygames.ysoccer.gui.Widget;
/*      */ import com.ygames.ysoccer.match.Data;
/*      */ import com.ygames.ysoccer.match.Hair;
/*      */ import com.ygames.ysoccer.match.Player;
/*      */ import com.ygames.ysoccer.match.PlayerSprite;
/*      */ import com.ygames.ysoccer.match.Skin;
/*      */ import com.ygames.ysoccer.match.Team;
/*      */ 
/*      */ class DeveloperKeeper extends GLScreen {
/*      */   private Animation animation;
/*      */   private int animationLength;
/*      */   private int animationSpeed;
/*      */   private Shadows shadows;
/*      */   
/*   26 */   private enum Animation { ANIMATION_OFF, HORIZONTAL, VERTICAL; }
/*      */   
/*   28 */   private enum Shadows { NONE, DAY, NIGHT; }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   36 */   private final int displayedRows = 7;
/*      */   private final Player player;
/*      */   private final PlayerSprite playerSprite;
/*      */   private int fmx;
/*      */   private int fmy;
/*   41 */   private int fmx2 = 0;
/*   42 */   private int fmy2 = 0;
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
/*      */   DeveloperKeeper(GLGame game) {
/*   61 */     super(game);
/*      */     
/*   63 */     Gdx.input.setInputProcessor((com.badlogic.gdx.InputProcessor)new InputProcessor());
/*      */     
/*   65 */     this.background = new Texture("images/backgrounds/menu_edit_team.jpg");
/*      */     
/*   67 */     this.animation = Animation.ANIMATION_OFF;
/*   68 */     this.animationLength = 8;
/*   69 */     this.animationSpeed = 3;
/*      */     
/*   71 */     this.shadows = Shadows.DAY;
/*      */ 
/*      */ 
/*      */     
/*   75 */     Team team = new Team();
/*   76 */     this.player = new Player();
/*   77 */     this.fmx = 0;
/*   78 */     this.fmy = 0;
/*   79 */     this.firstRow = 0;
/*   80 */     this.player.team = team;
/*   81 */     this.player.role = Player.Role.GOALKEEPER;
/*   82 */     this.player.isVisible = true;
/*   83 */     this.player.data[0] = new Data();
/*   84 */     this.player.save(0);
/*   85 */     this.player.skinColor = Skin.Color.PINK;
/*   86 */     this.player.hairColor = Hair.Color.BLACK;
/*   87 */     this.player.hairStyle = "SMOOTH_A";
/*   88 */     reloadKeeper();
/*   89 */     reloadHair();
/*   90 */     this.playerSprite = new PlayerSprite(game.glGraphics, this.player);
/*   91 */     this.savedHairMap = Assets.json.toJson(Assets.keeperHairMap);
/*   92 */     this.savedOrigins = Assets.json.toJson(Assets.keeperOrigins);
/*      */     
/*   94 */     int x = 10;
/*   95 */     int y = 20;
/*      */     
/*   97 */     SkinColorButton skinColorButton = new SkinColorButton(x, y);
/*   98 */     this.widgets.add(skinColorButton);
/*      */     
/*  100 */     HairColorButton hairColorButton = new HairColorButton(x + 30, y);
/*  101 */     this.widgets.add(hairColorButton);
/*      */     
/*  103 */     HairStyleButton hairStyleButton = new HairStyleButton(x + 60, y);
/*  104 */     this.widgets.add(hairStyleButton);
/*      */     
/*  106 */     y += 40;
/*  107 */     HairLabel hairLabel = new HairLabel(x, y);
/*  108 */     this.widgets.add(hairLabel);
/*      */     
/*  110 */     MoveHairButton moveHairButton = new MoveHairButton(x + 75, y, 0, -1);
/*  111 */     this.widgets.add(moveHairButton);
/*      */     
/*  113 */     this.hairFrameXButton = (Widget)new HairFrameButton(x + 118, y, 1, 0);
/*  114 */     this.widgets.add(this.hairFrameXButton);
/*      */     
/*  116 */     this.hairFrameYButton = (Widget)new HairFrameButton(x + 150, y, 0, 1);
/*  117 */     this.widgets.add(this.hairFrameYButton);
/*      */     
/*  119 */     y += 34;
/*  120 */     moveHairButton = new MoveHairButton(x + 20, y, -1, 0);
/*  121 */     this.widgets.add(moveHairButton);
/*      */     
/*  123 */     this.hairMapPosition = (Widget)new HairPosition(x + 54, y);
/*  124 */     this.widgets.add(this.hairMapPosition);
/*      */     
/*  126 */     moveHairButton = new MoveHairButton(x + 130, y, 1, 0);
/*  127 */     this.widgets.add(moveHairButton);
/*      */     
/*  129 */     y += 34;
/*  130 */     moveHairButton = new MoveHairButton(x + 75, y, 0, 1);
/*  131 */     this.widgets.add(moveHairButton);
/*      */     
/*  133 */     this.saveHairMapButton = (Widget)new SaveHairMapButton(x, y);
/*  134 */     this.widgets.add(this.saveHairMapButton);
/*      */     
/*  136 */     this.resetHairMapButton = (Widget)new ResetHairMapButton(x + 109, y);
/*  137 */     this.widgets.add(this.resetHairMapButton);
/*      */     
/*  139 */     y += 40;
/*  140 */     ColumnButton columnButton = new ColumnButton(x, y);
/*  141 */     this.widgets.add(columnButton);
/*      */     
/*  143 */     RowButton rowButton = new RowButton(x, y);
/*  144 */     this.widgets.add(rowButton);
/*      */     
/*  146 */     y += 40;
/*  147 */     AnimationTypeButton animationTypeButton = new AnimationTypeButton(x, y);
/*  148 */     this.widgets.add(animationTypeButton);
/*      */     
/*  150 */     y += 25;
/*  151 */     AnimationLengthButton animationLengthButton = new AnimationLengthButton(x, y);
/*  152 */     this.widgets.add(animationLengthButton);
/*      */     
/*  154 */     y += 25;
/*  155 */     AnimationSpeedButton animationSpeedButton = new AnimationSpeedButton(x, y);
/*  156 */     this.widgets.add(animationSpeedButton);
/*      */     
/*  158 */     y += 40;
/*  159 */     OriginLabel originLabel = new OriginLabel(x, y);
/*  160 */     this.widgets.add(originLabel);
/*      */     
/*  162 */     MoveOriginButton moveOriginButton = new MoveOriginButton(x + 75, y, 0, -1);
/*  163 */     this.widgets.add(moveOriginButton);
/*      */     
/*  165 */     y += 34;
/*  166 */     moveOriginButton = new MoveOriginButton(x + 20, y, -1, 0);
/*  167 */     this.widgets.add(moveOriginButton);
/*      */     
/*  169 */     this.originValue = (Widget)new OriginValue(x + 54, y);
/*  170 */     this.widgets.add(this.originValue);
/*      */     
/*  172 */     moveOriginButton = new MoveOriginButton(x + 130, y, 1, 0);
/*  173 */     this.widgets.add(moveOriginButton);
/*      */     
/*  175 */     y += 34;
/*  176 */     moveOriginButton = new MoveOriginButton(x + 75, y, 0, 1);
/*  177 */     this.widgets.add(moveOriginButton);
/*      */     
/*  179 */     this.saveOriginsButton = (Widget)new saveOriginsButton(x, y);
/*  180 */     this.widgets.add(this.saveOriginsButton);
/*      */     
/*  182 */     this.resetOriginsButton = (Widget)new ResetOriginsButton(x + 109, y);
/*  183 */     this.widgets.add(this.resetOriginsButton);
/*      */     
/*  185 */     y += 46;
/*  186 */     ShadowsButton shadowsButton = new ShadowsButton(x, y);
/*  187 */     this.widgets.add(shadowsButton);
/*      */     
/*  189 */     for (int c = 0; c < 8; c++) {
/*  190 */       for (int r = 0; r < 7; r++) {
/*  191 */         FrameButton frameButton = new FrameButton(c, r);
/*  192 */         this.widgets.add(frameButton);
/*      */       } 
/*      */     } 
/*      */     
/*  196 */     ExitButton exitButton = new ExitButton(x);
/*  197 */     this.widgets.add(exitButton);
/*      */     
/*  199 */     setSelectedWidget((Widget)exitButton);
/*      */   }
/*      */   
/*      */   public void render(float delta) {
/*      */     int i;
/*  204 */     super.render(delta);
/*      */ 
/*      */     
/*  207 */     if (this.hasExited)
/*      */       return; 
/*  209 */     this.camera.setToOrtho(true, this.game.gui.screenWidth / 2.0F, this.game.gui.screenHeight / 2.0F);
/*  210 */     this.camera.translate(-this.game.gui.originX / 2.0F, -this.game.gui.originY / 2.0F);
/*  211 */     this.camera.update();
/*      */     
/*  213 */     this.batch.setProjectionMatrix(this.camera.combined);
/*  214 */     this.batch.setColor(16777215, 1.0F);
/*      */     
/*  216 */     this.shapeRenderer.setProjectionMatrix(this.camera.combined);
/*  217 */     this.shapeRenderer.setColor(2795336, 1.0F);
/*  218 */     this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
/*  219 */     this.shapeRenderer.rect(100.0F, 0.0F, 540.0F, 360.0F);
/*      */     
/*  221 */     int x0 = 120;
/*  222 */     int y0 = 5;
/*      */ 
/*      */     
/*  225 */     this.shapeRenderer.setColor(4473924, 1.0F);
/*  226 */     this.shapeRenderer.rect((x0 - 18), y0 - 350.0F * this.firstRow / getPlayerRows(), 4.0F, 2450.0F / getPlayerRows());
/*      */ 
/*      */     
/*  229 */     this.shapeRenderer.setColor(16579584, 1.0F);
/*  230 */     switch (this.animation) {
/*      */       case ANIMATION_OFF:
/*  232 */         this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow)));
/*  233 */         this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*  234 */         this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*  235 */         this.shapeRenderer.line((x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*      */         break;
/*      */       case HORIZONTAL:
/*  238 */         for (i = 0; i < this.animationLength; i++) {
/*  239 */           int x = (this.fmx + i) % 8;
/*  240 */           this.shapeRenderer.line((x0 - 1 + 50 * x), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * (x + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow)));
/*  241 */           this.shapeRenderer.line((x0 - 1 + 50 * x), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50), (x0 - 1 + 50 * (x + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*  242 */           this.shapeRenderer.line((x0 - 1 + 50 * x), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * x), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*  243 */           this.shapeRenderer.line((x0 - 1 + 50 * (x + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow)), (x0 - 1 + 50 * (x + 1)), (y0 - 1 + 50 * (this.fmy + this.firstRow) + 50));
/*      */         } 
/*      */         break;
/*      */       case VERTICAL:
/*  247 */         for (i = 0; i < this.animationLength; i++) {
/*  248 */           int y = (this.fmy + this.firstRow + i) % getPlayerRows();
/*  249 */           this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * y), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * y));
/*  250 */           this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (y + 1)), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (y + 1)));
/*  251 */           this.shapeRenderer.line((x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * y), (x0 - 1 + 50 * this.fmx), (y0 - 1 + 50 * (y + 1)));
/*  252 */           this.shapeRenderer.line((x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * y), (x0 - 1 + 50 * (this.fmx + 1)), (y0 - 1 + 50 * (y + 1)));
/*      */         } 
/*      */         break;
/*      */     } 
/*  256 */     this.shapeRenderer.end();
/*      */ 
/*      */     
/*  259 */     this.batch.begin();
/*  260 */     for (int j = 0; j < 7; j++) {
/*  261 */       for (int k = 0; k < 8; k++) {
/*  262 */         this.player.fmx = k;
/*  263 */         this.player.fmy = (j - this.firstRow);
/*  264 */         this.player.x = (x0 + 50 * k + Assets.keeperOrigins[(int)Math.abs(Math.floor(this.player.fmy))][Math.round(this.player.fmx)][0].intValue());
/*  265 */         this.player.y = (y0 + 2 + 50 * j + Assets.keeperOrigins[(int)Math.abs(Math.floor(this.player.fmy))][Math.round(this.player.fmx)][1].intValue());
/*  266 */         this.player.save(0);
/*  267 */         drawKeeperShadow();
/*  268 */         this.playerSprite.draw(0);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  273 */     switch (this.animation) {
/*      */       case ANIMATION_OFF:
/*  275 */         this.fmx2 = 0;
/*  276 */         this.fmy2 = 0;
/*      */         break;
/*      */       
/*      */       case HORIZONTAL:
/*  280 */         if (Gdx.graphics.getFrameId() % (6 - this.animationSpeed) == 0L) {
/*  281 */           this.fmx2 = EMath.rotate(this.fmx2, 0, 5 * this.animationLength - 1, 1);
/*      */         }
/*  283 */         this.fmy2 = 0;
/*      */         break;
/*      */       
/*      */       case VERTICAL:
/*  287 */         this.fmx2 = 0;
/*  288 */         if (Gdx.graphics.getFrameId() % (6 - this.animationSpeed) == 0L) {
/*  289 */           this.fmy2 = EMath.rotate(this.fmy2, 0, 5 * this.animationLength - 1, 1);
/*      */         }
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  295 */     this.player.x = 572.0F;
/*  296 */     this.player.y = 120.0F;
/*  297 */     this.player.fmx = ((this.fmx + EMath.floor((this.fmx2 / 5.0F))) % 8);
/*  298 */     this.player.fmy = ((this.fmy + EMath.floor((this.fmy2 / 5.0F))) % getPlayerRows());
/*  299 */     this.player.save(0);
/*  300 */     drawKeeperShadow();
/*  301 */     this.playerSprite.draw(0);
/*      */     
/*  303 */     this.batch.end();
/*      */ 
/*      */     
/*  306 */     this.camera.setToOrtho(true, this.game.gui.screenWidth / 4.0F, this.game.gui.screenHeight / 4.0F);
/*  307 */     this.camera.translate(-this.game.gui.originX / 4.0F, -this.game.gui.originY / 4.0F);
/*  308 */     this.camera.update();
/*      */     
/*  310 */     this.batch.setProjectionMatrix(this.camera.combined);
/*  311 */     this.batch.setColor(16777215, 1.0F);
/*      */     
/*  313 */     this.player.x = 286.0F;
/*  314 */     this.player.y = 130.0F;
/*  315 */     this.player.save(0);
/*  316 */     this.batch.begin();
/*  317 */     drawKeeperShadow();
/*  318 */     this.playerSprite.draw(0);
/*  319 */     this.batch.end();
/*      */ 
/*      */     
/*  322 */     Data d = this.player.data[0];
/*  323 */     this.shapeRenderer.setProjectionMatrix(this.camera.combined);
/*  324 */     this.shapeRenderer.setAutoShapeType(true);
/*  325 */     this.shapeRenderer.begin();
/*  326 */     this.shapeRenderer.line((d.x - 1), d.y, (d.x + 1), d.y);
/*  327 */     this.shapeRenderer.line(d.x, (d.y - 1), d.x, (d.y + 1));
/*  328 */     this.shapeRenderer.end();
/*      */   }
/*      */   
/*      */   private void drawKeeperShadow() {
/*  332 */     if (this.shadows != Shadows.NONE) {
/*  333 */       for (int s = 0; s < ((this.shadows == Shadows.NIGHT) ? 4 : 1); s++) {
/*  334 */         Data d = this.player.data[0];
/*  335 */         if (d.isVisible) {
/*  336 */           Integer[] origin = Assets.keeperOrigins[d.fmy][d.fmx];
/*  337 */           float mX = (s == 0 || s == 3) ? 0.65F : -0.65F;
/*  338 */           float mY = (s == 0 || s == 1) ? 0.46F : -0.46F;
/*  339 */           this.batch.setColor(16777215, 0.5F);
/*  340 */           this.batch.draw(Assets.keeperShadow[d.fmx][d.fmy][s], (d.x - origin[0].intValue()) + mX * d.z, (d.y - origin[1].intValue()) + mY * d.z);
/*      */         } 
/*      */       } 
/*      */     }
/*  344 */     this.batch.setColor(16777215, 1.0F);
/*      */   }
/*      */   
/*      */   private class SkinColorButton
/*      */     extends Button {
/*      */     SkinColorButton(int x, int y) {
/*  350 */       setGeometry(x, y, 28, 24);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  355 */       Color3 skinColor = Skin.colors[DeveloperKeeper.this.player.skinColor.ordinal()];
/*  356 */       setColors(Integer.valueOf(skinColor.color2), Integer.valueOf(skinColor.color1), Integer.valueOf(skinColor.color3));
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  361 */       updateSkinColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  366 */       updateSkinColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  371 */       updateSkinColor(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  376 */       updateSkinColor(-1);
/*      */     }
/*      */     
/*      */     private void updateSkinColor(int n) {
/*  380 */       DeveloperKeeper.this.player.skinColor = Skin.Color.values()[EMath.rotate(DeveloperKeeper.this.player.skinColor.ordinal(), Skin.Color.PINK.ordinal(), Skin.Color.RED.ordinal(), n)];
/*      */       
/*  382 */       setDirty(true);
/*      */       
/*  384 */       DeveloperKeeper.this.reloadKeeper();
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairColorButton
/*      */     extends Button {
/*      */     HairColorButton(int x, int y) {
/*  391 */       setGeometry(x, y, 28, 24);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  396 */       Color3 hairColor = Hair.colors[DeveloperKeeper.this.player.hairColor.ordinal()];
/*  397 */       setColors(Integer.valueOf(hairColor.color2), Integer.valueOf(hairColor.color1), Integer.valueOf(hairColor.color3));
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  402 */       updateHairColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  407 */       updateHairColor(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  412 */       updateHairColor(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  417 */       updateHairColor(-1);
/*      */     }
/*      */     
/*      */     private void updateHairColor(int n) {
/*  421 */       int color = DeveloperKeeper.this.player.hairColor.ordinal();
/*  422 */       color = EMath.rotate(color, Hair.Color.BLACK.ordinal(), Hair.Color.PUNK_BLOND.ordinal(), n);
/*  423 */       DeveloperKeeper.this.player.hairColor = Hair.Color.values()[color];
/*      */       
/*  425 */       setDirty(true);
/*      */       
/*  427 */       DeveloperKeeper.this.reloadHair();
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairStyleButton
/*      */     extends Button {
/*      */     HairStyleButton(int x, int y) {
/*  434 */       setGeometry(x, y, 115, 24);
/*  435 */       setColors(Integer.valueOf(3181627), Integer.valueOf(4898904), Integer.valueOf(2054438));
/*  436 */       setText("", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  441 */       setText(DeveloperKeeper.this.player.hairStyle.replace('_', ' '));
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  446 */       updateHairStyle(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  451 */       updateHairStyle(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  456 */       updateHairStyle(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  461 */       updateHairStyle(-1);
/*      */     }
/*      */     
/*      */     private void updateHairStyle(int n) {
/*  465 */       int i = Assets.hairStyles.indexOf(DeveloperKeeper.this.player.hairStyle);
/*  466 */       if (i == -1) {
/*  467 */         i = 0;
/*      */       } else {
/*  469 */         i = EMath.rotate(i, 0, Assets.hairStyles.size() - 1, n);
/*      */       } 
/*  471 */       DeveloperKeeper.this.player.hairStyle = Assets.hairStyles.get(i);
/*      */       
/*  473 */       setDirty(true);
/*      */       
/*  475 */       DeveloperKeeper.this.reloadHair();
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairLabel
/*      */     extends Button {
/*      */     HairLabel(int x, int y) {
/*  482 */       setGeometry(x, y, 64, 24);
/*  483 */       setText("HAIR", Font.Align.CENTER, Assets.font10);
/*  484 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairFrameButton extends Button {
/*      */     final int xIncrement;
/*      */     final int yIncrement;
/*      */     
/*      */     HairFrameButton(int x, int y, int xIncrement, int yIncrement) {
/*  493 */       this.xIncrement = xIncrement;
/*  494 */       this.yIncrement = yIncrement;
/*  495 */       setGeometry(x, y, 30, 30);
/*  496 */       setColor(7688315);
/*  497 */       setText("", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  502 */       setText(((this.xIncrement != 0) ? Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][0] : Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][1]).intValue());
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  507 */       updateFrame(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  512 */       updateFrame(-1);
/*      */     }
/*      */     
/*      */     private void updateFrame(int sign) {
/*  516 */       Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][0] = Integer.valueOf(EMath.rotate(Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][0].intValue(), 0, 7, sign * this.xIncrement));
/*  517 */       Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][1] = Integer.valueOf(EMath.rotate(Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][1].intValue(), 0, 9, sign * this.yIncrement));
/*  518 */       setDirty(true);
/*  519 */       DeveloperKeeper.this.hairMapPosition.setDirty(true);
/*  520 */       DeveloperKeeper.this.saveHairMapButton.setDirty(true);
/*  521 */       DeveloperKeeper.this.resetHairMapButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class MoveHairButton extends Button {
/*      */     final int xDir;
/*      */     final int yDir;
/*      */     
/*      */     MoveHairButton(int x, int y, int xDir, int yDir) {
/*  530 */       this.xDir = xDir;
/*  531 */       this.yDir = yDir;
/*  532 */       setGeometry(x, y, 30, 30);
/*  533 */       setColor(2795336);
/*  534 */       int fx = ((yDir != 0) ? 2 : 0) + ((xDir < 0 || yDir < 0) ? 4 : 0);
/*  535 */       this.textureRegion = Assets.wind[fx][0];
/*  536 */       setImagePosition(-3, -3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  541 */       updateHair();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  546 */       updateHair();
/*      */     }
/*      */     
/*      */     private void updateHair() {
/*  550 */       Integer[] arrayOfInteger = Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx]; arrayOfInteger[2] = Integer.valueOf(arrayOfInteger[2].intValue() + this.xDir); Integer.valueOf(arrayOfInteger[2].intValue() + this.xDir);
/*  551 */       arrayOfInteger = Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx]; arrayOfInteger[3] = Integer.valueOf(arrayOfInteger[3].intValue() + this.yDir); Integer.valueOf(arrayOfInteger[3].intValue() + this.yDir);
/*  552 */       DeveloperKeeper.this.hairMapPosition.setDirty(true);
/*  553 */       DeveloperKeeper.this.saveHairMapButton.setDirty(true);
/*  554 */       DeveloperKeeper.this.resetHairMapButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class HairPosition
/*      */     extends Button {
/*      */     HairPosition(int x, int y) {
/*  561 */       setGeometry(x, y, 72, 30);
/*  562 */       setColor(6710886);
/*  563 */       setText("", Font.Align.CENTER, Assets.font10);
/*  564 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  569 */       setText(Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][2] + ", " + Assets.keeperHairMap[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][3]);
/*      */     }
/*      */   }
/*      */   
/*      */   private class SaveHairMapButton
/*      */     extends Button {
/*      */     SaveHairMapButton(int x, int y) {
/*  576 */       setGeometry(x, y, 71, 30);
/*  577 */       setText("SAVE", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  582 */       String current = Assets.json.toJson(Assets.keeperHairMap);
/*  583 */       boolean modified = !current.equals(DeveloperKeeper.this.savedHairMap);
/*  584 */       setColor(modified ? 13369344 : 6710886);
/*  585 */       setActive(modified);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  590 */       Assets.saveKeeperHairMap();
/*  591 */       DeveloperKeeper.this.savedHairMap = Assets.json.toJson(Assets.keeperHairMap);
/*  592 */       setDirty(true);
/*  593 */       DeveloperKeeper.this.resetHairMapButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ResetHairMapButton
/*      */     extends Button
/*      */   {
/*      */     ResetHairMapButton(int x, int y) {
/*  601 */       setGeometry(x, y, 71, 30);
/*  602 */       setText("RESET", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  607 */       String current = Assets.json.toJson(Assets.keeperHairMap);
/*  608 */       boolean modified = !current.equals(DeveloperKeeper.this.savedHairMap);
/*  609 */       setColor(modified ? 13421568 : 6710886);
/*  610 */       setActive(modified);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  615 */       Assets.loadKeeperHairMap();
/*  616 */       setDirty(true);
/*  617 */       DeveloperKeeper.this.saveHairMapButton.setDirty(true);
/*  618 */       DeveloperKeeper.this.hairMapPosition.setDirty(true);
/*  619 */       DeveloperKeeper.this.hairFrameXButton.setDirty(true);
/*  620 */       DeveloperKeeper.this.hairFrameYButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ColumnButton
/*      */     extends Button
/*      */   {
/*      */     ColumnButton(int x, int y) {
/*  628 */       setGeometry(x, y, 88, 24);
/*  629 */       setColors(Integer.valueOf(3181627), Integer.valueOf(4898904), Integer.valueOf(2054438));
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  634 */       setText("COL: " + DeveloperKeeper.this.fmx, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  639 */       updateColumn(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  644 */       updateColumn(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  649 */       updateColumn(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  654 */       updateColumn(-1);
/*      */     }
/*      */     
/*      */     private void updateColumn(int n) {
/*  658 */       DeveloperKeeper.this.fmx = EMath.rotate(DeveloperKeeper.this.fmx, 0, 7, n);
/*  659 */       DeveloperKeeper.this.refreshAllWidgets();
/*      */     }
/*      */   }
/*      */   
/*      */   private class RowButton
/*      */     extends Button {
/*      */     RowButton(int x, int y) {
/*  666 */       setGeometry(x + 4 + 88, y, 88, 24);
/*  667 */       setColors(Integer.valueOf(3181627), Integer.valueOf(4898904), Integer.valueOf(2054438));
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  672 */       setText("ROW: " + DeveloperKeeper.this.fmy, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  677 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  682 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  687 */       updateRow(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  692 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/*  696 */       DeveloperKeeper.this.fmy = EMath.slide(DeveloperKeeper.this.fmy, 0, DeveloperKeeper.this.getPlayerRows() - 1, n);
/*  697 */       DeveloperKeeper.this.refreshAllWidgets();
/*      */     }
/*      */   }
/*      */   
/*      */   private class AnimationTypeButton
/*      */     extends Button {
/*      */     AnimationTypeButton(int x, int y) {
/*  704 */       setGeometry(x, y, 180, 24);
/*  705 */       setColor(2837089);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  710 */       setText(DeveloperKeeper.this.animation.toString().replace('_', ' '), Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  715 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  720 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  725 */       updateRow(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  730 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/*  734 */       DeveloperKeeper.this.animation = DeveloperKeeper.Animation.values()[EMath.rotate(DeveloperKeeper.this.animation, DeveloperKeeper.Animation.ANIMATION_OFF, DeveloperKeeper.Animation.VERTICAL, n)];
/*  735 */       setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class AnimationLengthButton
/*      */     extends Button {
/*      */     AnimationLengthButton(int x, int y) {
/*  742 */       setGeometry(x, y, 180, 24);
/*  743 */       setColor(2837089);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  748 */       setText("LENGTH: " + DeveloperKeeper.this.animationLength, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  753 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  758 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  763 */       updateRow(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  768 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/*  772 */       DeveloperKeeper.this.animationLength = EMath.slide(DeveloperKeeper.this.animationLength, 2, 8, n);
/*  773 */       setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class AnimationSpeedButton
/*      */     extends Button {
/*      */     AnimationSpeedButton(int x, int y) {
/*  780 */       setGeometry(x, y, 180, 24);
/*  781 */       setColor(2837089);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  786 */       setText("SPEED: " + DeveloperKeeper.this.animationSpeed, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  791 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  796 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  801 */       updateRow(-1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Hold() {
/*  806 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/*  810 */       DeveloperKeeper.this.animationSpeed = EMath.slide(DeveloperKeeper.this.animationSpeed, 1, 5, n);
/*  811 */       setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ShadowsButton
/*      */     extends Button {
/*      */     ShadowsButton(int x, int y) {
/*  818 */       setGeometry(x, y, 180, 24);
/*  819 */       setColor(9394434);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  824 */       setText("SHADOWS: " + DeveloperKeeper.this.shadows, Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  829 */       updateRow(1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire2Down() {
/*  834 */       updateRow(-1);
/*      */     }
/*      */     
/*      */     private void updateRow(int n) {
/*  838 */       DeveloperKeeper.this.shadows = (DeveloperKeeper.Shadows)EMath.rotate(DeveloperKeeper.this.shadows, DeveloperKeeper.Shadows.class, n);
/*  839 */       setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class OriginLabel
/*      */     extends Button {
/*      */     OriginLabel(int x, int y) {
/*  846 */       setGeometry(x, y, 64, 24);
/*  847 */       setText("ORIGIN", Font.Align.CENTER, Assets.font10);
/*  848 */       setActive(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private class MoveOriginButton extends Button {
/*      */     final int xDir;
/*      */     final int yDir;
/*      */     
/*      */     MoveOriginButton(int x, int y, int xDir, int yDir) {
/*  857 */       this.xDir = xDir;
/*  858 */       this.yDir = yDir;
/*  859 */       setGeometry(x, y, 30, 30);
/*  860 */       setColor(2795336);
/*  861 */       int fx = ((yDir != 0) ? 2 : 0) + ((xDir < 0 || yDir < 0) ? 4 : 0);
/*  862 */       this.textureRegion = Assets.wind[fx][0];
/*  863 */       setImagePosition(-3, -3);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  868 */       updateOrigin();
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Hold() {
/*  873 */       updateOrigin();
/*      */     }
/*      */     
/*      */     private void updateOrigin() {
/*  877 */       Integer[] arrayOfInteger = Assets.keeperOrigins[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx]; arrayOfInteger[0] = Integer.valueOf(arrayOfInteger[0].intValue() + this.xDir); Integer.valueOf(arrayOfInteger[0].intValue() + this.xDir);
/*  878 */       arrayOfInteger = Assets.keeperOrigins[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx]; arrayOfInteger[1] = Integer.valueOf(arrayOfInteger[1].intValue() + this.yDir); Integer.valueOf(arrayOfInteger[1].intValue() + this.yDir);
/*  879 */       DeveloperKeeper.this.originValue.setDirty(true);
/*  880 */       DeveloperKeeper.this.saveOriginsButton.setDirty(true);
/*  881 */       DeveloperKeeper.this.resetOriginsButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class OriginValue
/*      */     extends Button {
/*      */     OriginValue(int x, int y) {
/*  888 */       setGeometry(x, y, 72, 30);
/*  889 */       setColor(6710886);
/*  890 */       setText("", Font.Align.CENTER, Assets.font10);
/*  891 */       setActive(false);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  896 */       setText(Assets.keeperOrigins[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][0] + ", " + Assets.keeperOrigins[DeveloperKeeper.this.fmy][DeveloperKeeper.this.fmx][1]);
/*      */     }
/*      */   }
/*      */   
/*      */   private class saveOriginsButton
/*      */     extends Button {
/*      */     saveOriginsButton(int x, int y) {
/*  903 */       setGeometry(x, y, 71, 30);
/*  904 */       setText("SAVE", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  909 */       String current = Assets.json.toJson(Assets.keeperOrigins);
/*  910 */       boolean modified = !current.equals(DeveloperKeeper.this.savedOrigins);
/*  911 */       setColor(modified ? 13369344 : 6710886);
/*  912 */       setActive(modified);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  917 */       Assets.saveKeeperOrigins();
/*  918 */       DeveloperKeeper.this.savedOrigins = Assets.json.toJson(Assets.keeperOrigins);
/*  919 */       setDirty(true);
/*  920 */       DeveloperKeeper.this.resetOriginsButton.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class ResetOriginsButton
/*      */     extends Button
/*      */   {
/*      */     ResetOriginsButton(int x, int y) {
/*  928 */       setGeometry(x, y, 71, 30);
/*  929 */       setText("RESET", Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  934 */       String current = Assets.json.toJson(Assets.keeperOrigins);
/*  935 */       boolean modified = !current.equals(DeveloperKeeper.this.savedOrigins);
/*  936 */       setColor(modified ? 13421568 : 6710886);
/*  937 */       setActive(modified);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  942 */       Assets.loadKeeperOrigins();
/*  943 */       setDirty(true);
/*  944 */       DeveloperKeeper.this.saveOriginsButton.setDirty(true);
/*  945 */       DeveloperKeeper.this.originValue.setDirty(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private class FrameButton
/*      */     extends Button {
/*      */     private final int column;
/*      */     private final int row;
/*      */     
/*      */     FrameButton(int column, int row) {
/*  955 */       this.column = column;
/*  956 */       this.row = row;
/*  957 */       int x0 = 238;
/*  958 */       int y0 = 8;
/*  959 */       setGeometry(x0 + 100 * column, y0 + 100 * row, 100, 100);
/*  960 */       setColors(Integer.valueOf(2795336), Integer.valueOf(2795336), Integer.valueOf(2795336));
/*      */     }
/*      */ 
/*      */     
/*      */     public void refresh() {
/*  965 */       boolean selected = (this.row == DeveloperKeeper.this.fmy + DeveloperKeeper.this.firstRow && this.column == DeveloperKeeper.this.fmx);
/*  966 */       setColors(null, selected ? Integer.valueOf(16711680) : null, selected ? Integer.valueOf(16711680) : null);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  971 */       updateSelected();
/*      */     }
/*      */     
/*      */     private void updateSelected() {
/*  975 */       DeveloperKeeper.this.fmx = this.column;
/*  976 */       DeveloperKeeper.this.fmy = this.row - DeveloperKeeper.this.firstRow;
/*  977 */       DeveloperKeeper.this.refreshAllWidgets();
/*      */     }
/*      */   }
/*      */   
/*      */   private class ExitButton
/*      */     extends Button {
/*      */     ExitButton(int x) {
/*  984 */       setGeometry(x, 680, 180, 32);
/*  985 */       setColor(13124096);
/*  986 */       setText(Assets.strings.get("EXIT"), Font.Align.CENTER, Assets.font10);
/*      */     }
/*      */ 
/*      */     
/*      */     public void onFire1Down() {
/*  991 */       DeveloperKeeper.this.hasExited = true;
/*  992 */       Assets.unloadKeeper(DeveloperKeeper.this.player);
/*  993 */       Gdx.input.setInputProcessor(null);
/*  994 */       DeveloperKeeper.this.game.setScreen((Screen)new DeveloperTools(DeveloperKeeper.this.game));
/*      */     }
/*      */   }
/*      */   
/*      */   private void reloadKeeper() {
/*  999 */     Assets.unloadKeeper(this.player);
/* 1000 */     Assets.loadKeeper(this.player);
/*      */   }
/*      */   
/*      */   private void reloadHair() {
/* 1004 */     Assets.unloadHair(this.player);
/* 1005 */     Assets.loadHair(this.player);
/*      */   }
/*      */   
/*      */   private int getPlayerRows() {
/* 1009 */     return (this.player.role == Player.Role.GOALKEEPER) ? 19 : 16;
/*      */   }
/*      */   
/*      */   private class InputProcessor extends InputAdapter {
/*      */     private InputProcessor() {}
/*      */     
/*      */     public boolean scrolled(int n) {
/* 1016 */       DeveloperKeeper.this.firstRow = EMath.slide(DeveloperKeeper.this.firstRow, 7 - DeveloperKeeper.this.getPlayerRows(), 0, -n);
/* 1017 */       DeveloperKeeper.this.refreshAllWidgets();
/* 1018 */       return true;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\DeveloperKeeper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */