package duke.commons;

import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Utility {
    public static void checkParameters(Map<String, List<String>> parameters, String[] acceptedParameters, Boolean hasPrimary, Boolean hasSecondary)
            throws DukeException {
        if (!hasPrimary && parameters.keySet().contains("primary")) {
            throw new DukeException("Invalid parameter: " + "primary");
        }

        if (!hasSecondary && parameters.keySet().contains("secondary")) {
            throw new DukeException("Invalid parameter: " + "secondary");
        }

        Set<String> acceptedSet = new HashSet<String>(Arrays.asList(acceptedParameters));
        acceptedSet.add("line");
        acceptedSet.add("primary");
        acceptedSet.add("secondary");
        acceptedSet.add("cmd");
        for (String key : parameters.keySet()) {
            if (!acceptedSet.contains(key)) {
                throw new DukeException("Invalid parameter: " + key);
            }
        }
    }
}
