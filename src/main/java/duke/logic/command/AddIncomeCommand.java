package duke.logic.command;

import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.exception.DukeException;
import duke.model.Income;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a specified command as AddCommand by extending the {@code Command} class.
 * Adds various specified incomeList into the IncomeList
 * Responses with the result.
 */
public class AddIncomeCommand extends Command {
    private static final String name = "addIncome";
    private static final String description = "Adds a new Income";
    private static final String usage = "add $income";

    private static final String COMPLETE_MESSAGE = "Added the income!";

    private enum SecondaryParam {
        DESCRIPTION("description", "a short description or name for source of the income");

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Creates a new command object, with its name, description, usage and secondary parameters.
     */
    public AddIncomeCommand() {
        super(name,
                description,
                usage,
                Stream.of(SecondaryParam.values())
                        .collect(Collectors.toMap(s -> s.name, s -> s.description))
        );
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        Income.Builder incomeBuilder = new Income.Builder();

        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "amount of income"));
        }

        if (!commandParams.containsParams(SecondaryParam.DESCRIPTION.name)) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "source of income"));

        }
        incomeBuilder.setAmount(commandParams.getMainParam());
        incomeBuilder.setDescription(commandParams.getParam(SecondaryParam.DESCRIPTION.name));

        model.addIncome(incomeBuilder.build());
        storage.saveIncomeList(model.getIncomeList());

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.BUDGET);
    }

}
