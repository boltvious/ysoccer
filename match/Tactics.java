/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.utils.GdxRuntimeException;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tactics
/*     */ {
/*  19 */   public static String[] codes = new String[] { "4-4-2", "5-4-1", "4-5-1", "5-3-2", "3-5-2", "4-3-3", "4-2-4", "3-4-3", "SWEEP", "5-2-3", "ATTACK", "DEFEND", "USER A", "USER B", "USER C", "USER D", "USER E", "USER F" };
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
/*  31 */   public static String[] fileNames = new String[] { "T442", "T541", "T451", "T532", "T352", "T433", "T424", "T343", "TLIBERO", "T523", "TATTACK", "TDEFEND", "T442", "T442", "T442", "T442", "T442", "T442" };
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
/*  43 */   public static int[][] order = new int[][] { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, { 0, 1, 2, 3, 6, 4, 5, 7, 9, 8, 10 }, { 0, 1, 2, 3, 4, 5, 6, 7, 9, 8, 10 }, { 0, 1, 2, 3, 6, 4, 5, 7, 8, 9, 10 }, { 0, 1, 2, 4, 5, 3, 6, 7, 8, 9, 10 }, { 0, 1, 2, 3, 4, 5, 6, 8, 7, 9, 10 }, { 0, 1, 2, 3, 4, 6, 7, 5, 9, 10, 8 }, { 0, 1, 2, 4, 5, 3, 6, 8, 7, 9, 10 }, { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }, { 0, 1, 2, 3, 6, 4, 7, 9, 5, 10, 8 }, { 0, 1, 2, 4, 3, 6, 5, 7, 9, 10, 8 }, { 0, 5, 1, 2, 3, 4, 8, 6, 7, 9, 10 } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  59 */   public int[][][] target = new int[11][35][2];
/*  60 */   public int[] pairs = new int[11];
/*     */   
/*     */   public int basedOn;
/*     */ 
/*     */   
/*     */   public void loadFile(InputStream in) throws IOException {
/*  66 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/*     */     
/*  68 */     byte[] buffer = new byte[16384];
/*     */     
/*     */     int len;
/*  71 */     while ((len = in.read(buffer, 0, buffer.length)) != -1) {
/*  72 */       os.write(buffer, 0, len);
/*     */     }
/*     */     
/*  75 */     os.flush();
/*     */     
/*  77 */     byte[] bytes = os.toByteArray();
/*     */ 
/*     */     
/*  80 */     int index = 0;
/*  81 */     this.name = "";
/*  82 */     boolean end_of_name = false;
/*  83 */     for (int c = 0; c < 9; c++) {
/*  84 */       int j = bytes[index++] & 0xFF;
/*  85 */       if (j == 0) {
/*  86 */         end_of_name = true;
/*     */       }
/*  88 */       if (!end_of_name) {
/*  89 */         this.name += (char)j;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  94 */     for (int player = 1; player < 11; player++) {
/*  95 */       for (int ball_zone = 0; ball_zone < 35; ball_zone++) {
/*  96 */         int j = bytes[index++] & 0xFF;
/*     */ 
/*     */         
/*  99 */         int x = j >> 4;
/* 100 */         int y = j & 0xF;
/*     */ 
/*     */         
/* 103 */         x -= 7;
/* 104 */         y = 2 * y - 15;
/*     */ 
/*     */         
/* 107 */         this.target[player][ball_zone][0] = x * 68;
/* 108 */         this.target[player][ball_zone][1] = y * 40;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 113 */     for (int i = 1; i < 11; i++) {
/* 114 */       this.pairs[i] = bytes[index++] & 0xFF;
/*     */     }
/*     */ 
/*     */     
/* 118 */     this.basedOn = bytes[index++] & 0xFF;
/*     */   }
/*     */   
/*     */   public void saveFile(String filename) {
/* 122 */     FileHandle file = Assets.tacticsFolder.child(filename);
/* 123 */     OutputStream file_tactics = file.write(false);
/*     */ 
/*     */     
/*     */     try {
/* 127 */       for (int j = 0; j < 9; j++) {
/* 128 */         if (j < this.name.length()) {
/* 129 */           file_tactics.write(this.name.charAt(j));
/*     */         } else {
/* 131 */           file_tactics.write(0);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 136 */       for (int player = 1; player < 11; player++) {
/* 137 */         for (int ball_zone = 0; ball_zone < 35; ball_zone++) {
/*     */ 
/*     */           
/* 140 */           int x = this.target[player][ball_zone][0] / 68;
/* 141 */           int y = this.target[player][ball_zone][1] / 40;
/*     */ 
/*     */           
/* 144 */           x += 7;
/* 145 */           y = (y + 15) / 2;
/*     */           
/* 147 */           file_tactics.write((x << 4) + y);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 152 */       for (int i = 1; i < 11; i++) {
/* 153 */         file_tactics.write(this.pairs[i]);
/*     */       }
/*     */ 
/*     */       
/* 157 */       file_tactics.write(this.basedOn);
/*     */       
/* 159 */       file_tactics.close();
/* 160 */     } catch (IOException e) {
/* 161 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 168 */     String s = this.name + "\n";
/*     */ 
/*     */     
/* 171 */     for (int ball_zone = 0; ball_zone < 35; ball_zone++) {
/* 172 */       s = s + String.format("%02d", new Object[] { Integer.valueOf(ball_zone) }) + ":";
/* 173 */       for (int i = 1; i < 11; i++) {
/*     */         
/* 175 */         int x = this.target[i][ball_zone][0] / 68;
/* 176 */         int y = this.target[i][ball_zone][1] / 40;
/*     */ 
/*     */         
/* 179 */         x += 7;
/* 180 */         y = (y + 15) / 2;
/*     */         
/* 182 */         s = s + " " + String.format("%02d", new Object[] { Integer.valueOf(x) });
/* 183 */         s = s + "-" + String.format("%02d", new Object[] { Integer.valueOf(y) });
/*     */       } 
/* 185 */       s = s + "\n";
/*     */     } 
/*     */ 
/*     */     
/* 189 */     s = s + "Pairs:";
/* 190 */     for (int player = 1; player < 11; player++) {
/* 191 */       int i = this.pairs[player];
/* 192 */       if (i == 255) {
/* 193 */         i = 0;
/*     */       } else {
/* 195 */         i++;
/*     */       } 
/* 197 */       s = s + " " + String.format("%02d", new Object[] { Integer.valueOf(i) });
/*     */     } 
/* 199 */     s = s + "\n";
/*     */ 
/*     */     
/* 202 */     s = s + "Based on: " + this.basedOn + "\n";
/*     */     
/* 204 */     return s;
/*     */   }
/*     */   
/*     */   public void copyFrom(Tactics t) {
/* 208 */     this.name = t.name;
/*     */ 
/*     */     
/* 211 */     for (int player = 1; player < 11; player++) {
/* 212 */       for (int zone = 0; zone < 35; zone++) {
/* 213 */         this.target[player][zone][0] = t.target[player][zone][0];
/* 214 */         this.target[player][zone][1] = t.target[player][zone][1];
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 219 */     for (int i = 1; i < 11; i++) {
/* 220 */       this.pairs[i] = t.pairs[i];
/*     */     }
/*     */ 
/*     */     
/* 224 */     this.basedOn = t.basedOn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDeletePair(int p1, int p2) {
/*     */     boolean found;
/* 230 */     int oldPair1 = this.pairs[p1];
/* 231 */     int oldPair2 = this.pairs[p2];
/*     */ 
/*     */     
/* 234 */     if (oldPair1 == oldPair2 && oldPair1 != 255) {
/* 235 */       this.pairs[p1] = 255;
/* 236 */       this.pairs[p2] = 255;
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 241 */     for (int i = 1; i < 11; i++) {
/* 242 */       if (this.pairs[i] == oldPair1) {
/* 243 */         this.pairs[i] = 255;
/*     */       }
/* 245 */       if (this.pairs[i] == oldPair2) {
/* 246 */         this.pairs[i] = 255;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 251 */     int newPairValue = 0;
/*     */     
/*     */     do {
/* 254 */       found = true;
/* 255 */       for (int j = 1; j < 11; j++) {
/* 256 */         if (this.pairs[j] == newPairValue) {
/* 257 */           found = false;
/*     */         }
/*     */       } 
/* 260 */       if (found)
/* 261 */         continue;  newPairValue++;
/*     */     }
/* 263 */     while (!found);
/*     */     
/* 265 */     this.pairs[p1] = newPairValue;
/* 266 */     this.pairs[p2] = newPairValue;
/*     */   }
/*     */   
/*     */   public boolean isPaired(int p) {
/* 270 */     return (this.pairs[p] != 255);
/*     */   }
/*     */   
/*     */   public int getPaired(int p) {
/* 274 */     int pairValue = this.pairs[p];
/* 275 */     if (pairValue == 255) {
/* 276 */       throw new GdxRuntimeException("This player is not paired");
/*     */     }
/* 278 */     for (int i = 1; i < 11; i++) {
/* 279 */       if (i != p && this.pairs[i] == pairValue) {
/* 280 */         return i;
/*     */       }
/*     */     } 
/* 283 */     throw new GdxRuntimeException("Paired player not found");
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Tactics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */