package optix.core;

public class Theatre {
    private final String SPACES = "  ";
    private final String STAGE = "                |STAGE|           \n";

    private Seat[][] seats = new Seat[6][10];
    private int tierOneSeats;
    private int tierTwoSeats;
    private int tierThreeSeats;

    private double cost;
    private double revenue;

    private String showName;

    public Theatre(String showName, double cost, double revenue) {
        this.showName = showName;
        this.cost = cost;
        this.revenue = revenue;
        initializeLayout();
    }

    public Theatre(String showName, double cost) {
        this.showName = showName;
        this.cost = cost;
        this.revenue = 0;
        initializeLayout();
    }

    private void initializeLayout() {
        tierOneSeats = 0;
        tierTwoSeats = 0;
        tierThreeSeats = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[i].length; j++) {
                switch (i) {
                    case 0:
                    case 1:
                        seats[i][j] = new Seat("1");
                        tierOneSeats++;
                        break;
                    case 2:
                    case 3:
                        seats[i][j] = new Seat("2");
                        tierTwoSeats++;
                        break;
                    case 4:
                    case 5:
                        seats[i][j] = new Seat("3");
                        tierThreeSeats++;
                        break;
                    default:
                        System.out.println("There shouldn't be anything here.");
                        break;
                }
            }
        }
    }

    public String getShowName() {
        return showName;
    }

    public int getTierOneSeats() {
        return tierOneSeats;
    }

    public int getTierTwoSeats() {
        return tierTwoSeats;
    }

    public int getTierThreeSeats() {
        return tierThreeSeats;
    }

    private int decreaseSeats(int numSeats) {
        return numSeats--;
    }

    public String writeToFile() {
        return String.format("%s | %f | %f \n", showName, cost, revenue);
    }

    public boolean hasSameName(String checkName) {
        return showName.toLowerCase().equals(checkName.toLowerCase());
    }

    public String getSeatingArrangement() {
        StringBuilder seatingArrangement = new StringBuilder(STAGE);

        for (int i = 0; i < seats.length; i++) {
            seatingArrangement.append(SPACES);
            for (int j = 0; j < seats[i].length; j++) {
                seatingArrangement.append(seats[i][j].getSeat());
            }
            seatingArrangement.append("\n");
        }

        return seatingArrangement.toString();
    }
}
