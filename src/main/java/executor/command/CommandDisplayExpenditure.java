package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

import java.text.DecimalFormat;

public class CommandDisplayExpenditure extends Command {

    public CommandDisplayExpenditure() {
    }

    @Override
    public void execute(TaskList taskList) {
    }

    @Override
    public void execute(Wallet wallet) {
        double totalExpenses = wallet.getTotalExpenses();
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        Ui.dukeSays("Total Expenditure: $"
                + decimalFormat.format(totalExpenses)
        );
    }
}
