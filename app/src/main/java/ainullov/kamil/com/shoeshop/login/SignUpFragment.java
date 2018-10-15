package ainullov.kamil.com.shoeshop.login;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;


public class SignUpFragment extends Fragment implements View.OnClickListener {
    Button btnBack;
    Button btnSignUp;
    EditText etLogin;
    EditText etPassword;
    EditText etRepPassword;
    EditText etName;
    EditText etNumber;
    EditText etEmail;

    String strLogin;
    String strPassword;
    String strRepPassword;
    String strName;
    String strNumber;
    String strEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack = (Button) view.findViewById(R.id.btnBack);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        etLogin = (EditText) view.findViewById(R.id.etLogin);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        etRepPassword = (EditText) view.findViewById(R.id.etRepPassword);
        etName = (EditText) view.findViewById(R.id.etNameDialog);
        etNumber = (EditText) view.findViewById(R.id.etNumber);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        btnBack.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        LoginFragment loginFragment = new LoginFragment();
        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.btnSignUp:
                strLogin = etLogin.getText().toString();
                strPassword = etPassword.getText().toString();
                strRepPassword = etRepPassword.getText().toString();
                strName = etName.getText().toString();
                strNumber = etNumber.getText().toString();
                strEmail = etEmail.getText().toString();

                if (strPassword.equals(strRepPassword)) {
                    try {
                        DataBaseHelper dbHelper;
                        dbHelper = new DataBaseHelper(getActivity());
                        dbHelper.createClientDB(dbHelper, strLogin);
                        // подключение к БД
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        ContentValues cv = new ContentValues();
                        cv.put("login", strLogin);
                        cv.put("password", strPassword);
                        cv.put("name", strName);
                        cv.put("number", strNumber);
                        cv.put("email", strEmail);
                        db.insert(strLogin + "user", null, cv);
                        Toast.makeText(getActivity(), "Вы зарегестрировались", Toast.LENGTH_SHORT).show();

                        dbHelper.close();

                        fTrans.remove(this);
                        fTrans.add(R.id.container, loginFragment);
                    } catch (SQLiteException e) {
                        Toast.makeText(getActivity(), "Такой логин уже существует", Toast.LENGTH_SHORT).show();
                        etLogin.setText("");
                        etPassword.setText("");
                        etRepPassword.setText("");

                    }
                } else {
                    Toast.makeText(getActivity(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etRepPassword.setText("");
                }
                break;
            case R.id.btnBack:
                fTrans.remove(this);
                fTrans.add(R.id.container, loginFragment);
                break;
        }
        fTrans.commit();
    }

}