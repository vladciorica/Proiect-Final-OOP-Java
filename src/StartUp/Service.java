package StartUp;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public class Service {

    public static final String menus_filename = "Menus.csv";
    public static final String menu_factory_filename = "MenusFactory.csv";
    public static final String products_filename = "Products.csv";
    public static final String product_factory_filename = "ProductsFactory.csv";
    public static final String audit_filename = "audit.csv";

    public static final String menus_database = "menus";
    public static final String menu_factory_database = "menusfactory";
    public static final String products_database = "products";
    public static final String product_factory_database = "productsfactory";
    public static final String audit_database = "audit.csv";


    private final MenuFactory menuFactory = new MenuFactory();;
    private final ProductFactory productFactory = new ProductFactory();
    private final Set<Menu> Menus = new TreeSet<>(); // maintain them in sorted order
    private ArrayList<ArrayList<Integer>>comenzi = new ArrayList<ArrayList<Integer>>();

    public void createNewMenu() throws Exception {
        MenuFactory MenuFactory = new MenuFactory();
        MenuFactory.setID(Menus.size());
        Menu m = MenuFactory.createMenu();
        if(m != null) {
            Menus.add(m);
            audit_log("createMenu");
        }
    }

    public void createProductNouForMenu(int MenuId) throws Exception {
        Menu m = searchMenuWithId(MenuId);
        ProductFactory ProductFactory = new ProductFactory();
        ProductFactory.setID(m.getProducts().size());
        Product p = ProductFactory.createProduct();
        if(p != null) {
            p.setMenu_id(MenuId);
            m.addProduct(p);
            audit_log("createProduct");
        }
    }

    public void deleteMenu(int MenuId) throws Exception{
        Set<Menu> auxM = new TreeSet<>(); // maintain them in sorted order
        for(Menu m : Menus)
        {
            if(m.getId() != MenuId)
            {
                auxM.add(m);
            }
        }

        Menus.clear();
        for(Menu m : auxM)
            Menus.add(m);
        int nr = 0;
        for(Menu m : Menus)
            m.setId(nr++);
    }

    public void updateMenu(int MenuId,String restaurantName) throws Exception{
        for(Menu m : Menus)
        {
            if(m.getId() == MenuId)
                m.setRestaurantName(restaurantName);
        }
    }

    public void updateMaxMenu(int maxMenu)
    {
        MenuFactory.setMax_menus(maxMenu);
    }

    public void updateMaxProducts(int maxProducts)
    {
        ProductFactory.setMax_products(maxProducts);
    }

    public int comandaRestaurant(int MenuId, ArrayList<Integer> ProductsId) throws Exception {
        Menu m = searchMenuWithId(MenuId);
        int pret = 0;
        for(int id : ProductsId) {
            pret += m.searchProductById(id).getPrice();
        }
        comenzi.add(ProductsId);
        audit_log("Command");
        return pret;
    }

    public void printMenusCost() throws IOException {
        for(Menu m: Menus)
            System.out.println("Menu id: " + m.getId() + ", Cost Menu: " + m.get_total_price());
        audit_log("MenusCost");
    }

    public Product checkIfMenuHasProductById(int MenuId, int ProductId) throws Exception {
        audit_log("MenuHasProductById");
        return searchMenuWithId(MenuId).searchProductById(ProductId);
    }

    public Product checkIfMenuHasProductByName(int MenuId, String ProductName) throws Exception {
        audit_log("MenuHasProductByName");
        return searchMenuWithId(MenuId).searchProductByName(ProductName);
    }

    public void afsiareProducts(int MenuId) throws Exception {
        ArrayList<Product> Products = searchMenuWithId(MenuId).getProducts();
        ArrayList<String> numeProducts = new ArrayList<String>();
        for(Product p : Products)
            numeProducts.add(p.getName() + " " + p.getId());
        System.out.println(numeProducts);
        audit_log("PrintProducts");
    }

    public void sortareProductsByPrice(int MenuId) throws Exception {
        searchMenuWithId(MenuId).sortareProducts();
        audit_log("SortProducts");
    }

    public void clearMenu(int MenuId) throws Exception {
        searchMenuWithId(MenuId).clearMenu();
        audit_log("clearMenu");
    }
    private Menu searchMenuWithId(int MenuId) throws Exception {
        audit_log("SearchMenuId");
        for (Menu m : Menus)
            if (m.getId() == MenuId)
                return m;
        throw new Exception("Nu exista niciun Menu cu id-ul: " + MenuId);
    }

    public String mostOrderedFood() throws IOException {
        Map<String, Integer> ordered = new HashMap<>();
        Integer maxOrderedFood = 0;
        String celMaiComandatProduct = "Nu exista Product cu acest nume";
        for (Menu m: Menus) {
            for (Product p: m.getProducts()) {
                String nume_Product = p.getName();
                if (ordered.containsKey(nume_Product))
                    ordered.put(nume_Product, ordered.get(nume_Product) + 1);
                else
                    ordered.put(nume_Product, 1);

                if (ordered.get(nume_Product) > maxOrderedFood) {
                    maxOrderedFood = ordered.get(nume_Product);
                    celMaiComandatProduct = nume_Product;
                }
            }
        }
        audit_log("MostOrderedFood");
        return celMaiComandatProduct;
    }

    public void load_db_data() throws Exception
    {
        menuFactory.load_data(menu_factory_database);
        productFactory.load_data(product_factory_database);

        Database db = Database.getInstance();
        ResultSet content = db.query("SELECT idMenus,restaurantName FROM " + menus_database);
        while(content.next())
        {
            int id = content.getInt("idMenus");
            String restaurantName = content.getString("restaurantName");
            Menu m = new Menu();
            m.load_data(id,restaurantName);
            Menus.add(m);
        }


        ArrayList<Product> Products = productFactory.load_products(products_database);
        for(Product p : Products)
        {
            Menu m = searchMenuWithId(p.getMenu_id());
            p.setId(m.getProducts().size());
            m.addProduct(p);
        }
    }
    public void load_data() throws Exception
    {
        Read reader = Read.getInstance();
        ArrayList<String> content;

        content = reader.read(product_factory_filename);
        System.out.println(content);
        productFactory.parse_data(content);
        content.clear();

        content = reader.read(menu_factory_filename);
        System.out.println(content);
        menuFactory.parse_data(content);
        content.clear();

        content = reader.read(menus_filename);
        System.out.println(content);
        int i = 0; ///ignore first csv line
        for(String line : content)
        {
            if(i > 0)
            {
                Menu m = new Menu();
                m.parse_data(line);
                Menus.add(m);
            }
            i++;
        }
        content.clear();

        content = reader.read(products_filename);
        System.out.println(content);
        ArrayList<Product> Products = productFactory.parsare_Products(content);
        for(Product p : Products)
        {
            Menu m = searchMenuWithId(p.getMenu_id());
            m.addProduct(p);
        }

    }

    public void save_database_changes() throws IOException, SQLException
    {
        Database db = Database.getInstance();

        db.update("DELETE FROM " + menus_database);
        db.update("DELETE FROM " + products_database);
        db.update("DELETE FROM " + menu_factory_database);
        db.update("DELETE FROM " + product_factory_database);

        menuFactory.save_database_changes(menu_factory_database);
        productFactory.save_database_changes(product_factory_database);

        int nrProducts = 0;
        for(Menu m : Menus)
        {
            m.save_database_changes(menus_database);
            for(Product p : m.getProducts())
            {
                p.setId(nrProducts);
                nrProducts++;
                p.save_database_changes(products_database);
            }
        }
    }

    public void save_changes() throws IOException
    {
        Write write_file = Write.getInstance();

        write_file.clear(products_filename);
        write_file.clear(menus_filename);
        write_file.clear(product_factory_filename);
        write_file.clear(menu_factory_filename);

        menuFactory.save_changes(menu_factory_filename);
        productFactory.save_changes(product_factory_filename);

        for(Menu m : Menus)
        {
            m.save_changes(menus_filename);
            for(Product p : m.getProducts())
            {
                p.save_changes(products_filename);
            }
        }
    }
    private void audit_log(String action) throws IOException {
        Write write_file = Write.getInstance();
        String timestamp = String.valueOf(System.currentTimeMillis());
        write_file.write(audit_filename, action + ", " + timestamp + ",\n");
    }
}
