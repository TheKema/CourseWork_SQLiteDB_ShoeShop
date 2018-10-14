package ainullov.kamil.com.shoeshop;

//import android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ainullov.kamil.com.shoeshop.login.LoginFragment;
import ainullov.kamil.com.shoeshop.manager.ManagerFragment;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.fragments.BasketFragment;
import ainullov.kamil.com.shoeshop.user.fragments.FavoriteFragment;
import ainullov.kamil.com.shoeshop.user.fragments.MainFragment;
import ainullov.kamil.com.shoeshop.user.fragments.ManFragment;
import ainullov.kamil.com.shoeshop.user.fragments.WomanFragment;
import ainullov.kamil.com.shoeshop.user.pojo.ShoeType;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static String USERNAME_USER_DB = "adminuser";
    public static String USERNAME_BASKET_DB = "adminbasket";
    public static String USERNAME_FAVORITE_DB = "adminfavorite";

    public static boolean manTRUEwomanFALSE = false;
    public static String shoesTYPE = "Кроссовки";
    public static String gender = "M"; // Ж 0 - женщина, М 1 = мужчина, 2

    public static List<ShoeType> shoeTypesMan = new ArrayList<>();
    public static List<ShoeType> shoeTypesWoman = new ArrayList<>();

    ManFragment manFragment = null;
    WomanFragment womanFragment = null;
    BasketFragment basketFragment = null;
    FavoriteFragment favoriteFragment = null;
    ManagerFragment managerFragment= null;
    MainFragment mainFragment = null;

    LoginFragment loginFragment = null;

    SharedPreferences prefs = null;

    // На время. первичное добавление данных в БД, для примера
    Random random = new Random();
    int uniquekey = random.nextInt(); // При доб. тов. добавить уникальный ключ

    @Override
    protected void onResume() {
        super.onResume();

        //Первый запуск. Проверка. Заполнения БД
        if (prefs.getBoolean("firstrun", true)) {

            addToDB("Кроссовки","М", 7);
            addToDB("Кроссовки","Ж", 5);
            addToDB("Ботинки","М", 4);
            addToDB("Ботинки","Ж", 9);
            addToDB("Туфли","Ж", 8);
            addToDB("Кеды","М", 2);
            addToDB("Кеды","Ж", 6);
            addToDB("Сапоги","Ж", 5);

            prefs.edit().putBoolean("firstrun", false).apply();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginFragment = new LoginFragment();
        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.container, loginFragment);
        fTrans.commit();

// mainFragment = new MainFragment();
//        FragmentTransaction fTrans;
//        fTrans = getFragmentManager().beginTransaction();
//        fTrans.replace(R.id.container, mainFragment);
//        fTrans.commit();

        prefs = getSharedPreferences("ainullov.kamil.com.shoeshop", MODE_PRIVATE);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        shoeTypesMan.add(new ShoeType("Ботинки"));
        shoeTypesMan.add(new ShoeType("Кеды"));
        shoeTypesMan.add(new ShoeType("Кроссовки"));
        shoeTypesMan.add(new ShoeType("Туфли"));

        shoeTypesWoman.add(new ShoeType("Ботинки"));
        shoeTypesWoman.add(new ShoeType("Кеды"));
        shoeTypesWoman.add(new ShoeType("Кроссовки"));
        shoeTypesWoman.add(new ShoeType("Туфли"));
        shoeTypesWoman.add(new ShoeType("Сапоги"));
        shoeTypesWoman.add(new ShoeType("Балетки"));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_personal_area) {
            managerFragment = new ManagerFragment();
            FragmentTransaction fTrans;
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.container, managerFragment);
            fTrans.addToBackStack(null);
            fTrans.commit();
            return true;
        }
        if (id == R.id.action_basket) {
            basketFragment = new BasketFragment();
            FragmentTransaction fTrans;
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.container, basketFragment);
            fTrans.addToBackStack(null);
            fTrans.commit();

            return true;
        }
        if (id == R.id.action_favorite) {
            favoriteFragment = new FavoriteFragment();
            FragmentTransaction fTrans;
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.container, favoriteFragment);
            fTrans.addToBackStack(null);
            fTrans.commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        mainFragment = new MainFragment();
        manFragment = new ManFragment();
        womanFragment = new WomanFragment();
        basketFragment = new BasketFragment();
        favoriteFragment = new FavoriteFragment();

        managerFragment = new ManagerFragment();

        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            fTrans.replace(R.id.container, mainFragment);
            fTrans.addToBackStack(null);
        } else if (id == R.id.nav_man) {
            manTRUEwomanFALSE = true;
            gender = "М";
            // Выводим выбранный пункт в заголовке
//            setTitle(item.getTitle());
            setTitle("М");
            fTrans.replace(R.id.container, manFragment);
            fTrans.addToBackStack(null);
        } else if (id == R.id.nav_woman) {
            manTRUEwomanFALSE = false;
            gender = "Ж";
            setTitle("Ж");
            fTrans.replace(R.id.container, womanFragment);
            fTrans.addToBackStack(null);
        } else if (id == R.id.nav_favorites) {
            fTrans.replace(R.id.container, favoriteFragment);
            fTrans.addToBackStack(null);
        } else if (id == R.id.nav_basket) {
            fTrans.replace(R.id.container, basketFragment);
            fTrans.addToBackStack(null);
        } else if (id == R.id.nav_personal_area) {
            fTrans.replace(R.id.container, managerFragment);
            fTrans.addToBackStack(null);
        }
        fTrans.commit();

        // Выделяем выбранный пункт меню в шторке
        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    // Метод для заполнения ДБ при старте приложения
    private void addToDB(String type, String gender, int forLength){
        // Заполнение БД
        DataBaseHelper dbHelper;
        dbHelper = new DataBaseHelper(this);
        // подключение к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        for (int i = 0; i < forLength; i++) {
            cv.put("type", type);
            cv.put("gender", gender);
            cv.put("coast", 1190+i*20);
            cv.put("name", type+" №"+i);
            cv.put("description", "Обувь произведена в Италии");
            cv.put("provider", "ОбувьДел№"+i);
            cv.put("date", String.valueOf(System.currentTimeMillis()));
//         ArrayList, из чисел - размер обуви, превращаем в строку и суем в db, потом достанем и превратив в ArrayList
            ArrayList<String> items = new ArrayList<>();
            items.add("40");
            items.add("42");
            items.add("42");
            items.add("43");
            JSONObject json = new JSONObject();
            try {
                json.put("uniqueArrays", new JSONArray(items));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String arrayList = json.toString();

            cv.put("quantity", items.size());
            cv.put("size", arrayList);

            while (checkRepeat("shoe") != 0) {
                uniquekey = random.nextInt();
            }

            if (checkRepeat("shoe") == 0) {
                cv.put("uniquekey", uniquekey);
            }

            db.insert("shoe", null, cv);
        }
        dbHelper.close();
    }


    // На время. первичное добавление данных в БД, для примера
    // Проверка, есть ли такой же уникальный ключ
    public int checkRepeat(String tableName) {
        DataBaseHelper dbHelper;
        dbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = null;
        try {
            db = dbHelper.getReadableDatabase();
            String query = "select count(*) from " + tableName + " where uniquekey = ?";
            c = db.rawQuery(query, new String[]{String.valueOf(uniquekey)});
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
            return 0;
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }


    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("USERNAME_USER_DB",  USERNAME_USER_DB);
        outState.putString("USERNAME_BASKET_DB", USERNAME_BASKET_DB);
        outState.putString("USERNAME_FAVORITE_DB", USERNAME_FAVORITE_DB);
        outState.putBoolean("manTRUEwomanFALSE", manTRUEwomanFALSE);
        outState.putString("shoesTYPE", shoesTYPE);
        outState.putString("gender", gender);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        USERNAME_USER_DB = savedInstanceState.getString("USERNAME_USER_DB");
        USERNAME_BASKET_DB = savedInstanceState.getString("USERNAME_BASKET_DB");
        USERNAME_FAVORITE_DB = savedInstanceState.getString("USERNAME_FAVORITE_DB");
        manTRUEwomanFALSE = savedInstanceState.getBoolean("manTRUEwomanFALSE");
        shoesTYPE = savedInstanceState.getString("shoesTYPE");
        gender = savedInstanceState.getString("gender");
    }
}
