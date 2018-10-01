package ainullov.kamil.com.shoeshop.pojo;

public class OneShoe {
    private int id;        // !
    private int uniquekey; // Добавить изображение
    private String type;
    private String gender;
    private int quantity;
    private String name;
    private int coast;
    private String desc;
    private String size; // JSON строка, в которой в массиве будут находится числа

    public OneShoe(String name) {
        this.name = name;
    }

    public OneShoe(int id, int uniquekey, String type, String gender, int quantity, String name, int coast, String desc, String size) {
        this.id = id;
        this.uniquekey = uniquekey;
        this.type = type;
        this.gender = gender;
        this.quantity = quantity;
        this.name = name;
        this.coast = coast;
        this.desc = desc;
        this.size = size;
    }

    public int getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(int uniquekey) {
        this.uniquekey = uniquekey;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }


}
