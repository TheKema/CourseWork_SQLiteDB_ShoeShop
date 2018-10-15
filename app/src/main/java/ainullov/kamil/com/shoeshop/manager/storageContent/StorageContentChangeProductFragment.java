package ainullov.kamil.com.shoeshop.manager.storageContent;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
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
import android.widget.Toast;

import java.util.ArrayList;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.manager.orderProduct.ChoseSizesOrderProductFragment;
import ainullov.kamil.com.shoeshop.manager.orderProduct.OrderProductFragment;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;

public class StorageContentChangeProductFragment extends Fragment implements View.OnClickListener {
    Spinner spinnerGender;
    Spinner spinnerType;
    EditText etName;
    EditText etCoast;
    EditText etProvider;
    EditText etDesc;
    Button btnSize;
    Button btnAddOrder;
    Button btnClearFields;

    private static int genderPosition = 0;
    private static int typePosition = 0;

    String[] strTypeMan = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли"};
    String[] strTypeWoman = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли", "Сапоги", "Балетки"};
    String[] strType = new String[]{};
    ArrayAdapter<String> adapterType;

    String[] strGender = new String[]{"М", "Ж"};


    // Переменные для вставки в бд
    String gender = "М";
    String type = "Кроссовки";
    int coast = 1990;
    String name = "Обувь 1";
    String provider = "КазОдеждСтрой";
    String description = "Обувь произведена в США";
    String date = String.valueOf(System.currentTimeMillis());  // При добавлении товара считывается текущее время


    // ArrayList, из чисел - размер обуви, превращаем в строку и суем в db, потом достанем и превратив в ArrayList
    ArrayList<String> sizeArrayList = new ArrayList<>();

    public static String size; // Нужно, чтобы из фрагмента можно было изменить.
    public static int quantity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orderproduct, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getActivity(), "OrderProductFragment " + OrderProductFragment.size, Toast.LENGTH_SHORT).show();

        spinnerGender = (Spinner) view.findViewById(R.id.spinnerGender);
        spinnerType = (Spinner) view.findViewById(R.id.spinnerType);
        etName = (EditText) view.findViewById(R.id.etNameDialog);
        etCoast = (EditText) view.findViewById(R.id.etCoast);
        etProvider = (EditText) view.findViewById(R.id.etProvider);
        etDesc = (EditText) view.findViewById(R.id.etDesc);
        btnSize = (Button) view.findViewById(R.id.btnSize);
        btnAddOrder = (Button) view.findViewById(R.id.btnAddOrder);
        btnAddOrder.setText("Изменить");
        btnClearFields = (Button) view.findViewById(R.id.btnClearFields);
        btnSize.setOnClickListener(this);
        btnAddOrder.setOnClickListener(this);
        btnClearFields.setOnClickListener(this);

//Установка уже имеющихся данных
        Bundle bundle = this.getArguments();
        gender = bundle.getString("gender");
        type = bundle.getString("type");
        coast = bundle.getInt("coast");
        name = bundle.getString("name");
        provider = bundle.getString("provider");
        size = bundle.getString("size");
        description = bundle.getString("description");
        quantity = bundle.getInt("quantity");

        etName.setText(name);
        etCoast.setText(String.valueOf(coast));
        etProvider.setText(provider);
        etDesc.setText(description);
// Установка сохраненых значений в spinner'ы
        if (gender.equals("М")) {
            genderPosition = 0;
            for (int i = 0; i < strTypeMan.length; i++) {
                if (type.equals(strTypeMan[i]))
                    typePosition = i;
                strType = strTypeMan;
            }
        } else if (gender.equals("Ж")) {
            genderPosition = 1;
            for (int i = 0; i < strTypeWoman.length; i++) {
                if (type.equals(strTypeWoman[i]))
                    typePosition = i;
                strType = strTypeWoman;
            }
        }
//
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
                Toast.makeText(getActivity(), "type " + type + " gender " + gender, Toast.LENGTH_SHORT).show();
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
                bundleAddOrChange.putString("addorchange", "change");
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
                description = etDesc.getText().toString();
                provider = etProvider.getText().toString();
                date = String.valueOf(System.currentTimeMillis());

                DataBaseHelper dbHelper;
                dbHelper = new DataBaseHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put("type", type);
                cv.put("gender", gender);
                cv.put("coast", coast);
                cv.put("name", name);
                cv.put("description", description);
                cv.put("provider", provider);
                cv.put("date", date);
                cv.put("size", size);
                cv.put("quantity", quantity);

                Bundle bundleUnique = this.getArguments();
                int uniquekey = bundleUnique.getInt("uniquekey");


                String whereClause = null;
                String[] whereArgs = null;
                whereClause = "uniquekey = ?";
                whereArgs = new String[]{String.valueOf(uniquekey)};


                db.update("shoe", cv, whereClause, whereArgs);
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

}
