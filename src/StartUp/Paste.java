package StartUp;

public class Paste extends Product{

    private String tip;
    Paste()
    {
        generalName = "Paste";
        tip = "Carbonnara";
    }
    @Override
    public String getName()
    {
        return "Paste " + tip;
    }

    @Override
    public void setPrice()
    {
        this.price = 30;
    }
}
