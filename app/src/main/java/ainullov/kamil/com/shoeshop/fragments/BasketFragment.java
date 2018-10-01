package ainullov.kamil.com.shoeshop.fragments;

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
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.adapters.BasketAdapter;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.pojo.BasketShoe;

// Корзина
public class BasketFragment extends Fragment implements View.OnClickListener {
    List<BasketShoe> basketShoes = new ArrayList<>();
    DataBaseHelper dbHelper;
    BasketAdapter adapter;

    Button btnBuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBuy = (Button) view.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(this);


        basketShoes.clear(); // Очистка, для того, чтобы элементы не дублировались. Исправить!

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("basket", null, null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int uniquekeyColIndex = c.getColumnIndex("shoeUniquekeyBasket");
            int shoeSizeColIndex = c.getColumnIndex("shoeSize");

            do {
                basketShoes.add(new BasketShoe(c.getInt(uniquekeyColIndex), c.getString(shoeSizeColIndex)));

            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvBasket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BasketAdapter(getActivity(), basketShoes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        // На потом, открыти новогофрагмента с оформлением покупки, в данный момент при нажатии на кнопку "Купить"
        // Будут просто элементы из корзины,бд пропадать
//        ManFragment manFragment = new ManFragment();
//        WomanFragment womanFragment = new WomanFragment();
//        FragmentTransaction fTrans;
//        fTrans = getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.btnBuy:
                DataBaseHelper dbHelper;
                dbHelper = new DataBaseHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("basket", null, null);
                dbHelper.close();
                basketShoes.clear();
                adapter.notifyDataSetChanged();
                break;
        }
//        fTrans.commit();
    }
}