package StartUp;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;

public class Service {

    private final Set<Meniu> meniuri = new TreeSet<>(); // maintain them in sorted order
    private ArrayList<ArrayList<Integer>>comenzi = new ArrayList<ArrayList<Integer>>();

    public void createNewMeniu() {
        MeniuFactory MeniuFactory = new MeniuFactory();
        Meniu m = MeniuFactory.createMeniu();
        meniuri.add(m);
    }

    public void createProdusNouForMeniu(int MeniuId) throws Exception {
        Meniu m = searchMeniuWithId(MeniuId);
        ProdusFactory ProdusFactory = new ProdusFactory();
        Produs p = ProdusFactory.createProdus();

        m.addProdus(p);
    }

    public int comandaRestaurant(int MeniuId, ArrayList<Integer> ProduseId) throws Exception {
        Meniu m = searchMeniuWithId(MeniuId);
        int pret = 0;
        for(int id : ProduseId)
        {
            pret += m.searchProdusById(id).getPrice();
        }
        comenzi.add(ProduseId);
        return pret;
    }

    public void printMeniuriCost() {
        for(Meniu m: meniuri)
            System.out.println("Meniu id: " + m.getId() + ", Cost Meniu: " + m.get_total_price());
    }

    public Produs checkIfMeniuHasProdusById(int MeniuId, int ProdusId) throws Exception {
        return searchMeniuWithId(MeniuId).searchProdusById(ProdusId);
    }

    public Produs checkIfMeniuHasProdusByName(int MeniuId, String ProdusName) throws Exception {
        return searchMeniuWithId(MeniuId).searchProdusByName(ProdusName);
    }

    public void afsiareProduse(int MeniuId) throws Exception {
        ArrayList<Produs> Produse = searchMeniuWithId(MeniuId).getProduse();
        ArrayList<String> numeProduse = new ArrayList<String>();
        for(Produs p : Produse)
            numeProduse.add(p.getName());
        System.out.println(numeProduse);
    }

    public void sortareProduseByPrice(int MeniuId) throws Exception {
        searchMeniuWithId(MeniuId).sortareProduse();
    }

    public void clearMeniu(int MeniuId) throws Exception {
        searchMeniuWithId(MeniuId).clearMeniu();
    }
    private Meniu searchMeniuWithId(int MeniuId) throws Exception {
        for (Meniu m : meniuri)
            if (m.getId() == MeniuId)
                return m;
        throw new Exception("Nu exista niciun meniu cu id-ul: " + MeniuId);
    }

    public String mostOrderedFood() {
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
        return celMaiComandatProdus;
    }
}
