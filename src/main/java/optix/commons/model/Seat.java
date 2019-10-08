package optix.commons.model;

public class Seat {
    private double ticketPrice;
    private String buyerName;
    private String seatTier;
    private boolean isBooked;

    public Seat(String seatTier) {
        this.isBooked = false;
        this.buyerName = null;
        this.seatTier = seatTier;
    }

    public void setName(String name) {
        this.buyerName = name;
    }


    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    //store this in case of refund.
    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public boolean isBooked() {
        return isBooked;
    }

    private String getStatusIcon() {
        return isBooked ?  "\u2713" : "\u2718";
    }

    public String getSeat() {
        return "[" + getStatusIcon() + "]";
    }

    public void setSeatTier(String seatTier) {
        this.seatTier = seatTier;
    }


    public String getName() {
        return buyerName;
    }

    public String getSeatTier() {
        return seatTier;
    }

    // need to make sure that there are anomalous data here
    public double getSeatPrice(double basePrice) {
        if (seatTier.equals("1")) {
            ticketPrice = basePrice * 1.5;
        }
        if (seatTier.equals("2")) {
            ticketPrice = basePrice * 1.2;
        }
        if (seatTier.equals("3")) {
            ticketPrice = basePrice;
        }
        return ticketPrice;

    }
}
