package StartUp;

import java.io.FileWriter;
import java.io.IOException;

public class Write {

    private static Write instance = null;

    private Write() {
    }

    public void write(String fileName, String str) throws IOException {
        FileWriter write_file = new FileWriter(fileName, true);
        write_file.write(str);
        write_file.close();
    }

    public static Write getInstance() {
        if (instance == null) {
            instance = new Write();
        }

        return instance;
    }
}
