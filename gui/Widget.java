/*     */ package com.ygames.ysoccer.gui;
/*     */ 
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLColor;
/*     */ import com.ygames.ysoccer.framework.GLShapeRenderer;
/*     */ import com.ygames.ysoccer.framework.GLSpriteBatch;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Widget
/*     */ {
/*     */   protected static final float alpha = 0.92F;
/*     */   public int x;
/*     */   public int y;
/*     */   public int w;
/*     */   public int h;
/*     */   public TextureRegion textureRegion;
/*     */   float imageScaleX;
/*     */   float imageScaleY;
/*     */   WidgetColor color;
/*     */   protected String text;
/*     */   Font font;
/*     */   Font.Align align;
/*     */   protected int textOffsetX;
/*     */   public boolean active;
/*     */   public boolean selected;
/*     */   public boolean entryMode;
/*     */   public boolean visible;
/*     */   boolean addShadow;
/*     */   private boolean dirty;
/*     */   
/*     */   public enum Event
/*     */   {
/*  44 */     FIRE1_DOWN, FIRE1_HOLD, FIRE1_UP, FIRE2_DOWN, FIRE2_HOLD, FIRE2_UP;
/*     */   }
/*     */   
/*     */   public Widget() {
/*  48 */     this.imageScaleX = 1.0F;
/*  49 */     this.imageScaleY = 1.0F;
/*  50 */     this.color = new WidgetColor();
/*  51 */     this.align = Font.Align.CENTER;
/*  52 */     this.visible = true;
/*  53 */     this.dirty = true;
/*     */   }
/*     */   
/*     */   public abstract void render(GLSpriteBatch paramGLSpriteBatch, GLShapeRenderer paramGLShapeRenderer);
/*     */   
/*     */   public void setGeometry(int x, int y, int w, int h) {
/*  59 */     setPosition(x, y);
/*  60 */     setSize(w, h);
/*     */   }
/*     */   
/*     */   public void setPosition(int x, int y) {
/*  64 */     this.x = x;
/*  65 */     this.y = y;
/*     */   }
/*     */   
/*     */   protected void setImageScale(float scaleX, float scaleY) {
/*  69 */     this.imageScaleX = scaleX;
/*  70 */     this.imageScaleY = scaleY;
/*     */   }
/*     */   
/*     */   public void setSize(int w, int h) {
/*  74 */     this.w = w;
/*  75 */     this.h = h;
/*     */   }
/*     */   
/*     */   public void setColors(Integer body, Integer lightBorder, Integer darkBorder) {
/*  79 */     this.color.set(body, lightBorder, darkBorder);
/*     */   }
/*     */   
/*     */   public void setColors(WidgetColor color) {
/*  83 */     if (color == null) {
/*  84 */       this.color.set(null, null, null);
/*     */     } else {
/*  86 */       this.color.set(color.body, color.lightBorder, color.darkBorder);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setColor(int color) {
/*  91 */     setColors(Integer.valueOf(color), Integer.valueOf(GLColor.brighter(color)), Integer.valueOf(GLColor.darker(color, 0.7D)));
/*     */   }
/*     */   
/*     */   public void setText(String text) {
/*  95 */     this.text = text;
/*     */   }
/*     */   
/*     */   public void setText(int i) {
/*  99 */     setText("" + i);
/*     */   }
/*     */   
/*     */   public void setText(String text, Font.Align align, Font font) {
/* 103 */     this.text = text;
/* 104 */     this.align = align;
/* 105 */     this.font = font;
/*     */   }
/*     */   
/*     */   public void setText(int i, Font.Align align, Font font) {
/* 109 */     this.text = "" + i;
/* 110 */     this.align = align;
/* 111 */     this.font = font;
/*     */   }
/*     */   
/*     */   public void setTextOffsetX(int textOffsetX) {
/* 115 */     this.textOffsetX = textOffsetX;
/*     */   }
/*     */   
/*     */   public void setActive(boolean active) {
/* 119 */     this.active = active;
/*     */   }
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 123 */     if (this.selected && !selected) {
/* 124 */       onDeselect();
/*     */     }
/* 126 */     this.selected = selected;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDeselect() {}
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 133 */     this.visible = visible;
/*     */   }
/*     */   
/*     */   protected void setAddShadow(boolean addShadow) {
/* 137 */     this.addShadow = addShadow;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fireEvent(Event widgetEvent) {
/* 142 */     if (!this.active)
/*     */       return; 
/* 144 */     switch (widgetEvent) {
/*     */       case FIRE1_DOWN:
/* 146 */         onFire1Down();
/*     */         break;
/*     */       
/*     */       case FIRE1_HOLD:
/* 150 */         onFire1Hold();
/*     */         break;
/*     */       
/*     */       case FIRE1_UP:
/* 154 */         onFire1Up();
/*     */         break;
/*     */       
/*     */       case FIRE2_DOWN:
/* 158 */         onFire2Down();
/*     */         break;
/*     */       
/*     */       case FIRE2_HOLD:
/* 162 */         onFire2Hold();
/*     */         break;
/*     */       
/*     */       case FIRE2_UP:
/* 166 */         onFire2Up();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFire1Down() {}
/*     */ 
/*     */   
/*     */   protected void onFire1Hold() {}
/*     */ 
/*     */   
/*     */   protected void onFire1Up() {}
/*     */ 
/*     */   
/*     */   protected void onFire2Down() {}
/*     */ 
/*     */   
/*     */   protected void onFire2Hold() {}
/*     */ 
/*     */   
/*     */   protected void onFire2Up() {}
/*     */ 
/*     */   
/*     */   public void refresh() {}
/*     */   
/*     */   public boolean getDirty() {
/* 193 */     return this.dirty;
/*     */   }
/*     */   
/*     */   public void setDirty(boolean dirty) {
/* 197 */     this.dirty = dirty;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void arrange(int width, int centerY, int rowHeight, int spacing, List<Widget> widgetList) {
/* 202 */     int maxRows = 8;
/* 203 */     int len = widgetList.size();
/* 204 */     if (len <= maxRows) {
/* 205 */       for (int i = 0; i < len; i++) {
/* 206 */         Widget w = widgetList.get(i);
/* 207 */         w.x = (width - w.w) / 2;
/* 208 */         w.y = centerY - rowHeight * len / 2 + rowHeight * i;
/*     */       } 
/* 210 */     } else if (len <= 2 * maxRows) {
/* 211 */       int col1 = EMath.floor((len + 1) / 2.0D);
/* 212 */       for (int i = 0; i < len; i++) {
/* 213 */         Widget w = widgetList.get(i);
/* 214 */         if (i < col1) {
/* 215 */           w.x = (width - 2 * w.w - spacing) / 2;
/* 216 */           w.y = centerY + (int)(rowHeight * (i - col1 / 2.0D));
/*     */         } else {
/* 218 */           w.x = (width + spacing) / 2;
/* 219 */           w.y = centerY + (int)(rowHeight * ((i - col1) - col1 / 2.0D));
/*     */         } 
/*     */       } 
/*     */     } else {
/* 223 */       int col1 = EMath.floor(len / 3.0D) + ((len % 3 == 2) ? 1 : 0);
/* 224 */       int col2 = EMath.floor(len / 3.0D) + ((len % 3 > 0) ? 1 : 0);
/* 225 */       for (int i = 0; i < len; i++) {
/* 226 */         Widget w = widgetList.get(i);
/* 227 */         if (i < col1) {
/* 228 */           w.x = (width - 3 * w.w) / 2 - spacing;
/* 229 */           w.y = centerY + (int)(rowHeight * (i - col2 / 2.0D));
/* 230 */         } else if (i < col1 + col2) {
/* 231 */           w.x = (width - w.w) / 2;
/* 232 */           w.y = centerY + (int)(rowHeight * ((i - col1) - col2 / 2.0D));
/*     */         } else {
/* 234 */           w.x = (width + w.w) / 2 + spacing;
/* 235 */           w.y = centerY + (int)(rowHeight * ((i - col1 - col2) - col2 / 2.0D));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getRows(List<Widget> widgetList) {
/* 243 */     int len = widgetList.size();
/* 244 */     return (len <= 8) ? len : (EMath.floor(len / 3.0D) + 1);
/*     */   }
/*     */   
/*     */   public boolean contains(float x0, float y0) {
/* 248 */     return (x0 >= this.x && x0 <= (this.x + this.w) && y0 >= this.y && y0 <= (this.y + this.h));
/*     */   }
/*     */   
/* 251 */   public static Comparator<Widget> widgetComparatorByText = new CompareByText();
/*     */   
/*     */   private static class CompareByText implements Comparator<Widget> {
/*     */     private CompareByText() {}
/*     */     
/*     */     public int compare(Widget o1, Widget o2) {
/* 257 */       return o1.text.compareTo(o2.text);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\gui\Widget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */