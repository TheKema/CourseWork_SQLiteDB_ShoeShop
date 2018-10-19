package ainullov.kamil.com.shoeshop.manager.dayResults;

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
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.manager.pojo.OrderAccountingPojo;

public class DayResultsFragment extends Fragment {

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

    Spinner spinnerGenderStorageContent;
    Spinner spinnerTypeStorageContent;
    TextView tvSoldSumSA;

    private static int genderPosition = 0;
    private static int typePosition = 0;
    String[] strTypeMan = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли"};
    String[] strTypeWoman = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли", "Сапоги", "Балетки"};
    String[] strType = new String[]{};
    ArrayAdapter<String> adapterType;
    // Переменные для вставки в бд
    String gender = "М";
    String type = "Кроссовки";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dayresults, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderAccountingPojos.clear();

        getActivity().setTitle("");

        spinnerGenderStorageContent = (Spinner) view.findViewById(R.id.spinnerGender);
        spinnerTypeStorageContent = (Spinner) view.findViewById(R.id.spinnerType);
        tvSoldSumSA = (TextView) view.findViewById(R.id.tvSoldSum);

        String[] strGender = new String[]{"М", "Ж", "Все"};
        final ArrayAdapter<String> adapterGender = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strGender);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenderStorageContent.setAdapter(adapterGender);
        spinnerGenderStorageContent.setPrompt("Title");
        spinnerGenderStorageContent.setSelection(genderPosition);
        spinnerGenderStorageContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                gender = adapterGender.getItem(position);
                if (gender.equals("М") || gender.equals("Ж")) {
                    spinnerTypeStorageContent.setVisibility(View.VISIBLE);

                    if (gender.equals("М"))
                        strType = strTypeMan;
                    else if (gender.equals("Ж"))
                        strType = strTypeWoman;

                    genderPosition = position;
                    adapterType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strType);
                    adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTypeStorageContent.setAdapter(adapterType);
                    spinnerTypeStorageContent.setPrompt("Title");
                    spinnerTypeStorageContent.setSelection(typePosition);


                } else if (gender.equals("Все")) {
                    spinnerTypeStorageContent.setVisibility(View.INVISIBLE);

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

                    tvSoldSumSA.setText("Сегодня продано " + orderAccountingPojos.size() + " пар(ы/а) на сумму: " + soldSum + " руб.");

                    Collections.reverse(orderAccountingPojos);
                    RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvSalesAccounting);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    DayResultsAdapter adapter = new DayResultsAdapter(getActivity(), orderAccountingPojos);
                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerTypeStorageContent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Очищаем
                orderAccountingPojos.clear();

                type = adapterType.getItem(position);
                typePosition = position;

                // Подсчет суммы проданного
                int soldSum = 0;

                SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
                String today = formatDayOfWeek.format(System.currentTimeMillis());


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

                tvSoldSumSA.setText("Сегодня продано " + orderAccountingPojos.size() + " пар(ы/а) на сумму: " + soldSum + " руб.");

                Collections.reverse(orderAccountingPojos);
                RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvSalesAccounting);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                DayResultsAdapter adapter = new DayResultsAdapter(getActivity(), orderAccountingPojos);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

}