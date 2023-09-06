/**
 * This must be a power of two, or the program should
 * immediately terminate with an error message.
 *
 * @param <T>
 */
public class DoubleHash<T> {
    MapNode[] map;
    boolean[] tombs;
    int capacity;
    int m;

    /**
     * Constructor for Double Hash
     *
     * @param arraySize:
     *            the initial size of Hash Array
     */
    public DoubleHash(int arraySize) {
        map = new MapNode[arraySize];
        tombs = new boolean[arraySize];
        capacity = arraySize;
        m = arraySize;
    }


    /**
     * method calls upon parent insert method
     *
     * @param id:
     *            the id of the hash
     * @param data:
     *            the data to be stored
     */
    public void insert(int id, T data) {
        insert(new MapNode(id, data));
    }


    /**
     * true insert method, increases size if
     * map is too small
     * decreases capacity after each insert
     *
     * @param node
     */
    private void insert(MapNode node) {
        if (!sizeRequirementMet()) {
            rebuild();
        }
        int spot = findVacancy(node.id);
        tombs[spot] = false;
        map[spot] = node;
        capacity--;
    }


    /**
     * retrieves the data for the id
     *
     * @param id:
     *            the key
     * @return the data stored at the key
     */
    public T get(int id) {
        int key = find(id);
        if (key == -1) {
            return null;
        }
        return (T)map[key].data;
    }


    /**
     * Deletes the value with the corresponding key
     *
     * @param id
     *            the key
     */
    public void delete(int id) {
        int index = find(id);
        tombs[index] = true;
        map[index] = null;
        capacity++;
        System.out.printf("Record with ID %d successfully deleted from the database%n", id);
    }


    /**
     * locates index of element in the map
     *
     * @param k
     *            the key
     * @return the index of the reference spot
     *         otherwise returns -1 if no spot/tomb
     */
    public int find(int k) {
        int h1 = k % m;
        int spot = h1;
        int step = (((k / m) % (m / 2)) * 2) + 1;
        int i = 0; // iteration
        while (!deadEnd(spot)) {
            if (spotFound(spot, k)) {
                return spot;
            }
            i++;
            spot = (step * i + h1) % m;
        }
        return -1;
    }

    /**
     * boolean to make sure spot isnt empty
     *
     * @param spot this is the spot
     * @param k
     * @return
     */
    private boolean spotFound(int spot, int k) {
        return map[spot] != null && map[spot].id == k;
    }


    private boolean deadEnd(int spot) {
        return (map[spot] == null && tombs[spot] == false); // no data, no tomb
    }


    /**
     * looks for open spot
     *
     * @param k:
     *            key
     * @return the closest available spot
     */
    private int findVacancy(int k) {
        int h1 = k % m;
        int spot = h1;
        int step = (((k / m) % (m / 2)) * 2) + 1;
        int i = 0; // iteration
        while (map[spot] != null) {
            i++;
            spot = (step * i + h1) % m;
        }
        return spot;
    }


    /**
     * determines if the input is a power of two
     *
     * @param n
     *            the number
     * @return true if it is a power of 2
     */
//    public static boolean isPowerOfTwo(int n) {
//        if (n <= 0) {
//            return false;
//        }
//        while (n > 1) {
//            if (n % 2 != 0) {
//                return false;
//            }
//            n /= 2;
//        }
//        return true;
//    }


    /**
     * doubles map size
     * iterates through the map, reinserts elements
     * skips null values
     */
    private void rebuild() {
        DoubleHash<T> newHash = new DoubleHash<T>(m * 2);
        for (MapNode<T> node : map) {
            if (node == null) {
                continue;
            }
            newHash.insert(node);
        }

        map = newHash.map;
        capacity = newHash.capacity;
        m = newHash.m;
        tombs = new boolean[m]; //cleans tombs
        System.out.println("Hash table expanded to " + m + " records");
    }


    /**
     * determines whether or not size is met
     * looks into the future
     *
     * @return true if capacity is greater than
     *         half after an insert
     */
    private boolean sizeRequirementMet() {
        return capacity - 1 >= (map.length / 2);
    }


    public String toString() {
        StringBuilder print = new StringBuilder("Hashtable:\n");
        int recordCount = 0;
        for (int i = 0; i < map.length; i++) {
            if (map[i] == null && tombs[i] == false) {
                continue;
            }
            print.append(i + ": ");
            if (tombs[i] == true) {
                print.append("TOMBSTONE\n");
            }
            else {
                recordCount++;
                print.append(map[i].id + "\n");
            }
        }
        print.append("total records: " + recordCount);
        return print.toString();
    }

    private class MapNode<T> {
        int id;
        T data;

        private MapNode(int id, T data) {
            this.data = data;
            this.id = id;
        }
    }

}
