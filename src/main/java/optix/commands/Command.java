package optix.commands;

import optix.commons.Model;
import optix.commons.Storage;
import optix.exceptions.OptixInvalidCommandException;
import optix.ui.Ui;

public abstract class Command {
    /**
     * Processes user input to be stored, queried, modified in ShowMap,
     * to show response by program in ui and store existing data in Storage.
     *
     * @param model   The data structure holding all the information.
     * @param ui      The User Interface that reads user input and response to user.
     * @param storage The filepath of txt file which data are being stored.
     */
    public abstract String execute(Model model, Ui ui, Storage storage);

    /**
     * Parses user input into its respective parameters.
     *
     * @param details User input command.
     * @return Array of string with respective parameters
     * @throws OptixInvalidCommandException The size of String array is not equals to expected
     *                                      number of parameters for the Command.
     */
    public abstract String[] parseDetails(String details) throws OptixInvalidCommandException;
}
