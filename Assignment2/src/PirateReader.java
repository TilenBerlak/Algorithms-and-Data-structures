/**
 * The FileReader program reads a text file about
 * pirate data.
 *
 * @author Tilen Berlak
 * @version 1.0
 * @since 14-5-2020
 *
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class PirateReader {

    private String file;
    private int T = 0; // Number of test cases
    private String pirateString = "";

    public PirateReader(String file) {
        this.file = file;
    }

    public void readFile() throws FileNotFoundException {

        Scanner scr = new Scanner(new FileReader(file));

        // Reads number of test cases
        if(scr.hasNext())
            this.T = Integer.parseInt(scr.nextLine());

        int n = 2, M = 0, Q = 0;
        int indexQ = 0, endTestCase = 0;
        while(scr.hasNext()) {
            String s = scr.nextLine();

            if(n == 2) {
                M = Integer.parseInt(s);
                indexQ = M * 2 + n + 1;
            } else if (n == indexQ) {
                Q = Integer.parseInt(s);
                endTestCase = n + Q;
            } else if (n == endTestCase) {
                n = 2;
                M = 0;
                Q = 0;
                indexQ = 0;
                endTestCase = 0;
                continue;
            } else if (n > M && n < indexQ) {
                // Process pairs and create a pirate string
                String s2 = scr.nextLine();
                this.pirateString += processPairs(s, s2);
                n++;
            } else if (n > Q) {
                // Process questions

            }

            n++;
        }

    }

    private String processPairs(String s1, String s2) {

        int x = Integer.parseInt(s1);
        String str = "";
        for(int i = 0; i < x; i++) {
            str += s2;
        }

        return str;
    }

    private void processQuestions(int n) {

    }

    public String getPirateString() {
        return this.pirateString;
    }

}
