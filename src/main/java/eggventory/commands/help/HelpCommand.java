package eggventory.commands.help;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import eggventory.ui.Ui;
import eggventory.Storage;
import eggventory.StockList;
import eggventory.enums.CommandType;
import eggventory.commands.Command;
import eggventory.enums.CommandType;

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
    public String execute(StockList list, Ui ui, Storage storage) {
        String output = "";
        String filename;
        if (this.options == null) {
            filename = "\\src\\main\\java\\eggventory\\commands\\help\\Help.txt";
            try {
                Path filePath = Paths.get(System.getProperty("user.dir"), filename);
                output = Files.readString(filePath); //default UTF-8 charset.
                ui.print(output);
            } catch (IOException e) {
                ui.print("Error in reading help.txt");
            }
        } else {
            switch (this.options) {
            case "add":
                filename = "\\src\\main\\java\\eggventory\\commands\\help\\Helpadd.txt";
                try {
                    Path filePath = Paths.get(System.getProperty("user.dir"), filename);
                    output = Files.readString(filePath); //default UTF-8 charset.
                    ui.print(output);
                } catch (IOException e) {
                    ui.print("Error in reading Helpadd.txt");
                }
                break;
            case "edit":
                filename = "\\src\\main\\java\\eggventory\\commands\\help\\Helpedit.txt";
                try {
                    Path filePath = Paths.get(System.getProperty("user.dir"), filename);
                    output = Files.readString(filePath); //default UTF-8 charset.
                    ui.print(output);
                } catch (IOException e) {
                    ui.print("Error in reading Helpedit.txt");
                }
                break;
            case "delete":
                filename = "\\src\\main\\java\\eggventory\\commands\\help\\Helpdelete.txt";
                try {
                    Path filePath = Paths.get(System.getProperty("user.dir"), filename);
                    output = Files.readString(filePath); //default UTF-8 charset.
                    ui.print(output);
                } catch (IOException e) {
                    ui.print("Error in reading Helpdelete.txt");
                }
                break;
            default:
                ui.print("Your help command is not defined. Please enter 'help' for reference.");
            }
        }
        return output;
    }
}