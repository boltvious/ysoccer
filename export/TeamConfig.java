/*    */ package com.ygames.ysoccer.export;
/*    */ 
/*    */ import com.badlogic.gdx.files.FileHandle;
/*    */ import com.badlogic.gdx.utils.Json;
/*    */ import com.badlogic.gdx.utils.JsonValue;
/*    */ 
/*    */ public class TeamConfig
/*    */   implements Json.Serializable {
/*    */   public FileHandle sourceFile;
/*    */   public String path;
/*    */   public int country;
/*    */   public int gtn;
/*    */   public int division;
/*    */   
/*    */   public TeamConfig() {
/* 16 */     this.sourceFile = null;
/* 17 */     this.path = null;
/* 18 */     this.country = 0;
/* 19 */     this.gtn = 0;
/* 20 */     this.division = -1;
/*    */   }
/*    */   
/*    */   public TeamConfig(String path, int country, int gtn, int division) {
/* 24 */     this.sourceFile = null;
/* 25 */     this.path = path;
/* 26 */     this.country = country;
/* 27 */     this.gtn = gtn;
/* 28 */     this.division = division;
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(Json json, JsonValue jsonData) {
/* 33 */     json.readFields(this, jsonData);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(Json json) {
/* 38 */     json.writeValue("path", this.path);
/* 39 */     json.writeValue("country", Integer.valueOf(this.country));
/* 40 */     json.writeValue("gtn", Integer.valueOf(this.gtn));
/* 41 */     json.writeValue("division", Integer.valueOf(this.division));
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\export\TeamConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */