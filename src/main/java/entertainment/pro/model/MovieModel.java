//@@author pdotdeep
package entertainment.pro.model;


/**
 * Movie Model wrapper.
 */
public class MovieModel {

    private long id;
    private String title;

    /**
     * construction for moviemodel.
     * @param id the id of the movie/TV show stored in the MovieDB API.
     * @param title the title of the movie or TV show stored in the MovieDB API.
     */
    public MovieModel(long id, String title) {
        this.id = id;
        this.title = title;
    }

    /**
     * Responsible for returning the id of the movie/TV show.
     * @return the id of the movie/TV show.
     */
    public long getId() {
        return id;
    }

    /**
     * Responsible for setting the id of the movie/TV show.
     * @param id the id of the movie/TV show to be set.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Responsible for returning the title of the movie/TV show.
     * @return the title of the movie/TV show.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Responsible for setting the title of the movie/TV show.
     * @param title thhe title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
