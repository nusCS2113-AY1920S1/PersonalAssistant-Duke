package duke.exception;

/**
 * Exceptions which result from conditions external to Duke rendering it incapable of normal operations (e.g.
 * permissions issues, application code corruption, etc.)
 */
public class DukeFatalException extends DukeException {

    /**
     * Generates a DukeFatalException with the specified message and an additional hint that Duke is being exited from.
     *
     * @param msg Custom message explaining the problem which makes Duke unusable and suggesting actions to rectify it.
     */
    public DukeFatalException(String msg) {
        super(msg + " Exiting Duke now...");
    }
}
