package ainullov.kamil.com.shoeshop.user.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.adapters.BasketAdapter;
import ainullov.kamil.com.shoeshop.user.pojo.BasketFavoriteShoe;
import ainullov.kamil.com.shoeshop.user.userOrderProduct.UserOrderProductFragment;

public class BasketFragment extends Fragment implements View.OnClickListener {
    List<BasketFavoriteShoe> basketFavoriteShoes = new ArrayList<>();
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
        getActivity().setTitle("Корзина");

        basketFavoriteShoes.clear(); // Очистка, для того, чтобы элементы не дублировались.

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(MainActivity.USERNAME_BASKET_DB, null, null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            int uniquekeyColIndex = c.getColumnIndex("shoeUniquekeyBasket");
            int shoeSizeColIndex = c.getColumnIndex("shoeSize");
            int imageurlColIndex = c.getColumnIndex("imageurl");

            do {
                basketFavoriteShoes.add(new BasketFavoriteShoe(c.getInt(uniquekeyColIndex), c.getString(imageurlColIndex), c.getString(shoeSizeColIndex)));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvBasket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BasketAdapter(getActivity(), basketFavoriteShoes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnBuy:
                if (basketFavoriteShoes.size() != 0) {
                    UserOrderProductFragment userOrderProductFragment = new UserOrderProductFragment();
                    FragmentTransaction fTrans;
                    fTrans = getFragmentManager().beginTransaction();
                    fTrans.replace(R.id.container, userOrderProductFragment);
                    fTrans.commit();
                } else
                    Toast.makeText(getActivity(), "В корзине нет товаров ", Toast.LENGTH_SHORT).show();

                break;
        }
    }
}