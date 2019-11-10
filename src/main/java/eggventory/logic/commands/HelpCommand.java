package eggventory.logic.commands;

import eggventory.commons.enums.CommandType;
import eggventory.model.StockList;
import eggventory.storage.Storage;
import eggventory.ui.Ui;
import eggventory.commons.exceptions.BadInputException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//@@author yanprosobo

//@@author yanprosobo
/**
 * Command object for all help command.
 */
public class HelpCommand extends Command {
    private String options;

    /**
     * Constructor for command: "help".
     * @param type The type of the command
     */
    public HelpCommand(CommandType type) {
        super(type);
        this.options = null;
    }

    /**
     * Constructor for command: "help xyz" where xyz are first parsed.
     * @param type The type of the command
     * @param options The command syntax for which detailed help is required
     */
    public HelpCommand(CommandType type, String options) {
        super(type);
        this.options = options;
    }

    /**
     * Execute the help prompt given to user when user request for help.
     * @param list The inventory currently managed by the system
     * @param ui Ui implementation to display help to
     * @param storage Storage object which handles saving and loading of data
     * @return The string passed to Ui for parsing.
     */
    @Override
    public String execute(StockList list, Ui ui, Storage storage) throws BadInputException {
        String output = "";
        if (this.options == null) {
            try {
                output = getStringFromFile("/help/Help.txt");
            } catch (IOException e) {
                output = "Error in reading Help.txt";
            }
        } else {
            switch (this.options) {
            case "add":
                try {
                    output = getStringFromFile("/help/HelpAdd.txt");
                } catch (IOException e) {
                    output = "Error in reading HelpAdd.txt";
                }
                break;
            case "edit":
                try {
                    output = getStringFromFile("/help/HelpEdit.txt");
                } catch (IOException e) {
                    output = "Error in reading HelpEdit.txt";
                }
                break;
            case "delete":
                try {
                    output = getStringFromFile("/help/HelpDelete.txt");
                } catch (IOException e) {
                    output = "Error in reading HelpDelete.txt";
                }
                break;
            case "list":
                try {
                    output = getStringFromFile("/help/HelpList.txt");
                } catch (IOException e) {
                    output = "Error in reading HelpList.txt";
                }
                break;
            case "find":
                try {
                    output = getStringFromFile("/help/HelpFind.txt");
                } catch (IOException e) {
                    output = "Error in reading HelpFind.txt";
                }
                break;
            case "bye":
                try {
                    output = getStringFromFile("/help/HelpBye.txt");
                } catch (IOException e) {
                    output = "Error in reading HelpBye.txt";
                }
                break;
            default:
                throw new BadInputException("Your help command is not defined. Please enter 'help' for reference.");
            }
        }
        ui.print(output);
        return output;
    }

    /**
     * Reads help files from the resource folder of the JAR and returns the files as
     * a String.
     * @param resourcePath Path to file in resources folder.
     * @return String format of entire file.
     * @throws IOException when file cannot be read for some unknown error.
     */
    private String getStringFromFile(String resourcePath) throws IOException {
        InputStream is = getClass().getResourceAsStream(resourcePath);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        StringBuffer sb = new StringBuffer();
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }

            sb.append(line).append("\n");
        }

        br.close();
        isr.close();
        is.close();

        return sb.toString();
    }
}
//@@author