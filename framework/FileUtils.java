/*    */ package com.ygames.ysoccer.framework;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ 
/*    */ 
/*    */ class FileUtils
/*    */ {
/*    */   static byte[] inputStreamToBytes(ByteArrayInputStream byteArrayInputStream) {
/* 11 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*    */     try {
/* 13 */       int read = byteArrayInputStream.read();
/* 14 */       while (read != -1) {
/* 15 */         byteArrayOutputStream.write(read);
/* 16 */         read = byteArrayInputStream.read();
/*    */       } 
/* 18 */     } catch (Exception e) {
/* 19 */       Gdx.app.error("Error converting inputStream", e.toString());
/*    */     } 
/* 21 */     return byteArrayOutputStream.toByteArray();
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\FileUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */