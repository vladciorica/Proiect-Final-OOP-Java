package StartUp;

public class Aperitiv extends Produs{
    private String tip;
    Aperitiv()
    {
        tip = "tartar de somon";

    }
    @Override
    public String getName()
    {
        return "Aperitiv " + tip;
    }

    @Override
    public void setPrice()
    {
        this.price = 30;
    }
}
