package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when file cannot be loaded.
 */
public class FileLoadFailException extends DukeException {
    private String file;

    /**
     * Constructs the Exception.
     *
     * @param file The file being loaded.
     */
    public FileLoadFailException(String file) {
        super(Messages.ERROR_FILE_NOT_FOUND);
        this.file = file;
    }

    public String getFile() {
        return file;
    }
}
