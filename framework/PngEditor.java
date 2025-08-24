/*     */ package com.ygames.ysoccer.framework;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.List;
/*     */ 
/*     */ class PngEditor
/*     */ {
/*  14 */   private static int[] crcTable = new int[] { 0, 1996959894, -301047508, -1727442502, 124634137, 1886057615, -379345611, -1637575261, 249268274, 2044508324, -522852066, -1747789432, 162941995, 2125561021, -407360249, -1866523247, 498536548, 1789927666, -205950648, -2067906082, 450548861, 1843258603, -187386543, -2083289657, 325883990, 1684777152, -43845254, -1973040660, 335633487, 1661365465, -99664541, -1928851979, 997073096, 1281953886, -715111964, -1570279054, 1006888145, 1258607687, -770865667, -1526024853, 901097722, 1119000684, -608450090, -1396901568, 853044451, 1172266101, -589951537, -1412350631, 651767980, 1373503546, -925412992, -1076862698, 565507253, 1454621731, -809855591, -1195530993, 671266974, 1594198024, -972236366, -1324619484, 795835527, 1483230225, -1050600021, -1234817731, 1994146192, 31158534, -1731059524, -271249366, 1907459465, 112637215, -1614814043, -390540237, 2013776290, 251722036, -1777751922, -519137256, 2137656763, 141376813, -1855689577, -429695999, 1802195444, 476864866, -2056965928, -228458418, 1812370925, 453092731, -2113342271, -183516073, 1706088902, 314042704, -1950435094, -54949764, 1658658271, 366619977, -1932296973, -69972891, 1303535960, 984961486, -1547960204, -725929758, 1256170817, 1037604311, -1529756563, -740887301, 1131014506, 879679996, -1385723834, -631195440, 1141124467, 855842277, -1442165665, -586318647, 1342533948, 654459306, -1106571248, -921952122, 1466479909, 544179635, -1184443383, -832445281, 1591671054, 702138776, -1328506846, -942167884, 1504918807, 783551873, -1212326853, -1061524307, -306674912, -1698712650, 62317068, 1957810842, -355121351, -1647151185, 81470997, 1943803523, -480048366, -1805370492, 225274430, 2053790376, -468791541, -1828061283, 167816743, 2097651377, -267414716, -2029476910, 503444072, 1762050814, -144550051, -2140837941, 426522225, 1852507879, -19653770, -1982649376, 282753626, 1742555852, -105259153, -1900089351, 397917763, 1622183637, -690576408, -1580100738, 953729732, 1340076626, -776247311, -1497606297, 1068828381, 1219638859, -670225446, -1358292148, 906185462, 1090812512, -547295293, -1469587627, 829329135, 1181335161, -882789492, -1134132454, 628085408, 1382605366, -871598187, -1156888829, 570562233, 1426400815, -977650754, -1296233688, 733239954, 1555261956, -1026031705, -1244606671, 752459403, 1541320221, -1687895376, -328994266, 1969922972, 40735498, -1677130071, -351390145, 1913087877, 83908371, -1782625662, -491226604, 2075208622, 213261112, -1831694693, -438977011, 2094854071, 198958881, -2032938284, -237706686, 1759359992, 534414190, -2118248755, -155638181, 1873836001, 414664567, -2012718362, -15766928, 1711684554, 285281116, -1889165569, -127750551, 1634467795, 376229701, -1609899400, -686959890, 1308918612, 956543938, -1486412191, -799009033, 1231636301, 1047427035, -1362007478, -640263460, 1088359270, 936918000, -1447252397, -558129467, 1202900863, 817233897, -1111625188, -893730166, 1404277552, 615818150, -1160759803, -841546093, 1423857449, 601450431, -1285129682, -1000256840, 1567103746, 711928724, -1274298825, -1022587231, 1510334235, 755167117 };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteArrayInputStream swapPalette(InputStream in, InputStream paletteFile) throws IOException {
/*  52 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/*     */     
/*  54 */     byte[] buffer = new byte[16384];
/*     */     
/*     */     int len;
/*  57 */     while ((len = in.read(buffer, 0, buffer.length)) != -1) {
/*  58 */       os.write(buffer, 0, len);
/*     */     }
/*     */     
/*  61 */     os.flush();
/*     */     
/*  63 */     byte[] bytes = os.toByteArray();
/*     */ 
/*     */     
/*  66 */     int[] palette = new int[256];
/*     */     try {
/*  68 */       DataInputStream is = new DataInputStream(paletteFile);
/*  69 */       BufferedReader br = new BufferedReader(new InputStreamReader(is));
/*     */ 
/*     */ 
/*     */       
/*  73 */       br.readLine();
/*     */ 
/*     */       
/*  76 */       br.readLine();
/*     */ 
/*     */       
/*  79 */       String line = br.readLine();
/*     */       
/*  81 */       int c = Integer.parseInt(line);
/*     */ 
/*     */       
/*  84 */       for (int k = 0; k < c; k++) {
/*  85 */         line = br.readLine();
/*     */         
/*  87 */         String[] colors = line.split("[ ]+");
/*     */         
/*  89 */         int r = Integer.parseInt(colors[0]);
/*  90 */         int g = Integer.parseInt(colors[1]);
/*  91 */         int b = Integer.parseInt(colors[2]);
/*     */         
/*  93 */         palette[k] = GLColor.rgb(r, g, b);
/*     */       } 
/*     */       
/*  96 */       is.close();
/*  97 */     } catch (Exception e) {
/*  98 */       throw new RuntimeException("Couldn't load palette!", e);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     int paletteOffset = searchPaletteOffset(bytes);
/*     */ 
/*     */     
/* 105 */     int dataSize = 0; int i;
/* 106 */     for (i = 0; i < 4; i++) {
/* 107 */       dataSize = 256 * dataSize + (bytes[paletteOffset + i] & 0xFF);
/*     */     }
/*     */ 
/*     */     
/* 111 */     for (i = 0; i < dataSize / 3; i++) {
/* 112 */       bytes[paletteOffset + 8 + 3 * i] = (byte)GLColor.red(palette[i]);
/* 113 */       bytes[paletteOffset + 8 + 3 * i + 1] = (byte)GLColor.green(palette[i]);
/* 114 */       bytes[paletteOffset + 8 + 3 * i + 2] = (byte)GLColor.blue(palette[i]);
/*     */     } 
/*     */ 
/*     */     
/* 118 */     int crc32 = getCrc(bytes, paletteOffset + 4, dataSize + 4);
/*     */     
/* 120 */     for (int j = 0; j < 4; j++) {
/* 121 */       bytes[paletteOffset + 8 + dataSize + j] = (byte)(crc32 >> 8 * (3 - j) & 0xFF);
/*     */     }
/*     */     
/* 124 */     return new ByteArrayInputStream(bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static ByteArrayInputStream editPalette(InputStream in, List<RgbPair> rgbPairs) throws IOException {
/* 131 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/*     */     
/* 133 */     byte[] buffer = new byte[16384];
/*     */     
/*     */     int len;
/* 136 */     while ((len = in.read(buffer, 0, buffer.length)) != -1) {
/* 137 */       os.write(buffer, 0, len);
/*     */     }
/*     */     
/* 140 */     os.flush();
/*     */     
/* 142 */     byte[] bytes = os.toByteArray();
/*     */ 
/*     */     
/* 145 */     int paletteOffset = searchPaletteOffset(bytes);
/*     */ 
/*     */     
/* 148 */     int dataSize = 0; int i;
/* 149 */     for (i = 0; i < 4; i++) {
/* 150 */       dataSize = 256 * dataSize + (bytes[paletteOffset + i] & 0xFF);
/*     */     }
/*     */ 
/*     */     
/* 154 */     for (i = 0; i < dataSize / 3; i++) {
/*     */       
/* 156 */       int r = bytes[paletteOffset + 8 + 3 * i] & 0xFF;
/* 157 */       int g = bytes[paletteOffset + 8 + 3 * i + 1] & 0xFF;
/* 158 */       int b = bytes[paletteOffset + 8 + 3 * i + 2] & 0xFF;
/*     */       
/* 160 */       int len2 = rgbPairs.size();
/* 161 */       for (int k = 0; k < len2; k++) {
/* 162 */         int color = GLColor.rgb(r, g, b);
/* 163 */         RgbPair pair = rgbPairs.get(k);
/* 164 */         if (color == pair.rgbOld) {
/* 165 */           bytes[paletteOffset + 8 + 3 * i] = (byte)GLColor.red(pair.rgbNew);
/* 166 */           bytes[paletteOffset + 8 + 3 * i + 1] = (byte)GLColor.green(pair.rgbNew);
/* 167 */           bytes[paletteOffset + 8 + 3 * i + 2] = (byte)GLColor.blue(pair.rgbNew);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 173 */     int crc32 = getCrc(bytes, paletteOffset + 4, dataSize + 4);
/*     */     
/* 175 */     for (int j = 0; j < 4; j++) {
/* 176 */       bytes[paletteOffset + 8 + dataSize + j] = (byte)(crc32 >> 8 * (3 - j) & 0xFF);
/*     */     }
/*     */     
/* 179 */     return new ByteArrayInputStream(bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int searchPaletteOffset(byte[] bytes) throws IOException {
/* 185 */     int i0 = bytes[0] & 0xFF;
/* 186 */     int i1 = bytes[1] & 0xFF;
/* 187 */     int i2 = bytes[2] & 0xFF;
/* 188 */     int i3 = bytes[3] & 0xFF;
/*     */     
/* 190 */     boolean paletteFound = false;
/*     */     int paletteOffset;
/* 192 */     for (paletteOffset = 4; paletteOffset < bytes.length; paletteOffset++) {
/*     */       
/* 194 */       if (i0 == 80 && i1 == 76 && i2 == 84 && i3 == 69) {
/* 195 */         paletteFound = true;
/*     */         break;
/*     */       } 
/* 198 */       i0 = i1;
/* 199 */       i1 = i2;
/* 200 */       i2 = i3;
/* 201 */       i3 = bytes[paletteOffset] & 0xFF;
/*     */     } 
/*     */     
/* 204 */     if (!paletteFound) {
/* 205 */       throw new IOException("Palette not found inside image!");
/*     */     }
/*     */     
/* 208 */     paletteOffset -= 8;
/*     */     
/* 210 */     return paletteOffset;
/*     */   }
/*     */   
/*     */   private static int getCrc(byte[] bytes, int start, int length) {
/* 214 */     int crc = -1;
/*     */     
/* 216 */     for (int i = start; i < start + length; i++) {
/* 217 */       byte b = bytes[i];
/* 218 */       crc = crc >>> 8 ^ crcTable[(crc ^ b) & 0xFF];
/*     */     } 
/*     */ 
/*     */     
/* 222 */     crc ^= 0xFFFFFFFF;
/*     */     
/* 224 */     return crc;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\PngEditor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */