import tester.* ;

import java.util.LinkedList;
public class ex_temp {
  ex_temp() {};
  gow tt = new gow();

//todo: announce which test(s) doesn't pass

  static boolean test1(Tester t){
    return t.checkExpect(gow.card_val(14), 1) 
        && t.checkExpect(gow.card_val(13), 0) 
        && t.checkExpect(gow.card_val(35), 9) 
        ;
  }
  
  static boolean test2(Tester t){
    return t.checkExpect(gow.card_Suit(12), "\u2664" ) 
        && t.checkExpect(gow.card_Suit(13), "\u25C7") 
        && t.checkExpect(gow.card_Suit(35), "\u2666")
        && t.checkExpect(gow.card_Suit(51), "\u2667")
        ;
  }
  
  static LinkedList<Integer> build_hand(String hand){
    LinkedList<Integer> newhand = new LinkedList<Integer>();
    switch(hand){
    case "rehand":
      for(int i = 0;i<3;i++){
        newhand.add(new Integer(i));
      }
      break;
    case "empty":
      break;
    default:
      break;
    }
    return newhand;
  }
  
  static boolean test3(Tester t){
    
    return t.checkExpect(gow.reHand(build_hand("rehand"),build_hand("black")),false) 
        && t.checkExpect(gow.reHand(build_hand("black"),build_hand("rehand")),true) 
        && t.checkExpect(gow.reHand(build_hand("black"),build_hand("blue")),false) 
        ;
  }
  
static boolean test_reHand_alt(Tester t){
    
    return t.checkExpect(gow.reHand(build_hand("rehand"),build_hand("black"),2),false) 
        && t.checkExpect(gow.reHand(build_hand("black"),build_hand("rehand"),3),true) 
        && t.checkExpect(gow.reHand(build_hand("black"),build_hand("blue"),1),false) 
        ;
  }
static boolean test_play_card(Tester t){
  LinkedList<Integer> l = new LinkedList<Integer>(); l.add(new Integer(25)); l.add(51); l.add(0);l.add(32);
  return t.checkExpect(gow.play_card(build_hand("rehand")),new Integer(0)) 
      && t.checkExpect(gow.play_card(l),new Integer(25)) 
      //&& t.checkExpect(gow.play_card(build_hand("black"),build_hand("blue"),1),false) 
      ;
}
//
static boolean test4(Tester t){
    
    return t.checkExpect(gow.findAsize(10,13),3) 
        && t.checkExpect(gow.findAsize(10,4),3) 
        && t.checkExpect(gow.findAsize(2,4),1) 
        && t.checkExpect(gow.findAsize(0,10),0)
        && t.checkExpect(gow.findAsize(1,10),0)
        ;
  }

  static boolean test5(Tester t) {

    return t.checkExpect(gow.show_card(new Integer(10)), "\u2664" + "Q ")
        && t.checkExpect(gow.show_card(new Integer(25)), "\u25C7" + "A ")
        && t.checkExpect(gow.show_card(new Integer(39)), "\u2667" + "2 ")
        && t.checkExpect(gow.show_card(new Integer(51)), "\u2667" + "A ");
  }
//LL<Integer> -> String  
  static boolean test6(Tester t) {
    LinkedList<Integer> l = new LinkedList<Integer>(); l.add(new Integer(25)); l.add(51); l.add(0);l.add(32);
    return t.checkExpect(gow.show_cards(l),"\u25C7" + "A " +"\u2667" + "A "+ "\u2664" + "2 "+"\u2666"+"8 ")
        && t.checkExpect(gow.show_cards(build_hand("empty")), "")
        //&& t.checkExpect(gow.show_cards(new Integer(39)), "\u2667" + "2 ")
        //&& t.checkExpect(gow.show_cards(new Integer(51)), "\u2667" + "A ")
        ;
  }
  static boolean test_block(){
    Tester t = new Tester();
    return test1(t)
         & test2(t) 
         & test3(t) 
         & test_reHand_alt(t)
         & test4(t)
         & test5(t)
         & test6(t)
         & test_play_card(t)
           ;
  }
}
