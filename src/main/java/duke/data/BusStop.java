package duke.data;

public class BusStop extends Location {
    private String busCode;
    private String description;

    /**
     * Creates a BusStop object.
     *
     */
    public BusStop(String busCode, String description, String address, double latitude, double longitude) {
        super(address, latitude, longitude, 0, 0);
        this.busCode = busCode;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getBusCode() {
        return busCode;
    }
}
