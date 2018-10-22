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

        sqLiteDatabase.execSQL("create table workers ("
                + "id integer primary key autoincrement,"
                + "uniquekey integer,"
                + "name text,"
                + "rating integer" + ");");

        sqLiteDatabase.execSQL("create table shoe ("
                + "id integer primary key autoincrement,"
                + "uniquekey integer,"
                + "type text,"
                + "gender text,"
                + "quantity integer,"
                + "name text,"
                + "coast integer,"
                + "discount integer,"
                + "provider text,"
                + "imageurl text," // использую сайт https://imgbb.com/ для хранения фотографий обуви
                + "date text,"
                + "description text,"
                + "size text" + ");");

        sqLiteDatabase.execSQL("create table deliveries (" // Для учета поставок
                + "id integer primary key autoincrement,"
                + "uniquekey integer,"
                + "type text,"
                + "gender text,"
                + "quantity integer,"
                + "name text,"
                + "coast integer,"
                + "discount integer,"
                + "provider text,"
                + "date text,"
                + "description text,"
                + "size text" + ");");

        sqLiteDatabase.execSQL("create table sold ("
                + "id integer primary key autoincrement,"
                + "uniquekey integer,"
                + "type text,"
                + "gender text,"
                + "name text,"
                + "coast integer,"
                + "discount integer,"
                + "provider text,"
                + "solddate text," //
                + "size text" + ");");

        sqLiteDatabase.execSQL("create table orders ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "number text,"
                + "date text,"
                + "type text,"
                + "gender text,"
                + "shoename text,"
                + "coast integer,"
                + "size text,"
                + "orderNumber integer,"
                + "email text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void createClientDB(DataBaseHelper dbHelper, String dbName) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        sqLiteDatabase.execSQL("create table " + dbName + "user ("
                + "id integer primary key autoincrement,"
                + "login text,"
                + "password text,"
                + "name text,"
                + "number text,"
                + "email text" + ");");

        sqLiteDatabase.execSQL("create table " + dbName + "basket ("
                + "id integer primary key autoincrement,"
                + "shoeUniquekeyBasket integer,"
                + "shoeSize text,"
                + "shoeId integer,"
                + "imageurl text,"
                + "gender text" + ");");

        sqLiteDatabase.execSQL("create table " + dbName + "favorite ("
                + "id integer primary key autoincrement,"
                + "shoeUniquekeyBasket integer,"
                + "shoeSize text,"
                + "shoeId integer,"
                + "imageurl text,"
                + "gender text" + ");");

    }
}
