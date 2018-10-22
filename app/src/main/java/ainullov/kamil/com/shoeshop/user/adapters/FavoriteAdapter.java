package ainullov.kamil.com.shoeshop.user.adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.user.fragments.ShoesDetailedFragment;
import ainullov.kamil.com.shoeshop.user.pojo.BasketFavoriteShoe;

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

        View view = inflater.inflate(R.layout.favorite_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, int position) {
        BasketFavoriteShoe basketFavoriteShoe = favoriteShoes.get(position);

//      Получаем уникальный ключ, полученный ранее из бд basket, по которому в бд shoe находим запись
        String uniquekey = String.valueOf(basketFavoriteShoe.getUniquekey());

        Cursor c;
        DataBaseHelper dbHelper;
        String selection = null;
        String[] selectionArgs = null;

        int nameColIndex;
        int coastColIndex;
        int discountColIndex;
        int imageurlColIndex;

        dbHelper = new DataBaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        selection = "uniquekey = ?";             // по уникальному ключу из basket
        selectionArgs = new String[]{uniquekey}; // в таблице shoe ищем товар и получаем необходимую информацию
        c = db.query("shoe", null, selection, selectionArgs, null, null, null);
        c.moveToFirst();
        if (c.moveToFirst()) {
            nameColIndex = c.getColumnIndex("name");
            coastColIndex = c.getColumnIndex("coast");
            discountColIndex = c.getColumnIndex("discount");
            imageurlColIndex = c.getColumnIndex("imageurl");

            do {
                holder.tvFavoriteName.setText(c.getString(nameColIndex));
                holder.tvFavoriteCoast.setText(String.valueOf(c.getInt(coastColIndex)));

                int discountcoast = 0;
                if (c.getInt(discountColIndex) != 0 && c.getInt(discountColIndex) != 100) {
                    discountcoast = (int) (100 - c.getInt(discountColIndex)) * c.getInt(coastColIndex) / 100;

                    holder.tvFavoriteCoast.setPaintFlags(holder.tvFavoriteCoast.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvFavoriteCoast.setTextColor(context.getResources().getColor(R.color.red));

                    holder.tvFavoriteDiscountCoast.setText(String.valueOf(discountcoast));
                } else holder.tvFavoriteDiscountCoast.setText("");
                Picasso.with(context).load(c.getString(imageurlColIndex)).into(holder.ivFavoriteShoe);
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
        final ConstraintLayout clFavorite;
        final ImageView ivFavoriteShoe;
        final TextView tvFavoriteName, tvFavoriteCoast, tvFavoriteDiscountCoast;
        final Button btnDelete;

        ViewHolder(View view) {
            super(view);
            clFavorite = (ConstraintLayout) view.findViewById(R.id.clFavorite);
            ivFavoriteShoe = (ImageView) view.findViewById(R.id.ivFavoriteShoe);
            tvFavoriteDiscountCoast = (TextView) view.findViewById(R.id.tvFavoriteDiscountCoast);
            tvFavoriteName = (TextView) view.findViewById(R.id.tvFavoriteName);
            tvFavoriteCoast = (TextView) view.findViewById(R.id.tvFavoriteCoast);
            btnDelete = (Button) view.findViewById(R.id.btnDelete);

            //Переход к товару из корзины, переход к ShoesDetailedFragment
            clFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BasketFavoriteShoe basketFavoriteShoe = favoriteShoes.get(getAdapterPosition());

                    ShoesDetailedFragment shoesDetailedFragment = new ShoesDetailedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("uniquekey", basketFavoriteShoe.getUniquekey());
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
                    db.delete(MainActivity.USERNAME_FAVORITE_DB, "shoeUniquekeyBasket = " + deleteItemByUniqueKey, null);
                    dbHelper.close();


                    favoriteShoes.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }
    }
}