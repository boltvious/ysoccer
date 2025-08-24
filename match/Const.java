/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ 
/*     */ 
/*     */ public class Const
/*     */ {
/*     */   static final int SECOND = 512;
/*     */   static final int BALL_R = 4;
/*  10 */   static float GRAVITY = 0.65F;
/*  11 */   static float AIR_FRICTION = 0.28F;
/*  12 */   static float SPIN_FACTOR = 12.0F;
/*  13 */   static float SPIN_DAMPENING = 7.0F;
/*  14 */   static float BOUNCE = 0.9F;
/*  15 */   static float PLAYER_RUN_ANIMATION = 0.18F;
/*  16 */   static float PASSING_THRESHOLD = 0.1F;
/*  17 */   static float PASSING_SPEED_FACTOR = 0.3F;
/*  18 */   static float SHOOTING_ANGLE_TOLERANCE = 22.5F;
/*     */   
/*     */   static final int REPLAY_DURATION = 8;
/*     */   
/*     */   static final int REPLAY_FRAMES = 512;
/*     */   static final int REPLAY_SUBFRAMES = 4096;
/*     */   static final int BALL_PREDICTION = 128;
/*  25 */   static int[][] goalsProbability = new int[][] { { 1000, 0, 0, 0, 0, 0, 0 }, { 870, 100, 25, 4, 1, 0, 0 }, { 730, 210, 50, 7, 2, 1, 0 }, { 510, 320, 140, 20, 6, 4, 0 }, { 390, 370, 180, 40, 10, 7, 3 }, { 220, 410, 190, 150, 15, 10, 5 }, { 130, 390, 240, 200, 18, 15, 7 }, { 40, 300, 380, 230, 25, 15, 10 }, { 20, 220, 240, 220, 120, 100, 80 }, { 10, 150, 190, 190, 170, 150, 140 }, { 0, 100, 150, 200, 200, 200, 150 } };
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
/*  39 */   public static String[] associations = new String[] { "AFG", "AIA", "ALB", "ALG", "AND", "ANG", "ARG", "ARM", "ARU", "ASA", "ATG", "AUS", "AUT", "AZE", "BAH", "BAN", "BDI", "BEL", "BEN", "BER", "BFA", "BHR", "BHU", "BIH", "BLR", "BLZ", "BOL", "BOT", "BRA", "BRB", "BRU", "BUL", "CAM", "CAN", "CAY", "CGO", "CHA", "CHI", "CHN", "CIV", "CMR", "COD", "COK", "COL", "COM", "CPV", "CRC", "CRO", "CTA", "CUB", "CUS", "CUW", "CYP", "CZE", "DEN", "DJI", "DMA", "DOM", "ECU", "EGY", "ENG", "EQG", "ERI", "ESP", "EST", "ETH", "FIJ", "FIN", "FRA", "FRO", "GAB", "GAM", "GEO", "GER", "GHA", "GNB", "GRE", "GRN", "GUA", "GUI", "GUM", "GUY", "HAI", "HKG", "HON", "HUN", "IDN", "IND", "IRL", "IRN", "IRQ", "ISL", "ISR", "ITA", "JAM", "JOR", "JPN", "KAZ", "KEN", "KGZ", "KOR", "KSA", "KUW", "LAO", "LBR", "LBY", "LCA", "LES", "LIB", "LIE", "LTU", "LUX", "LVA", "MAC", "MAD", "MAR", "MAS", "MDA", "MDV", "MEX", "MGL", "MKD", "MLI", "MLT", "MNE", "MOZ", "MRI", "MSR", "MTN", "MWI", "MYA", "NAM", "NCA", "NCL", "NED", "NEP", "NGA", "NIG", "NIR", "NOR", "NZL", "OMA", "PAK", "PAN", "PAR", "PER", "PHI", "PLE", "PNG", "POL", "POR", "PRK", "PUR", "QAT", "ROU", "RSA", "RUS", "RWA", "SAM", "SCO", "SDN", "SEN", "SEY", "SIN", "SKN", "SLE", "SLV", "SMR", "SOL", "SOM", "SRB", "SRI", "SSD", "STP", "SUI", "SUR", "SVK", "SVN", "SWE", "SWZ", "SYR", "TAH", "TAN", "TCA", "TGA", "THA", "TJK", "TKM", "TLS", "TOG", "TPE", "TRI", "TUN", "TUR", "UAE", "UGA", "UKR", "URU", "USA", "UZB", "VAN", "VEN", "VGB", "VIE", "VIN", "VIR", "WAL", "YEM", "ZAM", "ZIM" };
/*     */ 
/*     */   
/*     */   public static final int TEAM_SIZE = 11;
/*     */ 
/*     */   
/*     */   public static final int BASE_TEAM = 16;
/*     */ 
/*     */   
/*     */   public static final int FULL_TEAM = 26;
/*     */ 
/*     */   
/*     */   static final int GOAL_LINE = 640;
/*     */ 
/*     */   
/*     */   static final int TOUCH_LINE = 510;
/*     */ 
/*     */   
/*     */   static final int GOAL_AREA_W = 252;
/*     */   
/*     */   static final int GOAL_AREA_H = 58;
/*     */   
/*     */   static final int PENALTY_AREA_W = 572;
/*     */   
/*     */   static final int PENALTY_AREA_H = 174;
/*     */   
/*     */   static final int PENALTY_SPOT_Y = 524;
/*     */   
/*     */   static final float PENALTY_DISTANCE = 116.0F;
/*     */   
/*     */   static final float YARD = 9.666667F;
/*     */   
/*     */   static final float FREE_KICK_DISTANCE = 96.66667F;
/*     */   
/*     */   static final float DIRECT_SHOT_DISTANCE = 310.0F;
/*     */   
/*     */   static final int GOAL_BTM_X = -73;
/*     */   
/*     */   static final int GOAL_BTM_Y = 603;
/*     */   
/*     */   static final int BENCH_X = -544;
/*     */   
/*     */   static final int BENCH_Y_UP = -198;
/*     */   
/*     */   static final int BENCH_Y_DOWN = 38;
/*     */   
/*     */   static final int PLAYER_H = 22;
/*     */   
/*     */   static final int PLAYER_W = 12;
/*     */   
/*     */   static final int FIELD_XMIN = -565;
/*     */   
/*     */   static final int FIELD_XMAX = 572;
/*     */   
/*     */   static final int FIELD_YMIN = -700;
/*     */   
/*     */   static final int FIELD_YMAX = 698;
/*     */   
/*     */   static final int FLAGPOST_H = 21;
/*     */   
/*     */   static final int PITCH_W = 1700;
/*     */   
/*     */   static final int PITCH_H = 1800;
/*     */   
/*     */   static final int CENTER_X = 847;
/*     */   
/*     */   static final int CENTER_Y = 919;
/*     */   
/*     */   static final int JUMPER_X = 92;
/*     */   
/*     */   static final int JUMPER_Y = 684;
/*     */   
/*     */   static final int JUMPER_H = 40;
/*     */   
/*     */   static final int GOAL_DEPTH = 19;
/*     */   
/*     */   static final int POST_X = 71;
/*     */   
/*     */   static final int POST_R = 2;
/*     */   
/*     */   static final int CROSSBAR_H = 33;
/*     */   
/*     */   public static final int TACT_DX = 68;
/*     */   
/*     */   public static final int TACT_DY = 40;
/*     */   
/*     */   static final int BALL_ZONES = 35;
/*     */   
/*     */   static final int BALL_ZONE_DX = 206;
/*     */   
/*     */   static final int BALL_ZONE_DY = 184;
/*     */ 
/*     */   
/*     */   static boolean isInsidePenaltyArea(float x, float y, int ySide) {
/* 133 */     return (Math.abs(x) < 286.0F && 
/* 134 */       EMath.isIn(y, (ySide * 466), (ySide * 640)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isInsideGoalArea(float x, float y, int ySide) {
/* 141 */     return (Math.abs(x) < 126.0F && 
/* 142 */       EMath.isIn(y, (ySide * 582), (ySide * 640)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isInsideDirectShotArea(float x, float y, int ySide) {
/* 149 */     return (ySide * y < 640.0F && (
/* 150 */       EMath.dist(x, ySide * y, -71.0F, 640.0F) < 310.0F || 
/* 151 */       EMath.dist(x, ySide * y, 71.0F, 640.0F) < 310.0F));
/*     */   }
/*     */   
/*     */   static boolean seesTheGoal(float x, float y, float a) {
/* 155 */     int ySide = EMath.sgn(y);
/*     */     
/* 157 */     if (EMath.angleDiff(a, (ySide * 90)) > 90.0F) return false;
/*     */     
/* 159 */     float x0 = -59.0F;
/* 160 */     float y0 = (ySide * 640);
/* 161 */     float m0 = EMath.tan(a - ySide * SHOOTING_ANGLE_TOLERANCE);
/* 162 */     float s0 = EMath.sgn(EMath.cos(a - ySide * SHOOTING_ANGLE_TOLERANCE));
/* 163 */     float b0 = y0 - m0 * x0;
/* 164 */     float x1 = 59.0F;
/* 165 */     float y1 = (ySide * 640);
/* 166 */     float m1 = EMath.tan(a + ySide * SHOOTING_ANGLE_TOLERANCE);
/* 167 */     float s1 = EMath.sgn(EMath.cos(a + ySide * SHOOTING_ANGLE_TOLERANCE));
/* 168 */     float b1 = y1 - m1 * x1;
/* 169 */     return (s0 * ySide * y < s0 * ySide * (m0 * x + b0) && s1 * ySide * y > s1 * ySide * (m1 * x + b1));
/*     */   }
/*     */   
/*     */   static boolean isInsideGoal(float x, float y) {
/* 173 */     return (Math.abs(x) < 71.0F && 
/* 174 */       Math.abs(y) > 640.0F && 
/* 175 */       Math.abs(y) < 659.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Const.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */