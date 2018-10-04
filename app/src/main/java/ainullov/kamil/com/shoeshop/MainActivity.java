package ainullov.kamil.com.shoeshop;

//import android.app.Fragment;

import android.app.FragmentTransaction;
import android.content.ContentValues;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.user.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.fragments.BasketFragment;
import ainullov.kamil.com.shoeshop.user.fragments.FavoriteFragment;
import ainullov.kamil.com.shoeshop.user.fragments.MainFragment;
import ainullov.kamil.com.shoeshop.user.fragments.ManFragment;
import ainullov.kamil.com.shoeshop.user.fragments.WomanFragment;
import ainullov.kamil.com.shoeshop.user.pojo.ShoeType;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static boolean manTRUEwomanFALSE = false;
    public static String shoesTYPE = "Кроссовки";
    public static String gender = "M"; // Ж 0 - женщина, М 1 = мужчина, 2

    // !ВРЕМЕННО
    public static boolean VREMENNO = true;

    public static List<ShoeType> shoeTypesMan = new ArrayList<>();
    public static List<ShoeType> shoeTypesWoman = new ArrayList<>();


    MainFragment mainFragment = new MainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.container, mainFragment);
        fTrans.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (VREMENNO) {
            // !ВРЕМЕННО ...
            DataBaseHelper dbHelper;
            dbHelper = new DataBaseHelper(this);
            // подключение к БД
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            // подключение к БД
            cv.put("type", "Кроссовки");
            cv.put("gender", "М");
            cv.put("uniquekey", 0);
            cv.put("coast", 1399);
            cv.put("name", "Крос №1");

            // ArrayList, из чисел - размер обуви, превращаем в строку и суем в db, потом достанем и превратив в ArrayList
            ArrayList<String> items = new ArrayList<>();
            items.add("40");
            items.add("42");
            items.add("42");
            JSONObject json = new JSONObject();
            try {
                json.put("uniqueArrays", new JSONArray(items));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String arrayList = json.toString();

            cv.put("quantity", items.size());
            cv.put("size", arrayList);
            Toast.makeText(this,"sizes "+ arrayList, Toast.LENGTH_SHORT).show();
            // вставляем запись
            db.insert("shoe", null, cv);

            cv.put("type", "Кроссовки");
            cv.put("gender", "М");
            cv.put("uniquekey", 1);
            cv.put("quantity", items.size());
            cv.put("coast", 2239);
            cv.put("name", "Крос №2");
            db.insert("shoe", null, cv);

            cv.put("type", "Ботинки");
            cv.put("gender", "Ж");
            cv.put("uniquekey", 2);
            cv.put("coast", 2990);
            cv.put("quantity", items.size());
            cv.put("name", "Ботинки №1");
            // вставляем запись
            db.insert("shoe", null, cv);

            cv.put("type", "Кроссовки");
            cv.put("gender", "Ж");
            cv.put("uniquekey", 3);
            cv.put("coast", 990);
            cv.put("quantity", items.size());
            cv.put("name", "Крос №3");
            db.insert("shoe", null, cv);


            dbHelper.close();
            VREMENNO = false;
        }
        // ... ВРЕМЕННО!


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
            ManagerFragment managerFragment = new ManagerFragment();
            FragmentTransaction fTrans;
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.container, managerFragment);
            fTrans.addToBackStack(null);
            fTrans.commit();
            return true;
        }
        if (id == R.id.action_basket) {
            BasketFragment basketFragment = new BasketFragment();
            FragmentTransaction fTrans;
            fTrans = getFragmentManager().beginTransaction();
            fTrans.replace(R.id.container, basketFragment);
            fTrans.addToBackStack(null);
            fTrans.commit();

            return true;
        }
        if (id == R.id.action_favorite) {
            FavoriteFragment favoriteFragment = new FavoriteFragment();
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

        ManFragment manFragment = new ManFragment();
        WomanFragment womanFragment = new WomanFragment();
        BasketFragment basketFragment = new BasketFragment();
        FavoriteFragment favoriteFragment = new FavoriteFragment();

        ManagerFragment managerFragment = new ManagerFragment();

        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();
        int id = item.getItemId();

        if (id == R.id.nav_main) {
            fTrans.replace(R.id.container, mainFragment);
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
}
