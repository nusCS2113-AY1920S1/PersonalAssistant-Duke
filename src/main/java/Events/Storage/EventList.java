package Events.Storage;

import Events.EventTypes.Event;
import Events.EventTypes.EventSubClasses.AssessmentSubClasses.Exam;
import Events.EventTypes.EventSubClasses.AssessmentSubClasses.Recital;
import Events.EventTypes.EventSubClasses.Concert;
import Events.EventTypes.EventSubClasses.RecurringEventSubclasses.Lesson;
import Events.EventTypes.EventSubClasses.RecurringEventSubclasses.Practice;
import Events.EventTypes.EventSubClasses.ToDo;
import Events.EventTypes.Event;
import Events.Formatting.DateObj;
import Events.Formatting.Predicate;

import java.text.SimpleDateFormat;
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
    public EventList(ArrayList<String> inputList) {
        //magic characters for type of event
        final char TODO = 'T';
        final char CONCERT = 'C';
        final char LESSON = 'L';
        final char PRACTICE = 'P';
        final char EXAM = 'E';
        final char RECITAL = 'R';

        eventArrayList = new ArrayList<Event>();
        for (String currLine : inputList) {
            boolean isDone = (currLine.substring(4,7).equals("✓"));
            if (currLine.charAt(1) == TODO) { //todo type event
                eventArrayList.add(new ToDo());
            } else if (currLine.charAt(1) == CONCERT) {
                eventArrayList.add(new Concert());
            } else if (currLine.charAt(1) == LESSON) {
                eventArrayList.add(new Lesson());
            } else if (currLine.charAt(1) == PRACTICE) {
                eventArrayList.add(new Practice());
            } else if (currLine.charAt(1) == EXAM) {
                eventArrayList.add(new Exam());
            } else if (currLine.charAt(1) == RECITAL) {
                eventArrayList.add(new Recital());
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
        if (event instanceof ToDo) {
            this.eventArrayList.add(event);
            return true;
        }
        else {
            Event clashEvent = clashEvent(event); //check the list for a schedule clash
            if (clashEvent == null) { //null means no clash was found
                this.eventArrayList.add(event);
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
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy HHmm");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        DateObj eventDate = new DateObj(event.getStartDateObj().toOutputString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(eventDate.getJavaDate());
        for (int addEventCount = 0; addEventCount*period <= ONE_SEMESTER_DAYS; addEventCount++) {
            String timeString = null;
            if (eventDate.getFormat() == 1) {
                timeString = format1.format(calendar.getTime());
            }
            else if (eventDate.getFormat() == 2) {
                timeString = format2.format(calendar.getTime());
            }
            this.eventArrayList.add(new Event(event.getDescription(), timeString));
            calendar.add(Calendar.DATE, period);
        }
        return true;
    }

    /**
     * Checks the list of events for any clashes with the newly added event. If
     * there is a clash, return a reference to the event, if not, return null.
     * @param event newly added event
     * @return event that causes a clash
     */
    private Event clashEvent(Event event) {
        for (Event currEvent : eventArrayList) {
            try {
                if (currEvent.startDateToString().equals(event.startDateToString())) {
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
                if (eventArrayList.get(i) instanceof Event || eventArrayList.get(i) instanceof Deadline) {
                	if (!predicate.check(eventArrayList.get(i).getStartDateObj())) {
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
