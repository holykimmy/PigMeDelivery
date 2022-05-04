package th.ac.kmutnb.myprojectapp.ui.order;

public class OrderDetailModel {
    private int ID_Detail;
    private int ID_Food;
    private String Fname ;
    private Double Price ;
    private int Amount ;
    private String PicFood;

    public OrderDetailModel(int ID_Detail, int ID_Food, String fname, Double price, int amount,String PicFood) {
        this.ID_Detail = ID_Detail;
        this.ID_Food = ID_Food;
        this.Fname = fname;
        this.Price = price;
        this.Amount = amount;
        this.PicFood= PicFood;
    }

    public String getPicFood() {
        return PicFood;
    }

    public void setPicFood(String picFood) {
        PicFood = picFood;
    }

    public int getID_Detail() {
        return ID_Detail;
    }

    public void setID_Detail(int ID_Detail) {
        this.ID_Detail = ID_Detail;
    }

    public int getID_Food() {
        return ID_Food;
    }

    public void setID_Food(int ID_Food) {
        this.ID_Food = ID_Food;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }


    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

}
