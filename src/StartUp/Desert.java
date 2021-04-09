package StartUp;

public class Desert extends  Produs{
    private String tip;
    Desert()
    {tip = "lava cake";}
    @Override
    public String getName()
    {
        return "Desert " + tip;
    }

    @Override
    public void setPrice()
    {
        this.price = 30;
    }
}
