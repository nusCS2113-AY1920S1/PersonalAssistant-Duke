package duke.logic.command.payment;

import duke.exception.DukeException;
import duke.logic.CommandParams;
import duke.logic.CommandResult;
import duke.logic.command.AddExpenseCommand;
import duke.logic.command.Command;
import duke.model.Expense;
import duke.model.Income;
import duke.model.Model;
import duke.model.payment.Payment;
import duke.storage.Storage;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DonePaymentCommand extends Command {

    private static final String name = "donePayment";
    private static final String description = "Finish a Payment and add to expenseList";
    private static final String usage = "donePayment $index";

    private static final String COMPLETE_MESSAGE = "Finished the payment!";

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
     * Constructs a {@code DeletePaymentCommand} object
     * given the index of the payment to be deleted.
     */
    public DonePaymentCommand() {
        super(name, description, usage, Stream.of(SecondaryParam.values())
                .collect(Collectors.toMap(s -> s.name, s -> s.description)));
    }

    @Override
    public CommandResult execute(CommandParams commandParams, Model model, Storage storage) throws DukeException {

        if (!commandParams.containsMainParam()) {
            throw new DukeException(String.format(DukeException.MESSAGE_COMMAND_PARAM_MISSING, "index"));
        }

        String mainParam = commandParams.getMainParam();
        int targetIndex;
        try {
            targetIndex = Integer.parseInt(mainParam);
        } catch (NumberFormatException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_NUMBER_FORMAT_INVALID, mainParam));
        }

        Payment payment = model.getPayment(targetIndex);

        Expense.Builder expenseBuilder = new Expense.Builder(payment);

        model.addExpense(expenseBuilder.build());
        storage.saveExpenseList(model.getExpenseList());

        model.removePayment(targetIndex);
        try {
            storage.savePaymentList(model.getPaymentList());
        } catch (IOException e) {
            throw new DukeException(DukeException.MESSAGE_PAYMENT_SAVE_FAILED);
        }

        return new CommandResult(COMPLETE_MESSAGE, CommandResult.DisplayedPane.PAYMENT);
    }


}
