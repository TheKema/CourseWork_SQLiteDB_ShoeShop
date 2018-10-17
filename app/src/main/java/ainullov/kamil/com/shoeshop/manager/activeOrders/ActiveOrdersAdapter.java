package ainullov.kamil.com.shoeshop.manager.activeOrders;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.db.DataBaseHelper;
import ainullov.kamil.com.shoeshop.manager.pojo.ActiveOrdersPojo;
import ainullov.kamil.com.shoeshop.user.fragments.ShoesDetailedFragment;

public class ActiveOrdersAdapter extends RecyclerView.Adapter<ActiveOrdersAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ActiveOrdersPojo> activeOrdersPojos;
    private Context context;

    public ActiveOrdersAdapter(Context context, List<ActiveOrdersPojo> activeOrdersPojos) {
        this.activeOrdersPojos = activeOrdersPojos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public ActiveOrdersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.activeorders_item, parent, false);
        return new ActiveOrdersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActiveOrdersAdapter.ViewHolder holder, int position) {
        ActiveOrdersPojo activeOrdersPojo = activeOrdersPojos.get(position);

        SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy, HH:mm", Locale.ENGLISH);
        String dayOfWeeki = formatDayOfWeek.format(Double.valueOf(activeOrdersPojo.getDate()));

        holder.tvOrderNumber.setText("Номер заказа: " + activeOrdersPojo.getOrderNumber());
        holder.tvName.setText("Имя покупателя: " + activeOrdersPojo.getName());
        holder.tvNumber.setText("Номер: " + activeOrdersPojo.getNumber());
        holder.tvEmail.setText("Эл. почта: " + activeOrdersPojo.getEmail());
        holder.tvDate.setText("Дата заказа: " + dayOfWeeki);
        holder.tvShoeName.setText("Именование обуви: " + activeOrdersPojo.getShoename());
        holder.tvType.setText("Тип: " + activeOrdersPojo.getType());
        holder.tvGender.setText("Пол: " + activeOrdersPojo.getGender());
        holder.tvCoast.setText("Цена: " + activeOrdersPojo.getCoast());
        holder.tvSize.setText("Размер(ы):" + activeOrdersPojo.getSize());
    }

    @Override
    public int getItemCount() {
        return activeOrdersPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final Button btnDelete;
        final TextView tvOrderNumber, tvName, tvNumber,
                tvEmail, tvDate, tvShoeName, tvType, tvGender, tvCoast, tvSize;

        ViewHolder(View view) {
            super(view);
            btnDelete = (Button) view.findViewById(R.id.btnDelete);
            tvOrderNumber = (TextView) view.findViewById(R.id.tvOrderNumber);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvNumber = (TextView) view.findViewById(R.id.tvNumber);
            tvEmail = (TextView) view.findViewById(R.id.tvEmail);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvShoeName = (TextView) view.findViewById(R.id.tvShoeName);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvGender = (TextView) view.findViewById(R.id.tvGender);
            tvCoast = (TextView) view.findViewById(R.id.tvCoast);
            tvSize = (TextView) view.findViewById(R.id.tvSize);


            //Переход к товару переход к ShoesDetailedFragment
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DataBaseHelper dbHelper;
                    int deleteItemByOrderNumber = activeOrdersPojos.get(getAdapterPosition()).getOrderNumber();
                    dbHelper = new DataBaseHelper(context);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    db.delete("orders", "orderNumber = " + deleteItemByOrderNumber, null);
                    dbHelper.close();
                    activeOrdersPojos.remove(getAdapterPosition());
                    notifyDataSetChanged();

                }
            });
        }
    }

}