package duke.logic.api.requests;

import duke.commons.exceptions.ApiFailedRequestException;
import duke.commons.exceptions.ApiNullRequestException;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;

/**
 * URL request to OneMap StaticMap API to get image of location.
 */
public class StaticMapUrlRequest extends UrlRequest {
    private static final String API_LINK = "https://developers.onemap.sg/commonapi/staticmap/getStaticImage?";

    /**
     * Construct the URL Request.
     *
     * @param param The location query.
     */
    public StaticMapUrlRequest(String param) {
        super(param.replace(" ", "+"));
    }

    /**
     * Executes the URL request to StaticMap API.
     *
     * @return image The static map image
     * @throws ApiFailedRequestException If request fails.
     * @throws ApiNullRequestException If request gives no valid results.
     */
    @Override
    public Image execute() throws ApiFailedRequestException, ApiNullRequestException {
        Image image;
        try {
            URL url = new URL(API_LINK + param);
            image = new Image(url.toExternalForm(), true);
        } catch (IOException e) {
            throw new ApiFailedRequestException();
        }

        if (image != null) {
            return image;
        }
        throw new ApiNullRequestException();
    }
}
