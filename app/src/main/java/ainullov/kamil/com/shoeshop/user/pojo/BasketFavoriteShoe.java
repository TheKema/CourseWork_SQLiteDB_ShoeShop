package ainullov.kamil.com.shoeshop.user.pojo;

public class BasketFavoriteShoe {
    private int id;
    private int uniquekey; // Оставить только уникальный код, id и размер !
    private String name;
    private int coast;
    private String imageurl;

    private String size; // JSON строка, в которой в массиве будут находится числа

//    public BasketFavoriteShoe(int uniquekey, String size) {
//        this.uniquekey = uniquekey;
//        this.size = size;
//    }

    public BasketFavoriteShoe(int uniquekey, String imageurl, String size) {
        this.uniquekey = uniquekey;
        this.imageurl = imageurl;
        this.size = size;
    }

    public BasketFavoriteShoe(int id, int uniquekey, String name, int coast, String size) {
        this.id = id;
        this.uniquekey = uniquekey;
        this.name = name;
        this.coast = coast;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
