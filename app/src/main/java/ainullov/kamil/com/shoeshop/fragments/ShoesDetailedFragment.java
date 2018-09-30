package ainullov.kamil.com.shoeshop.fragments;

import android.app.Fragment;
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

public class ShoesDetailedFragment extends Fragment implements View.OnClickListener {
    ImageView ivShoeDetailed;
    TextView tvNameDetailed;
    TextView tvCoastDetailed;
    Spinner spinnerSizeDetailed;
    Button btnBasketDetailed;
    Button btnFavDetailed;
    TextView tvDescDetailed;

    DataBaseHelper dbHelper;
    String selection = null;
    String[] selectionArgs = null;

    List<String> arrayListSize = new ArrayList<>();
    Set<String> uniqueListSize;

    String[] sizesArray = {"35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45"};
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
        // sqLiteDatabase.query(INSPECTION_PLAN_TRANSACTION,
        // projection, input_number  + "= ? AND "+ name"= ?", new String[]{String.valueOf(numberToCheck), "XXX"},
        // null, null, null, null);
        selectionArgs = new String[]{String.valueOf(id)};
        // Чтение, делаем запрос всех данных из таблицы, получаем Cursor
        Cursor c = db.query("shoe", null, selection, selectionArgs, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int typeColIndex = c.getColumnIndex("type");
            int genderColIndex = c.getColumnIndex("gender");
            int quantityColIndex = c.getColumnIndex("quantity");
            int nameColIndex = c.getColumnIndex("name");
            int coastColIndex = c.getColumnIndex("coast");
            int desciptionColIndex = c.getColumnIndex("desciption");
            int sizeColIndex = c.getColumnIndex("size");

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

                tvNameDetailed.setText(c.getString(nameColIndex));
                tvCoastDetailed.setText(String.valueOf(c.getInt(coastColIndex)));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();


        // Конвертирование ArrayList в string array
        //First Step: convert ArrayList to an Object array.
        Object[] objSizes = arrayListSize.toArray();
        //Second Step: convert Object array to String array
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
                List<String> listGetPosition = new ArrayList<String>(uniqueListSize);
                sizePicked = listGetPosition.get(sizeposition);
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

                break;
            case R.id.btnFavDetailed:

                break;
        }
    }

}
