package moomoo.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

public class Expenditure {

    private double cost;
    private String name;
    public LocalDate date;
    private LocalDateTime dateTime;

    public Expenditure() {
        this.cost = 0.0;
    }

    public Expenditure(double cost, LocalDateTime dateTime) {
        this.cost = cost;
        this.dateTime = dateTime;
    }

    /**
     * Initializes value of expenditure as given by user.
     * @param name Name of the expenditure (e.g chicken rice)
     * @param cost Cost of the expenditure
     * @param dateTime Time of the expenditure.
     */
    public Expenditure(String name, double cost, LocalDateTime dateTime) {
        this.name = name;
        this.cost += 0.0;
        this.dateTime = dateTime;
    }

    public Expenditure(double cost) {
        this.cost += cost;
        long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        this.date = LocalDate.ofEpochDay(randomDay);
    }

    public String toString() {
        String output = "current spending is ";
        return this.name + output + "$ " + this.cost;
    }

    public double getCost() {
        return this.cost;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }
}
