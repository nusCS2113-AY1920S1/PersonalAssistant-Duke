package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.record.Expense;
import wallet.model.record.Loan;
import wallet.storage.StorageManager;
import wallet.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_VIEW_EXPENSES = "Here are your expenses:";
    public static final String MESSAGE_VIEW_LOANS = "Here are your loans:";
    public static final String MESSAGE_VIEW_EXPENSES_SPECIFIED = "Here are your expenses for ";
    public static final String MESSAGE_VIEW_LOANS_SPECIFIED = "Here are your loans for ";
    public static final String MESSAGE_EMPTY_LOANS = "There are no loans";
    public static final String MESSAGE_EMPTY_EXPENSES = "There are no expenses";
    public static final String MESSAGE_USAGE = "Error in format for command."
            + "\nExample: " + COMMAND_WORD + " all"
            + "\nExample: " + COMMAND_WORD + " 01/01/2019";

    private final String type;

    public ViewCommand(String type) {
        this.type = type;
    }

    @Override
    public boolean execute(Wallet wallet, StorageManager storageManager) {
        Ui ui = new Ui();
        if (type.equals("all")) {
            if (wallet.getExpenseList().getSize() != 0) {
                System.out.println(MESSAGE_VIEW_EXPENSES);
                for (Expense e : wallet.getExpenseList().getExpenseList()) {
                    System.out.println(e.toString());
                }
                ui.printLine();
            }
            else {
                System.out.println(MESSAGE_EMPTY_EXPENSES);
            }

            if (wallet.getLoanList().getSize() != 0) {
                System.out.println(MESSAGE_VIEW_LOANS);
                ui.printLine();
            }
            else {
                System.out.println(MESSAGE_EMPTY_LOANS);
            }
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(type.trim(), formatter);

        if (!date.equals(null)) {
            System.out.println(date);
            ui.printLine();
            System.out.println(MESSAGE_VIEW_EXPENSES_SPECIFIED + date);
            for (Expense e : wallet.getExpenseList().getExpenseList()) {
                if (e.getDate().equals(date)) {
                    System.out.println(e.toString());
                }
            }
            ui.printLine();

            System.out.println(MESSAGE_VIEW_LOANS_SPECIFIED + date);
            for (Loan l : wallet.getLoanList().getLoanList()) {
                if (l.getDate().equals(date)) {
                    System.out.println(l.toString());
                }
            }

        } else {
            System.out.println(MESSAGE_USAGE);
        }
        return false;
    }
}
