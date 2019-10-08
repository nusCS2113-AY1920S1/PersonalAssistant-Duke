package controllers.temp;

import exceptions.DukeException;
import models.temp.tasks.PeriodTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PeriodTaskFactory {
    /**
     * Method to create a new Recurring Task.
     *
     * @param input : Input from user
     * @return : Returns a PeriodTask data model
     * @throws DukeException : throws Exception when parsing date
     */
    public PeriodTask createTask(String input) throws DukeException {
        String[] allArgs = input.split(" ");
        List<String> listArgs = new ArrayList<>(Arrays.asList(allArgs));
        listArgs.remove(0);
        String tempString = String.join(" ", listArgs);
        String[] parsedStrings = tempString.split(" /between ");
        String[] startEndDate = parsedStrings[1].split(" and ");
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = formatter.parse(startEndDate[0]);
            Date endDate = formatter.parse(startEndDate[1]);
            String periodDescription = dateFormatHelper(startDate) + " and " + dateFormatHelper(endDate);
            return new PeriodTask(parsedStrings[0], periodDescription);
        } catch (ParseException e) {
            throw new DukeException("Your date cannot be parsed correctly");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Your recurring task is configured wrongly! Add date like /at dd/MM/yyyy HHmm");
        }
    }

    private String dateFormatHelper(Date date) {
        return new SimpleDateFormat("dd MMM yyyy").format(date);
    }
}
