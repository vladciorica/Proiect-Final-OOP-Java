package StartUp;

public class Aperitiv extends Product{
    private String tip;
    Aperitiv()
    {
        generalName = "Aperitiv";
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
