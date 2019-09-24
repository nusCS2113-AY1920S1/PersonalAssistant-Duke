package optix.core;

public class Show {

    private String name;

    private Theatre theatre;

    private double cost;

    private double revenue;

    public Show(String name, double cost) {
        this.name = name;
        this.theatre = new Theatre();
        this.cost = cost;
        this.revenue = 0;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
