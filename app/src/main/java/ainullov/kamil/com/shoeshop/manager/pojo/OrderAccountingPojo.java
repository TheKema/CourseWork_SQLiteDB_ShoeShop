package ainullov.kamil.com.shoeshop.manager.pojo;

public class OrderAccountingPojo {
    private int uniquekey;
    private String type;
    private String gender;
    private int quantity;
    private String name;
    private int coast;
    private String provider;
    private String imageurl;
    private String date;
    private String description;
    private String size;

    public OrderAccountingPojo() {
    }

    // С изображением
    public OrderAccountingPojo(int uniquekey, String type, String gender, int quantity, String name, int coast, String provider, String date, String description, String size, String imageurl) {
        this.uniquekey = uniquekey;
        this.type = type;
        this.gender = gender;
        this.quantity = quantity;
        this.name = name;
        this.coast = coast;
        this.provider = provider;
        this.date = date;
        this.description = description;
        this.size = size;
        this.imageurl = imageurl;
    }

    public OrderAccountingPojo(int uniquekey, String type, String gender, int quantity, String name, int coast, String provider, String date, String description, String size) {
        this.uniquekey = uniquekey;
        this.type = type;
        this.gender = gender;
        this.quantity = quantity;
        this.name = name;
        this.coast = coast;
        this.provider = provider;
        this.date = date;
        this.description = description;
        this.size = size;
    }

    public OrderAccountingPojo(int uniquekey, String type, String gender, String name, int coast, String provider, String solddate, String size) {
        this.uniquekey = uniquekey;
        this.type = type;
        this.gender = gender;
        this.name = name;
        this.coast = coast;
        this.provider = provider;
        this.date = solddate;
        this.size = size;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(int uniquekey) {
        this.uniquekey = uniquekey;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}