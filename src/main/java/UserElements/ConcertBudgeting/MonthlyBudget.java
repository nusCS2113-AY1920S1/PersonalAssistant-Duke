//@@author Ryan-Wong-Ren-Wei
package UserElements.ConcertBudgeting;

import Events.EventTypes.Event;
import Events.EventTypes.EventSubclasses.Concert;
import Events.Formatting.EventDate;

import java.util.ArrayList;

public class MonthlyBudget {
    private ArrayList<Concert> listOfConcerts; //list storing all concerts happening in the month
    private int totalCost; //total cost of concerts in month
    private EventDate date;

    /**
     * Constructor for monthly budget class.
     *
     * @param listOfConcerts Contains the list of Concert objects in the current month for easy access
     */
    public MonthlyBudget(ArrayList<Event> listOfConcerts) {
        this.listOfConcerts = new ArrayList<>();
        setDateToFirstOfMonth(listOfConcerts.get(0).getStartDate()); //set EventDate date to first day of month
        storeConcerts(listOfConcerts); //convert events to Concert objects, and store in list.
    }

    public MonthlyBudget(EventDate date) {
        this.totalCost = 0;
        setDateToFirstOfMonth(date);
        listOfConcerts = new ArrayList<>();
    }

    private void storeConcerts(ArrayList<Event> concertsInMonth) {
        this.totalCost = 0;
        for (Event currEvent : concertsInMonth) {
            Concert tempConcert = (Concert) currEvent;
            this.listOfConcerts.add(tempConcert);
            this.totalCost += tempConcert.getCost();
        }
    }

    public void addConcert(Concert concert, int budget) throws CostExceedsBudgetException {
        int newCost = this.totalCost + concert.getCost();
        if (newCost > budget) {
            throw new CostExceedsBudgetException(concert, budget);
        } else {
            this.totalCost = newCost;
        }
        this.listOfConcerts.add(concert);
    }

    /**
     * Updates EventDate date to correct value.
     * @param date EventDate of first event in the list of concerts for the current month
     */
    private void setDateToFirstOfMonth(EventDate date) {
        String dateString = date.getUserInputDateString();
        dateString = "01" + dateString.substring(2,10);
        this.date = new EventDate(dateString);
    }

    public ArrayList<Concert> getListOfConcerts() {
        return this.listOfConcerts;
    }

    public void removeConcert(Concert concert) {
        for (Concert currConcert : listOfConcerts) {
            if (currConcert.getStartDate().getUserInputDateString().equals(concert.getStartDate().getUserInputDateString())) {
                listOfConcerts.remove(currConcert);
                break;
            }
        }

        this.totalCost -= concert.getCost();
    }

    public int getTotalCost() {
        return this.totalCost;
    }
}
