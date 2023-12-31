import org.junit.Assert;
import student.TestCase;
/**
 * Stores the Position and record length, used in a hash map
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */
public class DoubleHashTest extends TestCase
{
    private DoubleHash<Integer> openDSA;
    
    /**
     * sets up each test case
     */
    public void setUp() 
    {
        openDSA = new DoubleHash<Integer>(16);
    }
    /**
     * tests insert
     */
    public void testInsert() 
    {
        openDSA.insert(55, 55);
        Assert.assertEquals(openDSA.toString(), "Hashtable:\n7: "
            + "55\ntotal records: 1");
    }
    
    /**
     * tests insert with collisions
     */
    public void testInsertWCollisions() 
    {
        openDSA.insert(55, 55);
        openDSA.insert(39, 39);
        openDSA.insert(92, 92);
        Assert.assertEquals(openDSA.toString(), "Hashtable:\n2: 92\n7: "
            + "55\n12: 39\ntotal records: 3");
    }
    
    /**
     * tests deletion
     */
    public void testDeletion() 
    {
        openDSA.insert(55, 55);
        openDSA.delete(55);
        openDSA.insert(39, 39);
        Assert.assertEquals(openDSA.toString(), "Hashtable:\n7: "
            + "39\ntotal records: 1");
    }
    
    /**
     * tests deletion problem
     */
    public void testDeletionProblem() 
    {
        DoubleHash<Object> tester = new DoubleHash<Object>(4);
        String expectedOut = "Hashtable:\n"
            + "1: 81\n"
            + "3: 3\n"
            + "4: 4\n"
            + "5: 3\n"
            + "total records: 4";
        tester.insert(44, 44);
        tester.delete(44);
        tester.insert(81, 81);
        tester.insert(4, tester);
        tester.insert(3, 3);
        tester.insert(3, tester);
        assertEquals(tester.toString(), expectedOut);

    }

}
