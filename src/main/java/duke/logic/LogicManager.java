package duke.logic;

import duke.exception.DukeException;
import duke.logic.command.Command;
import duke.model.Expense;
import duke.model.Model;
import duke.storage.Storage;
import javafx.collections.ObservableList;

public class LogicManager implements Logic {

    private Model model;
    private Storage storage;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

    @Override
    public CommandResult execute(String userInput) throws DukeException {
        CommandResult commandResult;
        CommandParams commandParams = new CommandParams(userInput);
        Command command = commandParams.getCommand();
        commandResult = command.execute(commandParams, model, storage);

        return commandResult;
    }

    @Override
    public ObservableList<Expense> getExternalExpenseList() {
        return model.getExpenseExternalList();
    }

}
