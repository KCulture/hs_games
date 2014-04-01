import java.util.LinkedList;

import libTypes.*;
import gameIfacAbstc.GameEssen;
import gameIfacAbstc.GameStubs;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class BlackJack<T extends Card> extends GameStubs<T> implements
    GameEssen {
  private Player<T> playa1, playa2;
  private int purse;
  private boolean go;
  private int bet;
  private int pot;
  private boolean insure;
  private LinkedList<T> split_holder[] ;
  private boolean split_hand = false;
  private boolean insplit= false;
  private boolean bj = false;
  private boolean mo; 
  
  /*
   * Class -> BlackJack Cls specifys the cards class both players Ai
   */
  public BlackJack(Class<T> ty) {
    super(ty);
    playa1 = new Dealer<T>();
    playa2 = new Dealer<T>();
    purse = 1000;
    

  }

  /*
   * Class Player -> BlackJack Class specifies the cards class 1 players Ai
   */
  public BlackJack(Class<T> ty, Player<T> p1) {
    super(ty);
    playa1 = p1;
    playa2 = new Dealer<T>();
    purse = 1000;

  }

  public void purseDeduct(int p) {
    purse -= p;
  }
  
  public void purseAdd(int p) {
    purse += p;
  }

  public int getPurse() {
    return purse;
  }
  
  public int getBet(){
    return bet;
  }
  
  public void setBet(int b){
    bet = b;
  }
  
  public void potAdd(int b){
    pot += b;
  }
  
  public void potDeduct(int b){
    pot -= b;
  }
  
  public int getPot(){
    return pot ;
  }
  
  public void setPot(int p){
    pot =p;
  }
  
  public boolean isInsure() {
    return insure;
  }

  public void setInsure(boolean insure) {
    this.insure = insure;
  }
  
  public boolean isEnuf() {
    return getPurse() > getBet();
  }
  
  public boolean noFunds() {
    return getPurse() <= 0;
  }
  
  public boolean isEnuf(int b) {
    return getPurse() > b;
  }

  public boolean isSplit() {
    return split_hand;
  }

  public void setSplit(boolean split_hand) {
    this.split_hand = split_hand;
  }

  public boolean isBJ() {
    return bj;
  }

  public void setBJ(boolean blackjack) {
    this.bj = blackjack;
  }

  public boolean isMo() {
    return mo;
  }

  public void setMo(boolean mo) {
    this.mo = mo;
  }

  public boolean isInsplit() {
    return insplit;
  }

  public void setInsplit(boolean insplit) {
    this.insplit = insplit;
  }

  @Override
  public void initial() {
    request_bet(getPurse());
    distribute_cards(playa1.getHand(), playa2.getHand(), 4);
  }

  /*
   * int -> void Player bet which gets deducted from purse.
   *   // enhance text filter
   */
  
  public void request_bet(int purse) {
    if(noFunds()) {go = true; stop();}
    System.out.println("What will your bet be? You have " +getPurse()+ " That you can bet with ");
    try (BufferedReader bufreader = new BufferedReader(new InputStreamReader(
        System.in));){ 
      while(!validCV(bufreader.readLine(),true,"")){
        System.out.println("This is not a valid amount"); // hope this is not going to cause a infinite loop
      }
      int b = (isEnuf(Integer.parseInt(bufreader.readLine()))) ? Integer.parseInt(bufreader.readLine()) : getPurse();
      setBet(b);
      purseDeduct(b);
      potAdd(getBet());
       
      }
      catch (Exception e) {
      System.out.println(" well it didn't work");
      }

  }
  
  private boolean validCV(String str,boolean isNum,String ops) {
    if(isNum){
      try{
      @SuppressWarnings("unused")
      int a = (int) Integer.parseInt(str);
      return true; 
      }catch(NumberFormatException e){
        return false;
      }
    }else return  str.matches(ops); // try this to expand regexp knowledge//!real_choice(str.toUpperCase().trim()).equals("");
    
  }

  @Override
  /*
   * void -> void This is the where the game is played
   */
  public void playin() {
    int ctr = 0;
    LinkedList<T> hand = playHand();
    //TODO will have to allow player to see dealers top card to find out if showing ace that makes insurance
    try (BufferedReader bufreader = new BufferedReader(new InputStreamReader(
        System.in));) {
      while (!isMo()) {
        if(eval_playablity(hand)){
          String options = playOptions(hand, ctr);
        System.out.println(options);
        while(!validCV(bufreader.readLine(),false,options)){
          System.out.println("This is not a valid amount"); // hope this is not going to cause an infinite loop - 
                                                           // still doesn't stop player from typing valid command that should not apply to them
                                                           // would have to make real choice interact with playable options
        }
        String choice = text_scrub(bufreader.readLine(),false); 
        
        ctr++;
        commitChoice(choice,hand);
        }else{
          ctr++;
          commitChoice("STAND",hand);
        }
      }
    } catch (Exception e) {
      System.out
          .println("I guess we are going to have to look harder at BufferedReader");
    } 
  }

  private LinkedList<T> playHand() {
    if(split_holder.length <= 0){
      if(playa1.getHand() == null){
        playa1.setHand(new LinkedList<T>());
     }return playa1.getHand();
    }else if(isSplit() && split_holder[1] != null ){
      return split_holder[1];
    }else return split_holder[0]; // TODO should never happen but keep until well tested
    
    
  }

  @Override
  public boolean isterminate() {
       return go;
  }

  @Override
  public String suitSymbol(Card card) {
    String suit;
    switch(card.getSuite()){
    case 0:
      suit = "\u2664";
      break;
    case 1:  
      suit = "\u25C7" ;
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

  @Override
  public int card_val(Card card) {
    return eval_rank(card.getRank(),false);
  }

  @Override
  public boolean gameRules() {
    // TODO not used so not sure if this should be changed upstream(Abstract Class)
    return false;
  }
  /*LL<T> -> boolean
  true if round it over*/
  public boolean eval_playablity(LinkedList<T> hand) {
    
    return sum_hand(hand,true) > 21;
  }

  public boolean eval_playablity(LinkedList<T> hand, LinkedList<T> hand2) {
    // TODO not used so not sure if this should be changed upstream(Abstract Class)
    return false;
  }

  /*
   * LL<extends Card> -> String 
   * Decides what options a play has and message with
   * returns acceptable interactions
   */
  public String playOptions(LinkedList<T> hand, int count) {
    // TODO change method to allow function registration so that user doesn't
    // have to hard code functions
    // that describe the rules of the game. This will represent game ruler that
    // I imagined.
    if(count < 1 && !isSplit() && sum_hand(hand,false) == 21){setBJ(true);}
    StringBuilder options = new StringBuilder("STAND HIT ");
    
      return options
       //   .append(dbldowops(hand))
       //   .append(splitops(hand, count))
       //   .append(insurop(playa2.getHand()))
       //   .append(even_moneyops(hand, playa2.getHand()))
          .toString();

  }

  /*
   * LL<T extends Card>, boolean -> int returns the total of hand- bool allows
   * for all A's to be evalueated at 1
   */private int sum_hand(LinkedList<T> hand, boolean min) {
    int sum = 0;
    if (min) {
      for (T card : hand) {
        sum += eval_rank(card.getRank(), min);
      }
    }else{
      for (T card : hand) {
        sum += eval_rank(card.getRank(),min);
      }
      if(sum > 21)sum = best_try(hand,sum);
    }
    return sum;
    

  }

  /*
   * int bool -> int turns rank into Blackjack values
   */
  private int eval_rank(int val, boolean min) {
    if (val >= 9 && val <= 11)
      return 10;
    else if (min && val == 12)
      return 1;
    else if (!min && val == 12)
      return 11;
    else
      return val + 2;

  }
  /*LL<T> int -> int
  give closest sum to 21 for given hand*/
  private int best_try(LinkedList<T> hand,int s){
    return sub10(s,countAces(hand));
  }
  /*LL<T> -> int
  Counts aces in players hand*/
  private int countAces(LinkedList<T> h){
    int c=0;
    for(T card:h){
      if(card.getRank() == 12) c++;
    }
    return c;
  }
  
  private int sub10(int sum, int count){
    if(sum < 22) return sum;
    else if(count <= 0) return sum;
    else return sub10(sum - 10,count-1);
  }

  /*
   * LL<T extends Card>, int -> String returns gameOption true or
   * gameOption if not
   */
  private String splitops(LinkedList<T> h, int c) {
    if (twoCardsSame(h) && c < 1 && isInsplit() && !isSplit())
      return " SPLIT ";
    return "";
    
  }

  /*
   * LL<T extends Card> -> String 
   * returns gameOption true or blank if false
   * if not
   */
  private String insurop(LinkedList<T> h) { //TODO make to only happen if not in split and counter < 1
    if(eval_rank(playa2.getHand().getFirst().getRank(),false) == 11 && isEnuf((int)(.5 * getBet()))) {return " INSUR ";}
    return "";
  }

  /*
   * LL<T extends Card>, -> String returns gameOption true or gameOption
   * if not
   */
  private String hit_meops(LinkedList<T> h) {
    if(sum_hand(h,true) < 21) {return " HIT ";}
    return "";
  }

  /*
   * LL<T extends Card>,LL<T extends Card> -> StringBuilder returns gameOption
   * true or gameOption if not
   */
  private String even_moneyops(LinkedList<T> p1, LinkedList<T> p2) {
    if(sum_hand(p1,false) == 21 && p2.getFirst().getRank() == 12) return " EVEN ";
    return "";
  }

  /*
   * LL<T extends Card> -> String returns gameOption true
   * or gameOption if not
   */
  private boolean onlyStand(LinkedList<T> p1) {
    return sum_hand(p1,true) > 21 ; 
    
  }

  private String dbldowops(LinkedList<T> p1_hand) {
    if (sum_hand(p1_hand,true) < 22) return " DD ";
    return ""; 
  }

  private boolean twoCardsSame(LinkedList<T> hand) {
    if (hand.getFirst().getRank() == hand.get(1).getRank())
      return true;
    return false;
  }
  
  private String text_scrub(String text,boolean isNum){
    if(isNum ){return only_nums(text);}
    else return onlyVCS(text.toUpperCase().trim());
     
  }

  /*String -> String
  function to modify user input before checking that input is a real command*/
  private String onlyVCS(String text) {
    return real_choice(text.replaceAll("[1-9]", ""));
  }
 /*String -> String
 make sure that type text is a command*/
  private String real_choice(String com) {
    String a = new String("DD:EVEN:INSUR:HIT:SPLIT:STAND:Q"); //
    for(String realChoice : a.split(":")){
      if(realChoice.equals(com)){
        return com;
      }
    }
    return "";
  }
  /*String -> String
  make sure that type text value bet amount*/
  private String only_nums(String text) {
    return text.replaceAll("[a-z]* [A-Z]*", "").trim();
  }

  /*String -> void
  take string performs appropriate action*/
  private void commitChoice(String c,LinkedList<T> hand) {
    //TODO make a better way to execute choice - maybe pattern matching
    switch(c){
    case "DD":
      dd_action();
      break;
    case "EVEN":
      even_action(hand);
      break;
    case "INSUR":
      insur_action();
      break;
    case "HIT":
      hit_up(hand);
      break;
    case "SPLIT":
      split_hand(hand);
      break;
    case "STAND":
      pay_out_or_up(hand,playa2.getHand());
      break;
    case "Q":
      go = true;
      
    default:
    }
  }
  /*void -> void
  execute dd action*/
  private void dd_action(){
    pot+=getBet();
  }
  
  private void hit_up(LinkedList<T> h){
    transfer(deck,h,1);
  }
  
  private void even_action(LinkedList<T>hand){
    transfer(hand,deck,1);
    purse += pot;
    pot =0;
  }
  
  private void insur_action(){
      purseDeduct(getBet());
      potAdd(((int) (.5 * getBet())));
      setInsure(true);
      
  }
  
  
  private void split_hand(LinkedList<T> hand){
    //TODO split should be more comprehensive because it allows player to have 2-hands and 2-bets 1-purse 1-player
    // will need to come up with more comprehensive method later
    transfer(hand,split_holder[0],1);
    transfer(hand,split_holder[1],1);
    setSplit(true);
    purseDeduct(getBet());
    potAdd(getBet());
    transfer(deck,split_holder[0],1);
    transfer(deck,split_holder[1],1);
    playa1.setHand(split_holder[0]); 
    
  }
  /*LL<T>, LL<T>
  figures out the payout or payup of player*/
  private void pay_out_or_up(LinkedList<T> p1_hand, LinkedList<T> p2_hand){
    // TODO this should also be a section where the dealer get chance to play hand - make dealer hand another thread
    int pos =getPos();
    int pots= getPotScale();
    
    if(isBJ()){
      if(((Dealer<T>)playa2).hasBJ() && isInsure()){ //TODO add for even money
        purseAdd((int)(pos * ((2/3) *getPot())));
        setPot(0);
      }
    }
    if(playerOver(p1_hand)){ potDeduct(pots);}
    else if(playerOver(p2_hand)) {purseAdd(pos);potDeduct(pots);}
    else if(sum_hand(p1_hand,false) > sum_hand(p2_hand,false)){ //this should be re-factored
      purseAdd(pos);  
      potDeduct(pots);}
      
    else if (sum_hand(p1_hand,false) == sum_hand(p2_hand,false))   
      {System.out.println("PUSH money remains in the pot");} 
    else{potDeduct(pots);}
    
    if(isSplit() && !isInsplit()){playin();}
    clean_up();
    setMo(true);
    
  }

  private void clean_up() {
    split_holder[0] = null; // might want a separate function to add more logic
    split_holder[1] = null;
    setBet(0);
    setPot(0); // this should not happen if push occurs
    setBJ(false);
    setSplit(false);
    setInsplit(false);
    setInsure(false);
    
  }

  private boolean playerOver(LinkedList<T> hand) {
    
    return sum_hand(hand,false) > 21;
  }

  private int getPos() {
    int scale;    
    if(isBJ()){scale = (int)((3.00/2.00) * getBet());}
    else{scale = 2 * getBet();}
    return scale;
  }

  private int getPotScale() {
    int scale;
    if(isSplit()){scale = (int)(.5 * getPot());}
    else scale = 1 * getPot();
    return scale ;
  }

  public boolean stop() {
    String tag = (noFunds()) ? " Make sure you have some money with you " : " Hopefully, we will win more of your money ";
    System.out.println(" Please come back " + tag);
    System.exit(0);
    return true;
  }

}
