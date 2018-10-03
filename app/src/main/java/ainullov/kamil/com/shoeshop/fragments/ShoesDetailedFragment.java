package ainullov.kamil.com.shoeshop.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;

//Конкретная информация о товаре
public class ShoesDetailedFragment extends Fragment implements View.OnClickListener {
    ImageView ivShoeDetailed;
    TextView tvNameDetailed;
    TextView tvCoastDetailed;
    Spinner spinnerSizeDetailed;
    Button btnBasketDetailed;
    Button btnFavDetailed;
    TextView tvDescDetailed;


    int shoeUniquekeyBasket;
    Cursor c;
    DataBaseHelper dbHelper;
    String selection = null;
    String[] selectionArgs = null;
    int idColIndex;
    int uniquekeyColIndex;
    int typeColIndex;
    int genderColIndex;
    int quantityColIndex;
    int nameColIndex;
    int coastColIndex;
    int desciptionColIndex;
    int sizeColIndex;


    List<String> arrayListSize = new ArrayList<>();
    Set<String> uniqueListSize;
    int sizeposition = 0;
    String sizePicked = "42";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shoesdetailed, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ivShoeDetailed = (ImageView) view.findViewById(R.id.ivShoeDetailed);
        tvNameDetailed = (TextView) view.findViewById(R.id.tvNameDetailed);
        tvCoastDetailed = (TextView) view.findViewById(R.id.tvCoastDetailed);
        tvDescDetailed = (TextView) view.findViewById(R.id.tvDescDetailed);
        spinnerSizeDetailed = (Spinner) view.findViewById(R.id.spinnerSizeDetailed);
        btnBasketDetailed = (Button) view.findViewById(R.id.btnBasketDetailed);
        btnFavDetailed = (Button) view.findViewById(R.id.btnFavDetailed);
        btnBasketDetailed.setOnClickListener(this);
        btnFavDetailed.setOnClickListener(this);


        Bundle bundle = this.getArguments();
        int id = bundle.getInt("id");

        dbHelper = new DataBaseHelper(getActivity());
        // подключение к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        selection = "id = ?";
        selectionArgs = new String[]{String.valueOf(id)};
        // Чтение, делаем запрос всех данных из таблицы, получаем Cursor
        c = db.query("shoe", null, selection, selectionArgs, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            idColIndex = c.getColumnIndex("id");
            uniquekeyColIndex = c.getColumnIndex("uniquekey");
            typeColIndex = c.getColumnIndex("type");
            genderColIndex = c.getColumnIndex("gender");
            quantityColIndex = c.getColumnIndex("quantity");
            nameColIndex = c.getColumnIndex("name");
            coastColIndex = c.getColumnIndex("coast");
            desciptionColIndex = c.getColumnIndex("desciption");
            sizeColIndex = c.getColumnIndex("size");

            do {
                try {
                    JSONObject json = new JSONObject(c.getString(sizeColIndex));
                    JSONArray items = json.optJSONArray("uniqueArrays");
                    for (int i = 0; i < items.length(); i++) {
                        arrayListSize.add(items.optString(i));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                shoeUniquekeyBasket = c.getInt(uniquekeyColIndex);
                tvNameDetailed.setText(c.getString(nameColIndex));
                tvCoastDetailed.setText(String.valueOf(c.getInt(coastColIndex)));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();

        //!!!
        // На будущее, когда нужно будет разбираться с размерами и количеством
        // Конвертирование ArrayList в string array
        Object[] objSizes = arrayListSize.toArray();
        String[] strSizes = Arrays.copyOf(objSizes, objSizes.length, String[].class);

        //Убрать повторяющиеся размеры
        // Нет сортировки по размеру
        uniqueListSize = new HashSet<String>(arrayListSize);
        Object[] objUniqueSizes = uniqueListSize.toArray();
        String[] strUniqueSizes = Arrays.copyOf(objUniqueSizes, objUniqueSizes.length, String[].class);

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strUniqueSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSizeDetailed.setAdapter(adapter);
        // заголовок
        spinnerSizeDetailed.setPrompt("Title");
        // выделяем элемент
        spinnerSizeDetailed.setSelection(0);
        // устанавливаем обработчик нажатия
        spinnerSizeDetailed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                sizeposition = position;
                // для метода get, чтобы получить значение выбранной позиции
                List<String> listGetPosition = new ArrayList<>(uniqueListSize);
                sizePicked = listGetPosition.get(sizeposition);

                Toast.makeText(getActivity(), "Pos " + sizePicked, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBasketDetailed:

                if (checkRepeat("basket") == 0) {

                    DataBaseHelper dbHelper;
                    dbHelper = new DataBaseHelper(getActivity());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues cv = new ContentValues();
                    cv.put("shoeUniquekeyBasket", shoeUniquekeyBasket);
                    cv.put("shoeSize", sizePicked);
                    db.insert("basket", null, cv);
                    dbHelper.close();

                }

                break;
            case R.id.btnFavDetailed:

                if (checkRepeat("favorite") == 0) {

                    DataBaseHelper dbHelperFav;
                    dbHelperFav = new DataBaseHelper(getActivity());
                    SQLiteDatabase dbFav = dbHelperFav.getWritableDatabase();

                    ContentValues cvFav = new ContentValues();
                    cvFav.put("shoeUniquekeyBasket", shoeUniquekeyBasket);
                    cvFav.put("shoeSize", sizePicked);
                    dbFav.insert("favorite", null, cvFav);
                    dbHelperFav.close();
                }

                break;
        }
    }


    // Проверка, добавлялся ли ранее товар в корзину или в избранное
    public int checkRepeat(String tableName) {
        DataBaseHelper dbHelper;
        dbHelper = new DataBaseHelper(getActivity());
        // подключение к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = null;
        try {
            db = dbHelper.getReadableDatabase();
            String query = "select count(*) from "+tableName+" where shoeUniquekeyBasket = ?";
            c = db.rawQuery(query, new String[]{String.valueOf(shoeUniquekeyBasket)});
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


}
