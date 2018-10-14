package ainullov.kamil.com.shoeshop.manager.ordersAccounting;

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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.manager.pojo.OrderAccountingPojo;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;


public class OrdersAccountingFragment extends Fragment {
    List<OrderAccountingPojo> orderAccountingPojos = new ArrayList<>();
    Cursor c;
    DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ordersaccounting, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderAccountingPojos.clear();

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

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        c = db.query("deliveries", null, null, null, null, null, null);
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
                        c.getString(sizeColIndex)));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();

        Collections.reverse(orderAccountingPojos);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvOrdersAccounting);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        OrdersAccountingAdapter adapter = new OrdersAccountingAdapter(getActivity(), orderAccountingPojos);
        recyclerView.setAdapter(adapter);
    }


}