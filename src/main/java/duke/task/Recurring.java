package duke.task;

import duke.command.AddCommand;
import duke.command.Command;
import duke.exception.DukeException;
import duke.storage.Storage;
import duke.tasklist.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Recurring extends Task {


    enum RecurrencePeriod {DAILY, WEEKLY}

    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    RecurrencePeriod recurrencePeriod;
    String description;
    String taskType;
    LocalDate dateCreated;

    public Recurring(String description, String taskType, TaskList tasks, Ui ui, Storage storage) {
        super(description);
        this.taskType = taskType;
        this.ui = ui;
        this.tasks = tasks;
        this.storage = storage;
        this.type = "[R]";

        this.dateCreated = LocalDate.now();
        String[] flagArray = description.split("-r ");
        this.description = flagArray[0];
        switch (flagArray[1]) {
            case "daily":
                recurrencePeriod = RecurrencePeriod.DAILY;
            case "weekly":
                recurrencePeriod = RecurrencePeriod.WEEKLY;
        }
        recurringTaskScheduler();
    }

    public RecurrencePeriod getRecurrencePeriod() {
        return recurrencePeriod;
    }

    public void recurringTaskScheduler() {
        switch (recurrencePeriod) {
            case DAILY:
                if (ChronoUnit.HOURS.between(dateCreated, LocalDate.now()) % 24 == 0) {
                    addRecurringTask();
                }
                break;
            case WEEKLY:
                if (ChronoUnit.DAYS.between(dateCreated, LocalDate.now()) % 7 == 0) {
                    addRecurringTask();
                }
                break;
        }
    }

    public void addRecurringTask() {
        try {
            Command c = new AddCommand(description, taskType);
            c.execute(tasks, ui, storage);
        } catch (ParseException e) {
            ui.showParsingError();
        } catch (DukeException | IOException e) {
            ui.showError(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return super.toString() + "";
    }
}
