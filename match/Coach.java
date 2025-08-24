/*    */ package com.ygames.ysoccer.match;public class Coach implements Json.Serializable { public String name;
/*    */   public String nationality;
/*    */   public Status status;
/*    */   public Team team;
/*    */   public int timer;
/*    */   public float x;
/*    */   public float y;
/*    */   public int fmx;
/*    */   
/* 10 */   enum Status { BENCH, STAND, CALL, LOOK_BENCH, SWAP; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Coach() {
/* 22 */     this.status = Status.BENCH;
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(Json json, JsonValue jsonData) {
/* 27 */     json.readFields(this, jsonData);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(Json json) {
/* 32 */     json.writeValue("name", this.name);
/* 33 */     json.writeValue("nationality", this.nationality);
/*    */   }
/*    */   
/*    */   public void update() {
/* 37 */     if (this.timer > 0) {
/* 38 */       this.timer--;
/*    */     }
/*    */     
/* 41 */     switch (this.status) {
/*    */       case BENCH:
/* 43 */         this.x = -544.0F;
/* 44 */         this.fmx = 0;
/*    */         break;
/*    */       
/*    */       case STAND:
/* 48 */         this.x = -536.0F;
/* 49 */         this.fmx = 0;
/*    */         break;
/*    */       
/*    */       case CALL:
/* 53 */         this.fmx = (System.currentTimeMillis() % 400L > 200L) ? 2 : 1;
/* 54 */         if (this.timer == 0) {
/* 55 */           this.status = Status.STAND;
/*    */         }
/*    */         break;
/*    */       
/*    */       case LOOK_BENCH:
/* 60 */         this.fmx = 3;
/* 61 */         if (this.timer == 0) {
/* 62 */           this.status = Status.STAND;
/*    */         }
/*    */         break;
/*    */       
/*    */       case SWAP:
/* 67 */         this.fmx = (System.currentTimeMillis() % 400L > 200L) ? 5 : 4;
/* 68 */         if (this.timer == 0)
/* 69 */           this.status = Status.STAND; 
/*    */         break;
/*    */     } 
/*    */   } }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Coach.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */