package optix.commons.model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Theatre {
    //@SuppressWarnings("checkstyle:membername")
    private static final String SPACES = "  "; // CHECKSTYLE IGNORE THIS LINE
    private static final String STAGE = "                |STAGE|           \n"; // CHECKSTYLE IGNORE THIS LINE

    private Seat[][] seats = new Seat[6][10];
    private int tierOneSeats;
    private int tierTwoSeats;
    private int tierThreeSeats;
    private double seatBasePrice;

    private Show show;

    /**
     * instantiates Theatre Object. Used when loading save file data.
     *
     * @param showName      name of show
     * @param revenue       expected revenue, calculated from seat purchases - cost
     * @param seatBasePrice base price of seats
     */
    public Theatre(String showName, double revenue, double seatBasePrice) {
        show = new Show(showName, revenue);
        this.seatBasePrice = seatBasePrice;
        initializeLayout();
    }

    /**
     * Instantiates Theatre Object. Used when there is no revenue yet (fresh instance).
     *
     * @param showName      name of show
     * @param seatBasePrice base price of seats.
     */
    public Theatre(String showName, double seatBasePrice) {
        show = new Show(showName, 0);
        this.seatBasePrice = seatBasePrice;
        initializeLayout();
    }

    public Theatre(Show show) {
        this.show = show;
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
                    assert i > seats.length;
                    break;
                }
            }
        }
    }

    public void setShowName(String showName) {
        show.setShowName(showName);
    }

    public String getShowName() {
        return show.getShowName();
    }

    public double getProfit() {
        return show.getProfit();
    }

    public Seat[][] getSeats() {
        return seats;
    }

    /**
     * function to set the status of a seat (change it to booked when a seat is bought).
     *
     * @param row       desired seat row
     * @param col       desired seat column
     */
    public void setSeat(int row, int col) {
        seats[row][col].setBooked(true);
        switch (seats[row][col].getSeatTier()) {
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
            System.out.println("Should have a Seat Tier!");
        }
    }

    /**
     * Get the seating arrangement of the Theatre.
     *
     * @return seating arrangement as a String.
     */
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

    private String getSeatsLeft() {
        return "\nTier 1 Seats: " + tierOneSeats + "\n"
                + "Tier 2 Seats: " + tierTwoSeats + "\n"
                + "Tier 3 Seats: " + tierThreeSeats + "\n";
    }


    /**
     * Sell seats to customers.
     *
     * @param seat      desired seat
     * @return cost of seat.
     */
    public double sellSeats(String seat) {
        int row = getRow(seat.substring(0, 1));
        int col = getCol(seat.substring(1));

        double costOfSeat = 0;

        //This needs to be changed in the event that the theatre dont have fixed seats for each row
        if (row == -1 || col == -1) {
            return costOfSeat;
        }

        double revenue = show.getProfit();

        if (!seats[row][col].isBooked()) {
            Seat soldSeat = seats[row][col];
            soldSeat.setBooked(true);
            costOfSeat = soldSeat.getSeatPrice(seatBasePrice);
            revenue += costOfSeat;

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
            seats[row][col] = soldSeat;

        }

        show.setProfit(revenue);

        return costOfSeat;
    }

    /**
     * Sell seats to customers. Used when customer wants to buy multiple seats.
     *
     * @param seats     String array of desired seats
     * @return Message detailing status of desired seats (sold out or successfully purchased.)
     */
    public String sellSeats(String... seats) {
        double totalCost = 0;
        ArrayList<String> seatsSold = new ArrayList<>();
        ArrayList<String> seatsNotSold = new ArrayList<>();
        String message;
        for (String seatNumber : seats) {
            double costOfSeat = sellSeats(seatNumber);

            if (costOfSeat != 0) {
                totalCost += costOfSeat;
                seatsSold.add(seatNumber);
            } else {
                seatsNotSold.add(seatNumber);
            }
        }

        if (seatsSold.isEmpty()) {
            message = String.format("☹ OOPS!!! All of the seats %s are unavailable\n", seatsNotSold);
        } else if (seatsNotSold.isEmpty()) {
            message = "You have successfully purchased the following seats: \n"
                    + seatsSold + "\n"
                    + "The total cost of the ticket is " + new DecimalFormat("$#.00").format(totalCost) + "\n";
        } else {
            message = "You have successfully purchased the following seats: \n"
                    + seatsSold + "\n"
                    + "The total cost of the ticket is " + new DecimalFormat("$#.00").format(totalCost) + "\n"
                    + "The following seats are unavailable: \n"
                    + seatsNotSold + "\n";
        }

        return message;
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


    public boolean hasSameName(String checkName) {
        return show.hasSameName(checkName);
    }

    public String writeToFile() {
        return String.format("%s | %f | %f\n", show.getShowName(), show.getProfit(), seatBasePrice);
    }
}