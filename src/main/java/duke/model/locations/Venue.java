package duke.model.locations;

/**
 * Class representing a venue.
 */
public class Venue {
    private String address;
    private double latitude;
    private double longitude;
    private double distX;
    private double distY;
    private static final int RADIUS_EARTH = 6371;

    /**
     * Creates a Venue object.
     *
     * @param address The address.
     * @param latitude The latitude.
     * @param longitude The longitude.
     * @param distX The distance in x-axis.
     * @param distY The distance in y-axis.
     */
    public Venue(String address, double latitude, double longitude, double distX, double distY) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distX = distX;
        this.distY = distY;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDistX() {
        return distX;
    }

    public double getDistY() {
        return distY;
    }

    /**
     * Calculates flat earth distance between 2 points based on latitude & longitude.
     *
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

        return Math.abs(RADIUS_EARTH * c * 1000);
    }

    /**
     * Sets the latitude of the venue.
     *
     * @param latitude The latitude value to set.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets the longitude of the venue.
     *
     * @param longitude The longitude value to set.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Converts the venue to a String format.
     *
     * @return The venue as a String.
     */
    @Override
    public String toString() {
        return getAddress() + " | " + getLatitude() + " | " + getLongitude() + " | " + getDistX() + " | " + getDistY();
    }

    /**
     * Compares a given venue with this object.
     *
     * @param otherVenue The query venue.
     * @return Whether the venues are equal.
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
}
