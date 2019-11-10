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
 * Searches and lists all payments in payment reminder
 * whose description, receiver or tag contains the given keyword.
 * Keyword matching is case insensitive.
 */
public class SearchPaymentCommand extends Command {

    private static final String name = "searchPayment";
    private static final String description = "searches payment with given keywords";
    private static final String usage = "searchPayment $keyword";

    private static final String COMPLETE_MESSAGE = "Here are searching results!";
    private static final String EXCEPTION_WORD_KEYWORD = "keyword";

    /**
     * Contains all secondary parameters used by {@code SearchPaymentCommand}.
     * Here the {@code SearchPaymentCommand} does not demand secondary parameters.
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
     * Creates a SearchPaymentCommand, with its name, description, usage and secondary parameters.
     */
    public SearchPaymentCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format
                    (DukeException.MESSAGE_COMMAND_PARAM_MISSING, EXCEPTION_WORD_KEYWORD));
        }

        model.setSearchKeyword(commandParams.getMainParam());

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.PAYMENT);
    }
}
