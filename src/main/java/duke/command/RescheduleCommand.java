package duke.command;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class RescheduleCommand extends Command {
    private final LocalDateTime rescheduleDate;
    private final int taskNumber;
    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    public RescheduleCommand(String data) throws DukeException {
        String trimmedData = data.trim();
        if (trimmedData.isEmpty() || trimmedData.isBlank()){
            throw new DukeException("Reschedule inputs cannot be blank or space.");
        }
        String[] splitData = trimmedData.split(" ");
        if (splitData.length < 3){
            throw new DukeException("A task, date and time must be specified.");
        } else if (splitData.length > 3){
            throw new DukeException("Invalid input."
                    + " Reschedule format is \"reschedule <task number> <date> <time>\"");
        }
        String pattern = "^[0-9]+$";
        if(!splitData[0].matches(pattern)){
            throw new DukeException("The task number should be numeric only");
        } else {
            try {
                this.taskNumber = Integer.parseInt(splitData[0]);
            } catch (NumberFormatException exceptionMessage) {
                throw new DukeException("The number must be an integer and cannot exceed 9 digits");
            }
        }
        String date = splitData[1] + " " + splitData[2];
        this.rescheduleDate = parseRescheduledTime(date);
    }

    private static LocalDateTime parseRescheduledTime(String date) throws DukeException {
        String dateRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        String timeRegex = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]$";
        String[] dateTime = date.split(" ", 2);
        if (dateTime.length != 2) {
            throw new DukeException("The format is wrong, please try in DD/MM/YYYY HHMM format");
        }
        String dateOnly = dateTime[0].trim();
        String timeOnly = dateTime[1].trim();
        if (!dateOnly.matches(dateRegex)) {
            throw new DukeException("The date format is wrong, please try in DD/MM/YYYY format");
        }
        if (!timeOnly.matches(timeRegex)) {
            throw new DukeException("The time format is wrong, please try again in HHMM format");
        }
        try {
            return LocalDateTime.parse(date, inputFormatter);
        } catch (DateTimeParseException e) {
            throw new DukeException("Time must be in the format DD/MM/YYYY HHMM format");
        }
    }

    public void execute(TaskList tasks, Ui ui, Storage storage){
        if (tasks.size() < this.taskNumber){
            ui.printError("Invalid task number, there are " + tasks.size() + " tasks");
            ui.printError("You entered: " + this.taskNumber);
        } else {
            try {
                tasks.rescheduleTask(this.taskNumber-1,this.rescheduleDate);
                ui.printMessage("Noted. Your task now has the following details:");
                ui.printMessage(tasks.get(this.taskNumber).toString());
                try {
                    storage.writeFile(tasks.export());
                } catch (IOException e) {
                    ui.printError("Error writing tasks to file");
                }
            } catch (IndexOutOfBoundsException exceptionMessage){
                ui.printError("Invalid task number, there are " + tasks.size() + " tasks");
                ui.printError("You entered: " + this.taskNumber);
            } catch (DukeException exceptionMessage) {
                ui.printError(exceptionMessage.toString());
            }
        }
    }
}
