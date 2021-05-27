package StartUp;

public class Garnitura extends Product{
    private String tip;
    Garnitura()
    {
        generalName = "Garnitura";
        tip = "cartofi prajiti";
    }
    @Override
    public String getName()
    {
        return "Garnitura de " + tip;
    }

    @Override
    public void setPrice()
    {
        this.price = 30;
    }
}
