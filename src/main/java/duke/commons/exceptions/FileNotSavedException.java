package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when file is not saved.
 */
public class FileNotSavedException extends DukeException {
    private String fileName;

    /**
     * Constructs the Exception.
     */
    public FileNotSavedException(String fileName) {
        super(Messages.ERROR_FILE_NOT_SAVED + "(" + fileName + ")");
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
