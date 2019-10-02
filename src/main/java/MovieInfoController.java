import EPstorage.Commands;
import EPstorage.ProfileStorage;
import EPstorage.UserProfile;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import movieRequesterAPI.RequestListener;
import movieRequesterAPI.RetrieveRequest;
import object.MovieInfoObject;
import ui.Ui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MovieInfoController extends Controller implements RequestListener {

    @FXML private Label movieTitleLabel;
    @FXML private Label movieGenresLabel;
    @FXML private Label movieReleaseDateLabel;
    @FXML private Label movieRatingLabel;
    @FXML private Label movieSummaryLabel;
    @FXML private ImageView movieBackdropImageView;
    @FXML private ScrollPane movieScrollPane;
    @FXML private VBox movieMainVBox;
    @FXML private GridPane movieGridPane;
    @FXML private TextField mSearchTextField;
    @FXML private Label mStatusLabel;
    @FXML private ProgressBar mProgressBar;
    private FlowPane mMoviesFlowPane;

    private RetrieveRequest mMovieRequest = new RetrieveRequest(this);
    private MovieHandler movieHandler = new MovieHandler();
    private ArrayList<MovieInfoObject> mMovies;
    private double[] mImagesLoadingProgress;

    private MovieInfoObject mMovie;

    @FXML Label userNameLabel;
    @FXML Label userAgeLabel;
    @FXML Label genreListLabel;
    private ProfileStorage profileStorage;
    private UserProfile userProfile;

    // Set the movie for this controller
    public void setMovie(MovieInfoObject movie) throws IOException {
        mMovie = movie;
        initialize();
    }

    @FXML public void initialize() throws IOException {

        profileStorage = new ProfileStorage();
        userProfile = profileStorage.load();
        Commands command = new Commands();
        userNameLabel.setText(userProfile.getUserName());
        userAgeLabel.setText(Integer.toString(userProfile.getUserAge()));
        genreListLabel.setText(command.convertToLabel(userProfile.getGenreId()));


        //mMovieRequest = new RetrieveRequest(this);
        // Load the movie info if movie has been set
        if (mMovie != null) {
            movieTitleLabel.setText(mMovie.getTitle());
            movieRatingLabel.setText(String.format("%.2f", mMovie.getRating()));

            if (mMovie.getReleaseDate() != null) {
                movieReleaseDateLabel.setText(String.format("%s", mMovie.getReleaseDate().toString()));
            } else{
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

    private void clearText(TextField textField){
        textField.setText("");
    }

    @FXML private void searchButtonClicked() throws IOException {
        String userInput = mSearchTextField.getText();
        //for setting up profile preferences
        String[] tokens = userInput.split((" "), 3);
        if (tokens.length == 3) {
            Commands command = new Commands();
            if (tokens[0].equals("set") && tokens[1].equals("name")) {
                command.setName(tokens[2]);
                clearText(mSearchTextField);
                initialize();
            } else if (tokens[0].equals("set") && tokens[1].equals("age")) {
                command.setAge(tokens[2]);
                clearText(mSearchTextField);
                initialize();
            } else if (tokens[0].equals("set") && tokens[1].equals("preference")) {
                command.setPreference(tokens[2]);
                clearText(mSearchTextField);
                initialize();
            }
        }
        //for searching movies
        else if (userInput.equals("show current movie")) {
            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.NOW_SHOWING);
        } else if (userInput.equals("show upcoming movie")) {
            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.UPCOMING);
        } else if (userInput.equals("show popular movie")) {
            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.POPULAR);
        } else if (userInput.equals("show current tv")) {
            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.TV_SHOWS);
        } else if (!userInput.isEmpty()) {
            mMovieRequest.beginSearchRequest(userInput);
        }
    }


    // Menu item events
    @FXML public void exitMenuItemClicked()
    {
        System.exit(0);
    }

    @FXML public void aboutMenuItemClicked()
    {
    }

    // Called when the fetch request for the movie data is completed
    @Override
    public void requestCompleted(ArrayList<MovieInfoObject> moviesInfo)
    {
        // Build the Movie poster views and add to the flow pane on the main thread
        mMovies = moviesInfo;
        mImagesLoadingProgress = new double[mMovies.size()];
        Platform.runLater(() -> buildMoviesFlowPane(moviesInfo));
    }

    // Called when the request to fetch movie data timed out.
    @Override
    public void requestTimedOut()
    {
        Platform.runLater(() -> showDownloadFailureAlert("Request timed out"));
    }

    // Called when the request fails due to an invalid internet connection
    @Override
    public void requestFailed()
    {
        Platform.runLater(() -> showDownloadFailureAlert("No internet connection"));
    }


    private void showDownloadFailureAlert(String headerText)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed to download");
        alert.setHeaderText(headerText);
        alert.setContentText("Please ensure you have an active internet connection.");
        alert.showAndWait();
    }

    private void buildMoviesFlowPane(ArrayList<MovieInfoObject> movies)
    {
        // Setup progress bar and status label
        mProgressBar.setProgress(0.0);
        mProgressBar.setVisible(true);
        mStatusLabel.setText("Loading..");

        // Build a flow pane layout with the width and size of the
        mMoviesFlowPane = new FlowPane(Orientation.HORIZONTAL);
        mMoviesFlowPane.setHgap(4);
        mMoviesFlowPane.setVgap(10);
        mMoviesFlowPane.setPadding(new Insets(10, 8, 4, 8));
        mMoviesFlowPane.prefWrapLengthProperty().bind(movieScrollPane.widthProperty());   // bind to scroll pane width

        for (MovieInfoObject movie : movies)
        {
            AnchorPane posterPane = buildMoviePosterPane(movie);
            mMoviesFlowPane.getChildren().add(posterPane);
        }

        movieScrollPane.setContent(mMoviesFlowPane);
    }


    private AnchorPane buildMoviePosterPane(MovieInfoObject movie) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MovieHandler.class.getResource("MoviePoster.fxml"));
            AnchorPane posterView = loader.load();
            posterView.setOnMouseClicked((mouseEvent) -> moviePosterClicked(movie));

            // set the movie info
            MoviePosterController controller = loader.getController();
            Image posterImage = new Image(movie.getFullPosterPath(), true);
            posterImage.progressProperty().addListener((observable, oldValue, newValue) -> updateProgressBar(movie, newValue.doubleValue()));

            controller.getMovieTitleLabel().setText(movie.getTitle());
            controller.getPosterImageView().setImage(posterImage);

            return posterView;
        } catch (IOException ex){
            Ui.printLine();
        }

        return null;
    }

    private void moviePosterClicked(MovieInfoObject movie) {

        mMainApplication.transitToMovieInfoController(movie);
    }

    private void updateProgressBar(MovieInfoObject movie, double progress)
    {
        // update the progress for that movie in the array
        int index = mMovies.indexOf(movie);
        if (index >= 0){
            mImagesLoadingProgress[index] = progress;
        }

        double currentTotalProgress = 0.0;
        for (double value : mImagesLoadingProgress){
            currentTotalProgress += value;
        }

        //System.out.println("Current total progress: " + currentTotalProgress);
        mProgressBar.setProgress((currentTotalProgress / mMovies.size()));

        if(currentTotalProgress >= mMovies.size()){
            mProgressBar.setVisible(false);
            mStatusLabel.setText("");
        }
    }
}