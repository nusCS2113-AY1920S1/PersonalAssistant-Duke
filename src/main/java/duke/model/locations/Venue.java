package duke.model.locations;

public class Venue {
    private String address;
    private double latitude;
    private double longitude;
    private double distX;
    private double distY;

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
}
