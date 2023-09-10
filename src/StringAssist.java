/**
 * String assist class
 *
 * @author maxrojtman
 * @author agerhardt
 * @version 09.032023
 */
public class StringAssist 
{
    /**
     * constructor function
     */
    public StringAssist() {
    }
    
    /**
     * helps the memory root string output
     * @param printout is the string
     * @return is a String of the output
     */
    public String toStringHelper(String printout) 
    {
        String[] lines = printout.split("\n");
        int lineCount = 0;
        int[][] array = new int[lines.length][2];
        
        for (String line : lines) 
        {
            if (!line.contains(":")) 
            {
                continue;
            }
            
            String[] tuple = line.split(": ");
            
            if (tuple.length < 2) 
            {
                System.out.print("Can't parse" + line);
            }
            
            int size = Integer.parseInt(tuple[0].trim());
            int location = Integer.parseInt(tuple[1].trim());
            
            array[lineCount][0] = size;
            array[lineCount][1] = location;
            lineCount++;
        }
        
        // Resize the array to fit only the valid elements
        int[][] resizedArray = new int[lineCount][2];
        System.arraycopy(array, 0, resizedArray, 0, lineCount);
        
        sortArray(resizedArray);
        return strVal(resizedArray);
    }
     /**
      * gets the string value of the array
      * @param array to be made to a string
      * @return string of the array
      */
    public String strVal(int[][] array) 
    {
        int prevSize = -1;
        StringBuilder output = new StringBuilder("");
        
        for (int[] arr : array) 
        {
            if (arr[0] == prevSize) 
            {
                output.append(" " + String.valueOf(arr[1]));
                continue;
            }
            output.append("\n" + String.valueOf(arr[0]) + ": " + 
                String.valueOf(arr[1]));
            prevSize = arr[0];
        }
        
        return output.toString();
    }
    /**
     * sort algorithm
     * @param array is the array to sort
     */
    public static void sortArray(int[][] array) 
    {
        for (int i = 0; i < array.length - 1; i++) 
        {
            for (int j = 0; j < array.length - i - 1; j++) 
            {
                if (array[j][0] != array[j + 1][0]) 
                {
                    if (array[j][0] > array[j + 1][0]) 
                    {
                        swap(array, j, j + 1);
                    }
                } 
                else 
                {
                    if (array[j][1] > array[j + 1][1]) 
                    {
                        swap(array, j, j + 1);
                    }
                }
            }
        }
    }
    
    /**
     * swaps positions
     * @param array is the array
     * @param i is first position
     * @param j is second position
     */
    private static void swap(int[][] array, int i, int j) 
    {
        int[] temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}