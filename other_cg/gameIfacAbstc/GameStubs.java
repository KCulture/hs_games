package gameIfacAbstc;


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;


import libTypes.*;
public abstract class GameStubs<T extends Card> {
  private Class<T> cls;
  public GameStubs(Class<T> ty){cls = (Class<T>) ty;}
  
  protected LinkedList<T> deck = msdeck(new LinkedList<T>(),cls);
  protected Card typename;
  
  
  public LinkedList<T>  msdeck(LinkedList<T> cards,Class<T> c){
    if(cards.isEmpty()){
      for(int i=0;i<52;i++){
        try{
        cards.add(c.getConstructor(int.class).newInstance(i));
        }catch(Exception e){System.out.println("can't create object because of "+e.getLocalizedMessage());}
      }
    }
    Collections.shuffle(cards);
    return cards;
  }
  
  

  public void transfer(LinkedList<T> from, LinkedList<T> to){
    for(T card : from){
      to.add(card);
      from.remove();
    }
  }
  
  /* might change this to while loops with !from.isEmpty
   * public void transfer(LinkedList<Integer> from, LinkedList<Integer> to){
    for(Integer card : from){
      to.add(card);
      from.remove();
    }
  }*/
  
  public void transfer(LinkedList<T> from, LinkedList<T> to, int limit){
    for(int i = 0; i< limit; i++) to.add(from.remove());
    }
  /*LL LL -> void
  This is what is going to be used to distribute cards
  TODO: what might make this more functional is to have it return a card
  Other idea is to make this a recursive function that add to players hand*/
  public void distribute_cards(LinkedList<T> h1, LinkedList<T> h2 ){
    for(T card: deck){
      if(card.getVal() % 2 == 1) h1.add(cls.cast(card));
      else h2.add(card);
      }
  }
  /*LL LL int -> void
  This is what is going to be used to distribute cards*/
  public void distribute_cards(LinkedList<T> h1, LinkedList<T> h2,int limit ){
    int i=0;
    Iterator<T> iter = deck.iterator();
    while(i<limit && iter.hasNext() ){
      T card = deck.pop();
      if(card.getVal() % 2 == 1) h1.add(card);
      else h2.add(card);
      }
  }
  public abstract boolean eval_playablity(LinkedList<T> hand);
}

  
  
  
