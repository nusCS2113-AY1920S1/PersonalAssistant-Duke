package duke.command;

import duke.Duke;
import duke.exception.DukeException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ViewCommand extends Command {
    private static final String name = "view";
    private static final String description = "Change how expenses are displayed";
    private static final String usage = "view $criteria";

    private enum SecondaryParam {
        PREVIOUS("previous", "the number of pages to move back by");

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Constructs an {@code ViewCommand} object.
     */
    public ViewCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
            .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public void execute(CommandParams commandParams, Duke duke) throws DukeException {
        String mainParam = commandParams.getMainParam();
        if (!commandParams.containsParams("previous")) {
            duke.expenseList.setViewScope(mainParam, 0);
            return;
        }

        int previous;
        try {
            previous = Integer.parseInt(commandParams.getParam("previous"));
        } catch (NumberFormatException e) {
            throw new DukeException(String.format(
                DukeException.MESSAGE_EXPENSE_VIEW_SCOPE_NUMBER_INVALID,
                commandParams.getParam("previous")));
        }
        duke.expenseList.setViewScope(mainParam, previous);
    }

}
