package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.Storage;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.io.File;
import java.io.IOException;

public class CreateNoteCommand extends Command {

    private String defaultFileName = "Notes";
    private static String userGivenFileName = "Notes";
    private static int defaultFileNameCounter = 1;
    private String defaultDirectoryPath = "data/notes/";

    private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t',
        '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':', '.', ','};

    /**
     * Constructor for CreateNoteCommand.
     * @param inputCommand Input Command from the user to create note.
     * @throws CakeException If the input command is invalid.
     */
    public CreateNoteCommand(String inputCommand) throws CakeException {
        type = CmdType.CREATENOTE;
        Storage.generateFolder(new File("data/notes/"));
        checksValidityOfCommand(inputCommand);
    }

    /**
     * Creates a file based on auto-generated file name, if no parameter is given.
     * Checks if auto-generated file name exist.
     * Concatenates defaultFileName with number if file name already exists.
     * Checks if specified file name is valid if inputCommand has a parameter.
     * Creates file if specified file name is valid.
     * @param inputCommand Input Command from the user to create note.
     * @throws CakeException If the input command is invalid.
     */
    private void checksValidityOfCommand(String inputCommand) throws CakeException {
        String bySpaces = "\\s+";
        String[] wordsInInputCommand = inputCommand.split(bySpaces);

        if (createNoteCommandHasSpecifiedFileName(inputCommand)) {
            String userSpecifiedFileName = wordsInInputCommand[1];
            checksForIllegalCharacters(userSpecifiedFileName);
        } else if (validCommandWithNoSpecifiedFileName(inputCommand)) {
            generateFileName();
        } else {
            throw new CakeException("Invalid command: To write notes, "
                    + "type 'createnote' followed by desired (optional) filename.");
        }
    }

    /**
     * Generates new unique file name to create new file.
     */
    private void generateFileName() {
        while (noteFileAlreadyExist(defaultDirectoryPath + userGivenFileName + ".txt")) {
            generateNewDefaultFileName();
        }
    }

    /**
     * Checks if file name contains illegal characters.
     * @param fileName Name of the file.
     * @throws CakeException If file name contains illegal characters.
     */
    private void checksForIllegalCharacters(String fileName) throws CakeException {
        if (noIllegalCharacters(fileName)) {
            userGivenFileName = fileName;
        } else {
            throw new CakeException("Invalid file name: Illegal character in file name detected!");
        }
    }

    /**
     * Checks if CreateNoteCommand has a specified file name.
     * @param inputCommand Input Command from the user to create note.
     * @return True if CreateNoteCommand has a specified file name.
     */
    private boolean createNoteCommandHasSpecifiedFileName(String inputCommand) {
        String bySpaces = "\\s+";
        String[] wordsInInputCommand = inputCommand.split(bySpaces);
        if (wordsInInputCommand.length == 2) {
            return true;
        }
        return false;
    }

    /**
     * Checks if CreateNoteCommand with no parameter is valid.
     * @param inputCommand Input Command from the user to create note.
     * @return True if CreateNoteCommand with no parameter is valid.
     */
    private boolean validCommandWithNoSpecifiedFileName(String inputCommand) {
        String[] wordsInInputCommand = inputCommand.split("\\s+");

        if (wordsInInputCommand.length == 1) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the input file name contains any illegal characters.
     * @param inputFileName Specified file name by user.
     * @return True if file name does not contains illegal characters.
     */
    public static boolean noIllegalCharacters(String inputFileName) {
        for (char illegalChar : ILLEGAL_CHARACTERS) {
            if (containsIllegal(inputFileName, illegalChar)) {
                return false;
            }
        }
        return true;
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
     * Generates file name for user.
     * Generated by concatenating number to defaultFileName.
     */
    private void generateNewDefaultFileName() {
        userGivenFileName = defaultFileName + defaultFileNameCounter;
        defaultFileNameCounter++;
    }

    /**
     * Checks if file name already exists.
     * If file name does not exist, create note for user.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @return Message when note is created successfully.
     * @throws CakeException If file does not exists.
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        StringBuilder sb = new StringBuilder();
        sb.append(userGivenFileName).append(".txt");
        String formattedFileName = sb.toString();
        sb.insert(0, defaultDirectoryPath);
        String newFilePath = sb.toString();

        if (noteFileAlreadyExist(newFilePath)) {
            String errorMessage = "File already exists, please type 'editnote "
                    + formattedFileName + "' to edit the file instead";
            throw new CakeException(errorMessage);
        } else {
            File file = new File(newFilePath);
            createFile(file, formattedFileName);
            return "File '" + formattedFileName + "'has been created successfully!\n";
        }

    }

    /**
     * Creates file using given file name.
     * Verifies if file creation is successful.
     * @param file File to be created as note.
     * @param formattedFileName Formatted file name used for new file.
     * @throws CakeException If file creation is unsuccessful.
     */
    private void createFile(File file, String formattedFileName) throws CakeException {
        try {
            if (!file.createNewFile()) {
                throw new CakeException("File '" + formattedFileName + "' was not created! Pls try again!");
            }
        } catch (IOException e) {
            throw new CakeException(e.getMessage());
        }
    }

    /**
     * Checks if file specified in file path exists.
     * @param filePath Path to the file to be created.
     * @return True if file already exists.
     */
    private boolean noteFileAlreadyExist(String filePath) {
        File tempFile = new File(filePath);
        return tempFile.exists();
    }
}