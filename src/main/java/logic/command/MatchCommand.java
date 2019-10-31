package logic.command;

import common.DukeException;
import model.Model;

public class MatchCommand extends Command {
    private String taskName;
    public MatchCommand(String name) {
        taskName = name;
    }

    @Override
    public CommandOutput execute(Model model) throws DukeException {
        return new CommandOutput("match command was called");
    }
}
