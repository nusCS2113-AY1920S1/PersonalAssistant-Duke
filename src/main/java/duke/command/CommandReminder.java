package duke.command;

import duke.task.TaskList;

public class CommandReminder extends Command {
    public CommandReminder() {
    }

    @Override
    public void execute(TaskList taskList) {
        try {
            for (int i = 0; i < taskList.getSize(); i++) {
                System.out.println(taskList.getList().get(i).genTaskDesc());
            }
        } catch (Exception e){
            System.out.println("sorry");
        }
    }
}
