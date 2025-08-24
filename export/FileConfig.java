/*    */ package com.ygames.ysoccer.export;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FileConfig
/*    */ {
/*    */   public String filename;
/*  9 */   public List<TeamConfig> teams = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public FileConfig() {}
/*    */   
/*    */   public FileConfig(String filename) {
/* 15 */     this.filename = filename;
/* 16 */     this.teams = new ArrayList<>();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\export\FileConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */