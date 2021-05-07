package StartUp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Meniu implements Comparable<Meniu>{
    private int id;
    private String restaurantName;
    private ArrayList<Produs> produse = new ArrayList<>();

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    private int total_price;
    public String getRestaurantName()
    {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addProdus(Produs p)
    {
        this.produse.add(p);
    }

    public ArrayList<Produs> getProduse() {
        return produse;
    }

    public void setProduse(ArrayList<Produs> produse) {
        this.produse = produse;
    }

    public int get_total_price()
    {
        for(Produs p : produse)
            total_price += p.getPrice();
        return total_price;

    }
    public void sortareProduse() {
        produse.sort(Comparator.comparing(Produs::getPrice));
    }

    public void clearMeniu()
    {
        produse.clear();
    }

    public Produs searchProdusById(int idProdus)
    {
        for(Produs p: produse)
            if(p.getId() == idProdus)
                return p;
        return null;
    }

    public Produs searchProdusByName(String numeProdus)
    {
        for(Produs p: produse)
            if(p.getName().equals(numeProdus))
                return p;
        return null;
    }

    @Override
    public int compareTo(Meniu m) {
        if(m.getProduse().size() == produse.size())
            return m.getId() - id;
        return m.getProduse().size() - produse.size();
    }


    public void parse_data(String line)
    {
        String aux[] = line.split(",");
        String meniu_id,meniu_name;
        meniu_id = aux[0];
        meniu_name = aux[1];
        System.out.println(meniu_id + " " + meniu_name);
        this.id = Integer.parseInt(meniu_id);
        this.restaurantName = meniu_name;
    }

    public void save_changes(String filename) throws IOException
    {
        Write write_file = Write.getInstance();
        String headers = "id,restaurantName\n";
        String body = id + "," + restaurantName  + "\n";
        if (write_file.fileEmpty(filename)) {
            write_file.write(filename, headers + body);
        }
        else {
            write_file.write(filename, body);
        }
    }
}
