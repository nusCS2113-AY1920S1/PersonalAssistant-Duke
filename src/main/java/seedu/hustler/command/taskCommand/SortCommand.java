package seedu.hustler.command.taskCommand;

import seedu.hustler.Hustler;
import seedu.hustler.command.Command;

public class SortCommand extends Command {
    private String sortType;

    public SortCommand(String rawInput) {
        this.sortType = rawInput.substring(1);
    }

    @Override
    public void execute() {
        Hustler.list.sortTask(sortType);
    }
}
