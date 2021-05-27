package StartUp;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        Service service = new Service();
        boolean exit_from_interface = false;
        //service.load_data();
        service.load_db_data();
        while(!exit_from_interface) {
            System.out.println("Introduceti comanda: ");
            String comanda = in.nextLine();
            try
            {
                if (comanda.equals("createMenu"))
                    service.createNewMenu();
                else if (comanda.equals("createProduct")) {
                    System.out.println("Menu Id in care cream Produsul: ");
                    int MenuId = in.nextInt();
                    in.nextLine();
                    service.createProductNouForMenu(MenuId);
                }
                else if (comanda.equals("deleteMenu"))
                    {
                        System.out.println("Id-ul meniului pe care il stergem: ");
                        int MenuId = in.nextInt();
                        in.nextLine();
                        service.deleteMenu(MenuId);
                    }
                else if (comanda.equals("updateMenu"))
                {
                    System.out.println("Id-ul meniului pe care il updatam: ");
                    int MenuId = in.nextInt();
                    in.nextLine();
                    System.out.println("Numele restaurantului: ");
                    String restaurantName = in.nextLine();
                    service.updateMenu(MenuId,restaurantName);
                }
                else if (comanda.equals("updateMaxMenu"))
                {
                    System.out.println("Numarul maxim de meniuri: ");
                    int maxMenu = in.nextInt();
                    in.nextLine();
                    service.updateMaxMenu(maxMenu);
                }
                else if (comanda.equals("updateMaxProducts"))
                {
                    System.out.println("Numarul maxim de produse dintr-un meniu: ");
                    int maxProducts = in.nextInt();
                    in.nextLine();
                    service.updateMaxProducts(maxProducts);
                }
                else if (comanda.equals("comandaProduse")) {
                    System.out.println("Meniu Id din care cumparam: ");
                    int MenuId = in.nextInt();
                    in.nextLine();
                    ArrayList<Integer> ProductsComandate = new ArrayList<Integer>();
                    System.out.println("Numar Produse comandate: ");
                    int n = in.nextInt();
                    for (int i = 0; i < n; i++) {
                        System.out.println("Produsul cu id-ul: ");
                        int ProductId = in.nextInt();
                        in.nextLine();
                        ProductsComandate.add(ProductId);
                    }
                    System.out.println("Comanda are costul de " + service.comandaRestaurant(MenuId, ProductsComandate));
                } else if (comanda.equals("pretMeniuri"))
                    service.printMenusCost();
                else if (comanda.equals("ProdusPreferat")) {
                    String Product = service.mostOrderedFood();
                    System.out.println("Cel mai popular Produs este: " + Product);
                } else if (comanda.equals("existaProdusulDupaId")) {
                    System.out.println("Meniu id: ");
                    int MenuId = in.nextInt();
                    in.nextLine();
                    System.out.println("Produs id: ");
                    int ProductId = in.nextInt();
                    in.nextLine();
                    if (service.checkIfMenuHasProductById(MenuId, ProductId) != null) {
                        System.out.println("Exista Produsul");
                    } else {
                        System.out.println("Nu exista Produsul");
                    }
                } else if (comanda.equals("existaProdusDupaNume")) {
                    System.out.println("Menu id: ");
                    int MenuId = in.nextInt();
                    in.nextLine();
                    System.out.println("Nume Produs: ");
                    String ProductName = in.nextLine();
                    if (service.checkIfMenuHasProductByName(MenuId, ProductName) != null) {
                        System.out.println("Exista Produsul");
                    } else {
                        System.out.println("Nu exista Produse");
                    }
                } else if (comanda.equals("afisareProduse")) {
                    System.out.println("Menu id: ");
                    int MenuId = in.nextInt();
                    in.nextLine();
                    service.afsiareProducts(MenuId);
                } else if (comanda.equals("sortareProduse")) {
                    System.out.println("Menu id: ");
                    int MenuId = in.nextInt();
                    in.nextLine();
                    service.sortareProductsByPrice(MenuId);
                    System.out.println("Produse sortate");
                } else if (comanda.equals("MeniuGol")) {
                    System.out.println("Meniu id: ");
                    int MenuId = in.nextInt();
                    in.nextLine();
                    service.clearMenu(MenuId);
                    System.out.println("Meniu sters");
                } else if (comanda.equals("iesire"))
                    exit_from_interface = true;
            }
            catch(Exception e) {
                throw new Exception("Comanda Invalida");
            }
         
        }

        //service.save_changes();
        service.save_database_changes();

    }
}
