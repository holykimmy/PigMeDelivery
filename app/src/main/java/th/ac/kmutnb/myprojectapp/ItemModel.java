package th.ac.kmutnb.myprojectapp;

public class ItemModel {
    private String ID_Food;
    private String Food_Name;

    public ItemModel(String ID_Food, String food_Name, String itemdetail, Double price, String itemcategory, String picFood) {
        this.ID_Food = ID_Food;
        Food_Name = food_Name;
        this.itemdetail = itemdetail;
        this.price = price;
        this.itemcategory = itemcategory;
        PicFood = picFood;
    }

    private String itemdetail;
    private Double price;

    private String itemcategory;
    private String PicFood;

//    public ListItemModel(int itemImage, String itemName, String itemDetail){
//        this.itemImage = itemImage;
//        this.itemName = itemName;
//        this.itemDetail = itemDetail;
//    }

    public String getItemid() { return ID_Food; }

    public void setID(String ID_Food) { this.ID_Food = ID_Food; }

    public String getItemimage(){
        return PicFood;
    }

    public void setItemimage(String PicFood) { this.PicFood = PicFood; }

    public String getItemname(){
        return Food_Name;
    }

    public void setItemname(String Food_Name) { this.Food_Name = Food_Name; }

    public String getItemdetail(){
        return itemdetail;
    }

    public void setItemdetail(String itemdetail) { this.itemdetail = itemdetail; }

    public Double getItemprice() { return price; }

    public void setItemprice(Double price) { this.price = price; }


}
