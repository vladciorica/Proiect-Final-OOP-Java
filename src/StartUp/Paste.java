package StartUp;

public class Paste extends Produs{

    private String tip;
    Paste()
    {
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
