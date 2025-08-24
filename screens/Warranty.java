/*    */ package com.ygames.ysoccer.screens;
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import com.badlogic.gdx.InputAdapter;
/*    */ import com.badlogic.gdx.InputProcessor;
/*    */ import com.badlogic.gdx.Screen;
/*    */ import com.badlogic.gdx.graphics.g2d.BitmapFont;
/*    */ import com.ygames.ysoccer.framework.GLGame;
/*    */ import com.ygames.ysoccer.framework.GLScreen;
/*    */ import com.ygames.ysoccer.gui.TextBox;
/*    */ import java.util.Arrays;
/*    */ import java.util.Objects;
/*    */ 
/*    */ class Warranty extends GLScreen {
/*    */   public Warranty(GLGame game) {
/* 15 */     super(game);
/*    */     
/* 17 */     Gdx.input.setInputProcessor((InputProcessor)new IntroInputProcessor());
/*    */ 
/*    */ 
/*    */     
/* 21 */     BitmapFont font = new BitmapFont(true);
/* 22 */     String[] lines = { "NO WARRANTY", "", "BECAUSE THE PROGRAM IS LICENSED FREE OF CHARGE, THERE IS NO WARRANTY", "FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE LAW.  EXCEPT WHEN", "OTHERWISE STATED IN WRITING THE COPYRIGHT HOLDERS AND/OR OTHER PARTIES", "PROVIDE THE PROGRAM \"AS IS\" WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESSED", "OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF", "MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.  THE ENTIRE RISK AS", "TO THE QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU.  SHOULD THE", "PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY SERVICING,", "REPAIR OR CORRECTION.", "", "IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN WRITING", "WILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MAY MODIFY AND/OR", "REDISTRIBUTE THE PROGRAM AS PERMITTED ABOVE, BE LIABLE TO YOU FOR DAMAGES", "INCLUDING ANY GENERAL, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING", "OUT OF THE USE OR INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED", "TO LOSS OF DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY", "YOU OR THIRD PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY OTHER", "PROGRAMS), EVEN IF SUCH HOLDER OR OTHER PARTY HAS BEEN ADVISED OF THE", "POSSIBILITY OF SUCH DAMAGES.", "", "Press any key to return" };
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
/*    */ 
/*    */     
/* 47 */     Objects.requireNonNull(game.gui); TextBox textBox = new TextBox(font, Arrays.asList(lines), 1280 / 2, 120);
/* 48 */     this.widgets.add(textBox);
/*    */   }
/*    */   
/*    */   private class IntroInputProcessor extends InputAdapter {
/*    */     private IntroInputProcessor() {}
/*    */     
/*    */     public boolean touchUp(int screenX, int screenY, int pointer, int button) {
/* 55 */       Warranty.this.game.setScreen((Screen)new Intro(Warranty.this.game));
/* 56 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean keyUp(int keycode) {
/* 61 */       Warranty.this.game.setScreen((Screen)new Intro(Warranty.this.game));
/* 62 */       return true;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\screens\Warranty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */