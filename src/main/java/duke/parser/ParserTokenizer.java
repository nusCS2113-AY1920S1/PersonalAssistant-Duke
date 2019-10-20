package duke.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParserTokenizer {

    /**
     * This function is responsible for mapping prefixes with their valid arguments.
     * @param args stores the user input
     * @param prefixes stores the prefixes that are expected in the user input
     * @return mapping of prefixes to arguments
     */
    public static MapPrefixesToArguments tokenize(String args,Prefix... prefixes) {
        /*The triple dot notation is used for varArgs. It means that you can pass variable number
          of arguments to a function.
         */
        List<PositionOfPrefix> prefixPositions = findAllPrefixPositions(args, prefixes);
        return getMapping(args,prefixPositions);
    }

    private static List<PositionOfPrefix> findAllPrefixPositions(String args,Prefix... prefixes) {
        /*Streams represents a sequence of objects from the source that can be aggregated
          They can be used to achieve SQL-like queries. Have a look at the tutorial provided
          in cs2113/T website
         */
        return Arrays.stream(prefixes)
                .flatMap(prefix -> findPrefixPositions(args,prefix).stream())
                .collect(Collectors.toList());
    }

    private static List<PositionOfPrefix> findPrefixPositions(String args, Prefix prefix) {
        List<PositionOfPrefix> positions = new ArrayList<>();

        int prefixPos = findPrefixPosition(args, prefix.getPrefix(), 0);
        while (prefixPos != -1) {
            PositionOfPrefix extendedPrefix = new PositionOfPrefix(prefix, prefixPos);
            positions.add(extendedPrefix);
            prefixPos = findPrefixPosition(args, prefix.getPrefix(), prefixPos);
        }

        return positions;
    }

    private static int findPrefixPosition(String args,String prefix,int startFromIndex) {
        int prefixIndex = args.indexOf(" " + prefix,startFromIndex);
        return prefixIndex == -1 ? -1 : prefixIndex + 1; //prefixIndex + 1 offsets for the whitespace
    }

    private static MapPrefixesToArguments getMapping(String args, List<PositionOfPrefix> prefixPositions) {
        //Sorting is required as we are trying to implement friendlier syntax where the order of prefixes
        //does not matter
        prefixPositions.sort((prefix1,prefix2) -> prefix1.getStartPosition() - prefix2.getStartPosition());
        //Add a dummy start position so that it can mark the start of extracting and mapping arguments
        PositionOfPrefix startPosition = new PositionOfPrefix(new Prefix(""),0);
        prefixPositions.add(0,startPosition);
        //Add a dummy end position so that it can extract the last token and map arguments to it
        PositionOfPrefix endPosition = new PositionOfPrefix(new Prefix(""),args.length());
        prefixPositions.add(endPosition);

        MapPrefixesToArguments mapPrefixesToArguments = new MapPrefixesToArguments();
        for (int i = 0; i < prefixPositions.size() - 1; i++) {
            Prefix currentPrefix = prefixPositions.get(i).getPrefix();
            //The mapping takes place by considering everything between the two prefixes as argument
            String argumentForCurrentPrefix = extractArgument(args,prefixPositions.get(i),
                    prefixPositions.get(i + 1));
            mapPrefixesToArguments.setMapping(currentPrefix,argumentForCurrentPrefix);
        }
        return mapPrefixesToArguments;
    }

    private static String extractArgument(String arg, PositionOfPrefix currentPos,
                                          PositionOfPrefix nextPos) {
        Prefix prefix = currentPos.getPrefix();
        int startIndex = currentPos.getStartPosition() + prefix.getPrefix().length();
        return arg.substring(startIndex,nextPos.getStartPosition()).trim();
    }
}