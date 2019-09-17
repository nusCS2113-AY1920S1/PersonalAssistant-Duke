package duke.command;

import duke.DukeContext;

public class FindCommand extends ArgCommand {
    public FindCommand() {
        emptyArgMsg = "You didn't tell me what to look for!";
    }

    @Override
    public void execute(DukeContext ctx) {
        String findStr = "Here are the tasks that contain '" + arg + "':";
        ctx.ui.print(findStr + ctx.taskList.find(arg));
    }
}
