package entertainment.pro.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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

    public Label getMovieReleaseDateLabel() {
        return movieReleaseDateLabel;
    }

    public void setMovieReleaseDateLabel(Label movieReleaseDateLabel) {
        this.movieReleaseDateLabel = movieReleaseDateLabel;
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

    public ImageView getMovieBackdropImageView() {
        return movieBackdropImageView;
    }

    public void setMovieBackdropImageView(ImageView movieBackdropImageView) {
        this.movieBackdropImageView = movieBackdropImageView;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public GridPane getMovieGridPane() {
        return movieGridPane;
    }

    public void setMovieGridPane(GridPane movieGridPane) {
        this.movieGridPane = movieGridPane;
    }

    public Label getMovieCastLabel() {
        return movieCastLabel;
    }

    public void setMovieCastLabel(Label movieCastLabel) {
        this.movieCastLabel = movieCastLabel;
    }

    public Label getMovieCertLabel() {
        return movieCertLabel;
    }

    public void setMovieCertLabel(Label movieCertLabel) {
        this.movieCertLabel = movieCertLabel;
    }
}
