package ainullov.kamil.com.shoeshop.user.fragments;

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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.user.adapters.BasketAdapter;
import ainullov.kamil.com.shoeshop.user.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.pojo.BasketFavoriteShoe;

// Корзина
public class BasketFragment extends Fragment implements View.OnClickListener {
    List<BasketFavoriteShoe> basketFavoriteShoes = new ArrayList<>();
    DataBaseHelper dbHelper;
    BasketAdapter adapter;

    Button btnBuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basket, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnBuy = (Button) view.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(this);
        getActivity().setTitle("Корзина");


        basketFavoriteShoes.clear(); // Очистка, для того, чтобы элементы не дублировались. Исправить!

        dbHelper = new DataBaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("basket", null, null, null, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int uniquekeyColIndex = c.getColumnIndex("shoeUniquekeyBasket");
            int shoeSizeColIndex = c.getColumnIndex("shoeSize");

            do {
                basketFavoriteShoes.add(new BasketFavoriteShoe(c.getInt(uniquekeyColIndex), c.getString(shoeSizeColIndex)));

            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.rvBasket);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BasketAdapter(getActivity(), basketFavoriteShoes);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        // На потом, открыти новогофрагмента с оформлением покупки, в данный момент при нажатии на кнопку "Купить"
        // Будут просто элементы из корзины,бд пропадать
//        ManFragment manFragment = new ManFragment();
//        WomanFragment womanFragment = new WomanFragment();
//        FragmentTransaction fTrans;
//        fTrans = getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.btnBuy:
                // Прохождение по всем товарам списка
                for (int i = 0; i < basketFavoriteShoes.size(); i++) {

                    int shoeUniquekeyBasket = basketFavoriteShoes.get(i).getUniquekey();
                    String shoeSize = basketFavoriteShoes.get(i).getSize();
                    int deleteNumber = 99999999; // Изменить, вдруг будет такое количество товаров

                    List<String> arrayListSize = new ArrayList<>();

                    Cursor c;
                    DataBaseHelper dbHelperChangeShoe;
                    String selection = null;
                    String[] selectionArgs = null;
                    int quantityColIndex;
                    int sizeColIndex;

                    // Подключаемся к БД и получаем у i позиции  количество и размеры
                    // !! Размеры у нас в json строке
                    dbHelperChangeShoe = new DataBaseHelper(getActivity());
                    SQLiteDatabase dbChangeShoe = dbHelperChangeShoe.getWritableDatabase();
                    selection = "uniquekey = ?";
                    selectionArgs = new String[]{String.valueOf(shoeUniquekeyBasket)};
                    // Чтение, делаем запрос всех данных из таблицы, получаем Cursor
                    c = dbChangeShoe.query("shoe", null, selection, selectionArgs, null, null, null);
                    c.moveToFirst();
                    if (c.moveToFirst()) {
                        quantityColIndex = c.getColumnIndex("quantity");
                        sizeColIndex = c.getColumnIndex("size");

                        do {
                            int quantityChange = c.getInt(quantityColIndex);
                            String jsonSizes = c.getString(sizeColIndex);

                            //json размеры превращаем в arraylist<String> с размерами
                            try {
                                JSONObject json = new JSONObject(c.getString(sizeColIndex));
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
                            //
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
                            Toast.makeText(getActivity(),"sizes "+ arrayList, Toast.LENGTH_SHORT).show();


                            // Обновляем записм в бд если >0, если =0, то удаляем
                            if(arrayListSize.size()>0) {
//                                db.insert("shoe", null, cv);
                                dbChangeShoe.update("shoe", cv, "uniquekey = ?", new String[] { String.valueOf(shoeUniquekeyBasket) });
                            }
                            if(arrayListSize.size()==0){
                                dbChangeShoe.delete("shoe", "uniquekey = " + shoeUniquekeyBasket, null);
                            }

                        } while (c.moveToNext());
                    }
                    c.close();
                    dbHelperChangeShoe.close();

                }

                // Удаляем элементы в корзине
                DataBaseHelper dbHelper;
                dbHelper = new DataBaseHelper(getActivity());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("basket", null, null);
                dbHelper.close();


                basketFavoriteShoes.clear();
                adapter.notifyDataSetChanged();
                break;
        }
//        fTrans.commit();
    }
}