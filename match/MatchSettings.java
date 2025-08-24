/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.ygames.ysoccer.competitions.Competition;
/*    */ import com.ygames.ysoccer.framework.EMath;
/*    */ import com.ygames.ysoccer.framework.Settings;
/*    */ 
/*    */ public class MatchSettings
/*    */   extends SceneSettings
/*    */ {
/*    */   int matchLength;
/*    */   public int substitutions;
/*    */   public int benchSize;
/*    */   boolean autoReplays;
/*    */   public boolean radar;
/*    */   boolean crowdChants;
/*    */   
/*    */   public MatchSettings(Competition competition, Settings gameSettings) {
/* 18 */     super(gameSettings);
/*    */     
/* 20 */     if (competition.type == Competition.Type.FRIENDLY || competition.category == Competition.Category.DIY_COMPETITION) {
/* 21 */       this.matchLength = gameSettings.matchLength;
/*    */     } else {
/* 23 */       this.matchLength = Settings.matchLengths[0].intValue();
/*    */     } 
/* 25 */     this.time = competition.getTime();
/* 26 */     this.pitchType = competition.getPitchType();
/* 27 */     this.weatherMaxStrength = gameSettings.weatherMaxStrength;
/* 28 */     for (int i = EMath.rand(0, 2 + 4 * this.weatherMaxStrength); i >= 0; i--) {
/* 29 */       rotateWeather();
/*    */     }
/* 31 */     this.substitutions = competition.substitutions;
/* 32 */     this.benchSize = competition.benchSize;
/* 33 */     this.autoReplays = gameSettings.autoReplays;
/* 34 */     this.radar = gameSettings.radar;
/* 35 */     this.crowdChants = true;
/* 36 */     this.commentary = gameSettings.commentary;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchSettings.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */