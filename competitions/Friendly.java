/*    */ package com.ygames.ysoccer.competitions;
/*    */ 
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.match.Match;
/*    */ import com.ygames.ysoccer.match.MatchSettings;
/*    */ import com.ygames.ysoccer.match.SceneSettings;
/*    */ 
/*    */ public class Friendly extends Competition {
/*    */   private Match match;
/*    */   
/*    */   public Friendly() {
/* 12 */     super(Competition.Type.FRIENDLY);
/* 13 */     this.name = Assets.strings.get("FRIENDLY");
/* 14 */     this.match = new Match();
/* 15 */     this.numberOfTeams = 2;
/* 16 */     this.weather = Competition.Weather.BY_PITCH_TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Match getMatch() {
/* 21 */     return this.match;
/*    */   }
/*    */ 
/*    */   
/*    */   public SceneSettings.Time getTime() {
/* 26 */     return MatchSettings.randomTime();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\Friendly.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */