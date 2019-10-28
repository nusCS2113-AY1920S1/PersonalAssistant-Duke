package entertainment.pro.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaylistMovieInfoObject extends MovieInfoObject {
    private String stringDate;

    /**
     * Construct info about a movie/tv show.
     *
     * @param id           id stored in the api.
     * @param title        Title of the movie/tv show.
     * @param date         Release date of the movie/tv show.
     * @param summary      Plot summary of the movie/tv show.
     * @param rating       Rating of the movie or tv show.
     * @param genreIDs     Array of numbers that contains the genres belonging to the movie/tv show extracted from api.
     * @param posterPath   Filepath of the movie/tv show poster.
     * @param backdropPath Filepath of the movie/tv show backdrop poster.
     */
    public PlaylistMovieInfoObject(boolean isMovie, long id, String title, Date date, String summary, double rating,
                                   long[] genreIDs, String posterPath, String backdropPath, boolean isAdult,
                                   String stringDate) {
        super(isMovie, id, title, date, summary, rating, genreIDs, posterPath, backdropPath, isAdult);
        this.stringDate = stringDate;
        if (super.getReleaseDate() == null) {
            convertStringToDate();
        }
        if (stringDate == null) {
            convertDateToString();
        }
    }

    public PlaylistMovieInfoObject(boolean isMovie, long id, String title, String stringDate) {
        super(isMovie, id, title);
        this.stringDate = stringDate;
    }


    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    private void convertStringToDate() {
        try {
            super.setMovieReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(stringDate));
        } catch (ParseException e) {
            System.out.println("cant convert date");
        }
    }

    private void convertDateToString() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        stringDate = dateFormat.format(super.getReleaseDate());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        PlaylistMovieInfoObject playlistMovieInfoObject = (PlaylistMovieInfoObject) object;
        if (playlistMovieInfoObject.getID() == super.getID()) {
            return true;
        }
        return false;
    }
}
