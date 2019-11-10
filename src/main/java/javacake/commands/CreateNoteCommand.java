package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.Storage;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.io.File;
import java.io.IOException;

public class CreateNoteCommand extends Command {

    private static String userGivenFileName = "Notes";
    private static int defaultFileNameCounter = 1;
    private static String defaultDirectoryPath = "data/notes/";
    private static final int MAXIMUM_PARAMETER = 2;
    private static final int MAX_FILENAME_LENGTH = 20;
    private static final String BY_SPACES = "\\s+";

    /**
     * Constructor for CreateNoteCommand.
     * @param inputCommand Input Command from the user to create note.
     * @throws CakeException If the input command is invalid.
     */
    public CreateNoteCommand(String inputCommand) throws CakeException {
        type = CmdType.CREATE_NOTE;
        updateDefaultDirectoryPath();
        checksValidityOfCommand(inputCommand);
    }

    /**
     * Updates default directory path according the storage.
     */
    private void updateDefaultDirectoryPath() {
        defaultDirectoryPath = Storage.returnNotesDefaultFilePath();

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
        String[] wordsInInputCommand = inputCommand.split(BY_SPACES);

        if (commandHasMultipleParameters(wordsInInputCommand)) {
            throw new CakeException("Additional Parameters in command detected!\n"
                    + "Please type 'createnote [desired file name]'\n"
                    + "Or simply 'createnote' for JavaCake for generate yummy file name for you!");
        } else if (createNoteCommandHasSpecifiedFileName(inputCommand)) {
            validateFileName(inputCommand);
            userGivenFileName = wordsInInputCommand[1];
        } else if (validCommandWithNoSpecifiedFileName(inputCommand)) {
            generateFileName();
        } else {
            throw new CakeException("Invalid command: To write notes, "
                    + "type 'createnote' followed by desired (optional) filename.");
        }
    }

    /**
     * Checks if create command contains additional parameters appended to command.
     * @param parameters Parameters within the createnote command.
     * @return True if there are multiple parameters.
     */
    private boolean commandHasMultipleParameters(String[] parameters) {
        return (parameters.length > MAXIMUM_PARAMETER);
    }

    /**
     * Checks the validity of file name.
     * Checks for illegal characters.
     * Checks for length of file name.
     * @param inputCommand CreateNoteCommand by user.
     * @throws CakeException If user fails the checks listed above.
     */
    private void validateFileName(String inputCommand) throws CakeException {
        String[] parameters = inputCommand.split(BY_SPACES);
        String fileName = parameters[1];
        if (Command.containsIllegalCharacter(inputCommand)) {
            throw new CakeException("Special Character(s) detected! Please use another file name!");
        } else if (fileNameExceedsWordLimit(fileName)) {
            throw new CakeException("File name exceeds word limit! Maximum length for file name is 20");
        }
    }

    /**
     * Checks if file name has length more than 20.
     * @param fileName Name of file from the user.
     * @return True if file name exceeds word limit of 20.
     */
    private boolean fileNameExceedsWordLimit(String fileName) {
        return (fileName.length() > MAX_FILENAME_LENGTH);
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
     * Checks if CreateNoteCommand has a specified file name.
     * @param inputCommand Input Command from the user to create note.
     * @return True if CreateNoteCommand has a specified file name.
     */
    private boolean createNoteCommandHasSpecifiedFileName(String inputCommand) {
        String[] wordsInInputCommand = inputCommand.split(BY_SPACES);
        return (wordsInInputCommand.length == 2);
    }

    /**
     * Checks if CreateNoteCommand with no parameter is valid.
     * @param inputCommand Input Command from the user to create note.
     * @return True if CreateNoteCommand with no parameter is valid.
     */
    private boolean validCommandWithNoSpecifiedFileName(String inputCommand) {
        String[] wordsInInputCommand = inputCommand.split(BY_SPACES);
        return (wordsInInputCommand.length == 1);
    }

    /**
     * Generates file name for user.
     * Generated by concatenating number to defaultFileName.
     */
    private void generateNewDefaultFileName() {
        String defaultFileName = "Notes";
        userGivenFileName = defaultFileName + defaultFileNameCounter;
        defaultFileNameCounter++;
    }

    /**
     * Checks if file name already exists.
     * If file name does not exist, create note for user.
     * @param logic tracks current location in program
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @return Message when note is created successfully.
     * @throws CakeException If file does not exists.
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        StringBuilder sb = new StringBuilder();
        sb.append(userGivenFileName).append(".txt");
        sb.insert(0, defaultDirectoryPath);
        String newFilePath = sb.toString();

        if (noteFileAlreadyExist(newFilePath)) {
            String errorMessage = "File already exists, please type 'editnote "
                    + userGivenFileName + "' to edit the file instead";
            throw new CakeException(errorMessage);
        } else {
            File file = new File(newFilePath);
            createFile(file, userGivenFileName);
            return "File [" + userGivenFileName + "] has been created successfully!\n";
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