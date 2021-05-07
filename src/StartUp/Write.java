package StartUp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Write {

    private static Write instance = null;

    private Write() {
    }

    public void clear(String fileName) throws IOException
    {
        new FileWriter(fileName).close();
    }

    public Boolean fileEmpty(String fileName) throws IOException {
        File file = new File(fileName);
        return file.length() == 0;
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
