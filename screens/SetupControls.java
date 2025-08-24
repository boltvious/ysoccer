/*     */ package com.ygames.ysoccer.screens;
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.Input;
/*     */ import com.badlogic.gdx.InputAdapter;
/*     */ import com.badlogic.gdx.InputProcessor;
/*     */ import com.badlogic.gdx.Screen;
/*     */ import com.badlogic.gdx.controllers.Controller;
/*     */ import com.badlogic.gdx.controllers.ControllerAdapter;
/*     */ import com.badlogic.gdx.controllers.ControllerListener;
/*     */ import com.badlogic.gdx.controllers.Controllers;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.Font;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.GLScreen;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.framework.InputDeviceConfig;
/*     */ import com.ygames.ysoccer.framework.JoystickConfig;
/*     */ import com.ygames.ysoccer.framework.KeyboardConfig;
/*     */ import com.ygames.ysoccer.gui.Button;
/*     */ import com.ygames.ysoccer.gui.Widget;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ 
/*     */ class SetupControls extends GLScreen {
/*     */   private enum ConfigParam {
/*  30 */     KEY_LEFT, KEY_RIGHT, KEY_UP, KEY_DOWN, BUTTON_1, BUTTON_2;
/*     */   }
/*  32 */   private final ConfigParam[] buttonParams = new ConfigParam[] { ConfigParam.BUTTON_1, ConfigParam.BUTTON_2 };
/*  33 */   private final ConfigParam[] axisParams = new ConfigParam[] { ConfigParam.KEY_LEFT, ConfigParam.KEY_RIGHT, ConfigParam.KEY_UP, ConfigParam.KEY_DOWN };
/*     */   
/*     */   private InputDeviceButton selectedInputDeviceButton;
/*     */   private final ArrayList<JoystickConfig> joystickConfigs;
/*     */   private final ArrayList<KeyboardConfig> keyboardConfigs;
/*     */   private final InputProcessor inputProcessor;
/*     */   private final JoystickListener joystickListener;
/*     */   private ConfigButton listeningConfigButton;
/*     */   
/*     */   SetupControls(GLGame game) {
/*  43 */     super(game);
/*  44 */     this.background = new Texture("images/backgrounds/menu_controls.jpg");
/*     */     
/*  46 */     this.inputProcessor = (InputProcessor)new SetupInputProcessor();
/*  47 */     this.joystickConfigs = new ArrayList<>();
/*  48 */     this.joystickListener = new JoystickListener();
/*  49 */     Controllers.addListener((ControllerListener)this.joystickListener);
/*  50 */     this.listeningConfigButton = null;
/*     */ 
/*     */ 
/*     */     
/*  54 */     GLScreen.TitleBar titleBar = new GLScreen.TitleBar(this, Assets.gettext("CONTROLS"), 8587164);
/*  55 */     this.widgets.add(titleBar);
/*     */     
/*  57 */     int pos = 0;
/*  58 */     this.keyboardConfigs = game.settings.getKeyboardConfigs(); int port;
/*  59 */     for (port = 0; port < 2; port++) {
/*  60 */       InputDeviceButton inputDeviceButton = new InputDeviceButton((InputDeviceConfig)this.keyboardConfigs.get(port), port, pos);
/*  61 */       this.widgets.add(inputDeviceButton);
/*     */       
/*  63 */       if (port == 0) {
/*  64 */         this.selectedInputDeviceButton = inputDeviceButton;
/*  65 */         setSelectedWidget((Widget)inputDeviceButton);
/*     */       } 
/*  67 */       pos++;
/*     */     } 
/*     */     
/*  70 */     port = 0;
/*  71 */     for (Controller controller : Controllers.getControllers()) {
/*     */ 
/*     */       
/*  74 */       JoystickConfig joystickConfig = game.settings.getJoystickConfigByName(controller.getName());
/*  75 */       if (joystickConfig == null) {
/*  76 */         joystickConfig = new JoystickConfig(controller.getName());
/*     */       }
/*     */       
/*  79 */       this.joystickConfigs.add(joystickConfig);
/*     */       
/*  81 */       InputDeviceButton inputDeviceButton = new InputDeviceButton((InputDeviceConfig)joystickConfig, port, pos);
/*  82 */       this.widgets.add(inputDeviceButton);
/*     */       
/*  84 */       ResetJoystickButton resetJoystickButton = new ResetJoystickButton(inputDeviceButton);
/*  85 */       this.widgets.add(resetJoystickButton);
/*     */       
/*  87 */       port++;
/*  88 */       pos++;
/*     */     } 
/*     */     
/*  91 */     InputDeviceLabel inputDeviceLabel = new InputDeviceLabel();
/*  92 */     this.widgets.add(inputDeviceLabel);
/*     */     
/*  94 */     LeftLabel leftLabel = new LeftLabel();
/*  95 */     this.widgets.add(leftLabel);
/*     */     
/*  97 */     LeftButton leftButton = new LeftButton();
/*  98 */     this.widgets.add(leftButton);
/*     */     
/* 100 */     RightLabel rightLabel = new RightLabel();
/* 101 */     this.widgets.add(rightLabel);
/*     */     
/* 103 */     RightButton rightButton = new RightButton();
/* 104 */     this.widgets.add(rightButton);
/*     */     
/* 106 */     UpLabel upLabel = new UpLabel();
/* 107 */     this.widgets.add(upLabel);
/*     */     
/* 109 */     UpButton upButton = new UpButton();
/* 110 */     this.widgets.add(upButton);
/*     */     
/* 112 */     DownLabel downLabel = new DownLabel();
/* 113 */     this.widgets.add(downLabel);
/*     */     
/* 115 */     DownButton downButton = new DownButton();
/* 116 */     this.widgets.add(downButton);
/*     */     
/* 118 */     FireLabel fireLabel2 = new FireLabel(1);
/* 119 */     this.widgets.add(fireLabel2);
/*     */     
/* 121 */     FireButton fireButton2 = new FireButton(1);
/* 122 */     this.widgets.add(fireButton2);
/*     */     
/* 124 */     FireLabel fireLabel1 = new FireLabel(2);
/* 125 */     this.widgets.add(fireLabel1);
/*     */     
/* 127 */     FireButton fireButton1 = new FireButton(2);
/* 128 */     this.widgets.add(fireButton1);
/*     */     
/* 130 */     ExitButton exitButton = new ExitButton();
/* 131 */     this.widgets.add(exitButton);
/*     */   }
/*     */   
/*     */   private class InputDeviceButton
/*     */     extends Button {
/*     */     private final InputDeviceConfig config;
/*     */     private final int port;
/*     */     
/*     */     InputDeviceButton(InputDeviceConfig config, int port, int pos) {
/* 140 */       this.config = config;
/* 141 */       this.port = port;
/* 142 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry(1280 / 2 - 560, 180 + 46 * pos, 240, 42);
/* 143 */       switch (config.type) {
/*     */         case KEY_LEFT:
/* 145 */           setText(Assets.gettext("KEYBOARD") + " " + (port + 1), Font.Align.CENTER, Assets.font14);
/*     */           break;
/*     */         
/*     */         case KEY_RIGHT:
/* 149 */           setText(Assets.gettext("JOYSTICK") + " " + (port + 1), Font.Align.CENTER, Assets.font14);
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 156 */       setColor((this == SetupControls.this.selectedInputDeviceButton) ? 3092318 : 4737169);
/* 157 */       switch (this.config.type) {
/*     */ 
/*     */ 
/*     */         
/*     */         case KEY_RIGHT:
/* 162 */           if (!((JoystickConfig)this.config).isConfigured()) {
/* 163 */             setColor((this == SetupControls.this.selectedInputDeviceButton) ? 8388608 : 11796480);
/*     */           }
/*     */           break;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 171 */       SetupControls.this.selectedInputDeviceButton = this;
/* 172 */       for (Widget widget : SetupControls.this.widgets)
/* 173 */         widget.setDirty(true); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ResetJoystickButton
/*     */     extends Button
/*     */   {
/*     */     final SetupControls.InputDeviceButton inputDeviceButton;
/*     */     
/*     */     ResetJoystickButton(SetupControls.InputDeviceButton inputDeviceButton) {
/* 183 */       this.inputDeviceButton = inputDeviceButton;
/* 184 */       setGeometry(inputDeviceButton.x + inputDeviceButton.w + 2, inputDeviceButton.y, 38, 42);
/* 185 */       setColor(11796480);
/* 186 */       setText("\023", Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 191 */       setVisible((this.inputDeviceButton == SetupControls.this.selectedInputDeviceButton && ((JoystickConfig)this.inputDeviceButton.config).isConfigured()));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 196 */       ((JoystickConfig)this.inputDeviceButton.config).reset();
/* 197 */       SetupControls.this.refreshAllWidgets();
/*     */     }
/*     */   }
/*     */   
/*     */   private class InputDeviceLabel
/*     */     extends Button {
/*     */     InputDeviceLabel() {
/* 204 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 760) / 2, 100, 760, 40);
/* 205 */       setColor(4210752);
/* 206 */       setText("", Font.Align.CENTER, Assets.font14);
/* 207 */       setActive(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void refresh() {
/* 212 */       switch (SetupControls.this.selectedInputDeviceButton.config.type) {
/*     */         case KEY_LEFT:
/* 214 */           setText("");
/* 215 */           setVisible(false);
/*     */           break;
/*     */         
/*     */         case KEY_RIGHT:
/* 219 */           setText(((JoystickConfig)SetupControls.this.selectedInputDeviceButton.config).name.toUpperCase());
/* 220 */           setVisible(true);
/*     */           break;
/*     */       } 
/*     */     } }
/*     */   
/*     */   private abstract class ConfigButton extends Button {
/*     */     SetupControls.ConfigParam configParam;
/*     */     
/*     */     private ConfigButton() {}
/*     */     
/*     */     void setKeyCode(int keyCode) {
/* 231 */       if (SetupControls.this.isKeyCodeReserved(keyCode))
/*     */         return; 
/* 233 */       if (keyCode != 131 && SetupControls.this.selectedInputDeviceButton.config.type == InputDevice.Type.KEYBOARD) {
/* 234 */         KeyboardConfig keyboardConfig = (KeyboardConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 235 */         switch (this.configParam) {
/*     */           case KEY_LEFT:
/* 237 */             if (SetupControls.this.isKeyCodeAssigned(keyCode, this.configParam, SetupControls.this.selectedInputDeviceButton.port)) {
/*     */               return;
/*     */             }
/* 240 */             keyboardConfig.keyLeft = keyCode;
/*     */             break;
/*     */           
/*     */           case KEY_RIGHT:
/* 244 */             if (SetupControls.this.isKeyCodeAssigned(keyCode, this.configParam, SetupControls.this.selectedInputDeviceButton.port)) {
/*     */               return;
/*     */             }
/* 247 */             keyboardConfig.keyRight = keyCode;
/*     */             break;
/*     */           
/*     */           case KEY_UP:
/* 251 */             if (SetupControls.this.isKeyCodeAssigned(keyCode, this.configParam, SetupControls.this.selectedInputDeviceButton.port)) {
/*     */               return;
/*     */             }
/* 254 */             keyboardConfig.keyUp = keyCode;
/*     */             break;
/*     */           
/*     */           case KEY_DOWN:
/* 258 */             if (SetupControls.this.isKeyCodeAssigned(keyCode, this.configParam, SetupControls.this.selectedInputDeviceButton.port)) {
/*     */               return;
/*     */             }
/* 261 */             keyboardConfig.keyDown = keyCode;
/*     */             break;
/*     */           
/*     */           case BUTTON_1:
/* 265 */             if (SetupControls.this.isKeyCodeAssigned(keyCode, this.configParam, SetupControls.this.selectedInputDeviceButton.port)) {
/*     */               return;
/*     */             }
/* 268 */             keyboardConfig.button1 = keyCode;
/*     */             break;
/*     */           
/*     */           case BUTTON_2:
/* 272 */             if (SetupControls.this.isKeyCodeAssigned(keyCode, this.configParam, SetupControls.this.selectedInputDeviceButton.port)) {
/*     */               return;
/*     */             }
/* 275 */             keyboardConfig.button2 = keyCode;
/*     */             break;
/*     */         } 
/*     */       } 
/* 279 */       SetupControls.this.setKeyboardConfigs();
/* 280 */       quitEntryMode();
/*     */     }
/*     */     
/*     */     void setJoystickConfigParam(int axisIndex, int buttonIndex) {
/* 284 */       JoystickConfig joystickConfig = (JoystickConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 285 */       switch (this.configParam) {
/*     */         case KEY_LEFT:
/*     */         case KEY_RIGHT:
/* 288 */           if (axisIndex == -1)
/* 289 */             return;  if (axisIndex == joystickConfig.yAxis)
/* 290 */             return;  joystickConfig.xAxis = axisIndex;
/*     */           break;
/*     */         
/*     */         case KEY_UP:
/*     */         case KEY_DOWN:
/* 295 */           if (axisIndex == -1)
/* 296 */             return;  if (axisIndex == joystickConfig.xAxis)
/* 297 */             return;  joystickConfig.yAxis = axisIndex;
/*     */           break;
/*     */         
/*     */         case BUTTON_1:
/* 301 */           if (buttonIndex == -1)
/* 302 */             return;  if (buttonIndex == joystickConfig.button2)
/* 303 */             return;  joystickConfig.button1 = buttonIndex;
/*     */           break;
/*     */         
/*     */         case BUTTON_2:
/* 307 */           if (buttonIndex == -1)
/* 308 */             return;  if (buttonIndex == joystickConfig.button1)
/* 309 */             return;  joystickConfig.button2 = buttonIndex;
/*     */           break;
/*     */       } 
/* 312 */       if (joystickConfig.isConfigured()) {
/* 313 */         SetupControls.this.setJoystickConfigs();
/*     */       }
/* 315 */       quitEntryMode();
/*     */     }
/*     */     
/*     */     private void quitEntryMode() {
/* 319 */       this.entryMode = false;
/* 320 */       SetupControls.this.listeningConfigButton = null;
/* 321 */       SetupControls.this.game.reloadInputDevices();
/* 322 */       Gdx.input.setInputProcessor(null);
/* 323 */       SetupControls.this.refreshAllWidgets();
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Down() {
/* 328 */       if (!this.entryMode) {
/* 329 */         this.entryMode = true;
/* 330 */         SetupControls.this.listeningConfigButton = this;
/* 331 */         SetupControls.this.game.inputDevices.clear();
/* 332 */         Gdx.input.setInputProcessor(SetupControls.this.inputProcessor);
/* 333 */         SetupControls.this.refreshAllWidgets();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void setKeyboardConfigs() {
/* 339 */     this.game.settings.setKeyboardConfigs(this.keyboardConfigs);
/*     */   }
/*     */   
/*     */   private void setJoystickConfigs() {
/* 343 */     ArrayList<JoystickConfig> configuredConfigs = new ArrayList<>();
/* 344 */     for (JoystickConfig joystickConfig : this.joystickConfigs) {
/* 345 */       if (joystickConfig.isConfigured()) {
/* 346 */         configuredConfigs.add(joystickConfig);
/*     */       }
/*     */     } 
/* 349 */     this.game.settings.setJoystickConfigs(configuredConfigs);
/*     */   }
/*     */   
/*     */   private boolean isKeyCodeAssigned(int keyCode, ConfigParam configParam, int port) {
/* 353 */     for (int i = 0; i < 2; i++) {
/* 354 */       KeyboardConfig config = this.keyboardConfigs.get(i);
/* 355 */       if (config.keyLeft == keyCode && (configParam != ConfigParam.KEY_LEFT || port != i))
/* 356 */         return true; 
/* 357 */       if (config.keyRight == keyCode && (configParam != ConfigParam.KEY_RIGHT || port != i))
/* 358 */         return true; 
/* 359 */       if (config.keyUp == keyCode && (configParam != ConfigParam.KEY_UP || port != i))
/* 360 */         return true; 
/* 361 */       if (config.keyDown == keyCode && (configParam != ConfigParam.KEY_DOWN || port != i))
/* 362 */         return true; 
/* 363 */       if (config.button1 == keyCode && (configParam != ConfigParam.BUTTON_1 || port != i))
/* 364 */         return true; 
/* 365 */       if (config.button2 == keyCode && (configParam != ConfigParam.BUTTON_2 || port != i))
/* 366 */         return true; 
/*     */     } 
/* 368 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isKeyCodeReserved(int keyCode) {
/* 377 */     Integer[] reservedKeyCodes = { Integer.valueOf(62), Integer.valueOf(46), Integer.valueOf(44), Integer.valueOf(36), Integer.valueOf(244), Integer.valueOf(245), Integer.valueOf(246), Integer.valueOf(247), Integer.valueOf(248), Integer.valueOf(249), Integer.valueOf(250), Integer.valueOf(251), Integer.valueOf(252), Integer.valueOf(253), Integer.valueOf(254), Integer.valueOf(255) };
/*     */     
/* 379 */     return Arrays.<Integer>asList(reservedKeyCodes).contains(Integer.valueOf(keyCode));
/*     */   }
/*     */   
/*     */   private class LeftLabel
/*     */     extends Button {
/*     */     LeftLabel() {
/* 385 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2 - 150, 340, 200, 40);
/* 386 */       setText(Assets.gettext("CONTROLS.LEFT"), Font.Align.CENTER, Assets.font14);
/* 387 */       setColor(4210752);
/* 388 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class LeftButton
/*     */     extends ConfigButton {
/*     */     LeftButton() {
/* 395 */       this.configParam = SetupControls.ConfigParam.KEY_LEFT;
/* 396 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2 - 150, 380, 200, 46);
/* 397 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     } public void refresh() {
/*     */       KeyboardConfig keyboardConfig;
/*     */       JoystickConfig joystickConfig;
/*     */       int xAxisIndex;
/* 402 */       switch (SetupControls.this.selectedInputDeviceButton.config.type) {
/*     */         case KEY_LEFT:
/* 404 */           keyboardConfig = (KeyboardConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 405 */           if (this.entryMode) {
/* 406 */             setText("?");
/* 407 */             setColor(15439154); break;
/*     */           } 
/* 409 */           setText(Input.Keys.toString(keyboardConfig.keyLeft).toUpperCase());
/* 410 */           setColor(5539924);
/*     */           break;
/*     */ 
/*     */         
/*     */         case KEY_RIGHT:
/* 415 */           joystickConfig = (JoystickConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 416 */           xAxisIndex = joystickConfig.xAxis;
/* 417 */           if (this.entryMode) {
/* 418 */             setText("?");
/* 419 */             setColor(15439154); break;
/* 420 */           }  if (xAxisIndex == -1) {
/* 421 */             setText(Assets.gettext("CONTROLS.UNKNOWN"));
/* 422 */             setColor(11796480); break;
/*     */           } 
/* 424 */           setText(Assets.gettext("CONTROLS.AXIS") + " " + xAxisIndex);
/* 425 */           setColor(5539924);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class RightLabel
/*     */     extends Button
/*     */   {
/*     */     RightLabel() {
/* 435 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2 + 150, 340, 200, 40);
/* 436 */       setText(Assets.gettext("CONTROLS.RIGHT"), Font.Align.CENTER, Assets.font14);
/* 437 */       setColor(4210752);
/* 438 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RightButton
/*     */     extends ConfigButton {
/*     */     RightButton() {
/* 445 */       this.configParam = SetupControls.ConfigParam.KEY_RIGHT;
/* 446 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2 + 150, 380, 200, 46);
/* 447 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     } public void refresh() {
/*     */       KeyboardConfig keyboardConfig;
/*     */       JoystickConfig joystickConfig;
/*     */       int xAxisIndex;
/* 452 */       switch (SetupControls.this.selectedInputDeviceButton.config.type) {
/*     */         case KEY_LEFT:
/* 454 */           keyboardConfig = (KeyboardConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 455 */           if (this.entryMode) {
/* 456 */             setText("?");
/* 457 */             setColor(15439154); break;
/*     */           } 
/* 459 */           setText(Input.Keys.toString(keyboardConfig.keyRight).toUpperCase());
/* 460 */           setColor(5539924);
/*     */           break;
/*     */ 
/*     */         
/*     */         case KEY_RIGHT:
/* 465 */           joystickConfig = (JoystickConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 466 */           xAxisIndex = joystickConfig.xAxis;
/* 467 */           if (this.entryMode) {
/* 468 */             setText("?");
/* 469 */             setColor(15439154); break;
/* 470 */           }  if (xAxisIndex == -1) {
/* 471 */             setText(Assets.gettext("CONTROLS.UNKNOWN"));
/* 472 */             setColor(11796480); break;
/*     */           } 
/* 474 */           setText(Assets.gettext("CONTROLS.AXIS") + " " + xAxisIndex);
/* 475 */           setColor(5539924);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class UpLabel
/*     */     extends Button
/*     */   {
/*     */     UpLabel() {
/* 485 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2, 180, 200, 40);
/* 486 */       setText(Assets.gettext("CONTROLS.UP"), Font.Align.CENTER, Assets.font14);
/* 487 */       setColor(4210752);
/* 488 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class UpButton
/*     */     extends ConfigButton {
/*     */     UpButton() {
/* 495 */       this.configParam = SetupControls.ConfigParam.KEY_UP;
/* 496 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2, 220, 200, 46);
/* 497 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     } public void refresh() {
/*     */       KeyboardConfig keyboardConfig;
/*     */       JoystickConfig joystickConfig;
/*     */       int yAxisIndex;
/* 502 */       switch (SetupControls.this.selectedInputDeviceButton.config.type) {
/*     */         case KEY_LEFT:
/* 504 */           keyboardConfig = (KeyboardConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 505 */           if (this.entryMode) {
/* 506 */             setText("?");
/* 507 */             setColor(15439154); break;
/*     */           } 
/* 509 */           setText(Input.Keys.toString(keyboardConfig.keyUp).toUpperCase());
/* 510 */           setColor(5539924);
/*     */           break;
/*     */ 
/*     */         
/*     */         case KEY_RIGHT:
/* 515 */           joystickConfig = (JoystickConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 516 */           yAxisIndex = joystickConfig.yAxis;
/* 517 */           if (this.entryMode) {
/* 518 */             setText("?");
/* 519 */             setColor(15439154); break;
/* 520 */           }  if (yAxisIndex == -1) {
/* 521 */             setText(Assets.gettext("CONTROLS.UNKNOWN"));
/* 522 */             setColor(11796480); break;
/*     */           } 
/* 524 */           setText(Assets.gettext("CONTROLS.AXIS") + " " + yAxisIndex);
/* 525 */           setColor(5539924);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class DownLabel
/*     */     extends Button
/*     */   {
/*     */     DownLabel() {
/* 535 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2, 500, 200, 40);
/* 536 */       setText(Assets.gettext("CONTROLS.DOWN"), Font.Align.CENTER, Assets.font14);
/* 537 */       setColor(4210752);
/* 538 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class DownButton
/*     */     extends ConfigButton {
/*     */     DownButton() {
/* 545 */       this.configParam = SetupControls.ConfigParam.KEY_DOWN;
/* 546 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2, 540, 200, 46);
/* 547 */       setText("", Font.Align.CENTER, Assets.font14);
/*     */     } public void refresh() {
/*     */       KeyboardConfig keyboardConfig;
/*     */       JoystickConfig joystickConfig;
/*     */       int yAxisIndex;
/* 552 */       switch (SetupControls.this.selectedInputDeviceButton.config.type) {
/*     */         case KEY_LEFT:
/* 554 */           keyboardConfig = (KeyboardConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 555 */           if (this.entryMode) {
/* 556 */             setText("?");
/* 557 */             setColor(15439154); break;
/*     */           } 
/* 559 */           setText(Input.Keys.toString(keyboardConfig.keyDown).toUpperCase());
/* 560 */           setColor(5539924);
/*     */           break;
/*     */ 
/*     */         
/*     */         case KEY_RIGHT:
/* 565 */           joystickConfig = (JoystickConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 566 */           yAxisIndex = joystickConfig.yAxis;
/* 567 */           if (this.entryMode) {
/* 568 */             setText("?");
/* 569 */             setColor(15439154); break;
/* 570 */           }  if (yAxisIndex == -1) {
/* 571 */             setText(Assets.gettext("CONTROLS.UNKNOWN"));
/* 572 */             setColor(11796480); break;
/*     */           } 
/* 574 */           setText(Assets.gettext("CONTROLS.AXIS") + " " + yAxisIndex);
/* 575 */           setColor(5539924);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class FireLabel
/*     */     extends Button
/*     */   {
/*     */     FireLabel(int buttonNumber) {
/* 585 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2 + 420, 240 + 210 * (buttonNumber - 1), 200, 40);
/* 586 */       setText(Assets.gettext("CONTROLS.BUTTON") + " " + ((buttonNumber == 1) ? "A" : "B"), Font.Align.CENTER, Assets.font14);
/* 587 */       setColor(4210752);
/* 588 */       setActive(false);
/*     */     }
/*     */   }
/*     */   
/*     */   private class FireButton
/*     */     extends ConfigButton {
/*     */     final int buttonNumber;
/*     */     
/*     */     FireButton(int buttonNumber) {
/* 597 */       this.buttonNumber = buttonNumber;
/* 598 */       this.configParam = (buttonNumber == 1) ? SetupControls.ConfigParam.BUTTON_1 : SetupControls.ConfigParam.BUTTON_2;
/* 599 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 200) / 2 + 420, 280 + 210 * (buttonNumber - 1), 200, 46);
/* 600 */       setText("", Font.Align.CENTER, Assets.font14); } public void refresh() {
/*     */       KeyboardConfig keyboardConfig;
/*     */       int value;
/*     */       JoystickConfig joystickConfig;
/*     */       int index;
/* 605 */       switch (SetupControls.this.selectedInputDeviceButton.config.type) {
/*     */         case KEY_LEFT:
/* 607 */           keyboardConfig = (KeyboardConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 608 */           value = (this.buttonNumber == 1) ? keyboardConfig.button1 : keyboardConfig.button2;
/* 609 */           if (this.entryMode) {
/* 610 */             setText("?");
/* 611 */             setColor(15439154); break;
/*     */           } 
/* 613 */           setText(Input.Keys.toString(value).toUpperCase());
/* 614 */           setColor(5539924);
/*     */           break;
/*     */ 
/*     */         
/*     */         case KEY_RIGHT:
/* 619 */           joystickConfig = (JoystickConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 620 */           index = (this.buttonNumber == 1) ? joystickConfig.button1 : joystickConfig.button2;
/* 621 */           if (this.entryMode) {
/* 622 */             setText("?");
/* 623 */             setColor(15439154); break;
/* 624 */           }  if (index == -1) {
/* 625 */             setText(Assets.gettext("CONTROLS.UNKNOWN"));
/* 626 */             setColor(11796480); break;
/*     */           } 
/* 628 */           setText(index);
/* 629 */           setColor(5539924);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExitButton
/*     */     extends Button
/*     */   {
/*     */     ExitButton() {
/* 639 */       setColor(13124096);
/* 640 */       Objects.requireNonNull(SetupControls.this.game.gui); setGeometry((1280 - 180) / 2, 660, 180, 36);
/* 641 */       setText(Assets.gettext("EXIT"), Font.Align.CENTER, Assets.font14);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onFire1Up() {
/* 646 */       Controllers.removeListener((ControllerListener)SetupControls.this.joystickListener);
/* 647 */       SetupControls.this.game.settings.save();
/* 648 */       SetupControls.this.game.setScreen((Screen)new Main(SetupControls.this.game));
/*     */     }
/*     */   }
/*     */   
/*     */   private class JoystickListener extends ControllerAdapter {
/*     */     private JoystickListener() {}
/*     */     
/*     */     public boolean buttonUp(Controller controller, int buttonIndex) {
/* 656 */       if (SetupControls.this.selectedInputDeviceButton.config.type != InputDevice.Type.JOYSTICK || SetupControls.this.listeningConfigButton == null) {
/* 657 */         return false;
/*     */       }
/*     */       
/* 660 */       if (EMath.isAmong(SetupControls.this.listeningConfigButton.configParam, (Object[])SetupControls.this.buttonParams)) {
/* 661 */         JoystickConfig joystickConfig = (JoystickConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 662 */         if (controller.getName().equals(joystickConfig.name)) {
/* 663 */           SetupControls.this.listeningConfigButton.setJoystickConfigParam(-1, buttonIndex);
/*     */         }
/*     */       } 
/* 666 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean axisMoved(Controller controller, int axisIndex, float value) {
/* 671 */       if (SetupControls.this.selectedInputDeviceButton.config.type != InputDevice.Type.JOYSTICK || SetupControls.this.listeningConfigButton == null) {
/* 672 */         return false;
/*     */       }
/*     */       
/* 675 */       if (EMath.isAmong(SetupControls.this.listeningConfigButton.configParam, (Object[])SetupControls.this.axisParams)) {
/* 676 */         JoystickConfig joystickConfig = (JoystickConfig)SetupControls.this.selectedInputDeviceButton.config;
/* 677 */         if (controller.getName().equals(joystickConfig.name)) {
/* 678 */           SetupControls.this.listeningConfigButton.setJoystickConfigParam(axisIndex, -1);
/*     */         }
/*     */       } 
/* 681 */       return false;
/*     */     } }
/*     */   
/*     */   private class SetupInputProcessor extends InputAdapter {
/*     */     private SetupInputProcessor() {}
/*     */     
/*     */     public boolean keyUp(int keycode) {
/* 688 */       SetupControls.this.listeningConfigButton.setKeyCode(keycode);
/* 689 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\SetupControls.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */