package Events.Storage;

import Events.EventTypes.Event;
import Events.EventTypes.EventSubclasses.Concert;
import Events.EventTypes.EventSubclasses.RecurringEventSubclasses.Lesson;
import Events.EventTypes.EventSubclasses.RecurringEventSubclasses.Practice;
import Events.EventTypes.EventSubclasses.ToDo;
import Events.Formatting.DateObj;
import Events.Formatting.Predicate;
import UserElements.Parser;

import java.util.ArrayList;
import java.util.Calendar;

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
     * Filter type codes
     */
    static final int DATE = 0;
    static final int TYPE = 1;

    protected int ONE_SEMESTER_DAYS = 16*7;

    /**
     * Creates new Model_Class.EventList object.
     *
     * @param inputList list of strings containing all information extracted from save file
     */
    public EventList(ArrayList<String> inputList, Parser parser) {
        //magic characters for type of event
        final char TODO = 'T';
        final char CONCERT = 'C';
        final char LESSON = 'L';
        final char PRACTICE = 'P';
        final char EXAM = 'E';
        final char RECITAL = 'R';

        eventArrayList = new ArrayList<Event>();
        for (String currLine : inputList) {
            boolean isDone = currLine.substring(0, 3).equals("✓");
            char eventType = currLine.charAt(3);

            if (eventType == TODO) { //for special todo type event (single date string)
                String[] splitString = currLine.split(" ");
                String description = splitString[1];
                String date = splitString[2];
                eventArrayList.add(new ToDo(description, isDone, date));
            } else { //for all other events
                String[] splitString = currLine.split(" ");
                String description = splitString[1];
                String startDateAndTime = splitString[2] + " " + splitString[3];
                String endDateAndTime;

                if (splitString.length == 6) {
                    endDateAndTime = splitString[4] + " " + splitString[5];
                } else {
                    endDateAndTime = "";
                }

                if (eventType == CONCERT) {
                    eventArrayList.add(new Concert(description, isDone, startDateAndTime, endDateAndTime));
                } else if (eventType == LESSON) {
                    eventArrayList.add(new Lesson(description, isDone, startDateAndTime, endDateAndTime));
                } else if (eventType == PRACTICE) {
                    eventArrayList.add(new Practice(description, isDone, startDateAndTime, endDateAndTime));
                }
            }
        }
    }

    /**
     * Checks for a clash, then adds a new event if possible.
     *
     * @param event Model_Class.Event object to be added
     * @return boolean signifying whether or not the event was added successfully. True if succeeded
     * and false if not
     */
    public boolean addEvent(Event event) {
        if (event.getType() == 'T') {
            DateObj eventStartDate = new DateObj(event.getStartDate().getUserInputDateString());
            this.eventArrayList.add(new ToDo(event.getDescription(), eventStartDate.getUserInputDateString()));
            return true;
        }
        else {
            Event clashEvent = clashEvent(event); //check the list for a schedule clash
            if (clashEvent == null) { //null means no clash was found
                if (event.getType() == 'L') {
                    this.eventArrayList.add(new Lesson(event.getDescription(), event.getStartDate().getUserInputDateString(), event.getEndDate().getUserInputDateString()));
                } else if (event.getType() == 'P') {
                    this.eventArrayList.add(new Practice(event.getDescription(), event.getStartDate().getUserInputDateString(), event.getEndDate().getUserInputDateString()));
                }
                return true;
            } else return false;
        }
    }

    /**
     * Adds recurring events to the list.
     *
     * @param event Event to be added as recursion.
     * @param period Period of the recursion.
     */
    public boolean addRecurringEvent(Event event, int period) {
        Calendar calendarStartDate = Calendar.getInstance();
        Calendar calendarEndDate = Calendar.getInstance();
        calendarStartDate.setTime(event.getStartDate().getEventJavaDate());
        calendarEndDate.setTime(event.getEndDate().getEventJavaDate());
        for (int addEventCount = 0; addEventCount*period <= ONE_SEMESTER_DAYS; addEventCount++) {
            DateObj toFormatCalendarStartDate = new DateObj(calendarStartDate.getTime());
            DateObj toFormatCalendarEndDate = new DateObj(calendarEndDate.getTime());
            if (event.getType() == 'L') {
                this.eventArrayList.add(new Lesson(event.getDescription(), toFormatCalendarStartDate.getUserInputDateString(), toFormatCalendarEndDate.getUserInputDateString()));
            } else if (event.getType() == 'P') {
                this.eventArrayList.add(new Practice(event.getDescription(), toFormatCalendarStartDate.getUserInputDateString(), toFormatCalendarEndDate.getUserInputDateString()));
            }
            calendarStartDate.add(Calendar.DATE, period);
            calendarEndDate.add(Calendar.DATE, period);
        }
        return true;
    }

    /**
     * Checks the list of events for any clashes with the newly added event. If
     * there is a clash, return a reference to the event, if not, return null.
     * @param checkingEvent newly added event
     * @return event that causes a clash
     */
    private Event clashEvent(Event checkingEvent) {
        for (Event currEvent : eventArrayList) {
            try {
                if (currEvent.toString().equals(checkingEvent.toString())) {
                    return currEvent;
                }
            } catch (Exception e){
            }
        }
        return null;
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
     * Gets a filtered list of events based on a predicate.
     * @return String containing the filtered list of events, separated by a newline.
     */
    public String filteredList(Predicate<Object> predicate, int filterCode) {
        String filteredEvents = "";
        int j = 1;
        for (int i = 0; i < eventArrayList.size(); ++i) {
            if (eventArrayList.get(i) == null) {
                continue;
            } else if (filterCode == DATE) {
                if (eventArrayList.get(i) != null) {
                    if (!predicate.check(eventArrayList.get(i).getStartDate())) {
                        continue;
                    }
                } else {
                    continue;
                }
            } else if (filterCode == TYPE) {
                if (!predicate.check(eventArrayList.get(i).getType())) {
                    continue;
                }
            }
            filteredEvents += j + ". " + this.getEvent(i).toString() + "\n";
            j++;
        }
        return filteredEvents;
    }
}
