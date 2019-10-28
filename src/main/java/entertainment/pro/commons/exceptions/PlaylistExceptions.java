package entertainment.pro.commons.exceptions;

import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.Playlist;
import entertainment.pro.model.PlaylistMovieInfoObject;
import entertainment.pro.model.UserProfile;
import entertainment.pro.ui.MovieHandler;

public class PlaylistExceptions extends Exceptions {
    public PlaylistExceptions(String message) {
        super(message);
    }

    /**
     * to check whether playlist with that name exists.
     * command: create / set -n
     * @throws PlaylistExceptions when that name already belongs to an existing playlist
     */
    public static void checkNameExist(String name, UserProfile userProfile) throws PlaylistExceptions {
        for (String log : userProfile.getPlaylistNames()) {
            if (name.equals(log)) {
                throw new PlaylistExceptions("ohno u already have a playlist with the same name :( "
                        + "pls try again with another name for this playlist");
            }
        }
    }

    /**
     * to check whether a particular movie can be added to playlist.
     * command: add -m
     * @throws PlaylistExceptions when movie already belongs to playlist
     */
    public static void checkMovieForAdd(MovieInfoObject movie, Playlist playlist) throws PlaylistExceptions {
        boolean flag = false;
        for (PlaylistMovieInfoObject log : playlist.getMovies()) {
            if (movie.getId() == log.getId()) {
                flag = true;
                break;
            }
        }
        if (flag) {
            throw new PlaylistExceptions("ohno the movie < " + movie.getMovieTitle()
                    + " > has already been added to this playlist :( pls try again with another movie");
        }
    }

    /**
     * to check whether a particular movie can be removed from playlist.
     * command: remove -m
     * @throws PlaylistExceptions when movie does not currently belong to playlist
     */
    public static void checkMovieForRemove(MovieInfoObject movie, Playlist playlist) throws PlaylistExceptions {
        boolean flag = true;
        for (PlaylistMovieInfoObject log : playlist.getMovies()) {
            if (movie.getId() == log.getId()) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new PlaylistExceptions("ohno the movie < " + movie.getMovieTitle()
                    + " > does not belong to this playlist :( pls try again with another movie");
        }
    }

    /**
     * to check whether playlist name in playload exists.
     * command: set / add / remove / clear / delete
     * @throws PlaylistExceptions when payload playlist does not exist
     */
    public static void checkPayloadPlaylist(String name, UserProfile userProfile) throws PlaylistExceptions {
        boolean flag = true;
        for (String log : userProfile.getPlaylistNames()) {
            if (name.equals(log)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            throw new PlaylistExceptions("ohno the playlist < " + name
                    + " > does not exist :( pls try again with another playlist");
        }
    }

    /**
     * to check whether the index indicated is out of range.
     * command: add -m / remove -m
     * @throws PlaylistExceptions when index is out of bounds
     */
    public static void checkIndex(int i, int size) throws PlaylistExceptions {
        if ((i < 0) || (i >= size)) {
            throw new PlaylistExceptions("ohno the index < " + i
                    + "> does not exist :( pls try again with another index");
        }
    }
}
