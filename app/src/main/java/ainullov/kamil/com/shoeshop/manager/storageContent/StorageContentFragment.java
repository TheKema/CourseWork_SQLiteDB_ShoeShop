package ainullov.kamil.com.shoeshop.manager.storageContent;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.manager.pojo.OrderAccountingPojo;

public class StorageContentFragment extends Fragment {
    List<OrderAccountingPojo> orderAccountingPojos = new ArrayList<>();
    Cursor c;
    DataBaseHelper dbHelper;
    String selection = null;
    String[] selectionArgs = null;

    int uniquekeyColIndex;
    int typeColIndex;
    int genderColIndex;
    int quantityColIndex;
    int nameColIndex;
    int coastColIndex;
    int providerColIndex;
    int dateColIndex;
    int descriptionColIndex;
    int sizeColIndex;
    int imageurlColIndex;

    Spinner spinnerGenderStorageContent;
    Spinner spinnerTypeStorageContent;


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
        return inflater.inflate(R.layout.fragment_storagecontent, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");
        orderAccountingPojos.clear();

        spinnerGenderStorageContent = (Spinner) view.findViewById(R.id.spinnerGender);
        spinnerTypeStorageContent = (Spinner) view.findViewById(R.id.spinnerType);


        String[] strGender = new String[]{"М", "Ж"};
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
                if (gender.equals("М"))
                    strType = strTypeMan;
                else
                    strType = strTypeWoman;

                genderPosition = position;
                adapterType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strType);
                adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTypeStorageContent.setAdapter(adapterType);
                // заголовок
                spinnerTypeStorageContent.setPrompt("Title");
                spinnerTypeStorageContent.setSelection(typePosition);
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


                dbHelper = new DataBaseHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                selection = "type = ? AND gender = ?";
                selectionArgs = new String[]{type, gender};
                c = db.query("shoe", null, selection, selectionArgs, null, null, null);
                c.moveToFirst();
                if (c.moveToFirst()) {
                    uniquekeyColIndex = c.getColumnIndex("uniquekey");
                    typeColIndex = c.getColumnIndex("type");
                    genderColIndex = c.getColumnIndex("gender");
                    quantityColIndex = c.getColumnIndex("quantity");
                    nameColIndex = c.getColumnIndex("name");
                    coastColIndex = c.getColumnIndex("coast");
                    providerColIndex = c.getColumnIndex("provider");
                    dateColIndex = c.getColumnIndex("date");
                    descriptionColIndex = c.getColumnIndex("description");
                    sizeColIndex = c.getColumnIndex("size");
                    imageurlColIndex = c.getColumnIndex("imageurl");

                    do {
                        orderAccountingPojos.add(new OrderAccountingPojo(c.getInt(uniquekeyColIndex),
                                c.getString(typeColIndex),
                                c.getString(genderColIndex),
                                c.getInt(quantityColIndex),
                                c.getString(nameColIndex),
                                c.getInt(coastColIndex),
                                c.getString(providerColIndex),
                                c.getString(dateColIndex),
                                c.getString(descriptionColIndex),
                                c.getString(sizeColIndex),
                                c.getString(imageurlColIndex)));
                    } while (c.moveToNext());
                }
                c.close();
                dbHelper.close();

                Collections.reverse(orderAccountingPojos);
                RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvSalesAccounting);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                StorageContentAdapter adapter = new StorageContentAdapter(getActivity(), orderAccountingPojos);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }
}