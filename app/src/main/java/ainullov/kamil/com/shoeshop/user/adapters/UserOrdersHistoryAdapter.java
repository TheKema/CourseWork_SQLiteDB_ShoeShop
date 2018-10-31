package ainullov.kamil.com.shoeshop.user.adapters;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
import ainullov.kamil.com.shoeshop.manager.activeOrders.ActiveOrdersAdapter;
import ainullov.kamil.com.shoeshop.manager.pojo.ActiveOrdersPojo;

public class UserOrdersHistoryAdapter  extends RecyclerView.Adapter<UserOrdersHistoryAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<ActiveOrdersPojo> activeOrdersPojos;
    private Context context;

    public UserOrdersHistoryAdapter(Context context, List<ActiveOrdersPojo> activeOrdersPojos) {
        this.activeOrdersPojos = activeOrdersPojos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public UserOrdersHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.userordershistory_item, parent, false);
        return new UserOrdersHistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserOrdersHistoryAdapter.ViewHolder holder, int position) {
        ActiveOrdersPojo activeOrdersPojo = activeOrdersPojos.get(position);

        SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy, HH:mm", Locale.ENGLISH);
        String dayOfWeeki = formatDayOfWeek.format(Double.valueOf(activeOrdersPojo.getDate()));

        holder.tvOrderNumber.setText("Номер заказа: " + activeOrdersPojo.getOrderNumber());
        holder.tvDate.setText("Дата заказа: " + dayOfWeeki);
        holder.tvShoeName.setText("Название: " + activeOrdersPojo.getShoename());
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

        final TextView tvOrderNumber, tvDate, tvShoeName, tvType, tvGender, tvCoast, tvSize;

        ViewHolder(View view) {
            super(view);
            tvOrderNumber = (TextView) view.findViewById(R.id.tvOrderNumber);
            tvDate = (TextView) view.findViewById(R.id.tvDate);
            tvShoeName = (TextView) view.findViewById(R.id.tvShoeName);
            tvType = (TextView) view.findViewById(R.id.tvType);
            tvGender = (TextView) view.findViewById(R.id.tvGender);
            tvCoast = (TextView) view.findViewById(R.id.tvCoast);
            tvSize = (TextView) view.findViewById(R.id.tvSize);
        }
    }

}