package cube.model.exception;

import cube.exception.CubeException;

public class ModelException extends CubeException {
    /**
     * Default constructor.
     */
    public ModelException() {
        super();
    }

    /**
     * Constructor with one argument.
     * Constructs the exception with message.
     *
     * @param message the message to be printed when exception happens.
     */
    public ModelException(String message) {
        super(message);
    }
}
