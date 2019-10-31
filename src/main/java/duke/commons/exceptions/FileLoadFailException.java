package duke.commons.exceptions;

import duke.commons.Messages;

import java.io.File;

/**
 * Exception thrown when file cannot be loaded.
 */
public class FileLoadFailException extends DukeException {
    private File file;

    /**
     * Constructs the Exception.
     *
     * @param file The file being loaded.
     */
    public FileLoadFailException(File file) {
        super(Messages.ERROR_FILE_NOT_FOUND);
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
