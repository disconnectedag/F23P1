import java.util.List;

/**
 * Memory root class
 *
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */
public class MemoryNode {
    public static int numspaces = -1;
    MemoryNode parent;
    int size;
    boolean dataNode;
    boolean splitted;
    int location;
    byte[] memory;
    MemoryNode l, r;

    public MemoryNode(MemoryNode prevPool) {
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
    public int getSize() {
        return size;
    }


    public MemoryNode(int space, int refpointer) {
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
    public void split() {
        l = new MemoryNode(size / 2, location);
        l.parent = this;
        r = new MemoryNode(size / 2, location + size / 2);
        r.parent = this;
        splitted = true;
    }



    public void fillData(int size) {
        dataNode = true;
        memory = String.valueOf(size).getBytes();
    }





    public void fillData(byte[] data) {
        dataNode = true;
        memory = data;
    }


    /**
     * method attempts to delegate work to child
     *
     * @param space
     * @return
     */
    private MemoryNode pass2child(int space) {
        if (l != null && l.getFree(space) != null) {
            return l.getFree(space);
        }
        if (r != null && r.getFree(space) != null) {
            return r.getFree(space);
        }
        return null;
    }


    /**
     * finds a free chunk of space in heap
     *
     * @param space
     *            the size of the space
     * @return
     */
    public MemoryNode getFree(int space) {
        if (space > size || dataNode == true) { // if not willing to adopt or                                    // size is too big
            return null;
        }
        if (space <= size / 2) {
            if (splitted) { // try to pass work on to children
                MemoryNode childLabor = pass2child(space);
                if (childLabor != null)
                    return childLabor;
            }
            if (!splitted) { // make children & try again
                split();
                return getFree(space);
            }
        }
        if (space <= size && isEmpty()) {
            return this;
        }
        return null;
    }


    /**
     * method for determining if node
     * is empty
     *
     * @return true if leaf and no data stored
     */
    boolean isEmpty() {
        // cannot Contain data
        if (dataNode) {
            return false;
        }
        if (l == null && r == null) {
            return true;
        }
        if (l != null && !l.isEmpty()) {
            return false;
        }
        if (r != null && !r.isEmpty()) {
            return false;
        }
        return true;
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
     */
    public void abortChild(MemoryNode child) {
        if (l == child) {
            l = null;
        }
        if (r == child) {
            r = null;
        }
        if (l == null && r == null) {
            splitted = false;
        }
    }
    public String toString() {
        String val = "";
        if (dataNode) {
            return "";
        }
        if (isEmpty()) {
            return size + ": " + location + "\n";
        }
        if (splitted) {
            val += (l != null)
                    ? l.toString()
                    : "\n" + size/2 + ": " + location + "\n";
            val += (r != null)
                    ? r.toString()
                    : "\n" + size/2 + ": " + (location+size/2) + "\n";
        }
        return val;

    }

}
