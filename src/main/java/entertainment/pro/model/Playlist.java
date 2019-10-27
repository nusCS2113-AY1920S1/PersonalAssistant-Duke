package entertainment.pro.model;

import java.util.ArrayList;

/**
 * structure for Playlist object
 */
public class Playlist {
    private String playlistName;
    private String description;
    private ArrayList<PlaylistMovieInfoObject> movies;

    public Playlist(String playlistName) {
        this.playlistName = playlistName;
        description = "";
        movies = new ArrayList<>();
    }

    public Playlist(String playlistName, String description, ArrayList<PlaylistMovieInfoObject> movies) {
        this.playlistName = playlistName;
        this.description = description;
        this.movies = movies;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<PlaylistMovieInfoObject> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<PlaylistMovieInfoObject> movies) {
        this.movies = movies;
    }

    public void add(ArrayList<PlaylistMovieInfoObject> movies) {
        this.movies.addAll(movies);
    }

    public void remove(ArrayList<PlaylistMovieInfoObject> movies) {
        for (PlaylistMovieInfoObject log : this.movies) {
            System.out.println(log.getTitle() + "hehe");
        }
        for (PlaylistMovieInfoObject log : movies) {
            System.out.println(log.getTitle() + "ew");
        }
        this.movies.removeAll(movies);
        for (PlaylistMovieInfoObject log : this.movies) {
            System.out.println(log.getTitle());
        }
    }

    public void clear() {
        movies = new ArrayList<>();
    }
}