package StartUp;

import java.io.IOException;
import java.sql.SQLException;

public abstract  class Product {
    private int id,Menu_id;
    protected int price;
    private String name;
    protected String generalName;
    Product()
    {
        setPrice();
        name = getName();
    }

    public int getMenu_id() {
        return Menu_id;
    }

    public void setMenu_id(int Menu_id) {
        this.Menu_id = Menu_id;
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
        String headers = "id,MenuId,Name\n";
        String body = id + "," + Menu_id  + "," + generalName +  "\n";
        if (write_file.fileEmpty(filename)) {
            write_file.write(filename, headers + body);
        }
        else {
            write_file.write(filename, body);
        }
    }

    public void save_database_changes(String databaseName) throws SQLException
    {
        Database db = Database.getInstance();
        db.update("INSERT INTO " + databaseName + "(idproducts, meniuId, productName) VALUES " +
                "(" + id + "," + Menu_id + ",'" + generalName + "')");
    }
}
