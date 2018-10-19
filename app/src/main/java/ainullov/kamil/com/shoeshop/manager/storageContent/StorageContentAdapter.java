package ainullov.kamil.com.shoeshop.manager.storageContent;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.manager.pojo.OrderAccountingPojo;
import ainullov.kamil.com.shoeshop.user.fragments.ShoesDetailedFragment;

public class StorageContentAdapter extends RecyclerView.Adapter<StorageContentAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<OrderAccountingPojo> orderAccountingPojos;
    private Context context;

    public StorageContentAdapter(Context context, List<OrderAccountingPojo> orderAccountingPojos) {
        this.orderAccountingPojos = orderAccountingPojos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public StorageContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.storagecontent_item, parent, false);
        return new StorageContentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StorageContentAdapter.ViewHolder holder, int position) {
        OrderAccountingPojo orderAccountingPojo = orderAccountingPojos.get(position);

        SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy, HH:mm", Locale.ENGLISH);
        String dayOfWeeki = formatDayOfWeek.format(Double.valueOf(orderAccountingPojo.getDate()));

        StringBuilder strSizes = new StringBuilder("");

        try {
            JSONObject json = new JSONObject(orderAccountingPojo.getSize());
            JSONArray items = json.optJSONArray("uniqueArrays");
            for (int i = 0; i < items.length(); i++) {
                if (i == 0) {
                    strSizes.append(items.optString(i));
                } else
                    strSizes.append(", " + items.optString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        holder.tvGenderCSItem.setText("Пол: " + orderAccountingPojo.getGender());
        holder.tvTypeCSItem.setText("Вид: " + orderAccountingPojo.getType());
        holder.tvNameCSItem.setText("Название: " + orderAccountingPojo.getName());
        holder.tvCoastCSItem.setText("Цена: " + orderAccountingPojo.getCoast());
        holder.tvProviderCSItem.setText("Поставщик: " + orderAccountingPojo.getProvider());
        holder.tvDateCSItem.setText("Дата поставки: " + dayOfWeeki);
        holder.tvSizeCSItem.setText("Кол-во: " + orderAccountingPojo.getQuantity() + " Размер(ы): " + strSizes);
    }

    @Override
    public int getItemCount() {
        return orderAccountingPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ConstraintLayout clCSitem;

        final ImageButton imageBtnDel, imageBtnChange;

        final TextView tvGenderCSItem, tvTypeCSItem, tvNameCSItem,
                tvCoastCSItem, tvProviderCSItem, tvDateCSItem, tvSizeCSItem;

        ViewHolder(View view) {
            super(view);
            clCSitem = (ConstraintLayout) view.findViewById(R.id.clitem);
            tvGenderCSItem = (TextView) view.findViewById(R.id.tvGenderItem);
            tvTypeCSItem = (TextView) view.findViewById(R.id.tvTypeItem);
            tvNameCSItem = (TextView) view.findViewById(R.id.tvNameItem);
            tvCoastCSItem = (TextView) view.findViewById(R.id.tvCoastItem);
            tvProviderCSItem = (TextView) view.findViewById(R.id.tvProviderItem);
            tvDateCSItem = (TextView) view.findViewById(R.id.tvDateSAItem);
            tvSizeCSItem = (TextView) view.findViewById(R.id.tvSizeSAItem);
            imageBtnDel = (ImageButton) view.findViewById(R.id.imageBtnDel);
            imageBtnChange = (ImageButton) view.findViewById(R.id.imageBtnChange);

            //Переход к товару переход к ShoesDetailedFragment
            clCSitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShoesDetailedFragment shoesDetailedFragment = new ShoesDetailedFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("uniquekey", orderAccountingPojos.get(getAdapterPosition()).getUniquekey());
                    shoesDetailedFragment.setArguments(bundle);

                    FragmentTransaction fTrans;
                    fTrans = ((Activity) context).getFragmentManager().beginTransaction();
                    fTrans.replace(R.id.container, shoesDetailedFragment);
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
            });

            imageBtnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseHelper dbHelper;
                    int deleteItemByUniqueKey = orderAccountingPojos.get(getAdapterPosition()).getUniquekey();
                    dbHelper = new DataBaseHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("shoe", "uniquekey = " + deleteItemByUniqueKey, null);
                    dbHelper.close();

                    orderAccountingPojos.remove(getAdapterPosition());
                    notifyDataSetChanged();

                }
            });

            imageBtnChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StorageContentChangeProductFragment storageContentChangeProductFragment = new StorageContentChangeProductFragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("uniquekey", orderAccountingPojos.get(getAdapterPosition()).getUniquekey());

                    //Данные данной позиции, чтобы отобразить текущие данные
                    bundle.putString("gender", orderAccountingPojos.get(getAdapterPosition()).getGender());
                    bundle.putString("type", orderAccountingPojos.get(getAdapterPosition()).getType());
                    bundle.putInt("coast", orderAccountingPojos.get(getAdapterPosition()).getCoast());
                    bundle.putString("name", orderAccountingPojos.get(getAdapterPosition()).getName());
                    bundle.putString("provider", orderAccountingPojos.get(getAdapterPosition()).getProvider());
                    bundle.putString("description", orderAccountingPojos.get(getAdapterPosition()).getDescription());
                    bundle.putString("size", orderAccountingPojos.get(getAdapterPosition()).getSize());
                    bundle.putInt("quantity", orderAccountingPojos.get(getAdapterPosition()).getQuantity());
                    bundle.putString("imageurl", orderAccountingPojos.get(getAdapterPosition()).getImageurl());

                    storageContentChangeProductFragment.setArguments(bundle);

                    FragmentTransaction fTrans;
                    fTrans = ((Activity) context).getFragmentManager().beginTransaction();
                    fTrans.replace(R.id.container, storageContentChangeProductFragment);
                    fTrans.addToBackStack(null);
                    fTrans.commit();

                }
            });

        }
    }
}