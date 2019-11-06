package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when file is not saved.
 */
public class FileNotSavedException extends DukeException {

    /**
     * Constructs the Exception.
     */
    public FileNotSavedException(String fileName) {
        super(Messages.ERROR_FILE_NOT_SAVED + "(" + fileName + ")");
    }
}
