Map<String, List<Object>> myMap = new HashMap<>();

// fill it
myMap.computeIfAbsent("hello", ignored -> new ArrayList<>())
  .addAll(Arrays.asList("hola", "buongiorno", "สวัสดี");

// retrieve
List<String> greetings = myMap.get("hello");
                      // ["hola", "buongiorno", "สวัสดี"]