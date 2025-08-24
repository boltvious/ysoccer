/*    */ package com.ygames.ysoccer.match;
/*    */ 
/*    */ import com.badlogic.gdx.Gdx;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class Fsm
/*    */ {
/* 14 */   private List<State> states = new ArrayList<>();
/*    */   protected State state;
/*    */   
/*    */   public State getState() {
/* 18 */     return this.state;
/*    */   }
/*    */   
/*    */   void addState(State s) {
/* 22 */     this.states.add(s);
/*    */   }
/*    */   
/*    */   public void think() {
/* 26 */     if (this.state == null) {
/*    */       return;
/*    */     }
/*    */     
/* 30 */     this.state.doActions();
/*    */     
/* 32 */     State newState = this.state.checkConditions();
/* 33 */     if (newState != null) {
/* 34 */       setState(newState.id);
/*    */     }
/*    */   }
/*    */   
/*    */   public void setState(int id) {
/* 39 */     if (id == -1) {
/* 40 */       this.state = null;
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     if (this.state != null) {
/* 45 */       this.state.exitActions();
/*    */     }
/*    */     
/* 48 */     boolean found = false;
/* 49 */     for (int i = 0; i < this.states.size(); i++) {
/* 50 */       State s = this.states.get(i);
/* 51 */       if (s.checkId(id)) {
/* 52 */         this.state = s;
/* 53 */         this.state.entryActions();
/* 54 */         found = true;
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     if (!found)
/* 59 */       Gdx.app.log(getClass().getSimpleName(), "Warning! Cannot find state: " + id); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Master\Downloads\ysoccer19\ysoccer.jar!\com\ygames\ysoccer\match\Fsm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */