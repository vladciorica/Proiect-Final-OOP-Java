package StartUp;

public class Bauturi extends Product{
    private String tip;
    private int cantitate,alcoolemie;
    Bauturi()
    {
        generalName = "Bauturi";
        tip = "cola";
    }
    @Override
    public String getName()
    {
        return "Bautura " + tip;
    }

    @Override
    public void setPrice()
    {
        this.price = 10;
    }
}
