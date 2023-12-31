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
 * @version 09.032023
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
    @SuppressWarnings("static-access")
    public void testInterpretStream() 
    {
        String correctOut = "";
        try 
        {
            correctOut = new String(Files.readAllBytes(Paths
                .get("P1Sample_output_1.txt")));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        SemManager manager = new SemManager();
        String[] sampleArgs = {"512", "4", "P1Sample_input_1.txt"};
        manager.main(sampleArgs);
        assertEquals(correctOut, outContent.toString());
    }
    /**
     * Test if the Stream is interpreted Correctly
     */
    @SuppressWarnings("static-access")
    public void testInterpretStream1() 
    {
        String correctOut = "";
        try 
        {
            correctOut = new String(Files.readAllBytes(Paths
                .get("P1Sample_output2.txt")));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        SemManager manager = new SemManager();
        String[] sampleArgs = {"64", "4", "P1Sample_input_1.txt"};
        manager.main(sampleArgs);
        assertEquals(correctOut, outContent.toString());
    }
    
    /**
     * test the stream again
     */
    @SuppressWarnings("static-access")
    public void testInterpretStream2() 
    {
        String correctOut = "";
        try 
        {
            correctOut = new String(Files.readAllBytes(Paths
                .get("SampleOutput2.txt")));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        SemManager manager = new SemManager();
        String[] sampleArgs = {"512", "4", "SampleInput2.txt"};
        manager.main(sampleArgs);
        assertEquals(correctOut, outContent.toString());

    }
}

