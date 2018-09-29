package ainullov.kamil.com.shoeshop.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.pojo.OneShoe;

public class MainFragment extends Fragment implements View.OnClickListener {
    Button btnManMain;
    Button btnWomanMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnManMain = (Button) view.findViewById(R.id.btnManMain);
        btnWomanMain = (Button) view.findViewById(R.id.btnWomanMain);
        btnManMain.setOnClickListener(this);
        btnWomanMain.setOnClickListener(this);


        DataBaseHelper dbHelper;
        dbHelper = new DataBaseHelper(getActivity());
        // подключение к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // !ВРЕМЕННО ...

        ContentValues cv = new ContentValues();
        // подключение к БД
        cv.put("type", "Кроссовки");
        cv.put("gender", "М");
        cv.put("name", "Крос №1");
        // вставляем запись
        db.insert("shoe", null, cv);

        cv.put("type", "Кроссовки");
        cv.put("gender", "М");
        cv.put("name", "Крос №2");
        db.insert("shoe", null, cv);

        cv.put("type", "Ботинки");
        cv.put("gender", "Ж");
        cv.put("name", "Ботинки №1");
        // вставляем запись
        db.insert("shoe", null, cv);

        cv.put("type", "Кроссовки");
        cv.put("gender", "Ж");
        cv.put("name", "Крос №3");
        db.insert("shoe", null, cv);

        // ... ВРЕМЕННО!

        dbHelper.close();

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
                fTrans.replace(R.id.container, manFragment);
                fTrans.addToBackStack(null);
                break;
            case R.id.btnWomanMain:
                MainActivity.manTRUEwomanFALSE = false;
                fTrans.replace(R.id.container, womanFragment);
                fTrans.addToBackStack(null);
                break;
        }
        fTrans.commit();
    }
}