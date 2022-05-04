package th.ac.kmutnb.myprojectapp.ui.order;

public class OrderModel {
    private String ID_Order;
    private String O_date;
    private int TotalAmount;
    private String status;
    private Double TotalPrice;
    private String ID_Address;
    private String ID_User;

    public OrderModel(String ID_Order, String O_date, int TotalAmount, Double TotalPrice, String status, String ID_Address,String ID_User) {
       this.ID_Order=ID_Order;
       this.O_date=O_date;
       this.TotalAmount=TotalAmount;
       this.TotalPrice=TotalPrice;
       this.status=status;
       this.ID_Address=ID_Address;
       this.ID_User=ID_User;
    }
    public String getID_Order() {
        return ID_Order;
    }

    public void setID_Order(String ID_Order) {
        this.ID_Order = ID_Order;
    }

    public String getO_date() {
        return O_date;
    }

    public void setO_date(String o_date) {
        O_date = o_date;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getID_Address() {
        return ID_Address;
    }

    public void setID_Address(String ID_Address) {
        this.ID_Address = ID_Address;
    }

    public String getID_User() {
        return ID_User;
    }

    public void setID_User(String ID_User) {
        this.ID_User = ID_User;
    }
}
