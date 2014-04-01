using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication2
{
    class Program
    {
        static List<Card> deck = new List<Card>(51);
        static List<Card> discard0 = new List<Card>();
        static List<Card> discard1 = new List<Card>();
        static List<Card> spoils = new List<Card>();
        static Player p0 = new Player();
        static Player p1 = new Player();
        static Card p1lc;
        static Card p2lc;
        static List<Card> msdeck(List<Card> cards){
         System.Random rand = new Random();
         int i = 0;
         List<int> checker = new List<int>();
         if(cards.Count <= 0){
             while (i < 52)
             {
                 int crd = rand.Next(0, 52);
                 if(!checker.Contains(crd)){
                     cards.Add(new Card(crd));
                     checker.Add(crd);
                    i++;
                 }
             }

         }
         else
         {
             List<Card> temp = new List<Card>(cards.Count);
             int num = cards.Count;
             for (i = 0; i < num; i++)
             {
                 int indx = rand.Next(0, num);
                 temp.Add(cards.ElementAt(indx));
             }
             cards = null;
             cards = temp;
             
         }
            return cards;
         }
             
    

        static String card_Suit(Card card)
        {
            String suit;
            switch (card.getSuite())
            {
                case 0:
                    suit = "\u2664";
                    break;
                case 1:
                    suit = "\u25C7";
                    break;
                case 2:
                    suit = "\u2666";
                    break;
                case 3:
                    suit = "\u2667";
                    break;
                default:
                    suit = "?";
                    break;
            }
            return suit;
        }


        static String show_card(Card card)
        {
            String l = "";
            int val = card.getRank();
            switch (val)
            {
                case 9:
                    l = "J";
                    break;
                case 10:
                    l = "Q";
                    break;
                case 11:
                    l = "K";
                    break;
                case 12:
                    l = "A";
                    break;
                default:
                    l = "" + (val + 2);
                    break;
            }
            return card_Suit(card) + l + " ";
        }

        static public bool reHand(List<Card> hand, List<Card> dis)
        {
            return hand.Count() == 0 && dis.Count() > 0;
        }

        static bool reHand(List<Card> hand, List<Card> dis, int limit)
        {
            return hand.Count() < limit && dis.Count() > 0;
        }

      

        static bool gameOver()
        {
            return ((p0.Hand.Count() | discard0.Count()) == 0) || ((p1.Hand.Count() | discard1.Count()) == 0);
            
        }

        static int findAsize(int cards1, int cards2)
        {
            int bounty = -1;
            if (cards1 > 3 && cards2 > 3) bounty = 3;
            else if (cards1 < 2 || cards2 < 2) bounty = 0;
            else if (cards1 < 3 || cards2 < 3)
            {
                if (cards1 < cards2) bounty = cards1 - 1;
                else bounty = cards2 - 1;
            }
            return bounty;
        }

        static String winner()
        {
            if (((p0.Hand.Count() | discard0.Count()) == 0)) return p1.Name + " wins";
            else return p0.Name + " wins";
        }
        static Card play_card(List<Card> hand)
        {
            Card card = hand.First();
            hand.Remove(card);
            spoils.Add(card);
            return card;
        }

        static String getVictor(Card p1lc, Card p2lc)
        {
            if (p1lc.getRank() == p2lc.getRank()) return "Battle";
            else if (p1lc.getRank() > p2lc.getRank()) return "p1";
            else return "p2";
        }

        static void transfer(List<Card> from, List<Card> to){
            foreach (Card card in from)
            {
                to.Add(card);
                from.Remove(card);
            }
        }
        static void transfer(List<Card> from, List<Card> to, int limit)
        {
            for (int i = 0; i < limit; i++)
            {
                to.Add(from.First());
                from.Remove(from.First());
            }
        }

        static void skirmish(){
  if(reHand(p0.Hand,discard0)){
    msdeck(discard0);
    transfer(discard0,p0.Hand,discard0.Count());
  }
  if(reHand(p1.Hand,discard1)){
    msdeck(discard1);
    transfer(discard1,p1.Hand,discard1.Count());
  }
    p1lc=play_card(p0.Hand);
    Console.WriteLine(show_card(p1lc));
    p2lc=play_card(p1.Hand);
    Console.WriteLine(show_card(p2lc));
    
    switch(getVictor(p1lc, p2lc)){
    case "Battle":
      battle_ready();
      break;
    case "p1" :
      int cards = spoils.Count();
      transfer(spoils,discard0,spoils.Count());
      Console.WriteLine(p0.Name+" wins skirmish and adds "+cards+ " cards to Discard pile");
      break;
    case "p2" :
      int other_cards = spoils.Count();
      transfer(spoils,discard1,spoils.Count());
      Console.WriteLine(p1.Name+" wins skirmish and adds "+other_cards+ " cards to Discard pile");
      break;
     default :
            break;
       
    }   
}
   static void battle_bounty(int count)
        {
            transfer(p0.Hand, spoils, count);
            transfer(p1.Hand, spoils, count);

        }

  static void distribute_cards(){
    foreach(Card card in deck)
    {
    if(card.Val % 2 == 1) p0.Hand.Add(card);
    else p1.Hand.Add(card);
    }
  }

   static void battle_ready(){
  if(reHand(p0.Hand,discard0,4)){
    discard0 = msdeck(discard0);
    transfer(discard0, p0.Hand,discard0.Count());
  }
  if(reHand(p1.Hand,discard1,4)){
    discard1 = msdeck(discard1);
    transfer(discard1, p1.Hand,discard1.Count());
  }
  battle_bounty(findAsize(p0.Hand.Count(),p1.Hand.Count()));
}




   static void Main(string[] args)
   {
       Console.WriteLine("Please give name to player1");
       String p0_Name = Console.ReadLine();
       p0.Name = p0_Name;

       Console.WriteLine("Please give name to player2");
       String p2_name = Console.ReadLine();
       // Generate deck
       deck = msdeck(deck);
       // distribute
       distribute_cards();
       do
       {
           skirmish();
       } while (!gameOver());
       Console.WriteLine(winner());
   }

        }
}
 
