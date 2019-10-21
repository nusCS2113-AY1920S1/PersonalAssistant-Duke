package optix.commands.parser;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class AddAliasCommand extends Command {
    private String newAlias;
    private String command;
    private HashMap<String, String> commandAliasMap;

    /**
     * Command to add a new alias to the command alias map.
     * @param alias alias to add
     * @param command command which the alias is to be paired to
     * @param commandAliasMap the command alias map
     */
    public AddAliasCommand(String alias, String command, HashMap<String, String> commandAliasMap) {
        this.newAlias = alias;
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
        commandAliasMap.put(this.newAlias, this.command);
        // open target file
        File currentDir = new File(System.getProperty("user.dir"));
        File filePath = new File(currentDir.toString() + "\\src\\main\\data\\ParserPreferences.txt");
        try {
            PrintWriter writer = new PrintWriter(filePath);
            for (Map.Entry<String, String> entry : commandAliasMap.entrySet()) {
                writer.write(entry.getKey() + "|" + entry.getValue() + '\n');
            }
            writer.close();
            String successMessage = String.format("Noted. The alias %s has been added to the command %s\n", this.newAlias, this.command);
            ui.setMessage(successMessage);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}

