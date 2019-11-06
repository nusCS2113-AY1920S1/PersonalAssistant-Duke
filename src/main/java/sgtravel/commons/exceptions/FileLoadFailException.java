package sgtravel.commons.exceptions;

import sgtravel.commons.Messages;

/**
 * Exception thrown when file cannot be loaded.
 */
public class FileLoadFailException extends DukeException {

    /**
     * Constructs the Exception.
     *
     * @param filePath The relative path of the file being loaded.
     */
    public FileLoadFailException(String filePath) {
        super(filePath + Messages.ERROR_FILE_NOT_FOUND);
    }
}
