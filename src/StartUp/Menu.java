package StartUp;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

public class Menu implements Comparable<Menu>{
    private int id;
    private String restaurantName;
    private ArrayList<Product> products = new ArrayList<>();

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    private int total_price;
    public String getRestaurantName()
    {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addProduct(Product p)
    {
        this.products.add(p);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setproducts(ArrayList<Product> products) {
        this.products = products;
    }

    public int get_total_price()
    {
        total_price = 0;
        for(Product p : products)
            total_price += p.getPrice();
        return total_price;

    }
    public void sortareProducts() {
        products.sort(Comparator.comparing(Product::getPrice));
    }

    public void clearMenu()
    {
        products.clear();
    }

    public Product searchProductById(int idProduct)
    {
        for(Product p: products)
            if(p.getId() == idProduct)
                return p;
        return null;
    }

    public Product searchProductByName(String numeProduct)
    {
        for(Product p: products)
            if(p.getName().equals(numeProduct))
                return p;
        return null;
    }

    @Override
    public int compareTo(Menu m) {
        if(m.getProducts().size() == products.size())
            return m.getId() - id;
        return m.getProducts().size() - products.size();
    }


    public void parse_data(String line)
    {
        String aux[] = line.split(",");
        String Menu_id,Menu_name;
        Menu_id = aux[0];
        Menu_name = aux[1];
        System.out.println(Menu_id + " " + Menu_name);
        this.id = Integer.parseInt(Menu_id);
        this.restaurantName = Menu_name;
    }

    public void load_data(int id, String restaurantName)
    {
        this.id = id;
        this.restaurantName = restaurantName;
    }
    public void save_changes(String filename) throws IOException
    {
        Write write_file = Write.getInstance();
        String headers = "id,restaurantName\n";
        String body = id + "," + restaurantName  + "\n";
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
        db.update("INSERT INTO " + databaseName + "(idMenus,restaurantName) VALUES " +
                "(" + id + ",'" + restaurantName + "')");
    }
}
