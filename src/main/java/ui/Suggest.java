package ui;

import java.util.Set;

//@@author karansarat
public class Suggest {
    /**
     * Takes a list of known words and another word and tries to return the closest match.
     * @param dict list of known words.
     * @param w1 word to try and match with dict.
     * @return a word that is the closest match.
     */
    public String guess(Set<String> dict, String w1) {
        Integer min = Integer.MAX_VALUE;
        String guess = new String();
        for (String w2 : dict) {
            int currDist = calcEditDist(w1, w2);
            if (currDist < min) {
                guess = w2;
                min = currDist;
            }
        }
        return guess;
    }
  
    /**
     * Calculates the edit distance between 2 words.
     * @param w1 word 1.
     * @param w2 word 2.
     * @return int representing the edit distance between the 2 words.
     */
    private static int calcEditDist(String w1, String w2) { 
        int l1 = w1.length();
        int l2 = w2.length();
        int[][] dist = new int[l1 + 1][l2 + 1]; 
        for (int i = 0; i <= l1; i++) { 
            for (int j = 0; j <= l2; j++) { 
                if (i == 0) {
                    dist[i][j] = j;
                } else if (j == 0) {
                    dist[i][j] = i;
                } else if (w1.charAt(i - 1) == w2.charAt(j - 1)) {
                    dist[i][j] = dist[i - 1][j - 1]; 
                } else {
                    dist[i][j] = 1 + Math.min(dist[i][j - 1], Math.min(dist[i - 1][j], dist[i - 1][j - 1]));
                }
            } 
        } 
        return dist[l1][l2];
    }
}