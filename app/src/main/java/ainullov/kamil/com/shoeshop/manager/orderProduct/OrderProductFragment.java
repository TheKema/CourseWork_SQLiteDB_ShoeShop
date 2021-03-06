package ainullov.kamil.com.shoeshop.manager.orderProduct;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Random;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;

public class OrderProductFragment extends Fragment implements View.OnClickListener {
    Spinner spinnerGender;
    Spinner spinnerType;
    EditText etName;
    EditText etCoast;
    EditText etProvider;
    EditText etDesc;
    EditText etImageUrl;
    EditText etDiscount;
    Button btnSize;
    Button btnAddOrder;
    Button btnClearFields;

    private static int genderPosition = 0;
    private static int typePosition = 0;

    String[] strTypeMan = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли"};
    String[] strTypeWoman = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли", "Сапоги", "Балетки"};
    String[] strType = new String[]{};
    ArrayAdapter<String> adapterType;

    // Переменные для вставки в бд
    String gender = "М";
    String type = "Кроссовки";
    int coast = 1990;
    int discount = 0; // В процентах
    String name = "Обувь 1";
    String provider = "КазОдеждСтрой";
    String description = "Обувь произведена в США";
    String date = String.valueOf(System.currentTimeMillis());  // При добавлении товара считывается текущее время
    String imageurl = "https://image.ibb.co/dqwH5f/2184312312312495-1.jpg"; // использую сайт https://imgbb.com/ для хранения фотографий обуви
    Random random = new Random();
    int uniquekey = random.nextInt(); // При доб. тов. добавить уникальный ключ

    public static String size; // Нужно, чтобы из фрагмента можно было изменить.
    public static int quantity;

    // ArrayList, из чисел - размер обуви, превращаем в строку и суем в db, потом достанем и превратив в ArrayList
    ArrayList<String> sizeArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orderproduct, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");

        spinnerGender = (Spinner) view.findViewById(R.id.spinnerGender);
        spinnerType = (Spinner) view.findViewById(R.id.spinnerType);
        etImageUrl = (EditText) view.findViewById(R.id.etImageUrl);
        etName = (EditText) view.findViewById(R.id.etNameDialog);
        etCoast = (EditText) view.findViewById(R.id.etCoast);
        etProvider = (EditText) view.findViewById(R.id.etProvider);
        etDesc = (EditText) view.findViewById(R.id.etDesc);
        etDiscount = (EditText) view.findViewById(R.id.etDiscount);
        btnSize = (Button) view.findViewById(R.id.btnSize);
        btnAddOrder = (Button) view.findViewById(R.id.btnAddOrder);
        btnClearFields = (Button) view.findViewById(R.id.btnClearFields);
        btnSize.setOnClickListener(this);
        btnAddOrder.setOnClickListener(this);
        btnClearFields.setOnClickListener(this);


        String[] strGender = new String[]{"М", "Ж"};
        final ArrayAdapter<String> adapterGender = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strGender);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGender);
        spinnerGender.setPrompt("Title");
        spinnerGender.setSelection(genderPosition);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                gender = adapterGender.getItem(position);
                if (gender.equals("М"))
                    strType = strTypeMan;
                else
                    strType = strTypeWoman;

                genderPosition = position;
                adapterType = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, strType);
                adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(adapterType);
                // заголовок
                spinnerType.setPrompt("Title");
                spinnerType.setSelection(typePosition);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                type = adapterType.getItem(position);
                typePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnSize:
                ChoseSizesOrderProductFragment choseSizesOrderProductFragment = new ChoseSizesOrderProductFragment();
                Bundle bundleAddOrChange = new Bundle();
                bundleAddOrChange.putString("addorchange", "add");
                choseSizesOrderProductFragment.setArguments(bundleAddOrChange);
                FragmentTransaction fTrans;
                fTrans = getFragmentManager().beginTransaction();
                fTrans.replace(R.id.container, choseSizesOrderProductFragment);
                fTrans.addToBackStack(null);
                fTrans.commit();

                break;
            case R.id.btnAddOrder:
                    name = etName.getText().toString();
                    coast = Integer.valueOf(etCoast.getText().toString());

                    if (etDiscount.getText().length() != 0)
                        discount = Integer.valueOf(etDiscount.getText().toString());
                    else discount = 0;

                    description = etDesc.getText().toString();
                    provider = etProvider.getText().toString();
                    date = String.valueOf(System.currentTimeMillis());
                    imageurl = etImageUrl.getText().toString();

                    DataBaseHelper dbHelper;
                    dbHelper = new DataBaseHelper(getActivity());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ContentValues cv = new ContentValues();
                    cv.put("type", type);
                    cv.put("gender", gender);
                    cv.put("coast", coast);
                    cv.put("discount", discount);
                    cv.put("name", name);
                    cv.put("description", description);
                    cv.put("imageurl", imageurl);
                    cv.put("provider", provider);
                    cv.put("date", date);
                    cv.put("size", size);
                    cv.put("quantity", quantity);

                    while (checkRepeat("shoe") != 0) {
                        uniquekey = random.nextInt();
                    }

                    if (checkRepeat("shoe") == 0) {
                        cv.put("uniquekey", uniquekey);
                    }

                    db.insert("shoe", null, cv);
                    db.insert("deliveries", null, cv);
                    dbHelper.close();

                break;
            case R.id.btnClearFields:
                size = "";
                etName.setText("");
                etCoast.setText("");
                etDesc.setText("");
                etProvider.setText("");
                break;
        }
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