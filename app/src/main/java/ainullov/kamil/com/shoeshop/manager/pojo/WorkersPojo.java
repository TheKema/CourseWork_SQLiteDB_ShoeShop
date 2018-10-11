package ainullov.kamil.com.shoeshop.manager.pojo;

import java.util.Comparator;

public class WorkersPojo {
    private int id;
    private String name;
    private int rating;
    private int uniquekey;

    public WorkersPojo(String name, int rating, int uniquekey) {
        this.name = name;
        this.rating = rating;
        this.uniquekey = uniquekey;
    }

    public int getUniquekey() {
        return uniquekey;
    }

    public void setUniquekey(int uniquekey) {
        this.uniquekey = uniquekey;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    public static final Comparator<WorkersPojo> COMPARE_BY_RATING = new Comparator<WorkersPojo>() {
        @Override
        public int compare(WorkersPojo workersPojo, WorkersPojo t1) {
            return t1.rating - workersPojo.rating;
        }
    };
}