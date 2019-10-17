package Events.EventTypes.EventSubclasses.RecurringEventSubclasses;

import Events.EventTypes.EventSubclasses.RecurrentEvent;

public class Practice extends RecurrentEvent {

    /**
     * creates new practice class with boolean to read from file
     */
    public Practice(String description, boolean isDone, String startDateAndTime, String endDateAndTime) {
        super(description, isDone, startDateAndTime, endDateAndTime, 'P');
    }

    /**
     * creates new practice class without boolean to read from user input (assume incopmlete)
     */
    public Practice(String description,String startDateAndTime, String endDateAndTime) {
        super(description, false, startDateAndTime, endDateAndTime, 'P');
    }
}