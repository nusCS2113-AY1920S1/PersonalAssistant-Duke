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

    public void setSeatTier(String seatTier) {
        this.seatTier = seatTier;
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
}
