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
import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.user.adapters.FavoriteAdapter;
import ainullov.kamil.com.shoeshop.user.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.pojo.BasketFavoriteShoe;

// Корзина
public class FavoriteFragment extends Fragment{
    List<BasketFavoriteShoe> favoriteShoes = new ArrayList<>();
    DataBaseHelper dbHelper;
    FavoriteAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Избранное");
        favoriteShoes.clear(); // Очистка, для того, чтобы элементы не дублировались. Исправить!

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("favorite", null, null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int uniquekeyColIndex = c.getColumnIndex("shoeUniquekeyBasket");
            int shoeSizeColIndex = c.getColumnIndex("shoeSize");

            do {
                favoriteShoes.add(new BasketFavoriteShoe(c.getInt(uniquekeyColIndex), c.getString(shoeSizeColIndex)));

            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvBasket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new FavoriteAdapter(getActivity(), favoriteShoes);
        recyclerView.setAdapter(adapter);
    }
}

