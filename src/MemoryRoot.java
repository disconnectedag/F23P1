/**
 * Memory root class
 *
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */

public class MemoryRoot 
{
    private MemoryNode head;
    private StringAssist strHelp;
    
    /**
     * constructor
     * @param size is the size param
     */
    public MemoryRoot(int size) 
    {
        strHelp = new StringAssist();
        head = new MemoryNode(size, 0);
    }
    
    /**
     * getter function for head
     * @return the head
     */
    public MemoryNode getHead()
    {
        return this.head;
    }
    
    /**
     * deletes data
     * @param location to delete
     */
    public void deleteData(int location) 
    {
        MemoryNode nodeToRemove = getNodeAtLocation(location);
        // can't ever be head
        nodeToRemove.getParent().abortChild(nodeToRemove); 
        head.collapse();
    }

    /**
     * gets node at location
     * @param location to get node from
     * @return the node
     */
    public MemoryNode getNodeAtLocation(int location) 
    {
        MemoryNode pointer = head;
        while (true) 
        {
            if (pointer.getLocation() == location) 
            {
                break;
            }
            if (pointer.getRightNode() == null || location < pointer
                .getRightNode().getLocation()) 
            {
                pointer = pointer.getLeftNode();
            }
            else 
            {
                pointer = pointer.getRightNode();
            }
        }
        while (!pointer.getDataNode()) 
        {
            pointer = pointer.getLeftNode();
        }
        return pointer;
    }


    /**
     *
     * @param data is the data byte array
     * @return a Handle that stores the location, and the size of the recently
     * inserted data
     */
    public Handle insertData(byte[] data) 
    {
        MemoryNode memoryPointer = getInsertionLocation(data.length);
        memoryPointer.fillData(data);
        // expandStorageIfNecessary(data.length);
        return new Handle(memoryPointer.getLocation(), data.length);
    }


    /**
     * Expands memory if necessary to fulfill the insertion request
     *
     * @param dataSize:
     *            the size of the data
     * @return MemoryNode pointer: block of memory allocated for insertion
     */
    private MemoryNode getInsertionLocation(int dataSize) 
    {
        int startingSize = head.getSize();
        MemoryNode memoryPointer = head.getFree(dataSize);
        while (memoryPointer == null) 
        {
            duplicateCapacity();
            memoryPointer = head.getFree(dataSize);
            if (startingSize != head.getSize()) 
            {
                System.out.println("Memory pool expanded to " + head.getSize()
                    + " bytes");
            }
        }
        return memoryPointer;
    }
    // add 4
    // remove3
    // add5

    /**
     * insert data
     * @param size of node
     */
    public void insertData(int size) 
    {
        MemoryNode memoryPointer;
        int startingSize = head.getSize();
        memoryPointer = head.getFree(size);
        while (memoryPointer == null) 
        {
            duplicateCapacity();
            memoryPointer = head.getFree(size);
        }
        if (startingSize != head.getSize()) 
        {
            System.out.println("Memory pool expanded to " + head.getSize()
                    + "bytes");
        }
        memoryPointer.fillData(size);
    }

    /**
     * expands the memory manager
     */
    private void duplicateCapacity() 
    {
        head.collapse();
        MemoryNode updatedHead = new MemoryNode(head);
        head = updatedHead;
    }

    /**
     * toString method
     * @return the string of freeblock list
     */
    public String toString()
    {
        String strOfPool = head.toString();
        String val = "Freeblock List:";
        val += head.toString().length() > 0 ? strHelp.toStringHelper(strOfPool) : 
            "\nThere are no freeblocks in the memory pool";
        return val;
    }
}
