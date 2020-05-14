import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        PirateReader pReader = new PirateReader("Test cases/1.IN");
        pReader.readFile();
        System.out.println(pReader.getPirateString());
    }

}
