package ainullov.kamil.com.shoeshop.user.pojo;

import java.util.Comparator;

public class OneShoe {
    private int id;
    private int uniquekey;
    private String type;
    private String gender;
    private int quantity;
    private String name;
    private String imageurl;
    private int coast;
    private int discount;
    private String desc;
    private String size; // JSON строка, в которой в массиве будут находится числа

    public OneShoe(String name) {
        this.name = name;
    }

    public OneShoe(int id, int uniquekey, String type, String gender, int quantity, String name, String imageurl, int coast, int discount, String desc, String size) {
        this.id = id;
        this.uniquekey = uniquekey;
        this.type = type;
        this.gender = gender;
        this.quantity = quantity;
        this.name = name;
        this.imageurl = imageurl;
        this.coast = coast;
        this.discount = discount;
        this.desc = desc;
        this.size = size;
    }

    //Сортировка по цене с учетом скидки
    public static final Comparator<OneShoe> COMPARE_BY_COAST = new Comparator<OneShoe>() {
        @Override
        public int compare(OneShoe oneShoe, OneShoe t1) {
            int t1coast = t1.coast;
            int oneShoecoast = oneShoe.coast;
            int t1discount = t1.discount;
            int oneShoediscount = oneShoe.discount;

            int discountcoastoneShoe = 0;
            if (oneShoediscount != 0 && oneShoediscount != 100)
                discountcoastoneShoe = (int) (100 - oneShoediscount) * oneShoecoast / 100;
            if (discountcoastoneShoe != 0)
                oneShoecoast = discountcoastoneShoe;

            int discountcoastt1 = 0;
            if (t1discount != 0 && t1discount != 100)
                discountcoastt1 = (int) (100 - t1discount) * t1coast / 100;
            if (discountcoastt1 != 0)
                t1coast = discountcoastt1;

            return t1coast - oneShoecoast;
        }
    };


    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
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
