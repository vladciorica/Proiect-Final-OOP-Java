package StartUp;

public class Pizza extends Product{

    private String tip;
    Pizza()
    {
        generalName = "Pizza";
        tip = "Diavolo";
    }
    @Override
    public String getName()
    {
        return "Pizza " + tip;
    }

    @Override
    public void setPrice()
    {
        this.price = 30;
    }
}
