package ainullov.kamil.com.shoeshop.manager.employeePerf;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ContentValues;
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
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.manager.pojo.WorkersPojo;

public class EmployeePerfFragment extends Fragment {
    List<WorkersPojo> workersPojos = new ArrayList<>();
    Cursor c;
    DataBaseHelper dbHelper;
    int nameColIndex;
    int ratingColIndex;
    int uniquekeyColIndex;

    Button btnEmpAdd;
    EmployeePerfAdapter adapter;

    Random random = new Random();
    int uniquekey = random.nextInt();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employeeperf, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");

        btnEmpAdd = (Button) view.findViewById(R.id.btnAddEmp);

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        c = db.query("workers", null, null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            ratingColIndex = c.getColumnIndex("rating");
            nameColIndex = c.getColumnIndex("name");
            uniquekeyColIndex = c.getColumnIndex("uniquekey");

            do {
                workersPojos.add(new WorkersPojo(
                        c.getString(nameColIndex),
                        c.getInt(ratingColIndex),
                        c.getInt(uniquekeyColIndex)));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();


        // Сортировка по рейтингу
        Collections.sort(workersPojos, WorkersPojo.COMPARE_BY_RATING);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvEmp);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new EmployeePerfAdapter(getActivity(), workersPojos);
        recyclerView.setAdapter(adapter);


        btnEmpAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Имя сотрудника и рейтинг");

                final EditText etNameDialog = (EditText) dialog.findViewById(R.id.etNameDialog);
                final EditText etRatingDialog = (EditText) dialog.findViewById(R.id.etRatingDialog);

                Button btnOkDialog = (Button) dialog.findViewById(R.id.btnOkDialog);

                btnOkDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strNameDialog = etNameDialog.getText().toString();
                        String strRatingDialog = etRatingDialog.getText().toString();

                        while (checkRepeat("workers") != 0) {
                            uniquekey = random.nextInt();
                        }

                        if (checkRepeat("workers") == 0) {

                            ContentValues cv = new ContentValues();
                            DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
                            SQLiteDatabase db = dbHelper.getWritableDatabase();

                            workersPojos.add(new WorkersPojo(
                                    strNameDialog,
                                    Integer.valueOf(strRatingDialog),
                                    uniquekey));

                            cv.put("name", strNameDialog);
                            cv.put("rating", strRatingDialog);
                            cv.put("uniquekey", uniquekey);
                            db.insert("workers", null, cv);
                            dbHelper.close();
                            dialog.dismiss();

                            Collections.sort(workersPojos, WorkersPojo.COMPARE_BY_RATING);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                dialog.show();
            }
        });
    }


    // Проверка, есть ли такой же уникальный ключ
    public int checkRepeat(String tableName) {
        DataBaseHelper dbHelper;
        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = null;
        try {
            db = dbHelper.getReadableDatabase();
            String query = "select count(*) from " + tableName + " where uniquekey = ?";
            c = db.rawQuery(query, new String[]{String.valueOf(uniquekey)});
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
            return 0;
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

}