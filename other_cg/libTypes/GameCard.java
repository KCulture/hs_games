package libTypes;

public class GameCard extends Card {
  private String  color;
 public GameCard(int v){
   super(v);
   
 }
 
 public String getColor(){
     switch(getVal() / 13){
     case 0:
       color = "Blue";
       break;
     case 1:  
       color = "Green";
       break;
     case 2:  
       color = "Red";
       break;
     case 3:  
       color = "Yellow";
       break;
     default:
       color = "?";
     }  
    return color ;
  }
}

