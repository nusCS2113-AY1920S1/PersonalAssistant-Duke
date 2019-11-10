package duke.parser.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tokenizes arguments string of the form: {@code textBeforeToken <token>value <prefix>value ...}<br>
 *     e.g. {@code some random text t/ 11.00 t/12.00 k/ m/ July}  where tokens are {@code t/ k/ m/}.<br>
 * 1. An argument's value can be an empty string e.g. the value of {@code k/} in the above example.<br>
 * 2. Leading and trailing whitespaces of an argument value will be discarded.<br>
 * 3. An argument may be repeated and all its values will be accumulated e.g. the value of {@code t/}
 *    in the above example.<br>
 */
public class ParserTokenizer {

    private static final int START_POSITION = 0;
    private static final int INVALID_INDEX = -1;

    /**
     * Maps tokens with their valid arguments.
     * @param args stores the user input
     * @param tokens stores the tokens that are expected in the user input
     * @return mapping of tokens to arguments
     */
    public static MapTokensToArguments tokenize(String args, Token... tokens) {
        //varArgs allow for variable number of arguments passed to a function.
        List<PositionOfToken> tokenPositions = findAllTokenPositions(args, tokens);
        return getMapping(args, tokenPositions);
    }

    /**
     * Finds all zero-based token positions in the given arguments string.
     * @param args stores the arguments associated with the tokens.
     * @param tokens tokens to find in the arguments string.
     * @return list of zero based token positions in the given string.
     */
    private static List<PositionOfToken> findAllTokenPositions(String args, Token... tokens) {

        return Arrays.stream(tokens)
                .flatMap(token -> findTokenPositions(args, token).stream())
                .collect(Collectors.toList());
    }

    private static List<PositionOfToken> findTokenPositions(String args, Token token) {
        List<PositionOfToken> positions = new ArrayList<>();

        int tokenPos = findTokenPosition(args, token.getToken(), START_POSITION);
        while (tokenPos != INVALID_INDEX) {
            PositionOfToken extendedToken = new PositionOfToken(token, tokenPos);
            positions.add(extendedToken);
            tokenPos = findTokenPosition(args, token.getToken(), tokenPos);
        }

        return positions;
    }

    /**
     * Returns the index of the first occurrence of a token in the string arguments
     * starting from index {@code startFromIndex}. Returns -1 if no such occurrence are
     * found.
     * @param args the argument string in which the token is to be looked for.
     * @param token the token to find.
     * @param startFromIndex the index from which the search begins.
     * @return the index of the first occurence.
     */
    private static int findTokenPosition(String args, String token, int startFromIndex) {
        int tokenIndex = args.indexOf(" " + token, startFromIndex);
        return tokenIndex == INVALID_INDEX ? INVALID_INDEX : tokenIndex + 1; //tokenIndex + 1 offsets for the whitespace
    }

    /**
     * Extracts the tokens and their arguments and returns a mapping of the tokens with their
     * respective arguments. Tokens are extracted based on their zero-based positions.
     * @param args argument string.
     * @param positionOfTokens zero-based index positions of all the tokens.
     * @return MapTokensToArguments object that maps tokens to their arguments.
     */
    private static MapTokensToArguments getMapping(String args, List<PositionOfToken> positionOfTokens) {
        //Sorting is required as we are trying to implement friendlier syntax where the order of tokens
        //does not matter
        positionOfTokens.sort((token1,token2) -> token1.getStartPosition() - token2.getStartPosition());
        //Add a dummy start position so that it can mark the start of extracting and mapping arguments
        PositionOfToken startPosition = new PositionOfToken(new Token(""), START_POSITION);
        positionOfTokens.add(START_POSITION, startPosition);
        //Add a dummy end position so that it can extract the last token and map arguments to it
        PositionOfToken endPosition = new PositionOfToken(new Token(""), args.length());
        positionOfTokens.add(endPosition);

        MapTokensToArguments mapTokensToArguments = new MapTokensToArguments();
        for (int i = 0; i < positionOfTokens.size() - 1; i++) {
            Token currentToken = positionOfTokens.get(i).getToken();
            //The mapping takes place by considering everything between the two tokens as argument
            String argumentForCurrentToken = extractArgument(args, positionOfTokens.get(i),
                    positionOfTokens.get(i + 1));
            mapTokensToArguments.setMapping(currentToken, argumentForCurrentToken);
        }
        return mapTokensToArguments;
    }

    /**
     * Returns the trimmed value of the argument between the specified start and end position.
     * @param arg the argument string.
     * @param currentPos specifies the start position.
     * @param nextPos specifies the end position of the value.
     * @return the trimmed value of argument.
     */
    private static String extractArgument(String arg, PositionOfToken currentPos,
                                          PositionOfToken nextPos) {
        Token token = currentPos.getToken();
        int startIndex = currentPos.getStartPosition() + token.getToken().length();
        return (arg.substring(startIndex,nextPos.getStartPosition()).trim());
    }

}