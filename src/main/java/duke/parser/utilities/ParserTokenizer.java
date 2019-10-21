package duke.parser.utilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParserTokenizer {

    /**
     * This function is responsible for mapping tokens with their valid arguments.
     * @param args stores the user input
     * @param tokens stores the tokens that are expected in the user input
     * @return mapping of tokens to arguments
     */
    public static MapTokensToArguments tokenize(String args, Token... tokens) {
        /*The triple dot notation is used for varArgs. It means that you can pass variable number
          of arguments to a function.
         */
        List<PositionOfToken> tokenPositions = findAllTokenPositions(args, tokens);
        return getMapping(args,tokenPositions);
    }

    private static List<PositionOfToken> findAllTokenPositions(String args, Token... tokens) {
        /*Streams represents a sequence of objects from the source that can be aggregated
          They can be used to achieve SQL-like queries. Have a look at the tutorial provided
          in cs2113/T website
         */
        return Arrays.stream(tokens)
                .flatMap(token -> findTokenPositions(args,token).stream())
                .collect(Collectors.toList());
    }

    private static List<PositionOfToken> findTokenPositions(String args, Token token) {
        List<PositionOfToken> positions = new ArrayList<>();

        int tokenPos = findTokenPosition(args, token.getToken(), 0);
        while (tokenPos != -1) {
            PositionOfToken extendedToken = new PositionOfToken(token, tokenPos);
            positions.add(extendedToken);
            tokenPos = findTokenPosition(args, token.getToken(), tokenPos);
        }

        return positions;
    }

    private static int findTokenPosition(String args, String token, int startFromIndex) {
        int tokenIndex = args.indexOf(" " + token,startFromIndex);
        return tokenIndex == -1 ? -1 : tokenIndex + 1; //tokenIndex + 1 offsets for the whitespace
    }

    private static MapTokensToArguments getMapping(String args, List<PositionOfToken> positionOfTokens) {
        //Sorting is required as we are trying to implement friendlier syntax where the order of tokens
        //does not matter
        positionOfTokens.sort((token1,token2) -> token1.getStartPosition() - token2.getStartPosition());
        //Add a dummy start position so that it can mark the start of extracting and mapping arguments
        PositionOfToken startPosition = new PositionOfToken(new Token(""),0);
        positionOfTokens.add(0,startPosition);
        //Add a dummy end position so that it can extract the last token and map arguments to it
        PositionOfToken endPosition = new PositionOfToken(new Token(""),args.length());
        positionOfTokens.add(endPosition);

        MapTokensToArguments mapTokensToArguments = new MapTokensToArguments();
        for (int i = 0; i < positionOfTokens.size() - 1; i++) {
            Token currentToken = positionOfTokens.get(i).getToken();
            //The mapping takes place by considering everything between the two tokens as argument
            String argumentForCurrentToken = extractArgument(args,positionOfTokens.get(i),
                    positionOfTokens.get(i + 1));
            mapTokensToArguments.setMapping(currentToken,argumentForCurrentToken);
        }
        return mapTokensToArguments;
    }

    private static String extractArgument(String arg, PositionOfToken currentPos,
                                          PositionOfToken nextPos) {
        Token token = currentPos.getToken();
        int startIndex = currentPos.getStartPosition() + token.getToken().length();
        return arg.substring(startIndex,nextPos.getStartPosition()).trim();
    }
}