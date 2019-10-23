package optix.commons.model;

public class Seat {
    private double ticketPrice;
    private String seatTier;
    private boolean isBooked;

    /**
     * the seat object.
     *
     * @param seatTier tier of the seat. Higher tier seat is more precious.
     */
    public Seat(String seatTier) {
        this.isBooked = false;
        this.seatTier = seatTier;
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
        return isBooked ? "✓" : "✘";
    }

    public String getSeat() {
        return "[" + getStatusIcon() + "]";
    }

    public void setSeatTier(String seatTier) {
        this.seatTier = seatTier;
    }

    public String getSeatTier() {
        return seatTier;
    }

    /**
     * Get the price of the seat according to its tier.
     * The seat tier cannot be out of bounds.
     *
     * @param basePrice base seat price of a show.
     * @return price seat according to its tier.
     */
    public double getSeatPrice(double basePrice) {
        assert (Integer.parseInt(seatTier) <= 3 && Integer.parseInt(seatTier) > 0);
        if (seatTier.equals("1")) {
            ticketPrice = basePrice;
        }
        if (seatTier.equals("2")) {
            ticketPrice = basePrice * 1.2;
        }
        if (seatTier.equals("3")) {
            ticketPrice = basePrice * 1.5;
        }
        return ticketPrice;

    }
}
