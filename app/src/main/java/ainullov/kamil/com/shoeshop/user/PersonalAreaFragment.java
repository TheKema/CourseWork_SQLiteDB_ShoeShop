package ainullov.kamil.com.shoeshop.user;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.login.LoginFragment;
import ainullov.kamil.com.shoeshop.user.fragments.UserOrdersHistoryFragment;

public class PersonalAreaFragment extends Fragment implements View.OnClickListener {
    TextView tvLogin;
    TextView tvName;
    TextView tvNumber;
    TextView tvEmail;
    Button btnExit;
    Button btnChangeData;
    Button btnChangePassword;
    Button btnUserOrdersHistory;

    String strLogin;
    String strPassword;
    String strName;
    String strNumber;
    String strEmail;

    Cursor c;
    DataBaseHelper dbHelper;
    int loginColIndex;
    int passwordColIndex;
    int nameColIndex;
    int numberColIndex;
    int emailColIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personalarea, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("");
        tvLogin = (TextView) view.findViewById(R.id.tvLogin);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvNumber = (TextView) view.findViewById(R.id.tvNumber);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        btnExit = (Button) view.findViewById(R.id.btnExit);
        btnChangeData = (Button) view.findViewById(R.id.btnChangeData);
        btnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);
        btnUserOrdersHistory = (Button) view.findViewById(R.id.btnUserOrdersHistory);
        btnExit.setOnClickListener(this);
        btnUserOrdersHistory.setOnClickListener(this);
        btnChangeData.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        c = db.query(MainActivity.USERNAME_USER_DB, null, null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            loginColIndex = c.getColumnIndex("login");
            passwordColIndex = c.getColumnIndex("password");
            nameColIndex = c.getColumnIndex("name");
            numberColIndex = c.getColumnIndex("number");
            emailColIndex = c.getColumnIndex("email");
            do {
                tvLogin.setText("Логин: " + c.getString(loginColIndex));
                tvName.setText("Имя: " + c.getString(nameColIndex));
                tvNumber.setText("Номер: " + c.getString(numberColIndex));
                tvEmail.setText("Эл. почта: " + c.getString(emailColIndex));
                strLogin = c.getString(loginColIndex);
                strName = c.getString(nameColIndex);
                strNumber = c.getString(numberColIndex);
                strEmail = c.getString(emailColIndex);
                strPassword = c.getString(passwordColIndex);
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();
    }

    @Override
    public void onClick(View view) {
        LoginFragment loginFragment = new LoginFragment();
        UserOrdersHistoryFragment userOrdersHistoryFragment = new UserOrdersHistoryFragment();
        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.btnExit:
                MainActivity.USERNAME_USER_DB = "";
                MainActivity.USERNAME_BASKET_DB = "";
                MainActivity.USERNAME_FAVORITE_DB = "";
                MainActivity.USERNAME_ORDERSHISTORY_DB = "";
                fTrans.remove(this);
                fTrans.add(R.id.container, loginFragment);
                break;
            case R.id.btnUserOrdersHistory:
                fTrans.replace(R.id.container, userOrdersHistoryFragment);
                fTrans.addToBackStack(null);
                break;
            case R.id.btnChangeData:

                final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.personalarea_changedata_dialog);
                dialog.setTitle("Изменить данные");

                final EditText etNameDialog = (EditText) dialog.findViewById(R.id.etNameDialog);
                etNameDialog.setText(strName);
                final EditText etNumberDialog = (EditText) dialog.findViewById(R.id.etNumberDialog);
                etNumberDialog.setText(strNumber);
                final EditText etEmailDialog = (EditText) dialog.findViewById(R.id.etEmailDialog);
                etEmailDialog.setText(strEmail);

                Button btnOkDialog = (Button) dialog.findViewById(R.id.btnOkDialog);
                Button btnBackDialog = (Button) dialog.findViewById(R.id.btnBackDialog);

                btnOkDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strNameDialog = etNameDialog.getText().toString();
                        String strNumberDialog = etNumberDialog.getText().toString();
                        String strEmailDialog = etEmailDialog.getText().toString();

                        tvName.setText("Имя: " + strNameDialog);
                        tvNumber.setText("Номер: " + strNumberDialog);
                        tvEmail.setText("Эл. почта: " + strEmailDialog);

                        ContentValues cv = new ContentValues();
                        DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        cv.put("name", strNameDialog);
                        cv.put("number", strNumberDialog);
                        cv.put("email", strEmailDialog);
                        db.update(MainActivity.USERNAME_USER_DB, cv, "login = ?", new String[]{strLogin});
                        dbHelper.close();

                        dialog.dismiss();
                    }
                });

                btnBackDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                break;

            case R.id.btnChangePassword:

                final Dialog dialogChangePassword = new Dialog(getActivity());
                dialogChangePassword.setContentView(R.layout.personalarea_changepassword_dialog);
                dialogChangePassword.setTitle("Изменить данные");

                final EditText etOldPasswordDialog = (EditText) dialogChangePassword.findViewById(R.id.etOldPasswordDialog);
                final EditText etNewPasswordDialog = (EditText) dialogChangePassword.findViewById(R.id.etNewPasswordDialog);

                Button btnChangePasswordDialog = (Button) dialogChangePassword.findViewById(R.id.btnChangePasswordDialog);
                Button btnBackDialogChangePassword = (Button) dialogChangePassword.findViewById(R.id.btnBackDialog);


                btnChangePasswordDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String strOldPasswordDialog = "";
                        String strNewPasswordDialog = "";

                        if (!etOldPasswordDialog.getText().toString().equals(null) && !etNewPasswordDialog.getText().toString().equals(null)) {
                            strOldPasswordDialog = etOldPasswordDialog.getText().toString();
                            strNewPasswordDialog = etNewPasswordDialog.getText().toString();
                        }

                        if (strOldPasswordDialog.equals(strPassword)) {
                            ContentValues cv = new ContentValues();
                            DataBaseHelper dbHelper = new DataBaseHelper(getActivity());
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            cv.put("password", strNewPasswordDialog);
                            db.update(MainActivity.USERNAME_USER_DB, cv, "login = ?", new String[]{strLogin});
                            dbHelper.close();
                            Toast.makeText(getActivity(), "Пароль сохранен", Toast.LENGTH_SHORT).show();
                            dialogChangePassword.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "Неверный пароль", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                btnBackDialogChangePassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogChangePassword.dismiss();
                    }
                });
                dialogChangePassword.show();
                break;
        }
        fTrans.commit();
    }

}