package optix.commons.model;

public class Show {
    private final String showName;
    private final double profit;

    public Show(String showName, double profit) {
        this.showName = showName;
        this.profit = profit;
    }

    public String getShowName() {
        return showName;
    }

    public double getProfit() {
        return profit;
    }

    public boolean hasSameName(String checkName) {
        return showName.toLowerCase().equals(checkName.toLowerCase());
    }
}
