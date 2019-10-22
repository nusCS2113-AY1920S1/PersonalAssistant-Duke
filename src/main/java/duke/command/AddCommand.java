package duke.command;

import duke.exception.DukeException;
import duke.extensions.AbnormalityChecker;
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


    public AddCommand(String description, String taskType, Optional<String> filter) throws DukeException {
        this.taskType = taskType;
        this.filter = filter;
        this.recurrencePeriod = "none";
        this.duration = 0;
        String[] flagArray = description.split(" -");
        if (flagArray.length != 1) {
            for (int i = 1; i < flagArray.length; i++) {
                String temp = flagArray[i];
                char c = temp.charAt(0);
                switch (c) {
                    case 'r':
                        String period = temp.substring(2);
                        if (period.endsWith("ly")) {
                            this.recurrencePeriod = period;
                        } else {
                            throw new DukeException("Please enter an acceptable recurrence period.");
                        }
                        break;
                    case 'd':
                        this.duration = Integer.parseInt(temp.substring(2));
                        break;
                }
            }
        }
        this.description = flagArray[0];
    }

    private void checkForFlag() {
        String[] flagArray = description.split(" -");
        if (flagArray.length != 1) {
            for (int i = 1; i < flagArray.length; i++) {
                System.out.println(flagArray[i]);
                switch (flagArray[i].charAt(0)) {
                    case 'r':
                        this.recurrencePeriod = flagArray[1].substring(2);
                        break;
                    case 'd':
                        this.duration = Integer.parseInt(flagArray[1].substring(2));
                        break;
                }
            }
        }
        this.description = flagArray[0];
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException, IOException {
        switch (taskType) {
            case "todo":
                tasks.add(new ToDo(description, filter, recurrencePeriod, duration));
                break;
            case "deadline":
                String[] dInfo = description.split(" /by ");
                LocalDateTime by;
                switch (dInfo[1]) {
                    case "today":
                        by = LocalDateTime.now();
                        break;
                    case "tomorrow":
                        by = LocalDateTime.now().plusDays(1);
                        break;
                    default:
                        String[] details = dInfo[1].split(" ", 2);
                        switch (details[0]) {
                            case "today":
                                by = LocalDateTime.of(LocalDate.now(),
                                        LocalTime.parse(details[1], DateTimeFormatter.ofPattern("HHmm")));
                                break;
                            case "tomorrow":
                                by = LocalDateTime.of(LocalDate.now().plusDays(1),
                                        LocalTime.parse(details[1], DateTimeFormatter.ofPattern("HHmm")));
                                break;
                            default:
                                by = convertToLocalDateTime(dInfo[1]);
                        }
                }
                tasks.add(new Deadline(dInfo[0], filter, recurrencePeriod, duration, by));
                break;
            case "event":
                String[] eInfo = description.split(" /at ");
                LocalDateTime at;
                switch (eInfo[1]) {
                    case "today":
                        at = LocalDateTime.now();
                        break;
                    case "tomorrow":
                        at = LocalDateTime.now().plusDays(1);
                        break;
                    default:
                        String[] details = eInfo[1].split(" ", 2);
                        switch (details[0]) {
                            case "today":
                                at = LocalDateTime.of(LocalDate.now(),
                                        LocalTime.parse(details[1], DateTimeFormatter.ofPattern("HHmm")));
                                break;
                            case "tomorrow":
                                at = LocalDateTime.of(LocalDate.now().plusDays(1),
                                        LocalTime.parse(details[1], DateTimeFormatter.ofPattern("HHmm")));
                                break;
                            default:
                                at = convertToLocalDateTime(eInfo[1]);
                        }
                }
                Event newEvent = new Event(eInfo[0], filter, recurrencePeriod, duration, at);
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

    public LocalDateTime convertToLocalDateTime(String rawDate) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("ddMMyy HHmm");
        LocalDateTime date = LocalDateTime.parse(rawDate, fmt);
        return date;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}