using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Models
{
    public class Price
    {
        public int PriceId { get; set; }
        public double Amount { get; set; }
        public Court Court { get; set; }
        public DateTime? Starts { get; set; }
        public DateTime? Ends { get; set; }
    }
}
