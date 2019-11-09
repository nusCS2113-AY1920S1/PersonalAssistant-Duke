package duke.logic.command.payment;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.logic.command.Command;
import duke.model.Model;
import duke.model.payment.Payment;
import duke.storage.Storage;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddPaymentCommand extends Command {

    private static final String name = "addPayment";
    private static final String description = "Adds a new pending payment";
    private static final String usage = "addPayment $cost";

    private static final String COMPLETE_MESSAGE = "Added the payment!";

    private enum SecondaryParam {
        DESCRIPTION("description", "a short description or name of the pending payment"),
        DUE("due", "the due date of affording the payment"),
        PRIORITY("priority", "the priority of the payment"),
        RECEIVER("receiver", "the receiver of the payment"),
        TAG("tag", "tag of the pending payment");

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Creates a AddPaymentCommand, with its name, description, usage and secondary parameters.
     */
    public AddPaymentCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
                    .collect(Collectors.toMap(s -> s.name, s -> s.description))
        );
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        Payment.Builder paymentBuilder = new Payment.Builder();

        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "amount"));
        }

        paymentBuilder.setAmount(commandParams.getMainParam());

        if (!commandParams.containsParams(SecondaryParam.DESCRIPTION.name)) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "description"));
        }

        paymentBuilder.setDescription(commandParams.getParam(SecondaryParam.DESCRIPTION.name));

        if (!commandParams.containsParams(SecondaryParam.DUE.name)) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "due"));
        }

        paymentBuilder.setDue(commandParams.getParam(SecondaryParam.DUE.name));

        if (commandParams.containsParams(SecondaryParam.PRIORITY.name)) {
            paymentBuilder.setPriority(commandParams.getParam(SecondaryParam.PRIORITY.name));
        }

        if (commandParams.containsParams(SecondaryParam.RECEIVER.name)) {
            paymentBuilder.setReceiver(commandParams.getParam(SecondaryParam.RECEIVER.name));
        }

        if (commandParams.containsParams(SecondaryParam.TAG.name)) {
            paymentBuilder.setTag(commandParams.getParam(SecondaryParam.TAG.name));
        }

        model.addPayment(paymentBuilder.build());
        try {
            storage.savePaymentList(model.getPaymentList());
        } catch (IOException e) {
            throw new DukeException(DukeException.MESSAGE_PAYMENT_SAVE_FAILED);
        }
        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.PAYMENT);
    }
}
