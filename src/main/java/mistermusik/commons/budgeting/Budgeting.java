//@@author Ryan-Wong-Ren-Wei

package mistermusik.commons.budgeting;

import mistermusik.commons.events.eventtypes.Event;
import mistermusik.commons.events.eventtypes.eventsubclasses.Concert;
import mistermusik.commons.events.formatting.EventDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Budgeting {

    /**
     * Map that stores all information regarding monthly budgeting for concerts
     * String is the month followed by year "MM-yyyy" representing the month that we analyze budget
     * MonthlyBudget is the class corresponding to the month being analyzed, stores all details
     * for budget analysis including the corresponding Concert objects.
     */
    private HashMap<String, MonthlyBudget> monthlyCosts;

    private int budget; //current user defined budget

    /**
     * Constructor for budgeting system. Sets budget and creates new map of monthly costs.
     *
     * @param eventList List of Event objects containing all events currently in the list, to be used
     *                  in monthly budget/cost calculation
     */
    public Budgeting(ArrayList<Event> eventList, int budget) {
        this.budget = budget;
        createMap(eventList);
    }

    /**
     * Remove costs from list when deleting a Concert object.
     *
     * @param concert Concert object to be deleted.
     */
    public void removeMonthlyCost(Concert concert) {
        String monthAndYear = getMonthAndYear(concert.getStartDate());

        MonthlyBudget currMonthlyBudget = monthlyCosts.get(monthAndYear);
        currMonthlyBudget.removeConcert(concert);

        if (currMonthlyBudget.getListOfConcerts().isEmpty()) {
            monthlyCosts.remove(monthAndYear);
        }
    }

    /**
     * Updates the monthly concert costs when a new Concert object is added to EventList.
     *
     * @param concert Concert object to be added.
     */
    public void updateMonthlyCost(Concert concert) throws CostExceedsBudgetException, NumberFormatException {
        String monthAndYear = getMonthAndYear(concert.getStartDate());

        MonthlyBudget currMonthlyBudget = monthlyCosts.get(monthAndYear);
        if (currMonthlyBudget == null) {
            currMonthlyBudget = new MonthlyBudget(concert.getStartDate());
            currMonthlyBudget.addConcert(concert, this.budget);
            monthlyCosts.put(monthAndYear, currMonthlyBudget);
        } else {
            currMonthlyBudget.addConcert(concert, this.budget);
        }
    }

    /**
     * Sets monthly budget.
     *
     * @param budget New monthly budget
     */
    public void setBudget(int budget) {
        if (budget < 0) {
            throw new NumberFormatException();
        }
        this.budget = budget;
    }

    /**
     * Creates a map where key is the month(MM-yyyy format) and value is the MonthlyBudget object for that month.
     *
     * @param eventList list of all events in the current list.
     * @return created map.
     */
    private void createMap(ArrayList<Event> eventList) {
        if (eventList.isEmpty() || !hasConcerts(eventList)) { //if empty list, initialize map and return
            monthlyCosts = new HashMap<>();
            return;
        }

        monthlyCosts = new HashMap<>();
        EventDate monthlyDate = null; //stores a date of a day in the month we are currently checking for
        ArrayList<Event> listOfConcerts = new ArrayList<Event>(); //to store the concerts in a given month
        String monthAndYear = "";

        for (Event currEvent : eventList) {
            if (currEvent.getType() != 'C') { //if not concert type event, skip iteration
                continue;
            }

            if (!isSameMonth(currEvent.getStartDate(), monthlyDate)) {
                if (!listOfConcerts.isEmpty()) {
                    this.monthlyCosts.put(monthAndYear, new MonthlyBudget(listOfConcerts));
                }

                monthlyDate = currEvent.getStartDate();
                monthAndYear = getMonthAndYear(monthlyDate);
                listOfConcerts.clear();
            }

            listOfConcerts.add(currEvent);
        }

        this.monthlyCosts.put(monthAndYear, new MonthlyBudget(listOfConcerts));
    }

    /**
     * Checks list of events for concerts.
     */
    private boolean hasConcerts(ArrayList<Event> eventList) {
        for (Event event : eventList) {
            if (event.getType() == 'C') {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether two EventDate objects have the same month.
     */
    private boolean isSameMonth(EventDate eventDateA, EventDate eventDateB) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(eventDateA.getEventJavaDate());

            int monthA = cal.get(Calendar.MONTH);
            int yearA = cal.get(Calendar.YEAR);

            cal.setTime(eventDateB.getEventJavaDate());

            int monthB = cal.get(Calendar.MONTH);
            int yearB = cal.get(Calendar.YEAR);

            return (monthA == monthB) && (yearA == yearB);
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Retrieves the month and year in a string "mm-YYYY" from an EvenDate object.
     */
    private String getMonthAndYear(EventDate date) {
        String monthAndYear = date.getUserInputDateString();
        monthAndYear = monthAndYear.substring(3, 10); //get MM-yyyy from dd-MM-yyyy HHmm

        return monthAndYear;
    }

    /**
     * Gets total concert costs for a month.
     *
     * @param monthAndYear String containing month and year "mm-YYYY" to retrieve costs from
     */
    public int getCostForMonth(String monthAndYear) throws NullPointerException {
        return monthlyCosts.get(monthAndYear).getTotalCost();
    }

    /**
     * Gets current monthly budget.
     */
    public int getBudget() {
        return this.budget;
    }
}
