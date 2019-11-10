//@@author Ryan-Wong-Ren-Wei

package mistermusik.commons.budgeting;

import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.eventtypes.eventsubclasses.Concert;
import mistermusik.commons.events.formatting.EventDate;

import java.util.ArrayList;

public class MonthlyBudget {
    private ArrayList<Concert> listOfConcerts; //list storing all concerts happening in the month
    private int totalCost; //total cost of concerts in month
    private EventDate date;

    /**
     * Constructor for monthly budget class.
     *
     * @param listOfConcerts Contains the list of Concert objects in the current month
     */
    public MonthlyBudget(ArrayList<Event> listOfConcerts) {
        this.listOfConcerts = new ArrayList<>();
        setDateToFirstOfMonth(listOfConcerts.get(0).getStartDate()); //set EventDate date to first day of month
        storeConcerts(listOfConcerts); //convert events to Concert objects, and store in list.
    }

    /**
     * Constructs an empty MonthlyBudget object.
     *
     * @param date corresponding date
     */
    public MonthlyBudget(EventDate date) {
        this.totalCost = 0;
        setDateToFirstOfMonth(date);
        listOfConcerts = new ArrayList<>();
    }

    /**
     * stores all concerts in the listOfConcerts, and updates concert costs accordingly.
     *
     * @param concertsInMonth list of concerts to be added
     */
    private void storeConcerts(ArrayList<Event> concertsInMonth) {
        this.totalCost = 0;
        for (Event currEvent : concertsInMonth) {
            Concert tempConcert = (Concert) currEvent;
            this.listOfConcerts.add(tempConcert);
            this.totalCost += tempConcert.getCost();
        }
    }

    /**
     * Attempts to add a new concert to the listOfConcerts, updates monthly budget. Throws exception
     * if this cannot be done due to cost exceeding budget.
     *
     * @param concert Concert object to be added
     * @param budget  Current budget
     * @throws CostExceedsBudgetException Exception thrown if operation is a failure
     */
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
     *
     * @param date EventDate of first event in the list of concerts for the current month
     */
    private void setDateToFirstOfMonth(EventDate date) {
        String dateString = date.getUserInputDateString();
        dateString = "01" + dateString.substring(2, 10);
        this.date = new EventDate(dateString);
    }

    /**
     * Returns list of concerts for this MonthlyBudget.
     */
    public ArrayList<Concert> getListOfConcerts() {
        return this.listOfConcerts;
    }

    /**
     * Removes a concert from the list. Updates cost accordingly.
     * @param concert Concert object to be removed.
     */
    public void removeConcert(Concert concert) {
        for (Concert currConcert : listOfConcerts) {
            if (currConcert.getStartDate().getUserInputDateString().equals(
                    concert.getStartDate().getUserInputDateString())) {
                listOfConcerts.remove(currConcert);
                break;
            }
        }

        this.totalCost -= concert.getCost();
    }

    /**
     * Returns total cost of concerts in current MonthlyBudget object.
     */
    public int getTotalCost() {
        return this.totalCost;
    }
}
