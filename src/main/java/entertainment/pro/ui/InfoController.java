package entertainment.pro.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * This class is
 */
public class InfoController {
    @FXML
    private Label movieTitleLabel;
    @FXML
    private Label movieGenresLabel;
    @FXML
    private Label movieReleaseDateLabel;
    @FXML
    private Label movieRatingLabel;
    @FXML
    private Label movieSummaryLabel;
    @FXML
    private ImageView movieBackdropImageView;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane movieGridPane;
    @FXML
    private Label movieCastLabel;
    @FXML
    private Label movieCertLabel;

    /**
     * Responsible for returning the title label.
     * @return the title label.
     */
    public Label getMovieTitleLabel() {
        return movieTitleLabel;
    }

    /**
     * Responsible for returning the genres label.
     * @return the genres label.
     */
    public Label getMovieGenresLabel() {
        return movieGenresLabel;
    }


    /**
     * Responsible for returning the release date label.
     * @return the release date label.
     */
    public Label getMovieReleaseDateLabel() {
        return movieReleaseDateLabel;
    }


    /**
     * Responsible for returning the rating label.
     * @return the rating label.
     */
    public Label getMovieRatingLabel() {
        return movieRatingLabel;
    }

    /**
     * Responsible for returning the summary label.
     * @return the summary label.
     */
    public Label getMovieSummaryLabel() {
        return movieSummaryLabel;
    }

    /**
     * Responsible for returning the backdrop poster image.
     * @return the backdrop poster image.
     */
    public ImageView getMovieBackdropImageView() {
        return movieBackdropImageView;
    }

    /**
     * Responsible for returning the cast label.
     * @return the cast label.
     */
    public Label getMovieCastLabel() {
        return movieCastLabel;
    }


    /**
     * Responsible for returning the certification label.
     * @return the certification label.
     */
    public Label getMovieCertLabel() {
        return movieCertLabel;
    }

}
