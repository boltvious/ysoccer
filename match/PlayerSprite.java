/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*    */ import com.ygames.ysoccer.framework.Assets;
/*    */ import com.ygames.ysoccer.framework.Font;
/*    */ import com.ygames.ysoccer.framework.GLGraphics;
/*    */ import com.ygames.ysoccer.framework.Settings;
/*    */ 
/*    */ public class PlayerSprite extends Sprite {
/*    */   Player player;
/*    */   
/*    */   public PlayerSprite(GLGraphics glGraphics, Player player) {
/* 14 */     super(glGraphics);
/* 15 */     this.player = player;
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(int subframe) {
/* 20 */     Data d = this.player.data[subframe];
/* 21 */     if (!d.isVisible) {
/*    */       return;
/*    */     }
/*    */     
/* 25 */     if (this.player.role == Player.Role.GOALKEEPER) {
/* 26 */       Integer[] origin = Assets.keeperOrigins[d.fmy][d.fmx];
/* 27 */       this.glGraphics.batch.draw(Assets.keeper[this.player.team.index][this.player.skinColor
/* 28 */             .ordinal()][d.fmx][d.fmy], (d.x - origin[0]
/* 29 */           .intValue()), (d.y - origin[1]
/* 30 */           .intValue() - d.z));
/*    */ 
/*    */       
/* 33 */       Integer[] hairMap = Assets.keeperHairMap[d.fmy][d.fmx];
/* 34 */       if (hairMap[2].intValue() != 0 || hairMap[3].intValue() != 0) {
/* 35 */         this.glGraphics.batch.draw(((TextureRegion[][])Assets.hairs
/* 36 */             .get(this.player.hair))[hairMap[0].intValue()][hairMap[1].intValue()], (d.x - origin[0]
/* 37 */             .intValue() + hairMap[2].intValue()), (d.y - origin[1]
/* 38 */             .intValue() - d.z + hairMap[3].intValue()));
/*    */       }
/*    */     } else {
/*    */       
/* 42 */       Integer[] origin = Assets.playerOrigins[d.fmy][d.fmx];
/* 43 */       this.glGraphics.batch.draw(Assets.player[this.player.team.index][this.player.skinColor
/* 44 */             .ordinal()][d.fmx][d.fmy], (d.x - origin[0]
/* 45 */           .intValue()), (d.y - origin[1]
/* 46 */           .intValue() - d.z));
/*    */ 
/*    */       
/* 49 */       Integer[] hairMap = Assets.playerHairMap[d.fmy][d.fmx];
/* 50 */       if (hairMap[2].intValue() != 0 || hairMap[3].intValue() != 0) {
/* 51 */         this.glGraphics.batch.draw(((TextureRegion[][])Assets.hairs
/* 52 */             .get(this.player.hair))[hairMap[0].intValue()][hairMap[1].intValue()], (d.x - origin[0]
/* 53 */             .intValue() - 9 + hairMap[2].intValue()), (d.y - origin[1]
/* 54 */             .intValue() - d.z - 9 + hairMap[3].intValue()));
/*    */       }
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 60 */     if (Settings.showDevelopmentInfo) {
/* 61 */       if (Settings.showPlayerState && this.player.fsm != null) {
/* 62 */         Assets.font3.draw((SpriteBatch)this.glGraphics.batch, PlayerFsm.Id.values()[d.playerState].toString(), d.x, d.y - 50 - d.z, Font.Align.CENTER);
/*    */       }
/* 64 */       if (Settings.showPlayerAiState && d.playerAiState != -1) {
/* 65 */         Assets.font3.draw((SpriteBatch)this.glGraphics.batch, AiFsm.Id.values()[d.playerAiState].toString(), d.x, d.y - 40 - d.z, Font.Align.CENTER);
/*    */       }
/* 67 */       if (Settings.showBestDefender && d.isBestDefender) {
/* 68 */         Assets.font6.draw((SpriteBatch)this.glGraphics.batch, "_", d.x, d.y - 12 - d.z, Font.Align.CENTER);
/*    */       }
/* 70 */       if (Settings.showFrameDistance) {
/* 71 */         Assets.font3.draw((SpriteBatch)this.glGraphics.batch, d.frameDistance, d.x, d.y - d.z + 4, Font.Align.CENTER);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getY(int subframe) {
/* 78 */     return (this.player.data[subframe]).y;
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\PlayerSprite.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */