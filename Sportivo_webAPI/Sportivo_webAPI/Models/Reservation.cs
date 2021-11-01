using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Models
{
    public class Reservation
    {
        public int ReservationId { get; set; }
        public User User { get; set; }
        public int UserId { get; set; }
        public Court Court { get; set; }
        public int CourtId { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }
        public int ReservationPrice { get; set; }
    }
}

