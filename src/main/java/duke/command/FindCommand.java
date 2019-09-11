package duke.command;

import duke.DukeContext;

public class FindCommand extends ArgCommand {

    public FindCommand() {
        emptyArgMsg = "You didn't tell me what to look for!";
    }

    @Override
    public void execute(DukeContext ctx) {
       ctx.ui.print(ctx.taskList.find(arg));
    }
}
