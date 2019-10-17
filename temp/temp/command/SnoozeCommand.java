package duke.command;

import duke.exceptions.DukeException;
import duke.parser.DateTimeRecognition;
import duke.storage.FileHandling;
import duke.tasks.Deadline;
import duke.tasks.Event;
import duke.tasks.Task;
import duke.tasks.TaskList;
import duke.ui.Ui;

import java.util.Arrays;
import java.util.List;

public class SnoozeCommand extends Command {
    private List<String> splitInput;
    private String inputTask;

    public SnoozeCommand(List<String> splitInput, String inputTask) {
        this.splitInput = splitInput;
        this.inputTask = inputTask;
    }

    private void updateSnooze(TaskList tasks, Ui ui, FileHandling storage,Task task,
                              int num, String split1) throws DukeException {

        if (task.toString().contains("[E]")) {
            try {
                List<String> splitDate = Arrays.asList(split1.trim().split(" to "));
                DateTimeRecognition from = new DateTimeRecognition(splitDate.get(0));
                DateTimeRecognition to = new DateTimeRecognition(splitDate.get(1));
                tasks.deleteTask(num - 1);
                tasks.changeTask(new Event(task.description, split1.trim()),num - 1);
                storage.saveData(tasks);
                ui.printSnoozeTask(tasks.getTask(tasks.numTasks() - 1).toString(), "E", inputTask);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DukeException(" Format for an event is: <event> /at"
                        + "<data and time> to <date and time>");
            }
        } else if (task.toString().contains("[D]")) {
            DateTimeRecognition convertDate = new DateTimeRecognition(split1.trim());
            tasks.deleteTask(num - 1);
            tasks.changeTask(new Deadline(task.description, split1.trim()),num - 1);
            storage.saveData(tasks);
            ui.printSnoozeTask(tasks.getTask(tasks.numTasks() - 1).toString(), "D", inputTask);
        } else {
            throw new DukeException(" You can only snooze/postpone/reschedule an event or a deadline task!");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, FileHandling storage)throws DukeException {
        int i;
        int k = 0;
        int num = 0;
        String split1 = "";
        if (splitInput.size() == 1) {
            throw new DukeException(" OOPS! instructions to " + inputTask + " a task cannot be empty");
        }
        for (i = 2; i < splitInput.size(); i++) {
            if (splitInput.get(i).equals("/to")) {
                k = 1;
            } else {
                split1 += splitInput.get(i) + " ";
            }
        }
        if (k == 0) {
            throw new DukeException(" Please make sure you have used \"/to\" to separate"
                    + " task and snoozed/postponed/rescheduled time");
        } else if (split1.trim().length() == 0) {
            throw new DukeException(" Please enter the new time frame");
        }

        try {
            num = Integer.parseInt(splitInput.get(1));
            Task task = tasks.getTask(num - 1);
            updateSnooze(tasks, ui, storage, task, num, split1);
        } catch (NumberFormatException obj) {
            throw new DukeException(" OOPS! Enter a positive integer after \"" + inputTask + "\"");
        } catch (IndexOutOfBoundsException obj) {
            throw new DukeException(" OOPS! Enter a number that is present in the list");
        }
    }
}