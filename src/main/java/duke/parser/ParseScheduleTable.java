package duke.parser;

import duke.models.ToDo;
import duke.util.ApacheLogger;
import duke.util.DateHandler;
import duke.view.CliViewSchedule;

import java.util.Scanner;

/**
 * Parse will handle the input received from the Schedule table.
 */
public class ParseScheduleTable {

    private CliViewSchedule cliViewSchedule = new CliViewSchedule();

    /**
     * Will parse the input received from the user to create an object to add.
     *
     * @param input the user input
     * @param date when to add the todo item
     * @return
     */
    public ToDo createToDo(String input, String date) {
        try {
            int indexName = input.indexOf("n/");
            int indexStart = input.indexOf("s/");
            int indexEnd = input.indexOf("d/");
            int indexLocation = input.indexOf("loc/");
            String name = input.substring(indexName + 2, indexStart);
            String start = input.substring(indexStart + 2, indexEnd);
            String end = input.substring(indexEnd + 2, indexLocation);
            String loc = input.substring(indexLocation + 4);
            while (!DateHandler.correctTime("HHmm", start)) {
                ApacheLogger.logMessage("ParseScheduleTable", "Incorrect Start time");
                cliViewSchedule.message("Sorry please enter start time in 24 hour format e.g 1400");
                Scanner scanner = new Scanner(System.in);
                start = scanner.nextLine();
            }
            while (!DateHandler.correctTime("HHmm", end)) {
                ApacheLogger.logMessage("ParseScheduleTable", "Incorrect End time");
                cliViewSchedule.message("Sorry please enter end time in 24 hour format e.g 1400");
                Scanner scanner = new Scanner(System.in);
                end = scanner.nextLine();
            }
            ToDo toDo = new ToDo(start, end, loc, name, date);
            ApacheLogger.logMessage("ParseScheduleTable", "Correct Time");
            return toDo;
        } catch (StringIndexOutOfBoundsException e) {
            cliViewSchedule.showDontKnow();
            ApacheLogger.logMessage("Schedule",
                "Wrong input format for adding to table");
        }
        return null;
    }

}
