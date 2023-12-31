
/**
 * Memory root class
 *
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */
public class MemoryNode 
{
    /** numspaces is always -1 */ 
    public static final int NUM_SPACES = -1;
    private MemoryNode parent;
    private int size;
    private boolean dataNode;
    private boolean splitted;
    private int location;
    private byte[] memory;
    private MemoryNode l;
    private MemoryNode r;
    
    /**
     * constructor function
     * @param prevPool is the previous pool
     */
    public MemoryNode(MemoryNode prevPool) 
    {
        parent = null;
        size = prevPool.size * 2;
        location = prevPool.location;
        dataNode = false;
        splitted = true;
        l = prevPool;
        l.parent = this;
        l.collapse();
        r = new MemoryNode(size / 2, location + size / 2);
        r.parent = this;
    }
    // getter functions 
    /**
     * gets l
     * @return l node
     */
    public MemoryNode getLeftNode()
    {
        return l;
    }
    /**
     * gets r
     * @return r node
     */
    public MemoryNode getRightNode()
    {
        return r;
    }
    /**
     * get memory byte array
     * @return byte array
     */
    public byte[] getMemory()
    {
        return memory;
    }
    /**
     * getter for the size
     * @return the size
     */
    public int getSize() 
    {
        return size;
    }
    
    /**
     * getter
     * @return the data node
     */
    public boolean getDataNode()
    {
        return dataNode;
    }
    
    /**
     * getter
     * @return splitted
     */
    public boolean getSplitted()
    {
        return splitted;
    }
    /**
     * getter
     * @return the location
     */
    public int getLocation() 
    {
        return location;
    }
    
    
    /**
     * getter for parent
     * @return parent node
     */
    public MemoryNode getParent()
    {
        return parent;
    }
    
    /**
     * constructor for node
     * @param space is the space
     * @param refpointer is the ref
     */
    public MemoryNode(int space, int refpointer) 
    {
        size = space;
        location = refpointer;
        dataNode = false;
        splitted = false;
    }


    /**
     * Maybe this can return the left node, that way it can keep being split
     * until size requirement is met
     *
     * @throws Exception
     */
    public void split() 
    {
        l = new MemoryNode(size / 2, location);
        l.parent = this;
        r = new MemoryNode(size / 2, location + size / 2);
        r.parent = this;
        splitted = true;
    }


    /**
     * fills in the data
     * @param sizeParam is the size
     */
    public void fillData(int sizeParam) 
    {
        dataNode = true;
        memory = String.valueOf(sizeParam).getBytes();
    }




    /**
     * fills the data
     * @param data is the payload
     */
    public void fillData(byte[] data) 
    {
        dataNode = true;
        memory = data;
    }


    /**
     * method attempts to delegate work to child
     *
     * @param space is the space
     * @return is the
     */
    private MemoryNode pass2child(int space) 
    {
        if (l != null && l.getFree(space) != null) 
        {
            return l.getFree(space);
        }
        if (r != null && r.getFree(space) != null) 
        {
            return r.getFree(space);
        }
        return null;
    }


    /**
     * finds a free chunk of space in heap
     * @param space is the space
     * @return is the return
     */
    public MemoryNode getFree(int space) {
        if (space > size || dataNode == true) 
        {
            return null;
        }
        if (space <= size / 2) 
        {
            if (splitted) 
            { // try to pass work on to children
                MemoryNode childLabor = pass2child(space);
                if (childLabor != null)
                {
                    return childLabor;
                }
            }
            if (!splitted) 
            { // make children & try again
                split();
                return getFree(space);
            }
        }
        if (space <= size && isEmpty()) 
        {
            return this;
        }
        return null;
    }

    /**
     * method for determining if node is empty
     * @return true if leaf and no data stored
     */
    boolean isEmpty() 
    {
        // cannot Contain data
        if (dataNode) 
        {
            return false;
        }
        if (l == null && r == null) 
        {
            return true;
        }
        if (l != null && !l.isEmpty()) 
        {
            return false;
        }
        return !(r != null && !r.isEmpty()) ;        
        // left must be null or l.getFree() == null
        // right must be null or r.getFree() == null
    }


    /**
     * collapses node if both children are empty
     */
    public void collapse() {
        if (dataNode) {
            return;
        }
        if (l != null) {
            l.collapse();
        }
        if (r != null) {
            r.collapse();
        }

        if (l == null && r == null) {
            if (parent != null) {
                parent.abortChild(this);
            }
        }
        return;
    }

    /**
     * Called on by the collapse function, this takes in the child that needs to
     * be deleted
     * @param child is the child
     */
    public void abortChild(MemoryNode child) 
    {
        if (l == child) 
        {
            l = null;
        }
        if (r == child) 
        {
            r = null;
        }
        if (l == null && r == null) 
        {
            splitted = false;
        }
    }
    
    /**
     * to string method
     * @return String that is memory node
     */
    public String toString() 
    {
        String val = "";
        if (dataNode) 
        {
            return "";
        }
        if (isEmpty()) 
        {
            return size + ": " + location + "\n";
        }
        if (splitted) 
        {
            val += (l != null)
                    ? l.toString()
                    : "\n" + size / 2 + ": " + location + "\n";
            val += (r != null)
                    ? r.toString()
                    : "\n" + size / 2 + ": " + (location + size / 2) + "\n";
        }
        return val;

    }

}
