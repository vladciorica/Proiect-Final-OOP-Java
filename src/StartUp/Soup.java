package StartUp;

public class Soup extends Produs{
    private String tip;
    Soup()
    {
        generalName = "Soup";
        tip = "de burta";
    }
    @Override
    public String getName()
    {
        return "Supa " + tip;
    }

    @Override
    public void setPrice()
    {
        this.price = 30;
    }
}
