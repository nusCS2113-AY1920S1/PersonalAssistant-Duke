package models.reminder;

import java.util.ArrayList;

public class ReminderList {

    //private String reminderListName;
    private ArrayList<Reminder> reminderList;

    /**
     * Class representing a list with all reminders.
     */
    public ReminderList() {
        //this.reminderListName= reminderListName;
        this.reminderList = new ArrayList<>();
    }

    /*public String getReminderListName() {
        return reminderListName;
    }*/

    /*public void setReminderListName(String reminderListName) {
        this.reminderListName = reminderListName;
    }*/

    /**
     * Adds a new reminder to the list of this project.
     * @param reminder A new reminder to be added to the project.
     */
    public void addReminderList(Reminder reminder) {
        this.reminderList.add(reminder);
    }

    public ArrayList<Reminder> getReminderList() {
        return reminderList;
    }

    public Reminder getReminder(int index) {
        return reminderList.get(index - 1);
    }

}
