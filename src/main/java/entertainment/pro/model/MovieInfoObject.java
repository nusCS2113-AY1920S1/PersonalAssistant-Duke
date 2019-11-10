package entertainment.pro.model;


import java.util.ArrayList;
import java.util.Date;

/**
 * Model class to represent a movie/tv show by storing details about it.
 */
public class MovieInfoObject extends MovieModel {
    private boolean isMovie;
    private Date releaseDateInfo;
    private String summaryInfo;
    private String posterPathInfo;
    private String fullPosterPathInfo;
    private String backdropPathInfo;
    private String fullBackdropPathInfo;
    private double ratingInfo;
    private ArrayList<Long> genreIdInfo;
    private boolean isAdultContent;
    private String certInfo;
    private ArrayList<String> castInfo;
    private final String defaultImageFileName = "/images/FakeMoviePoster.png";


    /**
     * Constructor for MovieInfoObject.
     *
     * @param id the id of the movie/TV show stored in the MovieDB API.
     * @param title the title of the movie or TV show stored in the MovieDB API.
     * @param isMovie whether the objeect stored is a movie or TV show.
     * @param releaseDateInfo the release date of the movie or TV show.
     * @param summaryInfo the plot summary of the movie or TV show.
     * @param posterPathInfo the file path of the poster.
     * @param backdropPathInfo the file path of the backdrop poster.
     * @param ratingInfo the rating of the movie or TV show.
     * @param genreIdInfo the list of genres pertaining to the movie or TV show.
     * @param isAdultContent whether the movie or TV show contains adult content.
     * @param certInfo the certification obtained by the movie or TV show.
     * @param castInfo the list of cast pertaining to the movie or TV show.
     */
    public MovieInfoObject(long id, String title, boolean isMovie, Date releaseDateInfo, String summaryInfo,
                           String posterPathInfo, String backdropPathInfo, double ratingInfo,
                           ArrayList<Long> genreIdInfo, boolean isAdultContent, String certInfo,
                           ArrayList<String> castInfo) {
        super(id, title);
        this.isMovie = isMovie;
        this.releaseDateInfo = releaseDateInfo;
        this.summaryInfo = summaryInfo;
        this.posterPathInfo = posterPathInfo;
        this.fullPosterPathInfo = fullPosterPathInfo;
        this.backdropPathInfo = backdropPathInfo;
        this.fullBackdropPathInfo = fullBackdropPathInfo;
        this.ratingInfo = ratingInfo;
        this.genreIdInfo = genreIdInfo;
        this.isAdultContent = isAdultContent;
        this.certInfo = certInfo;
        this.castInfo = castInfo;
    }

    /**
     * Construct info about a movie/tv show.
     * @param id ID stored in the api.
     * @param title Title of the movie/tv show.
     */
    public MovieInfoObject(long id, String title, boolean isMovie) {
        super(id, title);
        isMovie = false;
        this.isAdultContent = true;
    }

    /**
     * constructor.
     * @param id the id of the movie/TV show stored in the MovieDB API.
     * @param title the title of the movie or TV show stored in the MovieDB API.
     * @param isMovie whether the objeect stored is a movie or TV show.
     * @param releaseDateInfo the release date of the movie or TV show.
     * @param summaryInfo the plot summary of the movie or TV show.
     * @param posterPathInfo the file path of the poster.
     * @param backdropPathInfo the file path of the backdrop poster.
     * @param ratingInfo the rating of the movie or TV show.
     * @param genreIdInfo the list of genres pertaining to the movie or TV show.
     * @param isAdultContent whether the movie or TV show contains adult content.
     */
    public MovieInfoObject(long id, String title, boolean isMovie, Date releaseDateInfo, String summaryInfo,
                           String posterPathInfo, String backdropPathInfo, double ratingInfo,
                           ArrayList<Long> genreIdInfo, boolean isAdultContent) {
        super(id, title);
        this.isMovie = isMovie;
        this.releaseDateInfo = releaseDateInfo;
        this.summaryInfo = summaryInfo;
        this.posterPathInfo = posterPathInfo;
        this.fullPosterPathInfo = fullPosterPathInfo;
        this.backdropPathInfo = backdropPathInfo;
        this.fullBackdropPathInfo = fullBackdropPathInfo;
        this.ratingInfo = ratingInfo;
        this.genreIdInfo = genreIdInfo;
        this.isAdultContent = isAdultContent;
    }

    /**
     * Resonsible for returning whether the object is storing a movie or TV show.
     * @return true if the object is storing a movie and false otherwise.
     */
    public boolean isMovie() {
        return isMovie;
    }

    /**
     * Responsible for setting whether the object is storing a movie or TV show.
     * @param movie true if the object is storing a movie and false otherwise.
     */
    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    /**
     * Responsible for returning the release date of the object.
     * @return the release date of the movie/TV show.
     */
    public Date getReleaseDateInfo() {
        return releaseDateInfo;
    }

    /**
     * Responsible for setting the release date of the object.
     * @param releaseDateInfo the release date of the movie/TV show.
     */
    public void setReleaseDateInfo(Date releaseDateInfo) {
        this.releaseDateInfo = releaseDateInfo;
    }

    /**
     * Responsible for returning the plot summary of the object.
     * @return the plot summary of the movie/TV show.
     */
    public String getSummaryInfo() {
        return summaryInfo;
    }

    /**
     * Responsible for setting the plot summary of the object.
     * @param summaryInfo the plot summary of the movie/TV show.
     */
    public void setSummaryInfo(String summaryInfo) {
        this.summaryInfo = summaryInfo;
    }

    /**
     * Responsible for returning the poster path of the object.
     * @return the poster path of the movie/TV show.
     */
    public String getFullPosterPathInfo() {
        return fullPosterPathInfo;
    }

    /**
     * Repsonsible for setting the poster path of the object.
     * @param fullPosterPathInfo the poster path of the movie/TV show.
     */
    public void setFullPosterPathInfo(String fullPosterPathInfo) {
        this.fullPosterPathInfo = fullPosterPathInfo;
    }

    /**
     * Responsible for returning the backdrop poster path of the object.
     * @return the backdrop poster path of the movie/TV show.
     */
    public String getFullBackdropPathInfo() {
        return fullBackdropPathInfo;
    }

    /**
     * Repsonsible for setting the backdrop poster path of the object.
     * @param fullBackdropPathInfo the backdrop poster path of the movie/TV show.
     */
    public void setFullBackdropPathInfo(String fullBackdropPathInfo) {
        this.fullBackdropPathInfo = fullBackdropPathInfo;
    }

    /**
     * Responsible for getting the rating of the movie/TV show.
     * @return the rating of the movie/TV show.
     */
    public double getRatingInfo() {
        return ratingInfo;
    }

    /**
     * Responsible forsetting the rating of the movie/TV show.
     * @param ratingInfo the rating of the movie/TV show.
     */
    public void setRatingInfo(double ratingInfo) {
        this.ratingInfo = ratingInfo;
    }

    /**
     * Responsible for returning the genre IDs pertaining to the movie/TV show.
     * @return the genre IDs pertaining to the movie/TV show.
     */
    public ArrayList<Long> getGenreIdInfo() {
        return genreIdInfo;
    }

    /**
     * Responsible for setting the genre IDs pertaining to the movie/TV show.
     * @param genreIdInfo the genre IDs pertaining to the movie/TV show.
     */
    public void setGenreIdInfo(ArrayList<Long> genreIdInfo) {
        this.genreIdInfo = genreIdInfo;
    }

    /**
     * Responsible for returning whether the object contains adult content or not.
     * @return true if contains adult content and false otherwise.
     */
    public boolean isAdultContent() {
        return isAdultContent;
    }

    /**
     * Responsible for setting whether the object contains adult content or not.
     * @param adultContent true if contains adult content and false otherwise.
     */
    public void setAdultContent(boolean adultContent) {
        this.isAdultContent = adultContent;
    }

    /**
     * Responsible for getting the cert information about the movie/TV show.
     * @return the cert information about the movie/TV show.
     */
    public String getCertInfo() {
        return certInfo;
    }

    /**
     * Responsible for setting the cert information about the movie/TV show.
     * @param certInfo the cert information about the movie/TV show.
     */
    public void setCertInfo(String certInfo) {
        this.certInfo = certInfo;
    }

    /**
     * Responisible for returning the list of cast information pertaining to the movie/TV show.
     * @return the list of cast information pertaining to the movie/TV show.
     */
    public ArrayList<String> getCastInfo() {
        return castInfo;
    }

    /**
     * Responisible for setting the list of cast information pertaining to the movie/TV show.
     * @param castInfo list of cast information pertaining to the movie/TV show.
     */
    public void setCastInfo(ArrayList<String> castInfo) {
        this.castInfo = castInfo;
    }

    /**
     * Responsible for setting the root path for the movie/TV show posters and backdrop posters.
     * @param rootPath The root path for the poster images.
     * @param posterSize A string representing the size variant of the posters to download
     */
    public void setPosterRootPath(String rootPath, String posterSize, boolean isOffline) {
        if (isOffline) {
            fullPosterPathInfo = defaultImageFileName;
            fullBackdropPathInfo = defaultImageFileName;
        } else {
            fullPosterPathInfo = String.format("%s%s%s", rootPath, posterSize, posterPathInfo);
            fullBackdropPathInfo = String.format("%s%s%s", rootPath, posterSize, backdropPathInfo);
        }
    }

    public long getId() {
        return super.getId();
    }

    public String getMovieTitle() {
        return super.getTitle();
    }
}