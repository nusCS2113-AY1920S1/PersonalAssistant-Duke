package Events.EventTypes;

import Events.Formatting.EventDate;
import Events.Storage.Checklist;
import Events.Storage.Contact;
import Events.Storage.Goal;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Model_Class.Event object inherits Model_Class.Task.
 * Is a type of task available for use.
 */
public abstract class Event implements Comparable<Event> {
    private static Logger logger = Logger.getLogger("Event");
    protected String description;
    protected boolean isDone;
    private EventDate startEventDate;
    private EventDate endEventDate;
    private char eventType;
    protected ArrayList<Goal> goalsList;
    protected ArrayList<Contact> contactList;

    private Checklist checklist;

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
        this.checklist = new Checklist();
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
        this.checklist = new Checklist();
    }

    /**
     * Edit event with new description and two date input
     *
     * @param newDescription new event description
     */
    public void editEvent(String newDescription) {
        this.description = newDescription;
//        logger.log(Level.INFO, "The description of the event is edited");
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
            return getDoneSymbol() + getType() + "/" + getDescription() + "/" +
                    getStartDate().getUserInputDateString();
        }
        return getDoneSymbol() + getType() + "/" + getDescription() + "/" +
                getStartDate().getUserInputDateString() + "/" + getEndDate().getUserInputDateString();
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
//        logger.log(Level.INFO, "The event is marked as done");
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void rescheduleStartDate(EventDate newStartDate) {
        this.startEventDate = newStartDate;
//        logger.log(Level.INFO, "The startEventDate of the event is rescheduled");
    }

    public void rescheduleEndDate(EventDate newEndDate) {
        this.endEventDate = newEndDate;
//        logger.log(Level.INFO, "The endEventDate of the event is rescheduled");
    }

    public void addGoal(Goal goalInput) {
        goalsList.add(goalInput);
//        logger.log(Level.INFO, "The new goal is added to the list");
    }

    public void removeGoal(int goalID) {
        goalsList.remove(goalID);
//        logger.log(Level.INFO, "The goal is removed from the list");
    }

    public ArrayList<Goal> getGoalList() {
        return goalsList;
    }

    public void editGoalList(Goal goalInput, int index) {
        goalsList.set(index, goalInput);
    }

    public void updateGoalAchieved(int goalID) {
        goalsList.get(goalID).setAchieved();
    }

    //@@author YuanJiayi
    public void addContact(Contact contactInput) {
        contactList.add(contactInput);
//        logger.log(Level.INFO, "A new contact is added to the list");
    }

    public void removeContact(int contactIndex) {
        contactList.remove(contactIndex);
//        logger.log(Level.INFO, "The contact is removed from the list");
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

    //@@author ZhangYihanNus
    public void addChecklist(String newChecklist) {
        this.checklist.addItem(newChecklist);
//        logger.log(Level.INFO, "The new checklist is added to the list");
    }

    public ArrayList<String> getChecklist() {
        return this.checklist.getChecklist();
    }

    public void editChecklist(int checklistIndex, String newChecklist) {
        this.checklist.editItem(checklistIndex, newChecklist);
//        logger.log(Level.INFO, "The checklist is edited");
    }

    public void deleteChecklist(int checklistIndex) {
        this.checklist.deleteItem(checklistIndex);
//        logger.log(Level.INFO, "The checklist is removed form the list");
    }

    //@@author
    @Override
    public int compareTo(Event currEvent) {
        if (this.startEventDate.getEventJavaDate().compareTo(currEvent.startEventDate.getEventJavaDate()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
