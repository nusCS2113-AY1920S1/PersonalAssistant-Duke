package UserElements.ConcertBudgeting;

import Events.EventTypes.Event;
import Events.EventTypes.EventSubclasses.Concert;
import Events.Formatting.EventDate;

import java.util.ArrayList;

public class MonthlyBudget {
    private ArrayList<Concert> listOfConcerts = new ArrayList<>(); //list storing all concerts happening in the month
    private float totalCost; //total cost of concerts in month
    EventDate date;

    /**
     * Constructor for monthly budget class.
     *
     * @param listOfConcerts Contains the list of Concert objects in the current month for easy access
     */
    public MonthlyBudget(ArrayList<Event> listOfConcerts) {
        //setDateToFirstOfMonth(listOfConcerts.get(0).getStartDate()); //set EventDate date to first day of month
        convertAndStoreConcerts(listOfConcerts); //convert events to Concert objects, and store in list.
    }

    private void convertAndStoreConcerts(ArrayList<Event> concertsInMonth) {
        this.totalCost = 0;
        for (Event currEvent : concertsInMonth) {
            //instantiate new concert class for every event that is a concert, and add to the list.
            Concert tempConcert = new Concert(currEvent.getDescription(), currEvent.getIsDone(),
                    currEvent.getStartDate().getUserInputDateString(), currEvent.getEndDate().getUserInputDateString());

            this.listOfConcerts.add(tempConcert);
            this.totalCost += tempConcert.getCost();
        }
    }

    /**
     * Updates EventDate date to correct value.
     * @param date EventDate of first event in the list of concerts for the current month
     */
    private void setDateToFirstOfMonth(EventDate date) {
        String dateString = date.getUserInputDateString();
        dateString = "01" + dateString.substring(2);
        this.date = new EventDate(dateString);
    }

    public ArrayList<Concert> getListOfConcerts() {
        return this.listOfConcerts;
    }
}
