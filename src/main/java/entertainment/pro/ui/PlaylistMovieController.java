package entertainment.pro.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class PlaylistMovieController {
    @FXML
    private Label movieTitleLabel;
    @FXML
    private Label movieGenresLabel;
    @FXML
    private Label movieDateLabel;
    @FXML
    private Label movieRatingLabel;
    @FXML
    private Label movieSummaryLabel;
    @FXML
    private AnchorPane playlistMovieInfoAnchorPane;

    public Label getMovieTitleLabel() {
        return movieTitleLabel;
    }

    public void setMovieTitleLabel(Label movieTitleLabel) {
        this.movieTitleLabel = movieTitleLabel;
    }

    public Label getMovieGenresLabel() {
        return movieGenresLabel;
    }

    public void setMovieGenresLabel(Label movieGenresLabel) {
        this.movieGenresLabel = movieGenresLabel;
    }

    public Label getMovieDateLabel() {
        return movieDateLabel;
    }

    public void setMovieDateLabel(Label movieDateLabel) {
        this.movieDateLabel = movieDateLabel;
    }

    public Label getMovieRatingLabel() {
        return movieRatingLabel;
    }

    public void setMovieRatingLabel(Label movieRatingLabel) {
        this.movieRatingLabel = movieRatingLabel;
    }

    public Label getMovieSummaryLabel() {
        return movieSummaryLabel;
    }

    public void setMovieSummaryLabel(Label movieSummaryLabel) {
        this.movieSummaryLabel = movieSummaryLabel;
    }

    public AnchorPane getPlaylistMovieInfoAnchorPane() {
        return playlistMovieInfoAnchorPane;
    }

    public void setPlaylistMovieInfoAnchorPane(AnchorPane playlistMovieInfoAnchorPane) {
        this.playlistMovieInfoAnchorPane = playlistMovieInfoAnchorPane;
    }
}
