package entertainment.pro.model;

import java.util.ArrayList;

/**
 * structure for Playlist object.
 */
public class Playlist {
    private final String playlistName;
    private final String description;
    private final ArrayList<PlaylistMovieInfoObject> movies;

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

    public Playlist setPlaylistName(String playlistName) {
        return new Playlist(playlistName, this.getDescription(), this.getMovies());
    }

    public String getDescription() {
        return description;
    }

    public Playlist setDescription(String description) {
        return new Playlist(this.getPlaylistName(), description, this.getMovies());
    }

    public Playlist setBoth(String name, String description) {
        return new Playlist(name, description, this.getMovies());
    }

    public ArrayList<PlaylistMovieInfoObject> getMovies() {
        return movies;
    }

    public Playlist setMovies(ArrayList<PlaylistMovieInfoObject> movies) {
        return new Playlist(this.getPlaylistName(), this.getDescription(), movies);
    }

    /**
     * to add movies to playlists.
     */
    public Playlist add(ArrayList<PlaylistMovieInfoObject> movies) {
        ArrayList<PlaylistMovieInfoObject> newMovies = this.getMovies();
        newMovies.addAll(movies);
        return new Playlist(this.getPlaylistName(), this.getDescription(), newMovies);
    }

    /**
     * to remove movies from playlist.
     */
    public Playlist remove(ArrayList<PlaylistMovieInfoObject> movies) {
        ArrayList<PlaylistMovieInfoObject> newMovies = this.getMovies();
        newMovies.removeAll(movies);
        return new Playlist(this.getPlaylistName(), this.getDescription(), newMovies);
    }

    /**
     * to clear movies in playlist.
     */
    public Playlist clear() {
        return new Playlist(this.getPlaylistName(), this.getDescription(), new ArrayList<>());
    }
}