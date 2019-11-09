package ui;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import command.Storage;
import common.AlphaNUSException;

public class Suggest {
    private Storage storage;

    public String guess(Set<String> dict, String w1) throws AlphaNUSException {
        
        Integer min = Integer.MAX_VALUE;
        String guess = new String();
        for (String w2 : dict) {
            int currDist = minDistance(w1, w2);
            if (currDist < min) {
                guess = w2;
            }
        }
        return guess;
    }

    private int minDistance(String w1, String w2) {
        int m = w1.length();
        int n = w2.length();
        int[][] mem = new int[m][n];
        for (int[] arr: mem) {
            Arrays.fill(arr, -1);
        }
        return calDistance(w1, w2, mem, m-1, n-1);
    }
     
    private int calDistance(String w1, String w2, int[][] mem, int i, int j){ 
        if (i < 0) {
            return j + 1;
        } else if (j < 0){
            return i + 1;
        }
     
        if (mem[i][j] != -1) {
            return mem[i][j];
        }
     
        if (w1.charAt(i) == w2.charAt(j)) {
            mem[i][j] = calDistance(w1, w2, mem, i-1, j-1);
        } else {
            int prevMin = Math.min(calDistance(w1, w2, mem, i, j-1), calDistance(w1, w2, mem, i-1, j));
            prevMin = Math.min(prevMin, calDistance(w1, w2, mem, i-1, j-1));
            mem[i][j] = 1 + prevMin;
        }
        return mem[i][j];    
    }
}