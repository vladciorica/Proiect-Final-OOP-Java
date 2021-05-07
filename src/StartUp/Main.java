package StartUp;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Service service = new Service();
        boolean exit_from_interface = false;
        service.load_data();
        while(!exit_from_interface) {
            System.out.println("Introduceti comanda: ");
            String comanda = in.nextLine();
            try
            {
                if (comanda.equals("createMeniu"))
                    service.createNewMeniu();
                else if (comanda.equals("createProdus")) {
                    System.out.println("Meniu Id in care cream produsul: ");
                    int MeniuId = in.nextInt();
                    in.nextLine();
                    service.createProdusNouForMeniu(MeniuId);
                } else if (comanda.equals("comandaProduse")) {
                    System.out.println("Meniu Id din care cumparam: ");
                    int MeniuId = in.nextInt();
                    in.nextLine();
                    ArrayList<Integer> produseComandate = new ArrayList<Integer>();
                    System.out.println("Numar produse comandate: ");
                    int n = in.nextInt();
                    for (int i = 0; i < n; i++) {
                        System.out.println("Produsul cu id-ul: ");
                        int produsId = in.nextInt();
                        in.nextLine();
                        produseComandate.add(produsId);
                    }
                    System.out.println("Comanda are costul de " + service.comandaRestaurant(MeniuId, produseComandate));
                } else if (comanda.equals("pretMeniuri"))
                    service.printMeniuriCost();
                else if (comanda.equals("produsPreferat")) {
                    String produs = service.mostOrderedFood();
                    System.out.println("Cel mai popular produs este: " + produs);
                } else if (comanda.equals("existaProdusulDupaId")) {
                    System.out.println("Meniu id: ");
                    int MeniuId = in.nextInt();
                    in.nextLine();
                    System.out.println("produs id: ");
                    int produsId = in.nextInt();
                    in.nextLine();
                    if (service.checkIfMeniuHasProdusById(MeniuId, produsId) != null) {
                        System.out.println("Exista Produsul");
                    } else {
                        System.out.println("Nu exista Produsul");
                    }
                } else if (comanda.equals("existaProdusulDupaNume")) {
                    System.out.println("Meniu id: ");
                    int MeniuId = in.nextInt();
                    in.nextLine();
                    System.out.println("Nume produs: ");
                    String produsName = in.nextLine();
                    if (service.checkIfMeniuHasProdusByName(MeniuId, produsName) != null) {
                        System.out.println("Exista produsul");
                    } else {
                        System.out.println("Nu exista Produsul");
                    }
                } else if (comanda.equals("afisareProduse")) {
                    System.out.println("Meniu id: ");
                    int MeniuId = in.nextInt();
                    in.nextLine();
                    service.afsiareProduse(MeniuId);
                } else if (comanda.equals("sortareProduse")) {
                    System.out.println("Meniu id: ");
                    int MeniuId = in.nextInt();
                    in.nextLine();
                    service.sortareProduseByPrice(MeniuId);
                    System.out.println("produse sortate");
                } else if (comanda.equals("meniuGol")) {
                    System.out.println("Meniu id: ");
                    int MeniuId = in.nextInt();
                    in.nextLine();
                    service.clearMeniu(MeniuId);
                    System.out.println("Meniu sters");
                } else if (comanda.equals("iesire"))
                    exit_from_interface = true;
            }
            catch(Exception e) {
                throw new Exception("Comanda Invalida");
            }
         
        }

        service.save_changes();

    }
}
