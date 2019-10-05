package duke.parsers.requests;

import duke.commons.DukeException;

/**
 * Abstract class representing individual HTTP requests.
 */
public abstract class HttpRequest {
    private String reqType;
    private String url;
    private String param;

    /**
     * Initialises HTTP Request parameters.
     *
     * @param reqType The request type
     * @param url The request URL
     * @param param The parameters of the request
     */
    public HttpRequest(String reqType, String url, String param) {
        this.reqType = reqType;
        this.url = url;
        this.param = param;
    }

    /**
     * Executes the HTTP Request.
     */
    public abstract void execute() throws DukeException;
}
