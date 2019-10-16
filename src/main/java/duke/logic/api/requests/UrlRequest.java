package duke.logic.api.requests;

import com.google.gson.JsonObject;
import duke.commons.exceptions.DukeException;

/**
 * Abstract class representing individual URL requests.
 */
public abstract class UrlRequest<T> {
    protected String param;

    public UrlRequest(String param) {
        this.param = param;
    }

    /**
     * Executes and sends the given URL request.
     * @return response The response from the request
     */
    public abstract T execute() throws DukeException;
}
