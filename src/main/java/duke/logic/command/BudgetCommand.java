package duke.logic.command;
/*
import duke.Duke;
import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BudgetCommand extends Command {
    private static final String name = "budget";
    private static final String description = "sets a budget";
    private static final String usage = "budget $amount";

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
    public CommandResult execute(CommandParams commandParams, Duke duke) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "amount"));
        }
        try {
            BigDecimal amount = new BigDecimal(commandParams.getMainParam());
            BigDecimal scaledAmount = amount.setScale(2, RoundingMode.HALF_UP);
            if (commandParams.containsParams(SecondaryParam.TAG.name)) {
                String category = commandParams.getParam(SecondaryParam.TAG.name);
                duke.budget.setCategoryBudget(category, amount);
            }
            duke.budget.setMonthlyBudget(scaledAmount);
            duke.budget.save();
        } catch (NumberFormatException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_BUDGET_AMOUNT_INVALID,
                commandParams.getMainParam()));
        }

    }

}
*/