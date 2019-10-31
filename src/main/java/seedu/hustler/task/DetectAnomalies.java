package seedu.hustler.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static seedu.hustler.logic.parser.DateTimeParser.getDateTime;
import static seedu.hustler.task.TaskList.getDescription;
import static seedu.hustler.task.TaskList.getTimeString;

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
    public static Boolean test(String taskType, List<String> splitInput, ArrayList<Task> list) {
        String taskDescription = getDescription(splitInput);
        LocalDateTime taskDateTime = null;
        if (!taskType.equals("todo")) {
            taskDateTime = getDateTime(getTimeString(splitInput));
        }
        for (int i = 0; i < list.size(); i++) {
            if (taskDescription.equals(list.get(i).getDescription())) {
                return true;
            }

            if (taskDateTime == null || list.get(i).getDateTime() == null) {
                continue;
            }
            if (taskDateTime.isEqual(list.get(i).getDateTime())) {
                return true;
            }
        }
        return false;
    }
}
