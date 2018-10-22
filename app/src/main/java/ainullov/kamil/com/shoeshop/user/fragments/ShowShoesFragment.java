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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.adapters.ShowShoesAdapter;
import ainullov.kamil.com.shoeshop.user.pojo.OneShoe;

//Вид обуви
public class ShowShoesFragment extends Fragment {
    List<OneShoe> shoes = new ArrayList<>();
    DataBaseHelper dbHelper;
    String selection = null;
    String[] selectionArgs = null;
    Cursor c;

    Spinner spinnerShowShoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_showshoes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        shoes.clear();

        spinnerShowShoes = (Spinner) view.findViewById(R.id.spinnerShowShoes);

        String[] strSortBy = new String[]{"убыванию цены", "возрастанию цены", "скидке"};
        final ArrayAdapter<String> adapterSortBy = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strSortBy);
        adapterSortBy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerShowShoes.setAdapter(adapterSortBy);
        spinnerShowShoes.setPrompt("Title");
//        spinnerShowShoes.setSelection(genderPosition);
        spinnerShowShoes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {


                // Очищаем
                shoes.clear();

//                int typePosition = position;


                dbHelper = new DataBaseHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                selection = "type = ? AND gender = ?";
                selectionArgs = new String[]{MainActivity.shoesTYPE, MainActivity.gender};
//                selectionArgs = new String[]{type, gender};
                c = db.query("shoe", null, selection, selectionArgs, null, null, null);
                c.moveToFirst();
                if (c.moveToFirst()) {
                    int idColIndex = c.getColumnIndex("id");
                    int uniquekeyColIndex = c.getColumnIndex("uniquekey");
                    int typeColIndex = c.getColumnIndex("type");
                    int genderColIndex = c.getColumnIndex("gender");
                    int quantityColIndex = c.getColumnIndex("quantity");
                    int nameColIndex = c.getColumnIndex("name");
                    int coastColIndex = c.getColumnIndex("coast");
                    int discountColIndex = c.getColumnIndex("discount");
                    int descriptionColIndex = c.getColumnIndex("description");
                    int sizeColIndex = c.getColumnIndex("size");
                    int imageurlColIndex = c.getColumnIndex("imageurl");

                    do {
                        if (position == 2) {
                            if (c.getInt(discountColIndex) != 0 && c.getInt(discountColIndex) != 100) {
                                shoes.add(new OneShoe(c.getInt(idColIndex),
                                        c.getInt(uniquekeyColIndex),
                                        c.getString(typeColIndex),
                                        c.getString(genderColIndex),
                                        c.getInt(quantityColIndex),
                                        c.getString(nameColIndex),
                                        c.getString(imageurlColIndex),
                                        c.getInt(coastColIndex),
                                        c.getInt(discountColIndex),
                                        c.getString(descriptionColIndex),
                                        c.getString(sizeColIndex)));
                            }
                        } else {
                            shoes.add(new OneShoe(c.getInt(idColIndex),
                                    c.getInt(uniquekeyColIndex),
                                    c.getString(typeColIndex),
                                    c.getString(genderColIndex),
                                    c.getInt(quantityColIndex),
                                    c.getString(nameColIndex),
                                    c.getString(imageurlColIndex),
                                    c.getInt(coastColIndex),
                                    c.getInt(discountColIndex),
                                    c.getString(descriptionColIndex),
                                    c.getString(sizeColIndex)));
                        }
                    } while (c.moveToNext());
                }
                c.close();
                dbHelper.close();

                if (position == 0)
                    Collections.sort(shoes, OneShoe.COMPARE_BY_COAST);
                if (position == 1) {
                    Collections.sort(shoes, OneShoe.COMPARE_BY_COAST);
                    Collections.reverse(shoes);
                }
                RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvShow);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                ShowShoesAdapter adapter = new ShowShoesAdapter(getActivity(), shoes);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

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
            int discountColIndex = c.getColumnIndex("discount");
            int descriptionColIndex = c.getColumnIndex("description");
            int sizeColIndex = c.getColumnIndex("size");
            int imageurlColIndex = c.getColumnIndex("imageurl");

            do {
                shoes.add(new OneShoe(c.getInt(idColIndex),
                        c.getInt(uniquekeyColIndex),
                        c.getString(typeColIndex),
                        c.getString(genderColIndex),
                        c.getInt(quantityColIndex),
                        c.getString(nameColIndex),
                        c.getString(imageurlColIndex),
                        c.getInt(coastColIndex),
                        c.getInt(discountColIndex),
                        c.getString(descriptionColIndex),
                        c.getString(sizeColIndex)));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();


        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvShow);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        ShowShoesAdapter adapter = new ShowShoesAdapter(getActivity(), shoes);
        recyclerView.setAdapter(adapter);
    }

}
