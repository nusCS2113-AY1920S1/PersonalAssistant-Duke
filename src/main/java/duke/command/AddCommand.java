package duke.command;

import duke.exception.DukeException;
import duke.extensions.AbnormalityChecker;
import duke.parser.DateTimeParser;
import duke.storage.Storage;
import duke.task.*;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * duke.command.AddCommand that deals with the adding of new duke.task.Task objects to the duke.tasklist.TaskList
 */
public class AddCommand extends Command {
    String description;
    String taskType;
    String recurrencePeriod;
    int duration;
    Optional<String> filter;
    Optional<LocalDateTime> dateTime;


    public AddCommand(String description, String taskType, Optional<String> filter) throws DukeException {
        this.taskType = taskType;
        this.filter = filter;
        this.recurrencePeriod = "none";
        this.duration = 0;
        this.dateTime = Optional.empty();
        this.description = description;
        checkForFlag();
    }


    private void checkForFlag() throws DukeException {
        String[] flagArray = description.split(" -");
        if (flagArray.length != 1) {
            for (int i = 1; i < flagArray.length; i++) {
                String temp = flagArray[i];
                char c = temp.charAt(0);
                String info = temp.substring(2);
                switch (c) {
                    case 'r':
                        if (info.endsWith("ly")) {
                            this.recurrencePeriod = info;
                        } else {
                            throw new DukeException("Please enter an acceptable recurrence period.");
                        }
                        break;
                    case 'd':
                        this.duration = Integer.parseInt(info);
                        break;
                    case 't':
                        this.dateTime = Optional.of(DateTimeParser.parseDateTime(info));
                }
            }
            this.description = flagArray[0];
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws IOException, DukeException {
        switch (taskType) {
            case "task":
                Task newTask = new Task(description, filter, recurrencePeriod, duration, dateTime);
                tasks.add(newTask);
                break;
            case "event":
                if (dateTime.isEmpty()) {
                    throw new DukeException("Your event needs to have a starting time.");
                }
                Event newEvent = new Event(description, filter, recurrencePeriod, duration, dateTime);
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

    @Override
    public boolean isExit() {
        return false;
    }
}