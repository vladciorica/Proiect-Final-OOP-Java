package StartUp;

public class Write {

    private static Write instance = null;

    private Write() {
    }

    public static Write getInstance() {
        if (instance == null) {
            instance = new Write();
        }

        return instance;
    }
}
