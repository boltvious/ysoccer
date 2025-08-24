/*     */ package com.ygames.ysoccer.framework;
/*     */ 
/*     */ public class GLColor {
/*     */   public enum Component {
/*   5 */     ALPHA, RED, GREEN, BLUE; }
/*     */   
/*     */   public static int valueOf(String hexString) {
/*   8 */     return Integer.parseInt(hexString.substring(1), 16);
/*     */   }
/*     */   
/*     */   public static String toHexString(int color) {
/*  12 */     return String.format("#%02x%02x%02x", new Object[] { Integer.valueOf(red(color)), Integer.valueOf(green(color)), Integer.valueOf(blue(color)) });
/*     */   }
/*     */   
/*     */   public static int getComponentValue(int color, Component component) {
/*  16 */     switch (component) {
/*     */       case ALPHA:
/*  18 */         return alpha(color);
/*     */       case RED:
/*  20 */         return red(color);
/*     */       case GREEN:
/*  22 */         return green(color);
/*     */       case BLUE:
/*  24 */         return blue(color);
/*     */     } 
/*  26 */     return 0;
/*     */   }
/*     */   
/*     */   public static int argb(int r, int g, int b, int a) {
/*  30 */     return a << 24 | r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   public static int rgb(int r, int g, int b) {
/*  34 */     return r << 16 | g << 8 | b;
/*     */   }
/*     */   
/*     */   public static int alpha(int rgb) {
/*  38 */     return rgb >> 24 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int red(int rgb) {
/*  42 */     return rgb >> 16 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int green(int rgb) {
/*  46 */     return rgb >> 8 & 0xFF;
/*     */   }
/*     */   
/*     */   public static int blue(int rgb) {
/*  50 */     return rgb & 0xFF;
/*     */   }
/*     */   
/*     */   public static int brighter(int color) {
/*  54 */     int red = red(color);
/*  55 */     int green = green(color);
/*  56 */     int blue = blue(color);
/*  57 */     int alpha = alpha(color);
/*  58 */     byte b = 3;
/*  59 */     if (red == 0 && green == 0 && blue == 0) {
/*  60 */       return argb(red, green, blue, alpha);
/*     */     }
/*  62 */     if (red > 0 && red < b) {
/*  63 */       red = b;
/*     */     }
/*     */     
/*  66 */     if (green > 0 && green < b) {
/*  67 */       green = b;
/*     */     }
/*     */     
/*  70 */     if (blue > 0 && blue < b) {
/*  71 */       blue = b;
/*     */     }
/*     */     
/*  74 */     return argb(
/*  75 */         Math.min((int)(red / 0.7D), 255), 
/*  76 */         Math.min((int)(green / 0.7D), 255), 
/*  77 */         Math.min((int)(blue / 0.7D), 255), alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int darker(int color) {
/*  84 */     return darker(color, 0.7D);
/*     */   }
/*     */   
/*     */   public static int darker(int color, double factor) {
/*  88 */     return argb(
/*  89 */         Math.max((int)(red(color) * factor), 0), 
/*  90 */         Math.max((int)(green(color) * factor), 0), 
/*  91 */         Math.max((int)(blue(color) * factor), 0), 
/*  92 */         alpha(color));
/*     */   }
/*     */ 
/*     */   
/*     */   private static float[] rgbToXyz(int rgb) {
/*  97 */     float r = red(rgb) / 255.0F;
/*  98 */     float g = green(rgb) / 255.0F;
/*  99 */     float b = blue(rgb) / 255.0F;
/*     */     
/* 101 */     if (r > 0.04045F) {
/* 102 */       r = (float)Math.pow(((r + 0.055F) / 1.055F), 2.4000000953674316D);
/*     */     } else {
/* 104 */       r /= 12.92F;
/*     */     } 
/*     */     
/* 107 */     if (g > 0.04045F) {
/* 108 */       g = (float)Math.pow(((g + 0.055F) / 1.055F), 2.4000000953674316D);
/*     */     } else {
/* 110 */       g /= 12.92F;
/*     */     } 
/*     */     
/* 113 */     if (b > 0.04045D) {
/* 114 */       b = (float)Math.pow(((b + 0.055F) / 1.055F), 2.4000000953674316D);
/*     */     } else {
/* 116 */       b /= 12.92F;
/*     */     } 
/*     */     
/* 119 */     float x = 100.0F * (r * 0.4124F + g * 0.3576F + b * 0.1805F);
/* 120 */     float y = 100.0F * (r * 0.2126F + g * 0.7152F + b * 0.0722F);
/* 121 */     float z = 100.0F * (r * 0.0193F + g * 0.1192F + b * 0.9505F);
/*     */     
/* 123 */     return new float[] { x, y, z };
/*     */   }
/*     */   
/*     */   private static float[] xyzToLab(float[] xyz) {
/* 127 */     float x = xyz[0] / 95.047F;
/* 128 */     float y = xyz[1] / 100.0F;
/* 129 */     float z = xyz[2] / 108.883F;
/*     */     
/* 131 */     if (x > 0.008856F) {
/* 132 */       x = (float)Math.pow(x, 0.3333333432674408D);
/*     */     } else {
/* 134 */       x = 7.787F * x + 0.13793103F;
/*     */     } 
/*     */     
/* 137 */     if (y > 0.008856F) {
/* 138 */       y = (float)Math.pow(y, 0.3333333432674408D);
/*     */     } else {
/* 140 */       y = 7.787F * y + 0.13793103F;
/*     */     } 
/*     */     
/* 143 */     if (z > 0.008856D) {
/* 144 */       z = (float)Math.pow(z, 0.3333333432674408D);
/*     */     } else {
/* 146 */       z = 7.787F * z + 0.13793103F;
/*     */     } 
/*     */     
/* 149 */     float l = 116.0F * y - 16.0F;
/* 150 */     float a = 500.0F * (x - y);
/* 151 */     float b = 200.0F * (y - z);
/*     */     
/* 153 */     return new float[] { l, a, b };
/*     */   }
/*     */   
/*     */   private static float labDifference(float[] lab1, float[] lab2) {
/* 157 */     float c1 = (float)Math.sqrt((lab1[1] * lab1[1] + lab1[2] * lab1[2]));
/* 158 */     float c2 = (float)Math.sqrt((lab2[1] * lab2[1] + lab2[2] * lab2[2]));
/*     */     
/* 160 */     float dc = c1 - c2;
/* 161 */     float dl = lab1[0] - lab2[0];
/* 162 */     float da = lab1[1] - lab2[1];
/* 163 */     float db = lab1[2] - lab2[2];
/*     */     
/* 165 */     float dh = (float)Math.sqrt((da * da + db * db - dc * dc));
/* 166 */     float a = dl;
/* 167 */     float b = dc / (1.0F + 0.045F * c1);
/* 168 */     float c = dh / (1.0F + 0.015F * c1);
/*     */     
/* 170 */     return (float)Math.sqrt((a * a + b * b + c * c));
/*     */   }
/*     */   
/*     */   public static float difference(int rgb1, int rgb2) {
/* 174 */     return labDifference(xyzToLab(rgbToXyz(rgb1)), xyzToLab(rgbToXyz(rgb2)));
/*     */   }
/*     */   
/*     */   private static float[] rgbToHsv(int c) {
/* 178 */     float r = red(c) / 255.0F;
/* 179 */     float g = green(c) / 255.0F;
/* 180 */     float b = blue(c) / 255.0F;
/*     */     
/* 182 */     float vMin = Math.min(r, Math.min(g, b));
/* 183 */     float vMax = Math.max(r, Math.max(g, b));
/* 184 */     float delta = vMax - vMin;
/*     */     
/* 186 */     float h = 0.0F;
/* 187 */     float s = 0.0F;
/* 188 */     float v = 100.0F * vMax;
/*     */     
/* 190 */     if (delta > 0.0F) {
/* 191 */       s = 100.0F * delta / vMax;
/*     */       
/* 193 */       if (r == vMax) {
/* 194 */         h = (g - b) / delta;
/* 195 */       } else if (g == vMax) {
/* 196 */         h = 2.0F + (b - r) / delta;
/* 197 */       } else if (b == vMax) {
/* 198 */         h = 4.0F + (r - g) / delta;
/*     */       } 
/*     */       
/* 201 */       h *= 60.0F;
/*     */       
/* 203 */       if (h < 0.0F) {
/* 204 */         h += 360.0F;
/*     */       }
/*     */     } 
/* 207 */     return new float[] { h, s, v };
/*     */   }
/*     */   
/*     */   private static int hsvToRgb(float[] hsv) {
/* 211 */     float r, g, b, h = hsv[0] / 60.0F;
/* 212 */     float s = hsv[1] / 100.0F;
/* 213 */     float v = hsv[2] / 100.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (s == 0.0F)
/* 220 */     { r = v;
/* 221 */       g = v;
/* 222 */       b = v; }
/*     */     else
/* 224 */     { if (h == 6.0F) {
/* 225 */         h = 0.0F;
/*     */       }
/*     */       
/* 228 */       int i = (int)h;
/* 229 */       float f = h - i;
/* 230 */       float p = v * (1.0F - s);
/* 231 */       float q = v * (1.0F - s * f);
/* 232 */       float t = v * (1.0F - s * (1.0F - f));
/*     */       
/* 234 */       switch (i)
/*     */       { case 0:
/* 236 */           r = v;
/* 237 */           g = t;
/* 238 */           b = p;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 268 */           r *= 255.0F;
/* 269 */           g *= 255.0F;
/* 270 */           b *= 255.0F;
/*     */           
/* 272 */           return rgb((int)r, (int)g, (int)b);case 1: r = q; g = v; b = p; r *= 255.0F; g *= 255.0F; b *= 255.0F; return rgb((int)r, (int)g, (int)b);case 2: r = p; g = v; b = t; r *= 255.0F; g *= 255.0F; b *= 255.0F; return rgb((int)r, (int)g, (int)b);case 3: r = p; g = q; b = v; r *= 255.0F; g *= 255.0F; b *= 255.0F; return rgb((int)r, (int)g, (int)b);case 4: r = t; g = p; b = v; r *= 255.0F; g *= 255.0F; b *= 255.0F; return rgb((int)r, (int)g, (int)b); }  r = v; g = p; b = q; }  r *= 255.0F; g *= 255.0F; b *= 255.0F; return rgb((int)r, (int)g, (int)b);
/*     */   }
/*     */   
/*     */   public static int grayscale(int c) {
/* 276 */     float[] hsv = rgbToHsv(c);
/* 277 */     hsv[1] = 0.0F;
/* 278 */     hsv[2] = 100.0F * (0.21F * red(c) / 255.0F + 0.72F * green(c) / 255.0F + 0.07F * blue(c) / 255.0F);
/* 279 */     return hsvToRgb(hsv);
/*     */   }
/*     */   
/*     */   public static int sweepColor(int color1, int color2) {
/* 283 */     int r1 = red(color1);
/* 284 */     int g1 = green(color1);
/* 285 */     int b1 = blue(color1);
/* 286 */     int r2 = red(color2);
/* 287 */     int g2 = green(color2);
/* 288 */     int b2 = blue(color2);
/*     */ 
/*     */     
/* 291 */     float p = (20 + Math.abs((int)(0.2D * Math.abs(System.currentTimeMillis()) % 120.0D) - 60)) / 100.0F;
/*     */     
/* 293 */     int r = r1 + (int)(p * (r2 - r1));
/* 294 */     int g = g1 + (int)(p * (g2 - g1));
/* 295 */     int b = b1 + (int)(p * (b2 - b1));
/*     */     
/* 297 */     return rgb(r, g, b);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\GLColor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */