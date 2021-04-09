package StartUp;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Supplier;

public class ProdusFactory {
    private static int ID = 0;

    public Produs createProdus() {
        Produs new_product;
        Scanner in = new Scanner(System.in);

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
}
