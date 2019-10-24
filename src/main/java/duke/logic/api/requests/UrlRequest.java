package duke.logic.api.requests;

import duke.commons.exceptions.ApiFailedRequestException;
import duke.commons.exceptions.ApiNullRequestException;
import duke.commons.exceptions.ApiTimeoutException;

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
     *
     * @return response The response from the request.
     * @exception ApiFailedRequestException If the request fails.
     * @exception ApiNullRequestException If the request does not return a valid result.
     * @exception ApiTimeoutException If the request times out.
     */
    public abstract T execute() throws ApiFailedRequestException, ApiNullRequestException, ApiTimeoutException;
}
