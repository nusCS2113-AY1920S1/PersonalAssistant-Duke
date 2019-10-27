package entertainment.pro.model;


import java.util.Date;

/**
 * Model class to represent a movie/tv show by storing details about it.
 */
public class MovieInfoObject {
    private boolean isMovie;
    private long movieID;
    private String movieTitle;
    private Date movieReleaseDate;
    private String movieSummary;
    private String moviePosterPath;
    private String movieFullPosterPath;
    private String movieBackdropPath;
    private String movieFullBackdropPath;
    private double movieRating;
    private long[] movieGenreIDs;
    private boolean adult;


    /**
     * Construct info about a movie/tv show
     * @param ID ID stored in the api.
     * @param title Title of the movie/tv show.
     * @param date Release date of the movie/tv show.
     * @param summary Plot summary of the movie/tv show.
     * @param rating Rating of the movie or tv show.
     * @param genreIDs Array of numbers that contains the genres belonging to the movie/tv show extracted from api.
     * @param posterPath Filepath of the movie/tv show poster.
     * @param backdropPath Filepath of the movie/tv show backdrop poster.
     */

    public MovieInfoObject(boolean isMovie, long ID, String title, Date date, String summary, double rating, long[] genreIDs, String posterPath, String backdropPath, boolean isAdult) {
       this.isMovie = isMovie;
        movieID = ID;
        movieTitle = title;
        movieReleaseDate = date;
        movieSummary = summary;
        movieRating = rating;
        moviePosterPath = posterPath;
        movieBackdropPath = backdropPath;
        movieGenreIDs = genreIDs;
        this.adult = isAdult;
    }



    /**
     * Construct info about a movie/tv show.
     * @param id ID stored in the api.
     * @param title Title of the movie/tv show.
     */
    public MovieInfoObject(boolean isMovie, long id, String title) {
        isMovie = false;
        movieID = id;
        movieTitle = title;
        this.adult = true;
    }

    /**
     * This function sets the root path for movie/tv shows.
     * @param rootPath The root path of the poster.
     * @param posterSize String that consist of number of movie/tv posters to be downloaded.
     * @param backdropSize String that consist of number of backdrop posters to be downloaded.
     */
    public void setPosterRootPath(String rootPath, String posterSize, String backdropSize) {
        movieFullPosterPath = String.format("%s%s%s", rootPath, posterSize, moviePosterPath);
        movieFullBackdropPath = String.format("%s%s%s", rootPath, posterSize, movieBackdropPath);
    }

    /**
     * This function returns the movie/tv show ID as per the api.
     * @return the movie/tv show ID.
     */
    public long getID() {
        return movieID;
    }

    /**
     * This function returns the movie/tv show title.
     * @return the movie/tv show title.
     */
    public String getTitle() {
        return movieTitle;
    }

    /**
     * This function returns the release date of the movie/tv show.
     * @return the release date of the movie/tv show.
     */
    public Date getReleaseDate() {
        return movieReleaseDate;
    }

    /**
     * This function returns the plot summary of the movie/tv show.
     * @return the plot summary of the movie/tv show.
     */
    public String getSummary() {
        return movieSummary;
    }

    /**
     * This function returns the rating of the movie/tv show.
     * @return
     */
    public double getRating()
    {
        return movieRating;
    }

    public long[] getGenreIDs()
    {
        return movieGenreIDs;
    }

    public String getFullPosterPath()
    {
        return movieFullPosterPath;
    }

    public String getFullBackdropPath()
    {
        return movieFullBackdropPath;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public boolean isAdult() {
        return adult;
    }


    public long getMovieID() {
        return movieID;
    }

    public void setMovieID(long movieID) {
        this.movieID = movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public Date getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public void setMovieReleaseDate(Date movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public String getMovieSummary() {
        return movieSummary;
    }

    public void setMovieSummary(String movieSummary) {
        this.movieSummary = movieSummary;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public String getMovieFullPosterPath() {
        return movieFullPosterPath;
    }

    public void setMovieFullPosterPath(String movieFullPosterPath) {
        this.movieFullPosterPath = movieFullPosterPath;
    }

    public String getMovieBackdropPath() {
        return movieBackdropPath;
    }

    public void setMovieBackdropPath(String movieBackdropPath) {
        this.movieBackdropPath = movieBackdropPath;
    }

    public String getMovieFullBackdropPath() {
        return movieFullBackdropPath;
    }

    public void setMovieFullBackdropPath(String movieFullBackdropPath) {
        this.movieFullBackdropPath = movieFullBackdropPath;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
    }

    public long[] getMovieGenreIDs() {
        return movieGenreIDs;
    }

    public void setMovieGenreIDs(long[] movieGenreIDs) {
        this.movieGenreIDs = movieGenreIDs;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}
