package duke.logic.command.payment;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.logic.command.Command;
import duke.model.Model;
import duke.storage.Storage;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Deletes a payment identified using it's displayed index from the payments reminder.
 */
public class DeletePaymentCommand extends Command {

    private static final String name = "deletePayment";
    private static final String description = "Deletes a Payment";
    private static final String usage = "deletePayment $index";

    private static final String COMPLETE_MESSAGE = "Deleted the payment!";
    private static final String EXCEPTION_WORD_INDEX = "index";

    /**
     * Contains all secondary parameters used by {@code DeletePaymentCommand}.
     * Here the {@code DeletePaymentCommand} does not demand secondary parameters.
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
     * Creates a DeletePaymentCommand, with its name, description, usage and secondary parameters.
     */
    public DeletePaymentCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {

        if (!commandParams.containsMainParam()) {
            throw new DukeException(
                    String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, EXCEPTION_WORD_INDEX));
        }

        String mainParam = commandParams.getMainParam();
        try {
            model.removePayment(Integer.parseInt(mainParam));
        } catch (NumberFormatException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_NUMBER_FORMAT_INVALID, mainParam));
        }

        try {
            storage.savePaymentList(model.getPaymentList());
        } catch (IOException e) {
            throw new DukeException(DukeException.MESSAGE_PAYMENT_SAVE_FAILED);
        }

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.PAYMENT);
    }
}
