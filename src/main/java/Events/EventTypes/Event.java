package Events.EventTypes;

import Events.Formatting.EventDate;
import Events.Storage.Goal;
import Events.Storage.Contact;

import java.util.ArrayList;

/**
 * Model_Class.Event object inherits Model_Class.Task.
 * Is a type of task available for use.
 */
public abstract class Event implements Comparable<Event> {
    protected String description;
    protected boolean isDone;
    private EventDate startEventDate;
    private EventDate endEventDate;
    private char eventType;
    protected ArrayList<Goal> goalsList;
    protected ArrayList<Contact> contactList;

    private ArrayList<String> checklist;

    /**
     * Creates event with one date input (e.g todo)
     *
     * @param description event description
     * @param isDone      boolean representing state of event completion
     * @param dateAndTime string representing date of event
     */
    public Event(String description, boolean isDone, String dateAndTime) {
        this.description = description;
        this.isDone = isDone;
        this.startEventDate = new EventDate(dateAndTime);
        this.endEventDate = null; //no end date, set to null
        this.eventType = 'T'; //event with no end date can only be ToDo
        this.goalsList = new ArrayList<>();
        this.contactList = new ArrayList<>();
        this.checklist = new ArrayList<>();
    }

    /**
     * Creates event with two date input
     *
     * @param description      event description
     * @param isDone           boolean representing state of event completion
     * @param startDateAndTime string representing start date of event
     * @param endDateAndTime   string representing end date of event
     */
    public Event(String description, boolean isDone, String startDateAndTime, String endDateAndTime, char eventType) {
        this.description = description;
        this.isDone = isDone;
        this.startEventDate = new EventDate(startDateAndTime);
        this.endEventDate = new EventDate(endDateAndTime);
        this.eventType = eventType;
        this.goalsList = new ArrayList<>();
        this.contactList = new ArrayList<>();
        this.checklist = new ArrayList<>();
    }

    /**
     * Edit event with new description and two date input
     *
     * @param newDescription new event description
     */
    public void editEvent(String newDescription) {
        this.description = newDescription;
    }

    /**
     * Converts event type task to string format for printing.
     *
     * @return Formatted string representing the event, whether or not it is completed and its date.
     */
    public String toString() {
        if (getType() == 'T') { //if todo, then only one date entry
            return "[" + getDoneSymbol() + "][T] " + getDescription()
                    + " BY: " + this.getStartDate().getFormattedDateString();
        } else { //multiple date entries
            return "[" + getDoneSymbol() + "][" + getType() + "] " +
                    getDescription() + " START: " + startEventDate.getFormattedDateString() +
                    " END: " + endEventDate.getFormattedDateString();
        }
    }

    public String toStringForFile() { //string that is to be saved to file.
        if (getEndDate() == null) {
            return getDoneSymbol() + getType() + " " + getDescription() + " " +
                    getStartDate().getUserInputDateString();
        }
        return getDoneSymbol() + getType() + " " + getDescription() + " " +
                getStartDate().getUserInputDateString() + " " + getEndDate().getUserInputDateString();
    }

    public char getType() {
        return eventType;
    }

    public EventDate getStartDate() {
        return startEventDate;
    }

    public EventDate getEndDate() {
        return endEventDate;
    }

    public String getDescription() {
        return description;
    }

    public String getDoneSymbol() {
        return (isDone) ? "V" : "X";
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void rescheduleStartDate(EventDate newStartDate) {
        this.startEventDate = newStartDate;
    }

    public void rescheduleEndDate(EventDate newEndDate) {
        this.endEventDate = newEndDate;
    }

    public void addGoal(Goal goalInput) {
        goalsList.add(goalInput);
    }

    public void removeGoal(int goalID) {
        goalsList.remove(goalID);
    }

    public ArrayList<Goal> getGoalList() {
        return goalsList;
    }

    //@@author YuanJiayi
    public void addContact(Contact contactInput) {
        contactList.add(contactInput);
    }

    public void removeContact(int contactIndex) {
        contactList.remove(contactIndex);
    }

    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    public void editContact(int contactIndex, char editType, String newContact) {
        if (editType == 'N') {
            contactList.get(contactIndex).setName(newContact);
        }
        else if (editType == 'E') {
            contactList.get(contactIndex).setEmail(newContact);
        }
        else if (editType == 'P') {
            contactList.get(contactIndex).setPhoneNo(newContact);
        }
    }

    //@@author
    public void addChecklist(String newChecklist) {
        System.out.println(newChecklist);
        this.checklist.add(newChecklist);
    }

    public ArrayList<String> getChecklist() { return this.checklist; }

    public void editChecklist(int checklistIndex, String newChecklist) { this.checklist.set(checklistIndex, newChecklist); }

    public void deleteChecklist(int checklistIndex) { this.checklist.remove(checklistIndex); }

    @Override
    public int compareTo(Event currEvent) {
        if (this.startEventDate.getEventJavaDate().compareTo(currEvent.startEventDate.getEventJavaDate()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
