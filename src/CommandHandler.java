
/**
 * Stores the Position and record length, used in a hash map
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */
public class CommandHandler 
{
    private DoubleHash<Handle> map; 
    private MemoryRoot memoryPool; 

    /**
     * constructor function
     * @param memoryPoolSize is the initial size
     * @param hashsize is the initial size
     */
    public CommandHandler(int memoryPoolSize, int hashsize) {
        map = new DoubleHash<Handle>(hashsize);
        memoryPool = new MemoryRoot(memoryPoolSize);
    }

    /**
     * validates id is unique 
     * inserts into memory pool
     * updates map
     * @param id is the id
     * @param item is the item
     */
    public void insert(int id, Seminar item)
    {
        if (map.find(id) != -1)
        {
            System.out.printf("Insert FAILED - There is already a record "
                + "with ID %d%n", id);
            return;
        }
        try 
        {
            byte[] serializedData = item.serialize();
            Handle receipt = memoryPool.insertData(serializedData);
            map.insert(id, receipt);
            System.out.println("Successfully inserted record with ID " + id);
            System.out.println(item.toString());
            System.out.println("Size: " + serializedData.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }
    
    /**
     * search function
     * @param id is the seminar id
     */
    public void search(int id)
    {
        Handle handleOfTarget = (Handle) map.get(id);
        if (handleOfTarget == null)
        {
            System.out.printf("Search FAILED -- There is no record with "
                + "ID %d%n", id);
            return;
        }
        System.out.println("Found record with ID " + id + ":");
        byte[] serializedData = memoryPool.getNodeAtLocation(handleOfTarget
            .getStart()).getMemory();
        try 
        {
            Seminar retrievedValue = Seminar.deserialize(serializedData);
            System.out.println(retrievedValue.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    
    /**
     * prints either hash table or memory manager
     * @param printType is print type
     */
    public void print(String printType) 
    {
        if (printType.equals("hashtable"))
        {
            System.out.println(map.toString());
            return;
        }
        else 
        {
            System.out.println(memoryPool.toString());
        }
    }

    /**
     * Deletes records from the map and memory pool
     * @param id is the id of the seminar
     */
    public void delete(int id)
    {
        Handle handleOfTarget = (Handle) map.get(id);
        if (handleOfTarget == null)
        {
            System.out.printf("Delete FAILED -- There is no record "
                + "with ID %d%n", id);
            return;
        }
        memoryPool.deleteData(handleOfTarget.getStart());
        map.delete(id);
    }


}
