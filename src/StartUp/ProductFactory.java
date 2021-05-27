package StartUp;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Supplier;

public class ProductFactory {
    private static int ID = 0;
    private static int max_products;

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        ProductFactory.ID = ID;
    }

    HashMap<String, Supplier<Product>> types = new HashMap<>() {{
        put("Aperitiv", Aperitiv::new);
        put("Carne", Carne::new);
        put("Desert", Desert::new);
        put("Garnitura", Garnitura::new);
        put("Pizza", Pizza::new);
        put("Paste", Paste::new);
        put("Soup", Soup::new);
        put("Bauturi", Bauturi::new);
    }};

    public static int getMax_products() {
        return max_products;
    }

    public static void setMax_products(int max_products) {
        ProductFactory.max_products = max_products;
    }

    public Product createProduct() throws Exception{

        if(ID >= max_products)
        {
            System.out.println("Nu se mai pot crea produse noi in acest meniu");
            return null;
        }
        Product new_product;
        Scanner in = new Scanner(System.in);

        System.out.println("Tipul Productului: ");
        while(true) {
            String nume_Product = in.nextLine();
            if(types.containsKey(nume_Product)) {
                new_product = types.get(nume_Product).get();
                break;
            }
            System.out.println("Produse: ");
            types.forEach((type, value) -> System.out.print(type + " "));
            System.out.print("\n");
        }
        new_product.setId(ID++);

        System.out.println("Productul a fost creat cu succes si are Id-ul: " + new_product.getId());

        return new_product;
    }

    public void parse_data(ArrayList<String> content)
    {
        int i = 0; ///ignore first csv line
        for(String line : content)
        {
            if(i > 0)
            {
                setMax_products(Integer.parseInt(line));
                setID(Integer.parseInt(line));
                System.out.println(max_products);
            }
            i++;
        }
    }

    public void load_data(String database_name) throws SQLException
    {
        Database db = Database.getInstance();
        ResultSet content = db.query("SELECT maxProducts FROM " + database_name);
        content.next();
        int value = content.getInt("maxProducts");
        System.out.println(value);
        setMax_products(value);
    }

    public ArrayList<Product> parsare_Products(ArrayList<String> content)
    {
        ArrayList<Product> Products = new ArrayList<>();
        int i = 0; ///ignore first csv line
        for(String line : content)
        {
            if(i > 0)
            {
                String aux[] = line.split(",");
                String Menu_id,product_name,product_id;
                product_id = aux[0];
                Menu_id = aux[1];
                product_name = aux[2];
                System.out.println(product_id + " " + Menu_id + " " + product_name);
                Product p = types.get(product_name).get();
                p.setId(Integer.parseInt(product_id));
                p.setMenu_id(Integer.parseInt(Menu_id));
                Products.add(p);
            }
            i++;
        }
        return Products;
    }

    public ArrayList<Product> load_products(String database_name) throws SQLException
    {
        ArrayList<Product> Products = new ArrayList<>();
        Database db = Database.getInstance();
        ResultSet content = db.query("SELECT idproducts,meniuId,productName FROM " + database_name);
        while(content.next())
        {
            int product_id = content.getInt("idproducts");
            int menu_id = content.getInt("meniuId");
            String productName = content.getString("productName");
            Product p = types.get(productName).get();
            System.out.println(ID);
            p.setId(ID);
            ID++;
            p.setMenu_id(menu_id);
            Products.add(p);
        }

        return Products;
    }
    public void save_changes(String fileName) throws IOException {
        Write write_file = Write.getInstance();
        write_file.write(fileName, "MaxProducts\n" + getMax_products() + "\n");
    }

    public void save_database_changes(String databaseName) throws IOException,SQLException
    {
        Database db = Database.getInstance();
        db.update("INSERT INTO " + databaseName + "(maxProducts) VALUES (" + getMax_products() + ")");
    }
}
