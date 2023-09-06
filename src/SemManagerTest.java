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

