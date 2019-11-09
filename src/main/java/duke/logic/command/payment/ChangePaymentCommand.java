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

public class ChangePaymentCommand extends Command {
    private static final String name = "changePayment";
    private static final String description = "Changes a pending payment";
    private static final String usage = "changePayment $index";

    private static final String COMPLETE_MESSAGE = "Changed the payment!";

    private enum SecondaryParam {
        DESCRIPTION("description", "a short description or name of the pending payment"),
        DUE("due", "the due date of affording the payment"),
        PRIORITY("priority", "the priority of the payment"),
        RECEIVER("receiver", "the receiver of the payment"),
        AMOUNT("amount", "the money of payment"),
        TAG("tag", "remark of the pending payment");

        private String name;
        private String description;

        SecondaryParam(String name, String description) {
            this.name = name;
            this.description = description;
        }
    }

    /**
     * Creates a ChangePaymentCommand, with its name, description, usage and secondary parameters.
     */
    public ChangePaymentCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description))
        );
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {
        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "index"));
        }

        int index;
        String mainParam = commandParams.getMainParam();
        try {
            index = Integer.parseInt(mainParam);
        } catch (NumberFormatException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_NUMBER_FORMAT_INVALID, mainParam));
        }

        Payment.Builder paymentBuilder = new Payment.Builder(model.getPayment(index));

        if (commandParams.containsParams(SecondaryParam.AMOUNT.name)) {
            paymentBuilder.setAmount(commandParams.getParam(SecondaryParam.AMOUNT.name));
        }

        if (commandParams.containsParams(SecondaryParam.DESCRIPTION.name)) {
            paymentBuilder.setDescription(commandParams.getParam(SecondaryParam.DESCRIPTION.name));
        }

        if (commandParams.containsParams(SecondaryParam.DUE.name)) {
            paymentBuilder.setDue(commandParams.getParam(SecondaryParam.DUE.name));
        }

        if (commandParams.containsParams(SecondaryParam.PRIORITY.name)) {
            paymentBuilder.setPriority(commandParams.getParam(SecondaryParam.PRIORITY.name));
        }

        if (commandParams.containsParams(SecondaryParam.RECEIVER.name)) {
            paymentBuilder.setReceiver(commandParams.getParam(SecondaryParam.RECEIVER.name));
        }

        if (commandParams.containsParams(SecondaryParam.TAG.name)) {
            paymentBuilder.setTag(commandParams.getParam(SecondaryParam.TAG.name));
        }

        model.setPayment(index, paymentBuilder.build());
        try {
            storage.savePaymentList(model.getPaymentList());
        } catch (IOException e) {
            throw new DukeException(DukeException.MESSAGE_PAYMENT_SAVE_FAILED);
        }

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.PAYMENT);
    }
}
