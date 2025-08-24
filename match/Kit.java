/*     */ package com.ygames.ysoccer.match;
/*     */ import com.ygames.ysoccer.framework.GLColor;
/*     */ import com.ygames.ysoccer.framework.RgbPair;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Kit implements Json.Serializable {
/*     */   public String style;
/*     */   public int shirt1;
/*     */   public int shirt2;
/*     */   public int shirt3;
/*     */   public int shorts;
/*     */   public int socks;
/*     */   
/*     */   public enum Field {
/*  15 */     SHIRT1, SHIRT2, SHIRT3, SHORTS, SOCKS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  24 */   public static int[] colors = new int[] { 11514553, 14674155, 1973790, 16085804, 16129052, 3158977, 10040115, 4702187, 3196204, 15265583, 6103961, 16096993, 753109, 5276600, 4288569, 3023994, 10686994, 13631727, 8119011, 8519441, 16765440, 9737364, 13288573 };
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
/*     */   public Kit() {}
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
/*     */   public Kit(String style, int shirt1, int shirt2, int shirt3, int shorts, int socks) {
/*  54 */     this.style = style;
/*  55 */     this.shirt1 = shirt1;
/*  56 */     this.shirt2 = shirt2;
/*  57 */     this.shirt3 = shirt3;
/*  58 */     this.shorts = shorts;
/*  59 */     this.socks = socks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/*  64 */     json.writeValue("style", this.style);
/*  65 */     json.writeValue("shirt1", GLColor.toHexString(this.shirt1));
/*  66 */     json.writeValue("shirt2", GLColor.toHexString(this.shirt2));
/*  67 */     json.writeValue("shirt3", GLColor.toHexString(this.shirt3));
/*  68 */     json.writeValue("shorts", GLColor.toHexString(this.shorts));
/*  69 */     json.writeValue("socks", GLColor.toHexString(this.socks));
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonMap) {
/*  74 */     this.style = jsonMap.getString("style");
/*  75 */     this.shirt1 = GLColor.valueOf(jsonMap.getString("shirt1"));
/*  76 */     this.shirt2 = GLColor.valueOf(jsonMap.getString("shirt2"));
/*  77 */     this.shirt3 = GLColor.valueOf(jsonMap.getString("shirt3", "#000000"));
/*  78 */     this.shorts = GLColor.valueOf(jsonMap.getString("shorts"));
/*  79 */     this.socks = GLColor.valueOf(jsonMap.getString("socks"));
/*     */   }
/*     */   
/*     */   public TextureRegion loadImage() {
/*  83 */     List<RgbPair> rgbPairs = new ArrayList<>();
/*  84 */     addKitColors(rgbPairs);
/*  85 */     return Assets.loadTextureRegion("images/kit/" + this.style + ".png", rgbPairs);
/*     */   }
/*     */   
/*     */   public TextureRegion loadLogo() {
/*  89 */     List<RgbPair> rgbPairs = new ArrayList<>();
/*  90 */     addKitColors(rgbPairs);
/*  91 */     return Assets.loadTextureRegion("images/logo/" + this.style + ".png", rgbPairs);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addKitColors(List<RgbPair> rgbPairs) {
/*  97 */     rgbPairs.add(new RgbPair(14811168, this.shirt1));
/*  98 */     rgbPairs.add(new RgbPair(12517403, GLColor.darker(this.shirt1, 0.9D)));
/*  99 */     rgbPairs.add(new RgbPair(10223638, GLColor.darker(this.shirt1, 0.8D)));
/* 100 */     rgbPairs.add(new RgbPair(7929873, GLColor.darker(this.shirt1, 0.7D)));
/*     */ 
/*     */     
/* 103 */     rgbPairs.add(new RgbPair(131014, this.shirt2));
/* 104 */     rgbPairs.add(new RgbPair(51099, GLColor.darker(this.shirt2, 0.9D)));
/* 105 */     rgbPairs.add(new RgbPair(35692, GLColor.darker(this.shirt2, 0.8D)));
/* 106 */     rgbPairs.add(new RgbPair(27218, GLColor.darker(this.shirt2, 0.7D)));
/*     */ 
/*     */     
/* 109 */     rgbPairs.add(new RgbPair(13369599, this.shirt3));
/* 110 */     rgbPairs.add(new RgbPair(8781991, GLColor.darker(this.shirt3, 0.9D)));
/* 111 */     rgbPairs.add(new RgbPair(6357113, GLColor.darker(this.shirt3, 0.8D)));
/* 112 */     rgbPairs.add(new RgbPair(4325458, GLColor.darker(this.shirt3, 0.7D)));
/*     */ 
/*     */     
/* 115 */     rgbPairs.add(new RgbPair(16187136, this.shorts));
/* 116 */     rgbPairs.add(new RgbPair(13489152, GLColor.darker(this.shorts, 0.9D)));
/* 117 */     rgbPairs.add(new RgbPair(10725632, GLColor.darker(this.shorts, 0.8D)));
/* 118 */     rgbPairs.add(new RgbPair(8027648, GLColor.darker(this.shorts, 0.7D)));
/*     */ 
/*     */     
/* 121 */     rgbPairs.add(new RgbPair(38894, this.socks));
/* 122 */     rgbPairs.add(new RgbPair(35030, GLColor.darker(this.socks, 0.9D)));
/* 123 */     rgbPairs.add(new RgbPair(31167, GLColor.darker(this.socks, 0.8D)));
/* 124 */     rgbPairs.add(new RgbPair(27303, GLColor.darker(this.socks, 0.7D)));
/*     */   }
/*     */   
/*     */   public static float colorDifference(Kit homeKit, Kit awayKit) {
/* 128 */     int homeColor1 = homeKit.shirt1;
/* 129 */     int homeColor2 = homeKit.shirt2;
/* 130 */     int awayColor1 = awayKit.shirt1;
/* 131 */     int awayColor2 = awayKit.shirt2;
/* 132 */     float differencePair = GLColor.difference(homeColor1, awayColor1) + GLColor.difference(homeColor2, awayColor2);
/* 133 */     float differenceSwap = GLColor.difference(homeColor1, awayColor2) + GLColor.difference(homeColor2, awayColor1);
/* 134 */     return Math.min(differencePair, differenceSwap);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Kit.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */