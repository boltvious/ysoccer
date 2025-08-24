/*     */ package com.ygames.ysoccer.framework;public class MenuMusic {
/*     */   private State state;
/*     */   public static final int ALL = -2;
/*     */   public static final int SHUFFLE = -1;
/*     */   private Music music;
/*     */   private int volume;
/*     */   private int mode;
/*     */   private List<FileHandle> fileList;
/*     */   public List<FileHandle> playList;
/*     */   private int currentTrack;
/*     */   
/*     */   private enum State {
/*  13 */     STOPPED, PLAYING, PAUSED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MenuMusic(String folder) {
/*  28 */     this.fileList = new ArrayList<>();
/*  29 */     this.playList = new ArrayList<>();
/*  30 */     this.currentTrack = -1;
/*  31 */     this.state = State.STOPPED;
/*     */     
/*  33 */     FileHandle fileHandle = Gdx.files.internal(folder);
/*  34 */     for (FileHandle file : fileHandle.list()) {
/*  35 */       if (file.extension().toLowerCase().equals("ogg") || file
/*  36 */         .extension().toLowerCase().equals("wav") || file
/*  37 */         .extension().toLowerCase().equals("mp3")) {
/*  38 */         this.fileList.add(file);
/*     */       }
/*     */     } 
/*  41 */     Collections.sort(this.fileList, Assets.fileComparatorByName);
/*     */   }
/*     */   
/*     */   public int getMode() {
/*  45 */     return this.mode;
/*     */   }
/*     */   public void setMode(int mode) {
/*     */     int track;
/*  49 */     this.mode = mode;
/*  50 */     this.currentTrack = -1;
/*     */     
/*  52 */     if (this.state == State.PLAYING) {
/*  53 */       this.music.stop();
/*     */     }
/*     */     
/*  56 */     this.playList.clear();
/*     */     
/*  58 */     switch (mode) {
/*     */       case -2:
/*  60 */         this.playList.addAll(this.fileList);
/*     */         break;
/*     */       
/*     */       case -1:
/*  64 */         this.playList.addAll(this.fileList);
/*  65 */         Collections.shuffle(this.playList);
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/*  70 */         track = Math.min(mode, this.fileList.size() - 1);
/*  71 */         this.playList.add(this.fileList.get(track));
/*     */         break;
/*     */     } 
/*     */     
/*  75 */     if (this.playList.size() > 0) {
/*  76 */       this.state = State.PLAYING;
/*     */     }
/*     */   }
/*     */   
/*     */   public void update(int targetVolume) {
/*  81 */     switch (this.state) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case PLAYING:
/*  87 */         if (this.music == null || !this.music.isPlaying()) {
/*     */           
/*  89 */           this.currentTrack = EMath.rotate(this.currentTrack, 0, this.playList.size() - 1, 1);
/*  90 */           this.music = Gdx.audio.newMusic(this.playList.get(this.currentTrack));
/*     */           
/*  92 */           if (this.music == null) {
/*  93 */             Gdx.app.log("Cannot load track", ((FileHandle)this.playList.get(this.currentTrack)).name());
/*  94 */             this.state = State.STOPPED;
/*     */             
/*     */             return;
/*     */           } 
/*  98 */           this.music.setLooping((this.playList.size() == 1));
/*  99 */           this.music.play();
/*     */           
/* 101 */           if (!this.music.isPlaying()) {
/* 102 */             Gdx.app.log("Cannot play track", ((FileHandle)this.playList.get(this.currentTrack)).name());
/* 103 */             this.state = State.STOPPED;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 108 */         if (this.volume > targetVolume) {
/* 109 */           this.volume = Math.max(this.volume - 5, 0);
/* 110 */         } else if (this.volume < targetVolume) {
/* 111 */           this.volume = Math.min(this.volume + 2, 100);
/*     */         } 
/*     */ 
/*     */         
/* 115 */         if (this.volume == 0) {
/* 116 */           this.music.stop();
/* 117 */           this.state = State.PAUSED; break;
/*     */         } 
/* 119 */         if (!this.music.isPlaying()) {
/* 120 */           this.music.play();
/*     */         }
/* 122 */         this.music.setVolume(this.volume / 100.0F);
/*     */         break;
/*     */ 
/*     */       
/*     */       case PAUSED:
/* 127 */         if (targetVolume > 0) {
/* 128 */           this.state = State.PLAYING;
/*     */         }
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getCurrentTrackName() {
/* 135 */     if (this.state == State.STOPPED || this.currentTrack == -1) {
/* 136 */       return "";
/*     */     }
/* 138 */     return ((FileHandle)this.playList.get(this.currentTrack)).nameWithoutExtension().toUpperCase();
/*     */   }
/*     */   
/*     */   public int getModeMin() {
/* 142 */     return -2;
/*     */   }
/*     */   
/*     */   public int getModeMax() {
/* 146 */     return this.fileList.size() - 1;
/*     */   }
/*     */   
/*     */   boolean isPlaying() {
/* 150 */     return (this.state == State.PLAYING && this.music.isPlaying());
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\MenuMusic.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */