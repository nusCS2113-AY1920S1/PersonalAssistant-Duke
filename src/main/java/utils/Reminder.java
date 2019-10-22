package utils;

import core.Ui;
import model.tasks.Deadline;
import model.tasks.Event;
import model.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Reminder {
    /**
     * Check the reminders to display at the start.
     *
     * @param tasks the full task list.
     */
    public static void checkReminders(ArrayList<Task> tasks) {
        if (tasks.size() == 0) {
            Ui.print("You have nothing coming up in the next 3 days");
        } else {
            Date now = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.DATE, 4);
            Date ahead = c.getTime();

            ArrayList<Task> toBeReminded = new ArrayList<Task>();
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getClass().equals(Deadline.class)) {
                    Deadline temp = (Deadline) tasks.get(i);
                    if (temp.getTime().before(ahead) && temp.getTime().after(now)) {
                        toBeReminded.add(tasks.get(i));
                    }
                } else if (tasks.get(i).getClass().equals(Event.class)) {
                    Event temp = (Event) tasks.get(i);
                    if (temp.getTime().before(ahead) && temp.getTime().after(now)) {
                        toBeReminded.add(tasks.get(i));
                    }
                }
            }

            if (toBeReminded.size() == 0) {
                Ui.print("You have nothing coming up in the next 3 days");
            } else {
                String toPrint = "Reminder for the next three days";
                for (int i = 0; i < toBeReminded.size(); i++) {
                    toPrint += "\n" + toBeReminded.get(i);
                }

                Ui.print(toPrint);
            }
        }
    }
}
