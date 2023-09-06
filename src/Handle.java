/**
 * Stores the Position and record length, used in a hash map
 * @author maxrojtman
 * @author agerhardt
 *
 */
public class Handle {
    private int startPosition;
    private int recordLength;
    public Handle(int start, int length) {
        startPosition = start;
        recordLength = length;
    }
    /**
     * Getter for startPositon
     * @return the start Position
     */
    public int getStart() {
        return startPosition;
    }
    /**
     * Getter for the Length
     * @return the length of the record
     */
    public int getLength() {
        return recordLength;
    }
    public String toString() {
        return ("Start:"+startPosition+ " Length:"+recordLength);
    }
}