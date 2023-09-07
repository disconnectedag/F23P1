/**
 * Stores the Position and record length, used in a hash map
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 * @param <T> is the type of object
 */
public class DoubleHash<T> 
{
    private MapNode[] map;
    private boolean[] tombs;
    private int capacity;
    private int m;

    /**
     * constructor
     * @param arraySize is the initial size
     */
    public DoubleHash(int arraySize) 
    {
        map = new MapNode[arraySize];
        tombs = new boolean[arraySize];
        capacity = arraySize;
        m = arraySize;
    }
    
    /**
     * method calls upon parent insert method
     * @param id is the id
     * @param data is the payload
     */
    public void insert(int id, T data) 
    {
        insert(new MapNode(id, data));
    }

    /**
     * true insert method, increases size if
     * map is too small
     * decreases capacity after each insert
     * @param node is the node
     */
    private void insert(MapNode node) 
    {
        if (!sizeRequirementMet()) 
        {
            rebuild();
        }
        int spot = findVacancy(node.id);
        tombs[spot] = false;
        map[spot] = node;
        capacity--;
    }


    /**
     * retrieves the data for the id
     * @param id is the sem id
     * @return the data
     */
    public T get(int id) 
    {
        int key = find(id);
        if (key == -1) 
        {
            return null;
        }
        return (T)map[key].data;
    }


    /**
     * deletes value with corresponding id
     * @param id is desired id to delete
     */
    public void delete(int id) 
    {
        int index = find(id);
        tombs[index] = true;
        map[index] = null;
        capacity++;
        System.out.printf("Record with ID %d successfully deleted from "
            + "the database%n", id);
    }


    /**
     * Finds the index
     * @param k is the key
     * @return the index of the reference spot
     * otherwise returns -1 if no spot/tomb
     */
    public int find(int k) 
    {
        int h1 = k % m;
        int spot = h1;
        int step = (((k / m) % (m / 2)) * 2) + 1;
        int i = 0; // iteration
        while (!deadEnd(spot)) 
        {
            if (spotFound(spot, k)) 
            {
                return spot;
            }
            i++;
            spot = (step * i + h1) % m;
        }
        return -1;
    }

    /**
     * is the spot found
     * @param spot is the spot
     * @param k is the key
     * @return whether the spot was found
     */
    private boolean spotFound(int spot, int k) 
    {
        return map[spot] != null && map[spot].id == k;
    }

    /**
     * found a deal end
     * @param spot is the spot
     * @return is a boolean
     */
    private boolean deadEnd(int spot) 
    {
        return (map[spot] == null && tombs[spot] == false); // no data or tomb
    }


    /**
     * looks for an open spot
     * @param k is the key
     * @return is the spot of vacancy
     */
    private int findVacancy(int k) 
    {
        int h1 = k % m;
        int spot = h1;
        int step = (((k / m) % (m / 2)) * 2) + 1;
        int i = 0; // iteration
        while (map[spot] != null) 
        {
            i++;
            spot = (step * i + h1) % m;
        }
        return spot;
    }

    /**
     * doubles map size
     * iterates through the map, reinserts elements
     * skips null values
     */
    private void rebuild() 
    {
        DoubleHash<T> newHash = new DoubleHash<T>(m * 2);
        for (MapNode<T> node : map) 
        {
            if (node == null) 
            {
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
    private boolean sizeRequirementMet() 
    {
        return capacity - 1 >= (map.length / 2);
    }

    /**
     * returns the string of the hashtable
     * @return a string of the hash table
     */
    public String toString() 
    {
        StringBuilder print = new StringBuilder("Hashtable:\n");
        int recordCount = 0;
        for (int i = 0; i < map.length; i++) 
        {
            if (map[i] == null && tombs[i] == false) 
            {
                continue;
            }
            print.append(i + ": ");
            if (tombs[i] == true) 
            {
                print.append("TOMBSTONE\n");
            }
            else 
            {
                recordCount++;
                print.append(map[i].id + "\n");
            }
        }
        print.append("total records: " + recordCount);
        return print.toString();
    }
    
    /**
     * is the map node
     * @param <T> is the type
     */
    private class MapNode<T> 
    {
        private int id;
        private T data;
        
        /**
         * 
         * @param id is the seminar id
         * @param data is the payload
         */
        private MapNode(int id, T data) 
        {
            this.data = data;
            this.id = id;
        }
    }

}
