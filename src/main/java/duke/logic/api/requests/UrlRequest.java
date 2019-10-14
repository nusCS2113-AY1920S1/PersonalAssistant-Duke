package duke.logic.api.requests;

import com.google.gson.JsonObject;
import duke.commons.exceptions.DukeException;

/**
 * Abstract class representing individual URL requests.
 */
public abstract class UrlRequest {
    protected String url;
    protected String param;

    public UrlRequest(String url, String param) {
        this.url = url;
        this.param = param;
    }

    /**
     * Executes and sends the given URL request.
     */
    public abstract JsonObject execute() throws DukeException;
}
