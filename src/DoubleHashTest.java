import org.junit.Assert;
import student.TestCase;
public class DoubleHashTest extends TestCase{

    DoubleHash openDSA;
    public void setUp() {
        openDSA = new DoubleHash(16);
    }
    public void testInsert() {
        openDSA.insert(55, 55);
        Assert.assertEquals(openDSA.toString(), "Hashtable:\n7: 55\ntotal records: 1");
    }
    public void testInsertWCollisions() {
        openDSA.insert(55, 55);
        openDSA.insert(39, 39);
        openDSA.insert(92, 92);
        Assert.assertEquals(openDSA.toString(), "Hashtable:\n2: 92\n7: 55\n12: 39\ntotal records: 3");
    }
    public void testDeletion() {
        openDSA.insert(55, 55);
        openDSA.delete(55);
        openDSA.insert(39, 39);
        Assert.assertEquals(openDSA.toString(), "Hashtable:\n7: 39\ntotal records: 1");
    }
    public void testDeletionProblem() {
        DoubleHash tester = new DoubleHash(4);
        tester.insert(44, 44);
        tester.delete(44);
        tester.insert(81, 81);
        tester.insert(4, tester);
        tester.insert(3,3);
        tester.insert(3, tester);
        System.out.print(tester.toString());

    }
}
