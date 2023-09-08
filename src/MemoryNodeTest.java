import student.TestCase;

/**
 * Memory root class
 *
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */
public class MemoryNodeTest extends TestCase 
{
    private MemoryNode tester;
    private MemoryRoot testRoot;

    /**
     * sets up each test case
     */
    public void setUp() 
    {
        tester = new MemoryNode(8, 0);
    }

    /**
     * test empty
     */
    public void testEmpty() 
    {
        assertTrue(tester.isEmpty());
        tester.split();
        assertTrue(tester.isEmpty());
        tester.collapse();
        assertFalse(tester.getSplitted());
    }

    /**
     * test root
     */
    public void testRoot() {
        testRoot = new MemoryRoot(32);

        assertTrue(testRoot.getHead().isEmpty());
        assertEquals(testRoot.toString(), "Freeblock List:\n" + "32: 0");
        testRoot.insertData(10);
        assertEquals(testRoot.toString(), "Freeblock List:\n" + "16: 16");
        testRoot.getHead().isEmpty();
        assertFalse(testRoot.getHead().isEmpty());
    }
    
    /**
     * test root delete
     */
    public void testRootDelete() {
        testRoot = new MemoryRoot(4);
        Handle seven = testRoot.insertData("7777777".getBytes());
        testRoot.insertData("88888888".getBytes());
        testRoot.insertData("4444".getBytes());
        testRoot.deleteData(seven.getStart());
        System.out.print(testRoot);
    }

}
