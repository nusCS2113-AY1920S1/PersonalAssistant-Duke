package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when file cannot be read.
 */
public class CorruptedFileException extends DukeException {
    private String fileType;

    /**
     * Constructs the Exception.
     *
     * @param fileType The file being loaded.
     */
    public CorruptedFileException(String fileType) {
        super(Messages.ERROR_DATA_CORRUPTED);
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    /**
     * Gets the error message depending on the file specified in constructor.
     *
     * @return The error message.
     */
    public String getErrorMessage() {
        switch (fileType) {
        case "ROUTE":
            return Messages.ERROR_ROUTE_CORRUPTED;
        case "ROUTE_NODE":
            return Messages.ERROR_ROUTE_NODE_CORRUPTED;
        case "TASK":
            return Messages.ERROR_TASK_CORRUPTED;
        default:
            return Messages.ERROR_DATA_CORRUPTED;
        }
    }
}
