package entertainment.pro.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * Controller to get and set parameters in the MoviePoster View.
 */
public class MoviePosterController {
    @FXML
    private Label movieTitleLabel;

    @FXML
    private ImageView moviePosterImageView;

    @FXML
    private Label movieNumberLabel;

    public Label getMovieTitleLabel() {
        return movieTitleLabel;
    }

    public void setMovieTitleLabel(Label mMovieTitleLabel) {
        mMovieTitleLabel = mMovieTitleLabel;
    }

    public ImageView getPosterImageView() {
        return moviePosterImageView;
    }

    public void setPosterImageView(ImageView mPosterImageView) {
        mPosterImageView = mPosterImageView;
    }

    public Label getMovieNumberLabel() {
        return movieNumberLabel;
    }
}