package ainullov.kamil.com.shoeshop.manager.activeOrders;

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
import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.manager.pojo.ActiveOrdersPojo;

public class ActiveOrdersFragment extends Fragment {
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

        int nameColIndex;
        int numberColIndex;
        int dateColIndex;
        int typeColIndex;
        int genderColIndex;
        int shoenameColIndex;
        int coastColIndex;
        int sizeColIndex;
        int orderNumberColIndex;
        int emailColIndex;

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        c = db.query("orders", null, null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            nameColIndex = c.getColumnIndex("name");
            numberColIndex = c.getColumnIndex("number");
            dateColIndex = c.getColumnIndex("date");
            typeColIndex = c.getColumnIndex("type");
            genderColIndex = c.getColumnIndex("gender");
            shoenameColIndex = c.getColumnIndex("shoename");
            coastColIndex = c.getColumnIndex("coast");
            sizeColIndex = c.getColumnIndex("size");
            orderNumberColIndex = c.getColumnIndex("orderNumber");
            emailColIndex = c.getColumnIndex("email");

            do {
                activeOrdersPojos.add(new ActiveOrdersPojo(
                        c.getString(nameColIndex),
                        c.getString(numberColIndex),
                        c.getString(dateColIndex),
                        c.getString(typeColIndex),
                        c.getString(genderColIndex),
                        c.getString(shoenameColIndex),
                        c.getInt(coastColIndex),
                        c.getString(sizeColIndex),
                        c.getInt(orderNumberColIndex),
                        c.getString(emailColIndex)));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvActiveOrders);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ActiveOrdersAdapter adapter = new ActiveOrdersAdapter(getActivity(), activeOrdersPojos);
        recyclerView.setAdapter(adapter);
    }


}