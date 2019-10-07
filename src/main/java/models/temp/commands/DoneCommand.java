package models.temp.commands;

import models.temp.tasks.ITask;
import models.temp.tasks.TaskList;

public class DoneCommand implements ICommand {

    private String input;

    public DoneCommand(String input) {
        this.input = input;
    }

    @Override
    public void execute(TaskList taskList) {
        String[] allInputs = this.input.split(" ");
        for (String i : allInputs) {
            if (!i.equals("done")) {
                int index = Integer.parseInt(i) - 1;
                ITask chosenToDos = taskList.getTask(index);
                chosenToDos.markAsDone();
                System.out.println("[" + chosenToDos.getStatusIcon() + "] " + chosenToDos.getDescription());
            }
        }
    }
}
