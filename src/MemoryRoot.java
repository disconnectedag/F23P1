public class MemoryRoot {
    MemoryNode head;

    public MemoryRoot(int size) {
        head = new MemoryNode(size, 0);
    }


    public void deleteData(int location) {
        MemoryNode nodeToRemove = getNodeAtLocation(location);
        nodeToRemove.parent.abortChild(nodeToRemove);// can never be head cause
        // theres always
        // duplication of storage
        head.collapse();
    }


    public MemoryNode getNodeAtLocation(int location) {
        MemoryNode pointer = head;
        while (true) {
            if (pointer.location == location) {
                break;
            }
            if (pointer.r == null || location < pointer.r.location) {
                pointer = pointer.l;
            }
            else {
                pointer = pointer.r;
            }
        }
        while (!pointer.dataNode) {
            pointer = pointer.l;
        }
        return pointer;
    }


    /**
     *
     * @param data
     * @return a Handle that stores the location, and the size of the recently
     *         inserted data
     */
    public Handle insertData(byte[] data) {
        MemoryNode memoryPointer = getInsertionLocation(data.length);
        memoryPointer.fillData(data);
        // expandStorageIfNecessary(data.length);
        return new Handle(memoryPointer.location, data.length);
    }


    /**
     * Expands memory if necessary to fulfill the insertion request
     *
     * @param dataSize:
     *            the size of the data
     * @return MemoryNode pointer: block of memory allocated for insertion
     */
    private MemoryNode getInsertionLocation(int dataSize) {
        int startingSize = head.size;
        MemoryNode memoryPointer = head.getFree(dataSize);
        while (memoryPointer == null) {
            duplicateCapacity();
            memoryPointer = head.getFree(dataSize);
        }
        if (startingSize != head.size) {
            System.out.println("Memory pool expanded to " + head.size
                    + " bytes");
        }
        return memoryPointer;
    }
    // add 4
    // remove3
    // add5


    public void insertData(int size) {
        MemoryNode memoryPointer;
        int startingSize = head.size;
        memoryPointer = head.getFree(size);
        while (memoryPointer == null) {
            duplicateCapacity();
            memoryPointer = head.getFree(size);
        }
        if (startingSize != head.size) {
            System.out.println("Memory pool expanded to " + head.size
                    + "bytes");
        }
        memoryPointer.fillData(size);
    }


    private void duplicateCapacity() {
        head.collapse();
        MemoryNode updatedHead = new MemoryNode(head);
        head = updatedHead;
    }


    public String toString(){
        String strOfPool = head.toString();
        String val = "Freeblock List:";
        //val += head.toString().length() > 0 ? strOfPool :"\nThere are no freeblocks in the memory pool";

        val += head.toString().length() > 0 ? toStringHelper(strOfPool) :"\nThere are no freeblocks in the memory pool";
        return val;
    }


    /**
     * this method will take the string, and append the list
     */
    private String toStringHelper(String printout) {
        // newline character followed by previous key
        // regex set up for key1: val1\nkey2: val\ns
        String output = "";
        String prevKey = null;
        String[] lines = printout.split("\n");
        // System.out.println(Arrays.toString(lines));
        for (String line : lines) {
            if (!line.contains(":")) {
                continue;
            }
            String[] tuple = line.split(": ");
            if (tuple[0].equals(prevKey)) {
                output += " " + tuple[1];
                continue;
            }
            output += "\n" + line;
            prevKey = tuple[0];
        }
        output = "\n" + output.trim();
        return output;
    }

}
