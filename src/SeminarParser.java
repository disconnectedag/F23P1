import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * This class reads in txt files and
 * calls upon the commandhandler object to handle the request 
 * @author maxrojtman
 * @author agerhardt
 *
 */
public class SeminarParser {

    private Scanner sc;
    private CommandHandler commander;

    /**
     * Basic constructor that attempts to read in the file
     * @param filename: the name of the command text file
     * @param commander: the command Handler object that executes task
     */
    public SeminarParser(String filename, CommandHandler commander) {
        this.commander = commander;
        try {
            sc = new Scanner(new File(filename));
            readFile();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Reads through each line in a text file, determines which command 
     * should be called. 
     */
    private void readFile() {
        while (sc.hasNextLine()) {
            String command = "";
            try {
                command = sc.next();
            }
            catch(NoSuchElementException e) {
                continue;
            }
//            String command = sc.next();
            if (command.equals("print")) {
                String printType = sc.next();
                sc.nextLine();
                commander.print(printType);
                continue;
            }
            int id = sc.nextInt();
            sc.nextLine();
            switch (command) {
                case "insert":
                    insertMethod(id);
                    break;
                case "delete":
                    commander.delete(id);
                    break;
                case "search":
                    commander.search(id);
                    break;
            }
        }
    }

    /**
     * this reads in all of the data, and creates a seminar object
     * this seminar object is then inserted into the commandHandler
     * @param id
     */
    private void insertMethod(int id) {
        String title = sc.nextLine().trim();

        String date = sc.next().trim();
        int length = sc.nextInt();
        short xCord = sc.nextShort();
        short yCord = sc.nextShort();
        int cost = sc.nextInt();
        sc.nextLine();
        String words = sc.nextLine().trim();
        String[] keywords = words.split("\\s+");
        String desc = sc.nextLine().trim();
        Seminar entry = new Seminar(id, title, date, length, xCord, yCord,cost,keywords,desc);
        commander.insert(id,entry);
    }
}