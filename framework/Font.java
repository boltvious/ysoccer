/*     */ package com.ygames.ysoccer.framework;
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.graphics.Pixmap;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Font {
/*     */   private RgbPair rgbPair;
/*     */   public int size;
/*     */   private int columnWidth;
/*     */   private int rowHeight;
/*     */   private int regionWidth;
/*     */   public int regionHeight;
/*     */   public Texture texture;
/*     */   
/*     */   public enum Align {
/*  20 */     RIGHT, CENTER, LEFT;
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
/*  33 */   private int[] widths = new int[1024];
/*     */   
/*  35 */   private TextureRegion[] regions = new TextureRegion[1024];
/*     */   
/*     */   public Font(int size, int columnWidth, int rowHeight, int regionWidth, int regionHeight) {
/*  38 */     this.size = size;
/*  39 */     this.columnWidth = columnWidth;
/*  40 */     this.rowHeight = rowHeight;
/*  41 */     this.regionWidth = regionWidth;
/*  42 */     this.regionHeight = regionHeight;
/*     */   }
/*     */   
/*     */   public Font(int size, int columnWidth, int rowHeight, int regionWidth, int regionHeight, RgbPair rgbPair) {
/*  46 */     this(size, columnWidth, rowHeight, regionWidth, regionHeight);
/*  47 */     this.rgbPair = rgbPair;
/*     */   }
/*     */   
/*     */   public void load() {
/*  51 */     if (this.rgbPair == null) {
/*  52 */       this.texture = new Texture("images/font_" + this.size + ".png");
/*     */     } else {
/*  54 */       InputStream in = null;
/*  55 */       List<RgbPair> rgbPairs = new ArrayList<>();
/*  56 */       rgbPairs.add(this.rgbPair);
/*     */       try {
/*  58 */         in = Gdx.files.internal("images/font_" + this.size + ".png").read();
/*     */         
/*  60 */         ByteArrayInputStream inputStream = PngEditor.editPalette(in, rgbPairs);
/*     */         
/*  62 */         byte[] bytes = FileUtils.inputStreamToBytes(inputStream);
/*     */         
/*  64 */         Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
/*  65 */         this.texture = new Texture(pixmap);
/*  66 */       } catch (IOException e) {
/*  67 */         throw new RuntimeException("Couldn't load texture", e);
/*     */       } finally {
/*  69 */         if (in != null) {
/*     */           try {
/*  71 */             in.close();
/*  72 */           } catch (IOException e) {
/*  73 */             Gdx.app.error(getClass().toString(), e.toString());
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*  78 */     loadFontWidths(this.widths, "configs/font_" + this.size + ".txt");
/*     */     
/*  80 */     for (int i = 0; i < 1024; i++) {
/*  81 */       this.regions[i] = new TextureRegion(this.texture, (i & 0x3F) * this.columnWidth, (i >> 6) * this.rowHeight, this.regionWidth, this.regionHeight);
/*  82 */       this.regions[i].flip(false, true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void loadFontWidths(int[] fontWidths, String filePath) {
/*  87 */     InputStream in = null;
/*     */     
/*     */     try {
/*  90 */       in = Gdx.files.internal(filePath).read();
/*  91 */       setFontWidths(fontWidths, in);
/*  92 */     } catch (IOException e) {
/*  93 */       throw new RuntimeException("Error while reading font widths " + e.getMessage());
/*     */     } finally {
/*  95 */       if (in != null)
/*     */         try {
/*  97 */           in.close();
/*  98 */         } catch (IOException e) {
/*  99 */           Gdx.app.error("loadFontWidths", e.toString());
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void setFontWidths(int[] fontWidths, InputStream in) throws IOException {
/* 105 */     byte[] buffer = new byte[1];
/* 106 */     int s = 0;
/*     */ 
/*     */     
/* 109 */     for (int r = 0; r < 16; r++) {
/*     */ 
/*     */       
/* 112 */       for (int b = 0; b < 8; b++) {
/*     */ 
/*     */         
/* 115 */         for (int c = 0; c < 8; c++) {
/*     */           
/* 117 */           in.read(buffer);
/* 118 */           s = buffer[0] & 0xFF;
/*     */ 
/*     */           
/* 121 */           if (s >= 48 && s <= 57) {
/* 122 */             s -= 48;
/*     */           
/*     */           }
/* 125 */           else if (s >= 65 && s <= 78) {
/* 126 */             s -= 55;
/*     */           } else {
/* 128 */             s = 30;
/*     */           } 
/*     */           
/* 131 */           fontWidths[r * 64 + 8 * b + c] = s;
/*     */         } 
/*     */ 
/*     */         
/* 135 */         in.read(buffer);
/* 136 */         s = buffer[0] & 0xFF;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 141 */       if (s == 13) {
/* 142 */         in.read(buffer);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(SpriteBatch batch, String text, int x, int y, Align align) {
/* 149 */     int w = textWidth(text);
/*     */ 
/*     */     
/* 152 */     switch (align) {
/*     */       case RIGHT:
/* 154 */         x -= w;
/*     */         break;
/*     */       
/*     */       case CENTER:
/* 158 */         x -= w / 2;
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     for (int i = 0; i < text.length(); i++) {
/*     */       
/* 168 */       int c = text.charAt(i);
/*     */       
/* 170 */       if (c == 8364) c = 128;
/*     */       
/* 172 */       if (c >= this.regions.length) {
/* 173 */         c = 0;
/*     */       }
/*     */       
/* 176 */       batch.draw(this.regions[c], x, y, this.regionWidth, this.regionHeight);
/*     */       
/* 178 */       x += this.widths[c];
/*     */     } 
/*     */   }
/*     */   
/*     */   public void draw(SpriteBatch batch, int i, int x, int y, Align align) {
/* 183 */     draw(batch, "" + i, x, y, align);
/*     */   }
/*     */   
/*     */   public int textWidth(String text) {
/* 187 */     int w = 0;
/* 188 */     for (int i = 0; i < text.length(); i++) {
/* 189 */       int c = text.charAt(i);
/*     */       
/* 191 */       if (c == 8364) c = 128;
/*     */ 
/*     */       
/* 194 */       if (c == 13 && i + 1 < text.length() && text.charAt(i + 1) == '\n') {
/*     */         break;
/*     */       }
/*     */       
/* 198 */       if (c >= this.widths.length) {
/* 199 */         c = 0;
/*     */       }
/*     */       
/* 202 */       w += this.widths[c];
/*     */     } 
/* 204 */     return w;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\Font.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */