package ainullov.kamil.com.shoeshop;

import android.app.Fragment;
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

public class OrderProductFragment extends Fragment implements View.OnClickListener {
    Spinner spinnerGender;
    Spinner spinnerType;
    EditText etName;
    EditText etCoast;
    EditText etProvider;
    EditText etDesc;
    Button btnSize;
    Button btnAdd;
    Button btnClearFields;

    String[] strTypeMan = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли"};
    String[] strTypeWoman = new String[]{"Ботинки", "Кеды", "Кроссовки", "Туфли", "Сапоги", "Балетки"};
    String[] strType = new String[]{};
    ArrayAdapter<String> adapterType;

    // Переменные для вставки в бд
    String gender = "М";
    String type = "Кроссовки";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orderproduct, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerGender = (Spinner) view.findViewById(R.id.spinnerGender);
        spinnerType = (Spinner) view.findViewById(R.id.spinnerType);
        etName = (EditText) view.findViewById(R.id.etName);
        etCoast = (EditText) view.findViewById(R.id.etCoast);
        etProvider = (EditText) view.findViewById(R.id.etProvider);
        etDesc = (EditText) view.findViewById(R.id.etDesc);
        btnSize = (Button) view.findViewById(R.id.btnSize);
        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnClearFields = (Button) view.findViewById(R.id.btnClearFields);
        btnSize.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnClearFields.setOnClickListener(this);

        spinnerType.setVisibility(View.INVISIBLE);


        String[] strGender = new String[]{"М", "Ж"};
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strGender);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGender);
        // заголовок
        spinnerGender.setPrompt("Title");
        // выделяем элемент
        spinnerGender.setSelection(0);
        // устанавливаем обработчик нажатия
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position == 0) {
                    gender = "M";
                    strType = strTypeMan;
                } else if (position == 1) {
                    gender = "Ж";
                    strType = strTypeWoman;

                }
                spinnerType.setVisibility(View.VISIBLE);

                adapterType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strType);
                adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(adapterType);
                // заголовок
                spinnerType.setPrompt("Title");
                // выделяем элемент
                spinnerType.setSelection(0);
                // устанавливаем обработчик нажатия
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
                Toast.makeText(getActivity(),"type is "+ type, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


    }

    @Override
    public void onClick(View view) {

    }
}