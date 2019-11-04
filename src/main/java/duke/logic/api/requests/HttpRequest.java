package duke.logic.api.requests;

import duke.commons.exceptions.ApiException;

/**
 * Abstract class handling individual HTTP requests.
 */
public abstract class HttpRequest<T> {
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
     * @return response The response from request
     */
    public abstract T execute() throws ApiException;
}