package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.task.BudgetList;

import duke.dukeexception.DukeException;
import java.io.IOException;

public class ViewBudgetCommand extends Command {

    protected Ui ui = new Ui();

    public ViewBudgetCommand(BudgetList budgetList) {
        ui.showBudget(budgetList.getBudget());
    }

    @Override
    public void execute(TaskList items, Ui ui) {
    }

    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {

    }
}
