package duke.command;

import duke.extensions.AbnormalityChecker;
import duke.storage.Storage;
import duke.task.*;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * duke.command.AddCommand that deals with the adding of new duke.task.Task objects to the duke.tasklist.TaskList
 */
public class AddCommand extends Command {
    String description;
    String taskType;
    boolean isRecurring = false;
    boolean hasDuration = false;
    String recurrencePeriod;
    Optional<String> filter;


    public AddCommand(String description, String taskType, Optional<String> filter) {
        this.taskType = taskType;
        this.description = description;
        this.filter = filter;
        checkForFlag();
    }

    private void checkForFlag() {
        String[] flagArray = description.split("-");
        if (flagArray.length != 1) {
            switch (flagArray[1].charAt(0)) {
                case 'r':
                    isRecurring = true;
                    description = flagArray[0];
                    recurrencePeriod = flagArray[1].substring(2);
                    break;
                case 'd':
                    hasDuration = true;
            }
        }
    }


    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException, IOException {
        switch (taskType) {
            case "todo":
                if (hasDuration) {
                    String[] flagArray = description.split(" -", 2);
                    int duration = Integer.parseInt(flagArray[1].substring(2));
                    tasks.add(new FixedDurationTask(flagArray[0], filter, duration));
                } else if (isRecurring) {
                    tasks.add(new ToDo(description, filter, recurrencePeriod));
                } else {
                    tasks.add(new ToDo(description, filter));
                }
                break;
            case "deadline":
                String[] dInfo = description.split(" /by ");
                LocalDate by = convertToLocalDate(dInfo[1]);
                if (isRecurring) {
                    tasks.add(new Deadline(dInfo[0], filter, by, recurrencePeriod));
                } else {
                    tasks.add(new Deadline(dInfo[0], filter, by));
                }

                break;
            case "event":
                String[] eInfo = description.split(" /at ");
                LocalDate at = convertToLocalDate(eInfo[1]);
                Event newEvent;
                if (isRecurring) {
                    newEvent = new Event(eInfo[0], filter, at, recurrencePeriod);
                } else {
                    newEvent = new Event(eInfo[0], filter, at);
                }
                AbnormalityChecker abnormalityChecker = new AbnormalityChecker(tasks);
                if (abnormalityChecker.checkEventClash(newEvent)) {
                    System.out.println("There is a clash with another event at the same time");
                } else {
                    tasks.add(newEvent);
                }
                break;
        }
        storage.save(tasks);
    }

    public LocalDate convertToLocalDate(String rawDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("ddMMyyyy HHmm");
        LocalDate date = LocalDate.parse(rawDate, fmt);
        return date;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}