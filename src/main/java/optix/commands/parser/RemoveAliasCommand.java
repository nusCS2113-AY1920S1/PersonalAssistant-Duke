package optix.commands.parser;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RemoveAliasCommand extends Command {
    private String alias;
    private String command;
    private HashMap<String, String> commandAliasMap;

    /**
     * Command to remove an existing alias from aliasCommandMap.
     * @param alias alias to remove
     * @param command command which the alias belongs to
     * @param commandAliasMap the command alias map
     */
    public RemoveAliasCommand(String alias, String command, HashMap<String, String> commandAliasMap) {
        this.alias = alias;
        this.command = command;
        this.commandAliasMap = commandAliasMap;
    }

    /**
     * Processes user input to be stored, queried, modified in ShowMap,
     * to show response by program in ui and store existing data in Storage.
     *
     * @param model   The data structure holding all the information.
     * @param ui      The User Interface that reads user input and response to user.
     * @param storage The filepath of txt file which data are being stored.
     */
    @Override
    public String execute(Model model, Ui ui, Storage storage) {
        // edit command alias map
        commandAliasMap.remove(this.alias, this.command);
        // open target file
        File currentDir = new File(System.getProperty("user.dir"));
        File filePath = new File(currentDir.toString() + "\\src\\main\\data\\ParserPreferences.txt");
        try {
            PrintWriter writer = new PrintWriter(filePath);
            for (Map.Entry<String, String> entry : commandAliasMap.entrySet()) {
                writer.write(entry.getKey() + "|" + entry.getValue() + '\n');
            }
            writer.close();
            String successMessage = String.format("Noted. The alias %s has been removed\n", this.alias);
            ui.setMessage(successMessage);
        } catch (IOException e) {
            ui.setMessage(e.getMessage());
        }
        return "";
    }
}
