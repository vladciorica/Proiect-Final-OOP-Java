package StartUp;

public class Carne extends Product{
    private String tip;
    Carne(){
        generalName = "Carne";
        tip = "pui";}
    @Override
    public String getName()
    {
        return "Carne " + tip;
    }

    @Override
    public void setPrice()
    {
        this.price = 30;
    }
}
