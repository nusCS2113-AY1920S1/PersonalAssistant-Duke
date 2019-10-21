package optix.commands.parser;

import optix.commands.Command;
import optix.commons.Model;
import optix.commons.Storage;
import optix.ui.Ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ResetAliasCommand extends Command {

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
        // open target file
        File currentDir = new File(System.getProperty("user.dir"));
        File filePath = new File(currentDir.toString() + "\\src\\main\\data\\ParserPreferences.txt");
        try {
            PrintWriter pw = new PrintWriter(filePath);
            pw.close();
            String systemMessage = "Alias settings have been reset to default.\n";
            ui.setMessage(systemMessage);
        } catch (FileNotFoundException e) {
            ui.setMessage(e.getMessage());
        }
        return "";
    }
}

