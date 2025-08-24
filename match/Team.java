/*     */ package com.ygames.ysoccer.match;
/*     */ 
/*     */ import com.badlogic.gdx.Gdx;
/*     */ import com.badlogic.gdx.files.FileHandle;
/*     */ import com.badlogic.gdx.graphics.Texture;
/*     */ import com.badlogic.gdx.graphics.g2d.TextureRegion;
/*     */ import com.badlogic.gdx.math.Vector2;
/*     */ import com.badlogic.gdx.utils.Json;
/*     */ import com.badlogic.gdx.utils.JsonValue;
/*     */ import com.ygames.ysoccer.framework.Assets;
/*     */ import com.ygames.ysoccer.framework.EMath;
/*     */ import com.ygames.ysoccer.framework.GLGame;
/*     */ import com.ygames.ysoccer.framework.InputDevice;
/*     */ import com.ygames.ysoccer.framework.RgbPair;
/*     */ import com.ygames.ysoccer.framework.TeamList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Team
/*     */   implements Json.Serializable
/*     */ {
/*     */   public enum Type
/*     */   {
/*  43 */     CLUB, NATIONAL, CUSTOM; }
/*     */   
/*  45 */   public enum ControlMode { UNDEFINED, COMPUTER, PLAYER, COACH; }
/*     */   
/*  47 */   private final int[] controlModeColors = new int[] { 6710886, 9969182, 1195976, 39900 };
/*     */   public Match match;
/*     */   public String name;
/*     */   public Type type;
/*     */   public String path;
/*     */   public String country;
/*     */   private String confederation;
/*     */   public String league;
/*     */   public String city;
/*     */   public String stadium;
/*     */   public Coach coach;
/*     */   public int tactics;
/*     */   public int kitIndex;
/*     */   public List<Kit> kits;
/*     */   public static final int MIN_KITS = 3;
/*     */   public static final int MAX_KITS = 5;
/*     */   public int index;
/*     */   public List<Player> players;
/*     */   public List<Player> lineup;
/*     */   int substitutionsCount;
/*     */   int kickerIndex;
/*     */   ArrayList<Player> barrier;
/*     */   private static final Map<Player.Role, Player.Role[]> substitutionRules;
/*     */   public ControlMode controlMode;
/*     */   public InputDevice inputDevice;
/*     */   int side;
/*     */   Player near1;
/*     */   Player bestDefender;
/*     */   public TextureRegion image;
/*     */   public boolean imageIsDefaultLogo;
/*     */   
/*     */   static {
/*  79 */     Map<Player.Role, Player.Role[]> aMap = (Map)new HashMap<>();
/*  80 */     aMap.put(Player.Role.GOALKEEPER, new Player.Role[] { Player.Role.GOALKEEPER, Player.Role.GOALKEEPER });
/*  81 */     aMap.put(Player.Role.RIGHT_BACK, new Player.Role[] { Player.Role.LEFT_BACK, Player.Role.DEFENDER });
/*  82 */     aMap.put(Player.Role.LEFT_BACK, new Player.Role[] { Player.Role.RIGHT_BACK, Player.Role.DEFENDER });
/*  83 */     aMap.put(Player.Role.DEFENDER, new Player.Role[] { Player.Role.RIGHT_BACK, Player.Role.LEFT_BACK });
/*  84 */     aMap.put(Player.Role.RIGHT_WINGER, new Player.Role[] { Player.Role.LEFT_WINGER, Player.Role.MIDFIELDER });
/*  85 */     aMap.put(Player.Role.LEFT_WINGER, new Player.Role[] { Player.Role.RIGHT_WINGER, Player.Role.MIDFIELDER });
/*  86 */     aMap.put(Player.Role.MIDFIELDER, new Player.Role[] { Player.Role.RIGHT_WINGER, Player.Role.LEFT_WINGER });
/*  87 */     aMap.put(Player.Role.ATTACKER, new Player.Role[] { Player.Role.RIGHT_WINGER, Player.Role.LEFT_WINGER });
/*  88 */     substitutionRules = Collections.unmodifiableMap((Map)aMap);
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
/*     */   public Team() {
/* 102 */     this.controlMode = ControlMode.UNDEFINED;
/* 103 */     this.kits = new ArrayList<>();
/* 104 */     this.players = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public void read(Json json, JsonValue jsonData) {
/* 109 */     this.name = jsonData.getString("name");
/* 110 */     this.path = jsonData.getString("path", null);
/* 111 */     this.controlMode = (ControlMode)json.readValue("controlMode", ControlMode.class, ControlMode.UNDEFINED, jsonData);
/* 112 */     this.type = (Type)json.readValue("type", Type.class, jsonData);
/* 113 */     this.country = jsonData.getString("country");
/* 114 */     this.confederation = jsonData.getString("confederation", null);
/* 115 */     this.league = jsonData.getString("league", null);
/* 116 */     this.city = jsonData.getString("city");
/* 117 */     this.stadium = jsonData.getString("stadium");
/*     */     
/* 119 */     this.coach = (Coach)json.readValue("coach", Coach.class, jsonData);
/* 120 */     this.coach.team = this;
/*     */     
/* 122 */     String tacticsCode = jsonData.getString("tactics");
/* 123 */     this.tactics = 0;
/* 124 */     for (int i = 0; i < Tactics.codes.length; i++) {
/* 125 */       if (Tactics.codes[i].equals(tacticsCode)) {
/* 126 */         this.tactics = i;
/*     */       }
/*     */     } 
/*     */     
/* 130 */     Kit[] kitsArray = (Kit[])json.readValue("kits", Kit[].class, jsonData);
/* 131 */     Collections.addAll(this.kits, kitsArray);
/*     */     
/* 133 */     Player[] playersArray = (Player[])json.readValue("players", Player[].class, jsonData);
/* 134 */     for (Player player : playersArray) {
/* 135 */       player.team = this;
/* 136 */       this.players.add(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(Json json) {
/* 142 */     json.writeValue("name", this.name);
/* 143 */     if (this.path != null) {
/* 144 */       json.writeValue("path", this.path);
/*     */     }
/* 146 */     if (this.controlMode != ControlMode.UNDEFINED) {
/* 147 */       json.writeValue("controlMode", this.controlMode);
/*     */     }
/* 149 */     json.writeValue("type", this.type);
/* 150 */     json.writeValue("country", this.country);
/* 151 */     if (this.confederation != null) {
/* 152 */       json.writeValue("confederation", this.confederation);
/*     */     }
/* 154 */     if (this.league != null) {
/* 155 */       json.writeValue("league", this.league);
/*     */     }
/* 157 */     json.writeValue("city", this.city);
/* 158 */     json.writeValue("stadium", this.stadium);
/* 159 */     json.writeValue("coach", this.coach);
/* 160 */     json.writeValue("tactics", Tactics.codes[this.tactics]);
/* 161 */     json.writeValue("kits", this.kits, Kit[].class, Kit.class);
/* 162 */     json.writeValue("players", this.players, Player[].class, Player.class);
/*     */   }
/*     */   
/*     */   public void setInputDevice(InputDevice inputDevice) {
/* 166 */     this.inputDevice = inputDevice;
/*     */   }
/*     */   
/*     */   void setSide(int side) {
/* 170 */     this.side = side;
/* 171 */     for (Player player : this.lineup) {
/* 172 */       player.side = side;
/*     */     }
/*     */   }
/*     */   
/*     */   public Player newPlayer() {
/* 177 */     if (this.players.size() == 26) {
/* 178 */       return null;
/*     */     }
/*     */     
/* 181 */     Player player = new Player();
/* 182 */     player.name = "";
/* 183 */     player.shirtName = "";
/* 184 */     player.nationality = "";
/* 185 */     player.role = Player.Role.GOALKEEPER;
/* 186 */     rotatePlayerNumber(player, 1);
/* 187 */     player.skinColor = Skin.Color.PINK;
/* 188 */     player.hairColor = Hair.Color.BLACK;
/* 189 */     player.hairStyle = "SMOOTH_A";
/* 190 */     player.skills = new Player.Skills();
/* 191 */     this.players.add(player);
/* 192 */     return player;
/*     */   }
/*     */   
/*     */   void beforeMatch(Match match) {
/* 196 */     this.match = match;
/* 197 */     this.lineup = new ArrayList<>();
/* 198 */     int lineupSize = Math.min(this.players.size(), 11 + (match.getSettings()).benchSize);
/* 199 */     for (int i = 0; i < lineupSize; i++) {
/* 200 */       Player player = this.players.get(i);
/* 201 */       player.beforeMatch(match);
/* 202 */       player.index = i;
/* 203 */       this.lineup.add(player);
/*     */     } 
/* 205 */     this.substitutionsCount = 0;
/* 206 */     this.kickerIndex = 10;
/* 207 */     this.barrier = new ArrayList<>();
/*     */   }
/*     */   
/*     */   void beforeTraining(Training training) {
/* 211 */     this.lineup = new ArrayList<>();
/* 212 */     int lineupSize = this.players.size();
/* 213 */     for (int i = 0; i < lineupSize; i++) {
/* 214 */       Player player = this.players.get(i);
/* 215 */       player.beforeTraining(training);
/* 216 */       player.index = i;
/* 217 */       this.lineup.add(player);
/*     */     } 
/*     */   }
/*     */   
/*     */   void save(int subframe) {
/* 222 */     int len = this.lineup.size();
/* 223 */     for (int i = 0; i < len; i++) {
/* 224 */       ((Player)this.lineup.get(i)).save(subframe);
/*     */     }
/*     */   }
/*     */   
/*     */   void findNearest() {
/* 229 */     findNearest(11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void findNearest(int count) {
/* 236 */     this.near1 = null; int i;
/* 237 */     for (i = 0; i < count; i++) {
/* 238 */       Player player = this.lineup.get(i);
/*     */ 
/*     */ 
/*     */       
/* 242 */       if (player.frameDistance != 128)
/*     */       {
/*     */         
/* 245 */         if (this.near1 == null || player.frameDistance < this.near1.frameDistance) {
/* 246 */           this.near1 = player;
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/* 251 */     if (this.near1 == null) {
/* 252 */       this.near1 = this.lineup.get(0);
/* 253 */       for (i = 1; i < count; i++) {
/* 254 */         Player player = this.lineup.get(i);
/*     */         
/* 256 */         if (player.ballDistance < this.near1.ballDistance) {
/* 257 */           this.near1 = player;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void findBestDefender() {
/* 264 */     Player newBestDefender = null;
/*     */     
/* 266 */     float minBallDistance = 1280.0F;
/* 267 */     if (this.match.ball.ownerLast != null && this.match.ball.ownerLast.team != this) {
/* 268 */       float ballToGoalDistance = EMath.dist(this.match.ball.x, this.match.ball.y, 0.0F, (640 * this.side));
/*     */       
/* 270 */       for (int i = 1; i < 11; i++) {
/* 271 */         Player player = this.lineup.get(i);
/*     */         
/* 273 */         float playerGoalDistance = EMath.dist(player.x, player.y, 0.0F, (640 * this.side));
/* 274 */         if (playerGoalDistance < 0.95F * ballToGoalDistance && player.ballDistance < minBallDistance) {
/*     */           
/* 276 */           newBestDefender = player;
/* 277 */           minBallDistance = player.ballDistance;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 282 */     if (this.bestDefender == null || minBallDistance < 0.9F * this.bestDefender.ballDistance) {
/* 283 */       this.bestDefender = newBestDefender;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void updateTactics(boolean relativeToCenter) {
/* 289 */     int ball_zone = 17 - this.side * this.match.ball.zoneX - 5 * this.side * this.match.ball.zoneY;
/*     */     
/* 291 */     if (relativeToCenter) {
/* 292 */       ball_zone = 17;
/*     */     }
/*     */     
/* 295 */     for (int i = 1; i < 11; i++) {
/*     */       
/* 297 */       Player player = this.lineup.get(i);
/*     */       
/* 299 */       int tx = (Assets.tactics[this.tactics]).target[i][ball_zone][0];
/* 300 */       int ty = (Assets.tactics[this.tactics]).target[i][ball_zone][1];
/*     */       
/* 302 */       player.tx = (1.0F - Math.abs(this.match.ball.mx)) * tx + Math.abs(this.match.ball.mx) * tx;
/* 303 */       player.ty = (1.0F - Math.abs(this.match.ball.my)) * ty + Math.abs(this.match.ball.my) * ty;
/*     */       
/* 305 */       player.tx = -this.side * player.tx;
/* 306 */       player.ty = -this.side * (player.ty - 4.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   void keepTargetDistanceFrom(Vector2 position) {
/* 311 */     for (int i = 1; i < 11; i++) {
/* 312 */       Player player = this.lineup.get(i);
/* 313 */       Vector2 vec = new Vector2(player.tx, player.ty);
/* 314 */       vec.sub(position);
/* 315 */       if (vec.len() < 96.66667F) {
/* 316 */         vec.setLength(96.66667F);
/* 317 */         vec.add(position);
/* 318 */         player.setTarget(vec.x, vec.y);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   void setFreeKickBarrier() {
/* 324 */     this.barrier.clear();
/*     */ 
/*     */     
/* 327 */     float nearPostAngle = EMath.angle(this.match.foul.position.x, this.match.foul.position.y, Math.signum(this.match.foul.position.x) * 71.0F, (this.side * 640));
/* 328 */     float goalCenterAngle = EMath.angle(this.match.foul.position.x, this.match.foul.position.y, 0.0F, (this.side * 640));
/* 329 */     float angleToCover = EMath.signedAngleDiff(goalCenterAngle, nearPostAngle);
/*     */ 
/*     */     
/* 332 */     Vector2 foulToGoal = (new Vector2(0.0F, (this.side * 640))).sub(this.match.foul.position);
/* 333 */     Vector2 foulToBarrier = (new Vector2(foulToGoal)).setLength(102.66667F);
/* 334 */     float angleStep = EMath.aTan2(12.0F, foulToBarrier.len()) * Math.signum(angleToCover);
/*     */ 
/*     */     
/* 337 */     int size = EMath.ceil(angleToCover / angleStep);
/*     */     
/* 339 */     while (this.barrier.size() < size) {
/*     */ 
/*     */       
/* 342 */       float minDistance = 1280.0F;
/* 343 */       float goalToBarrierDistance = foulToGoal.len() - foulToBarrier.len();
/* 344 */       Player nearest = null;
/* 345 */       for (int j = 1; j < 11; j++) {
/* 346 */         Player player = this.lineup.get(j);
/* 347 */         if (!this.barrier.contains(player)) {
/* 348 */           float goalDistance = EMath.dist(player.tx, player.ty, 0.0F, (player.team.side * 640));
/* 349 */           if (goalDistance > goalToBarrierDistance && goalDistance < minDistance) {
/* 350 */             nearest = player;
/* 351 */             minDistance = goalDistance;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 356 */       if (nearest != null) {
/* 357 */         Gdx.app.debug(nearest.shirtName, "added to barrier");
/* 358 */         this.barrier.add(nearest);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     float barrierAngle = EMath.angle(this.match.foul.position.x, this.match.foul.position.y, Math.signum(this.match.foul.position.x) * 71.0F / 2.0F, (this.side * 640));
/*     */ 
/*     */ 
/*     */     
/* 369 */     Vector2 barrierCenter = (new Vector2(1.0F, 1.0F)).setLength(102.66667F).setAngle(barrierAngle).add(this.match.foul.position.x, this.match.foul.position.y);
/* 370 */     for (int i = 0; i < this.barrier.size(); i++) {
/* 371 */       float offset = i - (this.barrier.size() - 1) / 2.0F;
/*     */ 
/*     */ 
/*     */       
/* 375 */       Vector2 barrierPosition = (new Vector2(1.0F, 1.0F)).setLength(Math.abs(offset) * 12.0F).setAngle(barrierAngle + 90.0F * Math.signum(offset)).add(barrierCenter);
/* 376 */       ((Player)this.barrier.get(i)).setTarget(barrierPosition.x, barrierPosition.y);
/*     */     } 
/*     */   }
/*     */   
/*     */   void updateFrameDistance() {
/* 381 */     updateFrameDistance(11);
/*     */   }
/*     */   
/*     */   void updateFrameDistance(int count) {
/* 385 */     for (int i = 0; i < count; i++) {
/* 386 */       ((Player)this.lineup.get(i)).updateFrameDistance();
/*     */     }
/*     */   }
/*     */   
/*     */   boolean updatePlayers(boolean limit) {
/* 391 */     findNearest();
/*     */ 
/*     */     
/* 394 */     if (this.match.subframe % 8 == 0) findBestDefender();
/*     */     
/* 396 */     return updateLineup(limit);
/*     */   }
/*     */   
/*     */   boolean updateLineup(boolean limit) {
/* 400 */     boolean move = false;
/*     */     
/* 402 */     int len = this.lineup.size();
/* 403 */     for (int i = 0; i < len; i++) {
/* 404 */       Player player = this.lineup.get(i);
/* 405 */       if (player.update(limit)) {
/* 406 */         move = true;
/*     */       }
/* 408 */       player.think();
/*     */     } 
/*     */     
/* 411 */     return move;
/*     */   }
/*     */   
/*     */   void updateLineupAi() {
/* 415 */     int len = this.lineup.size();
/* 416 */     for (int i = 0; i < len; i++) {
/* 417 */       Player player = this.lineup.get(i);
/* 418 */       if (player.inputDevice == player.ai) {
/* 419 */         player.updateAi();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int nonAiInputDevicesCount() {
/* 425 */     int n = 0;
/* 426 */     for (Player player : this.players) {
/* 427 */       if (player.inputDevice != player.ai) n++; 
/*     */     } 
/* 429 */     return n;
/*     */   }
/*     */   
/*     */   public void deletePlayer(Player player) {
/* 433 */     if (this.players.size() > 16) {
/* 434 */       this.players.remove(player);
/*     */     }
/*     */   }
/*     */   
/*     */   public Kit newKit() {
/* 439 */     if (this.kits.size() == 5) {
/* 440 */       return null;
/*     */     }
/* 442 */     Kit kit = new Kit();
/* 443 */     this.kits.add(kit);
/* 444 */     return kit;
/*     */   }
/*     */   
/*     */   public Kit getKit() {
/* 448 */     return this.kits.get(this.kitIndex);
/*     */   }
/*     */   
/*     */   public boolean deleteKit() {
/* 452 */     if (this.kits.size() > 3) {
/* 453 */       return this.kits.remove(this.kits.get(this.kits.size() - 1));
/*     */     }
/* 455 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 460 */     if (obj == null || obj.getClass() != Team.class) return false;
/*     */     
/* 462 */     return Assets.teamsRootFolder.child(this.path).equals(Assets.teamsRootFolder.child(((Team)obj).path));
/*     */   }
/*     */   
/*     */   public void releaseNonAiInputDevices() {
/* 466 */     for (Player player : this.players) {
/* 467 */       if (player.inputDevice != player.ai) {
/* 468 */         player.inputDevice.available = true;
/* 469 */         player.inputDevice = (InputDevice)player.ai;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   boolean usesAutomaticInputDevice() {
/* 475 */     return (this.controlMode == ControlMode.PLAYER && this.inputDevice != null);
/*     */   }
/*     */   
/*     */   void setPlayersState(PlayerFsm.Id stateId, Player excluded) {
/* 479 */     for (int i = 0; i < 11; i++) {
/* 480 */       Player player = this.lineup.get(i);
/* 481 */       if (player != excluded) {
/* 482 */         player.setState(stateId);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   void assignAutomaticInputDevices(Player receiver) {
/* 488 */     if (usesAutomaticInputDevice()) {
/* 489 */       for (int i = 0; i < 11; i++) {
/* 490 */         Player player = this.lineup.get(i);
/* 491 */         if (player == receiver) {
/* 492 */           player.inputDevice = player.team.inputDevice;
/*     */         } else {
/* 494 */           player.inputDevice = (InputDevice)player.ai;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   void automaticInputDeviceSelection() {
/* 501 */     Ball ball = this.match.ball;
/*     */ 
/*     */     
/* 504 */     Player controlled = null;
/* 505 */     int len = this.lineup.size();
/* 506 */     for (int i = 0; i < len; i++) {
/* 507 */       Player player = this.lineup.get(i);
/* 508 */       if (player.inputDevice != player.ai) {
/* 509 */         controlled = player;
/*     */       }
/*     */     } 
/*     */     
/* 513 */     if (ball.owner != null) {
/*     */ 
/*     */       
/* 516 */       if (ball.owner.team.index == this.index) {
/* 517 */         if (ball.owner != controlled) {
/* 518 */           GLGame.debug(GLGame.LogType.PLAYER_SELECTION, getClass().getSimpleName(), ((controlled != null) ? controlled.numberName() : "") + " > " + ball.owner.numberName() + " because is ball owner (attacking)");
/* 519 */           if (controlled != null) {
/* 520 */             controlled.inputDevice = (InputDevice)controlled.ai;
/*     */           } else {
/* 522 */             controlled = ball.owner;
/*     */           } 
/* 524 */           ball.owner.inputDevice = this.inputDevice;
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/* 530 */       else if (bestDefenderBetterThan(controlled)) {
/* 531 */         GLGame.debug(GLGame.LogType.PLAYER_SELECTION, getClass().getSimpleName(), ((controlled != null) ? controlled.numberName() : "") + " > " + this.bestDefender.numberName() + " because is best defender (pressing)");
/* 532 */         if (controlled != null) {
/* 533 */           controlled.inputDevice = (InputDevice)controlled.ai;
/*     */         } else {
/* 535 */           controlled = this.bestDefender;
/*     */         } 
/* 537 */         this.bestDefender.inputDevice = this.inputDevice;
/* 538 */       } else if (controlled == null) {
/* 539 */         GLGame.debug(GLGame.LogType.PLAYER_SELECTION, getClass().getSimpleName(), " > " + this.near1.numberName() + " because of no best defender and no controlled (pressing)");
/* 540 */         controlled = this.near1;
/* 541 */         this.near1.inputDevice = this.inputDevice;
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 548 */     else if (this.match.ball.ownerLast != null && this.match.ball.ownerLast.team.index == this.index) {
/* 549 */       if (near1betterThan(controlled)) {
/* 550 */         GLGame.debug(GLGame.LogType.PLAYER_SELECTION, getClass().getSimpleName(), ((controlled != null) ? controlled.numberName() : "") + " > " + this.near1.numberName() + " because is nearest (attacking)");
/* 551 */         if (controlled != null) {
/* 552 */           controlled.inputDevice = (InputDevice)controlled.ai;
/*     */         } else {
/* 554 */           controlled = this.near1;
/*     */         } 
/* 556 */         this.near1.inputDevice = this.inputDevice;
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 563 */     else if (ball.ySide == this.side) {
/* 564 */       if (bestDefenderBetterThan(controlled)) {
/* 565 */         GLGame.debug(GLGame.LogType.PLAYER_SELECTION, getClass().getSimpleName(), ((controlled != null) ? controlled.numberName() : "") + " > " + this.bestDefender.numberName() + " because is best defender (intercepting ball in own side)");
/* 566 */         if (controlled != null) {
/* 567 */           controlled.inputDevice = (InputDevice)controlled.ai;
/*     */         } else {
/* 569 */           controlled = this.bestDefender;
/*     */         } 
/* 571 */         this.bestDefender.inputDevice = this.inputDevice;
/* 572 */       } else if (controlled == null) {
/* 573 */         GLGame.debug(GLGame.LogType.PLAYER_SELECTION, getClass().getSimpleName(), " > " + this.near1.numberName() + " because of no best defender and no controlled (intercepting ball in own side)");
/* 574 */         controlled = this.near1;
/* 575 */         this.near1.inputDevice = this.inputDevice;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 580 */     else if (near1betterThan(controlled)) {
/* 581 */       GLGame.debug(GLGame.LogType.PLAYER_SELECTION, getClass().getSimpleName(), ((controlled != null) ? controlled.numberName() : "") + " > " + this.near1.numberName() + " because is nearest (intercepting ball in opponent side)");
/* 582 */       if (controlled != null) {
/* 583 */         controlled.inputDevice = (InputDevice)controlled.ai;
/*     */       } else {
/* 585 */         controlled = this.near1;
/*     */       } 
/* 587 */       this.near1.inputDevice = this.inputDevice;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 592 */     if (controlled == null) {
/* 593 */       GLGame.debug(GLGame.LogType.PLAYER_SELECTION, getClass().getSimpleName(), " > " + this.near1.numberName() + " because of last option");
/*     */       
/* 595 */       if (this.near1.getState().checkId(PlayerFsm.Id.STATE_STAND_RUN)) {
/* 596 */         this.near1.inputDevice = this.inputDevice;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean near1betterThan(Player controlled) {
/* 602 */     if (this.near1 == controlled || this.near1.index == 0) return false;
/*     */     
/* 604 */     if (controlled == null) return true;
/*     */ 
/*     */     
/* 607 */     if (!controlled.checkState(PlayerFsm.Id.STATE_STAND_RUN)) return false;
/*     */ 
/*     */     
/* 610 */     if (this.near1 == controlled.passingMate) {
/* 611 */       return false;
/*     */     }
/* 613 */     return (this.near1.frameDistance < 0.5D * controlled.frameDistance);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean bestDefenderBetterThan(Player controlled) {
/* 618 */     if (this.bestDefender == null) return false;
/*     */     
/* 620 */     if (controlled == null) return true;
/*     */     
/* 622 */     return (this.bestDefender != controlled && this.bestDefender.frameDistance < 128);
/*     */   }
/*     */ 
/*     */   
/*     */   InputDevice fire1Down() {
/* 627 */     if (this.inputDevice != null) {
/* 628 */       if (this.inputDevice.fire1Down()) {
/* 629 */         return this.inputDevice;
/*     */       }
/*     */     } else {
/* 632 */       for (Player player : this.lineup) {
/* 633 */         if (player.inputDevice != player.ai && player.inputDevice.fire1Down()) {
/* 634 */           return player.inputDevice;
/*     */         }
/*     */       } 
/*     */     } 
/* 638 */     return null;
/*     */   }
/*     */   
/*     */   InputDevice fire1Up() {
/* 642 */     if (this.inputDevice != null) {
/* 643 */       if (this.inputDevice.fire1Up()) {
/* 644 */         return this.inputDevice;
/*     */       }
/*     */     } else {
/* 647 */       for (Player player : this.lineup) {
/* 648 */         if (player.inputDevice != player.ai && player.inputDevice.fire1Up()) {
/* 649 */           return player.inputDevice;
/*     */         }
/*     */       } 
/*     */     } 
/* 653 */     return null;
/*     */   }
/*     */   
/*     */   InputDevice fire2Down() {
/* 657 */     if (this.inputDevice != null) {
/* 658 */       if (this.inputDevice.fire2Down()) {
/* 659 */         return this.inputDevice;
/*     */       }
/*     */     } else {
/* 662 */       for (Player player : this.lineup) {
/* 663 */         if (player.inputDevice != player.ai && player.inputDevice.fire2Down()) {
/* 664 */           return player.inputDevice;
/*     */         }
/*     */       } 
/*     */     } 
/* 668 */     return null;
/*     */   }
/*     */   
/*     */   public Player playerAtPosition(int pos) {
/* 672 */     if (pos < this.players.size()) {
/* 673 */       int ply = (pos < 11) ? Tactics.order[(Assets.tactics[this.tactics]).basedOn][pos] : pos;
/* 674 */       return this.players.get(ply);
/*     */     } 
/* 676 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   Player lineupAtPosition(int pos) {
/* 681 */     if (pos < this.lineup.size()) {
/* 682 */       int ply = (pos < 11) ? Tactics.order[(Assets.tactics[this.tactics]).basedOn][pos] : pos;
/* 683 */       return this.lineup.get(ply);
/*     */     } 
/* 685 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   Player lastOfLineup() {
/* 690 */     for (int pos = 10; pos > 0; pos--) {
/* 691 */       Player ply = lineupAtPosition(pos);
/* 692 */       if (!ply.checkState(PlayerFsm.Id.STATE_OUTSIDE)) {
/* 693 */         return ply;
/*     */       }
/*     */     } 
/* 696 */     return null;
/*     */   }
/*     */   
/*     */   public int playerIndexAtPosition(int pos) {
/* 700 */     return playerIndexAtPosition(pos, null);
/*     */   }
/*     */   
/*     */   public int playerIndexAtPosition(int pos, Tactics tcs) {
/* 704 */     if (tcs == null) {
/* 705 */       tcs = Assets.tactics[this.tactics];
/*     */     }
/* 707 */     if (pos < this.players.size()) {
/* 708 */       return (pos < 11) ? Tactics.order[tcs.basedOn][pos] : pos;
/*     */     }
/* 710 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   int nearestBenchPlayerByRole(Player.Role role) {
/* 716 */     int level = -1;
/*     */ 
/*     */     
/* 719 */     Player.Role target = role;
/*     */     
/*     */     while (true) {
/* 722 */       for (int pos = 0; pos < 11; pos++) {
/* 723 */         if ((lineupAtPosition(pos)).role == target) {
/* 724 */           return pos;
/*     */         }
/*     */       } 
/*     */       
/* 728 */       level++;
/*     */ 
/*     */       
/* 731 */       if (level == 2) {
/* 732 */         return 0;
/*     */       }
/*     */       
/* 735 */       target = ((Player.Role[])substitutionRules.get(role))[level];
/*     */     } 
/*     */   }
/*     */   
/*     */   int defenseRating() {
/* 740 */     int defense = 0;
/* 741 */     for (int p = 0; p < 11; p++) {
/* 742 */       defense += playerAtPosition(p).getDefenseRating();
/*     */     }
/* 744 */     return defense;
/*     */   }
/*     */   
/*     */   int offenseRating() {
/* 748 */     int offense = 0;
/* 749 */     for (int p = 0; p < 11; p++) {
/* 750 */       offense += playerAtPosition(p).getOffenseRating();
/*     */     }
/* 752 */     return offense;
/*     */   }
/*     */   
/*     */   public static void kitAutoSelection(Team homeTeam, Team awayTeam) {
/* 756 */     for (int i = 0; i < homeTeam.kits.size(); i++) {
/* 757 */       for (int j = 0; j < awayTeam.kits.size(); j++) {
/* 758 */         Kit homeKit = homeTeam.kits.get(i);
/* 759 */         Kit awayKit = awayTeam.kits.get(j);
/* 760 */         float difference = Kit.colorDifference(homeKit, awayKit);
/* 761 */         if (difference > 45.0F) {
/* 762 */           homeTeam.kitIndex = i;
/* 763 */           awayTeam.kitIndex = j;
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   float getRank() {
/* 772 */     float r = 0.0F;
/* 773 */     for (int i = 0; i < 11; i++) {
/* 774 */       Player player = this.lineup.get(i);
/* 775 */       r += player.getValue() / 5.0F;
/*     */     } 
/* 777 */     return r / 11.0F;
/*     */   }
/*     */   
/*     */   public void rotatePlayerNumber(Player player, int direction) {
/*     */     boolean used;
/*     */     do {
/* 783 */       player.number = EMath.rotate(player.number, 1, 99, direction);
/* 784 */       used = false;
/* 785 */       for (Player ply : this.players) {
/* 786 */         if (ply != player && ply.number == player.number) {
/* 787 */           used = true;
/*     */         }
/*     */       } 
/* 790 */     } while (used);
/*     */   }
/*     */   
/*     */   public void persist() {
/* 794 */     if (this.path == null || this.path.equals("")) {
/* 795 */       Gdx.app.error("Team", "Cannot save in empty path");
/*     */       return;
/*     */     } 
/* 798 */     FileHandle fh = Assets.teamsRootFolder.child(this.path);
/* 799 */     String tmp = this.path;
/* 800 */     this.path = null;
/* 801 */     fh.writeString(Assets.json.prettyPrint(this), false, "UTF-8");
/* 802 */     this.path = tmp;
/*     */   }
/*     */   
/*     */   public static TeamList loadTeamList(List<String> paths) {
/* 806 */     TeamList teamList = new TeamList();
/* 807 */     for (String path : paths) {
/* 808 */       FileHandle teamFile = Assets.teamsRootFolder.child(path);
/* 809 */       if (teamFile.exists()) {
/* 810 */         Team team = (Team)Assets.json.fromJson(Team.class, teamFile.readString("UTF-8"));
/* 811 */         team.path = path;
/* 812 */         team.controlMode = ControlMode.COMPUTER;
/* 813 */         teamList.add(team);
/*     */       } 
/*     */     } 
/* 816 */     return teamList; } public void loadImage() { String logoPath;
/*     */     FileHandle customLogo;
/*     */     String flagPath;
/*     */     FileHandle file;
/* 820 */     if (this.image != null)
/*     */       return; 
/* 822 */     switch (this.type) {
/*     */       case CLUB:
/*     */       case CUSTOM:
/* 825 */         logoPath = this.path.replaceFirst("/team.", "/logo.").replaceFirst(".json", ".png");
/* 826 */         customLogo = Assets.teamsRootFolder.child(logoPath);
/* 827 */         if (customLogo.exists()) {
/* 828 */           Texture texture = new Texture(customLogo);
/* 829 */           this.image = new TextureRegion(texture);
/* 830 */           this.image.flip(false, true); break;
/*     */         } 
/* 832 */         this.image = ((Kit)this.kits.get(0)).loadLogo();
/* 833 */         this.imageIsDefaultLogo = true;
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case NATIONAL:
/* 839 */         flagPath = this.path.replaceFirst("/team.", "/flag.").replaceFirst(".json", ".png");
/* 840 */         file = Assets.teamsRootFolder.child(flagPath);
/* 841 */         if (!file.exists())
/*     */         {
/* 843 */           file = Gdx.files.internal("images/flags/medium/" + this.name.toLowerCase().replace(" ", "_").replace(".", "") + ".png");
/*     */         }
/* 845 */         if (file.exists()) {
/* 846 */           Texture texture = new Texture(file);
/* 847 */           this.image = new TextureRegion(texture);
/* 848 */           this.image.flip(false, true);
/*     */         } 
/*     */         break;
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public TextureRegion loadKit(int index) {
/* 856 */     String kitPath = this.path.replaceFirst("/team.", "/kit.").replaceFirst(".json", ".png");
/* 857 */     FileHandle file = Assets.teamsRootFolder.child(kitPath);
/* 858 */     if (file.exists()) {
/* 859 */       List<RgbPair> rgbPairs = new ArrayList<>();
/* 860 */       ((Kit)this.kits.get(index)).addKitColors(rgbPairs);
/* 861 */       return Assets.loadTextureRegion(file.path(), rgbPairs);
/*     */     } 
/*     */ 
/*     */     
/* 865 */     String[] names = { "home", "away", "third", "change1", "change2" };
/* 866 */     kitPath = this.path.replaceFirst("/team.", "/kit_" + names[index] + ".").replaceFirst(".json", ".png");
/* 867 */     file = Assets.teamsRootFolder.child(kitPath);
/* 868 */     if (file.exists()) {
/* 869 */       List<RgbPair> rgbPairs = new ArrayList<>();
/* 870 */       ((Kit)this.kits.get(index)).addKitColors(rgbPairs);
/* 871 */       return Assets.loadTextureRegion(file.path(), rgbPairs);
/*     */     } 
/*     */ 
/*     */     
/* 875 */     return ((Kit)this.kits.get(index)).loadImage();
/*     */   }
/*     */   
/*     */   public int controlModeColor() {
/* 879 */     return this.controlModeColors[this.controlMode.ordinal()];
/*     */   }
/*     */   
/*     */   Player searchPlayerTackledBy(Player tackler) {
/* 883 */     for (int i = 0; i < 11; i++) {
/* 884 */       Player ply = this.lineup.get(i);
/* 885 */       float feetDistance = EMath.dist(ply.x, ply.y, tackler.x, tackler.y);
/* 886 */       float bodyDistance = EMath.dist(ply.x, ply.y, tackler.x - 9.0F * EMath.cos(tackler.a), tackler.y - 9.0F * EMath.sin(tackler.a));
/* 887 */       if (feetDistance < 7.0F || bodyDistance < 9.0F) {
/* 888 */         return ply;
/*     */       }
/*     */     } 
/* 891 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Team.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */