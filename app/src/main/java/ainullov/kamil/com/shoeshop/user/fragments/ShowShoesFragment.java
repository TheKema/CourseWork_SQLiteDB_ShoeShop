package ainullov.kamil.com.shoeshop.user.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.user.adapters.ShowShoesAdapter;
import ainullov.kamil.com.shoeshop.user.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.pojo.OneShoe;

//Вид обуви
public class ShowShoesFragment extends Fragment {

    List<OneShoe> shoes = new ArrayList<>();
    DataBaseHelper dbHelper;
    String selection = null;
    String[] selectionArgs = null;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_showshoes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoes.clear();

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        selection = "type = ? AND gender = ?";
        selectionArgs = new String[]{MainActivity.shoesTYPE, MainActivity.gender};

        Cursor c = db.query("shoe", null, selection, selectionArgs, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int uniquekeyColIndex = c.getColumnIndex("uniquekey");
            int typeColIndex = c.getColumnIndex("type");
            int genderColIndex = c.getColumnIndex("gender");
            int quantityColIndex = c.getColumnIndex("quantity");
            int nameColIndex = c.getColumnIndex("name");
            int coastColIndex = c.getColumnIndex("coast");
            int descriptionColIndex = c.getColumnIndex("description");
            int sizeColIndex = c.getColumnIndex("size");

            do {
//            if (!c.getString(typeColIndex).isEmpty())
                shoes.add(new OneShoe(c.getInt(idColIndex),
                        c.getInt(uniquekeyColIndex),
                        c.getString(typeColIndex),
                        c.getString(genderColIndex),
                        c.getInt(quantityColIndex),
                        c.getString(nameColIndex),
                        c.getInt(coastColIndex),
                        c.getString(descriptionColIndex),
                        c.getString(sizeColIndex)));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();


        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvShow);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ShowShoesAdapter adapter = new ShowShoesAdapter(getActivity(), shoes);
        recyclerView.setAdapter(adapter);

    }


}
