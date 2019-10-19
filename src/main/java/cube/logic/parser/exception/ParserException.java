package cube.logic.parser.exception;

import cube.exception.CubeException;

public class ParserException extends CubeException {
    /**
     * Default constructor.
     */
    public ParserException() {
        super();
    }

    /**
     * Constructor with one argument.
     * Constructs the exception with message.
     *
     * @param message the message to be printed when exception happens.
     */
    public ParserException(String message) {
        super(message);
    }
}
