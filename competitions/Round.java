/*     */ package com.ygames.ysoccer.competitions;
/*     */ 
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ 
/*     */ public class Round implements Json.Serializable {
/*     */   Cup cup;
/*     */   public String name;
/*     */   public int numberOfLegs;
/*     */   public ExtraTime extraTime;
/*     */   public Penalties penalties;
/*     */   public ArrayList<Leg> legs;
/*     */   
/*  13 */   public enum ExtraTime { OFF, ON, IF_REPLAY; }
/*     */   
/*  15 */   public enum Penalties { OFF, ON, IF_REPLAY; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Round() {
/*  25 */     this.numberOfLegs = 1;
/*  26 */     this.extraTime = ExtraTime.ON;
/*  27 */     this.penalties = Penalties.ON;
/*  28 */     this.legs = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/*  33 */     this.name = jsonData.getString("name");
/*  34 */     this.numberOfLegs = jsonData.getInt("numberOfLegs");
/*  35 */     this.extraTime = (ExtraTime)json.readValue("extraTime", ExtraTime.class, jsonData);
/*  36 */     this.penalties = (Penalties)json.readValue("penalties", Penalties.class, jsonData);
/*     */     
/*  38 */     Match[][] legsArray = (Match[][])json.readValue("legs", Match[][].class, jsonData);
/*  39 */     if (legsArray != null) {
/*  40 */       for (Match[] matchesArray : legsArray) {
/*  41 */         Leg leg = new Leg(this);
/*  42 */         Collections.addAll(leg.matches, matchesArray);
/*  43 */         this.legs.add(leg);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/*  50 */     json.writeValue("name", this.name);
/*  51 */     json.writeValue("numberOfLegs", Integer.valueOf(this.numberOfLegs));
/*  52 */     json.writeValue("extraTime", this.extraTime);
/*  53 */     json.writeValue("penalties", this.penalties);
/*  54 */     json.writeArrayStart("legs");
/*  55 */     for (Leg leg : this.legs) {
/*  56 */       json.writeArrayStart();
/*  57 */       for (Match match : leg.matches) {
/*  58 */         json.writeValue(match, Match.class);
/*     */       }
/*  60 */       json.writeArrayEnd();
/*     */     } 
/*  62 */     json.writeArrayEnd();
/*     */   }
/*     */   
/*     */   public void setCup(Cup cup) {
/*  66 */     this.cup = cup;
/*     */   }
/*     */   
/*     */   public String getLegsLabel() {
/*  70 */     switch (this.numberOfLegs) {
/*     */       case 1:
/*  72 */         return "ONE LEG";
/*     */       
/*     */       case 2:
/*  75 */         return "TWO LEGS";
/*     */     } 
/*     */     
/*  78 */     throw new GdxRuntimeException("Wrong numberOfLegs value");
/*     */   }
/*     */ 
/*     */   
/*  82 */   private static String[] extraTimeLabels = new String[] { "EXTRA TIME.OFF", "EXTRA TIME.ON", "EXTRA TIME.IF REPLAY" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getExtraTimeLabel() {
/*  89 */     return extraTimeLabels[this.extraTime.ordinal()];
/*     */   }
/*     */   
/*  92 */   private static String[] penaltiesLabels = new String[] { "PENALTIES.OFF", "PENALTIES.ON", "PENALTIES.IF REPLAY" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPenaltiesLabel() {
/*  99 */     return penaltiesLabels[this.penalties.ordinal()];
/*     */   }
/*     */   
/*     */   public int getIndex() {
/* 103 */     return this.cup.rounds.indexOf(this);
/*     */   }
/*     */   
/*     */   public void newLeg() {
/* 107 */     Leg leg = new Leg(this);
/* 108 */     this.legs.add(leg);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\competitions\Round.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */