package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when file is not found.
 */
public class StorageFileNotFoundException extends DukeException {
    private String fileName;

    /**
     * Constructs the Exception.
     */
    public StorageFileNotFoundException(String fileName) {
        super(Messages.ERROR_FILE_NOT_FOUND + "(" + fileName + ")");
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
