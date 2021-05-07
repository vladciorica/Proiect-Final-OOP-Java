package StartUp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Read {

    private static Read instance = null;

    private Read() {
    }

    public ArrayList<String> read(String filename) throws FileNotFoundException {

        ArrayList<String> lines = new ArrayList<>();
        Scanner sc = new Scanner(new File(filename));
        ///sc.useDelimiter(",");
        while(sc.hasNext())
        {
            lines.add(sc.next().strip());
        }
        sc.close();
        return lines;
    }
    public static Read getInstance() {
        if (instance == null) {
            instance = new Read();
        }

        return instance;
    }

}
