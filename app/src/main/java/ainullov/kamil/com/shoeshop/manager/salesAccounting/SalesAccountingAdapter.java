package ainullov.kamil.com.shoeshop.manager.salesAccounting;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.manager.pojo.OrderAccountingPojo;

public class SalesAccountingAdapter extends RecyclerView.Adapter<SalesAccountingAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<OrderAccountingPojo> orderAccountingPojos;
    private Context context;

    public SalesAccountingAdapter(Context context, List<OrderAccountingPojo> orderAccountingPojos) {
        this.orderAccountingPojos = orderAccountingPojos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public SalesAccountingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.salesaccounting_item, parent, false);
        return new SalesAccountingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SalesAccountingAdapter.ViewHolder holder, int position) {
        OrderAccountingPojo orderAccountingPojo = orderAccountingPojos.get(position);

        SimpleDateFormat formatDayOfWeek = new SimpleDateFormat("dd.MM.yy, HH:mm", Locale.ENGLISH);
        String dayOfWeeki = formatDayOfWeek.format(Double.valueOf(orderAccountingPojo.getDate()));

        holder.tvGenderSAItem.setText("Пол: " + orderAccountingPojo.getGender());
        holder.tvTypeSAItem.setText("Вид: " + orderAccountingPojo.getType());
        holder.tvNameSAItem.setText("Название: " + orderAccountingPojo.getName());
        holder.tvCoastSAItem.setText("Цена: " + orderAccountingPojo.getCoast());
        holder.tvProviderSAItem.setText("Поставщик: " + orderAccountingPojo.getProvider());
        holder.tvSoldDateSAItem.setText("Дата продажи: " + dayOfWeeki);
        holder.tvSizeSAItem.setText("Размер: " + orderAccountingPojo.getSize());
    }

    @Override
    public int getItemCount() {
        return orderAccountingPojos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        final ConstraintLayout clSAitem;

        final TextView tvGenderSAItem, tvTypeSAItem, tvNameSAItem,
                tvCoastSAItem, tvProviderSAItem, tvSoldDateSAItem, tvSizeSAItem;

        ViewHolder(View view) {
            super(view);
            clSAitem = (ConstraintLayout) view.findViewById(R.id.clSAitem);
            tvGenderSAItem = (TextView) view.findViewById(R.id.tvGenderSAItem);
            tvTypeSAItem = (TextView) view.findViewById(R.id.tvTypeSAItem);
            tvNameSAItem = (TextView) view.findViewById(R.id.tvNameSAItem);
            tvCoastSAItem = (TextView) view.findViewById(R.id.tvCoastSAItem);
            tvProviderSAItem = (TextView) view.findViewById(R.id.tvProviderSAItem);
            tvSoldDateSAItem = (TextView) view.findViewById(R.id.tvDateSAItem);
            tvSizeSAItem = (TextView) view.findViewById(R.id.tvSizeSAItem);



        }
    }
}
