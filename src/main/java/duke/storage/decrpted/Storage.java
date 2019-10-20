package duke.storage.decrpted;

/**
 * Stores tasks in file and reads tasks from file.
 */
public class Storage {

//    private String path;
//
//    /**
//     * Constructor for Storage.
//     *
//     * @param path the path to store the file, including the file name.
//     */
//    public Storage(String path) {
//        this.path = path;
//    }
//
//    /**
//     * Serialize BakingList to json file.
//     *
//     * @param bakingList the TaskList to serialize.
//     * @throws DukeException if fails to serialize due to IO exception.
//     */
//    public void serialize(BakingList bakingList) throws DukeException {
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            //mapper.setDateFormat(new SimpleDateFormat(TimeParser.getDatePattern()));
//            mapper.writerWithType(BakingList.class).writeValue(new File(path), bakingList);
//        } catch (IOException i) {
//            throw new DukeException("IO Exception");
//        }
//    }
//
//    /**
//     * Deserializes BakingList from file. If the file is not found, creates new file and returns an empty
//     * BakingList.
//     *
//     * @return a BakingList object.
//     * @throws DukeException if file is damaged.
//     */
//    public BakingList deserialize() throws DukeException {
//        BakingList bakingList = null;
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            //mapper.setDateFormat(new SimpleDateFormat(TimeParser.getDatePattern()));
//            bakingList = mapper.readValue(new File(path), BakingList.class);
//            return bakingList;
//        } catch (IOException i) {
//            if (i instanceof FileNotFoundException) {
//                return new BakingList();
//            } else {
//                i.printStackTrace();
//                throw new DukeException("IO exception when loading data");
//            }
//        }
//    }

}