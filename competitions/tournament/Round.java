/*    */ package com.ygames.ysoccer.competitions.tournament;
/*    */ 
/*    */ import com.badlogic.gdx.utils.Json;
/*    */ 
/*    */ public abstract class Round {
/*    */   public Tournament tournament;
/*    */   public String name;
/*    */   public final Type type;
/*    */   public int numberOfTeams;
/*    */   public boolean seeded;
/*    */   
/* 12 */   public enum Type { GROUPS, KNOCKOUT; }
/*    */   
/* 14 */   public enum ExtraTime { OFF, ON, IF_REPLAY; }
/*    */   
/* 16 */   public enum Penalties { OFF, ON, IF_REPLAY; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Round(Type type) {
/* 25 */     this.type = type;
/*    */   }
/*    */   
/*    */   public void read(Json json, JsonValue jsonData) {
/* 29 */     this.name = jsonData.getString("name");
/* 30 */     this.numberOfTeams = jsonData.getInt("numberOfTeams");
/* 31 */     this.seeded = jsonData.getBoolean("seeded");
/*    */   }
/*    */   
/*    */   public void write(Json json) {
/* 35 */     json.writeValue("name", this.name);
/* 36 */     json.writeValue("numberOfTeams", Integer.valueOf(this.numberOfTeams));
/* 37 */     json.writeValue("seeded", Boolean.valueOf(this.seeded));
/*    */   }
/*    */   
/*    */   public void setTournament(Tournament tournament) {
/* 41 */     this.tournament = tournament;
/*    */   }
/*    */   
/*    */   protected abstract void start(ArrayList<Integer> paramArrayList);
/*    */   
/*    */   public abstract void restart();
/*    */   
/*    */   public abstract boolean isPreset();
/*    */   
/*    */   public abstract void clear();
/*    */   
/*    */   public abstract Match getMatch();
/*    */   
/*    */   public abstract void nextMatch();
/*    */   
/*    */   protected abstract String nextMatchLabel();
/*    */   
/*    */   protected abstract boolean nextMatchOnHold();
/*    */   
/*    */   public abstract boolean isEnded();
/*    */   
/*    */   public abstract void generateResult();
/*    */   
/*    */   protected abstract boolean playExtraTime();
/*    */   
/*    */   protected abstract boolean playPenalties();
/*    */   
/*    */   protected abstract String getMenuTitle();
/*    */   
/*    */   public static String getExtraTimeLabel(ExtraTime extraTime) {
/* 71 */     switch (extraTime) {
/*    */       case OFF:
/* 73 */         return "EXTRA TIME.OFF";
/*    */       case ON:
/* 75 */         return "EXTRA TIME.ON";
/*    */       case IF_REPLAY:
/* 77 */         return "EXTRA TIME.IF REPLAY";
/*    */     } 
/* 79 */     throw new GdxRuntimeException("Wrong ExtraTime value");
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getPenaltiesLabel(Penalties penalties) {
/* 84 */     switch (penalties) {
/*    */       case OFF:
/* 86 */         return "PENALTIES.OFF";
/*    */       case ON:
/* 88 */         return "PENALTIES.ON";
/*    */       case IF_REPLAY:
/* 90 */         return "PENALTIES.IF REPLAY";
/*    */     } 
/* 92 */     throw new GdxRuntimeException("Wrong Penalties value");
/*    */   }
/*    */   
/*    */   protected abstract void matchCompleted();
/*    */   
/*    */   protected abstract void matchInterrupted();
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\tournament\Round.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */