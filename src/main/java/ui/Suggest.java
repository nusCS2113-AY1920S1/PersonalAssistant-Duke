package ui;

import java.util.Set;
import common.AlphaNUSException;

public class Suggest {
    public String guess(Set<String> dict, String w1) throws AlphaNUSException {
        
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
  
    private static int calcEditDist(String w1, String w2) { 
        int l1 = w1.length();
        int l2 = w2.length();
        int dist[][] = new int[l1+1][l2+1]; 
       
        for (int i = 0; i <= l1; i++) { 
            for (int j = 0; j <= l2; j++) { 
                if (i == 0) 
                    dist[i][j] = j;
                else if (j == 0) 
                    dist[i][j] = i;
                else if (w1.charAt(i-1) == w2.charAt(j-1)) 
                    dist[i][j] = dist[i-1][j-1]; 
                else
                    dist[i][j] = 1 + Math.min(dist[i][j-1], Math.min(dist[i-1][j], dist[i-1][j-1]));
            } 
        } 
        return dist[l1][l2];
    }
}