package duke.logic;

import duke.command.Command;
import duke.commons.DukeException;
import duke.entities.recipe.Recipe;
import duke.parser.BakingHomeParser;
import duke.parser.exceptions.ParseException;
import duke.storage.BakingList;
import duke.storage.Storage;
import duke.storage.recipe.RecipeList;
import duke.ui.Ui;

public class Duke {

    private static final Storage STORAGE = new Storage("baking.json");
    private static BakingList bakingList = new BakingList();
    private static RecipeList recipeList = new RecipeList();
    private Ui ui;
    private CommandManager commandManager;

    public Duke(Ui ui) {
        this.ui = ui;
        try {
            bakingList = STORAGE.deserialize();
            //////////
            //For UI purpose, need to deserialize();
            Recipe recipe = new Recipe("");
            recipe.init();
            recipeList.add(recipe);
            /////////////
        } catch (DukeException e) {
            ui.showError(e.getMessage());
            ui.disableInput();
        }
        ui.initializePages();
        ui.refreshOrderList(bakingList.getOrderList(), bakingList.getOrderList());
        ui.showOrderPage();

        //////////
        ui.showRecipePage();
        //////////
        commandManager = new CommandManager(bakingList, STORAGE, ui);
    }

    public void executeInput(String input) {
        try {
            Command command = new BakingHomeParser().parseCommand(input);
            commandManager.execute(command);
        } catch (DukeException | ParseException e) {
            ui.showError(e.getMessage());
        }
    }
}
