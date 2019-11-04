package duke.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import duke.exception.DukeException;
import duke.task.Task;
import duke.tasklist.TaskList;

/**
 * Class that contains logic for vectorizing task descriptions and proposing closest filter
 */
public class TaskAssigner {
    /**
     * Finds a suitable filter and asks if user wants to assign to it
     *
     * @param tasks TaskList of all of user's tasks
     * @param task  Task to be auto assigned
     */
    public static void assign(TaskList tasks, Task task) throws DukeException {
        if (task.getFilter().isPresent()) {
            System.out.println(String.format("This task already has a filter \"%s\"!", task.getFilter().get()));
            return;
        }
        String toUpdate;
        Scanner scanner = new Scanner(System.in);
        Set<String> filters = getFilters(tasks);
        for (String s : filters) {
            if (task.getDescription().contains(s)) {
                System.out.println(String.format("This task's description contains the filter \"%s\", would you like to"
                        +
                        " assign to it? (Y/N)", s));
                toUpdate = scanner.nextLine().toUpperCase();
                while (!(toUpdate.equals("Y") || toUpdate.equals("N"))) {
                    System.out.println("Invalid. Y or N only.");
                    System.out.println(String.format("Would you like to assign task \"%s\" to filter \"%s\"? (Y/N)",
                            task.getDescription(), s));
                    toUpdate = scanner.nextLine().toUpperCase();
                }
                if (toUpdate.equals("Y")) {
                    task.setFilter(Optional.ofNullable(s));
                    System.out.println(String.format("Task \"%s\" has been assigned to filter \"%s\"",
                            task.getDescription(), s));
                    return;
                } else {
                    System.out.println("Alright then.");
                    return;
                }
            }
        }
        ArrayList<ArrayList<String>> tokensPerTask = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < tasks.size(); i++) {
            tokensPerTask.add(tokenize(tasks.get(i)));
        }
        Map<String, Integer> uniqueTokens = getUniqueTokens(tokensPerTask);
        ArrayList<ArrayList<Integer>> vectorCounts = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < tokensPerTask.size(); i++) {
            ArrayList<Integer> vector = getVectorCount(tokensPerTask.get(i), uniqueTokens);
            vectorCounts.add(vector);
        }
        Map<String, ArrayList<Integer>> filterVectorCounts = getFilterVectors(filters, tasks, vectorCounts);
        ArrayList<Integer> taskVector = getVectorCount(tokenize(task), uniqueTokens);
        String closestFilter = findClosestFilter(taskVector, filterVectorCounts);
        if (closestFilter == null) {
            System.out.println("There are no suitable filters to assign.");
            return;
        }
        System.out.println(String.format("The closest filter is \"%s\" based on similar words.",
                closestFilter));
        System.out.println(String.format("Would you like to assign task \"%s\" to filter \"%s\"? (Y/N)",
                task.getDescription(), closestFilter));
        toUpdate = scanner.nextLine().toUpperCase();
        while (!(toUpdate.equals("Y") || toUpdate.equals("N"))) {
            System.out.println("Invalid. Y or N only.");
            System.out.println(String.format("Would you like to assign task \"%s\" to filter \"%s\"? (Y/N)",
                    task.getDescription(), closestFilter));
            toUpdate = scanner.nextLine().toUpperCase();
        }
        if (toUpdate.equals("Y")) {
            task.setFilter(Optional.ofNullable(closestFilter));
            System.out.println(String.format("Task \"%s\" has been assigned to filter \"%s\"",
                    task.getDescription(), closestFilter));
        } else {
            System.out.println("Alright then.");
        }
    }

    /**
     * Cleans and tokenizes a task's description into arraylist of strings
     *
     * @param task Task whose description is to be tokenized
     * @return ArrayList of String containing tokens of task description
     */
    public static ArrayList<String> tokenize(Task task) {
        String description = task.getDescription();
        description = description.trim();
        description = description.replaceAll("(?:--|[\\[\\]{}()+/\\\\])", "");
        String[] tokensArray = description.split("\\s+");
        return new ArrayList<String>(Arrays.asList(tokensArray));
    }

    /**
     * Generates a map for unique token to index in count vector
     *
     * @param tokensPerTask An arraylist that contains arraylist of tokens for each task description
     * @return A map that maps unique tokens to their index in the count vector
     */
    public static Map<String, Integer> getUniqueTokens(ArrayList<ArrayList<String>> tokensPerTask) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < tokensPerTask.size(); i++) {
            for (int j = 0; j < tokensPerTask.get(i).size(); j++) {
                String token = tokensPerTask.get(i).get(j);
                if (!map.containsKey(token)) {
                    map.put(token, map.size());
                }
            }
        }
        return map;
    }

    /**
     * Given the tokens for a task's description and mapping to tokens to index, generate the count vector
     *
     * @param tokens       tokens for a particular task description
     * @param uniqueTokens a map that maps unique tokens to their index in the count vector
     * @return Arraylist of numbers which represent vector counts for description
     */
    public static ArrayList<Integer> getVectorCount(ArrayList<String> tokens, Map<String, Integer> uniqueTokens) {
        ArrayList<Integer> vector = new ArrayList<Integer>();
        // Initialize the vector
        for (int i = 0; i < uniqueTokens.size(); i++) {
            vector.add(0);
        }
        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            int index = uniqueTokens.get(token);
            vector.set(index, vector.get(index) + 1);
        }
        return vector;
    }

    /**
     * @param tasks TaskList of all of user's tasks
     * @return Set of filters
     */
    public static HashSet<String> getFilters(TaskList tasks) throws DukeException {
        ArrayList<String> filters = new ArrayList<String>();
        for (int i = 0; i < tasks.size(); i++) {
            Optional<String> filter = tasks.get(i).getFilter();
            if (filter.isPresent()) {
                filters.add(filter.get());
            }
        }
        // Remove dups
        HashSet<String> hashSet = new HashSet<>(filters);
        return hashSet;
    }

    /**
     * @param filters      Set of filters
     * @param tasks        TaskList of all of user's tasks
     * @param vectorCounts VectorCounts for each task
     * @return Map of filter to average VectorCount of filter
     */
    public static Map<String, ArrayList<Integer>> getFilterVectors(Set<String> filters, TaskList tasks,
                                                                   ArrayList<ArrayList<Integer>> vectorCounts) throws DukeException {
        Map<String, ArrayList<Integer>> filterVectorCounts = new HashMap<String, ArrayList<Integer>>();
        for (int i = 0; i < tasks.size(); i++) {
            Optional<String> filter = tasks.get(i).getFilter();
            if (filter.isPresent()) {
                if (!filterVectorCounts.containsKey(filter)) {
                    filterVectorCounts.put(filter.get(), vectorCounts.get(i));
                } else {
                    ArrayList<Integer> currentVectorCount = filterVectorCounts.get(filter.get());
                    ArrayList<Integer> toAddVectorCount = vectorCounts.get(i);
                    for (int j = 0; j < currentVectorCount.size(); j++) {
                        currentVectorCount.set(j, currentVectorCount.get(j) + toAddVectorCount.get(j));
                    }
                    filterVectorCounts.put(filter.get(), currentVectorCount);
                }
            }
        }
        return filterVectorCounts;
    }

    /**
     * @param taskVector         VectorCount of task we want to auto assign
     * @param filterVectorCounts Average VectorCounts of all the filters
     * @return The closest filter or null if best cosine similarity is 0
     */
    public static String findClosestFilter(ArrayList<Integer> taskVector, Map<String,
            ArrayList<Integer>> filterVectorCounts) {
        String closestFilter = null;
        double maxSimilarity = 0;
        for (String filter : filterVectorCounts.keySet()) {
            double similarity = cosine_similarity(filterVectorCounts.get(filter), taskVector);
            if (similarity > maxSimilarity) {
                maxSimilarity = similarity;
                closestFilter = filter;
            }
        }
        if (maxSimilarity == 0.0) {
            return null;
        }
        return closestFilter;
    }

    /**
     * @param vectorA first vectorCount
     * @param vectorB second vectorCount
     * @return Cosine similarity between the 2 vectors
     */
    public static double cosine_similarity(ArrayList<Integer> vectorA, ArrayList<Integer> vectorB) {
        // A and B must have same length
        double dotProduct = 0;
        double normA = 0;
        double normB = 0;
        for (int i = 0; i < vectorA.size(); i++) {
            dotProduct += (double) vectorA.get(i) * (double) vectorB.get(i);
        }
        for (int i = 0; i < vectorA.size(); i++) {
            normA += (double) vectorA.get(i) * (double) vectorA.get(i);
        }
        normA = Math.sqrt(normA);
        for (int i = 0; i < vectorB.size(); i++) {
            normB += (double) vectorB.get(i) * (double) vectorB.get(i);
        }
        normB = Math.sqrt(normB);
        return dotProduct / (normA * normB);

    }
}
