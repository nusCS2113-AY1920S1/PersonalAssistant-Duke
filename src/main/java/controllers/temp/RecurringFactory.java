package controllers.temp;

import exceptions.DukeException;
import models.tasks.Recurring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RecurringFactory {
    /**
     * Method to create a new Recurring Task.
     *
     * @param input : Input from user
     * @return : Returns a Recurring data model
     * @throws DukeException : throws Exception when parsing date
     */
    public Recurring createTask(String input) throws DukeException {
        String[] allArgs = input.split(" ");
        List<String> listArgs = new ArrayList<>(Arrays.asList(allArgs));
        listArgs.remove(0);
        String tempString = String.join(" ", listArgs);
        String[] parsedStrings = tempString.split(" /at ");

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date date = formatter.parse(parsedStrings[1]);
            String hour = new SimpleDateFormat("hh").format(date);
            String min = new SimpleDateFormat("mm").format(date);
            String periodMarker = new SimpleDateFormat("a").format(date);
            String formattedTime = hour + "." + min + " " + periodMarker;
            String formattedDate = new SimpleDateFormat("d MMMMM yyyy").format(date) + " " + formattedTime;
            return new Recurring(parsedStrings[0], formattedDate, date);
        } catch (ParseException e) {
            throw new DukeException("Your date cannot be parsed correctly");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("Your recurring task is configured wrongly! Add date like /at dd/MM/yyyy HHmm");
        }
    }
}
