package seedu.hustler.logic.parser.anomaly;

import seedu.hustler.Hustler;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import static seedu.hustler.parser.DateTimeParser.getDateTime;

/**
 * Detect anomalies in snooze command input.
 */
public class SnoozeAnomaly extends DetectAnomaly {

    @Override
    public boolean detect(String[] userInput) {
        if (userInput.length != 4) {
            System.out.println("Incorrect format for snooze command.");
            return true;
        }

        int index = Integer.parseInt(userInput[1]);
        if (index >= Hustler.list.size()) {
            System.out.println("The task index provided is invalid");
            return true;
        }

        // 1st method to snooze
        if (userInput[2].contains("/")) {
            String dateTimeString = userInput[2] + " " + userInput[3];
            if(getDateTime(dateTimeString) == null) {
                return true;
            }
            return false;
        } else {
            // 2nd method to snooze
            String period = userInput[3];
            period.toLowerCase();
            String[] validPeriods = {"minutes", "hours", "days", "weeks", "months"};
            for (String validPeriod : validPeriods) {
                if (period.equals(validPeriod)) {
                    return true;
                }
            }
            return false;
        }
    }
}
