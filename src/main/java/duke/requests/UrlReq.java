package duke.requests;

import com.google.gson.JsonObject;
import duke.commons.DukeException;

import java.io.IOException;

/**
 * Abstract class representing individual URL requests.
 */
public abstract class UrlReq {
    protected String url;
    protected String param;

    public UrlReq(String url, String param) {
        this.url = url;
        this.param = param;
    }

    /**
     * Executes and sends the given URL request.
     */
    public abstract JsonObject execute() throws DukeException, IOException;
}
