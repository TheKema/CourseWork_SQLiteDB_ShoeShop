package ainullov.kamil.com.shoeshop.user.fragments;

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

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.manager.pojo.ActiveOrdersPojo;
import ainullov.kamil.com.shoeshop.user.adapters.UserOrdersHistoryAdapter;

public class UserOrdersHistoryFragment extends Fragment {
    List<ActiveOrdersPojo> activeOrdersPojos = new ArrayList<>();
    Cursor c;
    DataBaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activeorders, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");

        activeOrdersPojos.clear();

        int dateColIndex;
        int typeColIndex;
        int genderColIndex;
        int shoenameColIndex;
        int coastColIndex;
        int sizeColIndex;
        int orderNumberColIndex;

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        c = db.query(MainActivity.USERNAME_ORDERSHISTORY_DB, null, null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            dateColIndex = c.getColumnIndex("date");
            typeColIndex = c.getColumnIndex("type");
            genderColIndex = c.getColumnIndex("gender");
            shoenameColIndex = c.getColumnIndex("shoename");
            coastColIndex = c.getColumnIndex("coast");
            sizeColIndex = c.getColumnIndex("size");
            orderNumberColIndex = c.getColumnIndex("orderNumber");

            do {
                activeOrdersPojos.add(new ActiveOrdersPojo(
                        c.getString(dateColIndex),
                        c.getString(typeColIndex),
                        c.getString(genderColIndex),
                        c.getString(shoenameColIndex),
                        c.getInt(coastColIndex),
                        c.getString(sizeColIndex),
                        c.getInt(orderNumberColIndex)));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();

        Collections.reverse(activeOrdersPojos);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvActiveOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserOrdersHistoryAdapter adapter = new UserOrdersHistoryAdapter(getActivity(), activeOrdersPojos);
        recyclerView.setAdapter(adapter);
    }
}