/**
 * Stores the Position and record length, used in a hash map
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */
public class Handle 
{
    private int startPosition;
    private int recordLength;
    
    /**
     * constructor
     * @param start is the start position
     * @param length is the end position
     */
    public Handle(int start, int length) 
    {
        startPosition = start;
        recordLength = length;
    }
    /**
     * Getter for startPositon
     * @return the start Position
     */
    public int getStart() 
    {
        return startPosition;
    }
    /**
     * Getter for the Length
     * @return the length of the record
     */
    public int getLength() 
    {
        return recordLength;
    }
    /**
     * toString method
     */
    public String toString() 
    {
        return ("Start:" + startPosition + " Length:" + recordLength);
    }
}