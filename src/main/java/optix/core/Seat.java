package optix.core;

public class Seat {
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

    public String getName() {
        return buyerName;
    }

    public String getSeatTier() {
        return seatTier;
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
}
