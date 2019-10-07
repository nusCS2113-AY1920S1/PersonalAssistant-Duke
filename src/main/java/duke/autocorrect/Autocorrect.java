package duke.autocorrect;

import java.util.HashMap;
import java.util.ArrayList;

public class Autocorrect {
    private String word;
    private HashMap<String,int[]> mapper = new HashMap();
    private ArrayList<String> words = new ArrayList();
    private int[] counter = new int[26];

    public Autocorrect() {}

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

    public void setWord(String word) {
        this.word = word;
        for (int i = 0; i < 26; i += 1) {
            counter[i] = 0;
        }
        for (int i = 0; i < word.length() - 1; i += 1) {
            counter[(int)word.charAt(i) - 97] += 1;
        }
    }

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
