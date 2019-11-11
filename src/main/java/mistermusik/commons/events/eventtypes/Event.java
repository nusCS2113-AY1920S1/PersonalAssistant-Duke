package mistermusik.commons.events.eventtypes;

import mistermusik.commons.events.formatting.EventDate;
import mistermusik.commons.Checklist;
import mistermusik.commons.Contact;
import mistermusik.commons.Goal;

import java.util.ArrayList;

/**
 * Parent class of all event types.
 */
public abstract class Event implements Comparable<Event> {
    /**
     * event description.
     */
    protected String description;

    /**
     * state of event (only applicable to ToDo).
     */
    protected boolean isDone;

    /**
     * event start date object.
     */
    protected EventDate startEventDate;

    /**
     * event end date object.
     */
    protected EventDate endEventDate;

    /**
     * character signifying event type.
     */
    private char eventType;

    /**
     * list of goals.
     */
    private ArrayList<Goal> goalsList;

    /**
     * list of contacts.
     */
    private ArrayList<Contact> contactList;

    /**
     * list of items to complete before event.
     */
    private Checklist checklist;

    /**
     * Creates event with one date input (e.g todo).
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
     * Creates event with two date input.
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
     * Edit event with new description and two date input.
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
            return "[" + getDoneSymbol() + "][" + getType() + "] "
                    + getDescription() + " START: " + startEventDate.getFormattedDateString()
                    + " END: " + endEventDate.getFormattedDateString();
        }
    }

    /**
     * Returns corresponding string containing information to be written to txt file for storage.
     */
    public String toStringForFile() { //string that is to be saved to file.
        if (getEndDate() == null) {
            return getDoneSymbol() + getType() + "/" + getDescription() + "/"
                    + getStartDate().getUserInputDateString();
        }
        return getDoneSymbol() + getType() + "/" + getDescription() + "/"
                + getStartDate().getUserInputDateString() + "/" + getEndDate().getUserInputDateString();
    }

    /**
     * Returns character signifying event type.
     */
    public char getType() {
        return eventType;
    }

    /**
     * Gets the event start date.
     */
    public EventDate getStartDate() {
        return startEventDate;
    }

    /**
     * Gets the event end date.
     */
    public EventDate getEndDate() {
        return endEventDate;
    }

    /**
     * Gets the event description (name).
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets string symbol "V" if done, "X" if not done.
     */
    public String getDoneSymbol() {
        return (isDone) ? "V" : "X";
    }

    /**
     * Marks an event as done by setting the isDone boolean to true.
     */
    public void setIsDoneToTrue() {
        this.isDone = true;
    }

    //@@author YuanJiayi

    /**
     * Changes the event start date & time.
     *
     * @param newStartDate new EventDate object to replace current EventDate.
     */
    public void rescheduleStartDate(EventDate newStartDate) {
        this.startEventDate = newStartDate;
    }

    /**
     * Changes the event end date & time.
     *
     * @param newEndDate new EventDate object to replace current EventDate.
     */
    public void rescheduleEndDate(EventDate newEndDate) {
        this.endEventDate = newEndDate;
    }

    //@@author yenpeichih

    /**
     * Adds a new goal to the list of goals for the event.
     *
     * @param goalInput Goal object to be added.
     */
    public void addGoal(Goal goalInput) {
        goalsList.add(goalInput);
    }

    /**
     * Removes a goal from the list of goals.
     *
     * @param goalID Index of goal to be removed.
     */
    public void removeGoal(int goalID) {
        goalsList.remove(goalID);
    }

    /**
     * Retrieves list of goals.
     *
     * @return ArrayList containing goals retrieved.
     */
    public ArrayList<Goal> getGoalList() {
        return goalsList;
    }

    /**
     * Retrieves specific goal object.
     *
     * @param goalID Index of goal object to be retrieved.
     */
    public Goal getGoalObject(int goalID) {
        return goalsList.get(goalID);
    }

    /**
     * Edits a specific goal.
     *
     * @param goalInput Input to replace old goal details.
     * @param index     Index of goal to be edited.
     */
    public void editGoalList(Goal goalInput, int index) {
        goalsList.set(index, goalInput);
    }

    /**
     * Sets a goal as achieved.
     *
     * @param goalID Index of goal in list.
     */
    public void updateGoalAchieved(int goalID) {
        goalsList.get(goalID).setAchieved();
    }

    //@@author YuanJiayi

    /**
     * Adds a contact to the list of contacts.
     *
     * @param contactInput Contact object to be added to list.
     */
    public void addContact(Contact contactInput) {
        contactList.add(contactInput);
    }

    /**
     * Removes a Contact object from the list.
     *
     * @param contactIndex Index for Contact to be removed.
     */
    public void removeContact(int contactIndex) {
        contactList.remove(contactIndex);
    }

    /**
     * Retrieves list of contacts for the event.
     */
    public ArrayList<Contact> getContactList() {
        return contactList;
    }

    /**
     * Edits an existing Contact.
     *
     * @param contactIndex Index of Contact object in list to be edited.
     * @param editType     Character signifying what is to be edited(N for new contact,
     *                     E for email, P for phone number).
     * @param newContact   Information required for editing, in form of string.
     */
    public void editContact(int contactIndex, char editType, String newContact) {
        if (editType == 'N') {
            contactList.get(contactIndex).setName(newContact);
        } else if (editType == 'E') {
            contactList.get(contactIndex).setEmail(newContact);
        } else if (editType == 'P') {
            contactList.get(contactIndex).setPhoneNo(newContact);
        }
    }

    //@@author ZhangYihanNus

    /**
     * Adds a new checklist for an event.
     */
    public void addChecklist(String newChecklist) {
        this.checklist.addItem(newChecklist);
    }

    /**
     * Retrieves checklist for current event.
     *
     * @return ArrayList of Strings containing checklist information.
     */
    public ArrayList<String> getChecklist() {
        return this.checklist.getChecklist();
    }

    /**
     * Edits an item in the checklist.
     *
     * @param checklistIndex Index of item to be edited.
     * @param newChecklist   String containing information required for edition.
     */
    public void editChecklist(int checklistIndex, String newChecklist) {
        this.checklist.editItem(checklistIndex, newChecklist);
    }

    /**
     * Deletes an item from the checklist.
     *
     * @param checklistIndex Index of item to be deleted.
     */
    public void deleteChecklist(int checklistIndex) {
        this.checklist.deleteItem(checklistIndex);
    }

    //@@author

    /**
     * Compares two Event objects based on date, used to sort the list of Events.
     */
    @Override
    public int compareTo(Event currEvent) {
        if (this.startEventDate.getEventJavaDate().compareTo(currEvent.startEventDate.getEventJavaDate()) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
