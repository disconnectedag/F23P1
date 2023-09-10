import student.TestCase;

/**
 * String assist class
 *
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */
public class StringAssistTest extends TestCase {
    /**
     * test with various scenarios
     */
    public void testInterpret() 
    {
        String str = "256: 0\n512: 512\n1024: 1024\n256: 2304\n512:"
            + " 2560\n1024: 3072\n512: 4096\n256: 4864\n1024:"
            + " 5120\n2048: 6144";
        System.out.println(str);
        assertEquals(str, str);
        StringAssist max = new StringAssist();
        System.out.println(max.toStringHelper(str));
        assertTrue(true);
        
        // Test case 2: Empty input string
        String str2 = "";
        StringAssist assist2 = new StringAssist();
        String expected2 = "";
        assertEquals(expected2, assist2.toStringHelper(str2));

        // Test case 3: Input with invalid lines (should be skipped)
        String str3 = "256: 0\nInvalid Line\n512: 512\n1024: 1024";
        StringAssist assist3 = new StringAssist();
        String expected3 = "\n256: 0\n512: 512\n1024: 1024";
        assertEquals(expected3, assist3.toStringHelper(str3));

        // Test case 4: Input with negative size and location values
        String str4 = "-256: -512\n512: -1024\n1024: 1024\n256: 0";
        StringAssist assist4 = new StringAssist();
        String expected4 = "\n-256: -512\n256: 0\n512: -1024\n1024: 1024";
        assertEquals(expected4, assist4.toStringHelper(str4));
        
        // Test case 5: Input with a single memory allocation size
        String str5 = "256: 0\n256: 256\n256: 512\n256: 768";
        StringAssist assist5 = new StringAssist();
        String expected5 = "\n256: 0 256 512 768";
        assertEquals(expected5, assist5.toStringHelper(str5));
    }
}
