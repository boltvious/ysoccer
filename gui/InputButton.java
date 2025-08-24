/*     */ package com.ygames.ysoccer.gui;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.InputAdapter;
/*     */ import com.badlogic.gdx.InputProcessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InputButton
/*     */   extends Button
/*     */ {
/*     */   private String entryString;
/*     */   private int entryLimit;
/*     */   private String inputFilter;
/*  18 */   private InputProcessor inputProcessor = (InputProcessor)new TextInputProcessor();
/*     */ 
/*     */   
/*     */   private void setEntryMode(boolean newValue, boolean applyChanges) {
/*  22 */     if (!this.entryMode && newValue) {
/*  23 */       Gdx.input.setInputProcessor(this.inputProcessor);
/*  24 */       this.entryString = this.text;
/*     */     } 
/*  26 */     if (this.entryMode && !newValue) {
/*  27 */       if (applyChanges && 
/*  28 */         !this.text.equals(this.entryString)) {
/*  29 */         this.text = this.entryString;
/*  30 */         onChanged();
/*     */       } 
/*     */       
/*  33 */       Gdx.input.setInputProcessor(null);
/*     */     } 
/*  35 */     this.entryMode = newValue;
/*     */   }
/*     */   
/*     */   protected void setEntryLimit(int entryLimit) {
/*  39 */     this.entryLimit = entryLimit;
/*     */   }
/*     */   
/*     */   protected void setInputFilter(String inputFilter) {
/*  43 */     this.inputFilter = inputFilter;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getText() {
/*  48 */     if (this.entryMode) {
/*  49 */       return this.entryString + "_";
/*     */     }
/*  51 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFire1Down() {
/*  57 */     setEntryMode(true, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeselect() {
/*  62 */     setEntryMode(false, true);
/*     */   }
/*     */   
/*     */   public void onChanged() {}
/*     */   
/*     */   private class TextInputProcessor extends InputAdapter {
/*     */     private TextInputProcessor() {}
/*     */     
/*     */     public boolean keyDown(int keycode) {
/*  71 */       switch (keycode) {
/*     */         case 66:
/*  73 */           InputButton.this.setEntryMode(false, true);
/*     */           break;
/*     */         
/*     */         case 131:
/*  77 */           InputButton.this.setEntryMode(false, false);
/*     */           break;
/*     */       } 
/*  80 */       return true;
/*     */     }
/*     */     
/*     */     public boolean keyTyped(char character) {
/*  84 */       if (InputButton.this.isPrintableChar(character) && InputButton.this.entryString.length() < InputButton.this.entryLimit) {
/*  85 */         InputButton.this.entryString += InputButton.this.charToString(character);
/*     */       } else {
/*  87 */         switch (character) {
/*     */           case '\b':
/*  89 */             InputButton.this.entryString = InputButton.this.entryString.substring(0, Math.max(InputButton.this.entryString.length() - 1, 0));
/*     */             break;
/*     */         } 
/*     */       } 
/*  93 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   private String charToString(char character) {
/*  98 */     String s = ("" + character).toUpperCase();
/*  99 */     if (this.inputFilter != null && !s.matches(this.inputFilter)) {
/* 100 */       return "";
/*     */     }
/* 102 */     return s;
/*     */   }
/*     */   
/*     */   private boolean isPrintableChar(char c) {
/* 106 */     Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
/* 107 */     return (!Character.isISOControl(c) && c != Character.MAX_VALUE && block != null && block != Character.UnicodeBlock.SPECIALS);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\InputButton.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */