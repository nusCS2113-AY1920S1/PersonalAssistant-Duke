package duke.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MapPrefixesToArguments {
    private final Map<Prefix, List<String>> mapPrefixesToArguments = new HashMap<>();

    /**
     * This function is used for mapping a given string to its corresponding prefix.
     * In this implementation, prefix is the key for the mapping
     * @param prefix stores the prefix
     * @param args stores the arguments mapped to a given key
     */
    public void setMapping(Prefix prefix, String args) {
        List<String> arguments = getAllValues(prefix);
        arguments.add(args);
        mapPrefixesToArguments.put(prefix, arguments);
    }

    /**
     * This function is used to get all the arguments mapped to a prefix.
     * As per the current implementation, we can have multiple arguments for the
     * same prefix.
     * @param prefix stores the prefix/key for the mapping
     * @return a list of all the arguments mapped to the prefix
     */
    public List<String> getAllValues(Prefix prefix) {

        if (!mapPrefixesToArguments.containsKey(prefix)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(mapPrefixesToArguments.get(prefix));
    }

    /**
     * This function is used to get the argument that was mapped to the prefix.
     * @param prefix stores the prefix
     * @return the argument mapped to the prefix
     */
    public Optional<String> getValue(Prefix prefix) {
        /*In case of multiple arguments related to a prefix
          this function will return the last argument associated with that token.
          Optional accounts for the case when the user does not input anything as argument
         */
        List<String> arguments = getAllValues(prefix);
        return arguments.isEmpty() ? Optional.empty() : Optional.of(arguments.get(arguments.size() - 1));
    }

    public String getTextBeforeFirstPrefix() {
        return getValue(new Prefix("")).orElse("");
    }
}
