package duke.commands;

import duke.DateFormatter;
import duke.constant.DukeResponse;
import duke.Storage;
import duke.task.DoAfter;
import duke.task.Task;
import duke.task.TaskList;
import duke.Ui;

public class DoAfterCommand extends Command{

    private String toDo;
    private String after;

    public DoAfterCommand(String description) {
        String[] split = description.split("/after");
        this.toDo = split[0];
        this.after = split[1];
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        Task newDoAfter;
        if (isDate(this.after)) {
            DateFormatter dateFormatter = new DateFormatter(this.after);
            newDoAfter = new DoAfter(this.toDo, dateFormatter.getDateTime());
        } else {
            newDoAfter = new DoAfter(this.toDo, this.after);
        }

//            Task newDoAfter = new DoAfter(this.toDo, this.after);
        tasks.add(newDoAfter);
        setResponse(ui, newDoAfter.toString(), tasks.size());
    }

    private Boolean isDate(String toTest) {
        String[] split = toTest.split("/");
        if (split.length == 3) {
            return true;
        }
        return false;
    }

    private void setResponse(Ui ui, String taskString, int size) {
        ui.setMessage(new DukeResponse().ADD + taskString + "\n"
                + "Now you have " + size + " tasks in your list.\n");
    }

}
