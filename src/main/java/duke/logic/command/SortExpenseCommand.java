package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SortExpenseCommand extends Command {
    private static final String name = "sortExpense";
    private static final String description = "Sort expenses according to a given criteria";
    private static final String usage = "sort $criteria";

    private static final String COMPLETE_MESSAGE = "Sorted the expense!";

    private enum SecondaryParam {
        ;

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Constructs an {@code SortCommand} object.
     */
    public SortExpenseCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        String mainParam = commandParams.getMainParam();
        model.sortExpense(mainParam);

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.EXPENSE);
    }

}
