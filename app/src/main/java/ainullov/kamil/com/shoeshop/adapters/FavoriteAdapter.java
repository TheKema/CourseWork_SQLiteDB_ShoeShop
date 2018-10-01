package ainullov.kamil.com.shoeshop.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.fragments.ShoesDetailedFragment;
import ainullov.kamil.com.shoeshop.pojo.BasketFavoriteShoe;

//Понравившееся Adapter
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<BasketFavoriteShoe> favoriteShoes;

    private Context context;

    public FavoriteAdapter(Context context, List<BasketFavoriteShoe> favoriteShoes) {
        this.favoriteShoes = favoriteShoes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.basket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, int position) {
        BasketFavoriteShoe basketShoe = favoriteShoes.get(position);
//        basketShoe.getUniquekey();
//        basketShoe.getSize();

//      Получаем уникальный ключ, полученный ранее из бд basket, по которому в бд shoe находим запись
        String uniquekey = String.valueOf(basketShoe.getUniquekey());

        Cursor c;
        DataBaseHelper dbHelper;
        String selection = null;
        String[] selectionArgs = null;
        int idColIndex;   // Если в нужно будет добавить дополнительную информацию в item
        int uniquekeyColIndex;
        int typeColIndex;
        int genderColIndex;
        int quantityColIndex;
        int nameColIndex;
        int coastColIndex;
        int desciptionColIndex;
        int sizeColIndex;

        dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        selection = "uniquekey = ?";             // по уникальному ключу из basket
        selectionArgs = new String[]{uniquekey}; // в таблице shoe ищем товар и получаем необходимую информацию
        c = db.query("shoe", null, selection, selectionArgs, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            idColIndex = c.getColumnIndex("id");
            uniquekeyColIndex = c.getColumnIndex("uniquekey");
            typeColIndex = c.getColumnIndex("type");
            genderColIndex = c.getColumnIndex("gender");
            quantityColIndex = c.getColumnIndex("quantity");
            nameColIndex = c.getColumnIndex("name");
            coastColIndex = c.getColumnIndex("coast");
            desciptionColIndex = c.getColumnIndex("desciption");
            sizeColIndex = c.getColumnIndex("size");

            do {
                holder.tvBasketName.setText(c.getString(nameColIndex));
                holder.tvBasketCoast.setText(String.valueOf(c.getInt(coastColIndex)));
                holder.tvBasketSize.setText(basketShoe.getSize());
            } while (c.moveToNext());
        }
        c.close();
        dbHelper.close();
    }

    @Override
    public int getItemCount() {
        return favoriteShoes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout clBasket;
        final ImageView ivBasketShoe;
        final TextView tvBasketName, tvBasketCoast, tvBasketSize;
        final Button btnDelete;

        ViewHolder(View view) {
            super(view);
            clBasket = (ConstraintLayout) view.findViewById(R.id.clBasket);
            ivBasketShoe = (ImageView) view.findViewById(R.id.ivBasketShoe);
            tvBasketName = (TextView) view.findViewById(R.id.tvBasketName);
            tvBasketCoast = (TextView) view.findViewById(R.id.tvBasketCoast);
            tvBasketSize = (TextView) view.findViewById(R.id.tvBasketSize);
            btnDelete = (Button) view.findViewById(R.id.btnDelete);

            //Переход к товару из корзины, переход к ShoesDetailedFragment
            clBasket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cursor c;
                    DataBaseHelper dbHelper;
                    String selection = null;
                    String[] selectionArgs = null;
                    int idColIndex;

                    int positionIndexInShoe = 99999; // По уникальному ключу узнаем id товара в shoe и переходим к нему
                    BasketFavoriteShoe basketShoe = favoriteShoes.get(getAdapterPosition());
                    String uniquekey = String.valueOf(basketShoe.getUniquekey());

                    dbHelper = new DataBaseHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    selection = "uniquekey = ?";// по уникальному ключу из basket
                    selectionArgs = new String[]{uniquekey}; // в таблице shoe ищем и получаем id товара для дальнейшего перехода
                    // в фрагмент ShoesDetailedFragment, туда передаем id и в нем отображается информация
                    c = db.query("shoe", null, selection, selectionArgs, null, null, null);
                    c.moveToFirst();
                    if (c.moveToFirst()) {
                        idColIndex = c.getColumnIndex("id");
                        do {
                            positionIndexInShoe = c.getInt(idColIndex);
                        } while (c.moveToNext());
                    }
                    c.close();
                    dbHelper.close();

                    ShoesDetailedFragment shoesDetailedFragment = new ShoesDetailedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", positionIndexInShoe);
                    shoesDetailedFragment.setArguments(bundle);

                    FragmentTransaction fTrans;
                    fTrans = ((Activity) context).getFragmentManager().beginTransaction();
                    fTrans.replace(R.id.container, shoesDetailedFragment);
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
            });

            // Удаление элемента в списке
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseHelper dbHelper;

                    BasketFavoriteShoe favoriteShoe = favoriteShoes.get(getAdapterPosition());
                    int deleteItemByUniqueKey = favoriteShoe.getUniquekey();
                    dbHelper = new DataBaseHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("favorite", "shoeUniquekeyBasket = " + deleteItemByUniqueKey, null);
                    dbHelper.close();


                    favoriteShoes.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}