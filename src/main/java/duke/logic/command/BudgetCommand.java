package duke.logic.command;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.model.Model;
import duke.storage.Storage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BudgetCommand extends Command {
    private static final String name = "addBudget";
    private static final String description = "sets a budget";
    private static final String usage = "budget $amount";

    private static final String COMPLETE_MESSAGE = "Set the budget!";
    private static final String DUPLICATE_MESSAGE = "Updated the budget!";



    private enum SecondaryParam {
        TAG("tag", "tags that we want a budget to be associated with");

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    public BudgetCommand() {
        super(name, description, usage, Stream.of(BudgetCommand.SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "amount"));
        }
        try {
            BigDecimal amount = new BigDecimal(commandParams.getMainParam());
            BigDecimal scaledAmount = amount.setScale(2, RoundingMode.HALF_UP);
            if (commandParams.containsParams(SecondaryParam.TAG.name)) {
                String category = commandParams.getParam(SecondaryParam.TAG.name).toUpperCase();
                model.setCategoryBudget(category, amount);
            } else {
                model.setMonthlyBudget(scaledAmount);
            }
            storage.saveBudget(model.getBudget());

        } catch (NumberFormatException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_BUDGET_AMOUNT_INVALID,
                    commandParams.getMainParam()));
        }
        if (model.getBudgetCategory().containsKey(commandParams.getParam(SecondaryParam.TAG.name))) {
            return new CommandResult(DUPLICATE_MESSAGE, CommandResult.DisplayedPane.BUDGET);
        } else {
            return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.BUDGET);
        }
    }
}