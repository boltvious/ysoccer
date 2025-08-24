/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MatchHotKeys
/*     */   extends SceneHotKeys
/*     */ {
/*     */   private boolean keyCommentary;
/*     */   private boolean keyCrowdChants;
/*     */   private boolean keyAutoReplay;
/*     */   private boolean keyRadar;
/*     */   private boolean keyRecordAction;
/*     */   
/*     */   MatchHotKeys(Match match) {
/*  24 */     super(match);
/*     */     
/*  26 */     String[] matchCommentary = { InputDevice.keyDescription(247), Assets.gettext("HELP.MATCH COMMENTARY") };
/*  27 */     this.keyMap.put(Integer.valueOf(4), matchCommentary);
/*     */     
/*  29 */     String[] crowdChants = { InputDevice.keyDescription(248), Assets.gettext("HELP.CROWD CHANTS") };
/*  30 */     this.keyMap.put(Integer.valueOf(5), crowdChants);
/*     */     
/*  32 */     String[] autoReplay = { InputDevice.keyDescription(252), Assets.gettext("HELP.AUTO REPLAYS") };
/*  33 */     this.keyMap.put(Integer.valueOf(10), autoReplay);
/*     */     
/*  35 */     String[] radar = { InputDevice.keyDescription(253), Assets.gettext("HELP.RADAR") };
/*  36 */     this.keyMap.put(Integer.valueOf(11), radar);
/*     */     
/*  38 */     String[] recordAction = { InputDevice.keyDescription(62), Assets.gettext("HELP.RECORD ACTION") };
/*  39 */     this.keyMap.put(Integer.valueOf(13), recordAction);
/*     */     
/*  41 */     String[] pause = { InputDevice.keyDescription(44), Assets.gettext("HELP.PAUSE") };
/*  42 */     this.keyMap.put(Integer.valueOf(14), pause);
/*     */   }
/*     */   
/*     */   private Match getMatch() {
/*  46 */     return (Match)this.scene;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update() {
/*  51 */     super.update();
/*     */     
/*  53 */     if (Gdx.input.isKeyPressed(247) && !this.keyCommentary) {
/*  54 */       this.scene.settings.commentary = !this.scene.settings.commentary;
/*     */       
/*  56 */       this.message = Assets.gettext("MATCH OPTIONS.COMMENTARY") + " ";
/*  57 */       if (this.scene.settings.commentary) {
/*  58 */         this.message += Assets.gettext("MATCH OPTIONS.COMMENTARY.ON");
/*     */       } else {
/*  60 */         this.message += Assets.gettext("MATCH OPTIONS.COMMENTARY.OFF");
/*     */       } 
/*  62 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/*  65 */     if (Gdx.input.isKeyPressed(248) && !this.keyCrowdChants) {
/*  66 */       (getMatch().getSettings()).crowdChants = !(getMatch().getSettings()).crowdChants;
/*  67 */       Assets.Sounds.intro.setVolume(Assets.Sounds.introId.longValue(), Assets.Sounds.volume / 100.0F);
/*  68 */       Assets.Sounds.crowd.setVolume(Assets.Sounds.crowdId.longValue(), Assets.Sounds.volume / 100.0F);
/*     */       
/*  70 */       this.message = Assets.gettext("MATCH OPTIONS.CROWD CHANTS") + " ";
/*  71 */       if ((getMatch().getSettings()).crowdChants) {
/*  72 */         this.message += Assets.gettext("MATCH OPTIONS.CROWD CHANTS.ON");
/*     */       } else {
/*  74 */         this.message += Assets.gettext("MATCH OPTIONS.CROWD CHANTS.OFF");
/*     */       } 
/*  76 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/*  79 */     if (Gdx.input.isKeyPressed(252) && !this.keyAutoReplay) {
/*  80 */       (getMatch().getSettings()).autoReplays = !(getMatch().getSettings()).autoReplays;
/*     */       
/*  82 */       this.message = Assets.gettext("AUTO REPLAYS") + " ";
/*     */       
/*  84 */       if ((getMatch().getSettings()).autoReplays) {
/*  85 */         this.message += Assets.gettext("AUTO REPLAYS.ON");
/*     */       } else {
/*  87 */         this.message += Assets.gettext("AUTO REPLAYS.OFF");
/*     */       } 
/*     */       
/*  90 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/*  93 */     if (Gdx.input.isKeyPressed(253) && !this.keyRadar) {
/*  94 */       (getMatch().getSettings()).radar = !(getMatch().getSettings()).radar;
/*     */       
/*  96 */       this.message = Assets.gettext("RADAR") + " ";
/*  97 */       if ((getMatch().getSettings()).radar) {
/*  98 */         this.message += Assets.gettext("RADAR.ON");
/*     */       } else {
/* 100 */         this.message += Assets.gettext("RADAR.OFF");
/*     */       } 
/*     */       
/* 103 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/* 106 */     if (Gdx.input.isKeyPressed(62) && !this.keyRecordAction) {
/* 107 */       (getMatch()).recorder.saveHighlight(this.scene.getRenderer());
/*     */       
/* 109 */       this.message = Assets.gettext("ACTION RECORDED");
/* 110 */       this.messageTimer = 60L;
/*     */     } 
/*     */     
/* 113 */     this.keyCommentary = Gdx.input.isKeyPressed(247);
/* 114 */     this.keyCrowdChants = Gdx.input.isKeyPressed(248);
/* 115 */     this.keyAutoReplay = Gdx.input.isKeyPressed(252);
/* 116 */     this.keyRadar = Gdx.input.isKeyPressed(253);
/* 117 */     this.keyRecordAction = Gdx.input.isKeyPressed(62);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void onChangeVolume() {
/* 123 */     super.onChangeVolume();
/*     */     
/* 125 */     Assets.Sounds.intro.setVolume(Assets.Sounds.introId.longValue(), Assets.Sounds.volume / 100.0F);
/* 126 */     Assets.Sounds.crowd.setVolume(Assets.Sounds.crowdId.longValue(), Assets.Sounds.volume / 100.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\MatchHotKeys.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */