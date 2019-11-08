package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.Storage;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.io.File;

public class DeleteNoteCommand extends Command {

    private String fileName;
    private static String fullFilePath;

    private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t',
        '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':', '.', ','};


    /**
     * Constructor for DeleteNoteCommand.
     * Command used to delete a specific existing notes.
     * @param inputCommand Command input from the user.
     * @throws CakeException If user does not input parameter.
     */
    public DeleteNoteCommand(String inputCommand) throws CakeException {
        validateFileName(inputCommand);
        type = CmdType.DELETE_NOTE;
        this.fileName = returnFileName(inputCommand);
    }

    /**
     * Returns the name of file after validation checks.
     * @param inputCommand Delete note command by user.
     * @return Name of file specified for deletion.
     */
    private String returnFileName(String inputCommand) {
        String bySpaces = "\\s+";
        String[] parametersInCommand = inputCommand.split(bySpaces);
        return parametersInCommand[1];
    }

    /**
     * Executes the deleting process.
     * Verifies if the file specified by the user has been deleted.
     * @param logic TaskList containing current tasks.
     * @param ui the Ui responsible for outputting messages.
     * @param storageManager storage container.
     * @return Notification messages depending delete outcome.
     * @throws CakeException If file does not exist.
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        try {
            File tempFile = new File(fullFilePath);
            if (tempFile.delete()) {
                return fileName + ".txt has been deleted successfully!";
            } else {
                return "Unable to delete " + fileName + ".txt";
            }
        } catch (Exception e) {
            throw new CakeException(e.getMessage());
        }
    }

    /**
     * Method used for verification process.
     * Checks for illegal characters and if file exists.
     * Assigns inputFileName to fileName to prepare for deletion.
     * @param inputCommand Input command by user.
     * @throws CakeException If illegal character or invalid file name detected.
     */
    private void validateFileName(String inputCommand) throws CakeException {
        String bySpaces = "\\s+";
        String[] parameters = inputCommand.split(bySpaces);

        if (hasNoFileName(parameters)) {
            throw new CakeException("Please indicate the file name you wish to delete");
        } else if (hasMultipleParams(parameters)) {
            throw new CakeException("Please only enter one file name! E.g. deletenote [name of file]");
        } else if (hasIllegalCharacters(parameters[1])) {
            throw new CakeException("Invalid file name: Illegal character in file name detected!");
        } else if (fileDoesNotExist(parameters[1])) {
            throw new CakeException("Invalid file name: No such file!");
        }
    }

    /**
     * Checks if delete note command contains more than one parameter.
     * @param parameters Array of parameters on top of delete note command keyword.
     * @return True if there is more than 2 parameters in command.
     */
    private boolean hasMultipleParams(String[] parameters) {
        return (parameters.length > 2);
    }

    /**
     * Checks if parameter is specified in the delete note command.
     * @param parameters Array of parameters on top of delete note command keyword.
     * @return True if there is no parameter.
     */
    private boolean hasNoFileName(String[] parameters) {
        return (parameters.length == 1);
    }

    /**
     * Method to generate full file path to the file to be deleted.
     * Concatenates the desired file name with the default path to notes.
     * @param inputFileName name of file user wants to delete.
     * @return Full file path to the file to be deleted.
     */
    private String processFilePath(String inputFileName) {
        String filePath = updateDefaultDirectoryPath();
        fullFilePath = filePath + inputFileName + ".txt" + "/";
        return fullFilePath;
    }

    /**
     * Updates default directory path according the storage.
     * @return updated default file path.
     */
    private String updateDefaultDirectoryPath() {
        return Storage.returnNotesDefaultFilePath();
    }

    /**
     * Checks if the input file name contains any illegal characters.
     * @param inputFileName Specified file name by user.
     * @return True if file name contains illegal characters.
     */
    public static boolean hasIllegalCharacters(String inputFileName) {
        for (char illegalChar : ILLEGAL_CHARACTERS) {
            if (containsIllegal(inputFileName, illegalChar)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if file name contains illegal characters.
     * @param inputFileName Name of input file.
     * @param illegalChar Characters that are not allowed in file name.
     * @return True if file name contains illegal character.
     */
    private static boolean containsIllegal(String inputFileName, char illegalChar) {
        return inputFileName.indexOf(illegalChar) >= 0;
    }

    /**
     * Checks if file exists in the note storage.
     * @param inputFileName name of file user wants to delete.
     * @return True if file does not exist.
     */
    private boolean fileDoesNotExist(String inputFileName) {
        String fullFilePath = processFilePath(inputFileName);
        File tempFile = new File(fullFilePath);
        return (!tempFile.exists());
    }

}
