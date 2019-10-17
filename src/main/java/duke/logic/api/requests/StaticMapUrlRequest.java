package duke.logic.api.requests;

import duke.commons.Messages;
import duke.commons.exceptions.DukeException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class StaticMapUrlRequest extends UrlRequest {
    private static final String URL = "https://developers.onemap.sg/commonapi/staticmap/getStaticImage?";

    /**
     * Construct the URL Request.
     * @param param The query
     */
    public StaticMapUrlRequest(String url, String param) {
        super(param.replace(" ", "+"));
    }

    /**
     * Executes the URL request to StaticMap API.
     * @return image The static map image
     * @throws DukeException IO or null response exceptions
     */
    @Override
    public Image execute() throws DukeException {
        Image image;
        BufferedImage response;
        try {
            URL url = new URL(URL + param);
            response = ImageIO.read(url);
            image = SwingFXUtils.toFXImage(response, null);
            assert (image != null);
        } catch (IOException e) {
            throw new DukeException(Messages.DATA_NOT_FOUND);
        } catch (Throwable e) {
            throw new DukeException(Messages.DATA_NULL);
        }

        return image;
    }
}
