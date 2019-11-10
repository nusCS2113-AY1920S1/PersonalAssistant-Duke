package duke.logic.command.payment;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.logic.command.Command;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Sets the sorting criteria of visible payments in payment reminder.
 * Sorting criteria include due, amount and priority.
 * The default sorting criteria is due.
 */
public class SortPaymentCommand extends Command {

    private static final String name = "sortPayment";
    private static final String description = "Sort Payments with given criteria";
    private static final String usage = "sortPayment $sortCriteria";

    private static final String COMPLETE_MESSAGE = "Payments are sorted!";
    private static final String EXCEPTION_WORD_SORTING_CRITERIA = "sorting criteria";

    /**
     * Contains all secondary parameters used by {@code SortPaymentCommand}.
     * Here the {@code SortPaymentCommand} does not demand secondary parameters.
     */
    private enum SecondaryParam {
        ;

        private String name;
        private String description;

        /**
         * Constructs a {@code SecondaryParam} with its name and usage.
         *
         * @param name        The name of the secondary parameter.
         * @param description The usage of this parameter.
         */
        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Creates a SortPaymentCommand, with its name, description, usage and secondary parameters.
     */
    public SortPaymentCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format
                    (DukeException.MESSAGE_COMMAND_PARAM_MISSING, EXCEPTION_WORD_SORTING_CRITERIA));
        }

        model.setPaymentSortingCriteria(commandParams.getMainParam());

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.PAYMENT);
    }
}
