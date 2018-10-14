package ainullov.kamil.com.shoeshop.login;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
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

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.fragments.MainFragment;

public class LoginFragment extends Fragment implements View.OnClickListener {

    Button btnSignIn;
    Button btnSignUp;
    EditText etLogin;
    EditText etPassword;

    String strLogin;
    String strPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnSignIn = (Button) view.findViewById(R.id.btnSignIn);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        etLogin = (EditText) view.findViewById(R.id.etLogin);
        etPassword = (EditText) view.findViewById(R.id.etPassword);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        MainFragment mainFragment = new MainFragment();
        SignUpFragment signUpFragment = new SignUpFragment();
        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();
        switch (view.getId()) {
            case R.id.btnSignIn:
                strLogin = etLogin.getText().toString();
                strPassword = etPassword.getText().toString();

                MainActivity.USERNAME_USER_DB = strLogin + "user";
                MainActivity.USERNAME_BASKET_DB = strLogin + "basket";
                MainActivity.USERNAME_FAVORITE_DB = strLogin + "favorite";

                Cursor c;
                DataBaseHelper dbHelper;
                int loginColIndex;
                int passwordColIndex;

                try {


                    dbHelper = new DataBaseHelper(getActivity());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    c = db.query(MainActivity.USERNAME_USER_DB, null, null, null, null, null, null);
                    c.moveToFirst();
                    if (c.moveToFirst()) {
                        loginColIndex = c.getColumnIndex("login");
                        passwordColIndex = c.getColumnIndex("password");
                        do {

                            if (strPassword.equals(c.getString(passwordColIndex))) {
                                Toast.makeText(getActivity(), "Здравствуйте, " + c.getString(loginColIndex), Toast.LENGTH_SHORT).show();
                                fTrans.remove(this);
                                fTrans.add(R.id.container, mainFragment);
                            } else {
                                Toast.makeText(getActivity(), "Неправильный пароль " + c.getString(loginColIndex), Toast.LENGTH_SHORT).show();
                                etPassword.setText("");
                            }
                        } while (c.moveToNext());
                    }

                    c.close();
                    dbHelper.close();


                } catch (SQLiteException e) {
                    Toast.makeText(getActivity(), " Не существует такого пользователя ", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.btnSignUp:
//                fTrans.replace(R.id.container, signUpFragment);
//                fTrans.addToBackStack(null);
                fTrans.remove(this);
                fTrans.add(R.id.container, signUpFragment);
                break;
        }
        fTrans.commit();
    }
}