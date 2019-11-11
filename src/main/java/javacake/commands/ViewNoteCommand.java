package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.Storage;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import javacake.utilities.IFileUtilities;

import java.io.File;

public class ViewNoteCommand extends Command implements IFileUtilities {

    private String fileName;
    private static String defaultFilePath = Storage.returnNotesDefaultFilePath();
    private static final String BY_SPACES = "\\s+";

    /**
     * Constructor for ViewNoteCommand.
     * Validates the command by checking parameters.
     * Instantiates command object if validation passed.
     * @param inputCommand ViewNoteCommand by user.
     * @throws CakeException If command is not validated.
     */
    public ViewNoteCommand(String inputCommand) throws CakeException {
        validateCommand(inputCommand);
        type = CmdType.VIEW_NOTE;
        this.fileName = returnFileName(inputCommand);
    }

    /**
     * Returns the name of file after validation checks.
     * @param inputCommand View note command by user.
     * @return Name of file specified for viewing.
     */
    private String returnFileName(String inputCommand) throws CakeException {
        String[] parametersInCommand = inputCommand.split(BY_SPACES);
        String nameOfFile = IFileUtilities.returnOriginalFileName(defaultFilePath, parametersInCommand[1]);
        return nameOfFile;
    }

    /**
     * Checks the parameters of input by user.
     * Checks if user inputs file name.
     * Checks if command contains multiple parameters.
     * Checks if command contains special characters.
     * Checks if file exists in save directory.
     * Checks if file specified is empty.
     * @param inputCommand View note command by user.
     * @throws CakeException If one of the above conditions is met.
     */
    private void validateCommand(String inputCommand) throws CakeException {
        if (doesNotContainFileName(inputCommand)) {
            throw new CakeException("Please input the name of the file you wish to view!"
            + "E.g. viewnote [name of file]");
        } else if (containsMultipleParameters(inputCommand)) {
            throw new CakeException("Please only enter one file name! E.g. viewnote [name of file]");
        } else if (Command.containsIllegalCharacter(inputCommand)) {
            throw new CakeException("Invalid file name: Special character(s) in file name detected!");
        } else if (fileDoesNotExist(inputCommand)) {
            throw new CakeException("File specified does not exist! "
            + "Please refer to the notes window to view existing notes file!");
        } else if (fileIsEmpty(inputCommand)) {
            throw new CakeException("File specified is empty! "
            + "Use 'editnote [name of file]' to write new content!");
        }
    }

    /**
     * Checks if file specified by user is empty.
     * @param inputCommand View note command by user.
     * @return True if file specified by user is empty.
     */
    private boolean fileIsEmpty(String inputCommand) {
        String[] parametersInCommand = inputCommand.split(BY_SPACES);
        String fileName = parametersInCommand[1];
        String filePath = defaultFilePath + fileName + ".txt";
        File tempFile = new File(filePath);
        return (tempFile.length() == 0);
    }

    /**
     * Checks if file exists.
     * @param inputCommand View note command by user.
     * @return True if file does not exist.
     */
    private boolean fileDoesNotExist(String inputCommand) {
        String[] parametersInCommand = inputCommand.split(BY_SPACES);
        String fileName = parametersInCommand[1];
        String filePath = defaultFilePath + fileName + ".txt";
        File tempFile = new File(filePath);
        return !tempFile.exists();
    }

    /**
     * Checks if user inputs multiple parameters.
     * @param inputCommand View note command by user.
     * @return True if user inputs multiple parameters.
     */
    private boolean containsMultipleParameters(String inputCommand) {
        String[] parametersInCommand = inputCommand.split(BY_SPACES);
        return (parametersInCommand.length > 2);
    }

    /**
     * Checks if user inputs name of file to view.
     * @param inputCommand View note command by user.
     * @return True if user does not input name of file to view.
     */
    private boolean doesNotContainFileName(String inputCommand) {
        String[] parametersInCommand = inputCommand.split(BY_SPACES);
        return (parametersInCommand.length == 1);
    }

    /**
     * Returns the full path to the note file to be viewed.
     * @return Full path to the note file to be viewed.
     */
    private String generateCurrentFilePath() {
        return defaultFilePath + fileName + ".txt";
    }

    /**
     * Views the content of the note file specified by the user.
     * Allows user to view the content without having to commit to any editing.
     * @param logic tracks current location in program
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container.
     * @return Content in the file specified by the user.
     * @throws CakeException If file does not exist.
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        String filePath = generateCurrentFilePath();
        return "Below is the file [" + fileName + "] that you want to view!\n"
                + IFileUtilities.readFile(filePath);
    }
}
