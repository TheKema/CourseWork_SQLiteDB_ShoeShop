package ainullov.kamil.com.shoeshop.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL("create table shoestype ("
//                + "id integer primary key autoincrement,"
//                + "type text,"
//                + "gender text" + ");");

        sqLiteDatabase.execSQL("create table shoe ("
                + "id integer primary key autoincrement,"
                + "type text,"  // SELECT * FROM child_table WHERE parent_id = 2
                + "gender text,"
                + "quantity integer," // Если == 0, удалить
                + "name text,"
                + "coast integer,"
                + "desciption text,"
                + "inf text,"
                + "size integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
