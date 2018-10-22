package ainullov.kamil.com.shoeshop.manager.pojo;

public class ActiveOrdersPojo {
    private int id;
    private String name;
    private String number;
    private String date;
    private String type;
    private String gender;
    private String shoename;
    private int coast;
    private int discount;
    private String size;
    private int orderNumber;
    private String email;

    public ActiveOrdersPojo(String name, String number, String date,
                            String type, String gender, String shoename,
                            int coast, String size, int orderNumber, String email) {
        this.name = name;
        this.number = number;
        this.date = date;
        this.type = type;
        this.gender = gender;
        this.shoename = shoename;
        this.coast = coast;
        this.size = size;
        this.orderNumber = orderNumber;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getShoename() {
        return shoename;
    }

    public void setShoename(String shoename) {
        this.shoename = shoename;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}