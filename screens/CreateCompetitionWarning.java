/*     */ package com.ygames.ysoccer.screens;
/*     */ 
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.competitions.Competition;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Label;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class CreateCompetitionWarning
/*     */   extends GLScreen {
/*     */   CreateCompetitionWarning(GLGame game, Competition.Category createCategory) {
/*  18 */     super(game);
/*  19 */     this.createCategory = createCategory;
/*     */     
/*  21 */     this.background = new Texture("images/backgrounds/menu_competition.jpg");
/*     */ 
/*     */ 
/*     */     
/*  25 */     String titleLabel = Competition.getCategoryLabel(createCategory);
/*     */     
/*  27 */     int titleColor = 0;
/*  28 */     switch (createCategory) {
/*     */       case DIY_COMPETITION:
/*  30 */         titleColor = 3632687;
/*     */         break;
/*     */       
/*     */       case PRESET_COMPETITION:
/*  34 */         titleColor = 4281856;
/*     */         break;
/*     */     } 
/*  37 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.strings.get(titleLabel), titleColor);
/*  38 */     this.widgets.add(titleBar);
/*     */ 
/*     */     
/*  41 */     Button button = new Button();
/*  42 */     Objects.requireNonNull(game.gui); button.setGeometry((1280 - 580) / 2, 270, 580, 180);
/*  43 */     button.setColors(Integer.valueOf(14417920), Integer.valueOf(16728385), Integer.valueOf(9175040));
/*  44 */     button.setActive(false);
/*  45 */     this.widgets.add(button);
/*     */     
/*  47 */     String msg = Assets.strings.get(Competition.getWarningLabel(game.competition.category));
/*  48 */     int cut = msg.indexOf(" ", msg.length() / 2);
/*     */     
/*  50 */     Label label = new Label();
/*  51 */     label.setText(msg.substring(0, cut), Font.Align.CENTER, Assets.font14);
/*  52 */     Objects.requireNonNull(game.gui); label.setPosition(1280 / 2, 340);
/*  53 */     this.widgets.add(label);
/*     */     
/*  55 */     label = new Label();
/*  56 */     label.setText(msg.substring(cut + 1), Font.Align.CENTER, Assets.font14);
/*  57 */     Objects.requireNonNull(game.gui); label.setPosition(1280 / 2, 380);
/*  58 */     this.widgets.add(label);
/*     */     
/*  60 */     ContinueButton continueButton = new ContinueButton();
/*  61 */     this.widgets.add(continueButton);
/*     */     
/*  63 */     AbortButton abortButton = new AbortButton();
/*  64 */     this.widgets.add(abortButton);
/*     */     
/*  66 */     setSelectedWidget((Widget)abortButton);
/*     */   }
/*     */   
/*     */   private Competition.Category createCategory;
/*     */   
/*     */   private class ContinueButton extends Button { ContinueButton() {
/*  72 */       Objects.requireNonNull(CreateCompetitionWarning.this.game.gui); setGeometry((1280 - 180) / 2, 605, 180, 36);
/*  73 */       setColors(Integer.valueOf(5669376), Integer.valueOf(7844864), Integer.valueOf(2375168));
/*  74 */       setText(Assets.strings.get("CONTINUE"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/*  79 */       CreateCompetitionWarning.this.game.clearCompetition();
/*  80 */       switch (CreateCompetitionWarning.this.createCategory) {
/*     */         case DIY_COMPETITION:
/*  82 */           CreateCompetitionWarning.this.game.setScreen((Screen)new DiyCompetition(CreateCompetitionWarning.this.game));
/*     */           break;
/*     */         
/*     */         case PRESET_COMPETITION:
/*  86 */           CreateCompetitionWarning.this.game.setState(GLGame.State.COMPETITION, Competition.Category.PRESET_COMPETITION);
/*  87 */           CreateCompetitionWarning.this.game.setScreen((Screen)new SelectCompetition(CreateCompetitionWarning.this.game, Assets.competitionsRootFolder));
/*     */           break;
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   private class AbortButton
/*     */     extends Button {
/*     */     AbortButton() {
/*  96 */       Objects.requireNonNull(CreateCompetitionWarning.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/*  97 */       setColors(Integer.valueOf(13124096), Integer.valueOf(16737561), Integer.valueOf(8401664));
/*  98 */       setText(Assets.strings.get("ABORT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 103 */       CreateCompetitionWarning.this.game.setScreen((Screen)new Main(CreateCompetitionWarning.this.game));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\CreateCompetitionWarning.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */