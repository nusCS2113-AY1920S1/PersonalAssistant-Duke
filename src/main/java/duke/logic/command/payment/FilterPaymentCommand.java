package duke.logic.command.payment;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.logic.command.Command;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterPaymentCommand extends Command {

    private static final String name = "viewPayment";
    private static final String description = "View coming Payments in future";
    private static final String usage = "viewPayment $timeScope";

    private static final String OUF_OF_DATE_SCOPE = "outdated";
    private static final String WEEK_SCOPE = "week";
    private static final String MONTH_SCOPE = "month";
    private static final String ALL_SCOPE = "all";

    private static final String COMPLETE_MESSAGE = "Here are payments!";

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
     * Constructs a {@code FilterPaymentCommand} object
     * given the time scope of coming payments in future to be showed.
     * The time scope includes out of date, week, month and all.
     */
    public FilterPaymentCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        if(!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "timeScope"));
        }

        String timeScope = commandParams.getMainParam().toLowerCase();

        if (timeScope.equals(OUF_OF_DATE_SCOPE)) {
            model.setOutOfDatePredicate();
        } else if (timeScope.equals(WEEK_SCOPE)) {
            model.setWeekPredicate();
        } else if (timeScope.equals(MONTH_SCOPE)) {
            model.setMonthPredicate();
        } else if (timeScope.equals(ALL_SCOPE)) {
            model.setAllPredicate();
        } else {
            throw new DukeException(String.format(DukeException.MESSAGE_PAYMENT_SCOPE_INVALID, timeScope));
        }

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.PAYMENT);
    }

}
