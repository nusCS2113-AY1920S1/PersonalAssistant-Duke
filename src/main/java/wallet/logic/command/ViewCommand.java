//@@author matthewng1996

package wallet.logic.command;

import wallet.model.Wallet;
import wallet.model.record.Budget;
import wallet.ui.Ui;

import java.text.DateFormatSymbols;

public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_EMPTY_BUDGET = "There is no budget set for ";
    public static final String MESSAGE_VIEW_BUDGET = "This is the budget left for ";
    public static final String MESSAGE_USAGE = "Error in format for command."
            + "\nExample: " + COMMAND_WORD + " budget 01/2019";

    public String[] type;

    public ViewCommand(String... type) {
        this.type = type;
    }

    @Override
    public boolean execute(Wallet wallet) {
        Ui ui = new Ui();
        if (type[0].equals("pie")) {
            ui.drawPieChart();
            return false;
        } else if (type.length == 2) {
            if (type[0].equals("budget")) {
                String[] monthYear = type[1].split("/", 2);
                int month = Integer.parseInt(monthYear[0].trim());
                int year = Integer.parseInt(monthYear[1].trim());

                for (Budget b : wallet.getBudgetList().getBudgetList()) {
                    if (b.getMonth() == month && b.getYear() == year) {
                        System.out.println(MESSAGE_VIEW_BUDGET
                                + new DateFormatSymbols().getMonths()[b.getMonth() - 1] + " " + b.getYear());
                        System.out.println("$" + b.getAmount());
                        return false;
                    }
                }
                System.out.println(MESSAGE_EMPTY_BUDGET
                        +  new DateFormatSymbols().getMonths()[month - 1] + " " + year);
            }
        } else {
            System.out.println(MESSAGE_USAGE);
        }
        return false;
    }
}
