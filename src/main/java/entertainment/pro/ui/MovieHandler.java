package entertainment.pro.ui;

import entertainment.pro.commons.PromptMessages;
import entertainment.pro.commons.exceptions.EmptyCommandException;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.commons.exceptions.MissingInfoException;
import entertainment.pro.logic.cinemaRequesterAPI.CinemaRetrieveRequest;
import entertainment.pro.logic.contexts.CommandContext;
import entertainment.pro.logic.contexts.ContextHelper;
import entertainment.pro.logic.contexts.SearchResultContext;
import entertainment.pro.logic.execution.CommandStack;
import entertainment.pro.logic.movieRequesterAPI.RequestListener;
import entertainment.pro.logic.movieRequesterAPI.RetrieveRequest;
import entertainment.pro.model.*;
import entertainment.pro.storage.user.Blacklist;
import entertainment.pro.storage.utils.*;
//import entertainment.pro.xtra.PastCommands;
//import entertainment.pro.storage.utils.PastUserCommands;

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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import entertainment.pro.logic.parsers.CommandParser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is main page of GUI.
 */
public class MovieHandler extends Controller implements RequestListener {

    @FXML
    private ScrollPane mMoviesScrollPane;

    @FXML
    Label userAdultLabel2;
    @FXML
    Label sortAlphaOrderLabel;
    @FXML
    Label sortLatestDateLabel;
    @FXML
    Label sortHighestRatingLabel;
    @FXML
    Label userNameLabel;
    @FXML
    Label userAgeLabel;
    @FXML
    private Label mStatusLabel;
    @FXML
    private Label userPlaylistsLabel;

    @FXML
    Text autoCompleteText;
    @FXML
    Text generalFeedbackText;

    @FXML
    private TextFlow genreListText;

    @FXML
    private TextField mSearchTextField;

    @FXML
    private MenuBar menuBar;

    @FXML
    private ProgressBar mProgressBar;

    @FXML
    private AnchorPane movieAnchorPane;


    private final static Logger LOGGER = Logger.getLogger(MovieHandler.class.getName());


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
    private ArrayList<String> playlists;
    private String playlistName = "";
    private PageTracker pageTracker = new PageTracker();
    private FlowPane mMoviesFlowPane;
    private VBox playlistVBox = new VBox();
    private static ArrayList<MovieInfoObject> mMovies = new ArrayList<>();
    private double[] mImagesLoadingProgress;
    private static RetrieveRequest mMovieRequest;
    private static CinemaRetrieveRequest mCinemaRequest;
    private int index = 0;
    static String command = "";
    ArrayList<Integer> genrePreference = new ArrayList<>();
    ArrayList<Integer> genreRestriction = new ArrayList<>();
    ArrayList<String> playlist = new ArrayList<>();
    boolean isAdultEnabled = false;
    boolean sortByAlphaOrder = false;
    boolean sortByRating = false;
    boolean sortByReleaseDate = false;
    boolean isMovie = true;
    String searchEntryName = "";
    String name = "";
    int age = 0;


    SearchProfile searchProfile = new SearchProfile(name, age, genrePreference, genreRestriction, isAdultEnabled,
            playlist, sortByAlphaOrder, sortByRating, sortByReleaseDate, searchEntryName, isMovie);


    public SearchProfile getSearchProfile() {
        return searchProfile;
    }


    /**
     * This function is called when JavaFx runtime when view is loaded.
     * Responsible for setting the components in the UI.
     */
    @FXML
    public void setLabels() throws IOException {
        System.out.println("called setlabels");
        EditProfileJson editProfileJson = new EditProfileJson();
        userProfile = editProfileJson.load();
        userNameLabel.setText(userProfile.getUserName());
        userAgeLabel.setText(Integer.toString(userProfile.getUserAge()));
        playlists = userProfile.getPlaylistNames();
        ProfileCommands command = new ProfileCommands(userProfile);
        userPlaylistsLabel.setText(Integer.toString(userProfile.getPlaylistNames().size()));
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
        genreListText.setLineSpacing(4);
        updateSortInterface(userProfile);
    }


    /**
     * Called by JavaFX runtime when view is loaded.
     */
    @FXML
    public void initialize() throws IOException, Exceptions {
        setLabels();
        mMovieRequest = new RetrieveRequest(this);
        mMovieRequest.setSearchProfile(searchProfile);
        mCinemaRequest = new CinemaRetrieveRequest(this);
        LOGGER.setLevel(Level.ALL);
        LOGGER.log(Level.INFO , "MAIN UI INITIALISED");
        CommandContext.initialiseContext();
        BlacklistStorage bp = new BlacklistStorage();
        System.out.println("Tgt we are winners");
        bp.load();
        HelpStorage.initialiseAllHelp();
        mSearchTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                System.out.println("Tab pressed");
                setAutoCompleteText(ContextHelper.getAllHints(mSearchTextField.getText(), this));
                event.consume();
            } else if (event.getCode().equals(KeyCode.ALT_GRAPH) || event.getCode().equals(KeyCode.ALT)) {
                System.out.println("I pressed bit");
                mSearchTextField.clear();
                String cmd = CommandStack.nextCommand();
                if (cmd == null) {
                    setAutoCompleteText("You don't have any commands in history!");
                } else {
                    mSearchTextField.clear();
                    mSearchTextField.setText(cmd);
                }
                mSearchTextField.positionCaret(mSearchTextField.getText().length());
            } else if (event.getCode() == KeyCode.ENTER) {
                clearAutoCompleteFeedbackText();
                setGeneralFeedbackText(PromptMessages.WAIT_FOR_APP_TO_PROCESS);
                command = mSearchTextField.getText();
                try {
                    CommandParser.parseCommands(command, this);
                } catch (IOException | Exceptions e) {
                    LOGGER.log(Level.SEVERE , "Exception in parsing command" + e);
                } catch (EmptyCommandException e) {
                    LOGGER.log(Level.SEVERE , PromptMessages.MISSING_COMMAND + e);
                    setGeneralFeedbackText(PromptMessages.MISSING_COMMAND);
                } catch (MissingInfoException e) {
                    setGeneralFeedbackText(PromptMessages.MISSING_ARGUMENTS);
                }
                clearSearchTextField();
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                mMoviesScrollPane.requestFocus();
                mMoviesFlowPane.getChildren().get(0).setStyle("-fx-border-color: white");
            }
        });

        mMovieRequest.beginSearchRequest(RetrieveRequest.MoviesRequestType.CURRENT_MOVIES);
        //Real time changes to text field
        mSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        System.out.println(generalFeedbackText.getText());

//        //Enter is Pressed
//        mSearchTextField.setOnKeyPressed(new KeyboardClick(this));
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
                    if (pageTracker.getCurrentPage().equals("playlistList")) {
                        int size = playlistVBox.getChildren().size();
                        if ((index + 1) != size) {
                            playlistVBox.getChildren().get(index).requestFocus();
                            index += 1;
                            if (index != 0) {
                                playlistVBox.getChildren().get(index - 1).setStyle("-fx-border-color: black");
                            }
                            playlistVBox.getChildren().get(index).setStyle("-fx-border-color: white");
                            if (index % 3 == 0) {
                                mMoviesScrollPane.setVvalue((double) (index + 1) / size);
                            }
                        }
                    } else {
                        int size = mMoviesFlowPane.getChildren().size();
                        if ((index + 1) != size) {
                            mMoviesFlowPane.getChildren().get(index).requestFocus();
                            index += 1;
                            if (index != 0) {
                                mMoviesFlowPane.getChildren().get(index - 1).setStyle("-fx-border-color: black");
                            }
                            mMoviesFlowPane.getChildren().get(index).setStyle("-fx-border-color: white");
                            if (index % 4 == 0) {
                                mMoviesScrollPane.setVvalue((double) (index + 1) / size);
                            }
                        }
                    }
                } else if (event.getCode().equals(KeyCode.LEFT)) {
                    System.out.println("yesssx");
                    if (pageTracker.getCurrentPage().equals("playlistList")) {
                        if (index != 0) {
                            playlistVBox.getChildren().get(index - 1).requestFocus();
                            index -= 1;
                            playlistVBox.getChildren().get(index + 1).setStyle("-fx-border-color: black");
                            playlistVBox.getChildren().get(index).setStyle("-fx-border-color: white");
                            double size = playlistVBox.getChildren().size();
                            if (index % 3 == 0) {
                                mMoviesScrollPane.setVvalue((index + 1) / size);
                            }
                        } else {
                            mSearchTextField.requestFocus();
                            playlistVBox.getChildren().get(index).setStyle("-fx-border-color: black");
                        }
                    } else {
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
                    }
                } else if (event.getCode().equals(KeyCode.ENTER)) {
                    try {
                        switch (pageTracker.getCurrentPage()) {
                            case "mainPage":
                                moviePosterClicked(mMovies.get(index));
                                break;
                            case "playlistInfo":
                                Playlist playlist1 = new EditPlaylistJson(playlistName).load();
                                playlistMoviePosterClicked(playlist1.getMovies().get(index));
                                break;
                            case "playlistList":
                                Playlist playlist2 = new EditPlaylistJson(playlists.get(index)).load();
                                playlistPaneClicked(playlist2);
                                break;
                            default:
                                break;
                        }


                    } catch (Exceptions | IOException exceptions) {
                        exceptions.printStackTrace();
                    }
                    index = 0;
                }
            }
        });
    }

    /**
     * This function is called to print message in UI when data for the movies/tv shows has been fetched from the API.
     * @param message String to be printed.
     */
    @Override
    public void requestCompleted(String message) {
        setGeneralFeedbackText(message);
    }

    /**
     * This function is called to print message in UI when data for the movies/tv shows has been fetched from the local files.
     * @param message String to be printed.
     */
    @Override
    public void requestTimedOut(String message) {
        setGeneralFeedbackText(message);
    }

    /**
     * This function is called to print message in UI when no results is found.
     */
    @Override
    public void emptyResults() {
        setGeneralFeedbackText(PromptMessages.NO_RESULTS_FOUND);
    }

    /**
     * Responsible for filtering the search results to remove blacklist items.
     * @param moviesInfo ArrayList containing all the seacrh results.
     */
    public void obtainedResultsData(ArrayList<MovieInfoObject> moviesInfo) {
        ArrayList<MovieInfoObject> filteredMovies = Blacklist.filter(moviesInfo);
        final ArrayList<MovieInfoObject> MoviesFinal = filteredMovies;
        mMovies.clear();
        System.out.println("cleared");
        for (MovieInfoObject mf : MoviesFinal) {
            //mMovies.add(mf);
            System.out.println("yaaz" + mf.getTitle());
        }
        //System.out.print("Request rsdceceived");
        SearchResultContext.addResults(MoviesFinal);
        mMovies = MoviesFinal;
        mImagesLoadingProgress = new double[mMovies.size()];
        Platform.runLater(() -> {
            try {
                buildMoviesFlowPane(MoviesFinal);
                pageTracker.setToMainPage();
            } catch (Exceptions exceptions) {
                exceptions.printStackTrace();
            }
        });
    }


    /**
     * This function initalizes the progress bar and extracts movie posters fro every movie.
     *
     * @param movies is a array containing details about every movie/tv show that is being displayed.
     */
    private void buildMoviesFlowPane(ArrayList<MovieInfoObject> movies) throws Exceptions {
        // Setup progress bar and status label
        mProgressBar.setProgress(0.0);
        mProgressBar.setVisible(true);
        mStatusLabel.setText("Loading..");

        mMoviesFlowPane = new FlowPane(Orientation.HORIZONTAL);
        mMoviesFlowPane.setHgap(4);
        mMoviesFlowPane.setVgap(10);
        mMoviesFlowPane.setPadding(new Insets(10, 8, 4, 8));
        mMoviesFlowPane.prefWrapLengthProperty().bind(mMoviesScrollPane.widthProperty());   // bind to scroll pane width
        //mMoviesFlowPane.getChildren().add(generalFeedbackLabel);
        for (int i = 0; i < movies.size(); i++) {
            AnchorPane posterPane = buildMoviePosterPane(movies.get(i), i + 1);
            mMoviesFlowPane.getChildren().add(posterPane);
        }
        mMoviesScrollPane.setFitToWidth(true);
        mMoviesScrollPane.setContent(mMoviesFlowPane);
        mMoviesScrollPane.setVvalue(0);
    }


    /**
     * to build the movie posters.
     * @param movie a object that contains information about a movie
     * @param index a unique number assigned to every movie/tv show that is being displayed.
     * @return Anchorpane consisting of the movie poster, name and the unique id.
     */

    private AnchorPane buildMoviePosterPane(MovieInfoObject movie, int index) throws Exceptions {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("MoviePoster.fxml"));
            AnchorPane posterView = loader.load();
            //posterView.setOnScroll();
            posterView.setOnMouseClicked((mouseEvent) -> {
                try {
                    moviePosterClicked(movie);
                } catch (Exceptions exceptions) {
                    exceptions.printStackTrace();
                }
            });

            // set the movie info
            MoviePosterController controller = loader.getController();
            try {
                if (movie.getFullPosterPathInfo() != null) {
                    //System.out.println("sianz");
                    Image posterImage = new Image(movie.getFullPosterPathInfo(), true);
                    posterImage.progressProperty().addListener((observable, oldValue, newValue) -> {
                        try {
                            updateProgressBar(movie, newValue.doubleValue());
                        } catch (Exceptions exceptions) {
                            exceptions.printStackTrace();
                        }
                    });
                    controller.getPosterImageView().setImage(posterImage);

                } else {

                    System.out.println("hi1");
                    File fakePoster = new File("./data/FakeMoviePoster.png");
                    Image posterImage = new Image(fakePoster.toURI().toString());
                    System.out.println("hi2");

                    posterImage.progressProperty().addListener((observable, oldValue, newValue) -> {
                        try {
                            updateProgressBar(movie, newValue.doubleValue());
                        } catch (Exceptions exceptions) {
                            exceptions.printStackTrace();
                        }
                    });
                    System.out.println("hi3");
                    controller.getPosterImageView().setImage(posterImage);
                    System.out.println("sianzzzzz");
                }
            } catch (NullPointerException ex) {

            }
            controller.getMovieTitleLabel().setText(movie.getTitle());
            controller.getMovieNumberLabel().setText(Integer.toString(index));
            return posterView;
        } catch (IOException ex) {
            //Ui.printLine();
        }

        return null;
    }

    /**
     * This funcion updates the progress bar as the movie poster is being displayed.
     *
     * @param movie    Object that contains all the information about a particular movie.
     * @param progress contains the progress value.
     */
    private void updateProgressBar(MovieInfoObject movie, double progress) throws Exceptions {
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
        }
    }


    /**
     * Responsible for displaying more information about a movie/TV show item.
     * @param num the movie/TV show item that user want to know mpre information about.
     * @throws Exceptions
     */
    public void showMovie(int num) throws Exceptions {
        //System.out.println("this is " + mMovies.size());
        MovieInfoObject movie = mMovies.get(num - 1);
        moviePosterClicked(movie);
        setGeneralFeedbackText(PromptMessages.TO_VIEW_BACK_SEARCHES);
        System.out.println("this is it 4");
    }



    private void buildPlaylistVBox(ArrayList<String> playlists) throws IOException {
        // Setup progress bar and status label
        mProgressBar.setProgress(0.0);
        mProgressBar.setVisible(true);
        mStatusLabel.setText("Loading..");
        playlistVBox.getChildren().clear();
        mMoviesScrollPane.setHvalue(0.5);
        mMoviesScrollPane.setVvalue(0.5);

        int count = 1;
        if (playlists.isEmpty()) {
            Label emptyLabel = new Label("u do not have any playlist currently :( "
                    + "\n try making some using command: playlist create <playlist name>");
            playlistVBox.getChildren().add(emptyLabel);
        } else {
            for (String log : playlists) {
                Playlist playlist = new EditPlaylistJson(log).load();
                System.out.println(playlist.getPlaylistName());
                System.out.println(playlist.getMovies().size());
                AnchorPane playlistPane = buildPlaylistPane(playlist, count);
                playlistVBox.getChildren().add(playlistPane);
                count++;
            }
        }
        mMoviesScrollPane.setContent(playlistVBox);
        mMoviesScrollPane.setVvalue(0);
        pageTracker.setToPlaylistList();
    }

    private AnchorPane buildPlaylistPane(Playlist playlist, int i) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("PlaylistPane.fxml"));
            AnchorPane playlistPane = loader.load();
            playlistPane.setOnMouseClicked((mouseEvent) -> {
                playlistPaneClicked(playlist);
            });
            // set the movie info
            PlaylistController controller = loader.getController();
            controller.setVBoxColour(i);
            controller.setTextColour();
            controller.getPlaylistNameLabel().setText(playlist.getPlaylistName());
            if (playlist.getDescription().trim().length() == 0) {
                controller.getPlaylistDescriptionLabel().setText("*this playlist does not have a description :(*");
            } else {
                controller.getPlaylistDescriptionLabel().setText(playlist.getDescription());
            }
            controller.getPlaylistMoviesLabel()
                    .setText("No. of movies: " + Integer.toString(playlist.getMovies().size()));
            System.out.println("no lei here");
            return playlistPane;
        } catch (IOException ex) {
//            Ui.printLine();
        }
        System.out.println("fk lah here");
        return null;
    }

    private void playlistPaneClicked(Playlist playlist) {
        buildPlaylistInfo(playlist);
        playlistName = playlist.getPlaylistName();
        mMovies = convert(playlist.getMovies());
    }

    private void buildPlaylistInfo(Playlist playlist) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("PlaylistInfo.fxml"));
            AnchorPane playlistPane = loader.load();
            PlaylistInfoController controller = loader.getController();
            controller.getPlaylistNameLabel().setText(playlist.getPlaylistName());
            if (playlist.getDescription().trim().length() == 0) {
                controller.getPlaylistDescriptionLabel().setStyle("-fx-font-style: italic");
                controller.getPlaylistDescriptionLabel().setText("*this playlist does not have a description :(*");
            } else {
                controller.getPlaylistDescriptionLabel().setText(playlist.getDescription());
            }
            if (playlist.getMovies().size() != 0) {
                controller.getPlaylistInfoVBox().getChildren().add(buildPlaylistMoviesFlowPane(playlist.getMovies()));
            } else {
                Label emptyMoviesLabel = new Label(playlist.getPlaylistName() + " does not contain any movies :(");
                controller.getPlaylistInfoVBox().getChildren().add(2, emptyMoviesLabel);
            }
            mMoviesScrollPane.setContent(controller.getPlaylistInfoVBox());
            pageTracker.setToPlaylistInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private FlowPane buildPlaylistMoviesFlowPane(ArrayList<PlaylistMovieInfoObject> movies) {
        // Setup progress bar and status label
        mProgressBar.setProgress(0.0);
        mProgressBar.setVisible(true);
        mStatusLabel.setText("Loading..");
        mMovies = convert(movies);

        mMoviesFlowPane = new FlowPane(Orientation.HORIZONTAL);
        mMoviesFlowPane.setHgap(4);
        mMoviesFlowPane.setVgap(10);
        mMoviesFlowPane.setPadding(new Insets(10, 8, 4, 8));
        mMoviesFlowPane.prefWrapLengthProperty().bind(mMoviesScrollPane.widthProperty());   // bind to scroll pane width

        for (int i = 0; i < movies.size(); i++) {
            AnchorPane posterPane = buildPlaylistMoviePosterPane(movies.get(i), i + 1);
            mMoviesFlowPane.getChildren().add(posterPane);
        }
        return mMoviesFlowPane;
    }

    private AnchorPane buildPlaylistMoviePosterPane(MovieInfoObject movie, int index) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("MoviePoster.fxml"));
            AnchorPane posterView = loader.load();
            //posterView.setOnScroll();
            posterView.setOnMouseClicked((mouseEvent) -> {
                try {
                    playlistMoviePosterClicked(movie);
                } catch (Exceptions exceptions) {
                    exceptions.printStackTrace();
                }
            });

            // set the movie info
            MoviePosterController controller = loader.getController();
            try {
                Image posterImage = new Image("/images/FakeMoviePoster.png");
                posterImage.progressProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        updateProgressBar(movie, newValue.doubleValue());
                    } catch (Exceptions exceptions) {
                        exceptions.printStackTrace();
                    }
                });
                controller.getPosterImageView().setImage(posterImage);
            } catch (NullPointerException ex) {

            }
            controller.getMovieTitleLabel().setText(movie.getTitle());
            controller.getMovieNumberLabel().setText(Integer.toString(index));
            return posterView;
        } catch (IOException ex) {
            //Ui.printLine();
        }

        return null;
    }

    public void showPlaylistList() throws IOException {
////        PlaylistUi playlistUi = new PlaylistUi(playlistName);
////        playlistVBox.getChildren().clear();
////        mMoviesScrollPane.setHvalue(0.5);
////        mMoviesScrollPane.setVvalue(0.5);
////        playlistVBox = playlistUi.buildPlaylistVBox(playlists, mProgressBar, mStatusLabel);
////        mMoviesScrollPane.setContent(playlistVBox);
////        mMoviesScrollPane.setVvalue(0);
////        pageTracker.setToPlaylistList();
//        PlaylistUi playlistUi = new PlaylistUi(playlistName, playlists);
//        mMoviesScrollPane.setContent(playlistUi.getPlaylistScrollPaneContent());
        playlist = userProfile.getPlaylistNames();
        buildPlaylistVBox(playlists);
    }

    private ArrayList<MovieInfoObject> convert(ArrayList<PlaylistMovieInfoObject> toConvert) {
        ArrayList<MovieInfoObject> converted = new ArrayList<>();
        boolean isMovie = false;
        for (PlaylistMovieInfoObject log : toConvert) {
            converted.add(new MovieInfoObject(log.getId(), log.getTitle(), isMovie,log.getReleaseDateInfo(), log.getSummaryInfo(), log.getFullPosterPathInfo(), log.getFullBackdropPathInfo(),
                    log.getRatingInfo(), log.getGenreIdInfo(), log.isAdultContent()));

        }
        return converted;
    }


    /**
     * This function is called when the user wants to see more information about a movie.
     * @param movie Object that contains all the informations about a movie/TV show.
     * @throws Exceptions
     */
    public void moviePosterClicked(MovieInfoObject movie) throws Exceptions {
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
            controller.getMovieRatingLabel().setText(String.format("%.2f", movie.getRatingInfo()));
            if (movie.getReleaseDateInfo() != null) {
                Date date = movie.getReleaseDateInfo();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String printDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                controller.getMovieReleaseDateLabel().setText(printDate);
            } else {
                controller.getMovieReleaseDateLabel().setText("N/A");
            }
            try {
                System.out.println(movie.getFullBackdropPathInfo());
                Image posterImage = new Image(movie.getFullBackdropPathInfo(), true);
                System.out.println(movie.getFullBackdropPathInfo());
                controller.getMovieBackdropImageView().setImage(posterImage);
            } catch (NullPointerException ex) {

            }

            controller.getMovieSummaryLabel().setText(movie.getSummaryInfo());
            mMovieRequest.beginMoreInfoRequest(movie);
            ArrayList<String> castStrings = movie.getCastInfo();
            String cast = "";
            for (int i = 0; i < castStrings.size(); i += 1) {
                cast += castStrings.get(i);
                cast += ", ";
            }
            controller.getMovieCastLabel().setText(cast);
            controller.getMovieCertLabel().setText(movie.getCertInfo());

            ArrayList<Long> genres = movie.getGenreIdInfo();
            String genreText = "";
            for (int i = 0; i < genres.size(); i += 1) {
                Long getGenre = genres.get(i);
                int convertGenre = getGenre.intValue();
                String genreAdd = ProfileCommands.findGenreName(convertGenre);
                if (!genreAdd.equals("0")) {
                    genreText += ProfileCommands.findGenreName(convertGenre);
                }
                if (i != genres.size() - 1) {
                    genreText += ", ";
                }

            }

            /**String[] genres = RetrieveRequest.getGenreStrings(movie);
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
             }**/
            //controller.getMovieGenresLabel().setText(builder.toString());
            controller.getMovieGenresLabel().setText(genreText);
            mMoviesFlowPane.getChildren().add(posterView);
            mMoviesScrollPane.setContent(mMoviesFlowPane);
            mMoviesScrollPane.setVvalue(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tis function is called when the user wants to see more information about a movie.
     */
    public void playlistMoviePosterClicked(MovieInfoObject movie) throws Exceptions {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("PlaylistMoreInfo.fxml"));
            AnchorPane posterView = loader.load();
            PlaylistMovieController controller = loader.getController();

            controller.getMovieTitleLabel().setText(movie.getTitle());
            controller.getMovieRatingLabel().setText(String.format("%.2f", movie.getRatingInfo()));
            if (movie.getReleaseDateInfo() != null) {
                Date date = movie.getReleaseDateInfo();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String printDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                controller.getMovieDateLabel().setText(printDate);
            } else {
                controller.getMovieDateLabel().setText("N/A");
            }
            Text summaryText = new Text(movie.getSummaryInfo());
            controller.getMovieSummaryLabel().setText(movie.getSummaryInfo());
            ArrayList<Long> genreList = movie.getGenreIdInfo();
            String genres = "";
            for (int i = 0; i < genreList.size(); i++) {
                if (genreList.size() == 0) {
                    genres = "no genres";
                }
                if (i != genreList.size() - 1) {
                    genres += ProfileCommands.findGenreName(genreList.get(i).intValue());
                    genres += " , ";
                } else {
                    genres += ProfileCommands.findGenreName(genreList.get(i).intValue());
                }
            }
            controller.getMovieGenresLabel().setText(genres);
            mMoviesScrollPane.setContent(controller.getPlaylistMovieInfoAnchorPane());
//            mMoviesFlowPane.getChildren().add(posterView);
//            mMoviesScrollPane.setContent(mMoviesFlowPane);
            mMoviesScrollPane.setVvalue(0);
            pageTracker.setToPlaylistMovieInfo();
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






    public void updateTextField(String updateStr) {
        mSearchTextField.setText(mSearchTextField.getText() + updateStr);
        mSearchTextField.positionCaret(mSearchTextField.getText().length());
    }

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public void setFeedbackText(ArrayList<String> txtArr) {
        String output = "";
        for (String s : txtArr) {
            output += s;
            output += "\n";

        }
        generalFeedbackText.setText(output);
    }

    /**
     * Sets text in the UI under generalFeedbackText.
     * @param txt The text to be printed.
     */
    public void setGeneralFeedbackText(String txt) {
        generalFeedbackText.setText(txt);
    }


    /**
     * Clears the text displayed under the generalFeedbackText.
     */
    public void clearGeneralFeedbackText() {
        generalFeedbackText.setText("");
    }

    /**
     * CLears the text stored under the autoCompleteText.
     */
    public void clearAutoCompleteFeedbackText() {
        autoCompleteText.setText("Press 'tab' to enable/view auto-complete options available for you...");
    }

    public void setAutoCompleteText(String text) {
        autoCompleteText.setText(text);
    }
    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
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

    /**
     * Retrieves the cinemaRetrieveRequest class.
     * @return the cinemaRetrieveRequest class
     */
    public CinemaRetrieveRequest getCinemaAPIRequester() {
        return mCinemaRequest;
    }

    public static String getCommands() {
        return command;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }


    public ArrayList<MovieInfoObject> getmMovies() {
        return mMovies;
    }

    @FXML
    private void clearSearchButtonClicked() {
        mSearchTextField.clear();
    }



    public static void setSearchProfile(SearchProfile searchProfile) {
        mMovieRequest.setSearchProfile(searchProfile);
    }

    /**
     * to update the user's preferred sort label.
     */
    private void updateSortInterface(UserProfile userProfile) {
        if (userProfile.isSortByAlphabetical()) {
            sortAlphaOrderLabel.setText("Y");
            sortLatestDateLabel.setText("N");
            sortHighestRatingLabel.setText("N");
        } else if (userProfile.isSortByLatestRelease()) {
            sortAlphaOrderLabel.setText("N");
            sortLatestDateLabel.setText("Y");
            sortHighestRatingLabel.setText("N");
        } else if (userProfile.isSortByHighestRating()) {
            sortAlphaOrderLabel.setText("N");
            sortLatestDateLabel.setText("N");
            sortHighestRatingLabel.setText("Y");
        } else {
            sortAlphaOrderLabel.setText("N");
            sortLatestDateLabel.setText("N");
            sortHighestRatingLabel.setText("N");
        }
    }


    public void setPlaylistName(String name) {
        playlistName = name;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    /**
     * to refresh the gui page so it reflects user's changes.
     */
    public void refresh() throws IOException {
        userProfile = new EditProfileJson().load();
        playlists = userProfile.getPlaylistNames();
        switch (pageTracker.getCurrentPage()) {
        case "playlistList":
            EditProfileJson editProfileJson = new EditProfileJson();
            buildPlaylistVBox(editProfileJson.load().getPlaylistNames());
            break;
        case "playlistInfo":
            EditPlaylistJson editPlaylistJson = new EditPlaylistJson(playlistName);
            buildPlaylistInfo(editPlaylistJson.load());
            break;
        default:
            break;
        }
    }

    public PageTracker getPageTracker() {
        return pageTracker;
    }

    /**
     * to go back to playlist info page from playlistmovieinfo page.
     */
    public void backToPlaylistInfo() throws IOException {
        if (pageTracker.isPlaylistMovieInfo()) {
            pageTracker.setToPlaylistInfo();
            refresh();
        }
    }
}