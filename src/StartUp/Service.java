package StartUp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public class Service {

    public static final String menus_filename = "Menus.csv";
    public static final String menu_factory_filename = "MeniuriFactory.csv";
    public static final String products_filename = "Produse.csv";
    public static final String product_factory_filename = "ProduseFactory.csv";
    public static final String audit_filename = "audit.csv";


    private final MeniuFactory menuFactory = new MeniuFactory();;
    private final ProdusFactory productFactory = new ProdusFactory();
    private final Set<Meniu> meniuri = new TreeSet<>(); // maintain them in sorted order
    private ArrayList<ArrayList<Integer>>comenzi = new ArrayList<ArrayList<Integer>>();

    public void createNewMeniu() throws IOException {
        MeniuFactory MeniuFactory = new MeniuFactory();
        Meniu m = MeniuFactory.createMeniu();
        meniuri.add(m);
        audit_log("createMeniu");
    }

    public void createProdusNouForMeniu(int MeniuId) throws Exception {
        Meniu m = searchMeniuWithId(MeniuId);
        ProdusFactory ProdusFactory = new ProdusFactory();
        Produs p = ProdusFactory.createProdus();
        p.setMeniu_id(MeniuId);
        m.addProdus(p);
        audit_log("createProduct");
    }

    public int comandaRestaurant(int MeniuId, ArrayList<Integer> ProduseId) throws Exception {
        Meniu m = searchMeniuWithId(MeniuId);
        int pret = 0;
        for(int id : ProduseId)
        {
            pret += m.searchProdusById(id).getPrice();
        }
        comenzi.add(ProduseId);
        audit_log("Command");
        return pret;
    }

    public void printMeniuriCost() throws IOException {
        for(Meniu m: meniuri)
            System.out.println("Meniu id: " + m.getId() + ", Cost Meniu: " + m.get_total_price());
        audit_log("MenusCost");
    }

    public Produs checkIfMeniuHasProdusById(int MeniuId, int ProdusId) throws Exception {
        audit_log("MenuHasProductById");
        return searchMeniuWithId(MeniuId).searchProdusById(ProdusId);
    }

    public Produs checkIfMeniuHasProdusByName(int MeniuId, String ProdusName) throws Exception {
        audit_log("MenuHasProductByName");
        return searchMeniuWithId(MeniuId).searchProdusByName(ProdusName);
    }

    public void afsiareProduse(int MeniuId) throws Exception {
        ArrayList<Produs> Produse = searchMeniuWithId(MeniuId).getProduse();
        ArrayList<String> numeProduse = new ArrayList<String>();
        for(Produs p : Produse)
            numeProduse.add(p.getName());
        System.out.println(numeProduse);
        audit_log("PrintProducts");
    }

    public void sortareProduseByPrice(int MeniuId) throws Exception {
        searchMeniuWithId(MeniuId).sortareProduse();
        audit_log("SortProducts");
    }

    public void clearMeniu(int MeniuId) throws Exception {
        searchMeniuWithId(MeniuId).clearMeniu();
        audit_log("clearMenu");
    }
    private Meniu searchMeniuWithId(int MeniuId) throws Exception {
        audit_log("SearchMeniuId");
        for (Meniu m : meniuri)
            if (m.getId() == MeniuId)
                return m;
        throw new Exception("Nu exista niciun meniu cu id-ul: " + MeniuId);
    }

    public String mostOrderedFood() throws IOException {
        Map<String, Integer> ordered = new HashMap<>();
        Integer maxOrderedFood = 0;
        String celMaiComandatProdus = "Nu exista produs cu acest nume";
        for (Meniu m: meniuri) {
            for (Produs p: m.getProduse()) {
                String nume_produs = p.getName();
                if (ordered.containsKey(nume_produs))
                    ordered.put(nume_produs, ordered.get(nume_produs) + 1);
                else
                    ordered.put(nume_produs, 1);

                if (ordered.get(nume_produs) > maxOrderedFood) {
                    maxOrderedFood = ordered.get(nume_produs);
                    celMaiComandatProdus = nume_produs;
                }
            }
        }
        audit_log("MostOrderedFood");
        return celMaiComandatProdus;
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
                Meniu m = new Meniu();
                m.parse_data(line);
                meniuri.add(m);
            }
            i++;
        }
        content.clear();

        content = reader.read(products_filename);
        System.out.println(content);
        ArrayList<Produs> produse = productFactory.parsare_produse(content);
        for(Produs p : produse)
        {
            Meniu m = searchMeniuWithId(p.getMeniu_id());
            m.addProdus(p);
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

        for(Meniu m : meniuri)
        {
            m.save_changes(menus_filename);
            for(Produs p : m.getProduse())
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
