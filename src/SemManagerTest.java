import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import student.TestCase;

/**
 * THE OFFICIAL TEST CLASS
 *
 * @author maxrojtman
 * @author agerhardt
 * @version 2023-02-3
 */
public class SemManagerTest extends TestCase {

    private final ByteArrayOutputStream outContent =
            new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent =
            new ByteArrayOutputStream();
    /**
     * Standard Setup
     */
    public void setUp() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    /**
     * test isPowerOfTwo method
     */
    public void testIsPowerOfTwo()
    {
        
        int num = 3;
        assertFalse(SemManager.isPowerOfTwo(num));
        num = 4;
        assertTrue(SemManager.isPowerOfTwo(num));
        //negative number and 0
        num = -1;
        assertFalse(SemManager.isPowerOfTwo(num));
        num = 0;
        assertFalse(SemManager.isPowerOfTwo(num));
    }
    /**
     * Test if the Stream is interpreted Correctly
     */
    public void testInterpretStream() {
        String correctOut = "";
        try {
            correctOut = new String(Files.readAllBytes(Paths.get("SampleOutput.txt")));
        }catch(IOException e) {
            e.printStackTrace();
        }
        SemManager manager = new SemManager();
        String[] sampleArgs = {"512","4","SampleInput.txt"};
        manager.main(sampleArgs);
        assertEquals(correctOut, outContent.toString());
    }

    public void testInterpretStream2() {
        String correctOut = "";
        try {
            correctOut = new String(Files.readAllBytes(Paths.get("SampleOutput2.txt")));
        }catch(IOException e) {
            e.printStackTrace();
        }
        SemManager manager = new SemManager();
        String[] sampleArgs = {"512","4","SampleInput2.txt"};
        manager.main(sampleArgs);
        assertEquals(correctOut, outContent.toString());

    }
}

