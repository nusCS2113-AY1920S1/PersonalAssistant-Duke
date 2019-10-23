package optix.commons.model;

public class Show {
    private String showName;
    private double profit;

    public Show(String showName, double profit) {
        this.showName = showName;
        this.profit = profit;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public void setProfit(double profit) {
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