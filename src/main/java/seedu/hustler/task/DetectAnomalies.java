package seedu.hustler.task;

import java.util.ArrayList;

/**
 * Detect any clash of date and time between tasks.
 * It also detects if tasks with same description has been added multiple times.
 */
public class DetectAnomalies {


    /**
     * Test if there is any clashes.
     *
     * @return true or false.
     */
    public static Boolean test(Task task, ArrayList<Task> list) {

        String[] descriptionToken = task.getDescription().split("/by|/at");
        boolean check = false;
        for (int i = 0; i < list.size(); i++) {

            boolean descriptionMatch = list.get(i).getDescription().trim().equals(descriptionToken[0].trim());

            if (task.getDateTime() == null) {
                if (descriptionMatch) {
                    check = true;
                }
            } else if (list.get(i).getDateTime() != null
                    && task.getDateTime().isEqual(list.get(i).getDateTime()) || descriptionMatch) {
                check = true;
            } else if (list.get(i).getDateTime() == null && descriptionMatch) {
                check = true;
            }
        }
        return check;
    }
}


