package entertainment.pro.storage.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.Playlist;
import entertainment.pro.model.PlaylistMovieInfoObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * class that deals with editing the Playlist.json file.
 */
public class EditPlaylistJson {
    private ObjectMapper mapper = new ObjectMapper();
    private File file;
    private JSONParser parser = new JSONParser();

    public EditPlaylistJson(String playlistName) {
        String fileName = "./" + playlistName + ".json";
        file = new File(fileName);
    }

    /**
     * to load Playlist object from playlist json file.
     */
    public Playlist load() throws IOException {
        if (file.length() != 0) {
            try {
                JSONObject playlist = (JSONObject) parser.parse(new FileReader(file));
                String playlistName = (String) playlist.get("playlistName");
                String description = (String) playlist.get("description");
                JSONArray movies = (JSONArray) playlist.get("movies");
                ArrayList<PlaylistMovieInfoObject> playlistMovies = new ArrayList<>();
                for (int i = 0; i < movies.size(); i++) {
                    JSONObject movie = (JSONObject) movies.get(i);
                    long movieID = (long) movie.get("id");
                    String movieTitle = (String) movie.get("title");
                    String movieReleaseDate = (String) movie.get("stringDate");
                    String movieSummary = (String) movie.get("summaryInfo");
                    String movieFullPosterPath = (String) movie.get("fullPosterPath");
                    String movieFullBackdropPath = (String) movie.get("fullBackdropPath");
                    double movieRating = (double) movie.get("ratingInfo");
                    JSONArray genreArray = (JSONArray) movie.get("genreIdInfo");
                    ArrayList<Long> movieGenreIDs = new ArrayList<>();
                    for (int j = 0; j < genreArray.size(); j++) {
                        movieGenreIDs.add((long) genreArray.get(j));
                    }
                    boolean adult = (boolean) movie.get("adultContent");
                    playlistMovies.add(new PlaylistMovieInfoObject(false, movieID, movieTitle, null,
                            movieSummary, movieRating, movieGenreIDs, movieFullPosterPath, movieFullBackdropPath,
                            false, movieReleaseDate));
                }
                return new Playlist(playlistName, description, playlistMovies);
            } catch (ParseException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void createPlaylist(Playlist playlist) throws IOException {
        file.createNewFile();
        mapper.writeValue(file, playlist);
    }

    public void deletePlaylist() {
        file.delete();
    }

    public void editPlaylist(Playlist playlist) throws IOException {
        mapper.writeValue(file, playlist);
    }

    /**
     * to rename the corresponding json file to the new playlist name.
     */
    public void renamePlaylist(Playlist playlist, String newName) throws IOException {
        editPlaylist(playlist);
        String fileName = "./" + newName + ".json";
        File newFile = new File(fileName);
        file.renameTo(newFile);
    }
}
