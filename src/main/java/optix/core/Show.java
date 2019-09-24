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

    // Might be removed depending on how the revenue is being added back into Show
    public Show(String name, double cost, double revenue) {
        this.name = name;
        this.cost = cost;
        this.revenue = revenue;
    }

    public String writeToFile() {
        return String.format("%s | %f | %f \n", this.name, this.cost, this.revenue);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
