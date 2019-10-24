package ui;

import dictionary.Word;
import dictionary.WordBank;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Stack;

/**
 * Represents the object that displays prompts and feedback from the system to the user's commands.
 */
public class Ui {

    /**
     * Greets users when they open the app.
     * @return a greeting string
     */
    public String greet() {
        return ("\n                      |   | _ _ _|   /  \\ _  \n"
                + "                      |/\\|(_)| (_|  \\__/|_) \n"
                + "                                            |   \n\n"
                + "Welcome, what would you like to do today?"
            );
    }

    /**
     * Greets user when they move to quiz scene.
     * @return a greeting string
     */
    public String quizGreet() {
        return ("\n                      |   | _ _ _|   /  \\ _  \n"
                    + "                      |/\\|(_)| (_|  \\__/|_) \n"
                + "                                            |   \n"
                + "Let's do some quiz to enhance your word knowledge \n"
                + "Type \"start\" to begin quiz or \"exit_quiz\" to go back"
            );
    }

    public String showDeleted(Word w) {
        return "Noted. I've removed this word:\n" + w.toString();
    }

    public String showAdded(Word w) {
        return "Got it. I've added this word:\n" + w.toString();
    }

    public String showEdited(Word w) {
        return "Got it. I've edited this word:\n" + w.toString();
    }

    /**
     * Shows the tags to be added.
     * @param word word to add tag
     * @param tags list of tags to be added
     * @param tagList hash set represents existed tags of the word
     * @return a string shown when command is completed
     */
    public String showAddTag(String word, ArrayList<String> tags, HashSet<String> tagList) {
        String returnedString = "I have added " + (tags.size() == 1 ? "this tag \"" + tags.get(0) + "\"" : "these tags")
                + " to word \"" + word + "\"" + "\n";
        returnedString += "Here " + (tagList.size() == 1 ? "is the tag " : "are the tags ")
                + "of word \"" + word + "\"" + "\n";
        StringBuilder stringBuilder = new StringBuilder();
        for (String tag : tagList) {
            stringBuilder.append(tag + "\n");
        }
        return returnedString + stringBuilder.toString();
    }

    /**
     * Shows the list of all words in the word bank.
     * @param wordBank to store all words
     * @param order order to show words (ascending / descending)
     * @return a string shown when command is completed
     */
    public String showList(WordBank wordBank, String order) {
        String returnedString = "Here are your words:\n";
        if (order.equals("asc") || order.equals("")) {
            for (Map.Entry<String, Word> entry : wordBank.getWordBank().entrySet()) {
                returnedString += entry.getValue() + "\n";
            }
        } else {
            for (String description : wordBank.getWordBank().descendingKeySet()) {
                returnedString += wordBank.getWordBank().get(description) + "\n";
            }
        }
        return returnedString;
    }

    /**
     * Shows completion when tags are deleted from words.
     * @param word word to be deleted tags
     * @param deletedTags list containing tags to delete from word
     * @return a string shown when command is completed
     */
    public String showDeletedTags(String word, ArrayList<String> deletedTags) {
        StringBuilder stringBuilder = new StringBuilder();
        if (deletedTags.size() > 0) {
            stringBuilder.append("I have removed " + (deletedTags.size() == 1 ? "this tag " : "these tags ")
                    + "from the word \"" + word + "\"" + "\n");
            for (String tag : deletedTags) {
                stringBuilder.append(tag + "\n");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Shows non-existing tags of the words that are searched.
     * @param word a string represents a word to be searched for tags
     * @param nullTags list of non-existing tags that are searched
     * @return a string to show all non-existing tags
     */
    public String showNullTags(String word, ArrayList<String> nullTags) {
        StringBuilder stringBuilder = new StringBuilder();
        if (nullTags.size() > 0) {
            stringBuilder.append((nullTags.size() == 1 ? "This tag " : "These tags ")
                    + "doesn't exist in the word \"" + word + "\"" + "\n");
            for (String tag : nullTags) {
                stringBuilder.append(tag + "\n");
            }
        }
        return stringBuilder.toString();
    }

    public String showSearch(String description, String meaning) {
        return ("Here is the meaning of " + description + ": " + meaning);
    }

    /**
     * Shows a list of words ordered by their search count in ascending or descending order as specified by the user.
     * @param wordBank a main class object containing the word bank content
     * @param order the order (asc/desc) in which to display the word list
     * @return a string to show list of words and their search count
     */
    public String showSearchFrequency(WordBank wordBank, String order) {
        TreeMap<Integer, TreeMap<String, Word>> wordCount = wordBank.getWordCount(); //get map ordered by word count
        String returnedString = "You have searched for these words ";
        if (order.equals("asc") || order.equals("")) { //list in ascending order
            returnedString += "least:\n";
            for (Map.Entry<Integer, TreeMap<String, Word>> entry : wordCount.entrySet()) {
                returnedString += entry.getKey() + " searches -\n";
                for (Map.Entry<String, Word> word : entry.getValue().entrySet()) {
                    returnedString += word.getKey() + "\n";
                }
            }
        } else { //list in descending order
            returnedString += "most:\n";
            for (Integer searchCount : wordCount.descendingKeySet()) {
                returnedString += searchCount + " searches -\n";
                for (Map.Entry<String, Word> word : wordCount.get(searchCount).entrySet()) {
                    returnedString += word.getKey() + "\n";
                }
            }
        }
        return returnedString;
    }

    /**
     * Shows a string to inform the completion when user look for search history.
     * @param wordHistory stack containing the closest searches
     * @param numberOfWordsToDisplay number of closest searched words to display
     * @return a string shown when command is completed
     */
    public String showHistory(Stack<Word> wordHistory, int numberOfWordsToDisplay) {
        int numberOfWords;
        String s = "";
        if (numberOfWordsToDisplay > wordHistory.size()) {
            s += "The number of words requested exceeds the number of words in your word bank.\n";
            numberOfWords = wordHistory.size();
        } else {
            numberOfWords = numberOfWordsToDisplay;
        }
        s += ("Here are the last " + numberOfWords + " words you have added:\n");
        for (int i = 0; i < numberOfWords; i++) {
            s += wordHistory.peek() + "\n";
            wordHistory.pop();
        }
        return s;
    }

    /**

     * Shows a string containing description and format of a specific instruction.
     * @param instruction name of the instruction which user wants to know more
     * @return a string to show manual of specific instruction
     */
    public String showHelp(String instruction) {
        if (instruction.equals("add")) {
            return "Add a word to wordbank.\n"
                    + "Format: add w/WORD w/MEANING [t/TAG]";
        } else if (instruction.equals("delete")) {
            return "Delete a word or tag from wordbank.\n"
                    + "Format: delete w/WORD_TO_BE_DELETED [t/TAG]";
        } else if (instruction.equals("exit")) {
            return "Exit WordUp.\n"
                    + "Format: exit";
        } else if (instruction.equals("search")) {
            return "Search the meaning of a specific word.\n"
                    + "Format: search w/WORD_TO_BE_SEARCHED";
        } else if (instruction.equals("list")) {
            return "Show the list of words in wordbank.\n"
                    + "Format: list [o/ORDER]\n"
                    + "(ORDER can be \"asc\" for ascending and \"desc\" for descending)";
        } else if (instruction.equals("history")) {
            return "View recent search history.\n"
                    + "Format: history {int value}";
        } else if (instruction.equals("freq")) {
            return "Show search frequency of each word.\n"
                    + "Format: freq [o/ORDER]\n"
                    + "(ORDER can be \"asc\" for ascending and \"desc\" for descending)";
        } else if (instruction.equals("edit")) {
            return "Edit the meaning of word.\n"
                    + "Format: edit w/WORD m/MEANING";
        } else if (instruction.equals("tag")) {
            return "Add tags of a specific word.\n"
                    + "Format: tag w/WORD t/TAG...";
        } else if (instruction.equals("quiz")) {
            return "Take a quiz to test yourself.\n"
                    + "Format: quiz";
        } else {
            return "Here are the instructions for WordUp.\n"
                    + "add, delete, edit, exit, freq, help, history, list, search, tag, quiz\n"
                    + "Enter \"help [instruction]\" for more details.";
        }
    }

    /**
     * Displays quiz to ask user.
     * @param question word to be asked for meaning
     * @param options options available to be chosen
     * @param optionSequence sequence of the options
     * @return a string shown when the command is completed
     */
    public String quizDisplay(String question, String[] options, int optionSequence){
        String s = ("What is the meaning of " + question +"?\n");
        int index=1;
        for(int i=optionSequence; i<optionSequence+4; i++){
            s += (index + "." + options[i%4] + "  ");
            index++;
        }
        s += "\n";
        return s;
    }

    /**
     * Shows respond of bot when user input the answer.
     * @param isCorrect is true if user get the correct answer
     * @param answer correct answer
     * @return a string shown when the command is completed
     */
    public String quizResponse(Boolean isCorrect, String answer) {
        if (isCorrect) {
            return ("Yes!! The correct answer is \"" + answer + "\".");
        } else {
            return ("Sorry, The answer is \"" + answer + "\".");
        }
    }

    public String quizIncorrect(Integer wrongQuiz, Integer countQuiz, ArrayList<String> quizArray){
        if(wrongQuiz==0){
            return ("Congratulations! You got " + (countQuiz-wrongQuiz) + "/" + countQuiz +" on this quiz!\n" +
                    "type exit_quiz to exit.");
        }
        else{
            return ("You got " + (countQuiz-wrongQuiz) + "/" + countQuiz +" on this quiz!\n" +
                    "These are the words you might want to review:\n" +
                    quizArray +
                    "\ntype exit_quiz to exit.");
        }
    }
}

