/*     */ package com.ygames.ysoccer.framework;
/*     */ 
/*     */ import com.ygames.ysoccer.gui.Widget;
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
/*     */ 
/*     */ class MenuInput
/*     */ {
/*     */   private int x;
/*     */   private int y;
/*     */   private boolean fire1;
/*     */   private boolean fire2;
/*     */   private boolean fire1Old;
/*     */   private boolean fire2Old;
/*     */   private int xTimer;
/*     */   private int yTimer;
/*     */   private int fire1Timer;
/*     */   private int fire2Timer;
/*     */   private final GLGame game;
/*     */   
/*     */   MenuInput(GLGame game) {
/*  32 */     this.game = game;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void read(GLScreen screen) {
/*  38 */     if (this.fire1) {
/*  39 */       if (!this.fire1Old) {
/*  40 */         this.fire1Timer = 20;
/*  41 */       } else if (this.fire1Timer == 0) {
/*  42 */         this.fire1Timer = 6;
/*     */       }
/*     */     
/*  45 */     } else if (!this.fire1Old) {
/*  46 */       this.fire1Timer = 0;
/*     */     } 
/*     */ 
/*     */     
/*  50 */     if (this.fire1Timer > 0) {
/*  51 */       this.fire1Timer--;
/*     */     }
/*     */ 
/*     */     
/*  55 */     if (this.fire2) {
/*  56 */       if (!this.fire2Old) {
/*  57 */         this.fire2Timer = 20;
/*  58 */       } else if (this.fire2Timer == 0) {
/*  59 */         this.fire2Timer = 6;
/*     */       }
/*     */     
/*  62 */     } else if (!this.fire2Old) {
/*  63 */       this.fire2Timer = 0;
/*     */     } 
/*     */ 
/*     */     
/*  67 */     if (this.fire2Timer > 0) {
/*  68 */       this.fire2Timer--;
/*     */     }
/*     */ 
/*     */     
/*  72 */     int xOld = this.x;
/*  73 */     int yOld = this.y;
/*  74 */     this.fire1Old = this.fire1;
/*  75 */     this.fire2Old = this.fire2;
/*     */     
/*  77 */     this.x = 0;
/*  78 */     this.y = 0;
/*  79 */     this.fire1 = false;
/*  80 */     this.fire2 = false;
/*     */     
/*  82 */     int len = this.game.inputDevices.size();
/*  83 */     for (int i = 0; i < len; i++) {
/*  84 */       InputDevice inputDevice = this.game.inputDevices.get(i);
/*     */ 
/*     */       
/*  87 */       int newX = inputDevice.x1;
/*  88 */       if (newX != 0) {
/*  89 */         this.x = newX;
/*  90 */         this.game.disableMouse();
/*     */       } 
/*     */ 
/*     */       
/*  94 */       int newY = inputDevice.y1;
/*  95 */       if (newY != 0) {
/*  96 */         this.y = newY;
/*  97 */         this.game.disableMouse();
/*     */       } 
/*     */ 
/*     */       
/* 101 */       if (inputDevice.fire11) {
/* 102 */         this.fire1 = true;
/* 103 */         screen.lastFireInputDevice = inputDevice;
/* 104 */         this.game.disableMouse();
/*     */       } 
/*     */ 
/*     */       
/* 108 */       if (inputDevice.fire21) {
/* 109 */         this.fire2 = true;
/* 110 */         screen.lastFireInputDevice = inputDevice;
/* 111 */         this.game.disableMouse();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 116 */     Widget selectedWidget = screen.getSelectedWidget();
/* 117 */     if (this.game.mouse.enabled && selectedWidget != null && selectedWidget
/*     */       
/* 119 */       .contains(this.game.mouse.position.x, this.game.mouse.position.y)) {
/* 120 */       if (this.game.mouse.buttonLeft) {
/* 121 */         this.fire1 = true;
/* 122 */         screen.lastFireInputDevice = null;
/*     */       } 
/* 124 */       if (this.game.mouse.buttonRight) {
/* 125 */         this.fire2 = true;
/* 126 */         screen.lastFireInputDevice = null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 131 */     int bias = 1;
/* 132 */     if (selectedWidget != null) {
/* 133 */       if (this.y == -1 && this.yTimer == 0) {
/* 134 */         float distMin = 50000.0F;
/*     */         
/* 136 */         for (Widget w : screen.widgets) {
/* 137 */           if (w.y + w.h <= selectedWidget.y) {
/* 138 */             float distance = EMath.hypo(bias * (w.x + 0.5F * w.w - selectedWidget.x + 0.5F * selectedWidget.w), w.y + 0.5F * w.h - selectedWidget.y + 0.5F * selectedWidget.h);
/* 139 */             if (distance < distMin && screen.setSelectedWidget(w)) {
/* 140 */               distMin = distance;
/*     */             }
/*     */           } 
/*     */         } 
/* 144 */       } else if (this.y == 1 && this.yTimer == 0) {
/* 145 */         float distMin = 50000.0F;
/*     */         
/* 147 */         for (Widget w : screen.widgets) {
/* 148 */           if (w.y >= selectedWidget.y + selectedWidget.h) {
/* 149 */             float distance = EMath.hypo(bias * (w.x + 0.5F * w.w - selectedWidget.x + 0.5F * selectedWidget.w), w.y + 0.5F * w.h - selectedWidget.y + 0.5F * selectedWidget.h);
/* 150 */             if (distance < distMin && screen.setSelectedWidget(w)) {
/* 151 */               distMin = distance;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 159 */     bias = 9;
/* 160 */     if (selectedWidget != null) {
/* 161 */       if (this.x == -1 && this.xTimer == 0) {
/* 162 */         float distMin = 50000.0F;
/*     */         
/* 164 */         for (Widget w : screen.widgets) {
/* 165 */           if (w.x + w.w <= selectedWidget.x) {
/* 166 */             float distance = EMath.hypo(w.x + 0.5F * w.w - selectedWidget.x + 0.5F * selectedWidget.w, bias * (w.y + 0.5F * w.h - selectedWidget.y + 0.5F * selectedWidget.h));
/* 167 */             if (distance < distMin && screen.setSelectedWidget(w)) {
/* 168 */               distMin = distance;
/*     */             }
/*     */           } 
/*     */         } 
/* 172 */       } else if (this.x == 1 && this.xTimer == 0) {
/* 173 */         float distMin = 50000.0F;
/*     */         
/* 175 */         for (Widget w : screen.widgets) {
/* 176 */           if (w.x >= selectedWidget.x + selectedWidget.w) {
/* 177 */             float distance = EMath.hypo(w.x + 0.5F * w.w - selectedWidget.x + 0.5F * selectedWidget.w, bias * (w.y + 0.5F * w.h - selectedWidget.y + 0.5F * selectedWidget.h));
/* 178 */             if (distance < distMin && screen.setSelectedWidget(w)) {
/* 179 */               distMin = distance;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 187 */     if (this.x != 0) {
/* 188 */       if (xOld == 0) {
/* 189 */         this.xTimer = 12;
/* 190 */       } else if (this.xTimer == 0) {
/* 191 */         this.xTimer = 4;
/*     */       } 
/*     */     } else {
/* 194 */       this.xTimer = 0;
/*     */     } 
/* 196 */     if (this.y != 0) {
/* 197 */       if (yOld == 0) {
/* 198 */         this.yTimer = 12;
/* 199 */       } else if (this.yTimer == 0) {
/* 200 */         this.yTimer = 4;
/*     */       } 
/*     */     } else {
/* 203 */       this.yTimer = 0;
/*     */     } 
/*     */     
/* 206 */     if (this.xTimer > 0) {
/* 207 */       this.xTimer--;
/*     */     }
/* 209 */     if (this.yTimer > 0) {
/* 210 */       this.yTimer--;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   Widget.Event getWidgetEvent() {
/* 216 */     if (this.fire1) {
/* 217 */       if (this.fire1Old) {
/* 218 */         if (this.fire1Timer == 0) {
/* 219 */           return Widget.Event.FIRE1_HOLD;
/*     */         }
/*     */       } else {
/* 222 */         return Widget.Event.FIRE1_DOWN;
/*     */       }
/*     */     
/* 225 */     } else if (this.fire1Old) {
/* 226 */       return Widget.Event.FIRE1_UP;
/*     */     } 
/*     */ 
/*     */     
/* 230 */     if (this.fire2) {
/* 231 */       if (this.fire2Old) {
/* 232 */         if (this.fire2Timer == 0) {
/* 233 */           return Widget.Event.FIRE2_HOLD;
/*     */         }
/*     */       } else {
/* 236 */         return Widget.Event.FIRE2_DOWN;
/*     */       }
/*     */     
/* 239 */     } else if (this.fire2Old) {
/* 240 */       return Widget.Event.FIRE2_UP;
/*     */     } 
/*     */ 
/*     */     
/* 244 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\framework\MenuInput.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */