package duke.parser.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Stores mapping of tokens to their respective arguments.
 * Each key may be associated with multiple argument values.
 * Values for a given key are stored in a list, and the insertion ordering is maintained.
 * Keys are unique, but the list of argument values may contain duplicate argument values, i.e. the same argument value
 * can be inserted multiple times for the same token.
 */
public class MapTokensToArguments {
    private final Map<Token, List<String>> mapTokensToArguments = new HashMap<>();
    private static final String EMPTY_STRING = "";

    /**
     * Maps a given string to its corresponding token.
     * In this implementation, token is the key for the mapping.
     * @param token stores the token.
     * @param args stores the arguments mapped to a given key.
     */
    public void setMapping(Token token, String args) {
        List<String> arguments = getAllValues(token);
        arguments.add(args);
        mapTokensToArguments.put(token, arguments);
    }

    /**
     * Finds and stores all the arguments mapped to a token.
     * There can be multiple arguments for the same token.
     * @param token stores the token/key for the mapping.
     * @return a list of all the arguments mapped to the token.
     */
    public List<String> getAllValues(Token token) {

        if (!mapTokensToArguments.containsKey(token)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(mapTokensToArguments.get(token));
    }

    /**
     * Finds the argument that was mapped to the token.
     * @param token stores the token
     * @return the argument mapped to the token
     */
    public Optional<String> getValue(Token token) {
        /*In case of multiple arguments related to a prefix
          this function will return the last argument associated with that token.
          Optional accounts for the case when the user does not input anything as argument
         */
        List<String> arguments = getAllValues(token);
        return arguments.isEmpty() ? Optional.empty() : Optional.of(arguments.get(arguments.size() - 1));
    }

    /**
     * Returns the text between the command word and the first valid token.
     * Trims any leading/trailing spaces.
     */
    public String getTextBeforeFirstToken() {
        return getValue(new Token(EMPTY_STRING)).orElse(EMPTY_STRING);
    }
}
