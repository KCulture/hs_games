package libTypes;

import gameIfacAbstc.AiPlayer;

public class Dealer<T extends Card> extends AiPlayer<T> {
  private boolean bj;

  public boolean hasBJ(){return bj;}; // need to add logic to determine dealer has BJ
}
