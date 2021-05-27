package StartUp;

public class Desert extends  Product{
    private String tip;
    Desert()
    {
        generalName = "Desert";
        tip = "lava cake";}
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
