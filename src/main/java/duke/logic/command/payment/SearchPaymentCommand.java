package duke.logic.command.payment;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.logic.command.Command;
import duke.model.Model;
import duke.storage.Storage;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchPaymentCommand extends Command {

    private static final String name = "searchPayment";
    private static final String description = "searches payment with given keywords";
    private static final String usage = "searchPayment $keyword";

    private static final String COMPLETE_MESSAGE = "Here are searching results!";

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
     * Constructs a {@code SearchPaymentCommand} object
     * given the keyword of the payment to be searched.
     * The searching scope includes description, receiver and remark of payments.
     */
    public SearchPaymentCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "keyword"));
        }

        model.setSearchKeyword(commandParams.getMainParam());

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.PAYMENT);
    }
}
