package entertainment.pro.model;

import java.util.ArrayList;

/**
 * structure for Playlist object.
 */
public class Playlist {
    private String playlistName;
    private String description;
    private ArrayList<PlaylistMovieInfoObject> movies;

    /**
     * constructor for Playlist.
     * used for creating new playlist so there's only playlistName available
     */
    public Playlist(String playlistName) {
        this.playlistName = playlistName;
        description = "";
        movies = new ArrayList<>();
    }

    /**
     * constructor for Playlist.
     * used for loading Playlist from json files so all playlist attributes are available.
     */
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

    /**
     * to add movies to playlists.
     */
    public void add(ArrayList<PlaylistMovieInfoObject> movies) {
        this.movies.addAll(movies);
    }

    /**
     * to remove movies from playlist.
     */
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

    /**
     * to clear movies in playlist.
     */
    public void clear() {
        movies = new ArrayList<>();
    }
}