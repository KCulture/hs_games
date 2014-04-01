package gameIfacAbstc;

import libTypes.Card;

public interface GameEssen {
  public void initial();
  public void playin();
  public boolean isterminate();
  public String suitSymbol(Card card);
  public int card_val(Card card);
  public boolean gameRules();
   
}
