package entertainment.pro.model;

public class CinemaInfoObject {
    private String name;
    private double rating;
    private String address;

    /**
     * initialises the parameters of the information about the cinema.
     */
    public CinemaInfoObject(String name, double rating, String address) {
        this.name = name;
        this.rating = rating;
        this.address = address;
    }

    /**
     * return the name of the cinema.
     * @return name of cinema
     */
    public String getName() {
        return name;
    }

    /**
     * returns the rating of the cinema.
     * @return returns the rating of the cinema
     */
    public double getRating() {
        return rating;
    }

    /**
     * returns an address of the cinema or a description.
     * @return an address of the cinema or a description
     */
    public String getAddress() {
        return address;
    }
}
