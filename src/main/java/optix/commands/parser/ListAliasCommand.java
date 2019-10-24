package optix.commands.parser;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;
import optix.util.Parser;

import java.util.Map;

public class ListAliasCommand extends Command {

    /**
     * Iterates through all entries in the commandAliasMap of a parser object.
     * Prints every key (alias) and value (command) pair.
     * to show response by program in ui and store existing data in Storage.
     *
     * @param model   The data structure holding all the information.
     * @param ui      The User Interface that reads user input and response to user.
     * @param storage The filepath of txt file which data are being stored.
     */
    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        StringBuilder systemMessage = new StringBuilder("Alias list: \n");
        for (Map.Entry<String, String> entry : Parser.commandAliasMap.entrySet()) {
            systemMessage.append(entry.getKey()).append(" : ").append(entry.getValue()).append('\n');
        }

        ui.setMessage(systemMessage.toString());
        return "seat";
    }

    /**
     * Dummy command.
     *
     * @param details n.a.
     * @return n.a.
     */
    @Override
    public String[] parseDetails(String details) {
        return new String[0];
    }
}

