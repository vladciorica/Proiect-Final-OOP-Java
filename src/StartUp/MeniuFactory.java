package StartUp;

import java.util.ArrayList;
import java.util.Scanner;

public class MeniuFactory {
    private static int ID = 0;
    private static int max_menus;

    public static int getMax_menus() {
        return max_menus;
    }

    public static void setMax_menus(int max_menus) {
        MeniuFactory.max_menus = max_menus;
    }

    public Meniu createMeniu() {
        Meniu new_meniu = new Meniu();
        Scanner in = new Scanner(System.in);

        System.out.println("Numele restaurantului: ");
        new_meniu.setRestaurantName(in.nextLine());
        new_meniu.setId(ID++);

        System.out.println("Meniu creat cu succes pentru restaurantul cu Id: " + new_meniu.getId());

        return new_meniu;
    }

    public void parse_data(ArrayList<String> content)
    {
        int i = 0; ///ignore first csv line
        for(String line : content)
        {
            if(i > 0)
            {
                setMax_menus(Integer.parseInt(line));
                System.out.println(max_menus);
            }
            i++;
        }
    }
}
