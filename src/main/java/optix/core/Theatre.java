package optix.core;

public class Theatre {

    private final String SPACES = "  ";
    private final String STAGE = "                |STAGE|           \n";

    private Seat[][] seats = new Seat[6][10];
    private int tierOneSeats;
    private int tierTwoSeats;
    private int tierThreeSeats;
    private double seatBasePrice;

    private double cost;
    private double revenue;

    private String showName;

    public Theatre(String showName, double cost, double revenue, double seatBasePrice) {
        this.showName = showName;
        this.cost = cost;
        this.revenue = revenue;
        this.seatBasePrice = seatBasePrice;
        initializeLayout();
    }

    public Theatre(String showName, double cost, double seatBasePrice) {
        this.showName = showName;
        this.cost = cost;
        this.revenue = 0;
        this.seatBasePrice = seatBasePrice;
        initializeLayout();
    }

    // can have multiple layouts to be added for future extensions.

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

    public String writeToFile() {
        return String.format("%s | %f | %f | %f\n", showName, cost, revenue, seatBasePrice);
    }

    public boolean hasSameName(String checkName) {
        return showName.toLowerCase().equals(checkName.toLowerCase());
    }

    private String getSeatsLeft() {
        return "\nTier 1 Seats: " + tierOneSeats + "\n"
                + "Tier 2 Seats: " + tierTwoSeats + "\n"
                + "Tier 3 Seats: " + tierThreeSeats + "\n";
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
        seatingArrangement.append(getSeatsLeft());

        return seatingArrangement.toString();
    }
    // need to think of better way to parse seat number
    public void sellSeats(String buyerName, String seat) {
        char[] seatNumber = seat.trim().toCharArray();
        String row = String.valueOf(seatNumber[0]);
        String col;
        if (seatNumber.length == 3) {
            col = String.valueOf(seatNumber[1]) + String.valueOf(seatNumber[2]);
        } else if (seatNumber.length == 2) {
            col = String.valueOf(seatNumber[1]);
        } else {
            //throw exception so that it doesn't continue
            System.out.println("This is not acceptable");
            col = "0";
        }


        if (!seats[getRow(row)][getCol(col)].isBooked()) {
            Seat soldSeat = seats[getRow(row)][getCol(col)];
            soldSeat.setBooked(true);
            soldSeat.setName(buyerName);
            revenue += soldSeat.getSeatPrice(seatBasePrice);

            switch (soldSeat.getSeatTier()) {
            case "1":
                tierOneSeats--;
                break;
            case "2":
                tierTwoSeats--;
                break;
            case "3":
                tierThreeSeats--;
                break;
            default:
            }

            seats[getRow(row)][getCol(col)] = soldSeat;
        }

    }

    public void sellSeats(String buyerName, String[] seats) {
        for (String seatNumber : seats) {
            sellSeats(buyerName, seatNumber);
        }
    }

    private int getRow(String row) {
        switch (row) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
            default:
                return -1;
        }
    }

    private int getCol(String col) {
        switch (col) {
            case "1":
                return 0;
            case "2":
                return 1;
            case "3":
                return 2;
            case "4":
                return 3;
            case "5":
                return 4;
            case "6":
                return 5;
            case "7":
                return 6;
            case "8":
                return 7;
            case "9":
                return 8;
            case "10":
                return 9;
            default:
                return -1;
        }
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
}
