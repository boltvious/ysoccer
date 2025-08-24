/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ public class Hair {
/*    */   public final Color color;
/*    */   public final String style;
/*    */   
/*    */   public enum Color {
/*  8 */     BLACK, BLOND, BROWN, RED, PLATINUM, GRAY, WHITE, PUNK_FUCHSIA, PUNK_BLOND;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Hair(Color color, String style) {
/* 14 */     this.color = color;
/* 15 */     this.style = style;
/*    */   }
/*    */   
/* 18 */   public static Color3[] colors = new Color3[] { new Color3(2763306, 1710618, 592137), new Color3(10657826, 8486938, 6315793), new Color3(10642466, 8080410, 5518353), new Color3(14977792, 11693313, 8343041), new Color3(16776574, 15065919, 13289472), new Color3(8026746, 5131854, 2236962), new Color3(14013909, 11382189, 8684676), new Color3(16711848, 7483183, 4332058), new Color3(16644917, 8421890, 5855749) };
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
/*    */   
/* 30 */   public static Color2[][] shavedColors = new Color2[][] { { new Color2(10582111, 8215882), new Color2(12568424, 10067796), new Color2(13079361, 9728315), new Color2(13079361, 9728315), new Color2(12568424, 10067796), new Color2(10582111, 8215882), new Color2(14073495, 11245948), new Color2(13079361, 9728315), new Color2(12568424, 10067796) }, { new Color2(4338213, 3220504), null, null, null, null, null, null, null, null }, { new Color2(10582111, 8215882), new Color2(12568424, 10067796), new Color2(13079361, 9728315), new Color2(13079361, 9728315), new Color2(12568424, 10067796), new Color2(10582111, 8215882), new Color2(14073495, 11245948), new Color2(13079361, 9728315), new Color2(12568424, 10067796) }, { new Color2(10582111, 8215882), new Color2(12568424, 10067796), new Color2(13079361, 9728315), new Color2(13079361, 9728315), new Color2(12568424, 10067796), new Color2(10582111, 8215882), new Color2(14073495, 11245948), new Color2(13079361, 9728315), new Color2(12568424, 10067796) }, { new Color2(9529415, 7754545), null, null, null, null, null, null, null, null }, { new Color2(9529415, 7754545), null, null, null, null, null, null, null, null }, { new Color2(10582111, 8215882), new Color2(12568424, 10067796), new Color2(13079361, 9728315), new Color2(13079361, 9728315), new Color2(12568424, 10067796), new Color2(10582111, 8215882), new Color2(14073495, 11245948), new Color2(13079361, 9728315), new Color2(12568424, 10067796) }, { null, null, null, null, null, null, null, null, null }, { null, null, null, null, null, null, null, null, null } };
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Hair.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */