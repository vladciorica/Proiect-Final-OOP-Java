package StartUp;

import java.io.IOException;

public abstract  class Produs {
    private int id,meniu_id;
    protected int price;
    private String name;
    protected String generalName;
    Produs()
    {
        setPrice();
        name = getName();
    }

    public int getMeniu_id() {
        return meniu_id;
    }

    public void setMeniu_id(int meniu_id) {
        this.meniu_id = meniu_id;
    }

    abstract String getName();
    abstract void setPrice();

    int getId()
    {
        return id;
    }
    void setId(int id)
    {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void save_changes(String filename) throws IOException
    {
        Write write_file = Write.getInstance();
        String headers = "id,meniuId,Name\n";
        String body = id + "," + meniu_id  + "," + generalName +  "\n";
        if (write_file.fileEmpty(filename)) {
            write_file.write(filename, headers + body);
        }
        else {
            write_file.write(filename, body);
        }
    }
}
