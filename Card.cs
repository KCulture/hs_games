using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApplication2
{
    public class Card
    {
        private int val;

        public Card(int v)
        {
            val = v;
        }

        public int getRank()
        {
            return val % 13;
        }

        public int getSuite()
        {
            return val / 13;
        }

        public int Val
        {
            get { return val; }
        }
    }
}
