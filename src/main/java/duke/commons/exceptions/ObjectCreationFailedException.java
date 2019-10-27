package duke.commons.exceptions;

import duke.commons.Messages;

/**
 * Exception thrown when index query is out of bounds.
 */
public class ObjectCreationFailedException extends DukeException {
    private String objectName;

    /**
     * Constructs the Exception.
     */
    public ObjectCreationFailedException(String queriedItem) {
        super(Messages.ERROR_OBJECT_NOT_CREATED + " (" + queriedItem + ")");
        this.objectName = objectName;
    }

    public String getObjectName() {
        return objectName;
    }
}
