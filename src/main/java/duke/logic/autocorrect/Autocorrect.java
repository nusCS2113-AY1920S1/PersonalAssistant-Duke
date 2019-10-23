package duke.logic.autocorrect;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * This is a class that will help to correct minor typo.
 */

public class Autocorrect {
    private String word;
    private HashMap<String,int[]> mapper = new HashMap();
    private ArrayList<String> words = new ArrayList();
    private int[] counter = new int[26];

    public Autocorrect() {
    }

    /**
     * This is a function that will store the pre defined correct words into the object.
     * @param word pre defined correct words to be stored.
     */
    public void load(String word) {
        int[] counting = new int[26];
        for (int i = 0; i < 26; i += 1) {
            counting[i] = 0;
        }
        for (int i = 0; i < word.length(); i += 1) {
            counting[word.charAt(i) - 97] += 1;
        }
        mapper.put(word, counting);
        words.add(word);
    }

    public String runOnCommand(String command) {
        setWord(command);
        execute();
        return getWord();
    }

    public String runOnArgument(String userInput) {
        if (userInput.trim().length() != 0) {
            String correctedInput = "";
            String[] splitString1 = userInput.split("/");
            if (splitString1.length > 1) {
                for (String data : splitString1) {
                    if (data.trim().length() != 0) {
                        String[] partitionedData = data.split(" ", 2);
                        setWord(partitionedData[0]);
                        execute();
                        String nutrient = getWord();
                        userInput.replace("/" + partitionedData[0], "/" + nutrient);
                    }
                }
            } else {
                return userInput;
            }
            return userInput;
        } else {
            return "";
        }
    }

    /**
     * This is a function that will store the user input word into the object.
     * @param word word to be stored.
     */
    public void setWord(String word) {
        this.word = word;
        for (int i = 0; i < 26; i += 1) {
            counter[i] = 0;
        }
        for (int i = 0; i < word.length() - 1; i += 1) {
            if ((int)word.charAt(i) - 97 >= 0 && (int)word.charAt(i) - 97 < 26) {
                counter[(int) word.charAt(i) - 97] += 1;
            }
        }
    }

    /**
     * This is a function that will compare the word in the object against.
     * pre defined correct words and change it to those words
     */

    public void execute() {
        int currentDistance = 4;
        int distance = 0;
        String likelyWord = word;
        for (int i = 0; i < words.size(); i += 1) {
            for (int j = 0; j < 26; j += 1) {
                distance += Math.abs(counter[j] - mapper.get(words.get(i))[j]);
            }
            if (distance < 4) {
                if (distance < currentDistance) {
                    likelyWord = words.get(i);
                    currentDistance = distance;
                }
            }
            distance = 0;
        }
        this.word = likelyWord;
    }

    public String getWord() {
        return this.word;
    }
}
