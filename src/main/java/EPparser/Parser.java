//package EPparser;
//
//import movieRequesterAPI.RetrieveRequest;
//
//public class Parser {
//    private String userInput;
//    private Methods method;
//    private RetrieveRequest mMovieRequest;
//
//    public Parser(String userInput){
//        this.userInput = userInput;
//    }
//
//
////    @FXML private void searchButtonClicked() throws IOException {
////        String userInput = mSearchTextField.getText();
////        //for setting up profile preferences
////        String[] tokens = userInput.split((" "), 3);
////        if (tokens.length == 3) {
////            Commands command = new Commands();
////            if (tokens[0].equals("set") && tokens[1].equals("name")) {
////                command.setName(tokens[2]);
////                clearText(mSearchTextField);
////                initialize();
////            } else if (tokens[0].equals("set") && tokens[1].equals("age")) {
////                command.setAge(tokens[2]);
////                clearText(mSearchTextField);
////                initialize();
////            } else if (tokens[0].equals("set") && tokens[1].equals("preference")) {
////                command.setPreference(tokens[2]);
////                clearText(mSearchTextField);
////                initialize();
////            }
////        }
////        //for searching movies
////        else if (userInput.equals("show current movie")) {
////            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.NOW_SHOWING);
////        } else if (userInput.equals("show upcoming movie")) {
////            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.UPCOMING);
////        } else if (userInput.equals("show popular movie")) {
////            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.POPULAR);
////        } else if (userInput.equals("show current tv")) {
////            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.TV_SHOWS);
////        } else if (userInput.contains(" -g ")) {
////
////        } else if (!userInput.isEmpty()) {
////            mMovieRequest.beginSearchRequest(userInput);
////        }
////    }
//
//    public Methods parse(){
//        String[] tokens =  userInput.split((" "), 3);
//        if (tokens.length == 3){
//            parseTokens(tokens);
//        } else{
//            if (userInput.equals("show current movie")) {
//                mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.NOW_SHOWING);
//            } else if (userInput.equals("show upcoming movie")) {
//                mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.UPCOMING);
//            } else if (userInput.equals("show popular movie")) {
//                mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.POPULAR);
//            } else if (userInput.equals("show current tv")) {
//                mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.TV_SHOWS);
//            } else if (userInput.contains(" -g ")) {
//
//            } else if (!userInput.isEmpty()) {
//                mMovieRequest.beginSearchRequest(userInput);
//            }
//        }
//    }
//
//
//}
