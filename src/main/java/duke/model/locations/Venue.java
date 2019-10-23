package duke.model.locations;

public class Venue {
    private String address;
    private double latitude;
    private double longitude;
    private double distX;
    private double distY;
    private static final int R = 6371; // Radius of the earth

    /**
     * Creates a location object.
     */
    public Venue(String address, double latitude, double longitude, double distX, double distY) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distX = distX;
        this.distY = distY;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getDistX() {
        return distX;
    }

    public double getDistY() {
        return distY;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return getAddress() + " | " + getLatitude() + " | " + getLongitude() + " | " + getDistX() + " | " + getDistY();
    }

    /**
     * Overloads the equals method.
     */
    public boolean equals(Venue otherVenue) {
        if (otherVenue == this) {
            return true;
        }

        return getLatitude() == otherVenue.getLatitude()
                && getLongitude() == otherVenue.getLongitude()
                && getAddress().equals(otherVenue.getAddress())
                && getDistX() == otherVenue.getDistX()
                && getDistY() == otherVenue.getDistY();
    }

    /**
     * Calculates flat earth distance between 2 points based on latitude & longitude.
     * @param otherVenue The other venue.
     * @return The absolute flat earth distance between the 2 venues.
     */
    public double getDistance(Venue otherVenue) {
        double latDistance = Math.toRadians(otherVenue.getLatitude() - getLatitude());
        double lonDistance = Math.toRadians(otherVenue.getLongitude() - getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(getLatitude())) * Math.cos(Math.toRadians(otherVenue.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.abs(R * c * 1000);
    }
}
