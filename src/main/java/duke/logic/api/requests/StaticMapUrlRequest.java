package duke.logic.api.requests;

import duke.commons.exceptions.ApiException;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;

/**
 * Handles static, map URL requests to OneMap StaticMap API.
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
     * @throws ApiException If there is an issue with the request.
     */
    @Override
    public Image execute() throws ApiException {
        Image image;
        try {
            URL url = new URL(API_LINK + param);
            image = new Image(url.toExternalForm(), true);
        } catch (IOException e) {
            throw new ApiException();
        }

        return image;
    }
}
