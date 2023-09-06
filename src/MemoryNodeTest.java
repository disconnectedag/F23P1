import student.TestCase;

/**
 * Memory root class
 *
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */
public class MemoryNodeTest extends TestCase {
    MemoryNode tester;
    MemoryRoot testRoot;

    public void setUp() {
        tester = new MemoryNode(8, 0);
    }


    public void testEmpty() {
        assertTrue(tester.isEmpty());
        tester.split();
        assertTrue(tester.isEmpty());
        tester.collapse();
        assertFalse(tester.splitted);
    }


    public void testRoot() {
        testRoot = new MemoryRoot(32);

        assertTrue(testRoot.head.isEmpty());
        assertEquals(testRoot.toString(), "Freeblock List:\n" + "32: 0");
        testRoot.insertData(10);
        assertEquals(testRoot.toString(), "Freeblock List:\n" + "16: 16");
        boolean ma = testRoot.head.isEmpty();
        assertFalse(testRoot.head.isEmpty());
    }
    public void testRootDelete() {
        testRoot = new MemoryRoot(4);
        Handle seven = testRoot.insertData("7777777".getBytes());
        Handle eight = testRoot.insertData("88888888".getBytes());
        Handle four = testRoot.insertData("4444".getBytes());
        testRoot.deleteData(seven.getStart());
        System.out.print(testRoot);
    }

}
