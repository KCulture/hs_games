using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication2
{
    class Player
    {
        String name;
        List<Card> hand = new List<Card>(26);
        

        public String Name
        {
            set { name = value; }
            get { return name; }
        }

        public List<Card> Hand
        {
            set { hand = value; }
            get { return hand; }
        }

        

    }

    
}
