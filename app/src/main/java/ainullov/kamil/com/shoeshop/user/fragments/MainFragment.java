package ainullov.kamil.com.shoeshop.user.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
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
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.adapters.ShowShoesAdapter;
import ainullov.kamil.com.shoeshop.user.pojo.OneShoe;

public class MainFragment extends Fragment implements View.OnClickListener {
    Button btnManMain;
    Button btnWomanMain;
    ImageView ivHeader;

    List<OneShoe> shoes = new ArrayList<>();
    DataBaseHelper dbHelper;

//  Если понадобится поворот экрана (в MА, в OnCreate в этом случае переделать создание MainFragment)
//  @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivHeader = (ImageView) view.findViewById(R.id.ivHeader);
        btnManMain = (Button) view.findViewById(R.id.btnManMain);
        btnWomanMain = (Button) view.findViewById(R.id.btnWomanMain);
        btnManMain.setOnClickListener(this);
        btnWomanMain.setOnClickListener(this);
        getActivity().setTitle("Котобувь");

        // в MainFragment происходит очищение bakcstack
        FragmentManager fm = getActivity().getFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        shoes.clear();

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor c = db.query("shoe", null, null, null, null, null, null);
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
                if (c.getInt(discountColIndex) != 0 && c.getInt(discountColIndex) != 100)
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

        // Чтобы показывались сначала самые новые
        Collections.reverse(shoes);
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvMain);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ShowShoesAdapter adapter = new ShowShoesAdapter(getActivity(), shoes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        ManFragment manFragment = new ManFragment();
        WomanFragment womanFragment = new WomanFragment();
        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.btnManMain:
                MainActivity.manTRUEwomanFALSE = true;
                MainActivity.gender = "М";
                fTrans.replace(R.id.container, manFragment);
                getActivity().setTitle("М");
                fTrans.addToBackStack(null);
                break;
            case R.id.btnWomanMain:
                MainActivity.manTRUEwomanFALSE = false;
                MainActivity.gender = "Ж";
                getActivity().setTitle("Ж");
                fTrans.replace(R.id.container, womanFragment);
                fTrans.addToBackStack(null);
                break;
        }
        fTrans.commit();
    }
}