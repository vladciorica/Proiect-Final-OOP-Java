package StartUp;

public abstract  class Produs {
    private int id;
    protected int price;
    private String name;
    Produs()
    {
        setPrice();
        name = getName();
    }
    abstract String getName();
    abstract void setPrice();
    int getId()
    {
        return id;
    }
    void setId(int id)
    {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }
}
