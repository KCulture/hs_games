package libTypes;

import java.util.LinkedList;

public class Player<T extends Card> { //TODO create abstract player (class or interface)that a blackjack player can inherit or can make  
  private String name;
  private LinkedList<T> hand = new LinkedList<T>();
  
     public  Player(){
        // TODO: Auto-generated Stub
              
     }
     public void setName(String value) {name = value;}
     public String getName(){return name;}  
     public void addToHand(T c){hand.add(c);}
     public LinkedList<T> getHand(){return hand;}
     public void setHand(LinkedList<T> h){hand = h ;} // would like to find more functional way of doing this
        
     
}
