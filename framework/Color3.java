/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.badlogic.gdx.utils.Json;
/*    */ import com.badlogic.gdx.utils.JsonValue;
/*    */ 
/*    */ public class Color3
/*    */   implements Json.Serializable
/*    */ {
/*    */   public int color1;
/*    */   public int color2;
/*    */   public int color3;
/*    */   
/*    */   public Color3() {}
/*    */   
/*    */   public Color3(int color1, int color2, int color3) {
/* 16 */     this.color1 = color1;
/* 17 */     this.color2 = color2;
/* 18 */     this.color3 = color3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(Json json) {
/* 23 */     json.writeValue("color1", GLColor.toHexString(this.color1));
/* 24 */     json.writeValue("color2", GLColor.toHexString(this.color2));
/* 25 */     json.writeValue("color3", GLColor.toHexString(this.color3));
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(Json json, JsonValue jsonMap) {
/* 30 */     this.color1 = GLColor.valueOf(jsonMap.getString("color1"));
/* 31 */     this.color2 = GLColor.valueOf(jsonMap.getString("color2"));
/* 32 */     this.color3 = GLColor.valueOf(jsonMap.getString("color3"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\Color3.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */