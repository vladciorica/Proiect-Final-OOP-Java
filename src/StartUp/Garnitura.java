package StartUp;

public class Garnitura extends Produs{
    private String tip;
    Garnitura()
    {
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
