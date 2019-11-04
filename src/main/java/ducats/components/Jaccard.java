package ducats.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

public class Jaccard implements Serializable {

    public Jaccard() {

    }

    /**
     * A function that returns the number of common elements between the two list, i.e. union
     *
     * @param list1 - list1 is the first list of the alphabets.
     * @param list2 - list2 is the second list of the alphabets.
     */
    public int union(List<String> list1, List<String> list2) {
        HashMap<String, Integer> mapLetterList1 = new HashMap<String, Integer>();
        for (String temp: list1) {
            if (mapLetterList1.containsKey(temp)) {
                mapLetterList1.put(temp,mapLetterList1.get(temp) + 1);
            } else {
                mapLetterList1.put(temp,1);
            }
        }
        int numCommon = 0;
        for (String temp: list2) {
            if (mapLetterList1.containsKey(temp)) {
                if (mapLetterList1.get(temp) > 0) {
                    //System.out.println(temp);
                    numCommon += 1;
                    mapLetterList1.put(temp,mapLetterList1.get(temp) - 1);
                }
            }
        }
        return numCommon;
    }
    /**
     * A function that returns the similarity between 2 words.
     *
     * @param word1 - word1 is the first word to compare.
     * @param word2 - word2 is the second word to compare.
     */

    public double similarity(String word1, String word2) {
        String [] word1Arr = word1.split("");
        String [] word1Arr1 = word1.split("");
        String [] word2Arr = word2.split("");
        List<String> word1List1 = new ArrayList(Arrays.asList(word1Arr1));
        List<String> word1List = new ArrayList(Arrays.asList(word1Arr));
        List<String> word2List = new ArrayList(Arrays.asList(word2Arr));

        int intersection = union(word1List,word2List);;
        word1List1.addAll(word2List);

        int union = word1List1.size();
        return intersection / ((double)union-intersection);
    }
}
