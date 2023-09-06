import java.util.logging.MemoryHandler;

/**
 * Stores the Position and record length, used in a hash map
 * @author maxrojtman
 * @author agerhardt
 *
 */
public class CommandHandler {
    DoubleHash map;
    MemoryRoot memoryPool;

    /**
     *
     * @param memoryPoolSize
     */
    public CommandHandler(int memoryPoolSize, int hashsize){
        map = new DoubleHash<Handle>(hashsize);
        memoryPool = new MemoryRoot(memoryPoolSize);
    }

    /**
     * validates id is unique
     * inserts into memorypool
     * updates map
     * @param id
     * @param item
     */

    public void insert(int id, Seminar item){
        if (map.find(id) != -1){
            System.out.printf("Insert FAILED - There is already a record with ID %d%n", id);
            return;
        }
        try {
            byte[] serializedData = item.serialize();
            Handle receipt = memoryPool.insertData(serializedData);
            map.insert(id, receipt);
            System.out.println("Successfully inserted record with ID "+id);
            System.out.println(item.toString());
            System.out.println("Size: " + serializedData.length);
        }
        catch (Exception e){
            e.printStackTrace();

        }

    }
    public void search(int id){
        Handle handleOfTarget = (Handle) map.get(id);
        if (handleOfTarget == null){
            System.out.printf("Search FAILED -- There is no record with ID %d%n", id);
            return;
        }
        System.out.println("Found record with ID "+id+":");
        byte[] serializedData = memoryPool.getNodeAtLocation(handleOfTarget.getStart()).memory;
        try {
            Seminar retrievedValue = Seminar.deserialize(serializedData);
            System.out.println(retrievedValue.toString());
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    /**
     * prints either the hashmap or the memory manager
     * @param printType: the print type
     */
    public void print(String printType) {
        if (printType.equals("hashtable")){
            System.out.println(map.toString());
            return;
        }
        else {
            System.out.println(memoryPool.toString());
        }
    }

    /**
     * Deletes records from the map and memorypool
     * @param id: the id of the seminar
     */
    public void delete(int id){
        Handle handleOfTarget = (Handle) map.get(id);
        if (handleOfTarget == null){
            System.out.printf("Delete FAILED -- There is no record with ID %d%n", id);
            return;
        }
        memoryPool.deleteData(handleOfTarget.getStart());
        map.delete(id);
    }


}
