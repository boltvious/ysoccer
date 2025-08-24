/*    */ package com.ygames.ysoccer.desktop;
/*    */ import com.badlogic.gdx.ApplicationListener;
/*    */ import com.badlogic.gdx.Files;
/*    */ import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
/*    */ import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
/*    */ import com.ygames.ysoccer.YSoccer;
/*    */ 
/*    */ public class DesktopLauncher {
/*    */   public static void main(String[] arg) {
/* 10 */     LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
/* 11 */     config.width = 1280;
/* 12 */     config.height = 720;
/* 13 */     config.addIcon("images/icon_128.png", Files.FileType.Internal);
/* 14 */     config.addIcon("images/icon_32.png", Files.FileType.Internal);
/* 15 */     new LwjglApplication((ApplicationListener)new YSoccer(), config);
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\desktop\DesktopLauncher.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */