package duke.logic.api.requests;

import duke.commons.Messages;
import duke.commons.exceptions.DukeApiException;
import javafx.scene.image.Image;
import java.io.IOException;
import java.net.URL;

public class StaticMapUrlRequest extends UrlRequest {
    private static final String URL = "https://developers.onemap.sg/commonapi/staticmap/getStaticImage?";

    /**
     * Construct the URL Request.
     * @param param The query
     */
    public StaticMapUrlRequest(String param) {
        super(param.replace(" ", "+"));
    }

    /**
     * Executes the URL request to StaticMap API.
     * @return image The static map image
     * @throws DukeApiException IO or null response exceptions
     */
    @Override
    public Image execute() throws DukeApiException {
        Image image;
        try {
            URL url = new URL(URL + param);
            image = new Image(url.toExternalForm());
            assert (image != null);
        } catch (IOException e) {
            throw new DukeApiException(Messages.DATA_NOT_FOUND);
        } catch (Throwable e) {
            throw new DukeApiException(Messages.DATA_NULL);
        }

        return image;
    }
}
