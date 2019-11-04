package mistermusik.commons.events.eventtypes.eventsubclasses;

import mistermusik.commons.events.eventtypes.Event;

public class Concert extends Event {
    private int cost;
    /**
     * creates new lesson class with boolean to read from file
     */
    public Concert(String description, boolean isDone, String startDateAndTime, String endDateAndTime, int cost) {
        super(description, isDone, startDateAndTime, endDateAndTime, 'C');
        this.cost = cost;
    }

    /**
     * creates new lesson class without boolean to read from user input (assumes incomplete)
     */
    public Concert(String description, String startDateAndTime, String endDateAndTime, int cost) {
        super(description, false, startDateAndTime, endDateAndTime, 'C');
        this.cost = cost;
    }

    public int getCost(){
        return this.cost;
    }

    @Override
    public String toStringForFile() {
        return getDoneSymbol() + getType() + "/" + getDescription() + "/" +
                getStartDate().getUserInputDateString() + "/" + getEndDate().getUserInputDateString() + "/" +
                this.cost;
    }
}