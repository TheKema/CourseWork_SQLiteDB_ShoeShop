package ainullov.kamil.com.shoeshop.user.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

    public DataBaseHelper(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table basket ("
                + "id integer primary key autoincrement,"
                + "shoeUniquekeyBasket integer,"
                + "shoeSize text,"
                + "shoeId integer," // Потом удалить, первоначально использовалось в качестве уникального ключа
                + "gender text" + ");");

        sqLiteDatabase.execSQL("create table favorite ("
                + "id integer primary key autoincrement,"
                + "shoeUniquekeyBasket integer,"
                + "shoeSize text,"
                + "shoeId integer,"
                + "gender text" + ");");

        //Создать поле - уникальный код товара
        sqLiteDatabase.execSQL("create table shoe ("
                + "id integer primary key autoincrement,"
                + "uniquekey integer,"
                + "type text,"  // SELECT * FROM child_table WHERE parent_id = 2
                + "gender text,"
                + "quantity integer," // Если == 0, удалить
                + "name text,"
                + "coast integer,"
                + "provider text,"
                + "date real,"
                + "desciption text,"
                + "size text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
}
