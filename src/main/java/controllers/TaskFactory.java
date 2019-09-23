package controllers;

import exceptions.DukeException;
import exceptions.InvalidDateTimeException;
import exceptions.NoTaskDetailsException;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import models.tasks.Deadline;
import models.tasks.Event;
import models.tasks.ITask;
import models.tasks.ToDos;

import java.awt.image.ImagingOpException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskFactory {
    private String taskName;
    private String inputDateTime;

    /**
     * Constructor for the TaskFactory class.
     */
    public TaskFactory() {
        this.taskName = "";
        this.inputDateTime = "";
    }

    /**
     * Factory class responsible for creation of objects based on interface.
     *
     * @param input : Command typed into CLI.
     * @return : returns an models.tasks.ITask based on command typed into CLI.
     * @throws DukeException : when command entered does not match existing Tasks.
     */
    public ITask createTask(String input) throws DukeException {
        Scanner scanner = new Scanner(input);
        String taskType = scanner.next();
        String taskDetails;
        try {
            taskDetails = scanner.nextLine();
        } catch (NoSuchElementException e) {
            throw new NoTaskDetailsException(taskType);
        }

        switch (taskType) {
        case "todo":
            String[] parsedStrings = taskDetails.split("/in");
            if (parsedStrings.length == 1) {
                return new ToDos(parsedStrings[0].trim());
            } else {
                return new ToDos(parsedStrings[0].trim(), parsedStrings[1].trim());
            }
        case "deadline":
            splitInput(taskDetails, "/by");
            try {
                Date dateTimeObject = getTaskDateTime(this.inputDateTime);
                String dateTimeString = getTaskDateTimeString(dateTimeObject);
                return new Deadline(this.taskName, dateTimeString, dateTimeObject);
            } catch (ParseException e) {
                throw new InvalidDateTimeException();
            } catch (ImagingOpException e) {
                throw new DukeException("OOPS! Please remember your /by flag!");
            }
        case "event":
            splitInput(taskDetails, "/at");
            try {
                Date dateTimeObject = getTaskDateTime(this.inputDateTime);
                String dateTimeString = getTaskDateTimeString(dateTimeObject);
                return new Event(this.taskName, dateTimeString, dateTimeObject);
            } catch (ParseException e) {
                throw new InvalidDateTimeException();
            } catch (ImagingOpException e) {
                throw new DukeException("OOPS! Please remember your /at flag!");
            }
        default:
            throw new DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }

    /**
     * Splits the input for deadline and event into task name and date/time.
     * @param input The full user command to add task to the list.
     * @param delimiter "/by" or "/at" to split the input, depending on task type.
     */
    private void splitInput(String input, String delimiter) {
        String[] parsedStrings = input.split(delimiter);
        this.taskName = parsedStrings[0].trim();
        this.inputDateTime = parsedStrings[1].trim();
    }

    /**
     * Returns a date object based on the user input in the format: dd/MM/yyyy HHmm.
     * @param inputDateTime The user's input date, in the format dd/MM/yyyy HHmm.
     * @return A date object corresponding to the user input.
     * @throws ParseException If the date is not entered in the stipulated format
     */
    private Date getTaskDateTime(String inputDateTime) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HHmm");
        Date date = formatter.parse(inputDateTime);
        return date;
    }

    /**
     * Returns the date as a String in the format: dd MMMMM yyyy hh.mm a.
     * @param date The date object.
     * @return The date as a String  in the format: dd MMMMM yyyy hh.mm a.
     */
    private String getTaskDateTimeString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMMM yyyy hh.mm a");
        return formatter.format(date);
    }
}
