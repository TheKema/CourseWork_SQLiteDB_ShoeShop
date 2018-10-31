package ainullov.kamil.com.shoeshop.user.userOrderProduct;

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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.fragments.MainFragment;
import ainullov.kamil.com.shoeshop.user.pojo.BasketFavoriteShoe;

public class UserOrderProductFragment extends Fragment implements View.OnClickListener {
    EditText etName;
    EditText etNumber;
    EditText etEmail;
    RadioButton rbOnlinePay;
    RadioButton rbPickup;
    Button btnUserOrderProduct;

    String nameOrders = "Error";
    String numberOrders = "Error";
    String emailOrders = "Error";
    String dateOrders = String.valueOf(System.currentTimeMillis());
    Random randomOrders = new Random();
    int orderNumber = randomOrders.nextInt(Integer.MAX_VALUE);


    DataBaseHelper dbHelper;
    // Переменные для вставки в бд
    String gender = "М";
    String type = "Кроссовки";
    int coast = 1990;
    int discount = 0;
    String name = "Обувь 1";
    String provider = "КазОдеждСтрой";
    String solddate = String.valueOf(System.currentTimeMillis());

    Random random = new Random();
    int uniquekey = random.nextInt();

    FragmentTransaction fTrans;
    MainFragment mainFragment = new MainFragment();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_userorderproduct, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etName = (EditText) view.findViewById(R.id.etName);
        etNumber = (EditText) view.findViewById(R.id.etNumber);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        rbOnlinePay = (RadioButton) view.findViewById(R.id.rbOnlinePay);
        rbPickup = (RadioButton) view.findViewById(R.id.rbPickup);
        btnUserOrderProduct = (Button) view.findViewById(R.id.btnUserOrderProduct);
        btnUserOrderProduct.setOnClickListener(this);
        getActivity().setTitle("");

        rbOnlinePay.setClickable(false);

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(MainActivity.USERNAME_USER_DB, null, null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            int numberColIndex = c.getColumnIndex("number");
            int emailColIndex = c.getColumnIndex("email");

            do {
                etName.setText(c.getString(nameColIndex));
                etNumber.setText(c.getString(numberColIndex));
                etEmail.setText(c.getString(emailColIndex));
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnUserOrderProduct:
                if (rbPickup.isChecked()) {
                    //Список значений для orders в columns gender,type,coast,name
                    String genderListOrders = "";
                    String typeListOrders = "";
                    int coastListOrders = 0;
                    String nameListOrders = "";
                    String sizeListOrders = "";

                    // BasketFavoriteShoe List для добавления в sold (1) и удаления из shoe (2)
                    List<BasketFavoriteShoe> basketFavoriteShoes = new ArrayList<>();
                    DataBaseHelper dbHelper;
                    int typeColIndex;
                    int genderColIndex;
                    int nameColIndex;
                    int coastColIndex;
                    int discountColIndex;
                    int providerColIndex;

                    basketFavoriteShoes.clear(); // Очистка, для того, чтобы элементы не дублировались. // Раньше было в другом месте

                    dbHelper = new DataBaseHelper(getActivity());
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    Cursor c = db.query(MainActivity.USERNAME_BASKET_DB, null, null, null, null, null, null);
                    c.moveToFirst();
                    if (c.moveToFirst()) {
                        int idColIndex = c.getColumnIndex("id");
                        int uniquekeyColIndex = c.getColumnIndex("shoeUniquekeyBasket");
                        int shoeSizeColIndex = c.getColumnIndex("shoeSize");
                        int imageurlColIndex = c.getColumnIndex("imageurl");

                        do {
                            basketFavoriteShoes.add(new BasketFavoriteShoe(c.getInt(uniquekeyColIndex), c.getString(imageurlColIndex), c.getString(shoeSizeColIndex)));
//                            size = c.getString(shoeSizeColIndex); //Размер для sold бд
                        } while (c.moveToNext());
                    }
                    c.close();
                    dbHelper.close();


                    // Отдельно реализовал добавление в sold (1) и удаление из shoe (2)

                    // (1)
                    // Прохождение по всем товарам списка
                    for (int i = 0; i < basketFavoriteShoes.size(); i++) {

                        int shoeUniquekeyBasket = basketFavoriteShoes.get(i).getUniquekey();
                        Cursor cSoldAdd;
                        DataBaseHelper dbHelperChangeShoe;
                        String selection = null;
                        String[] selectionArgs = null;

                        // Подключаемся к БД и получаем у i позиции  количество и размеры
                        // !! Размеры у нас в json строке
                        dbHelperChangeShoe = new DataBaseHelper(getActivity());
                        SQLiteDatabase dbChangeShoe = dbHelperChangeShoe.getWritableDatabase();
                        selection = "uniquekey = ?";
                        selectionArgs = new String[]{String.valueOf(shoeUniquekeyBasket)};
                        // Чтение, делаем запрос всех данных из таблицы, получаем Cursor
                        cSoldAdd = dbChangeShoe.query("shoe", null, selection, selectionArgs, null, null, null);
                        cSoldAdd.moveToFirst();
                        if (cSoldAdd.moveToFirst()) {
                            typeColIndex = cSoldAdd.getColumnIndex("type");
                            genderColIndex = cSoldAdd.getColumnIndex("gender");
                            nameColIndex = cSoldAdd.getColumnIndex("name");
                            coastColIndex = cSoldAdd.getColumnIndex("coast");


                            discountColIndex = cSoldAdd.getColumnIndex("discount");


                            providerColIndex = cSoldAdd.getColumnIndex("provider");

                            do {

                                int discountcoast = 0;
                                if (cSoldAdd.getInt(discountColIndex) != 0 && cSoldAdd.getInt(discountColIndex) != 100)
                                    discountcoast = (int) (100 - cSoldAdd.getInt(discountColIndex)) * cSoldAdd.getInt(coastColIndex) / 100;


                                gender = cSoldAdd.getString(genderColIndex);
                                type = cSoldAdd.getString(typeColIndex);
                                name = cSoldAdd.getString(nameColIndex);
//                                coast = cSoldAdd.getInt(coastColIndex);
                                // ТУТ ИЗМЕНЕНА ЦЕНА, ДАЛЬШЕ БУДЕТ СО СКИДКОЙ!
                                if (discountcoast != 0)
                                    coast = discountcoast;
                                else
                                    coast = cSoldAdd.getInt(coastColIndex);

                                discount = cSoldAdd.getInt(discountColIndex);

                                provider = cSoldAdd.getString(providerColIndex);

                            } while (cSoldAdd.moveToNext());
                        }
                        cSoldAdd.close();
                        dbHelperChangeShoe.close();

                        solddate = String.valueOf(System.currentTimeMillis());
                        DataBaseHelper dbHelperSold;
                        dbHelperSold = new DataBaseHelper(getActivity());
                        SQLiteDatabase dbSold = dbHelperSold.getWritableDatabase();

                        ContentValues cvSold = new ContentValues();
                        cvSold.put("type", type);
                        cvSold.put("gender", gender);
                        cvSold.put("discount", discount);

                        cvSold.put("coast", coast);


                        cvSold.put("name", name);
                        cvSold.put("provider", provider);
                        cvSold.put("solddate", solddate);
                        cvSold.put("size", basketFavoriteShoes.get(i).getSize());

                        //Добавление значений в лист для orders
                        if (i == basketFavoriteShoes.size() - 1) {
                            genderListOrders += gender;
                            typeListOrders += type;
                            coastListOrders += coast;
                            nameListOrders += name;
                            sizeListOrders += basketFavoriteShoes.get(i).getSize();
                        } else {
                            genderListOrders += gender + ", ";
                            typeListOrders += type + ", ";
                            coastListOrders += coast;
                            nameListOrders += name + ", ";
                            sizeListOrders += basketFavoriteShoes.get(i).getSize() + ", ";

                        }

                        while (checkRepeat("sold", "uniquekey") != 0) {
                            uniquekey = random.nextInt();
                        }
                        if (checkRepeat("sold", "uniquekey") == 0) {
                            cvSold.put("uniquekey", uniquekey);
                        }
                        dbSold.insert("sold", null, cvSold);
                        dbHelperSold.close();
                    }


                    // Вставка в БД Orders
                    nameOrders = etName.getText().toString();
                    numberOrders = etNumber.getText().toString();
                    emailOrders = etEmail.getText().toString();
                    dateOrders = String.valueOf(System.currentTimeMillis());

                    //Добавление в БД orders, для отслеживания заказов клиентов сотрудником
                    DataBaseHelper dbHelperOrders;
                    dbHelperOrders = new DataBaseHelper(getActivity());

                    DataBaseHelper dbHelperUserOrders;
                    dbHelperUserOrders = new DataBaseHelper(getActivity());
                    // подключение к БД
                    SQLiteDatabase dbOrders = dbHelperOrders.getWritableDatabase();
                    SQLiteDatabase dbUserOrders = dbHelperUserOrders.getWritableDatabase();
                    ContentValues cvUserOrders = new ContentValues();

                    ContentValues cvOrders = new ContentValues();
                    cvOrders.put("name", nameOrders);
                    cvOrders.put("number", numberOrders);
                    cvOrders.put("email", emailOrders);
                    cvOrders.put("date", dateOrders);
                    cvOrders.put("type", typeListOrders);
                    cvOrders.put("gender", genderListOrders);
                    cvOrders.put("coast", coastListOrders);
                    cvOrders.put("shoename", nameListOrders);
                    cvOrders.put("size", sizeListOrders);

                    cvUserOrders.put("date", dateOrders);
                    cvUserOrders.put("type", typeListOrders);
                    cvUserOrders.put("gender", genderListOrders);
                    cvUserOrders.put("coast", coastListOrders);
                    cvUserOrders.put("shoename", nameListOrders);
                    cvUserOrders.put("size", sizeListOrders);

                    while (checkRepeat("orders", "orderNumber") != 0) {
                        orderNumber = randomOrders.nextInt(Integer.MAX_VALUE);
                    }
                    if (checkRepeat("orders", "orderNumber") == 0) {
                        cvOrders.put("orderNumber", orderNumber);
                        cvUserOrders.put("orderNumber", orderNumber);
                    }

                    dbOrders.insert("orders", null, cvOrders);
                    //Одновременно в бд с заказами для клиента и для админа/сотрудника
                    dbUserOrders.insert(MainActivity.USERNAME_ORDERSHISTORY_DB, null, cvUserOrders);
                    dbHelperOrders.close();
                    dbHelperUserOrders.close();
                    //

                    // (2)
                    // Прохождение по всем товарам списка
                    for (int i = 0; i < basketFavoriteShoes.size(); i++) {
                        int shoeUniquekeyBasket = basketFavoriteShoes.get(i).getUniquekey();
                        String shoeSize = basketFavoriteShoes.get(i).getSize();
                        int deleteNumber = 99999999; // Изменить, вдруг будет такое количество товаров
                        List<String> arrayListSize = new ArrayList<>();

                        Cursor cBasketDelete;
                        DataBaseHelper dbHelperChangeShoe;
                        String selection = null;
                        String[] selectionArgs = null;
                        int sizeColIndex;

                        // Подключаемся к БД и получаем у i позиции  количество и размеры
                        // !! Размеры у нас в json строке
                        dbHelperChangeShoe = new DataBaseHelper(getActivity());
                        SQLiteDatabase dbChangeShoe = dbHelperChangeShoe.getWritableDatabase();
                        selection = "uniquekey = ?";
                        selectionArgs = new String[]{String.valueOf(shoeUniquekeyBasket)};
                        // Чтение, делаем запрос всех данных из таблицы, получаем Cursor
                        cBasketDelete = dbChangeShoe.query("shoe", null, selection, selectionArgs, null, null, null);
                        cBasketDelete.moveToFirst();
                        if (cBasketDelete.moveToFirst()) {
                            sizeColIndex = cBasketDelete.getColumnIndex("size");

                            do {
                                //json размеры превращаем в arraylist<String> с размерами
                                try {
                                    JSONObject json = new JSONObject(cBasketDelete.getString(sizeColIndex));
                                    JSONArray items = json.optJSONArray("uniqueArrays");
                                    for (int k = 0; k < items.length(); k++) {
                                        arrayListSize.add(items.optString(k));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                // Проходимся по каждому элементу arrayListSize и получаем индекс размера обуви из бд , который совпадает с размером в корзине
                                for (int j = 0; j < arrayListSize.size(); j++) {
                                    if (arrayListSize.get(j).equals(shoeSize)) {
                                        deleteNumber = j;
                                    }
                                }

                                // Удаляем размер, который был только что куплен
                                // Сделал не в цикле удаление( засунув в скобки j), т.к.
                                // вроде только при помощи итератора можно итерироваться и удалять одновременно // Проверить!
                                arrayListSize.remove(deleteNumber);

                                // Оставшиеся размеры преобразуем в json массив и суем в БД, количество товара рассчитывается из
                                // количества элементов в массиве
                                ContentValues cv = new ContentValues();
                                JSONObject json = new JSONObject();
                                try {
                                    json.put("uniqueArrays", new JSONArray(arrayListSize));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                String arrayList = json.toString();
                                cv.put("quantity", arrayListSize.size());
                                cv.put("size", arrayList);
                                // Обновляем записм в бд если >0, если =0, то удаляем
                                if (arrayListSize.size() > 0) {
                                    dbChangeShoe.update("shoe", cv, "uniquekey = ?", new String[]{String.valueOf(shoeUniquekeyBasket)});
                                }
                                if (arrayListSize.size() == 0) {
                                    dbChangeShoe.delete("shoe", "uniquekey = " + shoeUniquekeyBasket, null);
                                }

                            } while (cBasketDelete.moveToNext());
                        }
                        cBasketDelete.close();
                        dbHelperChangeShoe.close();

                    }

                    // Удаляем элементы в корзине
                    DataBaseHelper dbHelperBasketDelete;
                    dbHelperBasketDelete = new DataBaseHelper(getActivity());
                    SQLiteDatabase dbBasketDelete = dbHelperBasketDelete.getWritableDatabase();
                    dbBasketDelete.delete(MainActivity.USERNAME_BASKET_DB, null, null);
                    dbHelperBasketDelete.close();


                    basketFavoriteShoes.clear();

                    final Dialog dialog = new Dialog(getActivity());
                    dialog.setContentView(R.layout.userorderproduct_dialog);
                    final TextView tvOrderNumberDialog = (TextView) dialog.findViewById(R.id.tvOrderNumberDialog);
                    tvOrderNumberDialog.setText("Ваш номер заказа: " + String.valueOf(orderNumber));

                    //Диалог с уведомлением о завершении покупки
                    Button btnOkDialog = (Button) dialog.findViewById(R.id.btnOkDialog);
                    btnOkDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();

                            fTrans = getFragmentManager().beginTransaction();
                            fTrans.replace(R.id.container, mainFragment);
//                            fTrans.addToBackStack(null); // не добавляю, чтобы нельзя было возвратиться
                            fTrans.commit();
                        }
                    });

                    // Требование преподавателя
                    Button btnExit = (Button) dialog.findViewById(R.id.btnExit);
                    btnExit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().finish();
                        }
                    });

                    dialog.show();
                } else
                    Toast.makeText(getActivity(), "Выберите способ получения товара", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    //     Проверка, есть ли такой же уникальный ключ для db sold
    public int checkRepeat(String tableName, String column) {
        DataBaseHelper dbHelper;
        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = null;
        try {
            db = dbHelper.getReadableDatabase();
            String query = "select count(*) from " + tableName + " where " + column + " = ?";
            c = db.rawQuery(query, new String[]{String.valueOf(orderNumber)});
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