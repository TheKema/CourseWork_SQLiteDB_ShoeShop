package ainullov.kamil.com.shoeshop.manager.salesAccounting;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.manager.pojo.OrderAccountingPojo;
import ainullov.kamil.com.shoeshop.user.db.DataBaseHelper;

public class SalesAccountingFragment extends Fragment {

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

    Spinner spinnerGenderSalesAccounting;
    Spinner spinnerTypeSalesAccounting;
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
        return inflater.inflate(R.layout.fragment_salesaccounting, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderAccountingPojos.clear();

        spinnerGenderSalesAccounting = (Spinner) view.findViewById(R.id.spinnerGender);
        spinnerTypeSalesAccounting = (Spinner) view.findViewById(R.id.spinnerType);
        tvSoldSumSA = (TextView) view.findViewById(R.id.tvSoldSum);

        String[] strGender = new String[]{"М", "Ж"};
        final ArrayAdapter<String> adapterGender = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strGender);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenderSalesAccounting.setAdapter(adapterGender);
        spinnerGenderSalesAccounting.setPrompt("Title");
        spinnerGenderSalesAccounting.setSelection(genderPosition);
        spinnerGenderSalesAccounting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                gender = adapterGender.getItem(position);
                Toast.makeText(getActivity(), "type " + type + " gender " + gender, Toast.LENGTH_SHORT).show();
                if (gender.equals("М"))
                    strType = strTypeMan;
                else
                    strType = strTypeWoman;

                genderPosition = position;
                adapterType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strType);
                adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerTypeSalesAccounting.setAdapter(adapterType);
                // заголовок
                spinnerTypeSalesAccounting.setPrompt("Title");
                spinnerTypeSalesAccounting.setSelection(typePosition);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerTypeSalesAccounting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Очищаем
                orderAccountingPojos.clear();

                type = adapterType.getItem(position);
                typePosition = position;

                // Подсчет суммы проданного
                int soldSum = 0;

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
                        orderAccountingPojos.add(new OrderAccountingPojo(c.getInt(uniquekeyColIndex),
                                c.getString(typeColIndex),
                                c.getString(genderColIndex),
                                c.getString(nameColIndex),
                                c.getInt(coastColIndex),
                                c.getString(providerColIndex),
                                c.getString(solddateColIndex),
                                c.getString(sizeColIndex)));
                        soldSum += c.getInt(coastColIndex);
                    } while (c.moveToNext());
                }
                c.close();
                dbHelper.close();

                tvSoldSumSA.setText("Продано " + orderAccountingPojos.size() + " пар(ы/а) на сумму: " + soldSum + " руб.");

                Collections.reverse(orderAccountingPojos);
                RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvSalesAccounting);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                SalesAccountingAdapter adapter = new SalesAccountingAdapter(getActivity(), orderAccountingPojos);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }


}
