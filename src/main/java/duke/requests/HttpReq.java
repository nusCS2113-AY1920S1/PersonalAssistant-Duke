package duke.requests;

import duke.commons.DukeException;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Abstract class representing individual HTTP requests.
 */
public abstract class HttpReq {
    private String reqType;
    private String url;
    private String param;

    /**
     * Initialises HTTP Request parameters.
     * @param reqType The request type
     * @param url The request URL
     * @param param The parameters of the request
     */
    public HttpReq(String reqType, String url, String param) {
    }

    /**
     * Executes the HTTP Request.
     */
    public abstract void execute() throws DukeException, IOException;
}
