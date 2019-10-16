package UserElements;

import Events.Storage.EventList;
import Events.EventTypes.Event;
import Events.Formatting.EventDate;
import Events.Formatting.Predicate;

import java.util.Date;
import java.util.Queue;


/**
 * User interface: contains all methods pertaining to user interaction.
 */
public class UI {
    private static String lineSeparation = "____________________________________________________________\n";
    
    /** 
     * Comparator function codes
     */
	private static final int EQUAL = 0;
	private static final int GREATER_THAN = 1;
	private static final int SMALLER_THAN = 2;
	
	/**
	 * Filter type codes
	 */
    private static final int DATE = 0;
    private static final int TYPE = 1;
    
    /**
     * prints welcome message and instructions for use.
     */
    public void welcome(EventList Events){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println(lineSeparation + "Hello! I'm Duke\nWhat can I do for you?\n");

        System.out.println("Commands:");
        System.out.println("1. list: Print a list of events currently stored.");
        System.out.println("2. todo <description of event>: Adds a simple event with no time or date involved");
        System.out.println("3. event OR deadline <description of event> /at OR /by <time>: adds an event/deadline to the list of events.");
        System.out.println("4. done <event number>: completes a event");
        System.out.println("5. bye: exits the program\n");
        System.out.println("6. reminder: view your upcoming events for the next 3 days");
        System.out.println("When entering dates and times, you may do so in the following format for faster entry : \n" +
                "dd-MM-yyyy HHmm\n" + lineSeparation);
//        printReminder(Events);
        System.out.println("Enter a command:");
    }
    
    
    /**
     * Obtains the current date and prints the events to be completed within the next
     * three days as a reminder.
     * @param events the EventList used in the Duke function.
     */

    public void printReminder(EventList events) {
        String systemDateAndTime = new Date().toString();
    	EventDate limit = new EventDate(systemDateAndTime);
    	limit.addDaysAndSetMidnight(3);
    	String reminderDeadline = limit.getEventJavaDate().toString();
    	Predicate<Object> pred = new Predicate<>(limit, GREATER_THAN);
    	System.out.print(lineSeparation);
    	System.out.print("The time now is " + systemDateAndTime + ".\n");
    	System.out.print("Here is a list of events you need to complete in the next 3 days (by " + reminderDeadline + "):\n");
    	System.out.print(events.filteredList(pred, DATE));
    	System.out.print(lineSeparation);
    }

    /**
     * Prints a message when an invalid command is entered.
     */
    public void printInvalidCommand() {
        System.out.print(lineSeparation);
        System.out.println("Sorry! I don't know what that means.");
        System.out.print(lineSeparation);
    }

    /**
     * prints entire list of events stored.
     *
     * @param events Model_Class.EventList object containing all stored classes and pertaining methods.
     */
    public static void printListOfEvents(EventList events) {
        System.out.print(lineSeparation);
        System.out.print(events.listOfEvents_String());
        System.out.print(lineSeparation);
    }

    /**
     * prints goodbye message
     */
    public static void bye() {
        System.out.print(lineSeparation + "Bye. Hope to see you again soon!\n" + lineSeparation);
    }

    /**
     * @return line of underscores to separate different Model_Class.UI outputs.
     */
    public String getLineSeparation() {
        return lineSeparation;
    }

    /**
     * prints message when a event is successfully added
     *
     * @param eventAdded event in question
     * @param numEvents  total number of events
     */
    public void eventAdded(Event eventAdded, int numEvents) {
        try {
            System.out.println(lineSeparation + "Got it. I've added this event:");
            System.out.println("[" + eventAdded.getDoneSymbol() + "][" + eventAdded.getType() + "] " +
                    eventAdded.getDescription() + " START: " + eventAdded.getStartDate().getFormattedDateString() +
                    " END: " + eventAdded.getEndDate().getFormattedDateString());
            System.out.println("Now you have " + numEvents + " events in the list.");
            System.out.print(lineSeparation);
        } catch (NullPointerException e) {
            System.out.println("[" + eventAdded.getDoneSymbol() + "][" + eventAdded.getType() + "] " +
                    eventAdded.getDescription() + " BY: " + eventAdded.getStartDate().getFormattedDateString());
            System.out.println("Now you have " + numEvents + " events in the list.");
            System.out.print(lineSeparation);
        }
    }

    /**
     * prints message when a event is marked as completed
     *
     * @param event event in question
     */
    public void eventDone(Event event) {
        System.out.print(lineSeparation);
        System.out.println("Nice! I've marked this event as done:");
        System.out.println(event.toString());
        System.out.print(lineSeparation);
    }

    /**
     * prints message when a event is deleted successfully
     *
     * @param event event in question to be deleted
     */
    public void eventDeleted(Event event) {
        System.out.print(lineSeparation);
        System.out.println("Noted. I've removed this event:");
        System.out.println(event.toString());
        System.out.print(lineSeparation);
    }

    /**
     * prints message containing events found when a search is performed.
     * prints error message if no events are found
     *
     * @param allFoundEvents string containing all the events found, separated by newline character
     * @param found         boolean signifying whether or not any events were found
     */
    public void printFoundEvents(String allFoundEvents, boolean found) {
        if (found) {
            System.out.print(lineSeparation);
            System.out.println("Here are the matching events in your list:");
            System.out.print(allFoundEvents);
            System.out.print(lineSeparation);
        } else {
            System.out.print(lineSeparation);
            System.out.println("No such events were found! Please try again.");
            System.out.print(lineSeparation);
        }
    }

    /**
     * prints message if command does not contain valid input for related event.
     */
    public void noSuchEvent() {
        System.out.print(lineSeparation);
        System.out.println("There is no such event! Please try again.");
        System.out.print(lineSeparation);
    }

    /**
     * prints message if no event description is found when adding a new event to the list
     */
    public void eventDescriptionEmpty() {
        System.out.print(lineSeparation);
        System.out.println("The description of your event cannot be empty!");
        System.out.print(lineSeparation);
    }

    /**
     * prints message when event index from input is not an integer
     */
    public void notAnInteger() {
        System.out.print(lineSeparation);
        System.out.println("That is not an integer! Please enter the index of the event you intend to alter.");
        System.out.print(lineSeparation);
    }

    /**
     * prints message when input format is wrong for addition of new event type event.
     */
    public void eventFormatWrong() {
        System.out.print(lineSeparation);
        System.out.println("Please enter the date in the format 'dd-MM-yyyy HHmm' or 'dd-MM-yyyy'.");
        System.out.print(lineSeparation);
    }

    public void scheduleClash(Event event) {
        System.out.print(lineSeparation);
        System.out.println("That event clashes with another in the schedule! " +
                "Please resolve the conflict and try again!");
        System.out.println("Clashes with: " + event.toString());
        System.out.print(lineSeparation);
    }

    /**
     * prints message when recurring events are added to the list successfully
     */
    public void recurringEventAdded(Event eventAdded, int numEvents, int period) {
        System.out.println(lineSeparation + "Got it. I've added these recurring events:");
        System.out.println("[" + eventAdded.getDoneSymbol() + "][" + eventAdded.getType() + "] " +
                eventAdded.getDescription() + " START: " + eventAdded.getStartDate().getFormattedDateString() +
                " END: " + eventAdded.getEndDate().getFormattedDateString() + " (every " + period + " days)");
        System.out.println("Now you have " + numEvents + " events in the list.");
        System.out.print(lineSeparation);
    }

    /**
     * prints message when format of input is wrong for adding new recurring events
     */
    public void recursionFormatWrong() {
        System.out.print(lineSeparation);
        System.out.println("Please enter the period of the recurring event (in days) after /every.");
        System.out.print(lineSeparation);
    }

    /**
     * prints next 3 days that are free
     *
     * @param freeDays queue of free days of type DateObj
     */
    public void printFreeDays(Queue<String> freeDays) {
        System.out.print(lineSeparation);
        System.out.println("Here are the next 3 free days!");
        for(int i=0; i<=freeDays.size(); i++) {
            System.out.println(freeDays.poll());
        }
        System.out.print(lineSeparation);
    }

    /**
     * prints message when reschedule an event successfully
     *
     * @param event event after rescheduled
     */
    public void rescheduleEvent(Event event) {
        System.out.print(lineSeparation);
        System.out.println("Rescheduled event to " + event.toString() + " successfully!");
        System.out.print(lineSeparation);
    }

    public void errorWritingToFile() {
        System.out.print(lineSeparation);
        System.out.println("Error writing to file! Details not saved!");
        System.out.print(lineSeparation);
    }

    /**
     * Prints message to show success of edit command.
     *
     * @param eventIndex The index of the edited event
     */
    public void printEditedEvent(int eventIndex) {
        System.out.print(lineSeparation);
        System.out.println("Successfully edited event " + eventIndex + "!");
        System.out.print(lineSeparation);
    }
}
