package MovieUI;

import Contexts.CommandContext;
import Contexts.ContextHelper;
import Contexts.SearchResultContext;
import EPparser.CommandParser;
import EPstorage.*;
import Execution.CommandStack;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import movieRequesterAPI.RequestListener;
import movieRequesterAPI.RetrieveRequest;
import object.MovieInfoObject;
import object.PastCommandStructure;
import org.json.simple.parser.ParseException;
import retractCommands.PastUserCommands;
import sort.EditSortProfileJson;
import sort.SortProfile;
import ui.Ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This is main page of GUI.
 */
public class MovieHandler extends Controller implements RequestListener {

    @FXML
    private ScrollPane mMoviesScrollPane;

    @FXML
    private VBox vbox0, vBox1, vBox2, vBox3, gneresVBox, mainVBox, searchCommandVBox, generalFeedbackVBox, autoCompleteVBox;

    @FXML
    private HBox nameHBox, adultHBox, genresHBox, alphaSortHBox, latestDatesHBox, highestRatingHBox;

    @FXML
    private Label userPreferenceLabel, userNameLabel, userAgeLabel, userAdultLabel1, userAdultLabel2,
            userGenreLabel, sortAlphaOrderLabel, sortLatestDateLabel, sortHighestRatingLabel,
            sortHighestRatingText, autoCompleteLabel, generalFeedbackLabel;

    @FXML
    private Text userPreferenceText, userNameText, userAgeText, generalFeedbackText,
            sortAlphaOrderText, sortLatestDateText, autoCompleteText;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu fileMenu, helpMenu;

    @FXML
    TextFlow genreListText;

    @FXML
    private Label mStatusLabel;


    @FXML
    private ProgressBar mProgressBar;

    @FXML
    private TextField mSearchTextField;

    @FXML
    private AnchorPane movieAnchorPane;

    @FXML
    private Label userPlaylistsLabel;


    private boolean isViewBack = false;
    private boolean isViewBackMoreInfo = false;

    public boolean isViewBackMoreInfo() {
        return isViewBackMoreInfo;
    }

    public void setViewBackMoreInfo(boolean viewBackMoreInfo) {
        isViewBackMoreInfo = viewBackMoreInfo;
    }

    public void setViewBack(boolean viewBack) {
        isViewBack = viewBack;
    }

    private AnchorPane anchorPane;
    private static UserProfile userProfile;
    private ArrayList<Playlist> playlists;
    private FlowPane mMoviesFlowPane;
    private static ArrayList<MovieInfoObject> mMovies = new ArrayList<>();
    private double[] mImagesLoadingProgress;
    private static RetrieveRequest mMovieRequest;
    private int index = 0;
    private static SortProfile sortProfile;
    private static PastCommands pastCommands = new PastCommands();;
    static String command = "";
    Controller controller;


    public static void updatePastCommands(String now) {
        PastCommandStructure pastCommandStructure = new PastCommandStructure(now, command);
        ArrayList<PastCommandStructure> arrayList = pastCommands.getMap();
        arrayList.add(pastCommandStructure);
        System.out.println(now + " " + command);
        pastCommands.setMap(arrayList);
        PastUserCommands.update(pastCommands);
    }

    class KeyboardClick implements EventHandler<KeyEvent> {

        private Controller control;

        KeyboardClick(Controller control) {
            this.control = control;
        }

        /**
         * Handles user's inputs and respond appropriately.
         *
         * @param event consist of user's inputs.
         */
        @Override
        public void handle(KeyEvent event) {

            System.out.println("You Pressing : " + ((KeyEvent) event).getCode());
            if ((event.getCode().equals(KeyCode.ENTER))) {
                System.out.println("Hello");
                command = mSearchTextField.getText();
                //clickEntered(command, control);
               try {
                    CommandParser.parseCommands(command, control);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clearSearchTextField();
            } else if (event.getCode().equals(KeyCode.TAB)) {
                System.out.println("Tab presjenksjessed");
                event.consume();
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                mMoviesScrollPane.requestFocus();
                mMoviesFlowPane.getChildren().get(0).setStyle("-fx-border-color: white");
            }
        }

    }

    /**
     * This function is called when JavaFx runtime when view is loaded
     */
    @FXML
    public void setLabels() throws IOException {
        EditProfileJson editProfileJson = new EditProfileJson();
        userProfile = editProfileJson.load();
        EditSortProfileJson editSortProfileJson = new EditSortProfileJson();
        sortProfile = editSortProfileJson.load();
        try {
            pastCommands.setMap(PastUserCommands.load());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        EditPlaylistJson editPlaylistJson = new EditPlaylistJson();
        playlists = editPlaylistJson.load();
        ProfileCommands command = new ProfileCommands(userProfile);
        userNameLabel.setText(userProfile.getUserName());
        userAgeLabel.setText(Integer.toString(userProfile.getUserAge()));
        System.out.println("changed age");

        //setting adult label
        if (command.getAdultLabel().equals("allow")) {
            userAdultLabel2.setStyle("-fx-text-fill: \"#48C9B0\";");
        }
        if (command.getAdultLabel().equals("restrict")) {
            userAdultLabel2.setStyle("-fx-text-fill: \"#EC7063\";");
        }
        userAdultLabel2.setText(command.getAdultLabel());
        //setting text for preference & restrictions
        Text preferences = new Text(command.convertToLabel(userProfile.getGenreIdPreference()));
        preferences.setFill(Paint.valueOf("#48C9B0"));
        Text restrictions = new Text(command.convertToLabel(userProfile.getGenreIdRestriction()));
        restrictions.setFill(Paint.valueOf("#EC7063"));
        genreListText.getChildren().clear();
        genreListText.getChildren().addAll(preferences, restrictions);
        sortAlphaOrderLabel.setText(sortProfile.getAlphaOrder());
        sortLatestDateLabel.setText(sortProfile.getLatestDatesOrder());
        sortHighestRatingLabel.setText(sortProfile.getHighestRatingOrder());
        userPlaylistsLabel.setText(Integer.toString(playlists.size()));
    }


    @FXML
    public void initialize() throws IOException {
        setLabels();
        mMovieRequest = new RetrieveRequest(this);
        CommandContext.initialiseContext();

        BlacklistStorage bp = new BlacklistStorage();
        bp.load();

        HelpStorage.initialiseAllHelp();

        mSearchTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                System.out.println("Tab pressed");


                setAutoCompleteText(ContextHelper.getAllHints(mSearchTextField.getText(), this));
                event.consume();
            } else if (event.getCode().equals(KeyCode.BACK_SLASH)) {
                System.out.println("I pressed bit");
                mSearchTextField.clear();
                String cmd = CommandStack.nextCommand();
                if (cmd == null) {
                    setAutoCompleteText("You dont have any commands in history!");
                } else {
                    mSearchTextField.setText(cmd);
                }

                mSearchTextField.positionCaret(mSearchTextField.getText().length());
            }
        });

        mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.CURRENT_MOVIES, userProfile.isAdult());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String now = formatter.format(date);
        PastCommandStructure pastCommandStructure = new PastCommandStructure(now, "view movies current");
        ArrayList<PastCommandStructure> arrayList = pastCommands.getMap();
        arrayList.add(pastCommandStructure);
        //System.out.println(now + " " + command);
        pastCommands.setMap(arrayList);
        PastUserCommands.update(pastCommands);

        generalFeedbackText.setText("Welcome to Entertainment Pro. Displaying currently showing movies...");

        //Real time changes to text field
        mSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        System.out.println(generalFeedbackText.getText());

        //Enter is Pressed
        mSearchTextField.setOnKeyPressed(new KeyboardClick(this));
        mMoviesScrollPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.UP)) {
                    double num = (double) mMoviesScrollPane.getVvalue();
                    num *= 10;
                    if (num == 0) {
                        mSearchTextField.requestFocus();
                    }
                    mMoviesFlowPane.getChildren().get(index).setStyle("-fx-border-color: black");
                    index = 0;
                } else if (event.getCode().equals(KeyCode.RIGHT)) {
                    int size = mMoviesFlowPane.getChildren().size();
                    if ((index + 1) != size) {
                        mMoviesFlowPane.getChildren().get(index).requestFocus();
                        index += 1;
                        if (index != 0) {
                            mMoviesFlowPane.getChildren().get(index - 1).setStyle("-fx-border-color: black");
                        }
                        mMoviesFlowPane.getChildren().get(index).setStyle("-fx-border-color: white");
                        if (index % 4 == 0) {
                            mMoviesScrollPane.setVvalue((double)(index + 1) / size);
                        }
                    }
                } else if (event.getCode().equals(KeyCode.LEFT)) {
                    System.out.println("yesssx");
                    if (index != 0) {
                        mMoviesFlowPane.getChildren().get(index - 1).requestFocus();
                        index -= 1;
                        mMoviesFlowPane.getChildren().get(index + 1).setStyle("-fx-border-color: black");
                        mMoviesFlowPane.getChildren().get(index).setStyle("-fx-border-color: white");
                        double size = mMoviesFlowPane.getChildren().size();
                        if (index % 4 == 0) {
                            mMoviesScrollPane.setVvalue((index + 1) / size);
                        }
                    } else {
                        mSearchTextField.requestFocus();
                        mMoviesFlowPane.getChildren().get(index).setStyle("-fx-border-color: black");

                    }
                } else if (event.getCode().equals(KeyCode.ENTER)) {
                    moviePosterClicked(mMovies.get(index));
                    index = 0;
                }
            }
        });
    }

    /**
     * This function is called when data for the movies/tv shows has been fetched.
     */
    @Override
    public void requestCompleted(ArrayList<MovieInfoObject> moviesInfo) {
        // Build the Movie poster views and add to the flow pane on the main thread
        //System.out.print("Request received");
        final ArrayList<MovieInfoObject> MoviesFinal = Blacklist.filter(moviesInfo);
        mMovies.clear();
        System.out.println("cleared");
        for (MovieInfoObject mf : MoviesFinal) {
            //mMovies.add(mf);
            System.out.println("yaaz" + mf.getTitle());
        }
        //System.out.print("Request rsdceceived");
        SearchResultContext.addResults(MoviesFinal);
        mMovies = MoviesFinal;

        if (isViewBackMoreInfo) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    // Update UI here.
            PastCommandStructure pastCommandStructure = getPastCommands().getMap().get(
                    getPastCommands().getMap().size() - 2);
            String command = pastCommandStructure.getQuery();
            String[] getStrips = command.split(" ");
            int num = 0;
            if (getPastCommands().getMap().get(getPastCommands().getMap().size() - 2).getQuery().startsWith("view entry")) {
                num = Integer.parseInt(getStrips[2]);
            }
            showMovie(num);
            isViewBackMoreInfo = false;
                        getPastCommands().getMap().remove(getPastCommands().getMap().size() - 1);
                        getPastCommands().getMap().remove(getPastCommands().getMap().size() - 1);
                        PastUserCommands.update(pastCommands);
                        isViewBack = false;
                }
            });


        } else {
            //System.out.println("this is size: " + mMovies.size());
            mImagesLoadingProgress = new double[mMovies.size()];
            Platform.runLater(() -> buildMoviesFlowPane(MoviesFinal));
            if (isViewBack == true) {
                getPastCommands().getMap().remove(getPastCommands().getMap().size() - 1);
                getPastCommands().getMap().remove(getPastCommands().getMap().size() - 1);
                PastUserCommands.update(pastCommands);
                isViewBack = false;
            }
        }


    }

    public void displayMovies() {
        mMovies = SearchResultContext.getMoviesToDisplay();
        mImagesLoadingProgress = new double[mMovies.size()];
        Platform.runLater(() -> buildMoviesFlowPane(SearchResultContext.getMoviesToDisplay()));
    }

    /**
     * This function is called when data for the movies/tv shows failed to fetch due to timed out.
     */
    @Override
    public void requestTimedOut() {
        Platform.runLater(() -> showDownloadFailureAlert("Request timed out"));
    }

    /**
     * This function is called when data for the movies/tv shows failed due to internet connection
     */
    @Override
    public void requestFailed() {
        Platform.runLater(() -> showDownloadFailureAlert("No internet connection"));
    }

    /**
     * This funcion is called to print a message when the data for movies/tv shows is unavailable due to
     * no internet connection.
     *
     * @param headerText consists of the string to be printed.
     */
    private void showDownloadFailureAlert(String headerText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed to download");
        alert.setHeaderText(headerText);
        alert.setContentText("Please ensure you have an active internet connection.");
        alert.showAndWait();
    }

    /**
     * This function initalises the progress bar and extracts movie posters fro every movie.
     *
     * @param movies is a array containing details about every movie/tv show that is being displayed.
     */
    private void buildMoviesFlowPane(ArrayList<MovieInfoObject> movies) {
        // Setup progress bar and status label
        mProgressBar.setProgress(0.0);
        mProgressBar.setVisible(true);
        mStatusLabel.setText("Loading..");

        mMoviesFlowPane = new FlowPane(Orientation.HORIZONTAL);
        mMoviesFlowPane.setHgap(4);
        mMoviesFlowPane.setVgap(10);
        mMoviesFlowPane.setPadding(new Insets(10, 8, 4, 8));
        mMoviesFlowPane.prefWrapLengthProperty().bind(mMoviesScrollPane.widthProperty());   // bind to scroll pane width

        for (int i = 0; i < movies.size(); i++) {
            AnchorPane posterPane = buildMoviePosterPane(movies.get(i), i + 1);
            mMoviesFlowPane.getChildren().add(posterPane);
        }

        mMoviesScrollPane.setContent(mMoviesFlowPane);
        mMoviesScrollPane.setVvalue(0);
    }


    /**
     * @param movie a object that contains information about a movie
     * @param index a unique number assigned to every movie/tv show that is being displayed.
     * @return anchorpane consisting of the movie poster, name and the unique id.
     */

    private AnchorPane buildMoviePosterPane(MovieInfoObject movie, int index) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("MoviePoster.fxml"));
            AnchorPane posterView = loader.load();
            //posterView.setOnScroll();
            posterView.setOnMouseClicked((mouseEvent) -> moviePosterClicked(movie));

            // set the movie info
            MoviePosterController controller = loader.getController();
            try {
                Image posterImage = new Image(movie.getFullPosterPath(), true);
                posterImage.progressProperty().addListener((observable, oldValue, newValue) -> updateProgressBar(movie, newValue.doubleValue()));
                controller.getPosterImageView().setImage(posterImage);
            } catch (NullPointerException ex) {

            }
            controller.getMovieTitleLabel().setText(movie.getTitle());
            controller.getMovieNumberLabel().setText(Integer.toString(index));
            return posterView;
        } catch (IOException ex) {
            Ui.printLine();
        }

        return null;
    }

    /**
     * This funcion updates the progress bar as the movie poster is being displayed.
     *
     * @param movie    Object that contains all the information about a particular movie.
     * @param progress contains the progress value.
     */
    private void updateProgressBar(MovieInfoObject movie, double progress) {
        // update the progress for that movie in the array
        int index = mMovies.indexOf(movie);
        if (index >= 0) {
            mImagesLoadingProgress[index] = progress;
        }

        double currentTotalProgress = 0.0;
        for (double value : mImagesLoadingProgress) {
            currentTotalProgress += value;
        }

        //System.out.println("Current total progress: " + currentTotalProgress);
        mProgressBar.setProgress((currentTotalProgress / mMovies.size()));

        if (currentTotalProgress >= mMovies.size()) {
            mProgressBar.setVisible(false);
            mStatusLabel.setText("");
            if (isViewBack) {
                PastCommandStructure pastCommandStructure = getPastCommands().getMap().get(
                        getPastCommands().getMap().size() - 2);
                String command = pastCommandStructure.getQuery();
                String[] getStrips = command.split(" ");
                int num = 0;
                if (getPastCommands().getMap().get(getPastCommands().getMap().size() - 2).getQuery().startsWith("view entry")) {
                    num = Integer.parseInt(getStrips[2]);
                }
                showMovie(num);
                isViewBack = false;
                getPastCommands().getMap().remove(getPastCommands().getMap().size() - 1);
                getPastCommands().getMap().remove(getPastCommands().getMap().size() - 1);
                PastUserCommands.update(pastCommands);

            }
        }
    }

    public boolean isViewBack() {
        return isViewBack;
    }

    public void showMovie(int num) {
        //System.out.println("this is " + mMovies.size());
        MovieInfoObject movie = mMovies.get(num - 1);
        moviePosterClicked(movie);
        System.out.println("this is it 4");
    }

    /**
     * Tis function is called when the user wants to see more information about a movie.
     */
    public void moviePosterClicked(MovieInfoObject movie) {
        try {
            //mMainApplication.transitToMovieInfoController(movie);
            mMoviesFlowPane.getChildren().clear();
            mMoviesFlowPane = new FlowPane(Orientation.HORIZONTAL);
            mMoviesFlowPane.setHgap(4);
            mMoviesFlowPane.setVgap(10);
            mMoviesFlowPane.setPadding(new Insets(10, 8, 4, 8));
            mMoviesFlowPane.prefWrapLengthProperty().bind(mMoviesScrollPane.widthProperty());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("MoreInfo.fxml"));
            AnchorPane posterView = loader.load();
            InfoController controller = loader.getController();

            controller.getMovieTitleLabel().setText(movie.getTitle());
            controller.getMovieRatingLabel().setText(String.format("%.2f", movie.getRating()));
            if (movie.getReleaseDate() != null) {
                Date date = movie.getReleaseDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String printDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                controller.getMovieReleaseDateLabel().setText(printDate);
            } else {
                controller.getMovieReleaseDateLabel().setText("N/A");
            }
            try {
                Image posterImage = new Image(movie.getFullBackdropPath(), true);
                controller.getMovieBackdropImageView().setImage(posterImage);
            } catch (NullPointerException ex) {

            }

            controller.getMovieSummaryLabel().setText(movie.getSummary());
            controller.getMovieCastLabel().setText(RetrieveRequest.getCastStrings(movie));
            controller.getMovieCertLabel().setText(RetrieveRequest.getCertStrings(movie));
            String[] genres = RetrieveRequest.getGenreStrings(movie);
            StringBuilder builder = new StringBuilder();
            try {
                for (String genre : genres) {
                    builder.append(genre);
                    System.out.println(genre + "  " + genres.length);
                    // if not last string in array, append a ,
                    if (genres.length == 0) {
                        System.out.println("no genres");
                    } else if (!genres[genres.length - 1].equals(genre)) {
                        builder.append(", ");
                    }
                }
            } catch (NullPointerException ex) {

            }
            controller.getMovieGenresLabel().setText(builder.toString());
            mMoviesFlowPane.getChildren().add(posterView);
            mMoviesScrollPane.setContent(mMoviesFlowPane);
            mMoviesScrollPane.setVvalue(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function clears the searchTextField when called.
     */
    public void clearSearchTextField() {
        mSearchTextField.setText("");
    }



    /**
     * Updates the components in the SortProfile accordingly.
     *
     * @param isAlphaOrder    true when user have entered command to sort results in alphabetical order and otherwise false.
     * @param isLatDatesOrder true when user have entered command to sort results based on release dates and otherwise false.
     * @param isRatingsOrder  true when user have entered command to sort results based on ratings and otherwise false.
     */
    public void setSort(boolean isAlphaOrder, boolean isLatDatesOrder, boolean isRatingsOrder) {
        String yes = "Y";
        String no = "N";
        if (isAlphaOrder) {
            setSortText(yes, no, no);
        } else if (isLatDatesOrder) {
            setSortText(no, yes, no);
        } else if (isRatingsOrder) {
            setSortText(no, no, yes);
        }

    }


    /**
     * Sets the updated values for the sort components.
     * Sets the text for the sort components in the UI.
     * Updates the changes into json file by calling update function in the end.
     *
     * @param txt1 String text to be set in sortAlphaOrderLabel.
     * @param txt2 String text to be set in sortLatestDateLabel.
     * @param txt3 String text to be set in sortHighestRatingLabel.
     */
    public void setSortText(String txt1, String txt2, String txt3) {
        sortProfile.setAlphaOrder(txt1);
        sortAlphaOrderLabel.setText(txt1);
        sortProfile.setLatestDatesOrder(txt2);
        sortLatestDateLabel.setText(txt2);
        sortProfile.setHighestRatingOrder(txt3);
        sortHighestRatingLabel.setText(txt3);
        EditSortProfileJson.update(sortProfile);
    }

    public void updateTextField(String updateStr) {
        mSearchTextField.setText(mSearchTextField.getText() + updateStr);
        mSearchTextField.positionCaret(mSearchTextField.getText().length());
    }

    public void setFeedbackText(ArrayList<String> txtArr) {
        String output = "";
        for (String s : txtArr) {
            output += s;
            output += "\n";

        }
        generalFeedbackText.setText(output);
    }


    /**
     * Prints message in UI.
     *
     * @param txt which is the string text to be printed.
     */
    public void setFeedbackText(String txt) {
        generalFeedbackText.setText(txt);
    }


    public void setAutoCompleteText(String text) {
        autoCompleteText.setText(text);
    }

    public void setAutoCompleteText(ArrayList<String> txtArr) {
        String output = "";
        Set<String> hashSet = new HashSet<String>();
        for (String s : txtArr) {
            hashSet.add(s);
        }

        for (String s:hashSet) {
            output += s;
            output += "\n";

        }
        autoCompleteText.setText(output);
    }

    /**
     * Retrieves the RetrieveRequest class.
     *
     * @return the RetrieveRequest class.
     */
    public RetrieveRequest getAPIRequester() {
        return mMovieRequest;
    }

    public static String getCommands() {
        return command;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public SortProfile getSortProfile() {
        return sortProfile;
    }

    public static PastCommands getPastCommands() {
        return pastCommands;
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public ArrayList<MovieInfoObject> getmMovies() {
        return mMovies;
    }

    @FXML
    private void clearSearchButtonClicked() {
        mSearchTextField.clear();
    }


    // Menu item events
    @FXML
    public void exitMenuItemClicked() {
        System.exit(0);
    }

    @FXML
    public void aboutMenuItemClicked() {
    }

    /**
     * Displays list of current movies showing on cinemas.
     */
    public static void showCurrentMovies() {
        mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.CURRENT_MOVIES, userProfile.isAdult());
    }

    /**
     * Displays list of current tv shows showing.
     */
    public static void showCurrentTV() {
        mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.CURRENT_TV, userProfile.isAdult());
    }

    /**
     * Displays list of upcoming movies.
     */
    public static void showUpcomingMovies() {
        mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.UPCOMING_MOVIES, userProfile.isAdult());
    }

    /**
     * Displays list of upcoming tv shows.
     */
    public static void showUpcomingTV() {
        mMovieRequest.beginMovieRequest( RetrieveRequest.MoviesRequestType.CURRENT_TV, userProfile.isAdult());
    }

    /**
     * Displays list of popular movies.
     */
    public static void showPopMovies() {
        mMovieRequest.beginMovieRequest( RetrieveRequest.MoviesRequestType.POPULAR_MOVIES, userProfile.isAdult());
    }

    /**
     * Displays list of popular tv shows.
     */
    public static void showPopTV() {
        mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.POPULAR_TV, userProfile.isAdult());
    }


    /**
     * Displays list of trending movies.
     */
    public static void showTrendMovies() {
        mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.TRENDING_MOVIES, userProfile.isAdult());
        ;
    }

    /**
     * Displays list of trending tv shows.
     */
    public static void showTrendTV() {
        mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.TRENDING_TV, userProfile.isAdult());
    }


    public static void showSearch(String pay, ArrayList<Long>genres) {
        //mMovieRequest.beginMovieSearchRequest(payload, userProfile.isAdult());
        String payload = "";
        ArrayList<Long> trial = new ArrayList<>();
        mMovieRequest.getOfflineSearch(payload, trial, userProfile.isAdult());

    }

}