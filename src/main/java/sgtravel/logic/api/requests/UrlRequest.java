package sgtravel.logic.api.requests;

import sgtravel.commons.exceptions.ApiException;

/**
 * Abstract class representing individual URL requests.
 */
public abstract class UrlRequest<T> {
    protected String param;

    /**
     * Constructs the UrlRequest.
     *
     * @param param The parameter for the request.
     */
    public UrlRequest(String param) {
        this.param = param;
    }

    /**
     * Executes and sends the given URL request.
     *
     * @return response The response from the request.
     * @exception ApiException If there is an issue with the request.
     */
    public abstract T execute() throws ApiException;
}
