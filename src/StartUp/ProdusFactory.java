package StartUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Supplier;

public class ProdusFactory {
    private static int ID = 0;
    private static int max_products;

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        ProdusFactory.ID = ID;
    }

    HashMap<String, Supplier<Produs>> types = new HashMap<>() {{
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
        ProdusFactory.max_products = max_products;
    }

    public Produs createProdus() {
        Produs new_product;
        Scanner in = new Scanner(System.in);

        System.out.println("Tipul produsului: ");
        while(true) {
            String nume_produs = in.nextLine();
            if(types.containsKey(nume_produs)) {
                new_product = types.get(nume_produs).get();
                break;
            }
            System.out.println("Produse: ");
            types.forEach((type, value) -> System.out.print(type + " "));
            System.out.print("\n");
        }
        new_product.setId(ID++);

        System.out.println("Produsul a fost creat cu succes si are Id-ul: " + new_product.getId());

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

    public ArrayList<Produs> parsare_produse(ArrayList<String> content)
    {
        ArrayList<Produs> produse = new ArrayList<>();
        int i = 0; ///ignore first csv line
        for(String line : content)
        {
            if(i > 0)
            {
                String aux[] = line.split(",");
                String meniu_id,product_name,product_id;
                product_id = aux[0];
                meniu_id = aux[1];
                product_name = aux[2];
                System.out.println(product_id + " " + meniu_id + " " + product_name);
                Produs p = types.get(product_name).get();
                p.setId(Integer.parseInt(product_id));
                p.setMeniu_id(Integer.parseInt(meniu_id));
                produse.add(p);
            }
            i++;
        }
        return produse;
    }

    public void save_changes(String fileName) throws IOException {
        Write write_file = Write.getInstance();
        write_file.write(fileName, "MaxProducts\n" + getMax_products() + "\n");
    }
}
