package trial; /**
 * File: trial.MoviePosterController.java
 * Description: Simple controller for setting and getting the controls in the Movie Poster View
 * Author: Asad Ahmed
 */

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MoviePosterController
{
    @FXML
    private Label movieTitleLabel;

    public Label getMovieID() {
        return movieID;
    }

    public void setMovieID(Label movieID) {
        this.movieID = movieID;
    }

    public ImageView getMoviePosterImageView() {
        return moviePosterImageView;
    }

    public void setMoviePosterImageView(ImageView moviePosterImageView) {
        this.moviePosterImageView = moviePosterImageView;
    }

    @FXML
    private Label movieID;

    @FXML
    private ImageView moviePosterImageView;

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
}