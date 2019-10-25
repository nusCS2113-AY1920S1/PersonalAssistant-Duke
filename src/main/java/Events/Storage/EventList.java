package Events.Storage;

import Events.EventTypes.Event;
import Events.EventTypes.EventSubclasses.AssessmentSubclasses.Exam;
import Events.EventTypes.EventSubclasses.AssessmentSubclasses.Recital;
import Events.EventTypes.EventSubclasses.Concert;
import Events.EventTypes.EventSubclasses.RecurringEventSubclasses.Lesson;
import Events.EventTypes.EventSubclasses.RecurringEventSubclasses.Practice;
import Events.EventTypes.EventSubclasses.ToDo;
import Events.Formatting.EventDate;
import Events.Formatting.Predicate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * Allows for access to the list of events currently stored, and editing that list of events.
 * Does NOT contain any methods for reading/writing to savefile.
 */
public class EventList {

    /**
     * list of Model_Class.Event objects currently stored.
     */
    private ArrayList<Event> eventArrayList;

    /**
     * Comparator function codes
     */
    private static final int GREATER_THAN = 1;

    /**
     * Filter type codes
     */
    private static final int DATE = 0;
    private static final int TYPE = 1;

    /**
     * Creates new Model_Class.EventList object.
     *
     * @param inputList list of strings containing all information extracted from save file
     */
    public EventList(ArrayList<String> inputList) {
        //magic characters for type of event
        final char TODO = 'T';
        final char CONCERT = 'C';
        final char LESSON = 'L';
        final char PRACTICE = 'P';
        final char EXAM = 'E';
        final char RECITAL = 'R';

        eventArrayList = new ArrayList<>();
        for (String currLine : inputList) {
            boolean isDone = currLine.substring(0, 3).equals("✓");
            char eventType = currLine.charAt(3);

            if (eventType == TODO) {
                String[] splitString = currLine.split(" ");
                String description = splitString[1];
                String date = splitString[2];
                eventArrayList.add(new ToDo(description, isDone, date));

            } else { //for all other events
                String[] splitString = currLine.split(" ");
                String description = splitString[1];
                String startDateAndTime = splitString[2] + " " + splitString[3];
                String endDateAndTime = splitString[4] + " " + splitString[5];

                switch (eventType) {
                    case CONCERT:
                        eventArrayList.add(new Concert(description, isDone, startDateAndTime, endDateAndTime));
                        break;

                    case LESSON:
                        eventArrayList.add(new Lesson(description, isDone, startDateAndTime, endDateAndTime));
                        break;

                    case PRACTICE:
                        eventArrayList.add(new Practice(description, isDone, startDateAndTime, endDateAndTime));
                        break;

                    case EXAM:
                        eventArrayList.add(new Exam(description, isDone, startDateAndTime, endDateAndTime));
                        break;

                    case RECITAL:
                        eventArrayList.add(new Recital(description, isDone, startDateAndTime, endDateAndTime));
                        break;
                }
            }
        }
    }

    /**
     * Edit an event's description, start time and end time in the list.
     *
     * @param eventIndex     Index of the event to be edited.
     * @param newDescription The new description.
     */
    public void editEvent(int eventIndex, String newDescription) {
        Event tempEvent = this.eventArrayList.get(eventIndex);
        tempEvent.editEvent(newDescription);
        this.eventArrayList.set(eventIndex, tempEvent);
    }

    /**
     * Checks for a clash, then adds a new event if possible.
     *
     * @param event Model_Class.Event object to be added
     */
    public void addEvent(Event event) throws Exception {
        if (event.getStartDate().getEventJavaDate().compareTo(event.getEndDate().getEventJavaDate()) == 1) {
            throw new Exception();
        }

        Event clashEvent = clashEvent(event); //check the list for a schedule clash
        if (clashEvent == null) { //null means no clash was found
            this.eventArrayList.add(event);
        } else { //if clash is found, notify user via terminal.
            throw new ClashException(clashEvent);
        }
    }

    public void addNewTodo(Event event) {
        this.eventArrayList.add(event);
    }

    /**
     * Adds recurring events to the list.
     *  @param event  Event to be added as recursion.
     * @param period Period of the recursion.
     */
    public void addRecurringEvent(Event event, int period) throws ClashException {
        Calendar calendarStartDate = Calendar.getInstance();
        Calendar calendarEndDate = Calendar.getInstance();
        calendarStartDate.setTime(event.getStartDate().getEventJavaDate());
        calendarEndDate.setTime(event.getEndDate().getEventJavaDate());

        ArrayList<Event> tempEventList = new ArrayList<>();

        Event newEvent = null;
        int ONE_SEMESTER_DAYS = 16 * 7;
        for (int addEventCount = 0; addEventCount * period <= ONE_SEMESTER_DAYS; addEventCount++) {
            EventDate toFormatCalendarStartDate = new EventDate(calendarStartDate.getTime());
            EventDate toFormatCalendarEndDate = new EventDate(calendarEndDate.getTime());
            if (event.getType() == 'L') {
                newEvent = new Lesson(event.getDescription(), toFormatCalendarStartDate.getUserInputDateString(),
                        toFormatCalendarEndDate.getUserInputDateString());
            } else if (event.getType() == 'P') {
                newEvent = new Practice(event.getDescription(), toFormatCalendarStartDate.getUserInputDateString(),
                        toFormatCalendarEndDate.getUserInputDateString());
            }

            assert newEvent != null;
            if (clashEvent(newEvent) == null) {
                tempEventList.add(newEvent);
            } else {
                throw new ClashException(newEvent);
            }
            calendarStartDate.add(Calendar.DATE, period);
            calendarEndDate.add(Calendar.DATE, period);
        }

        this.eventArrayList.addAll(tempEventList);
    }

    /**
     * Checks the list of events for any clashes with the newly added event. If
     * there is a clash, return a reference to the event, if not, return null.
     *
     * @param checkingEvent newly added event
     * @return event that causes a clash
     */
    private Event clashEvent(Event checkingEvent) {
        /*  NOTE: DateObj userInputString is arranged as follows: dd-MM-yyyy HHmm.
            for now, only have one date with differing start time and end time, date in startDateObj will be same as
            in endDateObj */

        //split new event date string into date and time.
        String[] newEventStartDateTime = checkingEvent.getStartDate().getUserInputDateString().split(" ");
        String[] newEventEndDateTime = checkingEvent.getEndDate().getUserInputDateString().split(" ");
        String newEventDate = newEventStartDateTime[0]; //assign date
        int newEventStartTime = Integer.parseInt(newEventStartDateTime[1]); //assign time
        int newEventEndTime = Integer.parseInt(newEventEndDateTime[1]);

        for (Event currEvent : eventArrayList) { //scan list for clashes
            if (currEvent.getType() == 'T') continue; //skip scan if todo class

            String[] currEventStartDateTime = currEvent.getStartDate().getUserInputDateString().split(" ");
            String[] currEventEndDateTime = currEvent.getEndDate().getUserInputDateString().split(" ");

            if (newEventDate.equals(currEventStartDateTime[0]) && //check for same date
                    timeClash(newEventStartTime, newEventEndTime, currEventStartDateTime[1], currEventEndDateTime[1])) { //check for time clash
                return currEvent; //clash found
            }
        }
        return null; //no clash found
    }

    /**
     * Checks for a clash in time, returns appropriate boolean.
     */
    private boolean timeClash(int newEventStartTime, int newEventEndTime, String s, String s1) {
        int currEventStartTime = Integer.parseInt(s); //assign time
        int currEventEndTime = Integer.parseInt(s1);

        if (newEventStartTime > currEventStartTime) { //new event starts after current event starts
            return currEventEndTime > newEventStartTime; //check if new event starts before current event ends

        } else if (newEventStartTime < currEventStartTime) { //new event starts before current event starts
            return newEventEndTime > currEventStartTime; //check if new event ends after current event starts

        } else { //new event starts at the same time as current event
            return true;
        }
    }

    /**
     * sorts the list of events/tasks according to date, in increasing order.
     */
    public void sortList() {
        Collections.sort(eventArrayList);
    }

    /**
     * Deletes a event from the list.
     *
     * @param eventNo Index of event to be deleted
     */
    public void deleteEvent(int eventNo) {
        this.eventArrayList.remove(eventNo);
    }

    /**
     * Gets list of Model_Class.Event objects stored
     *
     * @return Array of EventLists containing all events.
     */
    public ArrayList<Event> getEventArrayList() {
        return this.eventArrayList;
    }

    /**
     * Gets number of events stored.
     *
     * @return number of events stored
     */
    public int getNumEvents() {
        return eventArrayList.size();
    }

    /**
     * Gets a specific event using indexing.
     *
     * @param index Index of event to be extracted
     * @return Model_Class.Event object of specified event
     */
    public Event getEvent(int index) {
        return eventArrayList.get(index);
    }

    /**
     * Gets the entire list of events stored in String format
     *
     * @return String containing all events, separated by a newline.
     */
    public String listOfEvents_String() {
        String allEvents = "";
        for (int i = 0; i < eventArrayList.size(); ++i) {
            if (eventArrayList.get(i) == null) continue;
            int j = i + 1;
            allEvents += j + ". " + this.getEvent(i).toString() + "\n";
        }
        return allEvents;
    }

    /**
     * @return String containing the filtered list of events, each separated by a newline.
     */
    private String filteredList(Predicate<Object> predicate) {
        String filteredEvents = "";
        int j;
        for (int i = 0; i < eventArrayList.size(); ++i) {
            if (eventArrayList.get(i) == null) continue;
            else if (!predicate.check(eventArrayList.get(i).getStartDate())) continue; 
            j = i + 1;
            filteredEvents += j + ". " + this.getEvent(i).toString() + "\n";
        }
        return filteredEvents;
    }

    /**
     * @return String containing events found in the next 3 days
     */
    public String getReminder() {
        String systemDateAndTime = new Date().toString();
        EventDate limit = new EventDate(systemDateAndTime);
        limit.addDaysAndSetMidnight(3);
        String reminderDeadline = limit.getEventJavaDate().toString();
        Predicate<Object> objectPredicate = new Predicate<>(limit, GREATER_THAN);
        return "The time now is " + systemDateAndTime + ".\n" +
                "Here is a list of events you need to complete in the next 3 days (by " +
                reminderDeadline + "):\n" + filteredList(objectPredicate);
    }

    /**
     * Used to reinstate deleted event in case of failure to reschedule
     */
    public void undoDeletionOfEvent(Event event) {
        eventArrayList.add(event);
    }
}
