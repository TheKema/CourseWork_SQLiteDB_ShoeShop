package ainullov.kamil.com.shoeshop.manager.salesVolume;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.manager.pojo.OrderAccountingPojo;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;

public class SalesVolumeFragment extends Fragment implements View.OnClickListener {

    // Будет использоваться для sold дб,
    // вместо date будет  solddate
    // вместо size в виде json будет один размер
    List<OrderAccountingPojo> orderAccountingPojos = new ArrayList<>();
    Cursor c;
    DataBaseHelper dbHelper;
    String selection = null;
    String[] selectionArgs = null;

    int uniquekeyColIndex;
    int typeColIndex;
    int genderColIndex;
    int nameColIndex;
    int coastColIndex;
    int providerColIndex;
    int solddateColIndex;
    int sizeColIndex;

    Spinner spinnerGenderSalesVolume;
    Spinner spinnerTypeSalesVolume;
    Spinner spinnerDateSalesVolume;
    TextView tvSoldSumSV;
    EditText etDate;
    Button btnGet;

    private static int genderPosition = 0;
    private static int typePosition = 0;
    private static int datePosition = 0;
    String[] strTypeMan = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли"};
    String[] strTypeWoman = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли", "Сапоги", "Балетки"};
    String[] strType = new String[]{};
    ArrayAdapter<String> adapterType;
    // Переменные для вставки в бд
    String gender = "М";
    String type = "Кроссовки";

    String date = "08.11.2018"; // ПРОВЕРИТЬ!


    // Подсчет суммы проданного
    int soldSum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_salesvolume, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderAccountingPojos.clear();

        spinnerGenderSalesVolume = (Spinner) view.findViewById(R.id.spinnerGender);
        spinnerTypeSalesVolume = (Spinner) view.findViewById(R.id.spinnerType);
        spinnerDateSalesVolume = (Spinner) view.findViewById(R.id.spinnerDate);
        tvSoldSumSV = (TextView) view.findViewById(R.id.tvSoldSum);
        etDate = (EditText) view.findViewById(R.id.etDate);
        btnGet = (Button) view.findViewById(R.id.btnGet);
        btnGet.setOnClickListener(this);

        String[] strGender = new String[]{"М", "Ж", "Все"};
        final ArrayAdapter<String> adapterGender = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strGender);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenderSalesVolume.setAdapter(adapterGender);
        spinnerGenderSalesVolume.setPrompt("Title");
        spinnerGenderSalesVolume.setSelection(genderPosition);
        spinnerGenderSalesVolume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                gender = adapterGender.getItem(position);
                Toast.makeText(getActivity(), "type " + type + " gender " + gender, Toast.LENGTH_SHORT).show();
                if (gender.equals("М") || gender.equals("Ж")) {
                    spinnerTypeSalesVolume.setVisibility(View.VISIBLE);

                    if (gender.equals("М"))
                        strType = strTypeMan;
                    else if (gender.equals("Ж"))
                        strType = strTypeWoman;

                    genderPosition = position;
                    adapterType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strType);
                    adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTypeSalesVolume.setAdapter(adapterType);
                    // заголовок
                    spinnerTypeSalesVolume.setPrompt("Title");
                    spinnerTypeSalesVolume.setSelection(typePosition);


                } else if (gender.equals("Все")) {
                    spinnerTypeSalesVolume.setVisibility(View.INVISIBLE);

                    orderAccountingPojos.clear();

                    // Подсчет суммы проданного
                    int soldSum = 0;

                    SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
                    String today = formatDayOfWeek.format(System.currentTimeMillis());

                    dbHelper = new DataBaseHelper(getActivity());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    c = db.query("sold", null, null, null, null, null, null);
                    c.moveToFirst();
                    if (c.moveToFirst()) {
                        uniquekeyColIndex = c.getColumnIndex("uniquekey");
                        typeColIndex = c.getColumnIndex("type");
                        genderColIndex = c.getColumnIndex("gender");
                        nameColIndex = c.getColumnIndex("name");
                        coastColIndex = c.getColumnIndex("coast");
                        providerColIndex = c.getColumnIndex("provider");
                        solddateColIndex = c.getColumnIndex("solddate");
                        sizeColIndex = c.getColumnIndex("size");

                        do {
                            // Добавляем только те, у которых день эквивалентен текущему дню

                            String dayOfWeek = formatDayOfWeek.format(Double.valueOf(c.getString(solddateColIndex)));
                            if (today.equals(dayOfWeek)) {

                                orderAccountingPojos.add(new OrderAccountingPojo(c.getInt(uniquekeyColIndex),
                                        c.getString(typeColIndex),
                                        c.getString(genderColIndex),
                                        c.getString(nameColIndex),
                                        c.getInt(coastColIndex),
                                        c.getString(providerColIndex),
                                        c.getString(solddateColIndex),
                                        c.getString(sizeColIndex)));
                                soldSum += c.getInt(coastColIndex);
                            }
                        } while (c.moveToNext());
                    }
                    c.close();
                    dbHelper.close();

                    tvSoldSumSV.setText("Продано " + orderAccountingPojos.size() + " пар(ы/а) на сумму: " + soldSum + " руб.");

                    Collections.reverse(orderAccountingPojos);
                    RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvSalesAccounting);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    SalesVolumeAdapter adapter = new SalesVolumeAdapter(getActivity(), orderAccountingPojos);
                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        spinnerTypeSalesVolume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Очищаем
                orderAccountingPojos.clear();

                type = adapterType.getItem(position);
                typePosition = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        String[] strDate = new String[]{"День", "Месяц"};
        final ArrayAdapter<String> adapterDay = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strDate);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDateSalesVolume.setAdapter(adapterDay);
//        spinnerDateSalesVolume.setPrompt("Title");
//        spinnerDateSalesVolume.setSelection(genderPosition);
        spinnerDateSalesVolume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                date = adapterDay.getItem(position);
                Toast.makeText(getActivity(), "date " + date, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGet:
                orderAccountingPojos.clear();

                SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
                String today = formatDayOfWeek.format(System.currentTimeMillis());

                if (!etDate.getText().toString().equals(null) || !etDate.getText().toString().equals("")) {
                    if (date.equals("День")) {
                        formatDayOfWeek = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
                        today = etDate.getText().toString();

                    } else if (date.equals("Месяц")) {
                        formatDayOfWeek = new SimpleDateFormat("MM.yy", Locale.ENGLISH);
                        today = etDate.getText().toString();

                    }
                }

                soldSum = 0;


                dbHelper = new DataBaseHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                selection = "type = ? AND gender = ?";
                selectionArgs = new String[]{type, gender};
                c = db.query("sold", null, selection, selectionArgs, null, null, null);
                c.moveToFirst();
                if (c.moveToFirst()) {
                    uniquekeyColIndex = c.getColumnIndex("uniquekey");
                    typeColIndex = c.getColumnIndex("type");
                    genderColIndex = c.getColumnIndex("gender");
                    nameColIndex = c.getColumnIndex("name");
                    coastColIndex = c.getColumnIndex("coast");
                    providerColIndex = c.getColumnIndex("provider");
                    solddateColIndex = c.getColumnIndex("solddate");
                    sizeColIndex = c.getColumnIndex("size");


                    do {
                        // Добавляем только те, у которых день эквивалентен текущему дню

                        String dayOfWeek = formatDayOfWeek.format(Double.valueOf(c.getString(solddateColIndex)));
                        if (today.equals(dayOfWeek)) {

                            orderAccountingPojos.add(new OrderAccountingPojo(c.getInt(uniquekeyColIndex),
                                    c.getString(typeColIndex),
                                    c.getString(genderColIndex),
                                    c.getString(nameColIndex),
                                    c.getInt(coastColIndex),
                                    c.getString(providerColIndex),
                                    c.getString(solddateColIndex),
                                    c.getString(sizeColIndex)));
                            soldSum += c.getInt(coastColIndex);
                        }
                    } while (c.moveToNext());
                }
                c.close();
                dbHelper.close();

                tvSoldSumSV.setText("Сегодня продано " + orderAccountingPojos.size() + " пар(ы/а) на сумму: " + soldSum + " руб.");

                Collections.reverse(orderAccountingPojos);
                RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvSalesAccounting);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                SalesVolumeAdapter adapter = new SalesVolumeAdapter(getActivity(), orderAccountingPojos);
                recyclerView.setAdapter(adapter);
                break;
        }
    }
}