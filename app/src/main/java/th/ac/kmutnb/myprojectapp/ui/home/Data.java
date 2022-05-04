package th.ac.kmutnb.myprojectapp.ui.home;

public class Data {
    private String id;
    private String title;
    private int mIcon;
    private String price;
    public Data(String id,String txtview1, String price,int icon) {
        this.id=id;
        this.title = txtview1;
        this.mIcon = icon;
        this.price = price;
    }
    public String getmTitle() {
        return title;
    }
    public String getmPrice() {
        return price;
    }
    public  String getId(){
        return  id;
    }
    public int getmIcon(){return  mIcon;}


}
