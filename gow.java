import java.util.List;
import java.util.Scanner; // this is used for user input
import java.util.LinkedList;
import java.util.Collections;

public class gow {
  static Integer p1lc , p2lc;
  static String p1_name , p2_name;
  static Scanner in = new Scanner(System.in);
  static LinkedList<Integer> p1h = new LinkedList<Integer>();
  static LinkedList<Integer> p2h =  new LinkedList<Integer>();
  static LinkedList<Integer> p1dis = new LinkedList<Integer>();
  static LinkedList<Integer> p2dis = new LinkedList<Integer>();
  static LinkedList<Integer>spoils = new LinkedList<Integer>();
  static LinkedList<Integer> deck = new LinkedList<Integer>();
  static boolean b,dh;
  
    
// AL<Integer> -> AL<Integer>
// Takes a deck (possible empty if so it first generates) then shuffles deck 
  static LinkedList<Integer> msdeck(LinkedList<Integer> cards){
  if(cards.isEmpty()){
    for(int i=0; i<52;i++){
    cards.add((Integer)i);
    }
  }
  
  Collections.shuffle(cards);
  
  return cards;
}


// Integer -> int
// take card and get card value by modulo 13

  static int card_val(Integer card){
  return (card.intValue() % 13);
}

//Integer -> String
//take card and get card value by int division by 13

  static String card_Suit(Integer card){
   String suit;
   switch(card.intValue() / 13){
   case 0:
     suit = "\u2660";
     break;
   case 1:  
     suit = "\u2661" ;
     break;
   case 2:  
     suit = "\u2666";
     break;
   case 3:  
     suit = "\u2667";
     break;
   default:
     suit = "?";
   }  
  return suit ;
}
// int -> String
// this is going to show the cards suit and val
  static String show_card(Integer card){
    String l = "";
    int val = card_val(card);
    switch(val){
    case 10:
      l = "J";
      break;
    case 11:
      l = "Q";
      break;
    case 12:
      l = "K";
      break;
    default:
      l = ""+val;
      break;  
    }
    return card_Suit(card) + l+" ";
  }  
//LL<Integer> -> String
//recursive
  static String show_cards(List<Integer> cards){
    if(cards.isEmpty()) return "";
    return show_card(cards.get(0)) + show_cards(cards.subList(1,cards.size()));
  }
// AL<Integer> , AL<Integer> -> Boolean
// checks if player needs to redistribute card from discard to hand

  static boolean reHand(LinkedList<Integer> hand, LinkedList<Integer> dis){
  return hand.size() == 0 && dis.size() > 0; 
}

  static boolean reHand(LinkedList<Integer> hand, LinkedList<Integer> dis,int limit){
  return hand.size() < limit && dis.size() > 0; 
}
// void -> Boolean
// checks if game is over
  static boolean gameOver(){
  return ((p1h.size() | p1dis.size()) == 0) || ((p2h.size() | p2dis.size()) == 0); 
}
// int int -> int
// finds out how many cards each player should add to soils during battle
  static int findAsize(int cards1, int cards2){
  int bounty = -1 ;
  if(cards1  > 3 && cards2 > 3) bounty= 3;
  else if(cards1  < 2 || cards2 < 2)  bounty= 0;
  else if(cards1  < 3 || cards2 < 3){
    if(cards1 < cards2) bounty = cards1 -1;
    else bounty = cards2 -1;
  }
  return bounty;
} 
// void -> String
// Makes an announcement of a winner
  static String winner(){
  if(((p1h.size() | p1dis.size()) == 0)) return p2_name+" wins";
  else return p1_name+" wins";
}

// AL<Integer> "Player hand" ->  Integer
  static Integer play_card(LinkedList<Integer> hand){
  Integer card = hand.remove(0);
  spoils.add(card);
  return card;
}
// int -> void
// procedure that transfers cards both players hand in spoils
  static void battle_bounty(int count){
    System.out.print(show_cards(p1h.subList(0, count)));
    transfer(p1h,spoils,count);
    System.out.print(show_cards(p2h.subList(0, count)));
    transfer(p2h,spoils,count);
  
}
// Integer Integer -> String
// take cards from both players and states which one or battle
  static String getVictor(Integer p1lc, Integer p2lc){
  if(card_val(p1lc) == card_val(p2lc)) return "Battle";
  else if(card_val(p1lc) > card_val(p2lc)) return "p1";
  else return "p2";
}
// AL<Integer> -> void
// transfers cards from one AL to another
  static void transfer(LinkedList<Integer> from, LinkedList<Integer> to){
  for(Integer card : from){
    to.add(card);
    from.remove(0);
  }
}    
    


//AL<Integer> -> void
//transfers cards from one AL to another
  static void transfer(LinkedList<Integer> from, LinkedList<Integer> to, int limit){
for(int i = 0; i< limit; i++) to.add(from.remove());
}
// void -> void
// procedure of skirmish
  static void skirmish(){
  if(reHand(p1h,p1dis)){
    msdeck(p1dis);
    transfer(p1dis,p1h,p1dis.size());
  }
  if(reHand(p2h,p2dis)){
    msdeck(p2dis);
    transfer(p2dis,p2h,p2dis.size());
  }
    p1lc=play_card(p1h);
    System.out.println(show_card(p1lc));
    p2lc=play_card(p2h);
    System.out.println(show_card(p2lc));
    /*
    System.out.println("p1h "+p1h);
    System.out.println("p2h "+p2h);
    System.out.println("p1dis "+p1dis);
    System.out.println("p2dis "+p2dis);
    System.out.println("spoils "+spoils);
    */
    switch(getVictor(p1lc, p2lc)){
    case "Battle":
      battle_ready();
      break;
    case "p1" :
      int cards = spoils.size();
      transfer(spoils,p1dis,spoils.size());
      System.out.println(p1_name+" wins skirmish and adds "+cards+ "cards to Discard pile");
      break;
    case "p2" :
      int other_cards = spoils.size();
      transfer(spoils,p2dis,spoils.size());
      System.out.println(p2_name+" wins skirmish and adds "+other_cards+ "cards to Discard pile");
     default :
       
    }   
}

// void -> void
// distribute cards to player 1 & 2
  static void distribute_cards(){
  for(Integer card: deck){
    if(card.intValue() % 2 == 1) p1h.add(card);
    else p2h.add(card);
    }
}
// void -> void
// setup a battle so that hand has enough cards to play
  static void battle_ready(){
  if(reHand(p1h,p1dis,4)){
    p1dis = msdeck(p1dis);
    transfer(p1dis, p1h,p1dis.size());
  }
  if(reHand(p2h,p2dis,4)){
    p2dis = msdeck(p2dis);
    transfer(p2dis, p2h,p2dis.size());
  }
  battle_bounty(findAsize(p1h.size(),p2h.size()));
}

  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    System.out.println(ex_temp.test_block());
    System.out.println("Please give name to player1");
    p1_name = in.nextLine();
    System.out.println("Please give name to player2");
    p2_name = in.nextLine();
    // Generate deck
    deck=msdeck(deck);
    // distribute
    distribute_cards();
    do{
      skirmish();
    }while (!gameOver());
    System.out.println(winner());
    }
  }


