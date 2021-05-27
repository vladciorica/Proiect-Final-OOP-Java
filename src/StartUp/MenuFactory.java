package StartUp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MenuFactory {
    private static int ID = 0;
    private static int max_menus;

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        MenuFactory.ID = ID;
    }

    public static int getMax_menus() {
        return max_menus;
    }

    public static void setMax_menus(int max_menus) {
        MenuFactory.max_menus = max_menus;
    }

    public Menu createMenu() throws Exception{
        if(ID >= max_menus)
        {
            System.out.println("Nu se mai pot crea meniuri noi");
            return null;
        }
        Menu new_Menu = new Menu();
        Scanner in = new Scanner(System.in);

        System.out.println("Numele restaurantului: ");
        new_Menu.setRestaurantName(in.nextLine());
        new_Menu.setId(ID++);

        System.out.println("Meniu creat cu succes pentru restaurantul cu Id: " + new_Menu.getId());

        return new_Menu;
    }

    public void parse_data(ArrayList<String> content)
    {
        int i = 0; ///ignore first csv line
        for(String line : content)
        {
            if(i > 0)
            {
                setMax_menus(Integer.parseInt(line));
                setID(Integer.parseInt(line));
                System.out.println(max_menus);
            }
            i++;
        }
    }

    public void load_data(String database_name) throws SQLException
    {
        Database db = Database.getInstance();
        ResultSet content = db.query("SELECT maxNumberMenus FROM " + database_name);
        content.next();
        int value = content.getInt("maxNumberMenus");
        System.out.println(value);
        setMax_menus(value);
        setID(value);
    }

    public void save_changes(String fileName) throws IOException {
        Write write_file = Write.getInstance();
        write_file.write(fileName, "MaxNumberMenus\n" + getMax_menus() + "\n");
    }

    public void save_database_changes(String databaseName) throws IOException,SQLException
    {
        Database db = Database.getInstance();
        db.update("INSERT INTO " + databaseName + "(maxNumberMenus) VALUES (" + getMax_menus() + ")");
    }
}
