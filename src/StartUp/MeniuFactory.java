package StartUp;

import java.util.Scanner;

public class MeniuFactory {
    private static int ID = 0;

    public Meniu createMeniu() {
        Meniu new_meniu = new Meniu();
        Scanner in = new Scanner(System.in);

        System.out.println("Numele restaurantului: ");
        new_meniu.setRestaurantName(in.nextLine());
        new_meniu.setId(ID++);

        System.out.println("Meniu creat cu succes pentru restaurantul cu Id: " + new_meniu.getId());

        return new_meniu;
    }
}
