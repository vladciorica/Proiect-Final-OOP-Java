package StartUp;

public class Bauturi extends Produs{
    private String tip;
    private int cantitate,alcoolemie;
    Bauturi()
    {
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
