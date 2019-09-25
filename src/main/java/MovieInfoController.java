import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import movieRequesterAPI.RetrieveRequest;
import object.MovieInfoObject;

public class MovieInfoController extends Controller
{
    @FXML private Label movieTitleLabel;
    @FXML private Label movieGenresLabel;
    @FXML private Label movieReleaseDateLabel;
    @FXML private Label movieRatingLabel;
    @FXML private Label movieSummaryLabel;
    @FXML private ImageView movieBackdropImageView;
    @FXML private ScrollPane movieScrollPane;
    @FXML private VBox movieMainVBox;
    @FXML private GridPane movieGridPane;

    private MovieInfoObject mMovie;

    // Set the movie for this controller
    public void setMovie(MovieInfoObject movie)
    {
        mMovie = movie;
        initialize();
    }

    @FXML public void initialize()
    {
        // Load the movie info if movie has been set
        if (mMovie != null)
        {
            movieTitleLabel.setText(mMovie.getTitle());
            movieRatingLabel.setText(String.format("%.2f", mMovie.getRating()));

            if (mMovie.getReleaseDate() != null) {
                movieReleaseDateLabel.setText(String.format("%s", mMovie.getReleaseDate().toString()));
            }
            else{
                movieReleaseDateLabel.setText("N/A");
            }

            movieSummaryLabel.setText(mMovie.getSummary());
            loadMovieBackdrop();
            loadGenres();
        }

        movieMainVBox.prefWidthProperty().bind(movieScrollPane.widthProperty());
        movieBackdropImageView.setPreserveRatio(true);
        movieGridPane.prefWidthProperty().bind(movieScrollPane.widthProperty());
    }

    // User clicks on the back button to navigate back to the movies scene
    @FXML public void backToMoviesButtonClick()
    {
        mMainApplication.transitionBackToMoviesController();
    }

    // Loads the movie backdrop image asynchronously
    private void loadMovieBackdrop()
    {
        if (mMovie.getFullBackdropPath() != null){
            Image backdropImage = new Image(mMovie.getFullBackdropPath(), true);
            movieBackdropImageView.setImage(backdropImage);
        }
    }

    private void loadGenres()
    {
        movieGenresLabel.setText("");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] genres = RetrieveRequest.getGenreStrings(mMovie);
                StringBuilder builder = new StringBuilder();

                for (String genre : genres){
                    builder.append(genre);

                    // if not last string in array, append a ,
                    if (!genres[genres.length - 1].equals(genre)){
                        builder.append(", ");
                    }
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        movieGenresLabel.setText(builder.toString());
                    }
                });
            }
        });

        t.start();
    }
}