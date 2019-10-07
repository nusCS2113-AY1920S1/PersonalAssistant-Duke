package duke.parsers.requests;

import com.google.gson.JsonObject;
import duke.commons.DukeException;

import java.io.IOException;

/**
 * Abstract class representing individual HTTP requests.
 */
public abstract class HttpRequest {
    protected String reqType;
    protected String url;
    protected String param;

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
     * @return
     */
    public abstract JsonObject execute() throws DukeException, IOException;
}
