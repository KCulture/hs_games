package libTypes;

public class Card {
    private int val;
    public Card(int v){
     val =v;  
    }
    
    public int getRank(){
       return val % 13;      
    }
    
    public int getSuite(){
       return val / 13;      
    }
  
    public int getVal(){
      return val ;      
   }
    
    
    
}
