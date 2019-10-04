package duke.logic;

import duke.logic.command.commons.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;
import duke.model.ModelManager;
import duke.parser.BakingHomeParser;
import duke.storage.Storage;
import duke.ui.Ui;

//import duke.storage.BakingList;

public class Duke {

    private static final Storage STORAGE = new Storage();
    //private static BakingList bakingList = new BakingList();
    private Ui ui;
    //private CommandManager commandManager;

    public Duke(Ui ui) {
        Model model = new ModelManager();
        BakingHomeParser parser = new BakingHomeParser();
        Logic logic = new LogicManager(model, STORAGE, parser);
        try {
            CommandResult commandResult = logic.execute("order add -name jj");
            System.out.println(commandResult.getFeedbackToUser());
            System.out.println(model.getFilteredOrderList().get(0));
        } catch (CommandException e) {
            e.printStackTrace();
        }

//        this.ui = ui;
//        try {
//            bakingList = STORAGE.deserialize();
//        } catch (DukeException e) {
//            ui.showError(e.getMessage());
//            ui.disableInput();
//        }
//        ui.initializePages();
//        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
//        ui.showOrderPage();
//
//        //////////
//        ui.showRecipePage();
//        //////////
//        commandManager = new CommandManager(bakingList, STORAGE, ui);
    }

    public void executeInput(String input) {
//        try {
//            Command command = new BakingHomeParser().parseCommand(input);
//            commandManager.execute(command);
//        } catch (DukeException | ParseException e) {
//            ui.showError(e.getMessage());
//        }
    }
}
