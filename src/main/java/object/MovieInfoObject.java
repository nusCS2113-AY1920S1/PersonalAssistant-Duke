package object;


import java.util.Date;

/**
 * Model class to represent a movie/tv show by storing details about it.
 */
public class MovieInfoObject {
    private int movieType;
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
     * @param type type of the object whether is it a movie or a tv show.
     * @param ID ID stored in the api.
     * @param title Title of the movie/tv show.
     * @param date Release date of the movie/tv show.
     * @param summary Plot summary of the movie/tv show.
     * @param rating Rating of the movie or tv show.
     * @param genreIDs Array of numbers that contains the genres belonging to the movie/tv show extracted from api.
     * @param posterPath Filepath of the movie/tv show poster.
     * @param backdropPath Filepath of the movie/tv show backdrop poster.
     */
    public MovieInfoObject(int type, long ID, String title, Date date, String summary, double rating, long[] genreIDs, String posterPath, String backdropPath) {
        movieType = type;
        movieID = ID;
        movieTitle = title;
        movieReleaseDate = date;
        movieSummary = summary;
        movieRating = rating;
        moviePosterPath = posterPath;
        movieBackdropPath = backdropPath;
        movieGenreIDs = genreIDs;
        this.adult = adult;
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

    public int getMovieType() {
        return movieType;
    }

    public boolean isAdult() {
        return adult;
    }
}
