package eggventory.commands.help;

import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import eggventory.ui.Cli;
import eggventory.Storage;
import eggventory.StockList;
import eggventory.enums.CommandType;
import eggventory.commands.Command;
import eggventory.enums.CommandType;

public class HelpCommand extends Command {
    private String options;

    public HelpCommand(CommandType type) {
        super(type);
        this.options = null;
    }

    public HelpCommand(CommandType type, String options) {
        super(type);
        this.options = options;
    }

    @Override
    public String execute(StockList list, Cli cli, Storage storage) {
        String output = "";
        String filename;
        if (this.options == null) {
            filename = "\\src\\main\\java\\eggventory\\commands\\help\\Help.txt";
            try {
                Path filePath = Paths.get(System.getProperty("user.dir"), filename);
                output = Files.readString(filePath); //default UTF-8 charset.
                cli.print(output);
            } catch (IOException e) {
                cli.print("Error in reading help.txt");
            }
        } else {
            switch (this.options) {
            case "add":
                filename = "\\src\\main\\java\\eggventory\\commands\\help\\Helpadd.txt";
                try {
                    Path filePath = Paths.get(System.getProperty("user.dir"), filename);
                    output = Files.readString(filePath); //default UTF-8 charset.
                    cli.print(output);
                } catch (IOException e) {
                    cli.print("Error in reading Helpadd.txt");
                }
                break;
            case "edit":
                filename = "\\src\\main\\java\\eggventory\\commands\\help\\Helpedit.txt";
                try {
                    Path filePath = Paths.get(System.getProperty("user.dir"), filename);
                    output = Files.readString(filePath); //default UTF-8 charset.
                    cli.print(output);
                } catch (IOException e) {
                    cli.print("Error in reading Helpedit.txt");
                }
                break;

            case "delete":
                filename = "\\src\\main\\java\\eggventory\\commands\\help\\Helpdelete.txt";
                try {
                    Path filePath = Paths.get(System.getProperty("user.dir"), filename);
                    output = Files.readString(filePath); //default UTF-8 charset.
                    cli.print(output);
                } catch (IOException e) {
                    cli.print("Error in reading Helpdelete.txt");
                }
                break;
            default:
                cli.print("Your help command is not defined. Please enter 'help' for reference.");
            }
        }
        return output;
    }
}